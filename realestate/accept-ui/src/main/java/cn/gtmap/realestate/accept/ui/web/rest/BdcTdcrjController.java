package cn.gtmap.realestate.accept.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlTdcrjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlTdcrjFeignService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/8
 * @description 土地出让金服务
 */
@RestController
@RequestMapping("/rest/v1.0/tdcrj")
@Api(tags = "不动产土地出让金")
public class BdcTdcrjController {

    @Autowired
    BdcSlTdcrjFeignService bdcSlTdcrjFeignService;

    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取土地出让金信息
      */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String", paramType = "query", required = true)})
    public List<BdcSlTdcrjDO> listBdcSlTdcrjByXmid(@RequestParam(name="xmid")String xmid) {
        return bdcSlTdcrjFeignService.listBdcSlTdcrjByXmid(xmid);
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 保存土地出让金信息
      */
    @PostMapping("")
    public void updateTdcrj(@RequestBody String json) {
        if (StringUtils.isBlank(json)) {
            throw new AppException("更新土地出让金缺失数据");
        }
        JSONArray jsonArray = JSONObject.parseArray(json);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String tdcrjid = jsonObject.getString("tdcrjid");
                if (StringUtils.isNotBlank(tdcrjid)) {
                    bdcSlEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(jsonObject), BdcSlTdcrjDO.class.getName());
                } else {
                    BdcSlTdcrjDO insertTdcrj = JSON.parseObject(JSONObject.toJSONString(jsonObject), BdcSlTdcrjDO.class);
                    bdcSlTdcrjFeignService.insertBdcSlTdcrj(insertTdcrj);

                }
            }

        }


    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除土地出让金信息
     */
    @DeleteMapping("")
    public void deleteTdcrj(@RequestParam(name = "tdcrjid") String tdcrjid) {
        if(StringUtils.isNotBlank(tdcrjid)){
            bdcSlTdcrjFeignService.deleteBdcSlTdcrj(tdcrjid);
        }


    }
}
