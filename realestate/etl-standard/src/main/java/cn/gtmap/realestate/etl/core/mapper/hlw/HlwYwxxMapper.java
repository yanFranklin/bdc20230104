package cn.gtmap.realestate.etl.core.mapper.hlw;

import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxmDo;
import cn.gtmap.realestate.etl.core.dto.GxWwBlztDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface HlwYwxxMapper {

    /**
     * @return 未创建互联网项目ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询未创建项目集合
     */
    List<GxWwSqxmDo> listWcjYhWwsqXmidList();

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据bdcdyh查询互联网办理状态
     */
    List<GxWwBlztDTO> listGxWwBlztDTOByBdcdyh(String bdcdyh);

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<String> listFailedWwsqXmidList(@Param("startTime") Date startTime,
                                             @Param("endTime") Date endTime
                                             );

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<String> listSuccessWwsqXmidList(@Param("startTime") Date startTime,
                                             @Param("endTime") Date endTime
    );

    /**
     *
     * @return
     */
    List<GxWwSqxmDo> listWwsqListByXmids(@Param("xmids") List<String> xmids);
}
