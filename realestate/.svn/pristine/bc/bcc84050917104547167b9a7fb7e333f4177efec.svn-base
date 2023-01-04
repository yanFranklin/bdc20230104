package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBmdDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcBmdLoginDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/4 20:04
 * @description
 */
public interface BdcGzBmdService {

    /**
     * @param czry   白名单人员
     * @param czrymm 白名单人员密码
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 查看是否登录
     */
    BdcBmdLoginDTO checkIsLogin(String czry, String czrymm);

    /**
     * @param map 白名单人员
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 分页查询白名单人员
     */
    Page<BdcGzBmdDO> listBdcBmdByPage(Pageable pageable, Map map);


    /**
     * @param bmdDOList
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 删除白名单
     */
    int deleteBdcBmd(List<BdcGzBmdDO> bmdDOList);


    /**
     * @param bdcGzBmdDO
     * @return 保存的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">huangjian</a>
     * @description 保存人员信息
     */
    int insertBdcBmd(BdcGzBmdDO bdcGzBmdDO);

    /**
     * @param bdcGzBmdDO bdcGzBmdDO
     * @return int 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">huangjian</a>
     * @description 修改人员信息
     */
    int updateBdcBmd(BdcGzBmdDO bdcGzBmdDO);

}
