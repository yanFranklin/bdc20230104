package cn.gtmap.realestate.accept.core.service;


import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxMxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSwxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.swxx.SwHsztDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 受理核税信息
 */
public interface BdcSlHsxxService {

    /**
     * @param bdcSlHsxxDO 受理核税信息
     * @return 受理核税信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取不动产受理核税信息
     */
    List<BdcSlHsxxDO> listBdcSlHsxx(BdcSlHsxxDO bdcSlHsxxDO);

    /**
     * @param bdcSlHsxxQO 受理核税信息
     * @return 受理核税信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description  获取不动产受理核税信息
     */
    List<BdcSlHsxxDO> listBdcSlHsxxByHsxxQo(BdcSlHsxxQO bdcSlHsxxQO);

    /**
     * @param bdcSlHsxxDO 受理核税信息
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据受理核税信息删除不动产受理核税信息
     */
    int delBdcSlHsxx(BdcSlHsxxDO bdcSlHsxxDO);

    /**
     * @param bdcSlHsxxQO 不动产受理核税信息QO对象
     * @return 删除数量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description  根据受理核税信息删除不动产受理核税信息
     */
    int deleteBdcSlHsxx(BdcSlHsxxQO bdcSlHsxxQO);

    /**
     * @param bdcSlHsxxDO 不动产受理核税信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新不动产受理核税信息
     */
    int updateBdcSlHsxx(BdcSlHsxxDO bdcSlHsxxDO);

    /**
     * @param xmid 项目ID
     * @param sqrlb 申请人类别
     * @return 税务信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid和申请人类别查询税务信息
     */
    List<BdcSwxxDTO> queryBdcSwxxDTO(String xmid,String sqrlb);

    /**
     * 根据项目ID与纳税人识别号信息更新受理核税信息
     * <p>先根据项目ID与纳税人识别号查询申请人信息表（BDC_SL_SQR）。
     * 在根据申请人类别区分为权利人还是义务人，来更新核税信息。
     * <p> 该更新接口只支持：hsxxid、xmid、nsrsbh做为查询条件,未传递这两个参数时，抛出｛@link MissingArgumentException｝
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: BdcSlHsxxDO 核税信息更新值，核税信息查询参数
     * @return: Integer 修改结果
     */
    Integer updateBdcSlHsxxByXmidAndNsrsbh(BdcSlHsxxDO bdcSlHsxxDO);

    /**
     * @param wszt
     * @param gzlslid
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新完税状态
     */
    void updateBatchWszt(Integer wszt, String gzlslid);

    /**
     * @param wszt
     * @param gzlslid
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新完税状态
     */
    void updateBatchWsztByQlrlb(Integer wszt, String gzlslid, String qlrlb);

    /**
     * @param slbh 受理编号
     * @return 核税信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据受理编号获取核税信息
     */
    List<BdcSlHsxxDO> listBdcSlHsxxBySlbh(String slbh);

    /**
     * 批量更新不动产受理核税信息
     * @param bdcSlHsxxDO 不动产受理核税信息 (可更新的参数有：jypzh、ytsswzt、yhjkrkzt)
     * @param gzlslid  工作流实例ID
     */
    void batchUpdateBdcSlHsxx(BdcSlHsxxDO bdcSlHsxxDO, String gzlslid);

    /**
     * @param bdcSlHsxxDO 不动产受理核税信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增受理核税信息
     * @date : 2020/5/19 14:08
     */
    Integer insertBdcSlHsxx(BdcSlHsxxDO bdcSlHsxxDO);

    /**
     * @param swHsztDTO 核税信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新核税状态
     * @date : 2020/10/28 15:05
     */
    Integer updateHszt(SwHsztDTO swHsztDTO);

    /**
     * @param gzlslid 工作流实例ID
     * @return 受理核税信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description  获取不动产受理核税信息
     */
    List<BdcSlHsxxDO> listBdcSlHsxxByGzlslid(String gzlslid,String slbh);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 重新生成税务信息
      */
    Integer recreateHsxx(String xmid,List<BdcSlHsxxDO> bdcSlHsxxDOList,List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList);


    /**
     * @param xmid 项目ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除税务信息
     */
    void deleteSwxx(String xmid);

    /**
     * @param xmid 项目ID
     * @param sqrlb 申请人类别
     * @return 税务信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid和申请人类别查询税务信息
     */
    List<BdcSlHsxxDO> queryQlrSwxx(String xmid,String sqrlb);


    /**
     * @param gzlslid 工作流实例ID
     * @param sqrlb 权利人类别
     * @return 受理核税信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description  获取不动产受理核税信息
     */
    List<BdcSlHsxxDO> listBdcSlHsxxByGzlslidAndSqrlb(String gzlslid, String sqrlb);
}
