package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzConfigDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description
 */
@Mapper
public interface BdcDzzzConfigMapper {

    /**
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 通过dwdm 获取配置表数据
     */
    BdcDzzzConfigDo queryBdcDzzzConfigDoByDwdm(@Param("dwdm") String dwdm);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description 查询数据
     */
    List<BdcDzzzConfigDo> listBdcDzzzConfig();
}
