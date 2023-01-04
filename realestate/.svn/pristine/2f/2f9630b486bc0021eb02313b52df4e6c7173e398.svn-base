package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-08-07
 * @description
 */
public interface BdcSdService {
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 锁定不动产单元
     */
    int sdBdcdy(List<BdcDysdDO> bdcDysdDOList, Integer sdzt);
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 锁定不动产权证
     */
    int sdBdczs(List<BdcZssdDO> bdcZssdDOList,Integer sdzt, String sdyy);

    /**
     * 新增锁定信息数据，锁定状态为0 （未锁定）
     * <p>注意：新增锁定信息的锁定状态为1（锁定）时，需同步权籍锁定状态</p>
     * @param bdcZssdDOList 证书锁定信息集合
     * @param sdyy 锁定原因
     */
    void addBdcZsSdxx(List<BdcZssdDO> bdcZssdDOList, String sdyy);

    /**
     * 新增单元锁定信息数据，锁定状态为0（未锁定）
     * <p>注意：新增锁定信息的锁定状态为1（锁定）时，需同步权籍锁定状态</p>
     * @param bdcDysdDOList
     * @param sdyy
     */
    void addBdcDySdxx(List<BdcDysdDO> bdcDysdDOList, String sdyy);

    /**
     * 更新证书锁定 <br>
     * <p>
     * 由于重新生成证书，所以先对于将原证书锁定信息关联到新生成的证书上 <br/>
     *
     * @param bdcZssdDOList 证书锁定集合
     * @return 更新数据条数
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    int updateSdZs(List<BdcZssdDO> bdcZssdDOList);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 解锁不动产单元
     */
    int jsBdcdy(List<BdcDysdDO> bdcDysdDOList,
                String jsyy);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 解锁不动产权证
     */
    int jsBdczs(List<BdcZssdDO> bdcZssdDOList,
                String jsyy);


    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询不动产单元锁定
     */
    List<BdcDysdDO> queryBdcdySd(BdcDysdDO bdcDysdDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询不动产证书锁定
     */
    List<BdcZssdDO> queryBdczsSd(BdcZssdDO bdcZssdDO);

    /**
     * @param bdcDysdDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存不动产单元锁定备注
     */
    int saveBdcdysdBz(@RequestBody BdcDysdDO bdcDysdDO);

    /**
     * @param bdcZssdDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存不动产证书锁定备注
     */
    int saveBdczssdBz(@RequestBody BdcZssdDO bdcZssdDO);

    /**
     * @param bdcZssdDOList 证书锁定集合
     * @return {int} 删除证书数目
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除证书锁定信息 <br>
     * 删除补录修改流程时需要同步删除证书锁定信息。
     */
    int deleteBdczsSd(List<BdcZssdDO> bdcZssdDOList);

    /**
     * 根据工作流实例ID删除证书锁定数据
     * @param gzlslid
     * @return
     */
    int deleteBdczsSdByGzlslid(String gzlslid);

    /**
     * 根据工作流实例ID查询证书锁定信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<BdcZssdDO> 证书锁定DO集合
     */
    List<BdcZssdDO> queryBdczsSdByGzlslid(String gzlslid);

    /**
     * 根据工作流实例ID查询单元锁定信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<BdcDysdDO> 单元锁定DO集合
     */
    List<BdcDysdDO> queryBdcDySdByGzlslid(String gzlslid);

    /**
     * 通过工作流实例ID获取历史关系，通过原项目ID获取锁定/冻结数据
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @return List<BdcZssdDO> 证书锁定DO集合
     */
    List<BdcZssdDO> queryYxmZssd(String gzlslid, Integer sdzt);

    /**
     * 通过工作流实例ID获取历史关系，通过原项目ID获取锁定/冻结数据
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<BdcDysdDO> 单元锁定DO集合
     */
    List<BdcDysdDO> queryYxmDysd(String gzlslid, Integer sdzt);

    /**
     * 锁定/冻结 不动产证书或不动产单元数据
     * <p>大云调用接口，冻结流程办结时触发</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void bdczsSdDj(String gzlslid);

    /**
     * 解锁/解冻 不动产证书或不动产单元数据
     * <p>大云调用接口，解冻流程办结时触发</p>
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void bdczsSdJd(String gzlslid);

    /**
     * 通过工作流实例ID修改证书锁定数据
     * @param bdcZssdDO 证书锁定DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void updateZssdByGzlslid(BdcZssdDO bdcZssdDO);

    /**
     * 通过工作流实例ID修改证书锁定数据
     * @param bdcDysdDO 单元锁定DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void updateDysdByGzlslid(BdcDysdDO bdcDysdDO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 根据bdcdyh解锁不动产单元
     * @param bdcDysdDO
     */
    void jsBdcdyhByBdcdyh(BdcDysdDO bdcDysdDO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 根据cqzh解锁不动产证书
     * @param bdcZssdDO
     */
    void jsBdczsByCqzh(BdcZssdDO bdcZssdDO);

    /**
     * 根据主键ID查询锁定信息
     * @param sdxxid 主键ID
     * @return Object 单元或者证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    Object queryBdcSdxxById(String sdxxid);

    /**
     * 根据cqzh批量查询证书锁定信息
     * @param cqzhList
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    List<BdcZssdDO> queryBdcZssdByCqzhs(List<String> cqzhList);

    /**
     * 解锁原项目的不动产证书
     * <p>大云调用接口，用于解锁原项目的不动产权证书</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void jsBdczsByCqzh(String processInsId);

    /**
     * @param list 证书锁定ID集合
     * @return {int} 删除证书数目
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 删除证书锁定信息
     */
    int batchDeleteBdcZssd(List<String> list);

    /**
     * 根据XMID查询证书锁定信息
     * @param xmid
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return List<BdcZssdDO> 证书锁定DO集合
     */
    List<BdcZssdDO> queryBdczsSdByXmid(String xmid);
}
