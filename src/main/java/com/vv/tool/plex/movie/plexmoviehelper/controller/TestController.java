package com.vv.tool.plex.movie.plexmoviehelper.controller;

import com.vv.tool.plex.movie.plexmoviehelper.properties.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private CustomProperties customProperties;

    @GetMapping("properties")
    public String properties() {
        return customProperties.toString();
    }
}
