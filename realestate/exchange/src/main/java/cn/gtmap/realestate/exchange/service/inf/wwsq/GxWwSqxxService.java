package cn.gtmap.realestate.exchange.service.inf.wwsq;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO;
import cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.FwxxbybdcdyhResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.dydjxxcx.request.DydjxxcxCxQO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.dydjxxcx.response.WwsqDydjfxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.request.BdcdyxxCxQO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.response.WwsqBdcdyxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.request.GzwxxcxQO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.gzwxxcx.response.GzwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.cfjfdj.CfjfdjResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.cfjfdj.CourtCfResponseDTo;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.exchange.core.bo.grdacx.GrdacxModel;
import cn.gtmap.realestate.exchange.core.bo.wwsq.ParamBody;
import cn.gtmap.realestate.exchange.core.domain.GxRygfqkDO;
import cn.gtmap.realestate.exchange.core.domain.GxWwSqxxDO;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.changzhou.wwsq.response.ZwbzshyDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dycxcq.request.DycxcqRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dycxcq.response.DycxcqResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dyizm.request.DyicxQO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dyizm.response.DyizmDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.fwxxwithqxdmzl.request.FwxxwithqxdmzlRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.gdxx.request.BdcGdxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getDzzzzip.request.BdcDzzzzipRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqCqzxx.response.GetWwsqCqzxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqCqzxxFy.response.GetWwsqCqzxxFyResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.request.GetYgxxRequestData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getYgxx.response.GetYgxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.gfxx.request.QlrGfxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.gzwsyqcx.request.GzwsyqcxQO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.gzwsyqcx.response.GzwsqyDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj.CreateData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj.InitCfjfdjRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.zydy.InitZyDyRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.queryPpgx.response.PpgxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.querySpfHtbaxx.response.SpfHtbaxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.rygfqk.request.RygfqkRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.sfdpdf.request.SfdPdfRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.spfBaxx.response.SpfBaxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.tdsyqcx.response.TdsyqDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.wwsqdeltask.request.WwsqdeltaskRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ychsbyysfwbm.response.YchsByYsfwbmResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.yyzmcx.request.YycxQO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.yyzmcx.response.YyzmDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestQlr;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.DocumentException;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 共享外网申请信息
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/3/15 15:00
 */
public interface GxWwSqxxService {
    /**
     * 将外网共享数据初始化完成创建
     *
     * @param xmid   申请项目 id
     * @return BdcCshSlxmDTO 初始化受理项目对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>

    BdcSlxxDTO initGxWwSqsjToBdc(String xmid) throws Exception;
     */

    /**
     * 获取办件节点接口
     *
     * @param slbh
     * @return 活动节点  办结显示已办结
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    String getWwsqzt(@NotBlank(message = "受理编号不能为空") String slbh);

    /**
     * @param wwsqdeltaskRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除任务
     */
    Map<String, Object> deleteTask(WwsqdeltaskRequestDTO wwsqdeltaskRequestDTO);


    /**
     * 抵押首次获取产权证信息
     *
     * @param cqzh
     * @param dyr
     * @param zl
     * @param dyrzjh
     * @return 响应结构
     */
    List<Map> getBdczsxx(String cqzh, String dyr, String zl, String dyrzjh, String xmid);

    /**
     * 抵押注销流程获取抵押产权信息
     *
     * @param bdcdjzmh
     * @param dyqr
     * @param dyrmc
     * @param dyqrzjh
     * @param dyrzjh
     * @return 响应结构
     */
    List<Map> getBdczmxx(String bdcdjzmh, String dyqr, String dyrmc, String dyqrzjh, String dyrzjh);

    /**
     * 抵押查询产权列表
     *
     * @param requestDTO
     * @return 响应结构
     */
    List<DycxcqResponseDTO> getWwsqDyCqxx(DycxcqRequestDTO requestDTO);


    /**
     * 获取产权证号下所有抵押信息
     *
     * @param cqzh
     * @return
     */
    List<Map> querycqzdyxx(@NotBlank(message = "参数不能为空") String cqzh);

    /**
     * @param pageResult
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询产权证信息 填充限制权利信息
     */
    GetWwsqCqzxxFyResponseData getWwsqCqzxxPageXzql(GetWwsqCqzxxFyResponseData pageResult);

    /**
     * @param rollbackParam
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 创建项目时 发生异常 需要回滚 项目 和流程
     */
    void rollbackInitXm(Map<String, String> rollbackParam);


    /**
     * @param gxWwSqxxDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据共享外网申请实体 获取工作流定义ID
     */
    void setGzldyidInSqxx(String methodName, GxWwSqxxDO gxWwSqxxDO);

    /**
     * @param gxWwSqxxDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据共享外网申请实体 获取工作流定义ID
     */
    String getGzldyidByWwSqxx(GxWwSqxxDO gxWwSqxxDO);


    /**
     * @param processDefKey
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 规则验证BDCDY
     */
    String gzyzBdcdy(String processDefKey, String bdcdyh);

    /**
     * @param sqrList
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 权利人限购验证
     */
    String qlrxgyz(List<BdcSlSqrDO> sqrList);


    /**
     * @param bdcqzh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据证号 获取BDCDYH
     */
    String queryBdcdyByBdcqzh(String bdcqzh);

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换 外网申请创建结果
     */
    ExchangeDsfCommonResponse<CfjfdjResponseDTO> revertCjResponseForYanchengCourt(String spxtywh, Object responseDTO,
                                                                                  List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf);


    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换 外网申请创建结果
     */
    Map<String, Object> revertCjResponse(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                         List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf);

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 转换 外网申请创建结果_
     */
    Map<String, Object> revertCzCjResponseForZhlc(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                                  List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf);

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 转换 外网申请创建结果 常州
     */
    Map<String, Object> revertCzCjResponse(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                           List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf);

    /**
     * @param responseDTO, fjclList
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    Map<String, Object> revertNtCjResponse(WwsqCjBdcXmResponseDTO responseDTO, List<FjclDTO> fjclList, String zdzf);

    /**
     * @param responseDTO 外网申请请求创建项目后的响应
     * @param fjclList    附件材料信息
     * @param zdzf        自动转发标识
     * @return {Map} 返回信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 南通一体化流程创建处理响应 (异步处理附件)
     */
    Map<String, Object> revertNtCjResponseAsync(WwsqCjBdcXmResponseDTO responseDTO, List<FjclDTO> fjclList, String zdzf);

    /**
     * @param slbh
     * @param fjclList
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 南通根据受理编号初始化附件信息
     */
    Map<String, Object> cshfjxx(String slbh, List<FjclDTO> fjclList);

    /**
     * @param gzlslid
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 自动转发
     */
    void autoTurnWorkflow(String gzlslid, String zdzf, String zdzfr);

    /**
     * @param responseDTO
     * @param slr
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 自动办结
     */
    void zdbj(WwsqCjBdcXmResponseDTO responseDTO, String slr);

    /**
     * @param responseDTO
     * @param slr
     * @param canbj
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 南通要求抵押注销不自动办结，但是互联网+可以推送来自动办结，所以加一个配置控制是否可以办结
     */
    void zdbjWithCanBj(WwsqCjBdcXmResponseDTO responseDTO, String slr, boolean canbj);

    /**
     * @param responseDTO
     * @param slr
     * @param zslist
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 自动办结并且更新证书印制号
     */
    void zdbjAndUpdateZsyzh(WwsqCjBdcXmResponseDTO responseDTO, String slr, List<JSONObject> zslist);

    /**
     * @param responseDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 像产权证信息中的CQZH 赋值
     */
    void setGetWwsqCqzxxCqzhByQlrxx(GetWwsqCqzxxResponseDTO responseDTO, String qlrzjh, String qlrmc);


    /**
     * @param request
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换DTO
     */
    WwsqCjBdcXmRequestDTO initZhlcRevertDTO(InitZyDyRequestDTO request);


    /**
     * @param model
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 预告查询结果做处理（填充是否查封）
     */
//    GrdacxModel dealGetWwsqYgxxResponse(GrdacxModel model);
    List<GetYgxxResponseData> dealGetWwsqYgxxResponse(GrdacxModel model);


    /**
     * @param requestDTO
     * @return java.util.List<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网请求 PDF 格式 SFXX
     */
    List<Map> sfxxPdf(SfdPdfRequestDTO requestDTO);

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 合肥 分页查询首次证明单
     */
    Page<Map> querySczmdByPage(Pageable pageable, Map<String, Object> paramMap);

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 分页查询抵押和预抵押信息
     */
    Page<Map> queryDyxxAndYgdyxxByPage(Pageable pageable, Map<String, Object> paramMap);

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:shaoliyao@gtmap.cn">huangjian</a>
     * @description 分页查询抵押和预抵押信息
     */
    Page<Map> queryDyxxYgdyxxForBdcdyhByPage(Pageable pageable, Map<String, Object> paramMap);

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 分页查询抵押或预抵押信息
     */
    Page<Map> queryDyxxOrYgdyxxByPage(Pageable pageable, Map<String, Object> paramMap);

    /**
     * @param pageable
     * @param fwxxwithqxdmzlRequestDTO
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 合肥 用坐落 分页查询 业务系统的  房屋信息
     */
    Page<Map> queryFwxxWithZlByPage(Pageable pageable, FwxxwithqxdmzlRequestDTO fwxxwithqxdmzlRequestDTO);

    /**
     * @param responseDTO
     * @return FwxxbybdcdyhResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询FWXX处理共有状态、抵押状态、查封状态、异议状态
     */
    FwxxbybdcdyhResponseDTO setZtTofwxxByBdcdyhResponse(FwxxbybdcdyhResponseDTO responseDTO);

    /**
     * @param responseDTO
     * @return cn.gtmap.realestate.exchange.core.dto.wwsq.ychsbyysfwbm.response.YchsByYsfwbmResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据预售房屋编码查询预测户室信息 处理限制权利状态
     */
    YchsByYsfwbmResponseDTO setZtToYchsByYsfwbmResponse(YchsByYsfwbmResponseDTO responseDTO);

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询现势产权的XMID
     */
    String getCqXmidByBdcdyh(String bdcdyh);

    /**
     * @param jsonObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网申请 税务三要素
     */
    JSONObject wwsqCxSwsys(JSONObject jsonObject);


    /**
     * @param rygfqkRequestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.domain.GxRygfqkDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询人员购房情况
     */
    List<GxRygfqkDO> listGxRygfqk(RygfqkRequestDTO rygfqkRequestDTO);

    /**
     * @param bdcZfxxQO
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 根据权利人名称、证件号查询住房信息
     */
    List<BdcZfxxDTO> getWwsqZfxx(BdcZfxxQO bdcZfxxQO);

    /**
     * 根据受理编号（必填），不动产权证号（选填）查询套打证书信息
     *
     * @param paramMap
     * @return
     */
    List<BdcZsDO> queryZsxx(JSONObject paramMap);

    /**
     * 合肥-互联网+银行  推送附件材料
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 15:42 2020/10/14
     */
    Map<String, Object> tsfjcl(JSONObject paramMap);

    /**
     * @param fwqlCxRequestQlr
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 获取限制状态 以及限制信息
     */
    String getWwsqCqzxxXzxx(FwqlCxRequestQlr fwqlCxRequestQlr);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据bdcdyh创建登记流程
     */
    Map<String, Object> bdcdycj(BdcCshSlxmDTO bdcCshSlxmDTO);


    /**
     * @param initCfjfdjRequestDTO
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 转换DTO
     */
    WwsqCjBdcXmRequestDTO initCfjfdjParamDTO(InitCfjfdjRequestDTO initCfjfdjRequestDTO);

    /**
     * @param responseDTO
     * @param slr
     * @return void
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 抵押注销根据参数自动办结或者自动转发
     */
    void zdbjOrzdzf(WwsqCjBdcXmResponseDTO responseDTO, String slr, String sfzdbj, String zdzf);

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description Court 转换 外网申请创建结果 court
     */
    ExchangeDsfCommonResponse<CourtCfResponseDTo> revertCjResponseCourt(String spxtywh, Object responseDTO,
                                                                        List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf);

    /**
     * @param wwsqCjBdcXmRequestDTO
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 外网申请创建项目 court
     */
    Object acceptCjServiceForCourt(WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) throws Exception;

    /**
     * @param createData
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 转换DTO
     */
    WwsqCjBdcXmRequestDTO initCfParamDTO(CreateData createData);

    /**
     * @param ygxxRequestDatas 查询参数
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 预抵押查询
     */
    List<GetYgxxResponseData> getYdyxxChangzhou(List<GetYgxxRequestData> ygxxRequestDatas);

    /**
     * @param slbh
     * @return json 证明附表打印数据
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 批量抵押, 证明附表打印数据
     */
    JSONObject getZmfb(String slbh) throws DocumentException, Exception;

    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description 转换dto
     */
    List<FwxxbybdcdyhResponseDTO> covertDate(List<BdcdyResponseDTO> bdcdyResponseDTOS, List<Map> djDcbResponseMap);

    /**
     * 构（建）筑物信息查询（不动产单元信息）
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<GzwxxResponseDTO> queryGzwxx(Pageable pageable, GzwxxcxQO gzwxxcxQO);

    /**
     * 构（建）筑物所有权查询
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<GzwsqyDTO> queryGzwsyq(Pageable pageable, GzwsyqcxQO gzwxxcxQO);

    /**
     * 土地所有权查询
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<TdsyqDTO> queryTdsyq(Pageable pageable, GzwsyqcxQO gzwxxcxQO);

    /**
     * 地役证明查询
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<DyizmDTO> queryDyzm(Pageable pageable, DyicxQO dyicxQO);


    /**
     * 异议证明查询
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<YyzmDTO> queryYyzm(Pageable pageable, YycxQO yycxQOQ);

    /**
     * @param
     * @param paramMap
     * @return cn.gtmap.realestate.common.core.dto.BdcCommonResponse
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/7/13 21:35
     * @description 根据权利人查询产权信息
     **/
    BdcCommonResponse queryCqxxByQlr(JSONObject paramMap);

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 根据权利人信息查询购房信息
    */
    BdcCommonResponse queryGfxxByQlr(QlrGfxxRequestDTO gfxxRequestDTO);

    /**
    * @return
    * @author <a href = "mailto:wutao@gtmap.cn">wutao</a>
    * @description 删除归档信息
    */
    BdcCommonResponse deleteGdxx(BdcGdxxRequestDTO bdcGdxxRequestDTO);

    /**
     * @return
     * @author <a href = "mailto:wutao@gtmap.cn">wutao</a>
     * @description 获取电子证照压缩包
     */
    BdcCommonResponse getDzzzzip(BdcDzzzzipRequestDTO bdcDzzzzipRequestDTO);
    /**
     * @param
     * @param paramMap
     * @return cn.gtmap.realestate.common.core.dto.BdcCommonResponse
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/3 17:27
     * @description 查询家庭不动产登记信息
     **/
    BdcCommonResponse queryJtBdcdjXx(JSONObject paramMap);

    /**
     * 权籍不动产单元查询
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<WwsqBdcdyxxDTO> getWwsqBdcdyxxFy(Pageable pageable, BdcdyxxCxQO bdcdyxxCxQO);



    /**
     * 抵押登记查询
     *
     * @param DydjxxcxCxQO dydjxxcxCxQO
     * @return
     * @Date 2022/9/15
     * @author wangyinghao
     */
    Page<WwsqDydjfxxDTO> getDydjxxcx(Pageable pageable, DydjxxcxCxQO dydjxxcxCxQO);


    /**
     * 登记系统政务办住所核验接口
     * @param cqzh
     * @param qlrmc
     * @return
     */
    public ZwbzshyDTO getZwbzshy(String cqzh, String qlrmc);

    /**
     * @description 连云港查询新建商品房网签合同数据
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/26 15:53
     * @param paramMap
     * @return List<SpfHtbaxxResponseDTO>
     */
    public List<SpfHtbaxxResponseDTO> querySpfHtbaxx(JSONObject paramMap);

    /**
     * @description 根据房产证号查询匹配关系信息、分户图和宗地图
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/16 15:01
     * @param fczh
     * @return List<PpgxResponseDTO>
     */
    List<PpgxResponseDTO> queryPpgx(String fczh);


    /**
     *
     * @param qlrmc
     * @param zjh
     * @return
     */
    JSONArray queryFwqsxx(String qlrmc,String zjh,Integer pageNumber,Integer pageSize);


    /**
     * @description 商品房备案信息查询（宣城）
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @date 2022/12/16 11:43
     */
    SpfBaxxResponseData xcBdcspfbacx(ParamBody paramBody);
}
