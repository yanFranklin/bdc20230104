package cn.gtmap.realestate.accept.service;


import cn.gtmap.realestate.common.core.domain.accept.BdcZdydbymPzDO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcFpyzHyxxDbxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcFpyzQyxxDbxxVO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/9/23
 * @description 自定义对比服务
 */
public interface BdcZdydbService {

    /**
     * @param dbdh 对比代号
     * @return 自定义对比配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据对比代号查询自定义对比配置
     */
    BdcZdydbymPzDO queryBdcZdydbPzByDbdh(String dbdh);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 加载对比信息
     */
    void generateDbxx(String dbdh,String paramJson);

    /**
     * @param xmid 项目ID
     * @return 婚姻对比信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 加载婚姻对比信息
     */
    List<BdcFpyzHyxxDbxxVO> generateHyxx(String xmid);

    /**
     * @param xmid 项目ID
     * @return 企业对比信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 加载企业对比信息
     */
    List<BdcFpyzQyxxDbxxVO> generateQyxx(String xmid);


}
