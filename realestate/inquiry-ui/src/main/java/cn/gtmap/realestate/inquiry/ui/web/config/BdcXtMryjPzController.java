package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtMryjPzFeignService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcXtMryjPzVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/29
 * @description 默认意见配置
 */
@RestController
@RequestMapping("/rest/v1.0/mryj")
public class BdcXtMryjPzController extends BaseController {
    /**
     * 默认意见配置服务
     */
    @Autowired
    BdcXtMryjPzFeignService bdcXtMryjPzFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    WorkFlowUtils workFlowUtils;

    /**
     * @param pageable
     * @param bdcXtMryjDO
     * @return 默认意见分页数据
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/page")
    public Object listBdcXtMryj(@LayuiPageable Pageable pageable, BdcXtMryjDO bdcXtMryjDO) {
        String mryjParamJson = JSON.toJSONString(bdcXtMryjDO);
        Page<BdcXtMryjPzVO> bdcXtMryjDOPage = bdcXtMryjPzFeignService.listBdcXtMryj(pageable, mryjParamJson);
        bdcXtMryjDOPage.getContent().forEach(mryj -> {
            UserDto userDto = userManagerUtils.getUserByUserid(mryj.getCjrid());
            if (userDto != null) {
                mryj.setCjrmc(userDto.getUsername());
            }
        });
        return super.addLayUiCode(bdcXtMryjDOPage);
    }

    /**
     * @param bdcXtMryjDO
     * @return 新增的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增不动产默认意见
     */
    @PutMapping
    public Object saveBdcXtMryj(@RequestBody BdcXtMryjDO bdcXtMryjDO) {
        if(bdcXtMryjDO==null){
            throw new AppException("保存的数据不能为空！");
        }
        if(bdcXtMryjDO.getSfky()==null){
            bdcXtMryjDO.setSfky(CommonConstantUtils.SF_F_DM);
        }
        /**
         * 数据类型 为sql 情况 转换 括号
         */
        if(bdcXtMryjDO.getSjlx()==1 && StringUtils.isNotBlank(bdcXtMryjDO.getYj())){
            bdcXtMryjDO.setYj(StringToolUtils.replaceBracket(bdcXtMryjDO.getYj()));
        }
        if(StringUtils.isBlank(bdcXtMryjDO.getGzldymc())){
            ProcessDefData processDefData=workFlowUtils.getAllProcessDefDataByGzldyid(bdcXtMryjDO.getGzldyid());
            if(processDefData!=null){
                bdcXtMryjDO.setGzldymc(processDefData.getName());
            }
        }
        return bdcXtMryjPzFeignService.saveBdcXtMryj(bdcXtMryjDO);
    }

    /**
     * @param bdcXtMryjDO
     * @return 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改不动产默认意见
     */
    @PostMapping
    public Object updateBdcXtMryj(@RequestBody BdcXtMryjDO bdcXtMryjDO) {
        if(bdcXtMryjDO==null){
            throw new AppException("保存的数据不能为空！");
        }
        if(bdcXtMryjDO.getSfky()==null){
            bdcXtMryjDO.setSfky(CommonConstantUtils.SF_F_DM);
        }
        /**
         * 数据类型 为sql 情况 转换 括号
         */
        if(bdcXtMryjDO.getSjlx()==1 && StringUtils.isNotBlank(bdcXtMryjDO.getYj())){
            bdcXtMryjDO.setYj(StringToolUtils.replaceBracket(bdcXtMryjDO.getYj()));
        }
        if(StringUtils.isBlank(bdcXtMryjDO.getGzldymc())){
            ProcessDefData processDefData=workFlowUtils.getAllProcessDefDataByGzldyid(bdcXtMryjDO.getGzldyid());
            if(processDefData!=null){
                bdcXtMryjDO.setGzldymc(processDefData.getName());
            }
        }
        return bdcXtMryjPzFeignService.updateBdcXtMryj(bdcXtMryjDO);
    }

    /**
     * @param bdcXtMryjDOList
     * @return 删除的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除默认意见配置
     */
    @DeleteMapping
    public Object deleteBdcXtMryj(@RequestBody List<BdcXtMryjDO> bdcXtMryjDOList) {
        return bdcXtMryjPzFeignService.deleteBdcXtMryj(bdcXtMryjDOList);
    }

    /**
     * @param
     * @return 工作流信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取工作流信息
     */
    @GetMapping("/gzldymcs")
    public Object queryAllProcessDefData() {
        List<ProcessDefData> processDefDataList=workFlowUtils.getAllProcessDefData();
        processDefDataList=processDefDataList.stream().filter(processDefData ->
            processDefData.getSuspensionState()==1
        ).collect(Collectors.toList());
        return processDefDataList;
    }

    /**
     * @param processDefKey 工作流定义key
     * @return 节点信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取节点信息
     */
    @GetMapping("/jdmcs")
    public Object queryUserTasks(String processDefKey) {
        if (StringUtils.isBlank(processDefKey)) {
            throw new MissingArgumentException("processDefKey,缺少流程定义Key");
        }
        return workFlowUtils.getUserTasks(processDefKey);
    }

    /**
     * @param cjrmc
     * @return cjrid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取创建人id
     */
    @GetMapping("/cjr")
    public Object queryUserId(String cjrmc) {
        String cjrId = null;
        UserDto userDto = userManagerUtils.getUserByName(cjrmc);
        if (userDto != null) {
            cjrId = userDto.getId();
        }
        return cjrId;
    }

}
