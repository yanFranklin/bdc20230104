package cn.gtmap.realestate.exchange.core.dto.yancheng.yth;

import cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.DjtDjSlsqOldDO;

import java.io.Serializable;
import java.util.List;

/**
 * 盐城_一体化撤件接口请求参数
 */
public class YthCancelDTO extends YthCommonDTO implements Serializable {

    private static final long serialVersionUID = -1607055537199081301L;

    /**
     * 登记申请信息
     */
    private List<DjtDjSlsqOldDO> djtDjSlsqDOList;

    public List<DjtDjSlsqOldDO> getDjtDjSlsqDOList() {
        return djtDjSlsqDOList;
    }

    public void setDjtDjSlsqDOList(List<DjtDjSlsqOldDO> djtDjSlsqDOList) {
        this.djtDjSlsqDOList = djtDjSlsqDOList;
    }
}
