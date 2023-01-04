package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYyQO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcYyVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-07-11
 * @description 不动产异议信息业务类
 */
public interface BdcYyXxService {
    /**
     * @param pageable bdcYyQO
     * @return Page<BdcYyVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产异议分页信息
     */
    Page<BdcYyVO> listBdcYyxxByPage(Pageable pageable, BdcYyQO bdcYyQO);

    /**
     * 常州，分页获取异议信息，拥有同综合查询一样多查询条件
     */
    Page<BdcYyVO> listBdcYyxxByPageCz(Pageable pageable, BdcYyQO bdcYyQO);

    /**
     * @return List<BdcYyVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产异议信息
     */
    List listBdcYyxx(BdcYyQO bdcYyQO);

    /**
     * 常州， 获取不动产异议信息
     */
    List listBdcYyxxCz(BdcYyQO bdcYyQO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 批量更新异议
     */
    int updateBdcYy(List<BdcYyDO> bdcYyDOList);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询异议
     */
    List listBdcYyByXmid(String xmid);


    List listBdcYyByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询抵押信息
     */
    List<BdcYyDO> listBdcYyByBdcdyhs(List<String> bdcdyhList);
}
