package com.example.mapper;


import com.example.entity.RUserPosition;

public interface RUserPositionMapper {
    int deleteByPrimaryKey(String id);

    int insert(RUserPosition record);

    int insertSelective(RUserPosition record);

    RUserPosition selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RUserPosition record);

    int updateByPrimaryKey(RUserPosition record);
}
