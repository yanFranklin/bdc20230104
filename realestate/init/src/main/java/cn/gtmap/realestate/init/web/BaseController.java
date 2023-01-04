package cn.gtmap.realestate.init.web;

import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础controller
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/1.
 * @description
 */
public class BaseController {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    protected MessageProvider messageProvider;

    @Autowired
    protected Repo repo;

}
