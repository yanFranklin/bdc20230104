package cn.gtmap.realestate.etl.core.mapper.qzk;

import cn.gtmap.realestate.common.core.domain.DzzzQzkDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/08/29
 * @description
 */
@Mapper
public interface DzzzQzkMapper {
    /**
    *  将推送数据插入前置库
    */
    void insertQzk(DzzzQzkDO dzzzQzkDO);

    /**
     * 更新前置库电子证照信息
     */
    void updateQzkByZzid(@Param(value = "zzid") String zzid);

    /**
     * 根据主键id查询数据
     */
    List<DzzzQzkDO> getQzkxxByKey(@Param("rowguid") String rowguid);

    /**
     * 根据主键更新前置库数据
     */
    void updateQzk(DzzzQzkDO dzzzQzkDO);
}
