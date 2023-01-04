package cn.gtmap.realestate.accept.service;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 税务信息
 */
public interface BdcSwService {

    /**
     * @param gzlslid 工作流实例ID
     * @param beanName 接口定义名称
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送业务信息到税务
     */
    Object tsSwxx(String gzlslid,String beanName);

    /**
     * 通过工作流实例ID获取税务信息，并推送税务信息
     * @param gzlslid  工作流实例ID
     * @param beanName 接口标识符
     * @param xmid 项目ID
     * @return 推送数据结果
     */
    Object tsjsxx(String gzlslid, String beanName, String xmid);


    /**
     * 通过工作流实例ID获取税务信息，并推送税务信息
     * @param gzlslid  工作流实例ID
     * @param xmid 项目ID
     * @return 推送数据结果
     */
    Object ntYcslTsjsxx(String gzlslid,String xmid, String fclx);

    /**
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  税务信息查询
     */
    QuerySwxxResponseDTO getSwxx(BdcSwxxQO bdcSwxxQO);

    /**
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description  税务信息批量查询
     */
    Object getPlSwxx(@RequestBody BdcSwxxQO bdcSwxxQO);

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @param gxlx 更新类型 1：先删除原有税务信息，重新插入税务信息 2.根据纳税人识别号更新核税信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 税务系信息查询合肥德宽版
     * @date : 2020/8/11 16:00
     */
    QuerySwxxResponseDTO getSwxxDk(String xmid,String slbh,String gxlx);

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 税务申报取消作废
     */
    Object qxzfSwxx(String xmid, String slbh);

    /**
     * @param xmid 项目ID
     * @param slbh 受理编号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 取消作废税务信息  合肥德宽版系统
     * @date : 2020/8/11 14:48
     */
    Object qxzfSwxxDK(String xmid, String slbh);

    /**
     * @param slbh 受理编号
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 批量取消作废税务信息 -合肥德宽版税务系统
     * @date : 2022/9/26
     */
    Object plQxzfSwxxDK( String slbh);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID税务申报取消作废
     */
    Object qxzfLcSwxx(String gzlslid);


    /**
     * @param responseDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理税务查询结果
     */
    void handleQuerySwxxResponse(QuerySwxxResponseDTO responseDTO, String xmid,String gxlx);

    /**
     * @param htbh 合同编号
     * @param wszt 完税状态
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据合同编号更新完税状态
     */
    Map updateWsztByHtbh(String htbh,String wszt);

    /**
     * @param gzlslid 工作流实例ID
     * @return 完税状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID验证是否完税
     */
    Boolean checkSfwsByGzlslid(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return QuerySwxxResponseDTO 完税信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询商品房完税状态
     */
    List<QuerySwxxResponseDTO> querySpfwszt(String gzlslid);

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param bdcXmDO 项目信息
     * @param gxlx 更新类型 1：先删除原有税务信息，重新插入税务信息 2.根据纳税人识别号更新核税信息
     * @return QuerySwxxResponseDTO 接口信息
     * @description 查询商品房项目完税状态
     */
    QuerySwxxResponseDTO getSpfXmWszt(BdcXmDO bdcXmDO,String gxlx);


    /**
     * @param gzlslid 工作流实例ID
     * @return String 最终结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取税务三要素核税信息
     */
    String getSwsysHsxx(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return Boolean 建行订单查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建行订单查询并缴库入库
     */
    Object yhddcxAndJkrk(String gzlslid);

    /**
     * @param bdcSfSsxxDTO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 银行缴库入库
     */
    Object yhjkrk(BdcSfSsxxDTO bdcSfSsxxDTO);

    /**
     * 调用一卡清接口，执行推送缴库
     * <p>接口调用方式： 1、工作流事件触发 2、规则验证时触发，验证推送缴库入库的状态</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 推送缴库入库的状态
     */
    CommonResponse ykqTsJkrk(String gzlslid);

    /**
     * 调用一卡清接口，执行账户结清
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 账户结清结果
     */
    CommonResponse ykqZhjq(String gzlslid);

    /**
     * @param tsswDataQO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询参数提供税务所需信息
     */
    TsswDataDTO getTsswDataDTO(TsswDataQO tsswDataQO);

    /**
     * @param bdcSwxxDTOList
     * @param xmid
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存税务信息
     */
    void saveOrUpdateSwxxDTO(List<BdcSwxxDTO> bdcSwxxDTOList, String xmid);

    /**
     * @param bdcSwxxDTOList 税务信息
     * @param htbh 合同编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据合同编号保存税务信息
     */
    void saveSwxxDTOByHtbh(List<BdcSwxxDTO> bdcSwxxDTOList, String htbh);

    /**
     * @param slbh 受理编号
     * @param bdcSlHsxxDOList 需要更新的税务信息集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据受理编号更新税务三要素
     */
    void updateSwsysByNsrbhAndSlbh(List<BdcSlHsxxDO> bdcSlHsxxDOList,String slbh);

    /**
     * @param receiveSwxxRequestDTO 需要更新的税务信息集合
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 接受德宽提供的税务信息
     */
    Map insertOrUpdateSwxx(ReceiveSwxxRequestDTO receiveSwxxRequestDTO);

    /**
     * 接收第三方的缴税状态，根据外网受理编号更新
     * @param receiveSwxxRequestDTO 不动产受理税务信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 接收缴税状态的更新结果
     */
    CommonResponse jsDsfJszt(ReceiveSwxxRequestDTO receiveSwxxRequestDTO);

    /**
     * 获取完税信息，根据完税状态处理完税信息
     * <p>未完税：则给与提示；已完税：下载电子税票;</>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 完税信息
     */
    Object getAndHandleWsxx(String gzlslid) throws IOException;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送附件材料到合肥税务
     * @date : 2020/10/27 10:48
     */
    void tsFjcl(String gzlslid);

    /**
     * 根据sply和processInsId判断当前流程是否完税 完税页面要给出相应提示
     *
     * @param processInsId
     * @param sply
     * @return 是否需要提示 boolean
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    Object showSpWsXx(String processInsId,String sply);

    /**
     * 推送核税信息
     * @param gzlslid 工作流实例ID
     * @return 推送结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Object tshsxx(String gzlslid);

    /**
     * @param slbh 受理编号
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据受理编号保存税务三要素信息
     */
    Integer insertSwsys(List<BdcSlSysxxDO> bdcSlSysxxDOList, String slbh);

    /**
     * @param slbh 受理编号
     * @return 三要素信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据受理编号获取三要素信息
     */
    List<BdcSlSysxxDO> listSysxxBySlbh(String slbh);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 一人办件税务信息
     * @date : 2022/8/8 9:31
     */
    Object getYrbjSwxx(String gzlslid, String beanName, String xmid, String fwlx);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税务申报单信息
     * @date : 2022/8/8 11:14
     */
    void getSwSbdxx(String gzlslid, String fwlx, String htbh);

    /**
     * 获取存量房计税信息内容，并保存核税信息内容
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yoayi@gtmap.cn">yaoyi</a>
     * @return 存量房计税信息
     */
    YrbClfskxxhqDTO getClfJsxx(BdcSwxxQO bdcSwxxQO);

    /**
     * 获取增量房计税信息内容，并保存核税信息内容
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yoayi@gtmap.cn">yaoyi</a>
     * @return 增量房计税信息
     */
    YrbZlfjsxxDTO getZlfJsxx(BdcSwxxQO bdcSwxxQO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 确认申报单
     * @date : 2022/8/8 11:40
     */
    Object qrSbd(String gzlslid, String fwlx);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税票
     * @date : 2022/8/8 14:15
     */
    CommonResponse getSpxx(String gzlslid, String jszt, String fwlx, String htbh);

    /**
     * 获取契税完税信息，调用税务【A009】获取契税完税
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    CommonResponse hqqsws(String gzlslid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取缴税二维码
     * @date : 2022/8/10 18:14
     */
    List<BdcSwJkmxxVO> getJsewm(String gzlslid, String htbh, String fwlx);

    /**
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税务契税完税凭证【A020】
     * @date : 2022/8/10 18:14
     */
    Object getQswspz(BdcSwxxQO bdcSwxxQO);

    /**
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 契税联系单获取
     * @date : 2022/8/10 18:14
     */
    Object getQslxd(BdcSwxxQO bdcSwxxQO);

    /**
     * @param yrbQswspzhqDTO 契税完税凭证DTO
     * @param xmid 项目ID
     * @param gxlx 更新类型
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 处理契税完税凭证
     */
    void handleQswspzResponse(YrbQswspzhqDTO yrbQswspzhqDTO, String xmid, String gxlx);

    /**
     * @param yrbQslxdhqDTO 契税联系单DTO
     * @param xmid 项目ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 处理契税联系单
     */
    void handleQslxdResponse(YrbQslxdhqDTO yrbQslxdhqDTO, String xmid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 接口5推确认结果
     * @date : 2022/9/21 11:48
     */
    void qrswjg(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @param htbh 合同编号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取税务完税信息
     * @date : 2022/9/21 14:05
     */
    void swWsxx(String gzlslid, String htbh);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 一窗受理获取税票
     * @date : 2022/9/21 14:23
     */
    String getSwSp(String gzlslid);

    /**
     * @param bdcSwxxQOStr 查询参数
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 获取税票信息列表
     */
    Page<BdcSwxxVO> listSwxxByPage(Pageable pageable, String bdcSwxxQOStr);

    /**
     * @description 获取税费信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/20 15:06
     * @param bdcSwxxQO 税务信息查询QO
     */
    Object getYhsfxx(BdcSwxxQO bdcSwxxQO);

    /**
     * 通知税务任务状态【A003】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 通知结果 {"fhm":"返回码","fhxx":"返回信息","fwuuid":"房屋编号","htbh":"合同编号","jyuuid":"交易编号","sjbh":"收件编号"}
     */
    Object tzSwRwzt(BdcSwxxQO bdcSwxxQO);

    /**
     * 房产交易信息申报信息【A014】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void getSwxxSbdwj(BdcSwxxQO bdcSwxxQO);

    /**
     * 获取房产交易待缴款信息清单【A017】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<BdcSlYjkxxDO> 待缴款信息
     */
    List<BdcSlYjkxxDO> getFcjyJkxxQd(BdcSwxxQO bdcSwxxQO);

    /**
     * 生成缴款信息二维码内容【A018】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Object getFcjyJkEwm(BdcSwxxQO bdcSwxxQO);

    /**
     * 获取税务缴款状态【A019】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    YrbEwmjkxxResponse getSwJkzt(BdcSwxxQO bdcSwxxQO);

    /**
     * 获取税务申报状态【A020】
     * @param bdcSwxxQO 税务信息查询QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return YrbEwmjkxxResponse 缴款状态查询结果
     */
    CommonResponse hqswsbzt(BdcSwxxQO bdcSwxxQO);

}
