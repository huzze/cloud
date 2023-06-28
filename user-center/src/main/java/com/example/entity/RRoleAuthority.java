package com.example.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * authority
 * @author
 */
@Data
public class RRoleAuthority implements Serializable {
    private String id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限类型
     */
    private Byte type;

    /**
     * 父权限id
     */
    private String parentId;

    private static final long serialVersionUID = 1L;
}
