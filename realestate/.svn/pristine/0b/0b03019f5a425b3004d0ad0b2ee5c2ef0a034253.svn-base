package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.dto.inquiry.jsc.JscDjslDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.jsc.JscLqjyDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.jsc.JscLqqlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.jsc.JscLsylwtDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 不动产驾驶舱
 *
 * @author wangyinghao
 */
public interface BdcJscMapper {

    /**
     * 登记总数量
     *
     * @param kssj
     * @param kssj
     * @return
     */
    Integer queryDjSl(@Param("kssj") Date kssj, @Param("jssj") Date jssj);

    /**
     * 登记总面积
     *
     * @param kssj
     * @param kssj
     * @return
     */
    Integer queryDjMj(@Param("kssj") Date kssj, @Param("jssj") Date jssj);

    /**
     * 查询林权登记数量
     *
     * @param kssj
     * @param kssj
     * @return
     */
    List<JscDjslDTO> queryJscDjslDy(@Param("kssj") Date kssj, @Param("jssj") Date jssj, @Param("summaryDimension") String summaryDimension);
    List<JscDjslDTO> queryJscDjslCf(@Param("kssj") Date kssj, @Param("jssj") Date jssj, @Param("summaryDimension") String summaryDimension);
    List<JscDjslDTO> queryJscDjslDj(@Param("kssj") Date kssj, @Param("jssj") Date jssj, @Param("summaryDimension") String summaryDimension);


    /**
     * 概况 已经办理
     *
     * @param kssj
     * @param kssj
     * @return
     */
    Integer queryJscSummaryYb(@Param("kssj") Date kssj, @Param("jssj") Date jssj);

    /**
     * 办理中
     *
     * @param kssj
     * @param kssj
     * @return
     */
    Integer queryJscSummaryBlz(@Param("kssj") Date kssj, @Param("jssj") Date jssj);

    /**
     * 历史遗留问题
     *
     * @param kssj
     * @param kssj
     * @return
     */
    Integer queryJscSummaryBlLsyl(@Param("kssj") Date kssj, @Param("jssj") Date jssj);

    /**
     * 林权权利情况
     *
     * @param kssj
     * @param jssj
     * @param qxdms
     * @return
     */
    List<JscLqqlDTO> queryJscQl(@Param("kssj") Date kssj,
                                @Param("jssj") Date jssj,
                                @Param("qxdms") List<String> qxdms
    );

    /**
     * 林权交易情况
     *
     * @param kssj
     * @param jssj
     * @param qxdms
     * @return
     */
    List<JscLqjyDTO> queryJscJyqk(@Param("kssj") Date kssj,
                                  @Param("jssj") Date jssj,
                                  @Param("qxdms") List<String> qxdms,
                                  @Param("djlx") String djlx
    );

    /**
     * 区县登记数量
     *
     * @param kssj
     * @param jssj
     * @param qxdms
     * @return
     */
    List<JscLqjyDTO> queryQXDjSl();


    /**
     * 历史遗留数据，
     *
     * @param kssj
     * @param jssj
     * @param qxdms
     * @return
     */
    List<JscLsylwtDTO> querylsylsj(@Param("qxdms") List<String> qxdmList);

}
