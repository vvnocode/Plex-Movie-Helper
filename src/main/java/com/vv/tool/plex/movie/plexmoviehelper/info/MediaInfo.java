package com.vv.tool.plex.movie.plexmoviehelper.info;

import com.vv.tool.plex.movie.plexmoviehelper.properties.CustomSets;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Set;

@Data
@NoArgsConstructor
public class MediaInfo {

    /**
     * 文件对象
     */
    private File file;
    /**
     * 是否为文件
     */
    private boolean isAFile;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 文件去除后缀
     */
    private String fileWithoutSuffix;
    /**
     * 是否是媒体文件
     */
    private boolean isMediaFile;
    /**
     * 是否是媒体信息文件
     */
    private boolean isMediaInfo;

    public MediaInfo(CustomSets customSets, File file) {
        Set<String> videoSuffixSet = customSets.getMovieSuffixes();
        Set<String> infoSuffixSet = customSets.getInfoSuffix();

        this.file = file;
        String name = file.getName();
        this.name = name;

        if (file.isFile()) {
            this.isAFile = true;
            String[] split = name.split("\\.");
            if (split.length > 1) {
                String fileSuffix = split[split.length - 1];
                this.fileSuffix = fileSuffix;
                int indexOf = name.indexOf(fileSuffix);
                this.fileWithoutSuffix = name.substring(0, indexOf - 1);
                if (videoSuffixSet != null && videoSuffixSet.contains(fileSuffix.toLowerCase())) {
                    this.isMediaFile = true;
                } else if (infoSuffixSet != null && infoSuffixSet.contains(fileSuffix.toLowerCase())) {
                    this.isMediaInfo = true;
                }
            }
        }

    }

}
