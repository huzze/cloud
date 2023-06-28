package com.example.mapper;


import com.example.entity.RPositionRole;

public interface RPositionRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(RPositionRole record);

    int insertSelective(RPositionRole record);

    RPositionRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RPositionRole record);

    int updateByPrimaryKey(RPositionRole record);
}
