package cn.gtmap.realestate.inquiry.ui.core.dto.gx.ythsp;

import java.util.List;

public class Data {
    private List<Files> files;
    private TdgyInfo tdgyInfo;
    private GhhsInfo ghhsInfo;
    private GhxkInfo ghxkInfo;

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    public TdgyInfo getTdgyInfo() {
        return tdgyInfo;
    }

    public void setTdgyInfo(TdgyInfo tdgyInfo) {
        this.tdgyInfo = tdgyInfo;
    }

    public GhhsInfo getGhhsInfo() {
        return ghhsInfo;
    }

    public void setGhhsInfo(GhhsInfo ghhsInfo) {
        this.ghhsInfo = ghhsInfo;
    }

    public GhxkInfo getGhxkInfo() {
        return ghxkInfo;
    }

    public void setGhxkInfo(GhxkInfo ghxkInfo) {
        this.ghxkInfo = ghxkInfo;
    }
}
