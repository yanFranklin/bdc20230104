package cn.gtmap.realestate.init.service.qlr;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.GzwQlrDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxQlrDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import cn.gtmap.realestate.init.App;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.qlr.impl.InitLpbToBdcQlrServiceImpl;
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
 * @description 获取权籍权利人做不动产权利人服务单元测试
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
public class InitLpbToBdcQlrServiceImplTest {
    @Autowired
    private InitLpbToBdcQlrServiceImpl initLpbToBdcQlrService;
    @Autowired
    private BdcXmService bdcXmService;

    @Test
    @Transactional(transactionManager = "transactionManager")
    @Rollback
    @DatabaseSetup(value = "/data/InitBdcQlrSetup-data.xml")
    public void initQlr(){
        BdcXmDO bdcXm=bdcXmService.queryBdcXmByPrimaryKey("546984618749814165");
        InitServiceQO initServiceQO = getInitServiceQO("qjfw",bdcXm);
        List<BdcQlrDO> bdcQlrDOList = initLpbToBdcQlrService.initQlr(initServiceQO);
        Assert.assertEquals("房屋房产权利人1",bdcQlrDOList.get(0).getQlrmc());
        Assert.assertEquals("1",bdcQlrDOList.get(0).getQlrlb());
        initServiceQO = getInitServiceQO("qjtd",bdcXm);
        bdcQlrDOList = initLpbToBdcQlrService.initQlr(initServiceQO);
        Assert.assertEquals("地籍权利人2",bdcQlrDOList.get(1).getQlrmc());
        Assert.assertEquals("1",bdcQlrDOList.get(0).getQlrlb());
        initServiceQO = getInitServiceQO("qjgzw",bdcXm);
        initServiceQO.setBdcdyDTO(initServiceQO.getGzwDcbResponseDTO().getGzwDcbDO());
        bdcQlrDOList = initLpbToBdcQlrService.initQlr(initServiceQO);
        Assert.assertEquals("构筑物权利人1",bdcQlrDOList.get(0).getQlrmc());
        Assert.assertEquals("1",bdcQlrDOList.get(0).getQlrlb());
    }

    public InitServiceQO getInitServiceQO(String sjly,BdcXmDO bdcXm){
        InitServiceQO initServiceQO = new InitServiceQO();
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
        initServiceQO.setBdcXm(bdcXm);
        return initServiceQO;
    }

    public BdcdyResponseDTO getBdcdyResponseDTO(String sjly){
        BdcdyResponseDTO bdcdyResponseDTO = new BdcdyResponseDTO();
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
        return djxxResponseDTO;
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

}
