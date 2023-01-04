package cn.gtmap.realestate.exchange.core.dto.sjpt;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.domain.sjpt.PeCxDO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/11/24
 * @description 省厅查询（定时、实时查询）：返回数据转换处理线程参数
 */
public class SjptCxjgThreadDTO {
    /**
     * 权利信息
     */
    private BdcQl bdcQl;
    private PeCxDO peCxDO;

    /**
     * 权利实体Class
     */
    private Class bdcQlClass;
    private Class bdcGxQlClass;

    private DozerBeanMapper dozerBeanMapper;
    private EntityMapper entityMapper;

    /**
     * 是否需要设置共有人、限制权利信息
     */
    private boolean needGyr;
    private boolean needXzql;

    /**
     * 按照xmid或者单元号组织的权利人、项目、限制权利信息
     */
    private Map<String, List<BdcQlrDO>> xmidQlrsMap;
    private Map<String, List<BdcQlrDO>> xmidYwrsMap;
    private Map<String, List<BdcXmDO>> xmidXmMap;
    private Map<String, List<BdcCfDO>> bdcdyhCfMap;
    private Map<String, List<BdcDyaqDO>> bdcdyhDyaMap;

    /**
     * 不动产权证号分隔符
     */
    private String bdcqzhFgfh;


    public BdcQl getBdcQl() {
        return bdcQl;
    }

    public void setBdcQl(BdcQl bdcQl) {
        this.bdcQl = bdcQl;
    }

    public PeCxDO getPeCxDO() {
        return peCxDO;
    }

    public void setPeCxDO(PeCxDO peCxDO) {
        this.peCxDO = peCxDO;
    }

    public Class getBdcQlClass() {
        return bdcQlClass;
    }

    public void setBdcQlClass(Class bdcQlClass) {
        this.bdcQlClass = bdcQlClass;
    }

    public Class getBdcGxQlClass() {
        return bdcGxQlClass;
    }

    public void setBdcGxQlClass(Class bdcGxQlClass) {
        this.bdcGxQlClass = bdcGxQlClass;
    }

    public DozerBeanMapper getDozerBeanMapper() {
        return dozerBeanMapper;
    }

    public void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    public EntityMapper getEntityMapper() {
        return entityMapper;
    }

    public void setEntityMapper(EntityMapper entityMapper) {
        this.entityMapper = entityMapper;
    }

    public Map<String, List<BdcQlrDO>> getXmidQlrsMap() {
        return xmidQlrsMap;
    }

    public void setXmidQlrsMap(Map<String, List<BdcQlrDO>> xmidQlrsMap) {
        this.xmidQlrsMap = xmidQlrsMap;
    }

    public Map<String, List<BdcQlrDO>> getXmidYwrsMap() {
        return xmidYwrsMap;
    }

    public void setXmidYwrsMap(Map<String, List<BdcQlrDO>> xmidYwrsMap) {
        this.xmidYwrsMap = xmidYwrsMap;
    }

    public Map<String, List<BdcXmDO>> getXmidXmMap() {
        return xmidXmMap;
    }

    public void setXmidXmMap(Map<String, List<BdcXmDO>> xmidXmMap) {
        this.xmidXmMap = xmidXmMap;
    }

    public Map<String, List<BdcCfDO>> getBdcdyhCfMap() {
        return bdcdyhCfMap;
    }

    public void setBdcdyhCfMap(Map<String, List<BdcCfDO>> bdcdyhCfMap) {
        this.bdcdyhCfMap = bdcdyhCfMap;
    }

    public Map<String, List<BdcDyaqDO>> getBdcdyhDyaMap() {
        return bdcdyhDyaMap;
    }

    public void setBdcdyhDyaMap(Map<String, List<BdcDyaqDO>> bdcdyhDyaMap) {
        this.bdcdyhDyaMap = bdcdyhDyaMap;
    }

    public void setBdcdyhDyaMap(ConcurrentHashMap<String, List<BdcDyaqDO>> bdcdyhDyaMap) {
        this.bdcdyhDyaMap = bdcdyhDyaMap;
    }

    public boolean isNeedGyr() {
        return needGyr;
    }

    public void setNeedGyr(boolean needGyr) {
        this.needGyr = needGyr;
    }

    public boolean isNeedXzql() {
        return needXzql;
    }

    public void setNeedXzql(boolean needXzql) {
        this.needXzql = needXzql;
    }

    public String getBdcqzhFgfh() {
        return bdcqzhFgfh;
    }

    public void setBdcqzhFgfh(String bdcqzhFgfh) {
        this.bdcqzhFgfh = bdcqzhFgfh;
    }

    @Override
    public String toString() {
        return "SjptCxjgThreadDTO{" +
                "bdcQl=" + bdcQl +
                ", peCxDO=" + peCxDO +
                ", bdcQlClass=" + bdcQlClass +
                ", bdcGxQlClass=" + bdcGxQlClass +
                ", dozerBeanMapper=" + dozerBeanMapper +
                ", entityMapper=" + entityMapper +
                ", needGyr=" + needGyr +
                ", needXzql=" + needXzql +
                ", xmidQlrsMap=" + xmidQlrsMap +
                ", xmidYwrsMap=" + xmidYwrsMap +
                ", xmidXmMap=" + xmidXmMap +
                ", bdcdyhCfMap=" + bdcdyhCfMap +
                ", bdcdyhDyaMap=" + bdcdyhDyaMap +
                ", bdcqzhFgfh='" + bdcqzhFgfh + '\'' +
                '}';
    }
}
