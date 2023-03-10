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
 * @description ????????????
 */
@RestController
@Api(tags = "?????????????????????")
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
     * @description ?????????????????????
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "?????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHstIndex", value = "???????????????", required = true, dataType = "String", paramType = "path")})
    public Integer deleteFwHstByFwHstIndex(@PathVariable("fwHstIndex") String fwHstIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHstService.deleteFwHstByFwHstIndex(fwHstIndex);
    }

    /**
     * @param fwHsIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????????????????
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "????????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "????????????", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "hslx", value = "????????????", required = true, dataType = "String", paramType = "path")})
    public void delFwhsHst(@PathVariable("fwHsIndex") String fwHsIndex,@PathVariable("hslx") String hslx,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        hstService.delFwhsHst(fwHsIndex,hslx);
    }

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????????
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "?????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "???????????????", required = true, dataType = "String", paramType = "path")})
    public void delFwLjzPmt(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        hstService.delFwLjzPmt(fwDcbIndex);
    }


    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description ???????????????????????????????????????
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "???????????????????????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "???????????????", required = true, dataType = "String", paramType = "path")})
    public List<FwHsDO> listHstDeficiency(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHstService.listHstDeficiency(fwDcbIndex);
    }

    /**
     * @param fwHstRequestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????????
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "???????????????")
    public FwHstDO saveHst(@RequestBody FwHstRequestDTO fwHstRequestDTO) {
        return hstService.saveFwHst(fwHstRequestDTO);
    }

    /**
     * @param fwHstRequestDTO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????????????????
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "???????????????")
    public FwHstDO saveLjzPmt(@RequestBody FwHstRequestDTO fwHstRequestDTO) {
        return hstService.saveLjzPmt(fwHstRequestDTO);
    }

    /**
     * @param fwHstIndex
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????????????????????
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "??????????????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHstIndex", value = "???????????????", required = true, dataType = "String", paramType = "path")})
    public FwHstDO queryHstByIndex(@PathVariable("fwHstIndex") String fwHstIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHstService.queryFwHstByIndex(fwHstIndex);
    }

    /**
     * @param fwHstIndex
     * @param qjgldm
     * @return cn.gtmap.realestate.common.core.domain.building.FwHstDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ???????????????????????????
     */
    @Override
    public FwHstDO queryFwHstByIndex(@RequestParam(name = "fwHstIndex", required = false) String fwHstIndex, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwHstMapper.queryFwHstByIndex(fwHstIndex);
    }

    /**
     * @param fwDcbIndex
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????? ?????????????????? base64??????
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "?????? ?????????????????? base64??????")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "???????????????", required = true, dataType = "String", paramType = "path")})
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
     * @description ??????????????? base64??????
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "??????????????? base64??????")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwHsIndex", value = "????????????", required = true, dataType = "String", paramType = "path")})
    public String queryFwHstBase64(@PathVariable(name = "fwHsIndex")String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        ReadHstService readHstService = hstService.getConfigReadService();
        if(readHstService != null){
            LOGGER.warn("?????????fw_hs_index{}??????????????????{}???????????????{}", fwHsIndex, qjgldm, readHstService.getClass().getName());
            return readHstService.readBase64ByFwHsIndex(fwHsIndex);
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????BDCDYH ?????????????????????
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "??????BDCDYH?????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "BDCDYH", required = true, dataType = "String", paramType = "path")})
    public String queryFwHstBase64ByBdcdyh(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        BdcdyResponseDTO responseDTO = bdcdyService.queryBdcdy(bdcdyh, Constants.BDCDYFWLX_H);
        if (responseDTO != null && StringUtils.isNotBlank(responseDTO.getDjid())) {
            LOGGER.warn("???????????????{}??????????????????{}????????????????????????fw_hs_index{}", bdcdyh, qjgldm, responseDTO.getDjid());
            return queryFwHstBase64(responseDTO.getDjid(), "");
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @param qjgldm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????????????????????????????????????????????????????????????????
     * @date : 2021/10/12 9:01
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "??????BDCDYH?????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "BDCDYH", required = true, dataType = "String", paramType = "path")})
    public String queryHstBase64ForHsAndDz(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        //?????????????????????????????????????????????????????????fw_dcb_index=hst_index????????????????????????
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByBdcdyh(bdcdyh);
        if (Objects.nonNull(fwLjzDO) && StringUtils.equals(Constants.BDCDYFWLX_DZ, fwLjzDO.getBdcdyfwlx())) {
            LOGGER.warn("???????????????{}??????????????????{}????????????????????????", bdcdyh, qjgldm);
            return queryLjzPmtBase64(fwLjzDO.getFwDcbIndex(), "");
        } else {
            //???????????????????????????
            FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByBdcdyh(bdcdyh);
            if (Objects.nonNull(fwXmxxDO) && StringUtils.isNotBlank(fwXmxxDO.getFwXmxxIndex())) {
                LOGGER.warn("???????????????{}??????????????????{}???????????????????????????", bdcdyh, qjgldm);
                //fwxmxxindex = hstindex
                ReadHstService readHstService = hstService.getConfigReadService();
                if (readHstService != null) {
                    return readHstService.readBase64ByFwHstIndex(fwXmxxDO.getFwXmxxIndex());
                }
            }
            //??????????????????????????????????????????
            return queryFwHstBase64ByBdcdyh(bdcdyh, "");
        }
    }

    /**
     * @param bdcdyh
     * @param qjgldm
     * @author <a href="mailto:wutao@gtmap.cn">gaolining</a>
     * @description ???????????????????????????????????????????????????????????????????????????????????????
     * @date : 2021/10/12 9:01
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "??????BDCDYH?????????????????????")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "BDCDYH", required = true, dataType = "String", paramType = "path")})
    public List<String> queryHstBase64Hefei(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        List <String> result = new ArrayList<>();
        FwHsDO fwHsDO = fwHsService.queryFwhsByBdcdyh(bdcdyh);
        if (Objects.nonNull(fwHsDO) && StringUtils.isNotBlank(fwHsDO.getFwHstIndex())) {
            if(StringUtils.isNotBlank(fwHsDO.getHbfx()) && (fwHsDO.getHbfx().equals(Constants.FWHS_HBFX_UP) || fwHsDO.getHbfx().equals(Constants.FWHS_HBFX_DWON))){
                // ???????????????????????????????????????????????????????????????????????????????????????
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
                // ???????????????????????????????????????????????????????????????????????????
                FwHstDO fwHstDO = entityMapper.selectByPrimaryKey(FwHstDO.class, fwHsDO.getFwHstIndex());
                if(fwHstDO != null){
                    result.add(BuildingUtils.blobToStr(fwHstDO.getHst()));
                }else{
                    LOGGER.error("?????????????????????????????????????????????,fwHsIndex:{}",fwHsDO.getFwHstIndex());
                }
            }
        }
        return result;
    }

    /**
     * @param
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description ????????????????????????ftp
     * @date : 2023/1/3
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "???????????????ftp", action = LogActionConstans.OTHER)
            public void downloadFcfhtHefei(@RequestBody FhtDTO fhtDTO) throws IOException {
        fwHsService.downloadFcfhtHefei(fhtDTO);

    }

    /**
     * @param bdcdyh
     * @return java.util.List
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description ?????????????????????????????????
     */
    @Override
    public List<String> queryFwHstBase64ByBdcdyhHefei(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm,
                                                @RequestParam(name = "slbh", required = false) String slbh, @RequestParam(name = "bdcqzh", required = false) String bdcqzh) throws IOException {
        String fcfhtStr = queryHstBase64ForHsAndDz(bdcdyh, qjgldm);
        if(StringUtils.isBlank(fcfhtStr)){
            //???FTP??????????????????????????????
            List<String> fcfhtList = fwHsService.getFcfhtFromFTP(bdcdyh);
            if(CollectionUtils.isNotEmpty(fcfhtList)){
                return fcfhtList;
            }
            List<String> fxfhtFromDaxx = fwHsService.getFxfhtFromDaxx(bdcdyh,slbh,bdcqzh,qjgldm);
            //??????????????????????????????
            return fxfhtFromDaxx;
        }
        return Arrays.asList(fcfhtStr);
    }
}