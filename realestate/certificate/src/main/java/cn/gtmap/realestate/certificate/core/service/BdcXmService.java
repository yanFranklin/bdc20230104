package cn.gtmap.realestate.certificate.core.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmZsGxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;

import java.util.Date;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/30
 * @description 不动产项目查询服务接口
 */
public interface BdcXmService {
    /**
     * @param xmid 项目ID
     * @param bz   备注
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目的备注内容
     */
    int updateXmBz(String xmid, String bz);

    /**
     * @param xmid 项目ID
     * @param fzyj 发证意见
     * @return int 更新数据量
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 更新项目附表的发证意见
     */
    int updateXmFbFzyj(String xmid, String fzyj);

    /**
     * @param xmid    项目ID
     * @param gzlslid 工作流实例ID
     * @return List<BdcXmDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程所有的项目信息
     */
    List<BdcXmDO> queryLcAllBdcXm(String xmid, String gzlslid);

    /**
     * @param xmid    项目ID
     * @param gzlslid 工作流实例ID
     * @return List<BdcXmFbDO>
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 获取当前流程所有的项目附表信息
     */
    List<BdcXmFbDO> queryLcAllBdcXmFb(String xmid, String gzlslid);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @return  历史关系
     * @description 通过项目id去获取关系表
     */
    List<BdcXmLsgxDO> queryBdcXmLsgxByXmid(String xmid);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @return  {BdcXmDO} 不动产项目
     * @description  根据项目ID获取对应项目
     */
    BdcXmDO queryBdcXm(String xmid);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   gzlslid 工作实例ID
     * @return  {List} 项目集合
     * @description  获取工作流实例关联的项目
     */
    List<BdcXmDO> listBdcXmByProInsID(String gzlslid);

    /**
     * @author  <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @param   gzlslid 工作实例ID
     * @return  {List} 项目集合
     * @description  获取工作流实例关联的项目附表
     */
    List<BdcXmFbDO> listBdcXmfbByProInsID(String gzlslid);

    /**
     * @author  <a href="mailto:zhangzinyu@gtmap.cn">zhangxinyu</a>
     * @param   zsid
     * @return  {List} 项目集合
     * @description  获取zsid关联的项目附表
     */
    List<BdcXmFbDO> listBdcXmfbByZsid(String zsid);

    /**
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param   gzlslidList 工作实例ID集合
     * @return  {List} 项目集合
     * @description  获取工作流实例关联的项目部分信息
     */
    List<BdcXmDTO> listBdcXmBfxxByProInsIDList(List<String> gzlslidList);

    /**
     * @param zsid 证书ID
     * @return List<BdcXmZsGxDO> 项目证书关系
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据zsid查询项目证书关系
     */
    List<BdcXmZsGxDO> queryBdcXmZsgxByZsid(String zsid);


    /**
     * @param djsj        登记时间
     * @param datePattern 时间格式模式
     * @param qszt        权属状态
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据登记时间和权属状态查询项目信息
     */
    List<BdcXmDO> listBdcXmByDjsj(Date djsj, String datePattern, Integer qszt);

    /**
     *
     * @author <a href="mailto:zedeqaing@gtmap.cn">zedq</a>
     * @description 根据受理编号获取相关的流程实例id号
     * @param slbhList
     * @return
     */
    List<BdcXmDO> listBdcXmFiles(List<String> slbhList);

    /**
     * 根据证书ID查询不动产项目信息
     * @param zsid  证书ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 不动产项目信息
     */
    List<BdcXmDO> listBdcXmByZsid(String zsid);

    /**
     * 根据工作流实例ID集合查询项目证书关系
     * @param gzlslidList 工作流实例ID集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 项目证书关系
     */
    List<BdcXmZsGxDO> listBdcXmZsGxByGzlslidList(List<String> gzlslidList);
}
