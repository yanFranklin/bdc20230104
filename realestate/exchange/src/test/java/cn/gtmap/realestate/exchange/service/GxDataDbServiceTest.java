package cn.gtmap.realestate.exchange.service;

import cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.util.DoubleUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-26
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class GxDataDbServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GxDataDbServiceTest.class);

    @Resource(name = "gxEntityMapper")
    private EntityMapper gxEntityMapper;

    @Test
    public void getData() {
        Map map = new HashMap();
        map.put("ywh","016F1323QWWV8352");
        map.put("djdl","200");
        DjtDjSlsqDO DO = gxEntityMapper.selectByPrimaryKey(DjtDjSlsqDO.class,map);
        System.out.println(JSONObject.toJSONString(DO));
    }

    @Test
    public void testPldyDealMessageModelDyje(){
        // 被担保被担保主债权数额
        Double zbdbzzqse =1200.00;
        Double sumbdbzzqse =0.00;
        Map<String, Double> ywhMjMap =new HashMap<>();
        ywhMjMap.put("xmid1",669.83);
        ywhMjMap.put("xmid2",650.10);
        ywhMjMap.put("xmid3",669.83);
        ywhMjMap.put("xmid4",669.83);
        ywhMjMap.put("xmid5",669.83);
        Double dzwZmj =3329.42;
        for(int i=0;i<5;i++) {
            Double bdbzzqse = countDyje(dzwZmj, MapUtils.getDouble(ywhMjMap, "xmid"+(i+1)), new Double(zbdbzzqse));

            //bdcXmList里的计算逻辑修改为总金额-之前的所有金额
            if (StringUtils.equals("xmid"+(i+1), "xmid5")) {
                bdbzzqse = DoubleUtils.sub(new Double(zbdbzzqse)
                        , sumbdbzzqse);

            }
            sumbdbzzqse =DoubleUtils.sum(sumbdbzzqse,bdbzzqse);
            LOGGER.info("bdbzzqse:{}", bdbzzqse);
        }

    }

    private static Double countDyje(Double dzwZmj, Double singleDzwmj, Double dyje) {
        if (dyje != null && singleDzwmj != null && dzwZmj != null) {
            Double result = (singleDzwmj / dzwZmj) * dyje;

            if(result.isInfinite() || result.isNaN()){
                return null;
            }

            BigDecimal b = new BigDecimal(result);
            result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return result;
        }
        return null;
    }

}