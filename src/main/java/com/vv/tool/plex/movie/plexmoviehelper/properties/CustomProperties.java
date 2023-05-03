package com.vv.tool.plex.movie.plexmoviehelper.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "plex")
public class CustomProperties {

    private String movieFolders;
    private String movieSuffix;
    private String infoSuffix;
    private String ignoreFilePrefix;
}
