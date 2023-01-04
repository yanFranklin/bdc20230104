package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXtGgDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtGgFeignService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcXtGgVO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.3, 2019/1/29
 * @description 不动产公告配置
 */
@RestController
@RequestMapping("/rest/v1.0/xtgg")
public class BdcXtGgController extends BaseController {
    /**
     * 公告配置服务
     */
    @Autowired
    BdcXtGgFeignService bdcXtGgFeignService;

    /**
     * @param pageable 分页信息
     * @param bdcXtGgDO 公告查询参数
     * @return 公告配置分页数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取公告配置分页数据
     */
    @GetMapping("/page")
    public Object listBdcXtGgByPage(@LayuiPageable Pageable pageable, BdcXtGgDO bdcXtGgDO) {
        String bdcXtGgJson = JSON.toJSONString(bdcXtGgDO);
        Page<BdcXtGgVO> bdcXtGgVOPage = bdcXtGgFeignService.listBdcXtGgByPage(pageable, bdcXtGgJson);
        return super.addLayUiCode(bdcXtGgVOPage);
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 保存公告配置信息
      */
    @PutMapping("")
    public Object saveBdcXtGg(@RequestBody BdcXtGgDO bdcXtGgDO) {
        if(bdcXtGgDO==null){
            throw new AppException("保存的数据不能为空！");
        }
       return bdcXtGgFeignService.saveOrUpdateBdcXtGg(bdcXtGgDO);
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 删除公告配置信息
      */
    @DeleteMapping("")
    public Object deleteBdcXtGg(@RequestBody List<String> xtggidList) {
        if(CollectionUtils.isEmpty(xtggidList)){
            throw new AppException("需要删除的公告配置为空！");
        }
        return bdcXtGgFeignService.deleteBdcXtGg(xtggidList);
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 执行配置sql
      */
    @PostMapping("/sql")
    public Object generateXtggBySql(@RequestBody BdcXtGgDO bdcXtGgDO,@RequestParam(name = "gzlslid") String gzlslid,@RequestParam(name = "xmid") String xmid) {
        if(bdcXtGgDO==null){
            throw new AppException("需要验证sql的数据不能为空！");
        }
        return bdcXtGgFeignService.generateXtggBySql(gzlslid, xmid, bdcXtGgDO);
    }





}
