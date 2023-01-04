package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcYwblhzxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.init.changzhou.BdcYwblHzxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 业务办理核证信息ui-controller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 10:39
 **/
@RestController
@RequestMapping("/slym/ywblhzxx")
@Validated
public class BdcYwblHzxxController extends BaseController {
    @Autowired
    BdcYwblHzxxFeignService bdcYwblHzxxFeignService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 页面展现
     * @date : 2021/7/21 10:41
     */
    @GetMapping("")
    public Object queryYwblHzxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return new BdcYwblhzxxDO();
        }
        List<BdcYwblhzxxDO> bdcYwblhzxxDOList = bdcYwblHzxxFeignService.listYwblHzxx(gzlslid);
        BdcYwblhzxxDO bdcYwblhzxxDO = new BdcYwblhzxxDO();
        if (CollectionUtils.isEmpty(bdcYwblhzxxDOList)) {
            bdcYwblhzxxDO.setGzlslid(gzlslid);
        } else {
            bdcYwblhzxxDO = bdcYwblhzxxDOList.get(0);
        }
        return bdcYwblhzxxDO;
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存核证信息
     * @date : 2021/7/21 13:48
     */

    @PostMapping("")
    public Object saveHzxx(@RequestBody String json) {
        if (StringUtils.isBlank(json)) {
            throw new AppException("更新核证信息异常");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        //添加核证人和核证日期
        jsonObject.put("hzr",userManagerUtils.getUserAlias());
        jsonObject.put("hzrq",new Date());
        json =JSONObject.toJSONString(jsonObject);
        String hzxxid = jsonObject.getString("hzxxid");
        if (StringUtils.isBlank(hzxxid)) {
            BdcYwblhzxxDO bdcYwblhzxxDO = JSONObject.parseObject(json, BdcYwblhzxxDO.class);
            return bdcYwblHzxxFeignService.insertYwblHzxx(bdcYwblhzxxDO);
        }
        return bdcEntityFeignService.updateByJsonEntity(json, BdcYwblhzxxDO.class.getName());
    }
}
