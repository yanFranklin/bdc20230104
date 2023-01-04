package cn.gtmap.realestate.common.core.vo.config.ui;

import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/30
 * @description 默认意见配置展现类
 */
public class BdcXtMryjPzVO extends BdcXtMryjDO {

    private String cjrmc;

    public String getCjrmc() {
        return cjrmc;
    }

    public void setCjrmc(String cjrmc) {
        this.cjrmc = cjrmc;
    }


    @Override
    public String toString() {
        return "BdcXtMryjPzVO{" +
                "cjrmc='" + cjrmc + '\'' +
                "} " + super.toString();
    }
}
