package cn.gtmap.realestate.inquiry.service.impl;/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @params [taskName]
 * @return java.lang.Object
 * @description
 */

import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.request.HefeiRanqiGhcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.response.HefeiWnRanqiGhcxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.HefeiWnRanqiSqghRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.RanqiSqghCommonRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiWnRanqiSqghResponseDTO;
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
import cn.gtmap.realestate.common.util.DateUtils;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @description:
 * @author: cy
 * @create: 2021-10-21
 **/
@Component
public class BdcSdqQiWNRQHandleImpl implements BdcSdqQiHandle {
    private static final Set<String> code = new HashSet<>();
    private static final String rqfwbsm = BdcSdqRqfwbsmEnum.WNRQ.key();

    /**
     * ????????????
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSdqQiWNRQHandleImpl.class.getName());

    static {
        code.add(BdcSdqRqfwbsmEnum.WNRQ.key());
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
    private StorageClientMatcher storageClient;
    @Value("${sdq.wnrq.ssrqgs:}")
    private String ssrqgs;
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
        LOGGER.info("{}?????????????????????",rqfwbsm);
        List<BdcXmDTO> listXm = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        BdcXmDTO bdcXmDO = listXm.get(0);
        HefeiRanqiGhcxRequestDTO dto = new HefeiRanqiGhcxRequestDTO();
        dto.setSlbh(bdcXmDO.getSlbh());
        dto.setXmid(bdcXmDO.getXmid());
        dto.setQjgldm(bdcXmDO.getQjgldm());
        dto.setRqfwbsm(rqfwbsm);
        dto.setUserhuhao(consNo);
        RanqiSqghCommonResponseDTO ranqiSqghCommonResponseDTO = hefeiSdqFeignService.ranqiGhcx(dto);
        return JSON.parseObject(ranqiSqghCommonResponseDTO.getResponseContent(), HefeiWnRanqiGhcxResponseDTO.class);
    }

    @Override
    public boolean qigh(String gzlslid, String isOneWebSource) {
        LOGGER.info("{}?????????????????????",rqfwbsm);
        String ycgzlslid = bdcSdqghService.ycGzlslid(gzlslid);

        //?????????????????????
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(ycgzlslid);
        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqywQO.setYwlx(BdcSdqEnum.Q.key());
        List<BdcSdqghDO> listDo = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
        if (CollectionUtils.isEmpty(listDo)) {
            LOGGER.info("????????????????????????????????????{}", bdcSdqywQO.toString());
            return false;
        }
        LOGGER.debug("????????????????????????????????????gzlslid???{}", gzlslid);
        BdcSdqghDO bdcSdqghDO = listDo.get(0);

        List<BdcQlrDO> qlrList = bdcSdqghMapper.getQlrxxByGzlslidIn(gzlslid);
        if (CollectionUtils.isEmpty(qlrList)) {
            LOGGER.info("??????????????????????????????gzlslid:{}", gzlslid);
            return false;
        }

        List<BdcSlSjclDO> listSjcl = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(listSjcl)) {
            LOGGER.info("???????????????????????????gzlslid:{}", gzlslid);
            return false;
        }

        //???????????????????????????,?????????
        BdcXmQO qo = new BdcXmQO();
        qo.setGzlslid(gzlslid);
        List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(qo);
        if (CollectionUtils.isEmpty(listXm)) {
            LOGGER.info("????????????????????????gzlslid:{}", gzlslid);
            return false;
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);

        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqywQO.setYwlx(BdcSdqEnum.Q.key());
        String result = "";
        try {
            //????????????????????????
            RanqiSqghCommonRequestDTO ranqiSqghCommonRequestDTO = new RanqiSqghCommonRequestDTO();
            ranqiSqghCommonRequestDTO.setSlbh(listXm.get(0).getSlbh());
            ranqiSqghCommonRequestDTO.setXmid(listXm.get(0).getXmid());
            ranqiSqghCommonRequestDTO.setQjgldm(bdcXmDTOList.get(0).getQjgldm());
            ranqiSqghCommonRequestDTO.setRqfwbsm(bdcSdqghDO.getRqfwbsm());
            //?????????????????????--???????????????????????????????????????????????????
            HefeiWnRanqiSqghRequestDTO hefeiWnRanqiSqghRequestDTO = new HefeiWnRanqiSqghRequestDTO();
            hefeiWnRanqiSqghRequestDTO.setFromUserhuhao(bdcSdqghDO.getConsno());
            hefeiWnRanqiSqghRequestDTO.setFromUame(bdcSdqghDO.getHzmc());
            hefeiWnRanqiSqghRequestDTO.setToUame(bdcSdqghDO.getXhzmc());

            // ???????????????
            for (int i = 0; i < qlrList.size(); i++) {
                BdcQlrDO bdcQlrDO = qlrList.get(i);
                //?????????????????????????????????????????????????????????
                if (CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb())) {
                    if (StringUtils.isNotEmpty(bdcSdqghDO.getXhzmc()) &&
                            bdcSdqghDO.getXhzmc().equals(bdcQlrDO.getQlrmc())) {
                        hefeiWnRanqiSqghRequestDTO.setToUserSfid(bdcQlrDO.getZjh());
                        hefeiWnRanqiSqghRequestDTO.setToUserMobile(bdcQlrDO.getDh());
                        break;
                    }
                }
            }
            // ???????????????
            for (int i = 0; i < qlrList.size(); i++) {
                BdcQlrDO bdcQlrDO = qlrList.get(i);
                //??????????????????????????????????????????????????????
                if (CommonConstantUtils.QLRLB_YWR.equals(bdcQlrDO.getQlrlb())) {
                    if (StringUtils.isNotEmpty(bdcSdqghDO.getHzmc()) &&
                            bdcSdqghDO.getHzmc().equals(bdcQlrDO.getQlrmc())) {
                        hefeiWnRanqiSqghRequestDTO.setFromUserSfid(bdcQlrDO.getZjh());
                        hefeiWnRanqiSqghRequestDTO.setFromUserMobile(bdcQlrDO.getDh());
                        break;
                    }
                }
            }
            // ?????????---?????????????????????,??????????????????
            for (BdcSlSjclDO bdcSlSjclDO : listSjcl) {
                if (Constants.QI_HTQDZP.equals(bdcSlSjclDO.getClmc())) {
                    //????????????url?????????
                    List<StorageDto> imgList
                            = storageClient.listAllSubsetStorages(bdcSlSjclDO.getWjzxid(), null, null, null, 0, null);
                    if (CollectionUtils.isNotEmpty(imgList)) {
                        StorageDto storageDto = imgList.get(0);
                        BaseResultDto baseResultDto = storageClient.downloadBase64(storageDto.getId());
                        hefeiWnRanqiSqghRequestDTO.setHetongImg(baseResultDto.getMsg());
                    }
                }else if (Constants.QI_XHZSFZZP.equals(bdcSlSjclDO.getClmc())) {
                    //????????????url?????????
                    List<StorageDto> imgList
                            = storageClient.listAllSubsetStorages(bdcSlSjclDO.getWjzxid(), null, null, null, 0, null);
                    if (CollectionUtils.isNotEmpty(imgList)) {
                        StorageDto storageDto = imgList.get(0);
                        BaseResultDto baseResultDto = storageClient.downloadBase64(storageDto.getId());
                        hefeiWnRanqiSqghRequestDTO.setToUserSfidimg(baseResultDto.getMsg());
                    }
                }
            }
            ranqiSqghCommonRequestDTO.setRequestContent(JSON.toJSONString(hefeiWnRanqiSqghRequestDTO));
            LOGGER.info("???????????????????????????????????????{}", JSON.toJSONString(ranqiSqghCommonRequestDTO));
            RanqiSqghCommonResponseDTO ranqiSqghCommonResponseDTO = hefeiSdqFeignService.ranqiSqgh(ranqiSqghCommonRequestDTO);
            if (StringUtils.isNotEmpty(ranqiSqghCommonResponseDTO.getResponseContent())) {
                HefeiWnRanqiSqghResponseDTO resDto = JSON.parseObject(ranqiSqghCommonResponseDTO.getResponseContent(), HefeiWnRanqiSqghResponseDTO.class);
                result = resDto.getResult();
            } else {
                result = Constants.QI_BLCGZFM;
            }

        } catch (Exception e) {
            bdcSdqywQO.setBlzt(CommonConstantUtils.BLSB);
            bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
            throw new AppException("????????????????????????????????????????????????");
        }
        //???????????? ????????????????????????????????????????????????,?????????????????????????????????????????????
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
            // ????????????
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
