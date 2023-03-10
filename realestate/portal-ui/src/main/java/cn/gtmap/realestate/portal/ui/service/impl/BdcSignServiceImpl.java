package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXtMryjFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.portal.ui.core.dto.WorkFlowDTO;
import cn.gtmap.realestate.portal.ui.service.BdcSignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BdcSignServiceImpl implements BdcSignService {
    @Autowired
    private WorkFlowUtils workFlowUtils;
    @Autowired
    private BdcXtMryjFeignService bdcXtMryjFeignService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private BdcShxxFeignService bdcShxxFeignService;
    @Autowired
    private UserManagerUtils userManagerUtils;

    @Override
    public boolean signCheck(List<WorkFlowDTO> workFlowDTOList) {
        UserDto currentUser = userManagerUtils.getCurrentUser();
        return this.signWithUser(workFlowDTOList, currentUser);
    }

    @Override
    public boolean signWithUser(List<WorkFlowDTO> workFlowDTOList, UserDto userDto) {
        if (CollectionUtils.isEmpty(workFlowDTOList) && userDto != null) {
            throw new MissingArgumentException("???????????????????????????");
        }
        boolean signJd = true;
        for (WorkFlowDTO workFlowDTO : workFlowDTOList) {
            if (StringUtils.isAnyBlank(workFlowDTO.getProcessInstanceId(), workFlowDTO.getTaskName(),
                    workFlowDTO.getProcessDefKey(), workFlowDTO.getTaskId())) {
                throw new MissingArgumentException("??????????????????????????????????????? jdmc???gzlslid???gzldyid???taskid ???????????????");
            }
            List<UserTaskDto> list = workFlowUtils.listShjdxx(workFlowDTO.getProcessInstanceId());
            if (CollectionUtils.isEmpty(list)) {
                signJd = false;
                break;
            }
            //????????????
            List<UserTaskDto> signList = list.stream().filter(
                    userTaskDto -> StringUtils.equals(userTaskDto.getName(), workFlowDTO.getTaskName())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(signList)) {
                signJd = false;
                break;
            }
        }
        //????????????????????????
        if (signJd) {
            for (WorkFlowDTO workFlowDTO : workFlowDTOList) {
                BdcXtMryjDO bdcXtMryjDO = bdcXtMryjFeignService.queryMryj(null, workFlowDTO.getProcessDefKey(), workFlowDTO.getTaskName());
                String signId = storageClient.userSign(userDto.getUsername());
                if (StringUtils.isEmpty(signId)) {
                    throw new AppException("????????????id??????");
                }
                BdcShxxQO bdcShxxQO = new BdcShxxQO();
                bdcShxxQO.setShxxid(workFlowDTO.getTaskId());
                List<BdcShxxDO> bdcShxxDOS = bdcShxxFeignService.queryBdcShxx(bdcShxxQO);
                // ??????????????????????????????????????????????????????
                if (CollectionUtils.isNotEmpty(bdcShxxDOS) && StringUtils.isNotBlank(bdcShxxDOS.get(0).getQmid())
                        && StringUtils.isNotBlank(bdcShxxDOS.get(0).getShyj()) && bdcShxxDOS.get(0).getQmsj() != null) {
                    continue;
                }

                BdcShxxDO bdcShxxDO = new BdcShxxDO();
                bdcShxxDO.setGzlslid(workFlowDTO.getProcessInstanceId());
                bdcShxxDO.setShxxid(workFlowDTO.getTaskId());
                bdcShxxDO.setJdmc(workFlowDTO.getTaskName());
                //????????????????????????????????? sql
                if (null != bdcXtMryjDO && StringUtils.isNotBlank(bdcXtMryjDO.getYj())) {
                    if (CommonConstantUtils.MRYJ_SJLX_SQL.equals(bdcXtMryjDO.getSjlx())) {
                        String[] sqlArr = StringUtils.split(bdcXtMryjDO.getYj(), CommonConstantUtils.ZF_YW_FH);
                        if (sqlArr.length > 0 && StringUtils.isNotBlank(sqlArr[0])) {
                            // ???????????????sql
                            bdcXtMryjDO.setYj(sqlArr[0]);
                            bdcShxxDO.setShyj(bdcShxxFeignService.generateMryjBySql(workFlowDTO.getProcessInstanceId(), bdcXtMryjDO));
                        }
                    } else {
                        bdcShxxDO.setShyj(bdcXtMryjDO.getYj());
                    }
                }
                // ?????????????????????????????????shxxid?????????taskId??????????????????????????????????????????
                bdcShxxDO.setShryxm(userDto.getUsername());
                bdcShxxDO.setShryid(userDto.getId());
                bdcShxxDO.setQmid(signId);
                bdcShxxDO.setQmsj(new Date());
                bdcShxxFeignService.saveOrUpdateBdcShxx(bdcShxxDO);
            }
        }
        return signJd;
    }
}
