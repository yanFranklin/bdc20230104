package cn.gtmap.realestate.common.core.dto.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/11 17:26
 * @description
 */
public class BdcGzZhgzDTO extends BdcGzZhgzDO {

     /**
      * 组合规则关联的子规则集合
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
      * @param
      * @return
      */
    private List<BdcGzZgzDTO> bdcGzZgzDTOList ;

    public List<BdcGzZgzDTO> getBdcGzZgzDTOList() {
        return bdcGzZgzDTOList;
    }

    public void setBdcGzZgzDTOList(List<BdcGzZgzDTO> bdcGzZgzDTOList) {
        this.bdcGzZgzDTOList = bdcGzZgzDTOList;
    }

    @Override
    public String toString() {
        return "BdcGzZhgzDTO{" +
                "bdcGzZgzDTOList=" + bdcGzZgzDTOList +
                '}';
    }
}
