package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/13
 * @description 规则验证相关服务
 */
public interface BdcGzyzRestService {

    /**
     * @param gzlslid 工作流实例ID
     * @return List 查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 规则验证-房改房-是否允许办理查询
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/gzyz/fgf/blcx/{gzlslid}", method = RequestMethod.GET)
    BdcGzyzVO checkFgfSfyxbl(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 规则验证-税务-建设银行缴库入库状态（未入库，则补退）
     */
    @Deprecated
    @RequestMapping(value = "/realestate-register/rest/v1.0/gzyz/jsyhjkrk/{gzlslid}", method = RequestMethod.GET)
    BdcGzyzVO checkYhjkrk(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 返回验证结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿前验证税费缴库入库状态
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/gzyz/sfjkrk/{gzlslid}", method = RequestMethod.GET)
    BdcGzyzVO checkSfjkrk(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿前验证线下缴费是否已上传税费缴纳凭证
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/gzyz/xxJfPz/{gzlslid}", method = RequestMethod.GET)
    BdcGzyzVO checkXxJfPz(@PathVariable(name = "gzlslid") String gzlslid);

    /**
     * @param processInsId 工作流实例ID
     * @return 验证结果信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 验证抵押权首次登记的抵押权人是否全部是签约银行
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/gzyz/sfxyjg/{processInsId}", method = RequestMethod.GET)
    Map<String,String> checkSfxyjg(@PathVariable(name = "processInsId") String processInsId);
}
