package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobUserDO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.config.core.mapper.BdcJobGroupMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobUserMapper;
import cn.gtmap.realestate.config.core.service.LoginService;
import cn.gtmap.realestate.config.job.annotation.PermissionLimit;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  2019-05-04 16:39:50
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private BdcJobUserMapper bdcJobUserMapper;
    @Resource
    private BdcJobGroupMapper bdcJobGroupMapper;

    @RequestMapping
    @PermissionLimit(adminuser = true)
    public String index(Model model) {

        // 执行器列表
        List<BdcJobGroupDTO> groupList = bdcJobGroupMapper.findAll();
        model.addAttribute("groupList", groupList);

        return "user/user.index";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        String username, int role) {

        // page list
        List<BdcJobUserDO> list = bdcJobUserMapper.pageList(start, length, username, role);
        int list_count = bdcJobUserMapper.pageListCount(start, length, username, role);

        // filter
        if (list!=null && list.size()>0) {
            for (BdcJobUserDO item: list) {
                item.setPassword(null);
            }
        }

        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);		// 总记录数
        maps.put("recordsFiltered", list_count);	// 过滤后的总记录数
        maps.put("data", list);  					// 分页列表
        return maps;
    }

    @RequestMapping("/add")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public ReturnT<String> add(BdcJobUserDO bdcJobUserDO) {

        // valid username
        if (!StringUtils.hasText(bdcJobUserDO.getUsername())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("system_please_input")+I18nUtil.getString("user_username") );
        }
        bdcJobUserDO.setUsername(bdcJobUserDO.getUsername().trim());
        if (!(bdcJobUserDO.getUsername().length()>=4 && bdcJobUserDO.getUsername().length()<=20)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("system_lengh_limit")+"[4-20]" );
        }
        // valid password
        if (!StringUtils.hasText(bdcJobUserDO.getPassword())) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("system_please_input")+I18nUtil.getString("user_password") );
        }
        bdcJobUserDO.setPassword(bdcJobUserDO.getPassword().trim());
        if (!(bdcJobUserDO.getPassword().length()>=4 && bdcJobUserDO.getPassword().length()<=20)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("system_lengh_limit")+"[4-20]" );
        }
        // md5 password
        bdcJobUserDO.setPassword(DigestUtils.md5DigestAsHex(bdcJobUserDO.getPassword().getBytes()));

        // check repeat
        BdcJobUserDO existUser = bdcJobUserMapper.loadByUserName(bdcJobUserDO.getUsername());
        if (existUser != null) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("user_username_repeat") );
        }

        // write
        bdcJobUserMapper.save(bdcJobUserDO);
        return ReturnT.SUCCESS;
    }

    @RequestMapping("/update")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public ReturnT<String> update(HttpServletRequest request, BdcJobUserDO bdcJobUserDO) {

        // avoid opt login seft
        BdcJobUserDO loginUser = (BdcJobUserDO) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        if (loginUser.getUsername().equals(bdcJobUserDO.getUsername())) {
            return new ReturnT<String>(ReturnT.FAIL.getCode(), I18nUtil.getString("user_update_loginuser_limit"));
        }

        // valid password
        if (StringUtils.hasText(bdcJobUserDO.getPassword())) {
            bdcJobUserDO.setPassword(bdcJobUserDO.getPassword().trim());
            if (!(bdcJobUserDO.getPassword().length()>=4 && bdcJobUserDO.getPassword().length()<=20)) {
                return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("system_lengh_limit")+"[4-20]" );
            }
            // md5 password
            bdcJobUserDO.setPassword(DigestUtils.md5DigestAsHex(bdcJobUserDO.getPassword().getBytes()));
        } else {
            bdcJobUserDO.setPassword(null);
        }

        // write
        bdcJobUserMapper.update(bdcJobUserDO);
        return ReturnT.SUCCESS;
    }

    @RequestMapping("/remove")
    @ResponseBody
    @PermissionLimit(adminuser = true)
    public ReturnT<String> remove(HttpServletRequest request, int id) {

        // avoid opt login seft
        BdcJobUserDO loginUser = (BdcJobUserDO) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        if (loginUser.getId() == id) {
            return new ReturnT<String>(ReturnT.FAIL.getCode(), I18nUtil.getString("user_update_loginuser_limit"));
        }

        bdcJobUserMapper.delete(id);
        return ReturnT.SUCCESS;
    }

    @RequestMapping("/updatePwd")
    @ResponseBody
    public ReturnT<String> updatePwd(HttpServletRequest request, String password){

        // valid password
        if (password==null || password.trim().length()==0){
            return new ReturnT<String>(ReturnT.FAIL.getCode(), "密码不可为空");
        }
        password = password.trim();
        if (!(password.length()>=4 && password.length()<=20)) {
            return new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("system_lengh_limit")+"[4-20]" );
        }

        // md5 password
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        // update pwd
        BdcJobUserDO loginUser = (BdcJobUserDO) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);

        // do write
        BdcJobUserDO existUser = bdcJobUserMapper.loadByUserName(loginUser.getUsername());
        existUser.setPassword(md5Password);
        bdcJobUserMapper.update(existUser);

        return ReturnT.SUCCESS;
    }

}
