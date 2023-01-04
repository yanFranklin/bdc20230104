package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.init.BdcDyaqDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQlxxRequestDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;

import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/1
 * @description 权利信息
 */
public interface BdcQllxService {
    /**
     * 获取权利类型table名
     * @param bdcQl
     * @return
     */
    String getTableName(BdcQl bdcQl);

    /**
     * 通过权利类型代码获取权利类型
     * @param qllx
     * @return
     */
     BdcQl makeSureQllx(String qllx);

    /**
     * 获取权利信息
     * @param bdcQl
     * @param xmid
     * @return
     */
     BdcQl queryQllxDO(BdcQl bdcQl, String xmid);

     /**
      * 获取当前流程生成的权利信息
      *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
      *@param gzlslid
      * @param slbh
      *@return List<BdcQl>
      *@description 获取当前流程生成的权利信息
      */
     List<BdcQl> listQlxxDO(String slbh,String gzlslid);


    /**
     * 获取当前流程生成的权利类型
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param gzlslid
     *@return List<String>
     *@description 获取当前流程生成的权利类型
     */
    List<String> listQllx(String gzlslid);

    /**
     * 获取当前流程需要注销的权利类型
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param gzlslid
     *@return List<String>
     *@description 获取当前流程需要注销的权利类型
     */
    List<String> listZxQllx(String gzlslid);

    /**
     * 根据项目ID获取生成的所有权利信息
     * @author  <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @param   xmidList 项目ID
     * @return  {List} 权利信息
     * @description 根据项目ID获取生成的所有权利信息
     */
    List<BdcQl> listQlxxDOByXmids(List<String> xmidList);


     /**
      * 获取当前流程需要注销的权利信息
      *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
      *@param slbh
      *@param gzlslid
      *@return List<BdcQl>
      *@description 获取当前流程需要注销的权利信息
      */
     List<BdcQl> listZxQlxxDO(String slbh,String gzlslid);


     /**
      * 获取要还原的已注销权利
      *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
      *@param slbh
      *@param gzlslid
      *@return List<BdcQl>
      *@description
      */
    List<BdcQl> listHyYzxQlxx(String slbh,String gzlslid);


    /**
     * 获取权利信息
     * @param xmid
     * @return
     */
     BdcQl queryQllxDO(String xmid);

    /**
     * @param xmid 项目ID
     * @return {BdcQl} 不动产权利
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  通过项目ID查询原权利基本信息
     */
    BdcQl queryBdcYqlxx(String xmid);

    /**
     * @param xmid 项目ID
     * @return {BdcQl} 不动产权利
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  通过项目ID查询原权利基本信息
     */
    List<BdcQl> queryBdcYqlxxs(String xmids);



    /**
     * 修改权利的接口
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcQl  权利对象
     *@return 更新数量
     *@description
     */
    int updateBdcQl(BdcQl bdcQl);

    /**
     * 新增权利的接口
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcQl  权利对象
     *@return 插入数量
     *@description
     */
    int insertBdcQl(BdcQl bdcQl);


    /**
     * 新增或修改权利的接口
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcQl  权利对象
     *@return
     *@description
     */
     void insertOrUpdateBdcQl(BdcQl bdcQl);

    /**
     * 修改某实体对象信息的接口(特殊方法自用,用前要注意)
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param obj  实体对象
     *@param id  id
     *@return  更新数量
     *@description
     */
    int updateObj(Object obj,String id);

    /**
     * 根据map条件查询 bdcQl
     *
     * @param bdcQl
     * @param map
     * @param orderBy 排序方式
     * @return
     */
    List<BdcQl> andEqualQueryBdcQl(BdcQl bdcQl, Map<String, Object> map, String orderBy);

    /**
     * 更新权利附记内容
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xmid  项目id
     *@param fjnr  附记内容
     *@description
     */
    void updateQlFj(String xmid,String fjnr);

    /**
     * 批量更新不动产权利
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return  更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量更新不动产权利
     */
    int updateBatchBdcQl(BdcDjxxUpdateQO bdcDjxxUpdateQO) throws ClassNotFoundException;

    /**
     * 更新同一个证书相关权利的附记
     * @param bdcQl 权利信息
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新同一个证书相关权利的附记
     */
    int updateBdcZsQlFj(BdcQl bdcQl);

    /**
     * 根据json字符串更新同一个证书相关权利的附记
     * @param json 更新的json 字符串
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据json字符串更新同一个证书相关权利的附记
     */
    int updateBdcZsQlFj(String json);

    /**
     * 根据xmid获取规划用途 先取权力表 再取xm表
     * @param xmid
     * @return
     */
    String getGhytByXmid(String xmid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQl 权利信息
     * @return {FwHsDO} 房屋户室信息
     * @description 查询指定权利不动产单元对应房屋户室信息
     */
    FwHsDO queryFwHsXx(BdcQl bdcQl);

    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 同步抵押权的bdbzzqse到zgzqse字段
     */
    void updateBdcDyaqZgzqse(String processInsId);

    /**
     * @param xmids
     * @return {List} 限制信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 根据不动产单元号获取限制信息
     */
    List<BdcQl> listXzxxByXmids( List<String> xmids);

    /**
     * @param bdcdyhs 项目ID
     * @return {List} 限制信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 根据不动产单元号获取限制信息
     */
    List<BdcQl> listXzxxByBdcdyhs(List<String> bdcdyhs);

    /**
     * 更新在建建筑物抵押范围
     * 土地证坐落+所有不动产单元的房间号（顿号分隔）+房屋及对应的建设用地使用权
     *
     * @param bdcQlList bdcQlList
     * @param gzldyid   gzldyid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    int updateZjjwdyfw(List<BdcQl> bdcQlList, String gzldyid,String qjgldm);

    Map queryFdcqByZl(String zl);

    /**
     * 查询权利人权利信息
     * @param bdcQlxx 查询参数
     * @return List<BdcQl> 权利信息
     */
    List<BdcQl> getBdcQlxxByQlr(BdcQlxxRequestDTO bdcQlxx);

}
