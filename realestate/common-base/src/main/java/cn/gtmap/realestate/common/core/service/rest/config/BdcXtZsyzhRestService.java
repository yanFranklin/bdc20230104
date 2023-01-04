package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcReturnData;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcYzhVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/1/30
 * @description 不动产系统证书印制号配置服务接口
 */
public interface BdcXtZsyzhRestService {
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  pageable 分页对象
     * @param  zsyzhParamJson 查询条件
     * @return {Page} 证书印制号配置分页数据
     * @description  查询证书印制号配置数据列表
     */
    @GetMapping("/realestate-config/rest/v1.0/zsyzh")
    Page<BdcYzhDO> listBdcZsyzh(Pageable pageable,
                                @RequestParam(name = "zsyzhParamJson", required = false) String zsyzhParamJson);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcYzhVO 证书印制号模板
     * @return {int} 操作数据记录数
     * @description  生成证书印制号
     */
    @PostMapping("/realestate-config/rest/v1.0/zsyzh")
    int generateBdcZsyzh(@RequestBody BdcYzhVO bdcYzhVO);

    /***
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 提取（提取到部门）、领取（领取到人）、撤回印制号（撤回到提取之前）
    */
    @PostMapping("/realestate-config/rest/v1.0/extractData")
    BdcReturnData extractData(@RequestBody BdcYzhVO bdcYzhVO);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcYzhDO 证书印制号配置实体
     * @return {int} 操作数据记录数
     * @description  保存证书印制号配置配置
     */
    @PutMapping("/realestate-config/rest/v1.0/zsyzh")
    int saveBdcZsyzh(@RequestBody BdcYzhDO bdcYzhDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsbhmbDOList 证书印制号配置集合
     * @return {int} 操作数据记录数
     * @description 删除证书印制号配置
     */
    @DeleteMapping("/realestate-config/rest/v1.0/zsyzh")
    int deleteBdcZsyzh(@RequestBody List<BdcYzhDO> bdcYzhDOList);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcYzhsymxDO 证书印制号使用明细
     * @return {int} 操作数据记录数
     * @description  作废证书印制号
     */
    @DeleteMapping("/realestate-config/rest/v1.0/zsyzh/zf")
    void deleteBdcZsyzh(@RequestBody BdcYzhsymxDO bdcYzhsymxDO);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcYzhsymxDO 证书印制号使用明细
     * @return {int} 操作数据记录数
     * @description  作废证书印制号
     */
    @PostMapping("/realestate-config/rest/v1.0/zsyzh/hy")
    void revertBdcZsyzh(@RequestBody BdcYzhsymxDO bdcYzhsymxDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  qxdm 区县代码
     * @return {String} 领取方式
     * @description 获取指定区县代码对应的印制号领取方式
     */
    @GetMapping("/realestate-config/rest/v1.0/zsyzh/{qxdm}/lqfs")
    String getZsyzhLqfs(@PathVariable(value = "qxdm", required = true) String qxdm);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 领取方式
     * @description 获取区县代码对应的印制号领取方式所有配置项
     */
    @GetMapping("/realestate-config/rest/v1.0/zsyzh/lqfs")
    Map<String, String> getAllZsyzhLqfs();

}
