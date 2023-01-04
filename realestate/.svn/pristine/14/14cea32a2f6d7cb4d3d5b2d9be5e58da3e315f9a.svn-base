package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/06/23/20:39
 * @Description:
 */
@Api(value = "BdcCqTjQO", description = "超期统计查询对象")
public class BdcCqTjQO {
    @ApiModelProperty("开始时间")
    private String kssj;
    @ApiModelProperty("截止时间")
    private String jzsj;
    @ApiModelProperty("工作流定义id")
    private String processDefKey;
    @ApiModelProperty("图表统计分类")
    private String tjlx;

    @ApiModelProperty(value = "分页当前页码")
    private int page;
    @ApiModelProperty(value = "分页每页数据量")
    private int size;

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }

    public String getProcessDefKey() {
        return processDefKey;
    }

    public void setProcessDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTjlx() {
        return tjlx;
    }

    public void setTjlx(String tjlx) {
        this.tjlx = tjlx;
    }

    @Override
    public String toString() {
        return "BdcCqTjQO{" +
                "kssj='" + kssj + '\'' +
                ", jzsj='" + jzsj + '\'' +
                ", processDefKey='" + processDefKey + '\'' +
                ", tjlx='" + tjlx + '\'' +
                ", page=" + page +
                ", size=" + size +
                '}';
    }
}
