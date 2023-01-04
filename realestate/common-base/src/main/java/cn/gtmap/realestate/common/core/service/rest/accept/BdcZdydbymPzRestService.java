package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcZdydbymPzDO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcFpyzHyxxDbxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcFpyzQyxxDbxxVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/9/24
 * @description 自定义对比页面配置rest服务
 */
public interface BdcZdydbymPzRestService {

    /**
     * @param dbdh 对比代号
     * @return 自定义对比配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据对比代号查询自定义对比配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/zdydbpz/{dbdh}")
    BdcZdydbymPzDO queryBdcZdydbPzByDbdh(@PathVariable(value = "dbdh") String dbdh);

    /**
     * @param xmid 项目ID
     * @return 婚姻对比信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 加载婚姻对比信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/zdydbpz/hyxx/{xmid}")
    List<BdcFpyzHyxxDbxxVO> generateHyxx(@PathVariable(value = "xmid") String xmid);

    /**
     * @param xmid 项目ID
     * @return 企业对比信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 加载企业对比信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/zdydbpz/qyxx/{xmid}")
    List<BdcFpyzQyxxDbxxVO> generateQyxx(@PathVariable(value = "xmid") String xmid);
}
