package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.dto.init.BdcDyaqDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQlxxRequestDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDyafeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcYgFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcYyXxCxFeignService;
import cn.gtmap.realestate.common.core.service.rest.building.FwLjzRestService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.SqlUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.config.BdcDzgxConfig;
import cn.gtmap.realestate.init.core.mapper.BdcQlMapper;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/1
 * @description ??????????????????
 */
@Service
public class BdcQllxServiceImpl implements BdcQllxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcQllxServiceImpl.class);


    /**
     * ??????????????????????????????????????????????????????????????????
     */
    @Value("#{'${yctscl.zjjzwdya.gzldyids:}'.split(',')}")
    private List<String> zjjzwdyas;

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcDzgxConfig bdcDzgxConfig;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;
    @Autowired
    private BdcQlMapper bdcQlMapper;
    @Autowired
    private FwHsFeignService FwHsFeignService;

    @Autowired
    private BdcDyafeignService bdcDyafeignService;

    @Autowired
    private BdcCfxxFeignService bdcCfxxFeignService;

    @Autowired
    private BdcYyXxCxFeignService bdcYyXxCxFeignService;

    @Autowired
    private BdcYgFeignService bdcYgFeignService;

    @Autowired
    private FwLjzFeginService fwLjzFeginService;

    @Override
    public String getTableName(BdcQl bdcQl) {
        String name = null;
        if (bdcQl != null) {
            Table annotation = bdcQl.getClass().getAnnotation(Table.class);
            if (annotation != null) {
                name = annotation.name();
            }
        }
        return name;
    }

    @Override
    public BdcQl makeSureQllx(String qllx) {
        BdcQl bdcQl = null;
        Map qllxMap = bdcDzgxConfig.getQllxMap();
        // ?????????????????????
        if (StringUtils.isNotBlank(qllx)) {
            Map<String, Object> qllxObjectMap = new HashMap<>();
            // ???????????????
            BdcCfDO bdcCfDO = new BdcCfDO();
            qllxObjectMap.put(getTableName(bdcCfDO).toUpperCase(), bdcCfDO);
            // ??????????????????
            BdcDyaqDO bdcDyaqDO = new BdcDyaqDO();
            qllxObjectMap.put(getTableName(bdcDyaqDO).toUpperCase(), bdcDyaqDO);
            // ?????????????????????
            BdcFdcqDO bdcFdcqDO = new BdcFdcqDO();
            qllxObjectMap.put(getTableName(bdcFdcqDO).toUpperCase(), bdcFdcqDO);
            // ???????????????
            BdcHysyqDO bdcHysyqDO = new BdcHysyqDO();
            qllxObjectMap.put(getTableName(bdcHysyqDO).toUpperCase(), bdcHysyqDO);
            // ??????????????????????????????????????????
            BdcJsydsyqDO bdcJsydsyqDO = new BdcJsydsyqDO();
            qllxObjectMap.put(getTableName(bdcJsydsyqDO).toUpperCase(), bdcJsydsyqDO);
            // ??????????????????????????????????????????????????????
            BdcFdcq3DO bdcFdcq3DO = new BdcFdcq3DO();
            qllxObjectMap.put(getTableName(bdcFdcq3DO).toUpperCase(), bdcFdcq3DO);
            // ???????????????????????????????????????
            BdcGjzwsyqDO bdcGjzwsyqDO = new BdcGjzwsyqDO();
            qllxObjectMap.put(getTableName(bdcGjzwsyqDO).toUpperCase(), bdcGjzwsyqDO);
            // ??????
            BdcLqDO bdcLqDO = new BdcLqDO();
            qllxObjectMap.put(getTableName(bdcLqDO).toUpperCase(), bdcLqDO);
            // ??????????????????????????????
            BdcQtxgqlDO bdcQtxgqlDO = new BdcQtxgqlDO();
            qllxObjectMap.put(getTableName(bdcQtxgqlDO).toUpperCase(), bdcQtxgqlDO);
            // ?????????????????????
            BdcTdcbnydsyqDO bdcTdcbnydsyqDO = new BdcTdcbnydsyqDO();
            qllxObjectMap.put(getTableName(bdcTdcbnydsyqDO).toUpperCase(), bdcTdcbnydsyqDO);
            // ?????????????????????
            BdcNydjyqDO nydjyqDO = new BdcNydjyqDO();
            qllxObjectMap.put(getTableName(nydjyqDO).toUpperCase(), nydjyqDO);
            // ???????????????
            BdcTdsyqDO bdcTdsyqDO = new BdcTdsyqDO();
            qllxObjectMap.put(getTableName(bdcTdsyqDO).toUpperCase(), bdcTdsyqDO);
            // ???????????????
            BdcYgDO bdcYgDO = new BdcYgDO();
            qllxObjectMap.put(getTableName(bdcYgDO).toUpperCase(), bdcYgDO);
            // ???????????????
            BdcYyDO bdcYyDO = new BdcYyDO();
            qllxObjectMap.put(getTableName(bdcYyDO).toUpperCase(), bdcYyDO);
            // ??????????????????
            BdcDyiqDO bdcDyiqDO = new BdcDyiqDO();
            qllxObjectMap.put(getTableName(bdcDyiqDO).toUpperCase(), bdcDyiqDO);
            // ?????????????????????
            BdcFwzlDO bdcFwzlDO = new BdcFwzlDO();
            qllxObjectMap.put(getTableName(bdcFwzlDO).toUpperCase(), bdcFwzlDO);
            // ??????????????????
            BdcJzqDO bdcJzqDO = new BdcJzqDO();
            qllxObjectMap.put(getTableName(bdcJzqDO).toUpperCase(), bdcJzqDO);

            // ????????????????????????
            bdcQl = (BdcQl) qllxObjectMap.get(qllxMap.get(qllx));
        }

        return bdcQl;
    }

    /**
     * @param slbh
     * @param gzlslid
     * @return List<BdcQl>
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description ?????????????????????????????????????????????
     */
    @Override
    public List<BdcQl> listZxQlxxDO(String slbh, String gzlslid) {
        List<BdcQl> bdcQlList = new ArrayList<>();
        //?????????????????????????????????
        List<BdcXmLsgxDO> list = bdcXmLsgxService.queryBdcXmLsgxList(slbh, gzlslid, CommonConstantUtils.SF_S_DM);
        if (CollectionUtils.isNotEmpty(list)) {
            for (BdcXmLsgxDO bdcXmLsgxDO : list) {
                if (bdcXmLsgxDO != null && StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                    BdcQl bdcQl = queryQllxDO(bdcXmLsgxDO.getYxmid());
                    if (bdcQl != null) {
                        bdcQlList.add(bdcQl);
                    }
                }
            }
        }
        return bdcQlList;
    }

    /**
     * ?????????????????????????????????
     *
     * @param slbh
     * @param gzlslid
     * @return List<BdcQl>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcQl> listHyYzxQlxx(String slbh, String gzlslid) {
        List<BdcQl> bdcQlList = new ArrayList<>();
        //???????????????????????????
        List<BdcXmLsgxDO> list = bdcXmLsgxService.queryBdcXmLsgxList(slbh, gzlslid, CommonConstantUtils.SF_S_DM);
        if (CollectionUtils.isNotEmpty(list)) {
            for (BdcXmLsgxDO bdcXmLsgxDO : list) {
                if (bdcXmLsgxDO != null && StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                    //?????????????????????  ?????????????????????????????????
                    BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmService.queryCshFwkgSl(bdcXmLsgxDO.getXmid());
                    if (bdcCshFwkgSlDO != null && CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfhyyzxql())) {
                        //???????????????????????????
                        List<BdcXmLsgxDO> listYzxXm = bdcXmLsgxService.queryBdcXmLsgxByXmid(bdcXmLsgxDO.getYxmid(),null);
                        if (CollectionUtils.isNotEmpty(listYzxXm)) {
                            for (BdcXmLsgxDO yzxXmLsgx : listYzxXm) {
                                if (yzxXmLsgx != null && StringUtils.isNotBlank(yzxXmLsgx.getYxmid()) && CommonConstantUtils.SF_S_DM.equals(yzxXmLsgx.getZxyql())) {
                                    BdcQl bdcQl = queryQllxDO(yzxXmLsgx.getYxmid());
                                    if (bdcQl != null) {
                                        bdcQlList.add(bdcQl);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bdcQlList;
    }


    /**
     * @param slbh
     * @param gzlslid
     * @return List<BdcQl>
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description ???????????????????????????????????????
     */
    @Override
    public List<BdcQl> listQlxxDO(String slbh, String gzlslid) {
        List<BdcQl> bdcQlList = new ArrayList<>();
        //?????????????????????????????????
        List<BdcXmDTO> bdcXmDOList = bdcXmService.listXmBfxx(gzlslid,slbh);

        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //??????
            bdcXmDOList.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
            //???????????????????????????????????????
            for (BdcXmDTO bdcXmDOTmp : bdcXmDOList) {
                if (bdcXmDOTmp != null && StringUtils.isNotBlank(bdcXmDOTmp.getXmid())) {
                    //??????????????????
                    BdcQl bdcQl = queryQllxDO(bdcXmDOTmp.getXmid());
                    if (bdcQl != null) {
                        bdcQlList.add(bdcQl);
                    }
                }
            }
        }
        return bdcQlList;
    }

    /**
     * @param gzlslid
     * @return List<String>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ???????????????????????????????????????
     */
    @Override
    public List<String> listQllx(String gzlslid) {
        List<String> bdcQllxList = new ArrayList<>();
        //?????????????????????????????????
        List<BdcXmDTO> bdcXmDOList = bdcXmService.listXmBfxx(gzlslid,null);

        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //??????
            bdcXmDOList.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
            //???????????????????????????
            int lx=bdcXmService.bdcXmLx(bdcXmDOList);
            boolean plOne =false;
            //????????????,????????????????????????????????????
            if(CommonConstantUtils.LCLX_PL.equals(lx)) {
                List<BdcXmDTO> bdcXmDTOList = bdcXmDOList.stream().filter(bdcXmDTO -> bdcXmDTO.getQllx() != null).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                    Map<Integer, List<BdcXmDTO>> qllxMap = bdcXmDTOList.stream().collect(Collectors.groupingBy(BdcXmDTO::getQllx));
                    if(qllxMap != null &&qllxMap.size() ==1){
                        plOne =true;
                    }
                }
            }
            //???????????????????????????????????????
            for (BdcXmDTO bdcXmDOTmp : bdcXmDOList) {
                if(bdcXmDOTmp.getQllx()!=null){
                    //?????????????????????
                    if(CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDOTmp.getQllx())){
                        BdcYgDO bdcYgDO= (BdcYgDO) queryQllxDO(new BdcYgDO(),bdcXmDOTmp.getXmid());
                        //????????????
                        if(bdcYgDO!=null){
                            String qllx=bdcXmDOTmp.getQllx().toString();
                            if(ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())){
                                qllx=CommonConstantUtils.YGDJ_YDY;
                            }
                            if(!bdcQllxList.contains(qllx)){
                                bdcQllxList.add(qllx);
                            }
                        }
                    }else{
                        BdcCshFwkgSlDO bdcCshFwkgSlDO=bdcXmService.queryCshFwkgSl(bdcXmDOTmp.getXmid());
                        //?????????????????????
                        if(bdcCshFwkgSlDO!=null){
                            if(CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfscql()) && !bdcQllxList.contains(bdcXmDOTmp.getQllx().toString())){
                                bdcQllxList.add(bdcXmDOTmp.getQllx().toString());
                            }
                        }else{
                            //?????????????????????????????? ?????????
                            BdcQl bdcQl=makeSureQllx(bdcXmDOTmp.getQllx().toString());
                            if(bdcQl!=null && queryQllxDO(bdcQl,bdcXmDOTmp.getXmid())!=null){
                                bdcQllxList.add(bdcXmDOTmp.getQllx().toString());
                            }
                        }
                    }
                    //???????????????
                    if(plOne){
                        break;
                    }
                }
            }
        }
        return bdcQllxList;
    }

    /**
     * @param gzlslid
     * @return List<String>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ?????????????????????????????????????????????
     */
    @Override
    public List<String> listZxQllx(String gzlslid) {
        List<String> bdcQllxList = new ArrayList<>();
        //?????????????????????????????????
        List<BdcXmLsgxDO> list = bdcXmLsgxService.queryBdcXmLsgxList(null, gzlslid, CommonConstantUtils.SF_S_DM);
        if (CollectionUtils.isNotEmpty(list)) {
            for (BdcXmLsgxDO bdcXmLsgxDO : list) {
                if (bdcXmLsgxDO != null && StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                    BdcXmDO bdcXmDO=bdcXmService.queryBdcXmByPrimaryKey(bdcXmLsgxDO.getYxmid());

                    if (bdcXmDO != null && bdcXmDO.getQllx()!=null) {
                        String qllx=bdcXmDO.getQllx().toString();
                        if(CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx())){
                            BdcYgDO bdcYgDO= (BdcYgDO) queryQllxDO(new BdcYgDO(),bdcXmDO.getXmid());
                            //????????????
                            if(bdcYgDO!=null){
                                if(ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())){
                                    qllx=CommonConstantUtils.YGDJ_YDY;
                                }
                            }
                        }
                        if(!bdcQllxList.contains(qllx)) {
                            bdcQllxList.add(qllx);
                        }
                    }
                }
            }
        }
        return bdcQllxList;
    }

    /**
     * @author  <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @param   xmidList ??????ID
     * @return  {List} ????????????
     * @description ????????????ID?????????????????????????????????
     */
    public List<BdcQl> listQlxxDOByXmids(List<String> xmidList){
        List<BdcQl> bdcQlList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(xmidList)) {
            //???????????????????????????????????????
            for (String xmid : xmidList) {
                if (StringUtils.isNotBlank(xmid)) {
                    //??????????????????
                    BdcQl bdcQl = queryQllxDO(xmid);
                    if (bdcQl != null) {
                        bdcQlList.add(bdcQl);
                    }
                }
            }
        }
        return bdcQlList;

    }

    @Override
    public BdcQl queryQllxDO(BdcQl bdcQl, String xmid) {
        BdcQl returnQllx = null;
        if (bdcQl != null && StringUtils.isNotBlank(xmid)) {
            Example qllxExample = new Example(bdcQl.getClass());
            qllxExample.createCriteria().andEqualTo("xmid", xmid);
            List<BdcQl> bdcQlList = entityMapper.selectByExample(qllxExample);
            if (CollectionUtils.isNotEmpty(bdcQlList)) {
                returnQllx = bdcQlList.get(0);
            }
        }
        return returnQllx;
    }

    @Override
    public BdcQl queryQllxDO(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(xmid);

        if (bdcXmDO == null || bdcXmDO.getQllx() == null) {
            return null;
        }
        BdcQl bdcQl;
        // ????????????????????????
        bdcQl = makeSureQllx(bdcXmDO.getQllx().toString());

        // ??????????????????
        bdcQl = queryQllxDO(bdcQl, xmid);

        return bdcQl;
    }

    @Override
    public BdcQl queryBdcYqlxx(String xmid){
        BdcQl bdcQl = null;
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(xmid);
        bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
        List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcXmLsgxService.listBdcXmLsgx(bdcXmLsgxQO);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList) && StringUtils.isNotBlank(bdcXmLsgxDOList.get(0).getYxmid())) {
            bdcQl = queryQllxDO(bdcXmLsgxDOList.get(0).getYxmid());
        }
        return bdcQl;

    }

    /**
     * @param xmids@return {BdcQl} ???????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????ID???????????????????????????
     */
    @Override
    public List<BdcQl> queryBdcYqlxxs(String xmids) {
        if(StringUtils.isNotBlank(xmids)) {
            List<BdcXmLsgxDO> bdcXmLsgxDOList =
                    bdcXmLsgxService.queryBdcXmLsgxByXmids(Arrays.asList(xmids.split(",")),CommonConstantUtils.SF_F_DM);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                List<String> yxmidList = bdcXmLsgxDOList
                        .stream()
                        .filter(bdcXmLsgxDO -> Objects.nonNull(bdcXmLsgxDO.getYxmid()))
                        .map(BdcXmLsgxDO::getYxmid).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(yxmidList)) {
                   return listQlxxDOByXmids(yxmidList);
                }
            }
        }
        return Collections.emptyList();
    }

    /**
     * ?????????????????????
     *
     * @param bdcQl ????????????
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public int updateBdcQl(BdcQl bdcQl) {
        int num;
        if (bdcQl != null && StringUtils.isNotBlank(bdcQl.getQlid())) {
            num = entityMapper.updateByPrimaryKeyNull(bdcQl);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return num;
    }

    /**
     * ?????????????????????
     *
     * @param bdcQl ????????????
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public int insertBdcQl(BdcQl bdcQl) {
        int num = 0;
        if (bdcQl != null) {
            if (StringUtils.isBlank(bdcQl.getQlid())) {
                bdcQl.setQlid(UUIDGenerator.generate16());
            }
            num = entityMapper.insertSelective(bdcQl);
        }
        return num;
    }

    /**
     * ??????????????????????????????
     *
     * @param bdcQl ????????????
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public void insertOrUpdateBdcQl(BdcQl bdcQl) {
        if (bdcQl != null) {
            if (StringUtils.isNotBlank(bdcQl.getQlid())) {
                updateBdcQl(bdcQl);
            } else {
                insertBdcQl(bdcQl);
            }
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param obj ????????????
     * @param id  id
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public int updateObj(Object obj, String id) {
        int num;
        if (obj != null && StringUtils.isNotBlank(id)) {
            num = entityMapper.updateByPrimaryKeyNull(obj);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return num;
    }

    /**
     * ??????map???????????? bdcQl
     * @param bdcQl
     * @param map
     * @param orderBy ????????????
     * @return
     */
    @Override
    public List<BdcQl> andEqualQueryBdcQl(BdcQl bdcQl, Map<String, Object> map, String orderBy) {
        List<BdcQl> list = null;
        if (bdcQl != null) {
            Example qllxTemp = new Example(bdcQl.getClass());
            if (MapUtils.isNotEmpty(map)) {
                Iterator iter = map.entrySet().iterator();
                Example.Criteria criteria = qllxTemp.createCriteria();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    if (val != null) {
                        criteria.andEqualTo(key.toString(), val);
                    }
                }
            }
            if (StringUtils.isNotBlank(orderBy)) {
                qllxTemp.setOrderByClause(orderBy);
            }
            if(CollectionUtils.isNotEmpty(qllxTemp.getOredCriteria()) && CollectionUtils.isNotEmpty(qllxTemp.getOredCriteria().get(0).getAllCriteria())){
                list = (List<BdcQl>) entityMapper.selectByExample(bdcQl.getClass(), qllxTemp);
            }
        }
        return list;
    }

    /**
     * ????????????????????????
     * @param xmid ??????id
     * @param fjnr ????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public void updateQlFj(String xmid, String fjnr) {
        BdcQl bdcQl = queryQllxDO(xmid);
        if (bdcQl != null) {
            bdcQl.setFj(fjnr);
            //????????????
            updateBdcQl(bdcQl);
        }
    }

    @Override
    public int updateBatchBdcQl(BdcDjxxUpdateQO bdcDjxxUpdateQO) throws ClassNotFoundException{
        if (bdcDjxxUpdateQO ==null ||StringUtils.isAnyBlank(bdcDjxxUpdateQO.getJsonStr(),bdcDjxxUpdateQO.getClassName()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            LOGGER.info("???????????????????????????{}", bdcDjxxUpdateQO != null ?JSONObject.toJSONString(bdcDjxxUpdateQO) :"");
            throw new NullPointerException("??????????????????");
        }
        String jsonStr = bdcDjxxUpdateQO.getJsonStr();
        //????????????json??????
        JSONObject jsonObject = JSON.parseObject(bdcDjxxUpdateQO.getJsonStr());
        // ??????????????????????????????????????????
        if (jsonObject.containsKey(CommonConstantUtils.ADD_FJ)) {
            jsonObject.put("fj", jsonObject.get(CommonConstantUtils.ADD_FJ).toString());
            jsonStr = JSON.toJSONString(jsonObject);
        }
        Map map = new HashMap();
        //????????????????????????
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, bdcDjxxUpdateQO.getClassName());
        if(!statement.contains("SET")) {
            return 0;
        }
        if (jsonObject.containsKey(CommonConstantUtils.ADD_FJ) && StringUtils.indexOf(statement, "FJ=") > -1) {
            statement = StringUtils.replace(statement, "FJ=", "FJ=FJ||CHR(13)||");
        }
        map.put("statement", statement);
        //where ??????
        map.putAll(bdcDjxxUpdateQO.getWhereMap());
        //??????????????????
        Object object = JSON.parseObject(jsonStr, Class.forName(bdcDjxxUpdateQO.getClassName()));
        map.put("record", object);
        return bdcQlMapper.updateBatchBdcQl(map);
    }

    /**
     * @param bdcQl ????????????
     * @return ???????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ????????????????????????????????????
     */
    @Override
    public int updateBdcZsQlFj(BdcQl bdcQl) {
        int result = 0;
        String xmid = bdcQl.getXmid();
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        Map<String, Object> whereMap = new HashMap();
        bdcDjxxUpdateQO.setWhereMap(whereMap);
        bdcDjxxUpdateQO.setClassName(bdcQl.getClass().getName());
        try {
            BdcQl bdcQlTemp = bdcQl.getClass().newInstance();
            bdcQlTemp.setFj(bdcQl.getFj());
            bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(bdcQlTemp));

            List<String> zsXmlist = bdcXmService.queryZsxmList(xmid);
            if (CollectionUtils.isEmpty(zsXmlist)) {
                // ???????????????????????????????????????????????????????????????
                zsXmlist = new ArrayList();
                zsXmlist.add(xmid);
            }
            whereMap.put(Constants.XMIDS, zsXmlist);
            result = this.updateBatchBdcQl(bdcDjxxUpdateQO);
        } catch (InstantiationException e) {
            result = -1;
            LOGGER.error("??????????????????", e);
        } catch (IllegalAccessException e) {
            result = -1;
            LOGGER.error("???????????????", e);
        } catch (ClassNotFoundException e) {
            result = -1;
            LOGGER.error("???????????????????????????????????????");
        }
        return result;
    }

    /**
     * @param json ?????????json ?????????
     * @return int ??????????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????json???????????????????????????????????????????????????
     */
    @Override
    public int updateBdcZsQlFj(String json) {
        if (StringUtils.isBlank(json)) {
            throw new MissingArgumentException("updateQlqtzkAndFj??????????????????Json?????????");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject.containsKey(Constants.XMID) && jsonObject.containsKey(Constants.FJ)) {
            String xmid = jsonObject.getString(Constants.XMID);
            String fj = jsonObject.getString(Constants.FJ);

            BdcQl bdcQl = this.queryQllxDO(xmid);
            //?????????????????????????????????
            if(Objects.nonNull(bdcQl) && StringUtils.isNotBlank(bdcQl.getQlid())) {
                bdcQl.setFj(fj);
                return this.updateBdcZsQlFj(bdcQl);
            }
        }
        LOGGER.debug("??????xmid???fj!");
        return -1;
    }

    /**
     * ??????xmid ??????????????????
     * @param xmid
     * @return
     */
    public String getGhytByXmid(String xmid) {
        if(StringUtils.isNotBlank(xmid)){
            BdcQl bdcql = this.queryQllxDO(xmid);
            if(null != bdcql && bdcql instanceof BdcFdcqDO){
                BdcFdcqDO bdcFdcqDO = (BdcFdcqDO)bdcql;
                return bdcFdcqDO.getGhyt()+"";
            }else{
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(xmid);
                List<BdcXmDO> list = bdcXmService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isNotEmpty(list)){
                    return list.get(0).getDzwyt()+"";
                }else{
                    return "";
                }
            }
        }else{
            return "";
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQl ????????????
     * @return {FwHsDO} ??????????????????
     * @description ?????????????????????????????????????????????????????????
     */
    @Override
    public FwHsDO queryFwHsXx(BdcQl bdcQl) {
        if(null == bdcQl || StringUtils.isBlank(bdcQl.getQlid())) {
            return null;
        }

        String bdcdyh = "";
        if(bdcQl instanceof BdcFdcqDO) {
            bdcdyh = ((BdcFdcqDO) bdcQl).getBdcdyh();
        }
        if(bdcQl instanceof BdcFdcq3DO) {
            bdcdyh = ((BdcFdcq3DO) bdcQl).getBdcdyh();
        }

        if(StringUtils.isBlank(bdcdyh)) {
            return null;
        }
        return FwHsFeignService.queryFwhsByBdcdyh(bdcdyh,"");
    }

    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ??????????????????bdbzzqse???zgzqse??????
     */
    @Override
    public void updateBdcDyaqZgzqse(String processInsId) {
        List<BdcQl> bdcQlList = listQlxxDO("",processInsId);
        if(CollectionUtils.isNotEmpty(bdcQlList)){
            for(BdcQl bdcQl : bdcQlList){
                if(bdcQl instanceof BdcDyaqDO){
                    BdcDyaqDO bdcDyaqDO = (BdcDyaqDO)bdcQl;
                    if (CommonConstantUtils.DYFS_ZGEDY.equals(bdcDyaqDO.getDyfs()) && null ==bdcDyaqDO.getZgzqqdse()) {
                        bdcDyaqDO.setZgzqqdse(bdcDyaqDO.getBdbzzqse());
                        updateBdcQl(bdcDyaqDO);
                    }
                }
            }
        }
    }

    /**
     * @author  <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param xmids
     * @return  {List} ????????????
     * @description ??????????????????????????????????????????
     */
    @Override
    public List<BdcQl> listXzxxByXmids(List<String> xmids) {
        if(CollectionUtils.isEmpty(xmids)){
            return Lists.newArrayList();
        }
        List<BdcQl> bdcQls = new ArrayList<>();
//        "fdcqList",
        Example fdcqExample = new Example(BdcFdcqDO.class);
        fdcqExample.createCriteria().andInWithListString("xmid",xmids).andEqualTo("qszt",CommonConstantUtils.QSZT_VALID);
        List<BdcQl> fdcqList = entityMapper.selectByExample(fdcqExample);
        if(CollectionUtils.isNotEmpty(fdcqList)){
            bdcQls.addAll(fdcqList);
        }

//        "gjzwsyqList",
        Example gjzwsyqExample = new Example(BdcGjzwsyqDO.class);
        gjzwsyqExample.createCriteria().andInWithListString("xmid",xmids).andEqualTo("qszt",CommonConstantUtils.QSZT_VALID);
        List<BdcQl> gjzwsyqList = entityMapper.selectByExample(gjzwsyqExample);
        if(CollectionUtils.isNotEmpty(gjzwsyqList)){
            bdcQls.addAll(gjzwsyqList);
        }

//        "hysyqList",
        Example hysyqExample = new Example(BdcHysyqDO.class);
        hysyqExample.createCriteria().andInWithListString("xmid",xmids).andEqualTo("qszt",CommonConstantUtils.QSZT_VALID);
        List<BdcQl> hysyqList = entityMapper.selectByExample(hysyqExample);
        if(CollectionUtils.isNotEmpty(hysyqList)){
            bdcQls.addAll(hysyqList);
        }

//        "jsydsyqList",
        Example jsydsyqExample = new Example(BdcJsydsyqDO.class);
        jsydsyqExample.createCriteria().andInWithListString("xmid",xmids).andEqualTo("qszt",CommonConstantUtils.QSZT_VALID);
        List<BdcQl> jsydsyqList = entityMapper.selectByExample(jsydsyqExample);
        if(CollectionUtils.isNotEmpty(jsydsyqList)){
            bdcQls.addAll(jsydsyqList);
        }

//        "lqList",
        Example lqExample = new Example(BdcLqDO.class);
        lqExample.createCriteria().andInWithListString("xmid",xmids).andEqualTo("qszt",CommonConstantUtils.QSZT_VALID);
        List<BdcQl> lqList = entityMapper.selectByExample(lqExample);
        if(CollectionUtils.isNotEmpty(lqList)){
            bdcQls.addAll(lqList);
        }

//        "tdsyqList"
        Example tdsyqExample = new Example(BdcTdsyqDO.class);
        tdsyqExample.createCriteria().andInWithListString("xmid",xmids).andEqualTo("qszt",CommonConstantUtils.QSZT_VALID);
        List<BdcQl> tdsyqList = entityMapper.selectByExample(tdsyqExample);
        if(CollectionUtils.isNotEmpty(tdsyqList)){
            bdcQls.addAll(tdsyqList);
        }


        return bdcQls;
    }

    /**
     * @author  <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param   bdcdyhList ??????ID
     * @return  {List} ????????????
     * @description ??????????????????????????????????????????
     */
    @Override
    public List<BdcQl> listXzxxByBdcdyhs(List<String> bdcdyhList) {
        if(CollectionUtils.isEmpty(bdcdyhList)){
            return Lists.newArrayList();
        }
        List<BdcQl> bdcQls = new ArrayList<>();

        // ??????
        List<BdcDyaqDO> bdcDyaqDOS = bdcDyafeignService.listBdcDyaqByBdcdyhs(bdcdyhList);
        if(CollectionUtils.isNotEmpty(bdcDyaqDOS)){
            bdcDyaqDOS = bdcDyaqDOS.stream().filter(x -> 1== x.getQszt()).collect(Collectors.toList());
            bdcQls.addAll(bdcDyaqDOS);
        }
        // ??????
        List<BdcYgDO> bdcYgDOS = bdcYgFeignService.listBdcYgByBdcdyhs(bdcdyhList);
        if(CollectionUtils.isNotEmpty(bdcYgDOS)){
            bdcYgDOS = bdcYgDOS.stream().filter(x -> 1== x.getQszt()).collect(Collectors.toList());
            bdcQls.addAll(bdcYgDOS);
        }
        // ??????
        List<BdcYyDO> bdcYyDOS = bdcYyXxCxFeignService.listBdcYyByBdcdyhs(bdcdyhList);
        if(CollectionUtils.isNotEmpty(bdcYyDOS)){
            bdcYyDOS = bdcYyDOS.stream().filter(x -> 1== x.getQszt()).collect(Collectors.toList());
            bdcQls.addAll(bdcYyDOS);
        }
        // ??????
        List<BdcCfDO> bdcCfDOS = bdcCfxxFeignService.listBdcCfByBdcdyhs(bdcdyhList);
        if(CollectionUtils.isNotEmpty(bdcCfDOS)){
            bdcCfDOS = bdcCfDOS.stream().filter(x -> 1== x.getQszt()).collect(Collectors.toList());
            bdcQls.addAll(bdcCfDOS);
        }

        return bdcQls;
    }

    /**
     * ?????????????????????????????????
     * ???????????????+???????????????????????????????????????????????????+???????????????????????????????????????
     *
     * @param bdcQlList bdcQlList
     * @param gzldyid   gzldyid
     * @return -1 ???????????????
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public int updateZjjwdyfw(List<BdcQl> bdcQlList, String gzldyid,String qjgldm) {
        if (CollectionUtils.isEmpty(zjjzwdyas) || CollectionUtils.isEmpty(bdcQlList) || !zjjzwdyas.contains(gzldyid)) {
            return -1;
        }
        StringJoiner zjjzwdyfw = new StringJoiner("\n");
        StringJoiner fjhSj = new StringJoiner("???");
        for (BdcQl bdcQl : bdcQlList) {
            if (bdcQl instanceof BdcDyaqDO) {
                BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                if (StringUtils.isNotBlank(bdcDyaqDO.getFjh())) {
                    fjhSj.add(bdcDyaqDO.getFjh());
                }
                if (bdcDyaqDO.getDybdclx() != null && bdcDyaqDO.getDybdclx().equals(CommonConstantUtils.DYBDCLX_CTD)) {
                    if(StringUtils.isNotBlank(bdcDyaqDO.getBdcdyh())) {
                        // ???????????????????????????????????????
                        FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByBdcdyh(bdcDyaqDO.getBdcdyh().substring(0, 19),qjgldm);
                        if(null != fwLjzDO && StringUtils.isNotBlank(fwLjzDO.getZldz())) {
                            zjjzwdyfw.add(fwLjzDO.getZldz());
                        }
                    } else {
                        zjjzwdyfw.add(bdcDyaqDO.getZl());
                    }
                }
            }
        }

        if (StringUtils.isBlank(fjhSj.toString())) {
            return 0;
        }

        zjjzwdyfw.merge(fjhSj).add("??????????????????????????????????????????");

        int result = 0;
        for (BdcQl bdcQl : bdcQlList) {
            if (bdcQl instanceof BdcDyaqDO) {
                BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                bdcDyaqDO.setZjjzwdyfw(zjjzwdyfw.toString());
                result += updateBdcQl(bdcQl);
            }
        }
        return result;
    }

    @Override
    public Map queryFdcqByZl(String zl) {
        Map map = new HashMap();
        map.put("zl",zl);
        return bdcQlMapper.queryFdcqByZl(map);
    }

    /**
     * ???????????????????????????
     * @param bdcQlxx ????????????
     * @return List<BdcQl> ????????????
     */
    @Override
    public List<BdcQl> getBdcQlxxByQlr(BdcQlxxRequestDTO bdcQlxx){
        if(StringToolUtils.isAllNullOrEmpty(bdcQlxx.getQlrmc(), bdcQlxx.getQlrzjh(), bdcQlxx.getBdcdyh(), bdcQlxx.getBdcqzh())) {
            throw new MissingArgumentException("??????????????????");
        }
        List<BdcXmDO> xmList =  bdcQlMapper.listBdcXmxxByQlr(bdcQlxx);

        List<BdcQl> list =  new ArrayList<>();
        // ?????????????????????listQlxxDOByXmids?????????????????? 1*N??? ??????
        for(String qllx : bdcQlxx.getQllxs()){
            // ????????????????????????
            BdcQl bdcQl = makeSureQllx(qllx);
            // ??????????????????
            for (BdcXmDO xm : xmList) {
                if (String.valueOf(xm.getQllx()).equals(qllx)) {
                    if (bdcQl != null && StringUtils.isNotBlank(xm.getXmid())) {
                        bdcQl = queryQllxDO(bdcQl, xm.getXmid());
                    }

                    list.add(bdcQl);
                }
            }
        }
        return list;
    }


}
