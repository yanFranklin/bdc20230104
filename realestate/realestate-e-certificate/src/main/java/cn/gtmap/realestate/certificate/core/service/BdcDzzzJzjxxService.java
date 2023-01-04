package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzJzjxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import org.springframework.dao.DataAccessException;

import java.util.Date;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description  不动产电子证照加注件信息
 */
public interface BdcDzzzJzjxxService {

    /**
     * @param bdcDzzzZzxx
     * @return
     * @throws DataAccessException
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 证照加注件信息入库
     */
    DzzzResponseModel insertBdcDzzzJzjxx(BdcDzzzZzxx bdcDzzzZzxx) throws DataAccessException;

    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description
     */
    void deleteBdcDzzzJzjxxByZzid(String zzid);

    void deleteBdcDzzzJzjxxFile(BdcDzzzJzjxxDO bdcDzzzJzjxxDO);

    DzzzResponseModel createBdcDzzzJzjxx(BdcDzzzZzxx bdcDzzzZzxx, String dzzzsymd, String jzjzzz, Date jzjyxqjzsj);

}
