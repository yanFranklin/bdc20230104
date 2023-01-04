package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcZdytjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZdttjTbxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDtcxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZdytjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version: V1.0, 2022/09/08
 * @description: 自定义统计服务
 */
public interface BdcZdytjRestService {

    /**
     * 保存动态查询所有配置信息
     *
     * @param bdcZdytjDO
     * @return void
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdczdytj/save/all")
    void saveAll(@RequestBody BdcZdytjDO bdcZdytjDO);


    /**
     * 获取动态查询对象分页信息
     *
     * @param zdytjQO 动态查询qo对象
     * @param page   分页参数
     * @param size   分页参数
     * @param sort   分页参数
     * @return
     * @date 2022/09/08
     * @author wutao
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdczdytj/list/page")
    Page<BdcZdytjDO> listZdytjPage(@RequestBody BdcZdytjQO zdytjQO, @RequestParam("page") int page
            , @RequestParam("size") int size, @RequestParam(value = "sort", required = false) Sort sort);

    /**
     * 获取对应查询代号的查询配置信息
     *
     * @param cxdh
     * @return
     * @date 2022/09/08
     * @author wutao
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/bdczdytj/get/{cxdh}")
    BdcZdytjDO getCxxxByCxdh(@PathVariable("cxdh") String cxdh);

    /**
     * 测试配置的sql是否能正常运行
     *
     * @param sql
     * @return
     * @date 2022/09/08
     * @author wutao
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/bdczdytj/checksql")
    Boolean checkSql(@RequestBody BdcZdytjDO bdcZdytjDO);

    /**
     * 获取对应查询代号的查询配置信息
     *
     * @param cxdh
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcZdttjTbxxDTO
     * @date 2022/09/08
     * @author wutao
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/bdczdytj/gettbxx/{cxdh}")
    BdcZdttjTbxxDTO getTbxxByCxdh(@PathVariable("cxdh") String cxdh);

    /**
     * 删除对应查询id的所有配置
     *
     * @param cxid 查询id
     * @return void
     * @date 2022/09/08
     * @author wutao
     */
    @DeleteMapping("/realestate-inquiry/rest/v1.0/dtcx/bdczdytj/{cxid}")
    void delCxConfig(@PathVariable("cxid") String cxid);

}