package com.ftp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @program: pms
 * @description: 分块文件对象
 * @author: linky
 * @create: 2020-08-27 19:51
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChunkFile {
    /**
     * 文件id
     */
    private Long id;
    /**
     * 当前文件块序数
     */
    private Integer chunkNum;
    /**
     * 分块大小
     */
    private Long chunkSize;
    /**
     * 当前分块大小
     */
    private Long curChunkSize;
    /**
     * 文件总大小
     */
    private Long totalSize;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件标识(md5)
     */
    private String identifier;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 总块数
     */
    private Integer totalChunks;
    /**
     * 文件类型
     */
    private String type;
}
