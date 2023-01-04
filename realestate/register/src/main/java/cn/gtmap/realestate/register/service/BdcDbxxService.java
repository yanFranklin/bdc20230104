package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhztRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxPlRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.register.XxblDbDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcHfQO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/2
 * @description 登簿信息服务接口
 */
public interface BdcDbxxService {
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   xmid 项目ID
     * @description 更新项目案件状态为2已完成状态，并更新项目结束时间
     */
    void changeAjzt(String xmid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产单元状态集合的对象
     */
    BatchBdcdyhztRequestDTO getBatchBdcdyhztRequestDTO(String gzlslid,String bs);


    /**
     * @param bdcdyhList
     * @param sdzt
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产单元状态集合的对象(锁定)
     */
    BatchBdcdyhSyncZtRequestDTO getBdcdySdztRequestDTO(List<String> bdcdyhList, Integer sdzt);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param gzlslid 工作流实例ID
     * @description 获取不动产单元信息集合的对象
     */
    BdcdyxxPlRequestDTO getBdcdyxxPlRequestDTO(String gzlslid);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param bdcXmDOList 项目集合
     * @description 获取不动产单元信息集合的对象
     */
    BdcdyxxPlRequestDTO getBdcdyxxPlRequestDTO(List<BdcXmDO> bdcXmDOList);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param bdcXmDO 项目集合
     * @description 获取不动产单元信息的对象
     */
    BdcdyxxRequestDTO getBdcdyxxRequestDTO(BdcXmDO bdcXmDO);

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param gzlslid 工作流实例ID
     * @param qszt 权属状态
     * @description 更新原注销权利的登簿信息和权属状态
     */
    void updateYzxqlDbxxAndQszt(String gzlslid, Integer qszt);

    void updateBdcDbxxAndQsztSyncQj(DbxxQO dbxxQO);

    /**
     * @param gzlslid 工作流实例ID
     * @param currentUserName 当前账户
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    void updateBdcDbxxAndQsztSyncQj(String gzlslid, String currentUserName);

    /**
     * @param processInsId    工作流实例id
     * @param currentUserName 当前账户
     * @author <a href ="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description AOP规则验证后，验证通过则登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    List<BdcGzyzVO> updateDbxxQsztGzyzAOP(String processInsId, String currentUserName);
    /**
     * @param gzlslid 工作流实例ID
     * @param syncQj 调用端可控制是否更新权籍信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿退回登簿信息和权属状态(还原当前权利为临时 ， 原权利为现势 ， 清空当前登簿信息 ， 清空注销权利的注销登簿信息)
     */
    void revertBdcDbxxAndQsztSyncQj(String gzlslid, boolean syncQj);

    /**
     * @param listQllx 生成的权利类型集合
     * @param dbxxQO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 更新项目登簿信息、现势权利权属状态
     */
    void updateBdcXmDbxx(DbxxQO dbxxQO, List<String> listQllx);

    /**
     * @param listQllx 生成的权利类型集合
     * @param dbxxQO
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 更新当前流程生成的历史权利和项目的登薄时间 减少60秒
     */
    void updateBdcLsQlXmDjsj(DbxxQO dbxxQO, List<String> listQllx);

    /**
     * @param dbxxQO  登簿信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 更新注销权利权属状态、登簿信息
     */
    void updateYxmDbxx(DbxxQO dbxxQO);

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param gzlslid 工作流实例ID
     * @return 规则验证结果
     * @description  登簿前规则验证
     */
    Object beforeDbGzyz(String gzlslid);

    /**
     * @param bdcZxQO 权属状态
     * @return {code int} 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目和权利的注销信息，不同步权籍
     * qszt{@code 1}清空注销人和注销时间，对登簿人和登记时间不做修改；{@code 2}同时更新注销人和注销时间
     */
    int updateXmAndQlZxxx(BdcZxQO bdcZxQO);

    /**
     * 更新权利恢复信息并同步权籍状态
     *
     * @param bdcHfQO 恢复信息
     * @author <a href="mailto:lixin1@gtmap.cn"lixin</a>
     */
    int updateXmAndQlHfxx(BdcHfQO bdcHfQO);

    /**
     * @param xxblDbDTO 信息补录登簿对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 信息补录登簿
     */
    void updateBdcDbxxAndQszt(XxblDbDTO xxblDbDTO);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 撤销流程，修改权属状态和案件状态
     */
    void cancelProcessQsztAndAjzt(String gzlslid);

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 招行流程抵押注销流程增加回传功能
     */
     void zsyhDyzxHcfw(String processInsId);


    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 招行流程抵押流程增加回传功能
     */
     void zsyhDyHcfw(String processInsId);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId]
     * @return void
     * @description 更新当前流程所注销权利的附记 通过工作流配置
     */
     void updateZxqlfj(String processInsId);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcZxQl]
     * @return void
     * @description 直接传入需要注销的权利 更新附记注销信息
     */
     void generateAndUpdateZxqlFj(BdcQl bdcZxQl);

    /**
     * （常州）变更登记（带抵押）流程登簿时候追加抵押证明附记内容、重新生成抵押证明电子证照
     * @param processInsId    工作流实例id
     * @param currentUserName 当前用户
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    void appendFjAndRebuildDzzz(String processInsId, String currentUserName);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 冻结/解冻时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    void updateQsztSyncQjForDj(String gzlslid);

    /**
     * @param gzlslid  工作流实例id
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 核定该宗地上每一户室所占份额，户室所占份额=户室实测建筑面积/宗地上所有已登记的实测总建筑面积
     */
    void syncHdhsfe(String gzlslid);

    /**
      * @param gzlslid 工作流实例ID
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 登簿将预查封转为现势查封
      */
    void changeYcfToCf(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登簿将预查封转为现势查封,生成新的查封记录，注销原有的预查封
     */
    void initCfFromYcf(String gzlslid);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 商品房首次登记更新外联证书的权利附记
     */
    void updateSpfscdjWlzsQlfj(String processInsId) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @param currentUserName 当前用户名
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新外网件登簿人
     */
    void updateWwsqDbr(String processInsId,String currentUserName);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 撤销登记,还原上一手产权项目对应外联历史关系中注销的原限制权利的状态
     */
    void updateYxmWlzxqlDbxxAndQsztForCxdj(String gzlslid);


    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 办理在建建筑抵押时，更新户室所在的土地证附记
     */
    void updateZjjzTdzfj(String processInsId) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登簿更新关联单元号土地抵押释放信息
     */
    void updateTddysfxx(String processInsId, String currentUserName);

    /**
     * 登记系统房屋发生转移类登记后，将合同的状态修改为注销
     * @param processInsId
     * @param currentUserName
     */
    void updateHtbazt(String processInsId, String currentUserName);

    /**
     * @param processInsId  工作流实例ID
     * @param spjg 审批结果 通过1/不通过0,删除传0，默认为1
     * @param spjd 审批节点  1受理 2办结
     * @param reason 删除原因
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送工改系统办件状态
     */
    void tzggxtBjzt(String processInsId,String spjg,String spjd,String reason);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送工改系统电子证照信息
     */
    void tsggztDzzzxx(String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 外联产权，产权选择注销，新流程在登簿的时候将产权上的现势限制权利挂到当前新产权上，单元号变更为新产权的单元号,单元号变更为新产权的单元号
     */
    void wlcqzx(String processInsId,String currentUserName) throws Exception;

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登簿时更新林权流转状态
     */
    void updateLqlzzt(String processInsId);
}
