package cn.gtmap.realestate.certificate.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
 * @version 1.0  2018-11-01
 * @description
 */
public class BaseController {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    /**
     * 分页查询方法Repo
     *
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询方法Repo
     */
    @Autowired
    protected Repo repo;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description Message提供者接口
     */
    @Autowired
    protected MessageProvider messageProvider;

    @Autowired
    protected UserManagerUtils userManagerUtils;

    /**
     * @param pageable
     * @param map
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理排序信息，处理pageable中sort传入的参数，
     * 将排序信息和查询信息整理成一个map作为查询条件
     */

    public static Map pageableSort(Pageable pageable, Map map) {
        if (pageable.getSort() != null && !"UNSORTED".equals(String.valueOf(pageable.getSort()))) {
            String sort = String.valueOf(pageable.getSort());
            String sortParameter = sort.replace(":", "");
            map.put("sortParameter", sortParameter);
        }
        return map;
    }

    /**
     * @param map    输入参数
     * @param smqKey 扫描枪参数KEY
     * @return 参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 处理扫描枪参数
     */
    public static Map<String, Object> getSmqsrParam(Map<String, Object> map, String smqKey) {
        String smqsr = MapUtils.getString(map, smqKey);
        if (StringUtils.isNotBlank(smqsr)) {
            String[] smq = smqsr.split(CommonConstantUtils.ZF_YW_DH);
            List smqsrList = new ArrayList();
            for (String smqString : smq) {
                if (StringUtils.isNotBlank(smqString)) {
                    smqsrList.add(smqString);
                }
            }
            map.put(smqKey, smqsrList);
        }
        return map;
    }

    /**
     * @param userName 登录账号
     * @param msg      操作信息
     * @return 用户信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据登录用户名获取用户信息
     */
    protected UserDto getUser(String userName, String msg) {
        UserDto user;
        if (StringUtils.isBlank(userName)) {
            user = userManagerUtils.getCurrentUser();
            LOGGER.info("{}获取当前用户1信息为：{}", msg, user);
        } else {
            user = userManagerUtils.getUserByName(userName);
            LOGGER.info("{}获取当前用户2信息为：{}", msg, user);
        }
        return user;
    }
}
