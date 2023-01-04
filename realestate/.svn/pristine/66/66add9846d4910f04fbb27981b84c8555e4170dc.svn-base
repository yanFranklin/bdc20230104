package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcJsQxdmGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdQjgldmDO;
import cn.gtmap.realestate.common.core.dto.BdcJsPzDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/12
 * @description 角色配置REST服务
 */
public interface BdcJsPzRestService {

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据角色编码获取权籍管理代码列表
      */
    @GetMapping(value = "/init/rest/v1.0/qjgldm/list/{roleCode}")
    List<String> listQjgldmByRoleCode(@PathVariable("roleCode") String roleCode);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据用户ID获取权籍管理列表
      */
    @GetMapping(value = "/init/rest/v1.0/qjgldm/list/distinct/{userId}")
    List<BdcZdQjgldmDO> listDistinctQjgldmByUserId(@PathVariable("userId") String userId);

    /**
      * @param userDto 用户信息
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据用户ID获取角色配置信息
      */
    @PostMapping(value = "/init/rest/v1.0/qjgldm/jspz")
    BdcJsPzDTO queryJsPzDTOByUserId(@RequestBody UserDto userDto);

    /**
     * 获取用户角色关联最多的那个权籍管理代码
     * @param userId 用户ID
     * @return {String} 权籍管理代码
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @GetMapping(value = "/init/rest/v1.0/{userId}/most/qjgldm")
    String listMostQjgldmByUserId(@PathVariable("userId") String userId);

    /**
     * @param roleCodeList 角色编码集合
     * @return  区县代码集合
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据角色获取区县代码集合
     */
    @PostMapping(value = "/init/rest/v1.0/jspz/qxdm")
    List<String> listQxdmByRoleCodeList(@RequestBody List<String> roleCodeList);

    /**
     * @param json 查询条件
     * @return 分页结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询角色区县代码配置
     */
    @PostMapping("/init/rest/v1.0/jspz/qxdm/page")
    Page<BdcJsQxdmGxDO> listBdcJsQxdmGxByPage(Pageable pageable, @RequestParam(name = "json", required = false) String json);

    /**
     * @param rolecode 角色编码
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据角色编码删除配置项信息
     */
    @DeleteMapping("/init/rest/v1.0/jspz/qxdm")
    void deleteJsQxdmPzByRoleCode(@RequestParam(name = "rolecode") String rolecode);

    /**
     * @param bdcJsQxdmGxDO 角色编码配置信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存配置项信息
     */
    @PostMapping("/init/rest/v1.0/jspz/qxdm/save")
    void saveJsQxdmPz(@RequestBody BdcJsQxdmGxDO bdcJsQxdmGxDO);

    /**
     * @param rolecode 角色编码
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 刷新角色区县代码配置内容
     */
    @GetMapping("/init/rest/v1.0/jspz/qxdm/refresh")
    void refreshBdcJsQxdmPz(@RequestParam(name = "rolecode") String rolecode);
}
