package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.HsBgxxService;
import cn.gtmap.realestate.building.service.bg.CxBgService;
import cn.gtmap.realestate.building.service.bg.FwBgService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dto.building.FwhsBgRequestDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.building.FwhsBgRestService;
import cn.gtmap.realestate.common.core.vo.building.FwHsHbVO;
import cn.gtmap.realestate.common.core.vo.building.FwhsBgVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.InterfaceCodeBeanFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-28
 * @description 房屋户室变更相关操作
 */
@RestController
@Api(tags = "房屋户室变更服务")
public class FwhsBgRestController extends InterfaceCodeBeanFactory implements FwhsBgRestService {

    @Autowired
    private HsBgxxService hsBgxxService;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private BgbhService bgbhService;

    @Autowired
    private SSjHsbgjlbService sSjHsbgjlbService;
    @Autowired
    FwYcHsService fwYcHsService;

    @Resource(name = "fwHsHbServiceImpl")
    private FwBgService fwHsHbService;

    @Resource(name = "fwYcHsHbServiceImpl")
    private FwBgService fwYcHsHbService;

    @Resource(name = "fwHsCfServiceImpl")
    private FwBgService fwHsCfServiceImpl;

    @Resource(name = "fwHsJbxxBgServiceImpl")
    private FwBgService fwHsJbxxBgServiceImpl;

    @Resource(name = "fwHsMsServiceImpl")
    private FwBgService fwHsMsServiceImpl;
    @Autowired
    private FwZhsService fwZhsService;

    @Autowired
    private CxBgService cxBgService;

    @Value("${hsfrzhsz}")
    private String hsfrzhsz;

    /**
     * @param fwhsBgRequestDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 拆分服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "房屋户室拆分服务")
    public List<FwHsDO> fwhsChaifen(@RequestBody FwhsBgRequestDTO fwhsBgRequestDTO) {
        if (fwhsBgRequestDTO != null
                && StringUtils.isNotBlank(fwhsBgRequestDTO.getBgbh())
                && CollectionUtils.isNotEmpty(fwhsBgRequestDTO.getyFwHsIndexList())
                && CollectionUtils.isNotEmpty(fwhsBgRequestDTO.getFwHsList())) {
            String bgbh = fwhsBgRequestDTO.getBgbh();
            FwHsDO yFwHsDO = fwHsService.queryFwHsByIndex(fwhsBgRequestDTO.getyFwHsIndexList().get(0));
            return fwHsCfServiceImpl.cfBg(bgbh, yFwHsDO, fwhsBgRequestDTO.getFwHsList());
        } else {
            throw new MissingArgumentException("");
        }
    }

    /**
     * @param fwhsBgRequestDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室合并服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "房屋户室合并服务")
    public Object fwhsHeBing(@RequestBody FwhsBgRequestDTO fwhsBgRequestDTO) {
        if (fwhsBgRequestDTO != null
                && StringUtils.isNotBlank(fwhsBgRequestDTO.getBgbh())
                && CollectionUtils.isNotEmpty(fwhsBgRequestDTO.getyFwHsIndexList())
                && (CollectionUtils.isNotEmpty(fwhsBgRequestDTO.getFwHsList()) || CollectionUtils.isNotEmpty(fwhsBgRequestDTO.getFwYchsDOList()))) {
            String bgbh = fwhsBgRequestDTO.getBgbh();
            List<FwHsDO> yFwHsList = new ArrayList<>(CollectionUtils.size(fwhsBgRequestDTO.getFwHsList()));
            List<FwYchsDO> yFwYchsList = new ArrayList<>(CollectionUtils.size(fwhsBgRequestDTO.getFwYchsDOList()));
            for (String yIndex : fwhsBgRequestDTO.getyFwHsIndexList()) {
                if (StringUtils.equals(CommonConstantUtils.HSLX_SC, fwhsBgRequestDTO.getHslx())) {
                    FwHsDO fwHsDO = fwHsService.queryFwHsByIndex(yIndex);
                    if (fwHsDO != null) {
                        yFwHsList.add(fwHsDO);
                    }
                } else {
                    //实测没有数据查询预测
                    FwYchsDO fwYchsDO = fwYcHsService.queryFwYcHsByFwHsIndex(yIndex);
                    if (Objects.nonNull(fwYchsDO)) {
                        yFwYchsList.add(fwYchsDO);
                    }
                }
            }
            List<FwZhsDO> fwZhsDOList = fwZhsService.listHsHbZhsByIndex(fwhsBgRequestDTO.getZhsList());
            if (StringUtils.equals(CommonConstantUtils.HSLX_SC, fwhsBgRequestDTO.getHslx())) {
                FwHsDO fwHsDO = fwhsBgRequestDTO.getFwHsList().get(0);
                Object result = fwHsHbService.hbBg(bgbh, yFwHsList, fwHsDO);
                fwZhsService.hsBgDealZhs((FwHsDO) result, null, fwZhsDOList);
                if (result != null) {
                    return result;
                }
            } else {
                FwYchsDO fwYchsDO = fwhsBgRequestDTO.getFwYchsDOList().get(0);
                Object result = fwYcHsHbService.hbBg(bgbh, yFwYchsList, fwYchsDO);
                fwZhsService.hsBgDealZhs(null, (FwYchsDO) result, fwZhsDOList);
                if (result != null) {
                    return result;
                }
            }
        } else {
            throw new MissingArgumentException("");
        }
        return null;
    }


    /**
     * @param fwhsBgRequestDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室灭失服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "房屋户室灭失服务")
    public void fwhsMieShi(@RequestBody FwhsBgRequestDTO fwhsBgRequestDTO) {
        if (fwhsBgRequestDTO != null
                && StringUtils.isNotBlank(fwhsBgRequestDTO.getBgbh())
                && CollectionUtils.isNotEmpty(fwhsBgRequestDTO.getyFwHsIndexList())) {
            String bgbh = fwhsBgRequestDTO.getBgbh();
            FwHsDO yFwHsDO = fwHsService.queryFwHsByIndex(fwhsBgRequestDTO.getyFwHsIndexList().get(0));
            if (yFwHsDO != null) {
                fwHsMsServiceImpl.msBg(bgbh, Constants.BGLX_MS, yFwHsDO);
            }
        } else {
            throw new MissingArgumentException("");
        }
    }

    /**
     * @param fwhsBgRequestDTO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室变更服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "房屋户室变更服务")
    public FwHsDO fwhsJbxxBianGeng(@RequestBody FwhsBgRequestDTO fwhsBgRequestDTO) {
        if (fwhsBgRequestDTO != null
                && StringUtils.isNotBlank(fwhsBgRequestDTO.getBgbh())
                && CollectionUtils.isNotEmpty(fwhsBgRequestDTO.getyFwHsIndexList())
                && CollectionUtils.isNotEmpty(fwhsBgRequestDTO.getFwHsList())) {
            String bgbh = fwhsBgRequestDTO.getBgbh();
            FwHsDO yFwHsDO = fwHsService.queryFwHsByIndex(fwhsBgRequestDTO.getyFwHsIndexList().get(0));
            FwHsDO fwHsDO = fwhsBgRequestDTO.getFwHsList().get(0);
            Object result = fwHsJbxxBgServiceImpl.jbxxBg(bgbh, Constants.BGLX_BG, yFwHsDO, fwHsDO);
            if (result != null) {
                return (FwHsDO) result;
            }
        } else {
            throw new MissingArgumentException("");
        }
        return null;
    }

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 撤销拆分服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "房屋户室撤销拆分服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bgbh", value = "变更编号", required = true, dataType = "string", paramType = "path")})
    public void fwhsRevokeChaifen(@PathVariable("bgbh") String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        cxBgService.executeBgRevoke(bgbh);
    }

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室撤销合并服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "房屋户室撤销合并服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bgbh", value = "变更编号", required = true, dataType = "string", paramType = "path")})
    public void fwhsRevokeHeBing(@PathVariable("bgbh") String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        cxBgService.executeBgRevoke(bgbh);
    }

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室撤销灭失服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "房屋户室撤销灭失服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bgbh", value = "变更编号", required = true, dataType = "string", paramType = "path")})
    public void fwhsRevokeMieShi(@PathVariable("bgbh") String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        cxBgService.executeBgRevoke(bgbh);
    }

    /**
     * @param bgbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室撤销变更服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "房屋户室撤销变更服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bgbh", value = "变更编号", required = true, dataType = "string", paramType = "path")})
    public void fwhsRevokeJbxxBianGeng(@PathVariable("bgbh") String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        cxBgService.executeBgRevoke(bgbh);
    }

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwHsDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋户室撤销变更服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "房屋户室撤销变更服务")
    public void fwhsRevokeBg(@RequestParam(value = "fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        cxBgService.executeBgRevokeByFwHsIndex(fwHsIndex);
    }

    /**
     * @param fwHsBgxxDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 变更信息保存
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "房屋户室变更信息保存")
    public FwHsBgxxDO insertFwHsBgxx(@RequestBody FwHsBgxxDO fwHsBgxxDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return hsBgxxService.insertFwHsBgxx(fwHsBgxxDO);
    }

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取变更编号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取变更编号")
    public String maxBgbh() {
        return bgbhService.maxBgbh();
    }

    /**
     * @param fwHsIndex
     * @return boolean
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 判断房屋是否有变更
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "判断房屋是否有变更")
    public boolean checkFwHsHistory(@RequestParam(value = "fwHsIndex") String fwHsIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        List<SSjHsbgljbDO> sSjHsbgljbDOList = sSjHsbgjlbService.listHsBgjlByFwHsIndex(fwHsIndex);
        if (CollectionUtils.isNotEmpty(sSjHsbgljbDOList)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @param bgbh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsBgxxDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据变更编号查询变更信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据变更编号查询变更信息")
    public FwHsBgxxDO getFwHsBgxxByBgbh(@PathVariable("bgbh") String bgbh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return hsBgxxService.getFwHsBgxxByBgbh(bgbh);
    }

    /**
     * @param
     * @return String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取户室变更配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取户室变更配置")
    public String getHsHbConfig() {
        return hsfrzhsz;
    }

    /**
     * @param
     * @return String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据户室变更dto获取有效不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据户室变更dto获取有效不动产单元号")
    public List<String> listValidBdcdyhByFwhsBgRequestDTO(@RequestBody FwhsBgRequestDTO fwhsBgRequestDTO) {
        return hsBgxxService.listValidBdcdyhByFwhsBgRequestDTO(fwhsBgRequestDTO);
    }

    /**
     * @param
     * @return String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据户室变更VO获取有效不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据户室变更VO获取有效不动产单元号")
    public List<String> listValidBdcdyhByFwhsBgVo(@RequestBody FwhsBgVO FwhsBgVO) {
        return hsBgxxService.listValidBdcdyhByFwhsBgVo(FwhsBgVO);
    }

    /**
     * @param
     * @return String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据户室合并VO获取有效不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据户室合并VO获取有效不动产单元号")
    public List<String> listValidBdcdyhByFwhsHbVo(@RequestBody FwHsHbVO fwHsHbVO) {
        return hsBgxxService.listValidBdcdyhByFwhsHbVo(fwHsHbVO);
    }
}
