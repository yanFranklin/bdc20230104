package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlPjqDO;
import cn.gtmap.realestate.common.core.dto.accept.SlymPjqDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: realestate
 * @description: 评价器调用
 * @author: <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @create: 2019-08-06 14:57
 **/
@Controller
@RequestMapping("/pjq")
public class BdcPjqController extends BaseController {

    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlPjqFeignService bdcSlPjqFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 推送评价结果至省厅
     * @param slymPjqDTO 评价器所需参数
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @return 评价结果
     */
    @ResponseBody
    @PostMapping("/saveAndTspjjg")
    public Object tsPjjg(@RequestBody SlymPjqDTO slymPjqDTO) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slymPjqDTO.getSlbh());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException("未获取到不动产项目信息");
        }
        slymPjqDTO.setBdcXmDO(bdcXmDOList.get(0));
        // 设置受理人
        UserDto userDto = userManagerUtils.getCurrentUser();
        // 设置区县代码
        slymPjqDTO.setSlr(userDto.getAlias());
        if(CollectionUtils.isNotEmpty(userDto.getOrgRecordList())){
            slymPjqDTO.setQxdm(userDto.getOrgRecordList().get(0).getRegionCode());
        }
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listAllBdcQlr(bdcXmDOList.get(0).getGzlslid(), CommonConstantUtils.QLRLB_QLR, "");
        //同时根据名称和证件号去重
        List<BdcQlrDO> bdcQlrDOS = bdcQlrDOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQlrmc() + ";" + o.getZjh()))), ArrayList::new));
        if(CollectionUtils.isNotEmpty(bdcQlrDOS)){
            slymPjqDTO.setQlrmc(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getQlrmc", ","));
            slymPjqDTO.setQlrlxfs(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOS, "getDh", ","));
        }
        if(StringUtils.isNotEmpty(slymPjqDTO.getGzlslid())){
            List<TaskData> processRunningTasks = processTaskClient.processRunningTasks(slymPjqDTO.getGzlslid());
            if (CollectionUtils.isNotEmpty(processRunningTasks)) {
                slymPjqDTO.setJdmc(processRunningTasks.get(0).getTaskName());
            }
        }
        slymPjqDTO.setPjsj(new Date());

        // 推送评价结果至省厅
        Object response = exchangeInterfaceFeignService.requestInterface("send_pjxx_to_sf", slymPjqDTO);
        // 保存评价结果信息
        this.bdcSlPjqFeignService.insertBdcSlPjq(convertPjqjg(slymPjqDTO, userDto));
        return response;
    }

    private BdcSlPjqDO convertPjqjg(SlymPjqDTO slymPjqDTO, UserDto userDto){
        BdcSlPjqDO bdcSlPjqDO = new BdcSlPjqDO();
        BeanUtils.copyProperties(slymPjqDTO, bdcSlPjqDO);
        bdcSlPjqDO.setYwbh(slymPjqDTO.getSlbh());
        bdcSlPjqDO.setBlry(userDto.getAlias());
        bdcSlPjqDO.setBlryid(userDto.getUsername());
        BdcXmDO bdcXmDO = slymPjqDTO.getBdcXmDO();
        bdcSlPjqDO.setYwmc(bdcXmDO.getGzldymc());
        bdcSlPjqDO.setYwblsj(bdcXmDO.getSlsj());
        bdcSlPjqDO.setDjbmdm(bdcXmDO.getDjbmdm());
        bdcSlPjqDO.setDjbmmc(bdcXmDO.getDjjg());
        bdcSlPjqDO.setSqrxm(slymPjqDTO.getQlrmc());
        bdcSlPjqDO.setSqrlxfs(slymPjqDTO.getQlrlxfs());
        return bdcSlPjqDO;
    }


}

