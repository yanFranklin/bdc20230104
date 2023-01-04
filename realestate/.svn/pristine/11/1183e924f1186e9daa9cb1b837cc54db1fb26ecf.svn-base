package cn.gtmap.realestate.common.core.service.rest.natural;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyXmDTO;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @version 1.0  2021/10/25
 * @description
 */
@Api(value = "ZrzyGg", tags = {"自然资源公告信息"})
public interface ZrzyGgRestService {

    /**
     * @param xmid 工作流实例id
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID新增信息
     */
    @DeleteMapping("/realestate-natural/rest/v1.0/ggmb/insert/{xmid}/{mbnr}")
    void insertZrzyGgmb(@PathVariable(value = "xmid") String xmid,
                        @PathVariable(value = "mbnr") String mbnr);

    /**
     * @param xmid 工作流实例id
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID更新信息
     */
    @DeleteMapping("/realestate-natural/rest/v1.0/ggmb/update/{xmid}/{mbnr}")
    void updateZrzyGgmb(@PathVariable(value = "xmid") String xmid,
                        @PathVariable(value = "mbnr") String mbnr);

    /**
     * @param xmid 工作流实例id
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID删除信息
     */
    @DeleteMapping("/realestate-natural/rest/v1.0/ggmb/delete/{xmid}")
    void deleteZrzyGgmb(@PathVariable(value = "xmid") String xmid);
}
