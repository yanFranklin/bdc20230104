package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/19
 */
public interface BdcDzzzUseConditionService {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description
     */
    void insertDzzzUseCondition(BdcDzzzZzxx bdcDzzzZzxx, String yymc);

    String countUseConditionYybm(String bdcqzh);
}
