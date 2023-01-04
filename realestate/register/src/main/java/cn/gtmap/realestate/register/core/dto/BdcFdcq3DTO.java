package cn.gtmap.realestate.register.core.dto;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3DO;
import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/12
 * @description 建筑物区分所有权业主共有部分登记信息DTO
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcFdcq3DTO.class)
public class BdcFdcq3DTO implements BdcQl {
    /**
     * 建筑物区分所有权业主共有部分登记信息
     */
    @ApiModelProperty(value = "建筑物区分所有权业主共有部分登记信息")
    private BdcFdcq3DO bdcFdcq3DO;

    /**
     * 建筑物区分所有权业主共有部分登记信息_共有部分
     */
    @ApiModelProperty(value = "建筑物区分所有权业主共有部分登记信息_共有部分")
    private List<BdcFdcq3GyxxDO> bdcFdcq3GyxxDOList;

    public BdcFdcq3DO getBdcFdcq3DO() {
        return bdcFdcq3DO;
    }

    public void setBdcFdcq3DO(BdcFdcq3DO bdcFdcq3DO) {
        this.bdcFdcq3DO = bdcFdcq3DO;
    }

    public List<BdcFdcq3GyxxDO> getBdcFdcq3GyxxDOList() {
        return bdcFdcq3GyxxDOList;
    }

    public void setBdcFdcq3GyxxDOList(List<BdcFdcq3GyxxDO> bdcFdcq3GyxxDOList) {
        this.bdcFdcq3GyxxDOList = bdcFdcq3GyxxDOList;
    }

    @Override
    public String toString() {
        return "BdcFdcq3DTO{" +
                "bdcFdcq3DO=" + bdcFdcq3DO +
                ", bdcFdcq3GyxxDOList=" + bdcFdcq3GyxxDOList +
                '}';
    }

    @Override
    public String getQlid() {
        return bdcFdcq3DO.getQlid();
    }

    @Override
    public void setQlid(String qlid) {
        bdcFdcq3DO.setQlid(qlid);
    }

    @Override
    public String getSlbh() {
        return bdcFdcq3DO.getSlbh();
    }

    @Override
    public void setSlbh(String slbh) {
        bdcFdcq3DO.setSlbh(slbh);
    }

    @Override
    public String getXmid() {
        return bdcFdcq3DO.getXmid();
    }

    @Override
    public void setXmid(String xmid) {
        bdcFdcq3DO.setXmid(xmid);
    }

    @Override
    public String getGyqk() {
        return bdcFdcq3DO.getGyqk();
    }

    @Override
    public void setGyqk(String gyqk) {
        bdcFdcq3DO.setGyqk(gyqk);
    }

    @Override
    public Date getDjsj() {
        return bdcFdcq3DO.getDjsj();
    }

    @Override
    public void setDjsj(Date djsj) {
        bdcFdcq3DO.setDjsj(djsj);
    }

    @Override
    public String getDbr() {
        return bdcFdcq3DO.getDbr();
    }

    @Override
    public void setDbr(String dbr) {
        bdcFdcq3DO.setDbr(dbr);
    }

    @Override
    public String getFj() {
        return bdcFdcq3DO.getFj();
    }

    @Override
    public void setFj(String fj) {
        bdcFdcq3DO.setFj(fj);
    }

    @Override
    public Integer getQszt() {
        return bdcFdcq3DO.getQszt();
    }

    @Override
    public void setQszt(Integer qszt) {
        bdcFdcq3DO.setQszt(qszt);
    }

    @Override
    public String getDjjg() {
        return bdcFdcq3DO.getDjjg();
    }

    @Override
    public void setDjjg(String djjg) {
        bdcFdcq3DO.setDjjg(djjg);
    }
}
