package cn.gtmap.realestate.inquiry.ui.web.rest.huaian;

import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlJyxxRestService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0, 2022/9/7
 * @description  淮安重新推送交易业务信息
 */
@Api(tags = "重新推送交易业务信息")
@RestController
@RequestMapping("/rest/v1.0/cxts/jyxx")
public class JyxxCxtsController extends BaseController {

    @Autowired
    BdcSlJyxxRestService bdcSlJyxxRestService;

    /**
     * @description 重新推送交易业务信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/7 19:43
     * @param rzids
     * @return void
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object listRyGzltjByBdcxx(@RequestParam(value = "rzids") String rzids) {
        if(StringUtils.isBlank(rzids)){
            throw new MissingArgumentException("缺少日志ID参数！");
        }

        try {
            String[] rzidArr = rzids.split(",");
            List<String> rzidList = Arrays.asList(rzidArr);
            List<String> resultList = new ArrayList<>(rzidList.size());
            for (int i = 0; i < rzidList.size(); i++) {
                boolean tsResult = bdcSlJyxxRestService.btHaFcjyYwxx(rzidList.get(i));
                if (tsResult) {
                    resultList.add(rzidList.get(i) + "推送成功");
                } else {
                    resultList.add(rzidList.get(i) + "推送失败");
                }
            }
            return resultList;
        } catch (Exception e) {
            LOGGER.error("重新推送交易业务信息异常", e);
        }
        return null;
    }
}
