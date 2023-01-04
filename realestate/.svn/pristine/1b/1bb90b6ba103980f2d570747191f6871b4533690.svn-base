package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.HeadModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.KttZdjbxxDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttZdjbxxMapper;
import cn.gtmap.realestate.exchange.service.AccessModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessHeadModelService;
import cn.gtmap.realestate.exchange.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取报文头信息  还差一个RecType
 */
@Service
public class NationalAccessHeadModelServiceImpl implements NationalAccessHeadModelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessHeadModelServiceImpl.class);

    @Autowired
    private AccessModelHandlerService accesssModelHandlerService;

    @Autowired
    KttZdjbxxMapper kttZdjbxxMapper;
    @Autowired
    BdcdjMapper bdcdjMapper;
    @Autowired
    private BdcXmMapper bdcXmMapper;


    /**
     * @param xmid
     * @return
     */
    @Override
    public HeadModel getAccessHeadModel(String xmid) {
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
            if (!ObjectUtils.isEmpty(bdcXmDO)) {
                areaCode = bdcXmDO.getQxdm();
                String bdcdyh = bdcXmDO.getBdcdyh();
                if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28) {
                    areaCode = bdcdyh.substring(0, 6);
                }
                if (!ObjectUtils.isEmpty(bdcXmDO.getQllx())) {
                    rightType = Integer.toString(bdcXmDO.getQllx());
                }
                if (!ObjectUtils.isEmpty(bdcXmDO.getDjlx())) {
                    regType = Integer.toString(bdcXmDO.getDjlx());
                }
                if (!ObjectUtils.isEmpty(bdcXmDO.getDjsj())) {
                    createDate = bdcXmDO.getDjsj();
                }
                if (!ObjectUtils.isEmpty(bdcXmDO.getDjjg())) {
                    regOrgId = bdcXmDO.getDjjg();
                }
                if (StringUtils.isNotBlank(bdcXmDO.getYcqzh())) {
                    preCerId = bdcXmDO.getYcqzh();
                }

                // 证书类型字典表(表名：BDC_ZD_ZSLX) 1：证书；2：证明；
                HashMap tempmap = new HashMap();
                tempmap.put("proid", xmid);
                tempmap.put("zstype", 1);
                certCount = bdcdjMapper.getZsNum(tempmap);
                tempmap.clear();
                tempmap.put("proid", xmid);
                tempmap.put("zstype", 2);
                proofCount = bdcdjMapper.getZsNum(tempmap);
            }
            Map<String, Object> param = new HashMap();
            param.put("ywh", xmid);
            List<KttZdjbxxDO> kttZdjbxxList = kttZdjbxxMapper.queryKttZdjbxxList(param);
            if (CollectionUtils.isNotEmpty(kttZdjbxxList)) {
                parceId = kttZdjbxxList.get(0).getDjh();
                estateNum = kttZdjbxxList.get(0).getBdcdyh();
            }

            List<HashMap<String, String>> preInfoList = bdcdjMapper.queryPreInfo(xmid);
            if (CollectionUtils.isNotEmpty(preInfoList)) {
                for (HashMap preInfo : preInfoList) {
                    if (StringUtils.isNotBlank(MapUtils.getString(preInfo, "BDCDYH"))
                            && !StringUtils.contains(preEstateNum, MapUtils.getString(preInfo, "BDCDYH"))) {
                        if (StringUtils.isNotBlank(preEstateNum)) {
                            preEstateNum += "," + preInfo.get("BDCDYH").toString();
                        } else {
                            preEstateNum = preInfo.get("BDCDYH").toString();
                        }
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

}

