package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYcslDjywDzbDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlJbxxXmVO;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.TaskUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/20
 * @description 受理信息初始化
 */
@Controller
@RequestMapping("/slxxcsh")
public class BdcSlxxCshController extends BaseController {
    @Autowired
    private BdcSlFeignService bdcSlFeignService;
    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    BdcInitFeignService bdcInitFeignService;
    @Autowired
    BdcYcslDjywDzbFeignService bdcYcslDjywDzbFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private TaskUtils taskUtils;
    @Autowired
    BdcZjjgFeignService bdcZjjgFeignService;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Value("#{'${query.plcjlc.gzldyids:}'.split(',')}")
    private List<String> plcjlcGzldyidList;

    /**
     * 修正流程的工作流定义id
     */
    @Value("${xzlc.gzldyid:}")
    protected String xzlcGzldyid;

    @Value("#{'${zdjbdb.gzldyid:}'.split(',')}")
    private List<String> zdjbdbDyidList;

    /**
     * @param bdcSlCshDTO 受理初始化信息DTO
     * @return 结果
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 受理信息初始化
     */
    @ResponseBody
    @GetMapping("")
    public Map csh(BdcSlCshDTO bdcSlCshDTO) throws Exception {
        Date startTime = new Date();
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(bdcSlCshDTO.getJbxxid()) || StringUtils.isBlank(bdcSlCshDTO.getGzldyid())) {
            throw new AppException("初始化缺失基本信息ID或工作流定义ID！");
        }
        CommonResponse commonResponse =bdcSlFeignService.yzCshxxBeforeCj(bdcSlCshDTO);
        if(!commonResponse.isSuccess()){
            LOGGER.info("受理信息初始化失败：流程基本信息ID{}:{}", bdcSlCshDTO.getJbxxid(),commonResponse.getFail().getMessage());
            throw new AppException(commonResponse.getFail().getMessage());
        }
        LOGGER.info("受理信息初始化开始：流程基本信息ID{}", bdcSlCshDTO.getJbxxid());
        //页面工作流定义id不为空，先根据工作流定义id创建流程再初始化
        TaskData taskData = processInstanceClient.directStartProcessInstance(bdcSlCshDTO.getGzldyid(), userManagerUtils.getCurrentUserName(), "","",bdcSlCshDTO.getIntegerCnqx());
        //初始化成功标志
        Boolean cshSuccess =false;
        //获取accept 配置的所有非登记业务的流程
        List<String> fdjywlcList =bdcZjjgFeignService.getFdjywlcDyidList("");
        if (taskData != null) {
            try {
                bdcSlCshDTO.setSlsj(taskData.getStartTime());
                bdcSlCshDTO.setGzldymc(taskData.getProcessDefName());
                //非登记业务流程
                if(CollectionUtils.isNotEmpty(fdjywlcList) &&fdjywlcList.contains(bdcSlCshDTO.getGzldyid())) {
                    bdcSlCshDTO.setGzlslid(taskData.getProcessInstanceId());
                    bdcSlFeignService.cshBdcSlSqxx(bdcSlCshDTO);
                } else {
                    bdcSlCshDTO.setGzlslid(taskData.getProcessInstanceId());
                    bdcSlJbxxFeignService.insertBdcSlJbxx(bdcSlCshDTO);
                    bdcSlFeignService.cshBdcSlxx(bdcSlCshDTO.getJbxxid());
                    //标志初始化成功
                    cshSuccess = true;
                    try {
                        bdcSlFeignService.cshSjcl(taskData.getProcessInstanceId());
                    } catch (Exception e) {
                        LOGGER.warn("流程创建收件材料失败，异常信息", e);
                    }
                    try {
                        bdcSlSfxxFeignService.cshSfxx(taskData.getProcessInstanceId(), null);
                    } catch (Exception e) {
                        LOGGER.warn("流程创建收费信息失败，异常信息", e);
                    }
                    //批量创建业务流程获取预告的交易信息
                    if (CollectionUtils.isNotEmpty(plcjlcGzldyidList) && plcjlcGzldyidList.contains(bdcSlCshDTO.getGzldyid())) {
                        try {
                            LOGGER.warn("开始获取预告信息{}", taskData.getProcessInstanceId());
                            bdcSlJyxxFeignService.extendYgJyxx(taskData.getProcessInstanceId());
                        } catch (Exception e) {
                            LOGGER.error("获取预告交易信息异常", e);
                        }

                    }
                }
            } catch (Exception ex) {
                LOGGER.error("初始化失败", ex);
                //删除受理信息
                bdcSlFeignService.deleteBdcSlxx(taskData.getProcessInstanceId());
                //删除业务信息
                if(cshSuccess) {
                    //初始化成功执行
                    bdcInitFeignService.deleteYwxx(taskData.getProcessInstanceId());
                }
                //删除流程
                taskUtils.deleteTask(taskData.getProcessInstanceId(),taskData.getReason());
                throw ex;
            }
            map.put("processInsId", taskData.getProcessInstanceId());
            map.put("taskId", taskData.getTaskId());
        }

        if (StringUtils.isBlank(map.get("msg"))) {
            map.put("msg", "success");
        }
        map.put("gzldyid",xzlcGzldyid);
        long time = System.currentTimeMillis() - startTime.getTime();
        LOGGER.info("已完成流程基本信息ID{}受理信息初始化，所耗时间：{}", bdcSlCshDTO.getJbxxid(), time);
        try{
            if(taskData !=null){
                LOGGER.info("流程创建事件开始：流程实例ID{}", taskData.getProcessInstanceId());
                processInstanceClient.exeAfterCreateForProcessStart(taskData.getProcessInstanceId());
                LOGGER.info("流程创建事件结束：流程实例ID{}", taskData.getProcessInstanceId());
            }
        }catch(Exception e){
            LOGGER.error("调用流程创建事件失败", e);
            throw new AppException(ErrorCode.CUSTOM, "初始化完成后，调用配置的流程创建事件。");
        }

        // 受理初始化完成后,判断当前流程是否需要税务同步
        try{
            if(taskData !=null &&StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ, systemVersion) && isSwtbLc(bdcSlCshDTO.getGzldyid())){
                this.bdcSlFeignService.syncYcslxx(taskData.getProcessInstanceId(), null);
            }
        }catch(Exception e){
            LOGGER.error("同步税务信息失败", e);
            throw new AppException(ErrorCode.CUSTOM, "受理初始化完成后，同步一窗涉税信息。");
        }
        return map;
    }

    /**
     * @param jbxxid 基本信息ID
     * @param gzldyid 工作流定义ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 增量初始化
     */
    @ResponseBody
    @GetMapping("/zlcsh")
    public void zlcsh(String jbxxid,String gzldyid,String gzlslid) throws Exception {
        if (StringUtils.isBlank(jbxxid) || StringUtils.isBlank(gzldyid)) {
            throw new AppException("增量初始化缺失基本信息ID或工作流定义ID！");
        }
        if(checkDb(gzlslid)){
            throw new AppException("已办结或已登簿项目不允许新增不动产单元！");
        }
        //增量初始化受理信息
        bdcSlFeignService.zlcshBdcSlxx(jbxxid);
    }

    /**
     * @param gzldyid 工作流定义ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据一窗流程查询一窗受理与登记业务对照关系
     */
    @ResponseBody
    @GetMapping("/queryYcslDjywDzb")
    public BdcSlYcslDjywDzbDO queryYcslDjywDzb(String gzldyid) {
        BdcSlYcslDjywDzbDO bdcSlYcslDjywDzbDO =new BdcSlYcslDjywDzbDO();
        bdcSlYcslDjywDzbDO.setYcslgzldyid(gzldyid);
        return bdcYcslDjywDzbFeignService.queryYcslDjywDzb(bdcSlYcslDjywDzbDO);
    }

    /**
     * @param bdcSlCshDTO
     * @return TaskData
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 创建一窗流程
     */
    @ResponseBody
    @GetMapping("/ycslCsh")
    public TaskData ycslCsh(BdcSlCshDTO bdcSlCshDTO) throws Exception {

        if (StringUtils.isBlank(bdcSlCshDTO.getJbxxid()) || StringUtils.isBlank(bdcSlCshDTO.getGzldyid())) {
            throw new AppException("一体化初始化缺失基本信息ID或工作流定义ID！");
        }
        // 创建流程
        TaskData taskData = processInstanceClient.directStartProcessInstance(bdcSlCshDTO.getGzldyid(), userManagerUtils.getCurrentUserName(), "","", bdcSlCshDTO.getIntegerCnqx());
        if(taskData != null) {
            try {
                bdcSlCshDTO.setSlsj(taskData.getStartTime());
                bdcSlCshDTO.setGzldymc(taskData.getProcessDefName());
                bdcSlCshDTO.setGzlslid(taskData.getProcessInstanceId());
                //判断是否只登记不登簿流程
                if (CollectionUtils.isNotEmpty(zdjbdbDyidList) && zdjbdbDyidList.contains(bdcSlCshDTO.getGzldyid())) {
                    bdcSlCshDTO.setZdjbdb(true);
                }
                bdcSlJbxxFeignService.insertBdcSlJbxx(bdcSlCshDTO);
                // 初始化一窗信息
                bdcSlFeignService.cshBdcSlYcslxx(bdcSlCshDTO);
                //初始化收件材料
                bdcSlFeignService.cshSjcl(taskData.getProcessInstanceId());
                //初始化收费信息
                bdcSlSfxxFeignService.cshFdjlcSfxx(taskData.getProcessInstanceId());
            }catch (Exception ex) {
                LOGGER.error("一窗初始化失败", ex);
                //删除受理信息
                bdcSlFeignService.deleteBdcSlxx(taskData.getProcessInstanceId());
                //删除流程
                taskUtils.deleteTask(taskData.getProcessInstanceId(),taskData.getReason());
                throw ex;
            }
        }
        return taskData;


    }

    /**
     * @param xmids 删除项目ID集合
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  删除不动产单元
     */
    @ResponseBody
    @DeleteMapping("/deleteBdcdy")
    public void deleteBdcdy(@RequestParam("xmids") String[] xmids,@RequestParam(value = "gzlslid",required = false) String gzlslid) throws Exception {
        if (ArrayUtils.isEmpty(xmids)) {
            throw new AppException("删除缺失参数项目ID！");
        }
        if(checkDb(gzlslid)){
            throw new AppException("已办结或已登簿项目不允许删除不动产单元！");
        }
        //部分流程更新权利信息
        BdcXmQO bdcXmQO = new BdcXmQO(xmids[0]);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        //删除受理信息
        bdcSlFeignService.deleteBdcSlxx(Arrays.asList(xmids));
        // 删除业务数据
        bdcInitFeignService.deleteYwxx(xmids);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //删除业务信息后查找到剩下的xmid数组
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcXmDOList.get(0).getGzlslid());
            List<String> xmidList = bdcXmDTOList.stream().map(BdcXmDTO::getXmid).collect(Collectors.toList());
            bdcQllxFeignService.updateZjjwdyfw(xmidList, bdcXmDOList.get(0).getGzldyid());
        }
    }

    /**
     * @param
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新一窗信息
     */
    @ResponseBody
    @GetMapping("/syncYcslxx")
    public void syncYcslxx(String gzlslid,String xmid) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失工作流实例ID！");
        }
        bdcSlFeignService.syncYcslxx(gzlslid, xmid);
    }

    /**
     * 同步登记数据至受理数据中
     * <p>不修改登记流程 sply 标志为一窗流程，只同步数据<p/>
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     */
    @ResponseBody
    @GetMapping("/syncSlxx")
    public void syncSlxx(String gzlslid, String xmid) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失工作流实例ID！");
        }
        bdcSlFeignService.syncSlxx(gzlslid, xmid);
    }

    /**
     * @return Map 生成受理编号和基本信息ID
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 生成受理编号和基本信息ID
     */
    @GetMapping("/process/param")
    @ResponseBody
    public Map initParam(String processInsId) {
        String slbh = bdcBhFeignService.queryBh("slbh", "");
        String jbxxid = UUIDGenerator.generate16();
        Map<String, Object> param = Maps.newHashMap();
        param.put("slbh", slbh);
        param.put("jbxxid", jbxxid);
        param.put("xzlcGzldyid", xzlcGzldyid);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            param.put("bdcXmList", bdcXmDOList);
        }
        return param;
    }

    @GetMapping("/plcsh")
    @ResponseBody
    public Object plcsh(String jbxxid, String slbh) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new AppException("批量创建业务缺失jbxxid");
        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(jbxxid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            List<BdcSlJbxxXmVO> bdcSlJbxxXmVOList = new ArrayList<>();
            Map<String, List<BdcSlXmDO>> groupXmMap = bdcSlXmDOList.stream().collect(Collectors.groupingBy(BdcSlXmDO::getBdcdyh));
            List<String> groupList = new ArrayList<>(groupXmMap.keySet());
            for (int i = 0; i < CollectionUtils.size(groupList); i++) {
                List<BdcSlXmDO> slXmDOList = groupXmMap.get(groupList.get(i));
                BdcSlJbxxXmVO bdcSlJbxxXmVO = new BdcSlJbxxXmVO();
                //第一条数据沿用页面生成的，其他的创建业务的数据重新生成
                if (!Objects.equals(0, i)) {
                    jbxxid = UUIDGenerator.generate16();
                    slbh = bdcBhFeignService.queryBh(CommonConstantUtils.YWLX_SLBH, "");
                    for (BdcSlXmDO bdcSlXmDO : slXmDOList) {
                        bdcSlXmDO.setJbxxid(jbxxid);
                        bdcSlXmFeignService.updateBdcSlXm(bdcSlXmDO);
                    }
                }
                bdcSlJbxxXmVO.setJbxxid(jbxxid);
                bdcSlJbxxXmVO.setSlbh(slbh);
                bdcSlJbxxXmVOList.add(bdcSlJbxxXmVO);
            }
            return bdcSlJbxxXmVOList;
        }
        return null;
    }

    /**
     * @param bdcSlCshDTO 受理初始化信息DTO
     * @return 不动产受理基本信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 存在规则例外保存不动产受理基本信息
     */
    @ResponseBody
    @GetMapping("/gzlw/jbxx")
    public void saveGzlwBdcSlJbxx(BdcSlCshDTO bdcSlCshDTO){
        bdcSlJbxxFeignService.saveGzlwBdcSlJbxx(bdcSlCshDTO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  验证是否已登簿
     */
    private boolean checkDb(String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmQO.setCount(1);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                //ajzt=2(已办结) 或者权属状态非临时状态（排除查封登记）
                if (CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDO.getAjzt())) {
                    return true;
                }
                //查封登记创建即现势
                if (!CommonConstantUtils.QSZT_TEMPORY.equals(bdcXmDO.getQszt()) && !(CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx()) && CommonConstantUtils.QSZT_VALID.equals(bdcXmDO.getQszt()))) {
                    return true;
                }
            }
        }
        return false;

    }
}
