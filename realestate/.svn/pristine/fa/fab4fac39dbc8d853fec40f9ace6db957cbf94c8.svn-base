package cn.gtmap.realestate.init.service.other;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 初始化不动产证书对外接口
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/17.
 * @description
 */
public interface InitBdcZsService {


    /**
     * 初始化不动产证书
     * @param cshFwkgSlList  每个项目的初始化实例
     * @param zsyl  是否证书预览
     * @param zssl 证书数量
     * @param plybz  是否是批量一本证逻辑，按项目处理的要传,gzlslid的会自己判断
     * @param sjbl 数据补录 （不生成权利其他状况和附记）
     * @return List<BdcZsDO>
     */
    List<BdcZsDO>  initBdcZs(List<BdcCshFwkgSlDO> cshFwkgSlList,boolean zsyl,boolean zssl,Boolean plybz,boolean sjbl) throws Exception;


    /**
     * 通过传入参数更新项目表
     * @param param 参数
     * @param value 数值
     */
    void updateXmVal(BdcXmDO param,BdcXmDO value);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: gzlslid 流程实例ID
     * @return: int 证书数量
     * @description 通过流程实例id获取当前流程的拥有房地产权利类型的证书数量
     * <P>根据证书的序号来判断证书数量，判断规则：1、证书序号为空则单独一本证。2、证书序号相同时，则组合发证。</P>
     */
    int countBdcZsWithFdcq(String gzlslid);
    
    /**
     * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @param: bdcXmDOList 不动产项目列表
     * @return: String 原产权证号
     * @description 生成批量流程中的原产权证号
     */
    String initYcqzhPl(List<BdcXmDO> bdcXmDOList);
    /**
     * @author: <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param: bdcXmDOList 不动产项目列表
     * @return: String 原产权证号
     * @description 生成原产权证号 只返回一个ycqzh 多余一个返回"详见抵押清单"
     */
    String initYcqzhOne(List<BdcXmDO> bdcXmDOList);

    /**
     * 更新权利其他状况 （将原不动产权证号追加到项目和证书的权利其他状况里）
     * @param processInsId
     */
    void updateCommonQlqtzk(@NotBlank(message = "工作流实例ID不能为空")String processInsId);

    /**
     * 更新权利其他状况 （将原不动产权证号追加到项目和证书的权利其他状况里）
     * @param processInsId
     * @param onlyOne （大于等于两条显示详见抵押清单）
     */
    void updateQlqtzk(@NotBlank(message = "工作流实例ID不能为空")String processInsId, boolean onlyOne);

    /**
     * 通过传入的工作流实例ID，追加附记内容至上一手的附记中去
     * @param processInsId  工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void zjsysFj(String processInsId);
}
