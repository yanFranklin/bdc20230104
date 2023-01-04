package cn.gtmap.realestate.exchange.service.impl.inf.gx;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.inf.gx.GxService;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessXmlService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.ObjectUtils;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-28
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class GxServiceImplTest {

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Autowired
    private GxService gxService;

    @Autowired
    private AccesssModelHandlerService accesssModelHandlerService;

    @Test
    public void saveGxDataModel() {
        String xmid = "f16a2a5c57aaff8080816a1149290c3e";
        BdcXmDO bdcXmDO = null;
        if (StringUtils.isNotBlank(xmid)) {
            bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
        }
        if(bdcXmDO != null){
            // 获取不同类型的策略 service
            NationalAccessXmlService nationalAccessXmlService = accesssModelHandlerService.getAccessXmlService(bdcXmDO);
            if (!ObjectUtils.isEmpty(nationalAccessXmlService)) {
                // 获取 message
                MessageModel messageModel = accesssModelHandlerService.getMessageModel(nationalAccessXmlService, bdcXmDO.getXmid());
                System.out.println(JSONObject.toJSONString(messageModel.getDataModel()));
                gxService.saveGxDataModel(messageModel.getDataModel());
            } else {
                Assert.fail("没有找到处理服务");
            }
        }else{
            Assert.fail("没有找到项目");
        }
    }
}