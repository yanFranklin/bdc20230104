package cn.gtmap.realestate.inquiry.core.dbs;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.inject.internal.asm.$ByteVector;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/3
 * @description 数据源配置类,在启动时触发，在该类中生成多个数据源实例并将其注入到 ApplicationContext 中
 */
@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
public class DataSourceConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfigurer.class);

    private static final String DEFAULT = "default";

    //自动注入环境类，用于获取配置文件的属性值
    @Autowired
    private Environment evn;

    private MybatisProperties mybatisProperties;


    public DataSourceConfigurer(MybatisProperties properties) {
        this.mybatisProperties = properties;
    }

    /**
     * @description 初始化连接数
     */
    @Value("${datasource.initialSize:10}")
    private int initialSize;

    /**
     * @description 最小连接数
     */
    @Value("${datasource.minIdle:10}")
    private int minIdle;

    /**
     * @description 最大激活连接数
     */
    @Value("${datasource.maxActive:500}")
    private int maxActive;

    /**
     * @description 配置获取连接等待超时的时间
     */
    @Value("${datasource.maxwaitmillis:10000}")
    private long maxWaitMillis;

    /**
     * @description 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    @Value("${datasource.timebetweenevictionrunsmillis:60000}")
    private long timeBetweenEvictionRunsMillis;

    /**
     * @description 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    @Value("${datasource.minevictableidletimemillis:300000}")
    private long minEvictableIdleTimeMillis;

    /**
     * @description 配置一个连接在池中最大生存的时间，单位是毫秒
     */
    @Value("${datasource.maxEvictableIdleTimeMillis:600000}")
    private long maxEvictableIdleTimeMillis;

    /**
     * @description 是否进行保活操作
     */
    @Value("${datasource.keepalive:true}")
    private boolean keepalive;



    /**
     * @param  dbType 数据库类型
     * @return
     * @description 创建数据源对象
     */
    private DruidDataSource createDataSource(String dbType) throws Exception{
        //如果不指定数据库类型，则使用默认数据库连接
        String dbName = dbType.trim().isEmpty() ? DEFAULT : dbType.trim();
        DruidDataSource dataSource = new DruidDataSource();
        String prefix;
        if(StringUtils.equals(DEFAULT,dbName)){
            prefix = "spring.datasource.";
        }else {
            prefix = "spring.datasource." + dbName + ".";
        }
        String dbUrl =evn.getProperty(prefix + "url");
        logger.info("数据库连接url：{}",dbUrl);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(evn.getProperty( prefix + "username"));
        dataSource.setPassword(evn.getProperty( prefix + "password"));
        dataSource.setDefaultAutoCommit(false);
        dataSource.setDefaultTransactionIsolation(2);
        //配置初始化大小、最小、最大
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        //配置获取连接等待超时的时间
        dataSource.setMaxWait(maxWaitMillis);
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);
        dataSource.setKeepAlive(keepalive);
        dataSource.setValidationQuery("SELECT SYSDATE FROM DUAL");
        dataSource.setTestWhileIdle(true);
        //是否测试连接池里连接是否可用
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        //开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        dataSource.setLogAbandoned(true);
        //配置监控统计拦截的filters，去掉后监控界面sql无法统计
        dataSource.setFilters("config");
        dataSource.setConnectionProperties("config.decrypt=true;config.decrypt.key="+evn.getProperty(prefix + "publickey"));
        return dataSource;
    }

    /**
     * @description spring boot 启动后将自定义创建好的数据源对象放到TargetDataSources中用于后续的切换数据源用,同时指定默认数据源连接
     */
    @Bean
    public DynamicDataSource dynamicDataSource() throws Exception
    {
        //获取动态数据库的实例（单例方式）
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();
        //自定义数据源key值，将创建好的数据源对象，赋值到targetDataSources中,用于切换数据源时指定对应key即可切换
        Map<Object,Object> map = new HashMap<>();
        //创建默认数据库连接对象
        DruidDataSource defaultDataSource = createDataSource(DEFAULT);
        map.put(DEFAULT, defaultDataSource);
        //创建其他数据库连接对象
        String dataSources=evn.getProperty("dynamic.datasources");
        String dynamicDatas = evn.getProperty("dynamic.enable");
        if(StringUtils.isNotBlank(dataSources) && "true".equals(dynamicDatas)) {
            for(String dbkey:dataSources.split(CommonConstantUtils.ZF_YW_DH)) {
                DruidDataSource dataSource = createDataSource(dbkey);
                map.put(dbkey, dataSource);
            }
        }

        dynamicDataSource.setTargetDataSources(map);
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);

        return dynamicDataSource;
    }


}
