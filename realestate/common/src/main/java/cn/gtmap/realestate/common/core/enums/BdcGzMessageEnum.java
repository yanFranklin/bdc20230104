package cn.gtmap.realestate.common.core.enums;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/10/19
 * @description 不动产规则短信操作参数枚举类
 */
public enum BdcGzMessageEnum {
    DELETEBDCGZZGZ("deleteBdcGzZgz", "删除", "子规则"),
    SAVEBDCGZZGZ("saveBdcGzZgz", "保存", "子规则"),
    INSERTBDCGZZHGZ("insertBdcGzZhGz", "新增", "组合规则"),
    DELBDCGZZHGZ("delBdcGzZhGz", "删除", "组合规则"),
    UPDATEBDCGZZHGZ("updateBdcGzZhGz", "修改", "组合规则");

    /**
     * code
     */
    private String method;
    /**
     * 动作 增 删 改
     */
    private String  action;

    /**
     * 规则类型 子规则 组合规则
     */
    private String gzlx;



    BdcGzMessageEnum(String method, String action, String gzlx) {
        this.method = method;
        this.action = action;
        this.gzlx = gzlx;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getGzlx() {
        return gzlx;
    }

    public void setGzlx(String gzlx) {
        this.gzlx = gzlx;
    }
}
