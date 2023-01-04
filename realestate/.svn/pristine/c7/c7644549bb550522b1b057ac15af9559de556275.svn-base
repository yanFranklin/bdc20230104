package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.moke.MokeZzdzjPlszExtendDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.hefei.moke.MokeHxyzhRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.shucheng.BdcZzdzRequestScDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcZzdzZmcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZzdzZmxxResponseDTO;
import cn.gtmap.realestate.inquiry.core.entity.YhLqrDO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/10/23
 * @description 自助打证
 */
public interface BdcZzdzService {
    /**
     * @param bdcZzdzRequestDTO
     * @return List<BdcZzdzResponseDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 证书证明
     */
    List<BdcZzdzResponseDTO> getBdcZzdz(BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @param bdcZzdzRequestDTO
     * @return List<BdcZszmDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 回写印制号
     */
    Integer reWriteYzhToBdcZs(BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @param bdcZzdzNtRequestDTO
     * @return List<BdcZzdzNtResultDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 证书证明
     */
    List<BdcZzdzNtResultDTO> getBdcZzdzNt(BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO);

    /**
     * @param bdcZzdzNtRequestDTO
     * @return BdcZzdzNtResponseDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 回写印制号
     */
    BdcZzdzNtResponseDTO reWriteYzhToBdcZsNt(BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO);

    /**
     * @param bdcZzdzNtRequestDTO
     * @return BdcZzdzNtResponseDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 回写印制号
     */
    BdcZzdzNtResponseDTO reWriteYzhToBdcZsYc(BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO);

    /**
     * @param bdcZzdzRequestDTO
     * @return 更新数量
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 回写印制号，值回写印制号
     */
    Integer reWriteYzhToBdcZsForStandard(@RequestBody BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @param bdcZzdzRequestDTO
     * @return 成功失败
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 自动办结
     */
    CommonResponse zdbjForZzdzj(@RequestBody BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @param bdcZzdzRequestScDTO
     * @return List<BdcZzdzResponseDTO>
     * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 证书证明(舒城)
     */
    List<BdcZzdzResponseDTO> getBdcZzdzSc(BdcZzdzRequestScDTO bdcZzdzRequestScDTO);

    /**
     * @param bdcZzdzRequestDTO
     * @return BdcZzdzNtResponseDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 回写印制号_常州版本，特殊逻辑处理
     */
    Integer reWriteYzhToBdcZsForChangZhou(BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @param bdcZzdzRequestDTO
     * @return List<BdcZszmDTO>
     * @author <a href="mailto:huangjian @gtmap.cn">huangjian</a>
     * @description 回写印制号_不办结！只转发到下个节点
     */
    Integer reWriteYzhToBdcZsForward(BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @param hxyzhRequestDTO
     * @return 更新数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 合肥摩科回写印制号_此处为摩科新增专用
     */
    BdcCommonResponse mokeRewriteYzhToBdcZs(MokeHxyzhRequestDTO hxyzhRequestDTO);

    /**
     * @param hxyzhRequestDTO@return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 合肥摩科查询打证信息接口
     */
    List<MokeZzdzjPlszExtendDTO> mokeQueryDzxx(MokeHxyzhRequestDTO hxyzhRequestDTO);

    /**
     * @param yhLqrDO
     * @return YhLqrDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据领取人姓名和证件号查银行
     */
    YhLqrDO getYhBylqr(YhLqrDO yhLqrDO);

    /**
     * @param bdcZzdzZmcxRequestDTO 自助打证机证明查询接口入参
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 宣城摩科登记证明查询接口
     */
    BdcZzdzZmxxResponseDTO mokeQueryDjzmxx(BdcZzdzZmcxRequestDTO bdcZzdzZmcxRequestDTO);
}
