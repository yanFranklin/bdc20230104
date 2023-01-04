package cn.gtmap.realestate.portal.ui.core.dto;

import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.portal.ui.core.vo.BdcBtxyzVO;

import java.io.Serializable;
import java.util.List;

/**
 * 批量转发的验证对象
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0  2019/11/13.
 */
public class ForwardPLDTO implements Serializable {

    /**
     * 验证规则对象
     */
    private List<ForwardYzDTO> forwardYzDTOS;

    /**
     * 转发成功数
     */
    private String successMsg;

    /**
     * 转发失败数
     */
    private String failMsg;

    /**
     * 执行失败返回消息
     */
    private String message;

    /**
     * 是否通过规则验证，通过是 true
     */
    private boolean gzyzMsg = true;

    public boolean isGzyzMsg() {
        return gzyzMsg;
    }

    public void setGzyzMsg(boolean gzyzMsg) {
        this.gzyzMsg = gzyzMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public List<ForwardYzDTO> getForwardYzDTOS() {
        return forwardYzDTOS;
    }

    public void setForwardYzDTOS(List<ForwardYzDTO> forwardYzDTOS) {
        this.forwardYzDTOS = forwardYzDTOS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 生成转发数目消息信息
     * @param success 转发成功条数
     * @param fail 转发失败条数
     */
    public void initForwardSizeMsg(Integer success, Integer fail){
        if ( success > 0 ) {
            successMsg = "转发成功: " + success + "条";
        }
        if ( fail > 0 ) {
            failMsg = "转发失败: " + fail + "条";
        }
    }
    @Override
    public String toString() {
        return "ForwardPLDTO{" +
                "forwardYzDTOS=" + forwardYzDTOS +
                ", failMsg='" + failMsg + '\'' +
                ", successMsg='" + successMsg + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
