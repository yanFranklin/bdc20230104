package cn.gtmap.realestate.building.service;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-25
 * @description 查询BDCDY的SQL构造器
 */
public interface BdcdyLsSqlBuilderService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param dzwtzm
     * @param zdtzm
     * @param fwlx
     * @param withQlr
     * @return java.util.List<java.lang.String>
     * @description 获取SQL主体LIST
     */
    List<String> getMainSqlList(String[] dzwtzm, String[] zdtzm, String[] fwlx, boolean withQlr,String qlrmh);


}
