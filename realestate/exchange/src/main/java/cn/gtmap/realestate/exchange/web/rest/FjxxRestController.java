package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.service.rest.exchange.FjxxRestService;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.service.inf.FjxxService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/4/2 15:14
 * @description
 */
@OpenController(consumer = "附件信息接口")
@RestController
@Api(tags = "附件信息接口")
public class FjxxRestController implements FjxxRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FjxxRestController.class);

    @Autowired
    private FjxxService fjPicxxService;

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [slbh]
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.exchange.fjpicxx.FjPicxxDTO>
     * @description 查询附件图片信息 南通
     */
    @Override
    public Object queryFjxxBySlbh(@PathVariable("slbh") String slbh) {
        if(StringUtils.isBlank(slbh)) {
            LOGGER.error("受理编号为空，无法查询附件图片信息！");
            return ExchangeDsfCommonResponse.fail("受理编号为空，无法查询附件信息！");
        }
        return fjPicxxService.queryFjxxBySlbh(slbh);
    }

    /**
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @params [bdcqzh]
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.exchange.fjpicxx.FjPicxxDTO>
     * @description 通过不动产权证号查询附件图片信息
     */
    @Override
    public Object queryFjxxByBdcqzh(@PathVariable("bdcqzh") String bdcqzh) {
        if(StringUtils.isBlank(bdcqzh)) {
            LOGGER.error("产权证号为空，无法查询附件图片信息！");
            return ExchangeDsfCommonResponse.fail("产权证号为空，无法查询附件信息！");
        }
        return fjPicxxService.queryFjxxByBdcqzh(bdcqzh);
    }
}
