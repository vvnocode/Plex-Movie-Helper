package com.vv.tool.plex.movie.plexmoviehelper.service;

import com.vv.tool.plex.movie.plexmoviehelper.info.MediaInfo;
import com.vv.tool.plex.movie.plexmoviehelper.info.MediaInfoHandler;
import com.vv.tool.plex.movie.plexmoviehelper.properties.CustomSets;
import com.vv.tool.plex.movie.plexmoviehelper.tree.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

@Service
public class TidyMovieService {

    @Autowired
    private CustomSets customSets;

    /**
     * 用于模糊查询
     */
    private TreeMap<String, TreeNode<MediaInfo>> treeMap = new TreeMap<>();

    private List<String> deleted = new LinkedList<>();

    /**
     * 移动指定文件夹内的电影
     *
     * @param folders
     * @return
     */
    public int moveMovie(Collection<String> folders) {
        int tempCount = 0;
        if (ObjectUtils.isEmpty(folders)) {
            return tempCount;
        }
        for (String folder : folders) {
            detailFolder(folder);
        }

        //clean
        treeMap.clear();
        return tempCount;
    }

    private void detailFolder(String folderPath) {
        MediaInfo rootMediaInfo = new MediaInfo(customSets, new File(folderPath));
        TreeNode node = new TreeNode<>(rootMediaInfo);
        MediaInfoHandler.loadFiles(customSets, node);

        //处理媒体文件
        MediaInfoHandler.rootElementsConsumer(node, this::toTreeMap);

        MediaInfoHandler.rootElementsConsumer(node, this::move);

        //删除未移动的
        treeMap.forEach((key, value) -> {
            if (!deleted.contains(key)) {
                value.getData().getFile().delete();
            }
        });
    }

    private void toTreeMap(TreeNode<MediaInfo> node) {
        MediaInfo mediaInfo = node.getData();
        treeMap.put(mediaInfo.getFile().getPath(), node);
    }

    private void move(TreeNode<MediaInfo> node) {
        MediaInfo mediaInfo = node.getData();
        if (!mediaInfo.isAFile()) {
            return;
        }
        //只处理视频文件
        if (!mediaInfo.isMediaFile()) {
            return;
        }

        //电影名
        String prefix = mediaInfo.getFileWithoutSuffix();
        //父级路径
        String rootPath = node.getParent().getData().getFile().getPath();
        //模糊查询
        String firstKey = treeMap.ceilingKey(rootPath + File.separator + prefix);
        String lastKey = treeMap.floorKey(rootPath + File.separator + prefix + Character.MAX_VALUE);

        if (firstKey != null && lastKey != null) {

            String newRootPath;
            //判断当前电影是否在电影文件夹下
            if (prefix.equals(node.getParent().getData().getName())) {
                newRootPath = rootPath;
            } else {
                newRootPath = rootPath + "/" + mediaInfo.getFileWithoutSuffix();
            }
            boolean mkdir = mkdir(newRootPath);
            if (!mkdir) {
                System.out.println("无法创建文件夹");
            }

            //移动文件
            for (String key : treeMap.subMap(firstKey, true, lastKey, true).keySet()) {
                TreeNode<MediaInfo> n = treeMap.get(key);
                //当前文件完整路径
                String path = n.getData().getFile().getPath();

                Path from = n.getData().getFile().toPath();
                Path to = Paths.get(newRootPath + "/" + n.getData().getName());
                //判断是不是已经整理过了
                if (from.equals(to)) {
                    deleted.add(path);
                    continue;
                }

                System.out.println(from + " --> " + to);
                try {
                    Files.move(from, to);
                    System.out.println("File path has been changed.");
                    deleted.add(path);
                } catch (FileAlreadyExistsException e) {
                    System.out.println("File exists");
                    deleted.add(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Failed to change file path.");
                }

            }
        }

    }

    private boolean mkdir(String dirPath) {
        File folder = new File(dirPath);
        if (!folder.exists()) {
            return folder.mkdirs();
        }
        return true;
    }
}
