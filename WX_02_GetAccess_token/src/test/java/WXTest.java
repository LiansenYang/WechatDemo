import com.LiansenYang.common.WXUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WXTest {
    private ApplicationContext context;

    @Before
    public void beforeTest() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void TestWxUtil(){
        WXUtil wxUtil = context.getBean(WXUtil.class);
        WXUtil.getAccessToken();
    }
}
