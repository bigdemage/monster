package com.monster.spring.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.monster.spring.entity.User;
import com.monster.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    private static File fontFile = null;

    @PostConstruct
    public void init() {
//        try {
//            log.info("初始化字体库");
//            //创建一个临时文件
//            File tempFile = File.createTempFile("temp", ".ttc");
//            Resource resource = new ClassPathResource("/font/simsun.ttc");
//            //将InputStream中的数据写入到临时文件中
//            InputStream inputstream = resource.getInputStream();
//            OutputStream outputstream = new FileOutputStream(tempFile);
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = inputstream.read(buffer)) != -1) {
//                outputstream.write(buffer, 0, length);
//            }
//            //关闭输入流和输出流
//            inputstream.close();
//            outputstream.close();
//            //将临时文件转换为File对象
//            File file = new File(tempFile.getAbsolutePath());
//            fontFile = file;
//        } catch (Exception e) {
//            log.error("初始化fontFile异常",e);
//        }

    }


    @GetMapping("/all")
    public List<User> getPort() {
        return userService.query();
    }

    @GetMapping("/findByPage")
    public IPage<User> getPort(@RequestParam Integer page, @RequestParam Integer size) {
        return userService.query(page, size);
    }

    @GetMapping("/findFont")
    public String findFont() throws IOException, FontFormatException {
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        return font.getFontName();
    }

    @PostMapping("/insert")
    public String insert(@RequestBody User user) {
       userService.insert(user);
       return "good";
    }

    @GetMapping("/getFile")
    public ResponseEntity<byte[]> getFile() throws IOException, FontFormatException {
        File file = new File("home/pic/download_113640.png");
        byte[] imageBytes = Files.readAllBytes(file.toPath());
        // 构建 HTTP 响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);
        // 返回包含图片字节数组的 ResponseEntity
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }



}
