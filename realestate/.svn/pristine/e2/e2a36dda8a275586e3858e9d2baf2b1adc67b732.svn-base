package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGxjkPdfDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/2/4
 * @description
 */
public interface BdcGxjkRestService {

    /**
     * @param dataList 共享接口数据
     * @return redis-key
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  保存共享接口数据到REDIS
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/gxjk/redis")
    String saveGxjkDataToRedis(@RequestBody List<Map> dataList);

    /**
     * @param bdcGxjkPdfDTO 共享接口PDF实体
     * @return PDF打印数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取PDF打印数据
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/gxjk/xml")
    String getPrintXmlOfGxjk(@RequestBody BdcGxjkPdfDTO bdcGxjkPdfDTO);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @param bdcGxjkPdfDTO PDF及流程信息
     * @return {String} 文件存储ID
     * @description  保存验证结果文件到大云并关联到当前流程作为附件
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/gxjk/pdf")
    String saveGxjkPdfFile(@RequestBody BdcGxjkPdfDTO bdcGxjkPdfDTO);
}
