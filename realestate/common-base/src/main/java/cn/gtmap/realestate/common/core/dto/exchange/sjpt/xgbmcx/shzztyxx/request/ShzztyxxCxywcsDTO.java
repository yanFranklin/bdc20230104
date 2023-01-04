package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.shzztyxx.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-11-02
 * @description
 */
public class ShzztyxxCxywcsDTO {
    /**
     * 统一社会信用代码
     */
    private String tyshxydm;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织类型
     */
    private String search_type;

    public String getTyshxydm() {
        return tyshxydm;
    }

    public void setTyshxydm(String tyshxydm) {
        this.tyshxydm = tyshxydm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearch_type() {
        return search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }

    @Override
    public String toString() {
        return "ShzztyxxRequestCxywcs{" +
                "tyshxydm='" + tyshxydm + '\'' +
                ", name='" + name + '\'' +
                ", search_type='" + search_type + '\'' +
                '}';
    }
}
