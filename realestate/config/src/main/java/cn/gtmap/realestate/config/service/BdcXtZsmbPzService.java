package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.BdcXtZsmbPzDO;
import cn.gtmap.realestate.common.core.qo.config.BdcXtZsmbPzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/17
 * @description 业务配置系统：证书模板配置处理Service接口定义
 */
public interface BdcXtZsmbPzService {
    /**
     * @param pageable      分页对象
     * @param bdcXtZsmbPzQO 查询条件
     * @return {Page} 证书模板配置分页数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询证书模板配置数据列表
     */
    Page<BdcXtZsmbPzDO> listBdcXtZsmbPz(Pageable pageable, BdcXtZsmbPzQO bdcXtZsmbPzQO);

    /**
     * @param mbsql 证书模板配置SQL
     * @return {Boolean} 正确：true，不正确：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 校验证书模板配置SQL是否正确
     */
    Boolean checkBdcXtZsmbPzSql(String mbsql);
    /**
     * @param mbsql 证书模板配置SQL
     * @param csmc 参数名称
     * @param csz 参数值
     * @return {String} sql执行结果
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @description 校验证书模板配置SQL是否正确
     */
    List<String> checkBdcXtZsmbPzSqlcs(String mbsql, String csmc,String csz);

    /**
     * @param bdcXtZsmbPzDO 证书模板配置实体
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存证书模板配置配置
     */
    int saveBdcXtZsmbPz(BdcXtZsmbPzDO bdcXtZsmbPzDO);

    /**
     * @param bdcXtZsmbPzDOList 证书模板配置实体集合
     * @return {int} 操作数据记录数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 删除证书模板配置
     */
    int deleteBdcXtZsmbPz(List<BdcXtZsmbPzDO> bdcXtZsmbPzDOList);

    /**
     * 根据权利类型查询,主要用于导入时判断是否存在
     *
     * @param qllx
     * @return BdcXtZsmbPzDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:22 2020/8/7
     */
    BdcXtZsmbPzDO queryBdcXtZsmbPzByQllx(Integer qllx);

    /**
     * 根据zsmbid查询，主要用于导出时，循环查询
     *
     * @param zsmbid
     * @return queryBdcXtZsmbPz
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 10:51 2020/8/7
     */
    BdcXtZsmbPzDO queryBdcXtZsmbPzByZsmbid(String zsmbid);

}
