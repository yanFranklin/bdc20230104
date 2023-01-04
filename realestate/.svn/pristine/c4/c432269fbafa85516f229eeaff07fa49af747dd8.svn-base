package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXdryxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclPzDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXdryQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcXdRyxxRestService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.inquiry.service.BdcXdryxxService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: 3.0
 * @description: 限定人员信息restController
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-08-03 10:13
 **/
@RestController
@Api(value = "限定人员信息")
public class BdcXdryxxRestController implements BdcXdRyxxRestService {

    @Autowired
    Repo repo;
    @Autowired
    BdcXdryxxService bdcXdryxxService;

    /**
     * @param bdcXdryQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询限定人员信息
     * @date : 2021/8/3 10:15
     */
    @Override
    public List<BdcXdryxxDO> listBdcXdyrxx(@RequestBody BdcXdryQO bdcXdryQO) {
        return bdcXdryxxService.listBdcXdyrxx(bdcXdryQO);
    }

    /**
     * @param pageable
     * @param bdcXdryJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询限定人员信息
     * @date : 2021/8/3 11:16
     */
    @Override
    public Page<BdcXdryxxDO> listBdcXdryByPage(Pageable pageable, @RequestParam(value = "查询条件json", required = false) String bdcXdryJson) {
        BdcXdryQO bdcXdryQO = new BdcXdryQO();
        if (StringUtils.isNotBlank(bdcXdryJson)) {
            bdcXdryQO = JSONObject.parseObject(bdcXdryJson, BdcXdryQO.class);
        }
        return repo.selectPaging("listBdcXdyrxxByPage", bdcXdryQO, pageable);
    }

    /**
     * @param idList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除限定人员信息
     * @date : 2021/8/3 11:16
     */
    @Override
    public void deletXdryxx(@RequestBody List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            bdcXdryxxService.deletXdryxx(idList);
        }
    }

    /**
     * @param bdcXdryxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增限定人员信息
     * @date : 2021/8/3 11:16
     */
    @Override
    public BdcXdryxxDO addBdcXdryxx(@RequestBody BdcXdryxxDO bdcXdryxxDO) {
        return bdcXdryxxService.addBdcXdryxx(bdcXdryxxDO);
    }

    /**
     * @param bdcXdryxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新限定人员信息
     * @date : 2021/8/3 11:17
     */
    @Override
    public BdcXdryxxDO updateBdcXdryxx(@RequestBody BdcXdryxxDO bdcXdryxxDO) {
        return bdcXdryxxService.updateBdcXdryxx(bdcXdryxxDO);
    }
}
