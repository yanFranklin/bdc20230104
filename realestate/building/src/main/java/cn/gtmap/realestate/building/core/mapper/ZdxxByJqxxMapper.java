package cn.gtmap.realestate.building.core.mapper;

import cn.gtmap.realestate.common.core.dto.building.ZdxxByJqxxDTO;
import cn.gtmap.realestate.common.core.qo.building.ZdxxByJqxxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
 * @version 1.0  2021-11-17
 * @description 根据籍权信息查询宗地信息查询
 */
public interface ZdxxByJqxxMapper {

    /**
     * 根据坐落，不动产单元号，地籍号查询 宗地信息 ZdDjdcb
     *
     * @param zdxxByJqxxQO
     * @return
     */
    List<ZdxxByJqxxDTO> findZdDjdcb(ZdxxByJqxxQO zdxxByJqxxQO);

    /**
     * 根据不动产单元号的前18位匹配数据，用于只传了证书入参+其他入参的情况
     *
     * @param bdcdyh
     * @return
     */
    List<ZdxxByJqxxDTO> findZdDjdcbByBdcdyh(@Param("bdcdyh") String bdcdyh);

}
