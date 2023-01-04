package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlXmVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/12
 * @description 受理业务信息
 */
@Repository
public interface BdcSlYwxxMapper {

    /**
     * @param map 查询条件
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息获取不动产受理项目集合
     * <p>2019-11-21 增加按照xmid ASC进行排序<p/>
     */
    List<BdcSlYwxxDTO> listBdcSLXmByPageOrder(Map map);


    /**
     * @param map 查询参数
     * @return 不动产受理项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取购物车所有数据包含yxmid
     */
    List<BdcSlYwxxDTO> listGwcData(Map map);

    /**
     * @param bdcSlXmQO 受理项目查询参数
     * @return 购物车已选项目数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询购物车已选项目数据(包含历史关系)
     */
    List<BdcSlXmVO> listGwcSelectDataWithLsgx(BdcSlXmQO bdcSlXmQO);
}
