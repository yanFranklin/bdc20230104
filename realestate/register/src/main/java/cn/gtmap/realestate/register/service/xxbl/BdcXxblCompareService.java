package cn.gtmap.realestate.register.service.xxbl;

import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbDetailVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbVO;

import java.util.List;

/**
 * 补录对比服务
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 5:27 下午 2020/4/24
 */
public interface BdcXxblCompareService {

    /**
     * 信息对比
     * 根据当前项目的 processInsId 获取日志中存储的初始化是的备份信息
     *
     * @param processInsId 修改流程的 processInsId
     * @return {List<BdcDbVO>} 对比 VO 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcDbVO> xxdb(String processInsId) throws Exception;

    /**
     * 查询页面高亮显示内容
     *
     * @param processInsId 修改流程的 processInsId
     * @return {List<BdcDbDetailVO>} 对比 VO 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcDbDetailVO> queryGlxx(String processInsId) throws Exception;
}
