package cn.gtmap.realestate.common.core.service.rest.building;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-28
 * @description 地籍调查表页面相关服务
 */
public interface DjdcbRestService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.List<java.lang.String>
     * @description 获取配置  地籍调查表页面需要显示的 TAB标签
     */
    @GetMapping("/building/rest/v1.0/djdcb/listtabname")
    List<String> listTabName();

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 从逻辑装页面打开的地籍调查表也买你展示Tab标签
     * @date : 2020/10/22 11:21
     */
    @GetMapping("/building/rest/v1.0/djdcb/tab/ljz")
    List<String> listTabNameFromLjz();

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  验证不动产单元是否有经营权地块
     */
    @GetMapping("/building/rest/v1.0/djdcb/jyq/{bdcdyh}")
    boolean hasJyqdkDcb(@PathVariable("bdcdyh") String bdcdyh);
}
