package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.dto.building.ZlsxDTO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/27
 * @description 坐落刷新相关service
 */
public interface ZlsxService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param zlsxDTO
     * @return void
     * @description 房屋坐落刷新(总,不确定刷新规则)
     */
    void zlsxByRule(ZlsxDTO zlsxDTO);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param zlsxDTO
     * @return void
     * @description 户室坐落刷新
     */
    void fwhsZlsxByRule(FwHsDO fwHsDO, ZlsxDTO zlsxDTO,String zlExceptHs);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zlsxDTO
     * @return void
     * @description 根据配置做 做坐落刷新
     */
    void zlsxByConfig(ZlsxDTO zlsxDTO);
    /**
     * @param zlsxDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据dto查询有效的不动产单元号
     */
    List<String> listValidBdcdyhByDTO(ZlsxDTO zlsxDTO);
}
