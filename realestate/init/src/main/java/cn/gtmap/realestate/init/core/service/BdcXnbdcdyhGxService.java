package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXnbdcdyhGxDO;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 虚拟不动产单元关系服务
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
public interface BdcXnbdcdyhGxService {

    /**
     *落宗服务
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcdyh
     *@param lsdyh
     *@param lsdyhbdclx 临时单元号不动产类型
     * @param gxlb 关系类别
     *@description
     */
    boolean lz(@NotBlank(message = "临时单元号参数不能为空")String lsdyh,
               @NotBlank(message = "不动产单元号参数不能为空")String bdcdyh,
               Integer lsdyhbdclx,
               Boolean gxbdcdyfwlx,
               String ip,
               Integer gxlb
    ) throws Exception;

    /**
     *取消落宗服务
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcdyh
     *@param qxlzxmid
     *@description
     */
    boolean qxlz(@NotBlank(message = "不动产单元号参数不能为空")String bdcdyh,@NotBlank(message = "取消落宗的项目ID不能为空")String qxlzxmid) throws Exception;

    /**
     * 根据项目id获取虚拟单元号关系
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xndyhxmid
     *@return  List<BdcXnbdcdyhGxDO>
     *@description
     */
    List<BdcXnbdcdyhGxDO> queryBdcXnbdcdyhGx(@NotBlank(message = "参数不能为空") String xndyhxmid);

    /**
     * 根据条件查询
     *
     * @param bdcXnbdcdyhGxDO
     * @return List<BdcXnbdcdyhGxDO>
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description
     */
    List<BdcXnbdcdyhGxDO> queryBdcXnbdcdyhByCondition(BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO);

    /**
     * @param bdcXmDOList,bdcdyh
     * @param bhdysd 是否包含(处理)单元锁定 0 不包含，其余均包含
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新单元号信息
     * @date : 2021/12/30 18:05
     */
    void updateBdcdyh(List<BdcXmDO> bdcXmDOList, String bdcdyh,String qjgldm,Integer bhdysd,Boolean gxbdcdyfwlx) throws Exception;

    /**
     * @param bdcXnbdcdyhGxDOS 关系表数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 插入单元号匹配关系
     */
    void insertXndyhGx(List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOS);



}
