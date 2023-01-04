package cn.gtmap.realestate.building.service.impl.lpb;

import cn.gtmap.realestate.building.util.LpbInfoTypeEnum;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-28
 * @description 展示字段处理逻辑  和 常量处理逻辑一致 直接转换配置项传递，不用做其他处理
 */
@Service
public class ShowColumnTypeServiceImpl extends ConstantTypeServiceImpl {

    @Override
    protected LpbInfoTypeEnum getTypeEnum() {
        return LpbInfoTypeEnum.SHOWCOLUMN;
    }
}
