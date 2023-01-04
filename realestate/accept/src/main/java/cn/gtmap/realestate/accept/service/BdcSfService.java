package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.JfsxxRequestDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfSsxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfxmJsQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/26
 * @description 不动产收费服务
 */
public interface BdcSfService {
    /**
     * @param gzlslid 工作流实例id
     * @param xmid 项目ID (不必传，传xmid时，组合流程根据项目id生成对应的收费信息)
     * @return 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 初始化收费信息
     */
    BdcSlSfxxDO cshSfxx(String gzlslid, String xmid);

    /**
     * @param gzlslid 工作流实例id
     * @return 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 初始化非登记流程收费信息
     */
    BdcSlSfxxDO cshFdjlcSfxx(String gzlslid);

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据基本信息id删除收费信息（包括收费信息和收费项目)
     */
    void deleteSfxx(BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * @param gzlslid 工作流实例id
     * @param xmid    项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织页面收费信息内容
     */
    BdcSlSfxxDTO generateSfxx(String gzlslid, String xmid);

    /**
     * @param gzlslid 工作流实例id
     * @param xmid    项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织页面收费信息内容(南通)
     */
    BdcSlSfxxDTO generateSfxxNt(String gzlslid, String xmid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [gzlslid, xmid]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO>
     * @description 查询收费信息（包含作废数据）
     */
    List<BdcSlSfxxDO> queryBdcSlSfxxBhzf(String gzlslid, String xmid);

    /**
     * @param bdcSlSfxxDO 受理收费信息
     * @return 不动产受理收费信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 更新受理收费信息
     */
    Boolean gxSfxxSfzt(BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * @param sfxxid  收费信息id
     * @param qlrlb   权利人类别
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新缴费书编码
     */
    Boolean gxSfxxJfsbm(String sfxxid, String qlrlb, String gzlslid);

    /**
     * 调用非税接口推送收费信息并生成缴费书编码信息
     * @param sfxxid 收费信息id
     * @param qlrlb 权利人类别
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void createTaxNotice(String sfxxid, String qlrlb, String gzlslid);

    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取收费项目名称
     */
    List listSfxmmc();

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 自动计算收费项目并保存相关数据(后期需要与下个接口合并,目前合肥转发使用)
     */
    void autoCountSfxm(@NotBlank(message = "工作流实例id不可为空") String gzlslid) throws Exception;

    /**
      * @param gzlslid 工作流实例ID
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 自动计算收费信息收费项目()
      */
    void autoCountSfxxSfxm(@NotBlank(message = "工作流实例id不可为空") String gzlslid) throws Exception;

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转发自动计算收费信息
     */
    void autoCountSfxxSfxmForZf(String gzlslid) throws Exception;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理收费单页面收费项目自动计算信息
     */
    List<BdcSlSfxmDO> countSfxm(BdcSfxmJsQO bdcSfxmJsQO)  throws Exception;

    /**
     * @param bdcSlSfxmDO 受理收费项目
     * @param bdcXmDOList 项目信息
     * @param sfzhlc      是否组合流程
     * @param sfcxjs      是否重新计算
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算登记费
     */
    BdcSlSfxmDO countDjf(BdcSlSfxmDO bdcSlSfxmDO, List<BdcXmDO> bdcXmDOList, boolean sfzhlc, boolean sfcxjs, String version);

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @return 收费基本信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织一窗流程页面收费基本信息内容
     */
    BdcSlSfxxDTO generateYcslSfxx(String gzlslid, String xmid);

    /**
     * @param sfxxid 收费信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据收费信息ID推送待缴费信息
     */
    BdcTsdjfxxResponseDTO tsdjfxx(String sfxxid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据收费信息ID查询收费信息并推送待缴费信息
     */
    BdcTsdjfxxResponseDTO cxtsdjfxx(String sfxxid, String tsly, String pjdm, boolean sftdsyj);

    /**
     * @param sfxxid 收费信息id
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">caolu</a>
     * @description 如皋市根据收费信息ID查询收费信息并推送待缴费信息
     */
    CommonResponse rgtsdjfxx(String sfxxid,String currentUserName,String qlrlb,String gzlslid);

    /**
     * 根据收费信息ID查询收费信息并作废待缴费信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 作废结果
     */
    CommonResponse zfdjfxx(BdcSlSfxxDO bdcSlSfxxDO);
    /**
     * 生成电子发票
     * @param sfxxid 收费信息ID
     * @return 电子发票信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Object hkdzfpxx(String sfxxid);

    /**
     * @param sfxxid 收费信息ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    Object getDzfpxx(String sfxxid);

    /**
     * 第三方根据外网受理编号，获取非税电子发票信息
     * @param bdcDsfSfxxDTO 第三方收费信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return [{"sfxxid": "", "dzfpBase64":""}] 电子发票信息
     */
    CommonResponse dsfDzfpxxxz(BdcDsfSfxxDTO bdcDsfSfxxDTO);

    /**
     * @param slbh 受理编号
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据受理编号和项目ID推送待缴费信息外网生成支付订单
     */
    BdcTsdjfxxResponseDTO tsdjfxxBySlbh(String slbh, String xmid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据gzlslid查询当前流程是否点击收费页面保存按钮
     * @date : 2019/12/23 13:42
     */
    Integer queryBczt(String gzlslid);

    /**
     * @param bdcSfSsxxQO 获取缴费明细查询QO
     * @return BdcSfSsxxDTO 不动产收费收税信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 提供缴费缴税信息
     */
    BdcSfSsxxDTO queryBdcSfSsxxDTO(BdcSfSsxxQO bdcSfSsxxQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取月结订单缴费明细
     */
    List<BdcSfSsxxDTO> listYjBdcSfSsxxDTO(BdcSfSsxxQO bdcSfSsxxQO);

    /**
     * @param xmid  项目ID
     * @param sqrlb 申请人类别
     * @param sfxxidList
     * @param sfyj 是否月结
     * @return 缴费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid和申请人类别查询缴费信息
     */
    List<BdcSfxxDTO> queryBdcSfxxDTO(String xmid, String sqrlb,List<String> sfxxidList,Integer sfyj);

    /**
     * @param bdcXmDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/12/26 13:55
     */
    BdcSlSfxmSlDTO querySfxmDjfSl(List<BdcXmDO> bdcXmDOList);

    /**
     * @param bdcSfSsxxDTO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新收费收税状态
     */
    void updateSfSszt(BdcSfSsxxDTO bdcSfSsxxDTO);


    /**
     * @param gzlslid 工作流实例ID
     * @return boolean
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 回传收费收税状态（缴费状态）给大云
     */
    boolean saveSfssxxZtToProcess(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 判断当前流程是否属于线上缴费
     */
    boolean checkIsXsjf(String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新受理收费信息银行缴库入库状态
     */
    void updateSlSfSsxxYhJkrkzt(BdcYhjkrkztDTO bdcYhjkrkztDTO);

    /**
     * 校验缴费情况
     * <p>通过工作流实例ID<i>gzlslid</i>,校验是否为线下缴费，是否已缴库。
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: gzlslid  工作流实例ID
     * @return: Integer  {@code -1}为线下缴费，{@code 0}为未缴库，
     * {@code 1}为已缴库
     * {@code 2}为
     */
    Integer checkjfqk(String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  判断当前流程是否已缴费
     */
    Integer checkSfqk(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return: Integer {@code 0} 未上传凭证 {@code 1} 已上传凭证 {@code 2} 线上缴费，不做验证
     * @description 验证线下缴费是否已上传税费缴纳凭证
     */
    Integer checkXxJfPz(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @retur 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费信息退款申请
     */
    BdcSlSfxxDO sfxxTksq(String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询退款状态
     */
    BdcSlSfxxDO queryTkqk(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转发自动修改收费状态为已核验
     */
    void autoUpdateSfztYhy(String gzlslid);

    /**
     * 通知政融平台POS缴费成功
     *
     * @param posJfDTO POS缴费结果信息
     * @return 通知结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Object tzzrpt(PosJfDTO posJfDTO);

    /**
     * 更新收费状态为已核验(7)
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    void updatSfztYhy(String gzlslid);

    /**
     * 查询收费缴款情况,并更新收费缴款信息
     * @param bdcSlSfxxQO 不动产收费信息QO
     * @return {BdcSlSfxxDO} 不动产收费信息缴款情况
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    BdcSlSfxxDO queryJkqkAndUpdate(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 查询非税是否已缴费,并更新收费缴款信息
     * @param bdcqzh 不动产权证号
     * @return 不动产收费信息缴款情况
     * @author <a href="mailto:yaoyi@gtmap.cn">caolu</a>
     */
    CommonResponse queryFsjfqk(String bdcqzh);

    /**
     * 获取收费信息，组装非税缴费书信息请求内容
     *
     * @param sfxxid  收费信息ID
     * @param qlrlb   权利人类别
     * @param gzlslid 工作流实例ID
     * @return 缴费书信息请求信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    JfsxxRequestDTO handlerFsTsxx(String sfxxid, String qlrlb, String gzlslid);

    /**
     * @param bdcXmDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取盐城计算登记费信息
     * @date : 2021/6/23 16:44
     */
    BdcSlSfxmSlDTO queryYcDjfSl(List<BdcXmDO> bdcXmDOList);


    /**
     * @param gzlslid 工作流实例ID
     * @param qxdm 用户区县代码
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 自动计算收费信息收费项目数据-- 南通逻辑
     * @date : 2021/7/28 9:44
     */
    void autoCountNtSfxx(String gzlslid,String qxdm);


    /**
     * @param gzlslid,djxl
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询南通登记费数量
     * @date : 2021/7/29 10:06
     */
    BdcSlSfxmSlDTO queryNtSfxmSl(String gzlslid,String djxl);

    /**
     * 查询收费状态
     *
     * @param gzlslid 工作流实例ID
     * @param qlrlb   权利人类别
     * @return 收费状态
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Integer queryJkqkxx(String gzlslid, String qlrlb);


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 抵押权推送收费信息
     * @date : 2021/9/15 9:19
     */
    BdcTsdjfxxResponseDTO tsjfDyaqSfxx(String gzlslid);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据缴费人姓名判断是否月结
      */
    Integer querySfyjByJfrxm(String jfrxm,String gzldyid);

    /**
     * @param bdcDsfSfxxDTO 第三方收费信息
     * @return 返回信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 接收第三方收费信息保存并推送
     */
    CommonResponse jsSfxxSaveAndTs(BdcDsfSfxxDTO bdcDsfSfxxDTO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费模式是否区县模式
     */
    String nantongSfMode(String processInsId, String configName);

    void updateSlSfxxSfztczsj(String processInsId);

    /**
     * @param bdcSlSfxmDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算土地使用权交易服务费-常州根据地段级别判断
     * @date : 2022/4/24 14:27
     */
    List<BdcSlSfxmDO> countTdsyqJyfwf(List<BdcSlSfxmDO> bdcSlSfxmDOList, String gzlslid, boolean sfcxjs);

    /**
     * 调用互联网缴费状态查询信息，并更新缴费状态、完税状态
     *
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void queryHlwJfzt(String processInsId);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 税费统缴推送信息
     * @date : 2022/9/26 13:35
     */
    Object sftjxx(String gzlslid, String xmid, String qlrlb, String sfxxid);

    /**
     * 计算土地交易服务费信息
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     */
    void countTdjyfwf(String sfxmid, String gzlslid, String xmid);
}
