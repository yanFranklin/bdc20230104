package cn.gtmap.realestate.common.core.dto.certificate;

import cn.gtmap.realestate.common.core.enums.BdcYzhZtEnum;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 印制号状态DTO
 */
public class BdcYzhZtDTO {
    /**
     * 状态标识
     */
    private Integer code;

    /**
     * 具体信息
     */
    private String msg;



    public BdcYzhZtDTO () {

    }

    public BdcYzhZtDTO(BdcYzhZtEnum bdcYzhZtEnum) {
        this.code = bdcYzhZtEnum.getCode();
        this.msg = bdcYzhZtEnum.getMsg();
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
