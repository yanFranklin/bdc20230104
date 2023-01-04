package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXtMryjFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcDjxlPzRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.register.ui.config.ShxxZhlcPrintDTO;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/14
 * @description 审核信息页面服务
 */
@RestController
@RequestMapping("/rest/v1.0/shxx")
public class BdcShxxController extends BaseController {
    @Autowired
    BdcXtMryjFeignService bdcXtMryjFeignService;
    @Autowired
    BdcShxxFeignService bdcShxxFeignService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    BdcXtMryjFeignService bdcXtMryjRestService;
    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    BdcDjxlPzRestService bdcDjxlPzRestService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    ShxxZhlcPrintDTO shxxZhlcPrintDTO;

    @Value("${shxx.print.zhlcsplit:false}")
    private Boolean shxxSplit;

    @Value("#{'${shxx.print.zhajlcdyid:}'.split(',')}")
    private List<String> zhajlcList;


    /**
     * @param gzlslid       工作流实例ID
     * @param currentJdmc   当前节点名称
     * @param taskId        大云任务ID，对应登记的shxxid
     * @param onlyCurrentJd 是否只返回当前一个节点的信息（true 则只返回当前一个节点的信息，其他签名节点不再获取）
     * @return List<BdcShxxVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @GetMapping(value = "/list")
    public Object listShxx(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "taskId") String taskId
            , @RequestParam(name = "currentJdmc") String currentJdmc, @RequestParam(name = "onlyCurrentJd") Boolean onlyCurrentJd) {
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        bdcShxxQO.setJdmc(currentJdmc);
        bdcShxxQO.setGzlslid(gzlslid);
        bdcShxxQO.setShxxid(taskId);
        bdcShxxQO.setOnlyCurrentJd(onlyCurrentJd);
        bdcShxxQO.setSignImageUrl(signImageUrl);
        // 存在内外网情况的话，配置了外网地址就用外网地址
        if(StringUtils.isNotBlank(signImageWwUrl)){
            bdcShxxQO.setSignImageUrl(signImageWwUrl);
        }
        return bdcShxxFeignService.queryShJdxx(bdcShxxQO);
    }

    /**
     * @param taskId 任务Id
     * @return BdcShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    @GetMapping(value = "/mryj/{taskId}")
    public BdcShxxVO queryMryj(@PathVariable(name = "taskId") String taskId) {
        return bdcShxxFeignService.queryMryj(taskId);
    }

    /**
     * 判断当前流程是否已经办结
     *
     * @param gzlslid gzlslid
     * @return java.lang.Boolean 是否办结
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    @GetMapping(value = "/process/{gzlslid}")
    public Boolean isEnded(@PathVariable(name = "gzlslid") String gzlslid) {
        ProcessInstanceData processInstance = processInstanceClient.getProcessInstance(gzlslid);
        if (processInstance == null) {
            throw new AppException("无法查询到流程信息");
        }
        return processInstance.isEnded();
    }

    /**
     * @param bdcShxxDO
     * @return signid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取sign id
     */
    @PutMapping(value = "/sign")
    public BdcShxxVO sign(@RequestBody BdcShxxDO bdcShxxDO) {
        BdcShxxVO bdcShxxVO = bdcShxxFeignService.getShxxSign(bdcShxxDO);
        bdcShxxVO.setQmtpdz(signImageUrl + bdcShxxVO.getQmid());
        // 存在内外网情况的话，配置了外网地址就用外网地址
        if(StringUtils.isNotBlank(signImageWwUrl)){
            bdcShxxVO.setQmtpdz(signImageWwUrl + bdcShxxVO.getQmid());
        }
        return bdcShxxVO;
    }

    /**
     * @param shxxid 审核信息ID
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 删除签名信息
     */
    @DeleteMapping(value = "/sign/{shxxid}")
    public int deleteSign(@PathVariable(name = "shxxid") String shxxid) {
        if (StringUtils.isBlank(shxxid)) {
            throw new MissingArgumentException("审核信息ID缺失!");
        }
        return bdcShxxFeignService.deleteShxxSign(shxxid);
    }

    /**
     * @param taskId
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取用户的可选意见
     */
    @GetMapping(value = "/kxyj")
    public List<BdcXtMryjDO> queryKxyj(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isEmpty(taskId)) {
            throw new MissingArgumentException("taskId");
        }
        TaskData taskData = workFlowUtils.getTaskById(taskId);
        String jdmc = taskData.getTaskName();
        String gzldyKey = taskData.getProcessKey();
        return bdcXtMryjFeignService.listBdcXtKxyj(null, gzldyKey, jdmc);
    }

    /**
     * @param paramList
     * @return int
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键更新多条审核信息数据
     */
    @PatchMapping(value = "/shxxList")
    public int updateShxx(@RequestBody List<BdcShxxDO> paramList) {
        if (CollectionUtils.isNotEmpty(paramList)) {
            return bdcShxxFeignService.updateShxxList(paramList);
        }
        return -1;
    }

    @GetMapping(value = "dylx/{gzlslid}")
    public Map<String, List<String>> getShxxDylx(@PathVariable(value = "gzlslid") String gzlslid) {
        Map<String, List<String>> dylx = new HashMap();
        if (StringUtils.isBlank(gzlslid)) {
            return dylx;
        }
        return bdcShxxFeignService.getShxxDylx(gzlslid);
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取项目类型
     * @date : 2020/6/29 10:15
     */
    @GetMapping("/lclx")
    public int queryLclx(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        return bdcXmFeignService.makeSureBdcXmLx(gzlslid);
    }


    @GetMapping("/shxxBtn")
    public Map queryShxxBtn(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        Map resultMap = new HashMap(2);
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        resultMap.put("lclx", xmlx);
        resultMap.put("zhajlc", false);
        if (shxxSplit && Objects.nonNull(shxxZhlcPrintDTO)) {
            //根据流程实例id查询定义id
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                //登记小类去重
                bdcXmDTOList = bdcXmDTOList.stream().filter(bdcXmDTO -> StringUtils.isNotBlank(bdcXmDTO.getDjxl())).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcXmDTO::getDjxl))), ArrayList::new));
                Map<String, String> shxxBtnMap = new HashMap<>(2);
                boolean zhajlc = false;
                for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                    Map btnMap = shxxZhlcPrintDTO.getZhlcbtn().get(bdcXmDTO.getGzldyid());
                    if (MapUtils.isNotEmpty(btnMap)) {
                        Map<String, String> slymBtnMap = MapUtils.getMap(btnMap, bdcXmDTO.getDjxl(), null);
                        shxxBtnMap.put(bdcXmDTO.getDjxl(), MapUtils.getString(slymBtnMap, "spb", ""));
                    }
                    if (CollectionUtils.isNotEmpty(zhajlcList) && zhajlcList.contains(bdcXmDTO.getGzldyid())) {
                        zhajlc = true;
                    }
                }
                resultMap.put("zhajlc", zhajlc);
                resultMap.put("shxxBtn", shxxBtnMap);
            }
        }
        resultMap.put("shxxSplit", shxxSplit);
        return resultMap;
    }


    /**
     * 保存spb的pdf到文件中心
     *
     * @param gzlslid
     * @param pdfUrl
     */
    @GetMapping(value = "/savePdfToWjzx")
    public String savePdfToWjzx(@RequestParam(name = "gzlslid") String gzlslid,
                                @RequestParam(name = "pdfUrl") String pdfUrl,
                                @RequestParam(name = "dyName") String dyName) {
        // 目录结构
        /* 审核信息
              个人审批表
                    pdf1
              单位审批表
                    pdf2
        */
        // 根目录 审核信息 文件夹
        StorageDto storageDtoGml = null;
        // 文件名
        String fileName = dyName + ".pdf";
        // 文件夹名
        String folderName = "审核信息";
        // 避免附件重复，上传前先判断，如果已存在，则删除已有的信息
        // 审核信息文件夹
        List<StorageDto> storageDtoList = storageClient.listStoragesByName("clientId", gzlslid, null, folderName, null, 0);

        if(CollectionUtils.isNotEmpty(storageDtoList)) {
            LOGGER.info("查询审核信息附件目录长度:{}",storageDtoList.size());
            List<String> listId = new ArrayList();
            // 审核信息文件夹实体
            StorageDto storageDto = storageDtoList.get(0);
            if(null != storageDto){
                // 查询审核 信息下的所有文件夹
                List<StorageDto> listEjWjj = storageClient.listAllSubsetStorages(storageDto.getId(),dyName,null,0,null,null);
                // 由于大云的删除接口目前没有递归物理删除，需要自行处理
                if(CollectionUtils.isNotEmpty(listEjWjj)){
                    StorageDto storageDtoWjj = listEjWjj.get(0);
                    // 查询 二级目录下的所有pdf文件
                    List<StorageDto> listPdfFile = storageClient.listAllSubsetStorages(storageDtoWjj.getId(),null,null,null,null,null);
                    if(CollectionUtils.isNotEmpty(listPdfFile)){
                        for (StorageDto storageDtoFile : listPdfFile) {
                            if(null != storageDtoWjj){
                                listId.add(storageDtoFile.getId());
                            }
                        }
                    }
                    listId.add(storageDtoWjj.getId());
                }
            }
            storageDtoGml = storageDto;

            if (CollectionUtils.isNotEmpty(listId)) {
                boolean result = storageClient.deleteStorages(listId);
                LOGGER.info("审核信息目录文件删除结果：{}", result);
            }

        }else{
            // 创建根目录
            storageDtoGml = storageClient.createRootFolder("clientId", gzlslid, folderName, null);
        }

        if(storageDtoGml != null){
            // 新建文件夹 (二级目录)
            StorageDto storageDto = storageClient.createFolder("clientId", "", storageDtoGml.getId(), dyName,"");

            if (storageDto != null) {
                //上传文件
                byte[] fileByte = Base64Utils.getFile(pdfUrl);

                MultipartDto multipartDto = new MultipartDto();
                multipartDto.setNodeId(storageDto.getId());
                multipartDto.setClientId(storageDto.getClientId());
                if (fileByte != null) {
                    multipartDto.setData(fileByte);
                    multipartDto.setOwner(userManagerUtils.getCurrentUserName());
                    multipartDto.setContentType("application/pdf");
                    multipartDto.setSize(fileByte.length);
                    multipartDto.setOriginalFilename(fileName);
                    multipartDto.setName(storageDto.getName());
                }

                //判断受理库收件材料
                List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, folderName);
                if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                    List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                        bdcSlSjclDO.setWjzxid(storageDto.getId());
                        bdcSlSjclDO.setDjxl(bdcXmDTOList.get(0).getDjxl());
                        bdcSlSjclDO.setClmc(folderName);
                        bdcSlSjclDO.setGzlslid(gzlslid);
                        bdcSlSjclDO.setFs(1);
                        bdcSlSjclDO.setYs(1);
                        bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
                        bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
                    }
                }
                storageDto = storageClient.multipartUpload(multipartDto);
                if (null != storageDto) {
                    LOGGER.info("审核信息pdf上传返回信息：{}", JSONObject.toJSONString(storageDto));
                    return storageDto.getDownUrl();
                }
            }
        }

        return "";
    }

    /**
     * 预览spb的pdf
     * @param gzlslid
     * @param dyName
     */
    @GetMapping(value = "/ylPdf")
    public String ylPdf(@RequestParam(name = "gzlslid") String gzlslid,@RequestParam(name = "dyName") String dyName) {

        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("查询审核信息pdf缺失工作实例id");
        }
        if(StringUtils.isBlank(dyName)){
            throw new AppException("查询审核信息pdf缺失审批表名称");
        }
        List<StorageDto> storageDtoList = storageClient.listStoragesByName("clientId", gzlslid, null, dyName, null, 0);

        if(CollectionUtils.isNotEmpty(storageDtoList)) {
            String id = storageDtoList.get(0).getId();
            List<StorageDto> listFile = storageClient.listAllSubsetStorages(id, StringUtils.EMPTY, 1, null, 0,null);
            if(CollectionUtils.isNotEmpty(listFile)){
                return listFile.get(0).getDownUrl();
            }
        }

        return "";
    }
}
