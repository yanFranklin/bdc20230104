package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.*;
import cn.gtmap.realestate.accept.thread.LocalThreadPool;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxInitDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyClfHtxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.qo.accept.BdcGzlwShQO;
import cn.gtmap.realestate.common.core.qo.accept.DjxlPzCxQO;
import cn.gtmap.realestate.common.core.qo.accept.InitSlxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxlDjyyQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/13
 * @description 初始化不动产项目信息
 */
@Service
public class CshBdcSlXmServiceImpl implements CshBdcSlXmService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CshBdcSlXmServiceImpl.class);
    /**
     * 通用流程初始化
     */
    @Resource(name = "initBdcSlxxCommonServiceImpl")
    private InitBdcSlxxService initBdcSlxxCommonServiceImpl;
    /**
     * 预转现流程初始化
     */
    @Resource(name = "initBdcSlxxYzxServiceImpl")
    private InitBdcSlxxService initBdcSlxxYzxServiceImpl;
    /**
     * 带抵押等限制权利流程初始化
     */
    @Resource(name = "initBdcSlxxWithDyServiceImpl")
    private InitBdcSlxxService initBdcSlxxWithDyServiceImpl;
    /**
     * 资金监管撤销流程初始化
     */
    @Resource(name = "initBdcSlxxZjjgServiceImpl")
    private InitBdcSlxxService initBdcSlxxZjjgServiceImpl;

    @Value("#{'${sjcl.extendYxm.gzldyid:}'.split(',')}")
    private List<String> extendYxmSjclDyidList;


    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产受理项目数据服务
     */
    @Autowired
    BdcSlXmService bdcSlXmService;
    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产受理项目历史关系数据服务
     */
    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;
    @Autowired
    BdcSjclService bdcSjclService;
    @Autowired
    BdcDjxlPzService bdcDjxlPzService;
    @Autowired
    BdcQllxService bdcQllxService;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;
    @Autowired
    BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcYxbdcdyKgPzService bdcYxbdcdyKgPzService;
    @Autowired
    BdcJbxxService bdcJbxxService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    MessageProvider messageProvider;
    @Autowired
    BdcSlxxHxService bdcSlxxHxService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    @Autowired
    BdcPpFeignService bdcPpFeignService;
    @Autowired
    BdcSlFeignService bdcSlFeignService;
    @Autowired
    BdcInitFeignService bdcInitFeignService;
    @Autowired
    BdcSfService bdcSfService;
    @Autowired
    private TaskUtils taskUtils;
    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    Set<BdcSlFdjywService> bdcSlFdjywServiceSet;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcGzlwFeignService bdcGzlwFeignService;
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    /**
     * 预转现
     */
    @Value("#{'${accept.lcbs.yzx:}'.split(',')}")
    private List<String> yzxlc;

    /**
     * 带抵押
     */
    @Value("#{'${accept.lcbs.withdy:}'.split(',')}")
    private List<String> withdylc;

    /**
     * 土地抵押转房屋抵押流程
     */
    @Value("#{'${accept.lcbs.tddyzfwdy:}'.split(',')}")
    private List<String> tddyzfwdy;

    /**
     * 资金监管撤销流程
     */
    @Value("#{'${fdjywlc.gzldyid.zjjgcx:}'.split(',')}")
    private List<String> zjjgcxlc;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  解封产权合并流程
     */
    @Value("#{'${lcbs.jfcqhb:}'.split(',')}")
    private List<String> jfcqhbGzldyidList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 解押产权合并流程
     */
    @Value("#{'${lcbs.dyzxcqhb:}'.split(',')}")
    private List<String> jycqhbGzldyidList;

    /**
     * 主房配置内容
     */
    @Value("${accept.sfzf.zfstr: 商业,公寓,住宅}")
    private String zfstr;

    @Value("${data.version:}")
    private String dataversion;

    @Value("${cjyz.lwsh:false}")
    private Boolean cjyzlwsh;

    @Value("${slbhscfs.version:}")
    private String slbhgs;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 单元号更正登记流程
     */
    @Value("#{'${lcbs.dyhgz:}'.split(',')}")
    private List<String> dyhgz;


    /**
     * 主房用途Key缓存内容
     * 第一次创建项目时，获取主房配置的用途内容，调用数据库获取对应的用途DM，
     * 后续直接获取缓存中数据
     */
    private static List<String> zfytKeyListCache = null;

    /**
     * @param bdcSlYwxxDTOList 不动产受理项目前台
     * @param gzlslid          基本信息ID
     * @param czrid            操作人员ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 初始化受理项目信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cshYxxm(List<BdcSlYwxxDTO> bdcSlYwxxDTOList, String gzlslid, String gzldyid, String jbxxid, String czrid) {
        //存储所有需要添加的数据
        List<BdcSlxxInitDTO> bdcSlxxInitDTOList = new ArrayList<>();
        //存储对应项目与原抵押证明号关系
        Map<String, String> xmidDyzmhMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            //此处前台应该设置虚拟添加，最终确认添加时触发实际保存，但此处不做前台修改，而使得前台重复提交时，删除上一次的保存的数据
            bdcdyhFilter(jbxxid, bdcSlYwxxDTOList);
            if (StringUtils.isNotBlank(gzldyid) && StringUtils.isNotBlank(jbxxid)) {
                for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcSlYwxxDTOList) {
                    bdcSlYwxxDTO.setGzldyid(gzldyid);
                    bdcSlYwxxDTO.setSelectDataQllx(bdcSlYwxxDTO.getQllx());
                    if (StringUtils.isNotEmpty(bdcSlYwxxDTO.getYt())) {
                        // 如果用途是在 商业 公寓 住宅类的用途内，则设置为主房
                        if (checkSfzf(bdcSlYwxxDTO.getYt())) {
                            bdcSlYwxxDTO.setSfzf(CommonConstantUtils.SF_S_DM);
                        } else {
                            bdcSlYwxxDTO.setSfzf(CommonConstantUtils.SF_F_DM);
                        }
                    } else {//用途是空的话，还是默认 不是主房
                        bdcSlYwxxDTO.setSfzf(CommonConstantUtils.SF_F_DM);
                    }
                    BdcSlxxInitDTO bdcSlxxInitDTO;
                    //存储当前添加的数据
                    List<BdcSlxxInitDTO> slxxInitDTOList = new ArrayList<>();
                    if (StringUtils.isNotBlank(bdcSlYwxxDTO.getFwDcbIndex())) {
                        bdcSlxxInitDTO = initLjzBdcSlxx(bdcSlYwxxDTO, gzldyid, czrid, jbxxid);
                        slxxInitDTOList.add(bdcSlxxInitDTO);
                    } else if (StringUtils.isNotBlank(bdcSlYwxxDTO.getBdcdyh())) {
                        //确定djxl_pz
                        DjxlPzCxQO djxlPzCxQO =new DjxlPzCxQO();
                        djxlPzCxQO.setGzldyid(gzldyid);
                        djxlPzCxQO.setBdcdyh(bdcSlYwxxDTO.getBdcdyh());
                        djxlPzCxQO.setYqllx(bdcSlYwxxDTO.getQllx());
                        djxlPzCxQO.setYxmid(bdcSlYwxxDTO.getXmid());
                        djxlPzCxQO.setDyhcxlx(bdcSlYwxxDTO.getDyhcxlx());
                        List<BdcDjxlPzDO> bdcDjxlPzDOList =bdcDjxlPzService.queryBdcDjxlPz(djxlPzCxQO);
                        if(CollectionUtils.isNotEmpty(dyhgz) &&dyhgz.contains(gzldyid)){
                            BdcDjxlPzDO bdcDjxlPzDO =bdcDjxlPzDOList.get(0);
                            bdcDjxlPzDOList =Collections.singletonList(bdcDjxlPzDO);
                            //单元号更正,只允许添加一条数据,生成一条受理项目数据
                        }
                        String yxmid = "";
                        for (int i = 0; i < bdcDjxlPzDOList.size(); i++) {
                            BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzDOList.get(i);
                            bdcSlYwxxDTO.setQllx(bdcDjxlPzDO.getQllx());
                            bdcSlYwxxDTO.setDjxl(bdcDjxlPzDO.getDjxl());
                            if (bdcDjxlPzDO.getBdclx() != null && bdcSlYwxxDTO.getBdclx() == null) {
                                bdcSlYwxxDTO.setBdclx(bdcDjxlPzDO.getBdclx());
                            }
                            //是否需要继承原不动产权证
                            Boolean dqybdcqz = true;
                            Integer startSxh =1;
                            Boolean isJyJfCqhb =(CollectionUtils.isNotEmpty(jfcqhbGzldyidList) &&jfcqhbGzldyidList.contains(gzldyid)) ||(CollectionUtils.isNotEmpty(jycqhbGzldyidList) &&jycqhbGzldyidList.contains(gzldyid));
                            //解押解封产权合并,解封解押项目与产权项目不存在上下手关系
                            if(isJyJfCqhb){
                                startSxh =2;
                            }
                            if (i < (startSxh-1)) {
                                yxmid = "";
                            }else if(i ==(startSxh-1)){
                                yxmid = bdcSlYwxxDTO.getXmid();
                            } else if (bdcDjxlPzDO.getSxh() > startSxh && CollectionUtils.isNotEmpty(slxxInitDTOList) && CollectionUtils.isNotEmpty(slxxInitDTOList.get(startSxh-1).getBdcSlXmDOList())) {
                                //合并流程将第一手的登记项目作为下一手的原项目
                                yxmid = slxxInitDTOList.get(startSxh-1).getBdcSlXmDOList().get(0).getXmid();
                                //组合流程新生成的数据作为原项目,ybdcqz为空
                                dqybdcqz = false;
                            }
                            InitSlxxQO initSlxxQO = new InitSlxxQO();
                            initSlxxQO.setBdcSlYwxxDTO(bdcSlYwxxDTO);
                            initSlxxQO.setCzrid(czrid);
                            initSlxxQO.setJbxxid(jbxxid);
                            initSlxxQO.setYxmid(yxmid);
                            initSlxxQO.setXmidDyzmhMap(xmidDyzmhMap);
                            initSlxxQO.setGzldyid(gzldyid);
                            initSlxxQO.setSxh(bdcDjxlPzDO.getSxh());
                            if (CollectionUtils.isNotEmpty(yzxlc) && yzxlc.contains(gzldyid)) {
                                bdcSlxxInitDTO = initBdcSlxxYzxServiceImpl.initBdcSlxx(initSlxxQO);
                            } else if (CollectionUtils.isNotEmpty(withdylc) && withdylc.contains(gzldyid) || (CollectionUtils.isNotEmpty(tddyzfwdy) && tddyzfwdy.contains(gzldyid))) {
                                bdcSlxxInitDTO = initBdcSlxxWithDyServiceImpl.initBdcSlxx(initSlxxQO);
                            } else if (CollectionUtils.isNotEmpty(zjjgcxlc) && zjjgcxlc.contains(gzldyid)) {
                                bdcSlxxInitDTO = initBdcSlxxZjjgServiceImpl.initBdcSlxx(initSlxxQO);
                            } else {
                                bdcSlxxInitDTO = initBdcSlxxCommonServiceImpl.initBdcSlxx(initSlxxQO);
                            }
                            //处理原不动产权证
                            if (!dqybdcqz && CollectionUtils.isNotEmpty(bdcSlxxInitDTO.getBdcSlXmDOList())) {
                                for (BdcSlXmDO bdcSlXmDO : bdcSlxxInitDTO.getBdcSlXmDOList()) {
                                    bdcSlXmDO.setYbdcqz("");
                                }
                            }

                            slxxInitDTOList.add(bdcSlxxInitDTO);
                        }
                    }
                    bdcSlxxInitDTOList.addAll(slxxInitDTOList);
                }
            }
            //处理证书序号
            dealZsxh(bdcSlxxInitDTOList, gzldyid, xmidDyzmhMap);
            LOGGER.info("基本信息id{},最终添加购物车的数量{},详细内容为{}", jbxxid, bdcSlxxInitDTOList.size(), JSONObject.toJSONString(bdcSlxxInitDTOList));
            try {
                bdcSlXmService.saveBdcSlxx(bdcSlxxInitDTOList, Constants.FUN_INSERT);
            } catch (IllegalAccessException e) {
                LOGGER.error(null, e);

            }
        }
    }

    /**
     * 根据YML配置判断是否主房
     *
     * @param yt 房屋用途DM
     * @return boolean  true：主房; false: 非主房;
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public boolean checkSfzf(String yt) {
        if (CollectionUtils.isEmpty(zfytKeyListCache)) {
            zfytKeyListCache = bdcXmFeignService.getZfYt(zfstr);
        }
        if (zfytKeyListCache.contains(yt)) {
            return true;
        }
        return false;
    }

    /**
     * @param jbxxid           基本信息id
     * @param bdcSlYwxxDTOList 不动产受理项目前台
     * @author CaptainY
     * @description 不动产单元号去重
     */
    private void bdcdyhFilter(String jbxxid, List<BdcSlYwxxDTO> bdcSlYwxxDTOList) {
        //查询出当前已经保存的受理项目
        List<BdcSlXmDO> bdcslxmList = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "");
        LOGGER.info("基本信息id为{},购物车中已经添加的数据量{}", jbxxid, bdcslxmList.size());
        List<String> bdcslxmJson = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcslxmList)) {
            List<String> currentXmidList = bdcslxmList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =bdcSlXmLsgxService.listSlXmLsgxPl(currentXmidList,CommonConstantUtils.SF_F_DM);
            Map<String, List<BdcSlXmLsgxDO>> slxmgxMap =new HashMap<>();
            if(CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                slxmgxMap = bdcSlXmLsgxDOList.stream().collect(Collectors.groupingBy(BdcSlXmLsgxDO::getXmid));
            }
            for (BdcSlXmDO bdcSlXmDO : bdcslxmList) {
                BdcSlYwxxDTO bdcSlYwxxDTO = new BdcSlYwxxDTO();
                //暂时用这些字段去对照  lst
                bdcSlYwxxDTO.setBdcdyh(bdcSlXmDO.getBdcdyh());
                if (bdcSlXmDO.getQlr() == null) {
                    bdcSlXmDO.setQlr("");
                }
                bdcSlYwxxDTO.setQlr(bdcSlXmDO.getQlr());
                bdcSlYwxxDTO.setZl(bdcSlXmDO.getZl());
                //针对同一不动产单元批量解封或批量解押时候需要用选择数据的xmid进行判断
                if (MapUtils.isNotEmpty(slxmgxMap) &&slxmgxMap.containsKey(bdcSlXmDO.getXmid())) {
                    bdcSlYwxxDTO.setXmid(slxmgxMap.get(bdcSlXmDO.getXmid()).get(0).getYxmid());
                }

                // 由于选择不动产单元界面数据ybdcqz缺少外联证书信息，去重失败导致重复数据。现去除ybdcqz来比对。
                String json = JSONObject.toJSONString(bdcSlYwxxDTO);
                bdcslxmJson.add(json);
            }
        }
        LOGGER.info("基本信息id为{},购物车已添加单元信息{}", jbxxid, bdcslxmJson);
        //存储过滤后的对象
        List<BdcSlYwxxDTO> container = new ArrayList<>();
        List<String> ywxxJson = new ArrayList<>();
        LOGGER.info("基本信息id{},选择添加的数量{}", jbxxid, bdcSlYwxxDTOList.size());
        for (BdcSlYwxxDTO bdcSlYwxx : bdcSlYwxxDTOList) {
            //暂时用这些字段去对照 lst
            BdcSlYwxxDTO bdcSlYwxxDTO = new BdcSlYwxxDTO();
            bdcSlYwxxDTO.setBdcdyh(bdcSlYwxx.getBdcdyh());
            if (bdcSlYwxx.getQlr() == null) {
                bdcSlYwxx.setQlr("");
            }
            bdcSlYwxxDTO.setQlr(bdcSlYwxx.getQlr());
            bdcSlYwxxDTO.setZl(bdcSlYwxx.getZl());
            bdcSlYwxxDTO.setXmid(bdcSlYwxx.getXmid());
            String json = JSONObject.toJSONString(bdcSlYwxxDTO);
            if (!bdcslxmJson.contains(json) && !ywxxJson.contains(json)) {
                container.add(bdcSlYwxx);
                ywxxJson.add(JSONObject.toJSONString(bdcSlYwxxDTO));
            }
        }
        LOGGER.info("基本信息id为{},选择添加的内容{}", jbxxid, ywxxJson);
        bdcSlYwxxDTOList.clear();
        bdcSlYwxxDTOList.addAll(container);
        // 判断是否存在外联证书，存在自动带入
        if (CollectionUtils.isNotEmpty(bdcslxmList)) {
            //找到购物车中任意一条数据的外联证书，如果存在，那么新添加的数据也要自动外联
            List<BdcSlXmLsgxDO> wlzsLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcslxmList.get(0).getXmid(), "", CommonConstantUtils.SF_S_DM);
            if (CollectionUtils.isNotEmpty(wlzsLsgxDOList)) {
                //1. 根据选择房屋的xmid 判断是否匹配，匹配的外联证书不带入
                //当前的项目 xmid
                String xmid = wlzsLsgxDOList.get(0).getXmid();
                List<BdcSlXmLsgxDO> lsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(xmid, "", CommonConstantUtils.SF_F_DM);
                if (CollectionUtils.isNotEmpty(lsgxDOList)) {
                    // 选择的房屋的xmid
                    String yxmid = lsgxDOList.get(0).getYxmid();
                    List<BdcFctdPpgxDO> bdcFctdPpgxDOList = new ArrayList<>();
                    if (StringUtils.isNotBlank(yxmid)) {
                        // 查找匹配关系表判断是否存在匹配关系
                        bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(yxmid);
                    }
                    // 根据外联的yxmid 在项目表查找不动产类型为1 的 且不动产单元号不为W的 不带入
                    String dzwTzm = "";
                    //不存在匹配关系，且土地证的单元号对应的是W 带入外联信息
                    for (BdcSlXmLsgxDO bdcSlXmLsgxDO : wlzsLsgxDOList) {
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setXmid(bdcSlXmLsgxDO.getYxmid());
                        bdcXmQO.setBdclx(CommonConstantUtils.DYBDCLX_CTD);
                        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            dzwTzm = BdcdyhToolUtils.getDzwTzm(bdcXmDOList.get(0).getBdcdyh());
                        }
                        if (CollectionUtils.isEmpty(bdcFctdPpgxDOList) && CommonConstantUtils.BDCLX_TZM_W.equals(dzwTzm)) {
                            for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcSlYwxxDTOList) {
                                BdcSlXmLsgxDO bdcSlXmLsgx = new BdcSlXmLsgxDO("", bdcSlXmLsgxDO.getYxmid(), "", "", "");
                                List<BdcSlXmLsgxDO> ywlzsLsgxList = (CollectionUtils.isNotEmpty(bdcSlYwxxDTO.getBdcWlSlXmLsgxDOList())) ? bdcSlYwxxDTO.getBdcWlSlXmLsgxDOList() : new ArrayList<>();
                                ywlzsLsgxList.add(bdcSlXmLsgx);
                                bdcSlYwxxDTO.setBdcWlSlXmLsgxDOList(ywlzsLsgxList);
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void cshSjcl(String gzlslid) {
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        String gzldyid = "";
        if (bdcSlJbxxDO != null) {
            gzldyid = bdcSlJbxxDO.getGzldyid();
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(), "");
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                bdcSjclService.listCshBdcSlSjclByLc(gzlslid, bdcSlXmDOList);
            }

        } else {
            //不存在受理基本信息
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                gzldyid = bdcXmDTOList.get(0).getGzldyid();
                List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
                for (BdcXmDTO bdcXmDTO : bdcXmDTOList) {
                    BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
                    BeanUtils.copyProperties(bdcXmDTO, bdcSlXmDO);
                    bdcSlXmDOList.add(bdcSlXmDO);
                }
                bdcSjclService.listCshBdcSlSjclByLc(gzlslid, bdcSlXmDOList);
            }
        }
        //判断是否批量预转现的流程---南通批量预转现创建多个流程需要继承上一手的预告和商品房的收件材料
        if (CollectionUtils.isNotEmpty(extendYxmSjclDyidList) && StringUtils.isNotBlank(gzldyid) && extendYxmSjclDyidList.contains(gzldyid)) {
            try {
                bdcSjclService.extendYxmSjcl(gzlslid);
            } catch (Exception e) {
                LOGGER.error("继承原项目收件材料异常", e);
            }

        }
    }

    @Override
    public void cshBdcSlSqxx(BdcSlCshDTO bdcSlCshDTO) throws Exception {
        //初始化受理项目信息
        List<BdcSlXmDO> bdcSlXmDOList = cshBdcSlXm(bdcSlCshDTO);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList) && StringUtils.isNotBlank(bdcSlXmDOList.get(0).getDjxl())) {
            bdcSlCshDTO.setDjxl(bdcSlXmDOList.get(0).getDjxl());
            bdcSlCshDTO.setBdcSlXmDOList(bdcSlXmDOList);

        }
        //初始化基本信息
        BdcSlJbxxDO bdcSlJbxxDO = bdcJbxxService.insertBdcSlJbxx(bdcSlCshDTO);
        //初始化非登记数据收费信息
        bdcSfService.cshFdjlcSfxx(bdcSlCshDTO.getGzlslid());

        //非登记业务信息初始化
        BdcSlFdjywService bdcSlFdjywService = InterfaceCodeBeanFactory.getBean(bdcSlFdjywServiceSet, bdcSlCshDTO.getGzldyid());
        if (bdcSlFdjywService != null) {
            bdcSlFdjywService.initFdjywxx(bdcSlCshDTO);
        }

        //初始化收件材料,以gzlslid存储
        bdcSjclService.listCshBdcSlSjclByLc(bdcSlCshDTO.getGzlslid(), bdcSlXmDOList);

        //回写信息
        bdcSlxxHxService.hxBdcSlxx(bdcSlJbxxDO);


    }


    /**
     * @param bdcSlCshDTO 受理初始化对象
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化受理项目信息（无登记业务流程数据）
     */
    private List<BdcSlXmDO> cshBdcSlXm(BdcSlCshDTO bdcSlCshDTO) {
        UserDto user = userManagerUtils.getCurrentUser();
        if (null == user) {
            throw new UserInformationAccessException(messageProvider.getMessage("message.nouser"));
        }
        if (StringUtils.isBlank(bdcSlCshDTO.getGzldyid()) || StringUtils.isBlank(bdcSlCshDTO.getJbxxid())) {
            throw new MissingArgumentException("gzldyid,jbxxid");

        }
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlCshDTO.getJbxxid(), "");
        if (CollectionUtils.isEmpty(bdcSlXmDOList)) {

            List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzService.queryBdcDjxlPzByGzldyid(bdcSlCshDTO.getGzldyid());
            //非登记业务流程,没有选择数据,默认生成一条受理项目,无论登记小类配置有几条
            if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
                BdcDjxlPzDO bdcDjxlPzDO =bdcDjxlPzDOList.get(0);
                bdcSlXmDOList = new ArrayList<>();
                BdcSlXmDO bdcSlXmDO = new BdcSlXmDO(user.getId());
                bdcSlXmDO.setJbxxid(bdcSlCshDTO.getJbxxid());
                bdcSlXmDO.setDjxl(bdcDjxlPzDO.getDjxl());
                //获取默认的的登记原因
                BdcDjxlDjyyQO bdcDjxlDjyyQO = new BdcDjxlDjyyQO();
                bdcDjxlDjyyQO.setDjxl(bdcDjxlPzDO.getDjxl());
                bdcDjxlDjyyQO.setGzldyid(bdcSlCshDTO.getGzldyid());
                List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList = bdcXmFeignService.listBdcDjxlDjyyGxWithParam(bdcDjxlDjyyQO);
                //找到设置了默认值的
                if (CollectionUtils.isNotEmpty(bdcDjxlDjyyGxDOList)) {
                    List<BdcDjxlDjyyGxDO> mrDjyyList = bdcDjxlDjyyGxDOList.stream().filter(bdcDjxlDjyyGxDO ->
                            (Objects.nonNull(bdcDjxlDjyyGxDO.getSfmr()) && Objects.equals(CommonConstantUtils.SF_S_DM, bdcDjxlDjyyGxDO.getSfmr()))
                    ).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(mrDjyyList)) {
                        bdcSlXmDO.setDjyy(mrDjyyList.get(0).getDjyy());
                    }
                }
                bdcSlXmService.insertBdcSlXm(bdcSlXmDO);
                bdcSlXmDOList.add(bdcSlXmDO);
            } else {
                throw new AppException("不动产登记小类配置（BDC_DJXL_PZ）未配置,请联系管理员,工作流定义id为:{}" + bdcSlCshDTO.getGzldyid());

            }
        }
        return bdcSlXmDOList;
    }



    @Override
    public Map<String, String> cshCjBdcXm(BdcSlCshDTO bdcSlCshDTO) throws Exception {
        Date startTime = new Date();
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(bdcSlCshDTO.getJbxxid()) || StringUtils.isBlank(bdcSlCshDTO.getGzldyid())) {
            throw new AppException("初始化缺失基本信息ID或工作流定义ID！");
        }
        LOGGER.info("受理信息初始化开始：流程基本信息ID{}", bdcSlCshDTO.getJbxxid());
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcSlCshDTO.getJbxxid());
        if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(bdcSlJbxxDO.getGzlslid());
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                throw new AppException("数据已初始化,请检查");
            }
        }

        //页面工作流定义id不为空，先根据工作流定义id创建流程再初始化
        String userName = "";
        if (StringUtils.isNotBlank(bdcSlCshDTO.getCzrid())) {
            UserDto userDto = userManagerUtils.getUserByUserid(bdcSlCshDTO.getCzrid());
            if (userDto != null) {
                userName = userDto.getUsername();
            }

        } else {
            userName = userManagerUtils.getCurrentUserName();
        }
        TaskData taskData = processInstanceClient.directStartProcessInstance(bdcSlCshDTO.getGzldyid(), userName, "", "", bdcSlCshDTO.getIntegerCnqx());
        //初始化成功标志
        Boolean cshSuccess = false;
        if (taskData != null) {
            try {
                if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ, dataversion) && bdcSlCshDTO.getTslc()) {
                    bdcSlCshDTO.setGzlslid(taskData.getProcessInstanceId());
                    cshBdcSlSqxx(bdcSlCshDTO);
                } else {
                    bdcSlCshDTO.setGzlslid(taskData.getProcessInstanceId());
                    bdcSlJbxxDO = bdcJbxxService.insertBdcSlJbxx(bdcSlCshDTO);
                    bdcSlFeignService.cshBdcSlxx(bdcSlCshDTO.getJbxxid());
                    //标志初始化成功
                    cshSuccess = true;
                    cshSjcl(taskData.getProcessInstanceId());
                    bdcSfService.cshSfxx(taskData.getProcessInstanceId(), null);
                }
            } catch (Exception ex) {
                LOGGER.error("初始化失败", ex);
                //删除受理信息
                bdcSlFeignService.deleteBdcSlxx(taskData.getProcessInstanceId());
                //删除业务信息
                if (cshSuccess) {
                    //初始化成功执行
                    bdcInitFeignService.deleteYwxx(taskData.getProcessInstanceId());
                }
                //删除流程
                taskUtils.deleteTask(taskData.getProcessInstanceId(), taskData.getReason());
                throw ex;
            }
            map.put("processInsId", taskData.getProcessInstanceId());
            map.put("taskId", taskData.getTaskId());
            map.put("slbh", bdcSlJbxxDO != null ? bdcSlJbxxDO.getSlbh() : "");
        }

        if (StringUtils.isBlank(map.get("msg"))) {
            map.put("msg", "success");
        }
        long time = System.currentTimeMillis() - startTime.getTime();
        LOGGER.info("已完成流程基本信息ID{}受理信息初始化，所耗时间：{}", bdcSlCshDTO.getJbxxid(), time);
        return map;

    }



    /**
     * @param bdcSlYwxxDTO
     * @return 受理信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 逻辑幢初始化逻辑
     */
    private BdcSlxxInitDTO initLjzBdcSlxx(BdcSlYwxxDTO bdcSlYwxxDTO, String gzldyid, String czrid, String jbxxid) {
        BdcSlxxInitDTO bdcSlxxInitDTO = new BdcSlxxInitDTO();
        if (StringUtils.isBlank(bdcSlYwxxDTO.getFwDcbIndex())) {
            return bdcSlxxInitDTO;
        }
        List<BdcSlXmDO> batchBdcSlXmDOList = new ArrayList<>();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        List<String> fwhsIndexList = new ArrayList<>();
        //存储户室和预测户室信息
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = new ArrayList<>();
        List<FwHsDO> fwHsDOList = new ArrayList<>();
        List<FwYchsDO> fwYchsDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcSlYwxxDTO.getFwDcbIndex())) {
            if (StringUtils.equals("ychs", bdcSlYwxxDTO.getLx())) {
                fwYchsDOList = fwYcHsFeignService.listFwYchsByFwDcbIndex(bdcSlYwxxDTO.getFwDcbIndex(), bdcSlYwxxDTO.getQjgldm());
            } else if (StringUtils.equals("hs", bdcSlYwxxDTO.getLx())) {
                fwHsDOList = fwHsFeignService.listFwhsByFwDcbIndex(bdcSlYwxxDTO.getFwDcbIndex(), bdcSlYwxxDTO.getQjgldm());
            } else {
                //预测户室和实测户室均读取
                fwHsDOList = fwHsFeignService.listFwhsByFwDcbIndex(bdcSlYwxxDTO.getFwDcbIndex(), bdcSlYwxxDTO.getQjgldm());
                fwYchsDOList = fwYcHsFeignService.listFwYchsByFwDcbIndex(bdcSlYwxxDTO.getFwDcbIndex(), bdcSlYwxxDTO.getQjgldm());
            }
            if (CollectionUtils.isNotEmpty(fwHsDOList)) {
                for (FwHsDO fwHsDO : fwHsDOList) {
                    BdcSlYwxxDTO hsSlYwxxDTO = new BdcSlYwxxDTO();
                    hsSlYwxxDTO.setBdcdyh(fwHsDO.getBdcdyh());
                    hsSlYwxxDTO.setQjid(fwHsDO.getFwHsIndex());
                    hsSlYwxxDTO.setZl(fwHsDO.getZl());
                    hsSlYwxxDTO.setYt(fwHsDO.getGhyt());
                    bdcSlYwxxDTOList.add(hsSlYwxxDTO);
                }
            }
            if (CollectionUtils.isNotEmpty(fwYchsDOList)) {
                for (FwYchsDO fwYchsDO : fwYchsDOList) {
                    BdcSlYwxxDTO ychsSlYwxxDTO = new BdcSlYwxxDTO();
                    ychsSlYwxxDTO.setBdcdyh(fwYchsDO.getBdcdyh());
                    ychsSlYwxxDTO.setQjid(fwYchsDO.getFwHsIndex());
                    ychsSlYwxxDTO.setZl(fwYchsDO.getZl());
                    ychsSlYwxxDTO.setYt(fwYchsDO.getGhyt());
                    bdcSlYwxxDTOList.add(ychsSlYwxxDTO);
                }
            }

            List<BdcDjxlPzDO> bdcDjxlPzDOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
                for (BdcSlYwxxDTO hsychsSlYwxxDTO : bdcSlYwxxDTOList) {
                    if (StringUtils.isNotBlank(hsychsSlYwxxDTO.getBdcdyh())) {
                        if (StringUtils.isNotEmpty(hsychsSlYwxxDTO.getYt())) {
                            // 如果用途是在 商业 公寓 住宅类的用途内，则设置为主房
                            if (checkSfzf(hsychsSlYwxxDTO.getYt())) {
                                hsychsSlYwxxDTO.setSfzf(CommonConstantUtils.SF_S_DM);
                            } else {
                                hsychsSlYwxxDTO.setSfzf(CommonConstantUtils.SF_F_DM);
                            }
                        } else {//用途是空的话，还是默认 不是主房
                            hsychsSlYwxxDTO.setSfzf(CommonConstantUtils.SF_F_DM);
                        }
                        fwhsIndexList.add(hsychsSlYwxxDTO.getQjid());
                        Integer qllx;
                        if (bdcSlYwxxDTO.getQllx() == null) {
                            qllx = bdcQllxService.getQllxByBdcdyh(hsychsSlYwxxDTO.getBdcdyh(), bdcSlYwxxDTO.getDyhcxlx());
                            if (qllx != null) {
                                bdcSlYwxxDTO.setQllx(qllx);
                                bdcDjxlPzDOList = bdcDjxlPzService.listBdcDjxlPzByGzldyid(gzldyid, qllx, null);
                            }
                        }
                        if (CollectionUtils.isEmpty(bdcDjxlPzDOList)) {
                            throw new AppException("不动产单元号：" + hsychsSlYwxxDTO.getBdcdyh() + "不动产登记小类配置（BDC_DJXL_PZ）未配置,请联系管理员");
                        } else {
                            String yxmid = "";
                            List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
                            for (int i = 0; i < bdcDjxlPzDOList.size(); i++) {
                                BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzDOList.get(i);
                                BdcSlXmDO bdcSlXmDO = new BdcSlXmDO(czrid);
                                bdcSlXmDO.setQllx(bdcDjxlPzDO.getQllx());
                                bdcSlXmDO.setBdcdyh(hsychsSlYwxxDTO.getBdcdyh());
                                bdcSlXmDO.setZl(hsychsSlYwxxDTO.getZl());
                                bdcSlXmDO.setQlr(bdcSlYwxxDTO.getQlr());
                                bdcSlXmDO.setQjid(hsychsSlYwxxDTO.getQjid());
                                bdcSlXmDO.setJbxxid(jbxxid);
                                bdcSlXmDO.setDjxl(bdcDjxlPzDO.getDjxl());
                                bdcSlXmDO.setSfzf(hsychsSlYwxxDTO.getSfzf());
                                if (bdcDjxlPzDO.getBdclx() != null) {
                                    bdcSlXmDO.setBdclx(bdcDjxlPzDO.getBdclx());
                                } else if (bdcSlYwxxDTO.getBdclx() != null) {
                                    bdcSlXmDO.setBdclx(bdcSlYwxxDTO.getBdclx());
                                } else {
                                    String bdclx = BdcdyhToolUtils.queryBdclxByBdcdyh(hsychsSlYwxxDTO.getBdcdyh(), "LJZ");
                                    if (StringUtils.isNotBlank(bdclx)) {
                                        bdcSlXmDO.setBdclx(Integer.parseInt(bdclx.split("/")[0]));
                                        bdcSlYwxxDTO.setBdclx(bdcSlXmDO.getBdclx());
                                    }
                                }
                                bdcSlXmDOList.add(bdcSlXmDO);
                                if (i == 0) {
                                    yxmid = bdcSlYwxxDTO.getXmid();
                                } else if (bdcDjxlPzDO.getSxh() > 1) {
                                    yxmid = bdcSlXmDOList.get(bdcDjxlPzDO.getSxh() - 2).getXmid();
                                }
                                BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), yxmid, "", "", "");
                                bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                                batchBdcSlXmDOList.add(bdcSlXmDO);
                            }
                        }
                    }
                }
                if (CollectionUtils.isEmpty(batchBdcSlXmDOList)) {
                    throw new AppException("逻辑幢：" + bdcSlYwxxDTO.getFwDcbIndex() + "未找到有效的户室不动产单元号，请检查数据");

                }
                //插入前清除不动产单元已有数据
                bdcSlXmService.deleteYxFwhs(fwhsIndexList, jbxxid);
                bdcSlxxInitDTO.setBdcSlXmDOList(batchBdcSlXmDOList);
                bdcSlxxInitDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);

            } else {
                throw new AppException("逻辑幢：" + bdcSlYwxxDTO.getFwDcbIndex() + "未找到有效的户室，请检查数据");

            }
        }
        return bdcSlxxInitDTO;

    }

    /**
     * @param bdcSlxxInitDTOList 受理信息
     * @param gzldyid            工作流定义ID
     * @param xmidDyzmhMap       项目与原抵押证明号关系
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理证书序号
     */
    private void dealZsxh(List<BdcSlxxInitDTO> bdcSlxxInitDTOList, String gzldyid, Map<String, String> xmidDyzmhMap) {
        if (CollectionUtils.isNotEmpty(bdcSlxxInitDTOList)) {
            List<BdcYxbdcdyKgPzDO> bdcYxbdcdyKgPzDOS = bdcYxbdcdyKgPzService.queryBdcYxbdcdyKgPzDOListByGzldyid(gzldyid);
            //默认一本证的项目
            List<BdcSlXmDO> ybzXmList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(bdcYxbdcdyKgPzDOS)) {
                Integer mrzsxh = Constants.ZSSL_MRZ;
                for (BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO : bdcYxbdcdyKgPzDOS) {
                    if (bdcYxbdcdyKgPzDO != null && Constants.ZSSL_MRZ.equals(bdcYxbdcdyKgPzDO.getZsslmrz())) {
                        Integer finalMrzsxh = mrzsxh;
                        bdcSlxxInitDTOList.forEach(bdcSlxxInitDTO -> bdcSlxxInitDTO.getBdcSlXmDOList().forEach(bdcSlXmDO -> {
                            //配置生成一本证,生成一本证默认证书序号相同,依次递增
                            if (StringUtils.isBlank(bdcYxbdcdyKgPzDO.getDjxl()) || StringUtils.equals(bdcYxbdcdyKgPzDO.getDjxl(), bdcSlXmDO.getDjxl())) {
                                bdcSlXmDO.setZsxh(finalMrzsxh);
                                ybzXmList.add(bdcSlXmDO);
                            }
                        }));
                        mrzsxh++;
                    }
                }
            }
            //抵押项目证书序号默认处理
            if (CollectionUtils.isNotEmpty(bdcSlxxInitDTOList)) {
                Integer dyzsxh = Constants.ZSXH_DYAQ_MRZ;
                Map<String, Integer> dyaqGroupMap = new HashMap<>();
                //2.不是默认一本证则找出抵押项目
                //抵押项目登记小类一致,如果存在原抵押证明号,则原抵押证明单号相同的证书序号相同
//                for (BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO : bdcYxbdcdyKgPzDOS) {
                    for (BdcSlxxInitDTO bdcSlxxInitDTO : bdcSlxxInitDTOList) {
                        for (BdcSlXmDO bdcSlXmDO : bdcSlxxInitDTO.getBdcSlXmDOList()) {
                            //抵押配置了多本证，证书序号统一设置为空（不处理）--多本证, 否则，不配置生成一本证
                            boolean contin = false;
                            if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlXmDO.getQllx()) && CollectionUtils.isNotEmpty(bdcYxbdcdyKgPzDOS)) {
                                //判断是否配置了已选单元开关，如果配置了，循环开关，否则走默认逻辑
                                for (BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO : bdcYxbdcdyKgPzDOS) {
                                    if ((StringUtils.isBlank(bdcYxbdcdyKgPzDO.getDjxl()) || StringUtils.equals(bdcYxbdcdyKgPzDO.getDjxl(), bdcSlXmDO.getDjxl()))
                                            && Objects.equals(9, bdcYxbdcdyKgPzDO.getZsslmrz())) {
                                        contin = true;
                                        break;
                                    }
                                }
                                if (contin) {
                                    continue;
                                }
                            }
                            if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcSlXmDO.getQllx()) && !ybzXmList.contains(bdcSlXmDO)) {
                                if (dyaqGroupMap.containsKey(bdcSlXmDO.getDjxl() + "_" + xmidDyzmhMap.get(bdcSlXmDO.getXmid()))) {
                                    bdcSlXmDO.setZsxh(dyaqGroupMap.get(bdcSlXmDO.getDjxl() + "_" + xmidDyzmhMap.get(bdcSlXmDO.getXmid())));
                                } else {
                                    bdcSlXmDO.setZsxh(dyzsxh);
                                    dyaqGroupMap.put(bdcSlXmDO.getDjxl() + "_" + xmidDyzmhMap.get(bdcSlXmDO.getXmid()), dyzsxh);
                                    dyzsxh--;
                                }
                            }
                        }
                    }
//                }
            }
        }
    }

    @Override
    public void cshHtxxSjcl(String gzlslid, String htbh) {
        if (StringUtils.isAnyBlank(gzlslid, htbh)) {
            return;
        }
        BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if (Objects.nonNull(bdcSlJbxxDO)) {
            // 接口请求参数
            Map<String, String> param = new HashMap<>(2);
            param.put("contractNo", htbh);

            Object response;
            String spfGzldyid = bdcJbxxService.spfGzldyid();
            // 调用接口获取合同信息
            if (spfGzldyid.indexOf(bdcSlJbxxDO.getGzldyid()) > -1) {
                response = exchangeInterfaceFeignService.requestInterface("fcjySpfBaxx", param);
            } else {
                response = exchangeInterfaceFeignService.requestInterface("fcjyClfHtxx", param);
            }

            if (Objects.isNull(response)) {
                throw new AppException("调取常州合同信息接口返回值为空");
            }
            String jsonString = JSONObject.toJSONString(response);
            LOGGER.info("调取常州合同信息接口返回值：{}", jsonString);
            FcjyClfHtxxDTO fcjyClfHtxxDTO = JSONObject.parseObject(jsonString, FcjyClfHtxxDTO.class);
            if (Objects.nonNull(fcjyClfHtxxDTO) && StringUtils.isNotBlank(fcjyClfHtxxDTO.getDzht())) {
                String base64Str = fcjyClfHtxxDTO.getDzht();
                // 判断base64字符串是否拥有头信息，没有添加pdf的base64头信息
                if (!base64Str.startsWith("data:")) {
                    base64Str = CommonConstantUtils.BASE64_QZ_PDF + base64Str;
                }
                try {
                    BdcPdfDTO bdcPdfDTO = new BdcPdfDTO(gzlslid, "", "买卖合同（1）", "买卖合同", CommonConstantUtils.WJHZ_PDF);
                    bdcPdfDTO.setBase64str(base64Str);
                    bdcUploadFileUtils.uploadBase64File(bdcPdfDTO);
                } catch (IOException e) {
                    LOGGER.error("生成电子合同信息失败：{}", e.getMessage());
                }


            }


        }

    }

    @Override
    public CommonResponse yzCshxxBeforeCj(BdcSlCshDTO bdcSlCshDTO){
        try {
            //验证是否已存在基本信息
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcSlCshDTO.getJbxxid());
            if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
                return CommonResponse.fail("jbxxid"+bdcSlJbxxDO.getJbxxid()+"受理基本数据已生成");
            }
            //验证例外审核
            if (Boolean.TRUE.equals(cjyzlwsh)) {
                if (StringUtils.isNotBlank(bdcSlCshDTO.getSlbh())) {
                    //查询是否存在审核通过以外的例外审核数据
                    BdcGzlwShQO bdcGzlwShQO = new BdcGzlwShQO();
                    //南通受理编号需要增加特征码
                    String slbh =bdcSlCshDTO.getSlbh();
                    if (StringUtils.equals(slbhgs, Constants.VERSION_NT)){
                        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(bdcSlCshDTO.getJbxxid(), "");
                        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                            String bdcdyh = bdcSlXmDOList.get(0).getBdcdyh();
                            String tzm = BdcdyhToolUtils.queryTzmByBdcdyh(bdcdyh);
                            slbh =tzm +bdcSlCshDTO.getSlbh();
                        }
                    }
                    bdcGzlwShQO.setSlbh(slbh);
                    bdcGzlwShQO.setPcshzt(CommonConstantUtils.GZLW_SHZT_TG);
                    List<BdcGzlwShDO> bdcGzlwShDOList = bdcGzlwFeignService.listBdcGzlwByBdcGzlwShQO(bdcGzlwShQO);
                    if (CollectionUtils.isNotEmpty(bdcGzlwShDOList)) {
                        return CommonResponse.fail("请先在例外审核页面审核通过后再创建");
                    }
                }
            }
        }catch (Exception e) {
            LOGGER.error("初始化创建前验证失败:{}", e.getMessage());
            return CommonResponse.fail(ExceptionUtils.getFeignErrorMsg(e));
        }
        return CommonResponse.ok();
    }

    @Override
    public void afterGzlwsh(List<String> slbhList,Integer shzt){
        if(CollectionUtils.isNotEmpty(slbhList) &&Boolean.TRUE.equals(cjyzlwsh)){
            LocalThreadPool.threadPool.execute(()-> {
                for (String slbh : slbhList) {
                    //判断流程是否已创建
                    BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxBySlbh(slbh, "");
                    if (bdcSlJbxxDO != null && StringUtils.isBlank(bdcSlJbxxDO.getGzlslid())) {
                        String cznr = "";
                        //获取基本信息ID
                        String jbxxid = bdcSlJbxxDO.getJbxxid();
                        if (CommonConstantUtils.GZLW_SHZT_TG.equals(shzt)) {
                            //审核通过,判断当前受理编号是否存在审核通过以外的例外审核数据,不存在自动创建流程
                            BdcGzlwShQO bdcGzlwShQO = new BdcGzlwShQO();
                            bdcGzlwShQO.setSlbh(slbh);
                            bdcGzlwShQO.setPcshzt(CommonConstantUtils.GZLW_SHZT_TG);
                            List<BdcGzlwShDO> bdcGzlwShDOList = bdcGzlwFeignService.listBdcGzlwByBdcGzlwShQO(bdcGzlwShQO);
                            if (CollectionUtils.isEmpty(bdcGzlwShDOList)) {
                                BdcSlCshDTO bdcSlCshDTO = new BdcSlCshDTO();
                                bdcSlCshDTO.setSlbh(slbh);
                                bdcSlCshDTO.setJbxxid(bdcSlJbxxDO.getJbxxid());
                                bdcSlCshDTO.setGzldyid(bdcSlJbxxDO.getGzldyid());
                                bdcSlCshDTO.setCzrid(bdcSlJbxxDO.getSlrid());
                                try {
                                    //先删除受理基本信息
                                    bdcSlJbxxService.deleteBdcSlJbxxByJbxxid(jbxxid);
                                    Map<String, String> cjResult = cshCjBdcXm(bdcSlCshDTO);
                                    if (StringUtils.isNotBlank(cjResult.get("processInsId"))) {
                                        LOGGER.info("slbh:{},基本信息ID:{}例外审核通过,执行流程自动创建操作", slbh, jbxxid);
                                        cznr = slbh + "例外审核通过,执行流程自动创建操作,基本信息ID" + jbxxid;
                                    } else {
                                        LOGGER.info("slbh:{},jbxxid:{}例外审核通过,执行流程自动创建操作失败", slbh, jbxxid);
                                        cznr = slbh + "例外审核通过,执行流程自动创建操作失败,基本信息ID" + jbxxid;
                                    }
                                } catch (Exception e) {
                                    LOGGER.error("slbh:{}基本信息ID:{}例外审核通过自动创建流程失败:{}", slbh, jbxxid, e.getMessage());
                                    cznr = slbh + "例外审核不通过,执行流程自动创建操作失败,基本信息ID" + jbxxid;
                                }
                            }
                        } else if (CommonConstantUtils.GZLW_SHZT_BTG.equals(shzt)) {
                            //审核不通过,直接删除受理信息
                            cznr = slbh + "例外审核不通过,执行删除受理信息操作,基本信息ID" + jbxxid;
                            List<String> xmidList = bdcSlXmService.getXmidListByJbxxid(jbxxid);
                            bdcSlXmService.deleteBdcSlxx(xmidList, CommonConstantUtils.SL_DELETE_GWC);
                            bdcSlJbxxService.deleteBdcSlJbxxByJbxxid(jbxxid);
                            LOGGER.info("slbh:{},jbxxid:{}例外审核不通过,删除对应受理信息", slbh, jbxxid);
                        }
                        if (StringUtils.isNotBlank(cznr)) {
                            //保存日志
                            Map<String, Object> logdata = new HashMap<>();
                            logdata.put("CZRQ", new Date());
                            logdata.put("slbh", slbh);
                            logdata.put("CZ", cznr);
                            AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), "GZLW_SH", logdata);
                            zipkinAuditEventRepository.add(auditEvent);
                        }
                    }
                }
            });
        }

    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  匹配预告登记种类
      */
    private List<BdcDjxlPzDO> ppygdjzl(List<BdcDjxlPzDO> bdcDjxlPzDOList,Integer yqllx,String yxmid){
        List<BdcDjxlPzDO> bdcDjxlPzDOS =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
            for (BdcDjxlPzDO bdcDjxlPzDO : bdcDjxlPzDOList) {
                if (bdcDjxlPzDO.getYgdjzl() != null) {
                    if (CommonConstantUtils.QLLX_YG_DM.equals(yqllx) && StringUtils.isNotBlank(yxmid)) {
                        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(yxmid);
                        if (bdcQl instanceof BdcYgDO) {
                            BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                            if (bdcYgDO.getYgdjzl() != null && StringUtils.contains(bdcDjxlPzDO.getYgdjzl(), bdcYgDO.getYgdjzl().toString())) {
                                bdcDjxlPzDOS.add(bdcDjxlPzDO);
                            }
                        }
                    }
                } else {
                    bdcDjxlPzDOS.add(bdcDjxlPzDO);
                }
            }
        }
        return bdcDjxlPzDOS;
    }
}

