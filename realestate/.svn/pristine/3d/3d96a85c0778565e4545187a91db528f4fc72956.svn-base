package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.config.ConfigInit;
import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogNationalHandlerService;
import cn.gtmap.realestate.exchange.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.util.Constants;
import cn.gtmap.realestate.exchange.util.WebServiceClientHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("nationalAccessLog_standard")
public class AccessLogNationalStdImpl implements AccessLogNationalHandlerService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ConfigInit configInit;

    /**
     * @param xml           xml文件
     * @param bdcJrDbrzjlDO 日志接入表实体类
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 部级webservice日志上报
     **/
    @Override
    public void nationalAccessLogWeb(String xml, BdcJrDbrzjlDO bdcJrDbrzjlDO) {
        String nationalUrl = configInit.getNationalUrl();
        String result = "";
        StringBuilder sb = WebServiceClientHelper.callService(nationalUrl, "exchangeInfo", xml);
        result = sb.toString();
        //保存到库里
        if (StringUtils.equals(Constants.SUCCESS, result)) {
            logger.info("接入日志成功");
            bdcJrDbrzjlDO.setCgbs(CommonConstantUtils.SF_S_DM);
            bdcJrDbrzjlDO.setXyxx("上报成功");
        } else {
            bdcJrDbrzjlDO.setXyxx(result);
            logger.info("接入日志失败，失败原因：" + result);
        }
    }
}
