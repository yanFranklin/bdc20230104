package cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSendHyxxService implements SendHyxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSendHyxxService.class);

    /**
     * 宗地特征码H 海域
     */
    public static final String ZDTZM_H = "H";

    /**
     * 校验当前bdcdyh是否是海域
     *
     * @param bdcdyh
     * @return
     */
    protected boolean checkHyByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)
                && bdcdyh.length() == 28) {
            return StringUtils.equals(ZDTZM_H, bdcdyh.substring(13, 14));
        }
        return false;
    }

    protected abstract CommonResponse sendHyxx(String gzlslid);

    @Override
    public CommonResponse commonSendHyxx(BdcXmDO bdcXmDO) {
        if (bdcXmDO == null) {
            return CommonResponse.fail("未查询到相关xm信息");
        }
        if (checkHyByBdcdyh(bdcXmDO.getBdcdyh())) {
            return sendHyxx(bdcXmDO.getGzlslid());
        }
        return CommonResponse.ok("当前xm信息不需要推送海域数据,bdcdyh:" + bdcXmDO.getBdcdyh() + ",gzlslid:" + bdcXmDO.getGzlslid());
    }

    protected void checkRep(CommonResponse response) {
        if (!response.isSuccess()) {
            LOGGER.info("海域接口调用失败:{}", response.getFail() != null ? response.getFail().getMessage() : null);
            throw new AppException(ErrorCode.ILLEGAL_STATE, response.getFail() != null ? response.getFail().getMessage() : null);
        }
    }
}
