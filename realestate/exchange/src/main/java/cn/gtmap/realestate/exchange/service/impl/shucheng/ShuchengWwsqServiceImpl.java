package cn.gtmap.realestate.exchange.service.impl.shucheng;

import cn.gtmap.gtc.workflow.clients.manage.FlowableNodeClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessInstanceClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclmxDTO;
import cn.gtmap.realestate.common.core.dto.portal.WorkFlowCommonDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.portal.BdcWorkFlowFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.QueryQlRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.common.YgQlWithXmQlrDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.ygdy.InitYgdyData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.ygdy.InitYgdyRequestDTO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.FjclService;
import cn.gtmap.realestate.exchange.util.BeanUtil;
import cn.gtmap.realestate.exchange.util.DozerUtil;
import cn.gtmap.realestate.exchange.util.Sm4Util;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.stream.Collectors;

@Service
public class ShuchengWwsqServiceImpl {
    private static Logger LOGGER = LoggerFactory.getLogger(ShuChengSqdServiceImpl.class);

    @Value("${wwsq.ygdj.sm4key:8f47e3a699b7409ca59d6fe1655dd3b3}")
    private String sm4key;

    @Value("${wwsq.ygdj.gzldyid:A4Tp3CAXWNNEbFTH}")
    private String gzldyid;

    @Value("${slRoleCode.sfcz:false}")
    private Boolean slRoleCode;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper exchangeDozerMapper;

    @Autowired
    FwYcHsFeignService fwYcHsFeignService;

    @Autowired
    private CommonService commonService;

    @Autowired
    FlowableNodeClient flowableNodeClient;

    @Autowired
    BdcWorkFlowFeignService bdcWorkFlowFeignService;

    @Autowired
    ProcessInstanceClient processInstanceClient;

    @Autowired
    FjclService fjclService;

    @Autowired
    BdcYwsjHxFeignService bdcYwsjHxFeignService;

    @Autowired
    BdcSlFeignService bdcSlFeignService;

    public Object wwsqCjBdcXmBb(JSONObject jsonObject) {
        if (Objects.nonNull(jsonObject)) {
            try {
                InitYgdyData initYgdyData = JSON.parseObject(JSON.toJSONString(jsonObject), InitYgdyData.class);
                if (CollectionUtils.isNotEmpty(initYgdyData.getQlrs())) {
                    List<InitYgdyData.Qlrs> ywrs = initYgdyData
                            .getQlrs()
                            .stream()
                            .filter(qlrs -> CommonConstantUtils.QLRLB_YWR.equals(qlrs.getQlrlx()))
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(ywrs)) {
                        initYgdyData.setYwrs(ywrs);
                    }
                }
                //实际的对象
                InitYgdyRequestDTO initYgdyRequestDTO = new InitYgdyRequestDTO();
                initYgdyRequestDTO.setContent(Arrays.asList(initYgdyData));

                //转换对象
                return cjxm(initYgdyRequestDTO);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("外网申请预告请求服务内容，错误{}", e.getMessage());
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("yxcode", -1);
                resultMap.put("yxmsg", e.getMessage());
                return resultMap;
            }
        }
        return null;
    }

    public Object wwsqCjBdcXm(JSONObject jsonObject) {
        if (Objects.nonNull(jsonObject)) {
            //解密
            try {
                String contentString = new String(Sm4Util.decrypt(
                        Base64.decodeBase64(jsonObject.getString("content").getBytes(StandardCharsets.UTF_8.toString())),
                        sm4key),
                        StandardCharsets.UTF_8);
                LOGGER.error("外网申请预告请求服务内容，解密后{}", contentString);
                InitYgdyData initYgdyData = JSON.parseObject(contentString, InitYgdyData.class);
                List<InitYgdyData.Qlrs> qlrsList = new ArrayList<>();
                InitYgdyData.Qlrs qlrs = new InitYgdyData.Qlrs();
                qlrs.setQlrmc(initYgdyData.getYwr());
                qlrs.setZjh(initYgdyData.getYwrzjh());
                qlrs.setZjzl(initYgdyData.getYwrzjzl());
                qlrsList.add(qlrs);
                initYgdyData.setYwrs(qlrsList);
                jsonObject.put("content", initYgdyData);
                //实际的对象
                InitYgdyRequestDTO initYgdyRequestDTO = JSON.parseObject(JSON.toJSONString(jsonObject),
                        InitYgdyRequestDTO.class);

                //转换对象
                return cjxm(initYgdyRequestDTO);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("外网申请预告请求服务内容，解密后错误{},密文内容{}", e.getMessage(), JSON.toJSONString(jsonObject));
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("yxcode", -1);
                resultMap.put("yxmsg", e.getMessage());
                return resultMap;
            }
        }
        return null;
    }


    private Map<String, Object> cjxm(InitYgdyRequestDTO initYgdyRequestDTO) throws Exception {
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        wwsqCjBdcXmRequestDTO.setGzyz(true);
        wwsqCjBdcXmRequestDTO.setSfss(false);
        wwsqCjBdcXmRequestDTO.setSfzdzfdb("0");
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        wwsqCjBdcXmRequestDTO.setBdcSlxxDTO(bdcSlxxDTO);
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        exchangeDozerMapper.map(initYgdyRequestDTO, bdcSlJbxxDO, "initWwsqxxYgdjSljbxx");
        bdcSlJbxxDO.setGzldyid(gzldyid);
        BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
        exchangeDozerMapper.map(initYgdyRequestDTO, bdcSlXmDTO, "initWwsqxxYgdjSlxmXX");
        //bdcSlXmDTO.getBdcSlXm().setBdcdyh("340102274002GB00036F00340093");
        //附件材料处理
        List<FjclDTOForZhlc> hbfjxx = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(initYgdyRequestDTO.getImgs())) {
            Map<String, List<InitYgdyRequestDTO.ImageFj>> imageFjGroup = initYgdyRequestDTO.getImgs()
                    .stream()
                    .collect(Collectors.groupingBy(InitYgdyRequestDTO.ImageFj::getSCredname));
            for (Map.Entry<String, List<InitYgdyRequestDTO.ImageFj>> stringListEntry : imageFjGroup.entrySet()) {
                List<InitYgdyRequestDTO.ImageFj> imageFjs = stringListEntry.getValue();
                FjclDTOForZhlc fjclDTOForZhlc = new FjclDTOForZhlc();
                fjclDTOForZhlc.setClmc(imageFjs.get(0).getSCredname());
                fjclDTOForZhlc.setFjlx("1");
                fjclDTOForZhlc.setYs(1);
                fjclDTOForZhlc.setFs(imageFjs.size());
                List<FjclmxDTO> clnr = new ArrayList<>();
                for (InitYgdyRequestDTO.ImageFj imageFj : imageFjs) {
                    FjclmxDTO fjclmxDTO = new FjclmxDTO();
                    fjclmxDTO.setFjnr(imageFj.getSBase64());
                    if (clnr.size() >= 1) {
                        fjclmxDTO.setFjmc(imageFj.getSCredname() + clnr.size() + "." + imageFj.getSFileFormatType());
                    } else {
                        fjclmxDTO.setFjmc(imageFj.getSCredname() + "." + imageFj.getSFileFormatType());
                    }
                    clnr.add(fjclmxDTO);
                }
                fjclDTOForZhlc.setClnr(clnr);
                hbfjxx.add(fjclDTOForZhlc);
            }
        }
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        bdcSlxxDTO.setBdcSlXmList(Arrays.asList(bdcSlXmDTO));
        bdcSlxxDTO.setHbfjxx(hbfjxx);
        if (slRoleCode) {
            wwsqCjBdcXmRequestDTO.setSlRoleCode("wwsqsl");
        }
        WwsqCjBdcXmResponseDTO wwsqCjBdcXmResponseDTO = bdcSlFeignService.wwsqCjBdcXm(wwsqCjBdcXmRequestDTO);
        return fcwRevertCjResponse(wwsqCjBdcXmResponseDTO,
                hbfjxx,
                wwsqCjBdcXmRequestDTO);
    }

    /**
     * 外网申请创建结果
     *
     * @param responseDTO
     * @param fjclList
     * @param wwsqCjBdcXmRequestDTO
     * @return
     */
    public Map<String, Object> fcwRevertCjResponse(WwsqCjBdcXmResponseDTO responseDTO,
                                                   List<FjclDTOForZhlc> fjclList,
                                                   WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) {
        // 验证不通过的处理
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getGzyzList())) {
            StringBuilder msgSb = new StringBuilder();
            List<Map<String, Object>> yzResult = responseDTO.getGzyzList();
            for (Map<String, Object> map : yzResult) {
                String bdcdyh = StringUtils.defaultString(MapUtils.getString(map, "bdcdyh"));
                String yzmsg = StringUtils.defaultString(MapUtils.getString(map, "msg"));
                msgSb.append(bdcdyh).append(yzmsg).append(",");
            }
            String msg = msgSb.toString();
            if (msg.endsWith(",")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("yxcode", -1);
            resultMap.put("yxmsg", msg);
            return resultMap;
        }
        // 异步上传附件
        if (CollectionUtils.isNotEmpty(fjclList)) {
            List<FjclDTO> fjclDTOList = new ArrayList<>();
            for (FjclDTOForZhlc fjclDTOForZhlc : fjclList) {
                FjclDTO fjclDTO = new FjclDTO();
                BeanUtils.copyProperties(fjclDTOForZhlc, fjclDTO);
                fjclDTO.setFjclmxDTOList(fjclDTOForZhlc.getClnr());
                fjclDTOList.add(fjclDTO);
            }
            fjclService.asynUploadAndSaveFjcl(responseDTO, fjclDTOList);
        }
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getBdcXmDOList())) {
            // 回写大云平台
            try {
                Map<String, Object> paramMap = new HashMap<>();
                String gzlslid = responseDTO.getBdcXmDOList().get(0).getGzlslid();
                // 预约分中心，开始时间，结束时间
                DsfSlxxDTO dsfSlxxDTO = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlXmList().get(0).getDsfSlxxDTO();
                Date yykssj = dsfSlxxDTO.getYykssj();
                Date yyjssj = dsfSlxxDTO.getYyjssj();
                String yyfzx = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlJbxx().getQxdm();
                if (StringUtils.isNotBlank(yyfzx) || null != yykssj || null != yyjssj) {
                    paramMap.put("YYKSSJ", dsfSlxxDTO.getYykssj());
                    paramMap.put("YYJSSJ", dsfSlxxDTO.getYyjssj());
                    paramMap.put("YYFZX", yyfzx);
                }
                // 是否自动转发登簿
                if (StringUtils.isNotBlank(wwsqCjBdcXmRequestDTO.getSfzdzfdb())) {
                    paramMap.put("SFZDZFDB", wwsqCjBdcXmRequestDTO.getSfzdzfdb());
                }
                //缴费状态
                List<BdcSfxxDTO> bdcSfxxDTOS = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO().getBdcSlXmList().get(0).getBdcSfxxDTOList();
                if (CollectionUtils.isNotEmpty(bdcSfxxDTOS) && bdcSfxxDTOS.get(0).getBdcSlSfxxDO() != null) {
                    Integer sfzt = bdcSfxxDTOS.get(0).getBdcSlSfxxDO().getSfzt();
                    if (sfzt != null) {
                        paramMap.put("JFZT", sfzt);
                    }
                }
                // 执行回写
                if (MapUtils.isNotEmpty(paramMap)) {
                    LOGGER.info("大云回写字段异常参数,paramMap:{}", JSON.toJSONString(paramMap));
                    bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, paramMap);
                }
            } catch (Exception e) {
                LOGGER.error("大云回写字段异常,slbh:{}", responseDTO.getBdcXmDOList().get(0).getSlbh(), e);
            }
            Map<String, Object> resultMap = new HashMap<>();
            Map<String, Object> yxdata = new HashMap<>();
            resultMap.put("yxcode", 200);
            resultMap.put("yxmsg", "成功");
            yxdata.put("ywh", responseDTO.getBdcXmDOList().get(0).getSlbh());
            resultMap.put("yxdata", yxdata);

            return resultMap;
        } else {
            throw new AppException("没有生成登记项目");
        }
    }

    /**
     * 预告流程核验,返回ychs数据，判断是否做过预告
     *
     * @param jsonObject
     * @return
     */
    public JSONObject yglchy(JSONObject jsonObject) {
        JSONObject response = new JSONObject();
        String ysfwbm = jsonObject.getString("ysfwbm");
        if (StringUtils.isBlank(ysfwbm)) {
            throw new MissingArgumentException("缺少查询参数");
        }
        //查询房屋预测信息
        List<FwYchsDO> fwYchsDOS = fwYcHsFeignService.listFwYchsByYsfwbm(ysfwbm);
        if (CollectionUtils.isNotEmpty(fwYchsDOS)) {
            FwYchsDO fwYchsDO = fwYchsDOS.get(0);
            response.put("bdcdyh", fwYchsDO.getBdcdyh());
            response.put("ysfwbm", fwYchsDO.getYsfwbm());
            response.put("ycjzmj", fwYchsDO.getYcjzmj());
            response.put("ghyt", fwYchsDO.getGhyt());
            response.put("fwxz", fwYchsDO.getFwxz());
            response.put("zl", fwYchsDO.getZl());
            QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
            queryQlRequestDTO.setBdcdyh(fwYchsDO.getBdcdyh());
            queryQlRequestDTO.setQszt(Constants.QSZT_XS + "");
            queryQlRequestDTO.setWithXm(false);
            List<YgQlWithXmQlrDTO> ygList = commonService.listYgByBdcdyh(queryQlRequestDTO);
            if (CollectionUtils.isNotEmpty(ygList)) {
                response.put("sfyg", 1);
            } else {
                response.put("sfyg", 0);
            }
        } else {
            response.put("bdcdyh", "");
            response.put("ysfwbm", "");
            response.put("ycjzmj", "");
            response.put("ghyt", "");
            response.put("fwxz", "");
            response.put("zl", "");
            response.put("sfyg", 0);
        }
        JSONObject result = new JSONObject();
        result.put("yxcode", 200);
        result.put("message", "成功");
        result.put("data", response);
        return result;
    }

    /**
     * 撤销
     *
     * @param jsonObject
     * @return
     */
    public JSONObject yglchcx(JSONObject jsonObject) {
        JSONObject response = new JSONObject();
        response.put("code", 200);
        try {
            String contentString = new String(Sm4Util.decrypt(
                    Base64.decodeBase64(jsonObject.getString("content").getBytes(StandardCharsets.UTF_8.toString())),
                    sm4key),
                    StandardCharsets.UTF_8);
            LOGGER.error("外网申请预告请求服务内容，解密后{}", contentString);
            //请求的真实内容
            JSONObject requestContent = JSON.parseObject(contentString);
            String ywh = requestContent.getString("ywh");
            if (StringUtils.isBlank(ywh)) {
                LOGGER.error("外网申请预告请求服务内容，解密后错误未获取到业务号,密文内容{}", contentString);
            }
            //检测是否可以撤销
            if (checkSlzt(ywh)) {
                response.put("code", -1);
                response.put("yxmsg", "任务已被认领不能删除");
                return response;
            }
            List<BdcXmDO> bdcXmDOList = commonService.listBdcXmBySlbh(ywh);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                response.put("code", -1);
                response.put("yxmsg", "删除异常");
                return response;
            }
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            // 获取工作流定义 id 对比判断是否属于 特殊流程，直接执行大云删除方法。
            ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(bdcXmDO.getGzlslid());
            if (processInstanceData != null) {
                try {
                    WorkFlowCommonDTO workFlowCommonDTO = new WorkFlowCommonDTO();
                    workFlowCommonDTO.setProcessInstanceId(bdcXmDO.getGzlslid());
                    workFlowCommonDTO.setReason("外网申请删除");
                    List<WorkFlowCommonDTO> workFlowCommonDTOS = new ArrayList<>(1);
                    workFlowCommonDTOS.add(workFlowCommonDTO);
                    bdcWorkFlowFeignService.feignDeleteTask(workFlowCommonDTOS);
                    response.put("yxmsg", "成功");
                } catch (Exception e) {
                    response.put("code", -1);
                    response.put("yxmsg", "删除异常");
                    LOGGER.error("删除登记数据错误:{}", bdcXmDO.getGzlslid(), e);
                }
            } else {
                response.put("code", -1);
                response.put("yxmsg", "工作流实例为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", -1);
            response.put("yxmsg", "请求参数错误");
            LOGGER.error("外网申请预告请求服务内容，解密后错误{},密文内容{}", e.getMessage(), JSON.toJSONString(jsonObject));
        }
        return response;
    }


    /**
     * @param ywh
     * @return boolea
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 验证是否处在受理节点并无受理人：false 否则：true
     */
    private boolean checkSlzt(String ywh) {
        // 查询登簿状态
        List<BdcXmDO> bdcXmDOList = commonService.listBdcXmBySlbh(ywh);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            boolean issljd = flowableNodeClient.isStartUserTaskRunning(bdcXmDOList.get(0).getGzlslid());
            if (!issljd) {
                return true;
            }
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    if (StringUtils.isNotBlank(bdcXmDO.getSlr())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
