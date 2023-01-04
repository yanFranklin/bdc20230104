package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 供地审批信息附件集合dto
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2020-12-28 08:52
 **/
public class FolderDTO implements Serializable {


    private static final long serialVersionUID = -8420109557517949834L;
    @ApiModelProperty(value = "子节点集合")
    private List<FolderDTO> childrenFolderDTOList;

    @ApiModelProperty(value = "文件集合")
    private List<FileDTO> fileDTOList;

    @ApiModelProperty(value = "文件夹名称")
    private String folderName;

    public List<FolderDTO> getChildrenFolderDTOList() {
        return childrenFolderDTOList;
    }

    public void setChildrenFolderDTOList(List<FolderDTO> childrenFolderDTOList) {
        this.childrenFolderDTOList = childrenFolderDTOList;
    }

    public List<FileDTO> getFileDTOList() {
        return fileDTOList;
    }

    public void setFileDTOList(List<FileDTO> fileDTOList) {
        this.fileDTOList = fileDTOList;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public String toString() {
        return "FolderDTO{" +
                "childrenFolderDTOList=" + childrenFolderDTOList +
                ", fileDTOList=" + fileDTOList +
                ", folderName='" + folderName + '\'' +
                '}';
    }
}
