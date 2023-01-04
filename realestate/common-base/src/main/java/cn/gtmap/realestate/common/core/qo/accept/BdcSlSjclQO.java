package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 受理收件材料查询条件QO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-03-25 14:19
 **/
public class BdcSlSjclQO {

    @ApiModelProperty(name = "收件材料id")
    private String sjclid;

    @ApiModelProperty(name = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(name = "材料名称")
    private String clmc;

    @ApiModelProperty(name = "登记小类")
    private String djxl;

    @ApiModelProperty(name = "顺序号")
    private Integer sxh;

    @ApiModelProperty(name = "收取部门")
    private String sqbm;

    public String getSjclid() {
        return sjclid;
    }

    public void setSjclid(String sjclid) {
        this.sjclid = sjclid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getSqbm() {
        return sqbm;
    }

    public void setSqbm(String sqbm) {
        this.sqbm = sqbm;
    }
}
