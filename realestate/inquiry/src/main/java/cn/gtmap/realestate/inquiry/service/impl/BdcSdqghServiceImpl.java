package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.fjts.request.HefeiDianFjtsRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.request.*;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.HefeiDianSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.SdqHyResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.yhcx.request.DianYhcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.request.HefeiShuiSqghRequestTransferPerson;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.HefeiShuiSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztUpdateHyDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.enums.BdcSdqRqfwbsmEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSqrFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.HefeiSdqFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ShuChengShuiFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.common.util.encrypt.MD5Util;
import cn.gtmap.realestate.inquiry.config.SdqFjtsFjmcConfig;
import cn.gtmap.realestate.inquiry.core.mapper.BdcQlrMapper;
import cn.gtmap.realestate.inquiry.core.mapper.BdcSdqghMapper;
import cn.gtmap.realestate.inquiry.service.BdcSdqQiHandle;
import cn.gtmap.realestate.inquiry.service.BdcSdqghService;
import cn.gtmap.realestate.inquiry.util.Constants;
import cn.gtmap.realestate.inquiry.util.FileUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2018/10/31
 * @description 水电气业务
 */
@Service
@Validated
public class BdcSdqghServiceImpl  extends InterfaceCodeBeanFactory  implements BdcSdqghService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSdqghServiceImpl.class.getName());

    /**
     * 权利人信息来源——匹配户主获取权利人
     */
    public static final Integer QLRXXSJLY_HZ = 1;

    /**
     * 权利人信息来源——读取业务所有的权利人
     */
    public static final Integer QLRXXSJLY_YWALL = 2;

    /**
     * 权利人信息来源——不匹配户主，取其中一个权利人
     */
    public static final Integer QLRXXSJLY_NHZ_ANY = 3;

    /**
     * 附件信息来源——不传
     */
    public static final Integer FJXXSJLY_N = 0;


    /**
     * 附件信息来源——电子证照
     */
    public static final Integer FJXXSJLY_DZZZ = 1;

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private HefeiSdqFeignService hefeiSdqFeignService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private BdcSlSjclFeignService bdcSlSjclService;
    @Autowired
    private BdcDjbxxFeignService bdcDjbxxFeignService;
    @Autowired
    private BdcSdqghMapper bdcSdqghMapper;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Value("${print.pdf:}")
    private String printPath;
    /**
     * 水电气过户，是否需要单位信息
     */
    @Value("${sdqgh.sfxydwxx:false}")
    private Boolean sfxydwxx;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    @Autowired
    private ECertificateFeignService eCertificateFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    private Set<BdcSdqQiHandle> bdcSdqQiHandleSet;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcQlrMapper bdcQlrMapper;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    SdqFjtsFjmcConfig sdqFjtsFjmcConfig;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    private BdcZdCache bdcZdCache;
    @Value("${sdq.verifyblzt: true}")
    private Boolean verifyBlzt;
    @Autowired
    SdqUtils sdqUtils;
    @Autowired
    private ShuChengShuiFeignService shuChengShuiFeignService;


    /**
     * 插入水电气过户信息
     *
     * @param bdcSdqghDO
     * @return
     */
    @Override
    public int insertSdqghDO(BdcSdqghDO bdcSdqghDO) {
        if (bdcSdqghDO != null) {
            return entityMapper.insertSelective(bdcSdqghDO);
        }
        return 0;
    }

    /**
     * 查询不动产水电气信息
     * @param bdcSdqywQO 水电气表查询参数对象
     * @return
     */
    @Override
    public List<BdcSdqghDO> querySdqyw(BdcSdqywQO bdcSdqywQO) {
        return bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
    }

    /**
     * 查询不动产水电气信息
     * @return
     */
    @Override
    public boolean shuigh(String gzlslid,String isOneWebSource) {
        String ycgzlslid = ycGzlslid(gzlslid);

        //是否办理水业务
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(ycgzlslid);
        bdcSdqywQO.setYwlx(BdcSdqEnum.S.key());
        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        List<BdcSdqghDO> listDo = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
        if(CollectionUtils.isEmpty(listDo)){
            LOGGER.info("未查询到水电气过户业务，{}",bdcSdqywQO.toString());
            return false;
        }

        List<BdcQlrDO> list = bdcSdqghMapper.getQlrxxByGzlslidIn(gzlslid);
        if(CollectionUtils.isEmpty(list)){
            LOGGER.info("未查询到权利人，gzlslid:{}",gzlslid);
            return false;
        }
        BdcSdqghDO bdcSdqghDO = listDo.get(0);
        HefeiShuiSqghResponseDTO dto = new HefeiShuiSqghResponseDTO();
        //如果是
        if(StringUtils.isNotBlank(bdcSdqghDO.getRqfwbsm())
                && BdcSdqRqfwbsmEnum.KXGS.key().equals(bdcSdqghDO.getRqfwbsm())){
            try {
                List<BdcXmDTO> listXm = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                shuChengShuiFeignService.sgh(gzlslid, "", listXm.get(0).getQjgldm());
                dto.setStatus(Constants.SHUI_BLCGZTM);
            }catch (Exception e){
                bdcSdqywQO.setBlzt(CommonConstantUtils.BLSB); //办理失败
                bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
                throw new AppException("推送水业务接口异常，请检查日志！");
            }
            return false;
        }else {
            //合肥供水
            HefeiShuiSqghRequestTransferPerson stp = new HefeiShuiSqghRequestTransferPerson();
            stp.setNewName(listDo.get(0).getXhzmc());
            stp.setOldName(listDo.get(0).getHzmc());
            LOGGER.info("开始推送办理水业务，参数gzlslid:{}", gzlslid);
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            for (int i = 0; i < list.size(); i++) {
                if (CommonConstantUtils.QLRLB_QLR.equals(list.get(i).getQlrlb())) {
                    // 如果已经记录了，就不需要再次循环了
                    if (StringUtils.isNotEmpty(stp.getCertificateNo())) {
                        continue;
                    }
                    // 不是空的话，说明受理页面记录了新户主的名称，用新户主的名称匹配权利人
                    // 匹配的信息set到接口的入参中
                    if (StringUtils.isNotEmpty(stp.getNewName())) {
                        if (stp.getNewName().equals(list.get(i).getQlrmc())) {
                            String zjzlmc = StringToolUtils.convertBeanPropertyValueOfZd(
                                    list.get(i).getZjzl(), zdMap.get("zjzl"));
                            stp.setNewTypeCertificates(zjzlmc);
                            stp.setMobile(list.get(i).getDh());
                            stp.setPhone(list.get(i).getDh());
                            stp.setCertificateNo(list.get(i).getZjh());
                            stp.setPropertyOwnership(list.get(i).getBdcqzh());
                        }
                        // 没有记录权利人的话，就去当前流程里面的权利人（多个的话，取第一个）
                    } else {
                        // 是空的话则取第一个权利人信息
                        stp.setNewName(list.get(i).getQlrmc());
                        String zjzlmc = StringToolUtils.convertBeanPropertyValueOfZd(
                                list.get(i).getZjzl(), zdMap.get("zjzl"));
                        stp.setNewTypeCertificates(zjzlmc);
                        stp.setMobile(list.get(i).getDh());
                        stp.setCertificateNo(list.get(i).getZjh());
                        stp.setPropertyOwnership(list.get(i).getBdcqzh());
                    }
                }
            }
            // 取附件
            List<BdcSlSjclDO> listSjcl = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
            if (CollectionUtils.isEmpty(listSjcl)) {
                LOGGER.info("未查询到附件材料，gzlslid:{}", gzlslid);
                return false;
            }

            for (int i = 0; i < listSjcl.size(); i++) {
                if (Constants.SHUIDIAN_XHZSFZZM.equals(listSjcl.get(i).getClmc())) {
//                mfxhzzfm+=listSjcl.get(i).getWjzxid()+",";
                    if (StringUtils.isBlank(listSjcl.get(i).getWjzxid())) {
                        LOGGER.info("收件材料缺失必要的附件，listSjcl:{}", listSjcl);
                        return false;
                    }
                    stp.setDoubleIdCardPath(listSjcl.get(i).getWjzxid());
                }
                if (Constants.SHUIDIAN_XHZSFZFM.equals(listSjcl.get(i).getClmc())) {
//                mfxhzzfm+=listSjcl.get(i).getWjzxid()+",";
                    if (StringUtils.isBlank(listSjcl.get(i).getWjzxid())) {
                        LOGGER.info("收件材料缺失必要的附件，listSjcl:{}", listSjcl);
                        return false;
                    }
                    stp.setDoubleIdCardPath1(listSjcl.get(i).getWjzxid());
                }
                if (Constants.SHUI_FZC.equals(listSjcl.get(i).getClmc())) {
                    stp.setPropertyOwnershipPath(listSjcl.get(i).getWjzxid());
                }
                if (Constants.SHUI_LHZSFZZM.equals(listSjcl.get(i).getClmc())) {
//                mflhzzfm+=listSjcl.get(i).getWjzxid()+",";
                    if (StringUtils.isBlank(listSjcl.get(i).getWjzxid())) {
                        LOGGER.info("收件材料缺失必要的附件，listSjcl:{}", listSjcl);
                        return false;
                    }
                    stp.setIdCardPath(listSjcl.get(i).getWjzxid());
                }
                if (Constants.SHUI_LHZSFZFM.equals(listSjcl.get(i).getClmc())) {
//                mflhzzfm+=listSjcl.get(i).getWjzxid()+",";
                    if (StringUtils.isBlank(listSjcl.get(i).getWjzxid())) {
                        LOGGER.info("收件材料缺失必要的附件，listSjcl:{}", listSjcl);
                        return false;
                    }
                    stp.setIdCardPath1(listSjcl.get(i).getWjzxid());
                }
                if (Constants.SHUI_SLD.equals(listSjcl.get(i).getClmc())) {
                    stp.setServiceHandlingForm(listSjcl.get(i).getWjzxid());
                }
                if (Constants.SHUI_HT.equals(listSjcl.get(i).getClmc())) {
                    stp.setContract(listSjcl.get(i).getWjzxid());
                }
            }

            stp.setReson("过户");
            stp.setCusno(listDo.get(0).getConsno());
            stp.setAcceptNumber(listDo.get(0).getSlbh());
            BdcXmQO qo = new BdcXmQO();
            qo.setGzlslid(gzlslid);
            List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(qo);
            stp.setAddress(listXm.get(0).getZl());
            // 记录日志
            if (CollectionUtils.isNotEmpty(listXm)) {
                stp.setSlbh(listXm.get(0).getSlbh());
                stp.setXmid(listXm.get(0).getXmid());
            }

            LOGGER.info("推送水业务过户入参:{}", stp);
            bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
            bdcSdqywQO.setYwlx(BdcSdqEnum.S.key());
            try {
                dto = hefeiSdqFeignService.newShuiSqgh(stp);
            } catch (Exception e) {
                bdcSdqywQO.setBlzt(CommonConstantUtils.BLSB); //办理失败
                bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
                throw new AppException("推送水业务接口异常，请检查日志！");
            }
            LOGGER.info("推送水业务过户返回值:{}",stp);
            if(Constants.SHUI_BLCGZTM.equals(dto.getStatus())){//更新状态
                bdcSdqywQO.setBlzt(CommonConstantUtils.BLCG); //办理成功
                bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
                return false;
            }else{
                bdcSdqywQO.setBlzt(CommonConstantUtils.BLSB); //办理失败
                bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
                return false;
            }
        }
    }

    /**
     * 查询不动产水电气信息
     *
     * @param gzlslid 水电气表查询参数对象
     * @return
     */
    @Override
    public boolean diangh(String gzlslid,String isOneWebSource) {
        LOGGER.info("电过户参数gzlslid:{}",gzlslid);
        String ycgzlslid = ycGzlslid(gzlslid);
        LOGGER.info("转换之后，电过户参数gzlslid:{}",ycgzlslid);

        //是否办理电业务
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(ycgzlslid);
        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqywQO.setYwlx(BdcSdqEnum.D.key());
        List<BdcSdqghDO> listDo = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
        LOGGER.info("电过户流程实体集合：{}",listDo);
        if(CollectionUtils.isEmpty(listDo)){
            LOGGER.info("未查询到水电气过户业务，{}",bdcSdqywQO.toString());
            return false;
        }
        // 处理登记簿
        String djbBase64str ="";
        try {
            djbBase64str =  getDjbJpgBygzlslid(gzlslid);
            djbBase64str = Constants.DIAN_BASE64HEAD + djbBase64str;
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        HefeiDianFjtsRequestDTO fjDtodjb = new HefeiDianFjtsRequestDTO();
        fjDtodjb.setAttachType(Constants.DIAN_FJLX2);
        fjDtodjb.setAppNo(gzlslid);
        fjDtodjb.setAttachFile(djbBase64str);

        LOGGER.info("开始推送办理电业务，参数gzlslid：{}",gzlslid);
        List listFjDto = new ArrayList();
        listFjDto.add(fjDtodjb);
        List<BdcQlrDO> list = bdcSdqghMapper.getQlrxxByGzlslidIn(gzlslid);
        if(CollectionUtils.isEmpty(list)){
            LOGGER.info("未查询到权利人信息，gzlslid:{}",gzlslid);
            return false;
        }

        HefeiDianSqghRequestData data = new HefeiDianSqghRequestData();
        HefeiDianSqghRequestDTO dto = new HefeiDianSqghRequestDTO();
        data.setConsNo(listDo.get(0).getConsno());
        String xhzmc = listDo.get(0).getXhzmc();
        for(int i=0;i<list.size();i++){
            if(CommonConstantUtils.QLRLB_QLR.equals(list.get(i).getQlrlb()) && xhzmc.equals(list.get(i).getQlrmc())){
                data.setNewConsName(list.get(i).getQlrmc());
                data.setAppNo(gzlslid);//户号作为appno
                data.setMobile(list.get(i).getDh());//权利人证件种类必须是sfz
                data.setIdCardNo(list.get(i).getZjh());
                break;
            }
        }

        dto.setSqghData(data);
        // 取附件
        List<BdcSlSjclDO> listSjcl = bdcSlSjclService.listBdcSlSjclByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(listSjcl)){
            LOGGER.info("未查询到附件材料，gzlslid:{}",gzlslid);
            return false;
        }
        for(int i=0;i<listSjcl.size();i++){
            HefeiDianFjtsRequestDTO fjDto = new HefeiDianFjtsRequestDTO();
            // 两张身份证不知道是新老 还是 正反，目前暂定为1,2
            if(Constants.SHUIDIAN_XHZSFZZM.equals(listSjcl.get(i).getClmc())){
                fjDto.setAttachType(Constants.DIAN_FJLX1);//默认为身份证
                fjDto.setAppNo(gzlslid);
                fjDto.setAttachFile(listSjcl.get(i).getWjzxid());
                listFjDto.add(fjDto);
            }

            if(Constants.SHUIDIAN_XHZSFZFM.equals(listSjcl.get(i).getClmc())){
                fjDto.setAttachType(Constants.DIAN_FJLX1);//默认为身份证
                fjDto.setAppNo(gzlslid);
                fjDto.setAttachFile(listSjcl.get(i).getWjzxid());
                listFjDto.add(fjDto);
            }

            if(Constants.DIAN_HT.equals(listSjcl.get(i).getClmc())){
                fjDto.setAttachType(Constants.DIAN_FJLX2);
                fjDto.setAppNo(gzlslid);
                fjDto.setAttachFile(listSjcl.get(i).getWjzxid());
                listFjDto.add(fjDto);
            }
            if(Constants.DIAN_XY.equals(listSjcl.get(i).getClmc())){
                fjDto.setAttachType(Constants.DIAN_FJLX2);
                fjDto.setAppNo(gzlslid);
                fjDto.setAttachFile(listSjcl.get(i).getWjzxid());
                listFjDto.add(fjDto);
            }
        }

        dto.setFjList(listFjDto);
        LOGGER.info("推送办理电业务，入参：{}",JSONObject.toJSONString(dto));
        HefeiDianSqghResponseDTO resdto = new HefeiDianSqghResponseDTO();
        BdcXmQO qo = new BdcXmQO();
        qo.setGzlslid(gzlslid);
        List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(qo) ;
        // 记录日志
        if(CollectionUtils.isNotEmpty(listXm)) {
            dto.setSlbh(listXm.get(0).getSlbh());
            dto.setXmid(listXm.get(0).getXmid());
        }
        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqywQO.setYwlx(BdcSdqEnum.D.key());
        try{
            resdto = hefeiSdqFeignService.dianSqgh(dto);
        }catch (Exception e){
            bdcSdqywQO.setBlzt(CommonConstantUtils.BLSB);
            bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
            throw new AppException("推送电业务接口异常，请检查日志！");
        }
        if(Constants.DIAN_BLCGZTM.equals(resdto.getResultCode())){//更新状态
            bdcSdqywQO.setBlzt(CommonConstantUtils.BLCG);
            bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
            return true;
        }else{
            bdcSdqywQO.setBlzt(CommonConstantUtils.BLSB);
            bdcSdqghMapper.updateSdqBlztIn(bdcSdqywQO);
            return false;
        }
    }

    /**
     * 查询不动产水电气信息
     * @param  gzlslid 水电气表查询参数对象
     * @return
     */
    @Override
    public boolean qigh(String gzlslid,String isOneWebSource) {
        String ycgzlslid = ycGzlslid(gzlslid);

        //是否办理气业务
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(ycgzlslid);
        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqywQO.setYwlx(BdcSdqEnum.Q.key());
        List<BdcSdqghDO> listDo = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);

        if(CollectionUtils.isEmpty(listDo)){
            LOGGER.info("未查询到水电气过户业务，{}",bdcSdqywQO.toString());
            return false;
        }
        BdcSdqQiHandle handle = getBean(bdcSdqQiHandleSet, listDo.get(0).getRqfwbsm());
        if(Objects.nonNull(handle)){
            return handle.qigh(gzlslid, isOneWebSource);
        }else {
            throw new IllegalArgumentException("未找到对应燃气服务");
        }
    }

    /**
     * 气 查询是否满足过户条件
     * @param
     * @param
     * @param
     * @return
     */
    @Override
    public Object getQiZt(BdcSdqywQO bdcSdqywQO) {
        String gzlslid = bdcSdqywQO.getGzlslid();
        String consNo = bdcSdqywQO.getConsno();
        String rqlx = bdcSdqywQO.getRqlx();
        LOGGER.info("bdcSdqywQO: {}",bdcSdqywQO);
        List<BdcXmDTO> listXm = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);

        if (CollectionUtils.isEmpty(listXm)) {
            throw new IllegalArgumentException("consNo, gzlslid, rqlx");
        }
        BdcXmDTO bdcXmDO = listXm.get(0);
        // 燃气服务标识码
        BdcSdqQiHandle handle = getBean(bdcSdqQiHandleSet, rqlx);
        if(Objects.nonNull(handle)){
            return handle.getQiZt(consNo, gzlslid, rqlx);
        }else {
            throw new IllegalArgumentException("未找到对应燃气服务");
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
        if (CommonConstantUtils.SDQYWLX_S.equals(bdcSdqywQO.getType())) {
            bdcSdqghDO.setYwlx(BdcSdqEnum.S.key());
            bdcSdqghDO.setRqfwbsm(bdcSdqywQO.getRqlx());
        } else if (CommonConstantUtils.SDQYWLX_D.equals(bdcSdqywQO.getType())) {
            bdcSdqghDO.setYwlx(BdcSdqEnum.D.key());
        } else {
            bdcSdqghDO.setYwlx(BdcSdqEnum.Q.key());
            //如果是气可能是有燃气类型字段
            if (StringUtils.isNotBlank(bdcSdqywQO.getRqlx()) && StringUtils.isNotEmpty(bdcSdqywQO.getGzlslid())) {
                // 燃气服务标识码
                List<BdcXmDTO> listXm = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSdqywQO.getGzlslid());
                if (CollectionUtils.isNotEmpty(listXm)) {
                    bdcSdqghDO.setRqfwbsm(bdcSdqywQO.getRqlx());
                }
            }
        }
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
        return insertSdqghDO(bdcSdqghDO);
    }


    public String getDjbJpgBygzlslidPdf(String gzlslid){
        List<BdcXmDO> list = new ArrayList<>();

        BdcXmQO qo = new BdcXmQO();
        qo.setGzlslid(gzlslid);
        list = bdcXmFeignService.listBdcXm(qo);

        if(CollectionUtils.isEmpty(list)){
            LOGGER.info("未查询到项目信息，gzlslid:{}",gzlslid);
            return "";
        }
        PdfReader reader = null;
        PdfStamper stamper = null;
        FileOutputStream out = null;
        String bdcdyh = list.get(0).getBdcdyh();
        String qllx = list.get(0).getQllx()+"";
        List qsztList = new ArrayList<>();
        qsztList.add(list.get(0).getQszt());
        List<BdcQl> listBdcQlxx = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, qllx, qsztList);
        if(CollectionUtils.isEmpty(listBdcQlxx)){
            LOGGER.info("未查询到权利信息，bdcdyh:{}，qllx：{}，qsztList：{}",bdcdyh,qllx,qsztList);
            return "";
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //获取字典项
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        String pdfTemplate = printPath + "djb.pdf";
        String pdfOpfile = printPath + "djb"+gzlslid+".pdf";
        FileUtils.copyFileUsingFileStreams(new File(pdfTemplate),new File(pdfOpfile));
        LOGGER.info("水电气生成登记簿pdf模板路径：{}",pdfTemplate);
        try {
            LOGGER.info("开始生成pdf模板");

            reader = new PdfReader(pdfOpfile);
            stamper = new PdfStamper(reader, byteArrayOutputStream);
            BaseFont baseFont = BaseFont.createFont("STSong-Light",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            AcroFields form = stamper.getAcroFields();
            form.addSubstitutionFont(baseFont);
            LOGGER.info("开始组织登记簿pdf数据");
            for (int i = 0; i < listBdcQlxx.size(); i++) {
                BdcQl bdcQl = listBdcQlxx.get(i);
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                BdcXmDO bdcXmDO = list.get(0);
                //开始进行字典项转换,格式转换
                String djlx = StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getDjlx(), zdMap.get("djxl"));
                String ghyt = StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getGhyt(), zdMap.get("ghyt"));
                String fwxz = StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getFwxz(), zdMap.get("fwxz"));
                String fwjg = StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getFwjg(), zdMap.get("fwjg"));
                form.setField("bdcdyh", bdcFdcqDO.getBdcdyh());
                form.setField("zl", bdcFdcqDO.getZl());
                form.setField("slbh" + i, bdcFdcqDO.getSlbh());
                form.setField("fwsyqr" + i, bdcXmDO.getQlr());
                form.setField("zjzl" + i, bdcXmDO.getQlrzjzl());
                form.setField("zjh" + i, bdcXmDO.getQlrzjh());
                form.setField("gyqk" + i, bdcFdcqDO.getGyqk());
                form.setField("qlrlx" + i, bdcXmDO.getQlrlx());
                form.setField("djlx" + i, djlx);
                form.setField("djyy" + i, bdcFdcqDO.getDjyy());
                form.setField("tdsyqr" + i, bdcFdcqDO.getTdsyqr());
                form.setField("dytdmj" + i, (null == bdcFdcqDO.getDytdmj()) ? "" : bdcFdcqDO.getDytdmj().toString());
                form.setField("fttdmj" + i, (null == bdcFdcqDO.getFttdmj()) ? "" : bdcFdcqDO.getFttdmj().toString());
                form.setField("tdsyqssj" + i, (null == bdcFdcqDO.getTdsyqssj()) ? "" : DateUtils.formateDateToString(bdcFdcqDO.getTdsyqssj(), DateUtils.DATE_FORMAT_2));
                form.setField("tdsyjssj" + i, (null == bdcFdcqDO.getTdsyjssj()) ? "" : DateUtils.formateDateToString(bdcFdcqDO.getTdsyjssj(), DateUtils.DATE_FORMAT_2));
                form.setField("jyjg" + i, (null == bdcFdcqDO.getJyjg()) ? "" : bdcFdcqDO.getJyjg().toString());
                form.setField("ghyt" + i, ghyt);
                form.setField("fwxz" + i, fwxz);
                form.setField("fwjg" + i, fwjg);
                form.setField("szc" + i, bdcFdcqDO.getSzmyc());
                form.setField("zcs" + i, (null == bdcFdcqDO.getZcs()) ? "" : bdcFdcqDO.getZcs().toString());
                form.setField("jzmj" + i, (null == bdcFdcqDO.getJzmj()) ? "" : bdcFdcqDO.getJzmj().toString());
                form.setField("zyjzmj" + i, (null == bdcFdcqDO.getZyjzmj()) ? "" : bdcFdcqDO.getZyjzmj().toString());
                form.setField("ftjzmj" + i, (null == bdcFdcqDO.getFtjzmj()) ? "" : bdcFdcqDO.getFtjzmj().toString());
                form.setField("jgsj" + i, bdcFdcqDO.getJgsj());
                form.setField("cqzh" + i, bdcXmDO.getBdcqzh());
                form.setField("djsj" + i, (null == bdcFdcqDO.getDjsj()) ? "" : DateUtils.formateDateToString(bdcFdcqDO.getDjsj(), DateUtils.DATE_FORMAT_2));
                form.setField("dbr" + i, bdcFdcqDO.getDbr());
                form.setField("fj" + i, StringUtils.deleteWhitespace(bdcFdcqDO.getFj()));
            }
            LOGGER.info("登记簿pdf数据组织结束，开始写pdf");

            stamper.setFormFlattening(true);

            // 这里要先关闭pdfReader文件流,下面才能写入数据
            stamper.close();
            reader.close();
            out = new FileOutputStream(pdfOpfile);
            byteArrayOutputStream.writeTo(out);
            out.flush();
        } catch (IOException e) {
            LOGGER.error("读取水电气模板失败,io异常",e);
        } catch (com.itextpdf.text.DocumentException e) {
            LOGGER.error("itextpdf插件异常",e);
        }catch (Exception e){
            LOGGER.error("读取水电气模板失败",e);
        }finally {
            try {
                LOGGER.info("开始关闭io流");
                byteArrayOutputStream.close();
                if(out != null){
                    out.close();
                }
            }catch (Exception e) {
                LOGGER.error("finally关闭水电气模板流失败Exception",e);
            }
        }
        LOGGER.info("----------------------生成登记簿pdf结束-------------");
        return pdfOpfile;
    }

    /**
     * 根据gzlslid 生成当前流程登记簿pdf
     * @param gzlslid
     * @return
     */
    public String getDjbJpgBygzlslid(String gzlslid){
        String pdfOpfile = getDjbJpgBygzlslidPdf(gzlslid);
        // 把pdf转换成jpg图片
        return FileUtils.pdf2Image(pdfOpfile,printPath,300);
    }

    /**
     * 根据工作流实例id获取一窗权利人
     * @param gzlslid
     * @return
     */
    public List<BdcQlrDO> getOneWebSourceQlrList(String gzlslid){
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        String jbxxid = bdcSlJbxxDO.getJbxxid();
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setJbxxid(jbxxid);
        List list = new ArrayList();
        List<BdcSlXmDO> listSlxm = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
        for(int i=0;i<listSlxm.size();i++){
            String xmid = listSlxm.get(i).getXmid();
            List<BdcSlSqrDO> listSqr = bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid,CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> listQlrDo = new ArrayList<>();
            for(int j=0;j<listSqr.size();j++ ){
                BdcQlrDO qlrDo = new BdcQlrDO();
                BdcSlSqrDO slQlrDo = listSqr.get(j);
                qlrDo.setQlrmc(slQlrDo.getSqrmc());
                qlrDo.setZjh(slQlrDo.getZjh());
                qlrDo.setZjzl(slQlrDo.getZjzl());
                listQlrDo.add(qlrDo);
                list.addAll(listQlrDo);
            }
        }
        return list;
    }

    /**
     * 查询水电气业务数据
     * @param gzlslid
     * @return
     */
    @Override
    public List<Map> getSdqSqbYwDyData(String gzlslid){
        if(StringUtils.isEmpty(gzlslid)){
            LOGGER.error("缺失gzlslid参数");
            return new ArrayList<>();
        }
        return bdcSdqghMapper.getSdqSqbYwDyData(gzlslid);
    }

    /**
     * 一窗的流程 要根据当前流程查询出原一窗的gzlslid
     * @param gzlslid
     * @return
     */
    public String ycGzlslid(String gzlslid){
        String ycgzlslid = gzlslid;
        BdcXmQO qo = new BdcXmQO();
        qo.setGzlslid(gzlslid);
        List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(qo) ;
        if(CollectionUtils.isNotEmpty(listXm)){
            Integer sply = listXm.get(0).getSply();
            if(sply.equals(CommonConstantUtils.SPLY_YCSL)){
                String spxtywh = listXm.get(0).getSpxtywh();

                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(spxtywh,"");
                // 一窗的流程 glzlsid要换成登记的gzlslid
                ycgzlslid = bdcSlJbxxDO.getGzlslid();
            }
        }
        return ycgzlslid;
    }

    /**
     * 更新水电气业务办理状态
     * @param bdcSdqBlztRequestDTO
     * @return BdcSdqBlztResponseDTO
     */
    @Override
    public BdcSdqBlztResponseDTO updateSdqBlzt(BdcSdqBlztRequestDTO bdcSdqBlztRequestDTO){
        BdcSdqBlztResponseDTO bdcSdqBlztResponseDTO = new BdcSdqBlztResponseDTO();
        if(StringUtils.isEmpty(bdcSdqBlztRequestDTO.getConsno()) && StringUtils.isEmpty(bdcSdqBlztRequestDTO.getGzlslid())){
            throw new AppException("缺失户号或工作流！");
        }
        if(bdcSdqBlztRequestDTO.getYwlx() == null ){
            throw new AppException("缺失业务类型！");
        }
        if(bdcSdqBlztRequestDTO.getBlzt() == null ){
            throw new AppException("缺失办理状态！");
        }
        LOGGER.info("水电气办理状态更新实体：{}", bdcSdqBlztRequestDTO);
        int count = bdcSdqghMapper.updateSdqBlzt(bdcSdqBlztRequestDTO);
        if(count > 0){
            bdcSdqBlztResponseDTO.setMessage("更新成功");
            bdcSdqBlztResponseDTO.setResult(true);
        }else{
            bdcSdqBlztResponseDTO.setMessage("更新失败");
            bdcSdqBlztResponseDTO.setResult(false);
        }
        return bdcSdqBlztResponseDTO;
    }

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 电过户核验，过户前查询是否可以过户
     */
    @Override
    public CommonResponse commonDianghhy(String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        SdqHyRequestDTO sdqHyRequestDTO;
        try {
            sdqHyRequestDTO = getSdqHyRequestDTO(processInsId,BdcSdqEnum.D);
        }catch (Exception e){
            LOGGER.error("工作流实例ID:{}组织电过户前核验请求参数异常:{}", processInsId,e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }
        LOGGER.info("工作流实例ID:{}电过户前核验请求信息:{}",processInsId,JSONObject.toJSONString(sdqHyRequestDTO));
        Object result = exchangeInterfaceFeignService.requestInterface("dian_cxyhxx", sdqHyRequestDTO);
        LOGGER.info("工作流实例ID:{}电过户前核验请求结果:{}",processInsId,JSONObject.toJSONString(result));
        if(result != null){
            SdqHyResponseDTO hyResponseDTO = JSON.parseObject(JSON.toJSONString(result), SdqHyResponseDTO.class);
            if(StringUtils.equals("0000", hyResponseDTO.getCode()) && Objects.nonNull(hyResponseDTO.getData())
                    && StringUtils.isNotBlank(hyResponseDTO.getData().getConsno())){
                return CommonResponse.ok();
            }else{
                return CommonResponse.fail("电力方提示:"+hyResponseDTO.getMsg());
            }
        }
        return CommonResponse.fail("操作失败");
    }

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 水过户核验，过户前查询是否可以过户
     */
    @Override
    public CommonResponse commonShuighhy(String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        SdqHyRequestDTO sdqHyRequestDTO;
        try {
            sdqHyRequestDTO = getSdqHyRequestDTO(processInsId,BdcSdqEnum.S);
        }catch (Exception e){
            LOGGER.error("工作流实例ID:{}组织水过户前核验请求参数异常:{}", processInsId,e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }
        LOGGER.info("工作流实例ID:{}水过户前核验请求信息:{}",processInsId,JSONObject.toJSONString(sdqHyRequestDTO));
        Object result = exchangeInterfaceFeignService.requestInterface("shui_cxyhxx", sdqHyRequestDTO);
        LOGGER.info("工作流实例ID:{}水过户前核验请求结果:{}",processInsId,JSONObject.toJSONString(result));
        if(result != null){
            SdqHyResponseDTO hyResponseDTO = JSON.parseObject(JSON.toJSONString(result), SdqHyResponseDTO.class);
            if(StringUtils.equals("0", hyResponseDTO.getCode()) && Objects.nonNull(hyResponseDTO.getData())
                    && StringUtils.isNotBlank(hyResponseDTO.getData().getConsno())){
                return CommonResponse.ok();
            }else{
                return CommonResponse.fail("水力方提示:"+hyResponseDTO.getMsg());
            }
        }
        return CommonResponse.fail("操作失败");
    }

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 气过户核验，过户前查询是否可以过户
     */
    @Override
    public CommonResponse commonQighhy(String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        SdqHyRequestDTO sdqHyRequestDTO;
        try {
            sdqHyRequestDTO = getSdqHyRequestDTO(processInsId,BdcSdqEnum.Q);
        }catch (Exception e){
            LOGGER.error("工作流实例ID:{}组织气过户前核验请求参数异常:{}", processInsId,e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }
        LOGGER.info("工作流实例ID:{}气过户前核验请求信息:{}",processInsId,JSONObject.toJSONString(sdqHyRequestDTO));
        Object result = exchangeInterfaceFeignService.requestInterface("qi_cxyhxx", sdqHyRequestDTO);
        LOGGER.info("工作流实例ID:{}气过户前核验请求结果:{}",processInsId,JSONObject.toJSONString(result));
        if(result != null){
            SdqHyResponseDTO hyResponseDTO = JSON.parseObject(JSON.toJSONString(result), SdqHyResponseDTO.class);
            // code为0000，并且data的limitFlag不为0
            if(StringUtils.equals("0000", hyResponseDTO.getCode()) && Objects.nonNull(hyResponseDTO.getData())
                    && !"0".equals(hyResponseDTO.getData().getLimitFlag())){
                return CommonResponse.ok();
            }else{
                return CommonResponse.fail("气力方提示:"+hyResponseDTO.getMsg());
            }
        }
        return CommonResponse.fail("操作失败");
    }

    /**
     * 组织过户前核验请求参数
     * @param gzlslid
     * @param bdcSdqEnum
     * @return
     */
    private SdqHyRequestDTO getSdqHyRequestDTO(String gzlslid,BdcSdqEnum bdcSdqEnum){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("工作流实例ID不能为空");
        }
        SdqHyRequestDTO sdqHyRequestDTO = new SdqHyRequestDTO();
        // 查询户号
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(gzlslid);
        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqywQO.setYwlx(bdcSdqEnum.key());
        List<BdcSdqghDO> bdcSdqghDOS = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
        if(CollectionUtils.isEmpty(bdcSdqghDOS)){
            throw new AppException("未获取到"+bdcSdqEnum.value()+"办理记录");
        }
        sdqHyRequestDTO.setConsno(bdcSdqghDOS.get(0).getConsno());
        // 查询单位信息，根据区县代码，读配置项
        BdcXmDO bdcXmDO =new BdcXmDO();
        bdcXmDO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList =entityMapper.selectByObj(bdcXmDO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
            bdcXmDOList = bdcXmDOList.stream().filter(xmDO -> ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, xmDO.getQllx())).collect(Collectors.toList());
        }
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("未获取到登记业务办理记录");
        }
        // 根据qxdm查询单位代码，没有配置区县代码对应单位代码的数据，不过户
        String dwdm = getDwdm(bdcSdqEnum, bdcXmDOList.get(0).getQxdm());
        sdqHyRequestDTO.setDwdm(dwdm);
        // 是否查询欠费
        sdqHyRequestDTO.setSfcxqf("1");
        return sdqHyRequestDTO;
    }

    /**
     * 配置项根据qxdm获取单位代码
     * @param bdcSdqEnum
     * @param qxdm 区县代码
     * @return 单位代码
     */
    private String getDwdm(BdcSdqEnum bdcSdqEnum, String qxdm) {
        String configName = "";
        if (BdcSdqEnum.S.key().equals(bdcSdqEnum.key())){
            configName = "gsdwdm";
        }
        if (BdcSdqEnum.D.key().equals(bdcSdqEnum.key())){
            configName = "gddwdm";
        }
        if (BdcSdqEnum.Q.key().equals(bdcSdqEnum.key())){
            configName = "gqdwdm";
        }
        String dwdm = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm(configName, "", qxdm);
        if (StringUtils.isBlank(dwdm)) {
            throw new AppException("未获取到对应的水电气单位代码！区县代码为" + qxdm);
        }
        return dwdm;
    }

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly    权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人 3：不匹配户主取其中一个
     * @param appendSign   权利人拼接符号
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 电过户请求
     */
    @Override
    public CommonResponse commonDiangh(String processInsId,Integer qlrxxsjly,String appendSign,Integer fjxxsjly){
        if(StringUtils.isBlank(processInsId)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        SdqGhRequestDTO sdqGhRequestDTO;
        try {
            sdqGhRequestDTO = getSdqGhRequestDTO(processInsId,qlrxxsjly,appendSign,BdcSdqEnum.D, fjxxsjly);
        }catch (Exception e){
            LOGGER.error("工作流实例ID:{}组织电推送请求参数异常:{}", processInsId,e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }
        LOGGER.info("工作流实例ID:{}电推送过户请求信息:{}",processInsId, sdqGhRequestDTO);
        Object result = exchangeInterfaceFeignService.requestInterface("dianPush", sdqGhRequestDTO);
        LOGGER.info("工作流实例ID:{}电推送过户请求结果:{}",processInsId,result);
        if(result != null){
            JSONObject jsonObject =JSONObject.parseObject(JSONObject.toJSONString(result));
            if(StringUtils.equals("200",jsonObject.getString("flag")) || StringUtils.equals("0000",jsonObject.getString("code"))){
                if(StringUtils.isNotBlank(sdqGhRequestDTO.getSdqid())) {
                    BdcSdqghDO updateZt = new BdcSdqghDO();
                    updateZt.setId(sdqGhRequestDTO.getSdqid());
                    updateZt.setBlzt(CommonConstantUtils.YSQ);
                    entityMapper.updateByPrimaryKeySelective(updateZt);
                }
                return CommonResponse.ok();
            }else{
                return CommonResponse.fail("电力方提示:"+jsonObject.getString("msg"));
            }
        }
        return CommonResponse.fail("操作失败");

    }

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly 权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign 权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 气过户请求
     */
    @Override
    public CommonResponse commonQigh(String processInsId,Integer qlrxxsjly,String appendSign, Integer fjxxsjly){
        if(StringUtils.isBlank(processInsId)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        SdqGhRequestDTO sdqGhRequestDTO;
        try {
            sdqGhRequestDTO = getSdqGhRequestDTO(processInsId,qlrxxsjly,appendSign,BdcSdqEnum.Q,fjxxsjly);
        }catch (Exception e){
            LOGGER.error("工作流实例ID:{}组织气推送请求参数异常:{}", processInsId,e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }
        LOGGER.info("工作流实例ID:{}气推送过户请求信息:{}",processInsId, sdqGhRequestDTO);
        Object result = exchangeInterfaceFeignService.requestInterface("qiPush", sdqGhRequestDTO);
        LOGGER.info("工作流实例ID:{}气推送过户请求结果:{}",processInsId,result);
        if(result != null){
            JSONObject jsonObject =JSONObject.parseObject(JSONObject.toJSONString(result));
            if(StringUtils.equals("1000",jsonObject.getString("code")) || StringUtils.equals("0000",jsonObject.getString("code"))){
                if(StringUtils.isNotBlank(sdqGhRequestDTO.getSdqid())) {
                    BdcSdqghDO updateZt = new BdcSdqghDO();
                    updateZt.setId(sdqGhRequestDTO.getSdqid());
                    updateZt.setBlzt(CommonConstantUtils.YSQ);
                    entityMapper.updateByPrimaryKeySelective(updateZt);
                }
                return CommonResponse.ok();
            }else{
                return CommonResponse.fail("燃气方提示:"+jsonObject.getString("msg"));
            }
        }
        return CommonResponse.fail("操作失败");

    }

    @Override
    public CommonResponse commonDianghFjcl(String processInsId){
        if(StringUtils.isBlank(processInsId)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        List<SdqGhFjclRequestDTO> sdqGhFjclRequestDTOList;
        try {
            sdqGhFjclRequestDTOList = getSdqFjclRequestDTO(processInsId);
        }catch (Exception e){
            LOGGER.error("工作流实例ID:{}组织电推送附件请求参数异常:{}", processInsId,e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }
        if(CollectionUtils.isEmpty(sdqGhFjclRequestDTOList)){
            LOGGER.info("未匹配到附件材料，gzlslid:{}",processInsId);
            return CommonResponse.ok();
        }
        for(SdqGhFjclRequestDTO sdqGhFjclRequestDTO : sdqGhFjclRequestDTOList){
            //base64过大,不输出
            LOGGER.info("工作流实例ID:{}电推送附件请求信息:{}",processInsId, sdqGhFjclRequestDTO.getImageType());
            Object result = exchangeInterfaceFeignService.requestInterface("dianAttach", sdqGhFjclRequestDTO);
            LOGGER.info("工作流实例ID:{}电推送附件请求结果:{}",processInsId,result);
        }
        return CommonResponse.ok();

    }

    private List<SdqGhFjclRequestDTO> getSdqFjclRequestDTO(String processInsId){
        List<SdqGhFjclRequestDTO> sdqGhFjclRequestDTOList =new ArrayList<>();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw  new AppException("未查询到项目信息,工作流实例id："+processInsId);
        }
        //查找附件图片
        List<StorageDto> storageDtoList = storageClient.queryMenus(CommonConstantUtils.WJZX_CLIENTID, processInsId, "", 0, null, "", null,null);
        Map<String, String> lxFjmcMap = sdqFjtsFjmcConfig.getFjmcdz();
        if(CollectionUtils.isNotEmpty(storageDtoList)) {
            for (StorageDto storageDto : storageDtoList) {
                if (StringUtils.isNotBlank(storageDto.getName())) {
                    String fjmc = storageDto.getName().split("\\.")[0];
                    if (lxFjmcMap.containsKey(fjmc)) {
                        List<StorageDto> listFiles = storageClient.listAllSubsetStorages(storageDto.getId(), null, null, null, null, null);
                        if (CollectionUtils.isNotEmpty(listFiles)) {
                            SdqGhFjclRequestDTO sdqGhFjclRequestDTO = new SdqGhFjclRequestDTO();
                            sdqGhFjclRequestDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
                            sdqGhFjclRequestDTO.setQxdm(bdcXmDOList.get(0).getQxdm());
                            sdqGhFjclRequestDTO.setStatus(String.valueOf(bdcXmDOList.get(0).getQszt()));
                            sdqGhFjclRequestDTO.setImageType(lxFjmcMap.get(fjmc));
                            BaseResultDto baseResultDto = storageClient.downloadBase64(listFiles.get(0).getId());
                            if (baseResultDto.getCode() == 0) {
                                if (StringUtils.isNotBlank(baseResultDto.getMsg())) {
                                    String[] baseArry = baseResultDto.getMsg().split(CommonConstantUtils.ZF_YW_DH);
                                    sdqGhFjclRequestDTO.setImageData(CommonConstantUtils.BASE64_QZ_IMAGE+baseArry[1]);
                                }
                            } else {
                                throw new AppException("获取附件图片失败！");
                            }
                            String[] fjmcArr = listFiles.get(0).getName().split("\\.");
                            sdqGhFjclRequestDTO.setFileType(fjmcArr.length >1?fjmcArr[fjmcArr.length - 1]:"jpg");
                            sdqGhFjclRequestDTOList.add(sdqGhFjclRequestDTO);
                        }
                    }
                }
            }
        }
        if(lxFjmcMap.containsKey("登记簿")) {
            // 处理登记簿
            String djbBase64str = "";
            try {
                djbBase64str = getDjbJpgBygzlslid(processInsId);
                djbBase64str = CommonConstantUtils.BASE64_QZ_IMAGE + djbBase64str;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            if (StringUtils.isNotBlank(djbBase64str)) {
                SdqGhFjclRequestDTO sdqGhFjclRequestDTO = new SdqGhFjclRequestDTO();
                sdqGhFjclRequestDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
                sdqGhFjclRequestDTO.setQxdm(bdcXmDOList.get(0).getQxdm());
                sdqGhFjclRequestDTO.setStatus(String.valueOf(bdcXmDOList.get(0).getQszt()));
                sdqGhFjclRequestDTO.setImageType(lxFjmcMap.get("登记簿"));
                sdqGhFjclRequestDTO.setImageData(djbBase64str);
                sdqGhFjclRequestDTO.setFileType("jpg");
                sdqGhFjclRequestDTOList.add(sdqGhFjclRequestDTO);
            }
        }
        return sdqGhFjclRequestDTOList;

    }

    @Override
    public void saveSdq(String gzlslid, List<BdcSdqghDO> bdcSdqghDOS, Boolean needHz){
        if (StringUtils.isBlank(gzlslid) || CollectionUtils.isEmpty(bdcSdqghDOS)){
            throw new AppException("未获取到工作流实例ID或水电气办理信息");
        }
        for (BdcSdqghDO bdcSdqghDO : bdcSdqghDOS) {
            // 淮安不需要户主姓名
            if (needHz == null || needHz) {
                if (StringUtils.isBlank(bdcSdqghDO.getHzmc()) || StringUtils.isBlank(bdcSdqghDO.getXhzmc())) {
                    LOGGER.info("当前水电气信息未获取户主或原户主必要字段,不进行保存处理");
                    continue;
                }
            }
            // 淮安户号不必填
            if (bdcSdqghDO.getYwlx() == null) {
                LOGGER.info("当前水电气信息未获取业务类型,不进行保存处理");
                continue;
            }
            if (StringUtils.isBlank(bdcSdqghDO.getId())) {
                BdcSdqywQO bdcSdqywQO =new BdcSdqywQO();
                bdcSdqywQO.setGzlslid(gzlslid);
                bdcSdqywQO.setYwlx(bdcSdqghDO.getYwlx());
                List<BdcSdqghDO> bdcSdqghDOList = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
                if(CollectionUtils.isNotEmpty(bdcSdqghDOList)){
                    bdcSdqghDO.setId(bdcSdqghDOList.get(0).getId());
                }
            }
            bdcSdqghDO.setSqsj(new Date());
            UserDto userDto =userManagerUtils.getCurrentUser();
            if(userDto != null) {
                bdcSdqghDO.setSqblrmc(userDto.getAlias());
            }
            // 以传入的为准，不传默认1
            if (null == bdcSdqghDO.getSfbl()) {
                bdcSdqghDO.setSfbl(CommonConstantUtils.SF_S_DM);
            }
            if(StringUtils.isNotBlank(bdcSdqghDO.getId())) {
                entityMapper.updateByPrimaryKeySelective(bdcSdqghDO);
            }else{
                bdcSdqghDO.setGzlslid(gzlslid);
                bdcSdqghDO.setSlbh(bdcXmFeignService.querySlbh(gzlslid));
                bdcSdqghDO.setId(UUIDGenerator.generate16());
                entityMapper.insertSelective(bdcSdqghDO);
            }
        }

    }

    /**
     * @param gzlslid 工作流实例ID
     * @param qlrxxsjly    权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人 3：(淮安)不匹配户主取其中一个
     * @param appendSign   权利人拼接符号
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @param bdcSdqEnum   业务类型：水,电,气
     * @return 水电气过户请求信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织水电气过户请求信息
     */
    private SdqGhRequestDTO getSdqGhRequestDTO(String gzlslid, Integer qlrxxsjly, String appendSign, BdcSdqEnum bdcSdqEnum,Integer fjxxsjly){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("工作流实例ID不能为空");
        }
        if(StringUtils.isBlank(appendSign)){
            appendSign =",";
        }
        // 默认为0，不传附件
        if (fjxxsjly == null) {
            fjxxsjly = 0;
        }
        SdqGhRequestDTO sdqGhRequestDTO =new SdqGhRequestDTO();
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(gzlslid);
        bdcSdqywQO.setSfbl(CommonConstantUtils.SF_S_DM);
        bdcSdqywQO.setYwlx(bdcSdqEnum.key());
        List<BdcSdqghDO> bdcSdqghDOS = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
        if(CollectionUtils.isEmpty(bdcSdqghDOS)){
            throw new AppException("未获取到"+bdcSdqEnum.value()+"办理记录");
        }
        sdqGhRequestDTO.setConsno(bdcSdqghDOS.get(0).getConsno());
        sdqGhRequestDTO.setSdqid(bdcSdqghDOS.get(0).getId());
        //项目信息
        BdcXmDO bdcXmDO =new BdcXmDO();
        bdcXmDO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList =entityMapper.selectByObj(bdcXmDO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
            bdcXmDOList = bdcXmDOList.stream().filter(xmDO -> ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, xmDO.getQllx())).collect(Collectors.toList());
        }
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("未获取到登记业务办理记录");
        }
        BeanUtils.copyProperties(bdcXmDOList.get(0), sdqGhRequestDTO);
        // 获取配置的单位代码，没有配置区县代码对应单位代码的数据，不过户
        if(sfxydwxx){
            // 单位代码
            String dwdm = getDwdm(bdcSdqEnum,bdcXmDOList.get(0).getQxdm());
            sdqGhRequestDTO.setDwdm(dwdm);
        }
        // 不动产权证号
        String ycqzh = bdcXmDOList.get(0).getYcqzh();
        if (StringUtils.isNotBlank(ycqzh) && ycqzh.contains(",")){
            ycqzh = ycqzh.replace(',',appendSign.toCharArray()[0]);
        }
        sdqGhRequestDTO.setBdcqzh(ycqzh);
        sdqGhRequestDTO.setMj(bdcXmDOList.get(0).getDzwyt() != null ? bdcXmDOList.get(0).getDzwmj().toString() : "");
        sdqGhRequestDTO.setYt(bdcXmDOList.get(0).getDzwyt() != null ? bdcXmDOList.get(0).getDzwyt().toString() : "");
        sdqGhRequestDTO.setStatus(bdcXmDOList.get(0).getQszt() != null ? bdcXmDOList.get(0).getQszt().toString() : "");
        //权利人信息
        // 匹配户主，校验户主信息
        if (QLRXXSJLY_HZ.equals(qlrxxsjly)){
            if (StringUtils.isBlank(bdcSdqghDOS.get(0).getHzmc()) || StringUtils.isBlank(bdcSdqghDOS.get(0).getXhzmc())) {
                throw new AppException("未获取到" + bdcSdqEnum.value() + "办理户主信息");
            }
        }
       /* if(CommonConstantUtils.YSQ.equals(bdcSdqghDOS.get(0).getBlzt())){
            throw new AppException("当前业务已申请，请勿重复推送");
        }*/
        if (verifyBlzt) {
            if (CommonConstantUtils.YSQ.equals(bdcSdqghDOS.get(0).getBlzt())) {
                throw new AppException("当前业务已申请，请勿重复推送");
            }
        }
        // 组织权利人义务人参数
        organizeQlrYwrxx(gzlslid, qlrxxsjly, appendSign, sdqGhRequestDTO, bdcSdqghDOS);
        // 组织附件信息
        // 添加电子证照
        if(FJXXSJLY_DZZZ.equals(fjxxsjly)){
            List<SdqGhFileDTO> ghFileDTOList = new ArrayList<>();
            for (BdcXmDO xmDO : bdcXmDOList) {
                SdqGhFileDTO ghFileDTO = this.getDzzzxx(xmDO);
                if (null != ghFileDTO && StringUtils.isNotBlank(ghFileDTO.getFileData())) {
                    ghFileDTOList.add(ghFileDTO);
                }
            }
            sdqGhRequestDTO.setData(ghFileDTOList);
        }
        return sdqGhRequestDTO;
    }

    /**
     * 组织权利人义务人参数
     * @param gzlslid 工作流实例ID
     * @param qlrxxsjly    权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人 3：不匹配户主取其中一个
     * @param appendSign   权利人拼接符号
     * @param sdqGhRequestDTO 水电气过户请求
     * @param bdcSdqghDOS 水电气数据
     */
    private void organizeQlrYwrxx(String gzlslid, Integer qlrxxsjly, String appendSign, SdqGhRequestDTO sdqGhRequestDTO, List<BdcSdqghDO> bdcSdqghDOS) {
        Map qlrparam = new HashMap();
        qlrparam.put("gzlslid", gzlslid);
        qlrparam.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
        // 匹配户主，增加户主查询条件
        if (QLRXXSJLY_HZ.equals(qlrxxsjly)) {
            qlrparam.put("qlrmc", bdcSdqghDOS.get(0).getXhzmc());
        }
        qlrparam.put("qllxs", Arrays.asList(CommonConstantUtils.QLLX_FDCQ));
        List<BdcQlrDO> bdcQlrList = bdcQlrMapper.queryQlrList(qlrparam);
        if (CollectionUtils.isEmpty(bdcQlrList)) {
            throw new AppException("未获取到当前业务的权利人信息");
        }
        List<Map> zjzlZd =new ArrayList<>();
        // 读取业务所有的权利人,证件种类dm转mc
        if(QLRXXSJLY_YWALL.equals(qlrxxsjly)){
            zjzlZd =bdcZdCache.getZdTableList("BDC_ZD_ZJZL", BdcZdZjzlDO.class);
        }
        // 取所有权利人，使用appendSign字符拼接
        if(QLRXXSJLY_YWALL.equals(qlrxxsjly)){
            sdqGhRequestDTO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(bdcQlrList, "getQlrmc", appendSign));
            sdqGhRequestDTO.setQlrzjzl(StringToolUtils.convertBeanPropertiesValueOfZd(bdcQlrList, "zjzl", zjzlZd, appendSign));
            sdqGhRequestDTO.setQlrzjh(StringToolUtils.resolveBeanToAppendStr(bdcQlrList, "getZjh", appendSign));
            sdqGhRequestDTO.setQlrdh(StringToolUtils.resolveBeanToAppendStr(bdcQlrList, "getDh", appendSign));
        }
        // 匹配户主获取权利人,权利人只有一条；不匹配户主取其中一个
        if(QLRXXSJLY_HZ.equals(qlrxxsjly) || QLRXXSJLY_NHZ_ANY.equals(qlrxxsjly)) {
            sdqGhRequestDTO.setQlrmc(bdcQlrList.get(0).getQlrmc());
            sdqGhRequestDTO.setQlrzjzl(bdcQlrList.get(0).getZjzl() != null ? bdcQlrList.get(0).getZjzl().toString() : "");
            sdqGhRequestDTO.setQlrzjh(bdcQlrList.get(0).getZjh());
            sdqGhRequestDTO.setQlrdh(bdcQlrList.get(0).getDh());
        }
        // 查义务人，与权利人逻辑相同
        qlrparam.put("qlrlb",CommonConstantUtils.QLRLB_YWR);
        if(QLRXXSJLY_HZ.equals(qlrxxsjly)) {
            qlrparam.put("qlrmc", bdcSdqghDOS.get(0).getHzmc());
        }
        List<BdcQlrDO> bdcYwrList =bdcQlrMapper.queryQlrList(qlrparam);
        if(CollectionUtils.isEmpty(bdcYwrList)){
            throw new AppException("未获取到当前业务的义务人信息");
        }
        if(QLRXXSJLY_YWALL.equals(qlrxxsjly)){
            sdqGhRequestDTO.setYwrmc(StringToolUtils.resolveBeanToAppendStr(bdcYwrList, "getQlrmc", appendSign));
            sdqGhRequestDTO.setYwrzjzl(StringToolUtils.convertBeanPropertiesValueOfZd(bdcYwrList, "zjzl", zjzlZd, appendSign));
            sdqGhRequestDTO.setYwrzjh(StringToolUtils.resolveBeanToAppendStr(bdcYwrList, "getZjh", appendSign));
            sdqGhRequestDTO.setYwrdh(StringToolUtils.resolveBeanToAppendStr(bdcYwrList, "getDh", appendSign));
        }
        if(QLRXXSJLY_HZ.equals(qlrxxsjly) || QLRXXSJLY_NHZ_ANY.equals(qlrxxsjly)) {
            sdqGhRequestDTO.setYwrmc(bdcYwrList.get(0).getQlrmc());
            sdqGhRequestDTO.setYwrzjzl(bdcYwrList.get(0).getZjzl() != null ? bdcYwrList.get(0).getZjzl().toString() : "");
            sdqGhRequestDTO.setYwrzjh(bdcYwrList.get(0).getZjh());
            sdqGhRequestDTO.setYwrdh(bdcYwrList.get(0).getDh());
        }
    }

    /**
     * 获取电子证照信息
     */
    private SdqGhFileDTO getDzzzxx(BdcXmDO bdcXmDO){
        List<BdcZsDO> bdcZsDOList = this.bdcZsFeignService.queryBdcZsByXmid(bdcXmDO.getXmid());
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            BdcZsDO bdcZsDO = bdcZsDOList.get(0);
            if (StringUtils.isNotBlank(bdcZsDO.getZzbs())) {
                SdqGhFileDTO ghFileDTO = new SdqGhFileDTO();
                ghFileDTO.setFileName(bdcZsDO.getBdcqzh() + CommonConstantUtils.WJHZ_PDF);
                if (StringUtils.isNotBlank(bdcZsDO.getStorageid())) {
                    BaseResultDto baseResultDto = storageClient.downloadBase64(bdcZsDO.getStorageid());
                    if (null != baseResultDto) {
                        ghFileDTO.setFileData(baseResultDto.getMsg());
                    }
                }
                if (StringUtils.isNotBlank(ghFileDTO.getFileData())) {
                    ghFileDTO.setFileData(this.eCertificateFeignService.getECertificateContent(bdcZsDO.getZzbs()));
                }
                return ghFileDTO;
            }
        }
        return null;
    }

    @Override
    public BdcSdqxxDTO querySdqxx(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("工作流实例ID不能为空");
        }
        BdcSdqxxDTO bdcSdqxxDTO =new BdcSdqxxDTO();
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(gzlslid);
        List<BdcSdqghDO> bdcSdqghDOS = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
        if(CollectionUtils.isNotEmpty(bdcSdqghDOS)){
            bdcSdqxxDTO.setBdcSdqghDOList(bdcSdqghDOS);
        }
        BdcXmDO bdcXmDO =new BdcXmDO();
        bdcXmDO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList =entityMapper.selectByObj(bdcXmDO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            bdcXmDOList= bdcXmDOList.stream().filter(xmDO -> ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, xmDO.getQllx())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                bdcSdqxxDTO.setZl(bdcXmDOList.get(0).getZl());
                Map param = new HashMap();
                param.put("gzlslid", gzlslid);
                param.put("xmid", bdcXmDOList.get(0).getXmid());
                List<BdcQlrDO> bdcQlrDOList = bdcQlrMapper.queryQlrList(param);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    bdcSdqxxDTO.setBdcQlrList(bdcQlrDOList.stream().filter(qlrDO -> StringUtils.equals(CommonConstantUtils.QLRLB_QLR, qlrDO.getQlrlb())).collect(Collectors.toList()));
                    bdcSdqxxDTO.setBdcYwrList(bdcQlrDOList.stream().filter(qlrDO -> StringUtils.equals(CommonConstantUtils.QLRLB_YWR, qlrDO.getQlrlb())).collect(Collectors.toList()));

                }
            }
        }
        return bdcSdqxxDTO;


    }

    @Override
    public CommonResponse commonDianghCancel(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(gzlslid);
        bdcSdqywQO.setYwlx(BdcSdqEnum.D.key());
        List<BdcSdqghDO> bdcSdqghDOS = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
        if(CollectionUtils.isEmpty(bdcSdqghDOS)){
            return CommonResponse.fail("未获取到电业务办理信息");
        }
        if(!CommonConstantUtils.YSQ.equals(bdcSdqghDOS.get(0).getBlzt())){
            return CommonResponse.fail("未推送过户");
        }
        Map paramMap =new HashMap();
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            return CommonResponse.fail("未获取到登记项目信息");
        }
        paramMap.put("slid",bdcXmDTOList.get(0).getSlbh());
        paramMap.put("imageType","3");
        paramMap.put("type","3");
        paramMap.put("orgno",bdcXmDTOList.get(0).getQxdm());
        LOGGER.info("工作流实例ID:{}电推送过户撤销请求信息:{}",gzlslid,paramMap);
        Object result = exchangeInterfaceFeignService.requestInterface("dianDelete", paramMap);
        LOGGER.info("工作流实例ID:{}电推送过户撤销请求结果:{}",gzlslid,result);
        if(result != null){
            JSONObject jsonObject =JSONObject.parseObject(JSONObject.toJSONString(result));
            if(StringUtils.equals("200",jsonObject.getString("flag"))){
                if(StringUtils.isNotBlank(bdcSdqghDOS.get(0).getId())) {
                    BdcSdqghDO updateZt = new BdcSdqghDO();
                    updateZt.setId(bdcSdqghDOS.get(0).getId());
                    updateZt.setBlzt(CommonConstantUtils.WSQ);
                    entityMapper.updateByPrimaryKeySelective(updateZt);
                }
                return CommonResponse.ok();
            }else{
                return CommonResponse.fail("电力方提示:"+jsonObject.getString("msg"));
            }
        }
        return CommonResponse.fail("操作失败");

    }

    @Override
    public CommonResponse commonQighCancel(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(gzlslid);
        bdcSdqywQO.setYwlx(BdcSdqEnum.Q.key());
        List<BdcSdqghDO> bdcSdqghDOS = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
        if(CollectionUtils.isEmpty(bdcSdqghDOS)){
            return CommonResponse.fail("未获取到气业务办理信息");
        }
        if(!CommonConstantUtils.YSQ.equals(bdcSdqghDOS.get(0).getBlzt())){
            return CommonResponse.fail("未推送过户");
        }
        Map paramMap =new HashMap();
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            return CommonResponse.fail("未获取到登记项目信息");
        }
        paramMap.put("slid",bdcXmDTOList.get(0).getSlbh());
        paramMap.put("imageType","3");
        paramMap.put("type","3");
        paramMap.put("orgno",bdcXmDTOList.get(0).getQxdm());
        LOGGER.info("工作流实例ID:{}气推送过户撤销请求信息:{}",gzlslid,paramMap);
        Object result = exchangeInterfaceFeignService.requestInterface("qiDelete", paramMap);
        LOGGER.info("工作流实例ID:{}气推送过户撤销请求结果:{}",gzlslid,result);
        if(result != null){
            JSONObject jsonObject =JSONObject.parseObject(JSONObject.toJSONString(result));
            if(StringUtils.equals("1000",jsonObject.getString("code"))){
                if(StringUtils.isNotBlank(bdcSdqghDOS.get(0).getId())) {
                    BdcSdqghDO updateZt = new BdcSdqghDO();
                    updateZt.setId(bdcSdqghDOS.get(0).getId());
                    updateZt.setBlzt(CommonConstantUtils.WSQ);
                    entityMapper.updateByPrimaryKeySelective(updateZt);
                }
                return CommonResponse.ok();
            }else{
                return CommonResponse.fail("燃气方提示:"+jsonObject.getString("msg"));
            }
        }
        return CommonResponse.fail("操作失败");

    }

    @Override
    public CommonResponse commonDianZt(String processInsId,String consNo){
        if(StringUtils.isBlank(processInsId) ||StringUtils.isBlank(consNo)){
            return CommonResponse.fail("工作流实例ID或户号不能为空");
        }
        DianYhcxRequestDTO dianYhcxRequestDTO = new DianYhcxRequestDTO();
        dianYhcxRequestDTO.setConsNo(consNo);
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        // 记录日志
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            dianYhcxRequestDTO.setSlbh(bdcXmDTOList.get(0).getSlbh());
            dianYhcxRequestDTO.setXmid(bdcXmDTOList.get(0).getXmid());
        }
        LOGGER.info("工作流实例ID:{}电查询是否欠费请求信息:{}",processInsId,dianYhcxRequestDTO);
        Object result = exchangeInterfaceFeignService.requestInterface("dianPreVarify", dianYhcxRequestDTO);
        LOGGER.info("工作流实例ID:{}电查询是否欠费请求结果:{}",processInsId,result);
        if(result != null){
            JSONObject jsonObject =JSONObject.parseObject(JSONObject.toJSONString(result));
            if(StringUtils.equals("200",jsonObject.getString("flag"))){
                return CommonResponse.ok(jsonObject);
            }else{
                return CommonResponse.fail("电力方提示:"+jsonObject.getString("msg"));
            }
        }
        return CommonResponse.fail("操作失败");
    }

    @Override
    public CommonResponse commonDianGhblzt(String processInsId,String consNo){
        if(StringUtils.isBlank(processInsId) ||StringUtils.isBlank(consNo)){
            return CommonResponse.fail("工作流实例ID或户号不能为空");
        }
        DianYhcxRequestDTO dianYhcxRequestDTO = new DianYhcxRequestDTO();
        dianYhcxRequestDTO.setConsNo(consNo);
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        // 记录日志
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            dianYhcxRequestDTO.setSlbh(bdcXmDTOList.get(0).getSlbh());
            dianYhcxRequestDTO.setXmid(bdcXmDTOList.get(0).getXmid());
        }
        LOGGER.info("工作流实例ID:{}电查询办理情况请求信息:{}",processInsId,dianYhcxRequestDTO);
        Object result = exchangeInterfaceFeignService.requestInterface("diangetResult", dianYhcxRequestDTO);
        LOGGER.info("工作流实例ID:{}电查询办理情况请求结果:{}",processInsId,result);
        if(result != null){
            JSONObject jsonObject =JSONObject.parseObject(JSONObject.toJSONString(result));
            if(StringUtils.equals("200",jsonObject.getString("flag"))){
                return CommonResponse.ok(jsonObject);
            }else{
                return CommonResponse.fail("电力方提示:"+jsonObject.getString("msg"));
            }
        }
        return CommonResponse.fail("操作失败");

    }

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly    权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign   权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 水过户请求
     */
    @Override
    public CommonResponse commonShuigh(String processInsId,Integer qlrxxsjly,String appendSign,Integer fjxxsjly){
        if(StringUtils.isBlank(processInsId)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        SdqGhRequestDTO sdqGhRequestDTO;
        try {
            sdqGhRequestDTO = getSdqGhRequestDTO(processInsId,qlrxxsjly,appendSign,BdcSdqEnum.S,fjxxsjly);
        }catch (Exception e){
            LOGGER.error("工作流实例ID:{}组织水推送请求参数异常:{}", processInsId,e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }
        Object requstObj = new Object();
        HashMap<String,Object> parmMap = new HashMap();
        parmMap.put("data",sdqGhRequestDTO);
        // 根据qxdm获取beanname,不传默认shuiPush
        String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("sdq.shuigh.beanname", "", sdqGhRequestDTO.getQxdm());
        if (StringUtils.isBlank(beanName)){
            beanName = "shuiPush";
            requstObj = parmMap;
        }else {
            requstObj = sdqGhRequestDTO;
        }
        LOGGER.info("工作流实例ID:{}水推送过户请求信息:{}",processInsId,requstObj);
        Object result = exchangeInterfaceFeignService.requestInterface(beanName, requstObj);
        LOGGER.info("工作流实例ID:{}水推送过户请求结果:{}",processInsId,result);
        if(result != null){
            JSONObject jsonObject =JSONObject.parseObject(JSONObject.toJSONString(result));
            if(StringUtils.equals("0",jsonObject.getString("code")) || StringUtils.equals("0000",jsonObject.getString("code"))){
                if(StringUtils.isNotBlank(sdqGhRequestDTO.getSdqid())) {
                    BdcSdqghDO updateZt = new BdcSdqghDO();
                    updateZt.setId(sdqGhRequestDTO.getSdqid());
                    updateZt.setBlzt(CommonConstantUtils.YSQ);
                    entityMapper.updateByPrimaryKeySelective(updateZt);
                }
                return CommonResponse.ok();
            }else{
                return CommonResponse.fail("水力方提示:"+jsonObject.getString("msg"));
            }
        }
        return CommonResponse.fail("操作失败");

    }


    @Override
    public CommonResponse commonShuighFjcl(String processInsId){
        if(StringUtils.isBlank(processInsId)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        List<SdqGhFjclRequestDTO> sdqGhFjclRequestDTOList;
        try {
            sdqGhFjclRequestDTOList = getSdqFjclRequestDTO(processInsId);
        }catch (Exception e){
            LOGGER.error("工作流实例ID:{}组织电推送附件请求参数异常:{}", processInsId,e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }
        if(CollectionUtils.isEmpty(sdqGhFjclRequestDTOList)){
            LOGGER.info("未匹配到附件材料，gzlslid:{}",processInsId);
            return CommonResponse.ok();
        }
        for(SdqGhFjclRequestDTO sdqGhFjclRequestDTO : sdqGhFjclRequestDTOList){
            //base64过大,不输出
            Map paramMap = new HashMap();
            paramMap.put("data",sdqGhFjclRequestDTO);
            LOGGER.info("工作流实例ID:{}水推送附件请求信息:{}",processInsId,paramMap);
            Object result = exchangeInterfaceFeignService.requestInterface("shuiAttach", paramMap);
            LOGGER.info("工作流实例ID:{}水推送附件请求结果:{}",processInsId,result);
        }
        return CommonResponse.ok();

    }

    @Override
    public CommonResponse commonShuighCancel(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            return CommonResponse.fail("工作流实例ID不能为空");
        }
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setGzlslid(gzlslid);
        bdcSdqywQO.setYwlx(BdcSdqEnum.S.key());
        List<BdcSdqghDO> bdcSdqghDOS = bdcSdqghMapper.listBdcSdqghIn(bdcSdqywQO);
        if(CollectionUtils.isEmpty(bdcSdqghDOS)){
            return CommonResponse.fail("未获取到水业务办理信息");
        }
        if(!CommonConstantUtils.YSQ.equals(bdcSdqghDOS.get(0).getBlzt())){
            return CommonResponse.fail("未推送过户");
        }
        Map paramMap =new HashMap();
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            return CommonResponse.fail("未获取到登记项目信息");
        }
        SdqGhCancelRequestDTO sdqGhCancelRequestDTO = new SdqGhCancelRequestDTO();
        sdqGhCancelRequestDTO.setSlbh(bdcXmDTOList.get(0).getSlbh());
        paramMap.put("data",sdqGhCancelRequestDTO);
        LOGGER.info("工作流实例ID:{}，水推送过户撤销请求信息:{}",gzlslid,paramMap);
        Object result = exchangeInterfaceFeignService.requestInterface("shuiDelete", paramMap);
        LOGGER.info("工作流实例ID:{}，水推送过户撤销请求结果:{}",gzlslid,result);
        if(result != null){
            JSONObject jsonObject =JSONObject.parseObject(JSONObject.toJSONString(result));
            if(StringUtils.equals("200",jsonObject.getString("code"))){
                if(StringUtils.isNotBlank(bdcSdqghDOS.get(0).getId())) {
                    BdcSdqghDO updateZt = new BdcSdqghDO();
                    updateZt.setId(bdcSdqghDOS.get(0).getId());
                    updateZt.setBlzt(CommonConstantUtils.WSQ);
                    entityMapper.updateByPrimaryKeySelective(updateZt);
                }
                return CommonResponse.ok();
            }else{
                return CommonResponse.fail("水力方提示:"+jsonObject.getString("msg"));
            }
        }
        return CommonResponse.fail("操作失败");

    }

    @Override
    public void updateSdqghhy(BdcSdqBlztUpdateHyDTO bdcSdqBlztUpdateHyDTO) {
        if (StringUtil.isEmpty(bdcSdqBlztUpdateHyDTO.getId())) {
            throw new AppException("更新核验信息时, 主键不能为空");
        }
        if (Objects.equals(CommonConstantUtils.HYJG_SUCESS, bdcSdqBlztUpdateHyDTO.getHyjg())) {
            BdcSdqghDO bdcSdqghDO = new BdcSdqghDO();
            bdcSdqghDO.setId(bdcSdqBlztUpdateHyDTO.getId());
            bdcSdqghDO.setHyjg(bdcSdqBlztUpdateHyDTO.getHyjg());
            if(StringUtils.isNotBlank(bdcSdqBlztUpdateHyDTO.getHzmc())){
                bdcSdqghDO.setHzmc(bdcSdqBlztUpdateHyDTO.getHzmc());
            }
            entityMapper.updateByPrimaryKeySelective(bdcSdqghDO);
            return;
        }

        if (Objects.equals(CommonConstantUtils.HYJG_FAIL, bdcSdqBlztUpdateHyDTO.getHyjg())) {
            BdcSdqghDO bdcSdqghDO = new BdcSdqghDO();
            bdcSdqghDO.setId(bdcSdqBlztUpdateHyDTO.getId());
            bdcSdqghDO.setHyjg(bdcSdqBlztUpdateHyDTO.getHyjg());
            bdcSdqghDO.setHyxq(bdcSdqBlztUpdateHyDTO.getHyxq());
            if(StringUtils.isNotBlank(bdcSdqBlztUpdateHyDTO.getHzmc())){
                bdcSdqghDO.setHzmc(bdcSdqBlztUpdateHyDTO.getHzmc());
            }
            entityMapper.updateByPrimaryKeySelective(bdcSdqghDO);
            return;
        }
    }
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户
     * @Date 2022/5/26 18:38
     **/
    @Override
    public CommonResponse shuchengShuigh(String processInsId) {
        shuChengShuiFeignService.sgh(processInsId, "","");
        return CommonResponse.ok();
    }
    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户检查
     * @Date 2022/5/26 18:38
     **/
    @Override
    public CommonResponse shuchengShuighjc(String processInsId) {
        return shuChengShuiFeignService.sghjc(processInsId, null,"");
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户登记簿数据
     * @Date 2022/5/26 18:38
     **/
    @Override
    public CommonResponse<String> shuchengShuiDjbData(String processInsId) {
        String pdfBinary = "";
        try {
            String path = getDjbJpgBygzlslidPdf(processInsId);
            LOGGER.info("临时文件路径为：{}", path);
            pdfBinary = FileUtils.getPDFBinary(path);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return CommonResponse.ok(pdfBinary);
    }
}
