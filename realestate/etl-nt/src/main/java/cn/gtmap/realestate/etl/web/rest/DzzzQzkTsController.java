package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.domain.DzzzQzkDO;
import cn.gtmap.realestate.common.core.dto.register.DzzzQzkDzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.etl.DzzzQzkTsRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.etl.core.mapper.qzk.DzzzQzkMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/08/29
 * @description
 */
@RestController
@Api(tags = "电子证照信息入库")
public class DzzzQzkTsController implements DzzzQzkTsRestService {

    @Autowired
    DzzzQzkMapper dzzzQzkMapper;

    @Autowired
    EntityMapper entityMapper;

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "将数据插入前置库", notes = "将数据插入前置库")
    @ApiImplicitParam(name = "dzzzQzkDzDTO", value = "电子证照信息", required = true, dataType = "DzzzQzkDzDTO")
    @Override
    public void insertDzzzQzk(@RequestBody DzzzQzkDzDTO dzzzQzkDzDTO) {
        if (null == dzzzQzkDzDTO){
            throw new AppException("推送数据不可为空!");
        }
        DzzzQzkDO dzzzQzkDO = new DzzzQzkDO();
        BeanUtils.copyProperties(dzzzQzkDzDTO,dzzzQzkDO);
        List<DzzzQzkDO> qzkList = dzzzQzkMapper.getQzkxxByKey(dzzzQzkDO.getRowguid());
        if (CollectionUtils.isNotEmpty(qzkList)){
            dzzzQzkMapper.updateQzk(dzzzQzkDO);
        }else {
            dzzzQzkMapper.insertQzk(dzzzQzkDO);
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通过zzid更新前置库数据", notes = "通过zzid更新前置库数据")
    @ApiImplicitParam(name = "zzid", value = "证照id", required = true, dataType = "String")
    @Override
    public void updateYdzzzByZzid(@RequestBody String zzid) {
        if (StringUtils.isBlank(zzid)){
            throw new AppException("zzid不可为空!");
        }
        dzzzQzkMapper.updateQzkByZzid(zzid);
    }

}
