package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmZsDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.dto.BdcWlzxcqXzqlDTO;
import cn.gtmap.realestate.register.core.qo.BdcWlzxcqXzqlQO;
import cn.gtmap.realestate.register.core.qo.BdcXmGxQO;
import cn.gtmap.realestate.register.core.qo.BdcXmfbZsxtQO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.sdk.mybatis.plugin.annotation.Crypt;
import cn.gtmap.sdk.mybatis.plugin.annotation.VersionEncryptKeys;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/6
 * @description 不动产项目
 */
@Repository
public interface BdcXmMapper {
    /**
     * @param dbxxQO 项目对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据项目ID更新项目的登簿信息字段
     */
    int updateBdcXmDbxxByXmid(DbxxQO dbxxQO);

    /**
     * @param bdcXmDO 项目对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据项目ID更新项目的权属状态字段
     */
    int updateBdcXmQsztByXmid(BdcXmDO bdcXmDO);

    /**
     * @param dbxxQO 项目对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据项目ID更新项目的登簿信息和权属状态字段
     */
    int updateBdcXmDbxxAndQsztByXmid(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据工作流实例ID更新当前所有项目的登簿信息和权属状态
     */
    int updateBdcXmDbxxAndQsztByGzlslid(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新当前需要注销的原项目的权属状态
     */
    int updateYxmQsztByGzlslid(DbxxQO dbxxQO);

    /**
     * @param bdcRyzdDTO 冗余字段DTO
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新项目中的权利人信息
     */
    int updateBdcXmQlrxxPl(BdcRyzdDTO bdcRyzdDTO);

    /**
     * @param bdcXmQO 项目查询QO
     * @return List<String> 不动产单元号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程办理的不动产单元号
     */
    List<String> queryLcBdcdyh(BdcXmQO bdcXmQO);

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param bdcRyzdDTO 冗余字段DTO
     * @description 更新项目的权利人信息
     */
    int updateBdcXmQlrxx(BdcRyzdDTO bdcRyzdDTO);

    /**
     * @param dbxxQO 登簿信息QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据不同的权利更新项目的登簿信息和权属状态
     */
    int updateBdcQlXmDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param gzlslid 工作流实例id
     * @param djsj 登薄时间
     * @param tableName 权利表名
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 更新不同历史权利的登记时间
     */
    int updateBdcLsQlDjsjPl(@Param("djsj")Date djsj,@Param("gzlslid")String gzlslid,@Param("tableName")String tableName);

    /**
     * @param gzlslid 工作流实例id
     * @param qllx 权利类型
     * @param djsj 登薄时间
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 更新不同历史权利项目的登记时间
     */
    int updateBdcLsQlXmDjsjPl(@Param("djsj")Date djsj,@Param("gzlslid")String gzlslid,@Param("qllx")Integer qllx);

    /**
     * @param dbxxQO 登簿信息QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新预抵押项目的登簿信息和权属状态
     */
    int updateYdyaXmDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param bdcXmParam
     * @return 项目集合
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询项目信息
     */
    List<BdcXmDO> listXmByPage(HashMap bdcXmParam);

    /**
     * 查询最大的虚拟不动产单元号
     *
     * @param bdcdyhPrefix 虚拟不动产单元号前缀
     * @return 最大的虚拟不动产单元号
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Crypt()
    String queryMaxXnBdcdyh(@Crypt @Param("bdcdyhPrefix") String bdcdyhPrefix);
    /**
     * 查看不动产项目信息
     * <p>不动产项目表，不动产历史关系表，不动产项目证书关系表
     * 三表关联通过原项目的证书ID信息查询现有项目的项目信息</p>
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [param] 工作流实例ID,证书ID,权利类型
     * @return: 不动产项目信息集合
     */
    @Crypt(
            encryptKeys = "gzlslid",
            versionEnCryptKeys = "{\"hefei\": \"zsid\"}",
            encryptKey = {
                @VersionEncryptKeys(version = "standard",encryptKeys = {"zsid"}),
                @VersionEncryptKeys(version = "hefei",encryptKeys = {"qllx"})}
    )
    List<BdcXmDO> queryXmByGzlslidAndZsid(HashMap param);

    /**
     * @param paramMap 更新参数
     * @return int 更新的结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    int updateBdcXmQsztAndAjztPl(Map paramMap);

    /**
     * @param bdcXmGxQO 项目查询关系QO
     * @return List<BdcXmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询原项目信息
     */
    List<BdcXmDO> listYbdcXm(BdcXmGxQO bdcXmGxQO);

    /**
     * 查询产权上现势抵押
     * @param gzlslid  工作流实例id
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcDyaqDO> queryYdyaq(@Param("gzlslid") String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取一个项目
     */
    BdcXmXmfbDTO getOnlyOneXmBfxx(@Param("gzlslid")String gzlslid,@Param("xmid") String xmid);

    /**
     * @param bdcXmfbZsxtQO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 更新项目附表证书形态
     */
    void updateBdcXmfbZsxt(BdcXmfbZsxtQO bdcXmfbZsxtQO);

    /**
     * @param gzlslid
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 完结案件状态
     */
    void updateAjztWjByGzlslid(@Param("gzlslid") String gzlslid, @Param("jssj") Date jssj);

    /**
     * @param gzlslid
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 查询证书
     */
    List<BdcXmZsDTO> queryZsByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * 通过xmid查询证书信息
     * @param xmid
     * @return
     */
    List<BdcXmZsDTO> queryZsByXmid(@Param("xmid") String xmid);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新项目表登簿信息
     */
    int updateBdcXmxxDbxxPl(DbxxQO dbxxQO);

    /**
     * @param bdcWlzxcqXzqlQO 外联选择注销产权对应的限制权利QO
     * @return 外联选择注销的产权对应的限制权利DTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询当前流程产权项目对应外联选择注销的产权上的现势限制权利
     */
    List<BdcWlzxcqXzqlDTO> listWlcqzxXzql(BdcWlzxcqXzqlQO bdcWlzxcqXzqlQO);

}
