package cn.gtmap.realestate.accept.ui.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @program: realestate
 * @description: listmap去重
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-10 14:26
 **/
public class StreamEx  {
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
