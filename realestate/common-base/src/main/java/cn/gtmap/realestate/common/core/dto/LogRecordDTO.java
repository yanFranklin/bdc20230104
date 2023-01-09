package cn.gtmap.realestate.common.core.dto;

import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/5/5
 * @description 日志记录DTO
 */
public class LogRecordDTO {

    /**
     * 日志类型
     */
    private String logType;
    /**
     * 日志操作名称
     */
    private String logAction;
    /**
     * 日志参数
     */
    private Map<String, Object> paramMap;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 是否记录日志至数据库
     */
    private boolean dbRecord;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogAction() {
        return logAction;
    }

    public void setLogAction(String logAction) {
        this.logAction = logAction;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isDbRecord() {
        return dbRecord;
    }

    public void setDbRecord(boolean dbRecord) {
        this.dbRecord = dbRecord;
    }
}
