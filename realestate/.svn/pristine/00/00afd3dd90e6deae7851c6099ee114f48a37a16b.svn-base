package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.moke.MokeZzdzjPlszDTO;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.moke.MokeZzdzjPlszExtendDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.hefei.moke.MokeHxyzhRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.shucheng.BdcZzdzRequestScDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcZzdzZmcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZzdzZmxxResponseDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHstFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcXmFbRestService;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZzdzRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.core.entity.YhLqrDO;
import cn.gtmap.realestate.inquiry.service.BdcZzdzService;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/10/23
 * @description 权利信息接口
 */
@RestController
@Api(tags = "查询可供自助打证机打印的证书证明服务")
public class BdcZzdzRestController implements BdcZzdzRestService {
    @Value("${zzdz.roleName:}")
    private String roleName;
    /**
     * 用户操作
     */
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcZzdzService bdcZzdzService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcXmFbRestService bdcXmFbRestService;
    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private BdcXtJgFeignService bdcXtJgFeignService;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Value("${zzdz.excludeGzldyid:}")
    private String excludeGzldyid;

    @Value("${zzdz.excludeGzldyidSc:}")
    private String excludeGzldyidSc;

    @Value("${zzdz.kdzjdmc:登簿制证}")
    private String kdzjdmc;
    @Value("${zzdzj.version:hefei}")
    private String zzdzjVersion;

    //南通大市只在缮证节点返回数据，其他节点不返回
    @Value("${zzdzj.zzszfhsj:false}")
    private Boolean ffsjIfSz;

    /**
     * 配置证书二维码后缀为zsid
     */
    @Value("${mokeEwm.url:}")
    private String mokeEwmUrl;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;


    @Autowired
    FwHstFeignService fwHstFeignService;

    @Autowired
    ZdFeignService zdFeignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZzdzRestController.class);

    /**
     * @param bdcZzdzRequestDTO
     * @return List<BdcZszmDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 权利信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("自助打证接口(合肥)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZzdzRequestDTO", value = "第三方接口入参dto", required = false,
            paramType = "query")})
    public List<BdcZzdzResponseDTO> getBdcZzdz(@RequestBody BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        if (StringUtils.isEmpty(bdcZzdzRequestDTO.getQlrzjh())
                || bdcZzdzRequestDTO.getZslx() == null) {
            throw new MissingArgumentException("缺失参数！");
        }
        List<BdcZzdzResponseDTO> list = bdcZzdzService.getBdcZzdz(bdcZzdzRequestDTO);
        List<BdcZzdzResponseDTO> resultList = new ArrayList<>();
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        if (CollectionUtils.isNotEmpty(list)) {
            LOGGER.info("自主打证查询返回值数量：{}", list.size());
            LOGGER.info("配置的节点名称：{}", kdzjdmc);
            for (int i = 0; i < list.size(); i++) {
                String gzlslid = list.get(i).getGzlslid();
                List<TaskData> listTask = processTaskClient.processRunningTasks(gzlslid);
                if (CollectionUtils.isNotEmpty(listTask)) {
                    LOGGER.info("通过gzlslid查询的节点结合：{}", JSONObject.toJSONString(listTask));
                    for (TaskData taskData : listTask) {
                        String taskName = taskData.getTaskName();
                        LOGGER.info("当前节点名称：{}", taskName);
                        if (StringUtils.isNotBlank(taskName) && kdzjdmc.indexOf(taskName) > -1) {
                            BdcZzdzResponseDTO bdcZzdzResponseDTO = list.get(i);
                            translateResult(bdcZzdzResponseDTO, zdMap);
                            resultList.add(bdcZzdzResponseDTO);
                        }
                    }
                }

            }
        }
        return resultList;
    }


    /**
     * @param bdcZzdzRequestDTO
     * @return List<BdcZszmDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 自助打证回写印制号接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("自助打证回写印制号接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZzdzRequestDTO", value = "第三方接口入参dto", required = false,
            paramType = "query")})
    public Integer reWriteYzhToBdcZs(@RequestBody BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        if (CollectionUtils.isEmpty(bdcZzdzRequestDTO.getBdczsidYzhGxDto())) {
            throw new MissingArgumentException("缺失参数！");
        }
        int count = 0;
        if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ, zzdzjVersion)) {
            //常州特殊逻辑
            count = bdcZzdzService.reWriteYzhToBdcZsForChangZhou(bdcZzdzRequestDTO);
        } else {
            count = bdcZzdzService.reWriteYzhToBdcZs(bdcZzdzRequestDTO);
        }
        return count;
    }

    /**********************以下为常州自助打证机接口，只转发不办结*********************/
    /**
     * @param bdcZzdzRequestDTO
     * @return List<BdcZszmDTO>
     * @author <a href="mailto:huangjian @gtmap.cn">huangjian</a>
     * @description 回写印制号_不办结！只转发到下个节点
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("回写印制号_不办结！只转发到下个节点")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZzdzRequestDTO", value = "第三方接口入参dto", required = false,
            paramType = "query")})
    public Integer reWriteYzhToBdcZsForward(@RequestBody BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        if (CollectionUtils.isEmpty(bdcZzdzRequestDTO.getBdczsidYzhGxDto())) {
            throw new MissingArgumentException("缺失参数！");
        }
        int count = 0;

        //常州特殊逻辑
        count = bdcZzdzService.reWriteYzhToBdcZsForward(bdcZzdzRequestDTO);

        return count;
    }


    /**********************以上为合肥自助打证接口，以下为南通『盐城』自助打证接口*********************/

    /**
     * @param bdcZzdzNtRequestDTO
     * @return BdcZzdzNtResponseDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 自助查询证书信息 『与盐城共用』
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("自助打证接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZzdzNtRequestDTO", value = "第三方接口入参dto", required = false,
            paramType = "query")})
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/nt")
    public BdcZzdzNtResponseDTO getBdcZzdzNt(@RequestBody BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO) {
        // 配置了 roleName 接口入参需要额外加上 username
        if (StringUtils.isNotBlank(roleName) && StringUtils.isEmpty(bdcZzdzNtRequestDTO.getZzjUsername())) {
            throw new MissingArgumentException("缺少自助机用户名！");
        }
        BdcZzdzNtResponseDTO authority = checkUserRole(bdcZzdzNtRequestDTO.getZzjUsername());
        if (authority != null) {
            return authority;
        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getIdNo())) {
            throw new MissingArgumentException("缺少操作人证件号！");
        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getZzjId())) {
            throw new MissingArgumentException("缺少自助机id！");
        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getTranstionId())) {
            throw new MissingArgumentException("缺少登记系统业务流水号！");
        }
        BdcZzdzNtResponseDTO dto = new BdcZzdzNtResponseDTO();

        // 判断当前流程的认领人是否是自助打证机用户
        String slbh = bdcZzdzNtRequestDTO.getTranstionId();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        List<BdcXmDO> listxm = bdcXmFeignService.listBdcXm(bdcXmQO);
        // 项目操作
        if (CollectionUtils.isNotEmpty(listxm)) {
            // 排除不能打证的流程
            String gzldyid = listxm.get(0).getGzldyid();
            if (StringUtils.isNotEmpty(excludeGzldyid)) {
                // 不能打证的流程
                if (excludeGzldyid.indexOf(gzldyid) != -1) {
                    dto.setMessage("此业务请到窗口领证！");
                    dto.setSuccess(false);
                    dto.setStatusCode(1);
                    dto.setData(null);
                    return dto;
                }
            }
            String gzlslid = listxm.get(0).getGzlslid();
            List<TaskData> listTask = processTaskClient.listProcessTask(gzlslid);
            if (CollectionUtils.isNotEmpty(listTask)) {
                LOGGER.info("查询当前流程的listtask：{}", listTask);
                // 南通大市只在缮证节点返回数据，其他节点不返回
                if (ffsjIfSz) {
                    // 所有正在进行的任务节点没有缮证节点
                    Boolean noHasSzjj = listTask.stream().noneMatch(item -> CommonConstantUtils.ZZDZ_SZ.equals(item.getTaskName()));
                    if (noHasSzjj) {
                        dto.setMessage("审批没到缮证节点，无法获取相关数据！");
                        dto.setSuccess(false);
                        dto.setStatusCode(1);
                        dto.setData(null);
                        return dto;
                    }
                    for (BdcXmDO bdcXmDO : listxm) {
                        //查询权利人名称
                        BdcQlrQO bdcQlrQO = new BdcQlrQO();
                        bdcQlrQO.setXmid(bdcXmDO.getXmid());
                        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                        List<BdcQlrDO> qlrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        if (CollectionUtils.isNotEmpty(qlrDOS)) {
                            for (BdcQlrDO bdcQlrDO : qlrDOS) {
                                //查询机构信息
                                BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcQlrDO.getQlrmc(),null);
                                if (StringUtils.isNotBlank(bdcXtJgDO.getJgid()) && bdcXtJgDO.getSfdz().equals(CommonConstantUtils.SF_F_DM)) {
                                    dto.setMessage("该银行已接入互联网，禁止打印纸质证明！");
                                    dto.setSuccess(false);
                                    dto.setStatusCode(1);
                                    dto.setData(null);
                                    return dto;
                                }
                            }
                        }
                    }


                }
                for (int j = 0; j < listTask.size(); j++) {
                    // 缮证节点
                    if (CommonConstantUtils.ZZDZ_SZ.equals(listTask.get(j).getTaskName())) {
                        LOGGER.info("缮证节点的任务信息：{}", listTask.get(j));
                        // 判断缮证节点的用户不为空并且不是 zzdz 角色
                        if (StringUtils.isNotEmpty(listTask.get(j).getTaskAssigin()) && !isZzdzj(listTask.get(j).getTaskAssigin())) {
                            dto.setMessage("缮证节点认领人错误，请到窗口确认认领情况！");
                            dto.setSuccess(false);
                            dto.setStatusCode(1);
                            dto.setData(null);
                            Date todat_date = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            dto.setQqsj(simpleDateFormat.format(todat_date));
                            return dto;
                        }
                    }
                }
            }
        }
        dto.setMessage("操作失败");
        dto.setSuccess(false);
        dto.setStatusCode(1);
        dto.setData(null);
        //查询数据
        List<BdcZzdzNtResultDTO> list = bdcZzdzService.getBdcZzdzNt(bdcZzdzNtRequestDTO);
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        List<BdcZzdzNtResultDTO> listRes = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                // 领证人证件号和权利人证件号过滤
                // 当输入的证件号 匹配了权利人的zjh或者领证人的zjh,则可以打证
                if ((StringUtils.isNotEmpty(list.get(i).getLZRZJH()) && list.get(i).getLZRZJH().contains(bdcZzdzNtRequestDTO.getIdNo()))
                        || ((StringUtils.isNotEmpty(list.get(i).getQLRZJH()) && list.get(i).getQLRZJH().contains
                        (bdcZzdzNtRequestDTO.getIdNo())))) {
                    // 循环调用收费接口，查询收费状态
                    List<BdcSlSfxxDO> listSfxx = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(list.get(i).getWIID());
                    list.get(i).setSFZT(String.valueOf(BdcSfztEnum.YJF.key()));
                    if (CollectionUtils.isNotEmpty(listSfxx)) {
                        for (int j = 0; j < listSfxx.size(); j++) {
                            if (!BdcSfztEnum.YJF.key().equals(listSfxx.get(j).getSfzt())) {
                                list.get(i).setSFZT(String.valueOf(BdcSfztEnum.WJF.key()));
                                break;
                            }
                        }
                    } else {
                        //南通要求，查不到收费信息，sfzt设置为已缴费
                        list.get(i).setSFZT(String.valueOf(BdcSfztEnum.YJF.key()));
                    }
                    translateResult(list.get(i), zdMap);

                    String bdcdyh = list.get(i).getBDCDYH();
                    String qjgldm = list.get(i).getQJGLDM();
                    if (StringUtils.isNotBlank(bdcdyh)) {
                        LOGGER.info("自主打证机查询户室图bdcdyh,qjgldm:{},{}", bdcdyh,qjgldm);
                        //获取户室图
                        String hstBase64 = fwHstFeignService.queryFwHstBase64ByBdcdyh(bdcdyh, qjgldm);
                        if (StringUtils.isNotBlank(hstBase64)) {
                            list.get(i).setHST(hstBase64);
                        }
                        //获取宗地图
                        ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcdyh, qjgldm);
                        if (Objects.nonNull(zdtResponseDTO) && StringUtils.isNotBlank(zdtResponseDTO.getBase64())) {
                            list.get(i).setZDT(zdtResponseDTO.getBase64());
                        }
                    }
                    listRes.add(list.get(i));
                }
            }
            dto.setMessage("操作成功");
            dto.setSuccess(true);
            dto.setStatusCode(0);
            dto.setData(listRes);
        } else {
            Date todat_date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dto.setQqsj(simpleDateFormat.format(todat_date));
            return dto;
        }
        // 当没有用lzrzjh过滤的时候能查出数据
        // 但是过滤后 返回值为空，则说明出的数据全部不匹配lzrzjh
        if (CollectionUtils.isNotEmpty(list) && CollectionUtils.isEmpty(listRes)) {
            dto.setMessage("您不是领证人 ，请使用领证人（权利人）身份证查询!");
            dto.setSuccess(false);
            dto.setStatusCode(1);
            dto.setData(null);
        }

        Date todat_date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setQqsj(simpleDateFormat.format(todat_date));
        return dto;
    }

    /**
     * 检测当前用户是否有权限
     * <strong>ps: 如果不配置 zzdz.roleName 则默认按照 zzdzj 用户处理</strong>
     *
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcZzdzNtResponseDTO
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private BdcZzdzNtResponseDTO checkUserRole(String username) {
        // 判断是否需要鉴权，未配置默认用户为 zzdzj 处理
        if (StringUtils.isBlank(roleName)) {
            return null;
        }
        UserDto currentUser = userManagerUtils.getUserByName(username);
        if (currentUser == null) {
            LOGGER.error("当前用户：{} 不存在", username);
            BdcZzdzNtResponseDTO dto = new BdcZzdzNtResponseDTO();
            dto.setMessage("操作失败，zzjUsername 不存在对应用户！");
            dto.setSuccess(false);
            dto.setStatusCode(0);
            return dto;
        }
        if (!userManagerUtils.hasRoleByUserIdAndRoleName(currentUser.getId(), roleName)) {
            LOGGER.error("当前用户：{}， 不属于：{}", currentUser.getUsername(), roleName);
            BdcZzdzNtResponseDTO dto = new BdcZzdzNtResponseDTO();
            dto.setMessage("操作失败，当前用户无权操作！");
            dto.setSuccess(false);
            dto.setStatusCode(0);
            return dto;
        }
        return null;
    }

    /**
     * 判断任务操作人 是否是 自助打证机 的角色
     * <strong>ps: 如果不配置 zzdz.roleName 则默认按照 zzdzj 用户处理</strong>
     *
     * @param taskAssigin taskAssigin
     * @return boolean true 表示是，false 表示否
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private boolean isZzdzj(String taskAssigin) {
        // 判断是否需要鉴权，未配置默认用户为 zzdzj 处理
        if (StringUtils.isBlank(roleName)) {
            return Constants.ZZDZ_USERNAME.equals(taskAssigin);
        }
        UserDto userDto = userManagerUtils.getUserByName(taskAssigin);
        List<RoleDto> roleRecordList = userDto.getRoleRecordList();
        if (CollectionUtils.isNotEmpty(roleRecordList)) {
            for (RoleDto role : roleRecordList) {
                if (role != null && StringUtils.equals(role.getName(), roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param bdcZzdzNtRequestDTO
     * @return BdcZzdzNtResponseDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 权利信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("自助打证回写印制号接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZzdzNtRequestDTO", value = "第三方接口入参dto", required = false,
            paramType = "query")})
    public BdcZzdzNtResponseDTO reWriteYzhToBdcZsNt(@RequestBody BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO) {
        // 配置了 roleName 接口入参需要额外加上 username
        if (StringUtils.isNotBlank(roleName) && StringUtils.isEmpty(bdcZzdzNtRequestDTO.getZzjUsername())) {
            throw new MissingArgumentException("缺少自助机用户名！");
        }
        BdcZzdzNtResponseDTO authority = checkUserRole(bdcZzdzNtRequestDTO.getZzjUsername());
        if (authority != null){
            return authority;
        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getZsid())) {
            throw new MissingArgumentException("缺少证书id！");
        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getLzrzjh())) {
            throw new MissingArgumentException("缺少领证人证件号！");
        }
//        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getZsh())) {
            throw new MissingArgumentException("缺少证书号！");
        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getTranstionId())) {
            throw new MissingArgumentException("缺少登记系统业务流水号！");
        }

        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getFzrq())) {
            throw new MissingArgumentException("缺少发证日期！");
        }
        BdcZzdzNtResponseDTO resDto = bdcZzdzService.reWriteYzhToBdcZsNt(bdcZzdzNtRequestDTO);
        return resDto;
    }

    /**
     * @param bdcZzdzNtRequestDTO
     * @return BdcZzdzNtResponseDTO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 权利信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("自助打证回写印制号接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZzdzNtRequestDTO", value = "第三方接口入参dto", required = false,
            paramType = "query")})
    public BdcZzdzNtResponseDTO reWriteYzhToBdcZsYc(@RequestBody BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO) {
        // 配置了 roleName 接口入参需要额外加上 username
        if (StringUtils.isNotBlank(roleName) && StringUtils.isEmpty(bdcZzdzNtRequestDTO.getZzjUsername())) {
            throw new MissingArgumentException("缺少自助机用户名！");
        }
        BdcZzdzNtResponseDTO authority = checkUserRole(bdcZzdzNtRequestDTO.getZzjUsername());
        if (authority != null) {
            return authority;
        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getZsid())) {
            throw new MissingArgumentException("缺少证书id！");
        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getLzrzjh())) {
            throw new MissingArgumentException("缺少领证人证件号！");
        }
//        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getZsh())) {
            throw new MissingArgumentException("缺少证书号！");
        }
        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getTranstionId())) {
            throw new MissingArgumentException("缺少登记系统业务流水号！");
        }

        if (StringUtils.isEmpty(bdcZzdzNtRequestDTO.getFzrq())) {
            throw new MissingArgumentException("缺少发证日期！");
        }
        BdcZzdzNtResponseDTO resDto = bdcZzdzService.reWriteYzhToBdcZsYc(bdcZzdzNtRequestDTO);
        return resDto;
    }

    /**
     * @param bdcZzdzRequestDTO
     * @return 更新数量
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 回写印制号, 不做自动办结的操作
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("自助打证回写印制号接口，只回写印制，不自动办结")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZzdzRequestDTO", value = "第三方接口入参dto", required = false,
            paramType = "query")})
    public Integer reWriteYzhToBdcZsForStandard(BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        if (CollectionUtils.isEmpty(bdcZzdzRequestDTO.getBdczsidYzhGxDto())) {
            throw new MissingArgumentException("缺失参数！");
        }

        int count = bdcZzdzService.reWriteYzhToBdcZsForStandard(bdcZzdzRequestDTO);
        return count;
    }

    /**
     * @param bdcZzdzRequestDTO
     * @return 更新数量
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 回写印制号
     */
    @Override
    public CommonResponse zdbjForStandard(BdcZzdzRequestDTO bdcZzdzRequestDTO) {
        if (CollectionUtils.isEmpty(bdcZzdzRequestDTO.getBdczsidYzhGxDto())) {
            throw new MissingArgumentException("缺失参数！");
        }

        return bdcZzdzService.zdbjForZzdzj(bdcZzdzRequestDTO);
    }


    /**
     * 翻译一些字典项
     *
     * @param obj
     * @param zdMap
     */
    private void translateResult(Object obj, Map<String, List<Map>> zdMap) {
        BdcZzdzResponseDTO bdcZzdzResponseDTO = null;
        BdcZzdzNtResultDTO bdcZzdzNtResultDTO = null;
        if (obj instanceof BdcZzdzResponseDTO) {
            bdcZzdzResponseDTO = (BdcZzdzResponseDTO) obj;
        }
        if (obj instanceof BdcZzdzNtResultDTO) {
            bdcZzdzNtResultDTO = (BdcZzdzNtResultDTO) obj;
        }
        // 合肥返回实体翻译
        if (null != bdcZzdzResponseDTO) {
            // 翻译不动产类型
            if (bdcZzdzResponseDTO.getBdclx() != null) {
                bdcZzdzResponseDTO.setBdclxmc(StringToolUtils.convertBeanPropertyValueOfZd(
                        bdcZzdzResponseDTO.getBdclx(), zdMap.get(Constants.BDCLX)));
            }
            // 翻译证书类型
            if (bdcZzdzResponseDTO.getZslx() != null) {
                bdcZzdzResponseDTO.setZslxmc(StringToolUtils.convertBeanPropertyValueOfZd(
                        bdcZzdzResponseDTO.getZslx(), zdMap.get(Constants.ZSLX)));
            }
            // 翻译规划用途
            /*if(bdcZzdzResponseDTO.getGhyt()!=null){
                bdcZzdzResponseDTO.setGhytmc(StringToolUtils.convertBeanPropertyValueOfZd(
                        bdcZzdzResponseDTO.getGhyt(), zdMap.get(Constants.FWYT)));
            }*/
            if (bdcZzdzResponseDTO.getQszt() != null) {
                bdcZzdzResponseDTO.setQsztmc(StringToolUtils.convertBeanPropertyValueOfZd(
                        bdcZzdzResponseDTO.getQszt(), zdMap.get(Constants.QSZT)));
            }
            // 翻译权利类型
            if (bdcZzdzResponseDTO.getQllx() != null) {
                bdcZzdzResponseDTO.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(
                        bdcZzdzResponseDTO.getQllx(), zdMap.get(Constants.QLLX)));
            }

            // 翻译共有方式
            if (bdcZzdzResponseDTO.getGyfs() != null) {
                bdcZzdzResponseDTO.setGyqk(StringToolUtils.convertBeanPropertyValueOfZd(
                        bdcZzdzResponseDTO.getGyfs(), zdMap.get(Constants.GYFS)));
            }
        }

        // 南通返回实体翻译
        if (null != bdcZzdzNtResultDTO) {
            if (bdcZzdzNtResultDTO.getZSLX() != null) {
                bdcZzdzNtResultDTO.setZSTYPE(StringToolUtils.convertBeanPropertyValueOfZd(
                        bdcZzdzNtResultDTO.getZSLX(), zdMap.get(Constants.ZSLX)));
            }
            // 翻译权利类型
            if (bdcZzdzNtResultDTO.getQLLX() != null) {
                bdcZzdzNtResultDTO.setQLLX(StringToolUtils.convertBeanPropertyValueOfZd(
                        Integer.valueOf(bdcZzdzNtResultDTO.getQLLX()), zdMap.get(Constants.QLLX)));
            }
            // 翻译登记类型
            if (bdcZzdzNtResultDTO.getDJLX() != null) {
                bdcZzdzNtResultDTO.setDJLX(StringToolUtils.convertBeanPropertyValueOfZd(
                        Integer.valueOf(bdcZzdzNtResultDTO.getDJLX()), zdMap.get(Constants.DJLX)));
            }
        }
    }

    /**
     * @param bdcZzdzRequestScDTO
     * @return List<BdcZszmDTO>
     * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 权利信息   注：回写印制号用合肥的方法回写
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("自助打证接口(舒城)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZzdzRequestScDTO", value = "第三方接口入参dto", required = false,
            paramType = "query")})
    public List<BdcZzdzResponseDTO> getBdcZzdzSc(@RequestBody BdcZzdzRequestScDTO bdcZzdzRequestScDTO) {
        List<BdcZzdzResponseDTO> list = new ArrayList<>();
        //个人查，直接查
        if (StringUtils.isNotBlank(bdcZzdzRequestScDTO.getXm()) && StringUtils.isNotBlank(bdcZzdzRequestScDTO.getSfzh())) {
            list = bdcZzdzService.getBdcZzdzSc(bdcZzdzRequestScDTO);
        }
        //领取人代表银行，查银行的信息，带入权利人中查
        if (StringUtils.isNotBlank(bdcZzdzRequestScDTO.getLqrxm()) && StringUtils.isNotBlank(bdcZzdzRequestScDTO.getLqrzjh())) {
            YhLqrDO yhLqrDO = new YhLqrDO();
            yhLqrDO.setLqrxm(bdcZzdzRequestScDTO.getLqrxm());
            yhLqrDO.setLqrzjh(bdcZzdzRequestScDTO.getLqrzjh());
            YhLqrDO yhLqrDO1 = bdcZzdzService.getYhBylqr(yhLqrDO);
            if (null != yhLqrDO1) {
                bdcZzdzRequestScDTO.setLqrxm(yhLqrDO1.getYhmc());
                bdcZzdzRequestScDTO.setLqrzjh(yhLqrDO1.getYhzjh());
            } else {
                LOGGER.info("找不到该领取人对应的银行名称");
                return new ArrayList<>();
            }
            list = bdcZzdzService.getBdcZzdzSc(bdcZzdzRequestScDTO);

        }
        List<BdcZzdzResponseDTO> resultList = new ArrayList<>();
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        if (CollectionUtils.isNotEmpty(list)) {
            LOGGER.info("代理人证件号： {} ,自主打证查询返回值数量：{}", bdcZzdzRequestScDTO.getSfzh(), list.size());
            LOGGER.info("代理人证件号： {} ,配置的节点名称：{}", bdcZzdzRequestScDTO.getSfzh(), kdzjdmc);
            for (int i = 0; i < list.size(); i++) {
                String gzlslid = list.get(i).getGzlslid();
                List<TaskData> listTask = processTaskClient.processRunningTasks(gzlslid);
                if (CollectionUtils.isNotEmpty(listTask)) {
                    LOGGER.info("代理人证件号： {} ,通过gzlslid查询的节点结合：{}", bdcZzdzRequestScDTO.getSfzh(), JSONObject.toJSONString(listTask));
                    for (TaskData taskData : listTask) {
                        String taskName = taskData.getTaskName();
                        LOGGER.info("代理人证件号： {} ,当前节点名称：{}", bdcZzdzRequestScDTO.getSfzh(), taskName);
                        if (StringUtils.isNotBlank(taskName) && kdzjdmc.indexOf(taskName) > -1) {
                            BdcZzdzResponseDTO bdcZzdzResponseDTO = list.get(i);
                            translateResult(bdcZzdzResponseDTO, zdMap);
                            resultList.add(bdcZzdzResponseDTO);
                        }
                    }
                }

            }
        }
        return resultList;
    }

    /**
     * @param hxyzhRequestDTO
     * @return 更新数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 合肥摩科回写印制号_此处为摩科新增专用
     */
    @Override
    public BdcCommonResponse mokeRewriteYzhToBdcZs(@RequestBody MokeHxyzhRequestDTO hxyzhRequestDTO) {
        if (null == hxyzhRequestDTO || StringUtils.isBlank(hxyzhRequestDTO.getBDCQZH())
                || StringUtils.isBlank(hxyzhRequestDTO.getQZYSXLH())
                || StringUtils.isBlank(hxyzhRequestDTO.getYWBH())
                || StringUtils.isBlank(hxyzhRequestDTO.getZSLX())) {
            return BdcCommonResponse.fail("400", "缺少参数！！");
        }

        return bdcZzdzService.mokeRewriteYzhToBdcZs(hxyzhRequestDTO);


    }

    /**
     * @param hxyzhRequestDTO@return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 合肥摩科查询打证信息接口
     */
    @Override
    public BdcCommonResponse mokeQueryDzxx(@RequestBody MokeHxyzhRequestDTO hxyzhRequestDTO) {
        if (null == hxyzhRequestDTO || StringUtils.isBlank(hxyzhRequestDTO.getYWBH())
                || StringUtils.isBlank(hxyzhRequestDTO.getZJHM())
                || StringUtils.isBlank(hxyzhRequestDTO.getZZJID())) {
            return BdcCommonResponse.fail("0", "缺少参数！！");
        }
        List<MokeZzdzjPlszExtendDTO> list = bdcZzdzService.mokeQueryDzxx(hxyzhRequestDTO);
        List<MokeZzdzjPlszDTO> resultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            LOGGER.info("mk自主打证查询返回值数量：{}", list.size());
            LOGGER.info("配置的节点名称：{}", kdzjdmc);
            for (int i = 0; i < list.size(); i++) {
                String gzlslid = list.get(i).getGzlslid();
                List<TaskData> listTask = processTaskClient.processRunningTasks(gzlslid);
                if (CollectionUtils.isNotEmpty(listTask)) {
                    LOGGER.info("通过gzlslid查询的节点结合：{}", JSONObject.toJSONString(listTask));
                    for (TaskData taskData : listTask) {
                        String taskName = taskData.getTaskName();
                        LOGGER.info("当前节点名称：{}", taskName);
                        if (StringUtils.isNotBlank(taskName) && kdzjdmc.indexOf(taskName) > -1) {
                            MokeZzdzjPlszDTO mokeZzdzjPlszDTO = list.get(i);
                            if (StringUtils.isNotBlank(mokeEwmUrl)) {
                                mokeZzdzjPlszDTO.setZDT(mokeEwmUrl + list.get(i).getZsid());
                                mokeZzdzjPlszDTO.setFHT(mokeEwmUrl + list.get(i).getZsid());
                            }
                            resultList.add(mokeZzdzjPlszDTO);
                        }
                    }
                }

            }
        }
        return BdcCommonResponse.ok(resultList);

    }


    /**
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 合肥摩科验证能否打证接口
     */
    @Override
    public BdcCommonResponse mokeVerifyDzxx(@RequestBody MokeHxyzhRequestDTO hxyzhRequestDTO) {
        if (null != hxyzhRequestDTO && StringUtils.isBlank(hxyzhRequestDTO.getBDCQZH())) {
            return BdcCommonResponse.fail("0", "缺少BDCQZH参数！！");
        }

        //根据证号查询证书表信息
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setBdcqzh(hxyzhRequestDTO.getBDCQZH());
        List<BdcZsDO> zsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        //判断查到的印制号是否为空，为空返回验证成功，可以打证，不为空则不能打证
        if (CollectionUtils.isNotEmpty(zsDOList)) {
            if (StringUtils.isBlank(zsDOList.get(0).getQzysxlh())) {
                return BdcCommonResponse.ok("该证书印制号为空！");
            } else {
                return BdcCommonResponse.fail("该证书印制号不为空");
            }
        }
        return BdcCommonResponse.fail("未查到该证书！");

    }

    @Override
    public BdcZzdzZmxxResponseDTO mokeQueryDjzmxx(@RequestBody BdcZzdzZmcxRequestDTO bdcZzdzZmcxRequestDTO){
        return bdcZzdzService.mokeQueryDjzmxx(bdcZzdzZmcxRequestDTO);

    }
}
