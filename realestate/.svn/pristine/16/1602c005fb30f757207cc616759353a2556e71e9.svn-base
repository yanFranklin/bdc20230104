package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzLogMapper;
import cn.gtmap.realestate.certificate.core.support.annotations.CheckToken;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzLogDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate.BdcDzzLogRestService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019-9-18
 */
@Controller
@Api("电子证照日志查询")
@RequestMapping(value = "/realestate-e-certificate/fegin/log")
public class DzzzLogController implements BdcDzzLogRestService {

    @Autowired
    private BdcDzzzLogMapper bdcDzzzLogMapper;

    /**
     * @param dzzzLogDO
     * @return
     * @description 查询电子证照操作日志
     */
    @Override
    @PostMapping("/queryByCzlx")
    @ResponseBody
    public DzzzLogDO queryLogByCzlx(DzzzLogDO dzzzLogDO) {
        if (StringUtils.isBlank(dzzzLogDO.getBdcqzh()) && StringUtils.isBlank(dzzzLogDO.getController())) {
            throw new MissingArgumentException("证号和操作类型不空为空！");
        }
        DzzzLogDO dzzzLogDO1 = bdcDzzzLogMapper.queryByDzzzLogByczlx(dzzzLogDO);
        return dzzzLogDO1;
    }
}
