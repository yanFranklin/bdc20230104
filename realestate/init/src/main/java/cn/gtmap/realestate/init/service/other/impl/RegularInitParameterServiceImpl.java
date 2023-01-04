package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSqrDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.other.InitFwKgService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.other.RegularInitParameterService;
import cn.gtmap.realestate.init.util.CommonUtils;
import cn.gtmap.realestate.init.util.DozerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/6
 * @description
 */
@Service
public class RegularInitParameterServiceImpl implements RegularInitParameterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegularInitParameterServiceImpl.class);

    @Autowired
    private InitFwKgService initFwKgService;
    @Autowired
    private DozerBeanMapper acceptDozerMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private InitServiceQoService initServiceQoService;
    @Autowired
    private DjxxFeignService djxxFeignService;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    /**
     * 受理时间以受理人认领时间为准,无受理人不生成受理时间
     */
    @Value("${sjd.rl.slsj:false}")
    private Boolean sjdrlslsj;

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcSlxxDTO
     *@return List<InitServiceQO>
     *@description 将受理模块传来的参数转换为初始化模块的标准入参(此方法后期可以考虑和初始化部分整合，直接用受理结构做转换)
     */
    @Override
    public List<InitServiceQO> changeAcceptDTOIntoInitQO(BdcSlxxDTO bdcSlxxDTO) {
        List<InitServiceQO> initServiceQOList = new ArrayList<>();
        UserDto userDto=userManagerUtils.getCurrentUser();
        //存储djh和地籍信息对照关系
        Map<String,DjxxResponseDTO> mapDjxx=new HashMap<>();
        if (bdcSlxxDTO != null && bdcSlxxDTO.getBdcSlJbxx() != null && CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            //存储初始化开关配置数据，避免重复查询
            Map<String,BdcCshFwkgDO> cshFwkgMap=new HashMap<>();
            //存储所有项目的原项目id
            List<String> yxmids=new ArrayList<>();
            //判定是否增量初始化
            boolean iszl=bdcSlxxDTO.getBdcSlXmList().get(0)!=null
                    && bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm()!=null
                    && CommonConstantUtils.SF_S_DM.equals(bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm().getSfzlcsh());
            //存储已存在项目的开关实例数据
            Map<String, List<BdcCshFwkgSlDO>> cshFwkgSlDataMap=null;
            if(iszl){
                List<BdcCshFwkgSlDO> list=bdcXmService.listBdCshSl(bdcSlxxDTO.getBdcSlJbxx().getGzlslid());
                if(CollectionUtils.isNotEmpty(list)){
                    cshFwkgSlDataMap=list.stream().collect(Collectors.groupingBy(BdcCshFwkgSlDO::getDjxl));
                }
            }
            //循环受理项目
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlxxDTO.getBdcSlXmList()) {
                InitServiceQO initServiceQO = initServiceQoService.creatInitServiceQO();
                //获取单一项目定义的初始化服务开关信息
                initServiceQO.setBdcCshFwkgSl(changeBdcSlParameterFwkg(bdcSlXmDTO,cshFwkgMap));
                //原项目id集合存到qo里
                initServiceQO.setYxmids(yxmids);
                if (bdcSlXmDTO != null) {
                    //转化单个项目的历史关系的信息
                    initServiceQO.setBdcXmLsgxList(changeBdcSlParameterLsgx(bdcSlXmDTO.getBdcSlXmLsgxList(),initServiceQO.getBdcCshFwkgSl()));
                    //获取原项目id
                    initServiceQO.setYxmid(changeBdcSlParameterYxmid(bdcSlXmDTO.getBdcSlXmLsgxList()));
                    //原项目id到集合里
                    if(StringUtils.isNotBlank(initServiceQO.getYxmid())){
                        yxmids.add(initServiceQO.getYxmid());
                    }
                    //受理项目存储
                    initServiceQO.setBdcSlXmDO(bdcSlXmDTO.getBdcSlXm());
                    //第三方受理数据存储
                    initServiceQO.setDsfSlxxDTO(bdcSlXmDTO.getDsfSlxxDTO());
                    //获取受理传过来的权利人 08-05 wzjStart
                    changeBdcSlParameterQlr(bdcSlXmDTO.getBdcSlSqrDTOList(), bdcSlXmDTO.getBdcSlSqrDOList(), initServiceQO);
                    //获取受理传过来的权利人 08-05 wzjEnd
                    //获取受理传过来的权利信息
                    initServiceQO.setBdcSlQl(bdcSlXmDTO.getBdcSlQl());
                    // 添加第三方权利人信息
                    initServiceQO.setBdcDsQlrDOList(bdcSlXmDTO.getBdcDsQlrDOList());
                    //权籍管理代码
                    initServiceQO.setQjgldm(bdcSlXmDTO.getBdcSlXm() != null ? bdcSlXmDTO.getBdcSlXm().getQjgldm() : "");
                    //获取受理传过来的更正信息
                    initServiceQO.setBdcGzdjDO(bdcSlXmDTO.getBdcGzdjDO());
                    //是否信息补录流程创建数据
                    initServiceQO.setXxblLccj(bdcSlxxDTO.getXxbl());
                }
                //转化受理传来的参数中项目的参数信息
                initServiceQO.setBdcXm(changeBdcSlParameterJbxx(bdcSlxxDTO.getBdcSlJbxx(), bdcSlXmDTO,userDto));
                //存储已存在项目的开关实例数据
                initServiceQO.setCshFwkgSlDataMap(cshFwkgSlDataMap);
                //获取地籍信息
                getLpbDjxx(initServiceQO,mapDjxx);
                initServiceQOList.add(initServiceQO);
            }
        }
        return initServiceQOList;
    }

    /**
     * @param bdcSlSqrDOList
     * @param initServiceQO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取受理传过来的权利人
     */
    public void changeBdcSlParameterQlr(List<BdcSlSqrDTO> bdcSlSqrDTOList,List<BdcSlSqrDO> bdcSlSqrDOList, InitServiceQO initServiceQO) {
        List<BdcQlrDO> bdcQlrDOList=new ArrayList<>();
        List<BdcQlrDO> bdcYwrDOList=new ArrayList<>();
        List<BdcDlrDO> bdcDlrDOList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcSlSqrDTOList)){
            for (BdcSlSqrDTO slSqrDTO : bdcSlSqrDTOList) {
                BdcQlrDO qlrDO = new BdcQlrDO();
                dozerUtils.initBeanDateConvert(slSqrDTO.getBdcSlSqrDO(), qlrDO, "slqlr");
                if (!StringUtils.isAnyBlank(qlrDO.getQlrid(), qlrDO.getQlrmc())) {
                    //代理人
                    if(CollectionUtils.isNotEmpty(slSqrDTO.getBdcDlrDOList())){
                        for(BdcDlrDO bdcDlrDO:slSqrDTO.getBdcDlrDOList()){
                            if(StringUtils.isNotBlank(bdcDlrDO.getDlrmc())) {
                                bdcDlrDO.setDlrid(UUIDGenerator.generate16());
                                bdcDlrDO.setQlrid(qlrDO.getQlrid());
                                bdcDlrDO.setXmid(qlrDO.getXmid());
                                bdcDlrDOList.add(bdcDlrDO);
                            }
                        }
                        if(StringUtils.isBlank(qlrDO.getDlrmc())){
                            //回写代理人到权利人表
                            qlrDO.setDlrmc(StringToolUtils.resolveBeanToAppendStr(slSqrDTO.getBdcDlrDOList(),
                                    "getDlrmc",CommonConstantUtils.ZF_YW_DH));
                            qlrDO.setDlrzjh(StringToolUtils.resolveBeanToAppendStr(slSqrDTO.getBdcDlrDOList(),
                                    "getZjh",CommonConstantUtils.ZF_YW_DH));
                            qlrDO.setDlrzjzl(StringToolUtils.resolveBeanToAppendStr(slSqrDTO.getBdcDlrDOList(),
                                    "getZjzl",CommonConstantUtils.ZF_YW_DH));
                            qlrDO.setDlrdh(StringToolUtils.resolveBeanToAppendStr(slSqrDTO.getBdcDlrDOList(),
                                    "getDh",CommonConstantUtils.ZF_YW_DH));
                        }
                    }else if(StringUtils.isNotBlank(qlrDO.getDlrmc())){
                        BdcDlrDO bdcDlrDO =new BdcDlrDO();
                        bdcDlrDO.setDlrid(UUIDGenerator.generate16());
                        bdcDlrDO.setQlrid(qlrDO.getQlrid());
                        bdcDlrDO.setXmid(qlrDO.getXmid());
                        bdcDlrDO.setDlrmc(qlrDO.getDlrmc());
                        if(qlrDO.getDlrzjzl() != null) {
                            bdcDlrDO.setZjzl(Integer.parseInt(qlrDO.getDlrzjzl()));
                        }
                        bdcDlrDO.setZjh(qlrDO.getDlrzjh());
                        bdcDlrDO.setDh(qlrDO.getDlrdh());
                        bdcDlrDOList.add(bdcDlrDO);

                    }
                    if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR,qlrDO.getQlrlb())) {
                        bdcQlrDOList.add(qlrDO);
                    } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR,qlrDO.getQlrlb())) {
                        bdcYwrDOList.add(qlrDO);
                    }
                }

            }

        }else if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
            for (BdcSlSqrDO slSqrDO : bdcSlSqrDOList) {
                BdcQlrDO qlrDO = new BdcQlrDO();
                dozerUtils.initBeanDateConvert(slSqrDO, qlrDO, "slqlr");
                if (!StringUtils.isAnyBlank(qlrDO.getQlrid(), qlrDO.getQlrmc())) {
                    if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR,qlrDO.getQlrlb())) {
                        bdcQlrDOList.add(qlrDO);
                    } else if (StringUtils.equals(CommonConstantUtils.QLRLB_YWR,qlrDO.getQlrlb())) {
                        bdcYwrDOList.add(qlrDO);
                    }
                }
            }

        }
        if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
            initServiceQO.setBdcQlrList(bdcQlrDOList);
        }
        if(CollectionUtils.isNotEmpty(bdcYwrDOList)){
            initServiceQO.setBdcYwrList(bdcYwrDOList);
        }
        if(CollectionUtils.isNotEmpty(bdcDlrDOList)){
            initServiceQO.setBdcDlrDOList(bdcDlrDOList);
        }
    }

    /**
     * @param bdcSlXmDTO
     * @param bdcSlJbxxDO
     * @param userDto
     * @return BdcXmDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 转化初始化需要的受理传来的参数中项目参数的信息
     */
    public BdcXmDO changeBdcSlParameterJbxx(BdcSlJbxxDO bdcSlJbxxDO, BdcSlXmDTO bdcSlXmDTO,UserDto userDto) {
        BdcXmDO bdcXmDO = new BdcXmDO();
        //转化受理传来的参数中的项目的共有信息
        if (bdcSlJbxxDO != null) {
            acceptDozerMapper.map(bdcSlJbxxDO,bdcXmDO);
            //受理时间
            if(bdcXmDO.getSlsj() == null){
                bdcXmDO.setSlsj(new Date());
            }
        }
        //转换第三方受理项目到单个项目的信息
        if (bdcSlXmDTO != null) {
            if(bdcSlXmDTO.getBdcSlXm() != null){
                acceptDozerMapper.map(bdcSlXmDTO.getBdcSlXm(),bdcXmDO);
            }
            //转换受理传来的参数中的单个项目的信息
            if(bdcSlXmDTO.getDsfSlxxDTO()!=null){
                acceptDozerMapper.map(bdcSlXmDTO.getDsfSlxxDTO(),bdcXmDO);
            }
            //转换受理传来的权利信息
            if(bdcSlXmDTO.getBdcSlQl()!=null){
                acceptDozerMapper.map(bdcSlXmDTO.getBdcSlQl(),bdcXmDO);
            }
        }
        //2019-09-19 lyq 如果当前token获取到的用户为null 则 使用基本信息中传递的SLR获取用户信息
        if(userDto == null && bdcSlJbxxDO!=null && StringUtils.isNotBlank(bdcSlJbxxDO.getSlrid())){
            userDto = userManagerUtils.getUserByUserid(bdcSlJbxxDO.getSlrid());
        }
        if(userDto!=null){
            if(CollectionUtils.isNotEmpty(userDto.getOrgRecordList())){
                OrganizationDto organizationDto=userDto.getOrgRecordList().get(0);
                if(organizationDto!=null){
                    if(StringUtils.isBlank(bdcXmDO.getQxdm())){
                        bdcXmDO.setQxdm(organizationDto.getRegionCode());
                    }
                    if(StringUtils.isBlank(bdcXmDO.getDjbmdm())){
                        bdcXmDO.setDjbmdm(organizationDto.getCode());
                    }
                    //受理的登记机构空的话存平台数据
                    if(StringUtils.isBlank(bdcXmDO.getDjjg())){
                        bdcXmDO.setDjjg(bdcSlJbxxFeignService.queryDjjgByUser(userDto));
                    }
                }
            }
            //更新受理人和受理人ID
            if(StringUtils.isBlank(bdcXmDO.getSlr())){
                bdcXmDO.setSlr(userDto.getAlias());
            }
            if(StringUtils.isBlank(bdcXmDO.getSlrid())){
                bdcXmDO.setSlrid(userDto.getId());
            }
        }
        if(StringUtils.isBlank(bdcXmDO.getSlr()) &&Boolean.TRUE.equals(sjdrlslsj)){
            //没有受理人,清空受理时间
            bdcXmDO.setSlsj(null);
        }
        return bdcXmDO;
    }

    /**
     * @param bdcSlXmLsgxDOList
     * @param bdcCshFwkgSlDO
     * @return List<BdcXmLsgxDO>
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 转化初始化需要的受理传来的参数中单个项目的历史关系信息
     */
    public List<BdcXmLsgxDO> changeBdcSlParameterLsgx(List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList,BdcCshFwkgSlDO bdcCshFwkgSlDO) {
        List<BdcXmLsgxDO> bdcXmLsgxDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
            for (BdcSlXmLsgxDO bdcSlXmLsgxDO : bdcSlXmLsgxDOList) {
                //原项目id不为空赋值
                if (bdcSlXmLsgxDO != null && StringUtils.isNotBlank(bdcSlXmLsgxDO.getYxmid())) {
                    BdcXmLsgxDO bdcXmLsgxDO = new BdcXmLsgxDO();
                    acceptDozerMapper.map(bdcSlXmLsgxDO, bdcXmLsgxDO);
                    //不判定是否为外联  没值的走配置
                    if(bdcXmLsgxDO.getZxyql()==null){
                        bdcXmLsgxDO.setZxyql(bdcCshFwkgSlDO.getSfzxyql());
                    }
                    bdcXmLsgxDOList.add(bdcXmLsgxDO);
                }
            }
        }
        return bdcXmLsgxDOList;
    }

    /**
     * @param bdcSlXmLsgxDOList
     * @return yxmid
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 转化初始化需要的受理传来的参数中原项目id
     */
    public String changeBdcSlParameterYxmid(List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList) {
        String yxmid = "";
        if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
            for (BdcSlXmLsgxDO bdcSlXmLsgxDO : bdcSlXmLsgxDOList) {
                if (bdcSlXmLsgxDO != null && StringUtils.isNotBlank(bdcSlXmLsgxDO.getYxmid())  && !CommonConstantUtils.SF_S_DM.equals(bdcSlXmLsgxDO.getSfwlzs())) {
                    //返回非外联的项目历史关系中第一个非空的原项目id
                    yxmid = bdcSlXmLsgxDO.getYxmid();
                    break;
                }
            }
        }
        return yxmid;
    }

    /**
     * @param bdcSlXmDTO
     * @param cshFwkgMap
     * @return bdcCshFwKg
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 转化初始化需要的受理传来的参数中单个项目的服务开关信息
     */
    public BdcCshFwkgSlDO changeBdcSlParameterFwkg(BdcSlXmDTO bdcSlXmDTO,Map<String,BdcCshFwkgDO> cshFwkgMap) {
        BdcCshFwkgSlDO bdcCshFwkgSlDO;
        if (bdcSlXmDTO != null && bdcSlXmDTO.getBdcSlXm() != null && StringUtils.isNotBlank(bdcSlXmDTO.getBdcSlXm().getDjxl())) {
            String djxl=bdcSlXmDTO.getBdcSlXm().getDjxl();
            BdcCshFwkgDO bdcCshFwkgDO;
            //map中有直接获取
            if(cshFwkgMap.containsKey(djxl)){
                bdcCshFwkgDO=cshFwkgMap.get(djxl);
            }else{
                bdcCshFwkgDO = initFwKgService.queryBdcCshFwKgDO(djxl);
                if(bdcCshFwkgDO==null){
                    throw new MissingArgumentException("djxl:"+djxl+"未配置bdc_csh_fwkg");
                }else {
                    cshFwkgMap.put(djxl,bdcCshFwkgDO);
                }
            }
            bdcCshFwkgSlDO=new BdcCshFwkgSlDO();
            BeanUtils.copyProperties(bdcCshFwkgDO,bdcCshFwkgSlDO);
            acceptDozerMapper.map(bdcSlXmDTO.getBdcSlXm(), bdcCshFwkgSlDO);
            bdcCshFwkgSlDO.setSfzf(bdcSlXmDTO.getBdcSlXm().getSfzf());
        }else{
            throw new MissingArgumentException("djxl");
        }
        return bdcCshFwkgSlDO;
    }


    /**
     *获取djxx
     * @param initServiceQO
     * @param mapDjxx
     * @return
     */
    private  DjxxResponseDTO getLpbDjxx(InitServiceQO initServiceQO,Map<String,DjxxResponseDTO> mapDjxx){
        //地籍信息对象
        DjxxResponseDTO djxxResponseDTO=null;
        //正常不动产单元号调用权籍数据   非虚拟单元号
        if(CommonUtils.sfcxQj(initServiceQO) && !BdcdyhToolUtils.checkXnbdcdyh(initServiceQO.getBdcdyh()) && StringUtils.isNotBlank(initServiceQO.getBdcdyh())){
            String djh=initServiceQO.getBdcdyh().substring(0,19);
            if(mapDjxx.containsKey(djh)){
                djxxResponseDTO=mapDjxx.get(djh);
            }else{
                String qjqlrgxid =initServiceQO.getBdcSlXmDO() != null ?initServiceQO.getBdcSlXmDO().getQjqlrgxid():"";

                // 页面版本众多，为减去修改页面繁琐，直接后台处理 modified by zhuyong
                List<String> qlgldmList = new ArrayList<>();
                if(StringUtils.isNotBlank(initServiceQO.getQjgldm())) {
                    // 当前单元指定qjgldm，直接查询指定权籍库
                    qlgldmList.add(initServiceQO.getQjgldm());
                } else {
                    // 未指定qjgldm，获取系统所有qjgldm，去对应库查询
                    qlgldmList.addAll(bdcXmService.listQjgldm());
                }

                // 查询指定权籍库
                if(CollectionUtils.isNotEmpty(qlgldmList)) {
                    for(String gldm : qlgldmList) {
                        djxxResponseDTO = this.getDjxxResponseDTO(initServiceQO, qjqlrgxid, gldm);
                        if(null != djxxResponseDTO) {
                            break;
                        }
                    }
                }

                // 查询默认权籍库
                if(null == djxxResponseDTO) {
                    djxxResponseDTO = this.getDjxxResponseDTO(initServiceQO, qjqlrgxid, null);
                }

                if (null == djxxResponseDTO) {
                    throw new AppException("没有获取到对应的调查表权籍数据！" + initServiceQO.getBdcdyh());
                }

                mapDjxx.put(djh,djxxResponseDTO);
            }
        }else{
            //非正规的赋值空对象
            djxxResponseDTO=new DjxxResponseDTO();
        }
        initServiceQO.setDjxxResponseDTO(djxxResponseDTO);
        return djxxResponseDTO;
    }

    /**
     * 查询指定权籍库地籍信息
     * @param initServiceQO 初始化信息
     * @param qjqlrgxid
     * @param qjgldm 权籍管理代码
     * @return 地籍信息
     */
    private DjxxResponseDTO getDjxxResponseDTO(InitServiceQO initServiceQO, String qjqlrgxid, String qjgldm) {
        DjxxResponseDTO djxxResponseDTO = djxxFeignService.queryDjxx(initServiceQO.getBdcdyh(),qjqlrgxid, qjgldm);
        if (djxxResponseDTO == null) {
            LOGGER.info("获取H地籍信息，bdcdyh:{}，qlgldm: {}",initServiceQO.getBdcdyh(), qjgldm);
            djxxResponseDTO = djxxFeignService.queryHDjxxByBdcdyh(initServiceQO.getBdcdyh(), qjgldm);
        }
        return djxxResponseDTO;
    }
}
