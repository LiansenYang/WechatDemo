import com.LiansenYang.common.PropertiesUtil;
import org.junit.Test;

/**
 * @description: 测试PropertiesUtil
 * @author: yangLs
 * @create: 2018-03-08 15:51
 **/
public class TestPropertiesUtil {

    @Test
    public void testPropertiesUtil(){
        System.out.println(PropertiesUtil.getAppConfig("weixin.app.id"));
    }
}
