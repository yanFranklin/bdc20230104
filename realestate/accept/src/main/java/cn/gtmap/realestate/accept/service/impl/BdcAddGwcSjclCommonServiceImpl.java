package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcAddGwcSjclCommonService;
import cn.gtmap.realestate.accept.service.BdcWlzsService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.config.accept.ZdyZlcshXztzPzConfig;
import cn.gtmap.realestate.common.core.domain.BdcFctdPpgxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYgDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.InitSlxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/9
 * @description 添加购物车数据处理公共服务
 */
@Service
public class BdcAddGwcSjclCommonServiceImpl implements BdcAddGwcSjclCommonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcAddGwcSjclCommonServiceImpl.class);

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcWlzsService bdcWlzsService;

    @Autowired
    private BdcPpFeignService bdcPpFeignService;

    @Autowired
    ZdyZlcshXztzPzConfig zdyZlcshXztzPzConfig;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 不发证登记小类
     */
    @Value("${bfzdjxl:}")
    private String bfzdjxl;

    @Override
    public void generateXmidDyzmhMap(String xmid, String yxmid, Map<String, String> xmidDyzmhMap) {
        if (StringUtils.isNotBlank(yxmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(yxmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())) {
                    xmidDyzmhMap.put(xmid, bdcXmDO.getBdcqzh());
                }
            }
        }

    }

    @Override
    public BdcSlXmDO changeYwxxDtoIntoBdcSlXm(BdcSlYwxxDTO bdcSlYwxxDTO,String czrid,String jbxxid){
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO(czrid);
        bdcSlXmDO.setBdcdyh(bdcSlYwxxDTO.getBdcdyh());
        bdcSlXmDO.setZl(bdcSlYwxxDTO.getZl());
        bdcSlXmDO.setQlr(bdcSlYwxxDTO.getQlr());
        bdcSlXmDO.setYbdcqz(bdcSlYwxxDTO.getYbdcqz());
        bdcSlXmDO.setQjid(bdcSlYwxxDTO.getQjid());
        bdcSlXmDO.setQjqlrgxid(bdcSlYwxxDTO.getQjqlrgxid());
        bdcSlXmDO.setJbxxid(jbxxid);
        bdcSlXmDO.setSfzlcsh(bdcSlYwxxDTO.getSfzlcsh());
        bdcSlXmDO.setSfzf(bdcSlYwxxDTO.getSfzf());
        bdcSlXmDO.setSfsczs(bdcSlYwxxDTO.getSfsczs());
        bdcSlXmDO.setZszl(bdcSlYwxxDTO.getZszl());
        bdcSlXmDO.setYt(bdcSlYwxxDTO.getYt());
        bdcSlXmDO.setQjgldm(bdcSlYwxxDTO.getQjgldm());
        bdcSlXmDO.setDzwmj(bdcSlYwxxDTO.getJzmj());
        if (StringUtils.isNotBlank(bdcSlYwxxDTO.getQlrsjly())) {
            bdcSlXmDO.setQlrsjly(Integer.parseInt(bdcSlYwxxDTO.getQlrsjly()));
        }
        //增量初始化读取配置的权利人数据来源
        if (Objects.equals(CommonConstantUtils.SF_S_DM, bdcSlYwxxDTO.getSfzlcsh())) {
            Map<String, String> qlrAndYwrSjlyMap = zdyZlcshXztzPzConfig.getQllxAndYwrsjly();
            if (MapUtils.isNotEmpty(qlrAndYwrSjlyMap)) {
                Map<String, String> sjlyMap = JSON.parseObject(qlrAndYwrSjlyMap.get(bdcSlYwxxDTO.getGzldyid()), Map.class);
                if (MapUtils.isNotEmpty(sjlyMap)) {
                    if (Objects.isNull(bdcSlYwxxDTO.getSelectDataQllx())) {
                        //选择数据qllx为空表示选择的是不动产单元号，义务人数据来源设置为从楼盘表获取
                        bdcSlXmDO.setYwrsjly(1);
                    }else {
                        String qlrsjly = sjlyMap.get(String.valueOf(bdcSlYwxxDTO.getSelectDataQllx()));
                        if (StringUtils.isNotBlank(qlrsjly)) {
                            bdcSlXmDO.setYwrsjly(Integer.parseInt(qlrsjly));
                        }
                    }
                }

            }
        }
        if (CollectionUtils.isNotEmpty(bdcSlYwxxDTO.getBdcSlYgDTOS())) {
            for (BdcSlYgDTO bdcSlYgDTO : bdcSlYwxxDTO.getBdcSlYgDTOS()) {
                if (checkPpYg(bdcSlYgDTO, bdcSlYwxxDTO.getQllx())) {
                    //权利人来源于预告权利人
                    bdcSlXmDO.setQlrsjly(Constants.QLRSJLY_YZX);
                    if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlYwxxDTO.getQllx())) {
                        bdcSlXmDO.setQlsjly(Constants.QLSJLY_YZX_YYD);
                    }

                }

            }
        }
        if (CommonConstantUtils.SF_F_DM.equals(bdcSlYwxxDTO.getSfsczs()) && StringUtils.isBlank(bdcSlYwxxDTO.getXmid()) && StringUtils.isNotBlank(bfzdjxl)) {
            //首次产权不发证,修改权利类型与登记小类
            bdcSlXmDO.setDjxl(bfzdjxl);
            bdcSlXmDO.setQllx(CommonConstantUtils.QLLX_JZWQFSYQYZGYBF_DM);
        } else {
            bdcSlXmDO.setDjxl(bdcSlYwxxDTO.getDjxl());
            bdcSlXmDO.setQllx(bdcSlYwxxDTO.getQllx());
        }
        if (bdcSlYwxxDTO.getBdclx() != null) {
            bdcSlXmDO.setBdclx(bdcSlYwxxDTO.getBdclx());
        } else {
            String bdclx = BdcdyhToolUtils.queryBdclxByBdcdyh(bdcSlYwxxDTO.getBdcdyh(), bdcSlYwxxDTO.getLx());
            if (StringUtils.isNotBlank(bdclx)) {
                bdcSlXmDO.setBdclx(Integer.parseInt(bdclx.split("/")[0]));
            }
        }
        return bdcSlXmDO;
    }

    @Override
    public BdcSlXmDTO initBdcSlXmDTOForWithdy(InitSlxxQO initSlxxQO,String dyxmid){
        BdcSlYwxxDTO bdcSlYwxxDTO =initSlxxQO.getBdcSlYwxxDTO();
        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO(initSlxxQO.getCzrid());
        bdcSlXmDO.setQllx(bdcSlYwxxDTO.getQllx());
        bdcSlXmDO.setBdcdyh(bdcSlYwxxDTO.getBdcdyh());
        bdcSlXmDO.setZl(bdcSlYwxxDTO.getZl());
        bdcSlXmDO.setJbxxid(initSlxxQO.getJbxxid());
        bdcSlXmDO.setDjxl(bdcSlYwxxDTO.getDjxl());
        bdcSlXmDO.setSfzlcsh(bdcSlYwxxDTO.getSfzlcsh());
        bdcSlXmDO.setSfzf(bdcSlYwxxDTO.getSfzf());
        bdcSlXmDO.setQjgldm(bdcSlYwxxDTO.getQjgldm());
        //获取抵押权利人
        BdcXmQO bdcXmQO =new BdcXmQO();
        bdcXmQO.setXmid(dyxmid);
        List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            bdcSlXmDO.setQlr(bdcXmDOList.get(0).getQlr());
        }
        if (bdcSlYwxxDTO.getBdclx() != null) {
            bdcSlXmDO.setBdclx(bdcSlYwxxDTO.getBdclx());
        } else {
            String bdclx = BdcdyhToolUtils.queryBdclxByBdcdyh(bdcSlYwxxDTO.getBdcdyh(), bdcSlYwxxDTO.getLx());
            if (StringUtils.isNotBlank(bdclx)) {
                bdcSlXmDO.setBdclx(Integer.parseInt(bdclx.split("/")[0]));
            }
        }
        if(CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlYwxxDTO.getQllx())) {
            //权利数据来源为原抵押
            bdcSlXmDO.setQlsjly(CommonConstantUtils.QLSJLY_ZH_YDY);
        }else if(CommonConstantUtils.QLLX_JZQ.equals(bdcSlYwxxDTO.getQllx())){
            bdcSlXmDO.setQlsjly(CommonConstantUtils.QLSJLY_YTQL);
        }
        //权利人数据来源为原权利权利人
        bdcSlXmDO.setQlrsjly(Constants.QLRSJLY_YTQL_QLR);
        //义务人数据来源(义务人数据来源为原权利人)
        bdcSlXmDO.setYwrsjly(Constants.QLRSJLY_YQLR);
        bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), dyxmid, "", "", "");
        //原权利作为外联证书
        bdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
        bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
        //外联土地证
        BdcSlXmLsgxDO tdzbdcSlXmLsgxDO = bdcWlzsService.wltdzNoInsert(dyxmid,"",bdcSlXmDO);
        if(tdzbdcSlXmLsgxDO != null){
            bdcSlXmLsgxDOList.add(tdzbdcSlXmLsgxDO);
        }


        if(StringUtils.isNotBlank(initSlxxQO.getYxmid())) {
            BdcXmQO xmQO = new BdcXmQO();
            xmQO.setXmid(initSlxxQO.getYxmid());
            List<BdcXmDO> yBdcXmList =bdcXmFeignService.listBdcXm(xmQO);
            if(CollectionUtils.isNotEmpty(yBdcXmList) &&ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, yBdcXmList.get(0).getQllx())){
                //原项目为限制权利，需要查找当前现势产权--针对在建抵押转现等流程
                BdcXmDO cqXmQO =new BdcXmDO();
                cqXmQO.setBdcdyh(bdcSlXmDO.getBdcdyh());
                if(CommonConstantUtils.DYBDCLX_ZJJZW.equals(bdcSlXmDO.getBdclx())){
                    bdcSlXmDO.setBdclx(CommonConstantUtils.DYBDCLX_FDYT);
                }
                cqXmQO.setBdclx(bdcSlXmDO.getBdclx());
                List<BdcXmDO> cqXmList = bdcXmFeignService.listXscqXm(Collections.singletonList(cqXmQO));
                if(CollectionUtils.isEmpty(cqXmList)){
                    throw new AppException("带抵押流程bdcdyh"+bdcSlXmDO.getBdcdyh()+"未查询到产权信息");
                }
                BdcSlXmLsgxDO xmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDTO.getBdcSlXm().getXmid(), cqXmList.get(0).getXmid(), "", "", "");
                xmLsgxDO.setZxyql(CommonConstantUtils.SF_F_DM);
                bdcSlXmLsgxDOList.add(xmLsgxDO);


            }else{
                //将新生成的产权作为主历史关系
                BdcSlXmLsgxDO xmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDTO.getBdcSlXm().getXmid(), initSlxxQO.getYxmid(), "", "", "");
                xmLsgxDO.setZxyql(CommonConstantUtils.SF_F_DM);
                bdcSlXmLsgxDOList.add(xmLsgxDO);
            }
        }

        if(CommonConstantUtils.QLLX_DYAQ_DM.equals(initSlxxQO.getBdcSlYwxxDTO().getQllx())) {
            //生成对应项目与原抵押证明号关系
            generateXmidDyzmhMap(bdcSlXmDTO.getBdcSlXm().getXmid(), dyxmid, initSlxxQO.getXmidDyzmhMap());
        }
        bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);

        return bdcSlXmDTO;

    }

    @Override
    public List<BdcSlXmLsgxDO>initWlXmlsgx(InitSlxxQO initSlxxQO,BdcSlXmDO bdcSlXmDO){
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =new ArrayList<>();
        //自动外联土地证
        BdcSlXmLsgxDO tdzbdcSlXmLsgxDO = wlTdz(initSlxxQO, bdcSlXmDO);
        if(tdzbdcSlXmLsgxDO != null){
            bdcSlXmLsgxDOList.add(tdzbdcSlXmLsgxDO);
        }
        if (CollectionUtils.isNotEmpty(initSlxxQO.getBdcSlYwxxDTO().getBdcWlSlXmLsgxDOList())) {
            for(BdcSlXmLsgxDO bdcWlSlXmLsgxDO:initSlxxQO.getBdcSlYwxxDTO().getBdcWlSlXmLsgxDOList()) {
                if(tdzbdcSlXmLsgxDO != null &&StringUtils.isNotBlank(tdzbdcSlXmLsgxDO.getYxmid()) &&StringUtils.equals(tdzbdcSlXmLsgxDO.getYxmid(),bdcWlSlXmLsgxDO.getYxmid())){
                    continue;
                }
                BdcSlXmLsgxDO wlbdcSlXmLsgxDO = bdcWlzsService.wlZsByWlxmid(bdcWlSlXmLsgxDO.getYxmid(), bdcSlXmDO);
                if (wlbdcSlXmLsgxDO != null) {
                    if(bdcWlSlXmLsgxDO.getZxyql() == null) {
                        wlbdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                    }else{
                        wlbdcSlXmLsgxDO.setZxyql(bdcWlSlXmLsgxDO.getZxyql());
                    }
                    bdcSlXmLsgxDOList.add(wlbdcSlXmLsgxDO);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(initSlxxQO.getBdcSlYwxxDTO().getBdcSlYgDTOS())) {
            for(BdcSlYgDTO bdcSlYgDTO:initSlxxQO.getBdcSlYwxxDTO().getBdcSlYgDTOS()) {
                if(tdzbdcSlXmLsgxDO != null &&StringUtils.isNotBlank(tdzbdcSlXmLsgxDO.getYxmid()) &&StringUtils.equals(tdzbdcSlXmLsgxDO.getYxmid(),bdcSlYgDTO.getXmid())){
                    continue;
                }
                if(checkPpYg(bdcSlYgDTO,initSlxxQO.getBdcSlYwxxDTO().getQllx())) {
                    BdcSlXmLsgxDO wlbdcSlXmLsgxDO = bdcWlzsService.wlZsByWlxmid(bdcSlYgDTO.getXmid(), bdcSlXmDO);
                    if (wlbdcSlXmLsgxDO != null) {
                        wlbdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                        bdcSlXmLsgxDOList.add(wlbdcSlXmLsgxDO);
                    }
                }
            }
        }
        return bdcSlXmLsgxDOList;
    }

    /**
     * @param initSlxxQO 受理业务信息
     * @return 受理项目历史关系
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自动外联土地证
     */
    private BdcSlXmLsgxDO wlTdz(InitSlxxQO initSlxxQO, BdcSlXmDO bdcSlXmDO) {
        BdcSlXmLsgxDO bdcSlXmLsgxDO = null;
        BdcSlYwxxDTO bdcSlYwxxDTO = initSlxxQO.getBdcSlYwxxDTO();
        LOGGER.info("自动外联土地证，选择项目数据:{}", bdcSlYwxxDTO);
        //判断所选数据是否为房产证,是则自动将土地证作为外联证书
        //说明 ： bdcslywxxdto.xmid 是选择的那条数据xmid，initSlxxQO 的yxmid 是当前项目的上一手xmid，相同带入外联，如果不等，应该是指合并流程，抵押的上一手是新的产权xmid，这是时候不需要带入外联土地证
        if (StringUtils.isNotBlank(bdcSlYwxxDTO.getXmid()) && StringUtils.isNotBlank(bdcSlYwxxDTO.getYbdcqz()) && !bdcSlYwxxDTO.getYbdcqz().contains("不动产")
                && StringUtils.equals(bdcSlYwxxDTO.getXmid(), initSlxxQO.getYxmid())) {
            List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(bdcSlYwxxDTO.getXmid());
            if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                LOGGER.info("自动外联土地证，获取到匹配记录:{}", bdcFctdPpgxDOList);
                bdcSlXmLsgxDO = bdcWlzsService.wlZsByWlxmid(bdcFctdPpgxDOList.get(0).getTdcqxmid(), bdcSlXmDO);
            }
        }

        return bdcSlXmLsgxDO;
    }

    /**
     * @param bdcSlYgDTO 关联预告信息
     * @param qllx 当前权利类型
     * @return 是否匹配预告
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证关联预告信息与当前项目是否匹配
     */
    @Override
    public Boolean checkPpYg(BdcSlYgDTO bdcSlYgDTO,Integer qllx){
        Boolean ppYg =false;
        //预告与产权关联,预抵押与抵押关联
        if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, bdcSlYgDTO.getYgdjzl()) &&!CommonConstantUtils.QLLX_DYAQ_DM.equals(qllx)) {
            ppYg =true;
        }else if(ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcSlYgDTO.getYgdjzl()) &&CommonConstantUtils.QLLX_DYAQ_DM.equals(qllx)){
            ppYg =true;
        }
        return ppYg;
    }
}
