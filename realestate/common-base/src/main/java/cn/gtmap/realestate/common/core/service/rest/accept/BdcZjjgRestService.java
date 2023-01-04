package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlZjjgDO;
import cn.gtmap.realestate.common.core.dto.accept.YthZjjgDto;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlZjjgQO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/22
 * @description
 */
public interface BdcZjjgRestService {

    /**
     * @param gzlslid 工作流实例ID
     * @return 资金监管信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取资金监管信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/zjjg/{gzlslid}")
    List<BdcSlZjjgDO> listBdcSlZjjg(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param xmid 受理项目ID
     * @return 资金监管信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据受理项目ID获取资金监管信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/zjjg/xmid/{xmid}")
    List<BdcSlZjjgDO> listBdcSlZjjgByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param ywlx 业务类型
     * @return 工作流定义ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取工作流定义ID集合
     */
    @GetMapping("/realestate-accept/rest/v1.0/zjjg/fdjywlc")
    List<String> getFdjywlcDyidList(@RequestParam(value = "ywlx", required = false) String ywlx);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询资金监管信息
     * @date : 2021/7/22 18:35
     */
    @PostMapping("/realestate-accept/rest/v1.0/zjjg")
    List<BdcSlZjjgDO> listBdcSlZjjg(@RequestBody BdcSlZjjgQO bdcSlZjjgQO);

    /**
     * 更新资金监管信息状态为已撤销
     * <p>资金监管撤销流程办结事件调用，更新资金监管信息状态为：已撤销</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/zjjg/zt/ycx")
    void updateZjjgZtYcx(@RequestParam(value = "processInsId") String processInsId);

    /**
     * 更新项目附表是否资金监管为是
     * <p>资金监管流程办结事件调用，更新项目附表是否资金监管为：是</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/zjjg/sfzjjg")
    void updateZjjgXmfbSfzjjg(@RequestParam(value = "processInsId") String processInsId);

    /**
     * 更新项目附表是否资金监管为否
     * <p>资金监管撤销流程办结事件调用，更新项目附表是否资金监管为：否</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/zjjgcx/sfzjjg")
    void updateZjjgCxXmfbSfzjjg(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @param xmid
     * @param htbh
     * @author wangyinghao
     * @description 验证是否资金监管
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/yth/zjjg/ythpt/{yxmid}/{htbh}")
    String sfzjjgYthpt(@PathVariable(name = "yxmid",required = false) String yxmid,
                       @PathVariable(name = "htbh") String htbh);

    /**
     * @param
     * @author wangyinghao
     * @description 查询一体化平台资金监管信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/yth/zjjg")
    YthZjjgDto listYthSlZjjg(@RequestParam(value = "processInsId") String processInsId,
                             @RequestParam(value = "htbh") String htbh);

    /**
     * 获取文件夹下的文件的storage地址
     * @param gzlslid 工作流实例ID
     * @param wjjmc 文件夹名称
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 文件信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/yth/zjjgfj")
    public Object getFileStorageUrl(@RequestParam("htbh") String htbh);
}
