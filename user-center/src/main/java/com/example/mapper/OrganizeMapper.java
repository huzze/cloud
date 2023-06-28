package com.example.mapper;


import com.example.entity.Organize;

public interface OrganizeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Organize record);

    int insertSelective(Organize record);

    Organize selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Organize record);

    int updateByPrimaryKey(Organize record);
}
