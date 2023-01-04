package cn.gtmap.realestate.common.core.service.rest.pub;

import cn.gtmap.realestate.common.core.dto.pub.ResponseEntityDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/12/16
 * @description 查询查封信息
 */
public interface SfxxRestService {
    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param slbh 受理编号
     * @return
     * @description 通过受理编号查询收费信息
     */
    @GetMapping(value = "/realestate-public/rest/v1.0/sfxx/slbh/{slbh}")
    ResponseEntityDTO querySfxxBySlbh(@PathVariable(value = "slbh") String slbh);

}
