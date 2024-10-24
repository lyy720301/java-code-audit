package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SQLTest {
    static SqlSession sqlSession;

    @BeforeAll
    public static void before() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @AfterAll
    public static void after() {
        sqlSession.close();
    }

    @Test
    public void test1() {
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = mapper.selectBlogById(1);
        System.out.println(blog);
    }

    @Test
    public void test2() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 万能密码
        Integer res = mapper.verifyNotSafe("' or 1 = 1 -- ", "");
        System.out.println(res);
    }

    @Test
    public void test3() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 万能密码
        Integer res = mapper.verifySafe("' or 1 = 1 -- ", "");
        System.out.println(res); // --> null
    }

    @Test
    public void test4() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 万能密码
        // #{ }会自动传入值加上单引号，而${ }不会。
        /*
        如果不加 '，会报如下错误
        org.apache.ibatis.exceptions.PersistenceException:
### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column '你好' in 'where clause'
         */
        Integer res = mapper.verifyNotSafe("你好", "123");
        System.out.println(res); // --> null
    }

    /**
     * 模糊匹配注入
     */
    @Test
    public void test5() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 注入
        User user = mapper.selectUserNotSafe("%' #");
        System.out.println(user);
        // 安全
        User user1 = mapper.selectUserSafe("%' #");
        System.out.println(user1);
    }

    /**
     * order by 注入
     */
    @Test
    public void test6() {
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectOrderedStudent("age");
        System.out.println(students);
        System.out.println("\n\n");
        List<Student> students1 = mapper.selectOrderedStudent("if(1=1,age,no)");
        System.out.println(students1);
    }
}
