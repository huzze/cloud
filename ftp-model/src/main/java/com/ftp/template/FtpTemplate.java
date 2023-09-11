package com.ftp.template;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ftp.config.FtpConfig;
import com.ftp.dto.UploadFile;
import com.ftp.pool.FtpPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * description 提供文件上传和下载
 * @author Linky
 * @date 2020/7/20 11:33
 */
@Slf4j
@Component
public class FtpTemplate {
    @Resource
    private FtpConfig config;
    @Resource
    private FtpPool pool;
    /**
     * FTP协议里面，规定文件名编码为iso-8859-1
     */
    private String serverCharset = "ISO-8859-1";

    private static String getCurrentDic() {
        LocalDateTime currentDate = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        return DateTimeFormatter.ofPattern("yyyyMMdd").format(currentDate);
    }
    private boolean existFile(FTPClient ftpClient, String pathName) throws IOException {
        boolean result = ftpClient.changeWorkingDirectory(getCurrentDic());
        if(!result){
            return false;
        }
        String newPathName = new String(pathName.getBytes(config.getCharset()),
                serverCharset);
        FTPFile[] ftpFileArr = ftpClient.listFiles(newPathName);
        ftpClient.changeToParentDirectory();
        return ftpFileArr.length > 0;
    }
    private String renameAutoIncrement(FTPClient ftpClient, String fileName) throws IOException {
        StringBuilder stringBuffer = new StringBuilder();
        int index = fileName.lastIndexOf(".");
        String name;
        if (index != -1) {
            name = fileName.substring(0, index);
        } else {
            name = fileName;
        }
        String type = fileName.substring(index);
        boolean b = existFile(ftpClient, fileName);
        log.info("上传文件地址:{}", ftpClient.printWorkingDirectory());
        if (b) {
            stringBuffer.setLength(0);
            stringBuffer.append(name).append("-").append(System.currentTimeMillis()).append(type);
            fileName = stringBuffer.toString();
        }
        return fileName;
    }

    private boolean saveFileToFtp(FTPClient ftpClient, InputStream fis, String filename, String... pathname) throws IOException {
        boolean flag;
        String dic = getCurrentDic();
        String workingDic = pathname.length < 1 ? dic : pathname[0];
        try {
            boolean changeDic =ftpClient.changeWorkingDirectory(workingDic);
            if(!changeDic){
                boolean makeResult = ftpClient.makeDirectory(workingDic);
                log.info("makeResult:" + makeResult);
                ftpClient.changeWorkingDirectory(workingDic);
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            log.info("upload_dic:" + ftpClient.printWorkingDirectory());
            flag = ftpClient.storeFile(new String(filename.getBytes(config.getCharset()),
                    serverCharset), fis);
            if (flag) {
                log.info("文件上传成功！");
            } else {
                log.warn("文件上传不成功！");
                // todo 抛出异常
            }
            fis.close();
        } catch (IOException e) {
            log.error("上传文件失败{}", e);
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    public String uploadFile(InputStream is, String fileName) {
        String workingDic = getCurrentDic();
        FTPClient ftpClient = pool.getFTPClient();
        try {
            fileName = fileName.replaceAll(" ", "");
            String newFileName = renameAutoIncrement(ftpClient, fileName);
            saveFileToFtp(ftpClient, is, newFileName, workingDic);
            return workingDic + "/" + newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("upload failed:{}", e);
        } finally {
            log.info("开始归还连接");
            //归还资源
            pool.returnFTPClient(ftpClient);
        }
        return "";
    }
    public UploadFile copyFile(String fromPath){
        if (StringUtils.isNotEmpty(fromPath)) {
            FTPClient ftpClient = pool.getFTPClient();
            try {
                log.info("当前路径:{}",ftpClient.printWorkingDirectory());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String baseUrl = fromPath.substring(0, fromPath.lastIndexOf("/"));
            log.info("来源文件基础路径: {}", baseUrl);
            String sourceDir = baseUrl.substring(baseUrl.lastIndexOf("/") + 1);
            log.info("源文件文件夹: {}", sourceDir);
            LocalDateTime currentDate = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
            String targetDir = DateTimeFormatter.ofPattern("yyyyMMdd").format(currentDate);
            log.info("目标文件夹: {}", targetDir);
            String baseFileName = fromPath.substring(fromPath.lastIndexOf("/") + 1);
            UploadFile newFile = copyDirectory(ftpClient, sourceDir, targetDir, baseFileName);
            String toPath = baseUrl.substring(0, baseUrl.lastIndexOf("/")) + newFile.getUrl();
            newFile.setUrl(toPath);
            log.info("新文件路径: {}", newFile.getUrl());
            if (Objects.nonNull(newFile)) {
                return newFile;
            } else {
                return null;
            }
        } else{
            return null;
        }
    }

    private UploadFile copyDirectory(FTPClient ftpClient, String formPath, String toPath, String fileName) {
        boolean copyFlag;
        FTPFile[] fileList;

        UploadFile uploadFile = null;
        try {
            ftpClient.enterLocalPassiveMode();
            fileList = ftpClient.listFiles(formPath);
            // 跳转路径到源文件路径下
            ftpClient.changeWorkingDirectory(formPath);
            FTPFile ftpFile;
            String category;
            InputStream inputStream;
            log.info("源路径: {}", ftpClient.printWorkingDirectory());
            for (FTPFile file : fileList) {
                ftpFile = file;
                category = ftpFile.getName();
                if (ftpFile.isFile() && category.equals(fileName)) {
                    // 如果是文件则复制文件
                    // 复制文件时掉用了retrieveFileStream方法
                    inputStream = ftpClient.retrieveFileStream(new String(category.getBytes(config.getCharset()), serverCharset));
                    // 释放客户端连接
                    ftpClient.disconnect();
                    log.info("初始化ftpClient");
                    // 初始化客户端
                    ftpClient = pool.getFTPClient();
                    if (inputStream != null) {
                        String fileNewName = renameAutoIncrement(ftpClient, fileName);
                        boolean b = ftpClient.changeWorkingDirectory(toPath);
                        if (!b) {
                            // 如果是目录则先创建该目录
                            ftpClient.makeDirectory(toPath);
                        }
                        log.info("目标路径:{}", ftpClient.printWorkingDirectory());
                        copyFlag = ftpClient.storeFile((new String(fileNewName.getBytes(config.getCharset()),
                                serverCharset)), inputStream);
                        uploadFile = new UploadFile();
                        // todo 缺个依赖
//                        uploadFile.setMd5(DigestUtils.md5Hex(inputStream));
                        uploadFile.setName(fileName);

                        uploadFile.setSize(file.getSize());
                        // 关闭文件流
                        inputStream.close();

                        if (!copyFlag) {
                            return null;
                        }
                        String newPath = "/" + toPath + "/" + fileNewName;
                        uploadFile.setUrl(newPath);
                        log.info("新文件路径:{}", newPath);
                    }
                }
            }
        } catch (IOException e) {
            log.error("FtpClientUtil.copyDirectory failed. caused by " + e.getMessage(), e);
            return null;
        }
        return uploadFile;

    }

    public String uploadFile(MultipartFile file) {
        InputStream inputStream = null;
        String workingDic = getCurrentDic();
        FTPClient ftpClient = pool.getFTPClient();
        try {
            inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename().replaceAll(" ", "");
            String newFileName = renameAutoIncrement(ftpClient, fileName);
            saveFileToFtp(ftpClient, inputStream, newFileName, workingDic);
            return workingDic + "/" + newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("upload failed:{}", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            pool.returnFTPClient(ftpClient);
        }
        return "";
    }
    /**
     * 上传文件.自动分配到当前日期对应的目录
     *
     * @param file 上传文件的文件全名
     * @return 相对路径/文件名
     */
    public String uploadFile(File file, String... isExist) {
        InputStream inputStream = null;
        String workingDic = getCurrentDic();
        FTPClient ftpClient = pool.getFTPClient();
        try {
            inputStream = new FileInputStream(file);
            if (null != isExist && isExist.length > 0) {
                workingDic = isExist[0];
            }
            String fileName = file.getName().replaceAll(" ", "");
            String newFileName;
            if (null == isExist || isExist.length == 0) {
                newFileName = renameAutoIncrement(ftpClient, fileName);
            } else {
                newFileName = fileName;
            }
            saveFileToFtp(ftpClient, inputStream, newFileName, workingDic);
            return workingDic + "/" + newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("upload failed:{}", e);
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (ftpClient != null && ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    /**
     * 删除文件
     *
     * @param pathname
     * @param filename
     * @return
     */
    public boolean deleteFile(String pathname, String filename, FTPClient... ftpClients) {
        boolean flag = false;
        FTPClient ftpClient = null;
        if (ftpClients != null && ftpClients.length > 0) {
            ftpClient = ftpClients[0];
        } else {
            ftpClient = new FTPClient();
        }
        try {
            log.info("开始删除文件");
            ftpClient = pool.getFTPClient();
            ftpClient.enterLocalPassiveMode();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            flag = ftpClient.deleteFile(new String(filename.getBytes(config.getCharset()),
                    serverCharset));
            if (flag) {
                log.info("删除文件成功！");
            } else {
                log.info("删除文件失败！");
            }
        } catch (Exception e) {
            log.info("删除文件失败");
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient != null && ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    /**
     * 删除FTP上的文件
     *
     * @param pathName 文件路径
     * @return boolean 是否删除
     */
    public boolean deleteFile(String pathName) {
        boolean isDelete;
        FTPClient ftpClient = pool.getFTPClient();
        try {
            isDelete = ftpClient.deleteFile(pathName);
            return isDelete;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ftpClient != null && ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Description: 向FTP服务器上传文件
     *
     * @param file 上传到FTP服务器上的文件
     * @return 成功返回文件名，否则返回null
     * @Version2.0
     */
    public String upload(MultipartFile file) throws Exception {
        FTPClient ftpClient = pool.getFTPClient();
        //开始进行文件上传
        String fileName = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        InputStream input = file.getInputStream();
        try {
            //执行文件传输
            boolean result = ftpClient.storeFile(fileName, input);
            //上传失败
            if (!result) {
                throw new RuntimeException("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {//关闭资源
            input.close();
            log.info("开始归还连接");
            //归还资源
            pool.returnFTPClient(ftpClient);
        }
        return fileName;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param fileName FTP服务器中的文件名
     * @param resp     响应客户的响应体
     * @Version1.0
     */
    public void downLoad(String fileName, HttpServletResponse resp) throws IOException {
        FTPClient ftpClient = pool.getFTPClient();
        // 设置强制下载不打开 MIME
        resp.setContentType("application/force-download");
        // 设置文件名
        resp.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        //将文件直接读取到响应体中
        OutputStream out = resp.getOutputStream();
        ftpClient.retrieveFile(config.getNginx() + "/" + fileName, out);
        out.flush();
        out.close();
        pool.returnFTPClient(ftpClient);
    }


}