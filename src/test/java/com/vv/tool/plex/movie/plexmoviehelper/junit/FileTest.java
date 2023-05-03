package com.vv.tool.plex.movie.plexmoviehelper.junit;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

public class FileTest {

    @Test
    public void test() throws UnsupportedEncodingException {
        String str = ",/Volumes/erotic/三级/香港三级片83部117G,";
        byte[] gbkBytes = str.getBytes("UTF-8");
        String isoStr = new String(gbkBytes, "ISO-8859-1");
        System.out.println(isoStr);

    }

}
