package cn.gtmap.realestate.register.ui.web.rest.xxbl;

import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcBlShDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcBlShQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXxblShFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 不动产信息补录审核服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/12/17 11:00
 */
@RestController
@RequestMapping("/rest/v1.0/blxx/blsh")
public class BdcBlshController extends BaseController {

    @Autowired
    private BdcXxblShFeignService bdcXxblShFeignService;
    @Autowired
    private RoleManagerClient roleManagerClient;

    @Value("${html.version:}")
    private String version;

    /**
     * 获取补录分页查询数据
     *
     * @param pageable  分页对象
     * @param bdcBlShQO 补录审核查询对象
     * @return 分页信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping
    public Object listBlShxxByPage(@LayuiPageable Pageable pageable, BdcBlShQO bdcBlShQO) {
        bdcBlShQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("xxbl","",""));
        if (StringUtils.isNotBlank(bdcBlShQO.getBdcdyh())) {
            bdcBlShQO.setBdcdyh(StringUtils.deleteWhitespace(bdcBlShQO.getBdcdyh()));
        }
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if (currentUser == null) {
            throw new AppException("获取当前用户失败！");
        }
        bdcBlShQO.setSlrid(currentUser.getId());
        bdcBlShQO.setShryid(currentUser.getId());
        if(StringUtils.isNotBlank(version) && StringUtils.equals(version, "yancheng")){
            bdcBlShQO.setBlshlx(1);
        }
        String bdcBlShQoStr = JSON.toJSONString(bdcBlShQO);
        return addLayUiCode(bdcXxblShFeignService.listBlShByPageJson(pageable, bdcBlShQoStr, this.version));
    }

    /**
     * 生成补录审核信息
     *
     * @param bdcBlShDO 不动产补录审核对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping
    public void generateBlShxx(@RequestBody BdcBlShDO bdcBlShDO) {
        if (bdcBlShDO.getBlshlx() == null) {
            throw new MissingArgumentException("缺少审核补录类型！");
        }
        bdcXxblShFeignService.generateBlShxx(bdcBlShDO);
    }

    /**
     * 判断是否正在审核
     *
     * @param bdcBlShDO 不动产补录审核对象
     * @return {boolean} 0: 正在审核， 1：未在审核， 2 表示正在审核但是本人打开
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/issh")
    public Integer checkIsSh(@RequestBody BdcBlShDO bdcBlShDO) {
        if (StringUtils.isBlank(bdcBlShDO.getXmid())) {
            throw new MissingArgumentException("转发缺少项目 ID！");
        }
        return bdcXxblShFeignService.checkIsSh(bdcBlShDO);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcBlShDO]
     * @return cn.gtmap.realestate.common.core.domain.BdcBlShDO
     * @description 根据项目id和受理人id查询可转发的补录记录
     */
    @PostMapping("/queryBdcBlshDO")
    public BdcBlShDO queryBdcBlshDO(@RequestBody BdcBlShDO bdcBlShDO){
        if (StringUtils.isBlank(bdcBlShDO.getXmid())) {
            throw new MissingArgumentException("转发缺少项目 ID！");
        }
        return bdcXxblShFeignService.queryBdcBlshDO(bdcBlShDO);
    }

    /**
     * 转发补录审核信息
     *
     * @param bdcBlShDO 不动产补录审核对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/forward")
    public void forwardBlShxx(@RequestBody BdcBlShDO bdcBlShDO) {
        if (StringUtils.isBlank(bdcBlShDO.getBlshid())) {
            throw new MissingArgumentException("转发缺少补录审核 ID！");
        }
        bdcXxblShFeignService.forwardBlShxx(bdcBlShDO);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcBlShDOList]
     * @return void
     * @description 批量转发补录审核信息 蚌埠
     */
    @PostMapping("/forward/pl")
    public void plForwardBlShxx(@RequestBody List<BdcBlShDO> bdcBlShDOList){
        if(CollectionUtils.isEmpty(bdcBlShDOList)){
            throw new AppException("补录审核信息列表为空！");
        }
        bdcXxblShFeignService.plForwardBlShxx(bdcBlShDOList);
    }

    /**
     * 转发补录审核信息
     *
     * @param blshid 不动产补录审核对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/end")
    public List<BdcGzyzVO> endBlShxx(@RequestParam("blshid") String blshid, @RequestParam("gzlslid") String gzlslid,
                                     @RequestParam("glGzyz") Boolean glGzyz, @RequestParam("bllx") Integer bllx) throws Exception {
        if (StringUtils.isAnyBlank(blshid, gzlslid)) {
            throw new MissingArgumentException("转发缺少补录审核 ID 和 gzlslid");
        }
        // 补录类型为修改，由于不发生权属状态的改变，不去调用验证
        if ( bllx != 1 && !glGzyz) {
            List<BdcGzyzVO> bdcGzyzVOS = bdcXxblShFeignService.dbYz(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcGzyzVOS)) {
                return bdcGzyzVOS;
            }
        }
        bdcXxblShFeignService.endBlShxx(blshid);
        return Lists.newArrayList();
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcBlShDOList, glGzyz]
     * @return java.util.List<cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO>
     * @description 蚌埠 批量办结补录审核信息
     */
    @PostMapping("/plEnd")
    public List<BdcGzyzVO> plEndBlShxx(@RequestBody List<BdcBlShDO> bdcBlShDOList, @RequestParam("glGzyz") Boolean glGzyz){
        if(CollectionUtils.isEmpty(bdcBlShDOList)){
            throw new AppException("批量补录审核列表为空！");
        }
        List<BdcGzyzVO> bdcGzyzVOList = new ArrayList<>();
        for(BdcBlShDO bdcBlShDO : bdcBlShDOList){
            if (bdcBlShDO.getBllx() != 1 && !glGzyz) {
                List<BdcGzyzVO> bdcGzyzVOS = null;
                try {
                    bdcGzyzVOS = bdcXxblShFeignService.dbYz(bdcBlShDO.getGzlslid());
                } catch (Exception e) {
                    LOGGER.error("规则验证失败！");
                    throw new AppException("规则验证失败！");
                }
                if (CollectionUtils.isNotEmpty(bdcGzyzVOS)) {
                    bdcGzyzVOList.addAll(bdcGzyzVOS);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(bdcGzyzVOList)){
            return bdcGzyzVOList;
        }
        bdcXxblShFeignService.plEndBlShxx(bdcBlShDOList);
        return Lists.newArrayList();
    }

    /**
     * 退回补录审核信息
     *
     * @param blshid 补录审核 ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/back")
    public boolean backBlShxx(@RequestParam("blshid") String blshid) {
        if (StringUtils.isBlank(blshid)) {
            throw new MissingArgumentException("转发缺少补录审核 ID！");
        }
        return bdcXxblShFeignService.backBlShxx(blshid);
    }


    /**
     * 查询转发角色
     * 获取全部启用角色
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/forward/roles")
    public List<RoleDto> queryForwardRoles() {
        return roleManagerClient.getEnabledRoleList();
    }

    /**
     * 查询转发用户
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/forward/users")
    public List<UserDto> queryForwardUsers(@RequestParam("roleid") String roleid) {
        return roleManagerClient.listEnableUsersByRoleId(roleid);
    }
}
