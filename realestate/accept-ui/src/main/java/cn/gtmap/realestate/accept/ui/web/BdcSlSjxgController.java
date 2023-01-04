package cn.gtmap.realestate.accept.ui.web;


import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjxgDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjxgFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSjxgVO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @param
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @description 数据修改页面交互层
 * @date : 2022/12/1
 */
@Controller
@Validated
@RequestMapping("/slsjxg")
public class BdcSlSjxgController extends BaseController {

    @Autowired
    BdcSlSjxgFeignService sjxgFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    /**
     * @param processInsId
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 获取数据修改申请信息
     * @date : 2022/12/1
     */
    @GetMapping("/query")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BdcSlSjxgVO getBdcSlSjxg(@RequestParam(name = "processInsId") String processInsId) {
        BdcSlSjxgVO bdcSlSjxgVO = new BdcSlSjxgVO();
        if (StringUtils.isBlank(processInsId)) {
            return bdcSlSjxgVO;
        }
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(processInsId);
        if (bdcSlJbxxDO != null) {
            bdcSlSjxgVO.setSlbh(bdcSlJbxxDO.getSlbh());
        }
        BdcSlSjxgDO bdcSlSjxgDO = sjxgFeignService.querySlSjxgDO(processInsId);
        if (bdcSlSjxgDO != null) {
            BeanUtils.copyProperties(bdcSlSjxgDO, bdcSlSjxgVO);
        }
        return bdcSlSjxgVO;
    }


    /**
     * @param json
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 保存数据修改申请信息
     * @date : 2022/12/1
     */
    @PutMapping("/save")
    @ResponseBody
    public void saveSlSjxg(@RequestBody String json) throws Exception {
        if (StringUtils.isBlank(json)) {
            throw new AppException("保存数据修改信息缺失数据");
        }
        BdcSlSjxgDO bdcSlSjxgDO = JSON.parseObject(json, BdcSlSjxgDO.class);
        if (bdcSlSjxgDO != null) {
            sjxgFeignService.saveSlSjxg(bdcSlSjxgDO);
        }
    }
}
