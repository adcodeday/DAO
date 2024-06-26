import com.lulu040108.DAO.dao.IUserDao;
import com.lulu040108.DAO.dao.User;
import com.lulu040108.DAO.io.Resources;
import com.lulu040108.DAO.sqlSession.SqlSession;
import com.lulu040108.DAO.sqlSession.SqlSessionFactory;
import com.lulu040108.DAO.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

/**
 * 测试SqlSesisionFactory是否可以成功创建
 */
public class ATest {
    @Test
    public void test01() throws Exception {
        InputStream inputStream= Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlsession=sqlSessionFactory.openSession();
//        sqlsession.selectOne("user.selectOne")
        User user = new User();
        user.setId(1);
        user.setUsername("tom");
        IUserDao mapper = sqlsession.getMapper(IUserDao.class);
        User byCondition = mapper.findByCondition(user);
        System.out.println(byCondition);
        sqlsession.close();

    }
}
