package cn.gtmap.realestate.etl.service;


import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
 * @version 2020/06/11,1.0
 * @description 互联网业务信息查询方法
 */
public interface HlwYwxxService {
    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<Map>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 分页查询互联网业务信息
     */
    Page<Map> listHlwYwxxByPage(Pageable pageable, Map map);

    /**
     * 根据互联网xmid查询互联网业务信息，组织为创建接口需要的格式
     *
     * @param hlwxmid
     * @return
     */
    JSONObject queryHlwJsonByHlwXmid(String hlwxmid);
}
