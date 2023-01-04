package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.certificate.core.dto.BdcCancelECertificateDTO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcXmZsGxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/9
 * @description 不动产项目查询Mapper接口
 */
public interface BdcXmMapper {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzlslid 工作实例ID
     * @return {List} 项目集合
     * @description 获取工作流实例关联的项目
     */
    List<BdcXmDO> listBdcXmByProInsID(@Param("gzlslid") String gzlslid);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @param gzlslid 工作实例ID
     * @return {List} 项目集合
     * @description 获取工作流实例关联的项目附表
     */
    List<BdcXmFbDO> listBdcXmfbByProInsID(@Param("gzlslid") String gzlslid);

    /**
     * @author  <a href="mailto:zhangzinyu@gtmap.cn">zhangxinyu</a>
     * @param   zsid
     * @return  {List} 项目集合
     * @description  获取zsid关联的项目附表
     */
    List<BdcXmFbDO> listBdcXmfbByZsid(@Param("zsid") String zsid);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @param gzlslidList 工作实例ID集合
     * @return {List} 项目集合
     * @description 获取工作流实例关联的项目部分信息
     */
    List<BdcXmDTO> listBdcXmBfxxByProInsIDList(@Param("gzlslidList") List<String> gzlslidList);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param slbhList
     * @return
     * @description 获取受理编号列表关联的工作流实例id
     */
    List<BdcXmDO> listBdcXmByslbhList(@Param("slbhList") List<String> slbhList);

    /**
     * 根据证书id查询不动产醒目信息
     * @param zsid
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 不动产项目
     */
    List<BdcXmDO> listBdcXmByZsid(@Param("zsid") String zsid);

    /**
     * 查询历史的需要注销的证书数据(盐城临时)
     * @param count
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @return BdcCancelECertificateDTO
     */
    List<BdcCancelECertificateDTO> listLsZzxxForYcCancel(@Param("count") Integer count);

    /**
     * 根据工作流实例ID集合查询项目证书关系
     * @param gzlslidList 工作流实例ID集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 项目证书关系
     */
    List<BdcXmZsGxDO> listBdcXmZsGxByGzlslidList(@Param("gzlslidList") List<String> gzlslidList);

    /**
     * 查询原注销项目
     * @param gzlslid 工作流实例ID
     * @return 原项目
     */
    List<BdcXmDO> listBdcZxYxm(@Param("gzlslid") String gzlslid);
}
