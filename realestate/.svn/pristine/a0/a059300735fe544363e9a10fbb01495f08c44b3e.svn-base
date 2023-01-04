package cn.gtmap.realestate.common.core.domain.engine;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/3/18
 * @description  不动产子规则查询实体QO
 */
public class BdcGzZgzQO extends BdcGzZgzDO{
    /**
     * 关联子规则：用于组合规则查询关联的子规则，1 是 2 否
     */
    private String glzgz;

    /**
     * 组合规则ID
     */
    private String zhid;



    public String getGlzgz() {
        return glzgz;
    }

    public void setGlzgz(String glzgz) {
        this.glzgz = glzgz;
    }

    public String getZhid() {
        return zhid;
    }

    public void setZhid(String zhid) {
        this.zhid = zhid;
    }

    @Override
    public String toString() {
        return "BdcGzZgzQO{" +
                "glzgz='" + glzgz + '\'' +
                ", zhid='" + zhid + '\'' +
                '}';
    }
}
