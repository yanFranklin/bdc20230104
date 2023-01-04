package cn.gtmap.realestate.building.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-11
 * @description 自然幢服务
 */
public interface ZrzService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @description 分页查询 自然幢
     */
    Page<Map> listZrzByPage(Pageable pageable, Map<String, Object> paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return int
     * @description 根据LSZD查询宗地下最大自然幢号（查询FW_LJZ FW_XMXX表）
     */
    int getMaxZrzhByDjh(String djh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.util.List<java.lang.String>
     * @description
     */
    List<String> listZrzhByDjh(String djh);
}
