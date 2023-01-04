package cn.gtmap.realestate.building.core.resource;

import cn.gtmap.realestate.building.config.lpb.LpbConfig;
import cn.gtmap.realestate.building.core.bo.ColumnBO;
import cn.gtmap.realestate.building.service.lpb.LpbConfigInfoService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.support.spring.Container;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-15
 * @description 实体基本属性字段 资源处理服务
 */
public class InfoResource extends AttributeResouce {

    /**
     * 链接配置标志，用来指定读取配置中哪个维度的链接配置
     */
    private String infoFlag;

    public InfoResource(AbstractResource resource){
        super(resource);
        this.infoFlag = LpbConfig.FLAG_HS;
    }

    /**
     * @param resource
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造函数
     */
    public InfoResource(AbstractResource resource,String infoFlag) {
        super(resource);
        this.infoFlag = infoFlag;
    }

    /**
     * @param
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理属性
     */
    @Override
    public void dealAttribute() {
        if (this.paramObject != null) {
            List<ColumnBO> columnBOList = Container.getBean(LpbConfig.class).getColumnList(this.configCode, this.infoFlag);
            // 普通字段类型 处理
            ((LpbConfigInfoService) Container.getBean(Constants.LPBCONFIG_INFO_SERVICE_COLUMN)).dealInfo(this, columnBOList);
            // 按钮类型 处理
            ((LpbConfigInfoService) Container.getBean(Constants.LPBCONFIG_INFO_SERVICE_BUTTON)).dealInfo(this, columnBOList);
            // 字典类型 处理
            ((LpbConfigInfoService) Container.getBean(Constants.LPBCONFIG_INFO_SERVICE_ZDCOLUMN)).dealInfo(this, columnBOList);
            // 常量类型 处理
            ((LpbConfigInfoService) Container.getBean(Constants.LPBCONFIG_INFO_SERVICE_CONSTANT)).dealInfo(this, columnBOList);
            // 展示字段类型 处理
            ((LpbConfigInfoService) Container.getBean(Constants.LPBCONFIG_INFO_SERVICE_SHOWCOLUMN)).dealInfo(this, columnBOList);
            // 外键关联类型 处理
            ((LpbConfigInfoService) Container.getBean(Constants.LPBCONFIG_INFO_SERVICE_JOINCOLUMN)).dealInfo(this, columnBOList);
            // 空判断关联类型 处理
            ((LpbConfigInfoService) Container.getBean(Constants.LPBCONFIG_INFO_SERVICE_NVLCOLUMN)).dealInfo(this, columnBOList);

        }
    }
}
