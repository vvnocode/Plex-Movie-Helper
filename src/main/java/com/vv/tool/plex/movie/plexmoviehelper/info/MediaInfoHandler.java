package com.vv.tool.plex.movie.plexmoviehelper.info;

import com.vv.tool.plex.movie.plexmoviehelper.properties.CustomSets;
import com.vv.tool.plex.movie.plexmoviehelper.tree.TreeNode;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class MediaInfoHandler {

    public static void loadFiles(CustomSets customSets, TreeNode<MediaInfo> node) {
        MediaInfo mediaInfo = node.getData();
        File file = mediaInfo.getFile();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                //对mac上奇奇怪怪前缀进行过滤
                if (!supportFilePrefix(customSets.getIgnoreFilePrefix(), f.getName())) {
                    continue;
                }

                MediaInfo fMediaInfo = new MediaInfo(customSets, f);

                TreeNode<MediaInfo> childNode = node.addChild(fMediaInfo);
                loadFiles(customSets, childNode);
            }
        }

    }


    private static boolean supportFilePrefix(Collection<String> set, String fileName) {
        for (String fix : set) {
            if (fileName.startsWith(fix)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 处理root下面的所有对象（所有数据都放到一层了，所以遍历一次）
     *
     * @param consumer
     */
    public static void rootElementsConsumer(TreeNode<MediaInfo> node, Consumer<TreeNode<MediaInfo>> consumer) {

        //遍历处理每个文件
        List<TreeNode<MediaInfo>> elementsIndex = node.getElementsIndex();
        for (TreeNode<MediaInfo> t : elementsIndex) {
            MediaInfo data;
            if (t.isRoot() || (data = t.getData()) == null || (!data.isMediaFile() && !data.isMediaInfo())) {
                continue;
            }

            //传入的函数
            consumer.accept(t);
        }
    }
}
