package cn.gtmap.realestate.exchange.config;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/28
 * @description 路径扫描匹配自定义规则
 */
public class FilterCustom implements TypeFilter {
    /**
     * 定义匹配路径（这里是应用启动时不需要扫描的包）
     */
    private static final String ZD_CACHE_PATH = "cn.gtmap.realestate.exchange.core.cache";
    private static final String ZD_UTIL_PATH = "cn.gtmap.realestate.exchange.util.EntityZdConvertUtils";


    /**
     * Determine whether this filter matches for the class described by
     * the given metadata.
     *
     * @param metadataReader        the metadata reader for the target class
     * @param metadataReaderFactory a factory for obtaining metadata readers
     *                              for other classes (such as superclasses and interfaces)
     * @return whether this filter matches
     * @throws IOException in case of I/O failure when reading metadata
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取路径名称
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        String className = classMetadata.getClassName();

        // 匹配路径
        if (className.startsWith(ZD_CACHE_PATH) ||
                className.equals(ZD_UTIL_PATH)) {
            return true;
        }
        return false;
    }
}
