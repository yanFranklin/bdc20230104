package cn.gtmap.realestate.init.service.xmxx;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.GzwQlrDO;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.xmxx.impl.InitBdcXmServiceImpl;
import cn.gtmap.realestate.init.util.DozerUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/28
 * @description 项目初始化单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config-dbunit.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = App.class)
public class InitBdcXmServiceImplTest {
    @Autowired
    private InitBdcXmServiceImpl initBdcXmService;
    @Autowired
    private BdcXmService bdcXmService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/InitBdcXmSetup-data.xml")
    public void initBdcXmTest(){
        InitServiceQO initServiceQO = getInitServiceQO("qjfw");
        BdcXmDO bdcXmDO = initBdcXmService.initBdcXm(initServiceQO,null);
        //原项目信息获取断言
        Assert.assertEquals("320508055012GB13335F65900000",bdcXmDO.getBdcdyh());
        //不动产单元信息获取断言
        Assert.assertEquals(4,bdcXmDO.getBdcdyfwlx().intValue());
        //权籍土地信息获取断言
        Assert.assertEquals("320506",bdcXmDO.getQxdm());
        //项目基本配置信息获取断言
        Assert.assertEquals("测试获取配置登记原因",bdcXmDO.getDjyy());
        //更新权利类型断言
        Assert.assertEquals(44,initServiceQO.getBdcCshFwkgSl().getQllx().intValue());
        //组合业务原项目信息获取断言
        initServiceQO = getInitServiceQO("qjfw");
        initServiceQO.setYxmid("5469");
        InitServiceDTO initServiceDTO = new InitServiceDTO();
        BdcXmDO bdcXmDOZhyw = new BdcXmDO();
        bdcXmDOZhyw = bdcXmService.queryBdcXmByPrimaryKey("5469");
        initServiceDTO.setBdcXm(bdcXmDOZhyw);
        initServiceQO.getServiceDataMap().put("5469",initServiceDTO);
        bdcXmDO = initBdcXmService.initBdcXm(initServiceQO,null);
        Assert.assertEquals("320508055012GB13335F99999999",bdcXmDO.getBdcdyh());
    }

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/InitBdcXmSetup-data.xml")
    public void initTest(){
        InitServiceDTO initServiceDTO = new InitServiceDTO();
        InitServiceQO initServiceQO = getInitServiceQO("qjfw");
        try{
            initServiceDTO = initBdcXmService.init(initServiceQO,initServiceDTO);
        }catch (Exception exception){
        }
        Assert.assertEquals("320508055012GB13335F65900000",initServiceDTO.getBdcXm().getBdcdyh());
    }

    public InitServiceQO getInitServiceQO(String sjly){
        InitServiceQO initServiceQO = new InitServiceQO();
        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setDjxl("55");
        initServiceQO.setBdcXm(bdcXmDO);
        initServiceQO.setYxmid("546984618749814165");
        initServiceQO.setBdcdyResponseDTO(getBdcdyResponseDTO(sjly));
        initServiceQO.setDjxxResponseDTO(getDjxxResponseDTO(sjly));
        initServiceQO.setGzwDcbResponseDTO(getGzwDcbResponseDTO(sjly));
        if(sjly.equals("qjfw")||sjly.equals("qjgzw")){
            initServiceQO.setBdcdyh("320508021040GB00600F00010202");
            initServiceQO.setXmid("469846954");
        }
        if(sjly.equals("qjtd")){
            initServiceQO.setBdcdyh("320508055012GB13335W00000000");
            initServiceQO.setXmid("469846954");
        }
        BdcCshFwkgSlDO bdcCshFwkgSlDO=new BdcCshFwkgSlDO();
        bdcCshFwkgSlDO.setQlsjly("2,1");
        initServiceQO.setBdcCshFwkgSl(bdcCshFwkgSlDO);
        return initServiceQO;
    }

    public BdcdyResponseDTO getBdcdyResponseDTO(String sjly){
        BdcdyResponseDTO bdcdyResponseDTO = new BdcdyResponseDTO();
        bdcdyResponseDTO.setBdcdyfwlx("4");
        bdcdyResponseDTO.setQlrList(getFwfcqlrList());
        return bdcdyResponseDTO;
    }

    public List<FwFcqlrDO> getFwfcqlrList(){
        List<FwFcqlrDO> fwFcqlrDOList = new ArrayList<>();
        FwFcqlrDO fwFcqlrDO = new FwFcqlrDO();
        fwFcqlrDO.setQlr("房屋房产权利人1");
        fwFcqlrDO.setQlrzjh("88888888");
        fwFcqlrDO.setQlrxz("1");
        fwFcqlrDO.setBz("备注");
        fwFcqlrDO.setDh("65468468-65647");
        FwFcqlrDO fwFcqlrDO2 = new FwFcqlrDO();
        fwFcqlrDO2.setQlr("房屋房产权利人2");
        fwFcqlrDO2.setQlrzjh("66666666");
        fwFcqlrDO2.setQlrxz("1");
        fwFcqlrDO2.setBz("备注2");
        fwFcqlrDO2.setDh("654646-46546");
        fwFcqlrDOList.add(fwFcqlrDO);
        fwFcqlrDOList.add(fwFcqlrDO2);
        return fwFcqlrDOList;
    }

    public DjxxResponseDTO getDjxxResponseDTO(String sjly){
        DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
        djxxResponseDTO.setQlrList(getDjQlrList());
        djxxResponseDTO.setDjDcbResponseDTO(getDjDcbResponseDTO());
        return djxxResponseDTO;
    }

    public DjDcbResponseDTO getDjDcbResponseDTO(){
        ZdDjdcbResponseDTO zdDjdcbResponseDTO = new ZdDjdcbResponseDTO();
        zdDjdcbResponseDTO.setScmj(265.52);
        zdDjdcbResponseDTO.setQsxz("2");
        zdDjdcbResponseDTO.setDjh("320506568667686578");
        return zdDjdcbResponseDTO;
    }

    public List<DjxxQlrDTO> getDjQlrList(){
        DjxxQlrDTO djxxQlrDTO = new DjxxQlrDTO();
        djxxQlrDTO.setQlrmc("地籍权利人1");
        djxxQlrDTO.setQlrlx("1");
        djxxQlrDTO.setSxh(2);
        djxxQlrDTO.setZjh("5857");
        djxxQlrDTO.setZjzl("2");
        djxxQlrDTO.setBz("土地权利人");
        DjxxQlrDTO djxxQlrDTO2 = new DjxxQlrDTO();
        djxxQlrDTO2.setQlrmc("地籍权利人2");
        djxxQlrDTO2.setQlrlx("1");
        djxxQlrDTO2.setSxh(1);
        djxxQlrDTO2.setZjh("6546987464646566");
        djxxQlrDTO2.setZjzl("1");
        djxxQlrDTO2.setBz("土地权利人2");
        List<DjxxQlrDTO> djxxQlrDTOList = new ArrayList<>();
        djxxQlrDTOList.add(djxxQlrDTO);
        djxxQlrDTOList.add(djxxQlrDTO2);
        return djxxQlrDTOList;
    }

    public GzwDcbResponseDTO getGzwDcbResponseDTO(String sjly){
        GzwDcbResponseDTO gzwDcbResponseDTO =new GzwDcbResponseDTO();
        gzwDcbResponseDTO.setGzwQlrDOList(getGzwQlrList());
        return gzwDcbResponseDTO;
    }

    public List<GzwQlrDO> getGzwQlrList(){
        List<GzwQlrDO> gzwQlrDOList =new ArrayList<>();
        GzwQlrDO gzwQlrDO = new GzwQlrDO();
        gzwQlrDO.setQlr("构筑物权利人1");
        gzwQlrDO.setDh("546687-465468");
        gzwQlrDO.setQlrzjh("54585748548");
        gzwQlrDO.setQlrzjlx("3");
        gzwQlrDO.setQlrxz("5");
        gzwQlrDOList.add(gzwQlrDO);
        return gzwQlrDOList;
    }
    @Autowired
    private DozerUtils dozerUtils;
    @Test
    public void test(){
        String yxmJSon="{\"ajzt\":2,\"bdcdyfwlx\":4,\"bdcdyh\":\"340103103001GB00115F00040006\",\"bdcdywybh\":\"8853918\",\"bdclx\":2,\"bdcqzh\":\"皖(2018)合肥市不动产证明第10012791号\",\"bz\":\"3\",\"dbr\":\"王国堂\",\"djbmdm\":\"29798\",\"djjg\":\"合肥市不动产登记中心\",\"djlx\":900,\"djsj\":1526256920000,\"djxl\":\"9000402\",\"djyy\":\"遗失补证(无公告)\",\"dzwmj\":93.38,\"dzwyt\":10,\"gyfs\":0,\"gzldymc\":\"遗失补证(无公告)\",\"gzljdmc\":\"归档\",\"jssj\":1526453484000,\"mjdw\":1,\"qllx\":95,\"qlr\":\"陈建高\",\"qlrlx\":\"个人\",\"qlrzjh\":\"340824197709290012\",\"qlrzjzl\":\"身份证\",\"qszt\":1,\"qxdm\":\"340103\",\"sfwtaj\":0,\"slbh\":\"66100300\",\"slr\":\"戚春惠\",\"slrid\":\"5435772\",\"slsj\":1525847302000,\"sqfbcz\":1,\"sqzsbs\":0,\"xmid\":\"B64K6QLK77QMDZ77D\",\"xmly\":2,\"ycqzh\":\"皖(2018)合肥市不动产权第10022291号\",\"ywr\":\"王登泽\",\"ywrzjh\":\"340103500108403\",\"ywrzjzl\":\"1\",\"zdzhyt\":\"0701\",\"zl\":\"庐阳区安庆路310号3幢101室\"}\n";
        String xmJson="{\"ajzt\":1,\"bdcdyfwlx\":4,\"bdcdyh\":\"340103103001GB00115F00040006\",\"bdcdywybh\":\"8853918\",\"bdclx\":2,\"cnqx\":3,\"djbmdm\":\"01\",\"djjg\":\"合肥项目组\",\"djxl\":\"9990403\",\"dzwmj\":93.38,\"dzwyt\":10,\"gzldymc\":\"房屋抵押权变更登记\",\"gzlslid\":\"327221\",\"mjdw\":1,\"qllx\":95,\"qlxz\":\"101\",\"qszt\":0,\"qxdm\":\"340104\",\"sfwtaj\":0,\"slbh\":\"201908010068\",\"slr\":\"蒋灵峰\",\"slrid\":\"ff8080816b9d43bd016bf3c7a058002a\",\"slsj\":1564648374000,\"sqfbcz\":1,\"sqzsbs\":0,\"ssxz\":\"340103103\",\"xmid\":\"381G3243JQOBN61Z\",\"xmly\":1,\"ycqzh\":\"皖(2018)合肥市不动产证明第10012791号\",\"zdzhmj\":20837.06,\"zdzhyt\":\"0701\",\"zdzhyt2\":\"0701\",\"zl\":\"庐阳区安庆路310号3幢101室\"}\n";
        BdcXmDO ybdcxm= JSONObject.parseObject(yxmJSon,BdcXmDO.class);
        BdcXmDO bdcXmDO=JSONObject.parseObject(xmJson,BdcXmDO.class);
        dozerUtils.initBeanDateConvert(ybdcxm, bdcXmDO, "yxmyzh");
    }
}
