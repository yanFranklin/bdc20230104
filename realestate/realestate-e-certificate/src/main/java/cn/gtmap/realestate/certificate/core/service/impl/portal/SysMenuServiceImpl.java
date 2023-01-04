package cn.gtmap.realestate.certificate.core.service.impl.portal;


import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzMenuMapper;
import cn.gtmap.realestate.certificate.core.service.portal.SysMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019-9-2
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BdcDzzzMenuMapper bdcDzzzMenuMapper;

    /*@Override
    public List<Map> getMenusByUserInfo(UserInfo userInfo) {
        Map param = new HashMap(6);
        param.put("pfPlatformMenu", BdcDzzzPdfUtil.PFPLATFORMMENU);
        param.put("pfPlatformAuthorize",BdcDzzzPdfUtil.PFPLATFORMAUTHORIZE);
        param.put("pfPlatformResource",BdcDzzzPdfUtil.PFPLATFORMRESOURCE);
        if (!userInfo.isAdmin()){
            String roles=userInfo.getRoleIds();
            if (StringUtils.isNotBlank(roles))
            {
                param.put("roles",roles);
            } else {
                param.put("roles","'-99'");
            }
        }
        logger.info("\n用户台账权限获取：参数：" + JSON.toJSONString(param) + "\n");
        return bdcDzzzMenuMapper.getMenuListByRoles(param);
    }*/
}
