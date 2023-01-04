package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/10/11
 * @description 不动产申请人收费统缴信息DTO
 */
public class BdcSqrSftjxxDTO {
    /**
     * 权利人类别
     */
    private String qlrlb;

    /**
     * 权利人名称
     */
    private String qlrmc;

    /**
     * 证件号
     */
    private String zjh;

    /**
     * 联系电话
     */
    private String lxdh;
    /**
     * 税费统缴收费、收税内容
     */
    private List<BdcSftjDTO> bdcSftjDTOList;
    /**
     * 税费统缴订单信息
     */
    private List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList;

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public List<BdcSftjDTO> getBdcSftjDTOList() {
        return bdcSftjDTOList;
    }

    public void setBdcSftjDTOList(List<BdcSftjDTO> bdcSftjDTOList) {
        this.bdcSftjDTOList = bdcSftjDTOList;
    }

    public List<BdcSlSfssDdxxDO> getBdcSlSfssDdxxDOList() {
        return bdcSlSfssDdxxDOList;
    }

    public void setBdcSlSfssDdxxDOList(List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList) {
        this.bdcSlSfssDdxxDOList = bdcSlSfssDdxxDOList;
    }

    @Override
    public String toString() {
        return "BdcSqrSftjxxDTO{" +
                "qlrlb='" + qlrlb + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", zjh='" + zjh + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", bdcSftjDTOList=" + bdcSftjDTOList +
                ", bdcSlSfssDdxxDOList=" + bdcSlSfssDdxxDOList +
                '}';
    }
}
