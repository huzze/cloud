package com.example.mapper;


import com.example.entity.RUserGroup;

public interface RUserGroupMapper {
    int deleteByPrimaryKey(String id);

    int insert(RUserGroup record);

    int insertSelective(RUserGroup record);

    RUserGroup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RUserGroup record);

    int updateByPrimaryKey(RUserGroup record);
}
