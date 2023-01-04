package cn.gtmap.realestate.building.config;

import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-06
 * @description 不动产 同步 权籍信息 对照
 */
@ImportResource(locations = {"classpath:conf/spring/config-dozer.xml"})
@Service
public class DozerConfig {
}
