package cn.gtmap.realestate.common.core.service.rest.inquiry.huaian;

import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcCstjxxVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcRyGzltjXmxxVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZqxGzltjXmxxVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/6/27
 * @description 工作量统计RestService, 根据项目表信息统计工作量
 */
public interface BdcGzlTjXmxxRestService {

    /**
     * 统计人员工作量信息（依据登记项目、证书等表数据）
     * @param  gzltjQO 查询参数：部门、统计类型、开始时间、结束时间
     * @return BdcGzltjXmxxVO 工作量内容：办件量、打印有房无房、权属证明、登记簿数量
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/gzltj/xmxx/list/rygzlxx")
    List<BdcRyGzltjXmxxVO> listRyGzltjByBdcxx(@RequestBody GzltjQO gzltjQO);

    /**
     * 周期性统计工作量信息（依据登记项目、证书等表数据）
     * @param  gzltjQO 查询参数：部门、统计维度、统计类型、开始时间、结束时间
     * @return BdcZqxGzltjXmxxVO 工作量内容：办件量、证书、证明、打印有房无房、权属证明、登记簿数量等
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/gzltj/xmxx/list/zqxgzlxx")
    List<BdcZqxGzltjXmxxVO> listZqxGzltjByBdcxx(@RequestBody GzltjQO gzltjQO);

    /**
     * 流程超期统计信息
     * @param  conditionStr 查询参数：部门、流程类型、开始时间、结束时间
     * @return BdcCstjxxVO 流程超期统计信息
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/cstj/lcxx/list")
    List<BdcCstjxxVO> listBdcCstjxx(@RequestBody String conditionStr);

}
