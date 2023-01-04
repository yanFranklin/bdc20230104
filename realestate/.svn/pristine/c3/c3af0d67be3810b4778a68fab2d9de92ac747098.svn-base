package cn.gtmap.realestate.common.core.support.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2018/10/29
 * @description Message实现类
 */
@Component
public class SpringMessageProvider extends AbstractMessageProvider {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String key, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(key, args, defaultMessage, locale);
    }
}
