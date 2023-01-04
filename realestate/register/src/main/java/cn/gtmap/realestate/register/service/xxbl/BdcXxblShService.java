package cn.gtmap.realestate.register.service.xxbl;

import cn.gtmap.realestate.common.core.domain.BdcBlShDO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 不动产信息补录审核服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.1, 2020/03/16 18:17
 */
public interface BdcXxblShService {
    /**
     * 生成补录审核信息
     *
     * @param bdcBlShDO 不动产补录审核对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    void generateBlShxx(BdcBlShDO bdcBlShDO);

    /**
     * 转发补录审核信息
     *
     * @param bdcBlShDO
     */
    void forwardBlShxx(BdcBlShDO bdcBlShDO);

    /**
     * 办结补录审核信息
     *
     * @param gzlslid
     */
    List<BdcGzyzVO> dbYz(String gzlslid) throws Exception;

    /**
     * 办结补录审核信息
     *
     * @param blshid
     */
    void endBlShxx(@NotBlank(message = "补录审核办结缺少 blshid") String blshid);

    /**
     * 转发补录审核信息
     *
     * @param blshid
     * @return {boolean} false:无法转发
     */
    boolean backBlShxx(String blshid);

    /**
     * 判断是否正在审核
     *
     * @param bdcBlShDO
     * @return {BdcBlShEnum.value}
     */
    Integer checkIsSh(BdcBlShDO bdcBlShDO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcBlShDO]
     * @return cn.gtmap.realestate.common.core.domain.BdcBlShDO
     * @description 根据项目id和受理人id查询可转发的补录记录
     */
    BdcBlShDO queryBdcBlshDO(BdcBlShDO bdcBlShDO);

    /**
     * @param xmid xmid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除补录审核信息 <br>
     * <strong>删除项目对应的所有审核信息，包括历史数据</strong>
     * 项目信息被删除后，不需要保留审核信息。
     */
    void deleteBlShxx(String xmid);

    /**
     * @param gzlslid gzlslid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 批量删除补录审核信息 <br>
     * <strong>删除项目对应的所有审核信息，包括历史数据</strong>
     * 项目信息被删除后，不需要保留审核信息。
     */
    void deleteBlShxxs(String gzlslid);

    /**
     * @param bdcBlShDO 信息补录审核信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新信息补录审核信息 <br>
     */
    int updateBlshxx(BdcBlShDO bdcBlShDO);
    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @params [bdcBlShDO]
     * @return cn.gtmap.realestate.common.core.domain.BdcBlShDO
     * @description 根据xmid查询信息补录审核信息
     */
    List<BdcBlShDO> queryBlshxxByXmid(String xmid);
}
