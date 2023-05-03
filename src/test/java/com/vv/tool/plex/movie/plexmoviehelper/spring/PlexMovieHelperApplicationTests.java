package com.vv.tool.plex.movie.plexmoviehelper.spring;

import com.vv.tool.plex.movie.plexmoviehelper.properties.CustomSets;
import com.vv.tool.plex.movie.plexmoviehelper.service.TidyMovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class PlexMovieHelperApplicationTests {

    @Autowired
    private CustomSets customSets;

    @Autowired
    private TidyMovieService tidyMovieService;

    @Test
    void contextLoads() {
        Set<String> movieFolders = customSets.getMovieFolders();
        System.out.println(movieFolders);

        tidyMovieService.moveMovie(movieFolders);
    }

}
