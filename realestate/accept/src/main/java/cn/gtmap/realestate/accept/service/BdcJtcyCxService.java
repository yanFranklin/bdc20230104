package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.dto.accept.BdcFileBase64DTO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcJtcyCxYmxxVO;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/4/14
 * @description 家庭成员信息查询对外接口
 */
public interface BdcJtcyCxService {

    /**
     * 南通查询婚姻信息，并生成查询文件PDF
     * @param gzlslid 工作流实例ID
     * @param isFirstHand 是否一手房
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void queryHyxxAndGenerateFile(String gzlslid,boolean isFirstHand);

    /**
     * 南通查询家庭成员信息，并生成查询信息文件PDF
     * @param gzlslid 工作流实例ID
     * @param isFirstHand 是否一手房
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void queryHjxxAndGenerateFile(String gzlslid,boolean isFirstHand);

    /**
     * 上传家庭成员页面截图图片
     * @param bdcFileBase64DTO 文件base64DTO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void uploadScreenShot(BdcFileBase64DTO bdcFileBase64DTO);

    /**
     * 根据工作流实例ID，获取家庭成员查询页面信息
     * @param gzlslid 工作流实例ID
     * @return 不动产家庭成员查询页面信息VO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    BdcJtcyCxYmxxVO queryJtcyYmxx(String gzlslid);

}
