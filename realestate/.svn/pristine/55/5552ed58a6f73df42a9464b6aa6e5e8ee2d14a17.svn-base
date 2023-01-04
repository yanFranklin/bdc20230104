package cn.gtmap.realestate.exchange.service.impl.national.access;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.HeadModel;
import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.national.*;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.service.national.access.CheckAccessDataService;
import cn.gtmap.realestate.exchange.util.UploadServiceUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-04-30
 * @description 数据校验
 */
@Service
public class CheckAccessDataServiceImpl implements CheckAccessDataService {
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private AccesssModelHandlerService accesssModelHandlerService;
    @Autowired
    private WsxNationalAccessService wsxNationalAccessService;
    @Resource(name = "nationalAccessXmlZxWlxmdj")
    private NationalAccessXmlService nationalAccessXmlZxWlxmdj;

    @Autowired
    NationalAccessHeadModelService nationalAccessHeadModelService;

    @Autowired
    BdcExchangeZddzService bdcExchangeZddzService;

    @Autowired
    AccessDefaultValueService accessDefaultValueService;

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckAccessDataServiceImpl.class);

    /**
     * @param xmid
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 上报日志台账数据校验
     */
    @Override
    public Boolean checkAccessData(String xmid) {
        boolean result=false;
        BdcXmDO bdcXmDO = null;
        if (StringUtils.isNotBlank(xmid)) {
            bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
        }
        if (bdcXmDO == null) {
            return result;
        }
        MessageModel messageModel = null;
        try {
            // 获取不同类型的策略 service
            NationalAccessXmlService nationalAccessXmlService = accesssModelHandlerService.getAccessXmlService(bdcXmDO);
            // 利用对应策略的 service 生成 message
            if (!ObjectUtils.isEmpty(nationalAccessXmlService)) {
                // 获取 message
                messageModel = accesssModelHandlerService.getMessageModel(nationalAccessXmlService, bdcXmDO.getXmid());
            } else {
                LOGGER.error("数据汇交 - 未找到对应的策略服务 xmid：{}", bdcXmDO.getXmid());
            }
        } catch (Exception e) {
            LOGGER.error("数据汇交失败：Xmid：{}", xmid, e);
        }
        if(messageModel!=null){
            List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUpload();
            for (NationalAccessUpload nationalAccessUpload : list) {
                result = nationalAccessUpload.checkData(messageModel);
                if(!result){
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param ywh
     * @param bdcdyh
     * @return java.lang.Boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 检查外市县上报数据
     */
    @Override
    public Boolean checkWsxAccessData(String qxdm,String ywh, String bdcdyh) {
        boolean result = false;
        if(StringUtils.isNotBlank(ywh) && StringUtils.isNotBlank(bdcdyh)){
            MessageModel messageModel = wsxNationalAccessService.getWsxMessageModel(qxdm,ywh,bdcdyh);
            if(messageModel!=null && messageModel.getHeadModel() != null && messageModel.getDataModel() != null){

                // 处理 biz msg id
                if(StringUtils.isBlank(messageModel.getHeadModel().getBizMsgID())){
                    String areaCode = messageModel.getHeadModel().getAreaCode();
                    String bizMsgId = accesssModelHandlerService.getBizMsgId(areaCode);
                    messageModel.getHeadModel().setBizMsgID(bizMsgId);
                }

                List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUpload();
                for (NationalAccessUpload nationalAccessUpload : list) {
                    result = nationalAccessUpload.checkData(messageModel);
                    if (!result) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param xmids
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外联项目数据校验
     * @date : 2022/12/6 15:04
     */
    @Override
    public Boolean checkWlxmAccessData(String xmids) {
        boolean result = false;
        MessageModel messageModel = new MessageModel();
        //传入gxid 作为ywlsh
        if (StringUtils.isNotBlank(xmids) && StringUtils.contains(xmids, CommonConstantUtils.ZF_YW_DH) && StringUtils.split(xmids, CommonConstantUtils.ZF_YW_DH).length == 2) {
            String xmid = xmids.split(CommonConstantUtils.ZF_YW_DH)[1];
            DataModel dataModel = nationalAccessXmlZxWlxmdj.getNationalAccessDataModel(xmids);
            if (dataModel == null || CollectionUtils.isEmpty(dataModel.getQlfQlZxdjList())) {
                return false;
            }
            HeadModel headModel = nationalAccessHeadModelService.getAccessHeadModel(xmid, true);
            headModel.setRecType(nationalAccessXmlZxWlxmdj.getRecType());
            headModel.setRecFlowID(dataModel.getQlfQlZxdjList().get(0).getYwh());
            // 字典表和国标字典对照
            headModel = bdcExchangeZddzService.handleHeadZddz(headModel);
            dataModel = bdcExchangeZddzService.handleZddz(dataModel, dataModel.getQlfQlZxdjList().get(0).getYwh());
            messageModel.setDataModel(dataModel);
            messageModel.setHeadModel(headModel);
            if (StringUtils.isBlank(messageModel.getHeadModel().getBizMsgID())) {
                String areaCode = messageModel.getHeadModel().getAreaCode();
                String bizMsgId = accesssModelHandlerService.getBizMsgId(areaCode);
                messageModel.getHeadModel().setBizMsgID(bizMsgId);
            }
            // 读取默认值配置表  赋默认值
            accessDefaultValueService.setDefaultValueWithDefaultTable(nationalAccessXmlZxWlxmdj.getNationalAccessDataServiceSet(), messageModel, true);
            List<NationalAccessUpload> list = UploadServiceUtil.listNationalAccessUpload();
            for (NationalAccessUpload nationalAccessUpload : list) {
                result = nationalAccessUpload.checkData(messageModel);
                if (!result) {
                    break;
                }
            }
        }

        return result;
    }
}
