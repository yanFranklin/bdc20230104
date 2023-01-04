package cn.gtmap.realestate.inquiry.service.impl;/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @params [taskName]
 * @return java.lang.Object
 * @description
 */

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.request.HefeiRanqiGhcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.response.HefeiRanqiGhcxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.HefeiRanqiSqghRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.RanqiSqghCommonRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiRanqiSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.RanqiSqghCommonResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.enums.BdcSdqRqfwbsmEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSqrFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.HefeiSdqFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.SdqUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.core.mapper.BdcSdqghMapper;
import cn.gtmap.realestate.inquiry.service.BdcSdqQiHandle;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 合肥燃气处理器(庐江区和合肥区)
 * @author: wyh
 * @create: 2021-10-21
 **/
@Component
public class BdcSdqQiHFRQHandleImpl implements BdcSdqQiHandle {
    private static final Set<String> code = new HashSet<>();
    private static final String rqfwbsm = BdcSdqRqfwbsmEnum.HFRQ.key();

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSdqQiHFRQHandleImpl.class.getName());

    static {
        code.add(BdcSdqRqfwbsmEnum.HFRQ.key());
    }

    @Autowired
    private HefeiSdqFeignService hefeiSdqFeignService;
    @Autowired
    private BdcSlSjclFeignService bdcSlSjclService;
    @Autowired
    private BdcSdqghMapper bdcSdqghMapper;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    @Lazy
    BdcSdqghServiceImpl bdcSdqghService;
    @Autowired
    SdqUtils sdqUtils;

    @Override
    public Set<String> getInterfaceCode() {
        return code;
    }

    @Override
    public Object getQiZt(String consNo, String gzlslid, String rqlx) {
        LOGGER.info("{}水电气过户业务", rqfwbsm);
        List<BdcXmDTO> listXm = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        BdcXmDTO bdcXmDO = listXm.get(0);
        HefeiRanqiGhcxRequestDTO dto = new HefeiRanqiGhcxRequestDTO();
        dto.setSlbh(bdcXmDO.getSlbh());
        dto.setXmid(bdcXmDO.getXmid());
        dto.setQjgldm(bdcXmDO.getQjgldm());
        dto.setRqfwbsm(rqfwbsm);
        dto.setUserhuhao(consNo);
        RanqiSqghCommonResponseDTO ranqiSqghCommonResponseDTO = hefeiSdqFeignService.ranqiGhcx(dto);
        return JSON.parseObject(ranqiSqghCommonResponseDTO.getResponseContent(), HefeiRanqiGhcxResponseDTO.class);
    }

    @Override
    public boolean qigh(String gzlslid, String isOneWebSource) {
        LOGGER.info("{}水电气过户业务", rqfwbsm);
        String ycgzlslid = bdcSdqghService.ycGzlslid(gzlslid);

        //是否办理气业务
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(ycgzlslid);
        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqywQO.setYwlx(BdcSdqEnum.Q.key());
        List<BdcSdqghDO> listDo = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);

        if (CollectionUtils.isEmpty(listDo)) {
            LOGGER.info("未查询到水电气过户业务，{}", bdcSdqywQO.toString());
            return false;
        }
        LOGGER.debug("开始推送办理气业务，参数gzlslid：{}", gzlslid);
        BdcSdqghDO bdcSdqghDO = listDo.get(0);
        String hzmc = bdcSdqghDO.getHzmc();

        List<BdcQlrDO> qlrList = bdcSdqghMapper.getQlrxxByGzlslidIn(gzlslid);
        if (CollectionUtils.isEmpty(qlrList)) {
            LOGGER.info("未查询到权利人信息，gzlslid:{}", gzlslid);
            return false;
        }

        List<BdcSlSjclDO> listSjcl = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(listSjcl)) {
            LOGGER.info("未查询到附件材料，gzlslid:{}", gzlslid);
            return false;
        }

        //有权利人一定有项目,不判空
        BdcXmQO qo = new BdcXmQO();
        qo.setGzlslid(gzlslid);
        List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(qo);
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);


        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqywQO.setYwlx(BdcSdqEnum.Q.key());
        String result = "";
        try {
            //通用燃气申请请求
            RanqiSqghCommonRequestDTO ranqiSqghCommonRequestDTO = new RanqiSqghCommonRequestDTO();
            ranqiSqghCommonRequestDTO.setSlbh(listXm.get(0).getSlbh());
            ranqiSqghCommonRequestDTO.setXmid(listXm.get(0).getXmid());
            ranqiSqghCommonRequestDTO.setQjgldm(bdcXmDTOList.get(0).getQjgldm());
            ranqiSqghCommonRequestDTO.setRqfwbsm(bdcSdqghDO.getRqfwbsm());
            //处理请求参数
            HefeiRanqiSqghRequestDTO dto = new HefeiRanqiSqghRequestDTO();
            dto.setToUame(bdcSdqghDO.getXhzmc());
            dto.setRqfwbsm(bdcSdqghDO.getRqfwbsm());
            // 处理权利人
            for (int i = 0; i < qlrList.size(); i++) {
                if (CommonConstantUtils.QLRLB_QLR.equals(qlrList.get(i).getQlrlb())) {
                    if (StringUtils.isNotEmpty(dto.getToUserSfid())) {
                        continue;
                    }
                    if (StringUtils.isNotEmpty(dto.getToUame()) && dto.getToUame().equals(qlrList.get(i).getQlrmc())) {
                        dto.setToUserSfid(qlrList.get(i).getZjh() + "");
                        dto.setToUserMobile(qlrList.get(i).getDh());
                    }
                }
            }
            // 处理义务人
            for (int i = 0; i < qlrList.size(); i++) {
                if (CommonConstantUtils.QLRLB_YWR.equals(qlrList.get(i).getQlrlb())) {
                    if (StringUtils.isNotEmpty(dto.getFromUname())) {
                        continue;
                    }
                    // 当老户主名称 和义务人名称一致
                    // 因为有可能存在多个义务人的情况（要转移的房子为共同共有）
                    if (qlrList.get(i).getQlrmc().equals(hzmc)) {
                        dto.setFromUname(qlrList.get(i).getQlrmc());
                        dto.setFromUserSfid(qlrList.get(i).getZjh());
                        dto.setFromUserMobile(qlrList.get(i).getDh());
                    }
                }
            }
            dto.setFromUserhuhao(bdcSdqghDO.getConsno());
            // 取附件
            for (int i = 0; i < listSjcl.size(); i++) {
                if (Constants.QI_XHZSFZZP.equals(listSjcl.get(i).getClmc())) {
                    dto.setToUserSfidimg(listSjcl.get(i).getWjzxid());
                    continue;
                }
                if (Constants.QI_HTQDZP.equals(listSjcl.get(i).getClmc())) {
                    dto.setHetongimg(listSjcl.get(i).getWjzxid());
                }
            }
            // 记录日志
            if (CollectionUtils.isNotEmpty(listXm)) {
                dto.setSlbh(listXm.get(0).getSlbh());
                dto.setXmid(listXm.get(0).getXmid());
                dto.setQjgldm(bdcXmDTOList.get(0).getQjgldm());
            }
            ranqiSqghCommonRequestDTO.setRequestContent(JSON.toJSONString(dto));
            LOGGER.info("推送办理气业务，入参：{}", JSON.toJSONString(ranqiSqghCommonRequestDTO));
            RanqiSqghCommonResponseDTO ranqiSqghCommonResponseDTO = hefeiSdqFeignService.ranqiSqgh(ranqiSqghCommonRequestDTO);
            if (StringUtils.isNotEmpty(ranqiSqghCommonResponseDTO.getResponseContent())) {
                HefeiRanqiSqghResponseDTO resDto = JSON.parseObject(ranqiSqghCommonResponseDTO.getResponseContent(), HefeiRanqiSqghResponseDTO.class);
                result = resDto.getResult();
            } else {
                result = Constants.QI_BLCGZFM;
            }

        } catch (Exception e) {
            bdcSdqywQO.setBlzt(CommonConstantUtils.BLSB);
            bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
            throw new AppException("推送气业务接口异常，请检查日志！");
        }
        //更新状态 皖能燃气需要等待对方调用确认结果,可能会出现不需要更新状态的情况
        if (Constants.QI_BLCGZTM.equals(result)) {
            bdcSdqywQO.setBlzt(CommonConstantUtils.BLCG);
            bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
            return true;
        } else if (Constants.QI_BLCGZFM.equals(result)) {
            bdcSdqywQO.setBlzt(CommonConstantUtils.BLSB);
            bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object saveData(BdcSdqywQO bdcSdqywQO) {
        BdcSdqghDO bdcSdqghDO = new BdcSdqghDO();
        bdcSdqghDO.setId(UUIDGenerator.generate16());
        bdcSdqghDO.setConsno(bdcSdqywQO.getConsno());
        bdcSdqghDO.setSqsj(new Date());
        bdcSdqghDO.setGzlslid(bdcSdqywQO.getGzlslid());
        bdcSdqghDO.setBlzt(CommonConstantUtils.YSQ);
        bdcSdqghDO.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqghDO.setYwlx(BdcSdqEnum.Q.key());
        bdcSdqghDO.setRqfwbsm(rqfwbsm);

        if (StringUtils.isNotEmpty(bdcSdqywQO.getGzlslid())) {
            // 一窗的件
            if ("true".equals(bdcSdqywQO.getYcsl())) {
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(bdcSdqywQO.getGzlslid());
                String slbh = bdcSlJbxxDO.getSlbh();
                bdcSdqghDO.setSlbh(slbh);
                bdcSdqghDO.setSqblrmc(bdcSlJbxxDO.getSlr());
            } else {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(bdcSdqywQO.getGzlslid());
                List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (!list.isEmpty()) {
                    bdcSdqghDO.setSlbh(list.get(0).getSlbh());
                    bdcSdqghDO.setSqblrmc(list.get(0).getSlr());
                }
            }
        }
        bdcSdqghDO.setHzmc(bdcSdqywQO.getHzmc());
        bdcSdqghDO.setXhzmc(bdcSdqywQO.getXhzmc());
        bdcSdqghDO.setHzzl(bdcSdqywQO.getHzzl());
        return bdcSdqghService.insertSdqghDO(bdcSdqghDO);
    }
}
