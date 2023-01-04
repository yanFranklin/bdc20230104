package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @program: realestate
 * @description: 收件材料创建上传DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-05-11 14:28
 **/
public class SjclUploadDTO {

    @ApiModelProperty("收件材料名称")
    @NotBlank(message = "材料名称不可为空")
    private String sjclmc;

    @ApiModelProperty("收件材料内容-base64")
    private String sjclnr;

    @ApiModelProperty("工作流实例id")
    @NotBlank(message = "工作流实例id不可为空")
    private String gzlslid;

    @ApiModelProperty("登记小类")
    private String djxl;

    @ApiModelProperty("多个附件信息")
    private List<TsswDataFjclXxDTO> fjList;


    public String getSjclmc() {
        return sjclmc;
    }

    public void setSjclmc(String sjclmc) {
        this.sjclmc = sjclmc;
    }

    public String getSjclnr() {
        return sjclnr;
    }

    public void setSjclnr(String sjclnr) {
        this.sjclnr = sjclnr;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public List<TsswDataFjclXxDTO> getFjList() {
        return fjList;
    }

    public void setFjList(List<TsswDataFjclXxDTO> fjList) {
        this.fjList = fjList;
    }

    @Override
    public String toString() {
        return "SjclUploadDTO{" +
                "sjclmc='" + sjclmc + '\'' +
                ", sjclnr='" + sjclnr + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", djxl='" + djxl + '\'' +
                ", fjList=" + fjList +
                '}';
    }
}
