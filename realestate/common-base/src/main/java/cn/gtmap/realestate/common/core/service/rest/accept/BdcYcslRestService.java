package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.dto.accept.BdcSfDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslFjxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslYmxxDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/25
 * @description 一窗受理rest服务
 */
public interface BdcYcslRestService {

    /**
     * @param xmid 项目ID
     * @return 一窗受理信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询一窗受理信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/ycsl/{xmid}")
    YcslYmxxDTO queryYcslYmxx(@PathVariable(value = "xmid") String xmid);



    /**
     * @param gzlslid 工作流受理ID
     * @return 一窗受理信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 查询一窗受理信息核税附件
     */
    @PostMapping("/realestate-accept/rest/v1.0/ycsl/fjxx")
    List<YcslFjxxDTO> queryYcslFjxx(@RequestParam("gzlslid") String gzlslid);


    /**
     * @param xmid 项目ID
     * @return 权利人和义务人税务信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询税务信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/ycsl/swxx/{xmid}")
    BdcSfDTO queryYcslSwxx(@PathVariable(value = "xmid") String xmid);



}
