package com.ftp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: 上传文件对象
 * @author Linky
 * @date 2019/10/17 19:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {
    /**
     * 文件id
     */
    private String id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件路径
     */
    private String url;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 文件类型 eg."text/javascript"
     */
    private String type;
    /**
     * 文件MD5
     */
    private String md5;
}
