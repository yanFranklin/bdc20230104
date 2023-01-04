package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.qo.accept.FcjyxxQO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/8/2
 * @description 房产交易备案信息（调用fcjybaxxbycqzhandzjh返回结果)
 */
@ApiModel(value = "FcjyBaxxDTO", description = "房产交易备案信息")
public class FcjyBaxxDTO {

    @ApiModelProperty(value = "响应信息")
    private String msg;

    @ApiModelProperty(value = "不动产受理交易信息")
    private BdcSlJyxxDO bdcSlJyxx;

    @ApiModelProperty(value = "响应码")
    private String code;

    @ApiModelProperty(value = "不动产权利人")
    private List<BdcQlrDO> bdcQlr;

    @ApiModelProperty(value = "不动产受理申请人")
    private List<BdcSlSqrDO> bdcSlSqr;

    @ApiModelProperty(value = "不动产受理房屋信息")
    private BdcSlFwxxDO bdcSlFwxx;

    @ApiModelProperty(value = "房屋信息集合")
    private List<BdcSlFwxxDO> bdcSlFwxxDOList;

    @ApiModelProperty(value = "不动产受理项目信息")
    private BdcSlXmDO bdcSlXmDO;

    @ApiModelProperty(value = "用于查询交易信息后直接返回数据进行页面展示")
    private Map<String, Object> jyxxMap;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "合同附件")
    private String htBase64;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "是否更新受理交易信息，默认false")
    private boolean modifyJyxx = false;

    @ApiModelProperty(value = "是否更新受理房屋信息，默认false")
    private boolean modifyFwxx = false;

    @ApiModelProperty(value = "是否更新登记项目与权利信息，默认false")
    private boolean modifyXmAndQlxx = false;

    @ApiModelProperty(value = "是否更新权利人信息，默认false")
    private boolean modifyQlrxx = false;

    @ApiModelProperty(value = "是否更新第三权利人信息，默认false")
    private boolean modifyDsQlr = false;

    @ApiModelProperty(value = "房产交易查询对象")
    private FcjyxxQO fcjyxxQO;

    @ApiModelProperty(value = "受理抵押集合(贷款方式：商业)")
    private List<BdcSlDyaqDTO> listBdcSlDyaqDTOSy;

    @ApiModelProperty(value = "受理抵押集合(贷款方式：公积金)")
    private List<BdcSlDyaqDTO> listBdcSlDyaqDTOGjj;

    public String getHtBase64() {
        return htBase64;
    }

    public void setHtBase64(String htBase64) {
        this.htBase64 = htBase64;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BdcSlJyxxDO getBdcSlJyxx() {
        return bdcSlJyxx;
    }

    public void setBdcSlJyxx(BdcSlJyxxDO bdcSlJyxx) {
        this.bdcSlJyxx = bdcSlJyxx;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<BdcQlrDO> getBdcQlr() {
        return bdcQlr;
    }

    public void setBdcQlr(List<BdcQlrDO> bdcQlr) {
        this.bdcQlr = bdcQlr;
    }

    public List<BdcSlSqrDO> getBdcSlSqr() {
        return bdcSlSqr;
    }

    public void setBdcSlSqr(List<BdcSlSqrDO> bdcSlSqr) {
        this.bdcSlSqr = bdcSlSqr;
    }

    public BdcSlFwxxDO getBdcSlFwxx() {
        return bdcSlFwxx;
    }

    public void setBdcSlFwxx(BdcSlFwxxDO bdcSlFwxx) {
        this.bdcSlFwxx = bdcSlFwxx;
    }

    public BdcSlXmDO getBdcSlXmDO() {
        return bdcSlXmDO;
    }

    public void setBdcSlXmDO(BdcSlXmDO bdcSlXmDO) {
        this.bdcSlXmDO = bdcSlXmDO;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public Map<String, Object> getJyxxMap() {
        return jyxxMap;
    }

    public void setJyxxMap(Map<String, Object> jyxxMap) {
        this.jyxxMap = jyxxMap;
    }


    public boolean isModifyJyxx() {
        return modifyJyxx;
    }

    public void setModifyJyxx(boolean modifyJyxx) {
        this.modifyJyxx = modifyJyxx;
    }

    public boolean isModifyFwxx() {
        return modifyFwxx;
    }

    public void setModifyFwxx(boolean modifyFwxx) {
        this.modifyFwxx = modifyFwxx;
    }

    public boolean isModifyXmAndQlxx() {
        return modifyXmAndQlxx;
    }

    public void setModifyXmAndQlxx(boolean modifyXmAndQlxx) {
        this.modifyXmAndQlxx = modifyXmAndQlxx;
    }

    public boolean isModifyQlrxx() {
        return modifyQlrxx;
    }

    public void setModifyQlrxx(boolean modifyQlrxx) {
        this.modifyQlrxx = modifyQlrxx;
    }

    public FcjyxxQO getFcjyxxQO() {
        return fcjyxxQO;
    }

    public void setFcjyxxQO(FcjyxxQO fcjyxxQO) {
        this.fcjyxxQO = fcjyxxQO;
    }

    public List<BdcSlDyaqDTO> getListBdcSlDyaqDTOSy() {
        return listBdcSlDyaqDTOSy;
    }

    public void setListBdcSlDyaqDTOSy(List<BdcSlDyaqDTO> listBdcSlDyaqDTOSy) {
        this.listBdcSlDyaqDTOSy = listBdcSlDyaqDTOSy;
    }

    public List<BdcSlDyaqDTO> getListBdcSlDyaqDTOGjj() {
        return listBdcSlDyaqDTOGjj;
    }

    public void setListBdcSlDyaqDTOGjj(List<BdcSlDyaqDTO> listBdcSlDyaqDTOGjj) {
        this.listBdcSlDyaqDTOGjj = listBdcSlDyaqDTOGjj;
    }

    public boolean isModifyDsQlr() {
        return modifyDsQlr;
    }

    public void setModifyDsQlr(boolean modifyDsQlr) {
        this.modifyDsQlr = modifyDsQlr;
    }


    public List<BdcSlFwxxDO> getBdcSlFwxxDOList() {
        return bdcSlFwxxDOList;
    }

    public void setBdcSlFwxxDOList(List<BdcSlFwxxDO> bdcSlFwxxDOList) {
        this.bdcSlFwxxDOList = bdcSlFwxxDOList;
    }

    @Override
    public String toString() {
        return "FcjyBaxxDTO{" +
                "msg='" + msg + '\'' +
                ", bdcSlJyxx=" + bdcSlJyxx +
                ", code='" + code + '\'' +
                ", bdcQlr=" + bdcQlr +
                ", bdcSlSqr=" + bdcSlSqr +
                ", bdcSlFwxx=" + bdcSlFwxx +
                ", bdcSlFwxxDOList=" + bdcSlFwxxDOList +
                ", bdcSlXmDO=" + bdcSlXmDO +
                ", jyxxMap=" + jyxxMap +
                ", xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", modifyJyxx=" + modifyJyxx +
                ", modifyFwxx=" + modifyFwxx +
                ", modifyXmAndQlxx=" + modifyXmAndQlxx +
                ", modifyQlrxx=" + modifyQlrxx +
                ", modifyDsQlr=" + modifyDsQlr +
                ", fcjyxxQO=" + fcjyxxQO +
                ", listBdcSlDyaqDTOSy=" + listBdcSlDyaqDTOSy +
                ", listBdcSlDyaqDTOGjj=" + listBdcSlDyaqDTOGjj +
                '}';
    }
}
