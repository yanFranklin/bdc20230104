package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.service.BdcQllxService;
import cn.gtmap.realestate.accept.util.KieBase;
import cn.gtmap.realestate.common.core.dto.accept.QllxDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/2/25
 * @description 不动产权利服务
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BdcQllxServiceImplTest {
    @Autowired
    BdcQllxService bdcQllxService;

    @Test
    public void getQllxByBdcdyh() {
        KieSession kieSession = KieBase.getKieSessionBySessionName("qllx");
        String bdcdyh = "320505400202GB00001F00010069";
        QllxDTO qllxDTO = new QllxDTO(StringUtils.substring(bdcdyh, 12, 13), StringUtils.substring(bdcdyh, 13, 14), StringUtils.substring(bdcdyh, 19, 20),"1");
        kieSession.insert(qllxDTO);
        List<Integer> qllxs = new ArrayList<>();
        kieSession.setGlobal("qllxs", qllxs);
        int count = kieSession.fireAllRules();
        kieSession.dispose();
        System.out.println(qllxs);
    }

    @Test
    public void getQllxByBdcdyh1() {
        Long startTime =System.currentTimeMillis();
        String bdcdyh = "320684100302JA00001W00000000";
        Integer qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("1",qllx.toString());
        bdcdyh = "320684100302ZA00001W00000000";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("1",qllx.toString());
        bdcdyh ="320684100001GA00001W00010000";
        Long startTime1 =System.currentTimeMillis();
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("2",qllx.toString());
        bdcdyh ="320684100002GB00631W00000000";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("3",qllx.toString());
        bdcdyh = "320684100302JB00001W00000000";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("7",qllx.toString());
        bdcdyh = "320684100302ZS00001W00000000";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("7",qllx.toString());
        bdcdyh ="340111303004GB00165F00010490";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("4",qllx.toString());
        bdcdyh = "320684108205JB00160F00010001";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("8",qllx.toString());
        bdcdyh = "320684108205JC00160W00010001";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("5",qllx.toString());
        bdcdyh = "320684108205GC00160W00010001";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("5",qllx.toString());
        bdcdyh = "320684108205ZC00160W00010001";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("5",qllx.toString());
        bdcdyh = "320684108205JC00160F00010001";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("6",qllx.toString());
        bdcdyh = "320684108205GE00160L00010001";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("10",qllx.toString());
        bdcdyh = "320684108205GL00160W00010001";
        qllx =bdcQllxService.getQllxByBdcdyh(bdcdyh,"1");
        Assert.assertEquals("11",qllx.toString());

    }


}