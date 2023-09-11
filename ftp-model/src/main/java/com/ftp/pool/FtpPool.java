package com.ftp.pool;

import com.ftp.client.FtpClientFactory;
import com.ftp.config.FtpConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * descriptionFTP连接池
 * 1.可以获取池中空闲链接
 * 2.可以将链接归还到池中
 * 3.当池中空闲链接不足时，可以创建链接
 * @author Linky
 * @date 2020/7/20 11:33
 */
@Slf4j
@Component
public class FtpPool {
    FtpClientFactory factory;
    private final GenericObjectPool<FTPClient> internalPool;

    /**
     * description 初始化连接池
     * @param factory
     * @return
     * @author Linky
     * @date 2020/7/20 11:37
     */
    public FtpPool(@Autowired FtpClientFactory factory) {
        this.factory = factory;
        FtpConfig config = factory.getConfig();
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(config.getMaxTotal());
        poolConfig.setMinIdle(config.getMinIdel());
        poolConfig.setMaxIdle(config.getMaxIdle());
        poolConfig.setMaxWaitMillis(config.getMaxWaitMillis());
        this.internalPool = new GenericObjectPool<>(factory, poolConfig);
        log.info("初始化FTP连接池完成：{}", internalPool);
    }

    /**
     * description 从连接池中取连接
     * @return org.apache.commons.net.ftp.FTPClient
     * @author Linky
     * @date 2020/7/20 11:37
     */
    public FTPClient getFTPClient() {
        try {
            FTPClient ftpClient = internalPool.borrowObject();
            log.info("从连接池中获取FTPClient：{}", ftpClient);
            return ftpClient;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * description 将链接归还到连接池
     * @param ftpClient
     * @return void
     * @author Linky
     * @date 2020/7/20 11:37
     */
    public void returnFTPClient(FTPClient ftpClient) {
        try {
            log.info("将FTPClient归还连接池：{}", ftpClient);
            internalPool.returnObject(ftpClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * description 销毁池子
     * @return void
     * @author Linky
     * @date 2020/7/20 11:37
     */
    public void destroy() {
        try {
            log.info("销毁FTP连接池");
            internalPool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}