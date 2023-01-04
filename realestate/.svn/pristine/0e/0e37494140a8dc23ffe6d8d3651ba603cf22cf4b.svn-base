package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.certificate.config.PropsConfig;
import cn.gtmap.realestate.certificate.service.BdcGdsjPzService;
import cn.gtmap.realestate.certificate.service.BdcGdxxService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcGdxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcGdxxRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2018/11/16
 * @description 归档服务
 */
@RestController
@Api(tags = "不动产归档服务接口")
public class BdcGdxxRestController extends BaseController implements BdcGdxxRestService {

    @Autowired
    BdcGdxxService bdcGdxxService;

    @Autowired
    BdcGdsjPzService bdcGdsjPzService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    PropsConfig propsConfig;
    /**
     * 归档系统地址
     */
    @Value("${certificate.url.archive:}")
    private String gdXtDz;

    /**
     * 不动产档案地址
     */
    @Value("${certificate.url.bdcDaUrl:}")
    private String bdcDaUrl;

    @Value("${showGdxxType:0}")
    protected String showGdxxType;

    @Value("${qxdmFilter:1}")
    protected String qxdmFilter;

    /**
     * 区县配置归档地址，key为区县代码，value为归档地址
     * 配置示例：{'320400':'http://127.0.0.1:8082/archive/gateway.action'}
     */
    @Value("#{${certificate.url.qxArchive:null}}")
    private Map<String, String> qxArchive;

    @Value("${gdxx.cxsl:false}")
    Boolean gdcxsl;

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    /**
     * @param archiveUrl
     * @param archiveXml
     * @return responseXml
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 调用档案接口并获取归档结果xml
     */
    @Override
    @ApiIgnore
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "调用档案接口并获取归档结果xml")
    @ApiImplicitParams({@ApiImplicitParam(name = "archiveUrl", value = "档案归档地址", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "archiveXml", value = "业务归档xml", dataType = "string", paramType = "query")})
    public String postArchiveInfo(@RequestParam(name = "archiveUrl") String archiveUrl, @RequestParam(name = "archiveXml") String archiveXml) {
        return bdcGdxxService.postArchiveInfo(archiveUrl, archiveXml);
    }

    /**
     * @param archiveResponseXml
     * @return List<BdcGdxxDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 根据档案返回结果初始化归档信息实体对象的集合
     */
    @Override
    @ApiIgnore
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据档案返回xml初始化归档信息集合")
    @ApiImplicitParams({@ApiImplicitParam(name = "archiveResponseXml", value = "档案返回结果xml", dataType = "string", paramType = "query")})
    public List<BdcGdxxDO> initBdcGdxx(@RequestParam(name = "archiveResponseXml") String archiveResponseXml) {
        return bdcGdxxService.initBdcGdxx(archiveResponseXml);
    }


    /**
     * @param archiveResponseXml
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 解析档案返回xml插入归档信息
     */
    @Override
    @ApiIgnore
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "解析档案返回xml批量插入归档信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "archiveResponseXml", value = "档案返回结果xml", dataType = "string", paramType = "query")})
    public void insertBdcGdxxList(@RequestParam(name = "archiveResponseXml") String archiveResponseXml,@RequestParam(name = "currentUserName") String currentUserName) {
        List<BdcGdxxDO> bdcGdxxList = bdcGdxxService.initBdcGdxx(archiveResponseXml);
        if (CollectionUtils.isNotEmpty(bdcGdxxList)) {
            for (BdcGdxxDO bdcGdxxDO : bdcGdxxList) {
                bdcGdxxDO.setGdxxid(UUIDGenerator.generate16());
                bdcGdxxDO.setGdsj(new Date());
                if(StringUtils.isNotBlank(currentUserName)){
                    bdcGdxxDO.setGdrxm(currentUserName);
                }
//                bdcGdxxService.insertBdcGdxx(bdcGdxxDO);
                bdcGdxxService.updateBdcGdxx(bdcGdxxDO);
            }
        }
    }

    /**
     * @param bdcGdxxQO 归档查询对象
     * @param pageable  分页查询参数
     * @return Page<BdcZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产归档信息分页
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询归档信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "size", value = "一页显示几条", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "第几页", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcXmGdxxDTO> listBdcGdxxByPage(Pageable pageable, @RequestParam(name = "bdcGdxxQO", required = false) String bdcGdxxQO) {
        if (StringUtils.isBlank(bdcGdxxQO)) {
            throw new AppException("查询参数不能为空！");
        }
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(bdcGdxxQO, HashMap.class));
        // 处理扫描枪参数
        map = getSmqsrParam(map,"smqsr");
        // 当前用户只能看到 本组织的信息，用区县代码过滤
        // 1为需要这个过滤 0不需要这个过滤
        if(1 == Integer.parseInt(qxdmFilter)){
            String qxdm = "";
            List<OrganizationDto> listOrgDto = userManagerUtils.getCurrentUser().getOrgRecordList();
            if(CollectionUtils.isNotEmpty(listOrgDto)){
                qxdm = listOrgDto.get(0).getRegionCode();
            }
            map.put("qxdm",qxdm);
        }


        if(gdcxsl){
            return bdcGdxxService.listBdcGdxxByPage(pageable, new HashMap<>(map), showGdxxType);
        }else {
            // 归档信息台账展示方式
            // 0：按流程展示，一条流程一条数据，批量组合流程展示任意一条存在现势产权的数据。
            // 1：按项目展示，一个项目一条数据.
            if(Constants.SHOW_GDXX_TYPE_HF == Integer.parseInt(showGdxxType)){
                Page<BdcXmGdxxDTO> pageList = repo.selectPaging("listBdcGdxxHfByPage", map, pageable);
                // 批量的件要在bdcdyh zl bdcqzh 后面加 等 字
                for(int i =0;i<pageList.getContent().size();i++){
                    String slbh = pageList.getContent().get(i).getSlbh();
                    BdcXmQO qo = new BdcXmQO();
                    qo.setSlbh(slbh);
                    List<BdcXmDO> listxm = bdcXmFeignService.listBdcXm(qo);

                    if(listxm.size() >1){
                        Set<String> bdcdyhSet = new HashSet();
                        Set<String> zlSet = new HashSet();
                        Set<String> bdcqzhSet = new HashSet();
                        for (BdcXmDO bdcXmDO : listxm) {
                            bdcdyhSet.add(bdcXmDO.getBdcdyh());
                            zlSet.add(bdcXmDO.getZl());
                            bdcqzhSet.add(bdcXmDO.getBdcqzh());
                        }

                        if (CollectionUtils.size(bdcdyhSet) > 1) {
                            pageList.getContent().get(i).setBdcdyh(pageList.getContent().get(i).getBdcdyh() + CommonConstantUtils.SUFFIX_PL);
                        }
                        if (CollectionUtils.size(zlSet) > 1) {
                            pageList.getContent().get(i).setZl(pageList.getContent().get(i).getZl() + CommonConstantUtils.SUFFIX_PL);
                        }
                        if (CollectionUtils.size(bdcqzhSet) > 1) {
                            pageList.getContent().get(i).setBdcqzh(pageList.getContent().get(i).getBdcqzh() + CommonConstantUtils.SUFFIX_PL);
                        }
                    }
                }
                return pageList;
            }else{
                return repo.selectPaging("listBdcGdxxNtByPage", map, pageable);
            }
        }
    }

    /**
     * @param bdcGdxxQO 归档查询对象
     * @return List<BdcGdxxDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 查询归档信息集合
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询归档信息集合")
    public List<BdcGdxxDO> listBdcGdxx(@RequestBody BdcGdxxQO bdcGdxxQO) {
        return bdcGdxxService.listBdcGdxx(bdcGdxxQO);
    }

    /**
     * @param gdxxid
     * @return BdcGdxxDO
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 根据主键查询归档信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键查询归档信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gdxxid", value = "归档信息id", dataType = "string", paramType = "path")})
    public BdcGdxxDO queryBdcGdxx(@NotBlank(message = "不能为空") @PathVariable(name = "gdxxid") String gdxxid) {
        return bdcGdxxService.queryBdcGdxx(gdxxid);
    }


    /**
     * @param gzlslid
     * @param archiveUrl
     * @return
     * @author <a href ="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 根据归档配置生成归档xml并归档
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据归档配置生成归档xml并归档")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "archiveUrl", value = "归档地址", dataType = "string", paramType = "query")})
    public String postArchiveByPz(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "archiveUrl", required = false) String archiveUrl, @RequestParam(value = "xmid", required = false) String xmid,@RequestParam(value = "currentUserName") String currentUserName) {

        String archiveXml = bdcGdsjPzService.gdBdcXx(gzlslid, xmid);
        archiveUrl = StringUtils.isBlank(archiveUrl) ? gdXtDz : archiveUrl;

        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        String qxdm = "";
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            //尝试查询受理的
            List<BdcSlXmDO> bdcSlXmDOS = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if(CollectionUtils.isEmpty(bdcSlXmDOS)) {
                throw new AppException("此工作流对应的项目信息为空！");
            }
            // 获取区县代码
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            if(Objects.nonNull(bdcSlJbxxDO)) {
                qxdm = bdcSlJbxxDO.getQxdm();
            }
        }else {
            // 获取区县代码
            qxdm = bdcXmDTOList.get(0).getQxdm();
        }
        if(MapUtils.isNotEmpty(qxArchive) && qxArchive.containsKey(qxdm)){
            archiveUrl = qxArchive.get(qxdm);
        }

        String archiveResponseXml = postArchiveInfo(archiveUrl, archiveXml);
        LOGGER.info("归档人:{},归档地址：{}工作流实例:{},返回信息:{}",currentUserName,archiveUrl,gzlslid,archiveResponseXml);
        insertBdcGdxxList(archiveResponseXml,currentUserName);
        return bdcGdxxService.getDaIdByXmId(xmid);
    }


    /**
     * @param gzlslid
     * @return 不动产档案url
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据工作流实例id查询gdxx,拼装不动产档案url
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例id查询gdxx,拼装不动产档案url")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "string", paramType = "path")})
    public String getBdcGdxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "damx", required = false)String damxPz,
                                      @RequestParam(value = "xmid",required = false) String xmid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Map map = new HashMap<>();
            map.put("gzlslid", gzlslid);
            map.put("damx", damxPz);
            map.put("xmid",xmid);
            BdcGdxxDO bdcGdxxDO = repo.selectOne("getBdcGdxxByGzlslid", map);
            //查项目附表获取qjgldm
            List<BdcXmDTO> bdcXmFbDOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmFbDOList) && StringUtils.isNotBlank(bdcXmFbDOList.get(0).getQjgldm()) && MapUtils.isNotEmpty(propsConfig.getUrl())) {
                String daUrl = propsConfig.getUrl().get(bdcXmFbDOList.get(0).getQjgldm());
                if (StringUtils.isNotBlank(daUrl)) {
                    return daUrl.replace("@id", bdcXmFbDOList.get(0).getSlbh());
                }
            }
            if (bdcGdxxDO != null) {
                String daid = bdcGdxxDO.getDaid();
                String damx = bdcGdxxDO.getDamx();
                if (StringUtils.isNotBlank(daid) && StringUtils.isNotBlank(damx)) {
                    return bdcDaUrl.replace("daid", daid).replace("damx", damx);
                } else {
                    throw new AppException("没有归档信息，无法查看登记档案！");
                }
            }
        }
        return bdcDaUrl;
    }

    /**
     * @param xmid
     * @return daid
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据xmid查询对应的daid
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据xmid查询对应的daid")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", dataType = "string", paramType = "path")})
    public String queryBdcDaid(@PathVariable(value = "xmid") String xmid) {
        return bdcGdxxService.queryBdcDaid(xmid);
    }

    /**
     * @param xmid
     * @return
     * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据xmid删除归档信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据xmid删除归档信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", dataType = "string", paramType = "path")})
    public int deleteBdcGdxxByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcGdxxService.deleteBdcGdxxByXmid(xmid);
    }

    /**
     * 获取移交单台账显示归档信息的配置
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取移交单台账显示归档信息配置")
    public String getGdxxType() {
        return showGdxxType;
    }

}
