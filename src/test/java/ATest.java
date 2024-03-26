import com.lulu040108.DAO.io.Resources;
import com.lulu040108.DAO.sqlSession.SqlSession;
import com.lulu040108.DAO.sqlSession.SqlSessionFactory;
import com.lulu040108.DAO.sqlSession.SqlSessionFactoryBuilder;
import com.mysql.cj.Session;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.io.InputStream;

/**
 * 测试SqlSesisionFactory是否可以成功创建
 */
public class ATest {
    @Test
    public void test01() throws DocumentException {
        InputStream inputStream= Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlsession=sqlSessionFactory.openSession();
//        sqlsession.selectOne("user.selectOne")

    }
}
