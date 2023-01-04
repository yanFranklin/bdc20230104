package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzTokenDo;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/19
 */
public interface BdcDzzzTokenService {

    /**
     * @param bdcDzzzTokenDo
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description token令牌获取
     */
    DzzzResponseModel addDzzzToken(BdcDzzzTokenDo bdcDzzzTokenDo);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param tokenId
     * @description 删除电子证照token信息
     */
    int deleteTokenByTokenId(String tokenId);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param tokenId
     * @return
     * @description 查询电子证照token信息
     */
    BdcDzzzTokenDo queryBdcDzzzToken(String tokenId);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param tokenName
     * @return
     * @description 通过tokenName查询电子证照token信息
     */
    BdcDzzzTokenDo queryTokenByTokenName(String tokenName);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param bdcDzzzTokenDo
     * @return
     * @description 更新电子证照token信息
     */
    DzzzResponseModel updateBdcDzzzTokenById(BdcDzzzTokenDo bdcDzzzTokenDo);
}
