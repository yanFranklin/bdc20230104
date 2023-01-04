package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.vo.etl.HtbaMsrVO;

import java.util.List;

/**
 * @program: realestate
 * @description: 合同备案权利人服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-15 19:15
 **/
public interface HtbaQlrService {

    /**
     * @param json 合同备案权利人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新合同备案权利人信息
     * @date : 2020/12/15 17:25
     */
    int saveOrUpdatHtbaQlr(String json);

    /**
     * @param qlrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人id删除备案权利人
     * @date : 2020/12/15 19:19
     */
    int deleteHtbaQlr(String qlrid);

    /**
     * @param bdcdyh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋户室xindex 查询买受人
     * @date : 2020/12/21 17:51
     */
    List<HtbaQlrDO> listHtbaQlr(String bdcdyh);

    /**
     * @param bdcdyh
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据bdcdyh 查询买受人
     * @date : 2021/01/18
     */
    FcjyBaxxDTO spfzyMsr(String bdcdyh,String qlrlb);

    /**
     * @param bdcdyhList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量查询买受人信息
     * @date : 2021/3/3 17:11
     */
    List<HtbaMsrVO> listMsr(List<String> bdcdyhList,String bazt);

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据备案id 查询备案权利人
     * @date : 2021/4/14 14:01
     */
    List<HtbaQlrDO> listHtbaQlrByBaid(String baid);


    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据备案id删除权利人
     * @date : 2021/4/14 14:02
     */
    void deleteHtbaQlrByBaid(String baid);
}
