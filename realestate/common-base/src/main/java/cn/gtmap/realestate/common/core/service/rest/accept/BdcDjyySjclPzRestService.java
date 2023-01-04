package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcDjyySjclPzDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcDjyySjclPzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: realestate
 * @description: 登记原因收件材料配置rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-11 14:18
 **/
public interface BdcDjyySjclPzRestService {

    /**
     * @param bdcDjyySjclPzQO 登记小类登记原因查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/12/11 14:26
     */
    @PostMapping("/realestate-accept/rest/v1.0/djyysjclpz")
    List<BdcDjyySjclPzDO> querySjclPz(@RequestBody BdcDjyySjclPzQO bdcDjyySjclPzQO);

    /**
     * @param bdcDjyySjclPzJson 登记原因收件材料配置
     * @return 登记原因收件材料配置分页
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记原因收件材料配置分页
     */
    @PostMapping("/realestate-accept/rest/v1.0/djyysjclpz/page")
    Page<BdcDjyySjclPzDO> listBdcDjyySjclPzByPage(Pageable pageable, @RequestParam(name = "bdcDjyySjclPzJson", required = false) String bdcDjyySjclPzJson);

    /**
     * @param bdcDjyySjclPzDO 不动产登记原因收件材料配置信息
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产登记原因收件材料配置信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/djyysjclpz")
    int saveBdcDjyySjclPz(@RequestBody BdcDjyySjclPzDO bdcDjyySjclPzDO);

    /**
     * @param bdcDjyySjclPzDOList 不动产登记原因收件材料配置集合
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产登记原因收件材料配置信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/djyysjclpz/list")
    int deleteBdcDjyySjclPzList(@RequestBody List<BdcDjyySjclPzDO> bdcDjyySjclPzDOList);

    /**
     * @param
     * @return
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取收件材料登记原因配置最大序号
     */
    @PostMapping("/realestate-accept/rest/v1.0/djyysjclpz/maxsxh")
    int queryDjyySjclPzMaxSxh(@RequestBody BdcDjyySjclPzDO bdcDjyySjclPzDO);
}
