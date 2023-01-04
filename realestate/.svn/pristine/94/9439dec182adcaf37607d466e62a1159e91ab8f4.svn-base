package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZhxpCxFeignService;
import cn.gtmap.realestate.inquiry.ui.util.GetDateUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/15
 * @description 综合小屏-中心不同业务的排号情况
 */
@RestController
@RequestMapping(value = "/rest/v1.0")
public class BdcZhxpCxController extends BaseController {
    @Autowired
    private BdcZhxpCxFeignService bdcZhxpCxFeignService;

    /**
     * version 1.0
     *
     * @return List 当前中心呼叫信息查询
     * @description 综合小屏当前呼叫信息查询
     * @date 2019/7/12
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @GetMapping("/zhxp/dqhj/{zxmc}")
    public List<Map> listBdcZhxpDqhj(@PathVariable("zxmc") String zxmc) {
        List<Map> dqhjList = bdcZhxpCxFeignService.listBdcZhxpDqhj(zxmc);

        if (CollectionUtils.isNotEmpty(dqhjList)) {
            Date startDate = GetDateUtils.getStartTime();
            Date endDate = GetDateUtils.getEndTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Map map : dqhjList) {
                if (MapUtils.getObject(map, "GXSJ") != null){
                    try {
                        Date gxsj = simpleDateFormat.parse(MapUtils.getString(map, "GXSJ"));
                        if (startDate.after(gxsj)
                                || endDate.before(gxsj)){
                            map.put("HJH", "");
                        }
                    }catch (ParseException ex){
                        LOGGER.error(ex.getMessage(),ex);
                    }
                }
            }
        }

        return dqhjList;
    }

    /**
     * version 1.0
     *
     * @return List 当前中心等待呼叫信息
     * @description 综合小屏等待呼叫信息查询
     * @date 2019/7/12
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @GetMapping("/zhxp/ddhj/{zxmc}")
    public List<Map> listBdcZhxpDdhj(@PathVariable("zxmc") String zxmc) {
        return bdcZhxpCxFeignService.listBdcZhxpDdhj(zxmc);
    }
}
