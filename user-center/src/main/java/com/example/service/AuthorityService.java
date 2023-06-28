package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.AuthorityDto;
import com.example.entity.Authority;

public interface AuthorityService extends IService<Authority> {
    void save(AuthorityDto authorityDto);
}
