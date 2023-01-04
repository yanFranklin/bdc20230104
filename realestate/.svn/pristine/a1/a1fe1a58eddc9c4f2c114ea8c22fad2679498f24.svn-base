package cn.gtmap.realestate.init.service.qlxx.qlfl;

import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.InitBdcQlDataAbstractService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 初始化预告信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/10/31.
 * @description
 */
@ConfigurationProperties(prefix = "init.default")
public abstract class InitBdcYgAbstractService extends InitBdcQlDataAbstractService {
    /**
     * 获取预告登记种类的配置
     */
    private Map<String,Integer> ygdjzl;
    /**
     * 获取预告抵押方式的配置
     */
    private Map<String,Integer> ygdyfs;

    /**
     * 预告登记种类数据处理
     * @param initServiceQO
     * @param bdcYgDO
     * @return
     */
    public BdcYgDO dealYgdjzl(String djxl, BdcYgDO bdcYgDO) {
        if (MapUtils.getInteger(ygdjzl, djxl) != null) {
            bdcYgDO.setYgdjzl(MapUtils.getInteger(ygdjzl, djxl));
        }
        return bdcYgDO;
    }

    /**
     * 预告抵押方式数据处理
     * @param initServiceQO
     * @param bdcYgDO
     * @return
     */
    public BdcYgDO dealYgdyfs(String djxl, BdcYgDO bdcYgDO) {
        if (MapUtils.getInteger(ygdyfs, djxl) != null) {
            bdcYgDO.setDyfs(MapUtils.getInteger(ygdyfs, djxl));
        }
        return bdcYgDO;
    }

    /**
     * 预告担保范围数据处理
     *
     * @param bdcSlXmDO
     * @param bdcYgDO
     * @return BdcYgDO
     */
    public BdcYgDO dealYgdbfw(BdcSlXmDO bdcSlXmDO, BdcYgDO bdcYgDO) {
        if (bdcSlXmDO != null && StringUtils.isNotBlank(bdcSlXmDO.getDbfw())) {
            bdcYgDO.setDbfw(bdcSlXmDO.getDbfw());
        }
        return bdcYgDO;
    }


    /**
     * 预告数据处理
     *
     * @param initServiceQO
     * @param bdcYgDO
     * @return
     */
    public BdcYgDO dealYg(InitServiceQO initServiceQO, BdcYgDO bdcYgDO) {
        if (initServiceQO.getBdcXm() != null && StringUtils.isNotBlank(initServiceQO.getBdcXm().getDjxl())) {
            dealYgdjzl(initServiceQO.getBdcXm().getDjxl(),bdcYgDO);
            dealYgdyfs(initServiceQO.getBdcXm().getDjxl(), bdcYgDO);
            dealYgdbfw(initServiceQO.getBdcSlXmDO(),bdcYgDO);
        }
        return bdcYgDO;
    }

    public Map<String, Integer> getYgdyfs() {
        return ygdyfs;
    }

    public void setYgdyfs(Map<String, Integer> ygdyfs) {
        this.ygdyfs = ygdyfs;
    }

    public Map<String, Integer> getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(Map<String, Integer> ygdjzl) {
        this.ygdjzl = ygdjzl;
    }
}
