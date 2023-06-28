package com.example.mapper;


import com.example.entity.Authority;

public interface AuthorityMapper {
    int deleteByPrimaryKey(String id);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);
}
