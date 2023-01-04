package cn.gtmap.realestate.certificate.core.mapper;


import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzCxtjDo;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxCzztDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/*
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0,
 * @description 不动产电子证照查询条件配置表
 */
@Mapper
public interface BdcDzzzCxtjMapper {


    /**
     * @param username
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @rerutn
     * @description 通过用户名称查询条件配置表
     */
    BdcDzzzCxtjDo queryBdcDzzzCxtjByUserName(String username);

}
