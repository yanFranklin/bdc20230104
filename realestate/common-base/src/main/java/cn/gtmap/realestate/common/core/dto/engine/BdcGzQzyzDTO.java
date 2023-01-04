package cn.gtmap.realestate.common.core.dto.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzGxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/10/01
 * @description 强制验证信息实体DTO
 */
public class BdcGzQzyzDTO {
    /**
     * 强制验证 组合规则集合
     */
    private List<BdcGzZhgzDO> bdcGzZhgzDOList;

    /**
     * 强制验证 组合规则和子规则关联关系集合
     */
    private List<BdcGzGxDO> bdcGzGxDOList;

    /**
     * 强制验证 关联的子规则信息集合
     */
    private List<BdcGzZgzDTO> bdcGzZgzDTOList;


    public List<BdcGzZhgzDO> getBdcGzZhgzDOList() {
        return bdcGzZhgzDOList;
    }

    public void setBdcGzZhgzDOList(List<BdcGzZhgzDO> bdcGzZhgzDOList) {
        this.bdcGzZhgzDOList = bdcGzZhgzDOList;
    }

    public List<BdcGzGxDO> getBdcGzGxDOList() {
        return bdcGzGxDOList;
    }

    public void setBdcGzGxDOList(List<BdcGzGxDO> bdcGzGxDOList) {
        this.bdcGzGxDOList = bdcGzGxDOList;
    }

    public List<BdcGzZgzDTO> getBdcGzZgzDTOList() {
        return bdcGzZgzDTOList;
    }

    public void setBdcGzZgzDTOList(List<BdcGzZgzDTO> bdcGzZgzDTOList) {
        this.bdcGzZgzDTOList = bdcGzZgzDTOList;
    }

    @Override
    public String toString() {
        return "BdcGzQzyzDTO{" +
                "bdcGzZhgzDOList=" + bdcGzZhgzDOList +
                ", bdcGzGxDOList=" + bdcGzGxDOList +
                ", bdcGzZgzDTOList=" + bdcGzZgzDTOList +
                '}';
    }
}
