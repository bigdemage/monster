package com.monster;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@SpringBootTest
@Slf4j
public class Font2Test {

    @Test
    void duqu() throws Exception {
        Resource resource = new ClassPathResource("/font/simsun.ttc");
        Font font = Font.createFont(Font.TRUETYPE_FONT, resource.getFile());
        log.info(font.getFontName());
        log.info(font.getName());

    }
    public static void main(String[] args) throws IOException, FontFormatException {

//        printFont();

        URL resource = Font2Test.class.getClassLoader().getResource("resources/font/simsun.ttc");
        String path = resource.getPath();

//        Font font= Font.createFont(Font.TRUETYPE_FONT, new File("classpath:/font/simsun.ttc"));
//
//        log.info(font.getFontName());
//        log.info(font.getName());


    }

    private static void printFont() {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = e.getAllFonts();
        for (Font font : fonts) {
            String fontName = font.getFontName();
            log.info("fontName:{}",fontName);
        }

        {
            String[] fontNames = e.getAvailableFontFamilyNames();
            for (String fontName : fontNames) {
                log.info("availName:{}",fontName);
            }
        }
    }
}
