package cn.gtmap.realestate.init.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcJsQxdmGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdQjgldmDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/12
 * @description
 */
public interface BdcQjgldmMapper {

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    List<String> listQjgldmByRoleCode(String rolecode);


    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据角色获取权籍管理代码集合
      */
    List<BdcZdQjgldmDO> listDistinctQjgldmByRoleCodeList(@Param("roleCodeList") List<String> roleCodeList);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据角色获取区县代码集合
      */
    List<BdcJsQxdmGxDO> listJsQxdmGx();

    /**
     * 获取用户角色关联最多的那个权籍管理代码
     * @param roleCodeList 用户角色
     * @return {String} 权籍管理代码
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    String listMostQjgldmByUserId(@Param("roleCodeList")List<String> roleCodeList);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据代码获取字典集合
      */
    List<BdcZdQjgldmDO> listDistinctQjgldmByDm(@Param("qjgldmList") List<String> qjgldmList);


}
