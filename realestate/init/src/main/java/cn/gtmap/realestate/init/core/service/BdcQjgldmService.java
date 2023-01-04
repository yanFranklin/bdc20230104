package cn.gtmap.realestate.init.core.service;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcJsQxdmGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdQjgldmDO;
import cn.gtmap.realestate.common.core.dto.BdcJsPzDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/12
 * @description
 */
public interface BdcQjgldmService {

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据角色编码获取权籍管理代码列表
      */
    List<String> listQjgldmByRoleCode(String roleCode);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据用户ID获取权籍管理列表
      */
    List<BdcZdQjgldmDO> listDistinctQjgldmByUserId(String userId);

    /**
     * @param userDto 用户信息
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据用户ID获取角色配置信息
     */
    BdcJsPzDTO queryJsPzDTOByUserId(UserDto userDto);

    /**
     * 获取用户角色关联最多的那个权籍管理代码
     * @param userId 用户ID
     * @return {String} 权籍管理代码
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    String listMostQjgldmByUserId(String userId);

    /**
     * @param roleCodeList 角色编码集合
     * @return  区县代码集合
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据角色获取区县代码集合
     */
    List<String> listQxdmByRoleCodeList(List<String> roleCodeList);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询角色区县代码配置
     */
    Page<BdcJsQxdmGxDO> listBdcJsQxdmGxByPage(Pageable pageable, BdcJsQxdmGxDO bdcJsQxdmGxDO);

    /**
     * @param rolecode 角色编码
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据角色编码删除配置项信息
     */
    void deleteJsQxdmPzByRoleCode(String rolecode);

    /**
     * @param bdcJsQxdmGxDO 角色编码配置信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据角色编码保存配置项信息
     */
    void saveJsQxdmPz(BdcJsQxdmGxDO bdcJsQxdmGxDO);

    /**
     * @param rolecode 角色编码
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 刷新角色区县代码配置内容
     */
    void refreshBdcJsQxdmPz(String rolecode);
}
