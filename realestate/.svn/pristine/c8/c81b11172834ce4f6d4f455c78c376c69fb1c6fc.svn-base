package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzLwDO;
import cn.gtmap.realestate.common.core.vo.engine.ui.BdcGzLwVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/14,1.0
 * @description
 */
public interface BdcXzYzLwService {

    /**
     * @param map 操作人员
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询操作人员
     */
    Page<BdcGzLwVO> listBdcXzLwByPage(Pageable pageable, Map map);

    /**
     * @param lwid 例外id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除例外
     */
    void deleteXzyzLw(String lwid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLwDOList 例外规则集合
     * @return {int} 操作数据记录数
     * @description 新增验证例外规则
     */
    int addBdcGzLw(List<BdcGzLwDO> bdcGzLwDOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLwDOList 待删除例外规则集合
     * @return {int} 操作数据记录数
     * @description 批量删除已设置的验证例外规则
     */
    int deleteBdcGzLwList(List<BdcGzLwDO> bdcGzLwDOList);
}
