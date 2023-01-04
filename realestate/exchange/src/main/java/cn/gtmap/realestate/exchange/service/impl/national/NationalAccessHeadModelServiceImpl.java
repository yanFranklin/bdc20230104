package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.common.core.domain.BdcBdcdjbZdjbxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXnbdcdyhGxDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.domain.exchange.HeadModel;
import cn.gtmap.realestate.common.core.domain.exchange.KttZdjbxxDO;
import cn.gtmap.realestate.common.core.domain.exchange.KttZhjbxxDO;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttZdjbxxMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessHeadModelService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 获取报文头信息  还差一个RecType
 */
@Service
public class NationalAccessHeadModelServiceImpl implements NationalAccessHeadModelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessHeadModelServiceImpl.class);

    @Autowired
    private AccesssModelHandlerService accesssModelHandlerService;

    @Autowired
    KttZdjbxxMapper kttZdjbxxMapper;
    @Autowired
    BdcdjMapper bdcdjMapper;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    UserManagerUtils userManagerUtils;
    //拼接字符替换
    @Value("${accessLog.replacezf:,}")
    private String replaceZf;
    @Value("${accessLog.turn-on-replacezf:false}")
    public boolean turnOnReplacezf;
    //qxdm是否需要对照
    @Value("${qxdm.convert:false}")
    protected boolean qxdmConvert;
    /*
     * 报文头上一手单元号读取项目表ybdcdyh
     * */
    @Value("${access.headmodel.ybdcdyh:false}")
    private boolean headModelYbdcdyh;

    @Value("${ydya.convert:false}")
    private boolean ydyaConvert;

    /**
     * @param xmid
     * @return
     */
    @Override
    public HeadModel getAccessHeadModel(String xmid, boolean sfwlxm) {
        HeadModel headModel = null;
        if (StringUtils.isNotBlank(xmid)) {
            String areaCode = "";
            String asId = Constants.ASID;
            String rightType = "";
            String regType = "";
            Date createDate = null;
            String ywh = xmid;
            String regOrgId = "";
            String parceId = "";
            String estateNum = "";
            String preEstateNum = "";
            String preCerId = "";
            Integer certCount = 0;
            Integer proofCount = 0;

            // 处理参数
            headModel = new HeadModel();
            BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
            Integer bdclx = null;
            if (!ObjectUtils.isEmpty(bdcXmDO)) {
                areaCode = bdcXmDO.getQxdm();
                bdclx = bdcXmDO.getBdclx();
                String bdcdyh = bdcXmDO.getBdcdyh();
                if (StringUtil.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
                    areaCode = bdcdyh.substring(0, 6);
                }
                if (qxdmConvert) {
                    areaCode = commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", areaCode);
                }
                if (!ObjectUtils.isEmpty(bdcXmDO.getQllx())) {
                    rightType = Integer.toString(bdcXmDO.getQllx());
                    //配置了预抵押对照的，把预抵押的权利类型设置为抵押
                    if (ydyaConvert && Objects.equals(CommonConstantUtils.QLLX_YG_DM, bdcXmDO.getQllx())) {
                        //查预告数据判断是否是预抵押
                        BdcYgDO bdcYgDO = bdcXmMapper.queryBdcYdya(bdcXmDO.getXmid());
                        LOGGER.info("当前xmid{}查询到预抵押数据{}且配置了预抵押对照为抵押权利类型", ywh, JSON.toJSONString(bdcYgDO));
                        if (Objects.nonNull(bdcYgDO)) {
                            rightType = CommonConstantUtils.QLLX_DYAQ_DM.toString();
                        }
                    }
                }
                if (!ObjectUtils.isEmpty(bdcXmDO.getDjlx())) {
                    regType = Integer.toString(bdcXmDO.getDjlx());
                    //外联项目的登记类型取值为注销登记
                    if (sfwlxm) {
                        regType = CommonConstantUtils.DJLX_ZXDJ_DM.toString();
                    }
                }
                if (!ObjectUtils.isEmpty(bdcXmDO.getDjsj())) {
                    createDate = bdcXmDO.getDjsj();
                }
                if (!ObjectUtils.isEmpty(bdcXmDO.getDjjg())) {
                    regOrgId = bdcXmDO.getDjjg();
                }
                if (StringUtils.isNotBlank(bdcXmDO.getYcqzh())) {
                    if (turnOnReplacezf) {
                        preCerId = bdcXmDO.getYcqzh().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf);

                    } else {
                        preCerId = bdcXmDO.getYcqzh();
                    }
                }

                // 证书类型字典表(表名：BDC_ZD_ZSLX) 1：证书；2：证明；
                HashMap tempmap = new HashMap(2);
                tempmap.put("proid", xmid);
                tempmap.put("zstype", 1);
                certCount = bdcdjMapper.getZsNum(tempmap);
                tempmap.clear();
                tempmap.put("proid", xmid);
                tempmap.put("zstype", 2);
                proofCount = bdcdjMapper.getZsNum(tempmap);
            }
            if (Objects.nonNull(bdclx) && ArrayUtils.contains(CommonConstantUtils.BDCLX_HYSYQ, bdclx)) {
                LOGGER.info("当前项目id{}不动产类型{}属于海域，开始查询bdc_bdcdjb_zhjbxx", xmid, bdclx);
                List<KttZhjbxxDO> kttZhjbxxDOList = bdcXmMapper.listZhjbxx(xmid);
                if (CollectionUtils.isNotEmpty(kttZhjbxxDOList)) {
                    LOGGER.info("当前xmid{}查询到宗海基本信息{}", xmid, JSON.toJSONString(kttZhjbxxDOList));
                    parceId = kttZhjbxxDOList.get(0).getZhdm();
                    estateNum = kttZhjbxxDOList.get(0).getBdcdyh();
                }
            } else {
                Map<String, Object> param = new HashMap(1);
                param.put("ywh", xmid);
                List<KttZdjbxxDO> kttZdjbxxList = kttZdjbxxMapper.queryKttZdjbxxList(param);
                if (CollectionUtils.isNotEmpty(kttZdjbxxList)) {
                    parceId = kttZdjbxxList.get(0).getDjh();
                    LOGGER.info("宗地基本信息查到的bdcdyh为：{}", kttZdjbxxList.toString());
                    estateNum = kttZdjbxxList.get(0).getBdcdyh();
                    LOGGER.info("2宗地基本信息查到的bdcdyh为：{}", estateNum);
                }
            }
            //如果配置优先读取配置，如果为空继续走之前的逻辑
            List<HashMap<String, String>> preInfoList = bdcdjMapper.queryPreInfo(xmid);
            if (CollectionUtils.isNotEmpty(preInfoList)) {
                for (HashMap preInfo : preInfoList) {
                    String ybdcdyh = MapUtils.getString(preInfo, "YBDCDYH", "");
                    String bdcdyh = MapUtils.getString(preInfo, "BDCDYH", "");
                    //上一手单元号的取值
                    if (headModelYbdcdyh) {
                        bdcdyh = StringUtils.isNotBlank(ybdcdyh) ? ybdcdyh : bdcdyh;
                    }
                    if (StringUtils.isNotBlank(bdcdyh)
                            && !StringUtils.contains(preEstateNum, bdcdyh)) {
                        if (StringUtils.isNotBlank(preEstateNum)) {
                            preEstateNum += replaceZf + bdcdyh;
                        } else {
                            preEstateNum = bdcdyh;
                        }
                    }
                }
            }
            if (sfwlxm && StringUtils.equals(CommonConstantUtils.BDCLX_TD, String.valueOf(bdcXmDO.getBdclx()))) {
                //外联项目的且是土地证的，查询bdc_xnbdcdyh_gx 表，查询匹配之前的单元号作为preEstateNum
                if (StringUtils.isNotBlank(bdcXmDO.getBdcdyh())) {
                    Map<String, String> paramMap = new HashMap<>(3);
                    paramMap.put("bdcdyh", bdcXmDO.getBdcdyh());
                    paramMap.put("xnbdcdyhxmid", bdcXmDO.getXmid());
                    List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList = bdcdjMapper.queryXndyhPpgx(paramMap);
                    if (CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)) {
                        preEstateNum = bdcXnbdcdyhGxDOList.get(0).getXnbdcdyh();
                    }
                }
            }
            //业务报文 ID
            String bizMsgId = accesssModelHandlerService.getBizMsgId(bdcXmDO);
            // 设置参数
            headModel.setBizMsgID(StringUtils.isNotBlank(bizMsgId) ? bizMsgId : "/");
            headModel.setAreaCode(StringUtils.isNotBlank(areaCode) ? areaCode : "/");
            headModel.setASID(StringUtils.isNotBlank(asId) ? asId : "/");
            headModel.setRightType(StringUtils.isNotBlank(rightType) ? rightType : "/");
            headModel.setRegType(StringUtils.isNotBlank(regType) ? regType : "/");
            headModel.setCreateDate(createDate);
            headModel.setRecFlowID(ywh);
            headModel.setRegOrgID(regOrgId);
            headModel.setParcelID(StringUtils.isNotBlank(parceId) ? parceId : "/");
            headModel.setEstateNum(StringUtils.isNotBlank(estateNum) ? estateNum : "/");
            headModel.setPreEstateNum(StringUtils.isNotBlank(preEstateNum) ? preEstateNum : "/");
            headModel.setPreCertID(StringUtils.isNotBlank(preCerId) ? preCerId : "/");
            headModel.setCertCount(StringUtils.isNotBlank(certCount.toString()) ? certCount.toString() : "/");
            headModel.setProofCount(StringUtils.isNotBlank(proofCount.toString()) ? proofCount.toString() : "/");
        }
        return headModel;
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 为生产xmid 数据的情况下组织headModel进行xsd校验
     * @date : 2022/11/21 16:50
     */
    @Override
    public HeadModel generateHeadModel(QjsjjcDTO qjsjjcDTO) {
        HeadModel headModel = new HeadModel();
        if (StringUtils.isBlank(qjsjjcDTO.getBdcdyh())) {
            return headModel;
        }
        String areaCode = "";
        String asId = Constants.ASID;
        String rightType = "";
        String regType = "";
        Date createDate = null;
        String ywh = UUIDGenerator.generate16();
        String regOrgId = "";
        String parceId = "";
        String estateNum = "";
        String preEstateNum = "";
        String preCerId = "";
        Integer certCount = 0;
        Integer proofCount = 0;

        // 处理参数
        headModel = new HeadModel();
        if (StringUtil.isNotBlank(qjsjjcDTO.getBdcdyh()) && qjsjjcDTO.getBdcdyh().length() == 28) {
            areaCode = qjsjjcDTO.getBdcdyh().substring(0, 6);
        }
        if (!ObjectUtils.isEmpty(qjsjjcDTO.getQllx())) {
            rightType = Integer.toString(qjsjjcDTO.getQllx());
        }
        if (!ObjectUtils.isEmpty(qjsjjcDTO.getDjlx())) {
            regType = Integer.toString(qjsjjcDTO.getDjlx());
        }
        createDate = new Date();
        regOrgId = qjsjjcDTO.getDjjg();
        if (StringUtils.isNotBlank(qjsjjcDTO.getYcqzh())) {
            if (turnOnReplacezf) {
                preCerId = qjsjjcDTO.getYcqzh().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf);
            } else {
                preCerId = qjsjjcDTO.getYcqzh();
            }
        }
        // 证书类型字典表(表名：BDC_ZD_ZSLX) 1：证书；2：证明；
        certCount = 1;
        proofCount = 0;
        List<BdcBdcdjbZdjbxxDO> bdcBdcdjbZdjbxxDOList = bdcXmMapper.listBdcDjbZdjbxx(qjsjjcDTO.getBdcdyh().substring(0, 19));
        if (CollectionUtils.isNotEmpty(bdcBdcdjbZdjbxxDOList)) {
            parceId = bdcBdcdjbZdjbxxDOList.get(0).getZddm();
            estateNum = qjsjjcDTO.getBdcdyh();
        }
        preEstateNum = qjsjjcDTO.getBdcdyh();
        //如果配置优先读取配置，如果为空继续走之前的逻辑
        //业务报文 ID
        String bizMsgId = accesssModelHandlerService.getBizMsgId(qjsjjcDTO.getBdcdyh().substring(0, 6));
        // 设置参数
        headModel.setBizMsgID(StringUtils.isNotBlank(bizMsgId) ? bizMsgId : "/");
        headModel.setAreaCode(StringUtils.isNotBlank(areaCode) ? areaCode : "/");
        headModel.setASID(StringUtils.isNotBlank(asId) ? asId : "/");
        headModel.setRightType(StringUtils.isNotBlank(rightType) ? rightType : "/");
        headModel.setRegType(StringUtils.isNotBlank(regType) ? regType : "/");
        headModel.setCreateDate(createDate);
        headModel.setRecFlowID(ywh);
        headModel.setRegOrgID(regOrgId);
        headModel.setParcelID(StringUtils.isNotBlank(parceId) ? parceId : "/");
        headModel.setEstateNum(StringUtils.isNotBlank(estateNum) ? estateNum : "/");
        headModel.setPreEstateNum(StringUtils.isNotBlank(preEstateNum) ? preEstateNum : "/");
        headModel.setPreCertID(StringUtils.isNotBlank(preCerId) ? preCerId : "/");
        headModel.setCertCount(StringUtils.isNotBlank(certCount.toString()) ? certCount.toString() : "/");
        headModel.setProofCount(StringUtils.isNotBlank(proofCount.toString()) ? proofCount.toString() : "/");
        return headModel;
    }

}

