package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSqrDTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareHyxxResultDTO;
import cn.gtmap.realestate.common.core.dto.accept.CompareHyxxResultMzhyxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJtcyQO;
import cn.gtmap.realestate.common.core.qo.accept.CompareHyxxQO;
import cn.gtmap.realestate.common.core.qo.accept.GetJtcyxxQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/26
 * @description 受理家庭成员
 */
public interface BdcSlJtcyService {

    /**
     * @param sqrid 申请人ID
     * @return 受理家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据申请人ID获取受理家庭成员信息
     */
    List<BdcSlJtcyDO> listBdcSlJtcyBySqrid(String sqrid);

    /**
     * @param bdcSlJtcyDO 受理家庭成员信息
     * @return 保存数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存受理家庭成员信息
     */
    Integer updateBdcSlJtcy(BdcSlJtcyDO bdcSlJtcyDO);

    /**
     * @param jtcyid 家庭成员id
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据交易信息id获取不动产受理交易信息
     */
    BdcSlJtcyDO queryBdcSlJtcyByJtcyid(String jtcyid);

    /**
     * @param bdcSlJtcyDO 家庭成员信息
     * @return 不动产家庭成员信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理家庭成员信息
     */
    BdcSlJtcyDO insertBdcSlJtcy(BdcSlJtcyDO bdcSlJtcyDO);

    /**
     * @param jtcyid 家庭成员id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理家庭成员信息
     */
    Integer deleteBdcSlJtcyByJtcyid(String jtcyid);

    /**
     * @param sqrid 申请人id
     * @return 删除数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理家庭成员信息
     */
    Integer deleteBdcSlJtcyBySqrid(String sqrid);

    /**
     * @param bdcSlJtcyQO 受理家庭成员查询QO对象
     * @return 家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询家庭成员信息
     */
    List<BdcSlJtcyDO> listBdcSlJtcy(BdcSlJtcyQO bdcSlJtcyQO);

    /**
     * @param zjh 证件号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据申请人ID集合和证件号批量删除不动产受理家庭成员
     */
    void deleteBatchBdcSlJtcy(List<String> sqridList,String zjh);

    /**
     * @param getJtcyxxQO 获取家庭成员接口入参
     * @return 家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 调用婚姻/公安接口获取信息直接返回不做处理
     */
    Object getHygaxx(GetJtcyxxQO getJtcyxxQO);

    /**
     * @param getJtcyxxQO 获取家庭成员接口入参
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 调用接口获取家庭成员信息
     */
    List<BdcSlJtcyDO> getJtcyxx(GetJtcyxxQO getJtcyxxQO);

    /**
     * @param bdcSlJtcyDOList 接口返回家庭成员信息
     * @return 实际存储家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  保存接口返回家庭成员信息
     */
    List<BdcSlJtcyDO> saveJkJtcyxx(List<BdcSlJtcyDO> bdcSlJtcyDOList,GetJtcyxxQO getJtcyxxQO);

    /**
     * @param gzlslid 工作流实例ID
     * @param sqrlb 申请人类别
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量删除家庭成员
     */
    void delBatchJtcyxx(String gzlslid, String sqrlb);

    /**
     * 获取家庭成员信息接口（南通）
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param sqrmc 申请人名称
     * @param zjh 证件号
     * @param sqrid 申请人ID
     * @return: void 无返回值
     */
    void getJtcyxxNt(String sqrmc, String zjh, String sqrid);

    /**
     * @param zjh 证件号
     * @param beanName
     * @return 家庭成员信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 南通公安接口调用
     */
    Object getGaxx(String zjh,String beanName);

    /**
     * 批量获取申请人家庭信息，通过XMID获取当前表单的所有申请人
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: xmid 项目ID
     * @param: qlrlb 权利人类别
     * @return: void 无返回值
     */
    void getJtcyxxByXmidNt(String xmid, String qlrlb);

    /**
     * 同步家庭成员至其他户
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlSqrDTO 不动产受理申请人DTO
     * @param gzlslid 工作流实例ID
     */
    void syncJtcyxx(BdcSlSqrDTO bdcSlSqrDTO, String gzlslid);

    /**
     * @param bdcSlJtcyDOList 家庭成员集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存家庭成员
     */
    void saveBdcSlJtcyList(List<BdcSlJtcyDO> bdcSlJtcyDOList);

    /**
     * @param getJtcyxxQO 家庭成员查询参数
     * @return 提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 民政婚姻信息比对
     */
    Map<String,String> compareHyxx(GetJtcyxxQO getJtcyxxQO);

    /**
     * @param getJtcyxxQO 家庭成员查询参数
     * @param isrk 是否入库(婚姻状态是否更新)
     * @return 配偶信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 调用第三方接口获取配偶信息
     */
    BdcSlJtcyDO requestHyxx(GetJtcyxxQO getJtcyxxQO,boolean isrk);

    /**
     * @param compareHyxxQO
     * @return 提示信息
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 南通一体化民政婚姻信息比对
     */
    CompareHyxxResultDTO compareHyznxx(CompareHyxxQO compareHyxxQO);

    /**
      * @param resultDTO 婚姻查询信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 导入婚姻比对信息
      */
    void drhybdxx(CompareHyxxResultDTO resultDTO);
}
