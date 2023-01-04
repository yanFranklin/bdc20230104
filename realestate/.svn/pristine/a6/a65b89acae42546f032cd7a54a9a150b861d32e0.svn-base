package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcSlFgfService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlFgfRestService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/01/15
 * @description 不动产受理房改房rest服务
 */
@RestController
@Api(tags = "不动产受理房改房服务")
public class BdcSlFgfRestController extends BaseController implements BdcSlFgfRestService {

    @Autowired
    BdcSlFgfService bdcSlFgfService;
    @Value("${tsszfgb.djyy:}")
    private String djyy;

    @Override
    public void tsszfgb(@PathVariable(value = "processInsId")String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new NullPointerException("未获取到工作流实例id！");
        }
        this.bdcSlFgfService.tsszfgb(gzlslid);
    }

    @Override
    public String getDjyy(){
        return djyy;
    }

}
