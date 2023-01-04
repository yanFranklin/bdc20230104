package cn.gtmap.realestate.inquiry.ui.core.dto;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/03/02
 * @description （标准版）综合查询配置项DTO
 */
public class BdcBzbZhcxPzDTO {
    /**
     * 查询证件号必填 配置
     */
    private String cxzjhbt;

    /**
     * 证明证件号必填 配置
     */
    private String zmzjhbt;

    /**
     * 档案系统地址
     */
    private String archiveUrl;

    /**
     * 查询目的
     */
    private String cxmd;

    private Boolean dadyj;

    /**
     * Excel全部导出的导出条数
     */
    private Integer dcts;


    private Map<String, String> cxdhmap;

    public String getCxmd() {
        return cxmd;
    }

    public void setCxmd(String cxmd) {
        this.cxmd = cxmd;
    }

    public String getCxzjhbt() {
        return cxzjhbt;
    }

    public void setCxzjhbt(String cxzjhbt) {
        this.cxzjhbt = cxzjhbt;
    }

    public String getZmzjhbt() {
        return zmzjhbt;
    }

    public void setZmzjhbt(String zmzjhbt) {
        this.zmzjhbt = zmzjhbt;
    }

    public String getArchiveUrl() {
        return archiveUrl;
    }

    public void setArchiveUrl(String archiveUrl) {
        this.archiveUrl = archiveUrl;
    }

    public Boolean getDadyj() {
        return dadyj;
    }

    public void setDadyj(Boolean dadyj) {
        this.dadyj = dadyj;
    }

    public Integer getDcts() {
        return dcts;
    }

    public void setDcts(Integer dcts) {
        this.dcts = dcts;
    }

    public Map<String, String> getCxdhmap() {
        return cxdhmap;
    }

    public void setCxdhmap(Map<String, String> cxdhmap) {
        this.cxdhmap = cxdhmap;
    }

    @Override
    public String toString() {
        return "BdcBzbZhcxPzDTO{" +
                "cxzjhbt='" + cxzjhbt + '\'' +
                ", zmzjhbt='" + zmzjhbt + '\'' +
                ", dadyj='" + dadyj + '\'' +
                ", dcts='" + dcts + '\'' +
                ", cxdhmap='" + cxdhmap + '\'' +
                '}';
    }
}
