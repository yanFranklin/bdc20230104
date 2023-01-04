package cn.gtmap.realestate.common.core.service.rest.etl;

import cn.gtmap.realestate.common.core.dto.accept.BdcNtFjParamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/2
 * @description 如东交易库数据获取
 */
public interface RudongFcjyDataRestService {

    /**
     * @param bdcHtxxQoStr 合同参数JSON结构
     * @return 存量房备案分页信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询存量房备案信息分页
     */
    @PostMapping("/realestate-etl/rest/v1.0/rudong/fcjy/clfhtxx/page")
    Page<Map> bdcClfHtxxByPageJson(Pageable pageable,
                                      @RequestParam(name = "bdcHtxxQoStr",required = false) String bdcHtxxQoStr);

    /**
     * @param bdcHtxxQoStr 合同参数JSON结构
     * @return 商品房备案分页信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询商品房备案信息分页
     */
    @PostMapping("/realestate-etl/rest/v1.0/rudong/fcjy/spfhtxx/page")
    Page<Map> bdcSpfHtxxByPageJson(Pageable pageable,
                                   @RequestParam(name = "bdcHtxxQoStr",required = false) String bdcHtxxQoStr);

    /**
     * @param fwfclx 房屋房产类型
     * @param gzlslid 工作流实例ID
     * @param htbh 合同编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 上传房产交易附件到流程
     */
    @PostMapping("/realestate-etl/rest/v1.0/rudong/fcjy/fj")
    void uploadFcjyfj(@RequestParam(name = "fwfclx")String fwfclx, @RequestParam(name = "gzlslid")String gzlslid, @RequestParam(name = "htbh")String htbh, @RequestBody BdcNtFjParamDTO bdcNtFjParamDTO);


}
