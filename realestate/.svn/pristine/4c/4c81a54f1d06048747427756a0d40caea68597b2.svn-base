package cn.gtmap.realestate.certificate.core.service.sign;

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @program: realestate
 * @description: 电子签章service
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-10 10:09
 **/
public interface BdcDzqzService {


    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/10 10:10
     */
    DzzzResponseModel checkBdcDzzzQzxx(BdcDzzzZzxx bdcDzzzZzxx, List<String> resultList);

    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/10 10:12
     */
    DzzzResponseModel checkBdcDzzzQzxxCreate(BdcDzzzZzxx bdcDzzzZzxx, List<String> resultList);

    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 签章信息入库
     * @date : 2022/3/10 11:40
     */
    DzzzResponseModel insertBdcDzzzQzxx(BdcDzzzZzxx bdcDzzzZzxx) throws DataAccessException;

    /**
     * @param zzid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据zzid 查询证照信息
     * @date : 2022/3/10 16:55
     */
    BdcDzzzZzxxDO queryBdcDzzzQzxx(String zzid);
}
