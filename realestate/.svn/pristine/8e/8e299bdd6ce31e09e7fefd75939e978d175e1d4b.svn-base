package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSfxmVO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-25
 * @description 收费信息实体
 */
public class BdcSfxxDTO {

    // 收费信息主表
    private BdcSlSfxxDO bdcSlSfxxDO;

    // 收费项目信息列表
    private List<BdcSlSfxmDO> bdcSlSfxmDOS;

    // 第三方需要信息
    private DsfSfxxDTO dsfSfxxDTO;

    /*
     * 收费信息数据处理收费项目单价
     * */
    private List<BdcSlSfxmVO> bdcSlSfxmVOList;

    /**
     * 单位代码
     */
    private String dwdm;

    /**
     * 单位代码+密钥通过md5加密生成的字符串
     */
    private String md5String;

    /**
     * 缴款类型 0：一般费用  1：税费统缴
     */
    private String jklx;

    /**
     * 批量收费金额合计【淮安登记费对公月结】
     */
    private Double plsfhj;

    public BdcSlSfxxDO getBdcSlSfxxDO() {
        return bdcSlSfxxDO;
    }

    public void setBdcSlSfxxDO(BdcSlSfxxDO bdcSlSfxxDO) {
        this.bdcSlSfxxDO = bdcSlSfxxDO;
    }

    public List<BdcSlSfxmDO> getBdcSlSfxmDOS() {
        return bdcSlSfxmDOS;
    }

    public void setBdcSlSfxmDOS(List<BdcSlSfxmDO> bdcSlSfxmDOS) {
        this.bdcSlSfxmDOS = bdcSlSfxmDOS;
    }

    public DsfSfxxDTO getDsfSfxxDTO() {
        return dsfSfxxDTO;
    }

    public void setDsfSfxxDTO(DsfSfxxDTO dsfSfxxDTO) {
        this.dsfSfxxDTO = dsfSfxxDTO;
    }

    public List<BdcSlSfxmVO> getBdcSlSfxmVOList() {
        return bdcSlSfxmVOList;
    }

    public void setBdcSlSfxmVOList(List<BdcSlSfxmVO> bdcSlSfxmVOList) {
        this.bdcSlSfxmVOList = bdcSlSfxmVOList;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getMd5String() {
        return md5String;
    }

    public void setMd5String(String md5String) {
        this.md5String = md5String;
    }

    public String getJklx() {
        return jklx;
    }

    public void setJklx(String jklx) {
        this.jklx = jklx;
    }

    public Double getPlsfhj() {
        return plsfhj;
    }

    public void setPlsfhj(Double plsfhj) {
        this.plsfhj = plsfhj;
    }

    @Override
    public String toString() {
        return "BdcSfxxDTO{" +
                "bdcSlSfxxDO=" + bdcSlSfxxDO +
                ", bdcSlSfxmDOS=" + bdcSlSfxmDOS +
                ", dsfSfxxDTO=" + dsfSfxxDTO +
                ", bdcSlSfxmVOList=" + bdcSlSfxmVOList +
                ", dwdm='" + dwdm + '\'' +
                ", md5String='" + md5String + '\'' +
                ", jklx='" + jklx + '\'' +
                ", plsfhj=" + plsfhj +
                '}';
    }
}
