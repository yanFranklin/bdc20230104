package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.common.ProcessOptResultDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.AutoForwardTaskDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcCfhzPdfRequestDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXzxxQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDysdQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcJjdFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.etl.BengbuFcjyDataFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.config.QxdmXzdmConfig;
import cn.gtmap.realestate.register.core.service.BdcdySdService;
import cn.gtmap.realestate.register.service.BdcLcczxgService;
import cn.gtmap.realestate.register.service.BdcPrintService;
import cn.gtmap.realestate.register.service.BdcQlxxService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>"
 * @version 1.0, 2020/11/25
 * @description 流程操作相关服务实现类
 */
@Service
public class BdcLcczxgServiceImpl implements BdcLcczxgService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcLcczxgServiceImpl.class);

    public static final String CLWJJMC = "法院文书";
    public static final String CFHZPDFDYLX = "cfhzPdf";
    public static final String CFHZPDFNAME = "查封回执";

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BengbuFcjyDataFeignService bengbuFcjyDataFeignService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    StorageClientMatcher storageClient;
    @Autowired
    BdcPrintService bdcPrintService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    /**
     * PDF打印服务
     */
    @Autowired
    PdfController pdfController;

    @Autowired
    BdcSlXzxxFeignService bdcSlXzxxFeignService;

    @Autowired
    TaskHandleClientMatcher taskHandleClient;

    @Autowired
    BdcSdFeignService bdcSdFeignService;

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    @Autowired
    EntityMapper entityMapper;


    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;

    @Autowired
    ProcessDefinitionClient processDefinitionClient;

    @Autowired
    ProcessInsCustomExtendClient processInsCustomExtendClient;

    @Autowired
    BdcBhFeignService bdcBhFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlFeignService bdcSlFeignService;
    @Autowired
    TaskUtils taskUtils;
    @Autowired
    BdcJjdFeignService bdcJjdFeignService;
    @Autowired
    BdcSlxxHxFeignService bdcSlxxHxFeignService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    BdcdySdService bdcdySdService;

    @Autowired
    BdcDbxxFeignService bdcDbxxFeignService;

    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    BdcQlxxService bdcQlxxService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    QxdmXzdmConfig qxdmXzdmConfig;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;

    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;

    @Autowired
    private ProcessTaskClient processTaskClient;


    /**
     * 打印文件路径
     */
    @Value("${print.path:}")
    private String printPath;

    /**
     * 档案交接工作流定义id
     */
    @Value("${dajj.gzldyid:}")
    private String dajjGzldyid;

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 是否根据印制号创建档案交接的流程定义id；
     * @date : 2022/1/18 9:12
     */
    @Value("#{'${dajj.sfcjbyyzh.gzldyid:}'.split(',')}")
    private List<String> sfcjdajjByYzhDyidList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  自动转发节点名称和受理人key对照
     */
    @Value("#{${zdzf.jdmcslr:{'复审': 'FSR','缮证':'DBR'}}}")
    private Map<String, String> zdzfJdmcAndSlr;

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 流程撤销删除要撤案交易信息
     */
    @Override
    public void zlfca(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new AppException("流程撤销删除要撤案交易信息,缺失工作流实例id");
        }
        LOGGER.info("开始调用增量房交易信息撤案接口，processInsId:{}", processInsId);

        List<BdcXmDTO> list = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(list)) {
            for (BdcXmDTO bdcXmDTO : list) {
                String bdcdyh = bdcXmDTO.getBdcdyh();
                LOGGER.info("调用增量房交易信息撤案接口bdcdyh:{}", bdcdyh);
                FcjyBaxxDTO dto = bengbuFcjyDataFeignService.zlfca(bdcdyh);
                LOGGER.info("调用增量房交易信息撤案接口返回值:{}", dto == null ? "" : JSONObject.toJSONString(dto));
            }
        }
    }


    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查封流程登簿办结后的需要发送回执单给法院
     */
    @Override
    public void cfhzPdf(String processInsId) {
        try {
            if (StringUtils.isBlank(processInsId)) {
                throw new AppException("查封流程登簿办结后的需要发送回执单给法院,缺失工作流实例id");
            }
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            String spxtywh = "";
            List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(list)) {
                spxtywh = list.get(0).getSpxtywh();
            } else {
                throw new AppException("查封流程登簿办结后的需要发送回执单给法院,缺失项目信息");
            }

            // 作为参数传入打印xml中
            String clmc = "";
            List<StorageDto> listFjcl = storageClient.listStoragesByName("clientId", processInsId, null, CLWJJMC, 1, 0);
            LOGGER.info("查询法院文书附件材料：{}", listFjcl);
            if (CollectionUtils.isNotEmpty(listFjcl)) {
                for (StorageDto storageDto : listFjcl) {
                    List<StorageDto> listFile = storageClient.listAllSubsetStorages(storageDto.getId(), StringUtils.EMPTY, 1, null, 0, null);
                    if (CollectionUtils.isNotEmpty(listFile)) {
                        for (StorageDto storage : listFile) {
                            clmc += storage.getName() + ",";
                        }
                    }
                }
                if (clmc.length() > 0) {
                    String realClmc = clmc.substring(0, clmc.length() - 1);
                    createPdfFile(processInsId, realClmc, spxtywh);
                }
            } else {
                throw new AppException("通过" + CLWJJMC + "未查询到附件材料,gzlslid:" + processInsId);
            }
        } catch (Exception e) {
            LOGGER.error("回传查封回执pdf异常", e);
        }

    }

    /**
     * 生成pdf文件
     *
     * @param processInsId
     * @param clmc
     */
    public void createPdfFile(String processInsId, String clmc, String spxtywh) throws IOException {

        Map<String, List<Map>> paramMap = new HashMap(1);
        Map<String, Object> map = new HashMap();
        List<Map> maps = new ArrayList();

        map.put("gzlslid", processInsId);
        map.put("clmc", clmc);
        maps.add(map);
        paramMap.put(CFHZPDFDYLX, maps);
        LOGGER.info("打印类型：{}。工作流示例ID：{}的打印参数为：{}：", CFHZPDFDYLX, processInsId, maps.toString());
        String xml = bdcPrintService.print(paramMap);
        LOGGER.info("打印类型xml：{}", xml);

        // 生成PDF文件
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + "cfhzPdf.docx");
        pdfExportDTO.setFileName(CFHZPDFNAME);
        pdfExportDTO.setXmlData(xml);
        LOGGER.info("===>生成pdf参数:{}", JSONObject.toJSONString(pdfExportDTO));
        String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
        LOGGER.info("===>生成法院查封回执单pdf文件路径：{}", pdfFilePath);


        File pdfFile = null;
        // 转换Base64数据
        try {
            pdfFile = new File(pdfFilePath);
            if (!pdfFile.exists()) {
                return;
            }
            String base64 = Base64Utils.getPDFBinary(pdfFile);
            String url = savePdfToWjzx(processInsId, pdfFile);
            BdcCfhzPdfRequestDTO bdcCfhzPdfRequestDTO = new BdcCfhzPdfRequestDTO();
            bdcCfhzPdfRequestDTO.setWjlj(url);
            bdcCfhzPdfRequestDTO.setWjlx("pdf");
            bdcCfhzPdfRequestDTO.setWjmc(CFHZPDFNAME);
            bdcCfhzPdfRequestDTO.setWsnr(base64);
            bdcCfhzPdfRequestDTO.setWwslbh(spxtywh);

            Map pdfParamMap = new HashMap();
            pdfParamMap.put("head", "");
            pdfParamMap.put("data", bdcCfhzPdfRequestDTO);
            LOGGER.info("调用回传查封回执pdf接口入参：{}", JSONObject.toJSONString(bdcCfhzPdfRequestDTO));
            Object obj = exchangeInterfaceFeignService.requestInterface("accept_receipt_info", pdfParamMap);
            if (obj != null) {
                LOGGER.info("调用回传查封回执pdf接口返回值：{}", JSONObject.toJSONString(obj));
                Map responesMap = MapUtils.getMap((Map) obj, "head");
                if (responesMap.containsKey("returncode") && null != responesMap.get("returncode")
                        && StringUtils.equals("0000", responesMap.get("returncode").toString())) {
                    LOGGER.info("回传查封pdf成功");
                } else {
                    throw new AppException("回传查封pdf失败，" + JSONObject.toJSONString(responesMap));
                }
            }
        } finally {
            if (null != pdfFile) {
                pdfFile.delete();
            }
        }
    }

    /**
     * 保存pdf到文件中心
     *
     * @param gzlslid
     * @param pdfFile
     */
    public String savePdfToWjzx(String gzlslid, File pdfFile) throws IOException {
        String pdfBase64Str = "data:application/pdf;base64," + Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(pdfFile));
        String id = bdcUploadFileUtils.uploadBase64File(pdfBase64Str, gzlslid, CFHZPDFNAME, CFHZPDFNAME, ".pdf");
        StorageDto storageDto = storageClient.findById(id);
        if (Objects.nonNull(storageDto)) {
            return storageDto.getDownUrl();
        }
        return "";
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 修正流程 没有办结之前 挂起被修的流程并锁定bdcdyh和证书
     */
    @Override
    public void xzlcGqAndSd(String processInsId, String currentUserName) {
        if (StringUtils.isBlank(processInsId)) {
            LOGGER.info("缺失processInsId,挂起锁定操作已终止！");
            return;
        }
        BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
        bdcSlXzxxQO.setGzlslid(processInsId);
        BdcSlXzxxDO bdcSlXzxxDO = bdcSlXzxxFeignService.queryBdcSlXzxx(bdcSlXzxxQO);
        if (bdcSlXzxxDO != null && StringUtils.isNotBlank(bdcSlXzxxDO.getYxmid())) {
            String xmid = bdcSlXzxxDO.getYxmid();
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(listXm)) {
                BdcXmDO bdcXmDO = listXm.get(0);
                String gzlslid = bdcXmDO.getGzlslid();
                Integer ajzt = bdcXmDO.getAjzt();
                // 在办的需要挂起
                if (ajzt.equals(CommonConstantUtils.AJZT_ZB_DM)) {
                    List<String> processInstanceIds = new ArrayList<>();
                    processInstanceIds.add(gzlslid);
                    List<ProcessOptResultDto> list = taskHandleClient.batchTaskHang(processInstanceIds, "正在办理修正流程");
                    if (CollectionUtils.isNotEmpty(list)) {
                        LOGGER.info("挂起成功：{}", gzlslid);
                    }
                }
                // 已办的要锁定bdcdyh和证书
                dysd(bdcXmDO, currentUserName);
                zssd(bdcXmDO, currentUserName);
            } else {
                LOGGER.info("未找到被修正的流程:{},挂起锁定操作已终止！", xmid);
            }
        } else {
            LOGGER.info("未找到被修正的流程,挂起锁定操作已终止！");
        }
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 修正流程 流程办结后 需要解挂和解锁被修正的流程
     */
    @Override
    public void xzlcJgAndJs(String processInsId, String currentUserName) {
        if (StringUtils.isBlank(processInsId)) {
            LOGGER.info("缺失processInsId,挂起锁定操作已终止！");
            return;
        }
        BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
        bdcSlXzxxQO.setGzlslid(processInsId);
        BdcSlXzxxDO bdcSlXzxxDO = bdcSlXzxxFeignService.queryBdcSlXzxx(bdcSlXzxxQO);
        if (bdcSlXzxxDO != null && StringUtils.isNotBlank(bdcSlXzxxDO.getYxmid())) {
            String xmid = bdcSlXzxxDO.getYxmid();
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(listXm)) {
                BdcXmDO bdcXmDO = listXm.get(0);
                String gzlslid = bdcXmDO.getGzlslid();
                Integer ajzt = bdcXmDO.getAjzt();
                // 在办的需要激活
                if (ajzt.equals(CommonConstantUtils.AJZT_ZB_DM)) {
                    List<String> processInstanceIds = new ArrayList<>();
                    processInstanceIds.add(gzlslid);
                    List<ProcessOptResultDto> list = taskHandleClient.batchTaskActivation(processInstanceIds, "修正流程已办结，激活被挂起流程");
                    if (CollectionUtils.isNotEmpty(list)) {
                        LOGGER.info("解挂成功：{}", gzlslid);
                    }
                }
                // 解锁不动产单元号和证书
                zsJsAndBdcdyhJs(bdcXmDO, currentUserName, "修正流程已办结，解锁不动产单元号", "修正流程已办结，解锁证书");
                //更新权籍锁定状态
                List<String> bdcdyhList = listXm.stream().map(BdcXmDO::getBdcdyh).collect(Collectors.toList());
                bdcDbxxFeignService.synQjBdcdyztSd(bdcdyhList, CommonConstantUtils.SDZT_JS);
            } else {
                LOGGER.info("未找到被修正的流程:{},挂起锁定操作已终止！", xmid);
            }
        } else {
            LOGGER.info("未找到被修正的流程,挂起锁定操作已终止！");
            return;
        }
    }

    /**
     * @param gzlslid 当前流程的工作流实例id而非创建档案交接的实列id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 创建档案交接流程
     * @date : 2021/6/9 9:30
     */
    @Override
    public void cjDajjLc(String gzlslid, String currentUserName) throws Exception {
        if (StringUtils.isNoneBlank(gzlslid, dajjGzldyid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            //登记库数据为空的话，查受理库信息
            String slrid = "";
            //当前流程定义id
            String gzldyid = "";
            String jbxxid = "";
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                // 获取受理人id
                if (Objects.nonNull(bdcSlJbxxDO)) {
                    gzldyid = bdcSlJbxxDO.getGzldyid();
                    jbxxid = bdcSlJbxxDO.getJbxxid();
                } else {
                    LOGGER.error("当前流程实例id{}未获取到项目信息和受理基本信息", gzlslid);
                }
            } else {
                gzldyid = bdcXmDOList.get(0).getGzldyid();
            }
            if (Objects.nonNull(bdcSlJbxxDO)) {
                slrid = bdcSlJbxxDO.getSlrid();
            }
            if (StringUtils.isNotBlank(slrid)) {
                boolean cjlc = true;
                if (CollectionUtils.isNotEmpty(sfcjdajjByYzhDyidList) && sfcjdajjByYzhDyidList.contains(gzldyid)) {
                    //查询当前流程是否有印制号，如果为空则不创建
                    List<BdcZsDO> bdcZsDOList = new ArrayList<>(1);
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        //1.先判断是否注销类流程，注销流程查询上一手证书的信息
                        BdcZsQO bdcZsQO = new BdcZsQO();
                        bdcZsQO.setGzlslid(gzlslid);
                        if (isZxlc(bdcXmDOList)) {
                            bdcZsDOList = bdcZsFeignService.queryYxmZs(bdcZsQO);
                        } else {
                            bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
                        }
                    } else {
                        //查受理库信息
                        if (StringUtils.isNotBlank(jbxxid)) {
                            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(jbxxid);
                            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDOList.get(0).getXmid(), "", CommonConstantUtils.SF_F_DM);
                                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                                    String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                                    BdcZsQO bdcZsQO = new BdcZsQO();
                                    bdcZsQO.setXmid(yxmid);
                                    bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
                                }
                            }
                        }
                    }
                    if (CollectionUtils.isEmpty(bdcZsDOList) || StringUtils.isBlank(bdcZsDOList.get(0).getQzysxlh()) || StringUtils.equals("/", bdcZsDOList.get(0).getQzysxlh())) {
                        cjlc = false;
                    }
                }
                if (cjlc) {
                    UserDto userDto = userManagerUtils.getUserByUserid(slrid);
                    TaskData taskData = processInstanceClient.directStartProcessInstance(dajjGzldyid, userDto.getUsername(), "", "", null);
                    if (Objects.nonNull(taskData)) {
                        //流程创建成功
                        LOGGER.info("档案交接流程创建成功，当前流程gzlslid{}，档案交接工作流实例id{}", gzlslid, taskData.getProcessInstanceId());
                        BdcSlCshDTO bdcSlCshDTO = new BdcSlCshDTO();
                        bdcSlCshDTO.setJbxxid(UUIDGenerator.generate16());
                        bdcSlCshDTO.setGzlslid(taskData.getProcessInstanceId());
                        bdcSlCshDTO.setSlsj(taskData.getStartTime());
                        bdcSlCshDTO.setGzldymc(taskData.getProcessDefName());
                        bdcSlCshDTO.setSlbh(bdcBhFeignService.queryBh("slbh", ""));
                        bdcSlCshDTO.setGzldyid(dajjGzldyid);
                        bdcSlCshDTO.setCzrid(slrid);
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            bdcSlCshDTO.setZl(bdcXmDOList.get(0).getZl());
                            bdcSlCshDTO.setQlrmc(bdcXmDOList.get(0).getQlr());
                            bdcSlCshDTO.setQxdm(bdcXmDOList.get(0).getQxdm());
                        } else if (Objects.nonNull(bdcSlJbxxDO)) {
                            bdcSlCshDTO.setQxdm(bdcSlJbxxDO.getQxdm());
                            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                                bdcSlCshDTO.setZl(bdcSlXmDOList.get(0).getZl());
                                List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrFeignService.listBdcSlSqrByXmid(bdcSlXmDOList.get(0).getXmid(), CommonConstantUtils.QLRLB_QLR);
                                if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                                    bdcSlCshDTO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getSqrmc", CommonConstantUtils.ZF_YW_DH));
                                } else {
                                    //如果没有获取到权利人，直接取回写到流程的权利人信息-- 有些流程是不存权利人或者申请人信息
                                    List<Map<String, Object>> list = processInsCustomExtendClient.getProcessInsCustomExtend(bdcSlJbxxDO.getGzlslid());
                                    if (CollectionUtils.isNotEmpty(list)) {
                                        bdcSlCshDTO.setQlrmc(MapUtils.getString(list.get(0), "QLR", StringUtils.EMPTY));
                                    }
                                }
                            }
                        }
                        String jjdid = "";
                        try {
                            bdcSlJbxxFeignService.insertBdcSlJbxx(bdcSlCshDTO);
                            //新增交接单数据
                            BdcJjdQO bdcJjdQO = new BdcJjdQO();
                            bdcJjdQO.setListGzlslid(Lists.newArrayList(gzlslid));
                            bdcJjdQO.setZfrid(slrid);
                            bdcJjdQO.setZfr(userDto.getUsername());
                            bdcJjdQO.setDajjGzlslid(taskData.getProcessInstanceId());
                            BdcJjdDO bdcJjdDO = bdcJjdFeignService.generateAndSaveJjdxx(bdcJjdQO);
                            if (Objects.nonNull(bdcJjdDO) && StringUtils.isNotBlank(bdcJjdDO.getJjdid())) {
                                jjdid = bdcJjdDO.getJjdid();
                            }

                            //回写数据到大云
                            this.hxDajjLcxxToCloud(taskData.getProcessInstanceId(), userDto.getUsername(), bdcXmDOList, bdcSlJbxxDO);

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
                            LOGGER.info("回写平台结束:{} {}", simpleDateFormat.format(new Date()), bdcSlCshDTO.getSlbh());
                        } catch (Exception ex) {
                            //删除受理信息
                            bdcSlFeignService.deleteBdcSlxx(taskData.getProcessInstanceId());
                            //删除交接单数据
                            if (StringUtils.isNotBlank(jjdid)) {
                                bdcJjdFeignService.delJjd(jjdid);
                            }
                            //删除流程
                            taskUtils.deleteTask(taskData.getProcessInstanceId(), taskData.getReason());
                            throw ex;
                        }
                        //自动转发
                        if (CollectionUtils.isNotEmpty(bdcXmDOList) && CheckWwsqOrYcslUtils.checkIsYhxt(bdcXmDOList.get(0).getSply())) {
                            AutoForwardTaskDTO autoForwardTaskDTO = new AutoForwardTaskDTO();
                            LOGGER.info("档案交接流程自动转发,当前流程gzlslid{}，档案交接工作流实例id{}", gzlslid, taskData.getProcessInstanceId());
                            bdcSlFeignService.wwsqAutoTurn(taskData.getProcessInstanceId(), autoForwardTaskDTO);
                        }
                    }
                } else {
                    LOGGER.error("当前流程实例id{}，定义id{}，配置了根据印制号是否生成判断是否创建档案交接业务，未找到印制号，不创建业务", gzlslid, gzldyid);
                    throw new AppException("根据印制号是否生成判断是否创建档案交接业务，未找到印制号，不创建业务");
                }
            } else {
                LOGGER.error("未获取到创建档案交接人员信息，请检查bdc_xm或者bdc_sl_jbxx数据slrid是否有值");
                throw new AppException("未获取到创建档案交接人员信息，请检查bdc_xm或者bdc_sl_jbxx数据slrid是否有值");
            }

        }
    }

    /**
     * 判断流程是否注销流程
     */
    private boolean isZxlc(List<BdcXmDO> bdcXmDOList){
        if (Objects.equals(CommonConstantUtils.DJLX_ZXDJ_DM, bdcXmDOList.get(0).getDjlx())) {
            LOGGER.warn("当前流程-实例id{}是注销流程djlx=400", bdcXmDOList.get(0).getGzldyid());
            return true;
        } else {
            //判断是否生成权利
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDOList.get(0).getXmid());
            if (Objects.isNull(bdcQl)) {
                LOGGER.warn("当前流程-实例id{}未生成权利，判断为注销流程", bdcXmDOList.get(0).getGzldyid());
                return true;
            }
        }
        return false;
    }

    /**
     * 回写档案交接流程信息至大云
     * @param processInstanceId  档案交接流程的工作流实例ID
     * @param userName     当前处理人
     * @param bdcXmDOList  原流程的项目信息
     * @param bdcSlJbxxDO  原流程的基本信息
     * @throws Exception
     */
    private void hxDajjLcxxToCloud(String processInstanceId, String userName, List<BdcXmDO> bdcXmDOList, BdcSlJbxxDO bdcSlJbxxDO) throws Exception {
        //回写数据到大云
        Map<String, Object> zdyxxMap = new HashMap<>(10);

        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            zdyxxMap.put("YSLBH", bdcXmDOList.get(0).getSlbh());
            zdyxxMap.put("YLCMC", bdcXmDOList.get(0).getGzldymc());
            zdyxxMap.put("QXDM", bdcXmDOList.get(0).getQxdm());
            zdyxxMap.put("YWR", bdcXmDOList.get(0).getYwr());
            zdyxxMap.put("ZSYS", this.getZsys(bdcXmDOList.get(0).getAjzt(), bdcXmDOList, null));
        } else if (Objects.nonNull(bdcSlJbxxDO)) {
            zdyxxMap.put("YSLBH", bdcSlJbxxDO.getSlbh());
            zdyxxMap.put("QXDM", bdcSlJbxxDO.getQxdm());
            //根据流程定义id查流程名称
            ProcessDefData processDefData = processDefinitionClient.getProcessDefByProcessDefKey(bdcSlJbxxDO.getGzldyid());
            if (Objects.nonNull(processDefData)) {
                zdyxxMap.put("YLCMC", processDefData.getName());
            }

            // 获取义务人信息
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
            String ywrmc = "";
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrFeignService.listBdcSlSqrByXmid(bdcSlXmDOList.get(0).getXmid(), CommonConstantUtils.QLRLB_YWR);
                if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                    ywrmc = StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getSqrmc", CommonConstantUtils.ZF_YW_DH);
                } else {
                    //如果没有获取到权利人，直接取回写到流程的权利人信息-- 有些流程是不存权利人或者申请人信息
                    List<Map<String, Object>> list = processInsCustomExtendClient.getProcessInsCustomExtend(bdcSlJbxxDO.getGzlslid());
                    if (CollectionUtils.isNotEmpty(list)) {
                        ywrmc = MapUtils.getString(list.get(0), "YWR", StringUtils.EMPTY);
                    }
                }
                // 添加证书样式字段
                zdyxxMap.put("ZSYS", this.getZsys(bdcSlJbxxDO.getYwslzt(), null, bdcSlXmDOList));
            }
            zdyxxMap.put("YWR", ywrmc);
        }
        zdyxxMap.put("DAJJSJ", new Date());
        zdyxxMap.put("JJR", userName);
        LOGGER.info("保存或更新受理信息和自定义信息至大云平台processInstanceId: {} zdyxxMap:{}", processInstanceId, zdyxxMap);
        bdcSlxxHxFeignService.hxBdcSlxxWithZdyxx(processInstanceId, zdyxxMap);
    }

    /**
     * 判断是电子证还是纸质证
     * <p>
     *     1、有印制号为纸质证
     *     2、流程办结、没有印制号则为电子证
     *     3、流程没有办结、没有印制号则为纸质证
     * </p>
     * @return 电子证、纸质证
     */
    private String getZsys(Integer ajzt, List<BdcXmDO> bdcXmDOList, List<BdcSlXmDO> bdcSlXmDOList) {
        String fbxmid = null;
        List<BdcZsDO> bdcZsDOList = new ArrayList<>(1);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            String xmid = bdcXmDOList.get(0).getXmid();
            // 1.先判断是否注销类流程，注销流程查询上一手项目附表
            if (isZxlc(bdcXmDOList)) {
                // 查上一手项目附表
                BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                bdcXmLsgxQO.setXmid(xmid);
                bdcXmLsgxQO.setWlxm(0);
                List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                    fbxmid = bdcXmLsgxDOList.get(0).getYxmid();
                }
            } else {
                // 查询当前项目附表
                fbxmid = xmid;
            }
        }
        Integer zsxt = null;
        if (StringUtil.isNotBlank(fbxmid)) {
            Example example = new Example(BdcXmFbDO.class);
            example.createCriteria().andEqualTo("xmid", fbxmid);
            List<BdcXmFbDO> xmFbDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(xmFbDOList)) {
                zsxt = xmFbDOList.get(0).getZsxt();
            }
        }

        // 只受理情况
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDOList.get(0).getXmid(), "", CommonConstantUtils.SF_F_DM);
            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                BdcZsQO bdcZsQO = new BdcZsQO();
                bdcZsQO.setXmid(yxmid);
                bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
            }
        }

        LOGGER.info("档案交接流程回写证书样式，当前情况：证书形态：{} 证书信息为：{} , 案件状态为：{}", zsxt, JSON.toJSONString(bdcZsDOList), ajzt);
        if (null != zsxt && zsxt == 2) {
            return Constants.ZSYS_ZZZ;
        }
        if (null != zsxt && zsxt == 1) {
            return Constants.ZSYS_DZZ;
        }
        // 存在证书印制号则为纸质证
        if (CollectionUtils.isNotEmpty(bdcZsDOList) && StringUtils.isNotBlank(bdcZsDOList.get(0).getQzysxlh())
                && !StringUtils.equals("/", bdcZsDOList.get(0).getQzysxlh())) {
           return Constants.ZSYS_ZZZ;
        }

        if (CommonConstantUtils.AJZT_YB_DM.equals(ajzt)) {
            return Constants.ZSYS_DZZ;
        } else {
            return Constants.ZSYS_ZZZ;
        }
    }

    /**
     * @param gzlslid
     * @param currentUserName
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 转发档案交接
     * @date : 2021/6/15 10:00
     */
    @Override
    public void zfDajj(String taskid, String gzlslid, String currentUserName) {
        //改变档案交接状态，接收人，接收时间，接收人部门
        BdcJjdDO bdcJjdDO = queryBdcJjd(gzlslid);
        if (Objects.nonNull(bdcJjdDO)) {
            //1.已转发
            bdcJjdDO.setJjdzt(5);
            UserDto userDto = userManagerUtils.getUserByName(currentUserName);
            if (Objects.nonNull(userDto)) {
                bdcJjdDO.setZfks(CollectionUtils.isNotEmpty(userDto.getOrgRecordList()) ? userDto.getOrgRecordList().get(0).getName() : "");
                bdcJjdDO.setZfr(userDto.getAlias());
                bdcJjdDO.setZfrid(userDto.getId());
                bdcJjdDO.setZfsj(new Date());
            }
            //获取转发人的信息
            String nextUserName = redisUtils.getStringValue("REDIS_ZFYH_" + taskid);
            if (StringUtils.isNotBlank(nextUserName)) {
                UserDto nextUser = userManagerUtils.getUserByName(nextUserName);
                if (Objects.nonNull(nextUser)) {
                    bdcJjdDO.setJsr(nextUser.getAlias());
                    bdcJjdDO.setJsrid(nextUser.getId());
                    bdcJjdDO.setJsks(CollectionUtils.isNotEmpty(nextUser.getOrgRecordList()) ? nextUser.getOrgRecordList().get(0).getName() : "");
                    bdcJjdDO.setJssj(new Date());
                }
            }
            bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcJjdDO), BdcJjdDO.class.getName());
            try {
                // 回写大云交接人、交接时间
                Map zdyxx = new HashMap();
                zdyxx.put("JJR", userDto.getAlias());
                zdyxx.put("DAJJSJ", new Date());
                bdcSlxxHxFeignService.hxBdcSlxxWithZdyxx(gzlslid, zdyxx);
            } catch (Exception e) {
                LOGGER.error("回写大云交接人、交接时间失败。" + e.getMessage(), e);
            }
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param opinion 退回意见
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 退回档案交接
     * @date : 2021/6/15 10:00
     */
    @Override
    public void thDajj(String gzlslid, String opinion) {
        //清空接收人，改变交接状态
        BdcJjdDO bdcJjdDO = queryBdcJjd(gzlslid);
        if (Objects.nonNull(bdcJjdDO)) {
            //7 交接失败 退回
            bdcJjdDO.setJjdzt(7);
            bdcJjdDO.setJsks("");
            bdcJjdDO.setJsr("");
            bdcJjdDO.setJsrid("");
            bdcJjdDO.setDah("");
            bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcJjdDO), BdcJjdDO.class.getName());
        }
        LOGGER.info("获取退回意见为：{}", opinion);
        if (StringUtils.isNotBlank(opinion)) {
            try {
                // 回写大云退回时间，退回原因
                Map zdyxx = new HashMap();
                zdyxx.put("DATHSJ", new Date());
                zdyxx.put("THYY", opinion);
                bdcSlxxHxFeignService.hxBdcSlxxWithZdyxx(gzlslid, zdyxx);
            } catch (Exception e) {
                LOGGER.error("回写大云退回时间、退回意见失败。" + e.getMessage(), e);
            }
        }
    }

    @Override
    public void saveDajjJsr(String gzlslid, String currentUserName) {
        //档案接收节点办结时更新接收人相关信息
        BdcJjdDO bdcJjdDO = queryBdcJjd(gzlslid);
        if (Objects.nonNull(bdcJjdDO) && StringUtils.isBlank(bdcJjdDO.getJsr())) {
            UserDto userDto = userManagerUtils.getUserByName(currentUserName);
            if (Objects.nonNull(userDto)) {
                bdcJjdDO.setJsr(userDto.getAlias());
                bdcJjdDO.setJsrid(userDto.getId());
                bdcJjdDO.setJsks(CollectionUtils.isNotEmpty(userDto.getOrgRecordList()) ? userDto.getOrgRecordList().get(0).getName() : "");
                bdcJjdDO.setJssj(new Date());
                bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcJjdDO), BdcJjdDO.class.getName());
            }
        }

    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 办结档案交接
     * @date : 2021/6/15 10:00
     */
    @Override
    public void bjDajj(String gzlslid) {
        //改变交接状态
        BdcJjdDO bdcJjdDO = queryBdcJjd(gzlslid);
        if (Objects.nonNull(bdcJjdDO)) {
            bdcJjdDO.setJjdzt(6);
            bdcEntityFeignService.updateByJsonEntity(JSON.toJSONString(bdcJjdDO), BdcJjdDO.class.getName());
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 改变不动产单元的状态为锁定状态-1,权利的备注中增加已裁决
     * @date : 2021/8/19 15:01
     */
    @Override
    public void updateSdztSdAndBz(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                List<String> bdcdyhList = bdcXmDTOList.stream().map(BdcXmDTO::getBdcdyh).collect(Collectors.toList());
                List<String> xmidList = bdcXmDTOList.stream().map(BdcXmDTO::getXmid).collect(Collectors.toList());
                BdcDysdQO bdcDysdQO = new BdcDysdQO();
                bdcDysdQO.setBdcdyhList(bdcdyhList);
                bdcDysdQO.setSdlx(CommonConstantUtils.SDLX_CJSD);
                List<BdcDysdDO> bdcDysdDOList = bdcdySdService.listBdcDySd(bdcDysdQO);
                if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
                    for (BdcDysdDO bdcDysdDO : bdcDysdDOList) {
                        bdcDysdDO.setSdyy("司法裁决锁定");
                        bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_SD);
                        bdcDysdDO.setSdsj(new Date());
                        entityMapper.updateByPrimaryKeySelective(bdcDysdDO);
                    }
                }
                //同步权籍的锁定状态
                bdcDbxxFeignService.synQjBdcdyztSd(bdcdyhList, CommonConstantUtils.SDZT_SD);
                //所有的权利,备注中统一增加"已裁决"字样
                List<String> yXmidList = new ArrayList<>(CollectionUtils.size(xmidList));
                for (String xmid : xmidList) {
                    //找到上一手的非外联的信息
                    BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                    bdcXmLsgxQO.setXmid(xmid);
//                    bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
                    List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                    List<String> yxmids = bdcXmLsgxDOList.stream().map(BdcXmLsgxDO::getYxmid).collect(Collectors.toList());
                    yXmidList.addAll(yxmids);
                }
                LOGGER.info("需要更新权利备注的xmid{}", yXmidList);
                //查询需要更新备注的权利信息
                List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByXmids(yXmidList);
                for (BdcQl bdcQl : bdcQlList) {
                    bdcQl.setFj((StringUtils.isNotBlank(bdcQl.getFj()) ? bdcQl.getFj() : "") + "  已裁决");
                    entityMapper.updateByPrimaryKeySelective(bdcQl);
                }
            }
        }
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 改变裁决锁定的状态为0 正常
     * @date : 2021/8/20 10:06
     */
    @Override
    public void updateSdztJs(String gzlslid) {
        sfcjDysdJs(gzlslid, "司法裁定转移");
    }

    /**
     * @param processInsId
     * @param currentUserName
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 删除修正流程
     * @date : 2021/8/30
     */
    @Override
    public void deleteXzlc(String processInsId, String currentUserName) {
        if (StringUtils.isBlank(processInsId)) {
            LOGGER.info("缺失processInsId,删除操作已终止！");
            return;
        }
        LOGGER.info("删除修正流程接口入参：{},{}", processInsId, currentUserName);
        BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
        bdcSlXzxxQO.setGzlslid(processInsId);
        BdcSlXzxxDO bdcSlXzxxDO = bdcSlXzxxFeignService.queryBdcSlXzxx(bdcSlXzxxQO);
        if (bdcSlXzxxDO != null && StringUtils.isNotBlank(bdcSlXzxxDO.getYxmid())) {
            String xmid = bdcSlXzxxDO.getYxmid();
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(listXm)) {
                BdcXmDO bdcXmDO = listXm.get(0);
                String gzlslid = bdcXmDO.getGzlslid();
                Integer ajzt = bdcXmDO.getAjzt();
                // 在办的需要激活
                if (ajzt.equals(CommonConstantUtils.AJZT_ZB_DM)) {
                    List<String> processInstanceIds = new ArrayList<>();
                    processInstanceIds.add(gzlslid);
                    List<ProcessOptResultDto> list = taskHandleClient.batchTaskActivation(processInstanceIds, "修正流程删除，激活被挂起流程");
                    if (CollectionUtils.isNotEmpty(list)) {
                        LOGGER.info("解挂成功：{}", gzlslid);
                    }
                }
                //解锁证书和不动产单元号
                zsJsAndBdcdyhJs(bdcXmDO, currentUserName, "修正流程删除，解锁不动产单元号", "修正流程删除，解锁证书");
                //更新权籍锁定状态
                List<String> bdcdyhList = listXm.stream().map(BdcXmDO::getBdcdyh).collect(Collectors.toList());
                bdcDbxxFeignService.synQjBdcdyztSd(bdcdyhList, CommonConstantUtils.SDZT_JS);
            } else {
                LOGGER.info("未找到被修正的流程:{},删除操作已终止！", xmid);
            }
        } else {
            LOGGER.info("未找到被修正的流程,删除操作已终止！");
        }
    }

    /**
     * 单元锁定
     *
     * @param bdcXmDO
     */
    private void dysd(BdcXmDO bdcXmDO, String currentUserName) {
        UserDto userDto = userManagerUtils.getUserByName(currentUserName);

        String bdcdyh = bdcXmDO.getBdcdyh();
        //先查询是否存在
        BdcDysdDO bdcDysdQO = new BdcDysdDO();
        bdcDysdQO.setXmid(bdcXmDO.getXmid());
        bdcDysdQO.setBdcdyh(bdcdyh);
        List<BdcDysdDO> bdcDySdDOList = bdcSdFeignService.queryBdcdySd(bdcDysdQO);
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        boolean update = false;
        if (CollectionUtils.isNotEmpty(bdcDySdDOList)) {
            bdcDysdDO = bdcDySdDOList.get(0);
            update = true;
        }
        bdcDysdDO.setBdcdyh(bdcdyh);
        bdcDysdDO.setBdcdywybh(bdcXmDO.getBdcdywybh());
        bdcDysdDO.setBdclx(bdcXmDO.getBdclx());
        bdcDysdDO.setSdr(userDto == null ? "" : userDto.getAlias());
        bdcDysdDO.setSdyy("正在办理修正流程");
        bdcDysdDO.setSdsj(new Date());
        bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_SD);
        bdcDysdDO.setXmid(bdcXmDO.getXmid());
        List<BdcDysdDO> listSd = new ArrayList<>();
        listSd.add(bdcDysdDO);
        if (update) {
            LOGGER.info("开始新增锁定bdcdy:{}", JSONObject.toJSONString(bdcDysdDO));
            entityMapper.updateByPrimaryKeySelective(bdcDysdDO);
            bdcDbxxFeignService.synQjBdcdyztSd(Collections.singletonList(bdcdyh), CommonConstantUtils.SDZT_SD);
        } else {
            LOGGER.info("开始锁定bdcdy:{}", JSONObject.toJSONString(bdcDysdDO));
            bdcSdFeignService.sdBdcdy(listSd);
        }
    }

    /**
     * 证书锁定
     *
     * @param bdcXmDO
     */
    private void zssd(BdcXmDO bdcXmDO, String currentUserName) {
        UserDto userDto = userManagerUtils.getUserByName(currentUserName);

        String xmid = bdcXmDO.getXmid();
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(xmid);
        List<BdcZsDO> listZs = bdcZsFeignService.listBdcZs(bdcZsQO);
        List<BdcZssdDO> listZssd = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(listZs)) {
            //查询是否存在证书锁定
            List<BdcZssdDO> bdcZssdDOList = bdcSdFeignService.queryBdczsSdByGzlslid(bdcXmDO.getGzlslid());
            BdcZssdDO bdcZssdDO = new BdcZssdDO();
            boolean update = false;
            if (CollectionUtils.isNotEmpty(bdcZssdDOList)) {
                bdcZssdDO = bdcZssdDOList.get(0);
                update = true;
            }
            for (BdcZsDO bdcZsDO : listZs) {
                if (!update) {
                    bdcZssdDO = new BdcZssdDO();
                }
                bdcZssdDO.setBdclx(bdcXmDO.getBdclx());
                bdcZssdDO.setCqzh(bdcZsDO.getBdcqzh());
                bdcZssdDO.setGzldymc(bdcXmDO.getGzldymc());
                bdcZssdDO.setGzlslid(bdcXmDO.getGzlslid());
                bdcZssdDO.setSdr(userDto == null ? "" : userDto.getAlias());
                bdcZssdDO.setSdyy("正在办理修正流程");
                bdcZssdDO.setXmid(xmid);
                bdcZssdDO.setZsid(bdcZsDO.getZsid());
                bdcZssdDO.setSdzt(CommonConstantUtils.SDZT_SD);
                if (update) {
                    LOGGER.info("开始更新锁定zs:{}", JSONObject.toJSONString(bdcZssdDO));
                    entityMapper.updateByPrimaryKeySelective(bdcZssdDO);
                    bdcDbxxFeignService.synQjBdcdyztSd(Collections.singletonList(bdcXmDO.getBdcdyh()), CommonConstantUtils.SDZT_SD);
                } else {
                    listZssd.add(bdcZssdDO);
                }
            }
            if (CollectionUtils.isNotEmpty(listZssd)) {
                LOGGER.info("开始锁定zs:{}", JSONObject.toJSONString(listZssd));
                bdcSdFeignService.sdBdczs(listZssd, "正在办理修正流程");
            }
        }
    }

    /**
     * 修正流程删除，解锁不动产单元和证书
     *
     * @param bdcXmDO
     * @param currentUserName
     * @param bdcdyhJsyy      不动产单元号解锁原因
     * @param zsJsyy          证书解锁原因
     */
    private void zsJsAndBdcdyhJs(BdcXmDO bdcXmDO, String currentUserName, String bdcdyhJsyy, String zsJsyy) {
        UserDto userDto;
        if (StringUtils.isNotBlank(currentUserName)) {
            userDto = userManagerUtils.getUserByName(currentUserName);
        } else {
            userDto = userManagerUtils.getCurrentUser();
        }
        String bdcdyh = bdcXmDO.getBdcdyh();
        BdcDysdDO bdcDysdDO = new BdcDysdDO();
        bdcDysdDO.setBdcdyh(bdcdyh);
        bdcDysdDO.setSdyy("正在办理修正流程");
        bdcDysdDO.setXmid(bdcXmDO.getXmid());
        // 根据bdcdyh和锁定原因 查询被锁定的bdcdyh 进行解锁
        List<BdcDysdDO> bdcDysdDOList = bdcSdFeignService.queryBdcdySd(bdcDysdDO);
        if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
            for (BdcDysdDO bdcDysdDOJs : bdcDysdDOList) {
                if (userDto != null) {
                    bdcDysdDOJs.setJsr(userDto.getAlias());
                    bdcDysdDOJs.setJsrid(userDto.getId());
                } else {
                    bdcDysdDOJs.setJsr("未知");
                }
                bdcDysdDOJs.setJssj(new Date());
                bdcDysdDOJs.setJsyy(bdcdyhJsyy);
                bdcDysdDOJs.setSdzt(CommonConstantUtils.SDZT_JS);
                entityMapper.updateByPrimaryKeySelective(bdcDysdDOJs);
            }
        }
        String bdcqzh = bdcXmDO.getBdcqzh();
        if (StringUtils.isNotBlank(bdcqzh)) {
            BdcZssdDO bdcZssdDO = new BdcZssdDO();
            bdcZssdDO.setCqzh(bdcqzh);
            bdcZssdDO.setSdyy("正在办理修正流程");
            bdcZssdDO.setXmid(bdcXmDO.getXmid());
            // 根据bdcdyh和锁定原因 查询被锁定的bdcdyh 进行解锁
            List<BdcZssdDO> bdcZssdDOList = bdcSdFeignService.queryBdczsSd(bdcZssdDO);

            if (CollectionUtils.isNotEmpty(bdcZssdDOList)) {
                for (BdcZssdDO bdcZssdDOJs : bdcZssdDOList) {
                    if (userDto != null) {
                        bdcZssdDOJs.setJsr(userDto.getAlias());
                        bdcZssdDOJs.setJsrid(userDto.getId());
                    } else {
                        bdcZssdDOJs.setJsr("未知");
                    }
                    bdcZssdDOJs.setJssj(new Date());
                    bdcZssdDOJs.setJsyy(zsJsyy);
                    bdcZssdDOJs.setSdzt(CommonConstantUtils.SDZT_JS);
                    entityMapper.updateByPrimaryKeySelective(bdcZssdDOJs);
                }
            }
        }
    }

    private BdcJjdDO queryBdcJjd(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcJjdQO bdcJjdQO = new BdcJjdQO();
            bdcJjdQO.setDajjGzlslid(gzlslid);
            List<BdcJjdDO> bdcJjdDOList = bdcJjdFeignService.listBdcJjd(bdcJjdQO);
            if (CollectionUtils.isNotEmpty(bdcJjdDOList)) {
                return bdcJjdDOList.get(0);
            }
        }
        return null;
    }

    /**
     * 获取行政代码
     *
     * @param gzlslid
     * @return
     */
    @Override
    public Map<String, String> getXzdm(String gzlslid) {
        String xzdm = "";
        if (StringUtils.isBlank(gzlslid)) {
            LOGGER.info("缺失processInsId！");
            return null;
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        LOGGER.info("获取的bdcXmDOList: {} ", JSON.toJSONString(bdcXmDOList));
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            xzdm = bdcXmDOList.get(0).getQxdm();
            if (MapUtils.isNotEmpty(qxdmXzdmConfig.getQxdmMap()) && qxdmXzdmConfig.getQxdmMap().containsKey(xzdm)) {
                xzdm = qxdmXzdmConfig.getQxdmMap().get(xzdm);
            }
        }
        Map<String, String> result = new HashMap<>(1);
        LOGGER.info("gzlslid {} 获取的行政代码为：{}", gzlslid, xzdm);
        //属地审核先固定传空， 大云服务存在问题
        result.put("message", xzdm);
        return result;
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 改变裁决锁定的状态为0 正常 流程删除时调用
     * @date : 2021/9/29 11:29
     */
    @Override
    public void updateSdztJsForLcsc(String gzlslid) {
        sfcjDysdJs(gzlslid, "司法裁决删除");
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 撤销登记转发或者办结时解锁单元锁定，更新时间和人
     * @date : 2022/9/1 11:42
     */
    @Override
    public void updateSdztForCxdj(String gzlslid, String userName) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                //只是撤销流程配置的事件，所以查询的作为外联项目的单元锁定数据
                List<String> bdcdyhList = bdcXmDTOList.stream().map(BdcXmDTO::getBdcdyh).collect(Collectors.toList());
                List<BdcXmLsgxDO> bdcXmLsgxDOList = new ArrayList<>(bdcXmDTOList.size());
                for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                    BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                    bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
                    bdcXmLsgxQO.setXmid(bdcXmDTO.getXmid());
                    List<BdcXmLsgxDO> bdcXmLsgxList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                    if (CollectionUtils.isNotEmpty(bdcXmLsgxList)) {
                        bdcXmLsgxDOList.addAll(bdcXmLsgxList);
                    }
                }
                if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                    List<BdcDysdDO> allDysdDOList = new ArrayList<>(CollectionUtils.size(bdcXmLsgxDOList));
                    for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxDOList) {
                        BdcDysdQO bdcDysdQO = new BdcDysdQO();
                        bdcDysdQO.setXmid(bdcXmLsgxDO.getYxmid());
                        bdcDysdQO.setSdlx(CommonConstantUtils.SDLX_CJSD);
                        List<BdcDysdDO> bdcDysdDOList = bdcdySdService.listBdcDySd(bdcDysdQO);
                        if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
                            allDysdDOList.addAll(bdcDysdDOList);
                        }
                    }
                    if (CollectionUtils.isNotEmpty(allDysdDOList)) {
                        LOGGER.warn("当前流程实例id{}撤销的上一手单元锁定数据{}", gzlslid, JSON.toJSONString(allDysdDOList));
                        for (BdcDysdDO bdcDysdDO : allDysdDOList) {
                            bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_JS);
                            bdcDysdDO.setJssj(new Date());
                            bdcDysdDO.setJsr(userName);
                            entityMapper.updateByPrimaryKeySelective(bdcDysdDO);
                        }
                        //同步权籍的锁定状态
                        bdcDbxxFeignService.synQjBdcdyztSd(bdcdyhList, CommonConstantUtils.SDZT_JS);
                    }
                }
            }
        }
    }

    /**
     * @param processInsId    工作流实例ID
     * @param currentNodeName 当前自动转发节点名称，如A节点自动转发到B节点，即为B节点名称
     * @return 返回指定人员用户名code=0,username=
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取节点自动转发指定人员
     */
    @Override
    public Map<String, String> getZdzfSlr(String processInsId, String currentNodeName) {
        Map<String, String> result = new HashMap<>(2);
        if(StringUtils.isNotBlank(processInsId) &&StringUtils.isNotBlank(currentNodeName)){
            if(MapUtils.isNotEmpty(zdzfJdmcAndSlr) &&zdzfJdmcAndSlr.containsKey(currentNodeName)){
                String key =zdzfJdmcAndSlr.get(currentNodeName);
                String slr =redisUtils.getHashValue(CommonConstantUtils.ZDZF_SLR+key,processInsId);
                if(StringUtils.isNotBlank(slr)&& !"null".equals(slr)){
                    result.put("code","0");
                    result.put("username",slr);
                }
            }else{
                LOGGER.error("gzlslid:{}获取节点自动转发指定人员配置节点与当前节点不符合:{}{}",processInsId,zdzfJdmcAndSlr,currentNodeName);
            }
        }
        LOGGER.info("gzlslid:{}节点：{}获取节点自动转发指定人员:{}",processInsId,currentNodeName,result);
        return result;

    }

    @Override
    public Map<String, String> getWwwsqYshry(String processInsId,String currentNodeName){
        Map<String, String> result = new HashMap<>(2);
        if(StringUtils.isNotBlank(processInsId) &&StringUtils.isNotBlank(currentNodeName)){
            //获取同外网受理编号的其他项目
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listOtherXmWithSameSpxtywh(processInsId);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                //根据受理时间排序
                bdcXmDOList.sort(Comparator.comparing(BdcXmDO::getSlsj));
                String ygzlslid =bdcXmDOList.get(bdcXmDOList.size()-1).getGzlslid();

                if(StringUtils.isNotBlank(ygzlslid)) {
                    List<TaskData> listTask = processTaskClient.listProcessTask(ygzlslid);
                    //匹配节点,找到办理人员
                    for(TaskData taskData:listTask){
                        if(StringUtils.equals(taskData.getTaskName(),currentNodeName)){
                            LOGGER.info("gzlslid：{}currentNodeName:{}获取外网申请退回件原审核人员：{}",processInsId,currentNodeName,taskData.getTaskAssigin());
                            result.put("code","0");
                            result.put("username",taskData.getTaskAssigin());
                            return result;
                        }
                    }
                }
            }
        }
        LOGGER.info("gzlslid：{}currentNodeName：{},未获取到外网申请退回件原审核人员",processInsId,currentNodeName);
        return result;

    }

    /**
     * @author 
     * @description  解锁锁定类型为司法裁决锁定的单元锁定数据
     */
    private void sfcjDysdJs(String gzlslid, String jsyy) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                List<String> bdcdyhList = bdcXmDTOList.stream().map(BdcXmDTO::getBdcdyh).collect(Collectors.toList());
                BdcDysdQO bdcDysdQO = new BdcDysdQO();
                bdcDysdQO.setBdcdyhList(bdcdyhList);
                bdcDysdQO.setSdlx(CommonConstantUtils.SDLX_CJSD);
                List<BdcDysdDO> bdcDysdDOList = bdcdySdService.listBdcDySd(bdcDysdQO);
                if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
                    for (BdcDysdDO bdcDysdDO : bdcDysdDOList) {
                        bdcDysdDO.setJsyy(jsyy);
                        bdcDysdDO.setSdzt(CommonConstantUtils.SDZT_JS);
                        bdcDysdDO.setJssj(new Date());
                        entityMapper.updateByPrimaryKeySelective(bdcDysdDO);
                    }
                    //同步权籍的锁定状态
                    bdcDbxxFeignService.synQjBdcdyztSd(bdcdyhList, CommonConstantUtils.SDZT_JS);
                }
            }
        }
    }
}
