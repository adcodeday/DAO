import com.lulu040108.io.Resources;
import com.lulu040108.sqlSession.SqlSessionFactory;
import com.lulu040108.sqlSession.SqlSessionFactoryBuilder;
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

    }
}
