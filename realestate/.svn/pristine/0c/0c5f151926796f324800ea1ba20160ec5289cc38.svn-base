package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.BdcSlWxzjService;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.wxzj.WxzjZftzRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/25.
 * @description
 */
@Service
public class BdcSlWxzjServiceImpl implements BdcSlWxzjService {

    private static final Logger logger = LoggerFactory.getLogger(BdcSlWxzjServiceImpl.class);

    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcSlSfxmService bdcSlSfxmService;
    @Autowired
    BdcSlSfxmPzService bdcSlSfxmPzService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlSfssDdxxService bdcSlSfssDdxxService;

    @Autowired
    BdcSlWxjjxxService bdcSlWxjjxxService;
    @Autowired
    BdcSlHsxxService bdcSlHsxxService;

    /**
     * 维修资金收费代码
     */
    private static final String WXZJ_SFDM = "89";

    /**
     * 维修资金收费名称
     */
    private static final String WXZJ_SFMC = "住房维修基金";

    /**
     * 生成维修资金项目信息
     */
    @Override
    public void generateWxzjXmxx(BdcSlWxjjxxDO bdcSlWxjjxxDO) {
        if(StringUtils.isAnyBlank(bdcSlWxjjxxDO.getGzlslid(), bdcSlWxjjxxDO.getXmid())){
            throw new MissingArgumentException("缺失参数项目ID或工作流实例ID");
        }
        // 获取已存在的维修资金收费项目信息
        BdcSlWxjjxxDO existWxzjXm = this.queryWxzjXmxx(bdcSlWxjjxxDO.getXmid(), bdcSlWxjjxxDO.getGzlslid());
        // 先删除原有维修基金信息，在添加查询的维修基金信息
        if(Objects.nonNull(existWxzjXm)){
            this.bdcSlWxjjxxService.deleteWxjjxxByWxjjxxid(existWxzjXm.getWxjjxxid());
        }
        bdcSlWxjjxxDO.setSfsj(new Date());
        bdcSlWxjjxxDO.setSfdm(WXZJ_SFDM);
        bdcSlWxjjxxDO.setSfmc(WXZJ_SFMC);
        bdcSlWxjjxxDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        this.bdcSlWxjjxxService.insertBdcSlWxjjxxDO(bdcSlWxjjxxDO);
    }

    /**
     * 获取维修资金信息
     */
    private BdcSlWxjjxxDO queryWxzjXmxx(String xmid, String gzlslid) {
        BdcSlWxjjxxDO queryParam = new BdcSlWxjjxxDO();
        queryParam.setXmid(xmid);
        queryParam.setGzlslid(gzlslid);
        queryParam.setSfdm(WXZJ_SFDM);
        List<BdcSlWxjjxxDO> bdcSlWxjjxxDOList = this.bdcSlWxjjxxService.queryBdcSlWxjjxx(queryParam);
        if(CollectionUtils.isNotEmpty(bdcSlWxjjxxDOList)){
            return bdcSlWxjjxxDOList.get(0);
        }
        return null;
    }

    /**
     * 通知维修资金支付成功
     */
    @Override
    public Object tzwxzj(String gzlslid, String xmid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失参数工作流实例ID");
        }

        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        bdcSlSfxxQO.setGzlslid(gzlslid);
        bdcSlSfxxQO.setXmid(xmid);
        bdcSlSfxxQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        bdcSlSfxxQO.setSfyj(CommonConstantUtils.SF_F_DM);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryBdcSlSfxx(bdcSlSfxxQO);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new NullPointerException("未获取到收费信息");
        }
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
        if (StringUtils.isBlank(bdcSlSfxxDO.getJfsewmurl())) {
            throw new AppException("未获取到缴费书编码信息");
        }
        // 未缴库不允许推送
        if(!CommonConstantUtils.SF_S_DM.equals(bdcSlSfxxDO.getYhjkrkzt())){
            throw new AppException(ErrorCode.CUSTOM, "未缴库成功不允许推送房产");
        }
        WxzjZftzRequestDTO wxzjZftzRequestDTO = this.getWxzjTzReqesutParam(bdcSlSfxxDO);
        // 调用一卡清获取缴费状态接口，更新订单信息，并比对订单金额与实际数据库中总金额
        if(!compareDdjeWithZje(gzlslid)){
            throw new AppException(ErrorCode.CUSTOM, "金额不一致");
        }
        Object response = this.exchangeInterfaceFeignService.requestInterface("ykq_payment_complete", wxzjZftzRequestDTO);
        if(Objects.nonNull(response)){
            Map result = (Map) response;
            BdcSlWxjjxxDO bdcSlWxjjxxDO = wxzjZftzRequestDTO.getBdcSlWxjjxxDO();
            if("00000".equals(result.get("code"))){
                bdcSlWxjjxxDO.setTzzt(Integer.parseInt(CommonConstantUtils.TSZT_SUCCESS_S));
            }else{
                bdcSlWxjjxxDO.setTzzt(Integer.parseInt(CommonConstantUtils.TSZT_FAIL_S));
            }
            this.bdcSlWxjjxxService.updateBdcSlWxjjxx(bdcSlWxjjxxDO);
        }
        return response;
    }

    /**
     * 获取维修资金通知接口请求参数
     */
    private WxzjZftzRequestDTO getWxzjTzReqesutParam(BdcSlSfxxDO bdcSlSfxxDO) {
        String xmid = bdcSlSfxxDO.getXmid();
        BdcSlWxjjxxDO bdcSlWxjjxxDO = this.queryWxzjXmxx(xmid, bdcSlSfxxDO.getGzlslid());
        if(Objects.isNull(bdcSlWxjjxxDO) || Objects.isNull(bdcSlWxjjxxDO.getHj()) || bdcSlWxjjxxDO.getHj()<= 0){
            throw new AppException("维修基金为空或0");
        }
        WxzjZftzRequestDTO wxzjZftzRequestDTO = new WxzjZftzRequestDTO();
        wxzjZftzRequestDTO.setBdcSlWxjjxxDO(bdcSlWxjjxxDO);
        wxzjZftzRequestDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
        wxzjZftzRequestDTO.setBdcQlrDO(this.queryBdcQlr(xmid));
        BdcXmDO bdcXmDO = this.queryBdcXm(xmid);
        wxzjZftzRequestDTO.setBdcXmDO(bdcXmDO);
        wxzjZftzRequestDTO.setBdcSlSfssDdxxDO(queryBdcSlSfssDdxx(bdcXmDO));
        return wxzjZftzRequestDTO;
    }

    /**
     * 获取收费收税订单信息
     */
    private BdcSlSfssDdxxDO queryBdcSlSfssDdxx(BdcXmDO bdcXmDO){
        if(Objects.nonNull(bdcXmDO)&&StringUtils.isNoneBlank(bdcXmDO.getGzldyid())){
            BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
            bdcSlSfssDdxxQO.setGzlslid(bdcXmDO.getGzlslid());
            bdcSlSfssDdxxQO.setJfzt(BdcSfztEnum.YJF.key());
            List<BdcSlSfssDdxxDO> list = bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
            if(CollectionUtils.isNotEmpty(list)){
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 获取权利人信息
     */
    private BdcQlrDO queryBdcQlr(String xmid) {
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            return bdcQlrDOList.get(0);
        }
        return null;
    }

    /**
     * 获取项目信息
     */
    private BdcXmDO queryBdcXm(String xmid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            return bdcXmDOList.get(0);
        }
        return null;
    }

    private boolean compareDdjeWithZje(String gzlslid){
        boolean compareResult = false;
        // 调用获取缴款情况接口，更新订单信息
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        //this.bdcSlSfssDdxxService.queryAndUpdateSfzt(bdcSlSfssDdxxQO);

        // 获取jfzt = 2 的订单信息金额，计算总金额
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        Double ddje = bdcSlSfssDdxxDOList.stream().filter(t->BdcSfztEnum.YJF.key().equals(t.getJfzt()) && null != t.getDdje())
                .mapToDouble(BdcSlSfssDdxxDO::getDdje).sum();
        logger.error("订单总金额：{}", ddje);
        // 获取税、费、维修基金总金额
        //获取权利人义务人税费总和
        Double sfssze = 0.00;
        //排除月结收费信息
        BdcSlSfxxDO sfxxQO =new BdcSlSfxxDO();
        sfxxQO.setGzlslid(gzlslid);
        sfxxQO.setSfyj(CommonConstantUtils.SF_F_DM);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.queryBdcSlSfxx(sfxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            sfssze += bdcSlSfxxDOList.stream().filter(sfxxDO -> sfxxDO.getHj() != null).mapToDouble(BdcSlSfxxDO::getHj).sum();
        }
        logger.error("收费金额：{}", sfssze);
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxxBySlbh(bdcXmDTOList.get(0).getSlbh());
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                sfssze += bdcSlHsxxDOList.stream().filter(hsxxDO -> hsxxDO.getSjyzhj() != null).mapToDouble(BdcSlHsxxDO::getSjyzhj).sum();
            }
            logger.error("收费+核税金额：{}", sfssze);
        }
        // 添加维修资金金额
        BdcSlWxjjxxDO queryParam = new BdcSlWxjjxxDO();
        queryParam.setGzlslid(gzlslid);
        List<BdcSlWxjjxxDO> bdcSlWxjjxxDOList = this.bdcSlWxjjxxService.queryBdcSlWxjjxx(queryParam);
        if(CollectionUtils.isNotEmpty(bdcSlWxjjxxDOList)){
            sfssze += bdcSlWxjjxxDOList.stream().filter(t -> t.getHj() != null).mapToDouble(BdcSlWxjjxxDO::getHj).sum();
            logger.error("收费+核税+维修资金金额：{}", sfssze);
        }


        sfssze = NumberUtil.formatDigit(sfssze, 2);
        ddje =  NumberUtil.formatDigit(ddje, 2);

        logger.error("订单金额：{}；税+费+维修基金总额：{}；", ddje, sfssze);
        logger.error("比对时订单：{}；税+费+维修基金总额：{}；", new BigDecimal(String.valueOf(ddje)).toString(), new BigDecimal(String.valueOf(sfssze)).toString());
        if(new BigDecimal(String.valueOf(ddje)).compareTo(new BigDecimal(String.valueOf(sfssze))) == 0){
            compareResult = true;
        }
        return compareResult;
    }

}
