package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCfDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCfxxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author <a href="mailto:zhongjinpeng@gtmap.cn>zhongjinpeng</a>"
 * @version 1.0, 2020/6/22
 * @description 不动产查封信息
 */
public interface BdcCfxxMapper {

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询查封
     */
    List listBdcCfByXmid(@Param("xmid") String xmid);

    /**
     * @param bdcdyh
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/7
     * @description 根据不动产单元号查询查封信息
     */
    List listBdcCfByBdcdyh(@Param("bdcdyh") String bdcdyh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询查封信息
     */
    List<BdcCfDO> listBdcCfByBdcdyhs(@Param("list") List<String> bdcdyhList);


    /**
     * @param bdcCfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询查封信息
     * @date : 2021/7/30 11:18
     */
    List<BdcCfDTO> listBdcCfxx(BdcCfxxQO bdcCfxxQO);

    /**
     * @param bdcCfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询续封上一首的查封编号
     * @date : 2021/7/30 11:25
     */
    List<BdcCfDTO> listBdcXfxx(BdcCfxxQO bdcCfxxQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID查询原查封信息
     */
    List<BdcCfDO> listYcfxxByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/11/1 11:15
     */
    List<BdcCfDTO> listBdcCfxxByCfsx(BdcCfxxQO bdcCfxxQO);
}
