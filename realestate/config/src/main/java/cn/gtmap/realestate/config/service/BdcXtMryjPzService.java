package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcXtMryjPzVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/29
 * @description 不动产默认意见业务类
 */
public interface BdcXtMryjPzService {

    /**
     * @param pageable
     * @param bdcXtMryjDO
     * @return 默认意见分页
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取默认意见分页数据
     */
    Page<BdcXtMryjPzVO> listBdcXtMryjByPage(Pageable pageable, BdcXtMryjDO bdcXtMryjDO);
    /**
     * @param bdcXtMryjDO
     * @return 保存的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存默认意见配置
     */
    int saveBdcXtMryj(BdcXtMryjDO bdcXtMryjDO);

    /**
     * @param bdcXtMryjDO
     * @return 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改默认意见配置
     */
    int updateBdcXtMryj(BdcXtMryjDO bdcXtMryjDO);
    /**
     * @param bdcXtMryjDOList
     * @return 删除的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除不动产默认意见配置
     */
    int deleteBdcXtMryj(List<BdcXtMryjDO> bdcXtMryjDOList);

    /**
     * @param bdcXtMryjDO
     * @return List<BdcXtMryjDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 默认意见配置数据
     */
    List<BdcXtMryjDO> listMryjpz(@RequestBody BdcXtMryjDO bdcXtMryjDO);


}
