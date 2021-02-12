package com.naichuan.security.uaa.dao;

import com.naichuan.security.uaa.model.PermissionDto;
import com.naichuan.security.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Naichuan Zhang
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate template;

    public UserDto getUserByUsername(String username) {
        String sql = "select id, username, password, fullname, mobile from t_user where username = ?";
        List<UserDto> list = template.query(sql, new Object[] {username}, new BeanPropertyRowMapper<>(UserDto.class));
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    public List<String> getPermissionsByUserId(String userId) {
        String sql = "select tp.id, code, tp.description, url from t_user tu\n" +
                "left join t_user_role tur on tu.id = tur.user_id\n" +
                "left join t_role tr on tr.id = tur.role_id\n" +
                "left join t_role_permission trp on tr.id = trp.role_id\n" +
                "left join t_permission tp on tp.id = trp.permission_id\n" +
                "where user_id = ?";
        List<PermissionDto> list = template.query(sql, new Object[] {userId}, new BeanPropertyRowMapper<>(PermissionDto.class));
        return list.stream().map(PermissionDto::getCode).collect(Collectors.toList());
    }
}
