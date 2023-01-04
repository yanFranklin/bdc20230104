package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.nantong.daxx.NanTongDaxxDo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/12/16
 * @description 保存档案信息服务类
 */
@Service("nanTongDaxxServiceImpl")
public class NanTongDaxxServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(NanTongDaxxServiceImpl.class);


    @Autowired
    private EntityMapper entityMapper;


    /**
     * 保存或更新推送的档案柜信息
     *
     * @param NanTongDaxxDo NanTongDaxxDo
     * @return ExchangeDsfCommonResponse ExchangeDsfCommonResponse
     * @Date 2021/12/16
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public ExchangeDsfCommonResponse saveOrUpdateDaxx(NanTongDaxxDo nanTongDaxxDo) {
        if (null != nanTongDaxxDo && StringUtils.isNotBlank(nanTongDaxxDo.getSlbh())) {
            try {
                int count = entityMapper.saveOrUpdate(nanTongDaxxDo, nanTongDaxxDo.getSlbh());
                if (count > 0) {
                    return ExchangeDsfCommonResponse.ok("成功！");
                } else {
                    return ExchangeDsfCommonResponse.fail("提交失败！");
                }
            } catch (Exception e) {
                LOGGER.info("保存或更新中失败！提交参数为：{}，原因：{}", nanTongDaxxDo.toString(), e);
                return ExchangeDsfCommonResponse.fail(e.toString());
            }

        } else {
            return ExchangeDsfCommonResponse.fail("缺失必要参数，请检查！");
        }

    }
}
