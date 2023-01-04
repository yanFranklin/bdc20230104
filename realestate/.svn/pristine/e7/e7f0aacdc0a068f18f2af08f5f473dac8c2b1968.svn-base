package cn.gtmap.realestate.inquiry.core.dbs;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/4
 * @description 切换数据库
 */
@Configuration
public class SwitchDB {

    private static final Logger logger = LoggerFactory.getLogger(SwitchDB.class);

    /**
      * @param dbName 已存在的数据库源对象
      * @return 返回当前数据库连接对象对应的key
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 切换数据库对外方法
      */
    public String change(String dbName){
        //切换数据库
        toDB(dbName);
        //获取当前连接的数据源对象的key
        return  DynamicDataSourceContextHolder.getDataSourceKey();

    }

   /**
     * @param dbName 已存在的数据库源对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 切换已存在的数据源
     */
    private void toDB(String dbName)
    {
        //如果不指定数据库，则直接连接默认数据库
        String dbSourceKey = StringUtils.isBlank(dbName) ||StringUtils.isBlank(dbName.trim()) ? "default" : dbName.trim();
        //获取当前连接的数据源对象的key
        String currentKey = DynamicDataSourceContextHolder.getDataSourceKey();
        //如果当前数据库连接已经是想要的连接，则直接返回
        if(StringUtils.equals(currentKey,dbSourceKey)) {
            return;
        }
        //判断储存动态数据源实例的map中key值是否存在
        if( DynamicDataSource.isExistDataSource(dbSourceKey) ){
            DynamicDataSourceContextHolder.setDataSourceKey(dbSourceKey);
            logger.info("当前数据源:{}",dbSourceKey);
        }else {
            logger.error("切换普通数据库时，数据源key={}不存在" ,dbName);
        }
    }
}
