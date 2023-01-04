package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 房产存量房合同信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-26 11:12
 **/
public class FcjyClfHtxxDTO implements Serializable {
    private static final long serialVersionUID = -4481152740943713390L;

    @ApiModelProperty(value = "响应信息")
    private String msg;

    @ApiModelProperty(value = "响应码")
    private String code;

    @ApiModelProperty(value = "不动产受理房屋信息")
    private BdcSlFwxxDO bdcSlFwxx;

    @ApiModelProperty(value = "不动产受理交易信息")
    private BdcSlJyxxDO bdcSlJyxx;

    @ApiModelProperty(value = "不动产权利人")
    private List<BdcQlrDO> bdcQlr;

    @ApiModelProperty(value = "不动产受理申请人")
    private List<BdcSlSqrDO> bdcSlSqr;

    @ApiModelProperty(value = "不动产受理项目信息")
    private BdcSlXmDO bdcSlXmDO;

    @ApiModelProperty(value = "其他在用但非表字段属性")
    private Map qtAttrMap;

    @ApiModelProperty(value = "一体化获取到的文件信息")
    private List<TsswDataFjclXxDTO> fileList;

    @ApiModelProperty(value = "电子合同base64字符串")
    private String dzht;

    public List<TsswDataFjclXxDTO> getFileList() {
        return fileList;
    }

    public void setFileList(List<TsswDataFjclXxDTO> fileList) {
        this.fileList = fileList;
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

    public BdcSlJyxxDO getBdcSlJyxx() {
        return bdcSlJyxx;
    }

    public void setBdcSlJyxx(BdcSlJyxxDO bdcSlJyxx) {
        this.bdcSlJyxx = bdcSlJyxx;
    }

    @Override
    public String toString() {
        return "FcjyClfHtxxDTO{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", bdcSlFwxx=" + bdcSlFwxx +
                ", bdcSlJyxx=" + bdcSlJyxx +
                ", bdcQlr=" + bdcQlr +
                ", bdcSlSqr=" + bdcSlSqr +
                ", bdcSlXmDO=" + bdcSlXmDO +
                ", qtAttrMap=" + qtAttrMap +
                ", fileList=" + fileList +
                ", dzht='" + dzht + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BdcSlXmDO getBdcSlXmDO() {
        return bdcSlXmDO;
    }

    public void setBdcSlXmDO(BdcSlXmDO bdcSlXmDO) {
        this.bdcSlXmDO = bdcSlXmDO;
    }

    public Map getQtAttrMap() {
        return qtAttrMap;
    }

    public void setQtAttrMap(Map qtAttrMap) {
        this.qtAttrMap = qtAttrMap;
    }

    public String getDzht() {
        return dzht;
    }

    public void setDzht(String dzht) {
        this.dzht = dzht;
    }
}
