package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.AuthorityDto;
import com.example.entity.Authority;
import com.example.mapper.AuthorityMapper;
import com.example.service.AuthorityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {
    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public void save(AuthorityDto authorityDto) {

    }
}
