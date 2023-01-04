package cn.gtmap.realestate.inquiry.ui.core.dto.gx.ggxt;

import java.util.List;

public class AttachData {
    private String localProjectCode;
    private String projectName;
    private String matterName;
    private String cwTime;
    private String attachFiles;
    private List<AttachList> attachList;

    public String getLocalProjectCode() {
        return localProjectCode;
    }

    public void setLocalProjectCode(String localProjectCode) {
        this.localProjectCode = localProjectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMatterName() {
        return matterName;
    }

    public void setMatterName(String matterName) {
        this.matterName = matterName;
    }

    public String getCwTime() {
        return cwTime;
    }

    public void setCwTime(String cwTime) {
        this.cwTime = cwTime;
    }


    public String getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(String attachFiles) {
        this.attachFiles = attachFiles;
    }

    public List<AttachList> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<AttachList> attachList) {
        this.attachList = attachList;
    }
}
