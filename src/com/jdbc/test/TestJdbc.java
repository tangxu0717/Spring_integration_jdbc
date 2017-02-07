package com.jdbc.test;

import com.jdbc.test.dao.IGroupDao;
import com.jdbc.test.dao.IUserDao;
import com.jdbc.test.model.Group;
import com.jdbc.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tangxu on 2017/1/15.
 * 当使用以下注释以后，就可以直接在Test中进行依赖注入
 */
//让Junit运行在Spring的测试环境中
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/com/jdbc/test/beans.xml")//加载beans.xml文件
public class TestJdbc {
    @Resource(name = "userJdbcDao")
    private IUserDao userJdbcDao;

    @Resource(name = "groupJdbcDao")
    private IGroupDao groupJdbcDao;

    @Test
    public void testAdd(){
        /*Group group = new Group();
        group.setName("administrator");
        groupJdbcDao.add(group);*/
        User u = new User();
        u.setUsername("jack");
        u.setPassword("123");
        u.setNickname("jack");
        userJdbcDao.add(u,1);
    }

    @Test
    public void testUpdate(){
        User u = new User();
        u.setId(1);
        u.setUsername("jack");
        u.setPassword("abc");
        u.setNickname("jacktx");
        userJdbcDao.update(u);
    }

    @Test
    public void testDelete(){
        userJdbcDao.delete(1);
    }

    @Test
    public void testLoad(){
        User user = userJdbcDao.load(2);
        System.out.println(user.getNickname()+","+user.getGroup().getName());
    }

    @Test
    public void testList(){
        List<User> list = userJdbcDao.list("select t1.id uid,t1.*,t2.* from t_user t1 left join t_group t2 on(t1.gid=t2.id)",null);
        for(User user : list){
            System.out.println(user.getNickname());
        }
    }
}
