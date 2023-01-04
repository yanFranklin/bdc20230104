package cn.gtmap.realestate.common.config;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/28
 * @description  路径扫描匹配自定义规则
 */
public class FilterCustom implements TypeFilter {
    /**
     * 定义匹配路径（这里是应用启动时不需要扫描的包）
     */
    private static final String[] PATHS = {"cn.gtmap.realestate.common.core.service.Impl",
            "cn.gtmap.realestate.common.core.support.mybatis",
            "cn.gtmap.realestate.common.core.cache",
            "cn.gtmap.realestate.common.util.EntityZdConvertUtils",
            "cn.gtmap.realestate.common.config.interceptor",
            "cn.gtmap.realestate.common.sendMsg"};

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
        for(String path : PATHS) {
            if(className.startsWith(path) || className.equals(path)){
                return true;
            }
        }
        return false;
    }
}
