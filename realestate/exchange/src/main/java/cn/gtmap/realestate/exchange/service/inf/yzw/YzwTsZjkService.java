package cn.gtmap.realestate.exchange.service.inf.yzw;

import cn.gtmap.realestate.common.core.domain.exchange.yzw.*;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.YzwDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/11/24
 * @description 标准一张网结构推送中间库服务
 */
@SuppressWarnings({"ALL", "AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc"})
public interface YzwTsZjkService {
    /**
     * 推送所有表入口
     *
     * @param
     * @return
     * @Date 2020/11/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void tszjkEntrance(String gzlslid);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证并推送前置库
     */
    List<String> checkTsqzk(InfApplyDO infApplyDO);

    /**
     * 对照TBmCasebaseinfo后，插入中间库
     *
     * @param infApplyList infApplyList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void pushTbmCasebaseinfo(List<InfApplyDO> infApplyList, String proid);


    /**
     * 对照TBmCaseprocesss，插入中间库
     *
     * @param infApplyProcessList infApplyProcessList
     * @param infApplyWlxxList    infApplyWlxxList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void pushTbmCaseprocesss(List<InfApplyProcessDO> infApplyProcessList, List<InfApplyWlxxDO> infApplyWlxxList);

    /**
     * 对照TBmCaseResult，插入中间库
     *
     * @param infApplyResultList infApplyResultList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void pushTbmCaseResult(List<InfApplyResultDO> infApplyResultList, List<InfApplyJgzzDO> infApplyJgzzList);

    /**
     * 对照TBmCaseJgzz，插入中间库
     *
     * @param infApplyJgzzList infApplyJgzzList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void pushTbmCaseJgzz(List<InfApplyJgzzDO> infApplyJgzzList);

    /**
     * 对照TBmCaseWlxx，插入中间库
     *
     * @param infApplyWlxxList infApplyWlxxList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void pushTbmCaseWlxx(List<InfApplyWlxxDO> infApplyWlxxList);

    /**
     * 对照TBmCaseMaterial，插入中间库
     *
     * @param infApplyMaterialList infApplyMaterialList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void pushTbmCaseMaterial(List<InfApplyMaterialDO> infApplyMaterialList);

    /**
     * 对照TBmCaseAccept，插入中间库
     *
     * @param infApplyProcessList infApplyProcessList
     * @Date 2020/11/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void pushTbmCaseAccept(List<InfApplyProcessDO> infApplyProcessList);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证推送前置库
     */
    List<String> checkTsqzkPl(List<String> yzwbhList, boolean isAll,boolean isToday);

    /**
     * 查询前置库数据
     * @param pageable
     * @param yzwDTO
     * @return
     */
    Page<Map> listShareDataByPage(Pageable pageable, YzwDTO yzwDTO);

    /**
     * 同步一张网数据到当前流程中
     * @param caseno
     * @param processInsId
     */
    void tbyzwsj(String caseno,String processInsId);
}
