package cn.gtmap.realestate.inquiry.core.mapper;

import java.util.List;

public interface BdcTbcxMapper {

    /**
     * 批量删除
     * @param list
     * @return
     */
    int deleteZctbs(List<String> list);

}
