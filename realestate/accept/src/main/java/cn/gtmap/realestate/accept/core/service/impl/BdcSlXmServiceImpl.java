package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.workflow.clients.define.v1.WorkDayClient;
import cn.gtmap.gtc.workflow.domain.define.Work;
import cn.gtmap.gtc.workflow.domain.define.WorkDay;
import cn.gtmap.realestate.accept.config.AcceptBeanFactory;
import cn.gtmap.realestate.accept.core.mapper.BdcSlXmMapper;
import cn.gtmap.realestate.accept.core.mapper.BdcSlYwxxMapper;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.*;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yzt.WorkDayVO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcGwcDeleteQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcGzlwShQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.register.BdcBdcdyQO;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCshXtPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.mybatis.utils.AnnotationsUtils;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlXmVO;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理项目数据服务
 */
@Service
public class BdcSlXmServiceImpl implements BdcSlXmService, BdcSlService,BdcCommonSlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlXmServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcSlSjclService bdcSlSjclService;
    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;
    @Autowired
    private BdcSlYwxxMapper bdcSlYwxxMapper;
    @Autowired
    private Repo repo;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private EntityService entityService;
    @Autowired
    private BdcSfService bdcSfService;
    @Autowired
    private BdcSlXmMapper bdcSlXmMapper;
    @Autowired
    private BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    private BdcSlSqrService bdcSlSqrService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    AcceptBeanFactory acceptBeanFactory;
    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;
    @Autowired
    BdcQllxService bdcQllxService;
    @Autowired
    BdcDjxlPzService bdcDjxlPzService;

    @Autowired
    BdcSlDyaqService bdcSlDyaqService;
    @Autowired
    BdcSlQjdcService bdcSlQjdcService;
    @Autowired
    BdcSlCdxxService bdcSlCdxxService;
    @Autowired
    BdcSlXzxxService bdcSlXzxxService;
    @Autowired
    BdcSlYwlzService bdcSlYwlzService;
    @Autowired
    BdcSlShxxService bdcSlShxxService;

    @Autowired
    WorkDayClient workDayClient;

    @Autowired
    BdcGzlwFeignService bdcGzlwFeignService;
    @Autowired
    BdcSlBysldjService bdcSlBysldjService;

    @Autowired
    BdcSjclService bdcSjclService;

    /**
     * 判断是否过滤重复的不动产单元号
     */
    @Value("${selectbdcdy.repeat: true}")
    private String repeat;

    @Value("#{'${zyhbba.gzldyid:}'.split(',')}")
    private List<String> zyhbbaGzldyidList;

    /**
     * 抵押查封不换证
     */
    @Value("#{'${accept.lcbs.withdycfbhz:}'.split(',')}")
    private List<String> withdycfbhz;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  不发证登记小类
     */
    @Value("${bfzdjxl:}")
    private String bfzdjxl;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 创建需要验证例外审核,审核均通过才能创建
     */
    @Value("${cjyz.lwsh:false}")
    private Boolean cjyzlwsh;

    /**
     * 忽略比较交易信息和受理申请人的流程
     */
    @Value("${cshtsyz.hlbjjyxxlc:}")
    private String hlbjjyxxlc;
    /**
     * @param xmid 项目ID
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID获取不动产受理项目
     */
    @Override
    public BdcSlXmDO queryBdcSlXmByXmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcSlXmDO.class, xmid);
    }

    /**
     * @param jbxxid 基本信息ID
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取不动产受理项目
     */
    @Override
    public List<BdcSlXmDO> listBdcSlXmByJbxxid(String jbxxid, String orderBy) {
        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(jbxxid)) {
            Example example = new Example(BdcSlXmDO.class);
            example.createCriteria().andEqualTo("jbxxid", jbxxid);
            if (StringUtils.isNotBlank(orderBy)) {
                example.setOrderByClause(orderBy);
            }
            bdcSlXmDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
            bdcSlXmDOList = Collections.emptyList();
        }
        return bdcSlXmDOList;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理项目
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据工作流实例ID获取所有不动产受理项目
     */
    @Override
    public List<BdcSlXmDO> listBdcSlXmByGzlslid(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("缺失工作流实例ID");
        }
        BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
        if(bdcSlJbxxDO != null){
            return this.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid(),null);
        }else{
            return Collections.emptyList();
        }
    }

    @Override
    public void updateQszt(String gzlslId) throws Exception{
        if(StringUtils.isNotBlank(gzlslId)) {
            List<BdcSlXmDO> bdcSlXmDOList = this.listBdcSlXmByGzlslid(gzlslId);
            if(CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                List<String> yxmidList = new ArrayList<>(CollectionUtils.size(bdcSlXmDOList));
                for(BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(),"",CommonConstantUtils.SF_F_DM);
                    if(CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                        String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                        yxmidList.add(yxmid);
                    }
                }
                if(CollectionUtils.isNotEmpty(yxmidList)) {
                    //更新项目
                    if(CollectionUtils.size(yxmidList) > 500) {
                        List<List> partXmidList = ListUtils.subList(yxmidList,500);
                        for(List list : partXmidList) {
                            updateQszt(list);
                        }
                    } else {
                        updateQszt(yxmidList);
                    }

                }
            }
        }
    }

    private void updateQszt(List list) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("qszt", CommonConstantUtils.QSZT_HISTORY);
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        bdcDjxxUpdateQO.setJsonStr(JSON.toJSONString(obj));
        Map whereMap = new HashMap<>();
        whereMap.put("xmids", list);
        bdcDjxxUpdateQO.setWhereMap(whereMap);
        bdcXmFeignService.updateBatchBdcXm(bdcDjxxUpdateQO);
        //更新权利信息
        for(Object o: list) {
            String xmid = (String) o;
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
            if(Objects.nonNull(bdcQl)) {
                JSONObject qlObj = new JSONObject();
                qlObj.put("qszt",CommonConstantUtils.QSZT_HISTORY);
                BdcDjxxUpdateQO bdcQlxxUpdateQO = new BdcDjxxUpdateQO();
                bdcQlxxUpdateQO.setJsonStr(JSON.toJSONString(qlObj));
                Map qlxxWhereMap = new HashMap<>();
                qlxxWhereMap.put("xmids", Collections.singletonList(xmid));
                bdcQlxxUpdateQO.setClassName(bdcQl.getClass().getName());
                bdcQlxxUpdateQO.setWhereMap(qlxxWhereMap);
                bdcQllxFeignService.updateBatchBdcQl(bdcQlxxUpdateQO);
            }
        }
    }

    @Override
    public List<BdcSlXmDO> listBdcSlXmByXmids(List<String> xmidList){
        if(CollectionUtils.isNotEmpty(xmidList)){
            return bdcSlXmMapper.listBdcSlXmByXmids(xmidList);
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcSlXmDO 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理项目
     */
    @Override
    public BdcSlXmDO insertBdcSlXm(BdcSlXmDO bdcSlXmDO) {
        if (bdcSlXmDO != null) {
            if (StringUtils.isBlank(bdcSlXmDO.getXmid())) {
                bdcSlXmDO.setXmid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlXmDO);
        }
        return bdcSlXmDO;
    }

    /**
     * @param bdcSlXmDO 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理项目
     */
    @Override
    public Integer updateBdcSlXm(BdcSlXmDO bdcSlXmDO) {
        int result = 0;
        if (bdcSlXmDO != null && StringUtils.isNotBlank(bdcSlXmDO.getXmid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlXmDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理项目
     */
    @Override
    public Integer deleteBdcSlXmByXmid(String xmid) {
        int result = 0;
        if (StringUtils.isNotBlank(xmid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlXmDO.class, xmid);
        }
        return result;
    }

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID删除不动产受理项目
     */
    @Override
    public Integer deleteBdcSlXmByJbxxid(String jbxxid) {
        int result = 0;
        if (StringUtils.isNotBlank(jbxxid)) {
            Example example = new Example(BdcSlXmDO.class);
            example.createCriteria().andEqualTo("jbxxid", jbxxid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    /**
     * @param jbxxid 基本信息ID
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息获取不动产受理项目集合
     */
    @Override
    public List<BdcSlYwxxDTO> listBdcSLXmDTOByJbxxid(String jbxxid) {
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = new ArrayList<>();
        if (StringUtils.isNotBlank(jbxxid)) {
            Map map = Maps.newHashMap();
            map.put("jbxxid", jbxxid);
            bdcSlYwxxDTOList = bdcSlYwxxMapper.listBdcSLXmByPageOrder(map);
        }
        if (CollectionUtils.isEmpty(bdcSlYwxxDTOList)) {
            bdcSlYwxxDTOList = Collections.emptyList();
        }
        return bdcSlYwxxDTOList;
    }

    /**
     * @param map 查询参数
     * @return 不动产受理项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取不动产受理项目集合
     * <p>2019-11-21 增加按照xmid ASC进行排序<p/>
     */
    @Override
    public List<BdcSlYwxxDTO> listBdcSLXmDTO(Map map) {
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = bdcSlYwxxMapper.listBdcSLXmByPageOrder(map);
        if(CollectionUtils.isEmpty(bdcSlYwxxDTOList)){
            bdcSlYwxxDTOList =new ArrayList<>();
        }
        return bdcSlYwxxDTOList;
    }

    /**
     * @param pageable 分页对象
     * @param map      查询条件
     * @return 不动产受理项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息获取不动产受理项目集合
     */
    @Override
    public Page<BdcSlYwxxDTO> listBdcSLXmDTOByPage(Pageable pageable, Map map) {
        return repo.selectPaging("listBdcSLXmByPageOrder", map, pageable);
    }

    /**
     * @param jbxxid     基本信息ID
     * @param bdcSlxxDTO 受理信息
     * @return 受理信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID获取受理信息
     */
    @Override
    public BdcSlxxDTO queryBdcSlxx(String jbxxid, BdcSlxxDTO bdcSlxxDTO, Integer sfzlcsh) {
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setJbxxid(jbxxid);
        bdcSlXmQO.setSfzlcsh(sfzlcsh);
        List<BdcSlXmDO> bdcSlXmDOList = listBdcSlXm(bdcSlXmQO);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            List<String> currentXmidList = bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
            //根据xmid排序
            bdcSlXmDOList.sort(Comparator.comparing(BdcSlXmDO::getXmid));
            List<BdcSlXmDTO> bdcSlXmDTOList = new ArrayList<>();
//            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
//                BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
//                bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
//                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgxByXmid(bdcSlXmDO.getXmid());
//                //特殊流程需要查询当前xmid作为原项目ID的数据
//                List<BdcSlXmLsgxDO> xmLsgxDOS = bdcSlXmLsgxService.listBdcSlXmLsgx("", bdcSlXmDO.getXmid(), null);
//                if (CollectionUtils.isNotEmpty(xmLsgxDOS)) {
//                    //当前xmid作为原项目ID,一种是特殊逻辑处理，还有可能当前为合并登记，需要排除此类数据
//                    xmLsgxDOS = xmLsgxDOS.stream().filter(lsgx -> !currentXmidList.contains(lsgx.getXmid())).collect(Collectors.toList());
//                    if(CollectionUtils.isNotEmpty(xmLsgxDOS)) {
//                        if (CollectionUtils.isNotEmpty(withdycfbhz) && StringUtils.isNotBlank(bdcSlxxDTO.getBdcSlJbxx().getGzldyid()) && withdycfbhz.contains(bdcSlxxDTO.getBdcSlJbxx().getGzldyid())) {
//                            bdcSlXmLsgxDOList.addAll(xmLsgxDOS);
//                        } else {
//                            for (BdcSlXmLsgxDO slXmLsgxDO : xmLsgxDOS) {
//                                if (StringUtils.isNotBlank(slXmLsgxDO.getXmid()) && slXmLsgxDO.getXmid().startsWith("TDDY_")) {
//                                    bdcSlXmLsgxDOList.add(slXmLsgxDO);
//                                }
//                            }
//                        }
//                    }
//                }
//                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
//                    bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);
//                }
//                List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(bdcSlXmDO.getXmid(), "");
//                if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
//                    bdcSlXmDTO.setBdcSlSqrDOList(bdcSlSqrDOList);
//                }
//                //交易信息
//                List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid(bdcSlXmDO.getXmid());
//                if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
//                    BdcSlJyxxDO bdcSlJyxxDO = bdcSlJyxxDOList.get(0);
//                    DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
//                    dsfSlxxDTO.setJyhth(bdcSlJyxxDO.getHtbh());
//                    dsfSlxxDTO.setJyje(bdcSlJyxxDO.getJyje());
//                    bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
//                    bdcSlXmDTO.setBdcSlJyxxDO(bdcSlJyxxDO);
//                }
//
//                //受理权利信息
//                BdcSlDyaqDO bdcSlDyaqDO = bdcSlDyaqService.queryBdcSlDyaqByXmid(bdcSlXmDO.getXmid());
//                if (null != bdcSlDyaqDO) {
//                    bdcSlXmDTO.setBdcSlQl(bdcSlDyaqDO);
//                }
//
//                bdcSlXmDTOList.add(bdcSlXmDTO);
//            }
            //根据xmid批量查询受理历史关系并按照xmid分组
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =bdcSlXmLsgxService.listSlXmLsgxPl(currentXmidList,null);
            Map<String, List<BdcSlXmLsgxDO>> slxmgxMap =new HashMap<>();
            if(CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                slxmgxMap = bdcSlXmLsgxDOList.stream().collect(Collectors.groupingBy(BdcSlXmLsgxDO::getXmid));
            }
            //特殊流程需要查询当前xmid作为原项目ID的数据
            List<BdcSlXmLsgxDO> slXmLsgxDOS =bdcSlXmLsgxService.listSlXmLsgxPlByYxmid(currentXmidList);
            Map<String, List<BdcSlXmLsgxDO>> slxmgxByYxmidMap =new HashMap<>();
            if(CollectionUtils.isNotEmpty(slXmLsgxDOS)) {
                //当前xmid作为原项目ID,一种是特殊逻辑处理，还有可能当前为合并登记，需要排除此类数据
                slXmLsgxDOS = slXmLsgxDOS.stream().filter(lsgx -> !currentXmidList.contains(lsgx.getXmid())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(slXmLsgxDOS)){
                    slxmgxByYxmidMap = slXmLsgxDOS.stream().collect(Collectors.groupingBy(BdcSlXmLsgxDO::getYxmid));
                }
            }
            //申请人
            List<BdcSlSqrDO> bdcSlSqrDOList =bdcSlSqrService.listSlSqrPl(currentXmidList);
            Map<String, List<BdcSlSqrDO>> slsqrMap =new HashMap<>();
            if(CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                slsqrMap = bdcSlSqrDOList.stream().collect(Collectors.groupingBy(BdcSlSqrDO::getXmid));
            }
            //交易信息
            BaseQO baseQO =new BaseQO();
            baseQO.setIds(currentXmidList);
            List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmids(baseQO);
            Map<String, List<BdcSlJyxxDO>> sljyxxMap =new HashMap<>();
            if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                sljyxxMap = bdcSlJyxxDOList.stream().collect(Collectors.groupingBy(BdcSlJyxxDO::getXmid));
            }
            //受理权利信息
            List<BdcSlDyaqDO> bdcSlDyaqDOList = bdcSlDyaqService.listSlDyaqPl(currentXmidList);
            Map<String, List<BdcSlDyaqDO>> sldyaqMap =new HashMap<>();
            if(CollectionUtils.isNotEmpty(bdcSlDyaqDOList)) {
                sldyaqMap = bdcSlDyaqDOList.stream().collect(Collectors.groupingBy(BdcSlDyaqDO::getXmid));
            }
            //循环组织数据
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
                bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
                bdcSlXmDTO.setBdcSlXmLsgxList(new ArrayList<>());
                if(MapUtils.isNotEmpty(slxmgxMap) &&slxmgxMap.containsKey(bdcSlXmDO.getXmid())){
                    bdcSlXmDTO.getBdcSlXmLsgxList().addAll(slxmgxMap.get(bdcSlXmDO.getXmid()));
                }
                if(MapUtils.isNotEmpty(slxmgxByYxmidMap) &&slxmgxByYxmidMap.containsKey(bdcSlXmDO.getXmid())){
                    List<BdcSlXmLsgxDO> yxmidLsgxList =slxmgxByYxmidMap.get(bdcSlXmDO.getXmid());
                    if (CollectionUtils.isNotEmpty(withdycfbhz) && StringUtils.isNotBlank(bdcSlxxDTO.getBdcSlJbxx().getGzldyid()) && withdycfbhz.contains(bdcSlxxDTO.getBdcSlJbxx().getGzldyid())) {
                        bdcSlXmDTO.getBdcSlXmLsgxList().addAll(yxmidLsgxList);
                    } else {
                        for (BdcSlXmLsgxDO slXmLsgxDO : yxmidLsgxList) {
                            if (StringUtils.isNotBlank(slXmLsgxDO.getXmid()) && slXmLsgxDO.getXmid().startsWith("TDDY_")) {
                                bdcSlXmDTO.getBdcSlXmLsgxList().add(slXmLsgxDO);
                            }
                        }
                    }
                }
                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                if(MapUtils.isNotEmpty(slsqrMap) &&slsqrMap.containsKey(bdcSlXmDO.getXmid())){
                    bdcSlXmDTO.getBdcSlSqrDOList().addAll(slsqrMap.get(bdcSlXmDO.getXmid()));
                }
                if(MapUtils.isNotEmpty(sljyxxMap) &&sljyxxMap.containsKey(bdcSlXmDO.getXmid())){
                    BdcSlJyxxDO bdcSlJyxxDO = sljyxxMap.get(bdcSlXmDO.getXmid()).get(0);
                    DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                    dsfSlxxDTO.setJyhth(bdcSlJyxxDO.getHtbh());
                    dsfSlxxDTO.setJyje(bdcSlJyxxDO.getJyje());
                    bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
                    bdcSlXmDTO.setBdcSlJyxxDO(bdcSlJyxxDO);
                }
                if(MapUtils.isNotEmpty(sldyaqMap) &&sldyaqMap.containsKey(bdcSlXmDO.getXmid())){
                    BdcSlDyaqDO bdcSlDyaqDO = sldyaqMap.get(bdcSlXmDO.getXmid()).get(0);
                    bdcSlXmDTO.setBdcSlQl(bdcSlDyaqDO);
                }
                bdcSlXmDTOList.add(bdcSlXmDTO);
            }
            bdcSlxxDTO.setBdcSlXmList(bdcSlXmDTOList);

        }
        return bdcSlxxDTO;
    }

    @Override
    public BdcSlXmDTO queryBdcSlxx(String xmid){
        BdcSlXmDTO bdcSlXmDTO =new BdcSlXmDTO();
        if(StringUtils.isNotBlank(xmid)) {
            BdcSlXmDO bdcSlXmDO = queryBdcSlXmByXmid(xmid);
            bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOList);
            }
            List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrService.listBdcSlSqrByXmid(xmid, "");
            if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                bdcSlXmDTO.setBdcSlSqrDOList(bdcSlSqrDOList);
            }
            //交易信息
            List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxService.listBdcSlJyxxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                BdcSlJyxxDO bdcSlJyxxDO = bdcSlJyxxDOList.get(0);
                DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
                dsfSlxxDTO.setJyhth(bdcSlJyxxDO.getHtbh());
                dsfSlxxDTO.setJyje(bdcSlJyxxDO.getJyje());
                bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
                bdcSlXmDTO.setBdcSlJyxxDO(bdcSlJyxxDO);
            }
        }
        return bdcSlXmDTO;
    }

    /**
     * @param cnqx
     * @param slsj
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取领证日期
     * @date : 2020/1/15 10:02
     */
    @Override
    public Date getLzrq(Integer cnqx, Date slsj) throws Exception {
        List<Work> works = workDayClient.getWorks();
        WorkDayVO workDayVO = new WorkDayVO();
        if (CollectionUtils.isNotEmpty(works)) {
            List<WorkDay> workDays = workDayClient.getWorkDays(works.get(0).getId(), "", "");
            workDayVO = DateUtilForWorkDay.getCloudWorkDays(workDays);
        }
        return DateUtilForWorkDay.getNextWorkDay(slsj, cnqx, workDayVO.getHolidayList(), workDayVO.getWorkList());
    }

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID更新受理信息
     */
    @Override
    public void updateBdcSlxx(BdcSlxxDTO bdcSlxxDTO) {
        if (bdcSlxxDTO != null && CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())) {
            List<BdcSlXmDTO> bdcSlXmDTOList = bdcSlxxDTO.getBdcSlXmList();
            for (BdcSlXmDTO bdcSlXmDTO : bdcSlXmDTOList) {
                if (bdcSlXmDTO.getBdcSlXm() != null) {
                    updateBdcSlXm(bdcSlXmDTO.getBdcSlXm());
                }
                if (CollectionUtils.isNotEmpty(bdcSlXmDTO.getBdcSlXmLsgxList())) {
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmDTO.getBdcSlXmLsgxList();
                    for (BdcSlXmLsgxDO bdcSlXmLsgxDO : bdcSlXmLsgxDOList) {
                        bdcSlXmLsgxService.updateBdcSlXmLsgx(bdcSlXmLsgxDO);
                    }
                }
            }
        }
    }

    /**
     * @param jbxxid  基本信息ID
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据基本信息ID, 工作流实例ID删除受理信息(全部信息删除)
     */
    @Override
    @Transactional
    public void deleteSlxx(String jbxxid, String gzlslid) {
        List<String> xmidList =new ArrayList<>();
        if(StringUtils.isBlank(jbxxid)){
            //可能是受理数据库不存在数据
            List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                xmidList = bdcXmDTOList.stream().map(BdcXmDTO::getXmid).collect(Collectors.toList());
            }

        } else {
            xmidList = getXmidListByJbxxid(jbxxid);
        }
        if (CollectionUtils.isNotEmpty(xmidList)) {
            deleteBdcSlxx(xmidList, CommonConstantUtils.SL_DELETE_ALL);
        }
        //删除收件材料同时删除storage附件
        bdcSjclService.deleteAllSjcl(gzlslid);
        // 删除收费信息
        BdcSlSfxxDO deleteParam = new BdcSlSfxxDO();
        deleteParam.setGzlslid(gzlslid);
        bdcSfService.deleteSfxx(deleteParam);
        //删除权籍调查信息
        bdcSlQjdcService.deleteSlQjdc(gzlslid);
        //删除查档信息
        for (String xmid : xmidList) {
            bdcSlCdxxService.deleteBdcCdxx("", xmid);
        }
        //删除修正信息
        bdcSlXzxxService.deleteBdcSlXzxxByGzlslid(gzlslid);

        //删除业务流转信息
        bdcSlYwlzService.delteBdcSlYwlz(gzlslid);
        //删除审核信息
        bdcSlShxxService.deleteShxxByGzlslid(gzlslid);
        //删除不予受理信息
        bdcSlBysldjService.deleteBdcByslDOBygzlslid(gzlslid);
    }

    @Override
    public Integer updateBdcSlXmList(String json) {
        Integer count = 0;
        JSONArray jsonArray = JSONObject.parseArray(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            count += entityService.updateByJsonEntity(JSONObject.toJSONString(obj), BdcSlXmDO.class);
        }
        return count;


    }

    @Override
    public Integer deleteYxFwhs(String fwhsIndexs, String jbxxid) {
        Integer count = 0;
        if (StringUtils.isNotBlank(fwhsIndexs) && StringUtils.isNotBlank(jbxxid)) {
            List<String> fwhsIndexList = Arrays.asList(fwhsIndexs.split(CommonConstantUtils.ZF_YW_DH));
            deleteYxFwhs(fwhsIndexList, jbxxid);
        }
        return count;

    }

    @Override
    public Integer deleteYxFwhs(List<String> fwhsIndexList, String jbxxid) {
        Integer count = 0;
        if (CollectionUtils.isNotEmpty(fwhsIndexList) && StringUtils.isNotBlank(jbxxid)) {
            List<List> partList = ListUtils.subList(fwhsIndexList, 1000);
            for (List data : partList) {
                bdcSlXmLsgxService.deleteBdcSlXmLsgx(jbxxid, data);
                Example example = new Example(BdcSlXmDO.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("jbxxid", jbxxid);
                criteria.andIn("qjid", data);
                count += entityMapper.deleteByExample(example);
            }
        }
        return count;

    }

    @Override
    public List<BdcSlXmDO> listBdcSlXm(BdcSlXmQO bdcSlXmQO) {
        if (!CheckParameter.checkAnyParameter(bdcSlXmQO)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        BeanUtils.copyProperties(bdcSlXmQO, bdcSlXmDO);
        return entityMapper.selectByObj(bdcSlXmDO);
    }

    @Override
    public Integer batchUpdateBdcSlXmZsxh(String zsxh, String jbxxid,List<String> xmidList){
        if(StringUtils.isBlank(jbxxid) ||CollectionUtils.isEmpty(xmidList)){
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        Map<String, Object> zsxhMap = new HashMap<>();
        zsxhMap.put("jbxxid", jbxxid);
        zsxhMap.put("zsxh", zsxh);
        int count=0;
        List<List> partList= ListUtils.subList(xmidList,1000);
        for(List data:partList){
            zsxhMap.put("xmids",data);
            count +=bdcSlXmMapper.updateSlxmZsxhPl(zsxhMap);
        }

        return count;

    }

    /**
     * @param map 查询参数
     * @return 不动产受理项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取购物车所有数据包含yxmid
     */
    @Override
    public List<BdcSlYwxxDTO> listGwcData(Map map) {
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = bdcSlYwxxMapper.listGwcData(map);
        List<BdcSlYwxxDTO> bdcSlYwxxDTOS = new ArrayList<>(bdcSlYwxxDTOList.size());
        if(CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            for(BdcSlYwxxDTO bdcSlYwxxDTO : bdcSlYwxxDTOList) {
                if(StringUtils.isNotBlank(bdcSlYwxxDTO.getXmid())) {
                    bdcXmQO.setXmid(bdcSlYwxxDTO.getXmid());
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        bdcSlYwxxDTO.setBdcdywybh(bdcXmDOList.get(0).getBdcdywybh());
                        bdcSlYwxxDTO.setXmly(bdcXmDOList.get(0).getXmly());
                        bdcSlYwxxDTOS.add(bdcSlYwxxDTO);
                    }
                }
            }
        }
        return bdcSlYwxxDTOS;
    }

    @Override
    public List<BdcSlXmDTO> dealCshData(List<BdcSlXmDTO> bdcSlXmDTOList,BdcSlJbxxDO bdcSlJbxxDO,boolean sfzlcsh) {
        if(CollectionUtils.isNotEmpty(bdcSlXmDTOList)) {
            List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
            List<BdcSlJyxxDO> bdcSlJyxxDOList = new ArrayList<>();
            List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>();
            for(BdcSlXmDTO bdcSlXmDTO : bdcSlXmDTOList) {
                if(bdcSlXmDTO.getBdcSlXm() != null && StringUtils.isNotBlank(bdcSlXmDTO.getBdcSlXm().getXmid())) {
                    //bug25357线下流程不传坐落
                    bdcSlXmDTO.getBdcSlXm().setZl("");
                    bdcSlXmDOList.add(bdcSlXmDTO.getBdcSlXm());
                }
                if(bdcSlXmDTO.getBdcSlJyxxDO() != null && StringUtils.isNotBlank(bdcSlXmDTO.getBdcSlJyxxDO().getJyxxid())) {
                    bdcSlJyxxDOList.add(bdcSlXmDTO.getBdcSlJyxxDO());
                }
                if(bdcSlXmDTO.getBdcSlSqrDOList() != null) {
                    bdcSlSqrDOList.addAll(bdcSlXmDTO.getBdcSlSqrDOList());
                }
            }
            //非增量初始化，需要判断交易信息和受理项目信息是否相同条数，相同带入权利人，不相同不带入权利人
            if(!sfzlcsh) {
                //组合流程根据bdcdyh去重后的数量判断
                Set<BdcSlXmDO> bdcSlXmDOSet = new TreeSet<>(Comparator.comparing(BdcSlXmDO::getBdcdyh));
                bdcSlXmDOSet.addAll(bdcSlXmDOList);
                LOGGER.info("jbxxid:{}bdcSlXmDOSet长度{}",bdcSlJbxxDO.getJbxxid(),CollectionUtils.size(bdcSlXmDOSet));
                LOGGER.info("jbxxid:{}bdcSlJyxxDOList长度{}",bdcSlJbxxDO.getJbxxid(),CollectionUtils.size(bdcSlJyxxDOList));
                LOGGER.info("jbxxid:{}bdcSlSqrDOList长度{}",bdcSlJbxxDO.getJbxxid(),CollectionUtils.size(bdcSlSqrDOList));

                // 并案流程要带入权利人 不论长度相等逻辑 这里做个配置
                if (!zyhbbaGzldyidList.contains(bdcSlJbxxDO.getGzldyid())) {
                    if (CollectionUtils.size(bdcSlXmDOSet) != CollectionUtils.size(bdcSlJyxxDOList) && CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                        if (StringUtils.isBlank(hlbjjyxxlc) || !StringToolUtils.existItemEquals(bdcSlJbxxDO.getGzldyid(),hlbjjyxxlc.split(","))){
                            //处理需要初始化的数据---判断交易信息和受理项目信息是否相同条数，相同带入权利人，不相同不带入权利人
                            bdcSlXmDTOList.forEach(bdcSlXmDTO -> bdcSlXmDTO.setBdcSlSqrDOList(Collections.emptyList()));
                            //删除受理库中的申请人数据
                            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                                LOGGER.info("判断交易信息和受理项目信息是否相同条数，相同带入权利人，不相同不带入权利人,判断结果代入");
                                bdcSlSqrService.batchDeleteSlSqr(bdcSlXmDOList.get(0).getJbxxid());
                            }
                        }

                    }
                }
            }
        }
        return bdcSlXmDTOList;
    }

    @Override
    @Transactional
    public void deleteBdcSlxx(List<String> xmidList,String type){
        Set<BdcCommonSlService> bdcSlDeleteServiceSet;
        if(StringUtils.equals(CommonConstantUtils.SL_DELETE_GWC,type)) {
            //购物车受理信息删除
            bdcSlDeleteServiceSet = acceptBeanFactory.getBdcGwcSlDeleteServiceSet();
        }else{
            bdcSlDeleteServiceSet = acceptBeanFactory.getBdcSlDeleteServiceSet();
        }
        if(CollectionUtils.isNotEmpty(bdcSlDeleteServiceSet) &&CollectionUtils.isNotEmpty(xmidList)){
            BdcSlDeleteCsDTO bdcSlDeleteCsDTO =new BdcSlDeleteCsDTO();
            List<List> partList= ListUtils.subList(xmidList,500);
            for(List data:partList){
                bdcSlDeleteCsDTO.setXmidList(data);
                for(BdcCommonSlService bdcSlService:bdcSlDeleteServiceSet){
                    bdcSlService.batchDelete(bdcSlDeleteCsDTO);
                }
            }
        }
    }

    @Override
    public List<String> getXmidListByJbxxid(String jbxxid){
        if(StringUtils.isNotBlank(jbxxid)){
            List<BdcSlXmDO> bdcSlXmDOList =listBdcSlXmByJbxxid(jbxxid,"");
            if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
                return  bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();

    }

    @Override
    public List<BdcSlXmVO> listGwcSelectDataWithLsgx(BdcSlXmQO bdcSlXmQO){
        if(StringUtils.isBlank(bdcSlXmQO.getJbxxid())){
            throw new AppException("缺失查询参数基本信息ID");
        }
        return bdcSlYwxxMapper.listGwcSelectDataWithLsgx(bdcSlXmQO);

    }



    /**
     * 判断是否过滤不动产单元号
     *
     * @param xmid xmid
     * @return 是否去除重复的不动产单元号
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public boolean bdcdyhIsRepeat(String xmid) {
        if (StringUtils.isBlank(xmid) || StringUtils.equals(repeat, "true")) {
            return true;
        }
        // 添加了配置，并且上一手权利类型为 查封或者抵押才会生效
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOS = bdcSlXmLsgxService.listBdcSlXmLsgxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOS) && null != bdcSlXmLsgxDOS.get(0) &&StringUtils.isNotBlank(bdcSlXmLsgxDOS.get(0).getYxmid())) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(bdcSlXmLsgxDOS.get(0).getYxmid());
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOS) && null != bdcXmDOS.get(0)) {
                if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDOS.get(0).getQllx()) ||
                        CommonConstantUtils.QLLX_CF.equals(bdcXmDOS.get(0).getQllx())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 根据历史关系找到项目表数据
     *
     * @param bdcSlXmQO
     * @return 受理项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @Override
    public List<BdcSlXmDO> queryBdcSlxmByLsgx(BdcSlXmQO bdcSlXmQO) {
        if(StringUtils.isBlank(bdcSlXmQO.getJbxxid()) || StringUtils.isBlank(bdcSlXmQO.getXmid())) {
            throw new AppException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        String json = JSON.toJSONString(bdcSlXmQO);
        Map map = JSONObject.parseObject(json, HashMap.class);
        return bdcSlXmMapper.querySlxmByLsgx(map);
    }

    @Override
    public void batchDelete(BdcSlDeleteCsDTO bdcSlDeleteCsDTO){
        if (!CheckParameter.checkAnyParameter(bdcSlDeleteCsDTO)) {
            throw new AppException("删除参数为空" + JSONObject.toJSONString(bdcSlDeleteCsDTO));
        }
        bdcSlXmMapper.batchDeleteBdcSlXm(bdcSlDeleteCsDTO);


    }

    @Override
    public List<BdcSlCshFwkgDataDTO> querySlFzztByJbxxid(String jbxxid){
        List<BdcSlCshFwkgDataDTO> bdcSlCshFwkgDataDTOList =bdcSlXmMapper.querySlFzztByJbxxid(jbxxid);
        if(CollectionUtils.isNotEmpty(bdcSlCshFwkgDataDTOList)){
            Map<String,BdcCshFwkgDO> djxlFwkgMap =new HashMap<>();
            //获取初始化房屋开关配置
            for(BdcSlCshFwkgDataDTO bdcSlCshFwkgDataDTO:bdcSlCshFwkgDataDTOList){
                if((bdcSlCshFwkgDataDTO.getSfsczs() ==null ||bdcSlCshFwkgDataDTO.getZszl() ==null) &&StringUtils.isNotBlank(bdcSlCshFwkgDataDTO.getDjxl())){
                    //发证情况为空时,读取开关配置表
                    BdcCshFwkgDO bdcCshFwkgDO;
                    if(djxlFwkgMap.get(bdcSlCshFwkgDataDTO.getDjxl()) != null){
                        bdcCshFwkgDO =djxlFwkgMap.get(bdcSlCshFwkgDataDTO.getDjxl());
                    }else {
                        bdcCshFwkgDO = bdcCshXtPzFeignService.queryBdcCshFwKgDO(bdcSlCshFwkgDataDTO.getDjxl());
                        djxlFwkgMap.put(bdcSlCshFwkgDataDTO.getDjxl(), bdcCshFwkgDO);
                    }
                    if(bdcCshFwkgDO != null) {
                        if (bdcSlCshFwkgDataDTO.getSfsczs() == null) {
                            bdcSlCshFwkgDataDTO.setSfsczs(bdcCshFwkgDO.getSfsczs());
                        }
                        if (bdcSlCshFwkgDataDTO.getZszl() == null) {
                            bdcSlCshFwkgDataDTO.setZszl(bdcCshFwkgDO.getZszl());
                        }
                    }
                }
            }

        }
        return bdcSlCshFwkgDataDTOList;

    }

    @Override
    public List<String> updateSlxmFzztPl(String jbxxid,List<BdcSlXmDO> fzztXmDOList,String gzldyid){
        Set<String> bdcdyhSet = new HashSet<>();
        if(StringUtils.isBlank(jbxxid) ||CollectionUtils.isEmpty(fzztXmDOList)){
            throw new MissingArgumentException("缺失参数基本信息ID或发证信息。");
        }

        List<BdcSlXmDO> bdcSlXmDOS = listBdcSlXmByJbxxid(jbxxid,"");
        if(CollectionUtils.isEmpty(bdcSlXmDOS)){
            return new ArrayList<>(bdcdyhSet);
        }

        //将获取到项目集合数据转换成Map<bdcdyh, bdcSlXmDO>结构，便于后续查找项目
        Map<String, BdcSlXmDO> bdcdyhXmMap = bdcSlXmDOS.stream().collect(Collectors.toMap(BdcSlXmDO::getBdcdyh, bdcSlXmDO -> bdcSlXmDO));

        //获取批量更新xmid集合
        List<String> xmidList = new ArrayList<>();

        for(BdcSlXmDO fzztXm: fzztXmDOList){
            if(StringUtils.isNotBlank(fzztXm.getBdcdyh())){
                BdcSlXmDO slXmDO =bdcdyhXmMap.get(fzztXm.getBdcdyh());
                if(slXmDO != null){
                    xmidList.add(slXmDO.getXmid());
                    bdcdyhSet.add(fzztXm.getBdcdyh());
                }
            }
        }

        if(CollectionUtils.isEmpty(xmidList)){
            return new ArrayList<>(bdcdyhSet);
        }

        Map<String,Object> fzztMap =new HashMap<>();
        Integer zszl = 0;
        LOGGER.info("证书种类:{}", fzztXmDOList.get(0).getZszl());
        if (Objects.nonNull(fzztXmDOList.get(0).getZszl())){
            zszl = fzztXmDOList.get(0).getZszl();
            LOGGER.info("证书种类:{}", zszl);
        }
        //获取发证信息(发证信息一致,任取一条判断即可)
        if(CommonConstantUtils.SF_F_DM.equals(fzztXmDOList.get(0).getSfsczs()) && 4 != zszl){
            if(StringUtils.isBlank(bfzdjxl)){
                throw new AppException("未配置不发证登记小类,请检查配置：bfzdjxl");
            }
            //不发证,修改权利类型与登记小类
            fzztMap.put("qllx",CommonConstantUtils.QLLX_JZWQFSYQYZGYBF_DM);
            fzztMap.put("djxl",bfzdjxl);
            LOGGER.info("成功更新登记小类:{}", bfzdjxl);
        }else{
            Integer dyhqllx = bdcQllxService.getQllxByBdcdyh(fzztXmDOList.get(0).getBdcdyh(), "");
            fzztMap.put("qllx",dyhqllx);
            List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzService.listBdcDjxlPzByGzldyid(gzldyid, dyhqllx, null);
            if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
                fzztMap.put("djxl", bdcDjxlPzDOList.get(0).getDjxl());
            }
        }
        fzztMap.put("sfsczs",fzztXmDOList.get(0).getSfsczs());
        if(fzztXmDOList.get(0).getZszl() != null) {
            fzztMap.put("zszl", fzztXmDOList.get(0).getZszl());
        }else{
            fzztMap.put("zszl", "");
        }
        //批量更新
        updateBatchBdcSlXm(JSONObject.toJSONString(fzztMap),xmidList);
        return new ArrayList<>(bdcdyhSet);

    }

    @Override
    public void deleteYxxm(BdcGwcDeleteQO bdcGwcDeleteQO){
        //验证必传参数
        if(bdcGwcDeleteQO ==null ||(StringUtils.isBlank(bdcGwcDeleteQO.getXmids())&&StringUtils.isBlank(bdcGwcDeleteQO.getOnexmid()) &&StringUtils.isBlank(bdcGwcDeleteQO.getJbxxid()))){
            return;
        }
        //获取删除项目ID集合
        List<String> xmidList =getDeleteXmidList(bdcGwcDeleteQO);
        //删除受理信息
        String bdcdyh = "";
        if(CollectionUtils.isNotEmpty(xmidList)) {
            BdcSlXmDO bdcSlXmDO = queryBdcSlXmByXmid(xmidList.get(0));
            if(bdcSlXmDO != null && StringUtils.isNotBlank(bdcSlXmDO.getBdcdyh())) {
                bdcdyh = bdcSlXmDO.getBdcdyh();
            }
            deleteBdcSlxx(xmidList,CommonConstantUtils.SL_DELETE_GWC);
            //如果存在例外信息且执行清除，同时删除-bdc_gzlw_sh表
            if (CommonConstantUtils.SF_S_DM.equals(bdcGwcDeleteQO.getClearGwc()) &&StringUtils.isNotBlank(bdcGwcDeleteQO.getSlbh())) {
                BdcGzlwShDO bdcGzlwShDO = new BdcGzlwShDO();
                String slbh =bdcGwcDeleteQO.getSlbh();
                //南通受理编号需要增加特征码
                if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_NT, bdcGwcDeleteQO.getSystemVersion())) {
                    String tzm = BdcdyhToolUtils.queryTzmByBdcdyh(bdcdyh);
                    slbh = tzm + bdcGwcDeleteQO.getSlbh();
                }
                bdcGzlwShDO.setSlbh(slbh);
                bdcGzlwFeignService.deleteBdcGzlwShByGzlw(bdcGzlwShDO);
            }
        }

    }

    /**
     * @param pageable
     * @param bdcBdcdyQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询当前流程房屋信息
     * @date : 2022/8/18 16:28
     */
    @Override
    public Page<BdcSlFwxxDTO> listBdcFwxxByPage(Pageable pageable, BdcBdcdyQO bdcBdcdyQO) {
        return repo.selectPaging("listBdcSlFwxxByPageOrder", bdcBdcdyQO, pageable);
    }

    /**
     * @param jbxxid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询购物车数据库的所有规划用途
     * @date : 2022/9/13 11:17
     */
    @Override
    public List<String> listGwcGhyt(String jbxxid) {
        if (StringUtils.isBlank(jbxxid)) {
            return null;
        }
        return bdcSlXmMapper.listGwcGhyt(jbxxid);
    }

    /**
     * @param jbxxid
     * @param djxl
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询购物车所有面积
     * @date : 2022/9/13 11:19
     */
    @Override
    public String queryGwcDzwmj(String jbxxid, String djxl) {
        if (StringUtils.isBlank(jbxxid)) {
            return null;
        }
        return bdcSlXmMapper.sumGwcDzwmj(jbxxid, djxl);
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取删除项目ID集合
     */
    private List<String> getDeleteXmidList(BdcGwcDeleteQO bdcGwcDeleteQO) {
        //获取项目ID集合
        List<String> xmidList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcGwcDeleteQO.getXmids())) {
            xmidList = Arrays.asList(bdcGwcDeleteQO.getXmids().split(CommonConstantUtils.ZF_YW_DH));
        } else if (StringUtils.isNotBlank(bdcGwcDeleteQO.getOnexmid())) {
            //单个xmid删除时，先根据xmid找到jbxxid，根据jbxxid找到当前项目id所对应的不动产单元号相同的一起删除
            BdcSlXmDO bdcSlXmDO = queryBdcSlXmByXmid(bdcGwcDeleteQO.getOnexmid());
            if (bdcSlXmDO != null &&StringUtils.isNotBlank(bdcSlXmDO.getJbxxid())) {
                List<BdcSlXmDO> bdcSlXmDOList;
                // modified by lixin 不动产单元号相同的不一并删除，只针对于 xmid 删除
                if (!bdcdyhIsRepeat(bdcSlXmDO.getXmid())) {
                    bdcSlXmDOList = Collections.singletonList(bdcSlXmDO);
                } else {
                    bdcSlXmDOList = listBdcSlXmByJbxxid(bdcSlXmDO.getJbxxid(),"");
                }
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    for (BdcSlXmDO bdcSlXm : bdcSlXmDOList) {
                        if (bdcSlXm.getBdcdyh().equals(bdcSlXmDO.getBdcdyh())) {
                            xmidList.add(bdcSlXm.getXmid());
                        }
                    }
                }
            }
        } else if (StringUtils.isNotBlank(bdcGwcDeleteQO.getJbxxid())) {
            List<BdcSlXmDO> bdcSlXmDOList =new ArrayList<>();
            if(Boolean.TRUE.equals(cjyzlwsh) &&StringUtils.isNotBlank(bdcGwcDeleteQO.getSlbh())){
                bdcSlXmDOList =listBdcSlXmByJbxxid(bdcGwcDeleteQO.getJbxxid(),"");
                if(CollectionUtils.isEmpty(bdcSlXmDOList)){
                    return new ArrayList<>();
                }
                String slbh =bdcGwcDeleteQO.getSlbh();
                //南通受理编号需要增加特征码
                if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_NT, bdcGwcDeleteQO.getSystemVersion())) {
                    String tzm = BdcdyhToolUtils.queryTzmByBdcdyh(bdcSlXmDOList.get(0).getBdcdyh());
                    slbh = tzm + slbh;
                }
                //查询是否存在例外审核数据
                BdcGzlwShQO bdcGzlwShQO = new BdcGzlwShQO();
                bdcGzlwShQO.setSlbh(slbh);
                List<BdcGzlwShDO> bdcGzlwShDOList = bdcGzlwFeignService.listBdcGzlwByBdcGzlwShQO(bdcGzlwShQO);
                if(CollectionUtils.isNotEmpty(bdcGzlwShDOList)){
                    //存在规则例外审核不清除受理信息
                    return new ArrayList<>();
                }
            }
            //增量初始化时,只清空增量初始化的受理信息
            if (CommonConstantUtils.SF_S_DM.equals(bdcGwcDeleteQO.getSfzlcsh())) {
                BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
                bdcSlXmQO.setJbxxid(bdcGwcDeleteQO.getJbxxid());
                bdcSlXmQO.setSfzlcsh(bdcGwcDeleteQO.getSfzlcsh());
                List<BdcSlXmDO> slXmDOS = listBdcSlXm(bdcSlXmQO);
                if(CollectionUtils.isNotEmpty(slXmDOS)){
                    for(BdcSlXmDO bdcSlXmDO:slXmDOS){
                        xmidList.add(bdcSlXmDO.getXmid());
                    }
                }
            }else {
                BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByJbxxid(bdcGwcDeleteQO.getJbxxid());
                if (bdcSlJbxxDO == null) {
                    if(CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                        xmidList =bdcSlXmDOList.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList());
                    }else {
                        xmidList = getXmidListByJbxxid(bdcGwcDeleteQO.getJbxxid());
                    }
                }
            }
        }
        return xmidList;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理项目
     */
    private int updateBatchBdcSlXm(String jsonStr, List<String> xmids) {
        if(StringUtils.isBlank(jsonStr) ||CollectionUtils.isEmpty(xmids)){
            return 0;
        }
        //获取更新json对象
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Map map = new HashMap();
        //获取批量更新语句
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcSlXmDO.class.getName());
        if(!statement.contains("SET")) {
            return 0;
        }
        map.put("statement", statement);
        //获取实体对象
        BdcSlXmDO bdcSlXmDO = JSON.parseObject(jsonStr, BdcSlXmDO.class);
        map.put("record", bdcSlXmDO);
        //where 条件
        map.put("xmids", xmids);
        return bdcSlXmMapper.batchUpdateBdcSlXm(map);

    }

    @Override
    public List<BdcSlXmDO> queryBdcSlXmDOList(BdcSlXmQO bdcSlXmQO) {
        return bdcSlXmMapper.queryBdcSlXmDOList(bdcSlXmQO);
    }

    /**
     * @param param
     * @return
     */
    @Override
    public List<BdcXmGdxxDTO> listBdcGdxxHf(HashMap<String, Object> param) {
        return bdcSlXmMapper.listBdcGdxxHf(param);
    }

    /**
     * @param dagsd
     * @param jbxxidList
     * @param xmidList
     * @return
     */
    @Override
    public Integer batchUpdateBdcSlXmDagsd(String dagsd, List<String> jbxxidList, List<String> xmidList) {
        if(StringUtils.isBlank(dagsd)){
            return 0;
        }
        BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
        bdcSlXmDO.setDagsd(dagsd);

        Example example = new Example(BdcSlXmDO.class);
        if (CollectionUtils.isNotEmpty(jbxxidList)){
            List<List<String>> partition = Lists.partition(jbxxidList, 1000);
            for (List<String> jbxxids : partition) {
                example.clear();
                Example.Criteria criteria = example.createCriteria();
                criteria.andIn("jbxxid",new ArrayList<>(jbxxids));
                entityMapper.updateByExampleSelective(bdcSlXmDO,example);
            }
            return jbxxidList.size();
        }
        if (CollectionUtils.isNotEmpty(xmidList)){
            List<List<String>> partition = Lists.partition(xmidList, 1000);
            for (List<String> xmids : partition) {
                example.clear();
                Example.Criteria criteria = example.createCriteria();
                criteria.andIn("xmid",new ArrayList<>(xmids));
                entityMapper.updateByExampleSelective(bdcSlXmDO,example);
            }
            return xmidList.size();
        }

        return 0;
    }

    /**
     * @param bdcSlxxInitDTOList
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 受理信息保存
     */
    @Override
    public void saveBdcSlxx(List<BdcSlxxInitDTO> bdcSlxxInitDTOList, String czlx) throws IllegalAccessException {

        BdcSlxxInitDTO bdcSlxxInitDTO = new BdcSlxxInitDTO();
        //整理数据
        dealDTO(bdcSlxxInitDTO, bdcSlxxInitDTOList);
        //先删除
        if (CollectionUtils.isNotEmpty(bdcSlxxInitDTO.getDeleteList())) {
            for (Object obj : bdcSlxxInitDTO.getDeleteList()) {
                Method method = AnnotationsUtils.getAnnotationsName(obj);
                String id = null;
                try {
                    if (method.invoke(obj) != null) {
                        id = method.invoke(obj).toString();
                    }
                } catch (Exception e) {
                    LOGGER.error(null, e);
                }
                entityMapper.deleteByPrimaryKey(obj.getClass(), id);
                LOGGER.info("数据入库前先执行删除操作，对象class：{}，主键ID：{}", obj.getClass().getName(), id);
            }
            //清空
            bdcSlxxInitDTO.getDeleteList().clear();
        }
        Field[] fields = BdcSlxxInitDTO.class.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object val = field.get(bdcSlxxInitDTO);
                //判断是否是list集合
                if (val != null && field.getType() == List.class) {
                    List filedList = (List) val;
                    if (CollectionUtils.isNotEmpty(filedList)) {
                        //存储实体类名为主键的map
                        HashMap<String, List> map = new HashMap<>();
                        List voList;
                        for (int i = 0; i < filedList.size(); i++) {
                            if (map.get(filedList.get(i).getClass().getSimpleName()) == null) {
                                voList = new ArrayList();
                                map.put(filedList.get(i).getClass().getSimpleName(), voList);
                            } else {
                                voList = map.get(filedList.get(i).getClass().getSimpleName());
                            }
                            voList.add(filedList.get(i));
                        }
                        if (MapUtils.isNotEmpty(map)) {
                            for (List list : map.values()) {
                                if (CollectionUtils.isNotEmpty(list)) {
                                    List<List> partList = ListUtils.subList(list, 500);
                                    for (List data : partList) {
                                        if (Constants.FUN_UPDATE.equals(czlx)) {
                                            //czlx为更新执行更新方法
                                            entityMapper.batchSaveSelective(data);
                                        } else {
                                            entityMapper.insertBatchSelective(data);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return 更新数量
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 批量更新受理项目
     */
    @Override
    public Integer updateBatchBdcSlXm(BdcDjxxUpdateQO bdcDjxxUpdateQO) {
        if (bdcDjxxUpdateQO == null || StringUtils.isBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
        }
        String jsonStr = bdcDjxxUpdateQO.getJsonStr();
        //获取更新json对象
        JSONObject jsonObject = JSON.parseObject(jsonStr);

        Map map = new HashMap();
        //获取批量更新语句
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcSlXmDO.class.getName());
        if (!statement.contains("SET")) {
            return 0;
        }

        map.put("statement", statement);
        //where 条件
        map.putAll(bdcDjxxUpdateQO.getWhereMap());
        //获取实体对象
        BdcSlXmDO bdcSlXmDO = JSON.parseObject(jsonStr, BdcSlXmDO.class);
        map.put("record", bdcSlXmDO);
        map.remove("gzlslid");
        return bdcSlXmMapper.batchUpdateBdcSlXm(map);
    }


    /**
     * @param
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理数据
     */
    private void dealDTO(BdcSlxxInitDTO bdcSlxxInitDTO, Collection<BdcSlxxInitDTO> collection) throws IllegalAccessException {
        //要封装的结构
        Field[] fields = BdcSlxxInitDTO.class.getDeclaredFields();
        for (Field resultField : fields) {
            resultField.setAccessible(true);
            for (BdcSlxxInitDTO slxxInitDTO : collection) {
                //每个业务的数据
                Field[] dtoFields = BdcSlxxInitDTO.class.getDeclaredFields();
                for (Field dtoField : dtoFields) {
                    dtoField.setAccessible(true);
                    Object val = dtoField.get(slxxInitDTO);
                    if (val != null) {
                        List list = (List) resultField.get(bdcSlxxInitDTO);
                        //空值的话进行赋值
                        if (list == null) {
                            resultField.set(bdcSlxxInitDTO, new ArrayList<>());
                        }
                        //比对转换
                        if (dtoField.getType() == List.class) {
                            if (StringUtils.equals(resultField.getGenericType().toString(), dtoField.getGenericType().toString())) {
                                ((List) resultField.get(bdcSlxxInitDTO)).addAll((List) val);
                                break;
                            }
                        } else {
                            //实体对象名有相近的 通过>去判断
                            if (StringUtils.contains(resultField.getGenericType().getTypeName(), dtoField.getType().getName() + ">")) {
                                ((List) resultField.get(bdcSlxxInitDTO)).add(val);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
