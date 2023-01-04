package cn.gtmap.realestate.common.core.service.rest.pub;

import cn.gtmap.realestate.common.core.dto.pub.ResponseEntityDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/12/16
 * @description 查询查封信息
 */
public interface CfxxRestService {
    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param bdcqzh 不动产权证号
     * @return
     * @description 通过不动产权证号查询查封信息
     */
    @GetMapping(value = "/realestate-public/rest/v1.0/cfxx/bdcqzh/{bdcqzh}")
    ResponseEntityDTO queryCfxxByBdcqzh(@PathVariable(value = "bdcqzh") String bdcqzh);

}
