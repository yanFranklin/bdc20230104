package cn.gtmap.realestate.common.core.vo.inquiry.count;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @description 根据项目统计部门工作量信息，返回结果
 */
public class BdcZqxGzltjXmxxVO {

    @ApiModelProperty(value = "人员名称")
    private String rymc;
    @ApiModelProperty(value = "人员id")
    private String ryid;
    @ApiModelProperty(value = "角色名称")
    private String jsmc;
    @ApiModelProperty(value = "部门名称")
    private String bmmc;
    @ApiModelProperty(value = "部门代码")
    private String bmdm;
    @ApiModelProperty(value = "受理收件量")
    private Integer sjl;
    @ApiModelProperty(value = "打印有房无房证明量")
    private Integer yfwfzmsl;
    @ApiModelProperty(value = "打印权属证明量")
    private Integer qszmsl;
    @ApiModelProperty(value = "打印登记簿量")
    private Integer djbsl;
    @ApiModelProperty(value = "综合查询量")
    private Integer zhcxsl;
    @ApiModelProperty(value = "缮证证书量")
    private Integer szzsl;
    @ApiModelProperty(value = "缮证证明量")
    private Integer szzml;
    @ApiModelProperty(value = "纸质证书量")
    private Integer zzzsl;
    @ApiModelProperty(value = "纸质证明量")
    private Integer zzzml;
    @ApiModelProperty(value = "电子证书量")
    private Integer dzzsl;
    @ApiModelProperty(value = "电子证明量")
    private Integer dzzml;
    @ApiModelProperty(value = "纸质与电子证书量")
    private Integer zzydzzsl;
    @ApiModelProperty(value = "纸质与电子证明量")
    private Integer zzydzzml;
    @ApiModelProperty(value = "登记类型数量Map, key：djlx, value:数量")
    private ConcurrentHashMap<Integer, Integer> djlxslMap;
    @ApiModelProperty(value = "合并流程收件量")
    private Integer hblcSjl;

    @ApiModelProperty(value = "发证量")
    private Integer fzl;

    @ApiModelProperty(value = "复审量")
    private Integer fsl;

    @ApiModelProperty(value = "重点流程工作量")
    private Integer zdlcgzl;

    public String getRymc() {
        return rymc;
    }

    public void setRymc(String rymc) {
        this.rymc = rymc;
    }

    public String getRyid() {
        return ryid;
    }

    public void setRyid(String ryid) {
        this.ryid = ryid;
    }

    public String getJsmc() {
        return jsmc;
    }

    public void setJsmc(String jsmc) {
        this.jsmc = jsmc;
    }

    public Integer getSjl() {
        return sjl;
    }

    public void setSjl(Integer sjl) {
        this.sjl = sjl;
    }

    public Integer getYfwfzmsl() {
        return yfwfzmsl;
    }

    public void setYfwfzmsl(Integer yfwfzmsl) {
        this.yfwfzmsl = yfwfzmsl;
    }

    public Integer getQszmsl() {
        return qszmsl;
    }

    public void setQszmsl(Integer qszmsl) {
        this.qszmsl = qszmsl;
    }

    public Integer getDjbsl() {
        return djbsl;
    }

    public void setDjbsl(Integer djbsl) {
        this.djbsl = djbsl;
    }

    public Integer getSzzsl() {
        return szzsl;
    }

    public void setSzzsl(Integer szzsl) {
        this.szzsl = szzsl;
    }

    public Integer getSzzml() {
        return szzml;
    }

    public void setSzzml(Integer szzml) {
        this.szzml = szzml;
    }

    public Integer getZzzsl() {
        return zzzsl;
    }

    public void setZzzsl(Integer zzzsl) {
        this.zzzsl = zzzsl;
    }

    public Integer getZzzml() {
        return zzzml;
    }

    public void setZzzml(Integer zzzml) {
        this.zzzml = zzzml;
    }

    public Integer getDzzsl() {
        return dzzsl;
    }

    public void setDzzsl(Integer dzzsl) {
        this.dzzsl = dzzsl;
    }

    public Integer getDzzml() {
        return dzzml;
    }

    public void setDzzml(Integer dzzml) {
        this.dzzml = dzzml;
    }

    public Integer getZzydzzsl() {
        return zzydzzsl;
    }

    public void setZzydzzsl(Integer zzydzzsl) {
        this.zzydzzsl = zzydzzsl;
    }

    public Integer getZzydzzml() {
        return zzydzzml;
    }

    public void setZzydzzml(Integer zzydzzml) {
        this.zzydzzml = zzydzzml;
    }


    public void addDjlxslMap(Map<Integer, Integer> djlxslMap){
        if(MapUtils.isEmpty(this.djlxslMap)){
            this.djlxslMap = new ConcurrentHashMap<>(djlxslMap.size());
            this.djlxslMap.putAll(djlxslMap);
        }else{
            this.djlxslMap.putAll(djlxslMap);
        }
    }
    public Map<Integer, Integer> getDjlxslMap() {
        return djlxslMap;
    }

    public void setDjlxslMap(ConcurrentHashMap<Integer, Integer> djlxslMap) {
        this.djlxslMap = djlxslMap;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getBmdm() {
        return bmdm;
    }

    public void setBmdm(String bmdm) {
        this.bmdm = bmdm;
    }

    public Integer getHblcSjl() {
        return hblcSjl;
    }

    public void setHblcSjl(Integer hblcSjl) {
        this.hblcSjl = hblcSjl;
    }

    public Integer getZhcxsl() {return zhcxsl;}

    public void setZhcxsl(Integer zhcxsl) {this.zhcxsl = zhcxsl;}

    public BdcZqxGzltjXmxxVO() {
    }

    public BdcZqxGzltjXmxxVO(String bmmc, String bmdm) {
        this.bmmc = bmmc;
        this.bmdm = bmdm;
    }

    public BdcZqxGzltjXmxxVO(String rymc, String ryid, String jsmc) {
        this.rymc = rymc;
        this.ryid = ryid;
        this.jsmc = jsmc;
    }

    /**
     * 根据统计维度获取统计的Key值
     */
    public String getTjKey(String tjwd){
        if(CommonConstantUtils.GZLTJ_TJWD_BM.equals(tjwd)){
            return this.bmdm;
        }
        if(CommonConstantUtils.GZLTJ_TJWD_RY.equals(tjwd)){
            return this.ryid;
        }
        return "";
    }

    public Integer getFzl() {
        return fzl;
    }

    public void setFzl(Integer fzl) {
        this.fzl = fzl;
    }

    public Integer getFsl() {
        return fsl;
    }

    public void setFsl(Integer fsl) {
        this.fsl = fsl;
    }

    public Integer getZdlcgzl() {return zdlcgzl;}

    public void setZdlcgzl(Integer zdlcgzl) {this.zdlcgzl = zdlcgzl;}
}
