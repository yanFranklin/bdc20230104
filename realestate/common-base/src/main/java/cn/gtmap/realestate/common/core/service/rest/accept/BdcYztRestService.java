package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcVYztGyGdxmDkDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 一张图
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2020/12/18
 **/
public interface BdcYztRestService {

    /**
     * @param pageable
     * @param bdcGdspxxQOJson
     * @return Page<BdcVYztGyGdxmDkDO>
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询 供地审批信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/yzt/gdspxx/page")
    Page<BdcVYztGyGdxmDkDO> listBdcGdspxxByPage(Pageable pageable, @RequestParam(name = "bdcGdspxxQOJson", required = true) String bdcGdspxxQOJson);


}
