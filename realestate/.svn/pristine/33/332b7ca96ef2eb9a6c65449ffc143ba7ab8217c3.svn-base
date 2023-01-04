package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/2/19
 * @description 外联证书
 */
public interface BdcWlzsService {

    /**
     * @param bdcCshSlxmDTO 不动产受理项目前台
     * @param jbxxid 基本信息id
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  外联证书操作
     */
    void wlZs(BdcCshSlxmDTO bdcCshSlxmDTO, String jbxxid);

    /**
     * @param bdcCshSlxmDTO 不动产受理项目前台
     * @param jbxxid 基本信息id
     * @param xmids 带入抵押信息的xmids
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  外联证书操作
     */
    void wlZsDyaqxx(BdcCshSlxmDTO bdcCshSlxmDTO, String jbxxid, String xmids);



    /**
     * @param wlxmid 外联项目ID
     * @param bdcSlXmDO  不动产受理项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据外联项目ID外联证书（生成历史关系数据）
     */
    BdcSlXmLsgxDO wlZsByWlxmid(String wlxmid, BdcSlXmDO bdcSlXmDO);

    /**
     * @param fcxmid 房产xmid
     * @param bdcSlXmDO  不动产受理项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房产ID外联证书（生成历史关系数据）土地证
     */
    BdcSlXmLsgxDO wltdz(String fcxmid,String fcbdcqz,BdcSlXmDO bdcSlXmDO);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fcxmid
     * @param fcbdcqz
     * @param bdcSlXmDO
     * @return cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO
     * @description 根据房产ID外联证书（生成历史关系数据）土地证(不保存直接返回)
     */
    BdcSlXmLsgxDO wltdzNoInsert(String fcxmid,String fcbdcqz,BdcSlXmDO bdcSlXmDO);
}
