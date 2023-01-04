package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.BdcXtQlqtzkFjPzDO;
import cn.gtmap.realestate.common.core.qo.config.BdcXtQlqtzkFjPzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/2/18
 * @description 权利其他状况附记配置业务类
 */
public interface BdcXtQlqtzkFjPzService {

    /**
     * @param pageable
     * @param bdcXtQlqtzkFjPzQO
     * @return 权利其他状况、附记配置分页
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取默认意见分页数据
     */
    Page<BdcXtQlqtzkFjPzDO> listBdcXtQlqtzkFjPzByPage(Pageable pageable, BdcXtQlqtzkFjPzQO bdcXtQlqtzkFjPzQO);
    /**
     * @param bdcXtQlqtzkFjPzDO
     * @return 权利其他状况、附记配置保存
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存权利其他状况、附记配置
     */
    int saveBdcXtQlqtzkFjPz(BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO);

    /**
     * @param bdcXtQlqtzkFjPzDO
     * @return 权利其他状况、附记配置修改
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改权利其他状况、附记配置
     */
    int updateBdcXtQlqtzkFjPz(BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO);
    /**
     * @param bdcXtQlqtzkFjPzDOS
     * @return 权利其他状况、附记配置删除
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除权利其他状况、附记配置
     */
    int deleteBdcXtQlqtzkFjPz(List<BdcXtQlqtzkFjPzDO> bdcXtQlqtzkFjPzDOS);
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证sql是否可运行
     */
    boolean checkBdcXtQlqtzkFjPz(List<String> sqlList, Map params);

    /**
     * @param bdcXtQlqtzkFjPzDO
     * @return List<BdcXtQlqtzkFjPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 系统权利其他状况附记配置
     */
    List<BdcXtQlqtzkFjPzDO> listQlqtzkFjpz(BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO);
}
