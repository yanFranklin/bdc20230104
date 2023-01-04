package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.dto.inquiry.ZipKinDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/07/18
 * @description
 */
public interface ZipKinMapper {
    /**
     * @return
     * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
     * @description 获取指定时间段数据
     *  @param startDate 起始时间
     * @param endDate 终止时间
     */
    List<ZipKinDTO> getTodayDatas(@Param("startDate")String startDate,@Param("endDate")String endDate);
}
