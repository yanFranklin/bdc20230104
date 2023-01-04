package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: realestate
 * @description: 收费项目配置
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-19 16:28
 **/
@Repository
public interface BdcSlSfxmPzMapper {
    /**
	 * @param bdcSlSfxmPzDO  收费项目配置
	 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
	 * @description 获取收费项目配置最大顺序号
	 */
	Integer querySfxmPzMaxSxh(BdcSlSfxmPzDO bdcSlSfxmPzDO);
	
	/**
	 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
	 * @description 获取收费项目名称
	 */
	List<String> querySfxmmc();
}
