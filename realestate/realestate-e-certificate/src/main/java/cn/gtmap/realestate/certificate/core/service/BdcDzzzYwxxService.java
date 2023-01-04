package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzYwxxDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;


/*
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0, chenyongqiang
 * @description  不动产电子证照业务信息
 */
public interface BdcDzzzYwxxService {

    /**
     *
     * @param bdcDzzzZzxx
     * @return
     * @description  证照信息转业务信息
     */
    BdcDzzzYwxxDo getYwxxFromZzxx(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     *
     * @param bdcDzzzZzxx
     * @param bdcDzzzYwxxDo
     * @return
     * @description  业务信息转证照信息
     */
    BdcDzzzZzxx getZzxxFromYwxx(BdcDzzzYwxxDo bdcDzzzYwxxDo, BdcDzzzZzxx bdcDzzzZzxx);

    /**
     *
     * @param bdcDzzzZzxx
     * @return
     * @description  插入
     */
    int insertYwxx(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     *
     * @param zzid
     * @return
     * @description  根据zzid查询
     */
    BdcDzzzYwxxDo queryYwxxByZzid(String zzid);

    int updateYwxxTbzt(String zzid, Integer tbzt);
}
