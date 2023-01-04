package cn.gtmap.realestate.init.service.other;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcDeleteYwxxDTO;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;

import java.util.List;
import java.util.Map;

/**
 * 初始化后的数据处理模块
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
public interface InitDataDealService {

    /**
     * 将所有业务初始化的数据整合到InitResultDTO中
     * @param serviceDTOMap  项目与对应初始化数据
     * @return
     */
    InitResultDTO  dealServiceDTO(Map<String,InitServiceDTO> serviceDTOMap) throws IllegalAccessException;

    /**
     * 将所有业务初始化的数据整合到InitResultDTO中
     * @param listDTO  初始化数据
     * @return
     */
    InitResultDTO dealServiceDTO(List<InitServiceDTO> listDTO) throws IllegalAccessException;

    /**
     * 将InitResultDTO中数据提交入库
     * @param initResultDTO
     * @param type 处理数据类型  1：初始化入库 2：初始化证书   3： 抓取楼盘表数据 4：正常逻辑  5:对照楼盘表数据
     * @return true/false
     */
    Boolean  dealResultDTO(InitResultDTO initResultDTO,Integer type) throws Exception;


    /**
     * 根据项目id数据删除数据服务
     * @param xmids
     * @return  true/false
     */
    Boolean  deleteYwsj(String[] xmids) throws Exception;


    /**
     * 根据工作流实例ID删除业务数据服务
     * @param gzlslid
     * @return  true/false
     */
    Boolean  deleteYwsj(String gzlslid) throws Exception;

    /**
     * 根据项目数据获取删除数据
     * @param xmList
     * @param sfzqlpbsj  是否抽取楼盘表数据
     * @param  sfzqlpbsj  是否对照楼盘表数据
     * @param  scplcl 删除批量处理
     * @param  sfdsc  是否只是删除处理
     * @return  List<Object>
     */
    List<Object>  queryDeleteData(List<BdcXmDO> xmList,Boolean sfzqlpbsj,Boolean sfdzbflpbsj,Boolean scplcl,Boolean sfdsc) throws Exception;

    /**
     * 根据gzlslid数据删除数据服务
     * @param gzlslid
     * @return  true/false
     */
    void deleteYwxxAllSubsystem(String gzlslid,String reason,String slzt) throws Exception;

    /**
     * 删除数据服务
     * @param bdcDeleteYwxxDTO 删除业务信息DTO对象
     * @throws Exception
     */
    void deleteYwxxAllSubsystem(BdcDeleteYwxxDTO bdcDeleteYwxxDTO)throws Exception;


    /**
     * 覆盖权籍中的null值
     * @param serviceDTOMap
     * @return
     */
    public void convertNullValue(Map<String,InitServiceDTO> serviceDTOMap);

}
