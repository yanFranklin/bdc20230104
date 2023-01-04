package cn.gtmap.realestate.accept.service.impl.initslxx;

import cn.gtmap.realestate.accept.core.service.BdcSlJyxxService;
import cn.gtmap.realestate.accept.service.BdcAddGwcSjclCommonService;
import cn.gtmap.realestate.accept.service.InitBdcSlxxService;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxInitDTO;
import cn.gtmap.realestate.common.core.qo.accept.InitSlxxQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCshXtPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/5/30
 * @description 带限制权利(换证)初始化受理信息
 */
@Service
public class InitBdcSlxxWithDyServiceImpl implements InitBdcSlxxService {

    /**
     * 土地抵押转房屋抵押流程
     */
    @Value("#{'${accept.lcbs.tddyzfwdy:}'.split(',')}")
    private List<String> tddyzfwdy;

    /**
     * 当前配置支持的限制权利:抵押权,经营权,居住权
     */
    private static final Integer[] BDC_XZQL = {CommonConstantUtils.QLLX_DYAQ_DM,CommonConstantUtils.QLLX_NYDJYQ,CommonConstantUtils.QLLX_JZQ};

    @Autowired
    BdcDjbxxFeignService bdcDjbxxFeignService;
    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcAddGwcSjclCommonService bdcAddGwcSjclCommonService;

    @Override
    public BdcSlxxInitDTO initBdcSlxx(InitSlxxQO initSlxxQO) {
        BdcSlxxInitDTO bdcSlxxInitDTO = new BdcSlxxInitDTO();
        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        if (ArrayUtils.contains(BDC_XZQL, initSlxxQO.getBdcSlYwxxDTO().getQllx())) {
            List<BdcQl> bdcQlList = listBdcQlxx(initSlxxQO.getBdcSlYwxxDTO().getBdcdyh(),initSlxxQO.getBdcSlYwxxDTO().getQllx().toString());
            if(CollectionUtils.isNotEmpty(tddyzfwdy) &&tddyzfwdy.contains(initSlxxQO.getGzldyid())) {
                //查询宗地抵押
                String dzwTzm = initSlxxQO.getBdcSlYwxxDTO().getBdcdyh().substring(19, 20);
                if (StringUtils.equals(CommonConstantUtils.DZWTZM_FW, dzwTzm)) {
                    String zdbdcdyh = StringUtils.substring(initSlxxQO.getBdcSlYwxxDTO().getBdcdyh(), 0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                    List<BdcQl> zdbdcQlList = listBdcQlxx(zdbdcdyh, initSlxxQO.getBdcSlYwxxDTO().getQllx().toString());
                    if (CollectionUtils.isNotEmpty(zdbdcQlList)) {
                        bdcQlList.addAll(zdbdcQlList);
                    }
                }
            }
            //根据权利信息生成项目
            if (CollectionUtils.isNotEmpty(bdcQlList)) {
                for (BdcQl bdcQl : bdcQlList) {
                    BdcSlXmDTO bdcSlXmDTO =bdcAddGwcSjclCommonService.initBdcSlXmDTOForWithdy(initSlxxQO,bdcQl.getXmid());
                    bdcSlXmDOList.add(bdcSlXmDTO.getBdcSlXm());
                    bdcSlXmLsgxDOList.addAll(bdcSlXmDTO.getBdcSlXmLsgxList());

                }
            }
            if(CollectionUtils.isNotEmpty(initSlxxQO.getBdcSlYwxxDTO().getDyxmidList())){
                for(String dyxmid:initSlxxQO.getBdcSlYwxxDTO().getDyxmidList()) {
                    BdcSlXmDTO bdcSlXmDTO = bdcAddGwcSjclCommonService.initBdcSlXmDTOForWithdy(initSlxxQO, dyxmid);
                    bdcSlXmDOList.add(bdcSlXmDTO.getBdcSlXm());
                    bdcSlXmLsgxDOList.addAll(bdcSlXmDTO.getBdcSlXmLsgxList());
                }
            }
        } else {
            BdcSlXmDO bdcSlXmDO =bdcAddGwcSjclCommonService.changeYwxxDtoIntoBdcSlXm(initSlxxQO.getBdcSlYwxxDTO(),initSlxxQO.getCzrid(),initSlxxQO.getJbxxid());
            BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), initSlxxQO.getBdcSlYwxxDTO().getXmid(), "", "", "");
            bdcSlXmDOList.add(bdcSlXmDO);
            bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);

            //生成外联历史关系
            List<BdcSlXmLsgxDO> wlXmlsgxList =bdcAddGwcSjclCommonService.initWlXmlsgx(initSlxxQO,bdcSlXmDO);
            if(CollectionUtils.isNotEmpty(wlXmlsgxList)){
                bdcSlXmLsgxDOList.addAll(wlXmlsgxList);
            }


            //判断存在房产交易信息
            if(initSlxxQO.getBdcSlYwxxDTO().getFcjyBaxxDTO() != null){
                bdcSlJyxxService.dealFcjyBaxxDTO(initSlxxQO.getBdcSlYwxxDTO(),bdcSlXmDO.getXmid(),bdcSlxxInitDTO,initSlxxQO.getGzldyid());
                //合并流程交易信息只带入一个项目,后面清空交易数据
                initSlxxQO.getBdcSlYwxxDTO().setFcjyBaxxDTO(null);
            }
        }



        bdcSlxxInitDTO.setBdcSlXmDOList(bdcSlXmDOList);
        bdcSlxxInitDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);

        return bdcSlxxInitDTO;
    }

    private List<BdcQl> listBdcQlxx(String bdcdyh,String qllx) {
       //查出现势权利
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        return bdcDjbxxFeignService.listBdcQlxx(bdcdyh, qllx, qsztList);

    }
}
