package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.exchange.BdcSjptRestService;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.exchange.service.inf.gx.GxCxqqService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 省级平台服务查询本地数据
 * @author: <a href="mailto:wutao2@gtmap.cn">wutao2</a>
 * @create: 2022-08-19 16:22
 **/
@RestController
@Api(tags = "系统接入配置接口")
public class BdcSjptRestController implements BdcSjptRestService {
    @Autowired
    private GxCxqqService gxCxqqService;
    /**
     * @param kssj,jssj
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据起始日期统计省厅数据的查询量
     */
    @Override
    public Integer querySjsjlcxCount(String kssj, String jssj){
        if(StringUtil.isBlank(kssj) || StringUtil.isBlank(jssj)){
            throw new MissingArgumentException("开始时间和结束时间都不能为空！");
        }
        return gxCxqqService.getSjsjlcxCount(kssj,jssj);
    }
}
