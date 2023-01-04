package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.cbirc.financialquery.CbircFinancialQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.baseInfo.PoliceQueryBaseInfoRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.idcheck.PoliceCheckIdRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.identitycheck.PoliceIdentityCheckRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.identitycheck.PoliceIdentityCheckResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery.CivilMarriageQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.cbirc.financialquery.CbircFinancialQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.scopsr.organquery.ScopsrOrganQueryRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.scopsr.organquery.ScopsrOrganQueryResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionapply.SupremeCourtDecisionApplyRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionapply.SupremeCourtDecisionApplyResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionresponse.SupremeCourtDecisionResponseRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionresponse.SupremeCourtDecisionResponseResponseDTO;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author <a href="mailto:zhongjinpeng@gtamp.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/10/30 16:24
 * @description 自然资源部接口
 */
public interface NaturalResourcesRestService {

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param info
     * @return PoliceIdentityCheckResponseDTO
     * @description 2.1 公安部-身份核查服务接口说明
     */
    @PostMapping("/realestate-exchange/rest/v1.0/naturalResources/police/identityCheck")
    PoliceIdentityCheckResponseDTO identityCheck(@RequestBody PoliceIdentityCheckRequestDTO info);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param info
     * @return Object
     * @description 2.3民政部-婚姻登记信息查询服务接口说明
     */
    @PostMapping("/realestate-exchange/rest/v1.0/naturalResources/civil/marriageQuery")
    CivilMarriageQueryResponseDTO marriageQuery(@RequestBody CivilMarriageQueryRequestDTO info);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param info
     * @return GxZrzybMzbHydjxxcxRequestDTO
     * @description 2.5银保监会-金融许可证查询接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/naturalResources/cbirc/financialQuery")
    CbircFinancialQueryResponseDTO financialQuery(@RequestBody CbircFinancialQueryRequestDTO info);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param info
     * @return GxZrzybZbbSydwdjxxcxRequestDTO
     * @description 2.6中编办-事业单位登记信息（含机关、群团信息）查询接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/naturalResources/scopsr/organQuery")
    ScopsrOrganQueryResponseDTO organQuery(@RequestBody ScopsrOrganQueryRequestDTO info);

    /**
     * @param info
     * @return GxZrzybZgfSfpjxxcxsqRequestDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 2.7最高法-司法判决信息查询申请接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/naturalResources/supremecourt/decisionApply")
    SupremeCourtDecisionResponseResponseDTO decisionApply(@RequestBody SupremeCourtDecisionApplyRequestDTO info);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param info
     * @return GxZrzybZgfSfpjxxjgRequestDTO
     * @description 2.8最高法-司法判决信息结果反馈接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/naturalResources/supremecourt/decisionResponse")
    SupremeCourtDecisionResponseResponseDTO decisionResponse(@RequestBody SupremeCourtDecisionResponseRequestDTO info);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zedq</a>
     * @description 3.12 公安部-人口库基准信息查询接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/naturalResources/police/baseinfo")
    CommonResponse<JSONArray> baseInfo(@RequestBody List<PoliceQueryBaseInfoRequestDTO> info);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 3.13 公安部-人口库身份核查接口
     * @return
     */
    @PostMapping("/realestate-exchange/rest/v1.0/naturalResources/police/id/check")
    CommonResponse<JSONArray> idCheck(@RequestBody List<PoliceCheckIdRequestDTO> info);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 海域通用请求接口
     * @return
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/send/djxx/to/hy")
    CommonResponse hyCommonRequest(@RequestParam(name = "processInsId") String gzlslid);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.1 海域基本信息
     * @return
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/zhjbxx")
    CommonResponse seaZhjbxx(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.2 用海坐标接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/yhydzb")
    CommonResponse seaYhydzb(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.3 用海状况接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/yhzk")
    CommonResponse seaYhzk(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.4 宗海变化情况接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/zhbhqk")
    CommonResponse seaZhbhqk(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.5 海域使用权信息接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/hysyq")
    CommonResponse seaHysyq(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.6 权利人信息接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/hy/qlr")
    CommonResponse seaQlr(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.8 抵押登记信息接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/hy/dyaq")
    CommonResponse seaDyaq(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.9 查封登记信息接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/hy/cf")
    CommonResponse seaCfdj(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.10 异议登记信息接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/hy/yydj")
    CommonResponse seaYydj(@RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.11 扫描件信息接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/hy/smjxx")
    CommonResponse seaSmjxx(@RequestParam(value = "gzlslid") String gzlslid);
    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 2.12 注销登记信息接口
     */
    @GetMapping("/realestate-exchange/rest/v1.0/naturalResources/hy/zxdj")
    CommonResponse seaZxdj(@RequestParam(value = "gzlslid") String gzlslid);

}
