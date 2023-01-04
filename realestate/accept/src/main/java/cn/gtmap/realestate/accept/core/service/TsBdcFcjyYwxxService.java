package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.dto.accept.TsFcjyYwxxDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description: 推送不动产业务信息
 * @author: jinfei
 * @email:  <a href="mailto:liaoxiang@gtmap.cn">jinfei</a>
 * @date: 2022/9/15 11:04
 **/
public interface TsBdcFcjyYwxxService {

    /**
     * @description 组织请求参数
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/19 14:42
     * @param tsFcjyYwxxDTO
     * @return Map<String,Object>
     */
    Map<String, Object> getFcjyTsYwxxRequestParam(TsFcjyYwxxDTO tsFcjyYwxxDTO);

    /**
     * @description 推送交易信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/19 14:43
     * @param paramMap
     * @return boolean
     */
    boolean tsFcjyYwxx(Map<String, Object> paramMap);

    /**
     * @description 保存操作日志
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/9/19 15:27
     * @param bdcCzrzDO
     * @return void
     */
    void addTssbLog(BdcCzrzDO bdcCzrzDO);
}
