package cn.gtmap.realestate.register.service;


import cn.gtmap.realestate.common.core.domain.BdcDldmSyqxGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmZsDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.qo.BdcXmGxQO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 项目信息业务服务类
 */
public interface BdcXmxxService {
    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新登簿信息
     */
    void updateBdcXmDbxx(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    void updateBdcXmDbxxAndQsztByGzlslid(DbxxQO dbxxQO);

    /**
     * @param xmid 项目ID
     * @return List<BdcXmLsgxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据xmid查询List<BdcXmLsgxDO>
     */
    List<BdcXmLsgxDO> getBdcXmLsgxByXmid(String xmid);

    /**
     * @param xmid  项目ID
     * @param zxyql 注销原权利（1:是，0：否）
     * @return List<BdcXmLsgxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据xmid和zxyql字段查询List<BdcXmLsgxDO>
     */
    List<BdcXmLsgxDO> getBdcXmLsgxByXmidAndZxyql(String xmid, Integer zxyql);

    /**
     * @param gzlslid 工作流实例ID
     * @return List<BdcXmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据工作流实例ID获取项目
     */
    List<BdcXmDO> getListBdcXmByGzlslid(String gzlslid);

    /**
     * @param xmid 项目ID
     * @param qszt 权属状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目的权属状态
     */
    int updateBdcXmQszt(String xmid, Integer qszt);

    /**
     * @param xmid 项目ID
     * @return BdcDldmSyqxGxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前项目土地的使用期限
     */
    BdcDldmSyqxGxDO queryDldmSyqxGx(String xmid);

    /**
     * @param bdcRyzdDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 项目更新权利人冗余字段
     */
    void updateBdcXmQlrxx(BdcRyzdDTO bdcRyzdDTO);

    /**
     * @param bdcRyzdDTO 冗余字段对象DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新项目表中的权利人信息（权利人相同）
     */
    int updateBdcXmQlrxxPl(BdcRyzdDTO bdcRyzdDTO, String gzlslid);

    /**
     * @param dbxxQO 登簿信息查询QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原项目的权属状态
     */
    int updateYxmQsztByGzlslid(DbxxQO dbxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcRyzdDTO 冗余字段信息
     * @description 更新项目表证号
     */
    int updateBdcXmBdcqzh(BdcRyzdDTO bdcRyzdDTO);

    /**
     * @param dbxxQO 登簿信息QO
     * @param qllx   权利类型
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据不同的权利更新项目的登簿信息和权属状态
     */
    int updateBdcQlXmDbxxAndQsztPl(DbxxQO dbxxQO, String qllx);

    /**
     * @param dbxxQO 登簿信息QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新预抵押项目的登簿信息和权属状态
     */
    int updateYdyaXmDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param fwBdcXmDO 房屋不动产项目
     * @param tdqllx    需要查询的土地的权利类型
     * @return BdcXmDO  查询到的土地的项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 已知单元号的房屋产权信息，查询同单元号的土地项目信息
     */
    BdcXmDO queryFwTdXm(BdcXmDO fwBdcXmDO, Integer tdqllx);

    /**
     * @param dbxxQO   登簿信息
     * @param qllxList 权利信息
     * @param isDb 标识是登簿还是退回（true 表示登簿，false表示退回）
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿，更新项目和权利信息（因为同类中调用其它事务方法会失效，所以讲该方法写在该接口中）
     */
    void updateBdcXmAndQlXx(DbxxQO dbxxQO, List<String> qllxList, boolean isDb);

    /**
     * @param gzlslid 工作流实例ID
     * @return List<String> 登记小类List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程的登记小类
     */
    String[] getListDjxlByGzlslid(String gzlslid);

    /**
     * @param xmid 项目主键
     * @return BdcXmDO 项目对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键项目ID获取项目信息
     */
    BdcXmDO getBdcXmByXmid(String xmid);

    /**
     * 通过上一手的证书信息查询现不动产项目信息
     * <p>不动产项目表，不动产历史关系表，不动产项目证书关系表三表关联查询</p>
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [gzlslid, zsid, qllx] 工作流实例ID， 证书ID（上一手项目的证书ID） 权利类型
     * @return: List<BdcXmDO> 不动产项目集合信息
     */
    List<BdcXmDO> getBdcXmByGzlslidAndZsid(String gzlslid, String zsid, Integer qllx);

    /**
     * @param gzlslid 工作流实例ID
     * @param qszt    项目的权属状态
     * @param ajzt    项目的案件状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目的权属状态和案件状态
     */
    int updateBdcXmQsztAndAjztPl(String gzlslid, Integer qszt, Integer ajzt);

    /**
     * @param bdcXmGxQO 查询QO
     * @return List<BdcXmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询原项目信息
     */
    List<BdcXmDO> getListYbdcXm(BdcXmGxQO bdcXmGxQO);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取一个项目
      */
    BdcXmXmfbDTO getOnlyOneXm(String gzlslid,String xmid);

    /**
     *
     * @param xmidList
     * @param zszt
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 更新项目附表证书形态
     */
    void updateBdcXmfbZsxt(List<String> xmidList, Integer zszt);

    /**
     *
     * @param gzlslid
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 根据工作流实例id更新案件状态为完结
     */
    void updateAjztWjByGzlslid(String gzlslid);

    /**
     *
     * @param gzlslid
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 查询证书
     */
    List<BdcXmZsDTO> queryZsByGzlslid(String gzlslid);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新项目表登簿信息
     */
    void updateBdcXmxxDbxxPl(DbxxQO dbxxQO);

}
