package cn.gtmap.realestate.common.core.dto;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/8
 * @description 项目和项目附表
 */
public class BdcXmXmfbDTO extends BdcXmDO {

    /**
     * 权籍管理代码
     */
    private String qjgldm;

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public String toString() {
        return "BdcXmXmfbDTO{" +
                "qjgldm='" + qjgldm + '\'' +
                '}';
    }
}
