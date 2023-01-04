package cn.gtmap.realestate.common.core.service.rest.accept;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/4/1
 * @description 不动产编号rest服务
 */
public interface BdcBhRestService {

    /**
     * @param ywlx 业务类型
     * @return 自增时间范围 包括YEAR  MONTH  DAY
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据自增时间范围、自增业务类型生成编号
     */
    @GetMapping("/realestate-accept/rest/v1.0/bh")
    String queryBh(@RequestParam(value = "ywlx") String ywlx, @RequestParam(value = "zzsjfw") String zzsjfw);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param ywlx 业务类型
     * @return zzsjfw 自增时间范围 包括YEAR  MONTH  DAY
     * @return {Integer} 流水号
     * @description 获取指定业务类型的流水号（例如 1、2、3）
     */
    @GetMapping("/realestate-accept/rest/v1.0/bh/lsh")
    Integer queryLsh(@RequestParam(value = "ywlx") String ywlx, @RequestParam(value = "zzsjfw") String zzsjfw);

    /**
     * @param ywlx 业务类型
     * @param zzsjfw 自增时间范围
     * @param zzxlh 自增序列号
     * @param prebh 编号前缀
     * @return 编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据自增时间范围、自增业务类型生成编号通用方法
     */
    @GetMapping("/realestate-accept/rest/v1.0/bh/queryCommonBh/{ywlx}/{zzsjfw}/{zzxlh}")
    String queryCommonBh(@PathVariable(name = "ywlx")String ywlx, @PathVariable(name = "zzsjfw") String zzsjfw, @PathVariable(name = "zzxlh")Integer zzxlh, @RequestParam(value = "prebh") String prebh);
}
