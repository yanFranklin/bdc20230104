package cn.gtmap.realestate.inquiry.ui.web.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzQO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzGdZgzFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0 2020/7/7
 * @description 固定子规则控制层
 */
@RestController
@RequestMapping("/gdzgz")
public class BdcGzGdZgzController extends BaseController {

    @Autowired
    BdcGzGdZgzFeignService bdcGzGdZgzFeignService;

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcGzZgzQO bdcGzZgzQO
     * @return
     * @description 获取固定子规则列表（不分页）
     */
    @GetMapping
    public Object listBdcGdZgz(BdcGzZgzQO bdcGzZgzQO){
        String zgzParamJson = JSON.toJSONString(bdcGzZgzQO);
        List<BdcGzZgzDO> bdcGzZgzDOList = bdcGzGdZgzFeignService.listBdcGzGdZgz(zgzParamJson);
        return addLayUiCodeWithOutPage(bdcGzZgzDOList);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcGzZgzDTO bdcGzZgzDTO
     * @return
     * @description 保存固定子规则信息
     */
    @PostMapping(value = "/saveBdcGzGdZgz")
    public String saveBdcGdZgz(@RequestBody BdcGzZgzDTO bdcGzZgzDTO){
        return bdcGzGdZgzFeignService.saveBdcGzGdZgz(bdcGzZgzDTO);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param bdcGzZgzDOS bdcGzZgzDOS
     * @description 删除固定子规则信息
     */
    @DeleteMapping
    public void deteleBdcGdZgz(@RequestBody List<BdcGzZgzDO> bdcGzZgzDOS){
        bdcGzGdZgzFeignService.deleteBdcGzGdZgz(bdcGzZgzDOS);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param gzid gzid
     * @return 固定子规则信息
     */
    @GetMapping("/queryBdcGzGdZgzDTOByGzid")
    public BdcGzZgzDTO queryBdcGzGdZgzDTOByGzid(String gzid) {
        return bdcGzGdZgzFeignService.queryBdcGzGdZgzDTO(gzid);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param objectList objectList
     * @return Object
     * @description 添加状态码
     */
    public Object addLayUiCodeWithOutPage(List<BdcGzZgzDO> objectList) {
        Map map = new HashMap();
        if (objectList != null) {
            map.put("data", objectList);
            if (map != null) {
                map.put("msg", "成功");
                map.put("count", objectList.size());
            }
            if (map == null) {
                map = new HashMap(1);
                map.put("msg", "无数据");
            }
            map.put("code", 0);
        }
        return map;
    }

}
