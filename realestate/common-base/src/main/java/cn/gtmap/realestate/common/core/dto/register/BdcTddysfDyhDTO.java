package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/13
 * @description 土地抵押释放单元号
 */
public class BdcTddysfDyhDTO {

    @ApiModelProperty(value = "生成方式：1：流程关联")
    private Integer scfs;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "不动产单元列表")
    private List<String> bdcdyhList;

    @ApiModelProperty(value = "例外原因")
    private String lwyy;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    public Integer getScfs() {
        return scfs;
    }

    public void setScfs(Integer scfs) {
        this.scfs = scfs;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public List<String> getBdcdyhList() {
        return bdcdyhList;
    }

    public void setBdcdyhList(List<String> bdcdyhList) {
        this.bdcdyhList = bdcdyhList;
    }

    public String getLwyy() {
        return lwyy;
    }

    public void setLwyy(String lwyy) {
        this.lwyy = lwyy;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }
}
