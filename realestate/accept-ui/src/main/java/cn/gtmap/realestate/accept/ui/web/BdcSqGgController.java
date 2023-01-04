package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcGgDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.changzhou.BdcGgFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 事前公告controller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 14:38
 **/
@RestController
@RequestMapping("/slym/sqgg")
public class BdcSqGgController extends BaseController {

    @Autowired
    BdcGgFeignService bdcGgFeignService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/21 14:41
     */
    @GetMapping("")
    public Object querySqgg(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return new BdcGgDTO();
        }
        BdcGgDTO bdcGgDTO = new BdcGgDTO();
        List<BdcGgDO> bdcGgDOList = bdcGgFeignService.listBdcGg(gzlslid);
        BdcGgDO bdcGgDO = new BdcGgDTO();
        if (CollectionUtils.isEmpty(bdcGgDOList)) {
            bdcGgDO.setGzlslid(gzlslid);
//            bdcGgDO = bdcGgFeignService.inserBdcGg(bdcGgDO);
        } else {
            bdcGgDO = bdcGgDOList.get(0);
        }
        //获取项目表坐落数据
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        String zl = "";
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            zl = StringToolUtils.resolveBeanToAppendStr(bdcXmDOList, "getZl", CommonConstantUtils.ZF_YW_DH);
        }
        BeanUtils.copyProperties(bdcGgDO, bdcGgDTO);
        bdcGgDTO.setZl(zl);
        return bdcGgDTO;
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新公告信息
     * @date : 2021/7/22 9:30
     */
    @PostMapping("")
    public Object saveBdcGg(@RequestBody String json) {
        if (StringUtils.isBlank(json)) {
            throw new AppException("更新公告信息缺失数据");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        String ggid = jsonObject.getString("ggid");
        if (StringUtils.isBlank(ggid)) {
            BdcGgDO bdcGgDO = JSON.parseObject(json, BdcGgDO.class);
            return bdcGgFeignService.inserBdcGg(bdcGgDO);
        }
        return bdcEntityFeignService.updateByJsonEntity(json, BdcGgDO.class.getName());
    }
}
