package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcYzhFzlTjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcYzhTjQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcYzhFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/03/03
 * @description 查询子系统：印制号查询
 */
@RestController
@RequestMapping("/rest/v1.0/yzh")
public class BdcYzhCxController {

    @Autowired
    BdcYzhFeignService bdcYzhFeignService;

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcYzhTjQO 不动产印制号统计QO
     * @description  印制号废证量统计
     */
    @PostMapping("/fjltj")
    public List<BdcYzhFzlTjDTO> fjlTj(@RequestBody BdcYzhTjQO bdcYzhTjQO) {
        if(null == bdcYzhTjQO){
            throw new AppException("印制号废证量统计异常：未指定查询参数");
        }
        return bdcYzhFeignService.yzhFzlTj(bdcYzhTjQO);
    }

}
