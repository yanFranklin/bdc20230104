package cn.gtmap.realestate.exchange.core.dto.hefei.hyxx.response;

import java.util.List;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019-7-4
 * @description 民政接口返回实体
 */
public class OpenApiResponseHyxxModel {
    private String IsSuccess;
    private List<OpenApiResponseHyxxData> DataList;
    private String TotalCount;
    private String ExceptionMessage;
    private String Message;
    private String DataType;
    private String ErrorCode;
    private String Result;

    public String getIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        IsSuccess = isSuccess;
    }

    public List<OpenApiResponseHyxxData> getDataList() {
        return DataList;
    }

    public void setDataList(List<OpenApiResponseHyxxData> dataList) {
        DataList = dataList;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public String getExceptionMessage() {
        return ExceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        ExceptionMessage = exceptionMessage;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
