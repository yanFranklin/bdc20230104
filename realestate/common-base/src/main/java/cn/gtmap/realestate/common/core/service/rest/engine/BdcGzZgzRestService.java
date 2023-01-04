package cn.gtmap.realestate.common.core.service.rest.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.dto.engine.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/5 14:23
 * @description 子规则接口服务
 */
public interface BdcGzZgzRestService {

     /**
      * 分页获取子规则数据列表
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
      * @param pageable pageable
      * @param zgzParamJson zgzParamJson
      * @return BdcGzZgzDO
      */
     @GetMapping("/realestate-engine/rest/v1.0/zgz/page")
    Page<BdcGzZgzDO> listBdcGzZgzPage(Pageable pageable,
                                      @RequestParam(name = "zgzParamJson",required = false) String zgzParamJson);

      /**
       * 删除子规则
       * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
       * @param bdcGzZgzDOList bdcGzZgzDOList
       */
     @DeleteMapping("/realestate-engine/rest/v1.0/zgz")
     void deleteBdcGzZgz(@RequestBody List<BdcGzZgzDO> bdcGzZgzDOList);

     /**
      *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
      *@param zhid
      *@return bdcGzZgzDTOList
      *@description 通过zhid获取子规则DTO列表
      */
     @GetMapping("/realestate-engine/rest/v1.0/zgz/list/{zhid}")
     List<BdcGzZgzDTO> queryBdcGzZgzDTOList(@PathVariable("zhid") String zhid);

     /**
      *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
      *@param gzid
      *@return bdcGzZgzDTO
      *@description 通过gzid获取子规则DTO
      */
     @GetMapping("/realestate-engine/rest/v1.0/zgz/{gzid}")
    BdcGzZgzDTO queryBdcGzZgzDTO(@PathVariable("gzid") String gzid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO  子规则DTO
     * @return {String} 子规则主键ID
     * @description  保存不动产子规则信息
     */
    @PostMapping("/realestate-engine/rest/v1.0/zgz")
    String saveBdcGzZgz(@RequestBody BdcGzZgzDTO bdcGzZgzDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzsDTO  子规则DTO
     * @return {List} 规则提示信息
     * @description  获取多个子规则校验结果信息（如果校验通过无需提示则返回空）
     */
    @PostMapping(value = "/realestate-engine/rest/v1.0/zgz/plyz")
    List<BdcGzZgzTsxxDTO> getBdcZgzCheckResult(@RequestBody BdcGzZgzsDTO bdcGzZgzsDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzDTO 验证参数信息
     * @description 获取子规则在不同参数条件下执行得到的数据流结果
     */
    @PostMapping("/realestate-engine/rest/v1.0/zgz/sjljg")
    List<BdcGzZgzTsxxDTO> getZgzSjlResult(@RequestBody BdcGzYzDTO bdcGzYzDTO);
    
    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: 子规则ID
     * @return: ｛String｝复制后新的子规则ID
     * @description 复制当前子规则ID的信息，并创建一个新的子规则
     */
    @PostMapping("/realestate-engine/rest/v1.0/zgz/{gzid}/fzxgz")
    public String copyBdcGzZgz(@PathVariable("gzid") String gzid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzCodeDTO  校验代码信息
     * @description  校验子规则动态代码
     */
    @PostMapping("/realestate-engine/rest/v1.0/zgz/code/check")
    String codeCheck(@RequestBody BdcGzCodeDTO bdcGzCodeDTO);
}
