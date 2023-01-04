package cn.gtmap.realestate.inquiry.ui.web.rest;


import cn.gtmap.realestate.common.core.domain.BdcXdryxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXdryQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcXdryxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 限定人员信息ui控制层
 * @date : 2021/8/3 14:55
 */
@RestController
@RequestMapping(value = "/rest/v1.0/xdryxx")
@Validated
public class BdcXdryxxController {

    @Autowired
    BdcXdryxxFeignService bdcXdryxxFeignService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;

    /**
     * @param xdryxxIdList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量删除限定人员信息
     * @date : 2021/8/3 14:55
     */
    @DeleteMapping("")
    public void batchDeleteXdryxx(@RequestParam(name = "ids") String ids) {
        if (StringUtils.isNotBlank(ids)) {
            bdcXdryxxFeignService.deletXdryxx(Arrays.asList(ids.split(CommonConstantUtils.ZF_YW_DH)));
        }
    }

    @GetMapping("")
    public Object queryXdryxx(@NotBlank(message = "查询限定人员信息主键id不可为空") String id) {
        BdcXdryQO bdcXdryQO = new BdcXdryQO();
        bdcXdryQO.setId(id);
        List<BdcXdryxxDO> bdcXdryxxDOList = bdcXdryxxFeignService.listBdcXdyrxx(bdcXdryQO);
        if (CollectionUtils.isNotEmpty(bdcXdryxxDOList)) {
            return bdcXdryxxDOList.get(0);
        }
        return new BdcXdryxxDO();
    }

    @PatchMapping("")
    public Object saveOrUpdateXdryxx(@RequestBody String json) {
        if (StringUtils.isBlank(json)) {
            throw new AppException("更新限定人员信息缺失数据");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        String id = jsonObject.getString("id");
        if (StringUtils.isBlank(id)) {
            BdcXdryxxDO bdcXdryxxDO = JSONObject.parseObject(json, BdcXdryxxDO.class);
            return bdcXdryxxFeignService.addBdcXdryxx(bdcXdryxxDO);
        }
        return bdcEntityFeignService.updateByJsonEntity(json, BdcXdryxxDO.class.getName());
    }


}
