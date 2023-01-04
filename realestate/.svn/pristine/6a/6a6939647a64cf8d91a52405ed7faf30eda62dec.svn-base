package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcWtsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.ZipKinDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @return
* @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
* @description 委托书数据层
*/
public interface BdcWtsMapper {
    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 获取当天委托书数据
    */
    List<BdcWtsDO> getWtsDatas();

    /**
     * 更新委托书数据
     */
    Integer updateByWtsbh(@Param("wtsbh") String wtsbh);
}
