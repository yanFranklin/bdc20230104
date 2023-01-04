package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.domain.BdcXtZsmbPzDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsmbSqlDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/17
 * @description 不动产系统证书模板配置服务接口
 */
public interface BdcXtZsmbPzRestService {

    /**
     * @param pageable        分页对象
     * @param zsmbpzParamJson 查询条件
     * @return {Page} 证书模板配置分页数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询证书模板配置数据列表
     */
    @GetMapping("/realestate-config/rest/v1.0/zsmbpz")
    Page<BdcXtZsmbPzDO> listBdcXtZsmbPz(Pageable pageable,
                                        @RequestParam(name = "zsmbpzParamJson", required = false) String zsmbpzParamJson);

     /**
     * @param bdcXtZsmbPzDO 证书模板配置实体
     * @return {Boolean} 正确：true，不正确：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 校验证书模板配置SQL是否正确
     */
    @PostMapping("/realestate-config/rest/v1.0/zsmbpz/sql")
    Boolean checkBdcXtZsmbPzSql(@RequestBody BdcXtZsmbPzDO bdcXtZsmbPzDO);
    /**
     * @param bdcXtZsmbSqlDTO 证书模板sql配置实体
     * @return {List<String></>} sql执行结果
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @description 校验证书模板配置SQL是否正确
     */
    @PostMapping("/realestate-config/rest/v1.0/zsmbpz/check")
    List<String> checkBdcXtZsmbPzSqlcs(@RequestBody BdcXtZsmbSqlDTO bdcXtZsmbSqlDTO);
    /**
     * @param bdcXtZsmbPzDO 证书模板配置实体
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存证书模板配置配置
     */
    @PutMapping("/realestate-config/rest/v1.0/zsmbpz")
    int saveBdcXtZsmbPz(@RequestBody BdcXtZsmbPzDO bdcXtZsmbPzDO);


    /**
     * @param bdcXtZsmbPzDOList 证书模板配置集合
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 删除证书模板配置
     */
    @DeleteMapping("/realestate-config/rest/v1.0/zsmbpz")
    int deleteBdcXtZsmbPz(@RequestBody List<BdcXtZsmbPzDO> bdcXtZsmbPzDOList);

    /**
     * 根据权利类型查询，主要用于导入时判断是否存在
     *
     * @param qllx
     * @return BdcXtZsmbPzDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:22 2020/8/7
     */
    @GetMapping("/realestate-config/rest/v1.0/zsmbpzByQllx/{qllx}")
    BdcXtZsmbPzDO queryBdcXtZsmbPzByQllx(@PathVariable("qllx") Integer qllx);

    /**
     * 根据zsmbid查询，主要用于导出时，循环查询
     *
     * @param zsmbid
     * @return queryBdcXtZsmbPz
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:51 2020/8/7
     */
    @GetMapping("/realestate-config/rest/v1.0/zsmbpzByZsmbid/{zsmbid}")
    BdcXtZsmbPzDO queryBdcXtZsmbPzByZsmbid(@PathVariable("zsmbid") String zsmbid);

}
