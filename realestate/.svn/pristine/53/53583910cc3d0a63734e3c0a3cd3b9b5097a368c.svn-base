package cn.gtmap.realestate.accept.service.impl.initslxx;

import cn.gtmap.realestate.accept.service.BdcWlzsService;
import cn.gtmap.realestate.accept.service.InitBdcSlxxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxInitDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.InitSlxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/5/30
 * @description 预转现初始化受理信息
 */
@Service
public class InitBdcSlxxYzxServiceImpl implements InitBdcSlxxService {
    @Autowired
    BdcDjbxxFeignService bdcDjbxxFeignService;
    @Autowired
    BdcWlzsService bdcWlzsService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * 预抵押登记小类
     */
    @Value("#{'${ydydjxl:}'.split(',')}")
    private List<String> ydydjxl;


    @Override
    public BdcSlxxInitDTO initBdcSlxx(InitSlxxQO initSlxxQO) {
        BdcSlxxInitDTO bdcSlxxInitDTO = new BdcSlxxInitDTO();
        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        Integer[] ygdjzl = CommonConstantUtils.YG_YGDJZL_ARR;
        if (CommonConstantUtils.QLLX_DYAQ_DM.equals(initSlxxQO.getBdcSlYwxxDTO().getQllx()) ||(CommonConstantUtils.QLLX_YG_DM.equals(initSlxxQO.getBdcSlYwxxDTO().getQllx()) &&CollectionUtils.isNotEmpty(ydydjxl) && ydydjxl.contains(initSlxxQO.getBdcSlYwxxDTO().getDjxl()))) {
            ygdjzl = CommonConstantUtils.YG_YGDJZL_YDY_ARR;
        }
        List<BdcYgDO> bdcYgDOList = listBdcYg(initSlxxQO.getBdcSlYwxxDTO().getBdcdyh());
        //根据预告信息生成项目
        if (CollectionUtils.isNotEmpty(bdcYgDOList)) {
            for (BdcYgDO bdcYgDO : bdcYgDOList) {
                if (ArrayUtils.contains(ygdjzl, bdcYgDO.getYgdjzl())) {
                    BdcSlXmDTO bdcSlXmDTO = initBdcSlXmDO(initSlxxQO.getBdcSlYwxxDTO(), bdcYgDO, initSlxxQO.getJbxxid(), initSlxxQO.getCzrid());
                    if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl()) && StringUtils.isNotBlank(initSlxxQO.getYxmid())) {
                        //抵押流程将新生成的转移作为主历史关系
                        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDTO.getBdcSlXm().getXmid(), initSlxxQO.getYxmid(), "", "", "");
                        bdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_F_DM);
                        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);

                    }
                    bdcSlXmDOList.add(bdcSlXmDTO.getBdcSlXm());
                    bdcSlXmLsgxDOList.addAll(bdcSlXmDTO.getBdcSlXmLsgxList());
                }
            }
        }else{
            throw new AppException("不动产单元号"+initSlxxQO.getBdcSlYwxxDTO().getBdcdyh() +"未查询到现势的预告信息,请检查数据");
        }

        bdcSlxxInitDTO.setBdcSlXmDOList(bdcSlXmDOList);
        bdcSlxxInitDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);
        return bdcSlxxInitDTO;
    }

    /**
     * @param
     * @return 受理信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 预告组织生成受理信息
     */
    private BdcSlXmDTO initBdcSlXmDO(BdcSlYwxxDTO bdcSlYwxxDTO, BdcYgDO bdcYgDO, String jbxxid, String czrid) {
        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO(czrid);
        bdcSlXmDO.setQllx(bdcSlYwxxDTO.getQllx());
        bdcSlXmDO.setBdcdyh(bdcYgDO.getBdcdyh());
        bdcSlXmDO.setZl(bdcSlYwxxDTO.getZl());
        bdcSlXmDO.setJbxxid(jbxxid);
        bdcSlXmDO.setDjxl(bdcSlYwxxDTO.getDjxl());
        bdcSlXmDO.setDyfs(bdcYgDO.getDyfs());
        bdcSlXmDO.setZwlxqssj(bdcYgDO.getZwlxqssj());
        bdcSlXmDO.setZwlxjssj(bdcYgDO.getZwlxjssj());
        bdcSlXmDO.setQlrsjly(Constants.QLRSJLY_YZX);
        bdcSlXmDO.setQjgldm(bdcSlYwxxDTO.getQjgldm());
        if (!ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())) {
            bdcSlXmDO.setYwrsjly(Constants.QLRSJLY_YZX);
        }else{
            //抵押的义务人数据来源为原权利人
            bdcSlXmDO.setYwrsjly(Constants.QLRSJLY_YQLR);
        }
        bdcSlXmDO.setSfzlcsh(bdcSlYwxxDTO.getSfzlcsh());
        bdcSlXmDO.setSfzf(bdcSlYwxxDTO.getSfzf());
        final Integer bdclx = bdcSlYwxxDTO.getBdclx();
        if (Objects.nonNull(bdclx)) {
            bdcSlXmDO.setBdclx(bdclx);
        } else {
            String bdclxStr = BdcdyhToolUtils.queryBdclxByBdcdyh(bdcSlYwxxDTO.getBdcdyh(), bdcSlYwxxDTO.getLx());
            if (StringUtils.isNotBlank(bdclxStr)) {
                bdcSlXmDO.setBdclx(Integer.parseInt(bdclxStr.split("/")[0]));
            }
        }
        String yxmid = bdcYgDO.getXmid();
        if(StringUtils.isNotBlank(yxmid)){
            BdcSlXmLsgxDO wlbdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), yxmid, "", "", "");
            //预告作为外联证书
            wlbdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
            wlbdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
            //外联土地证
            BdcSlXmLsgxDO tdzbdcSlXmLsgxDO = bdcWlzsService.wltdzNoInsert(bdcYgDO.getXmid(),"",bdcSlXmDO);
            if(Objects.nonNull(tdzbdcSlXmLsgxDO)){
                bdcSlXmLsgxDOList.add(tdzbdcSlXmLsgxDO);
            }
            if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, bdcYgDO.getYgdjzl())) {
                //将产权作为转移主历史关系证书
                if(!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL,bdcSlYwxxDTO.getQllx())){
                    BdcQl bdcQl = listBdcCq(bdcYgDO.getBdcdyh(), bdcSlYwxxDTO.getQllx().toString());
                    if (Objects.nonNull(bdcQl)) {
                        //存在产权，义务人读取产权的原权利人
                        bdcSlXmDO.setYwrsjly(Constants.QLRSJLY_YQLR);
                        if(!StringUtils.equals(bdcQl.getXmid(),bdcSlYwxxDTO.getXmid())){
                            if(bdcQl instanceof BdcFdcqDO) {
                                bdcSlXmDO.setZl(((BdcFdcqDO) bdcQl).getZl());
                            }
                        }

                        BdcSlXmLsgxDO bdcSlXmLsgxDO = bdcWlzsService.wlZsByWlxmid(bdcQl.getXmid(), bdcSlXmDO);
                        if (Objects.nonNull(bdcSlXmLsgxDO)) {
                            bdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                            bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_F_DM);
                            bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                        }
                    }
                }else{
                    //不存在产权,预告作为主历史关系
                    wlbdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_F_DM);
                }
            } else {
                if(CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlYwxxDTO.getQllx())) {
                    //抵押权利数据来源预告抵押
                    bdcSlXmDO.setQlsjly(Constants.QLSJLY_YZX_YYD);
                }else if(CommonConstantUtils.QLLX_YG_DM.equals(bdcSlYwxxDTO.getQllx())){
                    //预抵押权利数据来源为预告抵押
                    bdcSlXmDO.setQlsjly(Constants.QLSJLY_YDY_YDY);

                }
            }
            bdcSlXmLsgxDOList.add(wlbdcSlXmLsgxDO);
            //权利人
            if(CommonConstantUtils.QLLX_YG_DM.equals(bdcSlYwxxDTO.getQllx())){
                BdcXmQO bdcXmQO =new BdcXmQO();
                bdcXmQO.setXmid(yxmid);
                List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                    bdcSlXmDO.setQlr(bdcXmDOList.get(0).getQlr());
                }

            }
        }
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);
        return bdcSlXmDTO;

    }

    private List<BdcYgDO> listBdcYg(String bdcdyh) {
        List<BdcYgDO> bdcYgDOList = new ArrayList<>();
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, CommonConstantUtils.QLLX_YG_DM.toString(), qsztList);
        if (CollectionUtils.isNotEmpty(bdcQlList)) {
            for (BdcQl bdcQl : bdcQlList) {
                BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                bdcYgDOList.add(bdcYgDO);

            }
        }
        return bdcYgDOList;

    }


    private BdcQl listBdcCq(String bdcdyh, String qllx) {
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, qllx, qsztList);
        if (CollectionUtils.isNotEmpty(bdcQlList)) {
            //现势产权默认只一条
            return bdcQlList.get(0);
        }
        return null;

    }
}
