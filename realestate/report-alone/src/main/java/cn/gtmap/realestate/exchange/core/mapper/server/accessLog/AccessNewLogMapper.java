package cn.gtmap.realestate.exchange.core.mapper.server.accessLog;


import cn.gtmap.realestate.common.core.bo.accessnewlog.Access;
import cn.gtmap.realestate.common.core.bo.accessnewlog.Register;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/4/27
 * @description 登簿、上报数据组织-如实上报服务，不作弊
 */
@Component
public interface AccessNewLogMapper {

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取当天各业务的登簿数量
     */
    List<Map<String, String>> getDjsl(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取当天各业务的登簿数量 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，通过xm表的qxdm统计登簿量和接入量
     */
    List<Map<String, String>> getXnDjsl(Map map);

    /**
     * 获取登簿数据详情
     *
     * @param map
     * @return
     * @Date 2022/4/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Register> getRegisterDetails(Map map);


    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 获取当天各业务的接入成功的数量
     */
    List<Map<String, String>> getAccessSl(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取当天各业务的接入成功的数量 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，通过xm表的qxdm统计登簿量和接入量
     */
    List<Map<String, String>> getXnAccessSl(Map map);

    /**
     * 上报接入数量详情
     *
     * @param map
     * @return
     * @Date 2022/4/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Access> getAccessDetails(Map map);

    /**
     * 上报接入数量详情-查项目表区县代码
     *
     * @param map
     * @return
     * @Date 2022/4/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<Access> getXnAccessDetails(Map map);
}
