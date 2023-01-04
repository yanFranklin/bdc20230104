package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.dto.building.FwJsydzrzxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmJsydlhxxGxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.building.ZdJsydLhxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcJsydLhxxFeignService;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 不动产建设用地量化信息
 */
@RestController
@RequestMapping("/rest/v1.0/jsyd/lhxx")
public class BdcJsydLhxxController extends BaseController {

    @Value("${lh.check.ygsd.gzldyid:}")
    private String checkYgsdGzldyid;

    @Autowired
    BdcJsydLhxxFeignService bdcJsydLhxxFeignService;

    @Autowired
    ZdJsydLhxxFeignService zdJsydLhxxFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;


    @GetMapping(value = "/{gzlslid}")
    @ResponseStatus(HttpStatus.OK)
    public Object listJsydLhxx(@PathVariable(name = "gzlslid") String gzlslid){
        return bdcJsydLhxxFeignService.listJsydLhxx(gzlslid);
    }

    /**
     * 分页查询建设用地自然幢信息
     *
     * @param pageable 分页对象
     * @param fwJsydzrzxxDO 房屋建设用地自然幢信息
     * @return {Page} 分页查询结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/page")
    public Object listFwJsydzrzxxByPageJson(@LayuiPageable Pageable pageable, FwJsydzrzxxDO fwJsydzrzxxDO) {
        Map<String, Object> map = new HashMap<>();
        map.put("lszd", fwJsydzrzxxDO.getLszd());
        if(Objects.nonNull(fwJsydzrzxxDO.getLhdycs())){
            map.put("lhdycs", fwJsydzrzxxDO.getLhdycs());
        }
        if(Objects.nonNull(fwJsydzrzxxDO.getLhsdqlzt())){
            map.put("lhsdqlzt", fwJsydzrzxxDO.getLhsdqlzt());
        }
        Page<FwJsydzrzxxDTO> fwJsydzrzxxDTOPage = zdJsydLhxxFeignService.listFwJsydzrzxxByPageJson(pageable, JSON.toJSONString(map));
        return super.addLayUiCode(fwJsydzrzxxDTOPage);
    }

    /**
     * 根据工作流实例ID 删除建设用地量化关系
     *
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @DeleteMapping("")
    public void deleteFwJsydzrzxxGx(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("未获取到工作流实例ID");
        }
        this.bdcJsydLhxxFeignService.deleteJsydlhxxGxByGzlslid(gzlslid);
    }

    /**
     * 保存建设用地量化关系
     * @param bdcXmJsydlhxxGxDTO 不动产登记项目与建设用地量化信息关系DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("")
    public void saveFwJsydzezxxGx(@RequestBody BdcXmJsydlhxxGxDTO bdcXmJsydlhxxGxDTO){
        if(CollectionUtils.isEmpty(bdcXmJsydlhxxGxDTO.getJsydzrzxxidList())
                ||StringUtils.isBlank(bdcXmJsydlhxxGxDTO.getGzlslid())){
            throw new AppException("未获取到自然幢信息或工作流实例ID");
        }
        this.bdcJsydLhxxFeignService.batchInsertJsydlhxxGx(bdcXmJsydlhxxGxDTO);
    }

    /**
     * 获取流程初始化量化附记模板内容
     * @param gzlslid 工作流实例ID
     * @return 附记模板
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/init/lhfj")
    public Object getInitLhfj(@RequestParam(name = "gzlslid") String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少工作流实例ID.");
        }
        return this.bdcJsydLhxxFeignService.initLhxxFj(gzlslid);
    }

    /**
     * 重新生成附记信息，并追加量化信息附记至附记信息中
     * @param gzlslid 工作流实例ID
     * @param model 附记表述模板信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/sclhfj")
    public void sclhfj(@RequestParam(name="gzlslid") String gzlslid, @RequestParam(name="model") String model){
        if(StringUtils.isAnyBlank(gzlslid, model)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少工作流实例ID或附记表述信息");
        }
        this.bdcJsydLhxxFeignService.generateLhFjxxAndModifyFj(gzlslid, model);
    }

    /**
     * 打印量化逻辑幢信息，生成PDF文件
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/lhljz/pdf")
    public void generateLhLjzPdf(@RequestParam(name ="gzlslid") String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少工作流实例ID.");
        }
        this.bdcJsydLhxxFeignService.generateLhLjzPdf(gzlslid);
    }

    /**
     * 校验未勾选的逻辑幢是否已预告、已首登
     * @param bdcXmJsydlhxxGxDTO 不动产登记项目与建设用地量化信息关系DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/checkWgxLzj")
    public String checkWgxLzjSfYygOrYsd(@RequestBody BdcXmJsydlhxxGxDTO bdcXmJsydlhxxGxDTO){
        if(CollectionUtils.isEmpty(bdcXmJsydlhxxGxDTO.getJsydzrzxxidList()) ){
            throw new AppException("未获取到自然幢信息");
        }
        if(StringUtils.isNotBlank(checkYgsdGzldyid)){
            List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcXmJsydlhxxGxDTO.getGzlslid());
            if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                if(checkYgsdGzldyid.indexOf(bdcXmDTOList.get(0).getGzldyid()) > -1){
                   return this.bdcJsydLhxxFeignService.checkWgxLzjSfYygOrYsd(bdcXmJsydlhxxGxDTO);
                }
            }
        }
        return null;
    }

}
