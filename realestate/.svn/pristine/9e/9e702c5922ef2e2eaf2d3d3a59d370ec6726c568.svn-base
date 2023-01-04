package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.msg.client.MessageClient;
import cn.gtmap.gtc.msg.domain.dto.MessageDto;
import cn.gtmap.gtc.msg.domain.dto.ProduceMsgDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.portal.ui.core.dto.BdcMessageDTO;
import cn.gtmap.realestate.portal.ui.core.dto.MessageBackDto;
import cn.gtmap.realestate.portal.ui.core.dto.MsgUploadDto;
import cn.gtmap.realestate.portal.ui.utils.Constants;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/4/8.
 * @description
 */
@RestController
@RequestMapping("/rest/v1.0/msg")
@Api(tags = "消息中心")
public class BdcMsgController extends BaseController {

    @Autowired
    private MessageClient messageClient;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private UserManagerClient userManagerClient;

    @Value("${msg.center.clientId:}")
    private String clientId;
    @Value("${msg.center.msgType:}")
    private String msgType;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 需要确认消息应用ID
     */
    @Value("${msg.center.qrclientId:}")
    private String qrclientId;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 需要确认的消息，多个用英文逗号拼接
     */
    @Value("${msg.center.qrmsgType:CUSTOM}")
    private String qrMsgTypes;

    /**
     * 获取当前登录人的消息信息
     *
     * @return List<MessageDto>  消息集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @GetMapping("/list")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页获取消息中心的消息")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "clientId", value = "应用Id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgType", value = "消息类型", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "read", value = "是否已读", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "消息ID", dataType = "string", paramType = "query")})
    public Object msgList(@LayuiPageable Pageable pageable,
                          @RequestParam(value = "clientId", required = false) String clientId,
                          @RequestParam(value = "msgType", required = false) String msgType,
                          @RequestParam(value = "read", required = false) Integer read,
                          @RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isBlank(clientId)) {
            clientId = this.clientId;
        }
        if (StringUtils.isBlank(msgType)) {
            msgType = this.msgType;
        }
        //查询接口返回list
        List<MessageDto> messageDtoList = null;
        //重新构造返回
        List<MessageBackDto> messageBackDtoList = null;
        //若id有值，则根据消息id获取消息详情
        if (StringUtils.isNotBlank(id)) {
            messageBackDtoList = new ArrayList<>(1);
            messageDtoList = new ArrayList<>(1);
            //获取消息详情
            MessageDto messageDto = messageClient.queryMessageById(id);
            if (Objects.nonNull(messageDto)) {
                messageDtoList.add(messageDto);
                //处理消息
                processMsgBack(messageBackDtoList, messageDtoList);
                return addLayUiCode(new PageImpl(messageBackDtoList, pageable, 1));
            }
        } else {
            //分页获取消息
            Page<MessageDto> messageDtoPage = messageClient.pageMessage(pageable, clientId, null, msgType, null, userManagerUtils.getCurrentUserName(), null, read);
            if (messageDtoPage != null && CollectionUtils.isNotEmpty(messageDtoPage.getContent())) {
                messageBackDtoList = new ArrayList<>(messageDtoPage.getContent().size());
                messageDtoList = messageDtoPage.getContent();
                //处理消息
                processMsgBack(messageBackDtoList, messageDtoList);
                return addLayUiCode(new PageImpl(messageBackDtoList, pageable, messageDtoPage.getTotalElements()));
            }
        }
        return addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));
    }

    /**
     * 获取当前登录人的消息信息
     *
     * @return List<MessageDto>  消息集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @PostMapping("/home/list")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("首页获取消息中心的消息")
    public BdcMessageDTO homeMsgList() {
        BdcMessageDTO bdcMessageDTO = new BdcMessageDTO();
        Pageable pageable = new PageRequest(0, 10);
        Page<MessageDto> pageWd = messageClient.pageMessage(pageable, clientId, null, msgType, null, userManagerUtils.getCurrentUserName(), null, CommonConstantUtils.SF_F_DM);
        Page<MessageDto> pageYd = messageClient.pageMessage(pageable, clientId, null, msgType, null, userManagerUtils.getCurrentUserName(), null, CommonConstantUtils.SF_S_DM);
        Long ybSize = 0L;
        Long wbSize = 0L;
        //赋值未读数据
        if (pageWd != null && CollectionUtils.isNotEmpty(pageWd.getContent())) {
            wbSize = pageWd.getTotalElements();
            //重新构造未读返回
            List<MessageBackDto> messageBackDtoListWd = new ArrayList<>(pageWd.getContent().size());
            //处理消息
            processMsgBack(messageBackDtoListWd, pageWd.getContent());

            bdcMessageDTO.setWdList(messageBackDtoListWd);

        }
        //赋值已读数据
        if (pageYd != null && CollectionUtils.isNotEmpty(pageYd.getContent())) {
            ybSize = pageYd.getTotalElements();
            //重新构造已读返回
            List<MessageBackDto> messageBackDtoListYd = new ArrayList<>(pageYd.getContent().size());
            //处理消息
            processMsgBack(messageBackDtoListYd, pageYd.getContent());

            bdcMessageDTO.setYdList(messageBackDtoListYd);

        }
        bdcMessageDTO.setWbSize(wbSize.intValue());
        bdcMessageDTO.setYbSize(ybSize.intValue());

        //查找需确认的信息
        List<MessageDto> messageDtoList = messageClient.listMessage(qrclientId, "", qrMsgTypes, null, userManagerUtils.getCurrentUserName(), null, 0);

        if(CollectionUtils.isNotEmpty(messageDtoList)) {
            //重新构造确认返回
            List<MessageBackDto> messageBackDtoListQr = new ArrayList<>(messageDtoList.size());
            //处理消息
            processMsgBack(messageBackDtoListQr, messageDtoList);

            //根据消息类型分组
            Map<String, List<MessageBackDto>> qrxxMap = messageBackDtoListQr.stream().filter(messageBackDto -> StringUtils.isNotBlank(messageBackDto.getMsgCode())).collect(Collectors.groupingBy(MessageBackDto::getMsgCode));
            bdcMessageDTO.setQrxxMap(qrxxMap);
            bdcMessageDTO.setQrList(messageBackDtoListQr);
        }


        return bdcMessageDTO;
    }

    /**
     * @description 处理消息生产者名称
     * @param messageBackDtoList
     * @param messageDtoList
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/11/1 15:18
     */
    private void processMsgBack(List<MessageBackDto> messageBackDtoList, List<MessageDto> messageDtoList) {
        //重新构造需要确认的消息
        for (MessageDto messageDto : messageDtoList) {
            MessageBackDto messageBackDto = new MessageBackDto();
            //复制
            BeanUtils.copyProperties(messageDto,messageBackDto);
            //查找用户信息
            UserDto userDto = null;
            if (StringUtils.isNotBlank(messageDto.getProducer())){
                userDto = userManagerClient.getUserDetailByUsername(messageDto.getProducer());
            }
            if(Objects.nonNull(userDto)){
                messageBackDto.setProducerName(userDto.getAlias());
            } else {
                messageBackDto.setProducerName("系统");
            }
            messageBackDto.setFjbs(queryDownloadUrl(messageDto.getMsgCode()));
            messageBackDtoList.add(messageBackDto);
        }
    }

    /**
     * @description 获取附件下载地址
     * @param msgCode
     * @return: java.lang.int  0标识无附件  1标识有附件信息
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/11/1 16:32
     */
    private int queryDownloadUrl(String msgCode) {
        //构建返回信息
        if (StringUtils.isNotEmpty(msgCode)) {
            //按文件名称查询所有文件
            List<StorageDto> storageFjList = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, msgCode, "", Constants.MSG_FILE, 1, null);
            if (CollectionUtils.isNotEmpty(storageFjList)) {
                return 1;
            }
        } else {
            throw new AppException("参数缺失，无法下载文件！");
        }
        return 0;
    }

    /**
     * @param id 消息唯一ID
     * @return MessageDto
     * @description 消息查询
     */
    @GetMapping("/message/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = " 消息唯一ID", required = true, dataType = "String", paramType = "path")})
    public MessageBackDto queryMessage(@PathVariable("id") String id) {
        MessageBackDto messageBackDto = new MessageBackDto();
        //调消息查询接口
        MessageDto messageDto = messageClient.queryMessageById(id);
        if (Objects.nonNull(messageDto)) {
            //复制
            BeanUtils.copyProperties(messageDto,messageBackDto);
            //查找用户信息
            UserDto userDto = null;
            if (StringUtils.isNotBlank(messageDto.getProducer())){
                userDto = userManagerClient.getUserDetailByUsername(messageDto.getProducer());
            }
            if(Objects.nonNull(userDto)){
                messageBackDto.setProducerName(userDto.getAlias());
            } else {
                messageBackDto.setProducerName("系统");
            }
            //附件标识
            messageBackDto.setFjbs(queryDownloadUrl(messageDto.getMsgCode()));
        }
        return messageBackDto;
    }

    /**
     * @param ids 消息唯一IDs
     * @description 消息删除
     */
    @DeleteMapping("/deleteMessages")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = " 消息唯一IDs", required = true, dataType = "String", paramType = "query")})
    public void deleteMessages(@RequestBody List<String> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            String messageIds = StringUtils.join(ids, ",");
            messageClient.deleteMessages(messageIds);
        }

    }

    /**
     * @param ids 消息唯一IDs
     * @return
     * @description 设置已读 ids 为空，则设置所有已读
     */
    @PutMapping("/readMessages")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "消息唯一IDs", paramType = "query", dataType = "String")})
    @ResponseStatus(code = HttpStatus.OK)
    public void readMessages(@RequestBody(required = false) List<String> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            String userName = userManagerUtils.getCurrentUserName();
            if (ids.size() >= 500) {
                List<List> partList = ListUtils.subList(ids, 500);
                if (CollectionUtils.isNotEmpty(partList)) {
                    for (List idList : partList) {
                        messageClient.readMessages(userName, idList);
                    }
                }
            } else {
                messageClient.readMessages(userName, ids);
            }
        }
    }

    /**
     * @param msgCode
     * @description 根据消息code下载对应的附件内容
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/10/24 12:00
     */
    @PostMapping("/downloadMsgFile")
    @ApiImplicitParams({@ApiImplicitParam(name = "msgCode", value = "消息Code", paramType = "query", dataType = "String")})
    @ResponseStatus(code = HttpStatus.OK)
    public List<StorageDto> downloadMsgFile(@RequestParam(value = "msgCode") String msgCode) {
        //构建返回信息
        List<StorageDto> storageFjList = new ArrayList<>(5);
        if (StringUtils.isNotEmpty(msgCode)) {
            //按文件名称查询所有文件
            storageFjList = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, msgCode, "", Constants.MSG_FILE, 1, null);
        } else {
            throw new AppException("参数缺失，无法下载文件！");
        }
        return storageFjList;
    }

    /**
     * @param usernames 用户值
     * @return
     * @description 发送的usernames
     */
    @PostMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "usernames", value = "用户名的集合", paramType = "query", dataType = "String")})
    @ResponseStatus(code = HttpStatus.OK)
    public void sendMessages(@RequestParam(value = "usernames") List<String> usernames, @RequestParam(value = "msgContent") String msgContent, @RequestParam(value = "msgCode") String msgCode, @RequestParam(value = "msgTitle") String msgTitle) {
        if (CollectionUtils.isNotEmpty(usernames) && StringUtils.isNotBlank(msgContent)) {
            //若未上传附件，则msgCode值延用之前的CUSTOMMSG
            if (StringUtils.isBlank(msgCode)) {
                msgCode = "CUSTOMMSG";
            }
            ProduceMsgDto produceMsgDto = new ProduceMsgDto();
            produceMsgDto.setClientId(clientId);
            produceMsgDto.setMsgCode(msgCode);
            produceMsgDto.setMsgType("CUSTOM");
            produceMsgDto.setMsgTypeName("自定义消息");
            produceMsgDto.setMsgTitle(msgTitle);
            produceMsgDto.setConsumer(StringUtils.join(usernames, CommonConstantUtils.ZF_YW_DH));
            produceMsgDto.setConsumerType("batch");
            produceMsgDto.setProducer(userManagerUtils.getCurrentUserName());
            produceMsgDto.setProducerType("personal");
            produceMsgDto.setNotifyType("rabbitmq");
            produceMsgDto.setMsgContent(msgContent);
            produceMsgDto.setRead(0);
            produceMsgDto.setOptions("save");
            messageClient.saveProduceMsg(produceMsgDto);
        }
    }

    /**
     * @param files
     * @description 多文件上传
     * @return: cn.gtmap.gtc.storage.domain.dto.StorageDto
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/10/21 11:35
     */
    @PostMapping("/uploadMsgFile")
//    @ResponseStatus(code = HttpStatus.OK)
    public MsgUploadDto uploadMsgFile(@RequestBody List<MultipartFile> files) {
        //上传附件生成消息code,用于创建上传附件的的spaceId
        String msgCode = UUIDGenerator.generate16();

        //返回信息
        MsgUploadDto msgUploadDto = new MsgUploadDto();
        //获取当前登录人信息
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userId = "";
        if (Objects.nonNull(userDto) && StringUtils.isNotBlank(userDto.getId())) {
            userId = userDto.getId();
        }
        //构建返回信息
        List<StorageDto> storageDtoBackList = new ArrayList<>(10);
        StorageDto storageDto = null;

        //判空
        if (CollectionUtils.isNotEmpty(files)) {
            //避免附件重复，上传前先判断，如果已存在，则删除已有的信息
            List<StorageDto> storageDtoList = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, msgCode, userId, Constants.MSG_FILE, 1, null);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                storageDto = storageDtoList.get(0);
                List<String> listId = new ArrayList();
                for (StorageDto storageDtoTemp : storageDtoList) {
                    listId.add(storageDtoTemp.getId());
                }
                if (CollectionUtils.isNotEmpty(listId)) {
                    boolean result = storageClient.deleteStorages(listId);
                    LOGGER.warn("文件删除结果：{}。删除信息为：{}", result, JSONObject.toJSONString(storageDtoList));
                }
            }
            //依次上传文件
            for (MultipartFile file : files) {
                try {
                    // 上传的文件名称
                    String fileName = file.getOriginalFilename();

                    //创建新的文件夹，已存在，则不再创建
                    storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, msgCode, Constants.MSG_FILE, userId);
                    if (storageDto != null) {
                        MultipartDto multipartDto = this.getUploadParamDto(userId, storageDto.getId(), file, fileName);
                        storageDto = storageClient.multipartUpload(multipartDto);
                        storageDtoBackList.add(storageDto);
                        LOGGER.warn("上传信息：{}", JSONObject.toJSONString(storageDto));
                    }
                } catch (IOException e) {
                    LOGGER.error("文件解析异常！{}", e.getMessage());
                } catch (Exception e) {
                    LOGGER.error("文件操作异常{}", e.getMessage());
                }

            }
            msgUploadDto.setMsgCode(msgCode);
            msgUploadDto.setStorageDtoList(storageDtoBackList);
        }
        return msgUploadDto;
    }

    /**
     * @param userId
     * @param nodeId   文件夹目录Id
     * @param file
     * @param fileName
     * @description 组成文件中心上传所需的multipartDto对象
     * @return: cn.gtmap.gtc.storage.domain.dto.MultipartDto
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/10/21 14:23
     */
    private MultipartDto getUploadParamDto(String userId, String nodeId, MultipartFile file, String fileName) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(nodeId);
        multipartDto.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        multipartDto.setData(file.getBytes());
        multipartDto.setOwner(userId);
        multipartDto.setContentType(file.getContentType());
        multipartDto.setSize(file.getSize());
        multipartDto.setOriginalFilename(fileName);
        multipartDto.setName(fileName);
        return multipartDto;
    }

}
