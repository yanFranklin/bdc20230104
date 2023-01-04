package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.core.thread.SyncYcslxxThread;
import cn.gtmap.realestate.accept.service.BdcSwService;
import cn.gtmap.realestate.accept.service.BdcYcslManageService;
import cn.gtmap.realestate.accept.service.WwsqCjBdcXmService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.portal.BdcCheckFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcYcslxxVO;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/9
 * @description 一窗逻辑处理服务
 */
@Service
public class BdcYcslManageServiceImpl implements BdcYcslManageService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYcslManageServiceImpl.class);
    @Autowired
    private DozerBeanMapper acceptDozerMapper;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlxxHxService bdcSlxxHxService;
    @Autowired
    BdcSlFeignService bdcSlFeignService;
    @Autowired
    BdcSlSjclService bdcSlSjclService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    BdcSlYcslDjywDzbService bdcSlYcslDjywDzbService;
    @Autowired
    BdcSlFwxxService bdcSlFwxxService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    WwsqCjBdcXmService wwsqCjBdcXmService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    private BdcInitFeignService bdcInitFeignService;
    @Autowired
    private BdcCheckFeignService bdcCheckFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlSqrService bdcSlSqrService;
    @Autowired
    ThreadEngine threadEngine;
    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    private Repo repo;
    @Autowired
    BdcSlFwtcxxService bdcSlFwtcxxService;
    @Autowired
    BdcShxxFeignService bdcShxxFeignService;
    @Autowired
    BdcSwService bdcSwService;
    @Autowired
    BdcSlJtcyService bdcSlJtcyService;
    @Autowired
    private TaskHandleClientMatcher taskHandleClient;
    @Autowired
    Set<BdcSlQlxxService> bdcSlQlxxServiceSet;
    @Autowired
    BdcSlLzrService bdcSlLzrService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    BdcDjxlPzService bdcDjxlPzService;


    @Value("${accept.dealZfAndFsss:true}")
    private String dealZfAndFsss;

    @Value("${data.version:}")
    private String dataversion;

    /**
     * 模板样例：ytmc的证明单为：bdcqzh，ytmc的坐落为：zl，ytmc的面积为jzmj平方米。
     */
    private final static String ZF_FJ_TEMPLATE = "%s的证明单为：%s，%s的坐落为：%s，%s的面积为%f平方米。";

    /**
     * 模板样例：ytmc的备案合同号为：htbh，ytmc的坐落为：zl，ytmc合同签订时间htbasj。
     */
    private final static String ZF_JYBZ_TEMPLATE = "%s的备案合同号为：%s，%s的坐落为：%s，%s合同签订时间%s。";

    @Override
    @Transactional
    public BdcSlxxInitDTO cshBdcYcslXx(List<BdcYwxxDTO> bdcYwxxDTOList, BdcSlxxDTO bdcSlxxDTO) throws Exception{
        //转换业务数据为一窗数据
        BdcSlxxInitDTO bdcSlxxInitDTO =changeYwxxDTOToBdcSlYcslxx(bdcYwxxDTOList,bdcSlxxDTO);
        //回写数据到平台
        bdcSlxxHxService.hxBdcSlxx(bdcSlxxDTO.getBdcSlJbxx());
        return bdcSlxxInitDTO;

    }

    @Override
    public BdcSlxxInitDTO changeYwxxDTOToBdcSlYcslxx(List<BdcYwxxDTO> bdcYwxxDTOList, BdcSlxxDTO bdcSlxxDTO) throws Exception{
        BdcSlxxInitDTO bdcSlxxInitDTO =new BdcSlxxInitDTO();
        List<BdcSlFwxxDO> bdcSlFwxxDOList =new ArrayList<>();
        List<BdcSlSqrDO> bdcSlSqrDOList =new ArrayList<>();
        List<BdcSlJyxxDO> bdcSlJyxxDOList =new ArrayList<>();
        List<BdcSlXmDO> bdcSlXmDOList =new ArrayList<>();
        List<BdcSlQl> bdcSlQlList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcYwxxDTOList)){
            for(BdcYwxxDTO bdcYwxxDTO:bdcYwxxDTOList){
                BdcSlXmDTO bdcSlXmDTO =new BdcSlXmDTO();
                //匹配对应的不动产受理信息
                for (BdcSlXmDTO slXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                    if (StringUtils.equals(slXmDTO.getBdcSlXm().getXmid(), bdcYwxxDTO.getBdcXm().getXmid())) {
                        bdcSlXmDTO =slXmDTO;
                        break;
                    }
                }

                //转换项目信息
                bdcSlXmDOList.add(changeBdcYwxxParameterXmxx(bdcYwxxDTO,bdcSlXmDTO));

                //转换房屋信息
                BdcSlFwxxDO bdcSlFwxxDO = changeBdcYwxxParameterFwxx(bdcYwxxDTO, bdcSlXmDTO);
                if (bdcSlFwxxDO != null) {
                    bdcSlFwxxDOList.add(bdcSlFwxxDO);
                }

                //转换申请人信息
                bdcSlSqrDOList.addAll(changeBdcYwxxParameterSqrxx(bdcYwxxDTO, bdcSlXmDTO));

                //转换交易信息
                BdcSlJyxxDO bdcSlJyxxDO = changeBdcYwxxParameterJyxx(bdcYwxxDTO, bdcSlXmDTO);
                if (bdcSlJyxxDO != null) {
                    bdcSlJyxxDOList.add(bdcSlJyxxDO);
                }

                //处理税务信息
                changeBdcYwxxParameterSwxx(bdcSlXmDTO);

                //转换抵押信息
                BdcSlQl bdcSlQl = changeBdcYwxxParameterDyaqxx(bdcYwxxDTO);
                if (bdcSlQl != null) {
                    bdcSlQlList.add(bdcSlQl);
                }

            }

            // 特殊业务需求：选择多个项目，处理为一主房，多附属设施的情况
            if(bdcSlFwxxDOList.size()> 1 && dealZfAndFsss.equals("true")){
                dealZfAndFsss(bdcSlFwxxDOList,bdcSlJyxxDOList, bdcSlXmDOList);
            }

            bdcSlxxInitDTO.setBdcSlFwxxDOList(bdcSlFwxxDOList);
            bdcSlxxInitDTO.setBdcSlSqrDOList(bdcSlSqrDOList);
            bdcSlxxInitDTO.setBdcSlJyxxDOList(bdcSlJyxxDOList);
            bdcSlxxInitDTO.setBdcSlQlList(bdcSlQlList);
            bdcSlxxInitDTO.setBdcSlXmDOList(bdcSlXmDOList);

            //批量保存
            List<BdcSlxxInitDTO> bdcSlxxInitDTOList =new ArrayList<>();
            bdcSlxxInitDTOList.add(bdcSlxxInitDTO);

            bdcSlXmService.saveBdcSlxx(bdcSlxxInitDTOList, Constants.FUN_UPDATE);

        }
        return bdcSlxxInitDTO;
    }

    @Override
    public BdcSlxxInitDTO changeYwxxDTOToBdcSlYcslxxSingle(BdcYwxxDTO bdcYwxxDTO, BdcSlXmDTO bdcSlXmDTO){
        BdcSlxxInitDTO bdcSlxxInitDTO =new BdcSlxxInitDTO();
        List<BdcSlXmDO> bdcSlXmDOList =new ArrayList<>();
        List<BdcSlFwxxDO> bdcSlFwxxDOList =new ArrayList<>();
        List<BdcSlSqrDO> bdcSlSqrDOList =new ArrayList<>();
        List<BdcSlJyxxDO> bdcSlJyxxDOList =new ArrayList<>();

        //转换项目信息
        bdcSlXmDOList.add(changeBdcYwxxParameterXmxx(bdcYwxxDTO,bdcSlXmDTO));

        //转换房屋信息
        BdcSlFwxxDO bdcSlFwxxDO =changeBdcYwxxParameterFwxx(bdcYwxxDTO,bdcSlXmDTO);
        if(bdcSlFwxxDO != null) {
            bdcSlFwxxDOList.add(bdcSlFwxxDO);
        }

        //转换申请人信息
        bdcSlSqrDOList.addAll(changeBdcYwxxParameterSqrxx(bdcYwxxDTO,bdcSlXmDTO));

        //转换交易信息
        BdcSlJyxxDO bdcSlJyxxDO =changeBdcYwxxParameterJyxx(bdcYwxxDTO,bdcSlXmDTO);
        if(bdcSlJyxxDO != null){
            bdcSlJyxxDOList.add(bdcSlJyxxDO);

        }



        //处理税务信息
        changeBdcYwxxParameterSwxx(bdcSlXmDTO);

        bdcSlxxInitDTO.setBdcSlXmDOList(bdcSlXmDOList);
        bdcSlxxInitDTO.setBdcSlJyxxDOList(bdcSlJyxxDOList);
        bdcSlxxInitDTO.setBdcSlFwxxDOList(bdcSlFwxxDOList);
        bdcSlxxInitDTO.setBdcSlSqrDOList(bdcSlSqrDOList);

        return bdcSlxxInitDTO;

    }

    @Override
    public InitTsBdcDjxxResponseDTO initTsBdcDjxx(BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO) throws Exception{
        InitTsBdcDjxxResponseDTO initTsBdcDjxxResponseDTO =new InitTsBdcDjxxResponseDTO();
        if(StringUtils.isBlank(bdcTsDjxxRequestDTO.getGzlslid())){
            throw new MissingArgumentException("gzlslid");
        }
        if(StringUtils.isBlank(bdcTsDjxxRequestDTO.getJbxxid())){
            BdcSlJbxxDO bdcSlJbxxDO =bdcSlJbxxService.queryBdcSlJbxxByGzlslid(bdcTsDjxxRequestDTO.getGzlslid());
            if(bdcSlJbxxDO == null){
                throw new AppException("缺失基本信息");

            }
            bdcTsDjxxRequestDTO.setJbxxid(bdcSlJbxxDO.getJbxxid());
        }
        //组织一窗创建登记流程请求对象
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO =initWwsqCjBdcXmRequestDTO(bdcTsDjxxRequestDTO);

        //自动创建
        WwsqCjBdcXmResponseDTO wwsqCjBdcXmResponseDTO =bdcSlFeignService.wwsqCjBdcXm(wwsqCjBdcXmRequestDTO);

        //验证不通过的处理
        if(wwsqCjBdcXmResponseDTO != null && CollectionUtils.isNotEmpty(wwsqCjBdcXmResponseDTO.getGzyzList())){
            StringBuilder msgSb = new StringBuilder("");
            List<Map<String,Object>> yzResult = wwsqCjBdcXmResponseDTO.getGzyzList();
            for(Map<String,Object> map:yzResult){
                String bdcdyh = MapUtils.getString(map,"bdcdyh");
                String yzmsg = MapUtils.getString(map,"msg");
                msgSb.append(bdcdyh).append("：").append(yzmsg).append(",");
            }
            String msg = msgSb.toString();
            if(msg.endsWith(",")){
                msg = msg.substring(0,msg.length()-1);
            }
            //返回失败信息
            initTsBdcDjxxResponseDTO.setMsg(msg);
            initTsBdcDjxxResponseDTO.setLczt(Constants.LCZT_CJSB);

        }

         if(wwsqCjBdcXmResponseDTO != null && CollectionUtils.isNotEmpty(wwsqCjBdcXmResponseDTO.getBdcXmDOList())) {
             initTsBdcDjxxResponseDTO.setBdcXmDOList(wwsqCjBdcXmResponseDTO.getBdcXmDOList());
             initTsBdcDjxxResponseDTO.setLczt(Constants.LCZT_CJCG);

             // 处理附件信息
             String djgzlslid=wwsqCjBdcXmResponseDTO.getBdcXmDOList().get(0).getGzlslid();
             initSjcl(bdcTsDjxxRequestDTO.getGzlslid(), djgzlslid);
             //返回成功流程信息
             initTsBdcDjxxResponseDTO.setSlbh(wwsqCjBdcXmResponseDTO.getBdcXmDOList().get(0).getSlbh());
             initTsBdcDjxxResponseDTO.setGzlslid(wwsqCjBdcXmResponseDTO.getBdcXmDOList().get(0).getGzlslid());
             //流程
             List<TaskData> taskDataList = processTaskClient.processRunningTasks(djgzlslid);
             if(CollectionUtils.isNotEmpty(taskDataList)){
                 initTsBdcDjxxResponseDTO.setTaskId(taskDataList.get(0).getTaskId());
             }

             if(bdcTsDjxxRequestDTO.getBdcYcslPzDTO() != null &&bdcTsDjxxRequestDTO.getBdcYcslPzDTO().isAutoTurn()) {
                 String msg = "";
                 String gzldyid = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getGzldyid();
                 List list =new ArrayList();
                 try{
                    list = (List)(bdcCheckFeignService.feignBdcgz(gzldyid,djgzlslid, CommonConstantUtils.GZYZ_LX_ZF, initTsBdcDjxxResponseDTO.getTaskId()));
                 }catch (Exception e){
                     LOGGER.error("登记流程创建成功，但转发验证失败",e);
                 }
                 LOGGER.info("转发验证返回值：{}",list);
                 if(CollectionUtils.isNotEmpty(list)){
                     StringBuffer zfyzmsg = new StringBuffer(list.size());
                     String resultmsg = "";
                     for(int i = 0;i<list.size();i++){
                         Map map = (Map) list.get(i);
                         zfyzmsg.append(map.get("tsxx")).append(",");
                         resultmsg = zfyzmsg.toString();
                     }
                     if(resultmsg.endsWith(",")){
                         resultmsg = resultmsg.substring(0,resultmsg.length()-1);
                     }
                     //返回失败信息
                     initTsBdcDjxxResponseDTO.setMsg(resultmsg);
                     initTsBdcDjxxResponseDTO.setLczt(Constants.LCZT_ZFSB);
                 }else{
                     //自动转发
                     // 签名
                     BdcShxxDO bdcShxxDO = new BdcShxxDO();
                     bdcShxxDO.setJdmc(CommonConstantUtils.JD_SL);
                     bdcShxxDO.setShxxid(initTsBdcDjxxResponseDTO.getTaskId());
                     bdcShxxDO.setGzlslid(djgzlslid);
                     bdcShxxDO.setShryxm(bdcTsDjxxRequestDTO.getSlrdlm());
                     bdcShxxFeignService.getShxxSign(bdcShxxDO);
                     try {
                         wwsqCjBdcXmService.autoTurnWorkflow(djgzlslid,null);
                         initTsBdcDjxxResponseDTO.setLczt(Constants.LCZT_ZFCG);
                     } catch (Exception e) {
                         // 转发失败 删除签名
                         bdcShxxFeignService.deleteShxxSign(initTsBdcDjxxResponseDTO.getTaskId());

                         initTsBdcDjxxResponseDTO.setLczt(Constants.LCZT_ZFSB);
                         msg = e.getMessage();
                         LOGGER.error("创建成功，自动转发失败，失败原因：{}", e.getMessage(), e);
                     }
                     initTsBdcDjxxResponseDTO.setMsg(msg);
                 }
             }

             //自动办结原一体化流程
             if(bdcTsDjxxRequestDTO.getBdcYcslPzDTO() != null &&bdcTsDjxxRequestDTO.getBdcYcslPzDTO().isAutoComplete()) {
                 taskHandleClient.autoComplete(bdcTsDjxxRequestDTO.getGzlslid(),StringUtils.isBlank(bdcTsDjxxRequestDTO.getSlrdlm()) ?userManagerUtils.getCurrentUserName():bdcTsDjxxRequestDTO.getSlrdlm());
             }
         }



         return initTsBdcDjxxResponseDTO;
    }

    @Override
    public void changeAjztEnd(String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)){
            BdcSlJbxxDO bdcSlJbxxDO =bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
            if(bdcSlJbxxDO != null){
                bdcSlJbxxDO.setAjzt(CommonConstantUtils.AJZT_YB_DM);
                bdcSlJbxxService.updateBdcSlJbxx(bdcSlJbxxDO);
            }
        }

    }

    @Override
    public void updateYwslzt(String gzlslid,Integer ywslzt){
        if(StringUtils.isNotBlank(gzlslid)){
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList) && CheckWwsqOrYcslUtils.checkIsYcsl(bdcXmDOList.get(0).getSply())&&StringUtils.isNotBlank(bdcXmDOList.get(0).getSpxtywh())){
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxBySlbh(bdcXmDOList.get(0).getSpxtywh(),CommonConstantUtils.JBXX_TYPE_YCSL);
                if(bdcSlJbxxDO != null){
                    bdcSlJbxxDO.setYwslzt(ywslzt);
                    bdcSlJbxxService.updateBdcSlJbxx(bdcSlJbxxDO);
                }
            }

        }

    }

    @Override
    public BdcSlxxInitDTO syncYcslxx(String xmid,String jbxxid) throws Exception{
        BdcSlxxInitDTO bdcSlxxInitDTO =new BdcSlxxInitDTO();

        //获取业务数据
        BdcYwxxDTO bdcYwxxDTO = bdcInitFeignService.queryYwxx(xmid);

        //获取当前受理数据
        BdcSlXmDTO bdcSlXmDTO =bdcSlXmService.queryBdcSlxx(xmid);
        if(bdcYwxxDTO != null &&bdcYwxxDTO.getBdcXm() != null) {
            if(bdcSlXmDTO.getBdcSlXm() == null) {
                //生成受理项目信息
                BdcSlXmDO bdcSlXmDO =new BdcSlXmDO();
                bdcSlXmDO.setXmid(xmid);
                bdcSlXmDO.setJbxxid(jbxxid);
                bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
            }


            //原有房屋套次信息需要清空
            bdcSlFwtcxxService.deleteBdcSlFwtcxxByXmid(xmid, "","");

            //同步业务数据到受理
            bdcSlxxInitDTO =changeYwxxDTOToBdcSlYcslxxSingle(bdcYwxxDTO,bdcSlXmDTO);
        }
        return bdcSlxxInitDTO;
    }

    @Override
    public void syncLcYcslxx(String gzlslid,String xmid) throws Exception{
        Date startTime = new Date();
        LOGGER.info("同步生成一窗信息开始,gzlslid:{}",gzlslid);
        if(StringUtils.isNotBlank(gzlslid)){
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isEmpty(bdcXmDOList)){
                throw new AppException("项目集合为空");
            }
            //更新sply,spxtywh,标志为一窗流程
            BdcDjxxUpdateQO bdcDjxxUpdateQO =new BdcDjxxUpdateQO();
            JSONObject jsonObject = new JSONObject();
            Integer sply =CommonConstantUtils.SPLY_YCSL;
            if(CheckWwsqOrYcslUtils.checkIsWwsq(bdcXmDOList.get(0).getSply())){
                sply =CommonConstantUtils.SPLY_WWSQ_YCSL;
                jsonObject.put("sply",sply);

            }else {
                jsonObject.put("sply", sply);
                jsonObject.put("spxtywh", bdcXmDOList.get(0).getSlbh());
            }
            bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(jsonObject));
            Map<String, Object> map = new HashMap<>();
            if (StringUtils.isNotBlank(xmid)) {
                map.put("xmids", xmid.split(CommonConstantUtils.ZF_YW_DH));
            } else {
                map.put("gzlslid", gzlslid);
            }
            bdcDjxxUpdateQO.setWhereMap(map);
            bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);

            // 同步并更新受理信息
            this.syncslxx(xmid, gzlslid, bdcXmDOList);

            //回写大云
            Map<String, Object> processInsExtendDto = new HashMap<>();
            processInsExtendDto.put("SPLY", sply);
            bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, processInsExtendDto);
        }
        LOGGER.info("同步生成一窗信息结束,gzlslid:{},总用时：{}",gzlslid,System.currentTimeMillis() - startTime.getTime());

    }
    private void syncslxx(String xmid, String gzlslid, List<BdcXmDO> bdcXmDOList) throws Exception {
        List<BdcSlxxInitDTO> bdcSlxxInitDTOList =new ArrayList<>();
        //删除原有申请人
        bdcSlSqrService.delBatchSqrAndJtcyByXmid(gzlslid, xmid,"");
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if(bdcSlJbxxDO ==null){
            //新增受理基本信息
            bdcSlJbxxDO =new BdcSlJbxxDO();
            BdcYwxxDTO bdcYwxxDTO =new BdcYwxxDTO();
            bdcYwxxDTO.setBdcXm(bdcXmDOList.get(0));
            changeBdcYwxxParameterJbxx(bdcYwxxDTO,bdcSlJbxxDO);
            bdcSlJbxxDO.setJbxxid(UUIDGenerator.generate16());
            bdcSlJbxxService.insertBdcSlJbxx(bdcSlJbxxDO);
        }
        //判断是否不走多线程计数限制
        boolean sfbjs = (bdcXmDOList.size() <= 1);
        //循环线程
        List<SyncYcslxxThread> listThread = new ArrayList<>();
        for(BdcXmDO bdcXmDO: bdcXmDOList){
            SyncYcslxxThread syncYcslxxThread =new SyncYcslxxThread(this,bdcXmDO.getXmid(),bdcSlJbxxDO.getJbxxid());
            syncYcslxxThread.setSfbjs(sfbjs);
            listThread.add(syncYcslxxThread);
        }
        //多线程处理操作
        threadEngine.excuteThread(listThread, true);
        //获取对应返回值
        for(SyncYcslxxThread syncYcslxxThread:listThread){
            bdcSlxxInitDTOList.add(syncYcslxxThread.getBdcSlxxInitDTO());
        }

        bdcSlXmService.saveBdcSlxx(bdcSlxxInitDTOList, Constants.FUN_UPDATE);
    }

    @Override
    public void syncLcSlxx(String gzlslid, String xmid) throws Exception {
        Date startTime = new Date();
        LOGGER.info("同步生成受理开始,gzlslid:{}",gzlslid);
        if(StringUtils.isNotBlank(gzlslid)){
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isEmpty(bdcXmDOList)){
                throw new AppException("项目集合为空");
            }

            // 同步并更新受理信息
            this.syncslxx(xmid, gzlslid, bdcXmDOList);
        }
        LOGGER.info("同步生成受理信息结束,gzlslid:{},总用时：{}",gzlslid,System.currentTimeMillis() - startTime.getTime());
    }


    @Override
    public void syncWwsqxxToYcslxx(String gzlslid,String xmid,BdcSlxxDTO bdcSlxxDTO) throws Exception{
        List<BdcSlxxInitDTO> bdcSlxxInitDTOList =new ArrayList<>();
        if(StringUtils.isNotBlank(gzlslid)){
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isEmpty(bdcXmDOList)){
                throw new AppException("项目集合为空");
            }
            boolean updateJbxx =false;
            if(StringUtils.isNotBlank(bdcSlxxDTO.getBdcSlJbxx().getJbxxid())){
                BdcSlJbxxDO bdcSlJbxxDO =bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcSlxxDTO.getBdcSlJbxx().getJbxxid());
                if(bdcSlJbxxDO != null){
                    updateJbxx =true;
                }
            }
            if(!updateJbxx) {
                //新增bdc_sl_jbxx
                bdcSlJbxxService.insertBdcSlJbxx(bdcSlxxDTO.getBdcSlJbxx());
            }else{
                bdcSlJbxxService.updateBdcSlJbxx(bdcSlxxDTO.getBdcSlJbxx());
            }

            for (BdcSlXmDTO slXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                if(StringUtils.isBlank(xmid) ||StringUtils.equals(xmid,slXmDTO.getBdcSlXm().getXmid())) {
                    //获取业务数据
                    BdcYwxxDTO bdcYwxxDTO = bdcInitFeignService.queryYwxx(slXmDTO.getBdcSlXm().getXmid());
                    bdcSlxxInitDTOList.add(changeYwxxDTOToBdcSlYcslxxSingle(bdcYwxxDTO, slXmDTO));
                }

            }

            bdcSlXmService.saveBdcSlxx(bdcSlxxInitDTOList, Constants.FUN_UPDATE);
        }

    }

    @Override
    public Page<BdcYcslxxVO> listYcslxxByPage(Pageable pageable, String bdcYcslQOStr){
        Map map = JSONObject.parseObject(bdcYcslQOStr, HashMap.class);
        Page<BdcYcslxxVO> bdcYcslxxVOPage = repo.selectPaging("listYcslxxByPage", map, pageable);
        //处理申请人
        if (CollectionUtils.isNotEmpty(bdcYcslxxVOPage.getContent())) {
            dealYcslListQlrxx(bdcYcslxxVOPage.getContent());
        }
        return bdcYcslxxVOPage;

    }

    @Override
    public List<BdcYcslxxVO> listYcslxxList(String bdcYcslQOStr){
        Map map = JSONObject.parseObject(bdcYcslQOStr, HashMap.class);
        List<BdcYcslxxVO> bdcYcslxxVOList = repo.selectList("listYcslxxByPage", map);
        //处理申请人
        if (CollectionUtils.isNotEmpty(bdcYcslxxVOList)) {
            dealYcslListQlrxx(bdcYcslxxVOList);
        }
        return bdcYcslxxVOList;

    }

    @Override
    public String getWwsqSlbhByDjGzlslid(String gzlslid){
        String spxtywh ="";
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("缺失工作流实例ID");
        }
        BdcXmQO bdcXmQO =new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList) &&CheckWwsqOrYcslUtils.checkIsWwsq(bdcXmDOList.get(0).getSply())){
            if(CommonConstantUtils.SPLY_WWSQ_DETAIL.contains(bdcXmDOList.get(0).getSply())){
                //非涉税流程
                return bdcXmDOList.get(0).getSpxtywh();
            }else if(CommonConstantUtils.SPLY_WWSQ_YCSL.equals(bdcXmDOList.get(0).getSply())){
                //涉税流程
                BdcSlYcslDjywDzbDO bdcSlYcslDjywDzbQO =new BdcSlYcslDjywDzbDO();
                bdcSlYcslDjywDzbQO.setDjgzldyid(bdcXmDOList.get(0).getGzldyid());
                BdcSlYcslDjywDzbDO bdcSlYcslDjywDzbDO =bdcSlYcslDjywDzbService.queryYcslDjywDzb(bdcSlYcslDjywDzbQO);
                if(bdcSlYcslDjywDzbDO != null &&StringUtils.isNotBlank(bdcSlYcslDjywDzbDO.getYcslgzldyid())){
                    //存在一窗受理与登记对照关系,一体化与登记为独立流程,外网申请受理编号存在一体化流程中
                    if(StringUtils.isNotBlank(bdcXmDOList.get(0).getSpxtywh())) {
                        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxBySlbh(bdcXmDOList.get(0).getSpxtywh(),CommonConstantUtils.JBXX_TYPE_YCSL);
                        if(bdcSlJbxxDO != null) {
                            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
                            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                                return bdcSlXmDOList.get(0).getSpxtywh();
                            }
                        }
                    }
                }else{
                    //不存在对照关系,一窗与登记为融合流程
                    return bdcXmDOList.get(0).getSpxtywh();
                }
            }
        }

        // 未获取到项目数据时，则只生成了受理库的数据，判断受理项目表中的审批来源是否为互联网+，并返回审批系统业务号
        BdcSlJbxxDO bdcSlJbxxDO =  this.bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if(null != bdcSlJbxxDO){
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");

            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)){
                Integer xmywlx = bdcSlXmDOList.get(0).getXmywlx();
                if(CheckWwsqOrYcslUtils.checkIsWwsq(xmywlx)){
                    return bdcSlXmDOList.get(0).getSpxtywh();
                }
            }
        }
        return spxtywh;
    }

    @Override
    public String getYhxtSlbhByDjGzlslid(String gzlslid) {
        String spxtywh = "";
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失工作流实例ID");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && CheckWwsqOrYcslUtils.checkIsYhxt(bdcXmDOList.get(0).getSply())) {
            return bdcXmDOList.get(0).getSpxtywh();
        }
        // 未获取到项目数据时，则只生成了受理库的数据，判断受理项目表中的审批来源是否为互联网+，并返回审批系统业务号
        BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (null != bdcSlJbxxDO) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                Integer xmywlx = bdcSlXmDOList.get(0).getXmywlx();
                if (CommonConstantUtils.SPLY_YHXT.equals(xmywlx)) {
                    return bdcSlXmDOList.get(0).getSpxtywh();
                }
            }
        }
        return spxtywh;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理权利人
     */
    private void dealYcslListQlrxx(List<BdcYcslxxVO> bdcYcslxxVOList){
        for (BdcYcslxxVO bdcYcslxxVO : bdcYcslxxVOList) {
            if(StringUtils.isNotBlank(bdcYcslxxVO.getXmid())){
                //权利人
                List<BdcSlSqrDO> qlrList =bdcSlSqrService.listBdcSlSqrByXmid(bdcYcslxxVO.getXmid(),CommonConstantUtils.QLRLB_QLR);
                if(CollectionUtils.isNotEmpty(qlrList)){
                    bdcYcslxxVO.setQlr(StringToolUtils.resolveBeanToAppendStr(qlrList, "getSqrmc", ","));
                    bdcYcslxxVO.setQlrfwtc(StringToolUtils.resolveBeanToAppendStr(qlrList, "getFwtc", ","));
                    bdcYcslxxVO.setQlrsbfwtc(StringToolUtils.resolveBeanToAppendStr(qlrList, "getSbfwtc", ","));
                }
                //义务人
                List<BdcSlSqrDO> ywrList =bdcSlSqrService.listBdcSlSqrByXmid(bdcYcslxxVO.getXmid(),CommonConstantUtils.QLRLB_YWR);
                if(CollectionUtils.isNotEmpty(ywrList)){
                    bdcYcslxxVO.setYwr(StringToolUtils.resolveBeanToAppendStr(ywrList, "getSqrmc", ","));
                    bdcYcslxxVO.setYwrfwtc(StringToolUtils.resolveBeanToAppendStr(ywrList, "getFwtc", ","));
                    bdcYcslxxVO.setYwrsbfwtc(StringToolUtils.resolveBeanToAppendStr(ywrList, "getSbfwtc", ","));
                }
            }
        }

    }



    /**
     * @param bdcYwxxDTO 业务信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转换业务数据中单个项目的受理基本信息
     */
    private BdcSlJbxxDO changeBdcYwxxParameterJbxx(BdcYwxxDTO bdcYwxxDTO,BdcSlJbxxDO bdcSlJbxxDO) {
        //项目表
        acceptDozerMapper.map(bdcYwxxDTO.getBdcXm(),bdcSlJbxxDO);
        return bdcSlJbxxDO;

    }

    /**
     * @param bdcYwxxDTO 业务信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转换业务数据中单个项目的受理项目信息
     */
    private BdcSlXmDO changeBdcYwxxParameterXmxx(BdcYwxxDTO bdcYwxxDTO,BdcSlXmDTO bdcSlXmDTO) {
        BdcSlXmDO bdcSlXmDO =bdcSlXmDTO.getBdcSlXm();

        //项目表
        acceptDozerMapper.map(bdcYwxxDTO.getBdcXm(),bdcSlXmDO);
        //项目附表
        if(bdcYwxxDTO.getBdcXmFb() != null){
            acceptDozerMapper.map(bdcYwxxDTO.getBdcXmFb(),bdcSlXmDO);
        }
        if(CheckWwsqOrYcslUtils.checkIsWwsq(bdcSlXmDO.getXmywlx())){
            bdcSlXmDO.setXmywlx(CommonConstantUtils.SPLY_WWSQ_YCSL);
        }else {
            bdcSlXmDO.setXmywlx(Constants.XMYWLX_YCSL);
        }
        //sffc赋值为空
        bdcSlXmDO.setSffc(CommonConstantUtils.SF_F_DM);
        return bdcSlXmDO;

    }

    /**
     * @param bdcYwxxDTO 业务信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  转换业务数据中单个项目的受理房屋信息
     */
    private BdcSlFwxxDO changeBdcYwxxParameterFwxx(BdcYwxxDTO bdcYwxxDTO,BdcSlXmDTO bdcSlXmDTO) {
        //权利表
        if(bdcYwxxDTO.getBdcQl() instanceof BdcFdcqDO) {
            BdcSlFwxxDO bdcSlFwxxDO =new BdcSlFwxxDO();
            List<BdcSlFwxxDO> bdcSlFwxxDOList =bdcSlFwxxService.listBdcSlFwxxByXmid(bdcYwxxDTO.getBdcXm().getXmid());
            if(CollectionUtils.isNotEmpty(bdcSlFwxxDOList)){
                bdcSlFwxxDO = bdcSlFwxxDOList.get(0);
            }
            //项目表
            acceptDozerMapper.map(bdcYwxxDTO.getBdcXm(),bdcSlFwxxDO);
            acceptDozerMapper.map(bdcYwxxDTO.getBdcQl(), bdcSlFwxxDO);
            //特殊取值
            //建筑年份截取竣工时间前4位,并判断是否可转换为数字
            if(StringUtils.isNotBlank(((BdcFdcqDO) bdcYwxxDTO.getBdcQl()).getJgsj()) &&((BdcFdcqDO) bdcYwxxDTO.getBdcQl()).getJgsj().length() >=4){
                String nf =StringUtils.substring(((BdcFdcqDO) bdcYwxxDTO.getBdcQl()).getJgsj(),0,4);
                if(NumberUtils.isNumber(nf) && !nf.contains(".")){
                    bdcSlFwxxDO.setJznf(Integer.parseInt(nf));
                }

            }
            if(bdcSlXmDTO.getBdcSlQl() instanceof BdcSlFwxxDO) {
                bdcSlFwxxDO.setFwlx(((BdcSlFwxxDO) bdcSlXmDTO.getBdcSlQl()).getFwlx());
            }
            if(StringUtils.isBlank(bdcSlFwxxDO.getFwxxid())){
                bdcSlFwxxDO.setFwxxid(UUIDGenerator.generate16());
            }

            // 常州特殊业务需求，小区名称获取房屋坐落信息
            if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(dataversion)){
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcYwxxDTO.getBdcQl();
                bdcSlFwxxDO.setXqmc(bdcFdcqDO.getZl());
            }
            return bdcSlFwxxDO;
        }
        return null;
    }

    /**
     * @param bdcYwxxDTO 业务信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  转换业务数据中单个项目的受理申请人信息
     */
    private List<BdcSlSqrDO> changeBdcYwxxParameterSqrxx(BdcYwxxDTO bdcYwxxDTO,BdcSlXmDTO bdcSlXmDTO) {
        List<BdcSlSqrDO> bdcSlSqrDOList =new ArrayList<>();
        List<BdcSlSqrDO> bdcSlQlrDOList =new ArrayList<>();
        List<BdcSlSqrDO> bdcSlYwrDOList =new ArrayList<>();
        //解析原有数据是否已存在买卖双方信息,存在以原有信息为准
        if(CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlSqrDTOList())){
            for(BdcSlSqrDTO bdcSlSqrDTO:bdcSlXmDTO.getBdcSlSqrDTOList()){
                if(bdcSlSqrDTO.getBdcSlSqrDO() != null &&StringUtils.isNotBlank(bdcSlSqrDTO.getBdcSlSqrDO().getSqrmc())){
                    BdcSlSqrDO bdcSlSqrDO =bdcSlSqrDTO.getBdcSlSqrDO();
                    //家庭满五唯一为否，套次自动赋值其他套
                    if(CommonConstantUtils.SF_F_DM.equals(bdcSlSqrDO.getJtmwwyzz())){
                        bdcSlSqrDO.setFwtc(CommonConstantUtils.FWTC_QT);
                    }
                    if(CommonConstantUtils.QLRLB_QLR.equals(bdcSlSqrDO.getSqrlb())){
                        bdcSlQlrDOList.add(bdcSlSqrDO);
                    }else if(CommonConstantUtils.QLRLB_YWR.equals(bdcSlSqrDO.getSqrlb())){
                        bdcSlYwrDOList.add(bdcSlSqrDO);
                    }
                    if(StringUtils.isBlank(bdcSlSqrDO.getSqrid())){
                        bdcSlSqrDO.setSqrid(UUIDGenerator.generate16());
                    }
                    bdcSlSqrDOList.add(bdcSlSqrDO);
                    //处理家庭成员
                    if(CollectionUtils.isNotEmpty(bdcSlSqrDTO.getBdcSlJtcyDOList())){
                        for(BdcSlJtcyDO bdcSlJtcyDO:bdcSlSqrDTO.getBdcSlJtcyDOList()){
                            bdcSlJtcyDO.setSqrid(bdcSlSqrDO.getSqrid());
                            if(StringUtils.isBlank(bdcSlJtcyDO.getJtcyid())){
                                bdcSlJtcyDO.setJtcyid(UUIDGenerator.generate16());
                            }
                        }
                        bdcSlJtcyService.saveBdcSlJtcyList(bdcSlSqrDTO.getBdcSlJtcyDOList());
                    }


                }
            }

        }else if(CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlSqrDOList())){
            for(BdcSlSqrDO bdcSlSqrDO:bdcSlXmDTO.getBdcSlSqrDOList()){
                if (StringUtils.isNotBlank(bdcSlSqrDO.getSqrmc())) {
                    //家庭满五唯一为否，套次自动赋值其他套
                    if(CommonConstantUtils.SF_F_DM.equals(bdcSlSqrDO.getJtmwwyzz())){
                        bdcSlSqrDO.setFwtc(CommonConstantUtils.FWTC_QT);
                    }
                    if(CommonConstantUtils.QLRLB_QLR.equals(bdcSlSqrDO.getSqrlb())){
                        bdcSlQlrDOList.add(bdcSlSqrDO);
                    }else if(CommonConstantUtils.QLRLB_YWR.equals(bdcSlSqrDO.getSqrlb())){
                        bdcSlYwrDOList.add(bdcSlSqrDO);
                    }
                    bdcSlSqrDOList.add(bdcSlSqrDO);

                }
            }
        }


        if(CollectionUtils.isNotEmpty(bdcYwxxDTO.getBdcQlrList())) {
            for(BdcQlrDO bdcQlrDO:bdcYwxxDTO.getBdcQlrList()) {
                if((CollectionUtils.isEmpty(bdcSlQlrDOList) &&CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb())) ||(CollectionUtils.isEmpty(bdcSlYwrDOList) &&CommonConstantUtils.QLRLB_YWR.equals(bdcQlrDO.getQlrlb()))) {
                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                    acceptDozerMapper.map(bdcQlrDO, bdcSlSqrDO);
                    bdcSlSqrDOList.add(bdcSlSqrDO);
                }
            }

        }

        return bdcSlSqrDOList;
    }

    /**
     * @param bdcYwxxDTO 业务信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  转换业务数据中单个项目的受理交易信息
     */
    private BdcSlJyxxDO changeBdcYwxxParameterJyxx(BdcYwxxDTO bdcYwxxDTO,BdcSlXmDTO bdcSlXmDTO) {
        BdcSlJyxxDO bdcSlJyxxDO =null;
        if(bdcYwxxDTO.getBdcQl() instanceof BdcFdcqDO ||bdcYwxxDTO.getBdcQl() instanceof BdcYgDO) {
            bdcSlJyxxDO = bdcSlXmDTO.getBdcSlJyxxDO();
            if (bdcSlJyxxDO == null) {
                bdcSlJyxxDO = new BdcSlJyxxDO();
            }
            //原项目表
            if (CollectionUtils.isNotEmpty(bdcYwxxDTO.getBdcXmLsgxList())) {
                for (BdcXmLsgxDO bdcXmLsgxDO : bdcYwxxDTO.getBdcXmLsgxList()) {
                    if (CommonConstantUtils.SF_F_DM.equals(bdcXmLsgxDO.getWlxm()) && StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setXmid(bdcXmLsgxDO.getYxmid());
                        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            acceptDozerMapper.map(bdcXmDOList.get(0), bdcSlJyxxDO);
                        }

                    }
                }
            }
            if (StringUtils.isBlank(bdcSlJyxxDO.getJyxxid())) {
                bdcSlJyxxDO.setJyxxid(UUIDGenerator.generate16());
            }
            if (bdcSlXmDTO.getBdcSlXm() != null) {
                bdcSlJyxxDO.setXmid(bdcSlXmDTO.getBdcSlXm().getXmid());
            }
            // 新增同步登记交易金额
            if (null == bdcSlJyxxDO.getJyje()) {
                BdcQl bdcQl = bdcYwxxDTO.getBdcQl();
                if (bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    bdcSlJyxxDO.setJyje(bdcFdcqDO.getJyjg());
                }
                if (bdcQl instanceof BdcYgDO) {
                    BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                    bdcSlJyxxDO.setJyje(bdcYgDO.getJyje());
                }
            }
        }




        return bdcSlJyxxDO;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理税务信息
     */
    private void changeBdcYwxxParameterSwxx(BdcSlXmDTO bdcSlXmDTO) {
        List<BdcSwxxDTO> bdcSwxxDTOList =bdcSlXmDTO.getBdcSwxxDTOList();
        if(CollectionUtils.isNotEmpty(bdcSwxxDTOList)){
            bdcSwService.saveOrUpdateSwxxDTO(bdcSwxxDTOList,bdcSlXmDTO.getBdcSlXm().getXmid());
        }


    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理抵押信息
     */
    private BdcSlQl changeBdcYwxxParameterDyaqxx(BdcYwxxDTO bdcYwxxDTO) {

        if(bdcYwxxDTO.getBdcQl() instanceof BdcDyaqDO) {
            BdcSlDyaqDO bdcSlDyaqDO =new BdcSlDyaqDO();
            //权利表
            acceptDozerMapper.map(bdcYwxxDTO.getBdcQl(), bdcSlDyaqDO);
            bdcSlDyaqDO.setId(UUIDGenerator.generate16());
            return bdcSlDyaqDO;
        }
        return null;

    }



    /**
     * @param bdcTsDjxxRequestDTO
     * @return WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  组织一窗创建登记流程请求对象
     */
    private WwsqCjBdcXmRequestDTO initWwsqCjBdcXmRequestDTO(BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO){
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO =new WwsqCjBdcXmRequestDTO();
        //组织受理信息
        BdcSlxxDTO bdcSlxxDTO;
        if(bdcTsDjxxRequestDTO.getBdcSlxxDTO() !=null &&CollectionUtils.isNotEmpty(bdcTsDjxxRequestDTO.getBdcSlxxDTO().getBdcSlXmList())){
            bdcSlxxDTO =bdcTsDjxxRequestDTO.getBdcSlxxDTO();

        }else {
            bdcSlxxDTO = bdcSlFeignService.queryBdcSlxx(bdcTsDjxxRequestDTO.getJbxxid());
        }
        if(bdcSlxxDTO != null &&bdcSlxxDTO.getBdcSlJbxx() != null) {
            String slbh = bdcSlxxDTO.getBdcSlJbxx().getSlbh();
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setSlbh(slbh);
            List<BdcXmDO> listXm = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(listXm)){
                throw new AppException("已创建登记流程，不可重复创建！");
            }
            //获取对应登记流程
            BdcSlYcslDjywDzbDO bdcSlYcslDjywDzbQO =new BdcSlYcslDjywDzbDO();
            bdcSlYcslDjywDzbQO.setYcslgzldyid(bdcSlxxDTO.getBdcSlJbxx().getGzldyid());
            BdcSlYcslDjywDzbDO bdcSlYcslDjywDzbDO =bdcSlYcslDjywDzbService.queryYcslDjywDzb(bdcSlYcslDjywDzbQO);
            if(bdcSlYcslDjywDzbDO ==null ||StringUtils.isBlank(bdcSlYcslDjywDzbDO.getDjgzldyid())){
                throw new AppException("未找到一窗流程与登记流程对照关系（BDC_SL_YCSL_DJYW_DZB）,请检查配置");
            }
            bdcSlxxDTO.getBdcSlJbxx().setGzldyid(bdcSlYcslDjywDzbDO.getDjgzldyid());
            //受理人登录名
            String slrdlm ="";
            if(StringUtils.isNotBlank(bdcTsDjxxRequestDTO.getSlrdlm())) {
                slrdlm =bdcTsDjxxRequestDTO.getSlrdlm();
            }else{
                slrdlm =userManagerUtils.getCurrentUserName();
            }
            LOGGER.info("一体化自动创建登记，slr:{}",slrdlm);
            bdcSlxxDTO.getBdcSlJbxx().setSlr(slrdlm);
            //受理编号处理
            if(bdcTsDjxxRequestDTO.getBdcYcslPzDTO() == null ||!bdcTsDjxxRequestDTO.getBdcYcslPzDTO().isGyslbh()){
                bdcSlxxDTO.getBdcSlJbxx().setSlbh("");
            }
            //补充第三方信息
            if(CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())){
                for(int i = 0 ; i < bdcSlxxDTO.getBdcSlXmList().size() ; i++) {
                    BdcSlXmDTO bdcSlXmDTO = bdcSlxxDTO.getBdcSlXmList().get(i);
                    DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                    acceptDozerMapper.map(bdcSlxxDTO.getBdcSlJbxx(), dsfSlxxDTO);
                    acceptDozerMapper.map(bdcSlXmDTO.getBdcSlXm(), dsfSlxxDTO);
                    if (bdcSlXmDTO.getBdcSlJyxxDO() != null) {
                        acceptDozerMapper.map(bdcSlXmDTO.getBdcSlJyxxDO(), dsfSlxxDTO);
                    }
                    //获取登记小类配置的顺序号
                    BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzService.queryBdcDjxlPzByGzldyidAndDjxl(bdcSlxxDTO.getBdcSlJbxx().getGzldyid(), bdcSlXmDTO.getBdcSlXm().getDjxl());
                    if (Objects.isNull(bdcDjxlPzDO)) {
                        throw new AppException("未获取到配置的登记小类信息，工作流定义id为：" + bdcSlxxDTO.getBdcSlJbxx().getGzldyid());
                    }
                    //组合流程顺序号处理
                    dsfSlxxDTO.setSxh(bdcDjxlPzDO.getSxh());
                    //处理项目历史关系,原项目ID为上一手的去掉
                    if (dsfSlxxDTO.getSxh() != null && dsfSlxxDTO.getSxh() > 1) {
                        if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlXmLsgxList())) {
                            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
                            for (BdcSlXmLsgxDO bdcSlXmLsgxDO : bdcSlXmDTO.getBdcSlXmLsgxList()) {
                                //组合流程取产权的，组合贷款两个抵押权，原xmid 为产权的
                                String yxmid = getYxmid(bdcSlxxDTO.getBdcSlXmList(), bdcSlXmLsgxDO.getYxmid());
                                if (StringUtils.isNotBlank(bdcSlXmLsgxDO.getYxmid())) {
                                    if (!StringUtils.equals(bdcSlXmLsgxDO.getYxmid(), yxmid)) {
                                        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                                    } else {
                                        dsfSlxxDTO.setGlYxm(true);
                                    }

                                }
                            }
                            bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);
                        }
                    }
                    bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
                    if (StringUtils.isNotBlank(bdcSlXmDTO.getBdcSlXm().getXmid()) && bdcSlXmDTO.getBdcSlXm().getQllx() != null) {
                        BdcSlQlxxService bdcSlQlxxService = InterfaceCodeBeanFactory.getBean(bdcSlQlxxServiceSet, bdcSlXmDTO.getBdcSlXm().getQllx().toString());
                        if (null != bdcSlQlxxService) {
                            BdcSlQl bdcSlQl = bdcSlQlxxService.queryBdcSlQl(bdcSlXmDTO.getBdcSlXm().getXmid());
                            bdcSlXmDTO.setBdcSlQl(bdcSlQl);
                        }

                        List<BdcSlLzrDO> bdcSlLzrDOList =bdcSlLzrService.listBdcSlLzrByXmid(bdcSlXmDTO.getBdcSlXm().getXmid());
                        if(CollectionUtils.isNotEmpty(bdcSlLzrDOList)){
                            bdcSlXmDTO.setBdcSlLzrDOList(bdcSlLzrDOList);
                        }
                    }

                }
            }
            wwsqCjBdcXmRequestDTO.setBdcSlxxDTO(bdcSlxxDTO);
        }
        //规则验证标识
        wwsqCjBdcXmRequestDTO.setGzyz(true);
        if(bdcTsDjxxRequestDTO.getBdcYcslPzDTO() != null) {
            wwsqCjBdcXmRequestDTO.setJrrllb(bdcTsDjxxRequestDTO.getBdcYcslPzDTO().isJrrllb());
        }
        return wwsqCjBdcXmRequestDTO;

    }

    /**
     * @param ycgzlslid 一窗工作流实例ID
     * @param djgzlslid 登记工作流实例ID
     * @return WwsqCjBdcXmRequestDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  处理收件材料信息
     */
    private List<BdcSlSjclDO> initSjcl(String ycgzlslid,String djgzlslid){
        LOGGER.info("一体化推送登记生成收件材料，ycgzlslid:{},djgzlslid:{}",ycgzlslid,djgzlslid);
        UserDto userDto = userManagerUtils.getCurrentUser();
        List<BdcSlSjclDO> djSlSjclList =new ArrayList<>();

        List<BdcSlSjclDO> bdcSlSjclDOList =bdcSlSjclService.listBdcSlSjclByGzlslid(ycgzlslid);
        if(CollectionUtils.isNotEmpty(bdcSlSjclDOList)){
            for(BdcSlSjclDO bdcSlSjclDO:bdcSlSjclDOList){
                if(StringUtils.isNotBlank(bdcSlSjclDO.getClmc())) {
                    BdcSlSjclDO slSjclDO = new BdcSlSjclDO();
                    BeanUtils.copyProperties(bdcSlSjclDO, slSjclDO);
                    StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, djgzlslid, bdcSlSjclDO.getClmc(), userDto != null ?userDto.getId() :"");
                    //查询文件夹下文件
                    List<StorageDto> list=storageClient.listStoragesByName("clientId",ycgzlslid,null,bdcSlSjclDO.getClmc(),1,0);
                    if(CollectionUtils.isNotEmpty(list)) {
                        List<StorageDto> listFile = storageClient.listAllSubsetStorages(list.get(0).getId(), StringUtils.EMPTY, 1, null,0,null);
                        if (CollectionUtils.isNotEmpty(listFile)) {
                            for (StorageDto storage : listFile) {
                                //下载
                                MultipartDto multipartDto = storageClient.download(storage.getId());
                                //上传到登记业务
                                multipartDto.setNodeId(storageDto.getId());
                                storageClient.multipartUpload(multipartDto);
                            }
                        }
                    }
                    slSjclDO.setSjclid(UUIDGenerator.generate16());
                    slSjclDO.setGzlslid(djgzlslid);
                    slSjclDO.setWjzxid(storageDto.getId());
                    djSlSjclList.add(slSjclDO);
                }
            }
        }
        LOGGER.info("登记新增收件材料{}",djSlSjclList);
        //批量新增
        bdcSlSjclService.insertBatchSjclList(djSlSjclList);
        return djSlSjclList;
    }





    // 给主房增加附记与建筑面积
    private void dealZfAndFsss(List<BdcSlFwxxDO> bdcSlFwxxDOList,List<BdcSlJyxxDO> bdcSlJyxxDOList, List<BdcSlXmDO> bdcSlXmDOList) {
        LOGGER.info("开始执行创建主+附的一窗流程");

        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        Map<String, BdcSlXmDO> bdcSlXmDOMap = new HashMap<>(bdcSlXmDOList.size());
        BdcSlFwxxDO mainFwxx = null;
        BdcSlJyxxDO mainJyxx = null;
        // 通过不动产项目表的是否主房来判断，获取到主房xmid
        for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
            bdcSlXmDOMap.put(bdcSlXmDO.getXmid(), bdcSlXmDO);
            // 判断是否主房
            if (CommonConstantUtils.SF_S_DM.equals(bdcSlXmDO.getSfzf())) {
                for (BdcSlFwxxDO bdcSlFwxxDO : bdcSlFwxxDOList) {
                    if (bdcSlFwxxDO.getXmid().equals(bdcSlXmDO.getXmid())) {
                        mainFwxx = bdcSlFwxxDO;
                    }
                }
                if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                    for (BdcSlJyxxDO bdcSlJyxxDO : bdcSlJyxxDOList) {
                        if (bdcSlJyxxDO.getXmid().equals(bdcSlXmDO.getXmid())) {
                            mainJyxx = bdcSlJyxxDO;
                        }
                    }
                }
            }
        }

        LOGGER.info("执行创建主+附的一窗流程,找到主房：{}",mainFwxx);

        if (Objects.nonNull(mainFwxx)) {
            for(BdcSlFwxxDO bdcSlFwxxDO : bdcSlFwxxDOList){
                if(!bdcSlFwxxDO.getFwxxid().equals(mainFwxx.getFwxxid())){
                    String fwytmc = StringToolUtils.convertBeanPropertyValueOfZd(bdcSlFwxxDO.getFwyt(), zdMap.get("fwyt"));
                    //将附房的建筑面积赋值到主房的车库面积,阁楼面积,地下室面积字段
                    if(StringUtils.isNotBlank(fwytmc)){
                        String ytmc = "";
                        Double jzmj = Optional.ofNullable(bdcSlFwxxDO.getJzmj()).orElse(0.0);
                        if(fwytmc.indexOf("车库")>-1 || fwytmc.indexOf("车位")>-1){
                            ytmc = "车库";
                            mainFwxx.setCkmj(countMj(mainFwxx.getCkmj(), jzmj));
                        }
                        if(fwytmc.indexOf("阁楼")>-1 ){
                            ytmc = "阁楼";
                            mainFwxx.setGlmj(countMj(mainFwxx.getGlmj(), jzmj));
                        }
                        if(fwytmc.indexOf("储藏室")>-1 || fwytmc.indexOf("地下室")>-1){
                            ytmc = "储藏室";
                            mainFwxx.setDxsmj(countMj(mainFwxx.getDxsmj(), jzmj));
                        }
                        String xmid = bdcSlFwxxDO.getXmid();
                        String bdcqzh = Optional.ofNullable(bdcSlXmDOMap.get(xmid).getYbdcqz()).orElse("");
                        String zl = Optional.ofNullable(bdcSlXmDOMap.get(xmid).getZl()).orElse("");
                        // ytmc的证明单为：bdcqzh，zl，ytmc的面积为jzmj平方米。
                        String generateFj = String.format(ZF_FJ_TEMPLATE, ytmc, bdcqzh, ytmc, zl, ytmc, jzmj);
                        LOGGER.info("附记内容为：{}",generateFj);
                        String fj = mainFwxx.getFj();
                        if(StringUtils.isNotBlank(fj) && fj.indexOf(generateFj) == -1){
                            mainFwxx.setFj(mainFwxx.getFj()+" "+generateFj);
                        }else{
                            mainFwxx.setFj(generateFj);
                        }
                        LOGGER.info("处理完毕主+附创建一窗流程后，主房的实体：{}",mainFwxx);
                        if(Objects.nonNull(mainJyxx)){
                            //将附房的交易信息整理到主房交易的备注中
                            for (BdcSlJyxxDO bdcSlJyxxDO : bdcSlJyxxDOList) {
                                if (bdcSlFwxxDO.getXmid().equals(bdcSlJyxxDO.getXmid()) &&StringUtils.isNotBlank(bdcSlJyxxDO.getHtbh())) {
                                    String htbasj ="";
                                    if(bdcSlJyxxDO.getHtbasj() != null) {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                                        htbasj = dateFormat.format(bdcSlJyxxDO.getHtbasj());
                                    }
                                    String generateBz = String.format(ZF_JYBZ_TEMPLATE, ytmc, bdcSlJyxxDO.getHtbh(), ytmc, zl, ytmc, htbasj);
                                    LOGGER.info("交易备注内容为：{}",generateBz);
                                    String bz = mainJyxx.getBz();
                                    if(StringUtils.isNotBlank(bz) && bz.indexOf(generateBz) == -1){
                                        mainJyxx.setBz(bz+" "+generateBz);
                                    }else{
                                        mainJyxx.setBz(generateBz);
                                    }
                                }
                            }

                        }
                    }
                }

            }
        }
    }

    private Double countMj(Double mj, Double jzmj){
        if(Objects.isNull(mj)){
            return jzmj;
        }
        return  BigDecimal.valueOf(mj).add(BigDecimal.valueOf(jzmj)).doubleValue();
    }

    /**
     * @param bdcSlXmDTOList
     * @param yxmid
     * @return java.lang.String
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取逻辑，根据yxmid查找当前流程的项目id，如果相等，则说明有上一手
     */
    private String getYxmid(List<BdcSlXmDTO> bdcSlXmDTOList, String yxmid) {
        String yid = "";
        if (CollectionUtils.isNotEmpty(bdcSlXmDTOList)) {
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlXmDTOList) {
                if (StringUtils.equals(bdcSlXmDTO.getBdcSlXm().getXmid(), yxmid)) {
                    yid = bdcSlXmDTO.getBdcSlXm().getXmid();
                    break;
                }
            }
        }
        return yid;
    }

}
