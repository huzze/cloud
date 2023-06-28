package com.example.mapper;


import com.example.entity.RUserRole;

public interface RUserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(RUserRole record);

    int insertSelective(RUserRole record);

    RUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RUserRole record);

    int updateByPrimaryKey(RUserRole record);
}
