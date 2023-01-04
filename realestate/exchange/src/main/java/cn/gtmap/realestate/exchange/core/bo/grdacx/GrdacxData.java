package cn.gtmap.realestate.exchange.core.bo.grdacx;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-02
 * @description
 */
public class GrdacxData {

    private BdcFdcqQlxxBO bdcFdcqQlxxBO;
    private BdcTdsyqQlxxBO bdcTdsyqQlxxBO;
    private BdcJsydsyqQlxxBO bdcJsydsyqQlxxBO;

    private List<DyQlWithXmQlrDTO> dyaqList;

    private List<CfQlWithXmQlrDTO> cfList;

    private List<YyQlWithXmQlrDTO> yyList;

    private List<YgQlWithXmQlrDTO> ygList;

    private List<JzqQlWithXmQlrDTO> jzqList;

    private List<BdcZssdDO> zssdList;

    private List<BdcDysdDO> dysdList;

    private boolean hasCf;

    private boolean hasDy;

    private boolean hasYy;

    private List sdList;

    //结合单元锁定和证书锁定共同判断赋值，仅做状态判断使用，不做详细数据返回使用
    public List getSdList() {
        return sdList;
    }

    //查询预告信息接口，只用bdcdyh查询，是为true，否为false
    public boolean onlyBdcdyhQuery;

    //查询权籍坐落
    public String qjzl;

    //查询权籍坐落
    public String tdzh;

    public String getTdzh() {
        return tdzh;
    }

    public void setTdzh(String tdzh) {
        this.tdzh = tdzh;
    }

    public String getQjzl() {return qjzl;}

    public void setQjzl(String qjzl) {this.qjzl = qjzl;}

    public boolean isOnlyBdcdyhQuery() {
        return onlyBdcdyhQuery;
    }

    public void setOnlyBdcdyhQuery(boolean onlyBdcdyhQuery) {
        this.onlyBdcdyhQuery = onlyBdcdyhQuery;
    }

    public void setSdList(List sdList) {
        this.sdList = sdList;
    }

    /**
     * @param
     * @return cn.gtmap.realestate.common.core.domain.BdcXmDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 主产权或预告权利的项目信息
     */
    public BdcXmDO gtMainBdcXm() {
        if (this.getBdcFdcqQlxxBO() != null) {
            return this.getBdcFdcqQlxxBO().getBdcXmDO();
        }
        if (this.getBdcTdsyqQlxxBO() != null) {
            return this.getBdcTdsyqQlxxBO().getBdcXmDO();
        }
        if (this.getBdcJsydsyqQlxxBO() != null) {
            return this.getBdcJsydsyqQlxxBO().getBdcXmDO();
        }
        if (CollectionUtils.isNotEmpty(this.getYgList())) {
            for (YgQlWithXmQlrDTO ygDto : this.getYgList()) {
                if (CommonConstantUtils.YGDJZL_YSSPFYG.equals(ygDto.getBdcql().getYgdjzl())
                        || CommonConstantUtils.YGDJZL_QTYG.equals(ygDto.getBdcql().getYgdjzl())) {
                    return ygDto.getBdcXmDO();
                }
            }
        }
        return null;
    }


    public BdcTdsyqQlxxBO getBdcTdsyqQlxxBO() {
        return bdcTdsyqQlxxBO;
    }

    public void setBdcTdsyqQlxxBO(BdcTdsyqQlxxBO bdcTdsyqQlxxBO) {
        this.bdcTdsyqQlxxBO = bdcTdsyqQlxxBO;
    }

    public BdcJsydsyqQlxxBO getBdcJsydsyqQlxxBO() {
        return bdcJsydsyqQlxxBO;
    }

    public void setBdcJsydsyqQlxxBO(BdcJsydsyqQlxxBO bdcJsydsyqQlxxBO) {
        this.bdcJsydsyqQlxxBO = bdcJsydsyqQlxxBO;
    }

    public List<BdcZssdDO> getZssdList() {
        return zssdList;
    }

    public void setZssdList(List<BdcZssdDO> zssdList) {
        this.zssdList = zssdList;
    }

    public List<BdcDysdDO> getDysdList() {
        return dysdList;
    }

    public void setDysdList(List<BdcDysdDO> dysdList) {
        this.dysdList = dysdList;
    }

    public BdcFdcqQlxxBO getBdcFdcqQlxxBO() {
        return bdcFdcqQlxxBO;
    }

    public void setBdcFdcqQlxxBO(BdcFdcqQlxxBO bdcFdcqQlxxBO) {
        this.bdcFdcqQlxxBO = bdcFdcqQlxxBO;
    }

    public List<DyQlWithXmQlrDTO> getDyaqList() {
        return dyaqList;
    }

    public void setDyaqList(List<DyQlWithXmQlrDTO> dyaqList) {
        if (CollectionUtils.isNotEmpty(dyaqList)) {
            this.hasDy = true;
        }
        this.dyaqList = dyaqList;
    }

    public List<CfQlWithXmQlrDTO> getCfList() {
        return cfList;
    }

    public void setCfList(List<CfQlWithXmQlrDTO> cfList) {
        if (CollectionUtils.isNotEmpty(cfList)) {
            this.hasCf = true;
        }
        this.cfList = cfList;
    }

    public List<YyQlWithXmQlrDTO> getYyList() {
        return yyList;
    }

    public void setYyList(List<YyQlWithXmQlrDTO> yyList) {
        if (CollectionUtils.isNotEmpty(yyList)) {
            this.hasYy = true;
        }
        this.yyList = yyList;
    }

    public List<YgQlWithXmQlrDTO> getYgList() {
        return ygList;
    }

    public void setYgList(List<YgQlWithXmQlrDTO> ygList) {
        this.ygList = ygList;
    }

    public boolean isHasCf() {
        return hasCf;
    }

    public void setHasCf(boolean hasCf) {
        this.hasCf = hasCf;
    }

    public boolean isHasDy() {
        return hasDy;
    }

    public void setHasDy(boolean hasDy) {
        this.hasDy = hasDy;
    }

    public boolean isHasYy() {
        return hasYy;
    }

    public void setHasYy(boolean hasYy) {
        this.hasYy = hasYy;
    }

    public List<JzqQlWithXmQlrDTO> getJzqList() {
        return jzqList;
    }

    public void setJzqList(List<JzqQlWithXmQlrDTO> jzqList) {
        this.jzqList = jzqList;
    }
}
