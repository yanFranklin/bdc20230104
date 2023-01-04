package cn.gtmap.realestate.exchange.util;

import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.msg.client.MessageClient;
import cn.gtmap.gtc.msg.domain.dto.ProduceMsgDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/06/29
 * @description 消息提醒工具类
 */
@Service
public class MessageUtils {
    private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);

    @Autowired
    private MessageClient messageClient;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private RoleManagerClient roleManagerClient;

    @Value("${msg.center.clientId:}")
    private String clientId;


    /**
     * 发送系统站内消息到指定用户角色
     *
     * @param roleNames  目标角色
     * @param msgContent 通知内容
     */
    public void sendMessagesByRole(List<String> roleNames, String msgContent, String msgCode) {
        if (CollectionUtils.isEmpty(roleNames) || StringUtils.isBlank(msgContent)) {
            logger.error("发送系统站内消息未定义角色或消息内容，目标角色{}, 提示信息{}", roleNames, msgContent);
            return;
        }

        List<RoleDto> roleList = roleManagerClient.queryRoleByRoleNames(roleNames);
        if (CollectionUtils.isEmpty(roleList)) {
            logger.error("发送系统站内消息无法获取到角色，目标角色{}, 提示信息{}", roleNames, msgContent);
            return;
        }
        List<String> roleIdList = roleList.stream().map(RoleDto::getId).collect(Collectors.toList());

        ProduceMsgDto produceMsgDto = new ProduceMsgDto();
        produceMsgDto.setClientId(clientId);
        produceMsgDto.setMsgCode(msgCode);
        produceMsgDto.setMsgType("CUSTOM");
        produceMsgDto.setMsgTypeName("自定义消息");
        produceMsgDto.setMsgTitle("无");
        produceMsgDto.setConsumer(StringUtils.join(roleIdList, CommonConstantUtils.ZF_YW_DH));
        produceMsgDto.setConsumerType("role");
        produceMsgDto.setProducer(userManagerUtils.getCurrentUserName());
        produceMsgDto.setProducerType("personal");
        produceMsgDto.setNotifyType("rabbitmq");
        produceMsgDto.setMsgContent(msgContent);
        produceMsgDto.setRead(0);
        produceMsgDto.setOptions("save");
        messageClient.saveProduceMsg(produceMsgDto);
    }

    /**
     * 发送系统站内消息到指定用户
     * @param usernames 目标用户
     * @param msgContent 通知内容
     */
    public void sendMessagesByUser(List<String> usernames, String msgContent){
        if(CollectionUtils.isEmpty(usernames) || StringUtils.isBlank(msgContent)) {
            logger.error("发送系统站内消息未定义用户或消息内容，目标角色{}, 提示信息{}", usernames, msgContent);
            return;
        }

        ProduceMsgDto produceMsgDto = new ProduceMsgDto();
        produceMsgDto.setClientId(clientId);
        produceMsgDto.setMsgCode("CUSTOMMSG");
        produceMsgDto.setMsgType("CUSTOM");
        produceMsgDto.setMsgTypeName("自定义消息");
        produceMsgDto.setMsgTitle("无");
        produceMsgDto.setConsumer(StringUtils.join(usernames,CommonConstantUtils.ZF_YW_DH));
        produceMsgDto.setConsumerType("personal");
        produceMsgDto.setProducer(userManagerUtils.getCurrentUserName());
        produceMsgDto.setProducerType("personal");
        produceMsgDto.setNotifyType("rabbitmq");
        produceMsgDto.setMsgContent(msgContent);
        produceMsgDto.setRead(0);
        produceMsgDto.setOptions("save");
        messageClient.saveProduceMsg(produceMsgDto);
    }
}
