package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcYwblBahdDO;
import cn.gtmap.realestate.common.core.domain.BdcYwblhzxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.init.changzhou.BdcYwblBahdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 业务办理备案核对页面
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 14:19
 **/
@RestController
@RequestMapping("/slym/bahd")
public class BdcYwblBahdController extends BaseController {

    @Autowired
    BdcYwblBahdFeignService bdcYwblBahdFeignService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询备案核对信息
     * @date : 2021/7/21 14:19
     */
    @GetMapping("")
    public Object queryBahd(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return new BdcYwblhzxxDO();
        }
        List<BdcYwblBahdDO> bdcYwblBahdDOList = bdcYwblBahdFeignService.listYwblBahd(gzlslid);
        BdcYwblBahdDO bdcYwblBahdDO = new BdcYwblBahdDO();
        if (CollectionUtils.isEmpty(bdcYwblBahdDOList)) {
            bdcYwblBahdDO.setGzlslid(gzlslid);
//            bdcYwblBahdDO = bdcYwblBahdFeignService.insertYwblBahd(bdcYwblBahdDO);
        } else {
            bdcYwblBahdDO = bdcYwblBahdDOList.get(0);
        }
        if (Objects.isNull(bdcYwblBahdDO.getHdrq())) {
            bdcYwblBahdDO.setHdrq(new Date());
        }
        if (StringUtils.isBlank(bdcYwblBahdDO.getHdr())) {
            //如果为空核定人取当前登陆人员
            bdcYwblBahdDO.setHdr(userManagerUtils.getUserAlias());
        }
        return bdcYwblBahdDO;
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存备案核对信息
     * @date : 2021/7/21 14:22
     */
    @PostMapping("")
    public Object saveBahdxx(@RequestBody String json) {
        if (StringUtils.isBlank(json)) {
            throw new AppException("更新房屋建设状态查看信息缺失数据");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        String bahdid = jsonObject.getString("bahdid");
        if (StringUtils.isBlank(bahdid)) {
            BdcYwblBahdDO bdcYwblBahdDO = JSONObject.parseObject(json, BdcYwblBahdDO.class);
            return bdcYwblBahdFeignService.insertYwblBahd(bdcYwblBahdDO);
        }
        return bdcEntityFeignService.updateByJsonEntity(json, BdcYwblBahdDO.class.getName());
    }
}
