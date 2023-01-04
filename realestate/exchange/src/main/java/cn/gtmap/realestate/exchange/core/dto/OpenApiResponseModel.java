package cn.gtmap.realestate.exchange.core.dto;

import cn.gtmap.realestate.exchange.core.dto.hefei.hyxx.response.OpenApiResponseHyxxData;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-05
 * @description
 */
public class OpenApiResponseModel<T> {

    private String IsSuccess;
    private List<T> DataList;
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

    public List<T> getDataList() {
        return DataList;
    }

    public void setDataList(List<T> dataList) {
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
