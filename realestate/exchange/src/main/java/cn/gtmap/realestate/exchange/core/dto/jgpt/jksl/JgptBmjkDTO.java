package cn.gtmap.realestate.exchange.core.dto.jgpt.jksl;

import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(description = "部门接口数据返回DTO")
public class JgptBmjkDTO {

    private String bmmc;

    private Integer dycs;

    private List<JgptJkslDO> details;

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public Integer getDycs() {
        return dycs;
    }

    public void setDycs(Integer dycs) {
        this.dycs = dycs;
    }

    public List<JgptJkslDO> getDetails() {
        return details;
    }

    public void setDetails(List<JgptJkslDO> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "JgptBmjkDTO{" +
                "bmmc='" + bmmc + '\'' +
                ", dycs=" + dycs +
                ", details=" + details +
                '}';
    }
}
