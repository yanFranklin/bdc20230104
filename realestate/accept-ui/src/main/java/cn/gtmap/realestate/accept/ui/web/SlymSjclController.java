package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.ui.config.SwspConfig;
import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.sw.response.NtSwWsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.sw.response.QswsfjxxListbean;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QueryQswsxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSjclQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/17
 * @description 受理页面收件材料相关控制层
 */
@Controller
@RequestMapping("/slym/sjcl")
@Validated
public class SlymSjclController extends BaseController {
    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcGzyzFeignService bdcGzyzFeignService;

    @Autowired
    SwspConfig swspConfig;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    StorageClientMatcher storageClientMatcher;

    //    文件管理器权限设置
    private static final String wjglqqx = "{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanDelete\":0,\"CanRename\":0,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":0,\"CanTakePhoto\":0,\"CanScan\":0,\"CanEdit\":-1}";
    /*
     * 文件管理器默认权限
     * */
    private static final String defaultAuthority = "{\"CanRefresh\":1,\"CanCreateNewFolder\":0,\"CanAddFolder\":0,\"CanDeleteFolder\":0,\"CanRenameFolder\":0,\"CanAddFile\":1,\"CanDeleteFile\":1,\"CanRenameFile\":1,\"CanDelete\":1,\"CanRename\":1,\"CanPrint\":1,\"CanDownload\":1,\"CanUpload\":1,\"CanTakePhoto\":1,\"CanScan\":1,\"CanEdit\":-1}";

    /**
     * @param processInsId 工作流实例ID
     * @return 收件材料集合
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID获取收件材料集合（用于批量流程）
     */
    @ResponseBody
    @GetMapping("/list/pl")
    public Object listSjclPl(String processInsId, String taskId) {
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(processInsId);
        if (StringUtils.isNotBlank(taskId)) {
            TaskData taskData = processTaskClient.getTaskById(taskId);
            //非受理节点只展示收取部门为登记的数据
            if (Objects.nonNull(taskData) && !StringUtils.equals(CommonConstantUtils.JD_SL, taskData.getTaskName())) {
                bdcSlSjclDOList = bdcSlSjclDOList.stream().filter(bdcSlSjclDO -> StringUtils.isNotBlank(bdcSlSjclDO.getSqbm()) && StringUtils.indexOf(bdcSlSjclDO.getSqbm(), CommonConstantUtils.SJCL_SQBM_DJ_MC) > -1).collect(Collectors.toList());
            }
        }
        return bdcSlSjclDOList;
    }

    /**
     * @param json 页面传输收件材料Json
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 批量修改收件材料（用于简单流程、批量流程、组合流程）
     */
    @ResponseBody
    @PatchMapping(value = "/list")
    public Integer updateBdcSlSjcl(@RequestBody String json) {
        return bdcSlSjclFeignService.updateBdcSlSjcl(json);
    }

    /**
     * @param sjclid 收件材料ID
     * @param czlx   操作类型
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 修改收件材料顺序号（用于简单流程、批量流程、组合流程）
     */
    @ResponseBody
    @GetMapping(value = "/sxh")
    public Integer changeSjclSxh(String sjclid, String czlx) {
        return bdcSlSjclFeignService.changeSjclSxh(sjclid, czlx);
    }

    /**
     * @param sjclid 收件材料ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收件材料ID 删除收件材料
     */
    @ResponseBody
    @DeleteMapping("")
    public void deleteBdcsjcl(@RequestParam("sjclid") String sjclid) {
        bdcSlSjclFeignService.deleteBdcSlSjclBySjclid(sjclid);
    }

    /**
     * @param gzlslid 工作流实例id
     * @param xmid    项目id
     * @return 更新页数数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新收件材料页数
     */
    @ResponseBody
    @PatchMapping("/ys")
    public Integer updateBdcSjclYs(String gzlslid, String xmid) {
        return bdcSlSjclFeignService.updateSjclYs(gzlslid, xmid);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组合流程更新页数接口
     * @date : 2021/12/20 13:45
     */
    @ResponseBody
    @PatchMapping("/ys/zhlc")
    public Integer updateZhlcSjclYs(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return 0;
        }
        return bdcSlSjclFeignService.updateZhlcSjclYs(gzlslid);
    }

    /**
     * @param processInsId 工作流实例ID
     * @param clmc         材料名称
     * @param sjclxmid     收件材料项目ID
     * @param sfhqqx       是否获取权限
     * @return 文件上传参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织文件上传参数
     */
    @ResponseBody
    @GetMapping("/bdcSlWjscDTO")
    public Object bdcSlWjscDTO(String processInsId, String sjclxmid, String clmc, Boolean sfhqqx, String djxl) {
        BdcSlWjscDTO bdcSlWjscDTO = new BdcSlWjscDTO();
        bdcSlWjscDTO.setToken(queryToken());
        bdcSlWjscDTO.setClientId(Constants.WJZX_CLIENTID);
        if (StringUtils.isNotBlank(clmc)) {
            BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
            bdcSlSjclQO.setGzlslid(processInsId);
            bdcSlSjclQO.setClmc(clmc);
            if (StringUtils.isNotBlank(djxl)) {
                bdcSlSjclQO.setDjxl(djxl);
            }
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjcl(bdcSlSjclQO);
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                bdcSlWjscDTO.setNodeId(bdcSlSjclDOList.get(0).getWjzxid());
            }
        }
        if (sfhqqx != null && sfhqqx) {
            bdcSlWjscDTO.setsFrmOption(wjglqqx);
        } else {
            bdcSlWjscDTO.setsFrmOption(defaultAuthority);
        }
        return bdcSlWjscDTO;

    }

    /**
     * @param sjclids 收件材料ID数组
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收件材料ID 批量删除收件材料
     */
    @ResponseBody
    @DeleteMapping("/deleteBdcsjclPl")
    public void deleteBdcsjcl(@RequestParam("sjclids") String[] sjclids) {
        if (sjclids.length > 0) {
            for (int i = 0; i < sjclids.length; i++) {
                bdcSlSjclFeignService.deleteBdcSlSjclBySjclid(sjclids[i]);
            }
        }
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据gzlslid 删除所有的收件材料,若登记原因改变则重新根据登记原因关联收件材料
     * @date : 2019/12/12 16:49
     */
    @ResponseBody
    @GetMapping("/recreate")
    public Object reCreateSlSjcl(String gzlslid, Boolean recreate) {
        return bdcSlSjclFeignService.reCreateSjcl(gzlslid);
    }

    /**
     * @param htbh 合同编号
     * @param zjhm 证件号码
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取税票附件
     */
    @ResponseBody
    @GetMapping("/list/swspFj")
    public Object listSwspFj(String htbh, String zjhm, String gzlslid) throws Exception {
        Map param = new HashMap();
        if (StringUtils.isBlank(htbh) ||StringUtils.isBlank(gzlslid)) {
            throw new AppException("合同编号为空或工作流实例为空！");
        }
        List<BdcDsfSjclDTO> bdcDsfSjclDTOList = new ArrayList<>();
        param.put("htbh", htbh);
        param.put("zjhm", zjhm);
        Object response = exchangeInterfaceFeignService.requestInterface("nt_swspfj", param);
        if (response != null) {
            LOGGER.info("合同编号:{},调取税票附件接口成功,返回结果：{}", htbh, response);
            JSONObject fjxxObject = JSONObject.parseObject(JSONObject.toJSONString(response));
            if (fjxxObject.get("suc") != null &&"1".equals(fjxxObject.get("suc").toString())) {
                if(fjxxObject.get("data") != null) {
                    List<BdcSwspFjDTO> bdcSwspFjDTOList = JSONObject.parseArray(JSONObject.toJSONString(fjxxObject.get("data")),BdcSwspFjDTO.class);
                    if(CollectionUtils.isNotEmpty(bdcSwspFjDTOList)){
                        List<String> fjList =new ArrayList<>();
                        for(BdcSwspFjDTO bdcSwspFjDTO:bdcSwspFjDTOList){
                            if(StringUtils.isNotBlank(bdcSwspFjDTO.getFile()) &&StringUtils.isNotBlank(bdcSwspFjDTO.getPzhm())){
                                fjList.add(bdcSwspFjDTO.getFile());
                            }
                        }
                        if(CollectionUtils.isNotEmpty(fjList)) {
                            BdcDsfSjclDTO bdcDsfSjclDTO = new BdcDsfSjclDTO();
                            bdcDsfSjclDTO.setClmc("税票");
                            bdcDsfSjclDTO.setBase64fjList(fjList);
                            bdcDsfSjclDTOList.add(bdcDsfSjclDTO);
                        }
                    }
                    if (CollectionUtils.isNotEmpty(bdcDsfSjclDTOList)) {
                        bdcSlSjclFeignService.saveDsfSjcl(gzlslid, bdcDsfSjclDTOList);
                    }
                }
            } else {
                throw new AppException(fjxxObject.get("msg") != null ? fjxxObject.get("msg").toString() : "获取税票附件失败!");
            }
        }
        return bdcDsfSjclDTOList;
    }

    /**
     * @param qswsxxDTO 工作流实例id
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">caolu</a>
     * @description 南通获取契税完税信息
     */
    @ResponseBody
    @PostMapping("/list/swWsxx")
    public Object listNtSwWsxx(@RequestBody QueryQswsxxDTO qswsxxDTO) throws Exception {
        if (null == qswsxxDTO || StringUtils.isBlank(qswsxxDTO.getGzlslid())) {
            throw new AppException("工作流实例为空！");
        }
        String gzlslid = qswsxxDTO.getGzlslid();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("未获取到项目信息");
        }
        // htbh合同编号从前端获取
        String htbh = qswsxxDTO.getHtbh();
        // 如果为空则从后端获取
        if(StringUtils.isBlank(qswsxxDTO.getHtbh())){
            htbh = bdcXmDOList.get(0).getJyhth();
        }
        if(StringUtils.isBlank(htbh)){
            throw new AppException("合同编号为空！");
        }
        Map param = new HashMap();
        // 收件编号（合同编号）
        param.put("sjbh", htbh);
        // 取房屋项目信息
        bdcXmDOList = bdcXmDOList.stream().filter(t-> ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, t.getQllx())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("未获取到房屋项目信息");
        }
        // 收件日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        param.put("sjrq", simpleDateFormat.format(new Date()));
        if (StringUtils.isBlank(qswsxxDTO.getZlfclfbz())) {
            boolean flag = bdcGzyzFeignService.checkSpfLc(bdcXmDOList.get(0).getGzldyid());
            // 增量房存量房标志
            param.put("zlfclfbz", flag ? "1" : "2");
        } else {
            param.put("zlfclfbz", qswsxxDTO.getZlfclfbz());
        }

        // 数据归属地区(根据区县代码获得）
        String qxdm = bdcXmDOList.get(0).getQxdm();
        String qlrzjh = bdcXmDOList.get(0).getQlrzjh();
        if (StringUtils.isBlank(qlrzjh)) {
            throw new MissingArgumentException("未获取到证件号");
        }
        List<String> qlrzjhList = Arrays.asList(qlrzjh.split(","));
        param.put("sjgsdq", swspConfig.getQxdmSjgsdMap().get(qxdm));
        // 合同编号
        param.put("htbh", htbh);
        // 交易编号
        param.put("jyuuid", "");
        // 房屋编号
        param.put("fwuuid", "");
        // 土地房屋地址
        param.put("tdfwdz", "");
        // 纳税人名称
        param.put("nsrmc", "");
        // 电子税票号码
        if (StringUtils.isNotBlank(qswsxxDTO.getDzsphm())) {
            param.put("dzsphm", qswsxxDTO.getDzsphm());
        } else {
            param.put("dzsphm", "");
        }
        List<BdcDsfSjclDTO> bdcDsfSjclDTOList = new ArrayList<>();
        //beanName = Qxdm：区县代码。
        String beanName = swspConfig.getQxdmBeanNameMap().get(qxdm);


        if (CollectionUtils.isNotEmpty(qlrzjhList)) {
            // 纳税人识别号,取权利人证件号,每个权利人都要查询
            for (String zjh : qlrzjhList) {
                param.put("nsrsbh", zjh);
                LOGGER.info("开始调用南通获取税票exchange接口,接口的beanName：{},接口的参数：{}", beanName, param);
                Object response = exchangeInterfaceFeignService.requestInterface(beanName, param);
                if (response != null) {
                    LOGGER.info("合同编号:{},调取契税完税信息接口成功,返回结果：{}", htbh, response);
                    JSONObject wsxxObject = JSONObject.parseObject(JSONObject.toJSONString(response));
                    if (wsxxObject.get("code") != null && "200".equals(wsxxObject.get("code").toString())) {
                        if (wsxxObject.get("data") != null) {
                            NtSwWsxxDTO ntSwWsxxDTO = JSONObject.parseObject(JSONObject.toJSONString(wsxxObject.get("data")), NtSwWsxxDTO.class);
                            List<QswsfjxxListbean> qswsfjxxListbeanList = ntSwWsxxDTO.getQswsfjxxList();
                            if (CollectionUtils.isNotEmpty(qswsfjxxListbeanList)) {
                                List<String> fjList = new ArrayList<>();
                                for (QswsfjxxListbean qswsfjxxListbean : qswsfjxxListbeanList) {
                                    if (StringUtils.isNotBlank(qswsfjxxListbean.getWjsj()) && StringUtils.isNotBlank(qswsfjxxListbean.getFjid())) {
                                        if (qswsfjxxListbean.getWjsj().startsWith("data:application/pdf;base64,")) {
                                            fjList.add(qswsfjxxListbean.getWjsj());
                                        } else {
                                            fjList.add("data:application/pdf;base64," + qswsfjxxListbean.getWjsj());
                                        }
                                    }
                                }
                                // 不动产第三方收件材料
                                if (CollectionUtils.isNotEmpty(fjList)) {
                                    BdcDsfSjclDTO bdcDsfSjclDTO = new BdcDsfSjclDTO();
                                    // pdf存入的附件文件夹名称
                                    bdcDsfSjclDTO.setClmc(swspConfig.getWjjmc());
                                    bdcDsfSjclDTO.setBase64fjList(fjList);
                                    bdcDsfSjclDTOList.add(bdcDsfSjclDTO);
                                }
                            }
                            // 保存不动产第三方收件材料
                            if (CollectionUtils.isNotEmpty(bdcDsfSjclDTOList)) {
                                bdcSlSjclFeignService.saveDsfSjcl(gzlslid, bdcDsfSjclDTOList);
                            }
                        } else {
                            throw new AppException("获取契税完税信息失败");
                        }
                    } else {
                        LOGGER.error("获取契税完税信息失败");
                        throw new AppException("获取契税完税信息失败");
                    }
                }
            }
        }
        return bdcDsfSjclDTOList;
    }


    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组合流程分开收件材料的
     * @date : 2021/3/26 14:39
     */
    @ResponseBody
    @GetMapping("/zhsjcl")
    public Object queryZhsjcl(String gzlslid, String taskId) {
        if (StringUtils.isAnyBlank(gzlslid)) {
            throw new AppException("查询收件材料信息缺失工作流实例id");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        List<BdcZhSjclDTO> bdcZhSjclDTOList = new ArrayList<>(CollectionUtils.size(bdcXmDOList));
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            List<Map> zdList = bdcZdFeignService.queryBdcZd("djxl");
            bdcXmDOList = bdcXmDOList.stream().filter(bdcXmDO -> StringUtils.isNotBlank(bdcXmDO.getDjxl())).collect(Collectors.toList());
            //根据登记小类去重
            bdcXmDOList = bdcXmDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl))), ArrayList::new));
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                BdcSlSjclQO bdcSlSjclQO = new BdcSlSjclQO();
                bdcSlSjclQO.setGzlslid(gzlslid);
                bdcSlSjclQO.setDjxl(bdcXmDO.getDjxl());
                List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjcl(bdcSlSjclQO);
                if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                    //如果没有查到数据，可能是之前的老数据没有登记小类，直接根据工作流实例查
                    bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
                    if(CollectionUtils.isNotEmpty(bdcSlSjclDOList)){
                        bdcSlSjclDOList = bdcSlSjclDOList.stream().filter(bdcSlSjclDO -> StringUtils.isBlank(bdcSlSjclDO.getDjxl())).collect(Collectors.toList());
                    }
                    if(CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                        BdcZhSjclDTO bdcZhSjclDTO = new BdcZhSjclDTO();
                        bdcZhSjclDTO.setBdcSlSjclDOList(bdcSlSjclDOList);
                        bdcZhSjclDTOList.add(bdcZhSjclDTO);
                    }
                    break;
                }
                if (StringUtils.isNotBlank(taskId)) {
                    TaskData taskData = processTaskClient.getTaskById(taskId);
                    //非受理节点只展示收取部门为登记的数据
                    if (Objects.nonNull(taskData) && !StringUtils.equals(CommonConstantUtils.JD_SL, taskData.getTaskName())) {
                        bdcSlSjclDOList = bdcSlSjclDOList.stream().filter(bdcSlSjclDO -> StringUtils.isNotBlank(bdcSlSjclDO.getSqbm()) && StringUtils.indexOf(bdcSlSjclDO.getSqbm(), CommonConstantUtils.SJCL_SQBM_DJ_MC) > -1).collect(Collectors.toList());
                    }
                }
                String djxlmc = StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDO.getDjxl(), zdList);
                BdcZhSjclDTO bdcZhSjclDTO = new BdcZhSjclDTO();
                bdcZhSjclDTO.setDjxl(bdcXmDO.getDjxl());
                bdcZhSjclDTO.setDjxlmc(djxlmc);
                bdcZhSjclDTO.setBdcSlSjclDOList(bdcSlSjclDOList);
                bdcZhSjclDTO.setQlr(bdcXmDO.getQlr());
                bdcZhSjclDTOList.add(bdcZhSjclDTO);
            }
        }
        return bdcZhSjclDTOList;
    }

    /**
     * @param json 收件材料信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 盐城组合流程收件材料分开保存
     * @date : 2021/3/29 15:35
     */

    @ResponseBody
    @PatchMapping(value = "/zhsjcl")
    public Integer updateZhSjcl(@RequestBody String json) {
        return bdcSlSjclFeignService.updateZhSlSjcl(json);
    }

    /**
     * @param slbh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据受理编号查询收件材料
     * @date : 2021/7/13 19:45
     */
    @ResponseBody
    @GetMapping("/slbh")
    public Object listSjclBySlbh(@NotBlank(message = "查询收件材料受理编号不可为空") String slbh) {
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxBySlbh(slbh);
        String gzlslid ="";
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            //没有登记数据查询受理数据
            BdcSlJbxxDO bdcSlJbxxDO =bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(slbh,"");
            if(bdcSlJbxxDO != null){
                gzlslid =bdcSlJbxxDO.getGzlslid();
            }
        }else{
            gzlslid =bdcXmDTOList.get(0).getGzlslid();
        }
        if(StringUtils.isBlank(gzlslid)){
            return Collections.emptyList();
        }
        return this.listSjclPl(gzlslid, "");
    }

    /**
     * @param sjclJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 复制收件材料
     * @date : 2021/7/12 20:01
     */
    @ResponseBody
    @PostMapping("/copy")
    public void copySjcl(@NotBlank(message = "复制收件材料信息不可为空") @RequestBody String sjclJson) {
        bdcSlSjclFeignService.copySjcl(sjclJson);
    }

    /**
     * @param sjclJson
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 复制收件材料
     * @date : 2021/11/16 20:01
     */
    @ResponseBody
    @PostMapping("/copyzh")
    public void copyzhSjcl(@NotBlank(message = "复制收件材料信息不可为空") @RequestBody String sjclJson) {
        bdcSlSjclFeignService.copyzhSjcl(sjclJson);
    }

    /**
     * @param
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 南通获取抵押合同信息
     * @date : 2021/11/16 20:01
     */
    @ResponseBody
    @GetMapping("/queryDyhtxx")
    public String queryDyhtxx(String htbh,String processInsId) throws IOException {
        if (StringUtils.isBlank(htbh) || StringUtils.isBlank(processInsId)) {
            throw new AppException("合同编号为空或工作流实例为空！");
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("htbh", htbh);
        LOGGER.info("开始调用南通获取抵押合同信息exchange接口,接口的beanName：getDyaHtxx,接口的参数：{}", paramMap);
        Object response = exchangeInterfaceFeignService.requestInterface("getDyaHtxx", paramMap);
        LOGGER.info("结束调用南通获取抵押合同信息exchange接口,接口的beanName：getDyaHtxx,接口的参数：{},接口返回值：{}", paramMap,response);
        if (Objects.nonNull(response)) {
            LOGGER.info("合同编号:{},调用获取抵押合同信息接口成功,返回结果：{}", htbh, response);
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(response));
            if("0".equals(jsonObject.get("result")) && jsonObject.get("data") != null){
                HashMap<String,String> data = JSONObject.parseObject(jsonObject.get("data").toString(), HashMap.class);
                String content =  data.get("content");
                if(StringUtils.isNotBlank(content)){
                    return bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_PDF + content, processInsId, "抵押合同信息", "抵押合同信息", ".pdf");
                }
            }else {
                throw new AppException("调用获取抵押合同信息接口失败！" +jsonObject.get("message").toString());
            }
        }else{
            throw new AppException("调用获取抵押合同信息接口失败！");
        }
        return "";
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 上传base64文件到文件中心
     */
    @ResponseBody
    @PostMapping("/upload/base64")
    public void uploadBase64File(@RequestBody BdcPdfDTO bdcPdfDTO) throws IOException{
        bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
    }

    /**
     * 推送附件信息进行云签
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @return 云签结果
     */
    @ResponseBody
    @PostMapping("/tsyq")
    public Object tsyq(@RequestParam(value="gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID");
        }
        return this.bdcSlSjclFeignService.tsYqDzclxx(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例id
     * @return object
     * @throws IOException
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 从第三方获取获取委托信息文件保存到附件材料
     * @date : 2022/11/25
     */
    @ResponseBody
    @PostMapping("/fjxz")
    public Boolean downloadFile(@RequestParam("gzlslid") String gzlslid) throws IOException {
        return bdcSlSjclFeignService.downloadWtcl(gzlslid);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 多测合一附件获取
     * @date : 2022/12/19 10:54
     */
    @ResponseBody
    @GetMapping("/dchy")
    public void getDchy(SlymDchyDTO slymDchyDTO) {
        bdcSlSjclFeignService.downDchyFj(slymDchyDTO);
    }


}
