package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.init.BdcDeleteYwxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.*;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.BdcYctbFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcInitRestService;
import cn.gtmap.realestate.common.core.support.pdf.service.impl.OfficePdfServiceImpl;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.TaskUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * @program: realestate
 * @description: 一窗受理信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-24 14:52
 **/
@Controller
@RequestMapping("/ycsl")
@Validated
public class YcslXxController extends BaseController {
    @Autowired
    private BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    private BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Autowired
    private BdcYcslFeignService bdcYcslFeignService;
    @Autowired
    BdcSlFwxxFeignService bdcSlFwxxFeignService;
    @Autowired
    BdcSlFwcxFeignService bdcSlFwcxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcSlFeignService bdcSlFeignService;
    @Autowired
    BdcBhFeignService bdcBhFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    BdcSlHsxxFeignService bdcSlHsxxFeignService;
    @Autowired
    BdcSlHsxxMxFeignService bdcSlHsxxMxFeignService;
    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;
    @Autowired
    AcceptBdcdyFeignService acceptBdcdyFeignService;
    @Autowired
    BdcSlJyxxCezsFeignService bdcSlJyxxCezsFeignService;
    @Autowired
    BdcGzyzFeignService bdcGzyzFeignService;
    @Autowired
    private BdcInitRestService bdcInitFeignService;
    @Autowired
    BdcYctbFeignService bdcYctbFeignService;

    @Autowired
    private TaskUtils taskUtils;

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    OfficePdfServiceImpl officePdfService;

    @Value("${ycsl.taxurl:}")
    public String taxurl;

    @Value("${print.path:}")
    private String printPath;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    /**
     * 单个项目一窗页面
     */
    private static final String YCSL_HTML = "/view/ycsl/ycym.html";
    /**
     * 多个项目一窗页面
     */
    private static final String YCSL_PL_HTML = "/view/ycsl/ycymList.html";

    /**
     * 单个项目税务信息页面
     */
    private static final String SWXX_HTML = "/view/ycsl/swxx.html";
    /**
     * 多个项目税务信息页面
     */
    private static final String SWXX_PL_HTML = "/view/ycsl/swxxList.html";

    private static final List<Integer> sssplyList = new ArrayList<>(4);

    static {
        sssplyList.add(CommonConstantUtils.SPLY_YCSL);
        sssplyList.add(CommonConstantUtils.SPLY_WWSQ_YCSL);
        sssplyList.add(CommonConstantUtils.SPLY_YCTB_SS);
    }

    @GetMapping("/v1.0/ycym")
    @ResponseStatus(HttpStatus.OK)
    public String forwardYcslHtml(@RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        List<BdcXmDO> bdcXmDOList = new ArrayList<>();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        bdcXmQO.setSplys(sssplyList);
        // 查询审批来源为一窗的项目信息
        List<BdcXmDO> ycslXmList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(ycslXmList)) {
            bdcXmDOList.addAll(ycslXmList);
        }
        if (bdcXmDOList.size() == 1) {
            return YCSL_HTML;
        }
        return YCSL_PL_HTML;
    }

    /**
     * 税务信息页面跳转
     *
     * @param processInsId 工作流实例id
     * @return 页面路径
     */
    @GetMapping("/v1.0/swxx")
    @ResponseStatus(HttpStatus.OK)
    public String forwardSwxxHtml(@RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        List<BdcXmDO> bdcXmDOList = new ArrayList<>();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        bdcXmQO.setSplys(sssplyList);
        // 查询审批来源为一窗的项目信息
        List<BdcXmDO> ycslXmList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(ycslXmList)) {
            bdcXmDOList.addAll(ycslXmList);
        }
        if (bdcXmDOList.size() <= 1) {
            return SWXX_HTML;
        }
        return SWXX_PL_HTML;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例ID查询一窗受理相关信息
     */
    @ResponseBody
    @GetMapping("/bygzlid")
    public BdcSlXmDO listBdcSlXmByGzlslid(String gzlslid) {
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlXmDO> list = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(list)) {
                bdcSlXmDO = list.get(0);
            }
        }
        return bdcSlXmDO;
    }


    /**
     * @param gzlslid 工作流实例ID
     * @return 项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例ID查询一窗受理相关信息
     * @description 以xmid为准，批量流程打开（xmid），组合页面展示一窗受理tab（xmid），简单流程展示无xmid用gzlslid去查
     */
    @ResponseBody
    @GetMapping("")
    public Object queryYcslXx(String gzlslid, String xmid) {
        if (StringUtils.isBlank(xmid)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (bdcSlJbxxDO != null) {
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    xmid = bdcSlXmDOList.get(0).getXmid();
                }
            }
        }
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        YcslYmxxDTO ycslYmxxDTO = bdcYcslFeignService.queryYcslYmxx(xmid);

        List<BdcSlSqrDTO> bdcSlZrfDTOList = ycslYmxxDTO.getBdcSlZrfDTOList();
        List<BdcSlSqrDTO> listQlr = setSxh(bdcSlZrfDTOList);
        ycslYmxxDTO.setBdcSlZrfDTOList(listQlr);

        List<BdcSlSqrDTO> bdcSlZcfDTOList = ycslYmxxDTO.getBdcSlZcfDTOList();
        List<BdcSlSqrDTO> listYwr = setSxh(bdcSlZcfDTOList);
        ycslYmxxDTO.setBdcSlZcfDTOList(listYwr);

        return ycslYmxxDTO;
    }

    /**
     * 获取一窗批量页面信息
     *
     * @param gzlslid 工作流实例ID
     * @return 页面信息
     */
    @ResponseBody
    @GetMapping("/pl")
    public Object queryYcslXxPl(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (Objects.isNull(bdcSlJbxxDO)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到受理基本信息");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
        if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到受理项目信息");
        }
        List<YcslYmxxDTO> ycslYmxxDTOList = new ArrayList<>(bdcSlXmDOList.size());
        for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
            YcslYmxxDTO ycslYmxxDTO = bdcYcslFeignService.queryYcslYmxx(bdcSlXmDO.getXmid());

            List<BdcSlSqrDTO> bdcSlZrfDTOList = ycslYmxxDTO.getBdcSlZrfDTOList();
            List<BdcSlSqrDTO> listQlr = setSxh(bdcSlZrfDTOList);
            ycslYmxxDTO.setBdcSlZrfDTOList(listQlr);

            List<BdcSlSqrDTO> bdcSlZcfDTOList = ycslYmxxDTO.getBdcSlZcfDTOList();
            List<BdcSlSqrDTO> listYwr = setSxh(bdcSlZcfDTOList);
            ycslYmxxDTO.setBdcSlZcfDTOList(listYwr);

            ycslYmxxDTO.setBdcSlXmDO(bdcSlXmDO);
            ycslYmxxDTOList.add(ycslYmxxDTO);
        }
        return ycslYmxxDTOList;
    }

    /**
     * 根据不动产受理XMID更新受理项目信息，
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: BdcSlXmDO 不动产受理受理项目信息
     * @return: Integer 更新状态
     */
    @ResponseBody
    @PatchMapping("/xmxx")
    public Integer updateXmxxByXmid(@RequestBody BdcSlXmDO bdcSlXmDO) {
        return bdcSlXmFeignService.updateBdcSlXm(bdcSlXmDO);
    }

    /**
     * @param json 合同信息json
     * @return 更新数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存合同信息
     */
    @ResponseBody
    @PatchMapping("/htxx")
    public BdcSlJyxxDO updateHtxx(@RequestBody String json) {

        BdcSlJyxxDO bdcSlJyxxDO = JSONObject.parseObject(json, BdcSlJyxxDO.class);
        //更新合同信息接口
        bdcSlJyxxDO = bdcSlJyxxFeignService.saveBdcSlJyxx(bdcSlJyxxDO);
        return bdcSlJyxxDO;
    }

    /**
     * @param json 房屋信息json
     * @return 更新数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存房屋信息
     */
    @ResponseBody
    @PatchMapping("/fwxx")
    public Integer updateFwxx(@RequestBody String json) {

        BdcSlFwxxDO bdcSlFwxxDO = JSONObject.parseObject(json, BdcSlFwxxDO.class);
        //更新房屋信息接口
        return bdcSlFwxxFeignService.updateBdcSlFwxx(bdcSlFwxxDO);
    }

    /**
     * 根据xmid更新房屋信息
     *
     * @param json 房屋信息json
     * @return 更新数量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @PatchMapping("/fwxxbyxmid")
    public Integer updateFwxxByXmid(@RequestBody String json) {
        BdcSlFwxxDO bdcSlFwxxDO = JSONObject.parseObject(json, BdcSlFwxxDO.class);
        //更新房屋信息接口
        return bdcSlFwxxFeignService.updateBdcSlFwxxByXmid(bdcSlFwxxDO);
    }

    /**
     * @param json 核税信息json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新或保存核税信息
     * @date : 2020/5/19 14:17
     */
    @ResponseBody
    @PatchMapping("/hsxx")
    public Object updateHsxx(@RequestBody String json) {
        BdcSlHsxxDO bdcSlHsxxDO = JSONObject.parseObject(json, BdcSlHsxxDO.class);
        if (StringUtils.isBlank(bdcSlHsxxDO.getHsxxid())) {
            return bdcSlHsxxFeignService.insertBdcSlHsxxDO(bdcSlHsxxDO);
        }
        return bdcSlHsxxFeignService.updateBdcSlHsxx(bdcSlHsxxDO);
    }

    /**
     * @param json 不动产交易差额征收JSON
     * @return 更新数量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 保存不动产差额征收信息
     */
    @ResponseBody
    @PatchMapping("/jyxxcezs")
    public BdcSlJyxxCezsDO modifyJyxxCezs(@RequestBody String json) {
        Preconditions.checkArgument(StringUtils.isNotBlank(json), "缺少交易差额征收信息。");
        BdcSlJyxxCezsDO bdcSlJyxxCezsDO = JSONObject.parseObject(json, BdcSlJyxxCezsDO.class);
        return bdcSlJyxxCezsFeignService.saveBdcSlJyxxCezs(bdcSlJyxxCezsDO);
    }

    /**
     * @param fcjyxxQO
     * @return
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取房屋交易信息
     */
    @ResponseBody
    @GetMapping("/fcjyxx")
    public Object queryYcslFcjyxx(FcjyxxQO fcjyxxQO) {
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getHtbh()), "缺失合同编号");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getXmid()), "缺失当前项目ID");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getLclx()), "缺失流程类型参数");
        return bdcSlJyxxFeignService.queryFcjyClfHtxx(fcjyxxQO);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 宣城查询交易信息接口, 没有合同编号的先用不动产单元号查询合同编号，再查交易信息
     * @date : 2022/12/15 13:45
     */
    @ResponseBody
    @GetMapping("/fcjyxx/bdcdyh")
    public Object queryFcjyxxByBdcdyh(FcjyxxQO fcjyxxQO) {
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getXmid()), "缺失当前项目ID");
        Preconditions.checkArgument(StringUtils.isNotBlank(fcjyxxQO.getLclx()), "缺失流程类型参数");
        String htbh = fcjyxxQO.getHtbh();
        if (StringUtils.isBlank(fcjyxxQO.getHtbh())) {
            List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(fcjyxxQO.getXmid());
            if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                htbh = bdcSlJyxxDOList.get(0).getHtbh();
            }
        }
        if (StringUtils.isBlank(htbh)) {
            BdcXmQO bdcXmQO = new BdcXmQO(fcjyxxQO.getXmid());
            List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                Map paramMap = new HashMap(1);
                paramMap.put("bdcdyh", bdcXmList.get(0).getBdcdyh());
                Object response = exchangeInterfaceFeignService.requestInterface("xcFcjySpfQlr", paramMap);
                if (Objects.nonNull(response)) {
                    Map resultMap = (Map) response;
                    JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(resultMap.get("data")));
                    if (CollectionUtils.isNotEmpty(jsonArray)) {
                        JSONObject jsonObject = (jsonArray.getJSONObject(0));
                        htbh = jsonObject.getString("htbh");
                    }
                    Preconditions.checkArgument(StringUtils.isNotBlank(htbh), "接口返回值缺失合同编号");
                }
            }
        }
        fcjyxxQO.setHtbh(htbh);
        fcjyxxQO.setBeanName("xcFcjySpfBaxx");
        return bdcSlJyxxFeignService.queryFcjyClfHtxx(fcjyxxQO);
    }

    /**
     * @param xmid 项目ID
     * @return 房屋信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 房屋信息查询
     */
    @ResponseBody
    @GetMapping("/fwcx")
    public Object fwcx(String xmid) {
        BdcFwxxDTO bdcFwxxDTO = bdcSlFwcxFeignService.listFwxxByXmid(xmid);
        // 做完房查后 需要更新其余项目的fwtc字段
        updateOtherFwtc(xmid);
        return bdcFwxxDTO;
    }

    /**
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取房屋套次信息
     */
    @ResponseBody
    @GetMapping("/fwtcxx")
    public BdcFwxxDTO listBdcSlFwtcxxByXmid(String xmid) {
        BdcFwxxDTO bdcFwxxDTO = new BdcFwxxDTO();
        //转入方
        List<BdcSlFwtcxxDO> zrffwtcxxList = bdcSlFwcxFeignService.listBdcSlFwtcxxByXmid(xmid, CommonConstantUtils.QLRLB_QLR);
        if (CollectionUtils.isNotEmpty(zrffwtcxxList)) {
            bdcFwxxDTO.setBdcZrfZfxxDTOList(zrffwtcxxList);
        }
        //转出方
        List<BdcSlFwtcxxDO> zcffwtcxxList = bdcSlFwcxFeignService.listBdcSlFwtcxxByXmid(xmid, CommonConstantUtils.QLRLB_YWR);
        if (CollectionUtils.isNotEmpty(zcffwtcxxList)) {
            bdcFwxxDTO.setBdcZcfZfxxDTOList(zcffwtcxxList);
        }

        return bdcFwxxDTO;
    }

    /**
     * @param xmid 项目ID
     * @return 受理交易信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID查询交易信息
     */
    @ResponseBody
    @GetMapping("/jyxx")
    public Object jyxx(String xmid) {

        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
            return bdcSlJyxxDOList.get(0);
        } else {
            return null;
        }
    }

    /**
     * @param xmid 项目ID
     * @return 受理交易信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据项目ID查询房屋信息
     */
    @ResponseBody
    @GetMapping("/fwxx")
    public Object fwxx(String xmid) {
        List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxFeignService.listBdcSlFwxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList)) {
            return bdcSlFwxxDOList.get(0);
        }
        return null;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量保存交易信息（用于批量流程）
     */
    @ResponseBody
    @PatchMapping("/jyxx/list")
    public int updateJyxxPl(@RequestBody String json, @RequestParam("processInsId") String processInsId, @RequestParam(name = "djxl", required = false) String djxl, String xmids) {
        List<String> xmidList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmids)) {
            xmidList = Arrays.asList(xmids.split(CommonConstantUtils.ZF_YW_DH));
        }
        return bdcSlJyxxFeignService.updateBatchBdcSlJyxx(processInsId, json, xmidList);
    }

    /**
     * @param xmid 项目ID
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按照项目跟相关受理交易信息
     */
    @ResponseBody
    @PatchMapping("/jyxx/xm")
    public int updateXmJyxx(@RequestBody String json, @RequestParam("xmid") String xmid, @RequestParam(name = "djxl", required = false) String djxl) {
        return bdcSlJyxxFeignService.updateXmSlJyxx(xmid, json, djxl);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 维修资金缴纳情况验证
     */
    @ResponseBody
    @GetMapping("/queryHfwxzjJnzt")
    public String queryHfwxzjJnzt(@RequestParam(name = "xmid", required = false) String xmid) {
        return bdcSlJyxxFeignService.queryHfwxzjJnzt(xmid);
    }

    /**
     * @param bdcTsDjxxRequestDTO 一窗推送登记请求对象
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送一窗创建登记流程
     */
    @ResponseBody
    @PostMapping("/tsBdcDjxx")
    public InitTsBdcDjxxResponseDTO initTsBdcDjxx(@RequestBody BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO) throws Exception {
        return bdcSlFeignService.initTsBdcDjxx(bdcTsDjxxRequestDTO);
    }


    /**
     * 推送到登记前的验证
     *
     * @param xmid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/checkBeforeTsBdcDj/{xmid}")
    public Object checkBeforeTsBdcDj(@PathVariable("xmid") String xmid) {
        // 验证bdc_sl_hsxx_mx是否有值
        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setXmid(xmid);
        Map map = new HashMap();
        List<BdcSlHsxxDO> list = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                String hsxxid = list.get(i).getHsxxid();
                List<BdcSlHsxxMxDO> listMx = bdcSlHsxxMxFeignService.bdcSlHsxxMx(hsxxid);
                if (CollectionUtils.isEmpty(listMx)) {
                    map.put("result", false);
                    map.put("msg", "缺少完税明细信息，请检查！");
                    return map;
                }
            }
        } else {
            map.put("result", false);
            map.put("msg", "缺少完税信息，请检查！");
            return map;
        }

        map.put("result", true);
        map.put("msg", "");
        return map;
    }


    /**
     * 查询交税按钮操作所需要的信息
     *
     * @param xmid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/getTaxInfo/{xmid}")
    public Object payTaxInfo(@PathVariable("xmid") String xmid) {
        if (StringUtils.isEmpty(xmid)) {
            throw new AppException("未获取到xmid");
        }
        Map map = new HashMap();
        // 交税页面url
        map.put("url", taxurl);
        // 查询是否有核税信息明细
        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setXmid(xmid);
        List<BdcSlHsxxDO> list = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        if (CollectionUtils.isNotEmpty(list)) {
            String hsxxid = list.get(0).getHsxxid();
            map.put("ytsswzt", list.get(0).getYtsswzt());
            List<BdcSlHsxxMxDO> listHsxxMx = bdcSlHsxxMxFeignService.bdcSlHsxxMx(hsxxid);
            map.put("hsxxmx", listHsxxMx);
            // 有明细数据了 则直接return
            if (CollectionUtils.isNotEmpty(listHsxxMx)) {
                return map;
            }
        } else {
            map.put("hsxxmx", null);
            map.put("ytsswzt", 0);
        }
        // 获取htbh
        List<BdcSlJyxxDO> listJyxx = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(listJyxx)) {
            map.put("htbh", listJyxx.get(0).getHtbh());
        } else {
            throw new AppException("没有合同编号！");
        }
        return map;
    }

    /**
     * 更新ytsswzt
     *
     * @param xmid
     */
    @ResponseBody
    @GetMapping(value = "/updateYtsswzt/{xmid}")
    public void updateYtsswzt(@PathVariable("xmid") String xmid) {
        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setXmid(xmid);
        List<BdcSlHsxxDO> list = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                String hsxxid = list.get(i).getHsxxid();
                BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                bdcSlHsxxDO.setHsxxid(hsxxid);
                bdcSlHsxxDO.setYtsswzt(CommonConstantUtils.SF_S_DM);
                bdcSlHsxxFeignService.updateBdcSlHsxx(bdcSlHsxxDO);
            }
        }
    }


    /**
     * @param jyxxid 交易信息ID
     * @return 受理编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成合同编号
     */
    @ResponseBody
    @PostMapping("/generateHtbh")
    public String generateHtbh(String jyxxid) {
        if (StringUtils.isBlank(jyxxid)) {
            throw new MissingArgumentException("jyxxid");
        }
        String htbh = bdcBhFeignService.queryCommonBh(Constants.YWLX_HTBH, CommonConstantUtils.ZZSJFW_DAY, 5, "F");
        //存储合同编号
        BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
        bdcSlJyxxDO.setJyxxid(jyxxid);
        bdcSlJyxxDO.setHtbah(htbh);
        bdcSlJyxxDO.setHtbh(htbh);
        bdcSlJyxxFeignService.saveBdcSlJyxx(bdcSlJyxxDO);
        return htbh;
    }

    /**
     * @param xmid  项目ID
     * @param sqrlb 申请人类别
     * @return 无返回值
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description <p> 1、南通调用升级平台接口获取家庭成员信息，并同步至受理家庭成员信息。
     * 2、调用接口获取房屋套次信息，与申报套次进行校验
     */
    @ResponseBody
    @PostMapping("/getFwtcxx")
    public void getFwtcxx(String xmid, String sqrlb) {
        //调取查询接口获取房屋套次信息,与申报套次进行校验
        bdcSlFwcxFeignService.listBdcZfxxDTONT(xmid, sqrlb, true);
    }

    /**
     * 查询买卖双方及家庭成员住房信息, 将获取到的住房信息保存至 fwtcxx 中
     *
     * @param xmid 项目ID
     * @return 买卖双方房屋套次信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @GetMapping("/getAllFwtcxx/{xmid}")
    public BdcFwxxDTO getFwtcxx(@PathVariable(name = "xmid") String xmid) {
        BdcFwxxDTO bdcFwxxDTO = new BdcFwxxDTO();
        List<BdcSlFwtcxxDO> qlrFwtcxxList = bdcSlFwcxFeignService.listBdcZfxxDTONT(xmid, CommonConstantUtils.QLRLB_QLR, false);
        bdcFwxxDTO.setBdcZrfZfxxDTOList(qlrFwtcxxList);
        List<BdcSlFwtcxxDO> ywrFwtcxxList = bdcSlFwcxFeignService.listBdcZfxxDTONT(xmid, CommonConstantUtils.QLRLB_YWR, false);
        bdcFwxxDTO.setBdcZcfZfxxDTOList(ywrFwtcxxList);
        return bdcFwxxDTO;
    }

    /**
     * 房屋套次信息对比
     *
     * @param compareFwtcxxQO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @ResponseBody
    @PostMapping("nantong/compareFwtcxx")
    public CompareFwtcxxResultDTO compareFwtcxx(@RequestBody CompareFwtcxxQO compareFwtcxxQO) {
        if (StringUtils.isBlank(compareFwtcxxQO.getXmid()) && StringUtils.isBlank(compareFwtcxxQO.getSqrid())) {
            throw new AppException("项目ID,申请人ID不能均为空");
        }
        //调取查询接口获取房屋套次信息,与申报套次进行校验
        return bdcSlFwcxFeignService.compareFwtcxx(compareFwtcxxQO);
    }


    /**
     * @param gzlslid
     * @param loadpage 是否分页
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 一窗列表分页
     */
    @ResponseBody
    @GetMapping(value = "/listYcslxxByPage")
    public Object listYcslxxByPage(@LayuiPageable Pageable pageable, String gzlslid, Boolean loadpage) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("查询缺失参数gzlslid");
        }
        Map map = new HashMap();
        map.put("gzlslid", gzlslid);
        map.put("qllxs", Arrays.asList(CommonConstantUtils.QLLX_FDCQ));
        String ycslxxQO = JSON.toJSONString(map);
        if (loadpage != null && loadpage) {
            return addLayUiCode(bdcSlFeignService.listYcslxxByPage(pageable, ycslxxQO));
        } else {
            return bdcSlFeignService.listYcslxxList(ycslxxQO);
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 交易信息列表保存
     */
    @ResponseBody
    @PatchMapping("/updateJyxxList")
    public Integer updateJyxxList(@RequestBody String json) {
        Integer count = 0;
        for (Object obj : JSON.parseArray(json)) {
            count += bdcSlEntityFeignService.updateByJsonEntity(JSON.toJSONString(obj), BdcSlJyxxDO.class.getName());
        }
        return count;
    }

    /**
     * 更新其余房屋套次信息
     *
     * @param xmid
     */
    private void updateOtherFwtc(String xmid) {
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setXmid(xmid);
        List<BdcSlSqrDO> qlrList = bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid,
                CommonConstantUtils.QLRLB_QLR);
        int qlrsize = qlrList.size();
        List<BdcSlSqrDO> ywrList = bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid,
                CommonConstantUtils.QLRLB_YWR);
        int ywrsize = ywrList.size();
        // 某个户室(项目)
        List<BdcSlXmDO> list = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);

        if (CollectionUtils.isNotEmpty(list)) {
            String jbxxid = list.get(0).getJbxxid();
            bdcSlXmQO.setXmid(null);
            bdcSlXmQO.setJbxxid(jbxxid);
            // 所有户室
            list = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
            // 遍历该流程，把当前项目以外的权利人的sqrid拿到
            for (int i = 0; i < list.size(); i++) {
                // 不是本身流程则需要更新fwtc
                if (!xmid.equals(list.get(i).getXmid())) {
                    // 排除掉某个户室后的 某个户室的所有qlr
                    List<BdcSlSqrDO> listSqrQlr = bdcSlSqrFeignService.listBdcSlSqrByXmid(list.get(i).getXmid(),
                            CommonConstantUtils.QLRLB_QLR);
                    // 当两个流程的size一致（权利人）
                    if (listSqrQlr.size() == qlrsize) {
                        for (int j = 0; j < listSqrQlr.size(); j++) {
                            // 找到这个权利人 的首个房屋套次
                            String sqrZjh = listSqrQlr.get(j).getZjh();
                            for (int q = 0; q < qlrList.size(); q++) {
                                String qlrzjh = qlrList.get(q).getZjh();
                                if (sqrZjh.equals(qlrzjh)) {
                                    BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                                    bdcSlSqrQO.setXmid(qlrList.get(q).getXmid());
                                    bdcSlSqrQO.setZjh(qlrzjh);
                                    bdcSlSqrQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                                    List<BdcSlSqrDO> listSqr = bdcSlSqrFeignService.listBdcSlSqrByBdcSlSqrQO(bdcSlSqrQO);
                                    if (CollectionUtils.isNotEmpty(listSqr)) {
                                        // 找到房屋套次后 需要更新这个权利人后面的房屋套次
                                        updateFwtc(listSqr.get(0), list);
                                    }
                                }
                            }
                        }
                    }

                    // 排除掉某个户室后的 某个户室的所有ywr
                    List<BdcSlSqrDO> listSqrYwr = bdcSlSqrFeignService.listBdcSlSqrByXmid(list.get(i).getXmid(),
                            CommonConstantUtils.QLRLB_YWR);
                    // 当两个流程的size一致（权利人）
                    if (listSqrYwr.size() == ywrsize) {
                        for (int j = 0; j < listSqrYwr.size(); j++) {
                            // 找到这个权利人 的首个房屋套次
                            String sqrZjh = listSqrYwr.get(j).getZjh();
                            for (int q = 0; q < ywrList.size(); q++) {
                                String qlrzjh = ywrList.get(q).getZjh();
                                if (sqrZjh.equals(qlrzjh)) {
                                    BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                                    bdcSlSqrQO.setXmid(ywrList.get(q).getXmid());
                                    bdcSlSqrQO.setZjh(qlrzjh);
                                    bdcSlSqrQO.setSqrlb(CommonConstantUtils.QLRLB_YWR);
                                    List<BdcSlSqrDO> listSqr = bdcSlSqrFeignService.listBdcSlSqrByBdcSlSqrQO(bdcSlSqrQO);
                                    if (CollectionUtils.isNotEmpty(listSqr)) {
                                        updateFwtc(listSqr.get(0), list);
                                        // 找到房屋套次后 需要更新这个权利人后面的房屋套次
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 房屋套次递进更新
     *
     * @param bdcSlSqrDO
     * @param list
     */
    public void updateFwtc(BdcSlSqrDO bdcSlSqrDO, List<BdcSlXmDO> list) {
        String fwtc = bdcSlSqrDO.getFwtc();
        List<BdcSlXmDO> exincludeList = new ArrayList<>();
        // 遍历该流程，把当前项目以外的权利人的sqrid拿到
        for (int i = 0; i < list.size(); i++) {
            // 不是本身流程则需要更新fwtc
            if (!bdcSlSqrDO.getXmid().equals(list.get(i).getXmid())) {
                exincludeList.add(list.get(i));
            }
        }
        if (CollectionUtils.isNotEmpty(exincludeList)) {
            for (int i = 0; i < exincludeList.size(); i++) {
                String xmid = exincludeList.get(i).getXmid();
                BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                bdcSlSqrQO.setXmid(xmid);
                bdcSlSqrQO.setZjh(bdcSlSqrDO.getZjh());
                bdcSlSqrQO.setSqrlb(bdcSlSqrDO.getSqrlb());
                List<BdcSlSqrDO> listSqr = bdcSlSqrFeignService.listBdcSlSqrByBdcSlSqrQO(bdcSlSqrQO);
                if (CollectionUtils.isNotEmpty(listSqr)) {
                    if (StringUtils.isEmpty(listSqr.get(0).getFwtc())) {
                        BdcSlSqrDO bdcSlSqrDOOther = listSqr.get(0);
                        if (Integer.parseInt(fwtc) > 1) {
                            if (i == 0 || i == 1) {
                                bdcSlSqrDOOther.setFwtc((Integer.parseInt(fwtc) + 1) + "");
                            } else {
                                bdcSlSqrDOOther.setFwtc(CommonConstantUtils.FWTC_QT);
                            }
                        } else {
                            bdcSlSqrDOOther.setFwtc(CommonConstantUtils.FWTC_QT);
                        }
                        bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDOOther);
                    }
                }
            }
        }
    }

    /**
     * @param bdcSqrDOList 申请人列表
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 设置申请人顺序号
     */
    private List<BdcSlSqrDTO> setSxh(List<BdcSlSqrDTO> bdcSqrDOList) {
        if (CollectionUtils.isNotEmpty(bdcSqrDOList)) {
            int qlrsxh = 0;
            int ywrsxh = 0;
            for (int i = 0; i < bdcSqrDOList.size(); i++) {
                int sxh = 0;
                BdcSlSqrDO bdcSlSqrDO = bdcSqrDOList.get(i).getBdcSlSqrDO();
                if (StringUtils.equals(bdcSlSqrDO.getSqrlb(), CommonConstantUtils.QLRLB_QLR)) {
                    qlrsxh++;
                    sxh = qlrsxh;
                }
                if (StringUtils.equals(bdcSlSqrDO.getSqrlb(), CommonConstantUtils.QLRLB_YWR)) {
                    ywrsxh++;
                    sxh = ywrsxh;
                }
                if (bdcSlSqrDO.getSxh() == null || bdcSlSqrDO.getSxh() == 0) {
                    bdcSlSqrDO.setSxh(sxh);
                    bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDO);
                }
            }
        }
        return bdcSqrDOList;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例获取受理项目集合
     */
    @ResponseBody
    @GetMapping("/list/bdcslxm")
    public List<BdcSlXmDO> listBdcSlXm(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失工作流实例ID");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        /* 根据xmid排序 */
        bdcSlXmDOList.sort(Comparator.comparing(BdcSlXmDO::getXmid));
        return bdcSlXmDOList;
    }


    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据工作流实例ID通知互联网附件错误
     */
    @ResponseBody
    @GetMapping("/online/feedback")
    public void onlineFeedback(@RequestParam(name = "gzlslid") String gzlslid,
                               @RequestParam(name = "opinion") String opinion) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "未获取到工作流实例ID。");
        exchangeInterfaceFeignService.workflowSyncRequestInterface("wwsqupdateslzt", gzlslid, null, opinion, HlwSlztEnum.FJERROR.getSlzt());
    }

    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 蚌埠一窗页面
     * @date : 2020/5/19 9:03
     */
    @ResponseBody
    @GetMapping("/bengbu")
    public Object queryYcslYmxx(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new AppException("查询页面信息缺失项目id");
        }
        BbYcslYmDTO bbYcslYmDTO = new BbYcslYmDTO();
        List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxFeignService.listBdcSlFwxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList)) {
            bbYcslYmDTO.setBdcSlFwxxDO(bdcSlFwxxDOList.get(0));
        }
        List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
            bbYcslYmDTO.setBdcSlJyxxDO(bdcSlJyxxDOList.get(0));
        }
        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
        bdcSlHsxxQO.setXmid(xmid);
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
            bbYcslYmDTO.setBdcSlHsxxDO(bdcSlHsxxDOList.get(0));
        }
        return bbYcslYmDTO;
    }


    /**
     * 通过工作流实例ID判断房产交易类型
     *
     * @param gzlslid 工作流实例ID
     * @return spf 商品房， clf 存量房
     */
    @ResponseBody
    @GetMapping("/checkSpfLc")
    public String checkLcLx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("未获取到工作流实例ID信息");
        }
        BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (null == bdcSlJbxxDO) {
            throw new MissingArgumentException("未获取到流程基本信息");
        }
        if (bdcGzyzFeignService.checkSpfLc(bdcSlJbxxDO.getGzldyid())) {
            return CommonConstantUtils.FCJY_TYPE_SPF;
        }
        return CommonConstantUtils.FCJY_TYPE_CLF;
    }

    /**
     * 税务推送接收信息
     *
     * @param gzlslid 工作流实例id
     * @return
     */
    @ResponseBody
    @GetMapping("/tsjssj")
    public Object querySwTsjssj(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("未获取到工作流实例ID信息");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            HashMap<String, String> map = new HashMap<>();
            BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
            bdcSlHsxxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
            List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
            if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                String swjssj = bdcSlHsxxDOList.get(0).getJssksj() == null ? "" : DateFormatUtils.format(bdcSlHsxxDOList.get(0).getJssksj(), "yyyy-MM-dd HH:mm:ss");
                map.put("swjssj", swjssj);
                // 是否属于企业AB级交易过户
                map.put("qyjyzsbz", String.valueOf(bdcSlHsxxDOList.get(0).getQyjyzsbz() == null ? "" : bdcSlHsxxDOList.get(0).getQyjyzsbz()));
            }
            String swtssj = bdcSlXmDOList.get(0).getTsswsj() == null ? "" : DateFormatUtils.format(bdcSlXmDOList.get(0).getTsswsj(), "yyyy-MM-dd HH:mm:ss");
            map.put("swtssj", swtssj);
            return map;
        }
        return null;

    }

    @GetMapping("/shbtg")
    public void shbtg(String gzlslid, String reason) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("未获取到工作流实例ID信息");
        }
        try {
            BdcDeleteYwxxDTO bdcDeleteYwxxDTO = new BdcDeleteYwxxDTO();
            bdcDeleteYwxxDTO.setGzlslid(gzlslid);
            bdcDeleteYwxxDTO.setReason(reason);
            bdcDeleteYwxxDTO.setSlzt(HlwSlztEnum.ABANDON.getSlzt());
            bdcInitFeignService.deleteYwxxAllSubsystem(bdcDeleteYwxxDTO);
        } catch (Exception e) {
            LOGGER.error("删除业务信息异常，processInsId为：" + gzlslid, e);
            return;
        }

        try {
            // 删除流程
            taskUtils.deleteTask(gzlslid, reason);
        } catch (Exception e) {
            LOGGER.error("删除流程信息异常，processInsId为：" + gzlslid, e);
        }

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证一窗受理比对信息
     */
    @GetMapping("/bdxx")
    @ResponseBody
    public boolean checkYcslBdxx(String xmid, Boolean spflc) {
        return bdcGzyzFeignService.checkYcslBdxx(xmid, spflc);

    }

    /**
     * @param compareFwtcxxQO 套次查询信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 导入婚姻比对信息
     */
    @ResponseBody
    @PostMapping("import/fwtcbdxx")
    public void drhybdxx(@RequestBody CompareFwtcxxQO compareFwtcxxQO) {
        bdcSlFwcxFeignService.drFwtcxx(compareFwtcxxQO);
    }


    @ResponseBody
    @GetMapping("/tzspt")
    public Object tzspt(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("未获取到工作流实例ID信息");
        }
        return bdcYctbFeignService.yctbAddTaxInfo(gzlslid);
    }


    /**
     * @param fjxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/5 10:10
     */
    @ResponseBody
    @PostMapping("/fjxx/clf")
    public void scClfFjxx(@NotBlank(message = "上传一体化附件信息不可为空") @RequestBody String fjxx) {
        JSONObject jsonObject = JSON.parseObject(fjxx);
        SjclUploadDTO sjclUploadDTO = new SjclUploadDTO();
        JSONArray fjArray = jsonObject.getJSONArray("fjnr");
        LOGGER.info("一体化附件信息{}", JSON.toJSONString(jsonObject));
        if (CollectionUtils.isNotEmpty(fjArray)) {
            //同一个合同号只能存在一个，防止重复上传先删除所有的，再上传新的
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(jsonObject.getString("gzlslid"), jsonObject.getString("htbh"));
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                bdcSlSjclFeignService.deleteBdcSlSjclBySjclid(bdcSlSjclDOList.get(0).getSjclid());
            }
            List<TsswDataFjclXxDTO> fjList = fjArray.toJavaList(TsswDataFjclXxDTO.class);
            if (CollectionUtils.isNotEmpty(fjList)) {
                sjclUploadDTO.setFjList(fjList);
            }
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(jsonObject.getString("gzlslid"));
            String djxl = "";
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                djxl = bdcXmDTOList.get(0).getDjxl();
            } else {
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(jsonObject.getString("gzlslid"));
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    djxl = bdcSlXmDOList.get(0).getDjxl();
                }
            }
            sjclUploadDTO.setGzlslid(jsonObject.getString("gzlslid"));
            sjclUploadDTO.setSjclmc(jsonObject.getString("htbh"));
            sjclUploadDTO.setDjxl(djxl);
            try {
                bdcSlSjclFeignService.createAndUploadFile(sjclUploadDTO);
            } catch (IOException e) {
                LOGGER.error("上传附件出错", e);
            }
        }
    }

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 获取pdf附件信息
     * @date : 2022/8/16 8:29
     */
    @ResponseBody
    @PostMapping("/fcjy/tsfjxx")
    public List<YcslFjxxDTO> queryFjxx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("参数信息缺失");
        }
        List<YcslFjxxDTO> list = bdcYcslFeignService.queryYcslFjxx(gzlslid);
        return list;
    }

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 查看pdf附件信息
     * @date : 2022/8/19 13:51
     */
    @SneakyThrows
    @ResponseBody
    @GetMapping("/fcjy/ckpdf")
    public void checkFjxx(String pdfpath, HttpServletResponse response) throws IOException {
            OutputStream outputStream = response.getOutputStream();
            File pdfFile = null;
            try {
                pdfFile = new File(pdfpath);
                String file = URLEncoder.encode(pdfpath + ".pdf", "utf-8");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + file);
                // 读取临时pdf文件，浏览器下载
                FileUtils.copyFile(pdfFile, outputStream);
            } catch (Exception e) {
                LOGGER.error("系统导出PDF报错：{}", e.toString());
                throw new AppException("系统导出PDF报错，处理终止");
            }
        }


    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 组合打印pdf附件信息
     * @date : 2022/8/20 13:51
     */
    @ResponseBody
    @GetMapping("/hsxx/pdfprint")
    public void printFjxx(HttpServletResponse response, String[] json) throws IOException {
        Map map = new HashMap();

        if (null != json){
            for(int i = 0; i<json.length; i++){
                map.put(String.valueOf(i),json[i]);
            }
        }
        File pdfFile = null;
        OutputStream outputStream = response.getOutputStream();
        try {
            String xmlStr = officePdfService.mergePdfFiles(map);
            pdfFile = new File(xmlStr);
            String file = URLEncoder.encode(xmlStr + ".pdf", "utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + file);
            // 读取临时pdf文件，浏览器下载
            FileUtils.copyFile(pdfFile, outputStream);
        } catch (Exception e) {
            LOGGER.error("系统导出PDF报错：{}", e.toString());
            throw new AppException("系统导出PDF报错，处理终止");
        } finally {
            IOUtils.closeQuietly(outputStream);
            org.apache.commons.io.FileUtils.deleteQuietly(pdfFile);
        }
    }

    /**
     * @param gzlslid
     * @return 受理交易信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">sunyuzhuang</a>
     * @description 根据gzlslid查询交易信息
     */
    @ResponseBody
    @GetMapping("/getJyxxByGzlslid")
    public Object getJyxxByGzlslid(String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(gzlslid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOS)){
            for (BdcXmDO bdcXmDO : bdcXmDOS) {
                List<BdcSlJyxxDO> bdcSlJyxxDOS = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(bdcXmDO.getXmid());
                if(CollectionUtils.isNotEmpty(bdcSlJyxxDOS)){
                    return bdcSlJyxxDOS.get(0);
                }
            }
        }
        return null;
    }
}
