package cn.gtmap.realestate.common.core.enums;

import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhZtDTO;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 印制号核查状态（南通银行接口）
 */
public enum  BdcYzhZtEnum {
    STATUS_0(0, "接口参数为空或处理异常"),
    STATUS_1(1, "印制号不存在或被删除"),
    STATUS_2(2, "印制号存在，但是已经被使用"),
    STATUS_3(3, "印制号存在、未使用，但是该印制号不在当前用户权限下"),
    STATUS_4(4, "印制号存在、未使用，且在当前用户名下，可使用");

    private Integer code;

    private String msg;

    BdcYzhZtEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
