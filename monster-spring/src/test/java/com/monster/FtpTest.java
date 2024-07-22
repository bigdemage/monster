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
        String file="D:\\桌面东西\\截图\\1.png";
        InputStream in =null;
        String uploadPath="1/2016/11/16/55/FIR";
        try{
             in = new FileInputStream(file);
            ftpUtil.uploadFile("thisispic.png",uploadPath,in);
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
