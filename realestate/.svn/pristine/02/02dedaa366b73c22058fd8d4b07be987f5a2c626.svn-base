package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcFwjsztckDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcFwjsztCkDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.changzhou.BdcFwJsztCkFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 房屋建设状态从看contrller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-20 13:47
 **/

@RestController
@RequestMapping("/slym/fwjszt")
@Validated
public class BdcFwJsztCkController extends BaseController {
    @Autowired
    BdcFwJsztCkFeignService bdcFwJsztCkFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房屋建设状态查看信息
     * @date : 2021/7/20 14:15
     */
    @GetMapping("")
    public Object queryFwjszt(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            BdcFwjsztCkDTO bdcFwjsztCkDTO = new BdcFwjsztCkDTO();
            BdcFwjsztckDO bdcFwjsztckDO = new BdcFwjsztckDO();
            bdcFwjsztCkDTO.setBdcFwjsztckDO(bdcFwjsztckDO);
            return bdcFwjsztCkDTO;
        }
        List<BdcFwjsztckDO> bdcFwjsztckDOList = bdcFwJsztCkFeignService.queryFwjszt(gzlslid);
        BdcFwjsztCkDTO bdcFwjsztCkDTO = new BdcFwjsztCkDTO();
        BdcFwjsztckDO bdcFwjsztckDO = new BdcFwjsztckDO();
        if (CollectionUtils.isNotEmpty(bdcFwjsztckDOList)) {
            bdcFwjsztckDO = bdcFwjsztckDOList.get(0);
        } else {
            bdcFwjsztckDO.setGzlslid(gzlslid);
        }
        bdcFwjsztCkDTO.setBdcFwjsztckDO(bdcFwjsztckDO);
        //项目信息
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            bdcFwjsztCkDTO.setQlr(bdcXmDOList.get(0).getQlr());
            bdcFwjsztCkDTO.setZl(StringToolUtils.resolveBeanToAppendStr(bdcXmDOList, "getZl", CommonConstantUtils.ZF_YW_DH));
        }
        return bdcFwjsztCkDTO;
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新房屋建设状态查看信息
     * @date : 2021/7/21 13:44
     */

    @PostMapping("")
    public Object updateFwjszt(@RequestBody String json) {
        if (StringUtils.isBlank(json)) {
            throw new AppException("更新房屋建设状态查看信息缺失数据");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        String wyckid = jsonObject.getString("wyckid");
        //添加外业调查时间,外业调查人，权籍审核时间，权籍审核人
        String currentUser =userManagerUtils.getUserAlias();
        jsonObject.put("dcr",currentUser);
        jsonObject.put("dcsj",new Date());
        jsonObject.put("qjshr",currentUser);
        jsonObject.put("qjshsj",new Date());
        json =JSONObject.toJSONString(jsonObject);
        if (StringUtils.isBlank(wyckid)) {
            BdcFwjsztckDO bdcFwjsztckDO = JSONObject.parseObject(json, BdcFwjsztckDO.class);
            return bdcFwJsztCkFeignService.insertFwjsztCk(bdcFwjsztckDO);
        }
        return bdcEntityFeignService.updateByJsonEntity(json, BdcFwjsztckDO.class.getName());
    }
}
