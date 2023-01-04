package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

/**
 * @program: realestate
 * @description: 受理申请人信息和收费信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-07-17 13:47
 **/
public class BdcSlSqrSfxxDTO extends BdcSlSfxxDO implements Serializable {
    private static final long serialVersionUID = -5493696986532287064L;
    @ApiModelProperty(value = "名称")
    private String mc;
    @ApiModelProperty(value = "联系电话")
    private String lxdh;
    @ApiModelProperty(value = "代理人名称")
    private String dlrmc;
    @ApiModelProperty(value = "代理人联系电话")
    private String dlrlxdh;
    @ApiModelProperty(value = "收费项目信息")
    private List<BdcSlSfxmDO> bdcSlSfxmDOList;
    @ApiModelProperty(value = "是否按月结算")
    private boolean sfayjs;
    @ApiModelProperty(value = "是否小微企业")
    private boolean sfxwqy;
    @ApiModelProperty(value = "是否必须现场收费")
    private boolean xcsf;

    @Override
    public Double getXjze() {
        return xjze;
    }

    @Override
    public void setXjze(Double xjze) {
        this.xjze = xjze;
    }

    @ApiModelProperty(value = "小计总额")
    private Double xjze;

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getDlrmc() {
        return dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public String getDlrlxdh() {
        return dlrlxdh;
    }

    public void setDlrlxdh(String dlrlxdh) {
        this.dlrlxdh = dlrlxdh;
    }

    public List<BdcSlSfxmDO> getBdcSlSfxmDOList() {
        return bdcSlSfxmDOList;
    }

    public void setBdcSlSfxmDOList(List<BdcSlSfxmDO> bdcSlSfxmDOList) {
        this.bdcSlSfxmDOList = bdcSlSfxmDOList;
    }

    public boolean getSfayjs() {
        return sfayjs;
    }

    public void setSfayjs(boolean sfayjs) {
        this.sfayjs = sfayjs;
    }

    public boolean getSfxwqy() {
        return sfxwqy;
    }

    public void setSfxwqy(boolean sfxwqy) {
        this.sfxwqy = sfxwqy;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public boolean getXcsf() {
        return xcsf;
    }

    public void setXcsf(boolean xcsf) {
        this.xcsf = xcsf;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcSlSqrSfxxDTO.class.getSimpleName() + "[", "]")
                .add("mc='" + mc + "'")
                .add("lxdh='" + lxdh + "'")
                .add("dlrmc='" + dlrmc + "'")
                .add("dlrlxdh='" + dlrlxdh + "'")
                .add("bdcSlSfxmDOList=" + bdcSlSfxmDOList)
                .add("sfayjs=" + sfayjs)
                .add("sfxwqy=" + sfxwqy)
                .toString();
    }
}
