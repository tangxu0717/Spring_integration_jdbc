package com.jdbc.test.dao;

import com.jdbc.test.model.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by tangxu on 2017/1/16.
 */
@Repository("groupJdbcDao")
public class GroupJdbcDao implements IGroupDao {
    private JdbcTemplate jdbcTemplate;

    @Resource
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void add(Group group) {
        jdbcTemplate.update("insert into t_group(name) values(?)",group.getName());
    }
}
