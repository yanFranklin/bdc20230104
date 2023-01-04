package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcFcdaDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFwqlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcFwJbxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFcdaQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcFwQsxxQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/8
 * @description 查询子系统：不动产住房信息查询服务接口定义
 */
public interface BdcZfxxCxRestService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @description 根据权利人名称、证件号查询房产信息（有房、无房查询）
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zfxx")
    List<BdcZfxxDTO> listBdcZfxxDTO(@RequestBody BdcZfxxQO bdcZfxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @description 根据权利人名称、证件号查询房产信息（有房、无房查询）
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/nantong/zfxx")
    List<BdcZfxxDTO> listBdcNtZfxxDTO(@RequestBody BdcZfxxQO bdcZfxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return {List} 房产档案信息
     * @description 根据不动产单元号查询房产档案信息
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/zfxx/{bdcdyh}/fcda")
    BdcFcdaDTO getBdcFcdaDTO(@PathVariable("bdcdyh")String bdcdyh,@RequestParam(value = "qjgldm",required = false) String qjgldm);


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param BdcFcdaQO 查询参数
     * @return {List} 房产档案信息
     * @description 根据不动产单元号查询房产档案信息(exchange转发用)
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zfxx/fcda")
    BdcFcdaDTO getBdcFcdaInfo(@RequestBody BdcFcdaQO bdcFcdaQO);

    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param bdcFwqlQO 查询参数
     * @return {List} 房屋权利
     * @description  自助查询机查询房屋权利
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zzcxj/fwql")
    List<BdcFwqlDTO> listBdcFwqlDTO(@RequestBody BdcFwqlQO bdcFwqlQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFwQsxxQO 查询参数
     * @return {List} 房屋权属信息
     * @description  （盐城）查询房屋权属信息（以物为主）
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zzcxj/fwqsxx")
    List<BdcFwJbxxDTO> listBdcFwQsxxDTO(@RequestBody BdcFwQsxxQO bdcFwQsxxQO);


    /**
     * 自助查询机查询合同信息
     * @param bdcZfxxQO
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zzcxj/htxx")
    List<BdcZfxxDTO> listBdcHtxx(@RequestBody BdcZfxxQO bdcZfxxQO);
}
