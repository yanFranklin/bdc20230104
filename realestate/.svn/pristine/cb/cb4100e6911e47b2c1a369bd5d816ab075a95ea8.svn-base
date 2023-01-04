package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.DyaTjQO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcDyaTjVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/2/11
 * @description
 */
public interface BdcDyaXxMapper {
    /**
     * @param
     * @param dyaTjQO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当月的统计信息
     */
    BdcDyaTjVO getMonthDyatjXx(DyaTjQO dyaTjQO);

    /**
     * @param
     * @param dyaTjQO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当年的统计信息
     */
    BdcDyaTjVO getYearDyatjXx(DyaTjQO dyaTjQO);

    /**
     * @param
     * @param dyaTjQO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前现势的抵押统计信息
     */
    BdcDyaTjVO getValidDyatjXx(DyaTjQO dyaTjQO);

    /**
     * @param
     * @param dyaTjQO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取每日的抵押统计信息
     */
    List<BdcDyaTjVO> getDayDyatjXx(DyaTjQO dyaTjQO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询查封
     */
    List listDyaByXmid(@Param("xmid") String xmid);

    /**
     * 查询抵押全表 qszt！=2
     *
     * @param bdcdyh
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:22 2020/11/5
     */
    List listDyaByBdcdyh(@Param("bdcdyh") String bdcdyh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询抵押信息
     */
    List<BdcDyaqDO> listBdcDyaqByBdcdyhs(@Param("list") List<String> bdcdyhList);

    /**
     * 查询抵押全表
     *
     * @param bdcdyh
     * @param qszt
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:22 2020/11/5
     */
    List listDyaByBdcdyhAndqszt(@Param("bdcdyh") String bdcdyh, @Param("qszt") Integer qszt);

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
     * @version 2022/09/21
     * @description 根据地籍号查询抵押权表现势的在建工程抵押
     */
    List<BdcDyaqDO> listBdcDyaqByDjhAndQszt(@Param("djh") String djh,@Param("qszt") Integer qszt,@Param("dybdclx") Integer dybdclx);
}
