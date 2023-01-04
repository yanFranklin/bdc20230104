package cn.gtmap.realestate.exchange.service.impl.national.access;

import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.AccessModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessUpload;
import cn.gtmap.realestate.exchange.service.national.NationalAccessXmlService;
import cn.gtmap.realestate.exchange.service.national.access.CheckAccessDataService;
import cn.gtmap.realestate.exchange.util.UploadServiceUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
    private AccessModelHandlerService accessModelHandlerService;

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
            NationalAccessXmlService nationalAccessXmlService = accessModelHandlerService.getAccessXmlService(bdcXmDO);
            // 利用对应策略的 service 生成 message
            if (!ObjectUtils.isEmpty(nationalAccessXmlService)) {
                // 获取 message
                messageModel = accessModelHandlerService.getMessageModel(nationalAccessXmlService, bdcXmDO.getXmid());
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
}
