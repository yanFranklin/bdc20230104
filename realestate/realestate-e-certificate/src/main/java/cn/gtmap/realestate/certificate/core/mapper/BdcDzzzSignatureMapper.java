package cn.gtmap.realestate.certificate.core.mapper;


import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzSignatureDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;


@Mapper
public interface BdcDzzzSignatureMapper {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param sealName
     * @return
     * @description 查询签名数据
     */
    BdcDzzzSignatureDo querySignatureBySealName(String sealName);


    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param bdcDzzzSignatureDo
     * @return
     * @throws DataAccessException
     * @description 插入签名数据
     */
    int insertSignature(BdcDzzzSignatureDo bdcDzzzSignatureDo) throws DataAccessException;

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param sealName
     * @return
     * @description 删除签名数据
     */
    int deleteSignatureBySealName(String sealName);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param bdcDzzzSignatureDo
     * @return
     * @description 修改签名数据
     */
    int updateSignatureBySealName(BdcDzzzSignatureDo bdcDzzzSignatureDo);

}
