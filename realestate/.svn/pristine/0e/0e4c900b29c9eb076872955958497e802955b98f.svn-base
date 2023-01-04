package cn.gtmap.realestate.common.core.service.rest.exchange;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/6/8
 * @description 电相关接口服务
 */
public interface DianService {

    /**
     * @param
     * @param processInsId
     * @param currentUserName
     * @param jysfbl 校验是否办理
     * @return void
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/7/22 13:54
     * @description 根据gzlslid申请电过户
     **/
    @PostMapping("/realestate-exchange/rest/v1.0/ElectricGh/proccessid")
    void sqElectricGh(@RequestParam(name = "processInsId") String processInsId,
                      @RequestParam(value = "currentUserName", required = false) String currentUserName,
                      @RequestParam(value = "jysfbl", required = false) Boolean jysfbl);

    /**
     * @param
     * @param processInsId
     * @param currentUserName
     * @return void
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/16 16:34
     * @description 申请电过户（可无户号）
     **/
    @PostMapping("/realestate-exchange/rest/v1.0/ElectricGh/cannohh/proccessid")
    void sqElectricGhCanNohh(@RequestParam(name = "processInsId") String processInsId,
                      @RequestParam(value = "currentUserName", required = false) String currentUserName);

    /**
     * 查询电户号基本信息
     * @param electricFeeNum 电户号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 电客户基本信息
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ElectricGh/dian/yhxx")
    Object queryDianYhxx(@RequestParam(name = "electricFeeNum") String electricFeeNum, @RequestParam(value = "currentUserName",required = false) String currentUserName);

    /**
     * 查询电过户信息
     * @param contractId 申请流水号
     * @return 过户结果
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ElectricGh/dian/ghxx")
    Object queryDianGhxx(@RequestParam(name = "contractId") String contractId, @RequestParam(value = "currentUserName",required = false) String currentUserName);
}
