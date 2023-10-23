package com.ftp.dao;

import com.ftp.domain.ChunkFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @program: cloud
 * @description:
 * @author: 林开颜
 * @create: 2023/10/23 9:39
 */
@Mapper
public interface ChunkFileMapper {
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
    ChunkFile getByIdentifierAndChunkNum(@Param("identifier") String identifier, @Param("chunkNum") Integer chunkNum);
}
