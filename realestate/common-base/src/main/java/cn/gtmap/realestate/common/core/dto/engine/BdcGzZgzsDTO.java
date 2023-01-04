package cn.gtmap.realestate.common.core.dto.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/5
 * @description 规则子系统子规则DTO定义
 */
public class BdcGzZgzsDTO {
    /**
     * 子规则集合
     */
    private List<BdcGzZgzDTO> bdcGzZgzDOList;

    public BdcGzZgzsDTO(){

    }

    public BdcGzZgzsDTO(BdcGzZgzDTO bdcGzZgzDTO){
        List<BdcGzZgzDTO> bdcGzZgzDOList = new ArrayList<>(1);
        bdcGzZgzDOList.add(bdcGzZgzDTO);
        this.bdcGzZgzDOList = bdcGzZgzDOList;
    }

    public List<BdcGzZgzDTO> getBdcGzZgzDOList() {
        return bdcGzZgzDOList;
    }

    public void setBdcGzZgzDOList(List<BdcGzZgzDTO> bdcGzZgzDOList) {
        this.bdcGzZgzDOList = bdcGzZgzDOList;
    }

    @Override
    public String toString() {
        return "BdcGzZgzsDTO{" +
                "bdcGzZgzDOList=" + bdcGzZgzDOList +
                '}';
    }
}
