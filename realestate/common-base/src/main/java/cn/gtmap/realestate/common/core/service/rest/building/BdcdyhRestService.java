package cn.gtmap.realestate.common.core.service.rest.building;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/20
 * @description
 */
public interface BdcdyhRestService {
    /**
     * @param fwDcbIndex
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢主键生成不动产单元号
     */
    @GetMapping("/building/rest/v1.0/bdcdyh/{fwDcbIndex}")
    String creatFwBdcdyhByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex);
    /**
     * @param djh
     * @param zrzh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description
     */
    @GetMapping("/building/rest/v1.0/bdcdyh/{djh}/{zrzh}")
    String creatFwBdcdyhByDjhAndZrzh(@PathVariable("djh") String djh, @PathVariable("zrzh") String zrzh);
    /**
     * @param fwXmxxIndex
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键生成不动产单元号
     */
    @GetMapping("/building/rest/v1.0/xmxx/bdcdyh/{fwXmxxIndex}")
    String creatXmxxBdcdyhByFwXmxxIndex(@PathVariable("fwXmxxIndex") String fwXmxxIndex);


    /**
     * @param bdcdyh,qjgldm
     * @return java.lang.String
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 验证选择的不动产单元是否存在且有实测面积
     */
    @GetMapping("/building/rest/v1.0/checkscmj/bdcdyh/{bdcdyh}")
    String checkBdcdyhSfczscmj(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh,qjgldm
     * @return java.lang.String
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 验证所在宗地不动产单元锁定
     */
    @GetMapping("/building/rest/v1.0/checksfsd/bdcdyh/{bdcdyh}")
    String checkBdcdyhSfsd(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);
}
