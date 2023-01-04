package cn.gtmap.realestate.building.core.domain;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description
 */

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description 不动产单元编号
 */
@Table(name = "s_sj_maxbdcdyh")
public class SSjMaxBdcdyhDO {
    @Id
    /**
     * 主键
     */
    private String maxbdcdyhIndex;
    /**
     * 宗地宗海代码
     */
    private String zdzhdm;
    /**
     * 自然幢号
     */
    private String zrzh;
    /**
     * 最大流水号
     */
    private Integer maxlsh;
    /**
     * 乐观锁版本号
     */
    private Integer version;

    public Integer getMaxlsh() {
        return maxlsh;
    }

    public void setMaxlsh(Integer maxlsh) {
        this.maxlsh = maxlsh;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMaxbdcdyhIndex() {
        return maxbdcdyhIndex;
    }

    public void setMaxbdcdyhIndex(String maxbdcdyhIndex) {
        this.maxbdcdyhIndex = maxbdcdyhIndex;
    }

    public String getZdzhdm() {
        return zdzhdm;
    }

    public void setZdzhdm(String zdzhdm) {
        this.zdzhdm = zdzhdm;
    }

    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }

    @Override
    public String toString() {
        return "SSjMaxBdcdyhDO{" +
                "maxbdcdyhIndex='" + maxbdcdyhIndex + '\'' +
                ", zdzhdm='" + zdzhdm + '\'' +
                ", zrzh='" + zrzh + '\'' +
                ", maxlsh=" + maxlsh +
                ", version=" + version +
                '}';
    }
}
