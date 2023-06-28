package com.example.mapper;


import com.example.entity.RRoleAuthority;

public interface RRoleAuthorityMapper {
    int deleteByPrimaryKey(String id);

    int insert(RRoleAuthority record);

    int insertSelective(RRoleAuthority record);

    RRoleAuthority selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RRoleAuthority record);

    int updateByPrimaryKey(RRoleAuthority record);
}
