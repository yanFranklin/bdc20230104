package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-07-12
 * @description
 */
public interface BdcYyXxMapper {

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询异议
     */
    List listBdcYyByXmid(@Param("xmid") String xmid);

    List listBdcYyByBdcdyh(@Param("bdcdyh") String bdcdyh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询抵押信息
     */
    List<BdcYyDO> listBdcYyByBdcdyhs(@Param("list") List<String> bdcdyhList);
}
