package cn.gtmap.realestate.portal.ui.service;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;

import java.util.List;
import java.util.Map;

/**
 * 流程任务服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/28.
 * @description
 */
public interface ProcessService {

    /**
     * 获取新建任务列表
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param userDto
     *@param processDefName
     *@return
     *@description
     */
    Map<String,Object>  listCategoryProcess(UserDto userDto,String processDefName);

    /**
     * 获取自然资源新建任务列表
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param userDto
     *@param processDefName
     *@return
     *@description
     */
    Map<String,Object>  listZrzyCategoryProcess(UserDto userDto,String processDefName);

    /**
     * 获取流程列表
     *
     * @param userDto
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    public List<ProcessDefData> listAllProcess(UserDto userDto);

}
