package com.monster;

import com.monster.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
@Slf4j
public class FtpTest {
    @Autowired
    private FtpUtil ftpUtil;
    @Test
    void upload(){
        String file="D:\\桌面东西\\截图\\image-20240624140636330.png";
        InputStream in =null;
        String uploadPath="app/ayi/2/ll";
        try{
             in = new FileInputStream(file);
            ftpUtil.uploadFile("2.png",uploadPath,in);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
