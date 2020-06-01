import com.LiansenYang.common.WXUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WXTest {
    private ApplicationContext context;


    @Test
    public void TestWxUtil(){
        Assert.assertNotNull(WXUtil.getInstance().getWeixinParameterMapFromCache().get("access_token"));
        System.out.println(WXUtil.getInstance().getWeixinParameterMapFromCache().get("access_token"));
    }
}
