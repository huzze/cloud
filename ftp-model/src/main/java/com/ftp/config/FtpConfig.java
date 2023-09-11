package com.ftp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description ftp配置
 * @author Linky
 * @date 2020/7/20 11:19
 */
@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpConfig {
    /**
     * ftp ip
     */
    private String ip;
    /**
     * ftp 端口
     */
    private Integer port;
    /**
     * ftp用户
     */
    private String user;
    /**
     * ftp密码
     */
    private String password;
    /**
     * ftp路径
     */
    private String path = "/";
    /**
     * ftp nginx路径
     */
    private String nginx;
    /**
     * ftp承载系统编码
     */
    private String charset = "UTF-8";
    /**
     * 最大连接数，默认值 DEFAULT_MAX_TOTAL = 8
     */
    private Integer maxTotal = 8;
    /**
     * 最小空闲连接数， 默认值 DEFAULT_MIN_IDLE = 0
     */
    private Integer minIdel = 0;
    /**
     * ftp缓冲区大小
     */
    private Integer bufferSize = 1048576;
    /**
     * 最大空闲连接数， 默认值 DEFAULT_MAX_IDLE = 8
     */
    private Integer maxIdle = 8;
    /**
     * 当连接池资源用尽后，调用者获取连接时的最大等待时间
     */
    private Integer maxWaitMillis = 3000;
    /**
     * ftp数据链接超时时间
     */
    private Integer timeout = 300000;
    /**
     * ftp链接超时
     */
    private Integer connectTimeout = 5000;
}