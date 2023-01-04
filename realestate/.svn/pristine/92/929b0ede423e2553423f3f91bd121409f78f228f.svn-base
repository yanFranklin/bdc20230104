package cn.gtmap.realestate.common.core.dto.engine;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description  强制验证DTO定义
 */
public class BdcGzQzyzYzDTO {
    @ApiModelProperty(value = "状态编码")
    private String code;

    @ApiModelProperty(value = "提示信息")
    private String msg;

    @ApiModelProperty(value = "详细内容")
    private Object detail;

    @ApiModelProperty(value = "未配置强制验证的流程")
    private List<String> proList;

    @ApiModelProperty(value = "缺少配置基本子规则的强制验证")
    private Map<String, List<String>> qzyzMap;


    public BdcGzQzyzYzDTO(){

    }

    public BdcGzQzyzYzDTO(String code, String msg, Object detail){
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public List<String> getProList() {
        return proList;
    }

    public void setProList(List<String> proList) {
        this.proList = proList;
    }

    public Map<String, List<String>> getQzyzMap() {
        return qzyzMap;
    }

    public void setQzyzMap(Map<String, List<String>> qzyzMap) {
        this.qzyzMap = qzyzMap;
    }
}
