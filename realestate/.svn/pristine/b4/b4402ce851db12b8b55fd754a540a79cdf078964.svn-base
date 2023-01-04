package cn.gtmap.realestate.accept.ui.web;


import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlQjdcsqDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcQjdcFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlxxHxFeignService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @param
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 权籍调查页面交互层
 * @date : 2021/8/5 14:00
 */
@Controller
@Validated
@RequestMapping("/qjdc")
public class SlymQjdcController extends BaseController {

    @Autowired
    BdcQjdcFeignService bdcQjdcFeignService;
    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;
    @Autowired
    BdcSlxxHxFeignService bdcSlxxHxFeignService;

    /**
     * @param processInsId
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取权籍调查申请信息
     * @date : 2021/8/5 14:17
     */
    @GetMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object getBdcQjdcsq(@NotBlank(message = "获取权籍调查申请信息缺失工作流实例id") @RequestParam(name = "processInsId", required = true) String processInsId) {
        List<BdcSlQjdcsqDO> bdcSlQjdcsqDOList = bdcQjdcFeignService.listSlQjdc(processInsId);
        if (CollectionUtils.isNotEmpty(bdcSlQjdcsqDOList)) {
            return bdcSlQjdcsqDOList.get(0);
        }
        return new BdcSlQjdcsqDO();
    }

    @PostMapping("")
    @ResponseBody
    public Object saveQjdcXx(@RequestBody String json) throws Exception {
        if (StringUtils.isBlank(json)) {
            throw new AppException("保存权籍调查信息缺失数据");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        String qjdcsqxxid = jsonObject.getString("qjdcsqxxid");
        String gzlslid = jsonObject.getString("gzlslid");
        String bdcdyh = StringUtils.deleteWhitespace(jsonObject.getString("bdcdyh"));
        if (StringUtils.isBlank(qjdcsqxxid)) {
            BdcSlQjdcsqDO bdcSlQjdcsqDO = JSONObject.parseObject(json, BdcSlQjdcsqDO.class);
            bdcSlQjdcsqDO.setBdcdyh(bdcdyh);
            bdcQjdcFeignService.saveSlQjdc(bdcSlQjdcsqDO);
        } else {
            jsonObject.put("bdcdyh", bdcdyh);
            bdcSlEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(jsonObject), BdcSlQjdcsqDO.class.getName());
        }
        // 回写portal
        bdcSlxxHxFeignService.hxBdcSlxx(gzlslid);
        return 1;
    }
}
