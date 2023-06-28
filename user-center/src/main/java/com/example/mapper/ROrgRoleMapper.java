package com.example.mapper;


import com.example.entity.ROrgRole;

public interface ROrgRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(ROrgRole record);

    int insertSelective(ROrgRole record);

    ROrgRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ROrgRole record);

    int updateByPrimaryKey(ROrgRole record);
}
