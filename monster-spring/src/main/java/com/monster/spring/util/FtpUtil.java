package com.monster.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * ftp工具类
 */
@Component
@Slf4j
public class FtpUtil {

    private FTPSClient ftpClient = new FTPSClient("TLS", true);

    //本地字符编码
    private static String LOCAL_CHARSET = "UTF-8";

    @Value("${ftp.url}")
    public String url;//ftpClient url

    @Value("${ftp.port}")
    public int port;//端口

    @Value("${ftp.username}")
    public String username;//登录用户名

    @Value("${ftp.password}")
    public String password;//登录密码


    /**
     * Description: 向FTP服务器上传文件
     *
     * @param filename 文件名
     * @param input    输入流
     * @throws IOException
     */
    public void uploadFile(String filename, String path, InputStream input) {
        /**
         * 连接FTP服务器
         * 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
         */
        try {
            log.info("向FTP服务器上传文件,url:{},port:{},path:{},username:{},password:{},filename:{}", url, port, path, username, password, filename);
            ftpClient.connect(url, port);
            //登录
            boolean loginflag=ftpClient.login(username, password);

            //ftp连接的返回码：230表示连接成功，其他值表示连接失败
            int reply = ftpClient.getReplyCode();
            log.info("ftp登录结果:{}", reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.error("未连接到FTP，用户名密码错误");
                return;
            }
            /**
             * 这里修改为被动模式
             * 主动模式：客户端开放端口给服务端用；
             * 被动模式：服务端开放端口给客户端用。
             * 由于很多客户端在防火墙内，开放端口给服务器端用比较困难。所以用被动模式的时候比较多。
             */
            ftpClient.enterLocalPassiveMode();
            // 修改工作目录为path指定的目录
//            workingDirectory(ftpClient, path);
            //修改上传目录，没有则创建
            setUpFilePath(ftpClient, path);
            //查看服务器的编码格式是UTF-8还是GBK 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
//            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
//                LOCAL_CHARSET = "UTF-8";
//            }
            ftpClient.setControlEncoding(LOCAL_CHARSET);
            //ftp上传文件是以文本形式传输的，所以多媒体文件会失真，需要转为二进制形式传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.execPBSZ(0);
            ftpClient.execPROT("P");
            //文件名字进行编码转换
            boolean result = ftpClient.storeFile(utf8TOiso88591(filename,LOCAL_CHARSET), input);
            log.info("上传结果:{}", result);
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            log.error("上传文件失败", e);
        } finally {
            try {
                if (input != null) input.close();
            } catch (IOException e) {
                log.error("流关闭失败", e);
            }
        }

    }

    public void setUpFilePath(FTPClient ftp, String path) throws IOException {
        //切换到上传目录
        if (!ftp.changeWorkingDirectory(path)) {
            //如果目录不存在创建目录
            String[] dirs = path.split("/");
            String tempPath = "";
            for (String dir : dirs) {
                if (null == dir || "".equals(dir)) continue;
                tempPath += "/" + dir;
                if (!ftp.changeWorkingDirectory(tempPath)) {  //进不去目录，说明该目录不存在
                    if (!ftp.makeDirectory(tempPath)) { //创建目录
                        //如果创建文件目录失败，则返回
                        log.error("创建文件目录{}失败", tempPath);
                    } else {
                        //目录存在，则直接进入该目录
                        ftp.changeWorkingDirectory(tempPath);
                    }
                }
            }
        }
    }

    public void workingDirectory(FTPClient ftp, String path) {
        try {
            //切换工作目录为根目录
            ftp.changeWorkingDirectory("/");
            if (StringUtils.isBlank(path) || path.equals("/")) {
                //如果输入的路径为空或者为根路径，则不转换操作目录
            } else {
                //否则创建想要上传文件的目录，并且将操作目录转为新创建的目录
                //循环创建多级目录
                String directory = path.substring(0, path.lastIndexOf("/") + 1);
                if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"), "iso-8859-1"))) {

                    // 如果远程目录不存在，则递归创建远程服务器目录
                    int start = 0;
                    int end = 0;
                    if (directory.startsWith("/")) {
                        start = 1;
                    } else {

                        start = 0;
                    }
                    end = directory.indexOf("/", start);
                    while (true) {

                        String subDirectory = new String(path.substring(start, end).getBytes("GBK"),
                                "iso-8859-1");
                        if (!ftpClient.changeWorkingDirectory(subDirectory)) {

                            if (ftpClient.makeDirectory(subDirectory)) {

                                ftpClient.changeWorkingDirectory(subDirectory);

                            } else {

                                System.out.println("创建目录失败");
                            }
                        }
                        start = end + 1;
                        end = directory.indexOf("/", start);
                        // 检查所有目录是否创建完毕
                        if (end <= start) {

                            break;
                        }
                    }
                }
                //这个创建目录的函数只能创建一级目录，不能创建多级目录
                //boolean b = ftp.makeDirectory(gbkTOiso88591(path));
                //切换当前的工作目录为新建的目录
                ftp.changeWorkingDirectory(path);
            }
        } catch (Exception e) {
            log.error("FTP上传文件进行创建目录结构的时候出现了异常:" + e.getMessage());
        }
    }

    public String utf8TOiso88591(String filename, String codestyle) {
        try {
            return new String(filename.getBytes(codestyle), "iso-8859-1");
        } catch (Exception e) {
            log.error("FTP上传文件进行" + codestyle + "转ISO-8859-1的时候出现了异常:" + e.getMessage() + "返回的结果就还是传进来的参数");
            return filename;
        }
    }

    public String gbkTOiso88591(String path) {
        try {
            return new String(path.getBytes("GBK"), "iso-8859-1");
        } catch (Exception e) {
            log.error("FTP上传文件进行GBK转ISO-8859-1的时候出现了异常", e);
            return path;
        }
    }


    /**
     * Description: 删除ftp上的文件
     *
     * @param filePath 文件路径(后边跟上了文件名)
     * @throws IOException
     */
    public void deleteFile(String filePath) throws IOException {
        ftpClient.connect(url, port);
        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
        ftpClient.login(username, password);
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {

            ftpClient.disconnect();
            log.error("FTP断开了");
        }
        //修改为被动模式
        ftpClient.enterLocalPassiveMode();
        //删除文件
        ftpClient.deleteFile(filePath);
    }

    /**
     * Description: 下载/浏览器预览  需要的流信息
     *
     * @param filePath 文件路径(后边跟上了文件名)
     * @throws IOException
     */
    public InputStream getStream(String filePath) throws IOException {
        ftpClient.connect(url, port);//连接FTP服务器
        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
        ftpClient.login(username, password);//登录
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            log.error("FTP断开了");
        }
        InputStream inputStream = ftpClient.retrieveFileStream(filePath);
        return inputStream;
    }


}
