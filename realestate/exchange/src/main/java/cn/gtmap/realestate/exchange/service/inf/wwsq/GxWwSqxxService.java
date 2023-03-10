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
 * ????????????????????????
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/3/15 15:00
 */
public interface GxWwSqxxService {
    /**
     * ??????????????????????????????????????????
     *
     * @param xmid   ???????????? id
     * @return BdcCshSlxmDTO ???????????????????????????
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>

    BdcSlxxDTO initGxWwSqsjToBdc(String xmid) throws Exception;
     */

    /**
     * ????????????????????????
     *
     * @param slbh
     * @return ????????????  ?????????????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    String getWwsqzt(@NotBlank(message = "????????????????????????") String slbh);

    /**
     * @param wwsqdeltaskRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????
     */
    Map<String, Object> deleteTask(WwsqdeltaskRequestDTO wwsqdeltaskRequestDTO);


    /**
     * ?????????????????????????????????
     *
     * @param cqzh
     * @param dyr
     * @param zl
     * @param dyrzjh
     * @return ????????????
     */
    List<Map> getBdczsxx(String cqzh, String dyr, String zl, String dyrzjh, String xmid);

    /**
     * ??????????????????????????????????????????
     *
     * @param bdcdjzmh
     * @param dyqr
     * @param dyrmc
     * @param dyqrzjh
     * @param dyrzjh
     * @return ????????????
     */
    List<Map> getBdczmxx(String bdcdjzmh, String dyqr, String dyrmc, String dyqrzjh, String dyrzjh);

    /**
     * ????????????????????????
     *
     * @param requestDTO
     * @return ????????????
     */
    List<DycxcqResponseDTO> getWwsqDyCqxx(DycxcqRequestDTO requestDTO);


    /**
     * ???????????????????????????????????????
     *
     * @param cqzh
     * @return
     */
    List<Map> querycqzdyxx(@NotBlank(message = "??????????????????") String cqzh);

    /**
     * @param pageResult
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????????????????????????? ????????????????????????
     */
    GetWwsqCqzxxFyResponseData getWwsqCqzxxPageXzql(GetWwsqCqzxxFyResponseData pageResult);

    /**
     * @param rollbackParam
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????????????? ???????????? ???????????? ?????? ?????????
     */
    void rollbackInitXm(Map<String, String> rollbackParam);


    /**
     * @param gxWwSqxxDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????????????????? ?????????????????????ID
     */
    void setGzldyidInSqxx(String methodName, GxWwSqxxDO gxWwSqxxDO);

    /**
     * @param gxWwSqxxDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????????????????? ?????????????????????ID
     */
    String getGzldyidByWwSqxx(GxWwSqxxDO gxWwSqxxDO);


    /**
     * @param processDefKey
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????BDCDY
     */
    String gzyzBdcdy(String processDefKey, String bdcdyh);

    /**
     * @param sqrList
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????????
     */
    String qlrxgyz(List<BdcSlSqrDO> sqrList);


    /**
     * @param bdcqzh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????? ??????BDCDYH
     */
    String queryBdcdyByBdcqzh(String bdcqzh);

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????? ????????????????????????
     */
    ExchangeDsfCommonResponse<CfjfdjResponseDTO> revertCjResponseForYanchengCourt(String spxtywh, Object responseDTO,
                                                                                  List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf);


    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????? ????????????????????????
     */
    Map<String, Object> revertCjResponse(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                         List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf);

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description ?????? ????????????????????????_
     */
    Map<String, Object> revertCzCjResponseForZhlc(String spxtywh, WwsqCjBdcXmResponseDTO responseDTO,
                                                  List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf);

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description ?????? ???????????????????????? ??????
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
     * @param responseDTO ??????????????????????????????????????????
     * @param fjclList    ??????????????????
     * @param zdzf        ??????????????????
     * @return {Map} ????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ??????????????????????????????????????? (??????????????????)
     */
    Map<String, Object> revertNtCjResponseAsync(WwsqCjBdcXmResponseDTO responseDTO, List<FjclDTO> fjclList, String zdzf);

    /**
     * @param slbh
     * @param fjclList
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description ?????????????????????????????????????????????
     */
    Map<String, Object> cshfjxx(String slbh, List<FjclDTO> fjclList);

    /**
     * @param gzlslid
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????
     */
    void autoTurnWorkflow(String gzlslid, String zdzf, String zdzfr);

    /**
     * @param responseDTO
     * @param slr
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????
     */
    void zdbj(WwsqCjBdcXmResponseDTO responseDTO, String slr);

    /**
     * @param responseDTO
     * @param slr
     * @param canbj
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description ?????????????????????????????????????????????????????????+???????????????????????????????????????????????????????????????????????????
     */
    void zdbjWithCanBj(WwsqCjBdcXmResponseDTO responseDTO, String slr, boolean canbj);

    /**
     * @param responseDTO
     * @param slr
     * @param zslist
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description ???????????????????????????????????????
     */
    void zdbjAndUpdateZsyzh(WwsqCjBdcXmResponseDTO responseDTO, String slr, List<JSONObject> zslist);

    /**
     * @param responseDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????????????????CQZH ??????
     */
    void setGetWwsqCqzxxCqzhByQlrxx(GetWwsqCqzxxResponseDTO responseDTO, String qlrzjh, String qlrmc);


    /**
     * @param request
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????DTO
     */
    WwsqCjBdcXmRequestDTO initZhlcRevertDTO(InitZyDyRequestDTO request);


    /**
     * @param model
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????????????????????????????????????????????
     */
//    GrdacxModel dealGetWwsqYgxxResponse(GrdacxModel model);
    List<GetYgxxResponseData> dealGetWwsqYgxxResponse(GrdacxModel model);


    /**
     * @param requestDTO
     * @return java.util.List<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????? PDF ?????? SFXX
     */
    List<Map> sfxxPdf(SfdPdfRequestDTO requestDTO);

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????? ???????????????????????????
     */
    Page<Map> querySczmdByPage(Pageable pageable, Map<String, Object> paramMap);

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description ????????????????????????????????????
     */
    Page<Map> queryDyxxAndYgdyxxByPage(Pageable pageable, Map<String, Object> paramMap);

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:shaoliyao@gtmap.cn">huangjian</a>
     * @description ????????????????????????????????????
     */
    Page<Map> queryDyxxYgdyxxForBdcdyhByPage(Pageable pageable, Map<String, Object> paramMap);

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description ????????????????????????????????????
     */
    Page<Map> queryDyxxOrYgdyxxByPage(Pageable pageable, Map<String, Object> paramMap);

    /**
     * @param pageable
     * @param fwxxwithqxdmzlRequestDTO
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????? ????????? ???????????? ???????????????  ????????????
     */
    Page<Map> queryFwxxWithZlByPage(Pageable pageable, FwxxwithqxdmzlRequestDTO fwxxwithqxdmzlRequestDTO);

    /**
     * @param responseDTO
     * @return FwxxbybdcdyhResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????BDCDYH??????FWXX???????????????????????????????????????????????????????????????
     */
    FwxxbybdcdyhResponseDTO setZtTofwxxByBdcdyhResponse(FwxxbybdcdyhResponseDTO responseDTO);

    /**
     * @param responseDTO
     * @return cn.gtmap.realestate.exchange.core.dto.wwsq.ychsbyysfwbm.response.YchsByYsfwbmResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????????????????????????????????????????? ????????????????????????
     */
    YchsByYsfwbmResponseDTO setZtToYchsByYsfwbmResponse(YchsByYsfwbmResponseDTO responseDTO);

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????BDCDYH?????????????????????XMID
     */
    String getCqXmidByBdcdyh(String bdcdyh);

    /**
     * @param jsonObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????? ???????????????
     */
    JSONObject wwsqCxSwsys(JSONObject jsonObject);


    /**
     * @param rygfqkRequestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.domain.GxRygfqkDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????????????????
     */
    List<GxRygfqkDO> listGxRygfqk(RygfqkRequestDTO rygfqkRequestDTO);

    /**
     * @param bdcZfxxQO
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description ???????????????????????????????????????????????????
     */
    List<BdcZfxxDTO> getWwsqZfxx(BdcZfxxQO bdcZfxxQO);

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????
     *
     * @param paramMap
     * @return
     */
    List<BdcZsDO> queryZsxx(JSONObject paramMap);

    /**
     * ??????-?????????+??????  ??????????????????
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
     * ?????????????????? ??????????????????
     */
    String getWwsqCqzxxXzxx(FwqlCxRequestQlr fwqlCxRequestQlr);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????bdcdyh??????????????????
     */
    Map<String, Object> bdcdycj(BdcCshSlxmDTO bdcCshSlxmDTO);


    /**
     * @param initCfjfdjRequestDTO
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description ??????DTO
     */
    WwsqCjBdcXmRequestDTO initCfjfdjParamDTO(InitCfjfdjRequestDTO initCfjfdjRequestDTO);

    /**
     * @param responseDTO
     * @param slr
     * @return void
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description ??????????????????????????????????????????????????????
     */
    void zdbjOrzdzf(WwsqCjBdcXmResponseDTO responseDTO, String slr, String sfzdbj, String zdzf);

    /**
     * @param spxtywh
     * @param responseDTO
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description Court ?????? ???????????????????????? court
     */
    ExchangeDsfCommonResponse<CourtCfResponseDTo> revertCjResponseCourt(String spxtywh, Object responseDTO,
                                                                        List<FjclDTO> fjclList, WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO, String zdzf);

    /**
     * @param wwsqCjBdcXmRequestDTO
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description ???????????????????????? court
     */
    Object acceptCjServiceForCourt(WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) throws Exception;

    /**
     * @param createData
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description ??????DTO
     */
    WwsqCjBdcXmRequestDTO initCfParamDTO(CreateData createData);

    /**
     * @param ygxxRequestDatas ????????????
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description ???????????????
     */
    List<GetYgxxResponseData> getYdyxxChangzhou(List<GetYgxxRequestData> ygxxRequestDatas);

    /**
     * @param slbh
     * @return json ????????????????????????
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description ????????????, ????????????????????????
     */
    JSONObject getZmfb(String slbh) throws DocumentException, Exception;

    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description ??????dto
     */
    List<FwxxbybdcdyhResponseDTO> covertDate(List<BdcdyResponseDTO> bdcdyResponseDTOS, List<Map> djDcbResponseMap);

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<GzwxxResponseDTO> queryGzwxx(Pageable pageable, GzwxxcxQO gzwxxcxQO);

    /**
     * ?????????????????????????????????
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<GzwsqyDTO> queryGzwsyq(Pageable pageable, GzwsyqcxQO gzwxxcxQO);

    /**
     * ?????????????????????
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<TdsyqDTO> queryTdsyq(Pageable pageable, GzwsyqcxQO gzwxxcxQO);

    /**
     * ??????????????????
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<DyizmDTO> queryDyzm(Pageable pageable, DyicxQO dyicxQO);


    /**
     * ??????????????????
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
     * @description ?????????????????????????????????
     **/
    BdcCommonResponse queryCqxxByQlr(JSONObject paramMap);

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description ???????????????????????????????????????
    */
    BdcCommonResponse queryGfxxByQlr(QlrGfxxRequestDTO gfxxRequestDTO);

    /**
    * @return
    * @author <a href = "mailto:wutao@gtmap.cn">wutao</a>
    * @description ??????????????????
    */
    BdcCommonResponse deleteGdxx(BdcGdxxRequestDTO bdcGdxxRequestDTO);

    /**
     * @return
     * @author <a href = "mailto:wutao@gtmap.cn">wutao</a>
     * @description ???????????????????????????
     */
    BdcCommonResponse getDzzzzip(BdcDzzzzipRequestDTO bdcDzzzzipRequestDTO);
    /**
     * @param
     * @param paramMap
     * @return cn.gtmap.realestate.common.core.dto.BdcCommonResponse
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/3 17:27
     * @description ?????????????????????????????????
     **/
    BdcCommonResponse queryJtBdcdjXx(JSONObject paramMap);

    /**
     * ???????????????????????????
     *
     * @param GzwxxDTO GzwxxDTO
     * @return
     * @Date 2022/6/28
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<WwsqBdcdyxxDTO> getWwsqBdcdyxxFy(Pageable pageable, BdcdyxxCxQO bdcdyxxCxQO);



    /**
     * ??????????????????
     *
     * @param DydjxxcxCxQO dydjxxcxCxQO
     * @return
     * @Date 2022/9/15
     * @author wangyinghao
     */
    Page<WwsqDydjfxxDTO> getDydjxxcx(Pageable pageable, DydjxxcxCxQO dydjxxcxCxQO);


    /**
     * ???????????????????????????????????????
     * @param cqzh
     * @param qlrmc
     * @return
     */
    public ZwbzshyDTO getZwbzshy(String cqzh, String qlrmc);

    /**
     * @description ????????????????????????????????????????????????
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/26 15:53
     * @param paramMap
     * @return List<SpfHtbaxxResponseDTO>
     */
    public List<SpfHtbaxxResponseDTO> querySpfHtbaxx(JSONObject paramMap);

    /**
     * @description ??????????????????????????????????????????????????????????????????
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
     * @description ???????????????????????????????????????
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @date 2022/12/16 11:43
     */
    SpfBaxxResponseData xcBdcspfbacx(ParamBody paramBody);
}
