package com.lulu040108.DAO.config.io;

import java.io.InputStream;

public class Resources {
    public static InputStream getResourceAsStream(String path){
        InputStream resourceAsStream= Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}