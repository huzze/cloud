package com.ftp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author cb
 * @date 2019/10/23 13:55
 * @Description: 文件基础bean
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileBase {
    /**
     * 文件id
     */
    private String id;
    /**
     * 文件url
     */
    private String url;
    /**
     * 下载文件地址
     */
    private String previewUrl;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件扩展名
     */
    private String extendName;
    /**
     * 文件大小
     */
    private Integer size;
    /**
     * md5值
     */
    private String md5;
    /**
     * 创建人
     */
    private String createUid;
    /**
     * 创建人名字
     */
    private String createName;
    /**
     * 修改人
     */
    private String updateUid;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 使用时间
     */
    private Date useTime;
    /**
     * 文件密码
     */
    private String password;
    /**
     * 密级
     */
    private Integer secret;
    /**
     * 加密文件Url
     */
    private String secretUrl;
    /**
     * 加密时间戳
     */
    private long secretTimestamp;
    /**
     * 权限人员
     */
    private String userIds;
}
