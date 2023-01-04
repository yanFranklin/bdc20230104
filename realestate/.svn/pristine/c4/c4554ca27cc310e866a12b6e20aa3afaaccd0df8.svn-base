package cn.gtmap.realestate.common.core.qo.inquiry;

import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/8.
 * @description  蚌埠查询机查询房屋权属接口
 */
@ApiModel(value = "BdcFwqlQO", description = "不动产房屋权属查询封装对象")
public class BdcFwqlQO {
    /**
     * 权利人名称与证件号需要成对，避免单独分开名称和证件号
     */
    @ApiModelProperty(value = "权利人信息")
    private List<BdcFwqlQlrQO> qlrxx;

    /**
     *  2  自助查询机   目前只有蚌埠的自助查询机
     */
    @ApiModelProperty(value = "查询来源")
    private String cxly;

    /**
     * 查询单位名称（主要用于记录，不用于过滤）
     */
    @ApiModelProperty(value = "查询单位名称")
    private String cxdw;
    /**
     * 发起请求机器mac地址
     */
    @ApiModelProperty(value = "来源地址")
    private String lydz;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    @ApiModelProperty(value = "查询多幢信息")
    private boolean cxdz;

    @ApiModelProperty(value = "查询预告数据")
    private Boolean cxyg;

    @ApiModelProperty(value = "查询合同数据")
    private Boolean cxht;


    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public List<BdcFwqlQlrQO> getQlrxx() {
        return qlrxx;
    }

    public void setQlrxx(List<BdcFwqlQlrQO> qlrxx) {
        this.qlrxx = qlrxx;
    }

    public String getCxly() {
        return cxly;
    }

    public void setCxly(String cxly) {
        this.cxly = cxly;
    }

    public String getCxdw() {
        return cxdw;
    }

    public void setCxdw(String cxdw) {
        this.cxdw = cxdw;
    }

    public String getLydz() {
        return lydz;
    }

    public void setLydz(String lydz) {
        this.lydz = lydz;
    }

    public boolean getCxdz() {
        return cxdz;
    }

    public void setCxdz(boolean cxdz) {
        this.cxdz = cxdz;
    }

    public Boolean getCxyg() {
        return cxyg;
    }

    public void setCxyg(Boolean cxyg) {
        this.cxyg = cxyg;
    }

    public Boolean getCxht() {
        return cxht;
    }

    public void setCxht(Boolean cxht) {
        this.cxht = cxht;
    }
}
