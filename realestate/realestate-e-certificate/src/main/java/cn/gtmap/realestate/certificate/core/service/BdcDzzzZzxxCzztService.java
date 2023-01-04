package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxCzztDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;

import java.util.List;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description  不动产电子证照权利人表
 */
public interface BdcDzzzZzxxCzztService {


    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 插入权利人信息
     */
    int insertBdcDzzzZzxxCzztDo(BdcDzzzZzxx bdcDzzzZzxx);


    /**
     * @param zzid 证照id
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 查询证照对应的持证主体
     */
    List<BdcDzzzZzxxCzztDo> queryBdcDzzzZzxxCzztDoByZzid(String zzid);

    /**
     * @param zzid 证照id
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 删除证照对应的持证主体
     */
    void delBdcDzzzZzxxCzztDoByZzid(String zzid);
}
