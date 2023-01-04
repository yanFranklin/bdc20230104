package cn.gtmap.realestate.common.core.service.rest.inquiry;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/7/2 16:01
 * @description 导入excel查询
 */
public interface BdcDrExcelCxRestService{

    /**
     * 读取excel，返回读取的数据或者在后台获取到需要调用的sql，直接调用需要使用的方法调用饭后返回数据
     *
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/drcx/excelCx")
    Map excelCx(@RequestBody Map<String,List<String>> stringListMap);


}
