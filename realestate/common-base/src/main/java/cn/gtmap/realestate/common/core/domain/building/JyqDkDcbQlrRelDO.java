package cn.gtmap.realestate.common.core.domain.building;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/4
 * @description 土地经营权地块与权利人关系
 */
@Table(name = "jyqdk_dcbqlr_rel")
public class JyqDkDcbQlrRelDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String dcbQlrRelIndex;

    @ApiModelProperty(value = "经营权调查表主键")
    private String jyqDkDcbIndex;

    @ApiModelProperty(value = "经营权权利人主键")
    private String jyqDkQlrIndex;

    public String getDcbQlrRelIndex() {
        return dcbQlrRelIndex;
    }

    public void setDcbQlrRelIndex(String dcbQlrRelIndex) {
        this.dcbQlrRelIndex = dcbQlrRelIndex;
    }

    public String getJyqDkDcbIndex() {
        return jyqDkDcbIndex;
    }

    public void setJyqDkDcbIndex(String jyqDkDcbIndex) {
        this.jyqDkDcbIndex = jyqDkDcbIndex;
    }

    public String getJyqDkQlrIndex() {
        return jyqDkQlrIndex;
    }

    public void setJyqDkQlrIndex(String jyqDkQlrIndex) {
        this.jyqDkQlrIndex = jyqDkQlrIndex;
    }
}
