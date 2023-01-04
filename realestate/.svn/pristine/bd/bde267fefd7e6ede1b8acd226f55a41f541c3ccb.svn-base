package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.App;
import cn.gtmap.realestate.accept.service.BdcYcslManageService;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: realestate
 * @description: 一窗受理服务实现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-05-26 14:10
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class BdcYcslManageServiceImplTest {
    @Autowired
    BdcYcslManageService bdcYcslManageService;
    @Autowired
    BdcYcslManageServiceImpl bdcYcslManageServiceImpl;

    @Test
    @Rollback
    public void testchangeYwxxDTOToBdcSlYcslxx() throws Exception {
        List<BdcYwxxDTO> bdcYwxxDTOList = new ArrayList<>(10);
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        BdcXmDO bdcXmDO = new BdcXmDO();
        String xmid = UUIDGenerator.generate16();
        BdcYwxxDTO bdcYwxxDTO = new BdcYwxxDTO();
        BdcFdcqDO bdcFdcqDO = new BdcFdcqDO();
        bdcFdcqDO.setXmid(xmid);
        bdcFdcqDO.setGhyt(1);
        bdcYwxxDTO.setBdcQl(bdcFdcqDO);
        bdcXmDO.setXmid(xmid);
        bdcYwxxDTO.setBdcXm(bdcXmDO);
        bdcYwxxDTOList.add(bdcYwxxDTO);
        List<BdcSlXmDTO> bdcSlXmDOList = new ArrayList<>(10);
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        bdcSlXmDO.setXmid(xmid);
        bdcSlXmDO.setJbxxid(xmid);
        BdcSlFwxxDO bdcSlFwxxDO = new BdcSlFwxxDO();
        bdcSlFwxxDO.setFwlx(1);
        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
        bdcSlXmDTO.setBdcSlQl(bdcSlFwxxDO);
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
        bdcSlXmDOList.add(bdcSlXmDTO);
        bdcSlxxDTO.setBdcSlXmList(bdcSlXmDOList);
        bdcYcslManageService.changeYwxxDTOToBdcSlYcslxx(bdcYwxxDTOList,bdcSlxxDTO);
    }
}
