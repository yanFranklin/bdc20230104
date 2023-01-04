package cn.gtmap.realestate.exchange.test;


import cn.gtmap.realestate.exchange.ReportAloneApp;
import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.KttFwCDO;
import cn.gtmap.realestate.exchange.core.dto.BdcCshFwkgSlDO;
import cn.gtmap.realestate.exchange.core.mapper.qj.AccessBuildingMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.Example;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReportAloneApp.class)
public class DataSwitchTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSwitchTest.class);

    @Autowired
    BdcdjMapper bdcdjMapper;

    @Autowired
    BdcXmMapper bdcXmMapper;

    @Autowired
    AccessBuildingMapper accessBuildingMapper;

    @Autowired
    @Qualifier("serverEntityMapper")
    EntityMapper serverEntityMapper;

    @Autowired
    @Qualifier("gxEntityMapper")
    EntityMapper gxEntityMapper;

    @Autowired
    @Qualifier("qjEntityMapper")
    EntityMapper qjEntityMapper;

    @Test
    public void testDataSwitch() {
        String xmid = "592I10217BDK6003";

        BdcXmDO bdcXmDO = this.bdcdjMapper.queryBdcXm(xmid);
        LOGGER.info("1.bdcdjMapper.queryBdcXm[BdcXmDO]: {}", JSON.toJSONString(bdcXmDO));

        BdcXmDO bdcXmDO1 = bdcXmMapper.queryBdcXm(xmid);
        LOGGER.info("2.bdcXmMapper.queryBdcXm[BdcXmDO]: {}", JSON.toJSONString(bdcXmDO1));

        if(Objects.nonNull(bdcXmDO) && StringUtils.isNotBlank(bdcXmDO.getBdcdyh())){
            Map<String, String> param = new HashMap<>();
            param.put("bdcdyh", bdcXmDO.getBdcdyh());
            List<KttFwCDO> kttFwCDOList = accessBuildingMapper.queryKttFwCList(param);
            LOGGER.info("3.accessBuildingMapper.queryKttFwCList[List<KttFwCDO> ]: {}", JSON.toJSONString(kttFwCDOList));
        }

        BdcCshFwkgSlDO bdcCshFwkgSlDO = serverEntityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class, bdcXmDO.getXmid());
        LOGGER.info("4.serverEntityMapper.selectByPrimaryKey[BdcCshFwkgSlDO]: {}", JSON.toJSONString(bdcCshFwkgSlDO));
    }
}
