package com.ftp.service;

import com.ftp.domain.ChunkFile;

/**
 * @program: cloud
 * @description:
 * @author: 林开颜
 * @create: 2023/10/23 9:37
 */
public interface ChunkFileService {
    /**
     * description 分块新增
     * @param chunkFile
     * @return void
     * @author Linky
     * @date 2020/8/27 20:03
     */
    void add(ChunkFile chunkFile);
    /**
     * description 通过文件标识（md5）与文件块序数查询
     * @param identifier
     * @param chunkNum
     * @return com.jw.pms.domain.ChunkFile
     * @author Linky
     * @date 2020/8/27 20:29
     */
    ChunkFile getByIdentifierAndChunkNum(String identifier, Integer chunkNum);
}
