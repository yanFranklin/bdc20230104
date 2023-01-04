package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxCzrzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfSsxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfxmJsQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSfxxhzVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSfxxmxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSfxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcdjjfglVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/12
 * @description 不动产受理收费信息rest服务
 */
public interface BdcSlSfxxRestService {
    /**
     * @param sfxxid 收费信息ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID获取不动产受理收费信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/{sfxxid}")
    BdcSlSfxxDO queryBdcSlSfxxBySfxxid(@PathVariable(value = "sfxxid") String sfxxid);


    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取不动产受理收费信息（不包含已作废的数据）
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/list/gzlslid/{gzlslid}")
    List<BdcSlSfxxDO> listBdcSlSfxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取不动产受理收费信息（包含作废的数据）
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/list/gzlslid/{gzlslid}/bhzf")
    List<BdcSlSfxxDO> listBdcSlSfxxByGzlslidBhzf(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * 根据不动产收费信息DO获取不动产受理收费信息
     * @param bdcSlSfxxDO 收费信息DO
     * @return 不动产受理收费信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/sfxxdo")
    List<BdcSlSfxxDO> queryBdcSlSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * @param pageable
     * @return bdcSlSfxxDOJSON
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页查询 收费信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/page")
    Page<BdcSlSfxxVO> listBdcSlSfxxByPage(Pageable pageable, @RequestParam(name = "bdcSlSfxxQOJSON", required = false) String bdcSlSfxxQOJSON);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询 收费信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/list")
    List<BdcSlSfxxVO> listBdcSlSfxx(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查询 银行收费信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/list/yh")
    List<BdcSlSfxxVO> listBdcSlSfxxYh(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param xmid 项目ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取不动产受理收费信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/list/xmid/{xmid}")
    List<BdcSlSfxxDO> listBdcSlSfxxByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理收费信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/")
    BdcSlSfxxDO insertBdcSlSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理收费信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/sfxx/")
    Integer updateBdcSlSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * 根据 sfxxid 更新不动产收费信息和关联的收费项目
     * @param sfxxid  收费信息ID
     * @param bdcSlSfxxDO  更新内容
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping("/realestate-accept/rest/v1.0/sfxx/sfxxid")
    void updateBdcSlSfxxIdWithSfxm(@RequestParam(name="sfxxid") String sfxxid, @RequestBody BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存或更新不动产受理收费信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/sfxx/saveOrUpdate")
    Integer saveOrUpdateBdcSlSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * @param bdcSlSfxxDOList
     * @return Integer
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 批量更新收费信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/pl/")
    void updateBdcSlSfxxList(@RequestBody List<BdcSlSfxxDO> bdcSlSfxxDOList);

    /**
     * @param sfxxid 收费信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID删除不动产受理收费信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sfxx/{sfxxid}")
    Integer deleteBdcSlSfxxBySfxxid(@PathVariable(value = "sfxxid") String sfxxid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID删除不动产受理收费信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sfxx/list/{gzlslid}")
    Integer deleteBdcSlSfxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);


    /**
     * @param gzlslid 工作流实例id
     * @return 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 初始化收费信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/csh")
    BdcSlSfxxDO cshSfxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid", required = false) String xmid);

    /**
     * @param gzlslid 工作流实例id
     * @return 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 初始化非登记流程收费信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/yccsh/{gzlslid}")
    BdcSlSfxxDO cshFdjlcSfxx(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param xmid 项目ID
     * @param gzlslid 工作流实例id
     * @return 不动产受理收费信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织页面收费信息内容
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/generate")
    BdcSlSfxxDTO generateSfxx(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid);

    /**
     * @param xmid 项目ID
     * @param gzlslid 工作流实例id
     * @return 不动产受理收费信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织页面收费信息内容(南通)
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/generate/nt")
    BdcSlSfxxDTO generateSfxxNt(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [gzlslid, xmid]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO>
     * @description 查询收费信息（包含作废数据）南通
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/bhzf/nt")
    List<BdcSlSfxxDO> queryBdcSlSfxxBhzf(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid);

    /**
     * @param bdcSlSfxxDO 受理收费信息
     * @return 不动产受理收费信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 更新受理收费信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/gxSfxxSfzt")
    Boolean gxSfxxSfzt(@RequestBody BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * @param sfxxid 收费信息id
     * @param qlrlb 权利人类别
     * @param gzlslid 工作流实例id
     * @return 不动产受理收费信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 推送收费信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/gxSfxxJfsbm")
    Boolean gxSfxxJfsbm(@RequestParam(name = "sfxxid") String sfxxid,@RequestParam(name = "qlrlb") String qlrlb,@RequestParam(name = "gzlslid") String gzlslid);

    /**
     * 调用非税接口推送收费信息并生成缴费书编码信息
     * @param sfxxid 收费信息id
     * @param qlrlb 权利人类别
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/fs/taxNotice")
    void createTaxNotice(@RequestParam(name = "sfxxid") String sfxxid,@RequestParam(name = "qlrlb") String qlrlb,@RequestParam(name = "gzlslid") String gzlslid);

    /**
     * @return 收费项目名称
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取收费项目名称
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/sfxmmc")
    Object getSfxmmc();

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 根据工作流实例ID获取合计不为空的不动产受理收费信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/list/gzlslid/jefk/{gzlslid}")
    List<BdcSlSfxxDO> listBdcSlSfxxByGzlslidHjfk(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid  项目ID
     * @return 收费基本信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  组织一窗流程页面收费基本信息内容
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/generate/ycsl")
    BdcSlSfxxDTO generateYcslSfxx(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid") String xmid);

    /**
     * @param sfxxid 收费信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据收费信息ID推送待缴费信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/tsdjfxx/{sfxxid}")
    BdcTsdjfxxResponseDTO tsdjfxx(@PathVariable(value = "sfxxid") String sfxxid);

    /**
     * @param sfxxid 收费信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据收费信息ID查询收费信息并推送待缴费信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/cxtsdjfxx/{sfxxid}")
    BdcTsdjfxxResponseDTO cxtsdjfxx(@PathVariable(value = "sfxxid") String sfxxid, @RequestParam(name = "tsly") String tsly, @RequestParam(name = "pjdm", required = false) String pjdm, @RequestParam(name = "sftdsyj", required = false) boolean sftdsyj);

    /**
     * @param sfxxid 收费信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">caolu</a>
     * @description 如皋市根据收费信息ID查询收费信息并推送待缴费信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/rgtsdjfxx/{sfxxid}")
    CommonResponse rgtsdjfxx(@PathVariable(value = "sfxxid") String sfxxid, @RequestParam(name = "qlrlb") String qlrlb,
                             @RequestParam(name="currentUserName", required = false) String currentUserName,
                             @RequestParam(name = "gzlslid") String gzlslid);

    /**
     * 根据收费信息ID查询收费信息并作废待缴费信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 作废结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/zfdjfxx")
    CommonResponse zfdjfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * 生成电子发票
     * @param sfxxid 收费信息ID
     * @return 电子发票信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/hkdzfpxx/{sfxxid}")
    Object hkdzfpxx(@PathVariable(value = "sfxxid")String sfxxid);
    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 下载电子发票
      */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/dzfpxx/{sfxxid}")
    Object getDzfpxx(@PathVariable(value = "sfxxid")String sfxxid);

    /**
     * 第三方根据外网受理编号，获取非税电子发票信息
     * @param bdcDsfSfxxDTO 第三方收费信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return [{"sfxxid": "", "dzfpBase64":""}] 电子发票信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/dsf/dzfpxxxz")
    CommonResponse dsfDzfpxxxz(@RequestBody BdcDsfSfxxDTO bdcDsfSfxxDTO);

    /**
     * @param slbh 受理编号
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据受理编号和项目ID推送待缴费信息外网生成支付订单
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/tsdjfxxBySlbh")
    BdcTsdjfxxResponseDTO tsdjfxxBySlbh(@RequestParam(name = "slbh") String slbh, @RequestParam(name = "xmid") String xmid);

    /**
     * @param gzlslid 工作流实例id
     * @return
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description  根据工作流实例id查询收费页面是否点击保存按钮
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/bczt/{gzlslid}")
    Integer queryBczt(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcSfSsxxQO 获取缴费明细查询QO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid和申请人类别提供缴费缴税信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/sfssxx")
    BdcSfSsxxDTO queryBdcSfSsxxDTO(@RequestBody BdcSfSsxxQO bdcSfSsxxQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取月结订单缴费明细
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/sfssxx/list")
    List<BdcSfSsxxDTO> listYjBdcSfSsxxDTO(@RequestBody BdcSfSsxxQO bdcSfSsxxQO);

    /**
     * @param bdcSfSsxxDTO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新收费收税状态
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/updateSfSszt")
    void updateSfSszt(@RequestBody BdcSfSsxxDTO bdcSfSsxxDTO);

    /**
     * @param gzlslid 工作流实例ID
     * @return boolean
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 回传收费收税状态（缴费状态）给大云信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/sfssxx/{gzlslid}/process")
    boolean saveSfssxxZtToProcess(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 判断当前流程是否属于线上缴费
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/isxsjf/{gzlslid}")
    boolean checkIsXsjf(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新收费收税银行缴库入库状态
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/updateSlSfSsxxYhJkrkzt")
    void updateSlSfSsxxYhJkrkzt(@RequestBody BdcYhjkrkztDTO bdcYhjkrkztDTO);

    /**
     *  校验缴费缴库情况
     *  <p>通过工作流实例ID<i>gzlslid</i>,校验是否为线上缴费，是否已缴库。
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param:  gzlslid  工作流实例ID
     * @return: Integer  {@code -1}为线下缴费，{@code 0}为未缴库，
     *                   {@code 1}为已缴库
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/jfqk/{gzlslid}")
    Integer checkJfqk(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     *  校验收费情况
     *  <p>通过工作流实例ID
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param:  gzlslid  工作流实例ID
     * @return: Integer  {@code 2}为已缴费，{@code 1}为未缴费
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/sfqk/{gzlslid}")
    Integer checkSfqk(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return: Integer {@code 0} 未上传凭证 {@code 1} 已上传凭证 {@code 2} 线上缴费，不做验证
     * @description 验证线下缴费是否已上传税费缴纳凭证
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/gzyz/xxJfPz/{gzlslid}")
    Integer checkXxJfPz(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @retur 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费信息退款申请
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/sfxxTksq")
    BdcSlSfxxDO sfxxTksq(@RequestParam(name = "gzlslid") String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询退款状态
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/querySfxxTkqk")
    BdcSlSfxxDO querySfxxTkqk(@RequestParam(name = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转发自动修改收费状态为已核验
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/autoUpdateSfztYhy/{processInsId}")
    void autoUpdateSfztYhy(@PathVariable(name = "processInsId") String gzlslid);

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除当前流程的收费项目和收费信息
     * @date : 2020/5/9 14:42
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sfxx/sfxm")
    void deleteSfxmAndSfxx(@RequestBody BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * 通知政融平台POS缴费成功
     * <p>通过<code>PosJfDTO</code>参数中的参考号、商户代码、终端号判断：
     * 1、有参数，为POS缴费成功，需记录参数并通知政融平台
     * 2、无参数，为手动触发通知政融，需获取接口参数再通知
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param posJfDTO POS缴费结果信息
     * @return 通知结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/tzzrpt")
    Object tzzrpt(@RequestBody PosJfDTO posJfDTO);

    /**
     * 根据xmid分组查询项目的收费金额（权利人、义务人收费金额求和）
     * @param xmids 项目ID集合
     * @return map key: xmid  value: 收费合计金额
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/hj/xmids")
    Map<String, Object> queryHjGroupByXmids(@RequestBody List<String> xmids);

    /**
     * 提供自助查询机查询收费信息接口（盐城）
     *
     * @param zzcxjSfxxDTO 收费信息查询接口
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sfxx/zzcxj")
    List<BdcSlSfxxZzcxjResponseDTO> zzcxjQuerySfxx(@RequestBody ZzcxjSfxxDTO zzcxjSfxxDTO);


    /**
     * @param bdcSlSfxxQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 发票查询收费信息
     * @date : 2020/11/27 15:15
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sfxx/fpcx")
    List<BdcSlSfxxDO> listFpcxSfxx(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);


    /**
     * @param bdcSlSfxxQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 发票查询收费信息-批量查询返回收费信息和收费项目信息
     * @date : 2020/11/27 15:15
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sfxx/fpcx/pl")
    List<BdcSfxxDTO> listFpcxSfxxSfxm(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 登记缴费信息管理台账
     * @param pageable 分页
     * @param bdcSlSfxxQOJson 查询qo JSON
     * @return
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/sfxx/xm/djjfgl")
    Page<BdcdjjfglVO> djjfgl(Pageable pageable,
                             @RequestParam(name = "bdcSlSfxxQOJson", required = false) String bdcSlSfxxQOJson);


    /**
     * 缴费 更新收费状态 保存收费操作日志
     * @param jfList
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sfxx/jfcz")
    void jfcz(@RequestBody List<BdcdjjfglVO> jfList,@RequestParam("type") String type);


    /**
     * 非税开票
     * @param pageable 分页
     * @return
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/sfxx/fskp")
    Page<BdcSlSfxxDO> fskp(Pageable pageable,@RequestParam("gzlslid") String gzlslid);

    /**
     * 汇总缴费数据
     * @param bdcSlSfxxQO  收费信息查询QO
     * @return {BdcSfxxhzVO} 汇总缴费信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sfxx/hzjfsj")
    BdcSfxxhzVO hzjfsj(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 查询缴费数据明细
     * @param bdcSlSfxxQO  收费信息查询QO
     * @return {BdcSfxxmxVO} 缴费数据明细
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/sfxx/mxjfsj")
    BdcSfxxmxVO mxjfsj(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param bdcSlSfxxCzrzDO 不动产受理收费操作人信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 新增不动产受理收费操作人信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxxczr/")
    BdcSlSfxxCzrzDO insertBdcSlSfxxCzrz(@RequestBody BdcSlSfxxCzrzDO bdcSlSfxxCzrzDO);

    /**
     * 查询收费缴款情况,并更新收费缴款信息
     * @param bdcSlSfxxQO 不动产收费信息QO
     * @return {BdcSlSfxxDO} 不动产收费信息缴款情况
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/jkqk")
    BdcSlSfxxDO queryJkqkAndUpdate(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 查询非税是否已缴费,并更新收费缴款信息
     * @param bdcqzh 不动产权证号
     * @return 不动产收费信息缴款情况
     * @author <a href="mailto:yaoyi@gtmap.cn">caolu</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/fsjf")
    CommonResponse queryFsjfqk(@RequestParam(name= "bdcqzh") String bdcqzh);

    /**
     * 工作流事件，用于登簿时，更新收费信息表中的登簿时间
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/dbsj")
    void modifySlSfxxDbsj(@RequestParam(name= "processInsId") String processInsId);


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 自动计算收费信息 合计和收费项目数据 ---南通逻辑
     * @date : 2021/7/28 9:39
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/autocount/{gzlslid}")
    void autoCountSfxxNt(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid,djxl
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取南通收费项目数量
     * @date : 2021/7/29 10:03
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/sfxmsl/{gzlslid}")
    BdcSlSfxmSlDTO querySfxmSl(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(name = "djxl") String djxl);

    /**
     * @param  qxdm 区县代码
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取收费信息配置
     * @date : 2021/7/29 10:15
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/mrsfxxpz/{qxdm}")
    Map queryMrsfxxPz(@PathVariable(value = "qxdm") String qxdm);

    /**
     * 查询缴款情况状态
     *
     * @param gzlslid 工作流实例ID
     * @param qlrlb   权利人类别
     * @return 收费状态
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/cxsfzt")
    Integer querySfzt(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "qlrlb") String qlrlb);

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据相关查询条件查询推送收费信息管理台账数据
     * @date : 2021/9/13 14:34
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/tssfgl/page")
    Page<BdcdjjfglVO> listTssfglByPage(Pageable pageable, @RequestParam(value = "json", required = false) String json);


    /**
     * @param bdcSlSfxxQO 查询参数
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询推送收费信息 不分页
     * @date : 2021/9/15 9:17
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/tssfgl")
    List<BdcdjjfglVO> listTssfxx(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 抵押批量推送收费信息
     * @date : 2021/9/15 9:08
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/cxtsdyaqsf/{gzlslid}")
    BdcTsdjfxxResponseDTO cxtsDyaqSfxx(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据缴费人姓名判断是否月结
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/sfyj")
    Integer querySfyjByJfrxm(@RequestParam(value = "jfrxm", required = false) String jfrxm, @RequestParam(value = "gzldyid", required = false) String gzldyid);


    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据相关查询条件查询未缴费数据 fph 为空，缴费书编码不为空且未缴费的数据
     * @date : 2021/9/13 14:34
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/wjftz/page")
    Page<BdcSlWjfDTO> listSlWjfByPage(Pageable pageable, @RequestParam(value = "json", required = false) String json);

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算未缴费金额
     * @date : 2021/11/4 23:12
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/wjftz/hj")
    Map countWjfJe(@RequestParam(value = "json", required = false) String json);

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新收费状态
     * @date : 2021/11/5 10:59
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/wjf/gxsfzt")
    List<BdcSlWjfDTO> listSlWjfxx(@RequestParam(value = "json", required = false) String json);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自动计算收费信息收费项目()
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/js")
    void autoCountSfxxSfxm(@RequestParam("processInsId") String processInsId) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转发自动计算收费信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/js/zf")
    void autoCountSfxxSfxmForZf(@RequestParam("processInsId") String processInsId) throws Exception;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理收费单页面收费项目自动计算信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/js/sfxm")
    List<BdcSlSfxmDO> countSfxm(@RequestBody BdcSfxmJsQO bdcSfxmJsQO) throws Exception;

    /**
      * @param bdcDsfSfxxDTO 第三方收费信息
      * @return 返回信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 接收第三方收费信息保存并推送
      */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/save/ts")
    CommonResponse jsSfxxSaveAndTs(@RequestBody BdcDsfSfxxDTO bdcDsfSfxxDTO);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 收费模式是否区县模式
      */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/nantong/sfmode")
    String nantongSfMode(@RequestParam("processInsId") String processInsId, @RequestParam("configName") String configName);

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新收费状态操作时间
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/updateSlSfxxSfztczsj/{processInsId}")
    void updateSlSfxxSfztczsj(@PathVariable(name = "processInsId") String processInsId);

    /**
     * 工作流事件：审核转发缮证时触发，直接更新月结银行的收费状态为已收费，更新收费时间
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 月结银行更新收费状态操作时间
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/yjyh/updateSfztAndSfsj")
    void yjyhUpdateSfztAndSfsj(@RequestParam(name = "processInsId") String processInsId);


    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算土地使用权交易服务费
     * @date : 2022/4/24 14:30
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/tdysqjyfwf")
    List<BdcSlSfxmDO> countTdsyqJyfwf(@RequestBody List<BdcSlSfxmDO> bdcSlSfxmDOList, @RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "sfcxjs") boolean sfcxjs);


    /**
     * @param bdcSlSfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据受理编号查询收费信息
     * @date : 2022/8/1 10:15
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/qo")
    List<BdcSlSfxxDO> listBdcSlSfxxBySlbh(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 调用互联网缴费状态查询信息，并更新缴费状态、完税状态
     * @param processInsId 工作流实例ID
     * @return
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/hlw/jfzt")
    void queryHlwJfzt(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送税费
     * @date : 2022/9/27 10:50
     */
    @GetMapping("/realestate-accept/rest/v1.0/sfxx/sftj")
    Object bdcSftj(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "xmid", required = false) String xmid,
                   @RequestParam(name = "qlrlb") String qlrlb, @RequestParam(name = "sfxxid", required = false) String sfxxid);

    /**
     * @param
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 更新不动产受理收费信息并且根据收费状态推送登记流程
     * @date : 2022/9/26
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/updateBdcSlSfxxAndTsdjlc")
    Integer updateBdcSlSfxxAndTsdjlc(@RequestBody BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * 查询不动产收费信息和收费项目信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlSfxxQO 不动产受理收费信息查询QO
     * @return 收费信息和收费项目信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sfxx/sfxmxx/all")
    List<BdcSfxxDTO> queryBdcSlSfxxAndSfxm(@RequestBody BdcSlSfxxQO bdcSlSfxxQO);
}
