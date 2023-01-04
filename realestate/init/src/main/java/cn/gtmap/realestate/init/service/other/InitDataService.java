package cn.gtmap.realestate.init.service.other;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcCopyReplaceYwxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;

import java.util.List;

/**
 * 初始化总服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/29.
 * @description
 */
public interface InitDataService {

    /**
     * 初始化总体逻辑
     * @param listQO
     * @param rk 是否入库
     * @return InitResultDTO 初始化后的数据
     */
    InitResultDTO init(List<InitServiceQO> listQO,boolean rk) throws Exception;

    /**
     * 初始化权利附记和项目表的权利其他状况
     * @param listXm
     */
    void initQlfj(List<BdcXmDO> listXm, List<InitServiceQO> listQO) throws Exception;

    /**
     * 根据项目数据删除数据服务
     * @param xmList
     * @param sfPlDel 是否批量删除
     * @return  true/false
     */
    Boolean  deleteYwsj(List<BdcXmDO> xmList, Boolean sfPlDel) throws Exception;

    /**
     * 根据项目id数据查询数据服务
     * @param xmid
     * @return  InitServiceDTO
     */
    InitServiceDTO queryYwsj(String xmid) throws Exception;

    /**
     * 保存或更新业务数据
     * @param xmid
     * @param bdcYwxxDTO
     * @param isInsert 是否插入
     * @throws Exception
     */
    BdcYwxxDTO  saveOrUpdateYwsj(String xmid,BdcYwxxDTO bdcYwxxDTO,boolean isInsert) throws Exception;

    /**
     * 保存或更新业务数据
     * @param xmid
     * @param bdcYwxxDTOList
     * @param isInsert 是否插入
     * @throws Exception
     */
    List<BdcYwxxDTO>  saveOrUpdateYwsjPl(String xmid,List<BdcYwxxDTO> bdcYwxxDTOList,boolean isInsert);

    /**
     * 根据bdcdywybh查询是否已经存在业务信息
     * @param bdcdywybh
     * @return
     */
    List<BdcXmDO> queryYwxxByBdcdywybh(String bdcdywybh);

    /**
     * 初始化撤销业务信息
     * @param listQO
     * @param rk 是否入库
     * @return InitResultDTO 初始化后的数据
     */
    InitResultDTO initCxYwxx(List<InitServiceQO> listQO,boolean rk) throws Exception;

    /**
     * @param bdcCopyReplaceYwxxDTO 复制并且替换业务信息参数
     * @return 复制的业务信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 复制业务信息并替换单元号,新生成的项目与项目共一本证书
     */
    BdcYwxxDTO copyYwxx(BdcCopyReplaceYwxxDTO bdcCopyReplaceYwxxDTO);

    /**
     * @param bdcCopyReplaceYwxxDTOList 复制并且替换业务信息参数
     *
     * @return 处理后的业务信息集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 复制业务信息并替换相关字段
     */
    List<BdcYwxxDTO> copyAndReplaceYwxx(List<BdcCopyReplaceYwxxDTO> bdcCopyReplaceYwxxDTOList);



}
