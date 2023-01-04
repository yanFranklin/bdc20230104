package cn.gtmap.realestate.portal.ui.core.dto;

import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.portal.ui.core.vo.BdcBtxyzVO;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 转发的验证对象
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0  2019/11/13.
 */
public class ForwardYzDTO implements Serializable {

    /**
     * 验证规则对象
     */
    private List<BdcGzyzVO> bdcGzyzVOS;

    /**
     * 必填项验证对象
     */
    private List<BdcBtxyzVO> bdcBtxyzVOS;

    /**
     * 受理编号
     */
    private String slbh;
    /**
     * gzlslid
     */
    private String gzlslid;
    /**
     * 异常信息
     */
    private String errorMsg;

    /**
     * 转发信息
     */
    private String forwardMsg;

    public ForwardYzDTO() {
    }

    public ForwardYzDTO(String slbh) {
        this.slbh = slbh;
    }

    @Override
    public String toString() {
        return "ForwardYzDTO{" +
                "bdcGzyzVOS=" + bdcGzyzVOS +
                ", bdcBtxyzVOS=" + bdcBtxyzVOS +
                ", slbh='" + slbh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", forwardMsg='" + forwardMsg + '\'' +
                '}';
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public List<BdcGzyzVO> getBdcGzyzVOS() {
        return bdcGzyzVOS;
    }

    public void setBdcGzyzVOS(List<BdcGzyzVO> bdcGzyzVOS) {
        this.bdcGzyzVOS = bdcGzyzVOS;
    }

    public List<BdcBtxyzVO> getBdcBtxyzVOS() {
        return bdcBtxyzVOS;
    }

    public void setBdcBtxyzVOS(List<BdcBtxyzVO> bdcBtxyzVOS) {
        this.bdcBtxyzVOS = bdcBtxyzVOS;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 数据异常处理
     */
    public void generateDataError() {
        errorMsg = this.slbh +"数据存在问题;";
    }

    /**
     * 规则验证处理
     */
    public void generateGzyz(Object yzgz) {
        if (yzgz != null && yzgz instanceof List && ((List) yzgz).size() > 0) {
            bdcGzyzVOS = (List) yzgz;
        }
    }

    /**
     * 规则验证异常处理
     */
    public void generateGzyzError() {
        errorMsg = this.slbh +",验证规则出现异常;";
    }

    /**
     * 签名异常信息处理
     */
    public void generateSignError() {
        errorMsg = this.slbh +",签名失败;";
    }

    /**
     * 必填项信息处理
     */
    public void generateBtx(List<BdcBtxyzVO> btxyzVOS) {
        if (CollectionUtils.isNotEmpty(btxyzVOS)) {
            errorMsg = this.slbh +",必填项验证不通过;";
            bdcBtxyzVOS = btxyzVOS;
        }
    }

    /**
     * 必填项异常信息处理
     */
    public void generateBtxError() {
        errorMsg = this.slbh +",签名失败;";
    }

    public String getForwardMsg() {
        return forwardMsg;
    }

    public void setForwardMsg(String forwardMsg) {
        this.forwardMsg = forwardMsg;
    }
}
