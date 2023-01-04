package cn.gtmap.realestate.certificate.core.service;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.DzzzValidate;

import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/6
 */
public interface BdcDzzzValidateService {

    /**
     * @param dzzzValidate
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 验证方法
     */
    Map<String, Object> validate(DzzzValidate dzzzValidate);

    /**
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 获取验证编号
     */
    String getCheckCode();

    /**
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 获取验证信息
     */
    String getDescription();

}
