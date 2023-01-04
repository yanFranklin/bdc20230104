package cn.gtmap.realestate.check.core.vo;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/3/9
 * @description
 */
public class SfhlGzVO {

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  规则ID集合
      */
    private List<String> gzidList;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  是否忽略
      */
    private Integer sfhl;

    public List<String> getGzidList() {
        return gzidList;
    }

    public void setGzidList(List<String> gzidList) {
        this.gzidList = gzidList;
    }

    public Integer getSfhl() {
        return sfhl;
    }

    public void setSfhl(Integer sfhl) {
        this.sfhl = sfhl;
    }

    @Override
    public String toString() {
        return "SfhlGzVO{" +
                "gzidList=" + gzidList +
                ", sfhl=" + sfhl +
                '}';
    }
}
