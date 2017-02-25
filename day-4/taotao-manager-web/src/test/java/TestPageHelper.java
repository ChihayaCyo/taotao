import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by zh on 2/22/2017.
 */
public class TestPageHelper {

    @Test
    public void testPageHelper() throws Exception{
        //1-获得mapper代理对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        //2-设置分页
        PageHelper.startPage(1,30);
        //3-执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //4-取分页后的结果
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        long total = pageInfo.getTotal();
        System.out.println("total: "+total);
        int pages = pageInfo.getPages();
        System.out.println("pages: "+pages);
        int pageSize = pageInfo.getPageSize();
        System.out.println("pageSize: "+pageSize);
    }

}
