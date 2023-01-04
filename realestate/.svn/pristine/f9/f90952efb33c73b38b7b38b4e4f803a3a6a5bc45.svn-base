package cn.gtmap.realestate.exchange.service.impl.inf.ycsl;

import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CheckWwsqOrYcslUtils;
import cn.gtmap.realestate.exchange.core.dto.ycsl.tjqq.request.YcslTjqqRequestBody;
import cn.gtmap.realestate.exchange.core.dto.ycsl.tjqq.request.YcslTjqqRequestData;
import cn.gtmap.realestate.exchange.service.inf.ycsl.YcslTjqqService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class YcslTjqqServiceImpl implements YcslTjqqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YcslTjqqServiceImpl.class);
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private ProcessTaskClient processTaskClient;

    @Autowired
    private UserManagerClient userManagerClient;

    @Override
    public YcslTjqqRequestBody getTjqq(Map map) {
        YcslTjqqRequestBody ycslTjqqRequestBody = new YcslTjqqRequestBody();
        YcslTjqqRequestData ycslTjqqRequestData = new YcslTjqqRequestData();
        String taskId = MapUtils.getString(map, "taskId");
        String gzlslid = MapUtils.getString(map, "processInsId");
        String opinion = MapUtils.getString(map, "opinion");
        if(StringUtil.isBlank(opinion)){
            opinion = "退回";
        }
        LOGGER.info("退件推送一窗接口 processInsId:{},taskId:{},opinion:{}", gzlslid,taskId,opinion);
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例id为空");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO temp = bdcXmDOList.get(0);
            // 判断为一窗受理的 业务才给一窗推送
            if(CheckWwsqOrYcslUtils.checkIsYcsl(temp.getSply())){
                ycslTjqqRequestData.setYwh(temp.getSpxtywh());
            }
        }

        if(StringUtil.isNotEmpty(ycslTjqqRequestData.getYwh())){
            StringBuilder shyjSb = new StringBuilder("");
            // 如果审核意见为空 执行退回操作
            // 取节点办理人
            try{
                TaskData taskData = processTaskClient.getTaskById(taskId);
                if(taskData != null){
                    LOGGER.info("没有退件实体，查询任务实体:{}", JSONObject.toJSONString(taskData));
                    String userName = taskData.getTaskAssigin();
                    UserDto userDto = userManagerClient.getUserDetailByUsername(userName);
                    if(userDto != null){
                        shyjSb.append(userDto.getAlias()).append(opinion);
                    }
                }
                // 保存审核意见
                ycslTjqqRequestData.setShyj(shyjSb.toString());
                LOGGER.info("退件推送一窗接口 processInsId:{},taskId:{},,opinion:{},requestData:{}",
                        gzlslid,taskId,opinion,JSONObject.toJSONString(ycslTjqqRequestData));
                ycslTjqqRequestBody.setData(ycslTjqqRequestData);
                return ycslTjqqRequestBody;
            }catch (Exception e){
                LOGGER.error("获取退件任务异常 processInsId:{},taskId:{},opinion:{}", gzlslid,taskId,opinion,e);
            }
        }
        return null;
    }
}
