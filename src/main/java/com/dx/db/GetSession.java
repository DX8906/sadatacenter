package com.dx.db;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 32825
 */
public class GetSession {
    public static SqlSession getSession(){
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("db.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        MybatisSqlSessionFactoryBuilder builder = new MybatisSqlSessionFactoryBuilder();
        //获取session工厂
        SqlSessionFactory sessionFactory = builder.build(inputStream);
        //获取一个session会话
        SqlSession session = sessionFactory.openSession();

        return session;
    }

}
