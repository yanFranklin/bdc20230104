package cn.gtmap.realestate.exchange.web.rest.yancheng;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.etl.HtbaFwxxDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.etl.HtbaxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yth.YthTsyclpDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.etl.WwtsHtbaFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.YanchengYthRestService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthCancelDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthQueryDzzzxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthSfxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwsqxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.request.YthYjxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.respones.YthCommonResponse;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcYthService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
 * @version 1.0  2020-11-23
 * @description (盐城) 一体化相关服务
 */
@OpenController(consumer = "(盐城) 一体化相关服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0")
@Api(tags = "(盐城) 一体化相关服务")
public class BdcYthRestController implements YanchengYthRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYthRestController.class);

    @Autowired
    private BdcYthService bdcYthService;

    @Autowired
    private ProcessInsCustomExtendClient processInsCustomExtendClient;

    @Autowired
    private WwtsHtbaFeignService wwtsHtbaFeignService;

    /**
     * 盐城_一体化撤件请求
     *
     * @param ythCancelDTO 请求参数
     * @return ythCommonResponse 返回参数
     * @author <a href="mailto:zedeqiangg@gtmap.cn">zedq</a>
     */
    @OpenApi(apiDescription = "盐城_一体化撤件请求", apiName = "", openLog = false)
    @PostMapping("/yancheng/yth/cancel")
    @DsfLog(logEventName = "盐城_一体化撤件请求", dsfFlag = "", requester = "", responser = "BDC")
    public YthCommonResponse ythCancel(@RequestBody YthCancelDTO ythCancelDTO) {
        CommonResponse commonResponse = bdcYthService.ythCancel(ythCancelDTO);
        if (commonResponse.isSuccess()) {
            return YthCommonResponse.ok();
        } else {
            if (CommonResponse.ERROR_CODE_WITHOUT_DATA.equals(commonResponse.getFail().getCode())) {
                return YthCommonResponse.ok(commonResponse.getFail().getMessage());
            }
            return YthCommonResponse.fail(commonResponse.getFail().getMessage());
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 盐城_接收一体化平台收费信息
     */
    @OpenApi(apiDescription = "盐城_接收一体化平台收费信息", apiName = "", openLog = false)
    @PostMapping("/yancheng/yth/jssfxx")
    @DsfLog(logEventName = "盐城_接收一体化平台收费信息", dsfFlag = "YTH", requester = "", responser = "BDC")
    public YthCommonResponse ythJsSfxx(@RequestBody YthSfxxDTO ythSfxxDTO) {
        CommonResponse commonResponse = bdcYthService.ythJsSfxx(ythSfxxDTO);
        if (commonResponse.isSuccess()) {
            return YthCommonResponse.ok();
        } else {
            return YthCommonResponse.fail(commonResponse.getFail().getMessage());
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 盐城_一体化平台申请创建业务接口
     */
    @OpenApi(apiDescription = "盐城_一体化平台申请创建业务接口", apiName = "", openLog = false)
    @PostMapping("/yancheng/yth/jssqxxcj")
    @DsfLog(logEventName = "盐城_一体化平台申请创建业务接口", dsfFlag = "YTH", requester = "", responser = "BDC")
    public YthCommonResponse ythJsSqxxCj(@RequestBody YthYwsqxxDTO ythYwsqxxDTO) {
        CommonResponse commonResponse = bdcYthService.ythJsSqxxCj(ythYwsqxxDTO);
        if (commonResponse.isSuccess()) {
            return YthCommonResponse.ok();
        } else {
            return YthCommonResponse.fail(commonResponse.getFail().getMessage());
        }
    }

    /**
     * 登记推送一体化受理信息
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @OpenApi(apiDescription = "盐城_一体化推送受理信息", apiName = "", openLog = false)
    @PostMapping("/yancheng/yth/tsslxx")
    @DsfLog(logEventName = "盐城_一体化推送受理信息", dsfFlag = "YTH", requester = "", responser = "BDC")
    @Override
    public Object ythTsSlxx(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new AppException("缺失参数工作流实例ID");
        }
        return bdcYthService.ythTsSlxx(processInsId);
    }

    /**
     * 推送一体化预测楼盘信息
     *
     * @param bdcdyhList
     * @return CommonResponse
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @Override
    @ResponseBody
    @PostMapping("/yancheng/yth/tsyclp")
    public CommonResponse ythTsyclp(@RequestBody YthTsyclpDTO bdcdyhList) {
        if (StringUtils.isNotBlank(bdcdyhList.getFwDcbIndex())) {
            return bdcYthService.ythTsyclp(bdcdyhList.getFwDcbIndex(),bdcdyhList.getQjgldm());
        } else {
            return CommonResponse.fail("入参为空");
        }
    }

    /**
     * 一体化获取电子证照和宗地图相关信息
     *
     * @param ythQueryDzzzxxDTO
     * @return CommonResponse
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @ResponseBody
    @PostMapping("/yancheng/yth/get/dzzz/and/zdxx")
    public Object ythGetDzzzxx(@RequestBody YthQueryDzzzxxDTO ythQueryDzzzxxDTO) {
        CommonResponse commonResponse = bdcYthService.ythQueyrDzzzxx(ythQueryDzzzxxDTO);
        Map<String, Object> respMap = new HashMap<>();
        if (commonResponse.isSuccess()) {
            respMap.put("type", "success");
            respMap.put("data", commonResponse.getData());
            return respMap;
        } else if ("9998".equals(commonResponse.getFail().getCode())) {
            respMap.put("type", "success");
            Map<String, Object> data = new HashMap<>();
            data.put("message", "未找到匹配的证照信息");
            data.put("status", 10);
            respMap.put("data", data);
            return respMap;
        } else {
            respMap.put("type", "fail");
            Map<String, Object> data = new HashMap<>();
            data.put("message", commonResponse.getFail().getMessage());
            data.put("status", 10);
            respMap.put("data", data);
            return respMap;
        }
    }

    /**
     * 登记推送一体化审核信息
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @OpenApi(apiDescription = "盐城_一体化推送审核信息", apiName = "", openLog = false)
    @PostMapping("/yancheng/yth/tsshxx")
    @DsfLog(logEventName = "盐城_一体化推送审核信息", dsfFlag = "YTH", requester = "", responser = "BDC")
    @Override
    public Object ythTsShxx(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new AppException("缺失参数工作流实例ID");
        }
        return this.bdcYthService.ythTsShxx(processInsId);
    }

    @OpenApi(apiDescription = "盐城_一体化推送发证信息", apiName = "", openLog = false)
    @PostMapping("/yancheng/yth/tsfzxx")
    @DsfLog(logEventName = "盐城_一体化推送发证信息", dsfFlag = "YTH", requester = "", responser = "BDC")
    @Override
    public Object ythTsFzxx(@RequestParam("processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new AppException("缺失参数工作流实例ID");
        }
        CommonResponse commonResponse = bdcYthService.ythTsFzxx(processInsId);
        if (!commonResponse.isSuccess()) {
            throw new RuntimeException(commonResponse.getFail().getMessage());
        }
        return commonResponse;
    }

    /**
     * 登记删除业务，通知一体化
     *
     * @param processInsId gzlslid
     * @Date 2020/12/4
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @OpenApi(apiDescription = "盐城_登记删除业务，通知一体化", openLog = false)
    @PostMapping("/yancheng/yth/deletettzyth")
    @DsfLog(logEventName = "盐城_登记删除业务，通知一体化", dsfFlag = "YTH", requester = "", responser = "BDC")
    public CommonResponse deleteTzYth(@RequestParam("processInsId") String processInsId, @RequestParam("reason") String reason) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺失gzlslid！");
        }
        LOGGER.info("开始登记删除业务，通知一体化");
        //根据gzlslid查询回写数据，根据sply判断，是一体化的则执行通知操作
        Map<String, Object> processInsExtendDto = new HashMap<>(12);
        CommonResponse commonResponse = new CommonResponse();
        List<Map<String, Object>> list = processInsCustomExtendClient.getProcessInsCustomExtend(processInsId);
        LOGGER.info("流程扩展字段列表size：{}", list.size());
        if (CollectionUtils.isNotEmpty(list)) {
            processInsExtendDto = list.get(0);
            LOGGER.info("流程回写审批来源为：" + processInsExtendDto.get("SPLY"));
            if (String.valueOf(CommonConstantUtils.SPLY_YCSL).equals(processInsExtendDto.get("SPLY"))) {
                commonResponse = bdcYthService.deleteTzYth(processInsId, reason);
            }
        }
        LOGGER.info("登记删除业务，通知一体化结束");
        return commonResponse;
    }

    /**
     * @return java.lang.Object
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [httpServletRequest]
     * @description 盐城 一体化以人查房接口
     */
    @GetMapping("/yancheng/yth/yrcf")
    public Object queryFcxx(HttpServletRequest httpServletRequest) {
        // 验证分页参数是否传递
        if (StringUtils.isBlank(httpServletRequest.getParameter("PAGENUM"))
                || StringUtils.isBlank(httpServletRequest.getParameter("PAGESIZE"))) {
            LOGGER.error("分页参数PAGENUM或PAGESIZE为空！");
            return YthCommonResponse.fail("分页参数PAGENUM或PAGESIZE为空！");
        }

        // 证件号和不动产单元号不可同时为空
        if (StringUtils.isBlank(httpServletRequest.getParameter("ZJH"))
                && StringUtils.isBlank(httpServletRequest.getParameter("BDCDYH"))
                && StringUtils.isBlank(httpServletRequest.getParameter("ZL"))
                && StringUtils.isBlank(httpServletRequest.getParameter("BDCQZH"))) {
            LOGGER.error("入参不能同时为空！");
            return YthCommonResponse.fail("入参不能同时为空！");
        }

        JSONArray fwxx;
        try {
            fwxx = bdcYthService.queryYthYrcf(httpServletRequest);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return YthCommonResponse.fail(e.getMessage());
        }
        if (CollectionUtils.isEmpty(fwxx)) {
            return buildSuccessResponse(new ArrayList());
        }

        return buildSuccessResponse(fwxx);
    }


    /**
     * 一体化平台推送合同的相关信息，备案接受
     *
     * @param htbaxxDTO 合同备案信息
     * @return
     * @Date 2021/4/15
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @PostMapping("/yancheng/yth/htbajs")
    public Object ythHtbajs(@RequestBody HtbaxxDTO htbaxxDTO) {
        if (checkAllparam(htbaxxDTO)) {
            return wwtsHtbaFeignService.wwtsHtbaxx(htbaxxDTO);
        } else {
            return BdcCommonResponse.fail("请检查参数，确保所有必要参数不为空！");

        }
    }

    /**
     * 一体化平台推送合同的相关信息，备案撤销
     *
     * @param htbaxxDTO 合同备案信息
     * @return
     * @Date 2021/4/15
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @PostMapping("/yancheng/yth/htbacx")
    public Object ythHtbacx(@RequestBody HtbaxxDTO htbaxxDTO) {
        if (checkAllparam(htbaxxDTO)) {
            return wwtsHtbaFeignService.wwcxHtbaxx(htbaxxDTO);
        } else {
            return BdcCommonResponse.fail("请检查参数，确保所有必要参数不为空！");

        }
    }

    /**
     * @return java.lang.Object
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @params [httpServletRequest]
     * @description 一体化 接收邮寄信息
     */
    @PostMapping("/yancheng/yth/yjxx")
    public Object ythYjxx(@RequestBody YthYjxxRequestDTO ythYjxxRequestDTO) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isBlank(ythYjxxRequestDTO.getYwbh()) || StringUtils.isBlank(ythYjxxRequestDTO.getWaybillNo())) {
            LOGGER.info("一体化业务编号或Ems订单号不可为空");
            jsonObject.put("data", "");
            jsonObject.put("type", "fail");
            jsonObject.put("msg", "一体化业务编号或Ems订单号不可为空！");
            return jsonObject;
        }
        return bdcYthService.ythYjxx(ythYjxxRequestDTO);

    }

    /**
     * 验证所有参数
     *
     * @param htbaxxDTO
     * @return
     * @Date 2021/4/15
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Boolean checkAllparam(HtbaxxDTO htbaxxDTO) {

        if (null != htbaxxDTO) {
            if (null != htbaxxDTO.getHtbaSpfDO()) {
                if (!CheckParameter.checkParameter(htbaxxDTO.getHtbaSpfDO(), "htbh", "basj", "bazt", "htzjk", "kfsmc", "tdyt", "zzsyjzrq", "sysyjzrq")) {
                    return false;
                }
                if (CollectionUtils.isNotEmpty(htbaxxDTO.getHtbaQlrDOList()) && CollectionUtils.isNotEmpty(htbaxxDTO.getHtbaFwxxDOList())) {
                    for (HtbaQlrDO qlrDO : htbaxxDTO.getHtbaQlrDOList()) {
                        if (!CheckParameter.checkParameter(qlrDO, "qlrmc", "zjh", "lxdh", "qlrlb")) {
                            return false;
                        }
                    }
                    for (HtbaFwxxDO fwxxDO : htbaxxDTO.getHtbaFwxxDOList()) {
                        if (!CheckParameter.checkParameter(fwxxDO, "zl", "fwbm", "bdcdyh", "fwzl", "fwxz", "jzmj", "fwdj", "fwzcs", "fwszc", "cg", "fwjg")) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    private JSONObject buildSuccessResponse(List contentList) {
        JSONObject response = new JSONObject();
        response.put("type", "success");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fwxx", contentList);
        response.put("data", jsonObject);
        return response;
    }


}
