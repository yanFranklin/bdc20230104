package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzTokenDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/19
 */
@Mapper
public interface BdcDzzzTokenMapper {
    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param bdcDzzzTokenDo
     * @return
     * @description token令牌信息插入
     */
    int insertBdcDzzzToken(BdcDzzzTokenDo bdcDzzzTokenDo) throws DataAccessException;

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param yyid
     * @return
     * @description 删除电子证照token信息
     */
    int deleteTokenByTokenId(String yyid);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param yyid
     * @return
     * @description 查询电子证照token信息
     */
    BdcDzzzTokenDo queryBdcDzzzToken(String yyid);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param yymc
     * @return
     * @description 通过tokenName查询电子证照token信息
     */
    BdcDzzzTokenDo queryTokenByTokenName(String yymc);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param bdcDzzzTokenDo
     * @return
     * @description 更新电子证照token信息
     */
    int updateBdcDzzzTokenById(BdcDzzzTokenDo bdcDzzzTokenDo);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param map
     * @return
     * @description 查询电子证照token信息
     */
    List<BdcDzzzTokenDo> listBdcDzzzToken(Map map);
}
