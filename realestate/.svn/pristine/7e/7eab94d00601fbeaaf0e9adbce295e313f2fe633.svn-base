package cn.gtmap.realestate.init.service.other;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcQjtbxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcSjdzDTO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 业务同步数据接口
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/5/6
 * @description
 */
public interface BdcSynchService {


    /**
     * 同步流程的原证号信息
     * @param gzlslid
     * @throws Exception
     */
    void synchLcYzh(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) throws Exception;

    /**
     * 同步项目的原证号信息
     * @param xmid
     * @throws Exception
     */
    void synchXmYzh(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception;

    /**
     * 同步项目的原证号信息
     * @param bdcXmDO
     * @throws Exception
     */
    void synchXmYzh(BdcXmDO bdcXmDO) throws Exception;


    /**
     * 流程同步权籍数据
     * @param gzlslid
     * @throws Exception
     */
    void synchLpbDataToLc(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) throws Exception;

    /**
     * 项目同步权籍数据
     * @param xmid
     * @throws Exception
     */
    void synchLpbDataToXm(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception;


    /**
     * 项目同步权籍数据后的数据
     * @param xmid
     * @throws Exception
     */
    InitServiceDTO querySynchLpbData(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception;
    /**
     * 项目同步权籍数据后的数据
     * @param xmid
     * @throws Exception
     */
    InitServiceDTO querySynchYxmData(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception;
    /**
     * 项目获取权籍数据对照的信息
     * @param xmid
     * @return  List<BdcQjtbxxDTO>
     * @throws Exception
     */
    List<BdcQjtbxxDTO> queryLpbDataDzxx(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception;
    /**
     * 项目获取权籍数据对照的信息
     * @param xmid
     * @return  List<BdcQjtbxxDTO>
     * @throws Exception
     */
    List<BdcSjdzDTO> queryLpbAndYxmDataDzxx(@NotBlank(message = "项目ID不能为空") String xmid) throws Exception;


    /**
     * 根据前台传递的对照信息进行同步
     * @param list
     * @param xmid
     * @throws Exception
     */
    void synchLpbDzDataToXm(List<BdcQjtbxxDTO> list, String xmid) throws Exception;

    /**
     * 清空流程原产权证号(后生成的)和不动产权证号
     *
     * @param gzlslid
     * @throws Exception
     */
    void clearBdcqzhAndYcqzh(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) throws Exception;

    void clearBdcqzhAndYcqzhByXmid(@NotBlank(message = "工作流实例ID不能为空") String xmid) throws Exception;

}
