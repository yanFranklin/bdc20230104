package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlTfxxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlTfxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/19
 * @description  不动产退费信息 Rest 接口服务
 */
public interface BdcSlTfxxRestService {

    /**
     * 分页查询不动产受理退费信息
     * @param pageable 分页参数
     * @param json 不动产受理退费信息QO
     * @return 不动产受理退费信息DQ集合
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/tfxx/page")
    Page<BdcSlTfxxDO> listBdcSlTfxxByPage(Pageable pageable, @RequestParam(value = "json", required = false) String json);

    /**
     * 查询不动产退费信息
     * @param bdcSlTfxxQO 退费信息查询参数
     * @return 不动产受理退费信息DQ集合
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/tfxx/list")
    List<BdcSlTfxxDO> listbdcSlTfxx(@RequestBody BdcSlTfxxQO bdcSlTfxxQO);

    /**
     * 根据受理编号获取收费信息，生成退费信息内容
     * @param slbh 受理编号
     * @return 退费信息集合
     */
    @GetMapping("/realestate-accept/rest/v1.0/tfxx/sc")
    List<BdcSlTfxxDO> generateTfxx(@RequestParam(value="slbh") String slbh);

    /**
     * 批量保存或新增不动产受理退费信息
     * @param bdcSlTfxxDOList 不动产受理退费信息DO
     */
    @PostMapping("/realestate-accept/rest/v1.0/tfxx/bc/pl")
    void plSaveBdcSlTfxx(@RequestBody List<BdcSlTfxxDO> bdcSlTfxxDOList);

    /**
     * 批量删除不动产退费信息
     * @param tfxxidList 退费信息ID集合
     */
    @PostMapping("/realestate-accept/rest/v1.0/tfxx/sc/pl")
    void plRemoveBdcSlTfxx(@RequestBody List<String> tfxxidList);
}
