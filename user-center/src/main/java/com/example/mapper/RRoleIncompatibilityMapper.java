package com.example.mapper;


import com.example.entity.RRoleIncompatibility;

public interface RRoleIncompatibilityMapper {
    int deleteByPrimaryKey(String id);

    int insert(RRoleIncompatibility record);

    int insertSelective(RRoleIncompatibility record);

    RRoleIncompatibility selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RRoleIncompatibility record);

    int updateByPrimaryKey(RRoleIncompatibility record);
}
