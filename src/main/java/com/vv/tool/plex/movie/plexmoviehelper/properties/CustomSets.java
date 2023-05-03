package com.vv.tool.plex.movie.plexmoviehelper.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class CustomSets {

    @Autowired
    private CustomProperties customProperties;

    @Getter
    private Set<String> movieSuffixes;

    @Getter
    private Set<String> movieFolders;

    @Getter
    private Set<String> ignoreFilePrefix;

    @Getter
    private Set<String> infoSuffix;

    @Autowired
    public void setMovieSuffixes() {
        this.movieSuffixes = new HashSet<>(Arrays.asList(customProperties.getMovieSuffix().toLowerCase().split(",")));
    }

    @Autowired
    public void setMovieFolders() {
        movieFolders = new HashSet<>(Arrays.asList(customProperties.getMovieFolders().split(",")));
    }

    @Autowired
    public void setIgnoreFilePrefix() {
        ignoreFilePrefix = new HashSet<>(Arrays.asList(customProperties.getIgnoreFilePrefix().split(",")));
    }

    @Autowired
    public void setInfoSuffix() {
        infoSuffix = new HashSet<>(Arrays.asList(customProperties.getInfoSuffix().toLowerCase().split(",")));
    }

}
