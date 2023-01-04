package cn.gtmap.realestate.inquiry.ui.core.qo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/3/2
 * @description 竣工验收备案附件信息查询条件
 */
public class JgysFjxxQO {

    @ApiModelProperty("分页-页")
    private Integer page;

    @ApiModelProperty("分页-行")
    private Integer rows;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目代码")
    private String projectCode;

    @ApiModelProperty("企业名称")
    private String contractor;

    @ApiModelProperty("企业统一信用代码")
    private String creditCode;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }
}
