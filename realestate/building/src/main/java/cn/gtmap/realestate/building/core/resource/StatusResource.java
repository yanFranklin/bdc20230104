package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.building.config.lpb.LpbConfig;
import cn.gtmap.realestate.building.core.service.BdcdyZtService;
import cn.gtmap.realestate.building.util.LpbUtils;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-15
 * @description 状态资源
 */
public class StatusResource extends AttributeResouce {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LpbConfig.class);

    /**
     * 查状态需要的参数
     */
    protected SSjBdcdyhxsztDO sSjBdcdyhxsztDO;

    private List<String> statusList;

    /**
     * @param resource
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    public StatusResource(AbstractResource resource) {
        super(resource);
    }

    /**
     * @param resource
     * @param sSjBdcdyhxsztDO
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 需要使用状态实体 的 构造函数
     */
    public StatusResource(AbstractResource resource, SSjBdcdyhxsztDO sSjBdcdyhxsztDO) {
        super(resource);
        this.sSjBdcdyhxsztDO = sSjBdcdyhxsztDO;
    }

    /**
     * @param
     * @return cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 参数
     */
    public SSjBdcdyhxsztDO getsSjBdcdyhxsztDO() {
        return this.sSjBdcdyhxsztDO;
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理状态属性资源
     */
    @Override
    public void dealAttribute() {
        if(CollectionUtils.isEmpty(this.statusList)
                && StringUtils.isNotBlank(this.configCode)){
            // 获取状态配置
            this.statusList = LpbConfig.getConfigStatus(this.configCode);
        }
        if(this.sSjBdcdyhxsztDO == null){
            // 暂时注释掉   外层批量处理时 会查询
//            Map<String, Object> paramMap = LpbUtils.parseObjectToMap(paramObject);
////            String bdcdyh = MapUtils.getString(paramMap, "bdcdyh");
////            if (StringUtils.isNotBlank(bdcdyh) && CollectionUtils.isNotEmpty(this.statusList)) {
////                try {
////                    // 查询 状态实体
////                    this.sSjBdcdyhxsztDO = Container.getBean(BdcdyZtService.class).getBdcdyztDO(bdcdyh);
////                }catch (Exception e){
////                    LOGGER.error("bdcdyh:{}",bdcdyh,e);
////                }
////            }
        }
        LpbUtils.instanceStatus(this.configCode, this.statusList, this);
    }

    public void setStatusList(List<String> statusList){
        this.statusList = statusList;
    }

    public void setXsztDO(SSjBdcdyhxsztDO sSjBdcdyhxsztDO){
        this.sSjBdcdyhxsztDO = sSjBdcdyhxsztDO;
    }
}
