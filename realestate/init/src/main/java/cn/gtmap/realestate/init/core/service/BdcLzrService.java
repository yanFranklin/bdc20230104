package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcLzrDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcLzxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @program: realestate
 * @description: 领证人方法实现服务Service
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-17 16:46
 **/
public interface BdcLzrService {

    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询领证人信息
     * @date : 2020/1/17 16:48
     */
    List<BdcLzrDO> queryBdcLzr(BdcLzrDO bdcLzrDO, String order);

    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增领证人
     * @date : 2020/1/17 17:28
     */
    void insertBdcLzr(BdcLzrDO bdcLzrDO);

    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量新增领证人
     * @date : 2020/1/17 17:28
     */
    List<BdcLzrDO> insertBatchBdcLzr(BdcLzrDO bdcLzrDO, String gzlslid, String djxl);

    /**
     * @param bdcLzrDO 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新领证人
     * @date : 2020/1/17 17:28
     */
    int updateBdcLzr(BdcLzrDO bdcLzrDO);

    /**
     * @param lzrid 领证人id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除领证人
     * @date : 2020/1/17 17:28
     */
    void deleteBdcLzr(String lzrid);

    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 用xmid删除领证人
     * @date : 2020/1/17 17:28
     */
    void deleteBdcLzrByXmid(String xmid);

    /**
     * @param zsid 证书ID
     * @return List<BdcLzrDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据证书ID查询和证书相关的所有项目的受理领证人信息
     */
    List<BdcLzrDO> getAllZsXmLzrByZsid(String zsid);

    /**
     * 批量更新不动产权利人
     *
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return 更新数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新不动产权利人
     */
    int updateBatchBdcLzr(BdcDjxxUpdateQO bdcDjxxUpdateQO) throws ClassNotFoundException;

    /**
     * 根据领证人名称和证件号批量删除流程内权利人信息
     *
     * @param gzlslid
     * @param bdcLzrDO
     * @param djxl
     * @return
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据领证人名称和证件号批量删除流程内权利人信息
     */
    void deleteBdcLzrsByLzrxx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid, String djxl, BdcLzrDO bdcLzrDO);

    /**
     * @param bdcLzrDTO 前台传入领证人更新信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/2/21 9:24
     */

    Integer updateLzrxxToPllc(BdcLzrDTO bdcLzrDTO) throws Exception;


    /**
     * @param bdcLzrDTO 前台传入领证人更新信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 单个流程更新数据
     * @date : 2020/2/21 9:24
     */

    BdcLzrDO updateLzrxxToJdlc(BdcLzrDTO bdcLzrDTO);

    /**
     * @param bdcLzrDOList 领证人集合
     * @return 新增个数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量新增领证人
     */
    int insertBatchBdcLzr(List<BdcLzrDO> bdcLzrDOList);

    /**
     * 更新lzfs
     *
     * @param gzlslid
     * @param lzfs
     */
    void updateAllLzfsByGzlslid(String gzlslid, String lzfs);

    /**
     * 查询lzfs
     *
     * @param gzlslid
     */
    Integer getLzfsByGzlslid(String gzlslid);

    /**
     * 查询lzfs
     *
     * @param gzlslid
     */
    List<BdcLzxxDTO> getAllLzfsByGzlslid(String gzlslid);

    /**
     * 查询lzfs
     *
     * @param zsid
     */
    BdcLzxxDTO getLzfsByZsid(String zsid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcXmDOList]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcLzrDO>
     * @description 根据项目查询领证人
     */
    List<BdcLzrDO> getBdcLzrDOByXm(List<BdcXmDO> bdcXmDOList);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcLzrDOList]
     * @return int
     * @description 批量更新领证人
     */
    int batchUpdateBdcLzr(List<BdcLzrDO> bdcLzrDOList);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcLzrDOList]
     * @return void
     * @description 批量删除领证人
     */
    void batchDeleteBdcLzr(List<BdcLzrDO> bdcLzrDOList);

    /**
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcLzrDO>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcXmDOList]
     * @description 根据项目查询领证人
     */
    List<BdcLzrDO> getBdcLzrDOByGzlslid(String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除领证人信息
     * @date : 2022/3/25 13:28
     */
    void deleteLzrxx(String gzlslid, String lzrmc, String lzrzjh, String djxl, List<String> qlridList);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 回写领证人信息到证书表相关字段
     * @date : 2022/8/9 9:30
     */
    void hxLzrxxToZs(String gzlslid);
}
