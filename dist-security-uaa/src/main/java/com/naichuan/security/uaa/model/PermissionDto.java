package com.naichuan.security.uaa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naichuan Zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {
    private String id;
    private String code;
    private String description;
    private String url;
}
