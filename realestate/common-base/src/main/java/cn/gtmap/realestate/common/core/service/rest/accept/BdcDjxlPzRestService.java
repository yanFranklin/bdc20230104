package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDjxlPzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/12
 * @description 不动产登记小类配置rest服务
 */
public interface BdcDjxlPzRestService {
    /**
     * @param gzldyid 工作流定义ID
     * @param qllx    权利类型
     * @return 不动产登记小类配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产登记小类配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/djxlpz/list")
    List<BdcDjxlPzDO> listBdcDjxlPzByGzldyid(@RequestParam(value = "gzldyid") String gzldyid, @RequestParam(value = "qllx", required = false) Integer qllx);

    /**
     * @param pzid 配置ID
     * @return 不动产登记小类配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置ID获取不动产登记小类配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/djxlpz/{pzid}")
    BdcDjxlPzDO queryBdcDjxlPzDOByPzid(@PathVariable(value = "pzid") String pzid);

    /**
     * @param gzldyid 工作流定义ID
     * @return 不动产登记小类配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义ID获取不动产登记小类配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/djxlpz/list/{gzldyid}")
    List<BdcDjxlPzDO> queryBdcDjxlPzByGzldyid(@PathVariable(value = "gzldyid") String gzldyid);

    /**
     * @param bdcDjxlPzDO 不动产登记小类配置
     * @return 保存数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产登记小类配置
     */
    @PutMapping("/realestate-accept/rest/v1.0/djxlpz")
    int saveBdcDjxlPzDO(@RequestBody BdcDjxlPzDO bdcDjxlPzDO);

    /**
     * @param bdcDjxlPzDOList 登记小类配置
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除登记小类配置
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/djxlpz")
    int deleteBdcDjxlPzDO(@RequestBody List<BdcDjxlPzDO> bdcDjxlPzDOList);

    /**
     * @param bdcDjxlPzJson 登记小类配置
     * @return 登记小类配置分页
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记小类配置分页
     */
    @PostMapping("/realestate-accept/rest/v1.0/djxlpz/page")
    Page<BdcDjxlPzDO> listBdcDjxlPzByPage(Pageable pageable, @RequestParam(name = "bdcDjxlPzJson", required = false) String bdcDjxlPzJson);

    /**
     * @param bdcDjxlPzDO 登记小类配置
     * @return 最大顺序号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取登记小类配置最大顺序号
     */
    @PostMapping("/realestate-accept/rest/v1.0/djxlpz/maxsxh")
    int queryDjxlPzMaxSxh(@RequestBody BdcDjxlPzDO bdcDjxlPzDO);

    /**
     * @param gzldyid
     * @param djxl
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记小类配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/djxlpz/{gzldyid}/{djxl}")
    BdcDjxlPzDO queryBdcDjxlPzByGzldyidAndDjxl(@PathVariable(value = "gzldyid") String gzldyid, @PathVariable(value = "djxl") String djxl);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上报配置校验
     * @date : 2022/7/8 10:16
     */
    @GetMapping("/realestate-accept/rest/v1.0/djxlpz/sbpzjy")
    int sbpzjy(@RequestParam(value = "pzid", required = false) String pzid);

    /**
     * @param bdcDjxlPzQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询流程定义id
     * @date : 2022/11/29 17:04
     */
    @PostMapping("/realestate-accept/rest/v1.0/djxlpz/lcdyid")
    List<String> listGzldyid(@RequestBody BdcDjxlPzQO bdcDjxlPzQO);

}
