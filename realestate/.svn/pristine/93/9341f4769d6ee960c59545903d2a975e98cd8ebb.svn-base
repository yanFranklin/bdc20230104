package cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.jyqd;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/17
 * @description 推送住建存量房启动信息DTO
 */
@ApiModel(value = "ZjClfQdDTO", description = "推送住建存量房启动信息DTO")
public class ZjClfQdDTO {

    private ZjClfDjxxDTO djxx;

    private List<ZjClfFwxxDTO> fwxx;

    private List<ZjClfMsfxxDTO> msfxx;

    private List<ZjClfFjxxDTO> fjxx;

    @JSONField(name="CRFDZQM")
    private String CRFDZQM;

    @JSONField(name="MSFDZQM")
    private String MSFDZQM;

    public ZjClfDjxxDTO getDjxx() {
        return djxx;
    }

    public void setDjxx(ZjClfDjxxDTO djxx) {
        this.djxx = djxx;
    }

    public List<ZjClfFwxxDTO> getFwxx() {
        return fwxx;
    }

    public void setFwxx(List<ZjClfFwxxDTO> fwxx) {
        this.fwxx = fwxx;
    }

    public List<ZjClfMsfxxDTO> getMsfxx() {
        return msfxx;
    }

    public void setMsfxx(List<ZjClfMsfxxDTO> msfxx) {
        this.msfxx = msfxx;
    }

    public List<ZjClfFjxxDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<ZjClfFjxxDTO> fjxx) {
        this.fjxx = fjxx;
    }

    @JSONField(name="CRFDZQM")
    public String getCRFDZQM() {
        return CRFDZQM;
    }

    @JSONField(name="CRFDZQM")
    public void setCRFDZQM(String CRFDZQM) {
        this.CRFDZQM = CRFDZQM;
    }

    @JSONField(name="MSFDZQM")
    public String getMSFDZQM() {
        return MSFDZQM;
    }

    @JSONField(name="MSFDZQM")
    public void setMSFDZQM(String MSFDZQM) {
        this.MSFDZQM = MSFDZQM;
    }

    @Override
    public String toString() {
        return "ZjClfQdDTO{" +
                "djxx=" + djxx +
                ", fwxx=" + fwxx +
                ", msfxx=" + msfxx +
                ", fjxx=" + fjxx +
                ", CRFDZQM='" + CRFDZQM + '\'' +
                ", MSFDZQM='" + MSFDZQM + '\'' +
                '}';
    }
}
