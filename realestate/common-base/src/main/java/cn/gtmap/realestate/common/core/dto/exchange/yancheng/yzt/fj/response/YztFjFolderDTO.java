package cn.gtmap.realestate.common.core.dto.exchange.yancheng.yzt.fj.response;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/24
 * @description
 */
public class YztFjFolderDTO {

    private String folderName;

    private List<YztFjFileDTO> fileDTOList;

    private List<YztFjFolderDTO> childrenFolderDTOList;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<YztFjFileDTO> getFileDTOList() {
        return fileDTOList;
    }

    public void setFileDTOList(List<YztFjFileDTO> fileDTOList) {
        this.fileDTOList = fileDTOList;
    }

    public List<YztFjFolderDTO> getChildrenFolderDTOList() {
        return childrenFolderDTOList;
    }

    public void setChildrenFolderDTOList(List<YztFjFolderDTO> childrenFolderDTOList) {
        this.childrenFolderDTOList = childrenFolderDTOList;
    }
}
