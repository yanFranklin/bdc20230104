package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/11
 * @description  不动产系统证书编号模板服务接口
 */
public interface BdcXtZsbhmbRestService {

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  pageable 分页对象
     * @param  zsbhmbParamJson 查询条件
     * @return {Page} 证书编号模板分页数据
     * @description  查询证书编号模板数据列表
     */
    @GetMapping("/realestate-config/rest/v1.0/zsbhmb")
    Page<BdcXtZsbhmbDO> queryBdcXtZsbhmb(Pageable pageable,
                                         @RequestParam(name = "zsbhmbParamJson", required = false) String zsbhmbParamJson);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcXtZsbhmbDO 证书编号模板实体
     * @return {int} 操作数据记录数
     * @description  保存证书编号模板配置
     */
    @PutMapping("/realestate-config/rest/v1.0/zsbhmb")
    int saveBdcXtZsbhmb(@RequestBody BdcXtZsbhmbDO bdcXtZsbhmbDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsbhmbDOList 证书编号模板集合
     * @return {int} 操作数据记录数
     * @description 删除证书编号模板
     */
    @DeleteMapping("/realestate-config/rest/v1.0/zsbhmb")
    int deleteBdcXtZsbhmb(@RequestBody List<BdcXtZsbhmbDO> bdcXtZsbhmbDOList);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {String} 省区代码
     * @description  从证书模板配置中获取省区代码（正常来说所有模板配置的是一致的）
     */
    @GetMapping("/realestate-config/rest/v1.0/zsbhmb/sqdm")
    String querySqdm();

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @param zsbhmbid 证书编号模板ID
     * @return ｛String｝复制后新的证书编号模板ID
     * @description 复制当前证书编号模板的信息，并创建一个新的证书编号模板
     */
    @PostMapping("/realestate-config/rest/v1.0/zsbhmb/{zsbhmbid}/fzxzsbhmb")
    public String copyBdcXtZsbhmb(@PathVariable("zsbhmbid") String zsbhmbid);
}
