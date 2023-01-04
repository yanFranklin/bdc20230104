package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYgDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.InitSlxxQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/9
 * @description 添加购物车数据处理公共服务
 */
public interface BdcAddGwcSjclCommonService {

    /**
     * @param xmid 生成的新项目ID
     * @param yxmid 已选抵押的项目ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  生成对应项目与原抵押证明号关系
     */
    void generateXmidDyzmhMap(String xmid, String yxmid, Map<String,String> xmidDyzmhMap);

    /**
     * @param bdcSlYwxxDTO 受理业务信息
     * @param czrid 操作人ID
     * @param jbxxid 基本信息ID
     * @return 受理项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 接收业务信息组织受理信息
     */
    BdcSlXmDO changeYwxxDtoIntoBdcSlXm(BdcSlYwxxDTO bdcSlYwxxDTO, String czrid, String jbxxid);

    /**
     * @param initSlxxQO
     * @param dyxmid 抵押项目ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  组织受理信息(带抵押项目)
     */
    BdcSlXmDTO initBdcSlXmDTOForWithdy(InitSlxxQO initSlxxQO,String dyxmid);

    /**
     * @param initSlxxQO
     * @param bdcSlXmDO  受理项目
     * @return 外联历史关系
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  初始化生成外联历史关系
     */
    List<BdcSlXmLsgxDO>initWlXmlsgx(InitSlxxQO initSlxxQO,BdcSlXmDO bdcSlXmDO);

    /**
     * @param bdcSlYgDTO 关联预告信息
     * @param qllx 当前权利类型
     * @return 是否匹配预告
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证关联预告信息与当前项目是否匹配
     */
    Boolean checkPpYg(BdcSlYgDTO bdcSlYgDTO, Integer qllx);
}
