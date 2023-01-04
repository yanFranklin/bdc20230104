package cn.gtmap.realestate.certificate.core.service;



/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, 2019/1/17
 * @description 不动产电子证照证照注销
 */

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;

import java.util.Date;

public interface BdcDzzzZzzxConfigService {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param o
     * @return
     * @description 注销电子证照
     */
    DzzzResponseModel dzzzZzzx(Object o);

    BdcDzzzZzzxService getCreateDzzzService(String dwdm);

    DzzzResponseModel bdcDzzzZzzxBeforeCheck(BdcDzzzZzxx bdcDzzzZzxx);

    DzzzResponseModel bdcDzzzZzzxInfoCheck(BdcDzzzZzxxDO bdcDzzzZzxxDO);

    DzzzResponseModel updateDzzzZxxxInfo(String zzid, String zzbgyy, Date zzbgsj);

    /**
     * @param zzid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新签章注销信息
     * @date : 2022/3/10 21:23
     */
    DzzzResponseModel updateDzqzZxxxInfo(String zzid, String zzbgyy, Date zzbgsj, String zzwjlj);

}
