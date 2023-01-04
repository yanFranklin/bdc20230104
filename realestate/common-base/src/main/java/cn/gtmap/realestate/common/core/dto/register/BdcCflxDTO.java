package cn.gtmap.realestate.common.core.dto.register;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/02/22
 * @description 查封类型
 */
public class BdcCflxDTO {

    /**
     * 权利人类别
     */
    private String gzlslid;

    /**
     * 查封类型查询过滤
     */
    private List<Integer> cflxList;

    /**
     * 更新查封类型值
     */
    private Integer cflx;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public List<Integer> getCflxList() {
        return cflxList;
    }

    public void setCflxList(List<Integer> cflxList) {
        this.cflxList = cflxList;
    }

    public Integer getCflx() {
        return cflx;
    }

    public void setCflx(Integer cflx) {
        this.cflx = cflx;
    }
}
