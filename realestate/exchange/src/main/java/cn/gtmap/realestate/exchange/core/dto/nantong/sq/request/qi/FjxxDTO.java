package cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.qi;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @Description 南通水气过户请求参数
 * @Date 2022/6/14
 **/
public class FjxxDTO {
    /**
     * 材料名称--文件夹名称
     */
    private String clmc;
    /**
     * 材料内容--文件夹内容
     */
    private List<ClnrDTO> clnr;

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public List<ClnrDTO> getClnr() {
        return clnr;
    }

    public void setClnr(List<ClnrDTO> clnr) {
        this.clnr = clnr;
    }

    @Override
    public String toString() {
        return "FjxxDTO{" +
                "clmc='" + clmc + '\'' +
                ", clnr=" + clnr +
                '}';
    }
}
