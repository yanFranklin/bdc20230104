package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmJsydlhxxGxDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 建设用地量化信息rest服务
 */
public interface BdcJsydLhxxRestService {

    /**
     * @param gzlslid 工作流实例ID
     * @return 建设用地量化信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID查询建设用地量化信息
     */
    @GetMapping("/realestate-register/rest/v1.0/jsyd/lhxx/{gzlslid}")
    List<FwJsydzrzxxDO> listJsydLhxx(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * 批量新增建设用地量化关系信息
     * @param bdcXmJsydlhxxGxDTO 不动产登记项目与建设用地量化信息关系DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/gx")
    void batchInsertJsydlhxxGx(@RequestBody BdcXmJsydlhxxGxDTO bdcXmJsydlhxxGxDTO);

    /**
     * 根据工作流实例ID删除建设用地使用权量化关系信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @DeleteMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/gx/{gzlslid}")
    void deleteJsydlhxxGxByGzlslid(@PathVariable(value="gzlslid") String gzlslid);

    /**
     * 登簿时更新当前项目（现势）和原项目（历史）的量化抵押权利状态
     * <p>登簿时流程事件触发，更新量化抵押权利状态</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/dyaqzt")
    void updateJsydLhDyaqZt(@RequestParam(name="processInsId") String processInsId);

    /**
     * 登簿时更新 量化首登 权利状态
     * <p>登簿时流程事件触发，更新量化首登权利状态</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/lhsdqlzt")
    void updateJsydLhsdqlzt(@RequestParam(name="processInsId") String processInsId);

    /**
     * 登簿时更新 抵押权变更 的量化权利状态
     * <p>抵押权变更流程，对勾选的楼幢的状态进行 -1 操作</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/dyabg/zt")
    void updateJsydLhDyaBgZt(@RequestParam(name="processInsId") String processInsId);

    /**
     * 登簿时更新 抵押权 的量化权利状态
     * <p>抵押权流程，对勾选的楼幢状态不变，其余楼幢状态进行 +1 操作</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/dya/zt")
    void updateJsydLhDyZt(@RequestParam(name="processInsId") String processInsId);

    /**
     * 登簿时更新 抵押注销流程 的量化权利状态
     * <p>抵押注销流程，同步注销所有量化抵押状态</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/dyazx/zt")
    void updateJsydLhDyaZxZt(@RequestParam(name="processInsId") String processInsId);

    /**
     * 初始化生成量化信息附记内容
     * @param processInsId 工作流实例ID
     * @return 生成的附记内容
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/initfj")
    Object initLhxxFj(@RequestParam(name="processInsId") String processInsId);

    /**
     * 重新生成附记信息，并追加量化信息附记至附记信息中
     * @param processInsId 工作流实例ID
     * @param model 量化附记表述模板
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/sclhfj")
    void generateLhFjxxAndModifyFj(@RequestParam(name="processInsId") String processInsId, @RequestParam(name="model") String model);

    /**
     * 将宗地逻辑幢信息生成PDF文件
     * <p>获取当前流程关联逻辑幢,获取逻辑幢所在宗地，获取宗地上的所有逻辑幢信息，生成PDF文件</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/sclhlzj/pdf")
    void generateLhLjzPdf(@RequestParam(name="gzlslid") String gzlslid);

    /**
     * 判断当前宗地是否存在现势抵押
     * @param lszd 隶属宗地
     * @return true: 宗地存在现势抵押； false:宗地不存在现势抵押
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/xsdya")
    boolean checkXsDyaByLszd(@RequestParam(name = "lszd") String lszd);

    /**
     * 校验未勾选的逻辑幢，是否已预告或已首登
     * @param bdcXmJsydlhxxGxDTO 不动产登记项目与建设用地量化信息关系DTO
     * @return 若已预告或者已首登，则弹出提示框提示受理人员：XX幢号已预告/已首登，不应在抵押范围内，是否确定办理
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/jsyd/lhxx/ljz/zt")
    String checkWgxLzjSfYygOrYsd(@RequestBody BdcXmJsydlhxxGxDTO bdcXmJsydlhxxGxDTO);

    /**
     * 登簿时更新 查封流程 的量化查封权利状态
     * <p>查封流程，量化查封次数已勾选的楼幢不变，未勾选的楼幢+1使其在量化查封范围内</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhcf/cf/zt")
    void updateJsydLhCfZt(@RequestParam(name="processInsId") String processInsId);

    /**
     * 登簿时更新 注销查封流程 的量化查封权利状态
     * <p>解封流程，根据历史关系自动带入查封的量化查封楼幢信息，对量化查封范围内的楼幢量化查封次数 -1 </p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/jsyd/lhcf/jf/zt")
    void updateJsydLhcfzxZt(@RequestParam(name="processInsId") String processInsId);

}
