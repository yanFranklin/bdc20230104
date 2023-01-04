package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzLogDO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzLogDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/12
 */
@Mapper
public interface BdcDzzzLogMapper {
    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param bdcDzzzLogDO
     * @return
     * @throws DataAccessException
     * @description 日志信息入库
     */
    int insertBdcDzzzLog(BdcDzzzLogDO bdcDzzzLogDO) throws DataAccessException;

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param bdcDzzzLogDO
     * @return
     * @description 更改日志
     */
    int updateBdcDzzzLogByLogid(BdcDzzzLogDO bdcDzzzLogDO);

    /**
     * @param logId
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 查询日志
     */
    BdcDzzzLogDO queryBdcDzzzLogByLogid(String logId);

    /**
     * 证号和操作类型查询日志
     *
     * @param dzzzLogDO
     * @return
     * @Date 2021/11/17
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    DzzzLogDO queryByDzzzLogByczlx(DzzzLogDO dzzzLogDO);
}
