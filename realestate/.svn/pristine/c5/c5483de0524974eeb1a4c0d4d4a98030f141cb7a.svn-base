package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.dto.etl.HtbaxxDTO;
import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import cn.gtmap.realestate.common.core.dto.etl.HtbaSpfWqxxDTO;
import cn.gtmap.realestate.common.core.vo.etl.FgHtfyVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 合同备案商品房服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-14 14:54
 **/

public interface HtbaSpfService {

    /**
     * @param htbaxxQO 合同备案信息查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询合同备案信息
     * @date : 2020/12/14 15:10
     */
    Page<Map> listHtbaSpfxxByPage(Pageable pageable, HtbaxxQO htbaxxQO);

    /**
     * @param baids 备案id信息，多个逗号隔开
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/12/14 19:31
     */
    List<String> deleteBaxxList(String baids);

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询备案信息
     * @date : 2020/12/15 10:09
     */
    HtbaxxDTO queryHtbaxx(String baid);

    /**
     * @param json 合同备案商品房信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/12/15 17:25
     */
    HtbaSpfDO saveOrUpdatHtbaxx(String json);


    /**
     * @param bdcdyh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合同备案房屋信息
     * @date : 2020/12/16 9:48
     */
    List<HtbaSpfDO> listHtbaxx(String bdcdyh);

    /**
     * @param fwHsDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 从权籍获取数据保存合同备案信息
     * @date : 2020/12/16 9:48
     */
    HtbaSpfDO saveHtbaSpfFromFwhs(FwHsDO fwHsDO);

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据baid查询合同备案信息
     * @date : 2020/12/16 17:46
     */
    HtbaSpfDO queryHtbapf(String baid);


    /**
     * @param htbaxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合同备案商品房信息
     * @date : 2020/12/17 17:57
     */
    List<HtbaSpfDO> listHtbaSpf(HtbaxxQO htbaxxQO);

    /**
     * 获取商品房网签信息
     *
     * @param gzlslid 工作流实例ID
     * @return 合同备案商品房网签信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<HtbaSpfWqxxDTO> querySpfWqHtxx(String gzlslid);


    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存房屋信息
     * @date : 2021/3/8 20:38
     */
    List<HtbaSpfWqxxDTO> listSpfWqxx(String htbh, String gzlslid);

    /**
     * @param htbaxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询需要同步的数据
     * @date : 2021/7/6 15:09
     */
    Page<Map> listSyncHtbaSpfxxByPage(Pageable pageable, HtbaxxQO htbaxxQO);


    /**
     * @param htbaxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询需要同步的数据
     * @date : 2021/7/7 10:19
     */
    List<FgHtfyVO> listSyncBaxx(HtbaxxQO htbaxxQO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 同步备案数据
     * @date : 2021/7/7 9:24
     */
    int syncBaxx(List<FgHtfyVO> fgHtfyVOList);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 处理备案信息
     * @date : 2021/7/7 9:39
     */
    int dealBaxx(FwHsDO fwhs, FgHtfyVO fgHtfyVO, String bdcdyh);

}
