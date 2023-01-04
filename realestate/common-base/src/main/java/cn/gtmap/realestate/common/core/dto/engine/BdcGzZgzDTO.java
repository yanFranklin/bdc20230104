package cn.gtmap.realestate.common.core.dto.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBdsTsxxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/4
 * @description 不动产子规则DTO实体定义
 */
public class BdcGzZgzDTO extends BdcGzZgzDO{
    /**
     * 子规则关联的数据流集合
     */
    private List<BdcGzSjlDTO> bdcGzSjlDTOList;

    /**
     * 子规则关联的表达式、提示信息集合
     */
    private List<BdcGzBdsTsxxDO> bdcGzBdsTsxxDOList;


    public List<BdcGzSjlDTO> getBdcGzSjlDTOList() {
        return bdcGzSjlDTOList;
    }

    public void setBdcGzSjlDTOList(List<BdcGzSjlDTO> bdcGzSjlDTOList) {
        this.bdcGzSjlDTOList = bdcGzSjlDTOList;
    }

    public List<BdcGzBdsTsxxDO> getBdcGzBdsTsxxDOList() {
        return bdcGzBdsTsxxDOList;
    }

    public void setBdcGzBdsTsxxDOList(List<BdcGzBdsTsxxDO> bdcGzBdsTsxxDOList) {
        this.bdcGzBdsTsxxDOList = bdcGzBdsTsxxDOList;
    }

    @Override
    public String toString() {
        return "BdcGzZgzDTO{" +
                "bdcGzSjlDTOList=" + bdcGzSjlDTOList +
                ", bdcGzBdsTsxxDOList=" + bdcGzBdsTsxxDOList +
                "} " + super.toString();
    }
}
