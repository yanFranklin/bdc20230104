package cn.gtmap.realestate.common.core.vo.config.ui;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/3/9
 * @description 打印数据配置VO
 */
public class BdcDysjPzVO {
    // 打印参数
    private List<Map<String, Object>> dycsMapList;
    // 主表信息
    private BdcDysjPzDO bdcDysjPzDO;
    // 子表信息
    private List<BdcDysjZbPzDO> bdcDysjZbPzDOList;


    public List<Map<String, Object>> getDycsMapList() {
        return dycsMapList;
    }

    public void setDycsMapList(List<Map<String, Object>> dycsMapList) {
        this.dycsMapList = dycsMapList;
    }

    public BdcDysjPzDO getBdcDysjPzDO() {
        return bdcDysjPzDO;
    }

    public void setBdcDysjPzDO(BdcDysjPzDO bdcDysjPzDO) {
        this.bdcDysjPzDO = bdcDysjPzDO;
    }

    public List<BdcDysjZbPzDO> getBdcDysjZbPzDOList() {
        return bdcDysjZbPzDOList;
    }

    public void setBdcDysjZbPzDOList(List<BdcDysjZbPzDO> bdcDysjZbPzDOList) {
        this.bdcDysjZbPzDOList = bdcDysjZbPzDOList;
    }

    @Override
    public String toString() {
        return "BdcDysjPzVO{" +
                "dycsMapList=" + dycsMapList +
                ", bdcDysjPzDO=" + bdcDysjPzDO +
                ", bdcDysjZbPzDOList=" + bdcDysjZbPzDOList +
                '}';
    }
}
