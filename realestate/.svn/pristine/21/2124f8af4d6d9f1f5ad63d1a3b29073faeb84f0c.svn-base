package cn.gtmap.realestate.building.core.decorator;

import cn.gtmap.realestate.building.core.resource.StatusResource;
import cn.gtmap.realestate.building.util.FwHsStatusEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-01
 * @description 状态类型抽象类
 */
public abstract class StatusDecorator extends StatusResource {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param resource
     * @return
     * @description 构造函数
     */
    public StatusDecorator(StatusResource resource){
        super(resource);
        // 给参数 赋值
        this.sSjBdcdyhxsztDO = resource.getsSjBdcdyhxsztDO();
        // 为statusMap填充 状态值
        decoratWithStatus();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description  statusMap填充 状态值
     */
    public void decoratWithStatus(){
        Map<String,Object> statusMap = this.resouceDTO.getStatus();
        if(StringUtils.isNotBlank(this.statusEnum().getName())){
            String jsonkey = this.statusEnum().getName();
            if(StringUtils.isNotBlank(jsonkey)){
                statusMap.put(jsonkey.toLowerCase(),this.getStatusCount());
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.Integer
     * @description 查询状态数量
     */
    public abstract Integer getStatusCount();

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return cn.gtmap.realestate.building.util.FwHsStatusEnum
     * @description 状态枚举
     */
    public abstract FwHsStatusEnum statusEnum();
}
