package cn.gtmap.realestate.portal.ui.core.dto;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;

import java.util.List;

/**
 * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
 * @version 1.0  2022/10/24
 * @description 消息上传附件返回参数
 */
public class MsgUploadDto {
    /**
     * 消息code
     */
    private String msgCode;

    private List<StorageDto> storageDtoList;

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public List<StorageDto> getStorageDtoList() {
        return storageDtoList;
    }

    public void setStorageDtoList(List<StorageDto> storageDtoList) {
        this.storageDtoList = storageDtoList;
    }
}
