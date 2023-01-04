package cn.gtmap.realestate.inquiry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/12/17
 * @description 接口动态查询
 */
public interface DtcxJkViewService {
    /**
     * 查询操作获取结果
     *
     * @param pageable
     * @param jkid     接口id
     * @param dataMap  查询条件集
     * @return org.springframework.data.domain.Page
     */
    Object listResultByPage(Pageable pageable, String jk, String jkid, String fhzkey, Map dataMap);
}
