package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlJtcyGroupDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslYmxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSqrQO;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理申请人数据服务
 */
public interface BdcSlSqrService {
    /**
     * @param sqrid 申请人人ID
     * @return 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据申请人人ID获取不动产受理申请人
     */
    BdcSlSqrDO queryBdcSlSqrBySqrid(String sqrid);

    /**
     * @param xmid 项目ID
     * @return 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID获取不动产受理申请人
     */
    List<BdcSlSqrDO> listBdcSlSqrByXmid(String xmid,String sqrlb);

    /**
     * @param bdcSlSqrDO 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理申请人
     */
    BdcSlSqrDO insertBdcSlSqr(BdcSlSqrDO bdcSlSqrDO);

    /**
     * @param bdcSlSqrDO 不动产受理申请人
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理申请人
     */
    Integer updateBdcSlSqr(BdcSlSqrDO bdcSlSqrDO);

    /**
     * @param sqrid 申请人人ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据申请人人ID删除不动产受理申请人
     */
    Integer deleteBdcSlSqrBySqrid(String sqrid);

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理申请人
     */
    Integer deleteBdcSlSqrByXmid(String xmid,String sqrlb);

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询参数删除不动产受理申请人信息
     */
    void batchDeleteSlSqr(String jbxxid);

    /**
     * @param bdcSlSqrDOList 申请人数据
     * @return 转换后的数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 将权利人转为义务人
     */
    List<BdcSlSqrDO> changeQlrToYwr(List<BdcSlSqrDO> bdcSlSqrDOList);

    /**
     * @param sqrmc 申请人名称
     * @param zjh 证件号
     * @param sqrlb 申请人类别
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据申请人名称和证件号
     */
    List<BdcSlSqrDO> listBdcSlSqrBySqrmcAndZjh(String sqrmc,String zjh,String sqrlb);

    /**
     * @param xmid 项目ID
     * @param sqrlb 申请人类别
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID删除申请人以及对应的家庭成员
     */
    void deleteSqrAndJtcyByXmid(String xmid,String sqrlb);

    /**
     * 根据不动产受理申请人QO查询受理申请人信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcSlSqrQO] 受理申请人QO
     * @return: List<BdcSlSqrDO> 受理申请人DO
     */
    List<BdcSlSqrDO> queryBdcSlSqrBySqrQO(BdcSlSqrQO bdcSlSqrQO);

    /**
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据家庭分组获取申请人信息
     */
    YcslYmxxDTO getSqrxxGroupByJt(String xmid);

    /**
     * @param xmid 项目ID
     * @param sqrlb 申请人类别
     * @param sfhb 是否合并多个申请人
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据家庭分组获取申请人信息
     */
    List<List<BdcSlJtcyGroupDTO>> getSqrxxGroupByJtWithSqrlb(String xmid, String sqrlb, Boolean sfhb);

    /**
     * @param gzlslid
     * @param sqrlb
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量删除申请人
     */
    void delBatchSqrxx(String gzlslid, String sqrlb);

    /**
     * @param gzlslid
     *
     * @param sqrlb 申请人类别
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID删除申请人以及对应的家庭成员
     */
    void delBatchSqrAndJtcyByXmid(String gzlslid,String xmid,String sqrlb);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    List<BdcSlSqrDO> insertBatchSqr(String gzlslid, String qllx, BdcSlSqrDO bdcSlSqrDO, String djxl);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量删除申请人
     */
    void deleteBdcSqrsBySqrxx(String sqrmc, String zjh, String gzlslid, String qlrlb, String qllx, String djxl);

    /**
     * @param json
     * @param gzlslid 工作流实例ID
     * @param qllx    权利类型
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新申请人信息(组合流程同步 ）
     */
    List<BdcSlSqrDO> saveBatchBdcSlSqrWithZh(String json, String gzlslid, String qllx, String djxl);

    /**
     * @param sqrid 申请人ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量删除申请人(组合流程同步 ）
     */
    void deleteBdcSqrsBySqrxxWithZh(String sqrid, String gzlslid, String qllx, String djxl);

    /**
     * 同步受理申请人信息
     *
     * @param bdcQlrDOList 权利人集合
     * @param gzlslid      工作流实例ID
     * @param type         操作类型
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void syncSqrxx(List<BdcQlrDO> bdcQlrDOList, String gzlslid, String type);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 改变受理申请人共有方式
     * @date : 2022/9/20 9:23
     */
    List<BdcSlSqrDO> changeSlSqrGyfs(List<BdcSlSqrDO> bdcSlSqrDOList, String xmid);

    /**
     * @param xmidList 项目ID集合
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID集合批量查询受理申请人
     */
    List<BdcSlSqrDO> listSlSqrPl(List<String> xmidList);
}
