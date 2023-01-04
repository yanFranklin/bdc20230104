package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcUpdateDagsdDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcUpdateDagsdQo;
import cn.gtmap.realestate.common.core.qo.certificate.BdcGdxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcSendMsgFeignService;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcGdxxRestService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/2/22
 * @description 归档台账服务
 */
@RestController
@RequestMapping("/rest/v1.0/gdxx")
public class BdcGdxxController extends BaseController {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGdxxController.class.getName());

    @Autowired
    BdcGdxxRestService bdcGdxxRestService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    @Autowired
    BdcSendMsgFeignService bdcSendMsgFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    ProcessDefinitionClient processDefinitionClient;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * 档案系统地址
     */
    @Value("${url.archive}")
    String archiveUrl;


    /**
     * 归档是否要查询受理的数据
     */
    @Value("${gdxx.cxsl:false}")
    Boolean gdcxsl;


    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取归档分页查询数据
     */
    @GetMapping("/page")
    public Object listBdcGdxx(@LayuiPageable Pageable pageable, BdcGdxxQO gdxxQO,String smqsr) {
        String bdcGdxxQO = JSON.toJSONString(gdxxQO);
        JSONObject jsonObject = JSONObject.parseObject(bdcGdxxQO);
        jsonObject.put("smqsr",smqsr);
        bdcGdxxQO = JSON.toJSONString(jsonObject);
        Page<BdcXmGdxxDTO> gdxxPage = bdcGdxxRestService.listBdcGdxxByPage(pageable, bdcGdxxQO);

        //按照扫描枪的输入来排序
        if(StringUtils.isNotBlank(smqsr)) {
            List<String> smqsrList = Arrays.asList(smqsr.split(","));
            List<BdcXmGdxxDTO> bdcXmGdxxDTOList = gdxxPage.getContent();
            if(CollectionUtils.isNotEmpty(bdcXmGdxxDTOList)){
                List<BdcXmGdxxDTO> bdcXmGdxxSortedList = new ArrayList<>();
                Map<String, List<BdcXmGdxxDTO>> bdcXmGdxxMap = bdcXmGdxxDTOList
                        .stream()
                        .collect(Collectors.groupingBy(BdcXmGdxxDTO::getSlbh));
                for (String slbh : smqsrList) {
                    if(bdcXmGdxxMap.containsKey(slbh)){
                        bdcXmGdxxSortedList.addAll(bdcXmGdxxMap.get(slbh));
                    }
                }

                if(CollectionUtils.size(bdcXmGdxxSortedList) == CollectionUtils.size(bdcXmGdxxDTOList)){
                    //替换为按照扫描枪输入的顺序排序后的顺序
                    gdxxPage =  new PageImpl(bdcXmGdxxSortedList,pageable,gdxxPage.getTotalElements());
                }
            }
        }

        return super.addLayUiCode(gdxxPage);
    }
    /**
     * @param bdcXmGdxxDTOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 归档
     */
    @PostMapping("/gd")
    public Map<String,String> gd(@RequestBody List<BdcXmGdxxDTO> bdcXmGdxxDTOList) {
        if (CollectionUtils.isEmpty(bdcXmGdxxDTOList)) {
            throw new MissingArgumentException("归档数据不能为空！");
        }
        String currentUserName=userManagerUtils.getCurrentUserName();

        Map<String,String> map = new ConcurrentHashMap<>(16);
        List<String> successList = new ArrayList<>();
        Set<String> errorList = new HashSet<>();
        List<String> errorMsgList = new ArrayList<>();

        Set<String> gzlslidSet = new HashSet<>();
        for(BdcXmGdxxDTO gdxx : bdcXmGdxxDTOList){
            // 底层调用的gdBdcXx方法会根据流程去归档（批量或者合并），这里如果批量件分开归档，页面勾选了N条，那么会导致执行 N*N 次归档
            if(gzlslidSet.contains(gdxx.getGzlslid())) {
               continue;
            } else {
                gzlslidSet.add(gdxx.getGzlslid());
            }

            try{
                String daid = bdcGdxxRestService.postArchiveByPz(gdxx.getGzlslid(), archiveUrl, gdxx.getXmid(), currentUserName);
                if(!StringUtil.isNullOrEmpty(daid)){
                    successList.add(gdxx.getSlbh());
                }else{
                    errorList.add(gdxx.getSlbh());
                }
            }catch(Exception e){
                errorMsgList.add("受理编号：" + gdxx.getSlbh() + " 归档失败：" + e.getCause());
            }
        }

        if(CollectionUtils.isNotEmpty(successList)){
            map.put("success", "受理编号：" + StringUtils.join(successList, ",") + " 归档成功!");
        }

        StringBuilder errorMsgBuilder = new StringBuilder();
        if(CollectionUtils.isNotEmpty(errorList)){
            errorMsgBuilder.append("受理编号：").append(StringUtils.join(successList, ",")).append(" 归档失败!");
        }
        if(CollectionUtils.isNotEmpty(errorMsgList)){
            errorMsgBuilder.append(StringUtils.join(errorMsgList, ";"));
        }
        map.put("error", errorMsgBuilder.toString());
        return map;
    }

    /**
     * 更新档案归属地
     * @param bdcUpdateDagsdDTOList
     * @return
     */
    @PostMapping("/updateDagsd")
    public Integer updateDagsd(@RequestBody List<BdcUpdateDagsdDTO> bdcUpdateDagsdDTOList){
        //获取新增移交单台账配置
        String showGdxxType = bdcGdxxRestService.getGdxxType();
        List<BdcUpdateDagsdDTO> djsj = bdcUpdateDagsdDTOList.stream().filter(bdcUpdateDagsdDTO -> Objects.isNull(bdcUpdateDagsdDTO.getSjly()) ||
                "0".equals(bdcUpdateDagsdDTO.getSjly())).collect(Collectors.toList());

        List<BdcUpdateDagsdDTO> slsj = bdcUpdateDagsdDTOList.stream().filter(bdcUpdateDagsdDTO ->
                Objects.nonNull(bdcUpdateDagsdDTO.getSjly()) &&
                        "1".equals(bdcUpdateDagsdDTO.getSjly())).collect(Collectors.toList()
        );

        if ("0".equals(showGdxxType)) {
            if(CollectionUtils.isNotEmpty(djsj)){
                List<String> gzlslids = djsj
                        .stream()
                        .map(BdcUpdateDagsdDTO::getGzlslid)
                        .collect(Collectors.toList());
                //按流程显示，更新当前流程下所有项目
                try {
                    BdcDjxxUpdateQO bdcDjxxUpdateQO= new BdcDjxxUpdateQO();
                    JSONObject xmfbJson = new JSONObject();
                    xmfbJson.put("dagsd",djsj.get(0).getDagsd());
                    Map<String,Object> map = new HashMap<>();
                    map.put("gzlslids",gzlslids);
                    bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(xmfbJson));
                    bdcDjxxUpdateQO.setWhereMap(map);
                    bdcXmfbFeignService.updateBatchBdcxmFb(bdcDjxxUpdateQO);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(CollectionUtils.isNotEmpty(slsj)){
                List<String> gzlslids = slsj
                        .stream()
                        .map(BdcUpdateDagsdDTO::getGzlslid)
                        .collect(Collectors.toList());
                List<BdcSlJbxxDO> bdcSlJbxxDOList = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslids(gzlslids);
                if(CollectionUtils.isNotEmpty(bdcSlJbxxDOList)) {
                    List<String> jbxxidList = bdcSlJbxxDOList
                            .stream()
                            .map(BdcSlJbxxDO::getJbxxid)
                            .collect(Collectors.toList());
                    BdcUpdateDagsdQo bdcUpdateDagsdQo = new BdcUpdateDagsdQo();
                    bdcUpdateDagsdQo.setDagsd(djsj.get(0).getDagsd());
                    bdcUpdateDagsdQo.setJbxxidList(jbxxidList);
                    bdcSlXmFeignService.batchUpdateBdcSlXmDagsd(bdcUpdateDagsdQo);
                }
            }
        }else {
            if(CollectionUtils.isNotEmpty(slsj)){
                List<String> xmids = slsj
                        .stream()
                        .map(BdcUpdateDagsdDTO::getXmid)
                        .collect(Collectors.toList());
                BdcUpdateDagsdQo bdcUpdateDagsdQo = new BdcUpdateDagsdQo();
                bdcUpdateDagsdQo.setDagsd(djsj.get(0).getDagsd());
                bdcUpdateDagsdQo.setXmidList(xmids);
                bdcSlXmFeignService.batchUpdateBdcSlXmDagsd(bdcUpdateDagsdQo);
            }

            if(CollectionUtils.isNotEmpty(djsj)){
                List<BdcXmFbDO> bdcXmFbDOList = new ArrayList<>();
                for (BdcUpdateDagsdDTO bdcUpdateDagsdDTO : djsj) {
                    BdcXmFbDO bdcXmFbDO = new BdcXmFbDO();
                    BeanUtils.copyProperties(bdcUpdateDagsdDTO,bdcXmFbDO);
                    bdcXmFbDOList.add(bdcXmFbDO);
                }
                //按流程显示，更新当前流程下所有项目
                bdcXmfbFeignService.updateBatchBdcxmFbByXmid(bdcXmFbDOList);
            }
        }
        return 0;
    }

    /**
     * 根据当前xmid获取档案归属地
     * @param bdcXmFbQO
     * @return
     */
    @PostMapping("/getDagsd")
    public String getDagsd(@RequestBody BdcXmFbQO bdcXmFbQO){
        String dagsd = bdcXmfbFeignService.queryDagsd(bdcXmFbQO);
        if (StringUtils.isBlank(dagsd) && StringUtils.isNotBlank(bdcXmFbQO.getXmid())){
            //尝试查询受理的
            List<BdcSlXmDO> bdcSlXmDOS = bdcSlXmFeignService
                    .listBdcSlXmByXmids(Arrays.asList(bdcXmFbQO.getXmid()));
            if (CollectionUtils.isNotEmpty(bdcSlXmDOS)){
                return bdcSlXmDOS.get(0).getDagsd();
            }
        }
        return dagsd;
    }

    /**
     * @description 发送短信
     * @param gzlslids
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/11/23 10:55
     */
    @ResponseBody
    @PostMapping("/gdxxSendMsg")
    public void gdxxSendMsg(@RequestBody List<String> gzlslids){
        if (CollectionUtils.isEmpty(gzlslids)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        //对工作流实例ID去重
        List<String> gzlslidList = gzlslids.stream().distinct().collect(Collectors.toList());

        Map map = new HashMap<>(1);
        for (String gzlslid : gzlslidList) {

            map.put("gzlslid",gzlslid);
            //组织发送短信参数
            bdcSendMsgFeignService.sendSmsMsg(map,"szwctzqlr");
        }
    }

    /**
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @description  获取所有最新版本流程信息
     */
    @GetMapping("/process/definitions")
    public List<ProcessDefData> listProcessDefinitions(){
        return processDefinitionClient.getAllProcessDefData();
    }
}
