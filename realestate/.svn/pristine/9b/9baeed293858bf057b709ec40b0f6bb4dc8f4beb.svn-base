package cn.gtmap.realestate.register.ui.core.vo;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;

/**
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/1/3
 * @description 补录流程VO
 */
public class BdcBllcVO {


    /**
     * 工作流实例 ID
     */
    private String processInsId;
    /**
     * 项目 ID
     */
    private String xmid;
    /**
     * 类型：具体分为check 和 update 两类
     */
    private String type;
    /**
     * 修改内容登记高亮显示djxl
     */
    private String xgnrglxs;
    /**
     * 流程类型
     */
    private String lclx;
    /**
     * 初始化项目相关信息
     */
    public void generateXmxx(BdcXmDO bdcXmDO){
        if (bdcXmDO != null) {
            this.processInsId = bdcXmDO.getGzlslid();
            this.xmid = bdcXmDO.getXmid();
        }
    }

    public String getProcessInsId() {
        return processInsId;
    }

    public void setProcessInsId(String processInsId) {
        this.processInsId = processInsId;
    }

    public String getXgnrglxs() {
        return xgnrglxs;
    }

    public void setXgnrglxs(String xgnrglxs) {
        this.xgnrglxs = xgnrglxs;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BdcBllcVO() {
    }

    public String getLclx() {
        return lclx;
    }

    public void setLclx(String lclx) {
        this.lclx = lclx;
    }
}
