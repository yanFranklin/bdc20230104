package cn.gtmap.realestate.certificate.core.mapper;



import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzMlxxHjlogDO;

import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/4/30
 * @description
 */
public interface BdcDzzzMlxxHjlogMapper {
    /**
     * 查询目录信息汇交日志
     *
     * @param map
     * @return
     */
    public BdcDzzzMlxxHjlogDO queryBdcDzzzMlxxHjlog(Map map);

    /**
     * 查询上一条目录信息汇交日志
     *
     * @param xzqdm
     * @return
     */
    public BdcDzzzMlxxHjlogDO queryLastBdcDzzzMlxxHjlog(String xzqdm);

    /**
     * 查询第一条新增差值不为0的数据
     *
     * @param map
     * @return
     */
    public BdcDzzzMlxxHjlogDO queryFirstYcBdcDzzzMlxxHjlog(Map map);

    /**
     * 插入目录信息汇交日志
     *
     * @param bdcDzzzMlxxHjlog
     */
    public void insertBdcDzzzMlxxHjlog(BdcDzzzMlxxHjlogDO bdcDzzzMlxxHjlog);

    /**
     * 更新目录信息汇交日志
     *
     * @param bdcDzzzMlxxHjlog
     */
    public void updateOldBdcDzzzMlxxHjlog(BdcDzzzMlxxHjlogDO bdcDzzzMlxxHjlog);

    /**
     * 更新汇交日志中的新增差值条数
     *
     * @param map
     */
    public void updateBdcDzzzMlxxHjlogCz(Map map);

    /**
     * 归档汇交日志信息
     *
     * @param map
     */
    public void updateBdcDzzzMlxxHjlogGd(Map map);
}
