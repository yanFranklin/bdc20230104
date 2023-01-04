package cn.gtmap.realestate.common.core.service.rest.naturalresource;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.naturalresource.JbzkDO;
import cn.gtmap.realestate.common.core.dto.naturalresource.JbzkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @version 1.0  2019-01-23
 * @description 为自然资源子系统提供
 * 自然资源基本状况分页查询服务
 */
public interface NaturalJbzkRestService {

    /**
     * 自然资源基本状况分页查询
     *
     * @param pageable
     * @param ysdm
     * @param zl
     * @return
     */
    @PostMapping("/realestate-natural-resource/rest/v1.0/jbzk/list")
    Page<JbzkDO> listJbzkByPage(@LayuiPageable Pageable pageable,
                                @RequestParam(name = "ysdm") String ysdm,
                                @RequestParam(name = "zl") String zl,
                                @RequestParam(name = "mc") String mc
    );


    /**
     * 自然资源基本状况详情
     *
     * @param zrzydjdyh
     * @return
     */
    @GetMapping(value = "/realestate-natural-resource/rest/v1.0/jbzk/info/{zrzydjdyh}")
    JbzkDTO queryJbzkByZrzydjdyh(@PathVariable(name = "zrzydjdyh") String zrzydjdyh);
}
