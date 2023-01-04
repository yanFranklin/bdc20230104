package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclPzDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/28
 * @description 不动产受理收件材料rest服务
 */
public interface BdcSlSjclPzRestService {


    /**
     * @param djxl 登记小类
     * @return 不动产受理收件材料配置信息
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据登记小类获取不动产受理收件材料配置信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sjclpz/list/{djxl}")
    List<BdcSlSjclPzDO> listBdcSlSjclPzByDjxl(@PathVariable(value = "djxl") String djxl);

    /**
     * @param bdcSlSjclPzDO 不动产受理收件材料配置信息
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理收件材料配置信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/sjclpz")
    int saveBdcSlSjclPzDO(@RequestBody BdcSlSjclPzDO bdcSlSjclPzDO);

    /**
     * @param bdcSlSjclPzDOList 不动产受理收件材料配置集合
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产受理收件材料配置信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/sjclpz")
    int deleteBdcSlSjclPzDO(@RequestBody List<BdcSlSjclPzDO> bdcSlSjclPzDOList);

    /**
     * @param bdcSlSjclPzJson 收件材料配置
     * @return 收件材料配置分页
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 收件材料配置分页
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjclpz/page")
    Page<BdcSlSjclPzDO> listBdcSlSjclPzByPage(Pageable pageable, @RequestParam(name = "bdcSlSjclPzJson", required = false) String bdcSlSjclPzJson);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取收件材料配置最大序号
     */
    @PostMapping("/realestate-accept/rest/v1.0/sjclpz/maxsxh")
    Integer querySjclPzMaxSxh(@RequestBody BdcSlSjclPzDO bdcSlSjclPzDO);
}
