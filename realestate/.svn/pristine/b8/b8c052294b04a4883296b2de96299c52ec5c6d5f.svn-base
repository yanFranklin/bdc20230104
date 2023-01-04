package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.core.service.BdcDjxlPzService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmLsgxService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.service.BdcAddGwcSjclCommonService;
import cn.gtmap.realestate.accept.service.BdcQllxService;
import cn.gtmap.realestate.accept.service.BdcWlzsService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.accept.DjxlPzCxQO;
import cn.gtmap.realestate.common.core.qo.accept.InitSlxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
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
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/2/19
 * @description 外联证书
 */
@Service
public class BdcWlzsServiceImpl implements BdcWlzsService {

    /**
     * 配置工作流定义ID,外联证书，证书存在抵押，自动将抵押带入，生成新的抵押，抵押单元号使用选择的单元号逻辑处理
     */
    @Value("#{'${wlzs.wlzsdytofwdy:}'.split(',')}")
    private List<String> wlzsdytofwdy;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 单元号更正登记流程
     */
    @Value("#{'${lcbs.dyhgz:}'.split(',')}")
    private List<String> dyhgz;

    /**
     * 生成抵押方式--外联时复制生成所有登记数据
     */
    public static final Integer WLZS_SCDYFS_FZ = 1;

    /**
     * 生成抵押方式--外联时只生成受理项目,创建后生成登记数据
     */
    public static final Integer WLZS_SCDYFS_SLXM = 2;

    @Autowired
    BdcSlXmService bdcSlXmService;
    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcPpFeignService bdcPpFeignService;
    @Autowired
    BdcWlzsService bdcWlzsService;

    @Autowired
    BdcDjxlPzService bdcDjxlPzService;
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;

    @Autowired
    BdcInitFeignService bdcInitFeignService;

    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    BdcAddGwcSjclCommonService bdcAddGwcSjclCommonService;
    @Autowired
    BdcQllxService bdcQllxService;
    @Autowired
    UserManagerUtils userManagerUtils;


    private static final Logger LOGGER = LoggerFactory.getLogger(BdcWlzsServiceImpl.class);


    @Override
    public void wlZs(BdcCshSlxmDTO bdcCshSlxmDTO, String jbxxid) {
        if(StringUtils.isNotBlank(bdcCshSlxmDTO.getGzldyid()) &&dyhgz.contains(bdcCshSlxmDTO.getGzldyid())) {
            //单元号更正
            dyhgzWlzs(bdcCshSlxmDTO,jbxxid);
            return;
        }
        String xmids = bdcCshSlxmDTO.getXmids();
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = bdcCshSlxmDTO.getBdcSlYwxxDTOList();
        List<BdcSlXmDO> bdcSlXmDOList;
        if (StringUtils.isNotBlank(xmids)) {
            bdcSlXmDOList = bdcSlXmService.listBdcSlXmByXmids(Arrays.asList(xmids.split(CommonConstantUtils.ZF_YW_DH)));
        }else {
            bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "");
        }
        if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
            throw new AppException("请先选择不动产单元");
        }
        List<BdcSlXmLsgxDO> allSlXmLsgxDOList =new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList) && CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            //验证外联证书是否已存在
            List<String> currentXmidList = bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
            List<String> yxXmidList = bdcSlYwxxDTOList.stream().map(BdcSlYwxxDTO::getXmid).collect(Collectors.toList());
            allSlXmLsgxDOList =bdcSlXmLsgxService.listSlXmLsgxPl(currentXmidList,null);
            if(CollectionUtils.isNotEmpty(allSlXmLsgxDOList)) {
                //验证外联证书是否已关联
                List<BdcSlXmLsgxDO> list = allSlXmLsgxDOList.stream().filter(lsgx -> yxXmidList.contains(lsgx.getYxmid())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(list)){
                    throw new AppException("外联证书已存在，请勿重复关联");
                }
            }
            LOGGER.info("添加外联证书，bdcSlXmDOList:{},bdcSlYwxxDTOList:{}", JSONObject.toJSONString(bdcSlXmDOList), JSONObject.toJSONString(bdcSlYwxxDTOList));
            //找出带抵押的产权证书,特殊处理
            List<BdcSlYwxxDTO> tdzYwxxList = bdcSlYwxxDTOList.stream().filter(slYwxxDTO -> CommonConstantUtils.SF_S_DM.equals(slYwxxDTO.getWlwithdyZs())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(tdzYwxxList)) {
                for (BdcSlYwxxDTO tdzYwxx : tdzYwxxList) {
                    //外联证书带抵押的特殊处理
                    wlZsWithdy(bdcSlXmDOList, tdzYwxx.getXmid(), tdzYwxx.getZxyql(),WLZS_SCDYFS_FZ);
                }
                bdcSlYwxxDTOList.removeAll(tdzYwxxList);
            }
            //外联证书存在抵押特殊处理
            if(CollectionUtils.isNotEmpty(bdcSlYwxxDTOList) &&StringUtils.isNotBlank(bdcCshSlxmDTO.getGzldyid()) &&wlzsdytofwdy.contains(bdcCshSlxmDTO.getGzldyid())){
                //找出产权证书,特殊处理
                List<BdcSlYwxxDTO> cqzYwxxList = bdcSlYwxxDTOList.stream().filter(slYwxxDTO -> !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, slYwxxDTO.getQllx())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(cqzYwxxList)) {
                    for (BdcSlYwxxDTO bdcSlYwxxDTO : cqzYwxxList) {
                        wlZsWithdy(bdcSlXmDOList, bdcSlYwxxDTO.getXmid(), bdcSlYwxxDTO.getZxyql(), WLZS_SCDYFS_SLXM);
                    }
                    bdcSlYwxxDTOList.removeAll(cqzYwxxList);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList) && CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            LOGGER.info("添加外联证书，bdcSlXmDOList:{},bdcSlYwxxDTOList:{}",JSONObject.toJSONString(bdcSlXmDOList),JSONObject.toJSONString(bdcSlYwxxDTOList));
            //需要插入的受理历史关系
            List<BdcSlXmLsgxDO> insertSlLsgx =new ArrayList<>();
            Map<String, List<BdcSlXmLsgxDO>> slxmgxMap =new HashMap<>();
            if(CollectionUtils.isNotEmpty(allSlXmLsgxDOList)) {
                slxmgxMap = allSlXmLsgxDOList.stream().collect(Collectors.groupingBy(BdcSlXmLsgxDO::getXmid));
            }
            //key为工作流定义ID_登记小类,value为对应的登记小类配置
            Map<String,BdcDjxlPzDO> djxlPzMap =new HashMap<>();
            for (int i = 0; i < bdcSlXmDOList.size(); i++) {
                BdcSlXmDO bdcSlXmDO = bdcSlXmDOList.get(i);
                Set<String> ybdcqzSet = new TreeSet<>();
                if (StringUtils.isNotBlank(bdcSlXmDO.getYbdcqz())) {
                    ybdcqzSet.add(bdcSlXmDO.getYbdcqz());
                }
                for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcSlYwxxDTOList) {
                    //当前项目非外联的历史关系数据
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOS =new ArrayList<>();
                    if(MapUtils.isNotEmpty(slxmgxMap) &&slxmgxMap.containsKey(bdcSlXmDO.getXmid())){
                        //获取当前受理项目对应历史关系
                        List<BdcSlXmLsgxDO> currentSlXmLsgxList =slxmgxMap.get(bdcSlXmDO.getXmid());
                        if(CollectionUtils.isNotEmpty(currentSlXmLsgxList)){

                            bdcSlXmLsgxDOS = currentSlXmLsgxList.stream().filter(lsgx -> lsgx.getSfwlzs() ==null||CommonConstantUtils.SF_F_DM.equals(lsgx.getSfwlzs())).collect(Collectors.toList());
                        }
                    }
                    if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOS) && StringUtils.isNotBlank(bdcSlXmLsgxDOS.get(0).getYxmid())) {
                        String yxmid = bdcSlXmLsgxDOS.get(0).getYxmid();
                        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
                        if (StringUtils.isNotBlank(yxmid)) {
                            bdcSlXmQO.setXmid(yxmid);
                        }
                        bdcSlXmQO.setJbxxid(jbxxid);
                        List<BdcSlXmDO> list = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
                        if (CollectionUtils.isNotEmpty(list)) {
                            continue;
                        }
                    }
                    // 判断当房产证和土地证在系统中已经匹配过，则外联其中一本证时，自动将两本证一同带入外联
                    List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(bdcSlYwxxDTO.getXmid());
                    if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                        String tdcqXmid = bdcFctdPpgxDOList.get(0).getTdcqxmid();
                        if (StringUtils.isNotBlank(tdcqXmid)) {
                            BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), tdcqXmid, "", "", "");
                            bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                            if (bdcSlYwxxDTO.getZxyql() != null) {
                                // 页面传值,以页面传值为准
                                bdcSlXmLsgxDO.setZxyql(bdcSlYwxxDTO.getZxyql());
                            }
                            insertSlLsgx.add(bdcSlXmLsgxDO);
                            // 获取关联的土地证号
                            BdcXmQO bdcXmQO = new BdcXmQO();
                            bdcXmQO.setXmid(tdcqXmid);
                            List<BdcXmDO> tdXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                            if (CollectionUtils.isNotEmpty(tdXmList)) {
                                if (null != tdXmList.get(0) && StringUtils.isNotBlank(tdXmList.get(0).getBdcqzh())) {
                                    ybdcqzSet.add(tdXmList.get(0).getBdcqzh());
                                }
                            }
                        }
                    }
                    BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), bdcSlYwxxDTO.getXmid(), "", "", "");
                    bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                    if (bdcSlYwxxDTO.getZxyql() != null) {
                        //页面传值,以页面传值为准
                        bdcSlXmLsgxDO.setZxyql(bdcSlYwxxDTO.getZxyql());
                    }
                    insertSlLsgx.add(bdcSlXmLsgxDO);
                    if (bdcSlYwxxDTO.getQllx() != null && StringUtils.isNotBlank(bdcSlYwxxDTO.getYbdcqz()) && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcSlYwxxDTO.getQllx())) {
                        ybdcqzSet.add(bdcSlYwxxDTO.getYbdcqz());
                    }
                }
                BdcDjxlPzDO bdcDjxlPzDO;
                String key=bdcCshSlxmDTO.getGzldyid()+CommonConstantUtils.ZF_YW_DH+bdcSlXmDO.getDjxl();
                if(djxlPzMap.containsKey(key)){
                    bdcDjxlPzDO =djxlPzMap.get(key);
                }else {
                    bdcDjxlPzDO = bdcDjxlPzService.queryBdcDjxlPzByGzldyidAndDjxl(bdcCshSlxmDTO.getGzldyid(), bdcSlXmDO.getDjxl());
                    djxlPzMap.put(key,bdcDjxlPzDO);
                }
                if (CollectionUtils.isNotEmpty(ybdcqzSet) && bdcDjxlPzDO.getSxh() != null &&bdcDjxlPzDO.getSxh().equals(1)) {
                    String ybdcqz = StringUtils.join(ybdcqzSet,CommonConstantUtils.ZF_YW_DH);
                    bdcSlXmDO.setYbdcqz(ybdcqz);
                    bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
                }
            }
            if(CollectionUtils.isNotEmpty(insertSlLsgx)){
                List<List> partList = ListUtils.subList(insertSlLsgx, 500);
                for (List data : partList) {
                    entityMapper.insertBatchSelective(data);
                }
            }


            //判断登记库是否存在数据
            List<BdcXmDO> bdcXmDOList =new ArrayList<>();
            if (StringUtils.isNotBlank(xmids)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmidList(Arrays.asList(xmids.split(CommonConstantUtils.ZF_YW_DH)));
                bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            } else {
                //根据工作流实例查询
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByJbxxid(jbxxid);
                if (Objects.nonNull(bdcSlJbxxDO)) {
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setGzlslid(bdcSlJbxxDO.getGzlslid());
                    bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                }
            }
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                //1.存在新增历史关系
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    if (CollectionUtils.isNotEmpty(bdcCshSlxmDTO.getBdcSlYwxxDTOList())) {
                        Set<String> ybdcqzSet = new TreeSet<>();
                        if (StringUtils.isNotBlank(bdcXmDO.getYcqzh())) {
                            ybdcqzSet.add(bdcXmDO.getYcqzh());
                        }
                        List<BdcXmLsgxDO> bdcXmLsgxDOList = new ArrayList<>(CollectionUtils.size(bdcCshSlxmDTO.getBdcSlYwxxDTOList()));
                        for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcCshSlxmDTO.getBdcSlYwxxDTOList()) {
                            //先判断是否已经存在外联项目历史关系
                            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                            bdcXmLsgxQO.setXmid(bdcXmDO.getXmid());
                            bdcXmLsgxQO.setYxmid(bdcSlYwxxDTO.getXmid());
                            bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
                            List<BdcXmLsgxDO> bdcXmLsgxList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                            if (CollectionUtils.isEmpty(bdcXmLsgxList)) {
                                BdcXmLsgxDO bdcXmLsgxDO = new BdcXmLsgxDO();
                                bdcXmLsgxDO.setWlxm(CommonConstantUtils.SF_S_DM);
                                bdcXmLsgxDO.setXmid(bdcXmDO.getXmid());
                                bdcXmLsgxDO.setYxmid(bdcSlYwxxDTO.getXmid());
                                if (bdcSlYwxxDTO.getZxyql() != null) {
                                    //页面传值,以页面传值为准
                                    bdcXmLsgxDO.setZxyql(bdcSlYwxxDTO.getZxyql());
                                }
                                bdcXmLsgxDOList.add(bdcXmLsgxDO);
                                if (bdcSlYwxxDTO.getQllx() != null && StringUtils.isNotBlank(bdcSlYwxxDTO.getYbdcqz()) && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcSlYwxxDTO.getQllx())) {
                                    ybdcqzSet.add(bdcSlYwxxDTO.getYbdcqz());
                                }
                            }
                        }
                        bdcXmFeignService.batchInsertBdcXmLsgx(bdcXmLsgxDOList);
                        //2.修改项目表产权证号
                        BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzService.queryBdcDjxlPzByGzldyidAndDjxl(bdcCshSlxmDTO.getGzldyid(), bdcXmDO.getDjxl());
                        if (CollectionUtils.isNotEmpty(ybdcqzSet) && bdcDjxlPzDO.getSxh() !=null &&bdcDjxlPzDO.getSxh().equals(1)) {
                            String ybdcqz = StringUtils.join(ybdcqzSet,CommonConstantUtils.ZF_YW_DH);
                            bdcXmDO.setYcqzh(ybdcqz);
                            bdcXmFeignService.updateBdcXm(bdcXmDO);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param bdcCshSlxmDTO 不动产受理项目前台
     * @param jbxxid           基本信息id
     * @param xmids            带入抵押信息的xmids
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 外联证书操作
     */
    @Override
    public void wlZsDyaqxx(BdcCshSlxmDTO bdcCshSlxmDTO, String jbxxid, String xmids) {
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = bdcCshSlxmDTO.getBdcSlYwxxDTOList();
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXmByJbxxid(jbxxid, "");
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList) && CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            bdcSlXmDOList.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
            //按照xmid排序，取到添加购物车时选择的那条数据
            BdcSlXmDO mainSlXm = bdcSlXmDOList.get(0);
            for (int i = 0; i < bdcSlXmDOList.size(); i++) {
                BdcSlXmDO bdcSlXmDO = bdcSlXmDOList.get(i);
                StringBuilder ybdcqzBuilder = new StringBuilder();
                if (StringUtils.isNotBlank(bdcSlXmDO.getYbdcqz())) {
                    ybdcqzBuilder.append(bdcSlXmDO.getYbdcqz()).append(CommonConstantUtils.ZF_YW_DH);
                }
                for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcSlYwxxDTOList) {
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), bdcSlYwxxDTO.getXmid(), null);
                    if (CollectionUtils.isEmpty(bdcSlXmLsgxDOList)) {
                        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), bdcSlYwxxDTO.getXmid(), "", "", "");
                        bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                        if (bdcSlYwxxDTO.getZxyql() != null) {
                            //页面传值,以页面传值为准
                            bdcSlXmLsgxDO.setZxyql(bdcSlYwxxDTO.getZxyql());
                        }
                        bdcSlXmLsgxService.insertBdcSlXmLsgx(bdcSlXmLsgxDO);
                        if (bdcSlYwxxDTO.getQllx() != null && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcSlYwxxDTO.getQllx())) {
                            ybdcqzBuilder.append(bdcSlYwxxDTO.getYbdcqz()).append(CommonConstantUtils.ZF_YW_DH);
                        }
                    }
                }
                BdcDjxlPzDO djxlPzDO = bdcDjxlPzService.queryBdcDjxlPzByGzldyidAndDjxl(bdcCshSlxmDTO.getGzldyid(), bdcSlXmDO.getDjxl());
                if (StringUtils.isNotBlank(ybdcqzBuilder) && djxlPzDO.getSxh().equals(1) ) {
                    String ybdcqz = ybdcqzBuilder.substring(0, ybdcqzBuilder.length() - 1);
                    bdcSlXmDO.setYbdcqz(ybdcqz);
                    bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
                }
            }
            List<String> xmidList = Arrays.asList(StringUtils.split(xmids, CommonConstantUtils.ZF_YW_DH));
            if (CollectionUtils.isNotEmpty(xmidList)) {
                for (String xmid : xmidList) {
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(mainSlXm.getXmid(), xmid, null);
                    if (CollectionUtils.isEmpty(bdcSlXmLsgxDOList)) {
                        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(mainSlXm.getXmid(), xmid, "", "", "");
                        bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                        bdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                        bdcSlXmLsgxService.insertBdcSlXmLsgx(bdcSlXmLsgxDO);
                    }
                }
            }
        }
    }



    @Override
    public BdcSlXmLsgxDO wlZsByWlxmid(String wlxmid, BdcSlXmDO bdcSlXmDO) {
        BdcSlXmLsgxDO bdcSlXmLsgxDO = null;
        if (StringUtils.isBlank(wlxmid) || bdcSlXmDO == null) {
            throw new MissingArgumentException("外联证书缺失参数");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(wlxmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("外联证书参数错误，查询不到项目,wlxmid="+wlxmid);
        }
        BdcXmDO bdcXmDO = bdcXmDOList.get(0);
        StringBuilder ybdcqzBuilder = new StringBuilder();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), bdcXmDO.getXmid(), null);
        if (CollectionUtils.isEmpty(bdcSlXmLsgxDOList)) {
            bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), bdcXmDO.getXmid(), "", "", "");
            bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
            //拼接原产权证号的时候，只带入非限制权利的bdcqzh作为ycqzh
            if (StringUtils.isNotBlank(bdcXmDO.getBdcqzh()) && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())) {
                if (StringUtils.isNotBlank(bdcSlXmDO.getYbdcqz())) {
                    ybdcqzBuilder.append(bdcSlXmDO.getYbdcqz()).append(CommonConstantUtils.ZF_YW_DH).append(bdcXmDO.getBdcqzh());
                } else {
                    ybdcqzBuilder.append(bdcXmDO.getBdcqzh());
                }
                bdcSlXmDO.setYbdcqz(ybdcqzBuilder.toString());
                //更新受理项目表的原不动产权证字段
                bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
            }

        }
        return bdcSlXmLsgxDO;

    }

    /**
     * @param fcxmid    房产xmid
     * @param fcbdcqz
     * @param bdcSlXmDO 不动产受理项目  @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房产ID外联证书（生成历史关系数据）土地证
     */
    @Override
    public BdcSlXmLsgxDO wltdz(String fcxmid, String fcbdcqz, BdcSlXmDO bdcSlXmDO) {
        BdcSlXmLsgxDO bdcSlXmLsgxDO = wltdzNoInsert(fcxmid, fcbdcqz, bdcSlXmDO);
        if (bdcSlXmLsgxDO != null) {
            entityMapper.insertSelective(bdcSlXmLsgxDO);
        }
        return bdcSlXmLsgxDO;
    }

    /**
     * @param fcxmid
     * @param fcbdcqz
     * @param bdcSlXmDO
     * @return cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据房产ID外联证书（生成历史关系数据）土地证(不保存直接返回)
     */
    @Override
    public BdcSlXmLsgxDO wltdzNoInsert(String fcxmid, String fcbdcqz, BdcSlXmDO bdcSlXmDO) {
        BdcSlXmLsgxDO bdcSlXmLsgxDO = null;
        //判断所选数据是否为房产证,是则自动将土地证作为外联证书
        if (StringUtils.isNotBlank(fcxmid) && ((StringUtils.isNotBlank(fcbdcqz) && !fcbdcqz.contains(CommonConstantUtils.BDC_BDCQZH_BS)) || StringUtils.isBlank(fcbdcqz))) {
            List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(fcxmid);
            if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                bdcSlXmLsgxDO = bdcWlzsService.wlZsByWlxmid(bdcFctdPpgxDOList.get(0).getTdcqxmid(), bdcSlXmDO);
            }
        }
        return bdcSlXmLsgxDO;
    }

    /**
     * @param bdcSlXmDOList 当前项目集合
     * @param wlxmid 外联的证书项目ID
     * @param zxyql 外联证书是否注销
     * @param scdyfs 生成抵押方式 1：外联即复制生成所有登记数据 2：只生成受理项目，建立新抵押的历史关系
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 外联带抵押的产权证书逻辑处理
     *  1.当前项目与外联证书建立外联历史关系
     *  2.根据土地抵押新生成房屋抵押
     *  3.建立历史关系
     */
    public void wlZsWithdy(List<BdcSlXmDO> bdcSlXmDOList,String wlxmid,Integer zxyql,Integer scdyfs){
        WltdzWithdyResultDTO resultDTO=new WltdzWithdyResultDTO();
        if(StringUtils.isNotBlank(wlxmid) &&CollectionUtils.isNotEmpty(bdcSlXmDOList)){
            LOGGER.info("外联证书，证书项目ID:{}证书存在抵押,将抵押与新项目产权关联逻辑处理,新产权项目:{}", wlxmid,bdcSlXmDOList);
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
            //查询并验证外联项目
            BdcXmDO wlXm =queryWlxmWithDy(wlxmid);
            //查询外联证书的抵押信息
            BdcXmQO dyXmQO =new BdcXmQO();
            dyXmQO.setBdcdyh(wlXm.getBdcdyh());
            dyXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
            dyXmQO.setQllxs(Arrays.asList(CommonConstantUtils.QLLX_DYAQ_DM));
            List<BdcXmDO> dyXmList =bdcXmFeignService.listBdcXm(dyXmQO);
            LOGGER.info("外联带抵押证书，证书项目ID:{}查询抵押项目:{}", wlxmid,dyXmList);
            //建立外联历史关系
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                //1.当前项目与土地证建立外联历史关系,并更新原产权证号
                BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), wlxmid, "", "", "");
                bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                if (zxyql != null) {
                    //页面传值,以页面传值为准
                    bdcSlXmLsgxDO.setZxyql(zxyql);
                }
                bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                StringBuilder ybdcqzBuilder = new StringBuilder();
                if(StringUtils.isNotBlank(wlXm.getBdcqzh())&& !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, wlXm.getQllx())) {
                    if (StringUtils.isNotBlank(bdcSlXmDO.getYbdcqz())) {
                        ybdcqzBuilder.append(bdcSlXmDO.getYbdcqz()).append(CommonConstantUtils.ZF_YW_DH).append(wlXm.getBdcqzh());
                    } else {
                        ybdcqzBuilder.append(wlXm.getBdcqzh());
                    }
                    bdcSlXmDO.setYbdcqz(ybdcqzBuilder.toString());
                }
            }
            //生成抵押项目
            if(WLZS_SCDYFS_FZ.equals(scdyfs)) {
                //直接复制原抵押,替换单元号,生成登记数据
                resultDTO =fzDyYwxx(dyXmList,bdcSlXmDOList,wlxmid,zxyql);
            }else if(WLZS_SCDYFS_SLXM.equals(scdyfs)){
                //根据原抵押数量生成对应抵押受理项目
                resultDTO =scDySlXmDTO(dyXmList,bdcSlXmDOList,wlxmid,zxyql);
            }
            if(CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                if (CollectionUtils.isNotEmpty(resultDTO.getBdcSlXmLsgxDOList())) {
                    resultDTO.getBdcSlXmLsgxDOList().addAll(bdcSlXmLsgxDOList);
                } else {
                    resultDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);
                }
            }
            resultDTO.setBdcSlXmDOList(bdcSlXmDOList);
            //数据入库
            saveWlZsWithdyData(resultDTO);
        }

    }

    /**
     * @param dyXmList 抵押项目集合
     * @param bdcSlXmDOList 受理项目集合
     * @param wlxmid 外联证书项目ID
     * @param zxyql 是否注销原权利
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 复制抵押业务信息
     * 根据当前项目数量，生成对应的房屋抵押，房屋抵押信息复制原证书抵押
     * 新生成的房屋抵押关联当前项目
     * 当前项目与原有抵押建立外联历史关系
     * 不生成新的抵押证明，新抵押与原土地抵押证明建立关系
     */
    private WltdzWithdyResultDTO fzDyYwxx(List<BdcXmDO> dyXmList,List<BdcSlXmDO> bdcSlXmDOList,String wlxmid,Integer zxyql){
        WltdzWithdyResultDTO resultDTO=new WltdzWithdyResultDTO();
        List<BdcYwxxDTO> bdcYwxxDTOList = new ArrayList<>();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        //抵押项目ID与业务信息关系
        Map<String, BdcYwxxDTO> dyxmYwxxMap = new HashMap<>();
        //查询业务信息，建立关系
        if (CollectionUtils.isNotEmpty(dyXmList)) {
            for (BdcXmDO dyXm : dyXmList) {
                try {
                    //查询抵押业务信息
                    BdcYwxxDTO bdcYwxxDTO = bdcInitFeignService.queryYwxx(dyXm.getXmid());
                    if (bdcYwxxDTO == null || bdcYwxxDTO.getBdcXm() == null || StringUtils.isBlank(bdcYwxxDTO.getBdcXm().getXmid())) {
                        throw new AppException("获取抵押业务信息为空,抵押项目ID" + dyXm.getXmid());
                    }
                    dyxmYwxxMap.put(dyXm.getXmid(), bdcYwxxDTO);
                } catch (Exception e) {
                    LOGGER.info("查询抵押业务信息异常:{}", dyXm.getXmid());
                    throw new AppException("查询抵押业务信息异常");
                }
            }
        }
        int xmnum = 0;
        for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
            xmnum++;
            if (CollectionUtils.isNotEmpty(dyXmList)) {
                for (BdcXmDO dyXm : dyXmList) {

                    //2.根据当前项目数量，生成对应的房屋抵押，房屋抵押信息复制原证书抵押
                    if (checkScFwdy(bdcSlXmDO.getBdcdyh(), dyXm)) {
                        String xdyxmid = "FZDY_" + UUIDGenerator.generate16();
                        if (dyxmYwxxMap.containsKey(dyXm.getXmid())) {
                            //多个房屋抵押项目,抵押证明一条
                            bdcYwxxDTOList.add(copyDyYwxx(dyxmYwxxMap.get(dyXm.getXmid()), xdyxmid, bdcSlXmDO.getBdcdyh()));
                            //3.新生成的房屋抵押关联当前项目
                            BdcSlXmLsgxDO xdySlXmlsgx = new BdcSlXmLsgxDO(xdyxmid, bdcSlXmDO.getXmid(), "", "", "");
                            xdySlXmlsgx.setZxyql(CommonConstantUtils.SF_F_DM);
                            bdcSlXmLsgxDOList.add(xdySlXmlsgx);
                            //4.当前项目与原有抵押建立外联历史关系
                            BdcSlXmLsgxDO ydySlXmlsgx = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), dyXm.getXmid(), "", "", "");
                            ydySlXmlsgx.setSfwlzs(CommonConstantUtils.SF_S_DM);
                            if (zxyql != null) {
                                //页面传值,以页面传值为准
                                ydySlXmlsgx.setZxyql(zxyql);
                            } else {
                                ydySlXmlsgx.setZxyql(CommonConstantUtils.SF_S_DM);
                            }
                            bdcSlXmLsgxDOList.add(ydySlXmlsgx);


                        }
                    } else {
                        LOGGER.info("外联带抵押证书，证书项目ID:{}抵押项目:{}已转过房屋抵押不再重复生成", wlxmid, dyXm.getXmid());
                    }
                }
            }
        }
        resultDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);
        resultDTO.setBdcYwxxDTOList(bdcYwxxDTOList);
        return resultDTO;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  走带抵押逻辑，根据外联证书抵押项目，生成抵押受理项目和受理项目历史关系
     * 根据当前受理项目数量，生成对应的抵押受理项目和受理项目历史关系，抵押受理信息读取受理项目信息
     */
    private WltdzWithdyResultDTO scDySlXmDTO(List<BdcXmDO> dyXmList,List<BdcSlXmDO> bdcSlXmDOList,String wlxmid,Integer zxyql){
        LOGGER.info("外联带抵押证书，证书项目ID:{}生成抵押受理受理项目", wlxmid);
        WltdzWithdyResultDTO resultDTO=new WltdzWithdyResultDTO();
        //新增的抵押受理项目
        List<BdcSlXmDO> dySlxmList = new ArrayList<>();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(dyXmList)){
            for(BdcXmDO dyXm:dyXmList){
                //循环生成对应受理项目的抵押项目
                for(BdcSlXmDO bdcSlXmDO: bdcSlXmDOList){
                    InitSlxxQO initSlxxQO =new InitSlxxQO();
                    BdcSlYwxxDTO bdcSlYwxxDTO =new BdcSlYwxxDTO();
                    BeanUtils.copyProperties(bdcSlXmDO, bdcSlYwxxDTO);
                    bdcSlYwxxDTO.setQllx(CommonConstantUtils.QLLX_DYAQ_DM);
                    bdcSlYwxxDTO.setDjxl(dyXm.getDjxl());
                    initSlxxQO.setBdcSlYwxxDTO(bdcSlYwxxDTO);
                    initSlxxQO.setJbxxid(bdcSlXmDO.getJbxxid());
                    initSlxxQO.setCzrid(bdcSlXmDO.getCzrid());
                    initSlxxQO.setYxmid(bdcSlXmDO.getXmid());
                    initSlxxQO.setXmidDyzmhMap(new HashMap<>());
                    BdcSlXmDTO bdcSlXmDTO =bdcAddGwcSjclCommonService.initBdcSlXmDTOForWithdy(initSlxxQO,dyXm.getXmid());
                    dySlxmList.add(bdcSlXmDTO.getBdcSlXm());
                    if(CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlXmLsgxList())){
                        for(BdcSlXmLsgxDO bdcSlXmLsgxDO:bdcSlXmDTO.getBdcSlXmLsgxList()){
                            if(CommonConstantUtils.SF_S_DM.equals(bdcSlXmLsgxDO.getSfwlzs())){
                                bdcSlXmLsgxDO.setZxyql(zxyql);
                            }
                        }
                    }
                    bdcSlXmLsgxDOList.addAll(bdcSlXmDTO.getBdcSlXmLsgxList());
                }
            }
        }
        resultDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);
        resultDTO.setDySlXmList(dySlxmList);
        return  resultDTO;
    }



    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  外联带抵押的产权证书,根据外联项目ID查询外联项目
     */
    private BdcXmDO queryWlxmWithDy(String wlxmid){
        BdcXmQO bdcXmQO =new BdcXmQO();
        bdcXmQO.setXmid(wlxmid);
        List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList) ||StringUtils.isBlank(bdcXmDOList.get(0).getBdcdyh())) {
            LOGGER.error("外联带抵押证书，证书项目ID:{}未查询到完整的项目信息：{}", wlxmid,bdcXmDOList);
            throw new AppException("证书项目未查询到项目信息");
        }
        BdcXmDO wlXm =bdcXmDOList.get(0);
        if(ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, wlXm.getQllx())){
            LOGGER.error("外联带抵押证书，证书项目ID:{}非产权项目：{}", wlxmid,bdcXmDOList);
            throw new AppException("当前外联证书非产权项目");
        }
        return wlXm;
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  验证是否需要生成房屋抵押
      */
    private boolean checkScFwdy(String fwdyh,BdcXmDO dyXm){
        //判断是否已新生成房屋抵押
        BdcXmQO fwdyXmQO =new BdcXmQO();
        fwdyXmQO.setBdcdyh(fwdyh);
        fwdyXmQO.setBdcqzh(dyXm.getBdcqzh());
        fwdyXmQO.setGzlslid(dyXm.getGzlslid());
        fwdyXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
        List<BdcXmDO> fwdyXmList =bdcXmFeignService.listBdcXm(fwdyXmQO);
        if(CollectionUtils.isNotEmpty(fwdyXmList)){
            LOGGER.info("外联证书的抵押已转过房屋抵押，不再重复生成：{}",fwdyXmList);
            return false;
        }
        return true;
    }

    /**
      * @param bdcYwxxDTO 被复制的业务信息
      * @param newxmid 新业务项目ID
      * @param bdcdyh 新业务单元号
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  复制抵押业务信息生成房屋抵押业务信息，抵押证明信息共用
      */
    private BdcYwxxDTO copyDyYwxx(BdcYwxxDTO bdcYwxxDTO,String newxmid,String bdcdyh){
        BdcYwxxDTO newYwxx =new BdcYwxxDTO();
        if(bdcYwxxDTO != null) {
            if(bdcYwxxDTO.getBdcXm() != null) {
                BdcXmDO bdcXmDO =new BdcXmDO();
                BeanUtils.copyProperties(bdcYwxxDTO.getBdcXm(), bdcXmDO);
                bdcXmDO.setBdclx(CommonConstantUtils.DYBDCLX_FDYT);
                bdcXmDO.setBdcdyh(bdcdyh);
                bdcXmDO.setXmid(newxmid);
                bdcXmDO.setGzlslid(newxmid);
                //xmly赋值3,xmly=1要求必须有大云流程数据
                bdcXmDO.setXmly(CommonConstantUtils.XMLY_QT_DM);
                newYwxx.setBdcXm(bdcXmDO);
            }
            if(bdcYwxxDTO.getBdcXmFb() !=null) {
                BdcXmFbDO bdcXmFbDO =new BdcXmFbDO();
                BeanUtils.copyProperties(bdcYwxxDTO.getBdcXmFb(), bdcXmFbDO);
                bdcXmFbDO.setXmid(newxmid);
                bdcXmFbDO.setGzlslid(newxmid);
                newYwxx.setBdcXmFb(bdcXmFbDO);
            }
            if(bdcYwxxDTO.getBdcQl() instanceof BdcDyaqDO) {
                BdcDyaqDO bdcDyaqDO =new BdcDyaqDO();
                BeanUtils.copyProperties(bdcYwxxDTO.getBdcQl(), bdcDyaqDO);
                bdcDyaqDO.setDybdclx(CommonConstantUtils.DYBDCLX_FDYT);
                bdcDyaqDO.setBdcdyh(bdcdyh);
                bdcDyaqDO.setXmid(newxmid);
                bdcDyaqDO.setQlid(UUIDGenerator.generate16());
                newYwxx.setBdcQl(bdcDyaqDO);
            }
            //权利人
            if(CollectionUtils.isNotEmpty(bdcYwxxDTO.getBdcQlrList())){
                List<BdcQlrDO> bdcQlrDOList =new ArrayList<>();
                for(BdcQlrDO qlrDO:bdcYwxxDTO.getBdcQlrList()){
                    BdcQlrDO bdcQlrDO =new BdcQlrDO();
                    BeanUtils.copyProperties(qlrDO, bdcQlrDO);
                    bdcQlrDO.setXmid(newxmid);
                    bdcQlrDO.setQlrid(UUIDGenerator.generate16());
                    bdcQlrDOList.add(bdcQlrDO);
                }
                newYwxx.setBdcQlrList(bdcQlrDOList);
            }
            //项目证书关系
            if(CollectionUtils.isNotEmpty(bdcYwxxDTO.getBdcXmZsGxList())){
                List<BdcXmZsGxDO> bdcXmZsGxDOList =new ArrayList<>();
                for(BdcXmZsGxDO xmZsGxDO:bdcYwxxDTO.getBdcXmZsGxList()){
                    BdcXmZsGxDO bdcXmZsGxDO =new BdcXmZsGxDO();
                    BeanUtils.copyProperties(xmZsGxDO, bdcXmZsGxDO);
                    bdcXmZsGxDO.setXmid(newxmid);
                    bdcXmZsGxDO.setGxid(UUIDGenerator.generate16());
                    bdcXmZsGxDOList.add(bdcXmZsGxDO);
                }
                newYwxx.setBdcXmZsGxList(bdcXmZsGxDOList);
            }
        }
        return newYwxx;

    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  外联带抵押证书数据入库保存
      */
    private void saveWlZsWithdyData(WltdzWithdyResultDTO wltdzWithdyResultDTO){
        LOGGER.info("外联带抵押证书数据入库：{}",wltdzWithdyResultDTO);
        if(CollectionUtils.isNotEmpty(wltdzWithdyResultDTO.getBdcYwxxDTOList())){
            List<BdcYwxxDTO> bdcYwxxDTOList =bdcInitFeignService.insertYwxxList(wltdzWithdyResultDTO.getBdcYwxxDTOList());
            if(CollectionUtils.isEmpty(bdcYwxxDTOList)){
                LOGGER.error("外联带抵押数据入库异常，业务信息插入异常");
                throw new AppException("外联带抵押证书异常,数据插入异常");
            }
            List<String> bdcdyhList =new ArrayList<>();
            for(BdcYwxxDTO bdcYwxxDTO:bdcYwxxDTOList){
                if(bdcYwxxDTO.getBdcXm() != null &&StringUtils.isNotBlank(bdcYwxxDTO.getBdcXm().getBdcdyh())) {
                    bdcdyhList.add(bdcYwxxDTO.getBdcXm().getBdcdyh());
                }
            }
            //回写状态到权籍
            bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyhList,bdcYwxxDTOList.get(0).getBdcXmFb() != null?bdcYwxxDTOList.get(0).getBdcXmFb().getQjgldm():"");
        }

        if (CollectionUtils.isNotEmpty(wltdzWithdyResultDTO.getBdcSlXmLsgxDOList())) {
            entityMapper.insertBatchSelective(wltdzWithdyResultDTO.getBdcSlXmLsgxDOList());
        }
        if (CollectionUtils.isNotEmpty(wltdzWithdyResultDTO.getDySlXmList())) {
            entityMapper.insertBatchSelective(wltdzWithdyResultDTO.getDySlXmList());
        }
        if (CollectionUtils.isNotEmpty(wltdzWithdyResultDTO.getBdcSlXmDOList())) {
            for (BdcSlXmDO bdcSlXmDO : wltdzWithdyResultDTO.getBdcSlXmDOList()) {
                bdcSlXmService.updateBdcSlXm(bdcSlXmDO);
            }
        }

    }

    /**
     * @param bdcCshSlxmDTO
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 单元号更正流程外联证书处理
     */
    private void dyhgzWlzs(BdcCshSlxmDTO bdcCshSlxmDTO, String jbxxid){
        LOGGER.info("{}单元号更正登记,外联证书处理:{}",jbxxid,bdcCshSlxmDTO);
        List<BdcSlXmDO> slXmDOList =bdcSlXmService.listBdcSlXmByJbxxid(jbxxid,"");
        if(CollectionUtils.isEmpty(slXmDOList) ||slXmDOList.size() !=1){
            throw new AppException(ErrorCode.YW_JYSB,"请选择一条正确的不动产单元号");
        }
        if(CollectionUtils.isNotEmpty(bdcCshSlxmDTO.getBdcSlYwxxDTOList())){
            //新受理项目
            List<BdcSlXmDO> bdcSlXmDOList =new ArrayList<>();
            //新历史关系
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =new ArrayList<>();
            //存储单元号更正前和更正后的关系
            List<BdcXnbdcdyhGxDO> listXndyhgx=new ArrayList<>();
            //外联项目与新项目的对照关系
            Map<String,String> xmidDz =new HashMap<>();
            for(BdcSlYwxxDTO bdcSlYwxxDTO:bdcCshSlxmDTO.getBdcSlYwxxDTOList()){
                if(bdcSlYwxxDTO.getQllx() != null){
                    //获取djxl
                    DjxlPzCxQO djxlPzCxQO =new DjxlPzCxQO();
                    djxlPzCxQO.setGzldyid(bdcCshSlxmDTO.getGzldyid());
                    djxlPzCxQO.setBdcdyh(bdcSlYwxxDTO.getBdcdyh());
                    djxlPzCxQO.setYqllx(bdcSlYwxxDTO.getQllx());
                    djxlPzCxQO.setYxmid(bdcSlYwxxDTO.getXmid());
                    djxlPzCxQO.setDyhcxlx(bdcSlYwxxDTO.getDyhcxlx());
                    List<BdcDjxlPzDO> bdcDjxlPzDOList =bdcDjxlPzService.queryBdcDjxlPz(djxlPzCxQO);
                    for(BdcDjxlPzDO bdcDjxlPzDO:bdcDjxlPzDOList){
                        //生成受理项目
                        bdcSlYwxxDTO.setDjxl(bdcDjxlPzDO.getDjxl());
                        BdcSlXmDO bdcSlXmDO = bdcAddGwcSjclCommonService.changeYwxxDtoIntoBdcSlXm(bdcSlYwxxDTO, bdcCshSlxmDTO.getCzrid(), jbxxid);
                        bdcSlXmDOList.add(bdcSlXmDO);
                        //单元号替换为正确单元号
                        bdcSlXmDO.setBdcdyh(slXmDOList.get(0).getBdcdyh());
                        //记录替换前和替换后单元号关系
                        UserDto userDto =null;
                        if(StringUtils.isNotBlank(bdcCshSlxmDTO.getCzrid())) {
                           userDto = userManagerUtils.getUserByUserid(bdcCshSlxmDTO.getCzrid());
                        }
                        BdcXnbdcdyhGxDO xnbdcdyhGxDO = new BdcXnbdcdyhGxDO();
                        xnbdcdyhGxDO.setBdcdyh(bdcSlXmDO.getBdcdyh());
                        xnbdcdyhGxDO.setGxid(UUIDGenerator.generate16());
                        xnbdcdyhGxDO.setXnbdcdyh(bdcSlYwxxDTO.getBdcdyh());
                        xnbdcdyhGxDO.setXnbdcdyhxmid(bdcSlYwxxDTO.getXmid());
                        xnbdcdyhGxDO.setCzr(userDto != null?userDto.getAlias():"");
                        xnbdcdyhGxDO.setCzsj(new Date());
                        xnbdcdyhGxDO.setGxlb(CommonConstantUtils.GXLB_DYHGZ_LC);
                        listXndyhgx.add(xnbdcdyhGxDO);
                        //数据来源调整
                        bdcSlXmDO.setQlrsjly(Constants.QLRSJLY_YTQL_QLR);
                        bdcSlXmDO.setYwrsjly(Constants.QLRSJLY_YTQL_QLR);
                        bdcSlXmDO.setQlsjly(CommonConstantUtils.QLSJLY_YTQL);
                        //建立替换前项目与替换后项目的历史关系
                        BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), bdcSlYwxxDTO.getXmid(), "", "", "");
                        bdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                        bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                        xmidDz.put(bdcSlYwxxDTO.getXmid(),bdcSlXmDO.getXmid());

                    }
                }
            }
            //外联证书之间如果存在历史关系,新的项目也需要建立对应的关系
            List<String> yxmidList =bdcCshSlxmDTO.getBdcSlYwxxDTOList().stream().map(BdcSlYwxxDTO::getXmid).collect(Collectors.toList());
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOS =bdcSlXmLsgxService.listSlXmLsgxPl(yxmidList,null);
            if(CollectionUtils.isNotEmpty(bdcSlXmLsgxDOS)){
                for(BdcSlXmLsgxDO bdcSlXmLsgxDO:bdcSlXmLsgxDOS){
                    //根据对照关系生成新的历史关系
                    bdcSlXmLsgxDO.setXmid(xmidDz.get(bdcSlXmLsgxDO.getXmid()));
                    if(StringUtils.isNotBlank(bdcSlXmLsgxDO.getYxmid()) &&xmidDz.containsKey(bdcSlXmLsgxDO.getYxmid())){
                        bdcSlXmLsgxDO.setGxid(UUIDGenerator.generate16());
                        bdcSlXmLsgxDO.setYxmid(xmidDz.get(bdcSlXmLsgxDO.getYxmid()));
                        bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                    }
                }
            }
            //数据入库
            bdcPpFeignService.insertXndyhGx(listXndyhgx);
            BdcSlxxInitDTO bdcSlxxInitDTO =new BdcSlxxInitDTO();
            bdcSlxxInitDTO.setBdcSlXmDOList(bdcSlXmDOList);
            bdcSlxxInitDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);
            List<Object> deleteList = new ArrayList<>();
            deleteList.addAll(slXmDOList);
            bdcSlxxInitDTO.setDeleteList(deleteList);
            try {
                bdcSlXmService.saveBdcSlxx(Arrays.asList(bdcSlxxInitDTO), Constants.FUN_INSERT);
            }catch (IllegalAccessException e) {
                LOGGER.error("保存受理信息失败", e);

            }
        }

    }
}
