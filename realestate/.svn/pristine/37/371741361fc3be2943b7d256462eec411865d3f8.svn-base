package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDtcxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version: V1.0, 2019/07/16
 * @description:
 */
public interface BdcDtcxRestService{
    /**
     * 检查查询条件配置正确与否
     *
     * @param sql  sql字符串
     * @param cxtj 查询条件json字符串（所有条件配置项）
     * @return cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO
     * @date 2019/07/16
     * @author hanyaning
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dtcx/check/cxtj")
    DtcxConfigCheckDTO checkCxtj(@RequestParam("cxsql") String sql,@RequestParam("cxtj") String cxtj);

    /**
     * 检查查询结果配置项正确与否
     *
     * @param sql  sql
     * @param cxjg 查询结果配置项
     * @return cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO
     * @date 2019/07/16
     * @author hanyaning
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dtcx/check/cxjg")
    DtcxConfigCheckDTO checkCxjg(@RequestParam("cxsql") String sql,@RequestParam("cxjg") String cxjg);

    /**
     * 保存动态查询所有配置信息
     *
     * @param dtcxDO  动态查询do对象
     * @param fuzhiCx 是否为复制查询
     * @return void
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dtcx/save/all")
    void saveAll(@RequestBody BdcDtcxDTO dtcxDO,@RequestParam("fuzhiCx") boolean fuzhiCx);

    /**
     * 保存动态查询导入的配置信息
     *
     * @param bdcDtcxDTO 动态查询传输对象
     * @return void
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dtcx/save/config")
    void saveConfig(@RequestBody BdcDtcxDTO bdcDtcxDTO);

    /**
     * 获取动态查询对象分页信息
     *
     * @param dtcxQO 动态查询qo对象
     * @param page   分页参数
     * @param size   分页参数
     * @param sort   分页参数
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO>
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dtcx/list/page")
    Page<BdcDtcxDTO> listDtcxPage(@RequestBody BdcDtcxQO dtcxQO,@RequestParam("page") int page
            ,@RequestParam("size") int size,@RequestParam(value = "sort", required = false) Sort sort);

    /**
     * 获取对应查询代号的查询配置信息
     *
     * @param cxdh
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO
     * @date 2019/07/16
     * @author hanyi
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/dtcx/get/{cxdh}")
    BdcDtcxDTO getCxxxByCxdh(@PathVariable("cxdh") String cxdh);

    /**
     * 查询结果获取
     *
     * @param dataString json字符串，包括查询代号、sql、查询条件信息
     * @param page
     * @param size
     * @param sort
     * @return org.springframework.data.domain.Page
     * @date 2019/07/16
     * @author hanyi
     *
     * 修改dataString通过@RequestBody获取参数，避免get请求头过大问题
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dtcx/list/result")
    Page listResult(@RequestBody String dataString,@RequestParam("page") int page
            ,@RequestParam("size") int size,@RequestParam(value = "sort", required = false) Sort sort);

    /**
     * 删除对应查询id的所有配置
     *
     * @param cxid 查询id
     * @return void
     * @date 2019/07/16
     * @author hanyi
     */
    @DeleteMapping("/realestate-inquiry/rest/v1.0/dtcx/del/{cxid}")
    void delCxConfig(@PathVariable("cxid") String cxid);

    /**
     * hy 通过查询信息生成SQL
     *
     * @param dataString json字符串，包括查询代号、sql、查询条件信息
     * @return 查询SQL
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dtcx/list/result/data")
    List<Map> listResultData(@RequestBody String dataString);

    /**
     * hy 通过查询id获取查询结果
     *
     * @param cxid
     * @return 查询SQL
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/dtcx/list/cxjg/{cxid}")
    List<DtcxCxjgDO> getCxjgList(@PathVariable("cxid") String cxid);
}