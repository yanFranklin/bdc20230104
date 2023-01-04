package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDysdDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcdyCxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.LjzQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDysdQO;
import cn.gtmap.realestate.common.core.service.feign.building.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-08-07
 * @description 不动产单元、产权证 锁定、解锁业务类
 */
@RestController
@RequestMapping(value = "/rest/v1.0")
public class BdcSdController extends BaseController {

    @Autowired
    BdcSdFeignService bdcSdFeignService;
    @Autowired
    AcceptBdcdyFeignService acceptBdcdyFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    AcceptLsBdcdyFeignService acceptLsBdcdyFeignService;
    @Autowired
    private FwLjzFeginService fwLjzFeginService;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    
    /**
     * @param bdcDysdDTOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 锁定不动产单元
     */
    @PostMapping("/bdcdysd/sd")
    public void sdBdcdy(@RequestBody List<BdcDysdDTO> bdcDysdDTOList) {
        if (CollectionUtils.isEmpty(bdcDysdDTOList)) {
            throw new MissingArgumentException("不动产单元锁定信息");
        }
        List<BdcDysdDO> bdcDysdDOList = new ArrayList<>();
        for (BdcDysdDTO bdcDysdDTO : bdcDysdDTOList){
            BdcDysdDO bdcDysdDO = new BdcDysdDO();
            BeanUtils.copyProperties(bdcDysdDTO, bdcDysdDO);
            String bdclx = BdcdyhToolUtils.queryBdclxByBdcdyh(bdcDysdDO.getBdcdyh(), bdcDysdDTO.getLx());
            if (StringUtils.isNotBlank(bdclx)) {
                bdcDysdDO.setBdclx(Integer.parseInt(bdclx.split("/")[0]));
            }
            bdcDysdDOList.add(bdcDysdDO);
        }

        bdcSdFeignService.sdBdcdy(bdcDysdDOList);
    }
    
    /**
     * @param bdcDysdDOList
     * @param jsyy
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 解锁不动产单元
     */
    @PostMapping("/bdcdysd/js")
    public void jdBdcdy(@RequestBody List<BdcDysdDO> bdcDysdDOList,
                        String jsyy) {
        if (CollectionUtils.isEmpty(bdcDysdDOList)) {
            throw new MissingArgumentException("不动产单元锁定信息");
        }
        if (StringUtils.isBlank(jsyy)){
            throw new AppException("解锁原因不能为空");
        }
        // 判断单元锁定状态
        List<BdcDysdDO> bdcDysdDOSd = new ArrayList<>();
        for (BdcDysdDO bdcDysdDO : bdcDysdDOList){
            BdcDysdDO bdcDysdSdzt = new BdcDysdDO();
            bdcDysdSdzt.setSdzt(1);
            bdcDysdSdzt.setBdcdyh(bdcDysdDO.getBdcdyh());
            // 判断是否存在锁定
            List<BdcDysdDO> bdcDysdDOSdzt = bdcSdFeignService.queryBdcdySd(bdcDysdSdzt);
            // 当存在锁定时 加入解锁列表
            if (CollectionUtils.isNotEmpty(bdcDysdDOSdzt)){
                bdcDysdDOSd.add(bdcDysdDO);
            }
        }
        // 当存在锁定信息时 对锁定信息进行解锁
        if (CollectionUtils.isNotEmpty(bdcDysdDOSd)) {
            bdcSdFeignService.jsBdcdy(bdcDysdDOSd, jsyy);
        }
    }
    
    /**
     * @param bdcZssdDOList
     * @param sdyy
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 不动产证书锁定
     */
    @PostMapping("/bdczssd/sd")
    public void sdBdczs(@RequestBody List<BdcZssdDO> bdcZssdDOList,
                        String sdyy) {
        if (CollectionUtils.isEmpty(bdcZssdDOList)) {
            throw new MissingArgumentException("证书锁定信息");
        }
        if (StringUtils.isBlank(sdyy)) {
            throw new AppException("锁定原因不能为空");
        }
        //  进行锁定
        if (CollectionUtils.isNotEmpty(bdcZssdDOList)) {
            bdcSdFeignService.sdBdczs(bdcZssdDOList, sdyy);
        }
    }
    
    /**
     * @param bdcZssdDOList
     * @param jsyy
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 不动产证书解锁
     */
    @PostMapping("/bdczssd/js")
    public void jsBdczs(@RequestBody List<BdcZssdDO> bdcZssdDOList,
                        String jsyy) {
        if (CollectionUtils.isEmpty(bdcZssdDOList)) {
            throw new MissingArgumentException("证书锁定信息");
        }
        if (StringUtils.isBlank(jsyy)){
            throw new AppException("解锁原因不能为空");
        }
    
        // 判断证书锁定状态
        List<BdcZssdDO> bdcZssdDOSd = new ArrayList<>();
        for (BdcZssdDO bdcZssdDO : bdcZssdDOList) {
            // 确认证书是否已被锁定过
            BdcZssdDO bdcZssdSdzt = new BdcZssdDO();
            bdcZssdSdzt.setSdzt(1);
            bdcZssdSdzt.setZsid(bdcZssdDO.getZsid());
            bdcZssdSdzt.setCqzh(bdcZssdDO.getCqzh());
            List<BdcZssdDO> bdcZssdDOSdzt = bdcSdFeignService.queryBdczsSd(bdcZssdSdzt);
            if (CollectionUtils.isNotEmpty(bdcZssdDOSdzt)) {
                bdcZssdDOSd.add(bdcZssdDO);
            }
        }
        // 当存在已锁定证书时 进行解锁
        if (CollectionUtils.isNotEmpty(bdcZssdDOSd)) {
            bdcSdFeignService.jsBdczs(bdcZssdDOSd, jsyy);
        }
    }
    
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 不动产单元锁定状态
     */
    @PostMapping("/bdcdysd/sdzt")
    public Object queryBdcdySdzt(@RequestBody BdcDysdDO bdcDysdDO) {
        if (bdcDysdDO == null || StringUtils.isBlank(bdcDysdDO.getBdcdyh())) {
            throw new MissingArgumentException("bdcdyh,bdcdysdid");
        }
        List<BdcDysdDO> bdcDysdDOList = bdcSdFeignService.queryBdcdySd(bdcDysdDO);
        if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
            return bdcDysdDOList.get(0);
        }
        return new BdcDysdDO();
    }
    
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 不动产证书锁定状态
     */
    @PostMapping("/bdczssd/sdzt")
    public Object queryBdczsSdzt(@RequestBody BdcZssdDO bdcZssdDO) {
        if (bdcZssdDO == null || StringUtils.isBlank(bdcZssdDO.getCqzh())) {
            throw new MissingArgumentException("cqzh");
        }
        List<BdcZssdDO> bdcZssdDOList = bdcSdFeignService.queryBdczsSd(bdcZssdDO);
        if (CollectionUtils.isNotEmpty(bdcZssdDOList)) {
            return bdcZssdDOList.get(0);
        }
        return new BdcZssdDO();
    }
    
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产单元列表信息
     */
    @GetMapping("/bdcdy/page")
    public Object queryBdcdyList(@LayuiPageable Pageable pageable, BdcdyCxDTO bdcdyxxDTO) {
        String bdcdyh = bdcdyxxDTO.getBdcdyh();
        String zdtzm = StringUtils.isNotBlank(bdcdyh) ? StringUtils.substring(bdcdyh, 14, 1) : null;
        String dzwtzm = StringUtils.isNotBlank(bdcdyh) ? StringUtils.substring(bdcdyh, 20, 1) : null;
        if(StringUtils.isBlank(bdcdyxxDTO.getDzwtzm())){
            LOGGER.error("查询不动产单元列表定着物特征码过滤条件为空:{}",bdcdyxxDTO);
            return null;
        }
        if(StringUtils.isNotBlank(dzwtzm) &&StringUtils.equals(bdcdyxxDTO.getDzwtzm(),dzwtzm)){
            LOGGER.error("查询不动产单元列表不动产单元号与查询条件冲突:{}",bdcdyxxDTO);
            return null;
        }
        if(StringUtils.equals("ls",bdcdyxxDTO.getCxfw())) {
            return acceptLsBdcdyFeignService.listLsBdcdyhByPageOrList(pageable, JSON.toJSONString(bdcdyxxDTO), true);
        }else {
            switch (bdcdyxxDTO.getDyhcxlx()) {
                /**
                 * @param
                 * @return
                 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
                 * @description 分页查询定着物类型不动产单元信息
                 */
                case 1:
                    return addLayUiCode(acceptBdcdyFeignService.listTdAndDzwBdcdyByPage(pageable, null, zdtzm, bdcdyxxDTO.getDzwtzm(), null, JSON.toJSONString(bdcdyxxDTO)));
                /**
                 * @param
                 * @return
                 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
                 * @description 分页查询海域及其定着物类型不动产单元信息
                 */
                case 2:
                    return addLayUiCode(acceptBdcdyFeignService.listHyBdcdyByPage(pageable, zdtzm, dzwtzm, JSON.toJSONString(bdcdyxxDTO)));
                /**
                 * @param
                 * @return
                 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
                 * @description 分页查询构筑物不动产单元信息
                 */
                case 3:
                    return addLayUiCode(acceptBdcdyFeignService.listGzwBdcdyByPage(pageable, zdtzm, dzwtzm, JSON.toJSONString(bdcdyxxDTO)));
                default:
                    break;
            }
            return null;
        }
    }
    
    /**
     * @param bdcZsQO
     * @param pageable
     * @return Page<BdcZsVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询不动产证书信息
     */
    @GetMapping("/bdczs/page")
    public Object queryBdczsList(@LayuiPageable Pageable pageable, BdcZsQO bdcZsQO) {
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcZsVO> bdcZsVOPage = bdcZsFeignService.bdcZsByPageJson(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcZsQO);
        return addLayUiCode(bdcZsVOPage);
    }
    
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存不动产单元锁定备注
     */
    @PostMapping("/bdcdysd/bz")
    public void saveBdcdyBz(@RequestBody BdcDysdDO bdcDysdDO) {
        if (StringUtils.isAnyBlank(bdcDysdDO.getDysdid(), bdcDysdDO.getBz())) {
            throw new MissingArgumentException("dysdid,bz");
        }
        bdcSdFeignService.saveBdcdysdBz(bdcDysdDO);
    }
    
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存不动产证书锁定备注
     */
    @PostMapping("/bdczssd/bz")
    public void saveBdczsBz(@RequestBody BdcZssdDO bdcZssdDO) {
        if (StringUtils.isAnyBlank(bdcZssdDO.getZssdid(), bdcZssdDO.getBz())) {
            throw new MissingArgumentException("zssdid,bz");
        }
        bdcSdFeignService.saveBdczssdBz(bdcZssdDO);
    }

    /**
     * @param bdcZssdDO 不动产证书锁定信息
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 保存不动产证书锁定信息
     */
    @PostMapping("/bdczssd/save")
    public BdcZssdDO saveBdcZssd(@RequestBody BdcZssdDO bdcZssdDO) {
        if (Objects.isNull(bdcZssdDO)) {
            throw new MissingArgumentException("缺失证书锁定信息");
        }
        if(StringUtils.isBlank(bdcZssdDO.getZssdid())){
            bdcZssdDO.setZssdid(UUIDGenerator.generate16());
            bdcZssdDO.setSdsj(new Date());
        }
        this.bdcSdFeignService.updateSdZs(Arrays.asList(bdcZssdDO));
        return bdcZssdDO;
    }

    /**
     * 初始化锁定证书页面表单参数内容
     * @param gzlslid 工作流实例ID
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 不动产证书锁定DO
     */
    @GetMapping("/bdczssd/info")
    public BdcZssdDO getBdcZssdBasicInfo(String xmid, String gzlslid) {
        if (StringUtils.isBlank(xmid) && StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失参数项目ID或工作流实例ID");
        }
        BdcZssdDO bdcZssdDO = new BdcZssdDO();
        bdcZssdDO.setGzldymc(this.getGzldymc(xmid, gzlslid));
        bdcZssdDO.setSdsj(new Date());
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(Objects.nonNull(userDto)){
            bdcZssdDO.setSdr(userDto.getAlias());
            bdcZssdDO.setSdrid(userDto.getId());
        }
        if(StringUtils.isNotBlank(gzlslid)){
            List<BdcZsDO> bdcZsDOList = this.bdcZsFeignService.queryYxmBdcqzhByGzlslid(gzlslid, null);
            if(CollectionUtils.isNotEmpty(bdcZsDOList)){
                bdcZssdDO.setZsid(bdcZsDOList.get(0).getZsid());
                bdcZssdDO.setCqzh(bdcZsDOList.get(0).getBdcqzh());
            }
        }
        return bdcZssdDO;
    }

    /**
     * 获取工作流定义名称
     */
    private String getGzldymc(String xmid, String gzlslid){
        BdcXmQO queryParam = new BdcXmQO();
        queryParam.setXmid(xmid);
        queryParam.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(queryParam);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new MissingArgumentException("未获取到项目信息");
        }
        return bdcXmDOList.get(0).getGzldymc();
    }

    /**
     * 根据证书锁定ID批量删除不动产证书锁定信息
     * @param zssdIdList 工作流实例ID
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 不动产证书锁定DO
     */
    @DeleteMapping(value="/bdczssd/sc")
    public void saveBdcZssd(@RequestBody List<String> zssdIdList) {
        if(CollectionUtils.isEmpty(zssdIdList)){
            throw new MissingArgumentException("缺失证书锁定信息");
        }
        this.bdcSdFeignService.batchDeleteBdcZssd(zssdIdList);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取地区下拉
     */
    @GetMapping("/qjgldm/list")
    public List<BdcZdQjgldmDO> listQjgldm(){
        List<BdcZdQjgldmDO> zdqjgldmList =new ArrayList<>();
        List<String> qjgldmList = Container.getBean(BdcConfigUtils.class).qjgldmFilter("selectbdcdyh");
        List<Map> qjgldmZdMap = bdcZdFeignService.queryBdcZd("qjgldm");
        for(Map qjgldmZd:qjgldmZdMap){
            String dm =qjgldmZd.get("DM").toString();
            if(CollectionUtils.isEmpty(qjgldmList) ||qjgldmList.contains(dm)){
                BdcZdQjgldmDO bdcZdQjgldmDO =new BdcZdQjgldmDO();
                bdcZdQjgldmDO.setDm(dm);
                bdcZdQjgldmDO.setMc(qjgldmZd.get("MC").toString());
                zdqjgldmList.add(bdcZdQjgldmDO);
            }
        }
        if (CollectionUtils.isNotEmpty(qjgldmList) && CollectionUtils.isEmpty(zdqjgldmList)){
            // 配置权籍管理代码与字典不匹配
            throw new AppException("权籍管理代码过滤与字典表不对应，请检查配置，"+ JSONObject.toJSONString(qjgldmList));
        }
        return zdqjgldmList;
    }

    /**
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @description 分页查询逻辑幢信息
     */
    @ResponseBody
    @GetMapping("/listLjzByPageJson")
    public Object listLjzByPageJson(@LayuiPageable Pageable pageable, LjzQO ljzQO) {
        ljzQO.setBdcdyh(StringUtils.deleteWhitespace(ljzQO.getBdcdyh()));
        ljzQO.setZrzh(StringUtils.deleteWhitespace(ljzQO.getZrzh()));
        ljzQO.setLszd(StringUtils.deleteWhitespace(ljzQO.getLszd()));
        ljzQO.setFwmc(StringUtils.deleteWhitespace(ljzQO.getFwmc()));
        ljzQO.setLjzh(StringUtils.deleteWhitespace(ljzQO.getLjzh()));
        ljzQO.setZldz(StringUtils.deleteWhitespace(ljzQO.getZldz()));
        ljzQO.setDh(StringUtils.deleteWhitespace(ljzQO.getDh()));
        Page<Map> results = fwLjzFeginService.listLjzByPageJson(pageable, JSON.toJSONString(ljzQO));
        return PageUtils.addLayUiCodeWithQjgldm(results,ljzQO.getQjgldm());
    }

    /**
     * @param fwdcbIndexs 多个逻辑幢主键,用逗号隔开
     * @return 户室信息
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @description 根据逻辑幢主键获取户室信息
     */
    @ResponseBody
    @GetMapping("/getAllHsByFwdcbIndex")
    public List<FwHsDO> getAllHsByFwdcbIndex(String fwdcbIndexs, String qjgldm) {
        List<FwHsDO> fwHsDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(fwdcbIndexs)) {
            for (String fwdcbIndex : fwdcbIndexs.split(CommonConstantUtils.ZF_YW_DH)) {
                if (StringUtils.isNotBlank(fwdcbIndex)) {
                    List<FwHsDO> hsList = null;
                    //只查询实测户室
                    hsList = fwHsFeignService.listFwhsByFwDcbIndex(fwdcbIndex,qjgldm);
                    if (CollectionUtils.isEmpty(hsList)) {
                        throw new AppException("逻辑幢：" + fwdcbIndex + "未找到有效的户室，请检查数据");
                    } else {
                        fwHsDOList.addAll(hsList);
                    }
                }
            }
        }
        return fwHsDOList;
    }

    /**
     * @param bdcdyhList
     * @return
     * @author <a href ="mailto:huanghui@gtmap.cn">huanghui</a>
     * @description 查询根据不动产单元号批量查询锁定的不动产单元信息
     */
    @PostMapping("/bdcdysd/queryBdcdySdList")
    public Object queryBdcdySdList(@RequestBody List<String> bdcdyhList) {
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            throw new MissingArgumentException("查询不动产单元信息为空");
        }
        List<BdcDysdDO> bdcDysdDOList = new ArrayList<>(bdcdyhList.size());
        // 分批查询
        List<List> bdcdyhPartList = ListUtils.subList(bdcdyhList, 500);
        for (List partBdcdyhs : bdcdyhPartList) {
            BdcDysdQO bdcDysdQO = new BdcDysdQO();
            bdcDysdQO.setBdcdyhList(partBdcdyhs);
            bdcDysdQO.setSdzt(1);
            List<BdcDysdDO> bdcDysdDOPartList = bdcBdcdyFeignService.listBdcDysd(bdcDysdQO);
            if (CollectionUtils.isNotEmpty(bdcDysdDOPartList)) {
                bdcDysdDOList.addAll(bdcDysdDOPartList);
            }
        }
        //判断返回
        if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
            return bdcDysdDOList.get(0);
        }
        return new BdcDysdDO();

    }
}
