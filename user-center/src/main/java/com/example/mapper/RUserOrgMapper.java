package com.example.mapper;


import com.example.entity.RUserOrg;

public interface RUserOrgMapper {
    int deleteByPrimaryKey(String id);

    int insert(RUserOrg record);

    int insertSelective(RUserOrg record);

    RUserOrg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RUserOrg record);

    int updateByPrimaryKey(RUserOrg record);
}
