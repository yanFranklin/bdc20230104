package cn.gtmap.realestate.exchange.service.impl.inf.yzw;

import cn.gtmap.realestate.common.core.domain.BdcXtQtdjYwDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.exchange.ExchangeApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/12/24 15:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
//@ActiveProfiles("dev")
public class YzwServiceImplTest {
    @Autowired
    YzwServiceImpl yzwService;

    @Autowired
    @Qualifier("entityMapper")
    private EntityMapper entityMapper;

    @Value("${infApply.area.enble:false}")
    private boolean areaInf;
    @Value("${infApply.djxl.enble:false}")
    private boolean djxlInf;

    @Test
    public void shareYzwData() {
//        Example example = new Example(BdcXtQtdjYwDO.class);
//        Example.Criteria criteria = example.createCriteria();
//        if (areaInf) {
//            criteria.andEqualTo("xzdm", 100);
//        }
//        if(djxlInf){
//            criteria.andEqualTo("djxl", 100);
//        }
//        criteria.andEqualTo("gzldyid", "123");
//        List<BdcXtQtdjYwDO> listQtdjYw = entityMapper.selectByExample(example);
        yzwService.shareYzwData("457645",false);
    }
}