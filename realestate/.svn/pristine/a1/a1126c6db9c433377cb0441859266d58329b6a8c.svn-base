package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSysxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjkxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfSsxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.response.YrbClfskxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.response.YrbEwmjkxxResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.response.YrbQslxdhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.response.YrbQswspzhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.response.YrbZlfjsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.ReceiveSwxxRequestDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSwxxQO;
import cn.gtmap.realestate.common.core.qo.accept.TsswDataQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSwJkmxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSwxxVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 税务信息rest服务
 */
public interface BdcSwRestService {

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送业务信息到税务
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/tsswxx/{gzlslid}")
    Object tsSwxx(@PathVariable(value = "gzlslid") String gzlslid,@RequestParam(value = "beanName") String beanName);

    /**
     * 通过工作流实例ID获取税务信息，并推送税务信息
     * @param gzlslid  工作流实例ID
     * @param beanName  接口标识符
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 推送计税信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/tsjsxx")
    Object tsjsxx(@RequestParam(value = "gzlslid")String gzlslid, @RequestParam(value = "beanName") String beanName,
                  @RequestParam(value="xmid", required = false) String xmid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通一窗受理推送计税信息接口调整
     * @date : 2022/9/19 9:59
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/tsjsxx/ntycsl")
    Object ntYcslTsjsxx(@RequestParam(value = "gzlslid")String gzlslid,
                        @RequestParam(value="xmid", required = false) String xmid, @RequestParam(value = "fclx", required = false) String fclx);

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @param gxlx 更新类型 1：先删除原有税务信息，重新插入税务信息 2.根据纳税人识别号更新核税信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  税务信息查询
     */
    @Deprecated
    @GetMapping("/realestate-accept/rest/v1.0/sw/getswxx/{xmid}/{slbh}")
    QuerySwxxResponseDTO getSwxx(@PathVariable(value = "xmid") String xmid, @PathVariable(value = "slbh")String slbh,@RequestParam(value = "gxlx") String gxlx);

    /**
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  税务信息查询
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/getswxx")
    QuerySwxxResponseDTO getSwxx(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description  税务信息批量查询
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/getplswxx")
    Object getPlSwxx(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  处理保存税务查询结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/saveSwxxResponse/{xmid}/{gxlx}")
    void handleQuerySwxxResponse(@RequestBody QuerySwxxResponseDTO responseDTO, @PathVariable(value = "xmid") String xmid,@PathVariable(value = "gxlx")String gxlx);


    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @param gxlx 更新类型 1：先删除原有税务信息，重新插入税务信息 2.根据纳税人识别号更新核税信息     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 税务信息查询，合肥德宽版
     * @date : 2020/8/11 15:57
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/getswxx/{xmid}/{slbh}/dk")
    QuerySwxxResponseDTO getSwxxDk(@PathVariable(value = "xmid") String xmid, @PathVariable(value = "slbh")String slbh,@RequestParam(value = "gxlx") String gxlx);

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  税务申报取消作废
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/zfswxx/{xmid}/{slbh}")
    Object qxzfSwxx(@PathVariable(value = "xmid") String xmid,@PathVariable(value = "slbh")String slbh);


    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 取消作废税务信息 -合肥德宽版税务系统
     * @date : 2020/8/11 14:55
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/zfswxx/{xmid}/{slbh}/dk")
    Object qxzfSwxxDk(@PathVariable(value = "xmid") String xmid,@PathVariable(value = "slbh")String slbh);

    /**
     * @param slbh 受理编号
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 批量取消作废税务信息 -合肥德宽版税务系统
     * @date : 2022/9/26
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/plzfswxx/{slbh}/dk")
    Object plQxzfSwxxDk(@PathVariable(value = "slbh")String slbh);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据工作流实例ID税务申报取消作废
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/zfLcswxx/{processInsId}")
    Object qxzfLcSwxx(@PathVariable(value = "processInsId") String processInsId);

    /**
     * @param htbh 合同编号
     * @param wszt 完税状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据合同编号更新完税状态
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/{htbh}/{wszt}")
    Map updateWsztByHtbh(@PathVariable(value = "htbh") String htbh,@PathVariable(value = "wszt") String wszt);

    /**
     * @param gzlslid 工作流实例ID
     * @return 完税状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证是否完税
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/wszt/{gzlslid}")
    Boolean checkSfwsByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return QuerySwxxResponseDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 商品房完税状态
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/spfwszt/{gzlslid}")
    @Deprecated
    List<QuerySwxxResponseDTO> querySpfwszt(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcXmDO 不动产项目DO
     * @param gxlx 更新类型 1：先删除原有税务信息，重新插入税务信息 2.根据纳税人识别号更新核税信息 3有值更新无值插入
     * @return QuerySwxxResponseDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 商品房完税状态
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/spfwszt")
    QuerySwxxResponseDTO getSpfXmWszt(@RequestBody BdcXmDO bdcXmDO,@RequestParam(value = "gxlx") String gxlx);


    /**
     * @param gzlslid 工作流实例ID
     * @return String 最终结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取税务三要素核税信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/swsys")
    String getSwsysHsxx(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return Boolean 建行订单查询并缴库入库
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建行订单查询并缴库入库
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/yhddcx/jkrk")
    Object yhddcxAndJkrk(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * 调用一卡清接口，执行推送缴库
     * <p>接口调用方式： 1、工作流事件触发 2、规则验证时触发，验证推送缴库入库的状态 3、页面税费托管推送缴库按钮触发</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 推送缴库入库的状态
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/ykq/tsjkrk")
    CommonResponse ykqTsJkrk(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param bdcSfSsxxDTO 工作流实例ID
     * @return Boolean 建行缴库入库
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建行缴库入库
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/jkrk")
    Object yhjkrk(@RequestBody BdcSfSsxxDTO bdcSfSsxxDTO);

    /**
     * 调用一卡清接口，执行账户结清
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 账户结清结果
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/ykq/zhjq")
    CommonResponse ykqZhjq(@RequestParam(value = "processInsId") String processInsId);

    /**
     * @param tsswDataQO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询参数提供税务所需信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/gettsswxx")
    TsswDataDTO getTsswDataDTO(@RequestBody TsswDataQO tsswDataQO);

    /**
     * @param bdcSwxxDTOList 税务信息
     * @param htbh 合同编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据合同编号保存税务信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/saveSwxxDTO")
    void saveSwxxDTOByHtbh(@RequestBody List<BdcSwxxDTO> bdcSwxxDTOList,@RequestParam(value = "htbh") String htbh);

    /**
     * @param slbh 受理编号
     * @param bdcSlHsxxDOList 需要更新的税务信息集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据受理编号更新税务三要素
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/updateSwsys")
    void updateSwsysByNsrbhAndSlbh(@RequestBody List<BdcSlHsxxDO> bdcSlHsxxDOList, @RequestParam(value = "slbh") String slbh);

    /**
     * @param receiveSwxxRequestDTO 不动产受理税务信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 接收第三方的税务信息 包括hsxx和hsxxMx
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/insertOrUpdateSwxx")
    Map insertOrUpdateSwxx(@RequestBody ReceiveSwxxRequestDTO receiveSwxxRequestDTO);

    /**
     * 接收第三方的缴税状态，根据外网受理编号进行更新
     * @param receiveSwxxRequestDTO 不动产受理税务信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 接收缴税状态的更新结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/dsf/jszt")
    CommonResponse jsDsfJszt(@RequestBody ReceiveSwxxRequestDTO receiveSwxxRequestDTO);

    /**
     * 获取完税信息，根据完税状态处理完税信息
     * <p>未完税：则给与提示；已完税：下载电子税票;</>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 完税信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/getWsxx/{gzlslid}")
    Object getAndHandleWsxx(@PathVariable(value = "gzlslid") String gzlslid) throws IOException;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送税务附件材料
     * @date : 2020/10/27 10:46
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/fjcl/{gzlslid}")
    void tsFjcl(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * 根据sply和processInsId判断当前流程是否完税 完税页面要给出相应提示
     * @param processInsId
     * @param sply
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @return 是否需要提示 boolean
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/showSpWsXx")
    Object showSpWsXx(@RequestParam("processInsId") String processInsId, @RequestParam("sply") String sply);

    /**
     * 通过工作流实例ID获取税务信息，并推送税务信息
     * @param gzlslid  工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 推送核税信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/tshsxx")
    Object tshsxx(@RequestParam(value = "gzlslid")String gzlslid);

    /**
     * @param slbh 受理编号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据受理编号保存税务三要素信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/sys/slbh")
    Integer insertSwsys(@RequestBody List<BdcSlSysxxDO> bdcSlSysxxDOList, @RequestParam(value = "slbh")String slbh);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据受理编号获取三要素信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/sysxx/list/{slbh}")
    List<BdcSlSysxxDO> listSysxxBySlbh(@PathVariable(value = "slbh") String slbh);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 一人办件获取税务信息
     * @date : 2022/8/8 9:26
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/yrbj")
    Object getYrbjSwxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "beanName") String beanName,
                       @RequestParam(value = "xmid", required = false) String xmid, @RequestParam(value = "fwlx", required = false) String fwlx);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取申报单信息
     * @date : 2022/8/8 11:12
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/sbd")
    void getSwSbd(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "fwlx", required = false) String fwlx, @RequestParam(value = "htbh") String htbh);

    /**
     * 获取存量房计税信息，并更新到核税信息表中
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 存量房计税信息DTO
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/clf/jsxx")
    YrbClfskxxhqDTO getClfJsxx(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * 获取增量房计税信息，并更新到核税信息表中
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 增量房计税信息DTO
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/zlf/jsxx")
    YrbZlfjsxxDTO getZlfJsxx(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 确认申报单
     * @date : 2022/8/8 11:39
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/qrsbd")
    Object qrSbd(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "fwlx", required = false) String fwlx);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税票
     * @date : 2022/8/8 14:13
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/yrbjspxx")
    CommonResponse getSpxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "jszt") String jszt,
                 @RequestParam(value = "fwlx", required = false) String fwlx,
                 @RequestParam(value = "htbh") String htbh);

    /**
     * 获取契税完税信息，调用税务【A009】获取契税完税
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @return 获取契税完税结果
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/hqqsws")
    CommonResponse hqqsws(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取缴税二维码
     * @date : 2022/8/10 18:11
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/yrbjsewm")
    List<BdcSwJkmxxVO> getJsewm(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "htbh", required = false) String htbh, @RequestParam(value = "fwlx", required = false) String fwlx);

    /**
     * @param bdcSwxxQO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取税务契税完税凭证【A020】
     * @date : 2022/9/14 18:11
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/getQswspz")
    Object getQswspz(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * @param bdcSwxxQO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取盐城税务信息
     * @date : 2022/9/14 18:11
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/getQslxd")
    Object getQslxd(@RequestBody BdcSwxxQO bdcSwxxQO);
    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description  处理保存契税完税凭证查询结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/saveQswspzResponse/{xmid}/{gxlx}")
    void handleQswspzResponse(@RequestBody YrbQswspzhqDTO yrbQswspzhqDTO, @PathVariable(value = "xmid") String xmid,
                              @PathVariable(value="gxlx") String gxlx);

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 处理保存契税联系单查询结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/saveQslxdResponse/{xmid}")
    void handleQslxdResponse(@RequestBody YrbQslxdhqDTO yrbQslxdhqDTO, @PathVariable(value = "xmid") String xmid);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 接口5推确认结果
     * @date : 2022/9/21 11:48
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/qrjg")
    void qrswjg(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税务完税信息
     * @date : 2022/9/21 14:05
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/wsxx")
    void swWsxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "htbh") String htbh);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 一窗受理获取税票
     * @date : 2022/9/21 14:23
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/spxx")
    String getSwSp(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description  税务信息列表分页
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/page")
    Page<BdcSwxxVO> listSwxxByPage(Pageable pageable, @RequestParam(name = "bdcSwxxQOStr", required = false) String bdcSwxxQOStr);

    /**
     * @description 获取税费信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/20 15:06
     * @param bdcSwxxQO
     * @return Object
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/getYhsfxx")
    Object getYhsfxx(BdcSwxxQO bdcSwxxQO);

    /**
     * 通知税务任务状态【A003】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 通知结果 {"fhm":"返回码","fhxx":"返回信息","fwuuid":"房屋编号","htbh":"合同编号","jyuuid":"交易编号","sjbh":"收件编号"}
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/tz/rwzt")
    Object tzSwRwzt(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * 房产交易信息申报信息(pdf文件格式)【A014】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/sbdwj")
    void getSwxxSbdwj(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * 获取房产交易待缴款信息清单【A017】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<BdcSlYjkxxDO> 待缴款信息集合
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/jkxxqd")
    List<BdcSlYjkxxDO> getFcjyJkxxQd(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * 生成缴款信息二维码内容【A018】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/jkewm")
    Object getFcjyJkEwm(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * 获取税务缴款状态【A019】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return YrbEwmjkxxResponse 缴款状态查询结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/jkzt")
    YrbEwmjkxxResponse getSwJkzt(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * 获取税务申报状态【A010】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return YrbEwmjkxxResponse 缴款状态查询结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/hqswsbzt")
    CommonResponse hqswsbzt(@RequestBody BdcSwxxQO bdcSwxxQO);

}
