package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.mapper.FwHstMapper;
import cn.gtmap.realestate.building.core.mapper.FwHsthfMapper;
import cn.gtmap.realestate.building.core.service.FwHsService;
import cn.gtmap.realestate.building.core.service.FwHstService;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.core.service.FwXmxxService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.service.HstService;
import cn.gtmap.realestate.building.service.ReadHstService;
import cn.gtmap.realestate.building.service.impl.ReadHstFromDbAndFtpServiveImpl;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FhtDTO;
import cn.gtmap.realestate.common.core.dto.building.FwHstRequestDTO;
import cn.gtmap.realestate.common.core.service.rest.building.FwHstRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.LogActionConstans;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/6
 * @description 户室服务
 */
@RestController
@Api(tags = "户室图服务接口")
public class FwHstRestController extends BaseController implements FwHstRestService {
    private static Logger LOGGER = LoggerFactory.getLogger(FwHstRestController.class);

    @Autowired
    private FwHstService fwHstService;

    @Autowired
    private HstService hstService;

    @Autowired
    private BdcdyService bdcdyService;
    @Autowired
    private FwHstMapper fwHstMapper;

    @Autowired
    private FwHsthfMapper fwHsthfMapper;
    @Autowired
    FwLjzService fwLjzService;

    @Autowired
    FwXmxxService fwXmxxService;

    @Autowired
    FwHsService fwHsService;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param fwHstIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 删除户室图服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "删除户室图服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHstIndex", value = "户室图主键", required = true, dataType = "String", paramType = "path")})
    public Integer deleteFwHstByFwHstIndex(@PathVariable("fwHstIndex") String fwHstIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHstService.deleteFwHstByFwHstIndex(fwHstIndex);
    }

    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除户室的户室图
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "删除户室的户室图")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "户室主键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "hslx", value = "户室类型", required = true, dataType = "String", paramType = "path")})
    public void delFwhsHst(@PathVariable("fwHsIndex") String fwHsIndex,@PathVariable("hslx") String hslx,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        hstService.delFwhsHst(fwHsIndex,hslx);
    }

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 删除独幢平面图
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "删除独幢平面图")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "String", paramType = "path")})
    public void delFwLjzPmt(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        hstService.delFwLjzPmt(fwDcbIndex);
    }


    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查看逻辑幢下户室图缺失情况
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查看逻辑幢下户室图缺失情况")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "String", paramType = "path")})
    public List<FwHsDO> listHstDeficiency(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHstService.listHstDeficiency(fwDcbIndex);
    }

    /**
     * @param fwHstRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 户室图请求实体
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存户室图")
    public FwHstDO saveHst(@RequestBody FwHstRequestDTO fwHstRequestDTO) {
        return hstService.saveFwHst(fwHstRequestDTO);
    }

    /**
     * @param fwHstRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存幢平面图
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存户室图")
    public FwHstDO saveLjzPmt(@RequestBody FwHstRequestDTO fwHstRequestDTO) {
        return hstService.saveLjzPmt(fwHstRequestDTO);
    }

    /**
     * @param fwHstIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键查询户室图
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键查询户室图表")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHstIndex", value = "户室图主键", required = true, dataType = "String", paramType = "path")})
    public FwHstDO queryHstByIndex(@PathVariable("fwHstIndex") String fwHstIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHstService.queryFwHstByIndex(fwHstIndex);
    }

    /**
     * @param fwHstIndex
     * @param qjgldm
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据主键查询户室图
     */
    @Override
    public FwHstDO queryFwHstByIndex(@RequestParam(name = "fwHstIndex", required = false) String fwHstIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHstMapper.queryFwHstByIndex(fwHstIndex);
    }

    /**
     * @param fwDcbIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询 逻辑幢平面图 base64位码
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询 逻辑幢平面图 base64位码")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "String", paramType = "path")})
    public String queryLjzPmtBase64(@PathVariable(name = "fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        ReadHstService readHstService = hstService.getConfigReadService();
        if(readHstService != null){
            return readHstService.readBase64ByFwHstIndex(fwDcbIndex);
        }
        return null;
    }

    /**
     * @param fwHsIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询户室图 base64位码
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询户室图 base64位码")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "户室主键", required = true, dataType = "String", paramType = "path")})
    public String queryFwHstBase64(@PathVariable(name = "fwHsIndex")String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        ReadHstService readHstService = hstService.getConfigReadService();
        if(readHstService != null){
            LOGGER.warn("当前房fw_hs_index{}权籍管理代码{}，调用服务{}", fwHsIndex, qjgldm, readHstService.getClass().getName());
            return readHstService.readBase64ByFwHsIndex(fwHsIndex);
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询房屋户室图
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房屋户室图")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "BDCDYH", required = true, dataType = "String", paramType = "path")})
    public String queryFwHstBase64ByBdcdyh(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        BdcdyResponseDTO responseDTO = bdcdyService.queryBdcdy(bdcdyh, Constants.BDCDYFWLX_H);
        if (responseDTO != null && StringUtils.isNotBlank(responseDTO.getDjid())) {
            LOGGER.warn("当前单元号{}权籍管理代码{}查询到户室数据，fw_hs_index{}", bdcdyh, qjgldm, responseDTO.getDjid());
            return queryFwHstBase64(responseDTO.getDjid(), "");
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @param qjgldm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据不动产单元号查询户室图数据，包含独幢户室图和户室户室图
     * @date : 2021/10/12 9:01
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房屋户室图")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "BDCDYH", required = true, dataType = "String", paramType = "path")})
    public String queryHstBase64ForHsAndDz(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        //先根据单元查逻辑幢，如果是独幢，直接用fw_dcb_index=hst_index查逻辑幢的平面图
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByBdcdyh(bdcdyh);
        if (Objects.nonNull(fwLjzDO) && StringUtils.equals(Constants.BDCDYFWLX_DZ, fwLjzDO.getBdcdyfwlx())) {
            LOGGER.warn("当前单元号{}权籍管理代码{}查询到逻辑幢数据", bdcdyh, qjgldm);
            return queryLjzPmtBase64(fwLjzDO.getFwDcbIndex(), "");
        } else {
            //查询项目内多幢信息
            FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByBdcdyh(bdcdyh);
            if (Objects.nonNull(fwXmxxDO) && StringUtils.isNotBlank(fwXmxxDO.getFwXmxxIndex())) {
                LOGGER.warn("当前单元号{}权籍管理代码{}查询到项目信息数据", bdcdyh, qjgldm);
                //fwxmxxindex = hstindex
                ReadHstService readHstService = hstService.getConfigReadService();
                if (readHstService != null) {
                    return readHstService.readBase64ByFwHstIndex(fwXmxxDO.getFwXmxxIndex());
                }
            }
            //如果不是独幢用单元号查户室图
            return queryFwHstBase64ByBdcdyh(bdcdyh, "");
        }
    }

    /**
     * @param bdcdyh
     * @param qjgldm
     * @author <a href="mailto:wutao@gtmap.cn">gaolining</a>
     * @description 根据不动产单元号查询户室图数据，包含独幢户室图和户室户室图
     * @date : 2021/10/12 9:01
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房屋户室图")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "BDCDYH", required = true, dataType = "String", paramType = "path")})
    public List<String> queryHstBase64Hefei(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        List <String> result = new ArrayList<>();
        FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcdyh);
        if (Objects.nonNull(fwHsDO) && StringUtils.isNotBlank(fwHsDO.getFwHstIndex())) {
            if(StringUtils.isNotBlank(fwHsDO.getHbfx()) && (fwHsDO.getHbfx().equals(Constants.FWHS_HBFX_UP) || fwHsDO.getHbfx().equals(Constants.FWHS_HBFX_DWON))){
                // 户室图合并方式是上下的时候，去合肥特有的表中获取户室图信息
                Example example = new Example(FwHsthfDO.class);
                example.createCriteria().andEqualTo("fwHsIndex",fwHsDO.getFwHsIndex());
                List<FwHsthfDO> fwHsDOList = entityMapper.selectByExample(example);
                if(CollectionUtils.isNotEmpty(fwHsDOList)){
                    for(FwHsthfDO fwHsthfDO:fwHsDOList){
                        if(null != fwHsthfDO.getHst()){
                            result.add(BuildingUtils.blobToStr(fwHsthfDO.getHst()));
                        }
                    }
                }
            }else{
                // 户室图合并方式不是上下的时候，直接去户室图表中查询
                FwHstDO fwHstDO = entityMapper.selectByPrimaryKey(FwHstDO.class, fwHsDO.getFwHstIndex());
                if(fwHstDO != null){
                    result.add(BuildingUtils.blobToStr(fwHstDO.getHst()));
                }else{
                    LOGGER.error("根据户室主键查询不到户室图数据,fwHsIndex:{}",fwHsDO.getFwHstIndex());
                }
            }
        }
        return result;
    }

    /**
     * @param
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 下载分层分户图至ftp
     * @date : 2023/1/3
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "下载上传至ftp", action = LogActionConstans.OTHER)
            public void downloadFcfhtHefei(@RequestBody FhtDTO fhtDTO) throws IOException {
        fwHsService.downloadFcfhtHefei(fhtDTO);

    }

    /**
     * @param bdcdyh
     * @return java.util.List
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 查询房屋户室图（合肥）
     */
    @Override
    public List<String> queryFwHstBase64ByBdcdyhHefei(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm,
                                                @RequestParam(name = "slbh", required = false) String slbh, @RequestParam(name = "bdcqzh", required = false) String bdcqzh) throws IOException {
        String fcfhtStr = queryHstBase64ForHsAndDz(bdcdyh, qjgldm);
        if(StringUtils.isBlank(fcfhtStr)){
            //从FTP获取下载的分层分户图
            List<String> fcfhtList = fwHsService.getFcfhtFromFTP(bdcdyh);
            if(CollectionUtils.isNotEmpty(fcfhtList)){
                return fcfhtList;
            }
            List<String> fxfhtFromDaxx = fwHsService.getFxfhtFromDaxx(bdcdyh,slbh,bdcqzh,qjgldm);
            //获取不到调用接口获取
            return fxfhtFromDaxx;
        }
        return Arrays.asList(fcfhtStr);
    }
}