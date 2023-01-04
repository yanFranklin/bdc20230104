package cn.gtmap.realestate.inquiry.ui.core.vo;

import java.util.Date;
import java.util.List;

/**
 * 方法参数封装类
 */
public class BdcXzrzParameterVo {
    /**
     * 日志服务器路径
     */
    private String path;
    /**
     * 页面传来的参数应用名称
     */
    private List<String> listApply;
    /**
     * 页面传来的参数实例名称
     */
    private String applyName;
    /**
     * 页面传来的参数开始时间
     */
    private Date startTime;
    /**
     * 页面传来的参数结束时间
     */
    private Date endTime;

    public BdcXzrzParameterVo(String path, List<String> listApply, String applyName, Date startTime, Date endTime) {
        this.path = path;
        this.listApply = listApply;
        this.applyName = applyName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BdcXzrzParameterVo() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getListApply() {
        return listApply;
    }

    public void setListApply(List<String> listApply) {
        this.listApply = listApply;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
