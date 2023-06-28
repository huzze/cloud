package com.example.mapper;


import com.example.entity.RUserGroupRole;

public interface RUserGroupRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(RUserGroupRole record);

    int insertSelective(RUserGroupRole record);

    RUserGroupRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RUserGroupRole record);

    int updateByPrimaryKey(RUserGroupRole record);
}
