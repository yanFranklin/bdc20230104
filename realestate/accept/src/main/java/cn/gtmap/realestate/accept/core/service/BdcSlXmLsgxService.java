package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理项目历史关系数据服务
 */
public interface BdcSlXmLsgxService {
    /**
     * @param gxid 关系ID
     * @return 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据关系ID获取不动产受理项目历史关系
     */
    BdcSlXmLsgxDO queryBdcSlXmLsgxByGxid(String gxid);

    /**
     * @param xmid 项目ID
     * @return 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID获取不动产受理项目历史关系
     */
    List<BdcSlXmLsgxDO> listBdcSlXmLsgxByXmid(String xmid);

    /**
     * @param xmid 项目id
     * @param yxmid  原项目id
     * @param sfwlzs 是否外联证书
     * @return 不动产受理项目历史关系
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目id或者原项目id获取不动产受理项目历史关系
     */
    List<BdcSlXmLsgxDO> listBdcSlXmLsgx(String xmid,String yxmid,Integer sfwlzs);

    /**
     * @param bdcSlXmLsgx 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理项目历史关系
     */
    BdcSlXmLsgxDO insertBdcSlXmLsgx(BdcSlXmLsgxDO bdcSlXmLsgx);

    /**
     * @param bdcSlXmLsgx 不动产受理项目历史关系
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理项目历史关系
     */
    Integer updateBdcSlXmLsgx(BdcSlXmLsgxDO bdcSlXmLsgx);

    /**
     * @param gxid 关系ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据关系ID删除不动产受理项目历史关系
     */
    Integer deleteBdcSlXmLsgxByGxid(String gxid);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理项目历史关系
     */
    Integer deleteBdcSlXmLsgxByXmid(String xmid);

    /**
     * @param jbxxid 基本信息ID
     * @param qjidList 权籍ID数组
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询参数删除不动产受理项目历史关系
     */
    void deleteBdcSlXmLsgx(String jbxxid,List<String> qjidList);


    /**
     * @param gzlslid
     * @return 不动产受理项目上下手关系
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description  根据gzlslid获取不动产受理上下手关系数据
     */
    List<Map> sxxBdcXx(String gzlslid);

    /**
     * @param bdcSlDeleteCsDTO 受理信息删除参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据参数批量删除受理项目历史关系信息
     */
    void deleteBdcSlXmLsgx(BdcSlDeleteCsDTO bdcSlDeleteCsDTO);

    /**
     * @param xmidList 项目ID集合
     * @param sfwlzs 是否外联证书
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID集合批量查询受理项目历史关系
     */
    List<BdcSlXmLsgxDO> listSlXmLsgxPl(List<String> xmidList,Integer sfwlzs);

    /**
     * @param  yxmidList 原项目ID集合
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据原项目ID集合批量查询受理项目历史关系
     */
    List<BdcSlXmLsgxDO> listSlXmLsgxPlByYxmid(List<String> yxmidList);

}
