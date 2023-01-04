package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.domain.BdcFphDO;
import cn.gtmap.realestate.common.core.domain.BdcFphSymxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcFphSymxDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcFphQO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcFphVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-06
 * @description
 */
public interface BdcXtFphRestService {
    /**
     * @param pageable     分页对象
     * @param fphParamJson 查询条件
     * @return {Page} 发票号配置分页数据
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询发票号配置数据列表
     */
    @GetMapping("/realestate-config/rest/v1.0/fph")
    Page<BdcFphVO> listBdcFph(Pageable pageable,
                              @RequestParam(name = "fphParamJson", required = false) String fphParamJson);

    /**
     * @param bdcFphQO 发票号模板
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 生成发票号
     */
    @PostMapping("/realestate-config/rest/v1.0/fph")
    int generateBdcFph(@RequestBody BdcFphQO bdcFphQO);

    /**
     * @param bdcFphDO 发票号配置实体
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存发票号配置配置
     */
    @PutMapping("/realestate-config/rest/v1.0/fph")
    int saveBdcFph(@RequestBody BdcFphDO bdcFphDO);

    /**
     * 修改发票号信息
     * @param bdcFphDO
     */
    @PutMapping("/realestate-config/rest/v1.0/fphxx")
    void updateBdcFphxx(@RequestBody BdcFphDO bdcFphDO);

    /**
     * @param bdcFphDOList 发票号配置集合
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除发票号配置
     */
    @DeleteMapping("/realestate-config/rest/v1.0/fph")
    int deleteBdcFph(@RequestBody List<BdcFphDO> bdcFphDOList);

    /**
     * @param bdcFphSymxDO 发票号使用明细
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 作废发票号
     */
    @DeleteMapping("/realestate-config/rest/v1.0/fph/zf")
    void deleteBdcFph(@RequestBody BdcFphSymxDO bdcFphSymxDO);

    /**
     * 作废发票号
     * @param fph 发票号
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-config/rest/v1.0/fph/zf/{fph}")
    void zfBdcFph(@PathVariable(value="fph") String fph);

    /**
     * 取消发票号
     * @param fph 发票号
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-config/rest/v1.0/fph/qx/{fph}")
    void qxBdcFph(@PathVariable(value="fph") String fph);
    /**
     * @param qxdm 区县代码
     * @return {String} 领取方式
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取指定区县代码对应的发票号领取方式
     */
    @GetMapping("/realestate-config/rest/v1.0/fph/{qxdm}/lqfs")
    String getFphLqfs(@PathVariable(value = "qxdm") String qxdm);

    /**
     * @return {String} 领取方式
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取区县代码对应的发票号领取方式所有配置项
     */
    @GetMapping("/realestate-config/rest/v1.0/fph/lqfs")
    Map<String, String> getAllFphLqfs();

    /**
     * @param bdcSlSfxxDOList slbh
     * @return {List<BdcFphDO>}
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取可用发票号，并且更新使用状态和使用明细
     */
    @PostMapping("/realestate-config/rest/v1.0/fph/lqfs")
    List<BdcSlSfxxDO> getBdcFph(@RequestBody List<BdcSlSfxxDO> bdcSlSfxxDOList, @RequestParam(name = "slbh") String slbh);

    /**
     * 获取可用发票号，根据指定发票类别更新对应字段的发票号 「jspzfph 和 fssrfph」并且更新使用状态和使用明细
     *
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO>
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @PostMapping("/realestate-config/rest/v1.0/fph/lqfs/fplb")
    List<BdcSlSfxxDO> getBdcFph(@RequestBody List<BdcSlSfxxDO> bdcSlSfxxDOList, @RequestParam(name = "slbh") String slbh, @RequestParam(name = "fplb") String fplb);

    /**
     * 获取可用的发票号数量
     * @param count 需要的发票号数量
     * @return 发票号集合信息
     */
    @PostMapping("/realestate-config/rest/v1.0/fph/lqfs/fphxx")
    List<BdcFphDO> getBdcFphxx(@RequestParam(name = "count") int count);

    /**
     * @param fph 发票号
     * @return 发票号状态
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 根据发票号查询当前发票使用情况
     */
    @GetMapping("/realestate-config/rest/v1.0/fph/syqk")
    Integer getSyqk(@RequestParam(name = "fph") String fph);

    /**
     * @param bdcFphDOList 发票号信息
     * @return 发票号状态
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 根据发票号查询当前发票使用情况
     */
    @PostMapping("/realestate-config/rest/v1.0/fph/syqk")
    Integer syqkEdit(@RequestBody List<BdcFphDO> bdcFphDOList);

    /**
     * @param fph 发票号
     * @return 可领用状态
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 根据发票号查询当前是否可以领用
     */
    @GetMapping("/realestate-config/rest/v1.0/fph/yy/check")
    Boolean checkYyFph(@RequestParam(name = "fph") String fph);
    
    /**
     * @param bdcSlSfxxDO 收费信息
     * @param fph 发票号
     * @return 生成状态
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 为收费信息保存新的发票号
     */
    @PostMapping("/realestate-config/rest/v1.0/fph/save")
    Boolean saveSfxxFph(@RequestBody BdcSlSfxxDO bdcSlSfxxDO, @RequestParam(name = "slbh") String slbh, @RequestParam(name = "fph") String fph);

    /**
     * @param fphid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取发票号使用明细
     * @date : 2020/11/26 18:29
     */
    @GetMapping("/realestate-config/rest/v1.0/fph/symx/{fphid}")
    List<BdcFphSymxDTO> listFphSymx(@PathVariable(value = "fphid") String fphid);

    /**
     * @param fph
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据发票号查询发票信息
     * @date : 2021/9/16 9:06
     */
    @GetMapping("/realestate-config/rest/v1.0/fph/fpxx/{fph}")
    BdcFphDO listBdcFphxx(@PathVariable(value = "fph") String fph);
}
