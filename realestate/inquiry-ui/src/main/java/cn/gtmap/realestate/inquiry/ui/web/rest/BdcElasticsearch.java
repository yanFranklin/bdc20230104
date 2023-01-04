package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.clients.CustomRecordClient;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/7/31 18:57
 * @description 自定义es查询功能
 */
@RestController
@RequestMapping(value = "rest/v1.0/es")
public class BdcElasticsearch extends BaseController {

    @Autowired
    CustomRecordClient customRecordClient;

    /**
     * 查封es查询
     *
     * @param index 查询表名
     * @param value 查询内容
     * @param keys  查询范围
     * @return 查封分页
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/cf")
    Object listBdcCfPage(@LayuiPageable Pageable pageable,
                         String index,
                         String value,
                         String keys) {
        Page cfMap = customRecordClient.multiWildcard(pageable, index, value, keys);
        return super.addLayUiCode(cfMap);
    }

    /**
     * 抵押es查询
     *
     * @param index 查询表名
     * @param value 查询内容
     * @param keys  查询范围
     * @return 抵押分页
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/dya")
    Object listBdcDyaPage(@LayuiPageable Pageable pageable,
                          String index,
                          String value,
                          String keys) {
        Page dyaMap = customRecordClient.multiWildcard(pageable, index, value, keys);
        return super.addLayUiCode(dyaMap);
    }

    /**
     * 房地产权es查询
     *
     * @param index 查询表名
     * @param value 查询内容
     * @param keys  查询范围
     * @return 房地产权分页
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/fdcq")
    Object listBdcFdcqPage(@LayuiPageable Pageable pageable,
                           String index,
                           String value,
                           String keys) {
        Page fdcqMap = customRecordClient.multiWildcard(pageable, index, value, keys);
        return super.addLayUiCode(fdcqMap);
    }

}
