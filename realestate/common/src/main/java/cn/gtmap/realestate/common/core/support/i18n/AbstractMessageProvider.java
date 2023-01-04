package cn.gtmap.realestate.common.core.support.i18n;

import java.util.Locale;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2018/10/29
 * @description 抽象Message提供者
 */
public abstract class AbstractMessageProvider implements MessageProvider {

    @Override
    public String getMessage(String key) {
        return getMessage(key, null, null, null);
    }

    @Override
    public String getMessage(String key, Object[] args) {
        return getMessage(key, args, null, null);
    }

    @Override
    public String getMessage(String key, Object[] args, String defaultMessage) {
        return getMessage(key, args, defaultMessage, null);
    }

    @Override
    public abstract String getMessage(String key, Object[] args, String defaultMessage, Locale locale);
}
