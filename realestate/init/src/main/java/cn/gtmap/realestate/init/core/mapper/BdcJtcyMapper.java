package cn.gtmap.realestate.init.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrHkbGxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrHkbGxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrHkbGxQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/6.
 * @description
 */
public interface BdcJtcyMapper {
    /**
     * 根据户主证件号查询出最新版本的家庭成员信息
     * @param param
     * @return
     */
    List<BdcJtcyDO> queryLastBbhJtcyByHzZJH(String hzzjh);
    /**
     * 查询家庭成员
     * @param param
     * @return
     */
    List<BdcJtcyDO> queryJtcy(Map<String,Object> param);
    /**
     * 查询权利人户口本关系(包含权利人证件号)
     * @param bdcQlrHkbGxDO
     * @return
     */
    List<BdcQlrHkbGxDTO> queryBdcQlrHkbGx(BdcQlrHkbGxQO bdcQlrHkbGxQO);
    /**
     * 查询权利人户口本关系(返回BdcQlrHkbGxDO)
     * @param bdcQlrHkbGxDO
     * @return
     */
    List<BdcQlrHkbGxDO> listBdcQlrHkbGxDO(BdcQlrHkbGxQO bdcQlrHkbGxQO);
    /**
     * 删除权利人和户口簿关系
     * @param paramMap
     * @return
     */
    void deleteQlrHkbGx(Map paramMap);
}
