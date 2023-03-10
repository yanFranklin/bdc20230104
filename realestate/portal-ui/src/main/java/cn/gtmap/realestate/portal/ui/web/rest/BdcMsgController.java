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
@Api(tags = "????????????")
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
     * @description ????????????????????????ID
     */
    @Value("${msg.center.qrclientId:}")
    private String qrclientId;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????????????????????????????????????????
     */
    @Value("${msg.center.qrmsgType:CUSTOM}")
    private String qrMsgTypes;

    /**
     * ????????????????????????????????????
     *
     * @return List<MessageDto>  ????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @GetMapping("/list")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("?????????????????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "??????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "clientId", value = "??????Id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "msgType", value = "????????????", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "read", value = "????????????", dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "??????ID", dataType = "string", paramType = "query")})
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
        //??????????????????list
        List<MessageDto> messageDtoList = null;
        //??????????????????
        List<MessageBackDto> messageBackDtoList = null;
        //???id????????????????????????id??????????????????
        if (StringUtils.isNotBlank(id)) {
            messageBackDtoList = new ArrayList<>(1);
            messageDtoList = new ArrayList<>(1);
            //??????????????????
            MessageDto messageDto = messageClient.queryMessageById(id);
            if (Objects.nonNull(messageDto)) {
                messageDtoList.add(messageDto);
                //????????????
                processMsgBack(messageBackDtoList, messageDtoList);
                return addLayUiCode(new PageImpl(messageBackDtoList, pageable, 1));
            }
        } else {
            //??????????????????
            Page<MessageDto> messageDtoPage = messageClient.pageMessage(pageable, clientId, null, msgType, null, userManagerUtils.getCurrentUserName(), null, read);
            if (messageDtoPage != null && CollectionUtils.isNotEmpty(messageDtoPage.getContent())) {
                messageBackDtoList = new ArrayList<>(messageDtoPage.getContent().size());
                messageDtoList = messageDtoPage.getContent();
                //????????????
                processMsgBack(messageBackDtoList, messageDtoList);
                return addLayUiCode(new PageImpl(messageBackDtoList, pageable, messageDtoPage.getTotalElements()));
            }
        }
        return addLayUiCode(new PageImpl(new ArrayList(), pageable, 0));
    }

    /**
     * ????????????????????????????????????
     *
     * @return List<MessageDto>  ????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @PostMapping("/home/list")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("?????????????????????????????????")
    public BdcMessageDTO homeMsgList() {
        BdcMessageDTO bdcMessageDTO = new BdcMessageDTO();
        Pageable pageable = new PageRequest(0, 10);
        Page<MessageDto> pageWd = messageClient.pageMessage(pageable, clientId, null, msgType, null, userManagerUtils.getCurrentUserName(), null, CommonConstantUtils.SF_F_DM);
        Page<MessageDto> pageYd = messageClient.pageMessage(pageable, clientId, null, msgType, null, userManagerUtils.getCurrentUserName(), null, CommonConstantUtils.SF_S_DM);
        Long ybSize = 0L;
        Long wbSize = 0L;
        //??????????????????
        if (pageWd != null && CollectionUtils.isNotEmpty(pageWd.getContent())) {
            wbSize = pageWd.getTotalElements();
            //????????????????????????
            List<MessageBackDto> messageBackDtoListWd = new ArrayList<>(pageWd.getContent().size());
            //????????????
            processMsgBack(messageBackDtoListWd, pageWd.getContent());

            bdcMessageDTO.setWdList(messageBackDtoListWd);

        }
        //??????????????????
        if (pageYd != null && CollectionUtils.isNotEmpty(pageYd.getContent())) {
            ybSize = pageYd.getTotalElements();
            //????????????????????????
            List<MessageBackDto> messageBackDtoListYd = new ArrayList<>(pageYd.getContent().size());
            //????????????
            processMsgBack(messageBackDtoListYd, pageYd.getContent());

            bdcMessageDTO.setYdList(messageBackDtoListYd);

        }
        bdcMessageDTO.setWbSize(wbSize.intValue());
        bdcMessageDTO.setYbSize(ybSize.intValue());

        //????????????????????????
        List<MessageDto> messageDtoList = messageClient.listMessage(qrclientId, "", qrMsgTypes, null, userManagerUtils.getCurrentUserName(), null, 0);

        if(CollectionUtils.isNotEmpty(messageDtoList)) {
            //????????????????????????
            List<MessageBackDto> messageBackDtoListQr = new ArrayList<>(messageDtoList.size());
            //????????????
            processMsgBack(messageBackDtoListQr, messageDtoList);

            //????????????????????????
            Map<String, List<MessageBackDto>> qrxxMap = messageBackDtoListQr.stream().filter(messageBackDto -> StringUtils.isNotBlank(messageBackDto.getMsgCode())).collect(Collectors.groupingBy(MessageBackDto::getMsgCode));
            bdcMessageDTO.setQrxxMap(qrxxMap);
            bdcMessageDTO.setQrList(messageBackDtoListQr);
        }


        return bdcMessageDTO;
    }

    /**
     * @description ???????????????????????????
     * @param messageBackDtoList
     * @param messageDtoList
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/11/1 15:18
     */
    private void processMsgBack(List<MessageBackDto> messageBackDtoList, List<MessageDto> messageDtoList) {
        //?????????????????????????????????
        for (MessageDto messageDto : messageDtoList) {
            MessageBackDto messageBackDto = new MessageBackDto();
            //??????
            BeanUtils.copyProperties(messageDto,messageBackDto);
            //??????????????????
            UserDto userDto = null;
            if (StringUtils.isNotBlank(messageDto.getProducer())){
                userDto = userManagerClient.getUserDetailByUsername(messageDto.getProducer());
            }
            if(Objects.nonNull(userDto)){
                messageBackDto.setProducerName(userDto.getAlias());
            } else {
                messageBackDto.setProducerName("??????");
            }
            messageBackDto.setFjbs(queryDownloadUrl(messageDto.getMsgCode()));
            messageBackDtoList.add(messageBackDto);
        }
    }

    /**
     * @description ????????????????????????
     * @param msgCode
     * @return: java.lang.int  0???????????????  1?????????????????????
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/11/1 16:32
     */
    private int queryDownloadUrl(String msgCode) {
        //??????????????????
        if (StringUtils.isNotEmpty(msgCode)) {
            //?????????????????????????????????
            List<StorageDto> storageFjList = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, msgCode, "", Constants.MSG_FILE, 1, null);
            if (CollectionUtils.isNotEmpty(storageFjList)) {
                return 1;
            }
        } else {
            throw new AppException("????????????????????????????????????");
        }
        return 0;
    }

    /**
     * @param id ????????????ID
     * @return MessageDto
     * @description ????????????
     */
    @GetMapping("/message/{id}")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = " ????????????ID", required = true, dataType = "String", paramType = "path")})
    public MessageBackDto queryMessage(@PathVariable("id") String id) {
        MessageBackDto messageBackDto = new MessageBackDto();
        //?????????????????????
        MessageDto messageDto = messageClient.queryMessageById(id);
        if (Objects.nonNull(messageDto)) {
            //??????
            BeanUtils.copyProperties(messageDto,messageBackDto);
            //??????????????????
            UserDto userDto = null;
            if (StringUtils.isNotBlank(messageDto.getProducer())){
                userDto = userManagerClient.getUserDetailByUsername(messageDto.getProducer());
            }
            if(Objects.nonNull(userDto)){
                messageBackDto.setProducerName(userDto.getAlias());
            } else {
                messageBackDto.setProducerName("??????");
            }
            //????????????
            messageBackDto.setFjbs(queryDownloadUrl(messageDto.getMsgCode()));
        }
        return messageBackDto;
    }

    /**
     * @param ids ????????????IDs
     * @description ????????????
     */
    @DeleteMapping("/deleteMessages")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = " ????????????IDs", required = true, dataType = "String", paramType = "query")})
    public void deleteMessages(@RequestBody List<String> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            String messageIds = StringUtils.join(ids, ",");
            messageClient.deleteMessages(messageIds);
        }

    }

    /**
     * @param ids ????????????IDs
     * @return
     * @description ???????????? ids ??????????????????????????????
     */
    @PutMapping("/readMessages")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "????????????IDs", paramType = "query", dataType = "String")})
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
     * @description ????????????code???????????????????????????
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/10/24 12:00
     */
    @PostMapping("/downloadMsgFile")
    @ApiImplicitParams({@ApiImplicitParam(name = "msgCode", value = "??????Code", paramType = "query", dataType = "String")})
    @ResponseStatus(code = HttpStatus.OK)
    public List<StorageDto> downloadMsgFile(@RequestParam(value = "msgCode") String msgCode) {
        //??????????????????
        List<StorageDto> storageFjList = new ArrayList<>(5);
        if (StringUtils.isNotEmpty(msgCode)) {
            //?????????????????????????????????
            storageFjList = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, msgCode, "", Constants.MSG_FILE, 1, null);
        } else {
            throw new AppException("????????????????????????????????????");
        }
        return storageFjList;
    }

    /**
     * @param usernames ?????????
     * @return
     * @description ?????????usernames
     */
    @PostMapping
    @ApiImplicitParams({@ApiImplicitParam(name = "usernames", value = "??????????????????", paramType = "query", dataType = "String")})
    @ResponseStatus(code = HttpStatus.OK)
    public void sendMessages(@RequestParam(value = "usernames") List<String> usernames, @RequestParam(value = "msgContent") String msgContent, @RequestParam(value = "msgCode") String msgCode, @RequestParam(value = "msgTitle") String msgTitle) {
        if (CollectionUtils.isNotEmpty(usernames) && StringUtils.isNotBlank(msgContent)) {
            //????????????????????????msgCode??????????????????CUSTOMMSG
            if (StringUtils.isBlank(msgCode)) {
                msgCode = "CUSTOMMSG";
            }
            ProduceMsgDto produceMsgDto = new ProduceMsgDto();
            produceMsgDto.setClientId(clientId);
            produceMsgDto.setMsgCode(msgCode);
            produceMsgDto.setMsgType("CUSTOM");
            produceMsgDto.setMsgTypeName("???????????????");
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
     * @description ???????????????
     * @return: cn.gtmap.gtc.storage.domain.dto.StorageDto
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/10/21 11:35
     */
    @PostMapping("/uploadMsgFile")
//    @ResponseStatus(code = HttpStatus.OK)
    public MsgUploadDto uploadMsgFile(@RequestBody List<MultipartFile> files) {
        //????????????????????????code,??????????????????????????????spaceId
        String msgCode = UUIDGenerator.generate16();

        //????????????
        MsgUploadDto msgUploadDto = new MsgUploadDto();
        //???????????????????????????
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userId = "";
        if (Objects.nonNull(userDto) && StringUtils.isNotBlank(userDto.getId())) {
            userId = userDto.getId();
        }
        //??????????????????
        List<StorageDto> storageDtoBackList = new ArrayList<>(10);
        StorageDto storageDto = null;

        //??????
        if (CollectionUtils.isNotEmpty(files)) {
            //????????????????????????????????????????????????????????????????????????????????????
            List<StorageDto> storageDtoList = storageClient.listStoragesByName(CommonConstantUtils.WJZX_CLIENTID, msgCode, userId, Constants.MSG_FILE, 1, null);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                storageDto = storageDtoList.get(0);
                List<String> listId = new ArrayList();
                for (StorageDto storageDtoTemp : storageDtoList) {
                    listId.add(storageDtoTemp.getId());
                }
                if (CollectionUtils.isNotEmpty(listId)) {
                    boolean result = storageClient.deleteStorages(listId);
                    LOGGER.warn("?????????????????????{}?????????????????????{}", result, JSONObject.toJSONString(storageDtoList));
                }
            }
            //??????????????????
            for (MultipartFile file : files) {
                try {
                    // ?????????????????????
                    String fileName = file.getOriginalFilename();

                    //???????????????????????????????????????????????????
                    storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, msgCode, Constants.MSG_FILE, userId);
                    if (storageDto != null) {
                        MultipartDto multipartDto = this.getUploadParamDto(userId, storageDto.getId(), file, fileName);
                        storageDto = storageClient.multipartUpload(multipartDto);
                        storageDtoBackList.add(storageDto);
                        LOGGER.warn("???????????????{}", JSONObject.toJSONString(storageDto));
                    }
                } catch (IOException e) {
                    LOGGER.error("?????????????????????{}", e.getMessage());
                } catch (Exception e) {
                    LOGGER.error("??????????????????{}", e.getMessage());
                }

            }
            msgUploadDto.setMsgCode(msgCode);
            msgUploadDto.setStorageDtoList(storageDtoBackList);
        }
        return msgUploadDto;
    }

    /**
     * @param userId
     * @param nodeId   ???????????????Id
     * @param file
     * @param fileName
     * @description ?????????????????????????????????multipartDto??????
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
