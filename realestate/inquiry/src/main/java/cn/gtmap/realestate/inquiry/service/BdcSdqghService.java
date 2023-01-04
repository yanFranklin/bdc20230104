package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.SdqHyResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztUpdateHyDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/11/21
 * @description 水电气过户service
 */
public interface BdcSdqghService {
    /**
     * @param bdcSdqghDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 新增水电气过户信息
     */
    int insertSdqghDO(BdcSdqghDO bdcSdqghDO);

    /**
     * 通过传入的不动产项目参数查询不动产水电气业务信息
     *
     * @param bdcSdqywQO 查询参数对象
     * @return
     */
    List<BdcSdqghDO> querySdqyw(BdcSdqywQO bdcSdqywQO);


    /**
     * 水过户
     *
     * @param gzlslid
     */
    boolean shuigh(String gzlslid, String isOneWebSource);

    /**
     * 电过户
     *
     * @param gzlslid
     */
    boolean diangh(String gzlslid, String isOneWebSource);

    /**
     * 气过户
     *
     * @param gzlslid
     */
    boolean qigh(String gzlslid, String isOneWebSource);

    /**
     * 气 查询是否满足过户条件
     *
     * @param bdcSdqywQO
     * @return
     */
    public Object getQiZt(BdcSdqywQO bdcSdqywQO);

    /**
     * 录办理的户号，和3.0件关联上
     *
     * @param bdcSdqywQO
     * @return
     */
    public Object saveData(BdcSdqywQO bdcSdqywQO);

    /**
     * 查询业务数据
     *
     * @param gzlslid
     * @return
     */
    List<Map> getSdqSqbYwDyData(String gzlslid);

    /**
     * 更新水电气业务办理状态
     *
     * @param bdcSdqBlztRequestDTO
     * @return BdcSdqBlztResponseDTO
     */
    BdcSdqBlztResponseDTO updateSdqBlzt(BdcSdqBlztRequestDTO bdcSdqBlztRequestDTO);

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 电过户核验，过户前查询是否可以过户
     */
    CommonResponse commonDianghhy(String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 水过户核验，过户前查询是否可以过户
     */
    CommonResponse commonShuighhy(String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 气过户核验，过户前查询是否可以过户
     */
    CommonResponse commonQighhy(String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly    权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign   权利人拼接符号
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 电过户请求
     */
    CommonResponse commonDiangh(String processInsId, Integer qlrxxsjly, String appendSign, Integer fjxxsjly);

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly    权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign   权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 气过户请求
     */
    CommonResponse commonQigh(String processInsId, Integer qlrxxsjly, String appendSign, Integer fjxxsjly);

    /**
     * @param processInsId 工作流实例ID
     * @return 过户结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 电过户附件推送
     */
    CommonResponse commonDianghFjcl(String processInsId);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存水电气过户信息
     */
    void saveSdq(String gzlslid, List<BdcSdqghDO> bdcSdqghDOS, Boolean needHz);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询水电气信息
     */
    BdcSdqxxDTO querySdqxx(String gzlslid);

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 电过户请求撤销
     */
    CommonResponse commonDianghCancel(String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 气过户请求撤销
     */
    CommonResponse commonQighCancel(String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @param consNo       户号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 过户申请前查询状态，是否欠费
     */
    CommonResponse commonDianZt(String processInsId, String consNo);

    /**
     * @param processInsId 工作流实例ID
     * @param consNo       户号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 过户办理状态查询
     */
    CommonResponse commonDianGhblzt(String processInsId, String consNo);

    /**
     * @param processInsId 工作流实例ID
     * @param qlrxxsjly    权利人信息数据来源 默认匹配户主信息 1:匹配户主获取权利人 2读取业务所有的权利人
     * @param appendSign   权利人拼接字符
     * @param fjxxsjly     附件数据来源 默认为0 0：不传附件 1：电子证照
     * @return 过户结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 水过户请求
     */
    CommonResponse commonShuigh(String processInsId, Integer qlrxxsjly, String appendSign, Integer fjxxsjly);

    /**
     * @return 过户结果
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 水过户附件推送
     */
    CommonResponse commonShuighFjcl(String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 水过户请求撤销
     */
    CommonResponse commonShuighCancel(String processInsId);

    /**
     * @param bdcSdqBlztUpdateHyDTO
     * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @description 更新水电气过户核验信息
     */
    void updateSdqghhy(BdcSdqBlztUpdateHyDTO bdcSdqBlztUpdateHyDTO);

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户
     * @Date 2022/5/26 18:38
     **/
    CommonResponse shuchengShuigh(String processInsId);

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户检查
     * @Date 2022/5/26 18:38
     **/
    CommonResponse shuchengShuighjc(String processInsId);

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户登记簿数据
     * @Date 2022/5/26 18:38
     **/
    CommonResponse<String> shuchengShuiDjbData(String processInsId);
}
