package cn.gtmap.realestate.exchange.web.rest.shucheng;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.enums.BdcSplyQlrlyEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.exchange.ExchangeInterfaceRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/1/11
 * @description 舒城共享系统相关服务请求
 */
@RestController
@Api(tags = "舒城共享系统相关服务请求")
@RequestMapping("/realestate-exchange/rest/v1.0/gxxt")
public class GxxtRestController {
    private static Logger LOGGER = LoggerFactory.getLogger(GxxtRestController.class);
    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private ExchangeInterfaceRestService exchangeInterfaceRestService;

    /**
     * 根据合同号查询交易信息
     *
     * @param htbh 合同编号
     * @return map map
     * @Date 2022/1/11
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @ApiOperation(value = "推送共享业务系统数据接口")
    @ResponseStatus(code = HttpStatus.OK)
    @DsfLog(logEventName = "推送共享业务系统数据接口", dsfFlag = "GXXT", requester = "BDC", responser = "MK")
    @PostMapping("/tsGxxt/processInsId")
    @ResponseBody
    public void tsGxxtData(@RequestParam(value = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺少工作流必要参数！请检查");
        }
        LOGGER.info("开始推送舒城共享系统工作流id为：{}", processInsId);

        List<BdcXmDO> bdcXmDOList = bdcXmMapper.queryBdcXmList(processInsId);
        Map data = new HashMap();
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            LOGGER.info("推送舒城共享系统sply为：{}", bdcXmDOList.get(0).getSply());
            if (CommonConstantUtils.SPLY_GXXT.equals(bdcXmDOList.get(0).getSply())) {
                data.put("slbh", bdcXmDOList.get(0).getSpxtywh());
                data.put("bdcdyh", bdcXmDOList.get(0).getSpxtywh());
                LOGGER.info("推送舒城共享系统数据为：{}", data.toString());
            }
            Object object = exchangeInterfaceRestService.requestInterface("tsGxxt", data);
            if (null != object) {
                LOGGER.info("推送舒城共享系统数据返回：{}", object.toString());
            }
        }

    }
}
