package com.jdbc.test.dao;

import com.jdbc.test.model.Group;
import com.jdbc.test.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by tangxu on 2017/1/15.
 */
@Repository("userJdbcDao")
public class UserDao implements IUserDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Resource
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(User user,int gid) {
        jdbcTemplate.update("insert into t_user(username,password,nickname,gid) value(?,?,?,?)",user.getUsername(),user.getPassword(),user.getNickname(),gid);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("update t_user set username=?,password=?,nickname=? where id=?",user.getUsername(),user.getPassword(),user.getNickname(),user.getId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from t_user where id=?",id);
    }

    @Override
    public User load(int id) {
        String sql = "select t1.id uid,t1.*,t2.* from t_user t1 left join t_group t2 on(t1.gid=t2.id) where t1.id=?";
        User u = (User) jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserMapper());
        return u;
    }

    @Override
    public List<User> list(String sql,Object[] args) {
        return jdbcTemplate.query(sql,args,new UserMapper());
    }

    private class UserMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group group = new Group();
            group.setId(rs.getInt("gid"));
            group.setName(rs.getString("name"));
            User user = new User();
            user.setGroup(group);
            user.setId(rs.getInt("uid"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setNickname(rs.getString("nickname"));
            return user;
        }
    }

}
