package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrHkbGxDO;
import cn.gtmap.realestate.common.core.domain.building.NydJtcyDO;
import cn.gtmap.realestate.common.core.dto.init.BdcJtcySaveDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrHkbGxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrHkbGxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/4.
 * @description
 */
public interface BdcJtcyService {
    /**
     * 查询权利人户口本关系(包含权利人证件号)
     * @param bdcQlrHkbGxQO
     * @return
     */
    List<BdcQlrHkbGxDTO> queryBdcQlrHkbGx(BdcQlrHkbGxQO bdcQlrHkbGxQO);
    /**
     * 查询权利人户口本关系(返回BdcQlrHkbGxDO)
     * @param bdcQlrHkbGxQO
     * @return
     */
    List<BdcQlrHkbGxDO> listBdcQlrHkbGxDO(BdcQlrHkbGxQO bdcQlrHkbGxQO);

    /**
     * 批量删除权利人户口薄关系
     * @param gzlslid
     * @param qlrlb
     */
    void deleteBatchQlrHkbGx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid, String qlrlb);

    /**
     * 查询家庭成员
     * @param bdcJtcyDO
     * @return
     */
    List<BdcJtcyDO> queryJtcy(BdcJtcyDO bdcJtcyDO);

    /**
     * 比较权籍家庭成员和不动产家庭成员是否一致
     * @param nydJtcyDOList
     * @param jtcyDOList
     * @return
     */
    boolean compareJtcy(List<NydJtcyDO> nydJtcyDOList, List<BdcJtcyDO> jtcyDOList);

    /**
     * @param bdcQlrQO 权利人查询对象
     * @return 家庭成员列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据权利人参数获取家庭成员列表
     */
    List<BdcJtcyDO> listBdcJtcyByQlr(BdcQlrQO bdcQlrQO);

    /**
     * @param jtcyid 家庭成员ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据家庭成员ID和流程ID删除或生成新版本家庭成员信息
     * 权利人户口簿关系表存在其他业务,保留新版本家庭成员,不存在则直接删除
     */
    void deleteBdcJtcyByGzlslid(String jtcyid,String gzlslid);

    /**
     * @param bdcJtcySaveDTO 家庭成员批量保存对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量保存家庭成员集合
     */
    void saveBatchJtcyxx(BdcJtcySaveDTO bdcJtcySaveDTO);

    /**
     * 比较原不动产家庭成员和当前不动产家庭成员是否一致
     * @param yJtcyDOList 原不动产家庭成员
     * @param jtcyDOList 当前家庭成员
     * @return 是否一致
     */
    boolean compareJtcyWithYjtcy(List<BdcJtcyDO> yJtcyDOList, List<BdcJtcyDO> jtcyDOList);

    /**
     * @param bdcJtcyDOList 家庭成员列表
     * @return 新生成家庭成员版本
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成新的家庭成员版本
     */
    List<BdcJtcyDO> createNewBdcJtcy(List<BdcJtcyDO> bdcJtcyDOList,String hkbbm,Integer hkbbbh);

    /**
     * @param bdcQlrHkbGxDOList 权利人户口簿关系
     * @return 新生成权利人户口簿关系
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成新的户口簿关系
     */
    List<BdcQlrHkbGxDO> createNewBdcQlrHkbGx(List<BdcQlrHkbGxDO> bdcQlrHkbGxDOList,Integer hkbbbh);

    /**
     * @param qlrid 权利人ID
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量删除当前流程家庭成员
     * 需要根据qlrid找到当前流程同名的所有同名qlrid,删除权利人户口簿关系以及判断是否需要删除家庭成员
     */
    void deleteBatchJtcyByGzlslid(String qlrid, String gzlslid);

    /**
     * 根据户主的证件号信息查询家庭成员信息
     * @param zjh  户主证件号信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 家庭成员集合
     */
    List<BdcJtcyDO> queryJtcyxxByhzZjh(String zjh);


}
