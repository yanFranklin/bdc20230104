package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzYwxxMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzYwxxDo;
import cn.gtmap.realestate.common.core.domain.electronic.BdcDzzzZzxxDO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.BdcDzzzYwxxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmZsDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate.BdcZzxxRestService;
import cn.gtmap.realestate.common.util.CheckParameter;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.avalon.framework.parameters.ParameterException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/08/25
 * @description 电子证照信息
 */
@RestController
public class BdcDzzzRestController implements BdcZzxxRestService {
    @Autowired
    BdcDzzzZzxxMapper bdcDzzzZzxxMapper;

    @Autowired
    BdcDzzzYwxxMapper bdcDzzzYwxxMapper;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取证照信息", notes = "获取证照信息")
    @ApiImplicitParam(name = "bdcXmZsDTO", value = "获取zzbs", required = true, dataType = "BdcXmZsDTO")
    public BdcDzzzZzxxDO getZzxxByzzbs(@RequestBody BdcXmZsDTO bdcXmZsDTO) {
        BdcDzzzZzxxDO bdcDzzzZzxxDO = new BdcDzzzZzxxDO();
        BeanUtils.copyProperties(bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZzbs(bdcXmZsDTO.getZzbs()),bdcDzzzZzxxDO);
        return bdcDzzzZzxxDO;
    }

    /**
     * 根据zzid获取证照业务信息
     * @param zzid
     * @return
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取证照信息", notes = "获取证照信息")
    @ApiImplicitParam(name = "bdcXmZsDTO", value = "获取zzbs", required = true, dataType = "BdcXmZsDTO")
    public BdcDzzzYwxxDTO getZzywxxByzzid(@RequestParam(value = "zzid",required = true) String zzid) {
        if (StringUtils.isBlank(zzid)) {
            throw new MissingArgumentException("缺失必要查询条件");
        }
        List<BdcDzzzYwxxDo> bdcDzzzYwxxDos = bdcDzzzYwxxMapper.queryYwxxByZzid(zzid);
        if (CollectionUtils.isNotEmpty(bdcDzzzYwxxDos)) {
            BdcDzzzYwxxDTO bdcDzzzYwxxDTO = new BdcDzzzYwxxDTO();
            BeanUtils.copyProperties(bdcDzzzYwxxDos.get(0), bdcDzzzYwxxDTO);
            return bdcDzzzYwxxDTO;
        }
        return null;
    }

}
