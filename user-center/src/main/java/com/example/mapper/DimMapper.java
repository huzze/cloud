package com.example.mapper;


import com.example.entity.Dim;

public interface DimMapper {
    int deleteByPrimaryKey(String id);

    int insert(Dim record);

    int insertSelective(Dim record);

    Dim selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Dim record);

    int updateByPrimaryKey(Dim record);
}
