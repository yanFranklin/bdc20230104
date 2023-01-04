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
@Api(value = "ZrzyXm", tags = {"自然资源单元信息"})
public interface ZrzyXmRestService {

    /**
     * 自然资源单元信息列表查询
     *
     * @param pageable
     * @param zrzydjdyh
     * @param zl
     * @param djyy
     * @return
     */
    @PutMapping(value = "/realestate-natural/rest/v1.0/xm/list")
    Page<ZrzyXmDTO> zrzyXmList(Pageable pageable,
                               @RequestParam(name = "zrzydjdyh") String zrzydjdyh,
                               @RequestParam(name = "zl") String zl,
                               @RequestParam(name = "djyy") String djyy);

    /**
     *
     * @param gzlslid
     * @return
     */
    @GetMapping(value = "/realestate-natural/rest/v1.0/xm/list/{gzlslid}")
    List<ZrzyXmDO> listZrzyXmByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);


    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID删除受理信息
     */
    @DeleteMapping("/realestate-natural/rest/v1.0/xm/delete/{gzlslid}")
    void deleteZrzyXm(@PathVariable(value = "gzlslid") String gzlslid);
}
