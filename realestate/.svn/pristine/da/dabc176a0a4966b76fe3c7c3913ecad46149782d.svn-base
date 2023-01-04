package cn.gtmap.realestate.accept.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlYqFjxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlYqFjxxFeignService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1.0/yq")
@Api(tags = "不动产云签相关服务接口")
public class BdcYqController {

    @Autowired
    BdcSlYqFjxxFeignService bdcSlYqFjxxFeignService;

    /**
     * 重复推送验证
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @return 云签结果
     */
    @PostMapping("/tsyz")
    public Object tsyq(@RequestParam(value="gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        BdcSlYqFjxxDO bdcSlYqFjxxDO = new BdcSlYqFjxxDO();
        bdcSlYqFjxxDO.setGzlslid(gzlslid);
        return this.bdcSlYqFjxxFeignService.listBdcSlYqFjxx(bdcSlYqFjxxDO);
    }

    /**
     * 删除云签附件信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     */
    @GetMapping("/sc/yqfjxx/{gzlslid}")
    public void scYqFjxx(@PathVariable(value="gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        this.bdcSlYqFjxxFeignService.deleteBdcSlYqFjxxByGzlslid(gzlslid);
    }

}
