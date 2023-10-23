package com.ftp.service.impl;

import com.ftp.dao.ChunkFileMapper;
import com.ftp.domain.ChunkFile;
import com.ftp.service.ChunkFileService;

import javax.annotation.Resource;

/**
 * @program: cloud
 * @description: 文件上传业务类
 * @author: 林开颜
 * @create: 2023/10/23 9:37
 */
public class ChunkFileServiceImpl implements ChunkFileService {
    @Resource
    private ChunkFileMapper chunkFileMapper;

    @Override
    public void add(ChunkFile chunkFile) {
        chunkFileMapper.add(chunkFile);
    }

    @Override
    public ChunkFile getByIdentifierAndChunkNum(String identifier, Integer chunkNum) {
        return chunkFileMapper.getByIdentifierAndChunkNum(identifier, chunkNum);
    }
}
