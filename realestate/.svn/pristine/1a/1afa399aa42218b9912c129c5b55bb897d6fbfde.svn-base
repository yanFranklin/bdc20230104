package cn.gtmap.realestate.accept.service.impl;


import cn.gtmap.realestate.accept.core.service.BdcSlJtcyService;
import cn.gtmap.realestate.accept.core.service.BdcSlSqrService;
import cn.gtmap.realestate.accept.service.BdcZdydbService;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcZdydbymPzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcQlrInterfaceQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJtcyQO;
import cn.gtmap.realestate.common.core.qo.accept.GetJtcyxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcFpyzHyxxDbxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcFpyzHyxxMxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcFpyzQyxxDbxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcFpyzQyxxMxVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/9/23
 * @description 自定义对比服务
 */
@Service
public class BdcZdydbServiceImpl implements BdcZdydbService {

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcSlSqrService bdcSlSqrService;

    @Autowired
    BdcSlJtcyService bdcSlJtcyService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Override
    public BdcZdydbymPzDO queryBdcZdydbPzByDbdh(String dbdh){
        if(StringUtils.isNotBlank(dbdh)) {
            Example example = new Example(BdcZdydbymPzDO.class);
            example.createCriteria().andEqualTo("dbdh", dbdh);
            List<BdcZdydbymPzDO> bdcZdydbPzDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcZdydbPzDOList)) {
                return bdcZdydbPzDOList.get(0);
            }
        }
        return null;


    }

    @Override
    public void generateDbxx(String dbdh,String paramJson){


    }

    @Override
    public List<BdcFpyzHyxxDbxxVO> generateHyxx(String xmid){
        List<BdcFpyzHyxxDbxxVO> dbxxList =new ArrayList<>();
        if(StringUtils.isBlank(xmid)){
            throw new AppException("婚姻信息对比缺失参数xmid");
        }
        List<BdcSlSqrDO> bdcSlSqrDOList =bdcSlSqrService.listBdcSlSqrByXmid(xmid,"");
        if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)){
            BdcFpyzHyxxDbxxVO djxx =new BdcFpyzHyxxDbxxVO();
            BdcFpyzHyxxDbxxVO jkxx =new BdcFpyzHyxxDbxxVO();
            List<BdcFpyzHyxxMxVO> qlrdjBdcFbyzHyxxMxList =new ArrayList<>();
            List<BdcFpyzHyxxMxVO> ywrdjBdcFbyzHyxxMxList =new ArrayList<>();
            List<BdcFpyzHyxxMxVO> qlrjkBdcFbyzHyxxMxList =new ArrayList<>();
            List<BdcFpyzHyxxMxVO> ywrjkBdcFbyzHyxxMxList =new ArrayList<>();
            for(BdcSlSqrDO bdcSlSqrDO:bdcSlSqrDOList){
                //登记信息
                BdcFpyzHyxxMxVO bdcFpyzHyxxMxVO =new BdcFpyzHyxxMxVO();
                bdcFpyzHyxxMxVO.setXm(bdcSlSqrDO.getSqrmc());
                bdcFpyzHyxxMxVO.setZjhm(bdcSlSqrDO.getZjh());
                //查询配偶信息
                BdcSlJtcyQO bdcSlJtcyQO =new BdcSlJtcyQO();
                bdcSlJtcyQO.setYsqrgx("配偶");
                bdcSlJtcyQO.setSqrid(bdcSlSqrDO.getSqrid());
                List<BdcSlJtcyDO> poJtcyList =bdcSlJtcyService.listBdcSlJtcy(bdcSlJtcyQO);
                if(CollectionUtils.isNotEmpty(poJtcyList)){
                    bdcFpyzHyxxMxVO.setPoxm(poJtcyList.get(0).getJtcymc());
                    bdcFpyzHyxxMxVO.setPozjhm(poJtcyList.get(0).getZjh());
                }
                //接口信息
                BdcFpyzHyxxMxVO jkFbyzHyxxMxVO =new BdcFpyzHyxxMxVO();
                jkFbyzHyxxMxVO.setXm(bdcSlSqrDO.getSqrmc());
                jkFbyzHyxxMxVO.setZjhm(bdcSlSqrDO.getZjh());
                //查询配偶信息
                if(CommonConstantUtils.QLRLX_GR.equals(bdcSlSqrDO.getSqrlx())) {
                    GetJtcyxxQO getJtcyxxQO = new GetJtcyxxQO();
                    getJtcyxxQO.setXmid(bdcSlSqrDO.getXmid());
                    getJtcyxxQO.setQlrmc(bdcSlSqrDO.getSqrmc());
                    getJtcyxxQO.setQlrzjh(bdcSlSqrDO.getZjh());
                    //合肥合并婚姻接口
                    getJtcyxxQO.setBeanName("acceptHbHyxx");
                    BdcSlJtcyDO slJtcyDO = bdcSlJtcyService.requestHyxx(getJtcyxxQO, false);
                    if (slJtcyDO != null) {
                        jkFbyzHyxxMxVO.setPoxm(slJtcyDO.getJtcymc());
                        jkFbyzHyxxMxVO.setPozjhm(slJtcyDO.getZjh());
                    }
                }
                if(StringUtils.equals(CommonConstantUtils.QLRLB_QLR,bdcSlSqrDO.getSqrlb())){
                    qlrdjBdcFbyzHyxxMxList.add(bdcFpyzHyxxMxVO);
                    qlrjkBdcFbyzHyxxMxList.add(jkFbyzHyxxMxVO);
                }else{
                    ywrjkBdcFbyzHyxxMxList.add(jkFbyzHyxxMxVO);
                    ywrdjBdcFbyzHyxxMxList.add(bdcFpyzHyxxMxVO);
                }
            }
            djxx.setQlrBdcFbyzHyxxMxList(qlrdjBdcFbyzHyxxMxList);
            djxx.setYwrdcFbyzHyxxMxList(ywrdjBdcFbyzHyxxMxList);
            jkxx.setQlrBdcFbyzHyxxMxList(qlrjkBdcFbyzHyxxMxList);
            jkxx.setYwrdcFbyzHyxxMxList(ywrjkBdcFbyzHyxxMxList);
            dbxxList.add(djxx);
            dbxxList.add(jkxx);
        }
        return dbxxList;
    }

    public List<BdcFpyzQyxxDbxxVO> generateQyxx(String xmid){
        List<BdcFpyzQyxxDbxxVO> dbxxList =new ArrayList<>();
        if(StringUtils.isBlank(xmid)){
            throw new AppException("企业信息对比缺失参数xmid");
        }
        BdcQlrQO bdcQlrQO =new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        List<BdcQlrDO> bdcQlrDOList =bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            BdcFpyzQyxxDbxxVO djxx =new BdcFpyzQyxxDbxxVO();
            BdcFpyzQyxxDbxxVO jkxx =new BdcFpyzQyxxDbxxVO();
            List<BdcFpyzQyxxMxVO> qlrdjFpyzQyxxMxVOList =new ArrayList<>();
            List<BdcFpyzQyxxMxVO> ywrdjFpyzQyxxMxVOList =new ArrayList<>();
            List<BdcFpyzQyxxMxVO> qlrjkFpyzQyxxMxVOList =new ArrayList<>();
            List<BdcFpyzQyxxMxVO> ywrjkFpyzQyxxMxVOList =new ArrayList<>();
            for(BdcQlrDO bdcQlrDO:bdcQlrDOList){
                if(CommonConstantUtils.QLRLX_QY.equals(bdcQlrDO.getQlrlx())) {
                    //登记信息
                    BdcFpyzQyxxMxVO bdcFpyzQyxxMxVO = new BdcFpyzQyxxMxVO();
                    bdcFpyzQyxxMxVO.setQymc(bdcQlrDO.getQlrmc());
                    bdcFpyzQyxxMxVO.setZjh(bdcQlrDO.getZjh());

                    //接口信息
                    BdcFpyzQyxxMxVO jkFbyzHyxxMxVO = new BdcFpyzQyxxMxVO();
                    if(StringUtils.isNotBlank(bdcQlrDO.getQlrmc())) {
                        BdcQlrInterfaceQO bdcQlrInterfaceQO = new BdcQlrInterfaceQO();
                        bdcQlrInterfaceQO.setQlrmc(bdcQlrDO.getQlrmc());
                        bdcQlrInterfaceQO.setXmid(bdcQlrDO.getXmid());
                        Object response =exchangeInterfaceFeignService.requestInterface("hf_qyjbxxcx", bdcQlrInterfaceQO);
                        if (response != null) {
                            Map map = (Map) JSON.parse(JSON.toJSONString(response));
                            if(map != null &&map.get("qyxx") != null){
                                List<Map> qyxxList = (List<Map>) map.get("qyxx");
                                if(CollectionUtils.isNotEmpty(qyxxList) &&qyxxList.get(0).get("bdcQlrDO") != null){
                                    BdcQlrDO qyxx = JSON.parseObject(JSON.toJSONString(qyxxList.get(0).get("bdcQlrDO")),BdcQlrDO.class);
                                    if(qyxx != null){
                                        jkFbyzHyxxMxVO.setQymc(qyxx.getQlrmc());
                                        jkFbyzHyxxMxVO.setZjh(qyxx.getZjh());
                                    }
                                }
                            }
                        }
                    }

                    if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcQlrDO.getQlrlb())) {
                        qlrdjFpyzQyxxMxVOList.add(bdcFpyzQyxxMxVO);
                        qlrjkFpyzQyxxMxVOList.add(jkFbyzHyxxMxVO);
                    } else {
                        ywrdjFpyzQyxxMxVOList.add(bdcFpyzQyxxMxVO);
                        ywrjkFpyzQyxxMxVOList.add(jkFbyzHyxxMxVO);

                    }
                }
            }
            djxx.setQlrFpyzQyxxMxVOList(qlrdjFpyzQyxxMxVOList);
            djxx.setYwrFpyzQyxxMxVOList(ywrdjFpyzQyxxMxVOList);
            jkxx.setQlrFpyzQyxxMxVOList(qlrjkFpyzQyxxMxVOList);
            jkxx.setYwrFpyzQyxxMxVOList(ywrjkFpyzQyxxMxVOList);
            dbxxList.add(djxx);
            dbxxList.add(jkxx);
        }
        return dbxxList;
    }




}
