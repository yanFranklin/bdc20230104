package cn.gtmap.realestate.common.core.service.rest.natural;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyZrzk;
import cn.gtmap.realestate.common.core.dto.natural.ZrzySlymYwxxDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyXmDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyZrzkYwxxDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 业务信息REST服务
 */
public interface ZrzyYwxxRestService {

    /**
     * @param gzlslid 工作流实例ID
     * @return 自然资源业务填报信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取自然资源业务填报信息
     */
    @GetMapping(value = "/realestate-natural/rest/v1.0/ywxx/{gzlslid}")
    ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param zkxxid 状况信息ID
     * @param zrzklx 自然状况类型
     * @return 自然状况信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据主键获取自然状况信息
     */
    @GetMapping(value = "/realestate-natural/rest/v1.0/ywxx/zkxx/{zkxxid}/{zrzklx}")
    ZrzyZrzk queryZrzyZrzk(@PathVariable(value = "zkxxid") String zkxxid, @PathVariable(value = "zrzklx") String zrzklx);

    /**
     * 根据工作流实例ID获取自然资源自然状况信息
     *
     * @param gzlslid
     * @return
     */
    @GetMapping(value = "/realestate-natural/rest/v1.0/ywxx/info/{gzlslid}")
    ZrzyZrzkYwxxDTO queryZrzyZrzkYwxxDTO(@PathVariable(name = "gzlslid") String gzlslid);


    /**
     * 获取公告模板信息
     * @param gzlslid
     * @return
     */
    @GetMapping(value = "/realestate-natural/rest/v1.0/ywxx/ggmb/{gzlslid}")
    Object queryZrzyGgmbxxDTO(@PathVariable(value = "gzlslid") String gzlslid);
}
