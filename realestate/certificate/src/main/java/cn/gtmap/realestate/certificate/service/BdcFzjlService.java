package cn.gtmap.realestate.certificate.service;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlLzrQzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlZsDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcFzjlPdfDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcQzxxQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcSzxxVO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/28
 * @description 发证记录业务接口类
 */
public interface BdcFzjlService {
    /**
     * @param zsid 证书ID
     * @return BdcFzjlDTO 发证记录信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的发证记录
     */
    BdcFzjlDTO queryBdcZsFzjl(String zsid);

    /**
     * @param xmid  项目ID
     * @param zsid
     * @return BdcFzjlDTO 发证记录信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询项目的发证记录
     */
    BdcFzjlDTO queryFzjl(String xmid, String zsid);

    /**
     * @param gzlslid     工作流实例ID
     * @param bdcXmDOList 流程所有的项目信息
     * @return 发证记录信息（一个发证记录）
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程发一个发证记录（基本信息取一个项目，证书信息获取流程所有的证书信息）
     */
    BdcFzjlDTO queryLcOneFzjl(String gzlslid, List<BdcXmDO> bdcXmDOList);
    /**
     * @param gzlslid 工作流实例ID
     * @param bdcXmDOList 当前流程的项目信息
     * @return BdcFzjlDTO 发证记录信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 合并展示流程发证记录
     */
    BdcFzjlDTO queryHbFzjl(String gzlslid, List<BdcXmDO> bdcXmDOList);
    /**
     * @param gzlslid 工作流实例ID
     * @param sfhb    是否合并显示
     * @return List<BdcFzjlDTO> 发证记录信息List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程所有项目的发证记录
     */
    List<BdcFzjlDTO> queryListBdcFzjl(String gzlslid, boolean sfhb);

    /**
     * @param bdcFzjlZsDTOList 证书DTO对象，用于接收发证记录信息
     * @return int 更新记录的数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将发证记录的领证人更新到证书表中
     */
    int updateFzjlLzr(List<BdcFzjlZsDTO> bdcFzjlZsDTOList);

    /**
     * @param bdcFzjlZsDTO 发证记录证书信息
     * @return int 执行数量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据工作流实例ID集合更新领证人信息
     */
    void plUpdateLzrxx(BdcFzjlZsDTO bdcFzjlZsDTO);

    /**
     * @param xmid  项目ID
     * @param sfhb 是否是合并页面的批量更新
     * @param bz    领证备注
     * @return int 更新数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新发证记录的备注信息
     */
    int updateFzjlBz(String xmid, boolean sfhb, String bz);

    /**
     * @param xmid  项目ID
     * @param sfhb 是否是合并页面的批量更新
     * @param fzyj 发证意见
     * @return int 更新数量
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 更新发证记录的发证意见信息
     */
    int updateFzjlFzyj(String xmid, boolean sfhb, String fzyj);

    /**
     * @param gzlslid 工作流实例ID
     * @param fzr  发证人信息
     * @param isNullUpdate 只有当发证人为空的时候更新（true则做发证人是否为空的判断，否则直接更新发证信息）
     * @return int 更新记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新流程发证人
     */
    int updateFzr(String gzlslid, UserDto fzr, Boolean isNullUpdate);

    /**
     * @param gzlslid 工作流实例ID
     * @param szr     缮证人信息
     * @return int 更新记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新流程证书的缮证人
     */
    BdcSzxxVO updateSzr(String gzlslid, UserDto szr);

    /**
     * @param gzlslid 工作流实例ID
     * @param szr     缮证人信息
     * @return int 更新记录
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 更新流程证书的缮证人
     */
    BdcSzxxVO updateSzrByGzlslid(String gzlslid, UserDto szr);

    /**
     * @param bdcPrintDTO    打印DTO对象
     * @return 发证记录打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取发证记录的打印XMl
     */
    String fzjlPrintXml(BdcPrintDTO bdcPrintDTO);

    /**
     * 检查领证人信息（南通）
     * @param xmid       项目ID
     * @param bdcFzjlDTO 发证记录对象
     * @return String 提示信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    String checkLzr(String xmid, BdcFzjlDTO bdcFzjlDTO);

    /**
     * @param bdcPrintDTO 打印DTO对象
     * @return 发证记录打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按流程生成发证记录, 获取发证记录的打印XMl
     */
    String fzjlOnePrintXml(BdcPrintDTO bdcPrintDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlLzrQzxxDTO 领证人签字信息
     * @return {BdcQzxxDO} 入库签字信息
     * @description  保存领证人签字图片信息
     */
    BdcQzxxDO saveFzjlLzrQzxx(BdcFzjlLzrQzxxDTO bdcFzjlLzrQzxxDTO);

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcFzjlLzrQzxxDTO 领证人签字信息
     * @return {BdcQzxxDO} 入库签字信息
     * @description  批量保存领证人签字图片信息
     */
    List<BdcQzxxDO> plSaveFzjlLzrQzxx(BdcFzjlLzrQzxxDTO bdcFzjlLzrQzxxDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @param zsid 证书ID
     * @return {String} 领证人签字图片Base64字符串
     * @description  获取领证人签字图片信息
     */
    String getFzjlLzrQzxx(String xmid, String zsid);

    /**
     * 获取领证人签字图片信息（如果指定顺序号则查询项目指定顺序号签字信息，否则查询项目所有签字信息）
     * @param bdcQzxxQO 签字信息查询参数
     * @return {String} 项目签字信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcQzxxDO> getFzjlLzrQzxxs(BdcQzxxQO bdcQzxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlPdfDTO PDF及项目信息
     * @return {String} 文件存储ID
     * @description  保存发证记录文件到大云并关联到当前项目作为附件
     */
    String saveFzjlPdfFile(BdcFzjlPdfDTO bdcFzjlPdfDTO);
}
