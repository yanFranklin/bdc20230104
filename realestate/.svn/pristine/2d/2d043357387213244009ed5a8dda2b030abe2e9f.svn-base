package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYjxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlYjxxQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @description 不动产受理邮寄信息对外REST接口
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/12.
 */
public interface BdcSlYjxxRestService {

    /**
     * 根据工作流实例ID查询邮寄信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @return List<BdcSlYjxxDTO> 不动产受理邮寄信息集合
     */
    @GetMapping("/realestate-accept/rest/v1.0/yjxx/{gzlslid}")
    List<BdcSlYjxxDTO> queryBdcSlYjxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * 查询邮寄信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param bdcSlYjxxQO 查询参数
     * @return List<BdcSlYjxxDTO> 不动产受理邮寄信息集合
     */
    @PostMapping("/realestate-accept/rest/v1.0/yjxx/list")
    List<BdcSlYjxxDO> listBdcSlYjxx(@RequestBody BdcSlYjxxQO bdcSlYjxxQO);

    /**
     * 保存不动产受理邮寄信息
     * <p> 保存邮寄内容信息，当邮寄信息ID为｛@code null｝调用新增方法。
     * 邮寄信息ID不为空时，调用邮寄信息更新方法(根据邮件信息ID更新不动产受理邮寄信息，不更新字段为空和｛@code null｝的值)
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlYjxxDTO 不动产受理邮寄信息DTO
     * @return String 邮寄信息ID
     */
    @PostMapping("/realestate-accept/rest/v1.0/yjxx")
    String saveBdcSlYjxx(@RequestBody BdcSlYjxxDTO bdcSlYjxxDTO);

    /**
     * 根据工作流实例ID删除邮寄信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流程实例ID
     * @return void 无返回值
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/yjxx/{gzlslid}")
    void deleteBdcSlYjxxByGzlslid(@PathVariable(value = "gzlslid")String gzlslid);

    /**
     * 初始化不动产邮寄信息
     * <p> 初始化不动产邮寄信息，当收件人不为空时，通过工作流实例ID获取权利人信息，多个权利人时，默认采用第一权利人。
     *
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @return BdcSlYjxxDTO 不动产邮寄信息DTO
     */
    @GetMapping("/realestate-accept/rest/v1.0/yjxx/init/{gzlslid}")
    BdcSlYjxxDTO initBdcSlYjxx(@PathVariable(value = "gzlslid")String gzlslid);

    /**
     * 更新实体部分属性数据值
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param json, className  实体数据JSON ，对应实体类限定名
     * @return int 更新数量
     */
    @PutMapping("/realestate-accept/rest/v1.0/yjxx/entity")
    int updateByJsonEntity(@RequestBody String json);

    /**
     * 推送邮寄信息数据至EMS
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 推送结果
     */
    @GetMapping("/realestate-accept/rest/v1.0/yjxx/tsyjxx")
    String pushYjxxToEms(@RequestParam(name="processInsId") String processInsId, @RequestParam(value = "currentUserName",required = false) String currentUserName);

}
