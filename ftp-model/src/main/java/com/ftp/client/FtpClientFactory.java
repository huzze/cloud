package com.ftp.client;

import com.ftp.config.FtpConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * description ftp客户端工程
 * @author Linky
 * @date 2020/7/20 11:25
 */
@Slf4j
@Component
public class FtpClientFactory implements PooledObjectFactory<FTPClient> {
    @Resource
    private FtpConfig config;

    /**
     * description  创建连接到池中
     * @return org.apache.commons.pool2.PooledObject<org.apache.commons.net.ftp.FTPClient>
     * @author Linky
     * @date 2020/7/20 11:35
     */
    @Override
    public PooledObject<FTPClient> makeObject() {
        //创建客户端实例
        FTPClient ftpClient = new FTPClient();
        return new DefaultPooledObject<>(ftpClient);
    }

    /**
     * description 销毁连接，当连接池空闲数量达到上限时，调用此方法销毁连接
     * @param pooledObject
     * @return void
     * @author Linky
     * @date 2020/7/20 11:35
     */
    @Override
    public void destroyObject(PooledObject<FTPClient> pooledObject) {
        FTPClient ftpClient = pooledObject.getObject();
        try {
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not disconnect from server.", e);
        }
    }

    /**
     * description 链接状态检查
     * @param pooledObject
     * @return boolean
     * @author Linky
     * @date 2020/7/20 11:35
     */
    @Override
    public boolean validateObject(PooledObject<FTPClient> pooledObject) {
        FTPClient ftpClient = pooledObject.getObject();
        try {
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * description 初始化连接
     * @param pooledObject
     * @return void
     * @author Linky
     * @date 2020/7/20 11:35
     */
    @Override
    public void activateObject(PooledObject<FTPClient> pooledObject) throws Exception {
        FTPClient ftpClient = pooledObject.getObject();
        ftpClient.setConnectTimeout(5000);
        ftpClient.connect(config.getIp(), config.getPort());
        boolean login = ftpClient.login(config.getUser(), config.getPassword());
        if (!login) {
            log.error("ftpClient登录失败!");
        }
        ftpClient.setControlEncoding(config.getCharset());
        ftpClient.changeWorkingDirectory(config.getPath());
        ftpClient.setDataTimeout(config.getTimeout());
        ftpClient.setBufferSize(config.getBufferSize());
        //设置上传文件类型为二进制，否则将无法打开文件
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setUseEPSVwithIPv4(false);
        //进入被动模式
        ftpClient.enterLocalPassiveMode();
        if (!ftpClient.printWorkingDirectory().equals(config.getPath())) {
            ftpClient.changeWorkingDirectory(config.getPath());
        }
    }

    /**
     * description 钝化连接，使链接变为可用状态
     * @param pooledObject
     * @return void
     * @author Linky
     * @date 2020/7/20 11:36
     */
    @Override
    public void passivateObject(PooledObject<FTPClient> pooledObject) throws Exception {
        FTPClient ftpClient = pooledObject.getObject();
        try {
            ftpClient.changeWorkingDirectory(config.getPath());
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not disconnect from server.", e);
        }
    }

    /**
     * description 用于连接池中获取pool属性
     * @return com.jw.pms.config.FtpConfig
     * @author Linky
     * @date 2020/7/20 11:36
     */
    public FtpConfig getConfig() {
        return config;
    }
}
