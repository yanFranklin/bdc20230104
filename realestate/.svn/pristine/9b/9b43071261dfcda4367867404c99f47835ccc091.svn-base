package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmSfbzDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxmQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSfxmVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @program: realestate
 * @description: 收费项目
 * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @create: 2019-11-22
 **/
@Repository
public interface BdcSlSfxmMapper {
    /**
     * @param sfxxid 收费信息ID
     * @return List<BdcSlSfxmVO>
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查询收费项目
     */
    List<BdcSlSfxmVO> listBdcSlSfxm(String sfxxid);

    /**
     * @param bdcSlSfxmQO 收费项目查询条件
     * @return List<BdcSlSfxmVO>
     */
    List<BdcSlSfxmVO> listBdcSlSfxmBysfxxidList(BdcSlSfxmQO bdcSlSfxmQO);

    /**
     * @param bdcSlSfxmQO 收费项目查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/11/27 17:14
     */
    List<BdcSlSfxmDO> listSfxmBySfxmdm(BdcSlSfxmQO bdcSlSfxmQO);

    /**
     * 查询所有收费标准
     * @return
     */
    List<BdcSlSfxmSfbzDO> listBdcSlSfxmSfbzDOAll();

    /**
     * 查询出当天没有金额的数据
     */
    List<String> listNoSfxxYsjeData(@Param("sfsjks") Date sfsjks, @Param("sfsjjs") Date sfsjjs);

    /**
     * 批量更新收费状态
     *
     * @param sfxxidList 收费信息ID集合
     * @param sfzt       收费状态
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void plModifySfxmSfzt(@Param("sfxxidList") List<String> sfxxidList, @Param("sfzt") Integer sfzt);

    /**
     * @param bdcSlSfxmQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询收费项目信息
     * @date : 2022/5/17 17:11
     */
    List<BdcSlSfxmDO> listBdcSlSfxmByGzlslid(BdcSlSfxmQO bdcSlSfxmQO);
    /**
     * 批量修改收费项目登记费的实收金额
     * <p>
     *     慎重使用：
     *     该接口用于批量修改减免原因为小微企业时，更改收费项目的登记费实收金额为：0
     * </p>
     * @param sfxxidList 收费信息ID
     * @param ssje 实收金额
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void plxgDjfSfxmSsje(@Param("sfxxidList") List<String> sfxxidList, @Param("ssje") Double ssje);

    /**
     * 根据sfxmbz分组查询月结的收费项目信息
     * @param xmid 项目ID
     * @param qlrlb 申请人类别
     * @param sfxxidList 收费信息ID集合
     * @param sfyj 是否月结
     * @param hjfk 合计非空
     * @return 收费项目信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcSlSfxmDO> queryYjSfxmxxGroupBySfxmbz(@Param("xmid") String xmid, @Param("qlrlb") String qlrlb,
                                                 @Param("sfxxidList") List<String> sfxxidList, @Param("sfyj") Integer sfyj,
                                                 @Param("hjfk") boolean hjfk);

}
