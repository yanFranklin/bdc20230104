package cn.gtmap.realestate.common.core.service.rest.analysis;

import cn.gtmap.realestate.common.core.domain.analysis.DtcxDO;
import cn.gtmap.realestate.common.core.dto.analysis.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.dto.analysis.DtcxConfigCheckDTO;
import cn.gtmap.realestate.common.core.qo.analysis.BdcDtcxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.18
 * @description:
 */
public interface BdcDtcxRestService {
    /**
     * 检查查询条件配置正确与否
     *
     * @param sql  sql字符串
     * @param cxtj 查询条件json字符串（所有条件配置项）
     * @return cn.gtmap.realestate.common.core.dto.analysis.DtcxConfigCheckDTO
     * @date 2019.03.21 19:10
     * @author hanyaning
     */
    @PostMapping("/realestate-analysis/rest/v1.0/dtcx/check/cxtj")
    DtcxConfigCheckDTO checkCxtj(@RequestParam("cxsql") String sql, @RequestParam("cxtj") String cxtj);

    /**
     * 检查查询结果配置项正确与否
     * @date 2019.03.21 19:10
     * @author hanyaning
     * @param sql sql
     * @param cxjg 查询结果配置项
     * @return cn.gtmap.realestate.common.core.dto.analysis.DtcxConfigCheckDTO
     */
    @PostMapping("/realestate-analysis/rest/v1.0/dtcx/check/cxjg")
    DtcxConfigCheckDTO checkCxjg(@RequestParam("cxsql") String sql, @RequestParam("cxjg") String cxjg);

    /**
     * 保存动态查询所有配置信息
     * @date 2019.03.21 19:10
     * @author hanyaning
     * @param dtcxDO  动态查询do对象
     * @param sql sql配置
     * @param cxtj 查询条件配置
     * @param cxjg 查询结果配置
     * @return void
     */
    @PostMapping("/realestate-analysis/rest/v1.0/dtcx/save/all")
    void saveAll(@RequestBody DtcxDO dtcxDO, @RequestParam("cxsql") String sql,
                 @RequestParam("cxtj") String cxtj,
                 @RequestParam("cxjg") String cxjg);

    /**
     * 获取动态查询对象分页信息
     *
     * @param dtcxQO 动态查询qo对象
     * @param page   分页参数
     * @param size   分页参数
     * @param sort   分页参数
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.analysis.BdcDtcxDTO>
     * @date 2019.03.21 19:10
     * @author hanyaning
     */
    @PostMapping("/realestate-analysis/rest/v1.0/dtcx/list/page")
    Page<BdcDtcxDTO> listDtcxPage(@RequestBody BdcDtcxQO dtcxQO, @RequestParam("page") int page
            , @RequestParam("size") int size, @RequestParam("sort") Sort sort);

    /**
     * 获取对应查询代号的查询配置信息
     *
     * @param cxdh
     * @return cn.gtmap.realestate.common.core.dto.analysis.BdcDtcxDTO
     * @date 2019.03.27 16:08
     * @author hanyaning
     */
    @GetMapping("/realestate-analysis/rest/v1.0/dtcx/get/{cxdh}")
    BdcDtcxDTO getCxxxByCxdh(@PathVariable("cxdh") String cxdh);

    /**
     * 查询结果获取
     *
     * @param dataString json字符串，包括查询代号、sql、查询条件信息
     * @param page
     * @param size
     * @param sort
     * @return org.springframework.data.domain.Page
     * @date 2019.03.27 16:09
     * @author hanyaning
     */
    @PostMapping("/realestate-analysis/rest/v1.0/dtcx/list/result")
    Page listResult(@RequestParam("dataString") String dataString, @RequestParam("page") int page
            , @RequestParam("size") int size, @RequestParam("sort") Sort sort);

    /**
     * 删除对应查询id的所有配置
     *
     * @param cxid 查询id
     * @return void
     * @date 2019.03.27 16:10
     * @author hanyaning
     */
    @DeleteMapping("/realestate-analysis/rest/v1.0/dtcx/del/{cxid}")
    void delCxConfig(@PathVariable("cxid") String cxid);

    /**
     * 禁用或启用对应查询
     *
     * @param cxid 查询id
     * @param code 0：禁用；1：启用
     * @return void
     * @date 2019.03.27 16:11
     * @author hanyaning
     */
    @PostMapping("/realestate-analysis/rest/v1.0/dtcx/change/status/{cxid}")
    void changeStatus(@PathVariable("cxid") String cxid, @RequestParam("statusCode") String code);

}