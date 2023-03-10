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
            opinion = "??????";
        }
        LOGGER.info("???????????????????????? processInsId:{},taskId:{},opinion:{}", gzlslid,taskId,opinion);
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("???????????????id??????");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO temp = bdcXmDOList.get(0);
            // ???????????????????????? ????????????????????????
            if(CheckWwsqOrYcslUtils.checkIsYcsl(temp.getSply())){
                ycslTjqqRequestData.setYwh(temp.getSpxtywh());
            }
        }

        if(StringUtil.isNotEmpty(ycslTjqqRequestData.getYwh())){
            StringBuilder shyjSb = new StringBuilder("");
            // ???????????????????????? ??????????????????
            // ??????????????????
            try{
                TaskData taskData = processTaskClient.getTaskById(taskId);
                if(taskData != null){
                    LOGGER.info("???????????????????????????????????????:{}", JSONObject.toJSONString(taskData));
                    String userName = taskData.getTaskAssigin();
                    UserDto userDto = userManagerClient.getUserDetailByUsername(userName);
                    if(userDto != null){
                        shyjSb.append(userDto.getAlias()).append(opinion);
                    }
                }
                // ??????????????????
                ycslTjqqRequestData.setShyj(shyjSb.toString());
                LOGGER.info("???????????????????????? processInsId:{},taskId:{},,opinion:{},requestData:{}",
                        gzlslid,taskId,opinion,JSONObject.toJSONString(ycslTjqqRequestData));
                ycslTjqqRequestBody.setData(ycslTjqqRequestData);
                return ycslTjqqRequestBody;
            }catch (Exception e){
                LOGGER.error("???????????????????????? processInsId:{},taskId:{},opinion:{}", gzlslid,taskId,opinion,e);
            }
        }
        return null;
    }
}
