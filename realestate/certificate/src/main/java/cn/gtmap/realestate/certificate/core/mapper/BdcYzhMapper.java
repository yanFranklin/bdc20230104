package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhExcelDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYzhFzlTjDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcYzhTjQO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/4
 * @description 不动产印制号查询Mapper
 */
public interface BdcYzhMapper {
    /**
     * @param bdcYzhQO 印制号查询对象
     * @return List<BdcYzhDTO> 返回印制号DTOList
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 条件查询印制号(只查询印制号 ， 不包含明细)
     */
    List<BdcYzhDTO> queryBdcYzh(BdcYzhQO bdcYzhQO);

    /**
     * @param bdcYzhDO 印制号DO
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键更新印制号使用情况（syqk不一致时更新），会更新空字段
     */
    int updateYzhSyqk(BdcYzhDO bdcYzhDO);


    /**
     * @param bdcYzhDO 印制号DO
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 删除印制号使用明细
     */
    void deleteYzhSymx(BdcYzhDO bdcYzhDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zsid 证书ID
     * @description 清除虚拟印制号
     */
    int updateYzhToNull(@Param("zsid") String zsid);

    /**
     * 统计印制号废证量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcYzhTjQO 不动产印制号统计QO
     */
    List<BdcYzhFzlTjDTO> yzhFzlTj(BdcYzhTjQO bdcYzhTjQO);

    /**
     * 统计印制号领用印制号数量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcYzhTjQO 不动产印制号统计QO
     */
    List<BdcYzhFzlTjDTO> yzhLyTj(BdcYzhTjQO bdcYzhTjQO);

    List<BdcYzhExcelDTO> queryBdcYzhSymx(@Param("yzhidList")List<String> yzhidList);
}
