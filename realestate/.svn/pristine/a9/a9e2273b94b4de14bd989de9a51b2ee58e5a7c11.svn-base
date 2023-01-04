package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @description: 电子证照接口查询返回的电子证照信息
 * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @create: 2020-7-10
 **/
public class DzzzZzxxResponseDTO {

    @ApiModelProperty(value = "电子证照详细信息")
    private List<DzzzZzxxDTO> data;
    @ApiModelProperty(value = "查询接口返回标识，成功：200")
    private String flag;
    @ApiModelProperty(value = "查询接口返回信息")
    private String result;

    public List<DzzzZzxxDTO> getData() {
        return data;
    }

    public void setData(List<DzzzZzxxDTO> data) {
        this.data = data;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DzzzZzxxResponseDTO{" +
                "data=" + data +
                ", flag='" + flag + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
