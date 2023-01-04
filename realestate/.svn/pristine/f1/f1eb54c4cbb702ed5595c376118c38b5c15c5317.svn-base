package cn.gtmap.realestate.common.core.service.rest.inquiry.bengbu;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcDwgzglDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 16:16 2020/7/27
 * @description 单位公章管理相关接口
 */
public interface BdcDwgzglRestService {

    /**
     * 分页查询单位公章信息
     *
     * @param pageable
     * @param bdcDwgzJson
     * @return BdcCfxxDTO                                                             ;>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/dwgzgl/page")
    Page<BdcDwgzglDO> listBdcDwgzByPage(Pageable pageable,
                                        @RequestParam(name = "bdcDwgzJson", required = false) String bdcDwgzJson);

    /**
     * @param bdcDwgzglDO
     * @description 插入新的单位公章信息
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping(path = "/realestate-inquiry/rest/v1.0/dwgzgl/insertDwgz")
    BdcDwgzglDO insertBdcDwgz(@RequestBody BdcDwgzglDO bdcDwgzglDO);

    /**
     * 更新单位公章信息
     *
     * @param bdcDwgzglDO bdcDwgzglDO
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 19:52 2020/7/27
     */
    @PutMapping(path = "/realestate-inquiry/rest/v1.0/dwgzgl/updateDwgz")
    int updateBdcDwgz(@RequestBody BdcDwgzglDO bdcDwgzglDO);

}
