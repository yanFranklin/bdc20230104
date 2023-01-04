package cn.gtmap.realestate.etl.core.service;


import cn.gtmap.realestate.etl.core.domian.wwsq.*;
import cn.gtmap.realestate.etl.core.dto.GxWwBlztDTO;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
 * @version 2020/06/11,1.0
 * @description 互联网业务信息查询数据层级方法
 */
public interface HlwYwxxDataService {

    /**
     * 根据互联网项目id查询互联网项目
     *
     * @param hlwxmid 互联网项目ID
     * @return 申请项目
     */
    GxWwSqxmDo gxWwsqxmByHlwXmid(String hlwxmid);

    /**
     * 根据互联网项目id查询所有申请信息，单个，组合，批量
     *
     * @param xmid 互联网项目ID
     * @return 申请信息
     */
    List<GxWwSqxxDo> listGxWwSqxxByXmid(String xmid);


    /**
     * 根据互联网申请信息id查询权利人信息
     *
     * @param sqxxid 申请信息ID
     * @return 权利人信息
     */
    List<GxWwSqxxQlrDo> listGxWwSqxxQlrBySqxxid(String sqxxid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    GxWwSqxxQlrDo queryGxWwSqxxQlrByQlrid(String qlrid);

    /**
     * 根据互联网申请信息id查询材料信息
     *
     * @param sqxxId 申请信息ID
     * @return 材料信息
     */
    List<GxWwSqxxClxxDo> listGxWwSqxxClxxBySqxxid(String sqxxId);

    /**
     * 根据互联网材料id查询附件信息
     *
     * @param clxxid 材料id
     * @return 附件信息
     */
    List<GxWwSqxxFjxxDo> listGxWwSqxxFjxxByClid(String clxxid);

    /**
     * 根据附件id查询附件信息
     *
     * @param fjid 附件id
     * @return 附件信息
     */
    GxWwSqxxFjxxDo queryGxWwSqxxFjxxByFjid(String fjid);

    /**
     * 根据互联网申请信息id查询税务信息
     *
     * @param sqxxid 申请信息ID
     * @return 税务信息
     */
    List<GxWwSwxxDo> listGxWwSwxxBySqxxid(String sqxxid);

    /**
     * 根据互联网税务信息id查询明细信息
     *
     * @param swid 税务信息ID
     * @return 税务明细信息
     */
    List<GxWwSwmxDo> listGxWwSwmxBySwid(String swid);

    /**
     * 根据互联网项目ID查询材料信息
     *
     * @param hlwxmid 互联网项目ID
     * @return 材料信息
     */
    List<GxWwSqxxClxxDo> listGxWwSqxxClxxByXmid(String hlwxmid);

    /**
     * @param wwslbh 外网受理编号
     * @return 审核信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据外网受理编号查询审核信息
     */
    List<GxWwShxxDO> listGxWwShxxByWwslbh(String wwslbh);

    /**
     * @return 未创建互联网项目ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询未创建互联网银行项目集合
     */
    List<GxWwSqxmDo> listWcjYhWwsqXmidList();

    /**
     * @param wwxmid 外网项目ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 插入创建记录
     */
    void insertCjjl(String wwxmid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    void updateCjjl(String wwxmid, String wwslbh, String gzlslid, String sbyy);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除记录
     */
    void deleteSbCjjl(String wwxmid);


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据bdcdyh查询互联网办理状态
     */
    List<GxWwBlztDTO> listGxWwBlztDTOByBdcdyh(String bdcdyh);


    /**
     * @param
     * @return
     * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
     * @description 外网受理编号查询
     */
    GxWwSqxmDo getGxWwSqxmBySqslbh(String sqslbh);

    /**
     * @param sqxxId 申请信息ID
     * @return 物流信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据申请信息ID获取物流信息
     */
    List<GxWwSqxxWlxxDO> listGxWwSqxxWlxxBySqxxid(String sqxxId);

    /**
     * @param sqxxId 申请信息ID
     * @return 收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据申请信息ID获取收费信息
     */
    List<GxWwSqxxSfxxDO> listGxWwSqxxSfxxBySqxxid(String sqxxId);


    /**
     * 查询时间范围内创建失败的xmid
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> listFailedWwsqXmidList(Date startTime, Date endTime);

    /**
     * 查询时间范围内创建成功的xmid
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> listSuccessWwsqXmidList(Date startTime, Date endTime);


    /**
     * @param xmids
     * @return
     */
    List<GxWwSqxmDo> listWwsqListByXmids(List<String> xmids);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除记录
     */
    void deleteXmxx(String wwxmid);

}
