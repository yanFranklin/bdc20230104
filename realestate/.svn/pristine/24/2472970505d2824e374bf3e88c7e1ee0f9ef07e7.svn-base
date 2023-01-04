package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxmPzMapper;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.core.thread.FsAutoExecFpxxThread;
import cn.gtmap.realestate.accept.service.BdcDdxxAbstractService;
import cn.gtmap.realestate.accept.service.BdcGenerateQlrService;
import cn.gtmap.realestate.accept.service.BdcQllxService;
import cn.gtmap.realestate.accept.service.BdcSfService;
import cn.gtmap.realestate.accept.service.impl.ddxx.BdcDdxxFactory;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.config.accept.BdcSlSfxmConfig;
import cn.gtmap.realestate.common.config.accept.MrsfxxConfig;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.JfsxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.JfsxxRequestHsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.JfsxxRequestJkxmDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.fs.FsczfpxxRquestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.dzfpmx.response.YkqDzfpmxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmAndFbDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.*;
import cn.gtmap.realestate.common.core.qo.init.BdcLzrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxmPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSqrFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NantongFsxtzfFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDyafeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSfxmVO;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/26
 * @description 不动产收费服务
 */
@Service
public class BdcSfServiceImpl extends BaseController implements BdcSfService {

    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcSlSfxmPzService bdcSlSfxmPzService;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcSlSfxmService bdcSlSfxmService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    private BdcDjxlPzService bdcDjxlPzService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private NantongFsxtzfFeignService nantongFsxtzfFeignService;
    @Autowired
    private BdcSlSfxmPzFeignService bdcSlSfxmPzFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcSlSfxmPzMapper bdcSlSfxmPzMapper;
    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    BdcGenerateQlrService bdcGenerateQlrService;
    @Autowired
    BdcSlFwxxService bdcSlFwxxService;
    @Autowired
    BdcSlSqrService bdcSlSqrService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcSlHsxxService bdcSlHsxxService;

    @Autowired
    BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    private DozerBeanMapper acceptDozerMapper;
    @Resource(name = "dozerSameNullFMapper")
    private DozerBeanMapper dozerMapperF;
    @Autowired
    private BdcSlSjclService bdcSlSjclService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    BdcXtJgFeignService bdcXtJgFeignService;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    @Autowired
    BdcYjdhSfxxGxService bdcYjdhSfxxGxService;
    @Autowired
    BdcYjSfDdxxService bdcYjSfDdxxService;
    @Autowired
    BdcSlWxjjxxService bdcSlWxjjxxService;
    @Autowired
    BdcLzrFeignService bdcLzrFeignService;
    @Autowired
    BdcSlFpxxService bdcSlFpxxService;
    @Autowired
    ThreadEngine threadEngine;
    @Autowired
    BdcLcTsjfGxService bdcLcTsjfGxService;
    @Autowired
    private ProcessInsCustomExtendClient processInsCustomExtendClient;
    @Autowired
    BdcDyafeignService bdcDyafeignService;
    @Autowired
    private BdcSlSysService bdcSlSysService;
    @Autowired
    BdcDjbxxFeignService bdcDjbxxFeignService;
    @Autowired
    BdcSlSfssDdxxService bdcSlSfssDdxxService;
    @Autowired
    BdcDdxxFactory bdcDdxxFactory;
    @Autowired
    BdcQllxService bdcQllxService;

    //收费信息根据房间号字段判断不收费--常州特殊需求
    @Value("${sfxx.bsf.fjhcontains:}")
    private String fjhBsf;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费默认用户, 用于接收第三方收费信息时候无用户的情况
     */
    @Value("${sfxx.username:}")
    private String sfUsername;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  不发证登记小类
     */
    @Value("${bfzdjxl:}")
    private String bfzdjxl;


    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSfServiceImpl.class);

    @Value("#{'${hzlc.gzldyid:}'.split(',')}")
    protected List<String> hzlcdyidList;
    @Value("#{'${ysbz.gzldyid:}'.split(',')}")
    protected List<String> ysbzdyidList;
    @Value("#{'${fwdyqsc.djxl:}'.split(',')}")
    protected List<String> fwdyqscdjxlList;
    @Value("#{'${clfmmzy.djxl:}'.split(',')}")
    protected List<String> clfmmzydjxlList;
    @Value("#{'${sfxx.zzfwytdm:}'.split(',')}")
    protected List<String> zzfwytdmList;
    @Value("${sfxx.tsxx:}")
    private String tsxx;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费标准：不收登记费,工本费第一本不免费
     */
    @Value("#{'${clfmmzy.tssfdjyy:}'.split(',')}")
    protected List<String> tssfdjyyList;

    @Value("${fs.jfsbmurl:}")
    private String jfsbmurl;

    @Value("${sfxx.sfxm.tdsyqsfxmdm:}")
    private String tdsyqsfxmdm;

    /*
     * 非住宅房屋用途代码 --- 盐城使用
     * */
    @Value("#{'${sfxx.fzzfwytdm:}'.split(',')}")
    private List<String> fzzfwytdmList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 是否月结默认否的流程
     */
    @Value("#{'${sfxx.sfyjmrf:}'.split(',')}")
    protected List<String> sfyjmrfDyidList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 版本配置
     */
    @Value("${data.version:}")
    private String dataversion;

    /*
     * 特殊收费项目  key 为登记原因，value 为登记小类集合
     * */
    @Autowired
    BdcSlSfxmConfig bdcSlSfxmConfig;

    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;

    @Autowired
    MrsfxxConfig mrsfxxConfig;

    //登记费计算方法
    @Value("${sfxx.djfjsff: 0}")
    private Integer djfjsff;
    /*
     * 需要计算户室数量的定义id
     * */
    @Value("#{'${sfxx.counthsslDyids:}'.split(',')}")
    private List<String> jshsslDyidList;
    /**
     * 不生成土地收费信息的工作流定义ID配置
     */
    @Value("#{'${sfxx.bsctdsfxx.gzldyids:}'.split(',')}")
    private List<String> bsctdsfxxGzldyids;

    /*
     * 是否过滤土地收益金
     * */
    @Value("${sfxx.sfxm.sfgltdsyj:false}")
    private boolean sfgltdsyj;

    /**
     * 宅基地相关流程工作流定义id
     */
    @Value("#{'${zjdlc.gzldyid:}'.split(',')}")
    protected List<String> zjdlcdyidList;

    /*是否删除金额为0 的收费项目*/
    @Value("${sfxx.sfxm.delete0je: false}")
    private boolean delete0Jexm;

    /*金额为0 收费状态更新为不收费*/
    @Value("${sfxx.sfzt.bsf: true}")
    private boolean isBsf;
    // 合一支付缴费途径，默认值设置
    @Value("${sfxx.hyzfjftj:}")
    private Integer hyzfjftj;
    // 合一支付缴费类型，默认值设置
    @Value("${sfxx.hyzfjflx:}")
    private Integer hyzfjflx;

    // 存量房工作流定义ID
    @Value("#{'${clfzy.gzldyid:}'.split(',')}")
    private List<String> clfzygzldyidList;


    /**
     * @param gzlslid 工作流实例id
     * @return 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID获取不动产受理收费信息
     */
    @Override
    public BdcSlSfxxDO cshSfxx(String gzlslid, String xmid) {
        BdcSlSfxxDO bdcSlSfxxDO = null;
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        Integer lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            //1:普通  2：组合  3：批量  4:批量组合
            if (CommonConstantUtils.LCLX_PT.equals(lclx)) {
                for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                    bdcSlSfxxDO = listCshBdcSfxx(gzlslid, bdcXmDTO.getXmid(), bdcXmDTO.getDjxl(), bdcXmDTO.getGzldyid(), bdcXmDTO.getSlbh(), bdcXmDTO.getBdcdyh());
                }
            } else if (CommonConstantUtils.LCLX_ZH.equals(lclx)) {
                //组合流程
                if(StringUtils.isNotBlank(xmid)){
                    // 项目ID不为空，按项目区分，对不同的项目进行收费信息初始化
                    bdcSlSfxxDO = this.handlerSfxxByXmid(gzlslid, xmid);
                }else{
                    for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                        bdcSlSfxxDO = listCshBdcSfxx(gzlslid, bdcXmDTO.getXmid(), bdcXmDTO.getDjxl(), bdcXmDTO.getGzldyid(), bdcXmDTO.getSlbh(), bdcXmDTO.getBdcdyh());
                    }
                }
            } else if (CommonConstantUtils.LCLX_PL.equals(lclx)) {
                bdcSlSfxxDO = listCshBdcSfxx(gzlslid, bdcXmDTOList.get(0).getXmid(), bdcXmDTOList.get(0).getDjxl(), bdcXmDTOList.get(0).getGzldyid(), bdcXmDTOList.get(0).getSlbh(), bdcXmDTOList.get(0).getBdcdyh());
            } else if (CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
                //批量组合流程根据登记小类去重获取需要循环的数量
                if(StringUtils.isNotBlank(xmid)){
                    // 项目ID不为空，按项目区分，对不同的项目进行收费信息初始化
                    bdcSlSfxxDO = this.handlerSfxxByXmid(gzlslid, xmid);
                }else{
                    Set<BdcXmDTO> newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDTO::getDjxl));
                    newBdcXm.addAll(bdcXmDTOList);
                    for (BdcXmDTO bdcXmDTO : newBdcXm) {
                        bdcSlSfxxDO = listCshBdcSfxx(gzlslid, bdcXmDTO.getXmid(), bdcXmDTO.getDjxl(), bdcXmDTO.getGzldyid(), bdcXmDTO.getSlbh(), bdcXmDTO.getBdcdyh());
                    }
                }
            }
        } else {
            throw new AppException("初始化收费信息时未获取到项目,工作流实例id为:" + gzlslid);
        }
        return bdcSlSfxxDO;
    }

    /**
     * 收费信息初始化时，项目ID不为空，按项目区分，对不同的项目进行收费信息初始化
     */
    private BdcSlSfxxDO handlerSfxxByXmid(String gzlslid, String xmid){
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            return listCshBdcSfxx(gzlslid, bdcXmDOList.get(0).getXmid(), bdcXmDOList.get(0).getDjxl(), bdcXmDOList.get(0).getGzldyid(), bdcXmDOList.get(0).getSlbh(), bdcXmDOList.get(0).getBdcdyh());
        }
        return new BdcSlSfxxDO();
    }

    @Override
    public BdcSlSfxxDO cshFdjlcSfxx(String gzlslid) {
        BdcSlSfxxDO bdcSlSfxxDO = null;
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (bdcSlJbxxDO != null) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
                if (StringUtils.isNotBlank(processInstanceData.getProcessDefinitionKey())) {
                    String gzldyid = processInstanceData.getProcessDefinitionKey();
                    for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                        bdcSlSfxxDO = listCshBdcSfxx(gzlslid, bdcSlXmDO.getXmid(), bdcSlXmDO.getDjxl(), gzldyid, bdcSlJbxxDO.getSlbh(), bdcSlXmDO.getBdcdyh());
                    }
                }
            } else {
                throw new AppException("初始化收费信息时未获取到项目,工作流实例id为:{}" + gzlslid);
            }
        } else {
            throw new AppException("初始化收费信息时未获取到基本信息,工作流实例id为:{}" + gzlslid);
        }
        return bdcSlSfxxDO;
    }

    /**
     * @param bdcSlSfxxDO 工作流实例id
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据基本信息id删除收费信息（包括收费信息和收费项目)
     */
    @Override
    public void deleteSfxx(BdcSlSfxxDO bdcSlSfxxDO) {
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getGzlslid())) {
            String gzlslid = bdcSlSfxxDO.getGzlslid();
            List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>(10);
            if(StringUtils.isNotBlank(bdcSlSfxxDO.getXmid())){
                bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByXmid(bdcSlSfxxDO.getXmid());
            }else{
                bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            }
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                boolean deleteSfxx = true;
                //判断删除时是否需要删除收费信息
                List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                String gzldyid = "";
                String djxl = "";
                if (CollectionUtils.isEmpty(bdcXmDTOList)) {
                    BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
                    if (Objects.nonNull(bdcSlJbxxDO)) {
                        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
                        gzldyid = bdcSlJbxxDO.getGzldyid();
                        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                            djxl = bdcSlXmDOList.get(0).getDjxl();
                        }
                    }

                } else {
                    gzldyid = bdcXmDTOList.get(0).getGzldyid();
                    djxl = bdcXmDTOList.get(0).getDjxl();
                }
                if (StringUtils.isNotBlank(gzldyid) && StringUtils.isNotBlank(djxl)) {
                    BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzService.queryBdcDjxlPzByGzldyidAndDjxl(gzldyid, djxl);
                    if (Objects.nonNull(bdcDjxlPzDO) && Objects.equals(CommonConstantUtils.SF_F_DM, bdcDjxlPzDO.getLcscsfxx())) {
                        deleteSfxx = false;
                    }
                }
                if (deleteSfxx) {
                    bdcSlSfxxService.deleteBdcSlSfxxByGzlslidAndXmid(bdcSlSfxxDO.getGzlslid(), bdcSlSfxxDO.getXmid());
                }
                for (BdcSlSfxxDO sfxx : bdcSlSfxxDOList) {
                    if (!deleteSfxx) {
                        //配置不删除收费信息，更新sfsc 字段为1；
                        LOGGER.warn("当前流程实例id{}配置删除流程不删除收费信息,更新是否删除字段为1", gzlslid);
                        sfxx.setSfsc(1);
                        bdcSlSfxxService.updateBdcSlSfxx(sfxx);
                    }
                    bdcSlSfxmService.deleteBdcSlSfxmBySfxxid(sfxx.getSfxxid());
                }
            }
        }
    }

    /**
     * @param gzlslid 工作流实例id
     * @param xmid    项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织页面收费信息内容
     */
    @Override
    public BdcSlSfxxDTO generateSfxx(String gzlslid, String xmid) {
        BdcSlSfxxDTO bdcSlSfxxDTO = new BdcSlSfxxDTO();
        bdcSlSfxxDTO = generateSfxxData(bdcSlSfxxDTO, gzlslid, xmid);
        return bdcSlSfxxDTO;
    }

    /**
     * @param gzlslid 工作流实例id
     * @param xmid    项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织页面收费信息内容(南通)
     */
    @Override
    public BdcSlSfxxDTO generateSfxxNt(String gzlslid, String xmid) {
        BdcSlSfxxDTO bdcSlSfxxDTO = new BdcSlSfxxDTO();
        bdcSlSfxxDTO = generateSfxxDataNt(bdcSlSfxxDTO, gzlslid, xmid);
        return bdcSlSfxxDTO;
    }

    /**
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO>
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [gzlslid, xmid]
     * @description 查询收费信息（包含作废数据）
     */
    @Override
    public List<BdcSlSfxxDO> queryBdcSlSfxxBhzf(String gzlslid, String xmid) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList;
        //单个流程和批量流程是一个收费单，直接挂接的资源无法获取xmid，根据gzlslid查询
        //组合流程是列表展现，根据xmid获取
        if (StringUtils.isNotBlank(xmid)) {
            bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByXmidBhzf(xmid);
        } else {
            bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslidBhzf(gzlslid);
        }
        return bdcSlSfxxDOList;
    }

    @Override
    public Boolean gxSfxxSfzt(BdcSlSfxxDO bdcSlSfxxDO) {
        Boolean gxflag = false;
        // 当缴费书编码为空时 弹出错误信息
        if (StringUtils.isBlank(bdcSlSfxxDO.getJfsbm())) {
            throw new AppException("缴费书编码为空 更新失败");
        }
        LOGGER.info("更新收费信息内容为{}", bdcSlSfxxDO);

        // 根据缴费书号码 查询对应的收费信息
        Example example = new Example(BdcSlSfxxDO.class);
        example.createCriteria().andEqualTo("jfsbm", bdcSlSfxxDO.getJfsbm());
        List<BdcSlSfxxDO> bdcSlSfxxDOList = entityMapper.selectByExample(example);

        // 当收费信息不为空时 更新收费信息
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxx : bdcSlSfxxDOList) {
                bdcSlSfxx.setSfzt(bdcSlSfxxDO.getSfzt());
                bdcSlSfxx.setSfztczsj(bdcSlSfxxDO.getSfztczsj());
                bdcSlSfxx.setSfdwdm(bdcSlSfxxDO.getSfdwdm());
                bdcSlSfxx.setJkfs(bdcSlSfxxDO.getJkfs());
                bdcSlSfxx.setJypzh(bdcSlSfxxDO.getJypzh());
                entityMapper.updateByPrimaryKeySelective(bdcSlSfxx);
            }
            gxflag = true;
        }
        return gxflag;
    }

    @Override
    public Boolean gxSfxxJfsbm(String sfxxid, String qlrlb, String gzlslid) {
        CommonResponse response = this.tsSfxxAndGxSfxxJfsbm(sfxxid, qlrlb, gzlslid);
        return response.isSuccess();
    }

    private CommonResponse tsSfxxAndGxSfxxJfsbm(String sfxxid, String qlrlb, String gzlslid){
        CommonResponse commonResponse = new CommonResponse();
        boolean gxresult = false;
        if (StringUtils.isBlank(sfxxid)) {
            throw new AppException("收费信息不能为空");
        }
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例id不能为空");
        }
        // 查询收费信息
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
        // 查询收费项目
        List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        List<BdcQlrDO> bdcQlrDOList = new ArrayList<>();
        List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
        String zl = "";
        Set<String> zlSet = new TreeSet<>();
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                // 获取权利人信息
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcXmDO.getXmid());
                bdcQlrQO.setQlrlb(qlrlb);
                List<BdcQlrDO> bdcQlrList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                    bdcQlrDOList.addAll(bdcQlrList);
                }
                if (StringUtils.isNotBlank(bdcXmDO.getZl())) {
                    zlSet.add(bdcXmDO.getZl());
                }
            }
        } else {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                    if (StringUtils.isNotBlank(bdcSlXmDO.getZl())) {
                        zlSet.add(bdcSlXmDO.getZl());
                    }
                }
                //查询受理申请人
                bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDOList.get(0).getXmid(), qlrlb);
            }
        }

        // 处理坐落
        if (CollectionUtils.isNotEmpty(zlSet)) {
            zl = StringUtils.join(zlSet, CommonConstantUtils.ZF_YW_DH);
        }

        if (bdcSlSfxxDO != null && CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
            JfsxxRequestDTO jfsxxRequestDTO = new JfsxxRequestDTO();
            jfsxxRequestDTO.setTradeUniqueCode(sfxxid);
            jfsxxRequestDTO.setCoCode(bdcSlSfxxDO.getSfdwdm());
            jfsxxRequestDTO.setCoName(bdcSlSfxxDO.getSfdwmc());
            jfsxxRequestDTO.setExpDate("2099-01-01");
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    if (StringUtils.isNotBlank(bdcQlrDO.getDh())) {
                        jfsxxRequestDTO.setPayerMobile(bdcQlrDO.getDh());
                        break;
                    }
                }
            } else if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                    if (StringUtils.isNotBlank(bdcSlSqrDO.getDh())) {
                        jfsxxRequestDTO.setPayerMobile(bdcSlSqrDO.getDh());
                        break;
                    }
                }
            }
            jfsxxRequestDTO.setIncomeAmount(bdcSlSfxxDO.getHj());
            jfsxxRequestDTO.setPayerName(bdcSlSfxxDO.getJfrxm());

            List<JfsxxRequestJkxmDTO> jfsxxRequestJkxmDTOList = new ArrayList<>();
            StringBuilder sfgs = new StringBuilder("");
            for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                //如果收费项目数量为空或者数量为0不推送
                if (bdcSlSfxmDO.getSl() == null || bdcSlSfxmDO.getSl() == 0) {
                    continue;
                }
                JfsxxRequestJkxmDTO jfsxxRequestJkxmDTO = new JfsxxRequestJkxmDTO();
                // 获取对应收费项目单价
                List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzDOList = bdcSlSfxmPzFeignService.listBdcSlSfxmSfbzDO(bdcSlSfxmDO.getSfxmdm());
                if (CollectionUtils.isNotEmpty(bdcSlSfxmSfbzDOList)) {
                    for (BdcSlSfxmSfbzDO bdcSlSfxmSfbzDO : bdcSlSfxmSfbzDOList) {
                        if (StringUtils.equals(bdcSlSfxmDO.getSfxmbz(), bdcSlSfxmSfbzDO.getSfxmbz())) {
                            jfsxxRequestJkxmDTO.setAmount(Double.parseDouble(bdcSlSfxmSfbzDO.getSfxmdj()));
                            break;
                        }
                    }
                }
                // 补充收费项目信息
                jfsxxRequestJkxmDTO.setCode(bdcSlSfxmDO.getSfxmdm());
                jfsxxRequestJkxmDTO.setName(bdcSlSfxmDO.getSfxmmc());
                jfsxxRequestJkxmDTO.setUnits(bdcSlSfxmDO.getJedw());
                jfsxxRequestJkxmDTO.setCount(bdcSlSfxmDO.getSl());
                jfsxxRequestJkxmDTO.setTotalAmount(bdcSlSfxmDO.getSsje());

                sfgs.append(jfsxxRequestJkxmDTO.getCount())
                        .append("*")
                        .append(jfsxxRequestJkxmDTO.getAmount())
                        .append("=")
                        .append(jfsxxRequestJkxmDTO.getTotalAmount())
                        .append(",");
                // 将项目信息存入列表中
                jfsxxRequestJkxmDTOList.add(jfsxxRequestJkxmDTO);
            }
            if (StringUtils.isNotBlank(sfgs.toString())) {
                String sfgsStr = sfgs.toString();
                sfgsStr = sfgsStr.substring(0, sfgsStr.length() - 1);
                jfsxxRequestDTO.setRemark(sfgsStr + CommonConstantUtils.ZF_HH_CHAR + zl);
            }
            jfsxxRequestDTO.setItem(jfsxxRequestJkxmDTOList);
            UserDto userDto = getCurrentUser();
            if (userDto != null) {
                jfsxxRequestDTO.setCreater(userDto.getUsername());
            } else {
                jfsxxRequestDTO.setCreater(sfUsername);
            }
            // 调用创建缴费信息接口
            LOGGER.warn("sfxxid:{}南通市区推送缴费参数{}", sfxxid, JSON.toJSONString(jfsxxRequestDTO));
            String result = nantongFsxtzfFeignService.createBill(jfsxxRequestDTO);
            LOGGER.info("sfxxid:{}南通市区推送缴费接口结果：{}", sfxxid, result);
            if (StringUtil.isNotEmpty(result)) {
                String resultTemp = result.replaceAll("\\\\", "");
                if (resultTemp.length() > 2) {
                    result = resultTemp.substring(1, resultTemp.length() - 1);
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    if (StringUtil.isNotEmpty(jsonObject.getString("billno")) && StringUtil.isNotEmpty(jsonObject.getString("qrCodeUrl"))) {
                        String billno = jsonObject.getString("billno");
                        String qrCodeUrl = jsonObject.getString("qrCodeUrl");
                        //更新收费信息
                        bdcSlSfxxDO.setJfsbm(billno);
                        bdcSlSfxxDO.setJfsewmurl(qrCodeUrl);
                        entityMapper.updateByPrimaryKeySelective(bdcSlSfxxDO);

                        // 设置返回值信息
                        Map<String, String> resultMap = new HashMap<>(2);
                        resultMap.put("jfsewmurl", qrCodeUrl);
                        resultMap.put("jfsbm", billno);
                        commonResponse.setData(resultMap);
                    }
                    gxresult = true;
                }
            }
        }
        commonResponse.setSuccess(gxresult);
        return commonResponse;
    }

    @Override
    public List listSfxmmc() {
        return bdcSlSfxmPzMapper.querySfxmmc();
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 自动计算收费项目并保存相关数据
     */
    @Override
    public void autoCountSfxm(String gzlslid) throws Exception {
        int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        //证书生成数量
        Integer count;
        //工本费数量
        Integer gbfsl = 0;
        BdcXmQO bdcXmQO;
        List<BdcXmDO> bdcXmList = new ArrayList<>();
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                List<BdcSlSfxmDO> bdcSlSfxmList = new ArrayList<>();
                List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                //找到和当前收费信息所属项目同一个登记小类的所有项目（批量组合流程）
                if (CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
                    bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmid(bdcSlSfxxDO.getXmid());
                    List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                        bdcXmQO = new BdcXmQO();
                        bdcXmQO.setGzlslid(gzlslid);
                        bdcXmQO.setDjxl(bdcXmDOS.get(0).getDjxl());
                        bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    }
                } else if (CommonConstantUtils.LCLX_ZH.equals(lclx)) {
                    //组合流程获取对应收费信息对应的项目
                    bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmid(bdcSlSfxxDO.getXmid());
                    bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                } else {
                    bdcXmQO = new BdcXmQO();
                    bdcXmQO.setGzlslid(gzlslid);
                    bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                }
                if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {

                    for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                        //收取工本费时根据证书数量动态改变数量
                        //工本费第一次加载收费单页面的时候读取接口获取工本数量自动计算，加载完后保存到数据库，后续加载直接去读数据库
                        if (StringUtils.equals(bdcSlSfxmDO.getSfxmbz(), CommonConstantUtils.SFXM_BZ_GBF)) {
                            ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
                            if (processInstanceData != null) {
                                //获取当前权利人的信息
                                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                                bdcQlrQO.setXmid(bdcXmList.get(0).getXmid());
                                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                                if (CollectionUtils.isEmpty(bdcQlrDOList)) {
                                    bdcQlrDOList = Collections.emptyList();
                                }
                                Integer sffbcz = 0;
                                String djxl = "";
                                String djyy = "";
                                if (CollectionUtils.isNotEmpty(bdcXmList)) {
                                    sffbcz = bdcXmList.get(0).getSqfbcz();
                                    djxl = bdcXmList.get(0).getDjxl();
                                    djyy = bdcXmList.get(0).getDjyy();
                                }
                                if (hzlcdyidList.contains(processInstanceData.getProcessDefinitionKey()) || ysbzdyidList.contains(processInstanceData.getProcessDefinitionKey())) {
                                    //换证流程按照证书数量收费//遗失补证流程按照证书数量收费
                                    count = bdcZsInitFeignService.initLcBdcqzSl(gzlslid);
                                    gbfsl = count;
                                } else if (CollectionUtils.isNotEmpty(bdcXmList) && clfmmzydjxlList.contains(djxl) && CollectionUtils.isNotEmpty(tssfdjyyList) && tssfdjyyList.contains(djyy)) {
                                    //存量房买卖转移登记，登记原因为权利份额转移的，收费不收登记费，只收工本费，第一本不免费
                                    //计算工本费
                                    if (CommonConstantUtils.SF_F_DM.equals(sffbcz)) {
                                        //没有分别持证都是只出一本证,收一本费用
                                        gbfsl = 1;
                                    }
                                    if (CommonConstantUtils.SF_S_DM.equals(sffbcz)) {
                                        //单个和批量流程统一计算公式为(权利人数量)*项目数量，不区分收一本证还是收多本证，第一本不免费
                                        gbfsl = bdcQlrDOList.size() * bdcXmList.size();
                                    }
                                } else {
                                    //计算工本费
                                    if (CommonConstantUtils.SF_F_DM.equals(sffbcz)) {
                                        //没有分别持证都是只出一本证，不收费
                                        gbfsl = 0;
                                    }
                                    if (CommonConstantUtils.SF_S_DM.equals(sffbcz)) {
                                        //获取受理项目表的zsxh字段值,为空说明是多本证,不为空说明是一本证,计算方法是(权利人数量-1)* 生成几本证---逻辑已调整
                                        //单个和批量流程统一计算公式为(权利人数量-1)*项目数量，不区分收一本证还是收多本证
                                        gbfsl = (bdcQlrDOList.size() - 1) * bdcXmList.size();
                                    }
                                }
                            }
                            //数据库为空，读取gbfsl，否则读取数据库值，最小为0
                            if (bdcSlSfxmDO.getSl() == null) {
                                bdcSlSfxmDO.setSl((double) (gbfsl >= 0 ? gbfsl : 0));
                                bdcSlSfxmDO.setYsje(bdcSlSfxmDO.getSl() * 10);
                                bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getSl() * 10);
                                bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
                            }
                        }
                        //计算登记住宅费
                        //sf_xm_pz配置表需要两种登记费都配置，如果有一个收费项目的应收金额为空说明没有计算该登记费，删掉
                        if (StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), CommonConstantUtils.SFXM_BZ_DJF_DM)) {
                            //存量房买卖转移登记登记原因为"权利份额转移"不收登记费，原来项目中的全部删除
                            if (CollectionUtils.isNotEmpty(bdcXmList) && clfmmzydjxlList.contains(bdcXmList.get(0).getDjxl()) && CollectionUtils.isNotEmpty(tssfdjyyList) && tssfdjyyList.contains(bdcXmList.get(0).getDjyy())) {
                                bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
                            } else {
                                //组合流程用xmid关联
                                //组合流程
                                if (CommonConstantUtils.LCLX_ZH.equals(lclx)) {
                                    bdcSlSfxmDO = countDjf(bdcSlSfxmDO, bdcXmList, true, false, "");
                                    if (bdcSlSfxmDO.getYsje() == null) {
                                        bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
                                    }
                                } else {
                                    //单个和批量流程//批量组合流程
                                    bdcSlSfxmDO = countDjf(bdcSlSfxmDO, bdcXmList, false, false, "");
                                    if (bdcSlSfxmDO.getYsje() == null) {
                                        bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
                                    }
                                }
                            }
                        }
                        if (bdcSlSfxmDO.getYsje() != null) {
                            bdcSlSfxmList.add(bdcSlSfxmDO);
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(bdcSlSfxmList) && bdcSlSfxmDOList.size() > 1) {
                    //根据收费项目标准分组处理，分组后的收费项目如果数量大于1，只保留一条数据，其余删除
                    bdcSlSfxmList = bdcSlSfxmList.stream().filter(bdcSlSfxmDO -> StringUtils.isNotBlank(bdcSlSfxmDO.getSfxmbz())).collect(Collectors.toList());
                    Map<String, List<BdcSlSfxmDO>> sfxmMap = bdcSlSfxmList.stream().collect(Collectors.groupingBy(BdcSlSfxmDO::getSfxmbz));
                    for (Map.Entry<String, List<BdcSlSfxmDO>> entry : sfxmMap.entrySet()) {
                        if (entry.getValue().size() > 1) {
                            for (int i = 0; i < entry.getValue().size(); i++) {
                                if (i > 0) {
                                    bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(entry.getValue().get(i).getSfxmid());
                                }
                            }
                        }
                    }
                }
                bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());

                Double hj = bdcSlSfxmDOList.stream().filter(sfxm -> sfxm.getSsje() != null).map(t -> new BigDecimal(String.valueOf(t.getSsje())))
                        .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
                bdcSlSfxxDO.setHj(hj);
                //如果合计为0，认定为不收费，改变sfzt,盐城不改变
                if (Objects.equals(0d, hj) && isBsf) {
                    bdcSlSfxxDO.setSfzt(BdcSfztEnum.BSF.key());
                }
                //处理缴款人
                if (CollectionUtils.isNotEmpty(bdcXmList)) {
                    //判断缴款人是否发生改变
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setXmid(bdcXmList.get(0).getXmid());
                    String yjfrxm = bdcSlSfxxDO.getJfrxm();
                    if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfxxDO.getQlrlb())) {
                        bdcSlSfxxDO.setJfrxm(bdcXmList.get(0).getQlr());
                        if (StringUtils.isBlank(bdcSlSfxxDO.getJfrdh())) {
                            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                            List<BdcQlrDO> bdcQlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                            bdcSlSfxxDO.setJfrdh(getQlrDhAndZjh(bdcQlrDoList).getDh());
                        }
                    } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfxxDO.getQlrlb())) {
                        bdcSlSfxxDO.setJfrxm(bdcQlrFeignService.queryQlrsYbzs(gzlslid, CommonConstantUtils.QLRLB_YWR, bdcXmList.get(0).getDjxl()));
                        if (StringUtils.isBlank(bdcSlSfxxDO.getJfrdh())) {
                            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                            List<BdcQlrDO> bdcQlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                            bdcSlSfxxDO.setJfrdh(getQlrDhAndZjh(bdcQlrDoList).getDh());
                        }
                    }
                    // 设置是否月结
                    if ((null == bdcSlSfxxDO.getSfyj() || (StringUtils.isNotBlank(yjfrxm) && !StringUtils.equals(yjfrxm, bdcSlSfxxDO.getJfrxm())))) {
                        bdcSlSfxxDO.setSfyj(this.querySfyjByJfrxm(bdcSlSfxxDO.getJfrxm(), bdcXmList.get(0).getGzldyid()));
                    }
                }
                if (Objects.isNull(bdcSlSfxxDO.getSfsj())) {
                    bdcSlSfxxDO.setSfsj(new Date());
                }
                bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
            }
        }
    }

    @Override
    public void autoCountSfxxSfxm(@NotBlank(message = "工作流实例id不可为空") String gzlslid) throws Exception {
        if (StringUtils.isNotBlank(gzlslid)) {
            Integer lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    BdcSfxmJsQO bdcSfxmJsQO = new BdcSfxmJsQO();
                    bdcSfxmJsQO.setSfxxid(bdcSlSfxxDO.getSfxxid());
                    bdcSfxmJsQO.setGzlslid(gzlslid);
                    bdcSfxmJsQO.setSfcxjs(false);
                    bdcSfxmJsQO.setSfgxbczt(true);
                    if (Objects.equals(CommonConstantUtils.LCLX_ZH, lclx)) {
                        bdcSfxmJsQO.setXmid(bdcSlSfxxDO.getXmid());
                        countSfxm(bdcSfxmJsQO);
                    } else {
                        countSfxm(bdcSfxmJsQO);
                    }
                }
            }
        }

    }

    @Override
    public void autoCountSfxxSfxmForZf(String gzlslid) throws Exception {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {

                Integer lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if (!canRecount(bdcSlSfxxDO)) {
                        continue;
                    }
                    BdcSfxmJsQO bdcSfxmJsQO = new BdcSfxmJsQO();
                    bdcSfxmJsQO.setSfxxid(bdcSlSfxxDO.getSfxxid());
                    bdcSfxmJsQO.setGzlslid(gzlslid);
                    bdcSfxmJsQO.setSfcxjs(false);
                    bdcSfxmJsQO.setSfgxbczt(true);
                    if (Objects.equals(CommonConstantUtils.LCLX_ZH, lclx) || Objects.equals(CommonConstantUtils.LCLX_PLZH, lclx)) {
                        bdcSfxmJsQO.setXmid(bdcSlSfxxDO.getXmid());
                        countSfxm(bdcSfxmJsQO);
                    } else {
                        countSfxm(bdcSfxmJsQO);
                    }
                    if (sfgltdsyj) {
                        //单独处理土地交易的费用
                        List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                        if (StringUtils.isNotBlank(tdsyqsfxmdm) && CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                            bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> bdcSlSfxmDO.getSfxmdm().equals(tdsyqsfxmdm)).collect(Collectors.toList());
                            countTdsyqFwf(bdcSlSfxmDOList, gzlslid, false);
                        }
                    }

                }
            }


        }

    }

    // 判断收费信息是否能够重新计算
    private boolean canRecount(BdcSlSfxxDO bdcSlSfxxDO) {
        boolean canRecount = true;
        if (Objects.nonNull(bdcSlSfxxDO)) {
            if (CommonConstantUtils.SFZT_YJF.equals(bdcSlSfxxDO.getSfzt()) || StringUtils.isNotBlank(bdcSlSfxxDO.getJfsewmurl())) {
                canRecount = false;
            }
            if (CommonConstantUtils.SF_S_DM.equals(bdcSlSfxxDO.getBczt())) {
                canRecount = false;
            }
            List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
            if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                    if (CommonConstantUtils.SFZT_YJF.equals(bdcSlSfxmDO.getSfzt()) || StringUtils.isNotBlank(bdcSlSfxmDO.getJfsewmurl())) {
                        canRecount = false;
                        break;
                    }
                }
            }
        }
        return canRecount;
    }

    /*
     * 处理收费单页面收费项目自动计算信息
     * */
    @Override
    public List<BdcSlSfxmDO> countSfxm(BdcSfxmJsQO bdcSfxmJsQO) throws Exception {
        String xmid = bdcSfxmJsQO.getXmid();
        String gzlslid = bdcSfxmJsQO.getGzlslid();
        String sfxxid = bdcSfxmJsQO.getSfxxid();
        Boolean sfcxjs = bdcSfxmJsQO.getSfcxjs() != null && bdcSfxmJsQO.getSfcxjs();
        List<BdcSlSfxmDO> bdcSlSfxmDOList;
        List<BdcSlSfxmDO> bdcSlSfxmList = new ArrayList<>();
        List<BdcXmDO> bdcXmList;
        if (StringUtils.isNotBlank(xmid)) {
            //组合流程用xmid查询
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            //判断是否批量组合
            int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
            if (lclx == 4) {
                //批量组合流程,工本费需乘以当前项目对应登记小类的项目数量
                bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                bdcXmQO.setDjxl(bdcXmList.get(0).getDjxl());
                bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            }
        } else {
            //单个批量流程用gzlslid
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            //---盐城房屋性质为经济适用房，删除所有收费项目
            bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmList.get(0).getXmid());
            if (MapUtils.isNotEmpty(bdcSlSfxmConfig.getNosfxm()) && CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    if (MapUtils.getString(bdcSlSfxmConfig.getNosfxm(), String.valueOf(bdcFdcqDO.getFwxz()), StringUtils.EMPTY).contains(bdcXmList.get(0).getGzldyid())) {
                        bdcSlSfxmService.deleteBdcSlSfxmBySfxxid(sfxxid);
                        return Collections.emptyList();
                    }
                }
            }
        }
        bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
        if (sfgltdsyj) {
            //过滤掉土地收益金的信息
            bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> !StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
        }
        if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList) && CollectionUtils.isNotEmpty(bdcXmList)) {
            for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                //收取工本费时根据证书数量动态改变数量
                //工本费第一次加载收费单页面的时候读取接口获取工本数量自动计算，加载完后保存到数据库，后续加载直接去读数据库
                if (StringUtils.equals(bdcSlSfxmDO.getSfxmbz(), CommonConstantUtils.SFXM_BZ_GBF) && (bdcSlSfxmDO.getSl() == null || sfcxjs)) {
                    /*
                     * 计算工本费，一般是第一本默认不收费，证书数量加一本收一本的费用
                     * */
                    queryGbfSl(bdcXmList, bdcSlSfxmDO, xmid, gzlslid, sfcxjs);
                }
                //计算登记住宅费
                //sf_xm_pz配置表需要两种登记费都配置，如果有一个收费项目的应收金额为空说明没有计算该登记费，删掉
                //sfxmdm是登记费的或者标准包含 登记费三个字的
                if ((StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), CommonConstantUtils.SFXM_BZ_DJF_DM) || StringUtils.contains(bdcSlSfxmDO.getSfxmbz(), "登记费")) && (bdcSlSfxmDO.getYsje() == null || sfcxjs)) {
                    //存量房买卖转移登记登记原因为"权利份额转移"不收登记费，原来项目中的全部删除
                    if (CollectionUtils.isNotEmpty(bdcXmList) && clfmmzydjxlList.contains(bdcXmList.get(0).getDjxl()) && CollectionUtils.isNotEmpty(tssfdjyyList) && tssfdjyyList.contains(bdcXmList.get(0).getDjyy())) {
                        bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
                    } else {
                        BdcSlSfxmsDTO bdcSlSfxmsDTO = new BdcSlSfxmsDTO();
                        bdcSlSfxmsDTO.setBdcSlSfxmDO(bdcSlSfxmDO);
                        bdcSlSfxmsDTO.setBdcXmDOList(bdcXmList);
                        //组合流程用xmid关联
                        if (StringUtils.isNotBlank(xmid) && bdcXmList.size() <= 1) {
                            //单个组合
                            bdcSlSfxmsDTO.setSfzhlc(true);
                        } else {
                            //单个和批量流程,批量组合
                            bdcSlSfxmsDTO.setSfzhlc(false);
                        }
                        bdcSlSfxmsDTO.setSfcxjs(sfcxjs);
                        bdcSlSfxmsDTO.setVersion(dataversion);
                        bdcSlSfxmDO = countDjf(bdcSlSfxmsDTO.getBdcSlSfxmDO(), bdcSlSfxmsDTO.getBdcXmDOList(), bdcSlSfxmsDTO.getSfzhlc(), bdcSlSfxmsDTO.getSfcxjs(), bdcSlSfxmsDTO.getVersion());
                        if (bdcSlSfxmDO.getYsje() == null) {
                            bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
                        }
                    }
                }

                // 优惠信息为全免时，不删除收费金额为0 的收费项目
                if (delete0Jexm && Objects.nonNull(bdcSlSfxmDO.getYsje()) && bdcSlSfxmDO.getYsje() == 0
                        && !Objects.equals(bdcSlSfxmDO.getYh(), 3)) {
                    bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
                } else {
                    if (bdcSlSfxmDO.getYsje() != null) {
                        bdcSlSfxmList.add(bdcSlSfxmDO);
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcSlSfxmList) && bdcSlSfxmDOList.size() > 1) {
            //根据收费项目标准分组处理，分组后的收费项目如果数量大于1，只保留一条数据，其余删除
            bdcSlSfxmList = bdcSlSfxmList.stream().filter(bdcSlSfxmDO -> StringUtils.isNotBlank(bdcSlSfxmDO.getSfxmbz())).collect(Collectors.toList());
            Map<String, List<BdcSlSfxmDO>> sfxmMap = bdcSlSfxmList.stream().collect(Collectors.groupingBy(BdcSlSfxmDO::getSfxmbz));
            for (Map.Entry<String, List<BdcSlSfxmDO>> entry : sfxmMap.entrySet()) {
                if (entry.getValue().size() > 1) {
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        if (i > 0) {
                            bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(entry.getValue().get(i).getSfxmid());
                        }
                    }
                }
            }
        }
        bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
        Double hj = bdcSlSfxmDOList.stream().filter(sfxm -> null != sfxm.getSsje()).map(t -> new BigDecimal(String.valueOf(t.getSsje())))
                .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
        bdcSlSfxxDO.setHj(hj);
        //如果合计为0，认定为不收费，改变sfzt
        if (Objects.equals(0d, hj) && isBsf) {
            bdcSlSfxxDO.setSfzt(BdcSfztEnum.BSF.key());
        }
        if (sfgltdsyj) {
            bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> !StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
        }
        //处理缴款人
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            //判断缴款人是否发生改变
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXmList.get(0).getXmid());
            String yjfrxm = bdcSlSfxxDO.getJfrxm();
            if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfxxDO.getQlrlb())) {
                bdcSlSfxxDO.setJfrxm(bdcXmList.get(0).getQlr());
                if (StringUtils.isBlank(bdcSlSfxxDO.getJfrdh())) {
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    List<BdcQlrDO> bdcQlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    bdcSlSfxxDO.setJfrdh(getQlrDhAndZjh(bdcQlrDoList).getDh());
                }
            } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfxxDO.getQlrlb())) {
                bdcSlSfxxDO.setJfrxm(bdcQlrFeignService.queryQlrsYbzs(gzlslid, CommonConstantUtils.QLRLB_YWR, bdcXmList.get(0).getDjxl()));
                if (StringUtils.isBlank(bdcSlSfxxDO.getJfrdh())) {
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                    List<BdcQlrDO> bdcQlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    bdcSlSfxxDO.setJfrdh(getQlrDhAndZjh(bdcQlrDoList).getDh());
                }
            }
            // 设置是否月结
            if ((null == bdcSlSfxxDO.getSfyj() || (StringUtils.isNotBlank(yjfrxm) && !StringUtils.equals(yjfrxm, bdcSlSfxxDO.getJfrxm())))) {
                bdcSlSfxxDO.setSfyj(querySfyjByJfrxm(bdcSlSfxxDO.getJfrxm(), bdcXmList.get(0).getGzldyid()));
            }
        }
        if (Objects.isNull(bdcSlSfxxDO.getSfsj())) {
            bdcSlSfxxDO.setSfsj(new Date());
        }
        if (Boolean.TRUE.equals(bdcSfxmJsQO.getSfgxbczt())) {
            bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
        }
        return bdcSlSfxmDOList;
    }

    /*
     * 计算常州土地使用权服务费
     * */

    private List<BdcSlSfxmDO> countTdsyqFwf(List<BdcSlSfxmDO> bdcSlSfxmDOList, String gzlslid, boolean sfcxjs) {
        //常州计算土地使用权服务费
        List<BdcSlSfxmDO> newSlSfxmList = new ArrayList<>();
        if (CollectionUtils.isEmpty(bdcSlSfxmDOList)) {
            return newSlSfxmList;

        }
//        BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmDOList.get(0);
        //重新计算时删除前面的土地交易收费项目数据
        //其实这边收费信息id就一个
        String sfxxid = bdcSlSfxmDOList.get(0).getSfxxid();
        String sfxmdm = bdcSlSfxmDOList.get(0).getSfxmdm();
        if (sfcxjs) {
            for (BdcSlSfxmDO bdcSlSfxm : bdcSlSfxmDOList) {
                bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(bdcSlSfxm.getSfxmid());
            }
            String djxl = "";
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
            if (Objects.nonNull(bdcSlSfxxDO)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                if (StringUtils.isNotBlank(bdcSlSfxxDO.getXmid())) {
                    bdcXmQO.setXmid(bdcSlSfxxDO.getXmid());
                }
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    djxl = bdcXmDOList.get(0).getDjxl();
                }
            }

            if (StringUtils.isNotBlank(djxl)) {
                //删除后根据配置重新生成土地收费信息
                List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList = bdcSlSfxmPzFeignService.listBdcSlSfxmPzByDjxl(djxl);
                if (CollectionUtils.isNotEmpty(bdcSlSfxmPzDOList)) {
                    //只查询土地服务的配置
                    List<BdcSlSfxmPzDO> qlrSfxmPzDOList = bdcSlSfxmPzDOList.stream().filter(bdcSlSfxmPzDO -> bdcSlSfxmPzDO.getSfxmdm().equals(sfxmdm) && StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfxmPzDO.getQlrlb())).collect(Collectors.toList());
                    List<BdcSlSfxmPzDO> ywrSfxmPzDOList = bdcSlSfxmPzDOList.stream().filter(bdcSlSfxmPzDO -> bdcSlSfxmPzDO.getSfxmdm().equals(sfxmdm) && StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfxmPzDO.getQlrlb())).collect(Collectors.toList());

                    if (StringUtils.isNotBlank(sfxxid)) {
                        if (Objects.nonNull(bdcSlSfxxDO)) {
                            if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfxxDO.getQlrlb())) {
                                bdcSlSfxmService.listCshBdcSlSfxm(qlrSfxmPzDOList, sfxxid);
                            } else {
                                bdcSlSfxmService.listCshBdcSlSfxm(ywrSfxmPzDOList, sfxxid);
                            }
                        }
                    }
                }
            } else {
                LOGGER.error("当前流程未找到登记小类工作流实例id{}", gzlslid);
            }

        }
        //重新查询收费项目
        List<BdcSlSfxmDO> bdcSlSfxmDOS = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
        bdcSlSfxmDOS = bdcSlSfxmDOS.stream().filter(bdcSlSfxmDO -> bdcSlSfxmDO.getSfxmdm().equals(tdsyqsfxmdm)).collect(Collectors.toList());

        for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOS) {
            if (Objects.isNull(bdcSlSfxmDO.getYsje()) || sfcxjs) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                List<BdcXmAndFbDTO> bdcXmAndFbDTOList = bdcXmFeignService.listBdcXmAndFb(bdcXmQO);
                //根据宗地数量去重
                bdcXmAndFbDTOList = bdcXmAndFbDTOList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getBdcdyh().substring(0, 19)))), ArrayList::new));

                if (CollectionUtils.isNotEmpty(bdcXmAndFbDTOList)) {
                    BdcXmAndFbDTO xmxx = bdcXmAndFbDTOList.get(0);
                    // 判断是否产权
                    if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, xmxx.getQllx())) {
                        //计算逻辑
                        for (int i = 0; i < bdcXmAndFbDTOList.size(); i++) {
                            double tdsyqjyf = 0.00;
                            BdcXmAndFbDTO bdcXmAndFbDTO = bdcXmAndFbDTOList.get(i);
                            if (Objects.nonNull(bdcXmAndFbDTO.getDdjb()) && Objects.nonNull(bdcSlSfxmDO.getSfxmdj()) && ArrayUtils.contains(bdcSlSfxmDO.getDdjb().split(CommonConstantUtils.ZF_YW_DH), String.valueOf(bdcXmAndFbDTO.getDdjb())) && Objects.nonNull(bdcXmAndFbDTO.getZdzhmj())) {
                                tdsyqjyf = NumberUtil.multiply(bdcXmAndFbDTO.getZdzhmj(), bdcSlSfxmDO.getSfxmdj(), 4);
                                bdcSlSfxmDO.setYsje(tdsyqjyf);
                                bdcSlSfxmDO.setSsje(tdsyqjyf);
                                bdcSlSfxmDO.setSl(bdcXmAndFbDTO.getZdzhmj());
                                if (i == 0) {
                                    //第一条数据直接更新收费项目，如果有其他不同地段级别的，新增收费项目服务费
                                    bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
                                    newSlSfxmList.add(bdcSlSfxmDO);
                                } else {
                                    BdcSlSfxmDO bdcSlSfxm = new BdcSlSfxmDO();
                                    BeanUtils.copyProperties(bdcSlSfxmDO, bdcSlSfxm);
                                    bdcSlSfxm.setSfxmid(UUIDGenerator.generate16());
                                    bdcSlSfxmService.insertBdcSlSfxm(bdcSlSfxm);
                                    newSlSfxmList.add(bdcSlSfxm);
                                }
                            }
                        }
                    }

                    //土地使用权抵押权收费
                    if (CommonConstantUtils.QLLX_DYAQ_DM.equals(xmxx.getQllx())) {
                        //获取宗地数量，根据前19位地籍号去重
                        for (int i = 0; i < bdcXmAndFbDTOList.size(); i++) {
                            BdcXmAndFbDTO bdcXmAndFbDTO = bdcXmAndFbDTOList.get(i);
                            //根据项目id获取权利
                            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmAndFbDTO.getXmid());
                            if (bdcQl instanceof BdcDyaqDO) {
                                BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                                double dyajg = Objects.nonNull(bdcDyaqDO.getBdbzzqse()) ? bdcDyaqDO.getBdbzzqse() : (Objects.nonNull(bdcDyaqDO.getZgzqe()) ? bdcDyaqDO.getZgzqe() : 0);
                                if (Objects.nonNull(dyajg)) {
                                    double tdsyqdyaf = 0.00;
                                    if (dyajg <= 200) {
                                        tdsyqdyaf = 1000.00;
                                    } else if (200 < dyajg && dyajg <= 500) {
                                        tdsyqdyaf = 2000.00;
                                    } else if (dyajg > 500 && dyajg <= 1000) {
                                        tdsyqdyaf = 3000.00;
                                    } else if (dyajg > 1000 && dyajg <= 3000) {
                                        tdsyqdyaf = 4000.00;
                                    } else if (dyajg > 3000) {
                                        tdsyqdyaf = 10000.00;
                                    }
                                    if(sfczxtqlrywr(bdcXmAndFbDTO)){
                                        tdsyqdyaf = new BigDecimal(tdsyqdyaf).multiply(new BigDecimal(0.25)).doubleValue();
                                    }
                                    bdcSlSfxmDO.setYsje(tdsyqdyaf);
                                    bdcSlSfxmDO.setSsje(tdsyqdyaf);
                                }
                            }
                            bdcSlSfxmDO.setSl(1.00);
                            bdcSlSfxmDO.setSfxmbz(String.valueOf(bdcSlSfxmDO.getYsje()));
                            if (i == 0 && !sfcxjs) {
                                bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
                                newSlSfxmList.add(bdcSlSfxmDO);
                            } else {
                                BdcSlSfxmDO bdcSlSfxm = new BdcSlSfxmDO();
                                BeanUtils.copyProperties(bdcSlSfxmDO, bdcSlSfxm);
                                bdcSlSfxm.setSfxmid(UUIDGenerator.generate16());
                                bdcSlSfxmService.insertBdcSlSfxm(bdcSlSfxm);
                                newSlSfxmList.add(bdcSlSfxm);
                            }
                        }
                    }
                }
                countHj(gzlslid);
            } else {
                newSlSfxmList.addAll(bdcSlSfxmDOList);
            }
        }
        //最后计算到应收金额为空的删除
        //过滤应收金额为0 的收费项目
        List<BdcSlSfxmDO> allSfxmList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
        List<BdcSlSfxmDO> tdsyjSfxmList = allSfxmList.stream().filter(bdcSlSfxmDO -> StringUtils.equals(tdsyqsfxmdm, bdcSlSfxmDO.getSfxmdm())).collect(Collectors.toList());
        List<BdcSlSfxmDO> deleteSfxmList = tdsyjSfxmList.stream().filter(bdcSlSfxmDO -> (Objects.isNull(bdcSlSfxmDO.getYsje()) || Objects.equals(0.00, bdcSlSfxmDO.getYsje()))).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(deleteSfxmList)) {
            for (BdcSlSfxmDO deleteSfxm : deleteSfxmList) {
                bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(deleteSfxm.getSfxmid());
            }
            tdsyjSfxmList.removeAll(deleteSfxmList);
        }
        return tdsyjSfxmList;
    }

    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @param bdcXmAndFbDTO
    * @return
    * @description  是否存在相同权利人义务人的抵押权
    **/
    private boolean sfczxtqlrywr(BdcXmAndFbDTO bdcXmAndFbDTO){
        boolean sfcz = false;
        List qszts = new ArrayList(2);
        qszts.add(CommonConstantUtils.QSZT_VALID);
        qszts.add(CommonConstantUtils.QSZT_HISTORY);
        List<BdcQl> dyaqList = bdcDjbxxFeignService.listBdcQlxx(bdcXmAndFbDTO.getBdcdyh(), CommonConstantUtils.QLLX_DYAQ_DM.toString(), qszts);
        if(CollectionUtils.isNotEmpty(dyaqList)){
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXmAndFbDTO.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
            List<BdcQlrDO> bdcYwrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            for (BdcQl bdcQl : dyaqList) {
                BdcQlrQO yBdcQlrQO = new BdcQlrQO();
                yBdcQlrQO.setXmid(bdcQl.getXmid());
                yBdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                //原权利人
                List<BdcQlrDO> yBdcQlrDOList = bdcQlrFeignService.listBdcQlr(yBdcQlrQO);
                //原义务人
                yBdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                List<BdcQlrDO> yBdcYwrDOList = bdcQlrFeignService.listBdcQlr(yBdcQlrQO);
                if(compareQlr(bdcQlrDOList,yBdcQlrDOList) && compareQlr(bdcYwrDOList,yBdcYwrDOList)){
                    sfcz= true;
                    break;
                }

            }
        }
        return sfcz;
    }

    /**
     * 比较权利人集合的数据是否完全一致，根据权利人名称相同则认为两个BdcQlrDO相同
     *
     * @param qlr1 权利人
     * @param qlr2 权利人
     * @return true 一致 false 不一致
     */
    private boolean compareQlr(List<BdcQlrDO> qlr1, List<BdcQlrDO> qlr2) {
        boolean result = true;
        Map<String, Integer> map = new HashMap<>();
        // 将List1元素放入Map，计数1
        for (BdcQlrDO bdcQlrDO : qlr1) {
            String key = bdcQlrDO.getQlrmc();
            map.put(key, 1);
        }
        // 遍历List2，在Map中查找List2的元素，找到则计数+1；未找到则放入map，计数1
        for (BdcQlrDO bdcQlrDO : qlr2) {
            String key = bdcQlrDO.getQlrmc();
            Integer count = map.get(key);
            if (count != null) {
                map.put(key, ++count);
                continue;
            }
            map.put(key, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                result = false;
                break;
            }
        }
        return result;
    }

    public void countHj(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                    double hj = bdcSlSfxmDOList.stream().filter(sfxm -> null != sfxm.getSsje()).map(t -> new BigDecimal(String.valueOf(t.getSsje())))
                            .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
                    bdcSlSfxxDO.setHj(hj);
//                    LOGGER.info("合计{}", hj);
                }
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                }
            }
        }
    }

    /*
     * 获取工本费数量
     * */
    private void queryGbfSl(List<BdcXmDO> bdcXmList, BdcSlSfxmDO bdcSlSfxmDO, String xmid, String gzlslid, boolean sfcxjs) throws Exception {
        //证书生成数量
        Integer count;
        //工本费数量
        Integer gbfsl = 0;
        BdcSlSfxmSlDTO bdcSlSfxmSlDTO = queryYcDjfSl(bdcXmList);
        LOGGER.info("bdcSlSfxmSlDTO:{},xmid:{}", bdcSlSfxmSlDTO, xmid);
        if (StringUtils.equals(dataversion, CommonConstantUtils.SYSTEM_VERSION_YC) && bdcSlSfxmSlDTO.getSftssfxm()) {
            // 特殊收费根据登记原因获取sfxm
            List<BdcSlSfxmDO> bdcSlSfxmlist = bdcSlSfxmService.listBdcSlSfxmBySfxxidAndDjyy(bdcSlSfxmDO.getSfxxid(), bdcXmList.get(0).getDjyy());
            if (CollectionUtils.isNotEmpty(bdcSlSfxmlist)) {
                bdcSlSfxmDO.setSfxmdm(bdcSlSfxmlist.get(0).getSfxmdm());
                bdcSlSfxmDO.setSfxmbz(bdcSlSfxmlist.get(0).getSfxmbz());
            }
        }
        if (Objects.nonNull(bdcSlSfxmSlDTO) && bdcSlSfxmSlDTO.getSfyydj() && (Objects.isNull(bdcSlSfxmSlDTO.getQtdjfsl()) || Objects.equals(0, bdcSlSfxmSlDTO.getQtdjfsl()))) {
            //如果异议登记,且只有住宅或者非住宅，不再收取工本费，直接删除
            bdcSlSfxmService.deleteBdcSlSfxmBySfxmid(bdcSlSfxmDO.getSfxmid());
        } else {
            //组合流程两个单独的收费根据xmid查对应的收费信息,分别计算数量
            if (StringUtils.isNotBlank(xmid)) {
                count = bdcZsInitFeignService.initXmBdcqzSl(xmid);
                gbfsl = count - 1;
                if (CollectionUtils.isNotEmpty(bdcXmList) && clfmmzydjxlList.contains(bdcXmList.get(0).getDjxl()) && CollectionUtils.isNotEmpty(tssfdjyyList) && tssfdjyyList.contains(bdcXmList.get(0).getDjyy())) {
                    //存量房买卖转移登记，登记原因为权利份额转移的，收费不收登记费，只收工本费，第一本不免费
                    gbfsl = count;
                }
                //考虑批量组合
                gbfsl = gbfsl * bdcXmList.size();
            } else {
                String gzldyid = bdcXmList.get(0).getGzldyid();
                if (StringUtils.isBlank(gzldyid)) {
                    ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
                    if (processInstanceData != null) {
                        gzldyid = processInstanceData.getProcessDefinitionKey();
                    }
                }
                if (StringUtils.isNotBlank(gzldyid)) {
                    //获取当前权利人的信息
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setXmid(bdcXmList.get(0).getXmid());
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    if (CollectionUtils.isEmpty(bdcQlrDOList)) {
                        bdcQlrDOList = new ArrayList<>();
                    }
                    Integer sffbcz = 0;
                    String djxl = "";
                    String djyy = "";
                    if (CollectionUtils.isNotEmpty(bdcXmList)) {
                        sffbcz = bdcXmList.get(0).getSqfbcz();
                        djxl = bdcXmList.get(0).getDjxl();
                        djyy = bdcXmList.get(0).getDjyy();
                    }
                    if (hzlcdyidList.contains(gzldyid) || ysbzdyidList.contains(gzldyid)) {
                        /**
                         * @author <a href = "mailto:gaolining@gtmap.cn" > gaolining </a >
                         *@description 内容变更，遗失补证换证流程都是从第一本就开始收费
                         *@date :2019 / 12 / 5 10:53
                         */
                        count = bdcZsInitFeignService.initLcBdcqzSl(gzlslid);
                        gbfsl = count;
                    } else if (CollectionUtils.isNotEmpty(bdcXmList) && clfmmzydjxlList.contains(djxl) && CollectionUtils.isNotEmpty(tssfdjyyList) && tssfdjyyList.contains(djyy)) {
                        //存量房买卖转移登记，登记原因为权利份额转移的，收费不收登记费，只收工本费，第一本不免费
                        //计算工本费
                        if (CommonConstantUtils.SF_F_DM.equals(sffbcz)) {
                            //没有分别持证都是只出一本证,收一本费用
                            gbfsl = 1;
                        }
                        if (CommonConstantUtils.SF_S_DM.equals(sffbcz)) {
                            //单个和批量流程统一计算公式为(权利人数量)*项目数量，不区分收一本证还是收多本证，第一本不免费
                            gbfsl = bdcQlrDOList.size() * bdcXmList.size();
                        }
                    } else if (zjdlcdyidList.contains(gzldyid)) {
                        //宅基地相关流程
                        if (CommonConstantUtils.SF_F_DM.equals(sffbcz)) {
                            gbfsl = 1;
                        }
                        if (CommonConstantUtils.SF_S_DM.equals(sffbcz)) {
                            //分别持证计算公式为((权利人数量)-1)*项目数量，不区分收一本证还是收多本证，第一本不免费
                            gbfsl = (bdcQlrDOList.size() - 1) * bdcXmList.size();
                        }
                    } else {
                        //计算工本费
                        if (CommonConstantUtils.SF_F_DM.equals(sffbcz)) {
                            //没有分别持证都是只出一本证，不收费
                            gbfsl = 0;
                        }
                        if (CommonConstantUtils.SF_S_DM.equals(sffbcz)) {
                            //获取受理项目表的zsxh字段值,为空说明是多本证,不为空说明是一本证,计算方法是(权利人数量-1)* 生成几本证---逻辑已调整
                            //单个和批量流程统一计算公式为(权利人数量-1)*项目数量，不区分收一本证还是收多本证
                            gbfsl = (bdcQlrDOList.size() - 1) * bdcXmList.size();
                        }
                        if (CommonConstantUtils.SF_S_DM.equals(sffbcz) && StringUtils.equals(dataversion, CommonConstantUtils.SYSTEM_VERSION_YC) && bdcSlSfxmSlDTO.getSftssfxm()) {
                            // 盐城分割析产 工本费数量等于权利人数量
                            gbfsl = bdcQlrDOList.size();
                        }
                        if (CommonConstantUtils.SF_S_DM.equals(sffbcz) && StringUtils.equals(dataversion, CommonConstantUtils.SYSTEM_VERSION_CZ) && bdcSlSfxmSlDTO.getSftssfxm()) {
                            gbfsl = (bdcQlrDOList.size() - 1) * bdcXmList.size();
                        }
                    }
                }

            }
            // 盐城常州特殊收费，工本费数量默认为1
            if ((StringUtils.equals(dataversion, CommonConstantUtils.SYSTEM_VERSION_YC) || StringUtils.equals(dataversion, CommonConstantUtils.SYSTEM_VERSION_CZ)) && bdcSlSfxmSlDTO.getSftssfxm() && gbfsl == 0) {
                gbfsl = 1;
                bdcSlSfxmDO.setSl(1.00);
                bdcSlSfxmDO.setYsje(bdcSlSfxmDO.getSl() * 10);
                bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
                bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
            }
            //数据库为空，读取gbfsl，否则读取数据库值，最小为0
            if (bdcSlSfxmDO.getSl() == null || sfcxjs) {
                bdcSlSfxmDO.setSl((double) (gbfsl >= 0 ? gbfsl : 0));
                bdcSlSfxmDO.setYsje(bdcSlSfxmDO.getSl() * 10);
                bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
                bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
            }
        }
    }

    /**
     * 获取银行是否月结
     */
    @Override
    public Integer querySfyjByJfrxm(String jfrxm, String gzldyid) {
        //判断是否为默认是否月结为否的流程
        if (CollectionUtils.isNotEmpty(sfyjmrfDyidList) && StringUtils.isNotBlank(gzldyid) && sfyjmrfDyidList.contains(gzldyid)) {
            return CommonConstantUtils.SF_F_DM;
        }
        if (StringUtils.isNotBlank(jfrxm)) {
            List<BdcXtJgDO> bdcXtJgDOList = bdcXtJgFeignService.listAyjsBdcXtJgYhmc(jfrxm);
            if (CollectionUtils.isNotEmpty(bdcXtJgDOList)) {
                return CommonConstantUtils.SF_S_DM;
            } else {
                return CommonConstantUtils.SF_F_DM;
            }
        }
        return null;
    }

    @Override
    public CommonResponse jsSfxxSaveAndTs(BdcDsfSfxxDTO bdcDsfSfxxDTO) {
        if(StringUtils.isNotBlank(bdcDsfSfxxDTO.getKpyh())){
            LOGGER.error("接收外网推送收费信息接口，推送的用户为：", bdcDsfSfxxDTO.getKpyh());
            sfUsername = bdcDsfSfxxDTO.getKpyh();
        }
        if (StringUtils.isBlank(sfUsername)) {
            LOGGER.warn("获取不到收费操作的用户信息");
        }
        if (bdcDsfSfxxDTO == null || CollectionUtils.isEmpty(bdcDsfSfxxDTO.getBdcSfxxDTOS())) {
            return CommonResponse.fail("推送的收费信息为空");
        }
        if (StringUtils.isBlank(bdcDsfSfxxDTO.getGzlslid())) {
            return CommonResponse.fail("推送的收费信息关联不到登记项目");
        }
        List<BdcSlSfxxDO> ysfxxList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(bdcDsfSfxxDTO.getGzlslid());
        if (CollectionUtils.isNotEmpty(ysfxxList)) {
            for (BdcSlSfxxDO ysfxx : ysfxxList) {
                if (StringUtils.isNotBlank(ysfxx.getJfsbm()) || CommonConstantUtils.SFZT_YJF.equals(ysfxx.getSfzt())) {
                    return CommonResponse.fail("收费信息已推送或已缴费");
                }
            }
        }
        //数据处理，保存
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        List<BdcSlSfxmDO> bdcSlSfxmDOList = new ArrayList<>();
        for (BdcSfxxDTO bdcSfxxDTO : bdcDsfSfxxDTO.getBdcSfxxDTOS()) {
            if (bdcSfxxDTO.getBdcSlSfxxDO() == null || CollectionUtils.isEmpty(bdcSfxxDTO.getBdcSlSfxmDOS())) {
                return CommonResponse.fail("推送的收费信息为空");
            }
            try {
                bdcSfxxDTO.getBdcSlSfxxDO().setGzlslid(bdcDsfSfxxDTO.getGzlslid());
                handleSfxx(bdcSfxxDTO, sfUsername, bdcDsfSfxxDTO.getJkfs());
                bdcSlSfxxDOList.add(bdcSfxxDTO.getBdcSlSfxxDO());
                handleSfxm(bdcSfxxDTO.getBdcSlSfxmDOS(), bdcSfxxDTO.getBdcSlSfxxDO().getSfxxid());
                bdcSlSfxmDOList.addAll(bdcSfxxDTO.getBdcSlSfxmDOS());
            } catch (Exception e) {
                LOGGER.error("工作流实例ID:{}组织收费信息失败:{}", bdcDsfSfxxDTO.getGzlslid(), e);
                return CommonResponse.fail("组织收费信息失败" + e.getMessage());
            }
        }
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList) || CollectionUtils.isEmpty(bdcSlSfxmDOList)) {
            return CommonResponse.fail("推送的收费信息为空");
        }
        //保存入库
        try {
            BdcSlSfxxDO deleteParam = new BdcSlSfxxDO();
            deleteParam.setGzlslid(bdcDsfSfxxDTO.getGzlslid());
            deleteSfxx(deleteParam);
            entityMapper.insertBatchSelective(bdcSlSfxxDOList);
            entityMapper.insertBatchSelective(bdcSlSfxmDOList);
        } catch (Exception e) {
            LOGGER.error("工作流实例ID:{}保存收费信息失败:{}", bdcDsfSfxxDTO.getGzlslid(), e);
            return CommonResponse.fail("保存收费信息失败" + e.getMessage());
        }
        if (Boolean.TRUE.equals(bdcDsfSfxxDTO.isTsdjfxx()) && StringUtils.equals(dataversion, CommonConstantUtils.NANTONG)) {
            boolean tsjg = true;
            List<String> failSfxxList = new ArrayList<>();
            try {
                List<Map<String, String>> resultMapList = new ArrayList<>(bdcSlSfxxDOList.size());
                for (BdcSlSfxxDO tssfxx : bdcSlSfxxDOList) {
                    LOGGER.info("开始推送收费信息ID:{}", tssfxx.getSfxxid());
                    CommonResponse result = rgtsdjfxx(tssfxx.getSfxxid(), sfUsername, tssfxx.getQlrlb(), bdcDsfSfxxDTO.getGzlslid());
                    LOGGER.info("推送收费信息ID:{}结束,推送结果：{}", tssfxx.getSfxxid(), result);
                    if(result.isSuccess()){
                        Map<String, String> resultMap = new HashMap<>(3);
                        resultMap.put("sfxxid", tssfxx.getSfxxid());
                        resultMap.put("qlrlb", tssfxx.getQlrlb());
                        Map data = (Map) result.getData();
                        resultMap.put("jfsewmurl", (String) data.get("jfsewmurl"));
                        resultMap.put("jfsbm", (String) data.get("jfsbm"));
                        resultMapList.add(resultMap);
                    }else{
                        tsjg = false;
                        failSfxxList.add(tssfxx.getSfxxid());
                    }
                }

                if (tsjg) {
                    return CommonResponse.ok(resultMapList);
                }else{
                    return CommonResponse.fail("收费信息ID" + JSONObject.toJSONString(failSfxxList) + "推送收费信息失败");
                }
            } catch (Exception e) {
                LOGGER.error("工作流实例ID:{}推送收费信息失败:{}", bdcDsfSfxxDTO.getGzlslid(), e);
                return CommonResponse.fail("推送收费信息失败" + e.getMessage());
            }
        }
        return CommonResponse.ok();

    }

    @Override
    public String nantongSfMode(String processInsId, String configName) {
        if (StringUtils.isBlank(processInsId)) {
            throw new AppException("缺失参数，工作流实例ID为空");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        String qxdm = "";
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            qxdm = bdcXmDTOList.get(0).getQxdm();
        }
        if(StringUtils.isBlank(configName)){
            configName = "fs.config.dzfp.beanName";
        }
        String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm(configName, "", qxdm);
        return beanName;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理收费信息
     */
    private void handleSfxx(BdcSfxxDTO bdcSfxxDTO, String sfUsername, String jkfs) {
        BdcSlSfxxDO bdcSlSfxxDO = bdcSfxxDTO.getBdcSlSfxxDO();
        if (StringUtils.isBlank(bdcSlSfxxDO.getSfxxid())) {
            bdcSlSfxxDO.setSfxxid(UUIDGenerator.generate16());
        }
        String qxdm = userManagerUtils.getRegionCodeByUserName(sfUsername);
        if (StringUtils.isBlank(qxdm)) {
            LOGGER.warn("获取不到用户：{}的组织机构代码", sfUsername);
        }
        // 当配置中存在对应区县代码的默认收费信息时 读取配置
        Map mrsfxxMap = mrsfxxConfig.getMrsfxxMap().get(qxdm);
        LOGGER.info("当前用户区县代码：{}，默认收费信息：{}" , qxdm, (mrsfxxMap == null ? "null" : mrsfxxMap.toString()));
        if (mrsfxxMap != null) {
            bdcSlSfxxDO.setSfdwdm(StringUtils.isBlank(bdcSlSfxxDO.getSfdwdm()) ? MapUtils.getString(mrsfxxMap, "sfdwdm") : bdcSlSfxxDO.getSfdwdm());
            bdcSlSfxxDO.setSfrxm(StringUtils.isBlank(bdcSlSfxxDO.getSfrxm()) ? MapUtils.getString(mrsfxxMap, "sfrxm") : bdcSlSfxxDO.getSfrxm());
            bdcSlSfxxDO.setSfdwmc(StringUtils.isBlank(bdcSlSfxxDO.getSfdwmc()) ? MapUtils.getString(mrsfxxMap, "sfdwmc") : bdcSlSfxxDO.getSfdwmc());
            bdcSlSfxxDO.setSfrzh(StringUtils.isBlank(bdcSlSfxxDO.getSfrzh()) ? MapUtils.getString(mrsfxxMap, "sfrzh") : bdcSlSfxxDO.getSfrzh());
            bdcSlSfxxDO.setSfrkhyhwddm(StringUtils.isBlank(bdcSlSfxxDO.getSfrkhyhwddm()) ? MapUtils.getString(mrsfxxMap, "sfrkhyhwddm") : bdcSlSfxxDO.getSfrkhyhwddm());
            bdcSlSfxxDO.setSfrkhyh(StringUtils.isBlank(bdcSlSfxxDO.getSfrkhyh()) ? MapUtils.getString(mrsfxxMap, "sfrkhyh") : bdcSlSfxxDO.getSfrkhyh());
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlSfxxDO.getGzlslid());
        if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
            if (bdcSfxxDTO.getDsfSfxxDTO() != null && StringUtils.isNotBlank(bdcSfxxDTO.getDsfSfxxDTO().getQllx())) {
                String qllx = bdcSfxxDTO.getDsfSfxxDTO().getQllx();
                String dydjxl = bdcSfxxDTO.getDsfSfxxDTO().getDydjxl();
                String ygdjzl = bdcSfxxDTO.getDsfSfxxDTO().getYgdjzl();
                for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                    if (bdcXmDTO.getQllx() != null && StringUtils.equals(qllx, bdcXmDTO.getQllx().toString())
                            && (StringUtils.isBlank(dydjxl) || StringUtils.equals(dydjxl, bdcXmDTO.getDjxl()))) {
                        if (StringUtils.isBlank(ygdjzl)) {
                            bdcSlSfxxDO.setXmid(bdcXmDTO.getXmid());
                            break;
                        } else {
                            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDTO.getXmid());
                            if (bdcQl instanceof BdcYgDO) {
                                BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                                if (bdcYgDO.getYgdjzl() != null && StringUtils.equals(ygdjzl, bdcYgDO.getYgdjzl().toString())) {
                                    bdcSlSfxxDO.setXmid(bdcYgDO.getXmid());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }else{
            List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmService.listBdcSlXmByGzlslid(bdcSlSfxxDO.getGzlslid());
            if(CollectionUtils.isEmpty(bdcSlXmDOList)){
                LOGGER.error("工作流实例ID:{}, 未获取受理项目信息。", bdcSlSfxxDO.getGzlslid());
                throw new AppException(ErrorCode.CUSTOM, "收费信息关联不到项目信息");
            }
            bdcSlSfxxDO.setXmid(bdcSlXmDOList.get(0).getXmid());
        }

//        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
//            LOGGER.error("工作流实例ID:{}获取不到登记项目", bdcSlSfxxDO.getGzlslid());
//            throw new AppException("收费信息关联不到登记项目");
//        }
//        if (bdcSfxxDTO.getDsfSfxxDTO() != null && StringUtils.isNotBlank(bdcSfxxDTO.getDsfSfxxDTO().getQllx())) {
//            String qllx = bdcSfxxDTO.getDsfSfxxDTO().getQllx();
//            String dydjxl = bdcSfxxDTO.getDsfSfxxDTO().getDydjxl();
//            String ygdjzl = bdcSfxxDTO.getDsfSfxxDTO().getYgdjzl();
//            for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
//                if (bdcXmDTO.getQllx() != null && StringUtils.equals(qllx, bdcXmDTO.getQllx().toString())
//                        && (StringUtils.isBlank(dydjxl) || StringUtils.equals(dydjxl, bdcXmDTO.getDjxl()))) {
//                    if (StringUtils.isBlank(ygdjzl)) {
//                        bdcSlSfxxDO.setXmid(bdcXmDTO.getXmid());
//                        break;
//                    } else {
//                        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDTO.getXmid());
//                        if (bdcQl instanceof BdcYgDO) {
//                            BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
//                            if (bdcYgDO.getYgdjzl() != null && StringUtils.equals(ygdjzl, bdcYgDO.getYgdjzl().toString())) {
//                                bdcSlSfxxDO.setXmid(bdcYgDO.getXmid());
//                                break;
//                            }
//                        }
//                    }
//
//                }
//
//            }
//        } else {
//            bdcSlSfxxDO.setXmid(bdcXmDTOList.get(0).getXmid());
//        }
        if (StringUtils.isBlank(bdcSlSfxxDO.getJkfs())) {
            bdcSlSfxxDO.setJkfs(jkfs);
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理收费项目信息
     */
    private void handleSfxm(List<BdcSlSfxmDO> bdcSlSfxmDOS, String sfxxid) {
        for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOS) {
            bdcSlSfxmDO.setSfxmid(UUIDGenerator.generate16());
            bdcSlSfxmDO.setSfxxid(sfxxid);
            if (StringUtils.isBlank(bdcSlSfxmDO.getJedw())) {
                bdcSlSfxmDO.setJedw("1");
            }
        }


    }

    /**
     * @param gzldyid 基本信息ID
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @param djxl    登记小类
     * @return 受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化收费信息
     */
    private BdcSlSfxxDO listCshBdcSfxx(String gzlslid, String xmid, String djxl, String gzldyid, String slbh, String bdcdyh) {
        BdcDjxlPzDO bdcDjxlPzDO = new BdcDjxlPzDO();
        bdcDjxlPzDO.setGzldyid(gzldyid);
        if (StringUtils.isNotBlank(djxl) && StringUtils.isNotBlank(bdcdyh) && Objects.equals(djxl, bfzdjxl)){
            Integer dyhqllx = bdcQllxService.getQllxByBdcdyh(bdcdyh, "");
            LOGGER.info("登记小类单元好权利类型为:{},{}", djxl,dyhqllx);
            List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzService.listBdcDjxlPzByGzldyid(gzldyid, dyhqllx, null);
            if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList) && StringUtils.isNotBlank(bdcDjxlPzDOList.get(0).getDjxl())) {
                bdcDjxlPzDO.setDjxl(bdcDjxlPzDOList.get(0).getDjxl());
                djxl = bdcDjxlPzDOList.get(0).getDjxl();
            }
        }else {
            bdcDjxlPzDO.setDjxl(djxl);
        }
        LOGGER.info("登记小类配置,登记小类为:{},{}", bdcDjxlPzDO,djxl);
        List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzService.listBdcDjxlPz(bdcDjxlPzDO);
        BdcSlSfxxDO bdcSlQlrSfxxDO = new BdcSlSfxxDO();
        BdcSlSfxxDO bdcSlYwrSfxxDO = new BdcSlSfxxDO();
        if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList) && CommonConstantUtils.SF_S_DM.equals(bdcDjxlPzDOList.get(0).getSfsf())) {
            //根据收费项目配置分为权利人收费信息和义务人收费信息
            bdcSlQlrSfxxDO.setGzlslid(gzlslid);
            bdcSlQlrSfxxDO.setXmid(xmid);
            bdcSlQlrSfxxDO.setSlsj(new Date());
            bdcSlQlrSfxxDO.setSlbh(slbh);
            bdcSlYwrSfxxDO.setGzlslid(gzlslid);
            bdcSlYwrSfxxDO.setXmid(xmid);
            bdcSlYwrSfxxDO.setSlsj(new Date());
            bdcSlYwrSfxxDO.setSlbh(slbh);
            if (Objects.nonNull(hyzfjftj)) {
                bdcSlQlrSfxxDO.setHyzfjftj(hyzfjftj);
                bdcSlYwrSfxxDO.setHyzfjftj(hyzfjftj);
            }
            if (Objects.nonNull(hyzfjflx)) {
                bdcSlYwrSfxxDO.setHyzfjflx(hyzfjflx);
                bdcSlQlrSfxxDO.setHyzfjflx(hyzfjflx);
            }
            List<BdcSlSfxmPzDO> bdcSlSfxmPzQlrList = bdcSlSfxmPzService.listBdcSlSfxmPzByDjxlAndQlrlb(djxl, CommonConstantUtils.QLRLB_QLR);
            List<BdcSlSfxmPzDO> bdcSlSfxmPzYwrList = bdcSlSfxmPzService.listBdcSlSfxmPzByDjxlAndQlrlb(djxl, CommonConstantUtils.QLRLB_YWR);
            if (CollectionUtils.isNotEmpty(bdcSlSfxmPzQlrList)) {
                bdcSlQlrSfxxDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                bdcSlQlrSfxxDO = bdcSlSfxxService.insertBdcSlSfxx(bdcSlQlrSfxxDO);
                bdcSlSfxmService.listCshBdcSlSfxm(bdcSlSfxmPzQlrList, bdcSlQlrSfxxDO.getSfxxid());
            }
            if (CollectionUtils.isNotEmpty(bdcSlSfxmPzYwrList)) {
                bdcSlYwrSfxxDO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                bdcSlYwrSfxxDO = bdcSlSfxxService.insertBdcSlSfxx(bdcSlYwrSfxxDO);
                bdcSlSfxmService.listCshBdcSlSfxm(bdcSlSfxmPzYwrList, bdcSlYwrSfxxDO.getSfxxid());
            }
        }
        return bdcSlYwrSfxxDO;
    }

    private BdcSlSfxxDTO generateSfxxData(BdcSlSfxxDTO bdcSlSfxxDTO, String gzlslid, String xmid) {

        BdcXmDO bdcXmDO = new BdcXmDO();
        List<BdcSlSfxxDO> bdcSlSfxxDOList;
        //单个流程和批量流程是一个收费单，直接挂接的资源无法获取xmid，根据gzlslid查询
        //组合流程是列表展现，根据xmid获取
        if (StringUtils.isNotBlank(xmid)) {
            bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByXmid(xmid);
        } else {
            bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        }
        //常州收费单新增减免事由
        if (bdcSlSfxxDOList != null && bdcSlSfxxDOList.size() > 0) {
            if (StringUtils.isNotBlank(bdcSlSfxxDOList.get(0).getJmsy())) {
                bdcSlSfxxDTO.setJmsy(bdcSlSfxxDOList.get(0).getJmsy());
            }
        }

        // 打开收费页面时，初始化收费时间信息。
        this.cshsfsj(bdcSlSfxxDOList);
        Integer lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        BdcXmQO bdcXmQO = new BdcXmQO();
        //组合流程数据
        if (StringUtils.isNotBlank(xmid)) {
            //批量组合流程坐落，单元号加等，面积需要计算
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
                bdcXmDO = bdcXmList.get(0);
                bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                //主要用于分组
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                //根据登记小类分组
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    Map<String, List<BdcXmDO>> djxlMap = bdcXmDOList.stream().collect(Collectors.groupingBy(BdcXmDO::getDjxl));
                    //获取当前查看的xmid对应的djxl 对应的组合项目
                    List<BdcXmDO> bdcXms = djxlMap.get(bdcXmList.get(0).getDjxl());
                    bdcSlSfxxDTO = countPlSfxxData(bdcSlSfxxDTO, bdcXms);
                }
            } else {
                //普通组合流程
                if (CollectionUtils.isNotEmpty(bdcXmList)) {
                    bdcXmDO = bdcXmList.get(0);
                    bdcSlSfxxDTO = setJdSfxxData(bdcSlSfxxDTO, bdcXmDO);
                }
            }
        } else {
            //单个和批量流程还是按照gzlslid查询
            bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            //单个和批量流程
            if (CommonConstantUtils.LCLX_PL.equals(lclx)) {
                //批量流程
                bdcXmDO = bdcXmList.get(0);
                bdcSlSfxxDTO = countPlSfxxData(bdcSlSfxxDTO, bdcXmList);
            } else {
                bdcXmDO = bdcXmList.get(0);
                //简单流程
                bdcSlSfxxDTO = setJdSfxxData(bdcSlSfxxDTO, bdcXmDO);
            }
        }
        bdcSlSfxxDTO.setDjlx(bdcXmDO.getDjlx());
        bdcSlSfxxDTO.setDjxl(bdcXmDO.getDjxl());
        bdcSlSfxxDTO.setSlbh(bdcXmDO.getSlbh());
        bdcSlSfxxDTO.setQllx(bdcXmDO.getQllx());
        bdcSlSfxxDTO.setSlr(bdcXmDO.getSlr());
        //组织页面上的权利人和义务人数据
        //qlr
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(bdcXmDO.getXmid());
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        BdcSlSqrSfxxDTO bdcSlQlrSfxxDTO = new BdcSlSqrSfxxDTO();
        if (CollectionUtils.isNotEmpty(bdcQlrDoList)) {
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if (StringUtils.equals(bdcSlSfxxDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                        BdcSlSfxxDO bdcSlSfxx = new BdcSlSqrSfxxDTO();
                        BeanUtils.copyProperties(bdcSlSfxxDO, bdcSlSfxx);
                        bdcSlQlrSfxxDTO = (BdcSlSqrSfxxDTO) bdcSlSfxx;
                        bdcSlQlrSfxxDTO.setYhjkrkzt(queryYhjkrkzt(bdcSlSfxx));
                        break;
                    }
                }
            }
            for (BdcQlrDO bdcQlrDO : bdcQlrDoList) {
                //判断是否小微企业
                BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcQlrDO.getQlrmc(), "3");
                if (StringUtils.isNotBlank(bdcXtJgDO.getJgid())) {
                    bdcSlQlrSfxxDTO.setSfxwqy(true);
                    break;
                }
            }
            //是否月结为空或者缴费人发生改变时,根据银行月结配置默认赋值
            String jfrxm = bdcQlrDoList.get(0).getQlrmc();
            if ((bdcSlQlrSfxxDTO.getSfyj() == null || (StringUtils.isNotBlank(bdcSlQlrSfxxDTO.getJfrxm()) && !StringUtils.equals(bdcSlQlrSfxxDTO.getJfrxm(), jfrxm)))) {
                Integer sfyj = this.querySfyjByJfrxm(jfrxm, bdcXmDO.getGzldyid());
                bdcSlQlrSfxxDTO.setSfyj(sfyj);
                this.saveCshSfyj(bdcSlQlrSfxxDTO.getSfxxid(), sfyj);
            }

            // 判断是否一次支付，存量房默认为是
            if (bdcSlQlrSfxxDTO.getSfyczf() == null && CollectionUtils.isNotEmpty(clfzygzldyidList) && clfzygzldyidList.contains(bdcXmDO.getGzldyid())) {
                bdcSlQlrSfxxDTO.setSfyczf(CommonConstantUtils.SF_S_DM);
            }
            String qlrmc = StringToolUtils.resolveBeanToAppendStr(bdcQlrDoList, "getQlrmc", ",");
            String qlrdlrmc = StringToolUtils.resolveBeanToAppendStr(bdcQlrDoList, "getDlrmc", ",");
            String qlrdlrdh = StringToolUtils.resolveBeanToAppendStr(bdcQlrDoList, "getDlrdh", ",");
            bdcSlQlrSfxxDTO.setMc(qlrmc);
            bdcSlQlrSfxxDTO.setLxdh(this.getQlrDhAndZjh(bdcQlrDoList).getDh());
            bdcSlQlrSfxxDTO.setDlrmc(qlrdlrmc);
            bdcSlQlrSfxxDTO.setDlrlxdh(qlrdlrdh);
            bdcSlSfxxDTO.setBdcSlQlrSfxxDTO(bdcSlQlrSfxxDTO);
        }
        //ywr
        BdcSlSqrSfxxDTO bdcSlYwrSfxxDTO = new BdcSlSqrSfxxDTO();
        List<BdcQlrDO> bdcYwrList = bdcQlrFeignService.listAllBdcQlr(bdcXmDO.getGzlslid(), CommonConstantUtils.QLRLB_YWR, bdcXmDO.getDjxl());
        if (CollectionUtils.isNotEmpty(bdcYwrList)) {
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if (StringUtils.equals(bdcSlSfxxDO.getQlrlb(), CommonConstantUtils.QLRLB_YWR)) {
                        BdcSlSfxxDO bdcSlSfxx = new BdcSlSqrSfxxDTO();
                        BeanUtils.copyProperties(bdcSlSfxxDO, bdcSlSfxx);
                        bdcSlYwrSfxxDTO = (BdcSlSqrSfxxDTO) bdcSlSfxx;
                        bdcSlYwrSfxxDTO.setYhjkrkzt(queryYhjkrkzt(bdcSlSfxx));
                        break;
                    }
                }
            }
            for (BdcQlrDO bdcYwrDO : bdcYwrList) {
                //判断是否小微企业
                BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcYwrDO.getQlrmc(), "3");
                if (StringUtils.isNotBlank(bdcXtJgDO.getJgid())) {
                    bdcSlYwrSfxxDTO.setSfxwqy(true);
                    break;
                }
            }
            //是否月结为空或者缴费人发生改变时,根据银行月结配置默认赋值
            if (bdcSlYwrSfxxDTO.getSfyj() == null || (StringUtils.isNotBlank(bdcSlYwrSfxxDTO.getJfrxm()) && !StringUtils.equals(bdcSlYwrSfxxDTO.getJfrxm(), bdcYwrList.get(0).getQlrmc()))) {
                Integer sfyj = this.querySfyjByJfrxm(bdcYwrList.get(0).getQlrmc(), bdcXmDO.getGzldyid());
                bdcSlYwrSfxxDTO.setSfyj(sfyj);
                this.saveCshSfyj(bdcSlYwrSfxxDTO.getSfxxid(), sfyj);
            }

            // 判断是否一次支付，存量房默认为是
            if (bdcSlYwrSfxxDTO.getSfyczf() == null && CollectionUtils.isNotEmpty(clfzygzldyidList) && clfzygzldyidList.contains(bdcXmDO.getGzldyid())) {
                bdcSlYwrSfxxDTO.setSfyczf(CommonConstantUtils.SF_S_DM);
            }
            BdcQlrDO bdcYwrDO = bdcGenerateQlrService.getYwrData(bdcYwrList);
            bdcSlYwrSfxxDTO.setMc(bdcYwrDO.getQlrmc());
            bdcSlYwrSfxxDTO.setLxdh(this.getQlrDhAndZjh(bdcYwrList).getDh());
            bdcSlYwrSfxxDTO.setDlrmc(bdcYwrDO.getDlrmc());
            bdcSlYwrSfxxDTO.setDlrlxdh(bdcYwrDO.getDlrdh());
            bdcSlSfxxDTO.setBdcSlYwrSfxxDTO(bdcSlYwrSfxxDTO);
        } else {
            bdcSlSfxxDTO.setBdcSlYwrSfxxDTO(bdcSlYwrSfxxDTO);
        }
        //盐城-除了住宅，非住宅配置的用途，其他的收费页面 弹出提示信息
        if (Objects.nonNull(bdcXmDO.getDzwyt()) && !zzfwytdmList.contains(bdcXmDO.getDzwyt().toString()) && !fzzfwytdmList.contains(bdcXmDO.getDzwyt().toString()) || Objects.isNull(bdcXmDO.getDzwyt())) {
            bdcSlSfxxDTO.setTsxx(tsxx);
        }
        return bdcSlSfxxDTO;
    }

    // 合肥页面打开时，初始化收费时间
    private void cshsfsj(List<BdcSlSfxxDO> bdcSlSfxxDOList) {
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO record : bdcSlSfxxDOList) {
                if (null == record.getSfsj()) {
                    BdcSlSfxxDO sfxx = new BdcSlSfxxDO();
                    sfxx.setSfxxid(record.getSfxxid());
                    sfxx.setSfsj(new Date());
                    this.bdcSlSfxxService.updateBdcSlSfxx(sfxx);
                }
            }
        }
    }

    // 页面初始化收费信息时，是否月结为空时，初始化收费信息
    private void saveCshSfyj(String sfxxid, Integer sfyj) {
        if (StringUtils.isNotBlank(sfxxid) && Objects.nonNull(sfyj)) {
            BdcSlSfxxDO sfxx = new BdcSlSfxxDO();
            sfxx.setSfxxid(sfxxid);
            sfxx.setSfyj(sfyj);
            this.bdcSlSfxxService.updateBdcSlSfxx(sfxx);
        }
    }

    /**
     * 缴库状态是由bdc_sl_sfxx 和bdc_sl_hsxx 中yhjkrkzt ，只要一个失败，传到大云那边和页面上就显示失败
     * <p>
     * 获取缴库状态逻辑：
     * 缴库状态存在于 BDC_SL_SFXX和 BDC_SL_HSXX 中。
     * 当存在收费和核税信息时（审批来源为一窗流程时，会存在税和费），需要同时验证两个 yhjkrkzt
     * 合肥 义务人获取状态 --对于金额为0的数据，缴库的时候不传数据，所以也无法更新状态
     * 义务人合计金额为0 的，yhjkrkzt字段可以为空，展示已缴库
     */
    private int queryYhjkrkzt(BdcSlSfxxDO bdcSlSfxxDO) {
        if (bdcSlSfxxDO == null) {
            return CommonConstantUtils.SF_F_DM;
        }
        if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfxxDO.getQlrlb())) {
            //1.金额为0的数据 且 sfzt =2 默认已缴库
            if (bdcSlSfxxDO.getHj() != null && bdcSlSfxxDO.getHj().equals((double) 0) && (bdcSlSfxxDO.getSfzt() != null && BdcSfztEnum.YJF.key().equals(bdcSlSfxxDO.getSfzt()))) {
                bdcSlSfxxDO.setYhjkrkzt(CommonConstantUtils.SF_S_DM);
            }
        }
        final Integer yhjkrkzt = bdcSlSfxxDO.getYhjkrkzt();
        // 判断收费信息的缴库状态：不为1时，直接返回。
        if (Objects.isNull(yhjkrkzt) || CommonConstantUtils.SF_F_DM.equals(yhjkrkzt)) {
            return CommonConstantUtils.SF_F_DM;
        }
        // 获取核税信息的银行缴库入库状态
        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
        bdcSlHsxxDO.setXmid(bdcSlSfxxDO.getXmid());
        bdcSlHsxxDO.setSqrlb(bdcSlSfxxDO.getQlrlb());
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxDO);
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
            for (BdcSlHsxxDO hsxxDO : bdcSlHsxxDOList) {
                if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, hsxxDO.getSqrlb())) {
                    if (null != hsxxDO.getSjyzhj()) {
                        BigDecimal sjyzhj = new BigDecimal(String.valueOf(hsxxDO.getSjyzhj()));
                        BigDecimal zero = new BigDecimal("0");
                        if (zero.compareTo(sjyzhj) == 0) {
                            hsxxDO.setYhjkrkzt(CommonConstantUtils.SF_S_DM);
                        }
                    }
                }
                final Integer hsjkrkzt = hsxxDO.getYhjkrkzt();
                if (Objects.isNull(hsjkrkzt) || CommonConstantUtils.SF_F_DM.equals(hsjkrkzt)) {
                    return CommonConstantUtils.SF_F_DM;
                }
            }
        }
        return CommonConstantUtils.SF_S_DM;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织收费信息(南通 ）
     */
    private BdcSlSfxxDTO generateSfxxDataNt(BdcSlSfxxDTO bdcSlSfxxDTO, String gzlslid, String xmid) {
        BdcXmDO bdcXmDO;
        List<BdcSlSfxxDO> bdcSlSfxxDOList;
        //单个流程和批量流程是一个收费单，直接挂接的资源无法获取xmid，根据gzlslid查询
        //组合流程是列表展现，根据xmid获取
        if (StringUtils.isNotBlank(xmid)) {
            bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByXmid(xmid);
        } else {
            bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        }
        Integer lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        BdcXmQO bdcXmQO = new BdcXmQO();
        List<BdcXmDO> bdcXmList;
        //组合流程数据
        if (StringUtils.isNotBlank(xmid)) {
            //批量组合流程坐落，单元号加等，面积需要计算
            bdcXmQO.setXmid(xmid);
            bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmList) && CommonConstantUtils.LCLX_PLZH.equals(lclx)) {
                bdcXmDO = bdcXmList.get(0);
                bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                bdcXmQO.setDjxl(bdcXmDO.getDjxl());
                bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            }
        } else {
            //单个和批量流程还是按照gzlslid查询
            bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            bdcXmDO = bdcXmList.get(0);
            //获取使用权面积
            Double syqmj = getTdsyqMj(bdcXmList, null);
            if (bdcXmList.size() > 1) {
                String zl = bdcXmDO.getZl() + CommonConstantUtils.SUFFIX_PL;
                String bdcdyh = bdcXmDO.getBdcdyh() + CommonConstantUtils.SUFFIX_PL;
                bdcSlSfxxDTO.setBdcdyh(bdcdyh);
                bdcSlSfxxDTO.setZl(zl);
            } else {
                bdcSlSfxxDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                bdcSlSfxxDTO.setZl(bdcXmDO.getZl());
            }
            Double dzwmj = bdcXmList.stream().filter(xm -> xm.getDzwmj() != null).mapToDouble(BdcXmDO::getDzwmj).sum();
            bdcSlSfxxDTO.setDzwmj(NumberUtil.formatDigit(dzwmj, 2));
            bdcSlSfxxDTO.setTdsyqmj(NumberUtil.formatDigit(syqmj, 2));
            bdcSlSfxxDTO.setDjlx(bdcXmDO.getDjlx());
            bdcSlSfxxDTO.setDjxl(bdcXmDO.getDjxl());
            bdcSlSfxxDTO.setSlbh(bdcXmDO.getSlbh());
            bdcSlSfxxDTO.setQllx(bdcXmDO.getQllx());
            bdcSlSfxxDTO.setSlr(bdcXmDO.getSlr());
            //组织页面上的权利人和义务人数据
            //qlr
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXmDO.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> bdcQlrDoList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            BdcSlSqrSfxxDTO bdcSlQlrSfxxDTO = new BdcSlSqrSfxxDTO();
            BdcSlSqrSfxxDTO bdcSlYwrSfxxDTO = new BdcSlSqrSfxxDTO();
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if (StringUtils.equals(bdcSlSfxxDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                        BdcSlSfxxDO bdcSlSfxx = new BdcSlSqrSfxxDTO();
                        BeanUtils.copyProperties(bdcSlSfxxDO, bdcSlSfxx);
                        bdcSlQlrSfxxDTO = (BdcSlSqrSfxxDTO) bdcSlSfxx;
                        bdcSlSfxxDTO.setXmid(bdcSlSfxxDO.getXmid());
                    } else if (StringUtils.equals(bdcSlSfxxDO.getQlrlb(), CommonConstantUtils.QLRLB_YWR)) {
                        BdcSlSfxxDO bdcSlSfxx = new BdcSlSqrSfxxDTO();
                        BeanUtils.copyProperties(bdcSlSfxxDO, bdcSlSfxx);
                        bdcSlYwrSfxxDTO = (BdcSlSqrSfxxDTO) bdcSlSfxx;
                        bdcSlSfxxDTO.setXmid(bdcSlSfxxDO.getXmid());
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(bdcQlrDoList)) {
                for (BdcQlrDO bdcQlrDO : bdcQlrDoList) {
                    //判断是否小微企业，南通qlrlx=98也是小微企业
                    BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcQlrDO.getQlrmc(), "3");
                    if (StringUtils.isNotBlank(bdcXtJgDO.getJgid()) || CommonConstantUtils.QLRLX_XWQY.equals(bdcQlrDO.getQlrlx())) {
                        bdcSlQlrSfxxDTO.setSfxwqy(true);
                        break;
                    }
                }
                String qlrmc = StringToolUtils.resolveBeanToAppendStr(bdcQlrDoList, "getQlrmc", ",");
                String qlrdlrmc = StringToolUtils.resolveBeanToAppendStr(bdcQlrDoList, "getDlrmc", ",");
                String qlrdlrdh = StringToolUtils.resolveBeanToAppendStr(bdcQlrDoList, "getDlrdh", ",");
                List<BdcXtJgDO> bdcXtJgDOList = bdcXtJgFeignService.listAyjsBdcXtJgYhmc(bdcQlrDoList.get(0).getQlrmc());
                if (CollectionUtils.isNotEmpty(bdcXtJgDOList)) {
                    bdcSlQlrSfxxDTO.setSfayjs(true);
                } else {
                    bdcSlYwrSfxxDTO.setSfayjs(false);
                }
                bdcSlQlrSfxxDTO.setMc(qlrmc);
                bdcSlQlrSfxxDTO.setDlrmc(qlrdlrmc);
                bdcSlQlrSfxxDTO.setDlrlxdh(qlrdlrdh);
                bdcSlSfxxDTO.setBdcSlQlrSfxxDTO(bdcSlQlrSfxxDTO);
            } else {
                bdcSlQlrSfxxDTO.setMc("");
                bdcSlSfxxDTO.setBdcSlQlrSfxxDTO(bdcSlQlrSfxxDTO);
            }
            //ywr
            List<BdcQlrDO> bdcYwrList = bdcQlrFeignService.listAllBdcQlr(bdcXmDO.getGzlslid(), CommonConstantUtils.QLRLB_YWR, bdcXmDO.getDjxl());
            if (CollectionUtils.isNotEmpty(bdcYwrList)) {
                for (BdcQlrDO bdcYwrDO : bdcYwrList) {
                    //判断是否小微企业，南通qlrlx=98也是小微企业
                    BdcXtJgDO bdcXtJgDO = bdcXtJgFeignService.queryJgByJgmc(bdcYwrDO.getQlrmc(), "3");
                    if (StringUtils.isNotBlank(bdcXtJgDO.getJgid()) || CommonConstantUtils.QLRLX_XWQY.equals(bdcYwrDO.getQlrlx()) ) {
                        bdcSlYwrSfxxDTO.setSfxwqy(true);
                        break;
                    }
                }
                List<BdcXtJgDO> bdcXtJgDOList = bdcXtJgFeignService.listAyjsBdcXtJgYhmc(bdcYwrList.get(0).getQlrmc());
                if (CollectionUtils.isNotEmpty(bdcXtJgDOList)) {
                    bdcSlYwrSfxxDTO.setSfayjs(true);
                } else {
                    bdcSlYwrSfxxDTO.setSfayjs(false);
                }
                BdcQlrDO bdcYwrDO = bdcGenerateQlrService.getYwrData(bdcYwrList);
                bdcSlYwrSfxxDTO.setMc(bdcYwrDO.getQlrmc());
                bdcSlYwrSfxxDTO.setDlrmc(bdcYwrDO.getDlrmc());
                bdcSlYwrSfxxDTO.setDlrlxdh(bdcYwrDO.getDlrdh());
                bdcSlSfxxDTO.setBdcSlYwrSfxxDTO(bdcSlYwrSfxxDTO);
            } else {
                bdcSlYwrSfxxDTO.setMc("");
                bdcSlSfxxDTO.setBdcSlYwrSfxxDTO(bdcSlYwrSfxxDTO);
            }
            if (StringUtils.isBlank(bdcSlSfxxDTO.getXmid())) {
                //当收费信息为空,项目ID取值
                bdcSlSfxxDTO.setXmid(bdcXmDO.getXmid());
            }
        }
        return bdcSlSfxxDTO;
    }

    private BdcSlSfxxDTO countPlSfxxData(BdcSlSfxxDTO bdcSlSfxxDTO, List<BdcXmDO> bdcXmDOList) {
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            if (bdcXmDOList.size() > 1) {
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                String zl = bdcXmDO.getZl() + CommonConstantUtils.SUFFIX_PL;
                String bdcdyh = bdcXmDO.getBdcdyh() + CommonConstantUtils.SUFFIX_PL;
                bdcSlSfxxDTO.setBdcdyh(bdcdyh);
                bdcSlSfxxDTO.setZl(zl);
                //批量面积要求和
                //批量不动产单元判断所在宗地是否为同一宗地，同一宗地宗地面积取一个宗地即可，否则宗地相加
                List<BdcXmDO> bdcXmList = new ArrayList<>();
                for (BdcXmDO bdcXm : bdcXmDOList) {
                    if (bdcXm.getBdcdyh().length() > 19) {
                        String zdBdcdyh = bdcXm.getBdcdyh().substring(0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                        bdcXm.setBdcdyh(zdBdcdyh);
                        bdcXmList.add(bdcXm);
                    }
                }
                //根据宗地的不动产单元号去重
                Set<BdcXmDO> newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getBdcdyh));
                newBdcXm.addAll(bdcXmList);
                Double zdzhmj = newBdcXm.stream().filter(xm -> xm.getZdzhmj() != null).mapToDouble(BdcXmDO::getZdzhmj).sum();
                Double dzwmj = bdcXmDOList.stream().filter(xm -> xm.getDzwmj() != null).mapToDouble(BdcXmDO::getDzwmj).sum();
                bdcSlSfxxDTO.setDzwmj(NumberUtil.formatDigit(dzwmj, 2));
                bdcSlSfxxDTO.setZdzhmj(NumberUtil.formatDigit(zdzhmj, 2));
            } else if (CollectionUtils.size(bdcXmDOList) == 1) {
                setJdSfxxData(bdcSlSfxxDTO, bdcXmDOList.get(0));
            }

        }
        return bdcSlSfxxDTO;
    }

    private BdcSlSfxxDTO setJdSfxxData(BdcSlSfxxDTO bdcSlSfxxDTO, BdcXmDO bdcXmDO) {
        bdcSlSfxxDTO.setBdcdyh(bdcXmDO.getBdcdyh());
        bdcSlSfxxDTO.setZl(bdcXmDO.getZl());
        bdcSlSfxxDTO.setDzwmj(bdcXmDO.getDzwmj());
        bdcSlSfxxDTO.setZdzhmj(bdcXmDO.getZdzhmj());
        return bdcSlSfxxDTO;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取土地使用权面积, 房地产权取tdsyqmj, 土地取syqmj
     */
    private Double getTdsyqMj(List<BdcXmDO> bdcXmDOList, List<String> xmids) {
        Double syqmj = 0.00;
        List<String> xmidList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                xmidList.add(bdcXmDO.getXmid());
            }
        }
        if (CollectionUtils.isNotEmpty(xmids)) {
            xmidList.addAll(xmids);
        }
        List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByXmids(xmidList);
        if (CollectionUtils.isNotEmpty(bdcQlList)) {
            for (BdcQl bdcQl : bdcQlList) {
                if (bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    if (bdcFdcqDO.getTdsyqmj() != null) {
                        syqmj += bdcFdcqDO.getTdsyqmj();
                    }
                } else if (bdcQl instanceof BdcJsydsyqDO) {
                    BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) bdcQl;
                    if (bdcJsydsyqDO.getSyqmj() != null) {
                        syqmj += bdcJsydsyqDO.getSyqmj();
                    }
                } else if (bdcQl instanceof BdcDyaqDO) {
                    BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                    if (bdcDyaqDO.getTddymj() != null) {
                        syqmj += bdcDyaqDO.getTddymj();
                    }
                }
            }
        }
        return syqmj;
    }


    /**
     * @param bdcSlSfxmDO 受理项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算登记费
     */
    @Override
    public BdcSlSfxmDO countDjf(BdcSlSfxmDO bdcSlSfxmDO, List<BdcXmDO> bdcXmDOList, boolean sfzhlc, boolean sfcxjs, String version) {
        //批量流程
        if (!sfzhlc && bdcXmDOList.size() > 1) {
            BdcSlSfxmSlDTO bdcSlSfxmSlDTO;
            if (StringUtils.isNotBlank(version) && StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ, version)) {
                //常州计算登记费逻辑不同
                bdcSlSfxmSlDTO = queryCzSfxmDjfSl(bdcXmDOList);
            } else if (Objects.equals(4, djfjsff)) {
                bdcSlSfxmSlDTO = queryYcSfxmDjf(bdcXmDOList);
            } else {
                bdcSlSfxmSlDTO = querySfxmDjfSl(bdcXmDOList);
            }
            if (bdcSlSfxmDO.getYsje() == null || sfcxjs) {
                //先判断非住宅再判断住宅
                if (StringUtils.equals(CommonConstantUtils.SFXM_BZ_DJFMC_FZZ, bdcSlSfxmDO.getSfxmbz()) || StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "非住宅") > -1) {
                    if (bdcSlSfxmSlDTO.getFzzdjfsl() != null && bdcSlSfxmSlDTO.getFzzdjfsl() > 0) {
                        bdcSlSfxmDO.setSl((double) bdcSlSfxmSlDTO.getFzzdjfsl());
                        if (Objects.equals(4, djfjsff) && bdcSlSfxmSlDTO.getSfyydj()) {
                            this.addDjFzzJeJb(bdcSlSfxmDO, bdcSlSfxmDO.getSl());
                        } else {
                            this.addDjfFzzJe(bdcSlSfxmDO, bdcSlSfxmDO.getSl());
                        }
                        bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
                    } else if (sfcxjs && (bdcSlSfxmSlDTO.getFzzdjfsl() != null && bdcSlSfxmSlDTO.getFzzdjfsl() == 0)) {
                    /*
                    重新计算的时候，如果发现非住宅数量不为空 为0 的时候，把这条数据的应收金额置空，前台调用后会处理
                    * */
                        this.emptyBdcSfxmxx(bdcSlSfxmDO);
                    }
                    //其他登记费
                    if (bdcSlSfxmSlDTO.getQtdjfsl() != null && bdcSlSfxmSlDTO.getQtdjfsl() > 0) {
                        bdcSlSfxmDO.setSl((double) bdcSlSfxmSlDTO.getQtdjfsl());
                        this.addDjfFzzJe(bdcSlSfxmDO, bdcSlSfxmDO.getSl());
                        bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
                    } else if (sfcxjs && (bdcSlSfxmSlDTO.getQtdjfsl() != null
                            && bdcSlSfxmSlDTO.getQtdjfsl() == 0)) {
                        this.emptyBdcSfxmxx(bdcSlSfxmDO);
                    }
                } else if (StringUtils.equals(CommonConstantUtils.SFXM_BZ_DJFMC_ZZ, bdcSlSfxmDO.getSfxmbz()) || StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "住宅") > -1) {
                    if (bdcSlSfxmSlDTO.getZzdjfsl() != null && bdcSlSfxmSlDTO.getZzdjfsl() > 0) {
                        bdcSlSfxmDO.setSl((double) bdcSlSfxmSlDTO.getZzdjfsl());
                        if (Objects.equals(4, djfjsff) && bdcSlSfxmSlDTO.getSfyydj()) {
                            this.addDjfZzJeJb(bdcSlSfxmDO, bdcSlSfxmDO.getSl());
                        } else {
                            this.addDjfZzJe(bdcSlSfxmDO, bdcSlSfxmDO.getSl());
                        }
                        bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
                    } else if (sfcxjs && (bdcSlSfxmSlDTO.getZzdjfsl() != null && bdcSlSfxmSlDTO.getZzdjfsl() == 0)) {
                        this.emptyBdcSfxmxx(bdcSlSfxmDO);
                    }
                }
                //如果是特殊登记费，按照配置的生成登记费
                if (Objects.equals(4, djfjsff) && bdcSlSfxmSlDTO.getSftssfxm()) {
                    List<BdcSlSfxmDO> bdcSlSfxmlist = bdcSlSfxmService.listBdcSlSfxmBySfxxidAndDjyy(bdcSlSfxmDO.getSfxxid(), bdcXmDOList.get(0).getDjyy());
                    if (CollectionUtils.isNotEmpty(bdcSlSfxmlist)) {
                        bdcSlSfxmDO.setSl(1.00);
                        bdcSlSfxmDO.setSfxmdm(bdcSlSfxmlist.get(0).getSfxmdm());
                        bdcSlSfxmDO.setSfxmbz(bdcSlSfxmlist.get(0).getSfxmbz());
                        bdcSlSfxmDO.setYsje(bdcSlSfxmlist.get(0).getYsje() * bdcSlSfxmDO.getSl());
                        bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
                        bdcSlSfxmDO.setSfxmmc(bdcSlSfxmlist.get(0).getSfxmmc());
                        bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
                    }
                }
                //如果常州登记费是特殊收费
                if (StringUtils.isNotBlank(version) && StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ, version)) {
                    List<BdcSlSfxmDO> bdcSlSfxmlist = bdcSlSfxmService.listBdcSlSfxmBySfxxidAndDjyy(bdcSlSfxmDO.getSfxxid(), bdcXmDOList.get(0).getDjyy());
                    if (CollectionUtils.isNotEmpty(bdcSlSfxmlist)) {
                        bdcSlSfxmDO.setSl(0.00);
                        bdcSlSfxmDO.setSfxmdm(bdcSlSfxmlist.get(0).getSfxmdm());
                        bdcSlSfxmDO.setSfxmbz(bdcSlSfxmlist.get(0).getSfxmbz());
                        bdcSlSfxmDO.setYsje(bdcSlSfxmlist.get(0).getYsje() * bdcSlSfxmDO.getSl());
                        bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
                        bdcSlSfxmDO.setSfxmmc(bdcSlSfxmlist.get(0).getSfxmmc());
                        bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
                    }
                }
            }
        } else {
            //单个流程
            if (CollectionUtils.isNotEmpty(bdcXmDOList) && (bdcSlSfxmDO.getYsje() == null || sfcxjs)) {
                //车位，车库，成套住宅，储藏室为80  仓储、其他均为550
                //单个流程登记费收费项目数量就是1
                bdcSlSfxmDO.setSl(1.00);
                BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                bdcXmFbQO.setXmid(bdcXmDOList.get(0).getXmid());
                List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);

                if (StringUtils.isNotBlank(version) && StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ, version)) {
                    BdcSlSfxmSlDTO bdcSlSfxmSlDTO = queryYcSfxmDjf(bdcXmDOList);
                    if (bdcSlSfxmSlDTO.getSftssfxm()) {
                        List<BdcSlSfxmDO> bdcSlSfxmlist = bdcSlSfxmService.listBdcSlSfxmBySfxxidAndDjyy(bdcSlSfxmDO.getSfxxid(), bdcXmDOList.get(0).getDjyy());
                        if (CollectionUtils.isNotEmpty(bdcSlSfxmlist)) {
                            bdcSlSfxmDO.setSfxmdm(bdcSlSfxmlist.get(0).getSfxmdm());
                            bdcSlSfxmDO.setSl(0.00);
                            bdcSlSfxmDO.setYsje(bdcSlSfxmlist.get(0).getYsje());
                            bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
                            bdcSlSfxmDO.setSfxmbz(bdcSlSfxmlist.get(0).getSfxmbz());
                            bdcSlSfxmDO.setSfxmmc(bdcSlSfxmlist.get(0).getSfxmmc());
                        }
                    } else {
                        String ghyt = "";
                        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDOList.get(0).getXmid());
                        //常州特殊需求房间号带特殊字符不收费
                        boolean add = true;
                        if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcFdcqDO) {
                            BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                            if (StringUtils.isNotBlank(bdcFdcqDO.getFjh()) && StringUtils.isNotBlank(fjhBsf) && bdcFdcqDO.getFjh().contains(fjhBsf)) {
                                /*
                                 * 常州地方需求，根据房间号判断是否存在 特殊字段，存在特殊字段的不计算当前登记费
                                 * */
                                add = false;
                                LOGGER.warn("当前房间号{}存在特殊字符,不计算登记费", bdcFdcqDO.getFjh());
                            }
                        }
                        //常州优先根据收费用途字段判断是否住宅，为空再根据定着物用途判断
                        if (CollectionUtils.isNotEmpty(bdcXmFbDOList) && Objects.nonNull(bdcXmFbDOList.get(0).getSfyt())) {
                            ghyt = bdcXmFbDOList.get(0).getSfyt().toString();
                        } else {
                            //获取权力表的规划用途
                            if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcFdcqDO) {
                                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                                ghyt = Objects.nonNull(bdcFdcqDO.getGhyt()) ? bdcFdcqDO.getGhyt().toString() : "";
                            } else {
                                ghyt = Objects.nonNull(bdcXmDOList.get(0).getDzwyt()) ? bdcXmDOList.get(0).getDzwyt().toString() : "";
                            }
                        }
                        if (add) {
                            if (zzfwytdmList.contains(ghyt)) {
                                this.addDjfZzJe(bdcSlSfxmDO, bdcSlSfxmDO.getSl());
                            } else {
                                this.addDjfFzzJe(bdcSlSfxmDO, bdcSlSfxmDO.getSl());
                            }
                        }
                    }
                } else if (Objects.nonNull(djfjsff) && Objects.equals(4, djfjsff)) {
                    //盐城计算方法
                    BdcSlSfxmSlDTO bdcSlSfxmSlDTO = queryYcSfxmDjf(bdcXmDOList);
                    bdcSlSfxmDO.setSl(1.00);
                    //特殊收费项目的
                    if (bdcSlSfxmSlDTO.getSftssfxm()) {
                        List<BdcSlSfxmDO> bdcSlSfxmlist = bdcSlSfxmService.listBdcSlSfxmBySfxxidAndDjyy(bdcSlSfxmDO.getSfxxid(), bdcXmDOList.get(0).getDjyy());
                        if (CollectionUtils.isNotEmpty(bdcSlSfxmlist)) {
                            bdcSlSfxmDO.setSfxmdm(bdcSlSfxmlist.get(0).getSfxmdm());
                            bdcSlSfxmDO.setYsje(bdcSlSfxmlist.get(0).getYsje());
                            bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
                            bdcSlSfxmDO.setSfxmbz(bdcSlSfxmlist.get(0).getSfxmbz());
                            bdcSlSfxmDO.setSfxmmc(bdcSlSfxmlist.get(0).getSfxmmc());
                        }
                    } else if (bdcSlSfxmSlDTO.getSfyydj()) {
                        //异议登记，登记费收一半
                        //除了异议登记的
                        if (Objects.nonNull(bdcSlSfxmSlDTO.getZzdjfsl()) && bdcSlSfxmSlDTO.getZzdjfsl() > 0) {
                            this.addDjfZzJeJb(bdcSlSfxmDO, 1.0);
                        } else if (Objects.nonNull(bdcSlSfxmSlDTO.getFzzdjfsl()) && bdcSlSfxmSlDTO.getFzzdjfsl() > 0) {
                            this.addDjFzzJeJb(bdcSlSfxmDO, 1.0);
                        } else {
                            this.addDjfFzzJe(bdcSlSfxmDO, 1.0);
                        }
                    } else {
                        //除了异议登记的
                        if (Objects.nonNull(bdcSlSfxmSlDTO.getZzdjfsl()) && bdcSlSfxmSlDTO.getZzdjfsl() > 0) {
                            bdcSlSfxmDO.setSl((double) bdcSlSfxmSlDTO.getZzdjfsl());
                            this.addDjfZzJe(bdcSlSfxmDO, 1.0);
                        } else {
                            Integer sl = Optional.ofNullable(bdcSlSfxmSlDTO.getFzzdjfsl()).orElse(0) + Optional.ofNullable(bdcSlSfxmSlDTO.getQtdjfsl()).orElse(0);
                            bdcSlSfxmDO.setSl((double) sl);
                            this.addDjfFzzJe(bdcSlSfxmDO, 1.0);
                        }
                    }
                } else {
                    if (bdcXmDOList.get(0).getDzwyt() != null && zzfwytdmList.contains(bdcXmDOList.get(0).getDzwyt().toString())) {
                        this.addDjfZzJe(bdcSlSfxmDO, bdcSlSfxmDO.getSl());
                    } else {
                        this.addDjfFzzJe(bdcSlSfxmDO, bdcSlSfxmDO.getSl());
                    }
                }
                bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
            }
        }
        return bdcSlSfxmDO;
    }

    /**
     * 将收费项目应收金额和实收金额置空，后续将应收金额为空和0的删除
     */
    private void emptyBdcSfxmxx(BdcSlSfxmDO bdcSlSfxmDO) {
        bdcSlSfxmDO.setYsje(null);
        bdcSlSfxmDO.setSsje(null);
        bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
    }

    /**
     * 添加登记费住宅费
     */
    private void addDjfZzJe(BdcSlSfxmDO bdcSlSfxmDO, Double sl) {
        if (StringUtils.equals(CommonConstantUtils.SFXM_BZ_DJFMC_ZZ, bdcSlSfxmDO.getSfxmbz()) || (StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "住宅") > -1 && !(StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "非住宅") > -1))) {
            if (Objects.nonNull(bdcSlSfxmDO.getSfxmdj())) {
                bdcSlSfxmDO.setYsje(bdcSlSfxmDO.getSfxmdj() * sl);
            } else {
                bdcSlSfxmDO.setYsje(80 * sl);
            }
            bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
            bdcSlSfxmDO.setSfxmbz(bdcSlSfxmDO.getSfxmbz());
        } else {
            this.emptyBdcSfxmxx(bdcSlSfxmDO);
        }
    }

    /**
     * 添加登记费住宅费减半
     */
    private void addDjfZzJeJb(BdcSlSfxmDO bdcSlSfxmDO, Double sl) {
        if (StringUtils.equals(CommonConstantUtils.SFXM_BZ_DJFMC_ZZ, bdcSlSfxmDO.getSfxmbz()) || StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "住宅") > -1 && !(StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "非住宅") > -1)) {
            bdcSlSfxmDO.setYsje(CommonConstantUtils.SFXM_BZ_DJF_ZZ_JB_JE * sl);
            bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
            bdcSlSfxmDO.setSfxmbz(CommonConstantUtils.SFXM_BZ_DJFMC_ZZ_JB);
        } else {
            this.emptyBdcSfxmxx(bdcSlSfxmDO);
        }
    }

    /**
     * 添加登记费非住宅费
     */
    private void addDjfFzzJe(BdcSlSfxmDO bdcSlSfxmDO, Double sl) {
        if (StringUtils.equals(CommonConstantUtils.SFXM_BZ_DJFMC_FZZ, bdcSlSfxmDO.getSfxmbz()) || StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "非住宅") > -1) {
            if (Objects.nonNull(bdcSlSfxmDO.getSfxmdj())) {
                bdcSlSfxmDO.setYsje(bdcSlSfxmDO.getSfxmdj() * sl);
            } else {
                bdcSlSfxmDO.setYsje(550 * sl);
            }
            bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
            bdcSlSfxmDO.setSfxmbz(bdcSlSfxmDO.getSfxmbz());
        } else {
            this.emptyBdcSfxmxx(bdcSlSfxmDO);
        }
    }

    /**
     * 添加登记费非住宅费减半
     */
    private void addDjFzzJeJb(BdcSlSfxmDO bdcSlSfxmDO, Double sl) {
        if (StringUtils.equals(CommonConstantUtils.SFXM_BZ_DJFMC_FZZ, bdcSlSfxmDO.getSfxmbz()) || StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "住宅") > -1) {
            bdcSlSfxmDO.setYsje(CommonConstantUtils.SFXM_BZ_DJF_FZZ_JB_JE * sl);
            bdcSlSfxmDO.setSsje(bdcSlSfxmDO.getYsje());
            bdcSlSfxmDO.setSfxmbz(CommonConstantUtils.SFXM_BZ_DJFMC_FZZ_JB);
        } else {
            this.emptyBdcSfxmxx(bdcSlSfxmDO);
        }
    }

    @Override
    public BdcSlSfxxDTO generateYcslSfxx(String gzlslid, String xmid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        BdcSlSfxxDTO bdcSlSfxxDTO = new BdcSlSfxxDTO();
        List<BdcSlSfxxDO> bdcSlSfxxDOList;
        //组织基本信息
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (bdcSlJbxxDO != null) {
            BeanUtils.copyProperties(bdcSlJbxxDO, bdcSlSfxxDTO);
            BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
            bdcSlXmQO.setXmid(xmid);
            bdcSlXmQO.setJbxxid(bdcSlJbxxDO.getJbxxid());
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                bdcSlSfxxDTO.setBdcdyh(bdcSlXmDOList.get(0).getBdcdyh());
                bdcSlSfxxDTO.setZl(bdcSlXmDOList.get(0).getZl());
                bdcSlSfxxDTO.setDjxl(bdcSlXmDOList.get(0).getDjxl());
                List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxService.listBdcSlFwxxByXmid(bdcSlXmDOList.get(0).getXmid());
                if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList)) {
                    bdcSlSfxxDTO.setDzwmj(bdcSlFwxxDOList.get(0).getJzmj());
                }
                //组织收费信息
                if (StringUtils.isNotBlank(xmid)) {
                    bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByXmid(xmid);
                } else {
                    bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
                }
                if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                    BdcSlSqrSfxxDTO bdcSlQlrSfxxDTO = new BdcSlSqrSfxxDTO();
                    BdcSlSqrSfxxDTO bdcSlYwrSfxxDTO = new BdcSlSqrSfxxDTO();
                    for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                        bdcSlSfxxDO.setYhjkrkzt(queryYhjkrkzt(bdcSlSfxxDO));
                        if (StringUtils.equals(bdcSlSfxxDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                            BeanUtils.copyProperties(bdcSlSfxxDO, bdcSlQlrSfxxDTO);
                        } else if (StringUtils.equals(bdcSlSfxxDO.getQlrlb(), CommonConstantUtils.QLRLB_YWR)) {
                            BeanUtils.copyProperties(bdcSlSfxxDO, bdcSlYwrSfxxDTO);
                        }

                    }
                    //组织权利人义务人信息
                    bdcSlSfxxDTO.setBdcSlQlrSfxxDTO(bdcSlQlrSfxxDTO);
                    bdcSlSfxxDTO.setBdcSlYwrSfxxDTO(bdcSlYwrSfxxDTO);
                    organizeSfQlrxx(bdcSlQlrSfxxDTO, bdcSlXmDOList.get(0).getXmid(), CommonConstantUtils.QLRLB_QLR);
                    organizeSfQlrxx(bdcSlYwrSfxxDTO, bdcSlXmDOList.get(0).getXmid(), CommonConstantUtils.QLRLB_YWR);

                }
            }
        }

        return bdcSlSfxxDTO;


    }

    @Override
    public BdcTsdjfxxResponseDTO tsdjfxx(String sfxxid) {
        BdcTsdjfxxResponseDTO bdcTsdjfxxResponseDTO = new BdcTsdjfxxResponseDTO();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sfxxid", sfxxid);
        Object response = exchangeInterfaceFeignService.requestInterface("jfpt_tsdjfxx", paramMap);
        if (response != null) {
            LOGGER.info("收费信息ID:{},缴费平台推送待缴信息接口调用成功,返回结果：{}", sfxxid, response);
            bdcTsdjfxxResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), BdcTsdjfxxResponseDTO.class);
            //成功
            if (bdcTsdjfxxResponseDTO != null) {
                dealTsdjfxxResponseDTO(bdcTsdjfxxResponseDTO, sfxxid, false);
            }
        }
        return bdcTsdjfxxResponseDTO;
    }

    @Override
    public BdcTsdjfxxResponseDTO cxtsdjfxx(String sfxxid, String tsly, String pjdm, boolean sftdsyj) {
        BdcTsdjfxxResponseDTO bdcTsdjfxxResponseDTO = new BdcTsdjfxxResponseDTO();
        String slbh = "";
        if (StringUtils.isNotBlank(sfxxid)) {
            List<String> sfxxidList = new ArrayList<>();
            sfxxidList.add(sfxxid);
            List<BdcSfxxDTO> bdcSfxxDTOList = quertBdcSfxx("", "", sfxxidList, null, sftdsyj);
            if (CollectionUtils.isEmpty(bdcSfxxDTOList)) {
                throw new AppException("未查询到收费信息");
            }
            //获取缴费人信息
            BdcSfxxDTO bdcSfxxDTO = bdcSfxxDTOList.get(0);
            if (bdcSfxxDTO.getBdcSlSfxxDO() != null && StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getQlrlb())) {
                String xmid = bdcSfxxDTO.getBdcSlSfxxDO().getXmid();
                List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSfxxDTO.getBdcSlSfxxDO().getGzlslid());
                if (StringUtils.isBlank(xmid) && StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getGzlslid())) {
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        xmid = bdcXmDTOList.get(0).getXmid();
                    }
                }
                if (StringUtils.isNotBlank(xmid)) {
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setXmid(xmid);
                    bdcQlrQO.setQlrlb(bdcSfxxDTO.getBdcSlSfxxDO().getQlrlb());
                    List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    if (CollectionUtils.isNotEmpty(qlrDOList)) {
                        // 获取权利人电话、号码
                        BdcQlrDO qlrxx = this.getQlrDhAndZjh(qlrDOList);
                        DsfSfxxDTO dsfSfxxDTO = bdcSfxxDTO.getDsfSfxxDTO() != null ? bdcSfxxDTO.getDsfSfxxDTO() : new DsfSfxxDTO();
                        dsfSfxxDTO.setJfrdh(qlrxx.getDh());
                        dsfSfxxDTO.setJfrzjh(qlrxx.getZjh());
                        dsfSfxxDTO.setJfrxm(bdcSfxxDTO.getBdcSlSfxxDO().getJfrxm());
                        // 缴费人电话取收费信息的jfrdh
                        if (StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getJfrdh())) {
                            dsfSfxxDTO.setJfrdh(bdcSfxxDTO.getBdcSlSfxxDO().getJfrdh());
                        }
                        //接口传入受理编号
                        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                            String xslbh = "";
                            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxService.listLcTsjfGxBySfxxid(sfxxid);
                            if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                                String gzlslid = bdcLcTsjfGxDOList.get(0).getGzlslid();
                                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
                                if (Objects.nonNull(bdcSlJbxxDO)) {
                                    xslbh = bdcSlJbxxDO.getSlbh();
                                }
                            }
                            if (StringUtils.equals(CommonConstantUtils.SF_TSLY_SFYM, tsly)) {
                                dsfSfxxDTO.setSftype("0");
                                dsfSfxxDTO.setSlbh("受理编号:" + bdcXmDTOList.get(0).getSlbh());
                            } else {
                                dsfSfxxDTO.setSftype("1");
                                dsfSfxxDTO.setSlbh("大受理编号:" + xslbh + ";小受理编号:" + bdcXmDTOList.get(0).getSlbh());
                            }
                            dsfSfxxDTO.setQxdm(bdcXmDTOList.get(0).getQxdm());
                        } else {
                            dsfSfxxDTO.setSlbh("受理编号:");
                        }
                        if (StringUtils.isBlank(pjdm)) {
                            pjdm = CommonConstantUtils.SF_PJDM;
                        }
                        dsfSfxxDTO.setPjdm(pjdm);
                        // 设置推送收费信息的唯一标识为 收费项目ID
                        if (CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmVOList())) {
                            dsfSfxxDTO.setSfxmid(bdcSfxxDTO.getBdcSlSfxmVOList().get(0).getSfxmid());
                        }
                        bdcSfxxDTO.setDsfSfxxDTO(dsfSfxxDTO);
                    }
                }
                slbh = bdcXmDTOList.get(0).getSlbh();
                bdcTsdjfxxResponseDTO.setSlbh(bdcXmDTOList.get(0).getSlbh());
                // 处理收费项目代码，添加qxdm信息
                this.handlerSfxmdmByQxdm(bdcSfxxDTO);
            }
            // 根据qxdm 配置对应的税务同步接口
            String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("fs.config.ts.beanName", "fs_jftb", bdcSfxxDTO.getDsfSfxxDTO().getQxdm());
            LOGGER.info("生成二维码sfxxid{}, qxdm：{}, beanName: {}, 调用接口入参{}", sfxxid, bdcSfxxDTO.getDsfSfxxDTO().getQxdm(), beanName, JSON.toJSONString(bdcSfxxDTO));
            Object response = exchangeInterfaceFeignService.requestInterface(beanName, bdcSfxxDTO);
            if (response != null) {
                LOGGER.info("收费信息ID:{},缴费平台推送待缴信息接口调用成功,返回结果：{}", sfxxid, response);
                bdcTsdjfxxResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), BdcTsdjfxxResponseDTO.class);
                //成功
                dealTsdjfxxResponseDTO(bdcTsdjfxxResponseDTO, sfxxid, true);
            }

        }
        bdcTsdjfxxResponseDTO.setSlbh(slbh);
        return bdcTsdjfxxResponseDTO;
    }

    /**
     * 处理收费项目代码，添加区县代码内容
     * <p>收费项目拼接成 sfxmdm@qxdm </p>
     */
    private void handlerSfxmdmByQxdm(BdcSfxxDTO bdcSfxxDTO){
        if(Objects.nonNull(bdcSfxxDTO.getDsfSfxxDTO())){
            String qxdm = bdcSfxxDTO.getDsfSfxxDTO().getQxdm();
            if(StringUtils.isNotBlank(qxdm) && CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmVOList())){
                for(BdcSlSfxmVO bdcSlSfxmVO : bdcSfxxDTO.getBdcSlSfxmVOList()){
                    bdcSlSfxmVO.setSfxmdmAndQxdm(bdcSlSfxmVO.getSfxmdm() + "@" + qxdm);
                }
            }
        }
    }

    @Override
    public CommonResponse rgtsdjfxx(String sfxxid, String currentUserName, String qlrlb, String gzlslid) {
        CommonResponse commonResponse = new CommonResponse();
        if (StringUtils.isNotBlank(sfxxid) && StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            String qxdm = "";
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                qxdm = bdcXmDTOList.get(0).getQxdm();
            } else {
                //取受理数据的qxdm
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
                if (Objects.nonNull(bdcSlJbxxDO)) {
                    qxdm = bdcSlJbxxDO.getQxdm();
                }
            }
            String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("fs.config.ts.beanName", "", qxdm);
            if (StringUtils.isBlank(beanName)) {
                //南通市区模式, 未配置beanName，采用feign接口调用方式
                LOGGER.info("推送收费信息,sfxxid = {},调用南通市区模式接口入参", sfxxid);
                return this.tsSfxxAndGxSfxxJfsbm(sfxxid, qlrlb, gzlslid);
            }

            if(beanName.indexOf("ykq") > -1){
                BdcDdxxAbstractService bdcDdxxAbstractService = this.bdcDdxxFactory.getDdxxService("ykqfs");
                Object response = bdcDdxxAbstractService.sczfdd(gzlslid, CommonConstantUtils.DDXX_QD_XS, qlrlb);
                LOGGER.info("调用一卡清生成订单返回值信息为：{}", JSON.toJSON(response));
                if (response != null) {
                    Map<String, String> responseMap = (Map<String, String>) response;
                    String jfsbm = responseMap.get("jfsbm");
                    String jfsewmurl = responseMap.get("jfsewmurl");
                    this.modifySfxxJfsewmxx(sfxxid, jfsbm, jfsewmurl);
                    Map<String, String> resultMap = new HashMap<>(2);
                    resultMap.put("jfsbm", jfsbm);
                    resultMap.put("jfsewmurl", jfsewmurl);
                    commonResponse.setData(resultMap);
                }else {
                    throw new AppException("调用一卡清推送待缴费信息失败，返回信息为空。");
                }
            }else{
                BdcSfxxDTO bdcSfxxDTO = getBdcSfxxDTO(sfxxid, currentUserName);
                LOGGER.info("如皋推送收费信息,sfxxid = {},调用接口入参{}", sfxxid, JSON.toJSON(bdcSfxxDTO));
                Object response = exchangeInterfaceFeignService.requestInterface(beanName, bdcSfxxDTO);
                if (response != null) {
                    CommonResponse tsResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
                    commonResponse.setSuccess(tsResponse.isSuccess());
                    if (tsResponse.isSuccess()) {
                        // 更新缴费书编码，缴费书二维码
                        JSONObject jsonObject = (JSONObject) tsResponse.getData();
                        String jfsbm = (String) jsonObject.get("jfsbm");
                        String jfsewmurl = (String) jsonObject.get("jfsewmurl");
                        this.modifySfxxJfsewmxx(sfxxid, jfsbm, jfsewmurl);
                        // 设置返回值内容
                        Map<String, String> resultMap = new HashMap<>(2);
                        resultMap.put("jfsbm", jfsbm);
                        resultMap.put("jfsewmurl", jfsewmurl);
                        commonResponse.setData(resultMap);
                    } else {
                        LOGGER.error("如皋推送待缴费信息失败：{}", JSON.toJSONString(response));
                        throw new AppException("推送待缴费信息失败," + tsResponse.getFail().getMessage());
                    }
                }
            }
        }
        return commonResponse;
    }

    // 根据收费信息ID更新缴费书二维码、缴费书二维码url信息
    private void modifySfxxJfsewmxx(String sfxxid, String jfsbm, String jfsewmurl){
        BdcSlSfxxDO sfxx = new BdcSlSfxxDO();
        sfxx.setSfxxid(sfxxid);
        sfxx.setJfsbm(jfsbm);
        sfxx.setJfsewmurl(jfsewmurl);
        bdcSlSfxxService.updateBdcSlSfxx(sfxx);
    }

    @Override
    public CommonResponse zfdjfxx(BdcSlSfxxDO bdcSlSfxxDO) {
        CommonResponse commonResponse = new CommonResponse();
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getSfxxid()) && StringUtils.isNotBlank(bdcSlSfxxDO.getGzlslid())) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlSfxxDO.getGzlslid());
            String qxdm = "";
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                qxdm = bdcXmDTOList.get(0).getQxdm();
            }
            String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("fs.config.zf.beanName", "", qxdm);
            BdcSfxxDTO bdcSfxxDTO = getBdcSfxxDTO(bdcSlSfxxDO.getSfxxid(), "");
            LOGGER.info("作废收费信息,sfxxid = {},调用接口入参{}", bdcSlSfxxDO.getSfxxid(), JSON.toJSON(bdcSfxxDTO));
            Object response = exchangeInterfaceFeignService.requestInterface(beanName, bdcSfxxDTO);
            if(response != null) {
                commonResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
                if (commonResponse.isSuccess()) {
                    BdcSlSfxxDO zfsfxx = this.bdcSlSfxxService.queryBdcSlSfxxBySfxxid(bdcSlSfxxDO.getSfxxid());

                    // 将缴款码与缴费书URL置空，同时重新生成sfxxid，保证后续调用非税接口，流水号不会重复
                    BdcSlSfxxDO sfxx = new BdcSlSfxxDO();
                    sfxx.setSfxxid(UUIDGenerator.generate16());
                    sfxx.setJfsbm("");
                    sfxx.setJfsewmurl("");
                    this.bdcSlSfxxService.updateBdcSlSfxxIdWithSfxm(bdcSlSfxxDO.getSfxxid(), sfxx);

                    // 生成一条作废的收费信息
                    zfsfxx.setSfzf(CommonConstantUtils.SF_S_DM);
                    zfsfxx.setTfyy(bdcSlSfxxDO.getTfyy());
                    UserDto userDto = this.userManagerUtils.getCurrentUser();
                    zfsfxx.setTfrxm(userDto.getAlias());
                    this.bdcSlSfxxService.insertBdcSlSfxx(zfsfxx);

                    // 更新发票信息为已作废
                    List<BdcSlFpxxDO> bdcSlFpxxDOList = this.bdcSlFpxxService.queryBdcSlFpxxBySfxxid(bdcSlSfxxDO.getSfxxid());
                    if(CollectionUtils.isNotEmpty(bdcSlFpxxDOList)){
                       for(BdcSlFpxxDO bdcSlFpxxDO:bdcSlFpxxDOList){
                           bdcSlFpxxDO.setFpzt(CommonConstantUtils.FPZT_CH);
                           this.bdcSlFpxxService.saveOrUpdateBdcSlFpxx(bdcSlFpxxDO);
                       }
                    }
                }
            }
        }
        return commonResponse;
    }

    @Override
    public Object hkdzfpxx(String sfxxid) {
        if (StringUtils.isBlank(sfxxid)) {
            throw new MissingArgumentException("未获取到收费信息ID。");
        }
        BdcSfxxDTO bdcSfxxDTO = getBdcSfxxDTO(sfxxid, "");
        String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("fs.config.hkdzfp.beanName", "", bdcSfxxDTO != null ? bdcSfxxDTO.getDsfSfxxDTO().getQxdm() : "");
        LOGGER.info("调用打印电子发票接口, 请求参数：{},beanName:{}", JSON.toJSON(bdcSfxxDTO), beanName);
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, bdcSfxxDTO);
        return response;
    }

    @Override
    public Object getDzfpxx(String sfxxid) {
        if (StringUtils.isBlank(sfxxid)) {
            throw new MissingArgumentException("未获取到收费信息ID。");
        }
        BdcSfxxDTO bdcSfxxDTO = getBdcSfxxDTO(sfxxid, this.userManagerUtils.getCurrentUserName());
        String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("fs.config.dzfp.beanName", "fs_dzpjwjxz", bdcSfxxDTO != null ? bdcSfxxDTO.getDsfSfxxDTO().getQxdm() : "");
        JSONObject jsonObject = new JSONObject();
        LOGGER.info("调用打印电子发票接口, 请求参数：{},beanName:{}", JSON.toJSON(bdcSfxxDTO), beanName);
        if(beanName.indexOf("ykq") > -1){
            return getYkqDzfpxx(sfxxid, beanName);
        }else{
            Object response = exchangeInterfaceFeignService.requestInterface(beanName, bdcSfxxDTO);
            if (response != null) {
                CommonResponse commonResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
                LOGGER.info("打印电子发票接口, 返回值：{}", commonResponse.toString());
                if (commonResponse.isSuccess()) {
                    // base64
                    jsonObject = (JSONObject) commonResponse.getData();
                } else {
                    LOGGER.error("打印电子发票失败：{}", JSON.toJSONString(response));
                    throw new AppException("打印电子发票失败," + commonResponse.getFail().getMessage());
                }
            }
        }
        return jsonObject;
    }

    private Object getYkqDzfpxx(String sfxxid, String beanName){
        BdcSlSfxxDO bdcSlSfxxDO = this.bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
        if(Objects.isNull(bdcSlSfxxDO)){
            throw new AppException("未获取到收费信息, sfxxid:" + sfxxid);
        }
        String slbh = "";
        List<BdcXmDTO> bdcXmDTOList =  this.bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlSfxxDO.getGzlslid());
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByGzlslid(bdcSlSfxxDO.getGzlslid());
            if(Objects.isNull(bdcSlJbxxDO)){
                throw new AppException(ErrorCode.CUSTOM, "未获取到不动产项目数据和不动产受理基本信息。");
            }
            slbh = bdcSlJbxxDO.getSlbh();
        }else{
            slbh = bdcXmDTOList.get(0).getSlbh();
        }
        // 获取订单编号信息
        BdcSlSfssDdxxDO bdcSlSfssDdxxDO = this.queryBdcSlSfssDdxx(bdcSlSfxxDO.getGzlslid(), bdcSlSfxxDO.getQlrlb());
        Map<String, Object> param = new HashMap<>(2);
        if(Objects.nonNull(bdcSlSfssDdxxDO)){
            param.put("ddbh",bdcSlSfssDdxxDO.getDsfddbh());
        }
        param.put("slbh", slbh);
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, param);
        LOGGER.info("一卡清打印电子发票接口, 返回值：{}", StringUtils.left(JSON.toJSONString(response), 4000));
        if(Objects.nonNull(response)){
            YkqDzfpmxDTO ykqDzfpmxDTO = JSON.parseObject(JSON.toJSONString(response), YkqDzfpmxDTO.class);
            return ykqDzfpmxDTO;
        }else{
            throw new AppException("打印电子发票失败,返回值为空");
        }
    }

    /**
     * 根据工作流实例ID和权利人类别查询收费收费订单信息
     */
    private BdcSlSfssDdxxDO queryBdcSlSfssDdxx(String gzlslid, String qlrlb){
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        bdcSlSfssDdxxQO.setQlrlb(qlrlb);
        bdcSlSfssDdxxQO.setDdlx(CommonConstantUtils.DDXX_DDLX_JNF);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
            return bdcSlSfssDdxxDOList.get(0);
        }
        return null;
    }

    @Override
    public CommonResponse dsfDzfpxxxz(BdcDsfSfxxDTO bdcDsfSfxxDTO) {
        if(StringUtils.isAnyBlank(bdcDsfSfxxDTO.getWwslbh(), bdcDsfSfxxDTO.getKpyh())){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数外网受理编号或开票用户");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSpxtywh(bdcDsfSfxxDTO.getWwslbh());
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产信息，外网受理编号为：" +bdcDsfSfxxDTO.getWwslbh());
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.listBdcSlSfxxByGzlslid(bdcXmDOList.get(0).getGzlslid());
        if(CollectionUtils.isEmpty(bdcSlSfxxDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息，外网受理编号为：" +bdcDsfSfxxDTO.getWwslbh());
        }
        // 过滤合计为0的数据
        bdcSlSfxxDOList = bdcSlSfxxDOList.stream().filter(t-> t.getHj() > 0).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(bdcSlSfxxDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到收费信息，外网受理编号为：" +bdcDsfSfxxDTO.getWwslbh());
        }

        List<Map<String, String>> resultMapList = new ArrayList<>(bdcSlSfxxDOList.size());
        for(BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList){
            BdcSfxxDTO bdcSfxxDTO = getBdcSfxxDTO(bdcSlSfxxDO.getSfxxid(), bdcDsfSfxxDTO.getKpyh());
            String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("fs.config.dzfp.beanName", "fs_dzpjwjxz", bdcSfxxDTO != null ? bdcSfxxDTO.getDsfSfxxDTO().getQxdm() : "");
            LOGGER.info("调用打印电子发票接口, 请求参数：{},beanName:{}", JSON.toJSON(bdcSfxxDTO), beanName);
            Object response = exchangeInterfaceFeignService.requestInterface(beanName, bdcSfxxDTO);
            if (null !=response) {
                CommonResponse commonResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
                if (commonResponse.isSuccess()) {
                    Map<String, String> resultMap = new HashMap<>(2);
                    resultMap.put("sfxxid", bdcSlSfxxDO.getSfxxid());
                    JSONObject jsonObject = (JSONObject) commonResponse.getData();
                    resultMap.put("dzfpBase64", (String) jsonObject.get("base64"));
                    resultMapList.add(resultMap);
                } else {
                    LOGGER.error("外网受理编号为{}，电子发票失败下载返回值为：{}", bdcDsfSfxxDTO.getWwslbh(), JSON.toJSONString(response));
                    throw new AppException(ErrorCode.CUSTOM, "电子发票下载失败，外网受理编号为：" +bdcDsfSfxxDTO.getWwslbh());
                }
            }
        }
        return CommonResponse.ok(resultMapList);
    }

    /**
     * 根据收费信息id,查询BdcSfxxDTO
     *
     * @param sfxxid 收费信息id
     * @return BdcSfxxDTO
     */
    private BdcSfxxDTO getBdcSfxxDTO(String sfxxid, String currentUserName) {
        BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
        List<String> sfxxidList = new ArrayList<>();
        sfxxidList.add(sfxxid);
        // 收费信息主表BdcSlSfxxDO
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxPl("", "", sfxxidList, null, true);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException("未查询到收费信息");
        }
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
        bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
        // 获取收费信息数据，并处理收费项目单价bdcSlSfxmVOList
        List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
        if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
            //处理单价数据
            List<BdcSlSfxmVO> bdcSlSfxmVOList = new ArrayList<>(bdcSlSfxmDOList.size());
            for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                BdcSlSfxmVO bdcSlSfxmVO = new BdcSlSfxmVO();
                BeanUtils.copyProperties(bdcSlSfxmDO, bdcSlSfxmVO);
                double dj = bdcSlSfxmDO.getYsje() / (Objects.nonNull(bdcSlSfxmDO.getSl()) ? bdcSlSfxmDO.getSl() : 1);
                // 四舍五入
                dj = (double) (Math.round(dj * 100)) / 100;
                bdcSlSfxmVO.setSfxmdj(dj);
                bdcSlSfxmVOList.add(bdcSlSfxmVO);
            }
            bdcSfxxDTO.setBdcSlSfxmVOList(bdcSlSfxmVOList);
        }
        // 获取第三方信息
        String qxdm = "";
        String zl = "";
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getQlrlb())) {
            String xmid = bdcSlSfxxDO.getXmid();
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(bdcSlSfxxDO.getGzlslid());
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(bdcSlSfxxDO.getGzlslid());
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(bdcSlSfxxDO.getGzlslid());
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                qxdm = bdcXmDOList.get(0).getQxdm();
                zl =  bdcXmDOList.get(0).getZl();
            } else {
                // 如果登记库没有获取到数据，再查询受理库的信息
                if (Objects.nonNull(bdcSlJbxxDO)) {
                    qxdm = bdcSlJbxxDO.getQxdm();
                    zl =  bdcSlJbxxDO.getZl();
                }
            }
            // 如果xmid为空，根据工作流实例id查询项目
            if (StringUtils.isBlank(xmid) && StringUtils.isNotBlank(bdcSlSfxxDO.getGzlslid())) {
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    xmid = bdcXmDOList.get(0).getXmid();
                } else {
                    // 如果登记库没有获取到数据，再查询受理库的信息
                    if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                        xmid = bdcSlXmDOList.get(0).getXmid();
                    }
                }
            }
            if (StringUtils.isNotBlank(xmid)) {
                DsfSfxxDTO dsfSfxxDTO = new DsfSfxxDTO();
                if (StringUtils.isBlank(currentUserName)) {
                    currentUserName = userManagerUtils.getCurrentUserName();
                }
                dsfSfxxDTO.setUserName(currentUserName);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    dsfSfxxDTO.setSlrxm(bdcXmDOList.get(0).getSlr());
                } else {
                    // 如果登记库没有获取到数据，再查询受理库的信息
                    if (Objects.nonNull(bdcSlJbxxDO)) {
                        dsfSfxxDTO.setSlrxm(bdcSlJbxxDO.getSlr());
                    }
                }
                dsfSfxxDTO.setQxdm(qxdm);
                dsfSfxxDTO.setZl(zl);
                dsfSfxxDTO.setSkrzjh(this.getSkrZjh(xmid, bdcSlSfxxDO.getSfrxm()));

                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(xmid);
                bdcQlrQO.setQlrlb(bdcSlSfxxDO.getQlrlb());
                List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(qlrDOList)) {
                    // 获取权利人电话、号码
                    BdcQlrDO qlrxx = this.getQlrDhAndZjh(qlrDOList);
                    dsfSfxxDTO.setJfrdh(qlrxx.getDh());
                    dsfSfxxDTO.setJfrzjh(qlrxx.getZjh());
                } else {
                    // 如果登记库没有获取到数据，再查询受理库的信息
                    List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, bdcSlSfxxDO.getQlrlb());
                    if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                        // 获取受理库权利人电话、号码
                        BdcSlSqrDO qlrxx = getSlSqrDhAndZjh(bdcSlSqrDOList);
                        dsfSfxxDTO.setJfrdh(qlrxx.getDh());
                        dsfSfxxDTO.setJfrzjh(qlrxx.getZjh());
                    }
                }
                bdcSfxxDTO.setDsfSfxxDTO(dsfSfxxDTO);
            }
        }
        return bdcSfxxDTO;
    }

    /**
     * 获取权利人证件号，获取逻辑：先权利人、在代理人
     */
    private BdcQlrDO getQlrDhAndZjh(List<BdcQlrDO> bdcQlrDOList) {
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
            if (StringUtils.isBlank(bdcQlrDO.getDh())) {
                List<String> dhList = bdcQlrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getDh())).map(BdcQlrDO::getDh)
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(dhList)) {
                    bdcQlrDO.setDh(dhList.get(0));
                } else {
                    dhList = bdcQlrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getDlrdh())).map(BdcQlrDO::getDlrdh)
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(dhList)) {
                        bdcQlrDO.setDh(dhList.get(0));
                    }
                }
            }
            if (StringUtils.isBlank(bdcQlrDO.getZjh())) {
                List<String> zjhList = bdcQlrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getZjh())).map(BdcQlrDO::getZjh)
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(zjhList)) {
                    bdcQlrDO.setZjh(zjhList.get(0));
                } else {
                    zjhList = bdcQlrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getDlrzjh())).map(BdcQlrDO::getDlrzjh)
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(zjhList)) {
                        bdcQlrDO.setZjh(zjhList.get(0));
                    }
                }
            }
            return bdcQlrDO;
        }
        return new BdcQlrDO();
    }

    private BdcSlSqrDO getSlSqrDhAndZjh(List<BdcSlSqrDO> bdcSlSqrDOList) {
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            BdcSlSqrDO bdcSlSqrDO = bdcSlSqrDOList.get(0);
            if (StringUtils.isBlank(bdcSlSqrDO.getDh())) {
                List<String> dhList = bdcSlSqrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getDh())).map(BdcSlSqrDO::getDh)
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(dhList)) {
                    bdcSlSqrDO.setDh(dhList.get(0));
                } else {
                    dhList = bdcSlSqrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getDlrdh())).map(BdcSlSqrDO::getDlrdh)
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(dhList)) {
                        bdcSlSqrDO.setDh(dhList.get(0));
                    }
                }
            }
            if (StringUtils.isBlank(bdcSlSqrDO.getZjh())) {
                List<String> zjhList = bdcSlSqrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getZjh())).map(BdcSlSqrDO::getZjh)
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(zjhList)) {
                    bdcSlSqrDO.setZjh(zjhList.get(0));
                } else {
                    zjhList = bdcSlSqrDOList.stream().filter(t -> StringUtils.isNotBlank(t.getDlrzjh())).map(BdcSlSqrDO::getDlrzjh)
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(zjhList)) {
                        bdcSlSqrDO.setZjh(zjhList.get(0));
                    }
                }
            }
            return bdcSlSqrDO;
        }
        return new BdcSlSqrDO();
    }

    private String getSkrZjh(String xmid, String qlrmc){
        if(StringUtils.isAnyBlank(xmid, qlrmc)){
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrmc(qlrmc);
            List<BdcQlrDO> qlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if(CollectionUtils.isNotEmpty(qlrDOList)){
                return qlrDOList.get(0).getZjh();
            } else {
                // 如果登记库没有获取到数据，再查询受理库的信息
                BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                bdcSlSqrQO.setXmid(xmid);
                bdcSlSqrQO.setSqrmc(qlrmc);
                List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.queryBdcSlSqrBySqrQO(bdcSlSqrQO);
                if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                    return bdcSlSqrDOList.get(0).getZjh();
                }
            }
        }
        return "";
    }

    @Override
    public BdcTsdjfxxResponseDTO tsdjfxxBySlbh(String slbh, String xmid) {
        if (StringUtils.isAnyBlank(slbh, xmid)) {
            throw new MissingArgumentException("缺失接口参数受理编号或项目ID");
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("slbh", slbh);
        paramMap.put("xmid", xmid);
        paramMap.put("qd", CommonConstantUtils.JKQD_XX);
        Object response = exchangeInterfaceFeignService.requestInterface("wwsqScddh", paramMap);
        LOGGER.info("受理编号:{},推送缴费信息生成支付订单接口调用成功,返回结果：{}", slbh, response);
        BdcTsdjfxxResponseDTO bdcTsdjfxxResponseDTO = new BdcTsdjfxxResponseDTO();
        if (Objects.nonNull(response)) {
            bdcTsdjfxxResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), BdcTsdjfxxResponseDTO.class);
            if (Objects.nonNull(bdcTsdjfxxResponseDTO)) {
                if (!CheckParameter.checkAnyParameter(bdcTsdjfxxResponseDTO)) {
                    throw new AppException("推送缴费信息生成支付订单接口返回信息为空" + JSONObject.toJSONString(bdcTsdjfxxResponseDTO));
                }
                // 增加接口状态值判断，0000为下单成功状态码，其余为异常情况。
                if ("0000".equals(bdcTsdjfxxResponseDTO.getStatusCode())) {
                    //更新所有收费信息的缴费书编码（即订单单号）
                    BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                    BeanUtils.copyProperties(bdcTsdjfxxResponseDTO, bdcSlSfxxDO);
                    this.modifyAllBdcSlSfxxBySlbh(slbh, bdcSlSfxxDO);
                }
            }
        }
        return bdcTsdjfxxResponseDTO;
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据gzlslid查询当前流程是否点击收费页面保存按钮
     * ---逻辑调整互联网+ 抵押流程 的不需要验证是否点击保存按钮
     * @date : 2019/12/23 13:42
     */
    @Override
    public Integer queryBczt(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                //查询互联网+ 抵押项目的数据
                List<BdcXmDO> hlwDyaXmList = bdcXmDOList
                        .stream()
                        .filter(bdcXmDO -> (
                                CommonConstantUtils.SPLY_WWSQ_DETAIL.contains(bdcXmDO.getSply())
                                && CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx())
                        ))
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(hlwDyaXmList)) {
                    return CommonConstantUtils.SF_S_DM;
                }
            }
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    //只要有一条数据保存状态不为1 直接返回
                    if (bdcSlSfxxDO.getBczt() == null || bdcSlSfxxDO.getBczt().equals(CommonConstantUtils.SF_F_DM)) {
                        return CommonConstantUtils.SF_F_DM;
                    }
                }
            } else {
                //没有查询到当前流程的收费信息，不需要点击保存
                return CommonConstantUtils.SF_S_DM;
            }
        } else {
            throw new AppException("查询收费信息必要参数工作流实例id为空");
        }
        return CommonConstantUtils.SF_S_DM;
    }

    @Override
    public BdcSfSsxxDTO queryBdcSfSsxxDTO(BdcSfSsxxQO bdcSfSsxxQO) {
        BdcSfSsxxDTO bdcSfSsxxDTO = new BdcSfSsxxDTO();
        bdcSfSsxxDTO.setXmid(bdcSfSsxxQO.getXmid());
        bdcSfSsxxDTO.setQlrlb(bdcSfSsxxQO.getSqrlb());
        if (StringUtils.isNotBlank(bdcSfSsxxQO.getXmid())) {
            BdcXmDO bdcXmDO = this.bdcXmFeignService.queryBdcXmByXmid(bdcSfSsxxQO.getXmid());
            if(Objects.nonNull(bdcXmDO)){
                bdcSfSsxxDTO.setSlbh(bdcXmDO.getSlbh());
                bdcSfSsxxDTO.setQxdm(bdcXmDO.getQxdm());
                //查找登记权利人信息
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcSfSsxxQO.getXmid());
                bdcQlrQO.setQlrlb(bdcSfSsxxQO.getSqrlb());
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
                    for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                        BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                        acceptDozerMapper.map(bdcQlrDO, bdcSlSqrDO);
                        bdcSlSqrDOList.add(bdcSlSqrDO);
                    }
                    bdcSfSsxxDTO.setBdcSlSqrDOS(bdcSlSqrDOList);
                }
                //查受理基本信息和收费信息
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(bdcXmDO.getGzlslid());
                if (Objects.nonNull(bdcSlJbxxDO)) {
                    bdcSfSsxxDTO.setFwuuid(bdcSlJbxxDO.getFwuuid());
                }
                // 查询收费信息缴款书编码url
                this.queryBdcSfxxJksbm(bdcXmDO.getGzlslid(), bdcXmDO.getXmid(), bdcSfSsxxQO.getSqrlb(), bdcSfSsxxDTO);
            } else {
                BdcSlXmDO bdcSlXmDO = bdcSlXmService.queryBdcSlXmByXmid(bdcSfSsxxQO.getXmid());
                if (bdcSlXmDO != null) {
                    BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcSlXmDO.getJbxxid());
                    if (bdcSlJbxxDO != null) {
                        bdcSfSsxxDTO.setSlbh(bdcSlJbxxDO.getSlbh());
                        bdcSfSsxxDTO.setQxdm(bdcSlJbxxDO.getQxdm());
                        bdcSfSsxxDTO.setFwuuid(bdcSlJbxxDO.getFwuuid());
                        // 查询收费信息缴款书编码url
                        this.queryBdcSfxxJksbm(bdcSlJbxxDO.getGzlslid(), bdcSfSsxxQO.getXmid(), bdcSfSsxxQO.getSqrlb(), bdcSfSsxxDTO);
                    }
                }
                //申请人信息
                List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(bdcSfSsxxQO.getXmid(), bdcSfSsxxQO.getSqrlb());
                if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                    bdcSfSsxxDTO.setBdcSlSqrDOS(bdcSlSqrDOList);
                }
            }

            // 查询缴费信息
            if (!StringUtils.equals(CommonConstantUtils.SFSSXX_CXBZ_SS, bdcSfSsxxQO.getCxbz())) {
                //缴费信息
                List<BdcSfxxDTO> bdcSfxxDTOList = queryBdcSfxxDTO(bdcSfSsxxQO.getXmid(), bdcSfSsxxQO.getSqrlb(), null, CommonConstantUtils.SF_F_DM);
                if (CollectionUtils.isNotEmpty(bdcSfxxDTOList)) {
                    bdcSfSsxxDTO.setBdcSfxxDTOS(bdcSfxxDTOList);
                    bdcSfSsxxDTO.setSfyj(CommonConstantUtils.SF_F_DM);
                    // 获取缴费状态，默认为未缴费。
                    String jfzt = String.valueOf(BdcSfztEnum.WJF.key());
                    for (BdcSfxxDTO bdcSfxxDTO : bdcSfxxDTOList) {
                        BdcSlSfxxDO bdcSlSfxxDO = bdcSfxxDTO.getBdcSlSfxxDO();
                        if (Objects.nonNull(bdcSlSfxxDO) && Objects.nonNull(bdcSlSfxxDO.getSfzt())) {
                            jfzt = String.valueOf(bdcSlSfxxDO.getSfzt());
                            break;
                        }
                    }
                    bdcSfSsxxDTO.setJfzt(jfzt);
                }

                // 维修基金收费信息
                this.queryWxjjxx(bdcSfSsxxQO.getXmid(), bdcSfSsxxQO.getSqrlb(), bdcSfSsxxDTO);
            }

            // 查询缴税信息
            if (!StringUtils.equals(CommonConstantUtils.SFSSXX_CXBZ_SF, bdcSfSsxxQO.getCxbz())) {
                //缴税信息
                List<BdcSwxxDTO> bdcSwxxDTOList = bdcSlHsxxService.queryBdcSwxxDTO(bdcSfSsxxQO.getXmid(), bdcSfSsxxQO.getSqrlb());
                if (CollectionUtils.isNotEmpty(bdcSwxxDTOList)) {
                    bdcSfSsxxDTO.setBdcSwxxDTOS(bdcSwxxDTOList);
                }
            }
        }
        LOGGER.info("受理编号:{},xmid:{},查询参数：{}，查询收费收税信息,返回结果：{}", bdcSfSsxxDTO.getSlbh(), bdcSfSsxxQO.getXmid(), JSONObject.toJSONString(bdcSfSsxxQO), bdcSfSsxxDTO);
        return bdcSfSsxxDTO;
    }

    /**
     * 获取缴款书二维码URL
     */
    private void queryBdcSfxxJksbm(String gzlslid, String xmid, String sqrlb, BdcSfSsxxDTO bdcSfSsxxDTO){
        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        bdcSlSfxxQO.setQlrlb(sqrlb);
        bdcSlSfxxQO.setGzlslid(gzlslid);
        bdcSlSfxxQO.setXmid(xmid);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxBySlbh(bdcSlSfxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            LOGGER.warn("查询收费信息入参受理编号{},查询缴费书二维码url{}", bdcSlSfxxQO.getSlbh(), bdcSlSfxxDOList.get(0).getJfsewmurl());
            bdcSfSsxxDTO.setJksbm(bdcSlSfxxDOList.get(0).getJfsewmurl());
        }
    }

    /**
     * 获取维修基金收费信息
     *
     * @param xmid 项目ID
     */
    private void queryWxjjxx(String xmid, String qlrlb, BdcSfSsxxDTO bdcSfSsxxDTO) {
        List<BdcWxjjxxDTO> bdcWxjjxxDTOList = new ArrayList<>(10);
        BdcSlWxjjxxDO queryParam = new BdcSlWxjjxxDO();
        queryParam.setXmid(xmid);
        queryParam.setQlrlb(qlrlb);
        List<BdcSlWxjjxxDO> bdcSlWxjjxxDOS = this.bdcSlWxjjxxService.queryBdcSlWxjjxx(queryParam);
        if (CollectionUtils.isNotEmpty(bdcSlWxjjxxDOS)) {
            BdcWxjjxxDTO bdcWxjjxxDTO = new BdcWxjjxxDTO();
            bdcWxjjxxDTO.setBdcSlWxjjxxDO(bdcSlWxjjxxDOS.get(0));
            bdcWxjjxxDTOList.add(bdcWxjjxxDTO);
            bdcSfSsxxDTO.setBdcWxjjxxDTOS(bdcWxjjxxDTOList);
        }
    }

    @Override
    public List<BdcSfSsxxDTO> listYjBdcSfSsxxDTO(BdcSfSsxxQO bdcSfSsxxQO) {
        Date startTime = new Date();
        List<BdcSfSsxxDTO> bdcSfSsxxDTOList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcSfSsxxQO.getYjdh())) {
            List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.bdcSlSfssDdxxService.queryBdcSlSfssDdxxByYjdh(bdcSfSsxxQO.getYjdh());
            if(CollectionUtils.isEmpty(bdcSlSfssDdxxDOList)){
                throw new AppException(ErrorCode.CUSTOM, "未获取到月结单号为：" + bdcSfSsxxQO.getYjdh() + "的订单信息。");
            }
            List<String> sfxxidList = bdcYjdhSfxxGxService.queryYjSfxxidList(bdcSfSsxxQO.getYjdh());
            if (CollectionUtils.isNotEmpty(sfxxidList)) {
                List<BdcSfxxDTO> bdcSfxxDTOList = new ArrayList<>(sfxxidList.size());
                Integer hyzfjftj = bdcSlSfssDdxxDOList.get(0).getHyzfjftj();
                // 查询缴费信息
                if(Objects.nonNull(hyzfjftj) && Objects.equals(hyzfjftj,CommonConstantUtils.JKTJ_HYZF)){
                    bdcSfxxDTOList = this.queryHyzfBdcSfxxDTO("", bdcSfSsxxQO.getSqrlb(), sfxxidList, CommonConstantUtils.SF_S_DM);
                }else{
                    bdcSfxxDTOList = this.queryBdcSfxxDTO("", bdcSfSsxxQO.getSqrlb(), sfxxidList, CommonConstantUtils.SF_S_DM);
                }
                long time = System.currentTimeMillis() - startTime.getTime();
                LOGGER.info("生成收费信息，所耗时间：{}", time);
                if (CollectionUtils.isNotEmpty(bdcSfxxDTOList)) {
                    BdcSfSsxxDTO bdcSfSsxxDTO = new BdcSfSsxxDTO();
                    bdcSfSsxxDTO.setBdcSfxxDTOS(bdcSfxxDTOList);
                    //slbh赋值月结单号
                    bdcSfSsxxDTO.setSlbh(bdcSfSsxxQO.getYjdh());
                    bdcSfSsxxDTO.setSfyj(CommonConstantUtils.SF_S_DM);

                    // 获取缴费状态，默认为未缴费。
                    String jfzt = String.valueOf(BdcSfztEnum.WJF.key());
                    for (BdcSfxxDTO bdcSfxxDTO : bdcSfxxDTOList) {
                        BdcSlSfxxDO bdcSlSfxxDO = bdcSfxxDTO.getBdcSlSfxxDO();
                        if (Objects.nonNull(bdcSlSfxxDO) && Objects.nonNull(bdcSlSfxxDO.getSfzt())) {
                            jfzt = String.valueOf(bdcSlSfxxDO.getSfzt());
                            break;
                        }
                    }
                    bdcSfSsxxDTO.setJfzt(jfzt);
                    if (bdcSfxxDTOList.get(0).getBdcSlSfxxDO() != null) {
                        BdcSlSfxxDO bdcSlSfxxDO = bdcSfxxDTOList.get(0).getBdcSlSfxxDO();
                        bdcSfSsxxDTO.setXmid(bdcSlSfxxDO.getXmid());
                        bdcSfSsxxDTO.setJksbm(bdcSlSfxxDO.getJfsewmurl());
                        //查询受理基本信息
                        if (StringUtils.isNotBlank(bdcSlSfxxDO.getGzlslid())) {
                            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(bdcSlSfxxDO.getGzlslid());
                            if (Objects.nonNull(bdcSlJbxxDO)) {
                                bdcSfSsxxDTO.setFwuuid(bdcSlJbxxDO.getFwuuid());
                            }
                        }
                        //获取项目信息
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setXmid(bdcSfSsxxDTO.getXmid());
                        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            bdcSfSsxxDTO.setQxdm(bdcXmDOList.get(0).getQxdm());
                        }
                        //获取权利人信息
                        BdcQlrQO bdcQlrQO = new BdcQlrQO();
                        bdcQlrQO.setXmid(bdcSfSsxxDTO.getXmid());
                        bdcQlrQO.setQlrlb(bdcSlSfxxDO.getQlrlb());
                        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                            List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
                            for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                                BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                                acceptDozerMapper.map(bdcQlrDO, bdcSlSqrDO);
                                bdcSlSqrDOList.add(bdcSlSqrDO);
                            }
                            bdcSfSsxxDTO.setBdcSlSqrDOS(bdcSlSqrDOList);
                        }
                    }
                    bdcSfSsxxDTOList.add(bdcSfSsxxDTO);
                }
            }
        }
        long time = System.currentTimeMillis() - startTime.getTime();
        LOGGER.info("月结单号:{},申请人类别:{},查询收费收税信息,返回结果数量：{},所耗时间：{}", bdcSfSsxxQO.getYjdh(), bdcSfSsxxQO.getSqrlb(), bdcSfSsxxDTOList.size(), time);
        return bdcSfSsxxDTOList;
    }

    /**
     * 将月结单号关联的收费项目信息，按 sfxmbz 进行分组，计算所有数量和 所有的ssje 信息
     */
    private List<BdcSfxxDTO> queryHyzfBdcSfxxDTO(String xmid, String sqrlb, List<String> sfxxidList, Integer sfyj) {
        List<BdcSfxxDTO> bdcSfxxDTOList = new ArrayList<>();
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxPl(xmid, sqrlb, sfxxidList, sfyj, true);
        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
            BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
            bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDOList.get(0));
            // 根据收费项目标准，分组查询收费项目信息
            List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.queryYjSfxmxxGroupBySfxmbz(xmid, sqrlb, sfxxidList, sfyj, true);
            Double hj = 0.0;
            if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                for(BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList){
                    bdcSlSfxmDO.setSfxmid(UUIDGenerator.generate16());
                    bdcSlSfxmDO.setSfxxid(bdcSfxxDTO.getBdcSlSfxxDO().getSfxxid());
                }
                hj += bdcSlSfxmDOList.stream().filter(t -> t.getSsje() != null).mapToDouble(BdcSlSfxmDO::getSsje).sum();
                bdcSfxxDTO.setBdcSlSfxmDOS(bdcSlSfxmDOList);
                bdcSfxxDTO.getBdcSlSfxxDO().setHj(hj);
            }
            DsfSfxxDTO dsfSfxxDTO = new DsfSfxxDTO();
            dsfSfxxDTO.setUserName(userManagerUtils.getCurrentUserName());
            bdcSfxxDTO.setDsfSfxxDTO(dsfSfxxDTO);
            bdcSfxxDTOList.add(bdcSfxxDTO);
        }
        return bdcSfxxDTOList;
    }

    @Override
    public List<BdcSfxxDTO> queryBdcSfxxDTO(String xmid, String sqrlb, List<String> sfxxidList, Integer sfyj) {
        List<BdcSfxxDTO> bdcSfxxDTOList = new ArrayList<>();
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>(10);
        if(CollectionUtils.isNotEmpty(sfxxidList) && sfxxidList.size() > 500){
            List<List> subList = ListUtils.subList(sfxxidList, 500);
            for (List data : subList) {
                List copyList = new ArrayList(data);
                List<BdcSlSfxxDO> sfxxlist = bdcSlSfxxService.listBdcSlSfxxPl(xmid, sqrlb, copyList, sfyj, true);
                if(CollectionUtils.isNotEmpty(sfxxlist)){
                    bdcSlSfxxDOList.addAll(sfxxlist);
                }
            }
        }else{
            bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxPl(xmid, sqrlb, sfxxidList, sfyj, true);
        }
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
                bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
                List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                    bdcSfxxDTO.setBdcSlSfxmDOS(bdcSlSfxmDOList);
                }
                DsfSfxxDTO dsfSfxxDTO = new DsfSfxxDTO();
                dsfSfxxDTO.setUserName(userManagerUtils.getCurrentUserName());
                bdcSfxxDTO.setDsfSfxxDTO(dsfSfxxDTO);
                bdcSfxxDTOList.add(bdcSfxxDTO);

            }
        }
        return bdcSfxxDTOList;
    }

    private List<BdcSfxxDTO> quertBdcSfxx(String xmid, String sqrlb, List<String> sfxxidList, Integer sfyj, boolean sftdsyj) {
        List<BdcSfxxDTO> bdcSfxxDTOList = new ArrayList<>();
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxPl(xmid, sqrlb, sfxxidList, sfyj, true);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                BdcSfxxDTO bdcSfxxDTO = new BdcSfxxDTO();
                List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                    if (sftdsyj) {
                        bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
                    } else {
                        bdcSlSfxmDOList = bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> !StringUtils.equals(bdcSlSfxmDO.getSfxmdm(), tdsyqsfxmdm)).collect(Collectors.toList());
                    }
                    bdcSlSfxxDO.setHj(bdcSlSfxmDOList.stream().filter(bdcSlSfxmDO -> Objects.nonNull(bdcSlSfxmDO.getSsje())).mapToDouble(BdcSlSfxmDO::getSsje).sum());
                    bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
                    //处理单价数据
                    List<BdcSlSfxmVO> bdcSlSfxmVOList = new ArrayList<>(bdcSlSfxmDOList.size());
                    for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                        BdcSlSfxmVO bdcSlSfxmVO = new BdcSlSfxmVO();
                        BeanUtils.copyProperties(bdcSlSfxmDO, bdcSlSfxmVO);
                        if (Objects.isNull(bdcSlSfxmDO.getSfxmdj())) {
                            double dj = bdcSlSfxmDO.getYsje() / (Objects.nonNull(bdcSlSfxmDO.getSl()) ? bdcSlSfxmDO.getSl() : 1);
                            bdcSlSfxmVO.setSfxmdj(dj);
                        } else {
                            bdcSlSfxmVO.setSfxmdj(bdcSlSfxmDO.getSfxmdj());
                        }
                        bdcSlSfxmVOList.add(bdcSlSfxmVO);
                    }
                    bdcSfxxDTO.setBdcSlSfxmVOList(bdcSlSfxmVOList);
                }
                DsfSfxxDTO dsfSfxxDTO = new DsfSfxxDTO();
                dsfSfxxDTO.setUserName(userManagerUtils.getCurrentUserName());
                bdcSfxxDTO.setDsfSfxxDTO(dsfSfxxDTO);
                bdcSfxxDTOList.add(bdcSfxxDTO);
            }
        }
        return bdcSfxxDTOList;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private void organizeSfQlrxx(BdcSlSqrSfxxDTO bdcSlSqrSfxxDTO, String xmid, String qlrlb) {
        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, qlrlb);
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            String qlrmc = StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getSqrmc", ",");
            String qlrdlrmc = StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getDlrmc", ",");
            String qlrdlrdh = StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getDlrdh", ",");
            String lxdh = StringToolUtils.resolveBeanToAppendStr(bdcSlSqrDOList, "getDh", ",");
            bdcSlSqrSfxxDTO.setMc(qlrmc);
            bdcSlSqrSfxxDTO.setDlrmc(qlrdlrmc);
            bdcSlSqrSfxxDTO.setDlrlxdh(qlrdlrdh);
            bdcSlSqrSfxxDTO.setLxdh(lxdh);
        }

    }

    /**
     * @param bdcXmDOList 项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据项目获取收费项目登记费数量
     * @date : 2019/12/26 11:22
     */
    @Override
    public BdcSlSfxmSlDTO querySfxmDjfSl(List<BdcXmDO> bdcXmDOList) {
        BdcSlSfxmSlDTO bdcSlSfxmSlDTO = new BdcSlSfxmSlDTO();
        //先根据房屋用途分为住宅和非住宅收费
        List<BdcXmDO> bdcXmZzList = new ArrayList<>();
        List<BdcXmDO> bdcXmFzzList = new ArrayList<>();
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            if (bdcXmDO.getDzwyt() != null && zzfwytdmList.contains(bdcXmDO.getDzwyt().toString())) {
                bdcXmZzList.add(bdcXmDO);
            } else {
                bdcXmFzzList.add(bdcXmDO);
            }
        }
        //判断批量选择的不动产单元是否属于多个宗地，如果是则只收一份登记费；如果不是，需要按宗地数量收多份；
        //判断用途，如果存在不同的用途，则取最大金额的收费项
        //房屋抵押权首次进行如下判断收费
        if (fwdyqscdjxlList.contains(bdcXmDOList.get(0).getDjxl())) {
            List<BdcXmDO> zdBdcXmList = new ArrayList<>(bdcXmDOList.size());
            for (BdcXmDO bdcXm : bdcXmDOList) {
                if (bdcXm.getBdcdyh().length() > 19) {
                    String zdBdcdyh = bdcXm.getBdcdyh().substring(0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                    bdcXm.setBdcdyh(zdBdcdyh);
                    zdBdcXmList.add(bdcXm);
                }
            }
            //根据宗地的不动产单元号去重
            Set<BdcXmDO> newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getBdcdyh));
            newBdcXm.addAll(zdBdcXmList);
            if (newBdcXm.size() == 1) {
                //同一宗地只收一份登记费

                if (CollectionUtils.isNotEmpty(bdcXmFzzList)) {
                    //只要有非住宅的就按照非住宅收（按费用高的收）
                    bdcSlSfxmSlDTO.setFzzdjfsl(1);
                } else if (CollectionUtils.isNotEmpty(bdcXmZzList) && CollectionUtils.isEmpty(bdcXmFzzList)) {
                    bdcSlSfxmSlDTO.setZzdjfsl(1);
                }
            } else if (newBdcXm.size() > 1) {
                //不同宗地,根据已经组织好的宗地不动产单元号分组，区分不同的宗地
                Map<String, List<BdcXmDO>> bdcXmMap = zdBdcXmList.stream().collect(Collectors.groupingBy(BdcXmDO::getBdcdyh));
                int zzsl = 0;
                int fzzsl = 0;
                for (Map.Entry<String, List<BdcXmDO>> entry : bdcXmMap.entrySet()) {
                    List<BdcXmDO> zdBdcXmZzList = new ArrayList<>();
                    List<BdcXmDO> zdBdcXmFzzList = new ArrayList<>();
                    List<BdcXmDO> bdcXmList = entry.getValue();
                    for (BdcXmDO bdcXmDO : bdcXmList) {
                        if (bdcXmDO.getDzwyt() != null && zzfwytdmList.contains(bdcXmDO.getDzwyt().toString())) {
                            zdBdcXmZzList.add(bdcXmDO);
                        } else {
                            zdBdcXmFzzList.add(bdcXmDO);
                        }
                    }
                    if (CollectionUtils.isNotEmpty(zdBdcXmFzzList)) {
                        //同一宗地，按费用高的收
                        fzzsl++;
                    } else if (CollectionUtils.isNotEmpty(zdBdcXmZzList) && CollectionUtils.isEmpty(zdBdcXmFzzList)) {
                        zzsl++;
                    }
                }
                if (zzsl > 0) {
                    bdcSlSfxmSlDTO.setZzdjfsl(zzsl);
                }
                if (fzzsl > 0) {
                    bdcSlSfxmSlDTO.setFzzdjfsl(fzzsl);
                }
            }
        } else {
            //住宅费
            if (CollectionUtils.isNotEmpty(bdcXmZzList)) {
                //车位，车库，成套住宅，储藏室为80  仓储、其他均为550
                bdcSlSfxmSlDTO.setZzdjfsl(bdcXmZzList.size());
            }
            //非住宅费
            if (CollectionUtils.isNotEmpty(bdcXmFzzList)) {
                bdcSlSfxmSlDTO.setFzzdjfsl(bdcXmFzzList.size());
            }
        }
        return bdcSlSfxmSlDTO;
    }

    @Override
    public void updateSfSszt(BdcSfSsxxDTO bdcSfSsxxDTO) {
        if (bdcSfSsxxDTO == null || (CollectionUtils.isEmpty(bdcSfSsxxDTO.getBdcSfxxDTOS()) && CollectionUtils.isEmpty(bdcSfSsxxDTO.getBdcSwxxDTOS()))) {
            throw new AppException("未获取到收费收税更新数据");
        }
        if (bdcSfSsxxDTO.getJfzt() == null) {
            throw new AppException("未获取缴费状态");
        }
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs("BDC_ZD_SFZT");
        bdcZdDsfzdgxDO.setDsfzdz(bdcSfSsxxDTO.getJfzt());
        bdcZdDsfzdgxDO.setDsfxtbs("updateJfzt");
        BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        if (result == null || StringUtils.isBlank(result.getBdczdz())) {
            throw new AppException("未获取到第三方字典对照");
        }
        Integer sfzt = Integer.parseInt(result.getBdczdz());
        this.modifySfxxSfzt(bdcSfSsxxDTO, sfzt);
        this.modifyHsxxZt(bdcSfSsxxDTO, sfzt);
        this.modifyWxjjxxZt(bdcSfSsxxDTO, sfzt);
        this.modifyYjSfxxZt(bdcSfSsxxDTO, sfzt);
    }

    /**
     * 更新收费信息收费状态
     *
     * @param bdcSfSsxxDTO 不动产收费收税信息
     * @param sfzt         收费状态
     */
    private void modifySfxxSfzt(BdcSfSsxxDTO bdcSfSsxxDTO, Integer sfzt) {
        //更新收费状态
        if (CollectionUtils.isNotEmpty(bdcSfSsxxDTO.getBdcSfxxDTOS())) {
            for (BdcSfxxDTO bdcSfxxDTO : bdcSfSsxxDTO.getBdcSfxxDTOS()) {
                if (bdcSfxxDTO.getBdcSlSfxxDO() == null || StringUtils.isBlank(bdcSfxxDTO.getBdcSlSfxxDO().getSfxxid())) {
                    throw new MissingArgumentException("缺失参数sfxxid");
                }
                //只更新收费状态
                BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(bdcSfxxDTO.getBdcSlSfxxDO().getSfxxid());
                if (bdcSlSfxxDO != null) {
                    bdcSlSfxxDO.setSfzt(sfzt);
                    if (StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getJkfs())) {
                        //更新缴款方式
                        bdcSlSfxxDO.setJkfs((bdcSfxxDTO.getBdcSlSfxxDO().getJkfs()));
                    }
                    // 根据一卡清接口传的jfType标识判断更新银行缴库入库状态
                    if(Objects.equals(sfzt, BdcSfztEnum.YJF.key()) && StringUtils.equals("DK", bdcSfSsxxDTO.getJfType())){
                        bdcSlSfxxDO.setYhjkrkzt(CommonConstantUtils.SF_S_DM);
                    }
                    bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                }
            }
        }
    }

    /**
     * 更新核税信息状态
     *
     * @param bdcSfSsxxDTO 不动产收费收税信息
     * @param sfzt         收费状态
     */
    private void modifyHsxxZt(BdcSfSsxxDTO bdcSfSsxxDTO, Integer sfzt) {
        //更新支付状态
        if (CollectionUtils.isNotEmpty(bdcSfSsxxDTO.getBdcSwxxDTOS())) {
            for (BdcSwxxDTO bdcSwxxDTO : bdcSfSsxxDTO.getBdcSwxxDTOS()) {
                if (bdcSwxxDTO.getBdcSlHsxxDO() == null || StringUtils.isBlank(bdcSwxxDTO.getBdcSlHsxxDO().getHsxxid())) {
                    throw new MissingArgumentException("缺失参数hsxxid");
                }
                //只更新支付状态
                BdcSlHsxxDO bdcSlHsxxQO = new BdcSlHsxxDO();
                bdcSlHsxxQO.setHsxxid(bdcSwxxDTO.getBdcSlHsxxDO().getHsxxid());
                List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxQO);
                if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                    BdcSlHsxxDO updateBdcSlHsxx = bdcSlHsxxDOList.get(0);
                    updateBdcSlHsxx.setJfzt(sfzt);
                    if(Objects.equals(sfzt, BdcSfztEnum.YJF.key()) && StringUtils.equals("DK", bdcSfSsxxDTO.getJfType())){
                        updateBdcSlHsxx.setWszt(CommonConstantUtils.SF_S_DM);
                    }
                    bdcSlHsxxService.updateBdcSlHsxx(updateBdcSlHsxx);
                }
            }
        }
    }

    /**
     * 更新维修基金信息状态
     *
     * @param bdcSfSsxxDTO 不动产收费收税信息
     * @param sfzt         收费状态
     */
    private void modifyWxjjxxZt(BdcSfSsxxDTO bdcSfSsxxDTO, Integer sfzt) {
        if (CollectionUtils.isNotEmpty(bdcSfSsxxDTO.getBdcWxjjxxDTOS())) {
            for (BdcWxjjxxDTO bdcWxjjxxDTO : bdcSfSsxxDTO.getBdcWxjjxxDTOS()) {
                if (Objects.isNull(bdcWxjjxxDTO.getBdcSlWxjjxxDO())
                        || StringUtils.isBlank(bdcWxjjxxDTO.getBdcSlWxjjxxDO().getWxjjxxid())) {
                    throw new MissingArgumentException("缺失参数维修基金ID");
                }
                BdcSlWxjjxxDO bdcSlWxjjxxDO = new BdcSlWxjjxxDO();
                bdcSlWxjjxxDO.setWxjjxxid(bdcWxjjxxDTO.getBdcSlWxjjxxDO().getWxjjxxid());
                bdcSlWxjjxxDO.setSfzt(sfzt);
                this.bdcSlWxjjxxService.updateBdcSlWxjjxx(bdcSlWxjjxxDO);
            }
        }
    }

    /**
     * 更新月结收费信息状态
     *
     * @param bdcSfSsxxDTO 不动产收费收税信息
     * @param sfzt         收费状态
     */
    private void modifyYjSfxxZt(BdcSfSsxxDTO bdcSfSsxxDTO, Integer sfzt) {
        // 判断是否月结，月结数据更新月结订单信息状态
        if (CommonConstantUtils.SF_S_DM.equals(bdcSfSsxxDTO.getSfyj()) && StringUtils.isNotBlank(bdcSfSsxxDTO.getSlbh())) {
            BdcYjSfDdxxDO bdcYjSfDdxxDO = new BdcYjSfDdxxDO();
            bdcYjSfDdxxDO.setYjdh(bdcSfSsxxDTO.getSlbh());
            bdcYjSfDdxxDO.setDdzt(sfzt);
            bdcYjSfDdxxDO.setDdscsj(new Date());
            this.bdcYjSfDdxxService.updateYjSfDdxxByYjdh(bdcYjSfDdxxDO);
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return boolean
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 回传收费收税状态（缴费状态、缴库状态）给大云
     */
    @Override
    public boolean saveSfssxxZtToProcess(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        // 同步缴库状态给大云
        this.saveJkxxToProcess(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList) || CollectionUtils.size(bdcXmDOList) != 1) {
            return false;
        }
        int jfzt = BdcSfztEnum.YJF.key();
        String xmid = bdcXmDOList.get(0).getXmid();
        BdcSfSsxxQO bdcSfSsxxQO = new BdcSfSsxxQO();
        bdcSfSsxxQO.setXmid(xmid);
        bdcSfSsxxQO.setCxbz(CommonConstantUtils.SFSSXX_CXBZ_SF_SS);
        bdcSfSsxxQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
        BdcSfSsxxDTO bdcSfSsxxDTOQlr = this.queryBdcSfSsxxDTO(bdcSfSsxxQO);
        bdcSfSsxxQO.setSqrlb(CommonConstantUtils.QLRLB_YWR);
        BdcSfSsxxDTO bdcSfSsxxDTOYwr = this.queryBdcSfSsxxDTO(bdcSfSsxxQO);

        // 查询不到税费信息
        if ((null == bdcSfSsxxDTOQlr || (CollectionUtils.isEmpty(bdcSfSsxxDTOQlr.getBdcSfxxDTOS()) && CollectionUtils.isEmpty(bdcSfSsxxDTOQlr.getBdcSwxxDTOS())))
                && (null == bdcSfSsxxDTOYwr || (CollectionUtils.isEmpty(bdcSfSsxxDTOYwr.getBdcSfxxDTOS()) && CollectionUtils.isEmpty(bdcSfSsxxDTOYwr.getBdcSwxxDTOS())))) {
            return this.saveSfssxxZtToProcess(gzlslid, BdcSfztEnum.WJF.key());
        }
        // 合并权利人和义务人的缴费信息
        List<BdcSfxxDTO> bdcSfxxDTOList = new ArrayList<>();
        if (bdcSfSsxxDTOQlr != null && CollectionUtils.isNotEmpty(bdcSfSsxxDTOQlr.getBdcSfxxDTOS())) {
            bdcSfxxDTOList.addAll(bdcSfSsxxDTOQlr.getBdcSfxxDTOS());
        }
        if (CollectionUtils.isNotEmpty(bdcSfSsxxDTOYwr.getBdcSfxxDTOS())) {
            bdcSfxxDTOList.addAll(bdcSfSsxxDTOYwr.getBdcSfxxDTOS());
        }
        if (CollectionUtils.isNotEmpty(bdcSfxxDTOList)) {
            for (BdcSfxxDTO bdcSfxxDTO : bdcSfxxDTOList) {
                if (!BdcSfztEnum.YJF.key().equals(bdcSfxxDTO.getBdcSlSfxxDO().getSfzt())) {
                    jfzt = BdcSfztEnum.WJF.key();
                    break;
                }
            }
        }
        // 合并权利人和义务人的缴税信息
        if (BdcSfztEnum.YJF.key().equals(jfzt)) {
            List<BdcSwxxDTO> bdcSwxxDTOList = new ArrayList<>();
            if (bdcSfSsxxDTOQlr != null && CollectionUtils.isNotEmpty(bdcSfSsxxDTOQlr.getBdcSwxxDTOS())) {
                bdcSwxxDTOList.addAll(bdcSfSsxxDTOQlr.getBdcSwxxDTOS());
            }
            if (CollectionUtils.isNotEmpty(bdcSfSsxxDTOYwr.getBdcSwxxDTOS())) {
                bdcSwxxDTOList.addAll(bdcSfSsxxDTOYwr.getBdcSwxxDTOS());
            }
            if (CollectionUtils.isNotEmpty(bdcSwxxDTOList)) {
                for (BdcSwxxDTO bdcSwxxDTO : bdcSwxxDTOList) {
                    if (!CommonConstantUtils.SF_S_DM.equals(bdcSwxxDTO.getBdcSlHsxxDO().getWszt())) {
                        jfzt = BdcSfztEnum.WJF.key();
                        break;
                    }
                }
            }
        }
        return this.saveSfssxxZtToProcess(gzlslid, jfzt);
    }

    // 保存缴库信息至大云平台
    private void saveJkxxToProcess(String gzlslid) {
        try {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                int yhjkrkzt = CommonConstantUtils.SF_S_DM;
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if (this.queryYhjkrkzt(bdcSlSfxxDO) == CommonConstantUtils.SF_F_DM) {
                        yhjkrkzt = CommonConstantUtils.SF_F_DM;
                        break;
                    }
                }
                syncJkxxToProcess(gzlslid, yhjkrkzt);
            }
        } catch (Exception e) {
            LOGGER.error("保存缴库信息至大云失败。", e);
        }
    }

    @Override
    public boolean checkIsXsjf(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            //只要有一条线上缴费就是线上缴费
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                if (StringUtils.equals(CommonConstantUtils.JKFS_XSJF, bdcSlSfxxDO.getJkfs())) {
                    return true;
                }
            }
        }
        return false;
    }

    //-1 表示线下缴费 0 表示缴库失败 1表示缴库成功 2表示税的缴库状态为空，登簿时可以被忽略
    @Override
    public Integer checkjfqk(String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "缺失工作流实例ID!");
        //需要排除月结收费信息
        BdcSlSfxxDO sfxxQO = new BdcSlSfxxDO();
        sfxxQO.setGzlslid(gzlslid);
        sfxxQO.setSfyj(CommonConstantUtils.SF_F_DM);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.queryBdcSlSfxx(sfxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            // 只要有一条线上缴费就是线上缴费
            final BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
            // 判断是否为线上缴费
            if (StringUtils.equals(CommonConstantUtils.JKFS_XSJF, bdcSlSfxxDO.getJkfs())) {
                return getVerifyJkzt(bdcSlSfxxDO);
            }
        }
        return -1;
    }

    @Override
    public Integer checkSfqk(String gzlslid) {
        // 是否缴费
        Integer sfjf = BdcSfztEnum.YJF.key();
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                if ((null == bdcSlSfxxDO.getSfzt() || BdcSfztEnum.WJF.key().equals(bdcSlSfxxDO.getSfzt())
                        || BdcSfztEnum.BFJF.key().equals(bdcSlSfxxDO.getSfzt()))
                        && (bdcSlSfxxDO.getHj() != null && bdcSlSfxxDO.getHj() != 0)) {
                    sfjf = BdcSfztEnum.WJF.key();
                    break;
                }
            }
        }
        return sfjf;

    }

    /**
     * @param gzlslid 工作流实例ID
     * @return Integer {@code 0} 未上传凭证 {@code 1} 已上传凭证 {@code 2} 线上缴费，不做验证
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证线下缴费是否已上传税费缴纳凭证
     */
    @Override
    public Integer checkXxJfPz(String gzlslid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "缺失工作流实例ID!");
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            // 只要有一条线上缴费就是线上缴费
            final BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
            // 判断是否为线上缴费
            if (StringUtils.equals(CommonConstantUtils.JKFS_XSJF, bdcSlSfxxDO.getJkfs())) {
                return 2;
            }
        }
        // 线下缴费验证缴纳凭证
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclService.listBdcSlSjcl(gzlslid, "税费缴纳凭证");
        if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
            for (BdcSlSjclDO bdcSlSjclDO : bdcSlSjclDOList) {
                Integer clys = bdcSlSjclDO.getYs();
                if (null != clys && clys > 0) {
                    return 1;
                }
            }
        }

        return 0;
    }

    private int getVerifyJkzt(BdcSlSfxxDO bdcSlSfxxDO) {
        final Integer yhjkrkzt = bdcSlSfxxDO.getYhjkrkzt();
        // 判断收费信息的缴库状态：不为1时，缴库失败。
        if (Objects.isNull(yhjkrkzt) || CommonConstantUtils.SF_F_DM.equals(yhjkrkzt)) {
            return CommonConstantUtils.SF_F_DM;
        }
        // 获取核税信息的银行缴库入库状态
        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
        bdcSlHsxxDO.setXmid(bdcSlSfxxDO.getXmid());
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxDO);
        // 判断是否涉税
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
            final Integer hsjkrkzt = bdcSlHsxxDOList.get(0).getYhjkrkzt();
            // 核税中的缴库状态为空时，登簿验证时，页面进行提示，并可以忽略继续登簿
            if (Objects.isNull(hsjkrkzt)) {
                return 2;
            }
            if (CommonConstantUtils.SF_F_DM.equals(hsjkrkzt)) {
                return CommonConstantUtils.SF_F_DM;
            }
        }
        return CommonConstantUtils.SF_S_DM;
    }

    @Override
    public void updateSlSfSsxxYhJkrkzt(BdcYhjkrkztDTO bdcYhjkrkztDTO) {
        if (bdcYhjkrkztDTO == null || StringUtils.isBlank(bdcYhjkrkztDTO.getSlbh())) {
            throw new MissingArgumentException("缺失查询收费收税信息参数！");
        }
        LOGGER.info("受理编号:{},自动更新缴库入库状态,返回结果：{}", bdcYhjkrkztDTO.getSlbh(), JSONObject.toJSONString(bdcYhjkrkztDTO));

        // 判断是否月结，月结调用月结更新缴库状态方法，非月结数据，直接更新收费与核税信息
        if (CommonConstantUtils.SF_S_DM.equals(bdcYhjkrkztDTO.getSfyj())) {
            this.updateYjSfssxxYhJkrkzt(bdcYhjkrkztDTO);
        } else {
            // 更新收费信息
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcYhjkrkztDTO.getBdcSlSfxxDOList();
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO sfxxDO : bdcSlSfxxDOList) {
                    // 校验接口返回的收费信息中除sfxxid之外，其余属性有值时，进行更新操作
                    if (!CheckParameter.checkPartElementsNotAllExist(sfxxDO,
                            Arrays.asList(new Object[]{"serialVersionUID", "sfxxid"}))) {
                        //已缴库更新所有字段,未缴库只更新状态
                        if(CommonConstantUtils.SF_S_DM.equals(sfxxDO.getYhjkrkzt())) {
                            sfxxDO.setJkrksj(new Date());
                            bdcSlSfxxService.updateBdcSlSfxx(sfxxDO);
                        }else{
                            BdcSlSfxxDO wjkSfxx =new BdcSlSfxxDO();
                            wjkSfxx.setSfxxid(sfxxDO.getSfxxid());
                            wjkSfxx.setYhjkrkzt(CommonConstantUtils.SF_F_DM);
                            bdcSlSfxxService.updateBdcSlSfxx(wjkSfxx);
                        }
                    }
                }
            }
            // 更新三要素信息
            List<BdcSlSysxxDO> bdcSlSysxxDOList = bdcYhjkrkztDTO.getBdcSlSysxxDOList();
            if (CollectionUtils.isNotEmpty(bdcSlSysxxDOList)) {
                for (BdcSlSysxxDO bdcSlSysxxDO : bdcSlSysxxDOList) {
                    if (StringUtils.isNotBlank(bdcSlSysxxDO.getDzsphm())) {
                        //已缴库更新所有字段,未缴库只更新状态
                        if(CommonConstantUtils.SF_S_DM.equals(bdcSlSysxxDO.getYhjkrkzt())) {
                            bdcSlSysxxDO.setJkrksj(new Date());
                            bdcSlSysService.updateBdcSlSysxx(bdcSlSysxxDO.getDzsphm(), bdcSlSysxxDO);
                        }else{
                            BdcSlSysxxDO wjkSysxx =new BdcSlSysxxDO();
                            wjkSysxx.setYhjkrkzt(CommonConstantUtils.SF_F_DM);
                            bdcSlSysService.updateBdcSlSysxx(bdcSlSysxxDO.getDzsphm(), wjkSysxx);
                        }
                    }
                }
                //更新核税信息
                List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxxByGzlslid("", bdcYhjkrkztDTO.getSlbh());
                if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                    for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                        List<BdcSlSysxxDO> bdcSlSysxxDOS = bdcSlSysService.listBdcSlSysxx(bdcSlHsxxDO.getHsxxid(), "", "");
                        if (CollectionUtils.isNotEmpty(bdcSlSysxxDOS)) {
                            List<BdcSlSysxxDO> wjkSysxx = bdcSlSysxxDOS.stream().filter(bdcSlSysxxDO -> !CommonConstantUtils.SF_S_DM.equals(bdcSlSysxxDO.getYhjkrkzt())).collect(Collectors.toList());
                            if (CollectionUtils.isEmpty(wjkSysxx)) {
                                bdcSlHsxxDO.setYhjkrkzt(CommonConstantUtils.SF_S_DM);
                                bdcSlHsxxDO.setJkrksj(new Date());
                                bdcSlHsxxService.updateBdcSlHsxx(bdcSlHsxxDO);
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 推送缴库完成后，处理月结单号缴库入库状态
     */
    private void updateYjSfssxxYhJkrkzt(BdcYhjkrkztDTO bdcYhjkrkztDTO) {
        if (StringUtils.isBlank(bdcYhjkrkztDTO.getSlbh())) {
            throw new MissingArgumentException("缺失月结单号参数！");
        }
        String yjdh = bdcYhjkrkztDTO.getSlbh();
        if (CollectionUtils.isNotEmpty(bdcYhjkrkztDTO.getBdcSlSfxxDOList())) {
            BdcSlSfxxDO bdcSlSfxxDO = bdcYhjkrkztDTO.getBdcSlSfxxDOList().get(0);

            List<String> sfxxidList = bdcYjdhSfxxGxService.queryYjSfxxidList(yjdh);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxPl(null, null, sfxxidList, CommonConstantUtils.SF_S_DM, true);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO sfxx : bdcSlSfxxDOList) {
                    //已缴库更新所有字段,未缴库只更新状态
                    if(CommonConstantUtils.SF_S_DM.equals(bdcSlSfxxDO.getYhjkrkzt())) {
                        sfxx.setYhjkrkzt(bdcSlSfxxDO.getYhjkrkzt());
                        sfxx.setJfsewmurl(bdcSlSfxxDO.getJfsewmurl());
                        sfxx.setJkrksj(new Date());
                        this.bdcSlSfxxService.updateBdcSlSfxx(sfxx);
                    }else{
                        sfxx.setYhjkrkzt(bdcSlSfxxDO.getYhjkrkzt());
                        this.bdcSlSfxxService.updateBdcSlSfxx(sfxx);
                    }
                }
            }
        }

    }

    private void modifyAllBdcSlSfxxBySlbh(String slbh, BdcSlSfxxDO bdcSlSfxxDO) {
        Preconditions.checkArgument(StringUtils.isNotBlank(slbh), "未获取到受理编号!");
        if (StringUtils.isNotBlank(slbh)) {
            String gzlslid = bdcXmFeignService.queryGzlslid(slbh);
            Preconditions.checkArgument(StringUtils.isNotBlank(gzlslid), "未查询到对应项目!");
            // 获取所有收费信息并更新
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO sfxxDO : bdcSlSfxxDOList) {
                    bdcSlSfxxDO.setSfxxid(sfxxDO.getSfxxid());
                    if (!CheckParameter.checkPartElementsNotAllExist(sfxxDO,
                            Arrays.asList(new Object[]{"serialVersionUID", "sfxxid"}))) {
                        bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                    }
                }
            }
        }
    }

    /**
     * 退款申请
     *
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     */
    @Override
    public BdcSlSfxxDO sfxxTksq(String gzlslid) {
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        if (StringUtils.isAnyBlank(gzlslid)) {
            throw new MissingArgumentException("缺失接口参数工作流实例ID");
        }

        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList) && !BdcSfztEnum.YJF.key().equals(bdcSlSfxxDOList.get(0).getSfzt())) {
            throw new AppException("当前非已缴费状态");
        }

        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            throw new AppException("项目集合为空");
        }
        Map<String, String> paramMap = new HashMap<>(8);
        paramMap.put("slbh", bdcXmDTOList.get(0).getSlbh());
        paramMap.put("xmid", bdcXmDTOList.get(0).getXmid());
        Object response = exchangeInterfaceFeignService.requestInterface("wwsq_ykqtkcz", paramMap);
        if (response != null) {
            LOGGER.info("受理编号:{},退款申请接口调用成功,返回结果：{}", bdcXmDTOList.get(0).getSlbh(), response);
            bdcSlSfxxDO = JSONObject.parseObject(JSONObject.toJSONString(response), BdcSlSfxxDO.class);
            if (bdcSlSfxxDO != null) {
                if (!CheckParameter.checkAnyParameter(bdcSlSfxxDO)) {
                    throw new AppException("接口返回信息为空" + JSONObject.toJSONString(bdcSlSfxxDO));

                }
                // 更新所有收费信息的 收费状态和退款单号
                this.modifyAllBdcSlSfxxBySlbh(bdcXmDTOList.get(0).getSlbh(), bdcSlSfxxDO);
            }
        }
        return bdcSlSfxxDO;
    }

    @Override
    public BdcSlSfxxDO queryTkqk(String gzlslid) {
        BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
        if (StringUtils.isAnyBlank(gzlslid)) {
            throw new MissingArgumentException("缺失接口参数工作流实例ID");
        }
        //判断是否已经申请退款
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList) || StringUtils.isBlank(bdcSlSfxxDOList.get(0).getTkdh())) {
            throw new AppException("不存在收费信息或者未申请退款");
        }
        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDTOList)) {
            throw new AppException("项目集合为空");
        }
        Map<String, String> paramMap = new HashMap<>(8);
        paramMap.put("slbh", bdcXmDTOList.get(0).getSlbh());
        paramMap.put("xmid", bdcXmDTOList.get(0).getXmid());
        paramMap.put("tkdh", bdcSlSfxxDOList.get(0).getTkdh());
        Object response = exchangeInterfaceFeignService.requestInterface("wwsq_ykqtkztcx", paramMap);
        if (response != null) {
            LOGGER.info("受理编号:{},退款结果查询接口调用成功,返回结果：{}", bdcXmDTOList.get(0).getSlbh(), response);
            bdcSlSfxxDO = JSONObject.parseObject(JSONObject.toJSONString(response), BdcSlSfxxDO.class);
            if (bdcSlSfxxDO != null) {
                if (!CheckParameter.checkAnyParameter(bdcSlSfxxDO)) {
                    throw new AppException("接口返回信息为空" + JSONObject.toJSONString(bdcSlSfxxDO));
                }
                //更新所有收费信息收费状态
                this.modifyAllBdcSlSfxxBySlbh(bdcXmDTOList.get(0).getSlbh(), bdcSlSfxxDO);
            }
        }
        return bdcSlSfxxDO;
    }

    @Override
    public void autoUpdateSfztYhy(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失工作流实例ID");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        //互联网涉税不自动更新
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && !CommonConstantUtils.SPLY_WWSQ_YCSL.equals(bdcXmDOList.get(0).getSply())) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    if (bdcSlSfxxDO.getSfzt() == null) {
                        //只有收费状态为空才进行更新
                        bdcSlSfxxDO.setSfzt(BdcSfztEnum.YHY.key());
                        bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                    }

                }
            }
        }
    }

    /**
     * 通过<code>PosJfDTO</code>参数中的参考号、商户代码、终端号判断：
     * 1、有参数，为POS缴费成功，需记录参数并通知政融平台
     * 2、无参数，为手动触发通知政融，需获取接口参数再通知
     *
     * @param posJfDTO POS缴费结果信息
     * @return 通知结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public Object tzzrpt(PosJfDTO posJfDTO) {
        if (StringUtils.isAnyBlank(posJfDTO.getCkh(), posJfDTO.getDdbh(), posJfDTO.getShdm(), posJfDTO.getZdh())) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.listBdcSlSfxxByGzlslid(posJfDTO.getGzlslid());
            if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
                throw new MissingArgumentException("未获取到收费信息。");
            }
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
            if (StringUtils.isAnyBlank(bdcSlSfxxDO.getJfsbm(), bdcSlSfxxDO.getCkh(), bdcSlSfxxDO.getShdm(), bdcSlSfxxDO.getZdh())) {
                throw new MissingArgumentException("未获取到订单编号、参考号、商户代码、终端号、工作流实例ID参数，请POS缴费后再通知政融！");
            }
            acceptDozerMapper.map(bdcSlSfxxDOList.get(0), posJfDTO);
            posJfDTO.setDdbh(bdcSlSfxxDOList.get(0).getJfsbm());
        } else { // POS缴费成功记录商户代码、终端号、参考号等信息
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.listBdcSlSfxxByGzlslid(posJfDTO.getGzlslid());
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    acceptDozerMapper.map(posJfDTO, bdcSlSfxxDO);
                    this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                }
            }
        }
        // 获取受理编号用于日志内容记录
        posJfDTO.setSlbh(this.getSlbhByGzlslid(posJfDTO.getGzlslid()));
        final Object response = exchangeInterfaceFeignService.requestInterface("wwsq_pos_gxdd", posJfDTO);
        LOGGER.info("通知政融平台支付成功接口返回值：{}", response);
        return response;
    }

    /**
     * 更新收费状态为已核验(7)
     *
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @Override
    public void updatSfztYhy(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失工作流实例ID");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
            for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                bdcSlSfxxDO.setSfzt(BdcSfztEnum.YHY.key());
                bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
            }
        }
    }

    /**
     * 根据工作流实例ID获取受理编号
     */
    private String getSlbhByGzlslid(String gzlslid) {
        String slbh = "";
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                slbh = bdcXmDTOList.get(0).getSlbh();
            }
        }
        return slbh;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param jfzt    是否缴费
     * @return boolean 回写是否成功
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将缴费状态更新到流程信息
     */
    private boolean saveSfssxxZtToProcess(String gzlslid, Integer jfzt) {
        boolean result = true;
        try {
            Map<String, Object> jfztMap = new HashMap<>();
            jfztMap.put("JFZT", jfzt);
            bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, jfztMap);
        } catch (Exception e) {
            LOGGER.error("回写大云字段异常！gzlslid={},回写字段jfzt={}", gzlslid, jfzt);
            result = false;
        }
        return result;
    }

    /**
     * 同步缴库信息至流程信息
     *
     * @param gzlslid  工作流实例ID
     * @param yhjkrkzt 银行缴库入库状态
     * @return boolean   同步大云流程数据标识
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    private boolean syncJkxxToProcess(String gzlslid, int yhjkrkzt) {
        boolean result = true;
        try {
            Map<String, Object> yhjkrkztMap = new HashMap<>();
            yhjkrkztMap.put("YHJKRKZT", yhjkrkzt);
            bdcYwsjHxFeignService.updateBdcYwsj(gzlslid, yhjkrkztMap);
        } catch (Exception e) {
            LOGGER.error("回写大云字段异常！gzlslid={},回写字段yhjkrkzt={}", gzlslid, yhjkrkzt);
            result = false;
        }
        return result;
    }

    /**
     * @param bdcXmDOList 项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州登记费计算方法
     * @date : 2020/9/8 15:10
     */
    private BdcSlSfxmSlDTO queryCzSfxmDjfSl(List<BdcXmDO> bdcXmDOList) {
        BdcSlSfxmSlDTO bdcSlSfxmSlDTO = new BdcSlSfxmSlDTO();
        if (MapUtils.isNotEmpty(bdcSlSfxmConfig.getTsdjyysf()) && MapUtils.getString(bdcSlSfxmConfig.getTsdjyysf(), bdcXmDOList.get(0).getDjyy(), StringUtils.EMPTY).contains(bdcXmDOList.get(0).getDjxl())) {
            //特殊收费项目的直接返回,
            bdcSlSfxmSlDTO.setSftssfxm(true);
            return bdcSlSfxmSlDTO;
        }
        //先根据房屋用途分为住宅和非住宅收费
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcXmDOList.get(0).getGzlslid());
        bdcXmQO.setDjxl(bdcXmDOList.get(0).getDjxl());
        //组合流程需分开产权/抵押，传入登记小类判断
        List<BdcXmAndFbDTO> bdcXmAndFbDTOList = bdcXmFeignService.listBdcXmAndFb(bdcXmQO);
        int zzsl = 0;
        int fzzsl = 0;
        //常州多个宗地逻辑
        //先判断同一宗地内有住宅和非住宅的收取两个费用
        //根据地籍号去重后再计算数量

        /**
         * @param bdcXmDOList
         * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
         * @description 产权的不按照宗地收，有多少物收多少份
         * @date : 2021/11/6 15:31
         */
        if (CollectionUtils.isNotEmpty(bdcXmAndFbDTOList)) {
            List<BdcXmAndFbDTO> zdBdcXmList = new ArrayList<>(bdcXmDOList.size());
            if (ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmAndFbDTOList.get(0).getQllx())) {
                for (BdcXmAndFbDTO bdcXmAndFbDTO : bdcXmAndFbDTOList) {
                    if (bdcXmAndFbDTO.getBdcdyh().length() > 19) {
                        String zdBdcdyh = bdcXmAndFbDTO.getBdcdyh().substring(0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                        bdcXmAndFbDTO.setBdcdyh(zdBdcdyh);
                        zdBdcXmList.add(bdcXmAndFbDTO);
                    }
                }
            } else {
                zdBdcXmList.addAll(bdcXmAndFbDTOList);
            }
            //根据宗地项目分组
            zdBdcXmList = zdBdcXmList.stream().filter(bdcXmDO -> StringUtils.isNotBlank(bdcXmDO.getBdcdyh()) && StringUtils.length(bdcXmDO.getBdcdyh()) > 19).collect(Collectors.toList());
            Map<String, List<BdcXmAndFbDTO>> zdxmMap = zdBdcXmList.stream().collect(Collectors.groupingBy(BdcXmAndFbDTO::getBdcdyh));
            for (Map.Entry<String, List<BdcXmAndFbDTO>> entry : zdxmMap.entrySet()) {
                //分组后的宗地信息
                List<BdcXmAndFbDTO> bdcXmZzList = new ArrayList<>(entry.getValue().size());
                List<BdcXmAndFbDTO> bdcXmFzzList = new ArrayList<>(entry.getValue().size());
                //每一个宗地里面判断是否存在住宅和非住宅，同时存在
                boolean add = true;
                for (BdcXmAndFbDTO bdcXmAndFbDTO : entry.getValue()) {
                    if (Objects.nonNull(bdcXmAndFbDTO.getSfyt())) {
                        LOGGER.info("受理编号{}，收费用途{}", bdcXmAndFbDTO.getSlbh(), bdcXmAndFbDTO.getSfyt());
                        //优先判断
                        if (zzfwytdmList.contains(bdcXmAndFbDTO.getSfyt().toString())) {
                            bdcXmZzList.add(bdcXmAndFbDTO);
                        } else {
                            bdcXmFzzList.add(bdcXmAndFbDTO);
                        }
                    } else {
                        //常州收费不取项目表的dzwyt字段，取房地产权表的数据
                        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmAndFbDTO.getXmid());
                        String ghyt = "";
                        if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcFdcqDO) {
                            BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                            if (StringUtils.isNotBlank(bdcFdcqDO.getFjh()) && StringUtils.isNotBlank(fjhBsf) && bdcFdcqDO.getFjh().contains(fjhBsf)) {
                                /*
                                 * 常州地方需求，根据房间号判断是否存在 特殊字段，存在特殊字段的不计算当前登记费
                                 * */
                                add = false;
                                LOGGER.warn("当前房间号{}存在特殊字符,不计算登记费", bdcFdcqDO.getFjh());
                            }
                            if (bdcFdcqDO.getGhyt() != null) {
                                ghyt = bdcFdcqDO.getGhyt().toString();
                            }
                        } else if (bdcXmAndFbDTO.getDzwyt() != null) {
                            ghyt = Objects.nonNull(bdcXmAndFbDTO.getDzwyt()) ? bdcXmAndFbDTO.getDzwyt().toString() : "";
                        }
                        LOGGER.info("受理编号{}，规划用途{},权力信息{}", bdcXmAndFbDTO.getSlbh(), ghyt, JSONObject.toJSON(bdcQl));
                        if (zzfwytdmList.contains(ghyt)) {
                            bdcXmZzList.add(bdcXmAndFbDTO);
                        } else {
                            bdcXmFzzList.add(bdcXmAndFbDTO);
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(bdcXmZzList) && add) {
                    zzsl++;
                }
                if (CollectionUtils.isNotEmpty(bdcXmFzzList) && add) {
                    fzzsl++;
                }
            }
            if (zzsl > 0) {
                bdcSlSfxmSlDTO.setZzdjfsl(zzsl);
            }
            if (fzzsl > 0) {
                bdcSlSfxmSlDTO.setFzzdjfsl(fzzsl);
            }
        }
        return bdcSlSfxmSlDTO;
    }

    /**
     * @param gxsfxm 更新收费项目 true: 只更新收费项目； false: 只更新收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理推送缴费信息返回结果
     */
    private void dealTsdjfxxResponseDTO(BdcTsdjfxxResponseDTO bdcTsdjfxxResponseDTO, String sfxxid, boolean gxsfxm) {
        if (bdcTsdjfxxResponseDTO != null && StringUtils.isNotBlank(sfxxid) && (StringUtils.isNotBlank(bdcTsdjfxxResponseDTO.getJfsbm()) || StringUtils.isNotBlank(bdcTsdjfxxResponseDTO.getJfsewmurl()))) {
            //更新缴费书编码和二维码
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
            if (bdcSlSfxxDO != null) {
                Date jkmsj = new Date();
                if (gxsfxm) {
                    // 更新收费项目信息
                    List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);
                    if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                        for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                            bdcSlSfxmDO.setJkmsj(jkmsj);
                            bdcSlSfxmDO.setJfsbm(bdcTsdjfxxResponseDTO.getJfsbm());
                            bdcSlSfxmDO.setJfsewmurl(bdcTsdjfxxResponseDTO.getJfsewmurl());
                        }
                        this.entityMapper.batchSaveSelective(bdcSlSfxmDOList);
                    }
                } else {
                    bdcSlSfxxDO.setJfsbm(bdcTsdjfxxResponseDTO.getJfsbm());
                    bdcSlSfxxDO.setJfsewmurl(bdcTsdjfxxResponseDTO.getJfsewmurl());
                    bdcSlSfxxDO.setJkmsj(jkmsj);
                    bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                }
            }
        }
    }

    /**
     * 查询缴款情况并更新
     */
    @Override
    public BdcSlSfxxDO queryJkqkAndUpdate(BdcSlSfxxQO bdcSlSfxxQO) {
        if (StringUtils.isBlank(bdcSlSfxxQO.getSfxxid())) {
            throw new AppException("未获取到收费信息ID。");
        }
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryBdcSlSfxx(bdcSlSfxxQO);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            throw new AppException("未获取到收费信息。");
        }
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
        if (StringUtils.isBlank(bdcSlSfxxDO.getJfsbm())) {
            throw new AppException("未获取到缴费书编码，请先推送收费信息缴费完成后在进行查询。");
        }
        return this.requestQueryJkqk(bdcSlSfxxDO);
    }

    /**
     * 查询非税缴款情况并更新
     */
    @Override
    public CommonResponse queryFsjfqk(String bdcqzh) {
        String result = "";
        if (StringUtils.isBlank(bdcqzh)) {
            return CommonResponse.fail("未获取到不动产权证号。");
        }
        List<BdcXmDO> bdcXmList = bdcXmFeignService.queryXmByZsBdcqzh(bdcqzh);
        if (CollectionUtils.isEmpty(bdcXmList)) {
            return CommonResponse.fail("未获取到不动产权证号对应的项目信息。");
        }
        // 多个项目
        for (BdcXmDO bdcXmDO : bdcXmList) {
            BdcSlSfxxDO sfxxQO = new BdcSlSfxxDO();
            sfxxQO.setXmid(bdcXmDO.getXmid());
            sfxxQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryBdcSlSfxx(sfxxQO);
            if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
                LOGGER.info("该项目没有收费信息：{}", JSON.toJSONString(bdcXmDO));
                result = "该本证书未通过非税缴款，无法打印，请至窗口缴费打证！";
            } else {
                BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxDOList.get(0);
                LOGGER.info("该项目的收费信息：{}", JSON.toJSONString(bdcSlSfxxDO));
                // 缴款码为空
                if (StringUtils.isBlank(bdcSlSfxxDO.getJfsbm())) {
                    result = "该本证书未通过非税缴款，无法打印，请至窗口缴费打证！";
                } else {
                    // 缴款码不为空，缴费状态未缴费,盐城缴费状态两种 1:未缴费 2:已缴费
                    if (bdcSlSfxxDO.getSfzt() == 1) {
                        result = queryJkqk(bdcSlSfxxDO, bdcXmDO.getSlrid());
                    } else if (bdcSlSfxxDO.getSfzt() == 2) {
                        // 缴款码不为空，缴费状态已缴费
                        result = "该本证书已缴费，请继续打证！";
                    }
                }
            }
        }
        Map<String, String> data = new HashMap<>();
        data.put("result", result);
        return CommonResponse.ok(data);
    }

    private BdcSlSfxxDO requestQueryJkqk(BdcSlSfxxDO bdcSlSfxxDO) {
        Map<String, String> param = new HashMap<>(2);
        param.put("pay_code", bdcSlSfxxDO.getJfsbm());
        Object response = this.exchangeInterfaceFeignService.requestInterface("confirmPayStatus", param);
        LOGGER.info("调用获取缴费情况接口返回值：{}", JSON.toJSONString(response));
        if (Objects.nonNull(response)) {
            CommonResponse commonResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
            if (commonResponse.isSuccess()) {
                // 返回值缴款信息
                JSONObject jsonObject = (JSONObject) commonResponse.getData();
                BdcSlSfxxDO bdcSlSfxxDONew = JSON.parseObject(jsonObject.toJSONString(), BdcSlSfxxDO.class);
                dozerMapperF.map(bdcSlSfxxDONew, bdcSlSfxxDO);
                // 设置缴费状态为已缴费
                bdcSlSfxxDO.setSfzt(BdcSfztEnum.YJF.key());
                // 查询到缴款信息后默认设置收费人为： 非税线上缴费
                bdcSlSfxxDO.setSfrxm(Constants.FS_SFR_XM);
                // 保存缴款信息
                this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                // 添加收费信息操作日志
                this.addSfxxCzrz(bdcSlSfxxDO);
                return bdcSlSfxxDO;
            } else {
                throw new AppException(ErrorCode.CUSTOM, "获取缴费情况接口异常" + commonResponse.getFail().getMessage());
            }
        }
        return null;
    }

    private String queryJkqk(BdcSlSfxxDO bdcSlSfxxDO, String slrid) {
        Map<String, String> param = new HashMap<>(2);
        param.put("pay_code", bdcSlSfxxDO.getJfsbm());
        Object response = this.exchangeInterfaceFeignService.requestInterface("confirmPayStatus", param);
        LOGGER.info("调用获取缴费情况接口返回值：{}", JSON.toJSONString(response));
        if (Objects.nonNull(response)) {
            CommonResponse commonResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
            if (commonResponse.isSuccess()) {
                // 返回值缴款信息
                JSONObject jsonObject = (JSONObject) commonResponse.getData();
                BdcSlSfxxDO bdcSlSfxxDONew = JSON.parseObject(jsonObject.toJSONString(), BdcSlSfxxDO.class);
                dozerMapperF.map(bdcSlSfxxDONew, bdcSlSfxxDO);
                // 设置缴费状态为已缴费
                bdcSlSfxxDO.setSfzt(BdcSfztEnum.YJF.key());
                // 查询到缴款信息后默认设置收费人为： 非税线上缴费
                bdcSlSfxxDO.setSfrxm(Constants.FS_SFR_XM);
                // 保存缴款信息
                this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                // 添加收费信息操作日志
                this.addSfxxCzrz(bdcSlSfxxDO);
                LOGGER.info("调用获取缴费情况接口返回值：{}", JSON.toJSONString(bdcSlSfxxDO));
                String sfxxid = bdcSlSfxxDO.getSfxxid();
                String qlrlb = bdcSlSfxxDO.getQlrlb();
                String gzlslid = bdcSlSfxxDO.getGzlslid();
                if (StringUtils.isAnyBlank(sfxxid, gzlslid, qlrlb)) {
                    throw new AppException(ErrorCode.MISSING_ARG, "缺失参数工作流实例ID或收费信息ID");
                }
                if (StringUtils.isNotBlank(bdcSlSfxxDO.getFph())) {
                    // 存在发票号则不进行自动执行操作
                    LOGGER.info("存在发票号则不进行自动执行操作");
                } else {
                    // 自动执行电子票号获取、开具发票、获取发票信息的操作
                    LOGGER.info("自动执行电子票号获取、开具发票、获取发票信息的操作");
                    autoQueryDzfpxx(sfxxid, qlrlb, gzlslid, slrid);
                }
                return "该本证书已缴费，请继续打证！";
            } else {
                LOGGER.error("获取缴费情况接口异常,{}" , commonResponse.getFail().getMessage());
                return "未查询到缴款信息，请确认是否已扫码缴费！";
            }
        }
        return "未查询到缴款信息，请确认是否已扫码缴费！";
    }

    private void autoQueryDzfpxx(String sfxxid, String qlrlb, String gzlslid, String slrid) {
        // 1、 获取电子票号
        try {
            // 产权证号对应业务的受理人,自助机调用没有当前用户
            String userCode = "";
            UserDto currentUser = userManagerUtils.getUserByUserid(slrid);
            if (currentUser != null && StringUtils.isNotBlank(currentUser.getUsername())) {
                userCode = currentUser.getUsername();
            }
            LOGGER.info("获取电子发票号,请求参数sfxxid:{},userCode:{}", sfxxid, userCode);
            this.bdcSlFpxxService.queryDzph(sfxxid, userCode);
        } catch (Exception e) {
            throw new AppException(ErrorCode.CUSTOM, String.format("获取电子票号失败，错误信息：%s", e.getMessage()));
        }
        // 2、开具电子发票
        try {
            LOGGER.info("开具电子发票，请求参数sfxxid:{},gzlslid:{}", sfxxid, gzlslid);
            this.bdcSlFpxxService.inovice(sfxxid, qlrlb, gzlslid);
        } catch (Exception e) {
            throw new AppException(ErrorCode.CUSTOM, String.format("开具电子发票失败，错误信息：%s", e.getMessage()));
        }
        // 等待 5s， 开具发票后，需要等待5s在进行获取电子发票
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            LOGGER.error("开具发票后，等待5s线程异常");
        }

        // 3、获取电子发票信息
        try {
            // 获取电子发票信息，保存电子发票信息，上传电子发票附件
            LOGGER.info("获取电子发票信息，请求参数sfxxid:{},gzlslid:{}", sfxxid, gzlslid);
            this.bdcSlFpxxService.getFpxxAndUploadFpxx(sfxxid, gzlslid);
        } catch (Exception e) {
            throw new AppException(ErrorCode.CUSTOM, String.format("获取电子发票信息失败，错误信息：%s", e.getMessage()));
        }
    }


    /**
     * 收费信息操作日志
     */
    private void addSfxxCzrz(BdcSlSfxxDO bdcSlSfxxDO) {
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getSfxxid())) {
            BdcSlSfxxCzrzDO queryParam = new BdcSlSfxxCzrzDO();
            queryParam.setSfxxid(bdcSlSfxxDO.getSfxxid());
            // 查询是否已经记录了操作日志
            int count = this.entityMapper.count(queryParam);
            // 未记录收费操作日志时，添加操作日志
            if (count == 0) {
                BdcSlSfxxCzrzDO bdcSlSfxxCzrzDO = new BdcSlSfxxCzrzDO();
                BeanUtils.copyProperties(bdcSlSfxxDO, bdcSlSfxxCzrzDO);
                bdcSlSfxxCzrzDO.setId(UUIDGenerator.generate16());
                bdcSlSfxxCzrzDO.setCzr(Constants.FS_SFR_XM);
                bdcSlSfxxCzrzDO.setSfsj(bdcSlSfxxDO.getJkrq());
                // 获取受理编号
                if (StringUtils.isNotBlank(bdcSlSfxxDO.getGzlslid())) {
                    List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlSfxxDO.getGzlslid());
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        bdcSlSfxxCzrzDO.setSlbh(bdcXmDTOList.get(0).getSlbh());
                    }
                }
                this.entityMapper.insert(bdcSlSfxxCzrzDO);
            }
        }
    }

    @Override
    public void createTaxNotice(String sfxxid, String qlrlb, String gzlslid) {
        if (StringUtils.isBlank(sfxxid)) {
            throw new AppException("收费信息不能为空");
        }
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("工作流实例id不能为空");
        }
        JfsxxRequestDTO jfsxxRequestDTO = this.handlerFsTsxx(sfxxid, qlrlb, gzlslid);
        if (Objects.isNull(jfsxxRequestDTO)) {
            throw new AppException("未获取到收费信息");
        }
        FsczfpxxRquestDTO fsczfpxxRquestDTO = new FsczfpxxRquestDTO();
        BeanUtils.copyProperties(jfsxxRequestDTO, fsczfpxxRquestDTO);
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if (currentUser != null && StringUtils.isNotBlank(currentUser.getUsername())) {
            fsczfpxxRquestDTO.setUserCode(currentUser.getUsername());
        }
        Object response = this.exchangeInterfaceFeignService.requestInterface("createTaxNotice", fsczfpxxRquestDTO);
        LOGGER.info("非税开具电子缴款书接口返回值为：{}", JSON.toJSONString(response));
        if (Objects.nonNull(response)) {
            CommonResponse commonResponse = JSON.parseObject(JSON.toJSONString(response), CommonResponse.class);
            if (commonResponse.isSuccess()) {
                JSONObject jsonObject = JSON.parseObject((String) commonResponse.getData());
                String jfsbm = (String) jsonObject.get("pay_code");
                BdcSlSfxxDO bdcSlSfxxDO = new BdcSlSfxxDO();
                bdcSlSfxxDO.setSfxxid(sfxxid);
                bdcSlSfxxDO.setJfsbm(jfsbm);
                bdcSlSfxxDO.setJfsewmurl(jfsbmurl + jfsbm);
                this.bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
            }
        }
    }

    @Override
    public JfsxxRequestDTO handlerFsTsxx(String sfxxid, String qlrlb, String gzlslid) {
        // 查询收费信息
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);

        // 查询收费项目
        List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxid);

        JfsxxRequestDTO jfsxxRequestDTO = new JfsxxRequestDTO();

        if (bdcSlSfxxDO != null && CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
            // 组装收费信息
            jfsxxRequestDTO.setTradeUniqueCode(sfxxid);
            jfsxxRequestDTO.setCoCode(bdcSlSfxxDO.getSfdwdm());
            jfsxxRequestDTO.setCoName(bdcSlSfxxDO.getSfdwmc());
            jfsxxRequestDTO.setExpDate("2099-01-01");
            jfsxxRequestDTO.setIncomeAmount(bdcSlSfxxDO.getHj());
            jfsxxRequestDTO.setPayerName(bdcSlSfxxDO.getJfrxm());

            String xmid = bdcSlSfxxDO.getXmid();
            // 获取领证人的电话，不存在时获取权利人电话
            String dh = "";
            BdcLzrQO bdcLzrQO = new BdcLzrQO();
            bdcLzrQO.setXmid(xmid);
            List<BdcLzrDO> bdcLzrDOList = this.bdcLzrFeignService.listBdcLzr(bdcLzrQO);
            if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
                dh = bdcLzrDOList.get(0).getLzrdh();
            }

            // 未获取到领证人的电话时，获取权利人的电话
            if (StringUtils.isBlank(dh)) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(xmid);
                bdcQlrQO.setQlrlb(qlrlb);
                List<BdcQlrDO> bdcQlrList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                    dh = bdcQlrList.get(0).getDh();
                }
            }
            jfsxxRequestDTO.setPayerMobile(dh);
            // 组装收费项目信息
            this.addSfXmxx(jfsxxRequestDTO, bdcSlSfxmDOList);

            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                // 抵押类业务收费，义务人名称拼接字符串作为备注
                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDOList.get(0).getQllx())) {
                    BdcQlrQO queryParam = new BdcQlrQO();
                    queryParam.setXmid(xmid);
                    queryParam.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                    List<BdcQlrDO> bdcYwrList = bdcQlrFeignService.listBdcQlr(queryParam);
                    if (CollectionUtils.isNotEmpty(bdcYwrList)) {
                        List<String> ywrmcList = bdcYwrList.stream().map(BdcQlrDO::getQlrmc).collect(Collectors.toList());
                        String remark = StringUtils.isBlank(jfsxxRequestDTO.getRemark()) ? "" : jfsxxRequestDTO.getRemark();
                        remark += StringUtils.join(ywrmcList, ",");
                        jfsxxRequestDTO.setRemark(remark);
                    }
                }

                // 添加当前开票人为受理人
                jfsxxRequestDTO.setCreater(bdcXmDOList.get(0).getSlr());
            }
        }
        return jfsxxRequestDTO;
    }

    /**
     * @param bdcXmDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取盐城计算登记费信息
     * @date : 2021/6/23 16:44
     */
    @Override
    public BdcSlSfxmSlDTO queryYcDjfSl(List<BdcXmDO> bdcXmDOList) {
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            return queryYcSfxmDjf(bdcXmDOList);
        }
        return null;
    }

    @Override
    public void autoCountNtSfxx(String gzlslid,String qxdm) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxService.listBdcSlSfxxByGzlslid(gzlslid);
            //区县代码取值,优先读取入参qxdm,没有则获取当前用户刑侦区划,还是没有则直接读取项目表qxdm
            if(StringUtils.isBlank(qxdm)) {
                qxdm = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getCurrentUserName());
            }
            if(StringUtils.isBlank(qxdm)){
                List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                    qxdm =bdcXmDTOList.get(0).getQxdm();
                }
            }
            // 当配置中存在对应区县代码的默认收费信息时 读取配置
            Map mrsfxxMap = mrsfxxConfig.getMrsfxxMap().get(qxdm);
            LOGGER.info("当前用户区县代码：{}默认收费信息：{}", qxdm, (mrsfxxMap == null ? "null" : mrsfxxMap.toString()));

            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                bdcSlSfxxDOList.forEach(bdcSlSfxxDO -> {
                    //只有未缴费才会去自动计算保存收费项目
                    //增加逻辑 收费状态为空或者未收费，且合计是空的或者0
                    if ((bdcSlSfxxDO.getSfzt() == null || BdcSfztEnum.WJF.key().equals(bdcSlSfxxDO.getSfzt())) && (Objects.isNull(bdcSlSfxxDO.getHj()) || Objects.equals(0.00, bdcSlSfxxDO.getHj()))) {
                        LOGGER.warn("收费信息id{}当前收费状态{}合计为{}开始计算", bdcSlSfxxDO.getSfxxid(), bdcSlSfxxDO.getSfzt(), bdcSlSfxxDO.getHj());
                        if (mrsfxxMap != null) {
                            bdcSlSfxxDO.setSfdwdm(StringUtils.isBlank(bdcSlSfxxDO.getSfdwdm()) ? MapUtils.getString(mrsfxxMap, "sfdwdm") : bdcSlSfxxDO.getSfdwdm());
                            bdcSlSfxxDO.setSfrxm(StringUtils.isBlank(bdcSlSfxxDO.getSfrxm()) ? MapUtils.getString(mrsfxxMap, "sfrxm") : bdcSlSfxxDO.getSfrxm());
                            bdcSlSfxxDO.setSfdwmc(StringUtils.isBlank(bdcSlSfxxDO.getSfdwmc()) ? MapUtils.getString(mrsfxxMap, "sfdwmc") : bdcSlSfxxDO.getSfdwmc());
                            bdcSlSfxxDO.setSfrzh(StringUtils.isBlank(bdcSlSfxxDO.getSfrzh()) ? MapUtils.getString(mrsfxxMap, "sfrzh") : bdcSlSfxxDO.getSfrzh());
                            bdcSlSfxxDO.setSfrkhyhwddm(StringUtils.isBlank(bdcSlSfxxDO.getSfrkhyhwddm()) ? MapUtils.getString(mrsfxxMap, "sfrkhyhwddm") : bdcSlSfxxDO.getSfrkhyhwddm());
                            bdcSlSfxxDO.setSfrkhyh(StringUtils.isBlank(bdcSlSfxxDO.getSfrkhyh()) ? MapUtils.getString(mrsfxxMap, "sfrkhyh") : bdcSlSfxxDO.getSfrkhyh());
                        } else {
                            bdcSlSfxxDO.setSfdwdm(StringUtils.isBlank(bdcSlSfxxDO.getSfdwdm()) ? "077022" : bdcSlSfxxDO.getSfdwdm());
                            bdcSlSfxxDO.setSfrxm(StringUtils.isBlank(bdcSlSfxxDO.getSfrxm()) ? "南通市财政局" : bdcSlSfxxDO.getSfrxm());
                            bdcSlSfxxDO.setSfdwmc(StringUtils.isBlank(bdcSlSfxxDO.getSfdwmc()) ? "南通市不动产登记中心" : bdcSlSfxxDO.getSfdwmc());
                            bdcSlSfxxDO.setSfrzh(StringUtils.isBlank(bdcSlSfxxDO.getSfrzh()) ? "10707001040001772" : bdcSlSfxxDO.getSfrzh());
                            bdcSlSfxxDO.setSfrkhyhwddm(StringUtils.isBlank(bdcSlSfxxDO.getSfrkhyhwddm()) ? "102" : bdcSlSfxxDO.getSfrkhyhwddm());
                            bdcSlSfxxDO.setSfrkhyh(StringUtils.isBlank(bdcSlSfxxDO.getSfrkhyh()) ? "农业银行" : bdcSlSfxxDO.getSfrkhyh());
                        }
                        bdcSlSfxxDO.setKpfs(StringUtils.isBlank(bdcSlSfxxDO.getKpfs()) ? "电脑打印" : bdcSlSfxxDO.getKpfs());
                        bdcSlSfxxDO.setJkfs(StringUtils.isBlank(bdcSlSfxxDO.getJkfs()) ? "转账" : bdcSlSfxxDO.getJkfs());
                        if (StringUtils.isNotBlank(bdcSlSfxxDO.getXmid())) {
                            BdcXmQO bdcXmQO = new BdcXmQO();
                            bdcXmQO.setXmid(bdcSlSfxxDO.getXmid());
                            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                                //缴费人姓名
                                BdcQlrDO bdcYwr = bdcSlSqrFeignService.generateYwr(gzlslid, CommonConstantUtils.QLRLB_YWR, bdcXmDOList.get(0).getDjxl());
                                if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, bdcSlSfxxDO.getQlrlb())) {
                                    bdcSlSfxxDO.setJfrxm(bdcXmDOList.get(0).getQlr());
                                    bdcSlSfxxDO.setYwr(StringUtils.isNotBlank(bdcYwr.getQlrmc()) ? bdcYwr.getQlrmc() : "");
                                } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR, bdcSlSfxxDO.getQlrlb())) {
                                    String str = bdcQlrFeignService.queryQlrsYbzs(gzlslid, CommonConstantUtils.QLRLB_YWR, bdcXmDOList.get(0).getDjxl());
                                    if (StringUtils.isNotBlank(str)) {
                                        bdcSlSfxxDO.setJfrxm(StringUtils.join(str.split(" "), ","));
                                    }

                                    bdcSlSfxxDO.setYwr(StringUtils.isNotBlank(bdcYwr.getQlrmc()) ? bdcYwr.getQlrmc() : "");
                                }
                            }
                        }
                        saveSfxm(bdcSlSfxxDO, bdcSlSfxxDO.getXmid(), gzlslid);
                    }
                });
            }
        }
    }

    @Override
    public BdcSlSfxmSlDTO queryNtSfxmSl(@NotBlank(message = "获取收费项目数量工作量实例id不可为空") String gzlslid, @NotBlank(message = "获取收费项目数量登记小类不可为空") String djxl) {
        return this.querySfxmSl(gzlslid, djxl);
    }

    @Override
    public Integer queryJkqkxx(String gzlslid, String qlrlb) {
        LOGGER.error("请求参数为{}，{}", gzlslid, qlrlb);
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失工作流实例ID");
        }
        // 查询收费状态
        BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
        bdcSlSfxxQO.setGzlslid(gzlslid);
        bdcSlSfxxQO.setQlrlb(qlrlb);
        bdcSlSfxxQO.setSfyj(CommonConstantUtils.SF_F_DM);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.queryBdcSlSfxx(bdcSlSfxxQO);
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            return null;
        }
        // 判断是否已缴费
        boolean yjf = true;
        for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
            if (!Objects.equals(BdcSfztEnum.YJF.key(), bdcSlSfxxDO.getSfzt())) {
                yjf = false;
            }
        }
        // 缴费为已缴费时，直接验证通过
        if (yjf) {
            return BdcSfztEnum.YJF.key();
        }

        // 查询到流程未缴费，则调用查询缴款状态接口，查询缴款状态
        Integer sfzt = BdcSfztEnum.YJF.key();
        List<FsAutoExecFpxxThread> fsAutoExecFpxxThreads = new ArrayList<>(1);
        for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
            if (StringUtils.isBlank(bdcSlSfxxDO.getJfsbm())) {
                continue;
            }
            if (!Objects.equals(BdcSfztEnum.YJF.key(), bdcSlSfxxDO.getSfzt())) {
                try {
                    bdcSlSfxxDO = this.requestQueryJkqk(bdcSlSfxxDO);
                    if (Objects.nonNull(bdcSlSfxxDO) && BdcSfztEnum.YJF.key().equals(bdcSlSfxxDO.getSfzt())) {
                        fsAutoExecFpxxThreads.add(new FsAutoExecFpxxThread(gzlslid, bdcSlSfxxDO, bdcSlFpxxService));
                    } else {
                        sfzt = BdcSfztEnum.WJF.key();
                    }
                } catch (Exception e) {
                    sfzt = BdcSfztEnum.WJF.key();
                    LOGGER.error("查询缴款情况失败，请求参数为{}，错误信息为{}", bdcSlSfxxDO.getJfsbm(), e.getMessage());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(fsAutoExecFpxxThreads)) {
            threadEngine.excuteThread(fsAutoExecFpxxThreads, true);
        }
        return sfzt;
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 抵押权推送收费信息
     * @date : 2021/9/15 9:19
     */
    @Override
    public BdcTsdjfxxResponseDTO tsjfDyaqSfxx(String gzlslid) {
        BdcTsdjfxxResponseDTO bdcTsdjfxxResponseDTO = new BdcTsdjfxxResponseDTO();
        String slbh = "";
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = bdcLcTsjfGxService.listLcTsjfGx(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                List<BdcSfxxDTO> bdcSfxxDTOList = queryBdcSfxxDTO("", "", Collections.singletonList(bdcLcTsjfGxDOList.get(0).getSfxxid()), null);
                if (CollectionUtils.isNotEmpty(bdcSfxxDTOList)) {
                    BdcSfxxDTO bdcSfxxDTO = bdcSfxxDTOList.get(0);
                    //设置第三方收费信息jfrdh
                    DsfSfxxDTO dsfSfxxDTO = bdcSfxxDTO.getDsfSfxxDTO() != null ? bdcSfxxDTO.getDsfSfxxDTO() : new DsfSfxxDTO();
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setXmid(bdcSfxxDTO.getBdcSlSfxxDO().getXmid());
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                        // 获取权利人电话、号码
                        BdcQlrDO qlrxx = this.getQlrDhAndZjh(bdcQlrDOList);
                        dsfSfxxDTO.setJfrzjh(qlrxx.getZjh());
                        dsfSfxxDTO.setPjdm(CommonConstantUtils.SF_PJDM);
                        if (StringUtils.isNotBlank(bdcLcTsjfGxDOList.get(0).getLxrmc()) && StringUtils.isNotBlank(bdcLcTsjfGxDOList.get(0).getLxdh())) {
                            dsfSfxxDTO.setJfrxm(bdcLcTsjfGxDOList.get(0).getLxrmc());
                            dsfSfxxDTO.setJfrdh(bdcLcTsjfGxDOList.get(0).getLxdh());
                        } else {
                            dsfSfxxDTO.setJfrdh(qlrxx.getDh());
                            // 优先取收费信息中的缴费人电话
                            if (null != bdcSfxxDTO.getBdcSlSfxxDO() && StringUtils.isNotBlank(bdcSfxxDTO.getBdcSlSfxxDO().getJfrdh())) {
                                dsfSfxxDTO.setJfrdh(bdcSfxxDTO.getBdcSlSfxxDO().getJfrdh());
                            }
                            dsfSfxxDTO.setJfrxm(bdcSfxxDTO.getBdcSlSfxxDO().getJfrxm());
                        }
                        dsfSfxxDTO.setSftype("1");
                    }
                    // 设置推送收费信息的唯一标识
                    String tsid = bdcLcTsjfGxDOList.get(0).getTsid();
                    if (StringUtils.isBlank(tsid)) {
                        dsfSfxxDTO.setSfxmid(UUIDGenerator.generate16());
                    } else {
                        dsfSfxxDTO.setSfxmid(tsid);
                    }

                    //查找所有的收费项目，根据收费标准分组 数量求和，计算合计值
                    List<BdcSlSfxmDO> bdcAllSfxmList = new ArrayList<>(1);
                    List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>(bdcLcTsjfGxDOList.size());
                    Set<String> slbhSet = new LinkedHashSet<>();
                    String qxdm = "";
                    for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(bdcLcTsjfGxDO.getSfxxid());
                        //查询受理编号
                        List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlSfxxDO.getGzlslid());
                        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                            slbhSet.add(bdcXmDTOList.get(0).getSlbh());
                            qxdm = bdcXmDTOList.get(0).getQxdm();
                        }
                        if (!Objects.equals(CommonConstantUtils.SFZT_YJF, bdcSlSfxxDO.getSfzf())) {
                            bdcSlSfxxDOList.add(bdcSlSfxxDO);
                            List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                            if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                                bdcAllSfxmList.addAll(bdcSlSfxmDOList);
                            }
                        }
                    }
                    dsfSfxxDTO.setQxdm(qxdm);
                    String xslbh = "";
                    BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
                    if (Objects.nonNull(bdcSlJbxxDO)) {
                        xslbh = bdcSlJbxxDO.getSlbh();
                    }
                    slbh = StringUtils.join(slbhSet, CommonConstantUtils.ZF_YW_DH);
                    dsfSfxxDTO.setSlbh("大受理编号:" + xslbh);
                    //根据收费标准分组去重数量相加
                    Map<String, List<BdcSlSfxmDO>> bdcAllSfxmMap = bdcAllSfxmList.stream().collect(Collectors.groupingBy(BdcSlSfxmDO::getSfxmbz));
                    //根据收费标准去重
                    bdcAllSfxmList = bdcAllSfxmList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcSlSfxmDO::getSfxmbz))), ArrayList::new));
                    List<BdcSlSfxmVO> bdcSlSfxmVOS = new ArrayList<>(bdcAllSfxmList.size());
                    for (BdcSlSfxmDO bdcSlSfxmDO : bdcAllSfxmList) {
                        BdcSlSfxmVO bdcSlSfxmVO = new BdcSlSfxmVO();
                        bdcSlSfxmDO.setSl((double) CollectionUtils.size(bdcAllSfxmMap.get(bdcSlSfxmDO.getSfxmbz())));
                        bdcSlSfxmDO.setSsje(bdcAllSfxmMap.get(bdcSlSfxmDO.getSfxmbz()).stream().filter(bdcSlSfxm -> Objects.nonNull(bdcSlSfxm.getSsje())).mapToDouble(BdcSlSfxmDO::getSsje).sum());
                        double dj = bdcSlSfxmDO.getSsje() / (Objects.nonNull(bdcSlSfxmDO.getSl()) ? bdcSlSfxmDO.getSl() : 1);
                        BeanUtils.copyProperties(bdcSlSfxmDO, bdcSlSfxmVO);
                        bdcSlSfxmVO.setSfxmdj(dj);
                        bdcSlSfxmVO.setSfxmdmAndQxdm(bdcSlSfxmDO.getSfxmdm() + "@" + qxdm);
                        bdcSlSfxmVOS.add(bdcSlSfxmVO);
                    }
                    bdcSfxxDTO.setBdcSlSfxmVOList(bdcSlSfxmVOS);
                    //收费信息合计求和
                    BdcSlSfxxDO bdcSlSfxxDO = bdcSfxxDTO.getBdcSlSfxxDO();
                    bdcSlSfxxDO.setHj(bdcSlSfxxDOList.stream().filter(bdcSlSfxx -> Objects.nonNull(bdcSlSfxx.getHj())).mapToDouble(BdcSlSfxxDO::getHj).sum());
                    String beanName = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm("fs.config.ts.beanName", "fs_jftb", qxdm);
                    LOGGER.info(" 推送抵押的收费信息 qxdm：{}, beanName: {}, 合计{}, 调用接口入参{}", qxdm, beanName, bdcSlSfxxDO.getHj(), JSON.toJSONString(bdcSfxxDTO));
                    Object response = exchangeInterfaceFeignService.requestInterface("fs_jftb", bdcSfxxDTO);
                    if (response != null) {
                        LOGGER.info("流程{}缴费平台推送待缴抵押收费信息接口调用成功,返回结果：{}", gzlslid, response);
                        bdcTsdjfxxResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(response), BdcTsdjfxxResponseDTO.class);
                        //成功-多条数据循环更新jfsbm和ewm
                        for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                            dealTsdjfxxResponseDTO(bdcTsdjfxxResponseDTO, bdcLcTsjfGxDO.getSfxxid(), true);
                        }
                        // 更新批量抵押收费中，财政的推送ID
                        this.modifyLcTsjfGxTsId(gzlslid, bdcSfxxDTO.getDsfSfxxDTO().getSfxmid());
                    }
                }
            }
        }
        bdcTsdjfxxResponseDTO.setSlbh(slbh);
        return bdcTsdjfxxResponseDTO;
    }

    /**
     * 抵押权批量缴费时,批量更新财政推送ID
     */
    private void modifyLcTsjfGxTsId(String gzlslid, String tsid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList = this.bdcLcTsjfGxService.listLcTsjfGx(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcLcTsjfGxDOList)) {
                for (BdcLcTsjfGxDO bdcLcTsjfGxDO : bdcLcTsjfGxDOList) {
                    bdcLcTsjfGxDO.setTsid(tsid);
                }
                this.bdcLcTsjfGxService.batchUpdateLcTsjfGx(bdcLcTsjfGxDOList);
            }
        }
    }

    private BdcSlSfxmSlDTO querySfxmSl(String gzlslid, String djxl) {
        BdcSlSfxmSlDTO bdcSlSfxmSlDTO = new BdcSlSfxmSlDTO();
        //住宅登记费数量
        Integer zzdjfsl = 0;
        //非住宅登记费数量
        Integer fzzdjfsl = 0;
        //工本费数量(第一本减免）
        Integer gbfsl = 0;
        //工本费数量(不减免）
        Integer gbfslAll = 0;
        //查询当前收费信息对应的项目列表
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setDjxl(djxl);
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //根据证书序号进行分组，证书序号相同生成一本证
            Map<String, BdcXmDO> zsxhMap = new HashMap<>();
            List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = new ArrayList<>();
            List<BdcCshFwkgSlDO> cshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(cshFwkgSlDOList)) {
                //获取所有符合条件的数据
                for (BdcCshFwkgSlDO bdcCshFwkgSlDO : cshFwkgSlDOList) {
                    if (StringUtils.isBlank(djxl) || bdcCshFwkgSlDO.getDjxl().equals(djxl)) {
                        bdcCshFwkgSlDOList.add(bdcCshFwkgSlDO);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(bdcCshFwkgSlDOList)) {
                //查出所有zsxh为空的数据
                List<BdcCshFwkgSlDO> bdcCshFwkgSlDOS = bdcCshFwkgSlDOList.stream().filter(xm -> xm.getZsxh() == null).collect(Collectors.toList());
                //zsxh为空单独发证
                if (CollectionUtils.isNotEmpty(bdcCshFwkgSlDOS)) {
                    for (BdcCshFwkgSlDO bdcCshFwkgSlDO : bdcCshFwkgSlDOS) {
                        //匹配对应的不动产项目
                        for (BdcXmDO bdcXmDO : bdcXmDOList) {
                            if (StringUtils.equals(bdcXmDO.getXmid(), bdcCshFwkgSlDO.getId())) {
                                zsxhMap.put(bdcCshFwkgSlDO.getId(), bdcXmDO);
                                break;
                            }
                        }

                    }
                    bdcCshFwkgSlDOList.removeAll(bdcCshFwkgSlDOS);
                }
                //处理证书序号不为空的数据,相同生成一本证
                if (CollectionUtils.isNotEmpty(bdcCshFwkgSlDOList)) {
                    Map<Integer, List<BdcCshFwkgSlDO>> zsxhGroupMap = bdcCshFwkgSlDOList.stream().collect(Collectors.groupingBy(BdcCshFwkgSlDO::getZsxh));
                    for (Map.Entry<Integer, List<BdcCshFwkgSlDO>> entry : zsxhGroupMap.entrySet()) {
                        List<BdcCshFwkgSlDO> fwkgSlDOList = entry.getValue();
                        //匹配对应的不动产项目
                        for (BdcXmDO bdcXmDO : bdcXmDOList) {
                            if (StringUtils.equals(bdcXmDO.getXmid(), fwkgSlDOList.get(0).getId())) {
                                zsxhMap.put(entry.getKey().toString(), bdcXmDO);
                                break;
                            }
                        }
                    }
                }
            }

            if (MapUtils.isNotEmpty(zsxhMap)) {
                //房地产权判断单独处理登记费,根据权利表用途判断
                List<String> fdcqxmidList = new ArrayList<>();
                List<BdcXmDO> bdcXmDOS = new ArrayList<>();
                for (Map.Entry<String, BdcXmDO> entry : zsxhMap.entrySet()) {
                    BdcXmDO bdcXmDO = entry.getValue();
                    if (ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcXmDO.getQllx())) {
                        fdcqxmidList.add(bdcXmDO.getXmid());
                    } else if (fwdyqscdjxlList.contains(bdcXmDO.getDjxl())) {
                        bdcXmDOS = bdcXmDOList;
                    } else {
                        bdcXmDOS.add(bdcXmDO);
                    }

                    //换证流程等特殊流程，工本费数量就是证书数量
                    if (hzlcdyidList.contains(bdcXmDO.getGzldyid())) {
                        gbfsl++;
                        gbfslAll++;
                    } else {
                        if (CommonConstantUtils.SF_S_DM.equals(bdcXmDO.getSqfbcz())) {
                            //分别持证
                            BdcQlrQO bdcQlrQO = new BdcQlrQO();
                            bdcQlrQO.setXmid(bdcXmDO.getXmid());
                            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                                gbfsl = gbfsl + (bdcQlrDOList.size() - 1);
                                gbfslAll = gbfslAll + bdcQlrDOList.size();
                            }
                        } else {
                            gbfslAll = 1;
                            //生成一本证,不收工本费(减免)
                        }
                    }
                }
                //房地产权根据规划用途判断住宅和非住宅,计算住宅登记费和非住宅登记费
                if (CollectionUtils.isNotEmpty(fdcqxmidList)) {
                    List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByXmids(fdcqxmidList);
                    for (BdcQl bdcQl : bdcQlList) {
                        if (bdcQl instanceof BdcFdcqDO) {
                            BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                            if (CollectionUtils.isNotEmpty(zzfwytdmList) && bdcFdcqDO.getGhyt() != null && zzfwytdmList.contains(bdcFdcqDO.getGhyt().toString())) {
                                //住宅
                                zzdjfsl++;
                            } else {
                                //非住宅
                                fzzdjfsl++;
                            }

                        }
                    }
                } else {
                    BdcSlSfxmSlDTO bdcSlSfxmSl = this.querySfxmDjfSl(bdcXmDOS);
                    zzdjfsl = bdcSlSfxmSl.getZzdjfsl();
                    fzzdjfsl = bdcSlSfxmSl.getFzzdjfsl();
                }
            }

            List<String> zzxmidList = new ArrayList<>();
            List<String> fzzxmidList = new ArrayList<>();
            //房地产权判断单独处理土地交易费,根据权利表用途判断
            List<String> fdcqxmidList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    if (ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcXmDO.getQllx())) {
                        fdcqxmidList.add(bdcXmDO.getXmid());
                    } else if (CollectionUtils.isNotEmpty(zzfwytdmList) && bdcXmDO.getDzwyt() != null && zzfwytdmList.contains(bdcXmDO.getDzwyt().toString())) {
                        zzxmidList.add(bdcXmDO.getXmid());
                    } else {
                        fzzxmidList.add(bdcXmDO.getXmid());
                    }
                }
            }

            //住宅使用权面积
            Double zzsyqmj = getTdsyqMj(null, zzxmidList);
            //非住宅使用权面积
            Double fzzsyqmj = getTdsyqMj(null, fzzxmidList);
            //房地产权根据规划用途判断住宅和非住宅,计算使用权面积
            if (CollectionUtils.isNotEmpty(fdcqxmidList)) {
                List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByXmids(fdcqxmidList);
                if (CollectionUtils.isNotEmpty(bdcQlList)) {
                    for (BdcQl bdcQl : bdcQlList) {
                        if (bdcQl instanceof BdcFdcqDO) {
                            BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                            if (bdcFdcqDO.getTdsyqmj() != null) {
                                if (CollectionUtils.isNotEmpty(zzfwytdmList) && bdcFdcqDO.getGhyt() != null && zzfwytdmList.contains(bdcFdcqDO.getGhyt().toString())) {
                                    //住宅
                                    zzsyqmj += bdcFdcqDO.getTdsyqmj();
                                } else {
                                    //非住宅
                                    fzzsyqmj += bdcFdcqDO.getTdsyqmj();
                                }
                            }
                        }
                    }
                }
            }
            bdcSlSfxmSlDTO.setZzmjsl(NumberUtil.formatDigit(zzsyqmj, 2));
            bdcSlSfxmSlDTO.setFzzmjsl(NumberUtil.formatDigit(fzzsyqmj, 2));
            bdcSlSfxmSlDTO.setTdsyjsl(NumberUtil.formatDigit(zzsyqmj + fzzsyqmj, 2));

        } else {
            /*
             * 查询受理库收费数量
             * */
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                List<String> fdcqxmidList = new ArrayList<>();
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
                String gzldyid = "";
                if (Objects.nonNull(bdcSlJbxxDO)) {
                    gzldyid = bdcSlJbxxDO.getGzldyid();
                }
                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                    //换证流程等特殊流程，工本费数量就是证书数量
                    if (hzlcdyidList.contains(gzldyid)) {
                        gbfsl++;
                        gbfslAll++;
                    } else {
                        if (CommonConstantUtils.SF_S_DM.equals(bdcSlXmDO.getSqfbcz())) {
                            //分别持证
                            List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDO.getXmid(), CommonConstantUtils.QLRLB_QLR);
                            if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                                gbfsl = gbfsl + (bdcSlSqrDOList.size() - 1);
                                gbfslAll = gbfslAll + bdcSlSqrDOList.size();
                            }
                        } else {
                            gbfslAll = 1;
                            //生成一本证,不收工本费(减免)
                        }
                    }
                    if (ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcSlXmDO.getQllx())) {
                        fdcqxmidList.add(bdcSlXmDO.getXmid());
                    }
                }
                //房地产权根据规划用途判断住宅和非住宅,计算住宅登记费和非住宅登记费
                Double zzsyqmj = 0.00;
                Double fzzsyqmj = 0.00;
                if (CollectionUtils.isNotEmpty(fdcqxmidList)) {
                    for (String xmid : fdcqxmidList) {
                        List<BdcSlFwxxDO> bdcSlFwxxDOList = bdcSlFwxxService.listBdcSlFwxxByXmid(xmid);
                        if (CollectionUtils.isNotEmpty(bdcSlFwxxDOList)) {
                            if (CollectionUtils.isNotEmpty(zzfwytdmList) && bdcSlFwxxDOList.get(0).getFwyt() != null && zzfwytdmList.contains(bdcSlFwxxDOList.get(0).toString())) {
                                //住宅
                                zzdjfsl++;
                            } else {
                                //非住宅
                                fzzdjfsl++;
                            }

                            if (bdcSlFwxxDOList.get(0).getTdsyqmj() != null) {
                                if (CollectionUtils.isNotEmpty(zzfwytdmList) && bdcSlFwxxDOList.get(0).getFwyt() != null && zzfwytdmList.contains(bdcSlFwxxDOList.get(0).getFwyt().toString())) {
                                    //住宅
                                    zzsyqmj += bdcSlFwxxDOList.get(0).getTdsyqmj();
                                } else {
                                    //非住宅
                                    fzzsyqmj += bdcSlFwxxDOList.get(0).getTdsyqmj();
                                }
                            }
                        }
                    }
                }
                bdcSlSfxmSlDTO.setZzmjsl(NumberUtil.formatDigit(zzsyqmj, 2));
                bdcSlSfxmSlDTO.setFzzmjsl(NumberUtil.formatDigit(fzzsyqmj, 2));
                bdcSlSfxmSlDTO.setTdsyjsl(NumberUtil.formatDigit(zzsyqmj + fzzsyqmj, 2));
            }
        }


        bdcSlSfxmSlDTO.setZzdjfsl(zzdjfsl);
        bdcSlSfxmSlDTO.setFzzdjfsl(fzzdjfsl);
        bdcSlSfxmSlDTO.setGbfsl(gbfsl);
        bdcSlSfxmSlDTO.setGbfslAll(gbfslAll);
        return bdcSlSfxmSlDTO;


    }

    private void saveSfxm(BdcSlSfxxDO bdcSlSfxxDO, String xmid, String gzlslid) {
        if (StringUtils.isNotBlank(bdcSlSfxxDO.getSfxxid())) {
            String djxl = "";
            if (StringUtils.isNotBlank(xmid)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(xmid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    djxl = bdcXmDOList.get(0).getDjxl();
                }

            }
            //判断是否为小微企业,小微企业减免登记费
            boolean isXwqy = false;
            if (StringUtils.isNotBlank(xmid)) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(xmid);
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                        if (CommonConstantUtils.QLRLX_XWQY.equals(bdcQlrDO.getQlrlx())) {
                            isXwqy = true;
                            break;
                        }
                    }
                }
            }
            BdcSlSfxmSlDTO bdcSlSfxmSlDTO = querySfxmSl(gzlslid, djxl);
            List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
            if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                    List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzDOList = bdcSlSfxmPzFeignService.listBdcSlSfxmSfbzDO(bdcSlSfxmDO.getSfxmdm());
                    boolean finalIsXwqy = isXwqy;
                    bdcSlSfxmSfbzDOList.forEach(bdcSlSfxmSfbzDO -> {
                        if (StringUtils.isNotBlank(bdcSlSfxmDO.getSfxmbz()) && bdcSlSfxmDO.getSfxmbz().equals(bdcSlSfxmSfbzDO.getSfxmbz())) {
                            if (StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "工本费") > -1) {
                                if (CommonConstantUtils.SFXMJSFF_ALLGBFSL.equals(bdcSlSfxmDO.getJsff())) {
                                    bdcSlSfxmDO.setSl((double) bdcSlSfxmSlDTO.getGbfslAll());
                                } else if (CommonConstantUtils.SFXMJSFF_GBFSLJM.equals(bdcSlSfxmDO.getJsff())) {
                                    bdcSlSfxmDO.setSl((double) bdcSlSfxmSlDTO.getGbfsl());
                                } else {
                                    bdcSlSfxmDO.setSl((double) bdcSlSfxmSlDTO.getGbfsl());
                                }
                                bdcSlSfxmDO.setYsje(NumberUtil.formatDigit(bdcSlSfxmDO.getSl() * Double.parseDouble(bdcSlSfxmSfbzDO.getSfxmdj()), 2));
                                bdcSlSfxmDO.setSsje(NumberUtil.formatDigit(bdcSlSfxmDO.getYsje() - (bdcSlSfxmDO.getJmje() != null ? bdcSlSfxmDO.getJmje() : 0), 2));
                            } else if (StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "非住宅") > -1) {
                                bdcSlSfxmDO.setSl((double) bdcSlSfxmSlDTO.getFzzdjfsl());
                                bdcSlSfxmDO.setYsje(NumberUtil.formatDigit(bdcSlSfxmDO.getSl() * Double.parseDouble(bdcSlSfxmSfbzDO.getSfxmdj()), 2));
                                if (finalIsXwqy) {
                                    //小微企业减免登记费
                                    bdcSlSfxmDO.setJmje(bdcSlSfxmDO.getYsje());
                                }
                                bdcSlSfxmDO.setSsje(NumberUtil.formatDigit(bdcSlSfxmDO.getYsje() - (bdcSlSfxmDO.getJmje() != null ? bdcSlSfxmDO.getJmje() : 0), 2));
                            } else if (StringUtils.indexOf(bdcSlSfxmDO.getSfxmbz(), "住宅") > -1) {
                                bdcSlSfxmDO.setSl((double) bdcSlSfxmSlDTO.getZzdjfsl());
                                bdcSlSfxmDO.setYsje(NumberUtil.multiply(bdcSlSfxmDO.getSl(), Double.parseDouble(bdcSlSfxmSfbzDO.getSfxmdj()), 2));
                                if (finalIsXwqy) {
                                    //小微企业减免登记费
                                    bdcSlSfxmDO.setJmje(bdcSlSfxmDO.getYsje());
                                }
                                bdcSlSfxmDO.setSsje(NumberUtil.subtract(bdcSlSfxmDO.getYsje(), (bdcSlSfxmDO.getJmje() != null ? bdcSlSfxmDO.getJmje() : 0), 2));
                            } else if (StringUtils.indexOf(bdcSlSfxmDO.getSfxmmc(), "土地使用权交易服务费") > -1 && (Objects.isNull(bdcSlSfxmDO.getSl()) || (bdcSlSfxmDO.getSl() <= 0))) {
                                if (CommonConstantUtils.SFXMJSFF_ZZ.equals(bdcSlSfxmDO.getJsff())) {
                                    bdcSlSfxmDO.setSl(bdcSlSfxmSlDTO.getZzmjsl());
                                } else if (CommonConstantUtils.SFXMJSFF_FZZ.equals(bdcSlSfxmDO.getJsff())) {
                                    bdcSlSfxmDO.setSl(bdcSlSfxmSlDTO.getFzzmjsl());

                                } else if (CommonConstantUtils.SFXMJSFF_ALL.equals(bdcSlSfxmDO.getJsff())) {
                                    bdcSlSfxmDO.setSl(bdcSlSfxmSlDTO.getTdsyjsl());
                                } else {
                                    bdcSlSfxmDO.setSl(0.00);
                                }
                                bdcSlSfxmDO.setYsje(NumberUtil.multiply(bdcSlSfxmDO.getSl(), Double.parseDouble(bdcSlSfxmSfbzDO.getSfxmdj()), 2));
                                bdcSlSfxmDO.setSsje(NumberUtil.subtract(bdcSlSfxmDO.getYsje(), (bdcSlSfxmDO.getJmje() != null ? bdcSlSfxmDO.getJmje() : 0), 2));
                            } else {
                                if (bdcSlSfxmDO.getSl() != null) {
                                    bdcSlSfxmDO.setYsje(NumberUtil.multiply(bdcSlSfxmDO.getSl(), Double.parseDouble(bdcSlSfxmSfbzDO.getSfxmdj()), 2));
                                    bdcSlSfxmDO.setSsje(NumberUtil.subtract(bdcSlSfxmDO.getYsje(), (bdcSlSfxmDO.getJmje() != null ? bdcSlSfxmDO.getJmje() : 0), 2));
                                } else {
                                    bdcSlSfxmDO.setYsje(0.00);
                                    bdcSlSfxmDO.setSsje(0.00);
                                }
                            }
                            bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
                        }
                    });
                }
                bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                Double hj = bdcSlSfxmDOList.stream().filter(sfxm -> null != sfxm.getSsje()).map(t -> new BigDecimal(String.valueOf(t.getSsje())))
                        .reduce((m, n) -> m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
                bdcSlSfxxDO.setHj(hj);
                //如果合计为0，认定为不收费，改变sfzt
                if (Objects.equals(0d, hj) && isBsf) {
                    bdcSlSfxxDO.setSfzt(BdcSfztEnum.BSF.key());
                }
                LOGGER.info("当前流程收费信息id{}收费计算数量结果{}最后更新合计大小为{}", bdcSlSfxxDO.getSfxxid(), JSON.toJSONString(bdcSlSfxmSlDTO), hj);
                bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
            }
        }
    }

    private void addSfXmxx(JfsxxRequestDTO jfsxxRequestDTO, List<BdcSlSfxmDO> bdcSlSfxmDOList) {
        // 组装收费项目
        List<JfsxxRequestJkxmDTO> jfsxxRequestJkxmDTOList = new ArrayList<>();
        for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
            //如果收费项目数量为空或者数量为0不推送
            if (bdcSlSfxmDO.getSl() == null || bdcSlSfxmDO.getSl() == 0) {
                continue;
            }
            JfsxxRequestJkxmDTO jfsxxRequestJkxmDTO = new JfsxxRequestJkxmDTO();
            // 获取对应收费项目单价
            List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzDOList = bdcSlSfxmPzFeignService.listBdcSlSfxmSfbzDO(bdcSlSfxmDO.getSfxmdm());
            if (CollectionUtils.isNotEmpty(bdcSlSfxmSfbzDOList)) {
                for (BdcSlSfxmSfbzDO bdcSlSfxmSfbzDO : bdcSlSfxmSfbzDOList) {
                    if (StringUtils.equals(bdcSlSfxmDO.getSfxmbz(), bdcSlSfxmSfbzDO.getSfxmbz())) {
                        jfsxxRequestJkxmDTO.setAmount(Double.parseDouble(bdcSlSfxmSfbzDO.getSfxmdj()));
                        break;
                    }
                }
            }
            // 补充收费项目信息
            jfsxxRequestJkxmDTO.setCode(bdcSlSfxmDO.getSfxmdm());
            jfsxxRequestJkxmDTO.setName(bdcSlSfxmDO.getSfxmmc());
            jfsxxRequestJkxmDTO.setUnits(bdcSlSfxmDO.getJedw());
            jfsxxRequestJkxmDTO.setCount(bdcSlSfxmDO.getSl());
            jfsxxRequestJkxmDTO.setTotalAmount(bdcSlSfxmDO.getSsje());
            // 将项目信息存入列表中
            jfsxxRequestJkxmDTOList.add(jfsxxRequestJkxmDTO);
        }
        jfsxxRequestDTO.setItem(jfsxxRequestJkxmDTOList);
    }

    private BdcSlSfxmSlDTO queryYcSfxmDjf(List<BdcXmDO> bdcXmDOList) {
        BdcSlSfxmSlDTO bdcSlSfxmSlDTO = new BdcSlSfxmSlDTO();
        List<BdcXmDO> bdcXmZzList = new ArrayList<>(bdcXmDOList.size());
        List<BdcXmDO> bdcXmFzzList = new ArrayList<>(bdcXmDOList.size());
        List<BdcXmDO> bdcXmQtList = new ArrayList<>(bdcXmDOList.size());
        LOGGER.info("特殊收费项目配置：{}, 不动产项目登记原因：{},不动产项目登记小类: {}", bdcSlSfxmConfig.getTsdjyysf(), bdcXmDOList.get(0).getDjyy(), bdcXmDOList.get(0).getDjxl());
        if (MapUtils.isNotEmpty(bdcSlSfxmConfig.getTsdjyysf()) && MapUtils.getString(bdcSlSfxmConfig.getTsdjyysf(), bdcXmDOList.get(0).getDjyy(), StringUtils.EMPTY).contains(bdcXmDOList.get(0).getDjxl())) {
            //特殊收费项目的就直接返回，不需要判断住宅非住宅
            bdcSlSfxmSlDTO.setSftssfxm(true);
            return bdcSlSfxmSlDTO;
        }
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDOList.get(0).getXmid());
        if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcYyDO) {
            bdcSlSfxmSlDTO.setSfyydj(true);
        }
        String gzldyid = bdcXmDOList.get(0).getGzldyid();
        // 判断是否不生成土地收费信息
        boolean bscTdSfxx = CollectionUtils.isNotEmpty(bsctdsfxxGzldyids) && bsctdsfxxGzldyids.contains(gzldyid);
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            if (bscTdSfxx && Objects.equals(bdcXmDO.getBdclx(), Integer.parseInt(CommonConstantUtils.BDCLX_TD_DM))) {
                continue;
            }
            if (bdcXmDO.getDzwyt() != null) {
                if (zzfwytdmList.contains(bdcXmDO.getDzwyt().toString())) {
                    bdcXmZzList.add(bdcXmDO);
                } else if (fzzfwytdmList.contains(bdcXmDO.getDzwyt().toString())) {
                    bdcXmFzzList.add(bdcXmDO);
                } else {
                    bdcXmQtList.add(bdcXmDO);
                }
            } else {
                bdcXmQtList.add(bdcXmDO);
            }
        }

        //住宅费
        if (CollectionUtils.isNotEmpty(bdcXmZzList)) {
            //车位，车库，成套住宅，储藏室为80  仓储、其他均为550
            bdcSlSfxmSlDTO.setZzdjfsl(1);
            if (CollectionUtils.isNotEmpty(jshsslDyidList) && jshsslDyidList.contains(gzldyid)) {
                bdcSlSfxmSlDTO.setZzdjfsl(bdcXmZzList.size());
            }
        }
        //非住宅费
        if (CollectionUtils.isNotEmpty(bdcXmFzzList)) {
            bdcSlSfxmSlDTO.setFzzdjfsl(1);
            if (CollectionUtils.isNotEmpty(jshsslDyidList) && jshsslDyidList.contains(gzldyid)) {
                bdcSlSfxmSlDTO.setFzzdjfsl(bdcXmFzzList.size());
            }
        }

        //其登记费， 盐城特殊要求：其他登记费按非住宅进行计算
        bdcSlSfxmSlDTO.setQtdjfsl(null);
        if (CollectionUtils.isNotEmpty(bdcXmQtList)) {
            if (CollectionUtils.isNotEmpty(jshsslDyidList) && jshsslDyidList.contains(gzldyid)) {
                Integer fzzsl = bdcSlSfxmSlDTO.getFzzdjfsl() + bdcXmQtList.size();
                bdcSlSfxmSlDTO.setFzzdjfsl(fzzsl);
            } else {
                bdcSlSfxmSlDTO.setFzzdjfsl(1);
            }
        }

        return bdcSlSfxmSlDTO;
    }

    @Override
    public void updateSlSfxxSfztczsj(String processInsId) {
        List<BdcSlSfxxDO> bdcSlSfxxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(processInsId)) {
            List<BdcXtJgDO> bdcXtJgDOS = bdcXtJgFeignService.listAyjsBdcXtJgYhmcLike("");
            List<String> ayjsyhList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bdcXtJgDOS)) {
                bdcXtJgDOS.forEach(bdcXtJgDO -> {
                    ayjsyhList.add(bdcXtJgDO.getJgmc());
                });
            }

            Example example = new Example(BdcSlSfxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("gzlslid", processInsId);
            // 去除收费信息作废的数据
            criteria.andNotEqualNvlTo("sfzf", "1", "0");
            bdcSlSfxxDOList = entityMapper.selectByExample(example);
            LOGGER.info("月结银行：{}", JSON.toJSONString(ayjsyhList));
            LOGGER.info("收费信息bdcSlSfxxDOList：{}", JSON.toJSONString(bdcSlSfxxDOList));
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                    Boolean flag = bdcSlSfxxDO.getSfzt() == null && bdcSlSfxxDO.getSfztczsj() == null &&
                            StringUtils.isNotBlank(bdcSlSfxxDO.getJfrxm()) && ayjsyhList.contains(bdcSlSfxxDO.getJfrxm());
                    if (flag) {
                        LOGGER.warn("当前流程实例id{}，缴费人属于月结银行，转发时更新收费状态和时间", processInsId);
                        bdcSlSfxxDO.setSfztczsj(new Date());
                        entityMapper.updateByPrimaryKeySelective(bdcSlSfxxDO);
                    }
                }
            }
        }
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 计算土地使用权交易服务费-常州根据地段级别判断
     * @date : 2022/4/24 14:27
     */
    @Override
    public List<BdcSlSfxmDO> countTdsyqJyfwf(List<BdcSlSfxmDO> bdcSlSfxmDOList, String gzlslid, boolean sfcxjs) {
        return countTdsyqFwf(bdcSlSfxmDOList, gzlslid, sfcxjs);
    }

    @Override
    public void queryHlwJfzt(String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到工作流实例ID");
        }
        String wwslbh = this.getSpxtywh(processInsId);
        if(StringUtils.isBlank(wwslbh)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到外网受理编号信息");
        }
        Map<String, String> param = new HashMap<>(2);
        param.put("slbh", wwslbh);
        LOGGER.info("请求外网缴费状态查询接口参数：{}", JSON.toJSONString(param));
        Object response = this.exchangeInterfaceFeignService.requestInterface("wwsq_jfxxcx", param);
        if (Objects.nonNull(response)) {
            BdcSfSsxxDTO bdcSfSsxxDTO = JSON.parseObject(JSON.toJSONString(response), BdcSfSsxxDTO.class);
            // 更新收费状态
            this.modifySfxxSfzt(bdcSfSsxxDTO);
            // 更新核税信息完税状态
            this.modifyHsxxWszt(bdcSfSsxxDTO);
        }
    }

    /**
     * @param gzlslid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 税费统缴推送信息
     * @date : 2022/9/26 13:35
     */
    @Override
    public Object sftjxx(String gzlslid, String xmid, String qlrlb, String sfxxid) {
        if(StringUtils.isAnyBlank(gzlslid, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别");
        }

        BdcSlSfxxDO bdcSlSfxxDO = null;
        if (StringUtils.isNotBlank(sfxxid)) {
            // 根据sfxxid获取收费信息
            bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxxid);
        }
        //推送逻辑 1. 权利人义务人分开，2. 税费一起
        //如果xmid为空，先查登记数据，没有值查受理数据，组合只查产权的信息
        if (StringUtils.isBlank(xmid)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                //判断是否组合流程
                int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
                if (Objects.equals(CommonConstantUtils.LCLX_PT, lclx) || Objects.equals(CommonConstantUtils.LCLX_PL, lclx)) {
                    xmid = bdcXmDTOList.get(0).getXmid();
                } else {
                    for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                        if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDTO.getQllx())) {
                            xmid = bdcXmDTO.getXmid();
                            break;
                        }
                    }
                }
            } else {
                //查受理项目数据
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                        if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcSlXmDO.getQllx())) {
                            xmid = bdcSlXmDO.getXmid();
                            break;
                        }
                    }
                }
            }
        }
        if (StringUtils.isBlank(xmid)) {
            throw new AppException("当前流程实例id" + gzlslid + "未查询到项目信息");
        }

        BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
        bdcSlHsxxQO.setXmid(xmid);
        bdcSlHsxxQO.setSqrlb(qlrlb);
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxxByHsxxQo(bdcSlHsxxQO);
        // 如果收费信息和税务信息都为空，不推送
        if (CollectionUtils.isEmpty(bdcSlHsxxDOList)) {
            throw new AppException("当前流程实例id" + gzlslid + "未查询到税费信息，不允许推送税费");
        }
        // 组织报文推送税费同缴信息
        tsSftjxx(gzlslid, xmid, qlrlb, bdcSlSfxxDO, bdcSlHsxxDOList);
        return CommonResponse.ok();
    }

    //计算土地交易服务费方法
    @Override
    public void countTdjyfwf(String sfxmid, String gzlslid, String xmid) {
        BdcSlSfxmDO bdcSlSfxmDO = new BdcSlSfxmDO();

        //查询到土地交易服务费收费项目
        if (StringUtils.isNotBlank(sfxmid)){
            bdcSlSfxmDO = bdcSlSfxmService.queryBdcSlSfxmBySfxmid(sfxmid);
        }
        BdcSlSfxxDTO bdcSlSfxxDTO = new BdcSlSfxxDTO();
        Double ysje = 0.00;
        //获取改流程下收费信息的宗地宗海面积
        bdcSlSfxxDTO = generateSfxxData(bdcSlSfxxDTO, gzlslid, xmid);

        //计算应收金额
        if (Objects.nonNull(bdcSlSfxmDO.getSfxmdj()) && Objects.nonNull(bdcSlSfxxDTO.getZdzhmj())){
            ysje = (bdcSlSfxmDO.getSfxmdj())*(bdcSlSfxxDTO.getZdzhmj());
        }
        bdcSlSfxmDO.setYsje(ysje);
        bdcSlSfxmDO.setSsje(ysje);
        //该收费项目数量为宗地宗海面积
        bdcSlSfxmDO.setSl(bdcSlSfxxDTO.getZdzhmj());
        bdcSlSfxmService.updateBdcSlSfxm(bdcSlSfxmDO);
    }

    private void tsSftjxx(String gzlslid, String xmid, String qlrlb, BdcSlSfxxDO bdcSlSfxxDO, List<BdcSlHsxxDO> bdcSlHsxxDOList) {
        JfsxxRequestDTO jfsxxRequestDTO = new JfsxxRequestDTO();
        //给登记费信息赋值
        setSftjDjfxx(jfsxxRequestDTO, bdcSlSfxxDO, qlrlb);
        //给税务信息赋值
        setSftjHsxx(jfsxxRequestDTO, bdcSlHsxxDOList, xmid, qlrlb);

        //调用接口推送
        LOGGER.warn("当前流程实例id{}，权利人类别：{}，税费统缴调用接口入参{}", gzlslid, qlrlb, JSON.toJSONString(jfsxxRequestDTO));
        String result = nantongFsxtzfFeignService.createSftjBill(jfsxxRequestDTO);
        LOGGER.info("当前流程实例id{}，权利人类别：{}，税费统缴调用接口返回信息{}", gzlslid, qlrlb, result);
        if (StringUtils.isNotBlank(result)) {
            String temp = result.replaceAll("\\\\", "");
            LOGGER.warn("当前流程实例id{}，权利人类别：{}，转化后的result结果{}", gzlslid, qlrlb, temp);
            if (temp.length() > 2) {
                result = temp.substring(1, temp.length() - 1);
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (StringUtils.isNotBlank(jsonObject.getString("totalAmount")) && StringUtils.isNotBlank(jsonObject.getString("billno")) && StringUtils.isNotBlank(jsonObject.getString("qrCodeUrl"))) {
                    // 将数据存放在hsxx中
                    if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                        bdcSlHsxxDOList.forEach(bdcSlHsxxDO ->{
                            bdcSlHsxxDO.setHyzfxjze(Double.valueOf(jsonObject.getString("totalAmount")));
                            bdcSlHsxxDO.setHyzfjfsbm(jsonObject.getString("billno"));
                            bdcSlHsxxDO.setHyzfurl(jsonObject.getString("qrCodeUrl"));
                            bdcSlHsxxService.updateBdcSlHsxx(bdcSlHsxxDO);
                        });
                    }
                }
            }
        }
    }

    /*
     * 设置税费统缴的登记费相关信息
     * */
    private void setSftjDjfxx(JfsxxRequestDTO jfsxxRequestDTO, BdcSlSfxxDO bdcSlSfxxDO, String qlrlb) {
        // 收费信息为空时，必传字段传固定值
        if (Objects.isNull(bdcSlSfxxDO)) {
            String qxdm = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getCurrentUserName());
            Map<String, String> mrsfxxMap = mrsfxxConfig.getMrsfxxMap().get(qxdm);
            LOGGER.info("当前用户区县代码：{}默认收费信息：{}", qxdm, (mrsfxxMap == null ? "null" : mrsfxxMap.toString()));
            if (MapUtils.isNotEmpty(mrsfxxMap)) {
                jfsxxRequestDTO.setCoCode(MapUtils.getString(mrsfxxMap, "sfdwdm"));
                jfsxxRequestDTO.setCoName(MapUtils.getString(mrsfxxMap, "sfdwmc"));
            }
            UserDto userDto = getCurrentUser();
            if (userDto != null) {
                jfsxxRequestDTO.setCreater(userDto.getUsername());
            } else {
                jfsxxRequestDTO.setCreater(sfUsername);
            }
            jfsxxRequestDTO.setIncomeAmount(0D);
            return;
        }
        jfsxxRequestDTO.setTradeUniqueCode(bdcSlSfxxDO.getSfxxid());
        jfsxxRequestDTO.setCoCode(bdcSlSfxxDO.getSfdwdm());
        jfsxxRequestDTO.setCoName(bdcSlSfxxDO.getSfdwmc());
        jfsxxRequestDTO.setExpDate("2099-01-01");
        jfsxxRequestDTO.setBillNo(bdcSlSfxxDO.getJfsbm());

        String xmid = bdcSlSfxxDO.getXmid();
        if (StringUtils.isNotBlank(xmid)) {
            //查询权利人的联系电话，先查登记库，没有再查受理申请人
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(qlrlb);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    if (StringUtils.isNotBlank(bdcQlrDO.getDh())) {
                        jfsxxRequestDTO.setPayerMobile(bdcQlrDO.getDh());
                        break;
                    }
                }
            } else {
                List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, qlrlb);
                if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                    for (BdcSlSqrDO bdcSlSqrDO : bdcSlSqrDOList) {
                        if (StringUtils.isNotBlank(bdcSlSqrDO.getDh())) {
                            jfsxxRequestDTO.setPayerMobile(bdcSlSqrDO.getDh());
                            break;
                        }
                    }
                }
            }
        }
        if (Objects.nonNull(bdcSlSfxxDO.getHj())) {
            jfsxxRequestDTO.setIncomeAmount(bdcSlSfxxDO.getHj());
        }
        jfsxxRequestDTO.setPayerName(bdcSlSfxxDO.getJfrxm());
        List<JfsxxRequestJkxmDTO> jfsxxRequestJkxmDTOList = new ArrayList<>();
        //设置收费项目数据
        List<BdcSlSfxmDO> bdcSlSfxmDOList = bdcSlSfxmService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
        if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
            for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                //如果收费项目数量为空或者数量为0不推送
                if (bdcSlSfxmDO.getSl() == null || bdcSlSfxmDO.getSl() == 0) {
                    continue;
                }
                JfsxxRequestJkxmDTO jfsxxRequestJkxmDTO = new JfsxxRequestJkxmDTO();
                // 获取对应收费项目单价
                List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzDOList = bdcSlSfxmPzFeignService.listBdcSlSfxmSfbzDO(bdcSlSfxmDO.getSfxmdm());
                if (CollectionUtils.isNotEmpty(bdcSlSfxmSfbzDOList)) {
                    for (BdcSlSfxmSfbzDO bdcSlSfxmSfbzDO : bdcSlSfxmSfbzDOList) {
                        if (StringUtils.equals(bdcSlSfxmDO.getSfxmbz(), bdcSlSfxmSfbzDO.getSfxmbz())) {
                            jfsxxRequestJkxmDTO.setAmount(Double.parseDouble(bdcSlSfxmSfbzDO.getSfxmdj()));
                            break;
                        }
                    }
                }
                // 补充收费项目信息
                jfsxxRequestJkxmDTO.setCode(bdcSlSfxmDO.getSfxmdm());
                jfsxxRequestJkxmDTO.setName(bdcSlSfxmDO.getSfxmmc());
                jfsxxRequestJkxmDTO.setUnits(bdcSlSfxmDO.getJedw());
                jfsxxRequestJkxmDTO.setCount(bdcSlSfxmDO.getSl());
                jfsxxRequestJkxmDTO.setTotalAmount(bdcSlSfxmDO.getSsje());
                // 将项目信息存入列表中
                jfsxxRequestJkxmDTOList.add(jfsxxRequestJkxmDTO);
            }
            jfsxxRequestDTO.setItem(jfsxxRequestJkxmDTOList);
        }
        UserDto userDto = getCurrentUser();
        if (userDto != null) {
            jfsxxRequestDTO.setCreater(userDto.getUsername());
        } else {
            jfsxxRequestDTO.setCreater(sfUsername);
        }
        // 是否税费同缴，0:财政缴费，1：税费同缴
        jfsxxRequestDTO.setSwxxflag("1");
    }

    /*
     *
     * 设置税费统缴的税务相关信息
     * */
    private void setSftjHsxx(JfsxxRequestDTO jfsxxRequestDTO, List<BdcSlHsxxDO> bdcSlHsxxDOList, String xmid, String qlrlb) {
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
            List<JfsxxRequestHsxxDTO> jfsxxRequestHsxxDTOList = new ArrayList<>(bdcSlHsxxDOList.size());
            //查询已交款信息的凭证序号
            String pzxh = "";
            Example example = new Example(BdcSlYjkxxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            criteria.andEqualTo("qlrlb", qlrlb);
            List<BdcSlYjkxxDO> bdcSlYjkxxDOList = entityMapper.selectByExampleNotNull(example);
            if (CollectionUtils.isNotEmpty(bdcSlYjkxxDOList)) {
                pzxh = bdcSlYjkxxDOList.get(0).getPzxh();
            }
            //查询交易信息
            List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid(xmid);
//            Double swhj = 0.00;
            for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                JfsxxRequestHsxxDTO jfsxxRequestHsxxDTO = new JfsxxRequestHsxxDTO();
                if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                    jfsxxRequestHsxxDTO.setHtbh(bdcSlJyxxDOList.get(0).getHtbh());
                }
                jfsxxRequestHsxxDTO.setNsrmc(bdcSlHsxxDO.getNsrmc());
                jfsxxRequestHsxxDTO.setNsrsbh(bdcSlHsxxDO.getNsrsbh());
                jfsxxRequestHsxxDTO.setZrfcsfbz(qlrlb);
                jfsxxRequestHsxxDTO.setPzxh(pzxh);
                jfsxxRequestHsxxDTO.setDzsphm(bdcSlHsxxDO.getSphm());
                jfsxxRequestHsxxDTO.setSkje(String.valueOf(bdcSlHsxxDO.getSjyzhj()));
                jfsxxRequestHsxxDTO.setSkssjgdm(bdcSlHsxxDO.getSwjgdm());
                jfsxxRequestHsxxDTO.setKjrq(DateUtils.formateDateToString(bdcSlHsxxDO.getJkrksj(), DateUtils.DATE_FORMAT));
                jfsxxRequestHsxxDTO.setSkssjgdmmc("");
                jfsxxRequestHsxxDTO.setBz("");
                jfsxxRequestHsxxDTO.setXtsphm("");
//                if (Objects.nonNull(bdcSlHsxxDO.getSjyzhj())) {
//                    swhj += bdcSlHsxxDO.getSjyzhj();
//                }
                jfsxxRequestHsxxDTOList.add(jfsxxRequestHsxxDTO);
            }
            jfsxxRequestDTO.setSwxxlist(JSON.toJSONString(jfsxxRequestHsxxDTOList, SerializerFeature.WriteNullStringAsEmpty));
//            jfsxxRequestDTO.setIncomeAmount(jfsxxRequestDTO.getIncomeAmount() + swhj);
        }
    }

    /**
     * 获取审批系统业务号，先获取项目表中的spxtywh，不存在时获取受理项目表中的spxtywh
     */
    private String getSpxtywh(String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getSpxtywh())) {
            return bdcXmDOList.get(0).getSpxtywh();
        }

        List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到项目信息");
        }
        String wwslbh = bdcSlXmDOList.get(0).getSpxtywh();
        if(StringUtils.isBlank(wwslbh)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到外网受理编号信息");
        }
        return wwslbh;
    }

    /**
     * 更新收费状态
     */

    private void modifySfxxSfzt(BdcSfSsxxDTO bdcSfSsxxDTO){
        if(CollectionUtils.isNotEmpty(bdcSfSsxxDTO.getBdcSfxxDTOS())){
            for(BdcSfxxDTO bdcSfxxDTO : bdcSfSsxxDTO.getBdcSfxxDTOS()){
                BdcSlSfxxDO sfxx = bdcSfxxDTO.getBdcSlSfxxDO();
                if(Objects.nonNull(sfxx) && StringUtils.isNotBlank(sfxx.getSfxxid())){
                    BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxService.queryBdcSlSfxxBySfxxid(sfxx.getSfxxid());
                    if (Objects.nonNull(bdcSlSfxxDO) && Objects.nonNull(sfxx.getSfzt())) {
                        bdcSlSfxxDO.setSfzt(sfxx.getSfzt());
                        bdcSlSfxxService.updateBdcSlSfxx(bdcSlSfxxDO);
                    }
                }
            }
        }
    }

    /**
     * 更新核税信息完税状态
     */
    private void modifyHsxxWszt(BdcSfSsxxDTO bdcSfSsxxDTO) {
        if (CollectionUtils.isNotEmpty(bdcSfSsxxDTO.getBdcSwxxDTOS())) {
            for (BdcSwxxDTO bdcSwxxDTO : bdcSfSsxxDTO.getBdcSwxxDTOS()) {
                BdcSlHsxxDO hsxx = bdcSwxxDTO.getBdcSlHsxxDO();
                if(Objects.nonNull(hsxx) && StringUtils.isNotBlank(hsxx.getHsxxid())){
                    BdcSlHsxxDO bdcSlHsxxQO = new BdcSlHsxxDO();
                    bdcSlHsxxQO.setHsxxid(bdcSwxxDTO.getBdcSlHsxxDO().getHsxxid());
                    List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxx(bdcSlHsxxQO);
                    if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                        BdcSlHsxxDO updateBdcSlHsxx = bdcSlHsxxDOList.get(0);
                        updateBdcSlHsxx.setWszt(hsxx.getWszt());
                        bdcSlHsxxService.updateBdcSlHsxx(updateBdcSlHsxx);
                    }
                }
            }
        }
    }


}
