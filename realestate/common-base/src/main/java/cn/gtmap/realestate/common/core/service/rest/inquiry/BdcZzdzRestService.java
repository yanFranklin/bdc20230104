package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.hefei.moke.MokeHxyzhRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.shucheng.BdcZzdzRequestScDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcZzdzZmcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZzdzZmxxResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/10/23
 * @description 自助打证机接口
 */
public interface BdcZzdzRestService {
    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param bdcZzdzRequestDTO
     * @return List<BdcZzdzResponseDTO>
     * @description 证书信息
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz",method = RequestMethod.POST)
    List<BdcZzdzResponseDTO> getBdcZzdz(@RequestBody BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @param bdcZzdzRequestDTO
     * @return 更新数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 回写印制号
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/reWriteYzh", method = RequestMethod.POST)
    Integer reWriteYzhToBdcZs(@RequestBody BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @param bdcZzdzRequestDTO
     * @return 更新数量
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 回写印制号_不办结！只转发到下个节点
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/reWriteYzhToBdcZsForward", method = RequestMethod.POST)
    Integer reWriteYzhToBdcZsForward(@RequestBody BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @param bdcZzdzNtRequestDTO
     * @return List<BdcZzdzNtResultDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 证书信息
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/nt", method = RequestMethod.POST)
    BdcZzdzNtResponseDTO getBdcZzdzNt(@RequestBody BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO);

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param bdcZzdzNtRequestDTO
     * @return 更新数量
     * @description 回写印制号
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/reWriteYzh/nt",method = RequestMethod.POST)
    BdcZzdzNtResponseDTO reWriteYzhToBdcZsNt(@RequestBody BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param bdcZzdzNtRequestDTO
     * @return 更新数量
     * @description 回写印制号
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/reWriteYzh/yc",method = RequestMethod.POST)
    BdcZzdzNtResponseDTO reWriteYzhToBdcZsYc(@RequestBody BdcZzdzNtRequestDTO bdcZzdzNtRequestDTO);

    /**
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @param bdcZzdzRequestDTO
     * @return 更新数量
     * @description 回写印制号
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/reWriteYzh/standard",method = RequestMethod.POST)
    Integer reWriteYzhToBdcZsForStandard(@RequestBody BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @param bdcZzdzRequestDTO
     * @return 更新数量
     * @description 回写印制号
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/zdbj/standard",method = RequestMethod.POST)
    CommonResponse zdbjForStandard(@RequestBody BdcZzdzRequestDTO bdcZzdzRequestDTO);

    /**
     * @param bdcZzdzRequestScDTO
     * @return List<BdcZzdzResponseDTO>
     * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 证书信息 （舒城）
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/sc", method = RequestMethod.POST)
    List<BdcZzdzResponseDTO> getBdcZzdzSc(@RequestBody BdcZzdzRequestScDTO bdcZzdzRequestScDTO);

    /**
     * @param hxyzhRequestDTO
     * @return 更新数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 合肥摩科回写印制号
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/mokeRewriteYzh", method = RequestMethod.POST)
    BdcCommonResponse mokeRewriteYzhToBdcZs(@RequestBody MokeHxyzhRequestDTO hxyzhRequestDTO);

    /**
     * @param hxyzhRequestDTO
     * @return
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 合肥摩科查询打证信息接口
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/mokeQueryDzxx", method = RequestMethod.POST)
    BdcCommonResponse mokeQueryDzxx(@RequestBody MokeHxyzhRequestDTO hxyzhRequestDTO);

    /**
     * @param hxyzhRequestDTO
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 合肥摩科验证能否打证接口
     */
    @RequestMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/verify", method = RequestMethod.POST)
    BdcCommonResponse mokeVerifyDzxx(@RequestBody MokeHxyzhRequestDTO hxyzhRequestDTO);

    /**
     * @param bdcZzdzZmcxRequestDTO 自助打证机证明查询接口入参
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 宣城摩科登记证明查询接口
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcZzdz/mokeQueryDjzmxx")
    BdcZzdzZmxxResponseDTO mokeQueryDjzmxx(@RequestBody BdcZzdzZmcxRequestDTO bdcZzdzZmcxRequestDTO);
}
