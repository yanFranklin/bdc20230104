package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcCqBgLsDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxModelDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxXzqlModelDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.service.*;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 历史关系服务
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0
 * @description
 */
@Service
@Validated
public class LsgxModelServiceImpl implements LsgxModelService{

    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;
    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    BdcSdFeignService bdcSdFeignService;
    @Autowired
    BdcZsService bdcZsService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;

    private static final Logger logger = LoggerFactory.getLogger(LsgxModelServiceImpl.class);

    /**
     * 通过proid获得同不动产单元id的所有不动产产权
     *
     * @param bdcdyh 不动产单元号
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Override
    public List<LsgxModelDTO> getBdcCqLsgxListByBdcdyh(@NotBlank(message = "参数不能为空") String bdcdyh) throws ReflectiveOperationException{
        List<LsgxModelDTO> bdcCqList = new ArrayList(50);
        //根据bdcdyh查询所有不动产产权
        HashMap bdcdyMap = new HashMap(2);
        bdcdyMap.put(Constants.BDCDYH,bdcdyh);
        //先查询没有对应产权的预查封数据
        addYcfXxToList(bdcCqList,bdcdyh);
        logger.info("预查封数据为:" + JSONObject.toJSONString(bdcCqList));
        //先查询没有对应产权的预告数据
        addCqXxToList(bdcCqList,bdcdyh,new BdcYgDO());
        logger.info("没有对应产权的预告数据为:" + JSONObject.toJSONString(bdcCqList));
        //循环可以生成证书的权利,根据不动产单元进行查询
        for (Class qlCalss : Constants.BDCQZS_CLASS){
            if (qlCalss != null){
                BdcQl cqBdcQl = (BdcQl) qlCalss.newInstance();
                //此处去掉判断，循环处理
                addCqXxToList(bdcCqList,bdcdyh,cqBdcQl);
                logger.info("循环可以生成证书的权利数据为:" + JSONObject.toJSONString(bdcCqList));
            }
        }
        return bdcCqList;
    }

    /**
     * @param bdcQl
     * @param bdcXm
     * @param bdcXmLsgxDO
     * @return LsgxModelDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据项目信息 初始化LsgxModel
     */
    @Override
    public LsgxModelDTO initLsgxModel(BdcXmDO bdcXm,BdcQl bdcQl,BdcXmLsgxDO bdcXmLsgxDO){
        LsgxModelDTO lsgxModel = new LsgxModelDTO();
        if (bdcXm != null){
            lsgxModel.setBdcXmDO(bdcXm);
            lsgxModel.setId(bdcXm.getXmid());
            lsgxModel.setBdcdyfwlx(bdcXm.getBdcdyfwlx());
            if (bdcXmLsgxDO != null && StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())){
                lsgxModel.setYid(bdcXmLsgxDO.getYxmid());
            }
            lsgxModel.setQllx(bdcXm.getQllx());
            lsgxModel.setCjsj(bdcXm.getSlsj());
            lsgxModel.setBdcqzh(bdcXm.getBdcqzh());
            lsgxModel.setDjlx(bdcXm.getDjlx());

            if (bdcQl != null){
                lsgxModel.setBdcQl(bdcQl);
                lsgxModel.setDjsj(bdcQl.getDjsj());
                lsgxModel.setDbr(bdcQl.getDbr());
                lsgxModel.setQszt(bdcQl.getQszt());
                if (bdcQl instanceof BdcCfDO){
                    lsgxModel.setBdcqzh(((BdcCfDO) bdcQl).getCfwh());
                }
                lsgxModel.setQlxx(JSONObject.toJSONString(bdcQl));
            }
        }
        return lsgxModel;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 添加锁定信息到权利历史限制信息中
     */
    private List<LsgxModelDTO> addSdToXzqlModel(String xmid){
        List<LsgxModelDTO> sdLsgxModel = new ArrayList<>();
        List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcZsDOList)){
            for (BdcZsDO zsDO : bdcZsDOList){
                if (StringUtils.isNotBlank(zsDO.getZsid()) && StringUtils.isNotBlank(zsDO.getBdcqzh())){
                    BdcZssdDO zssdQO = new BdcZssdDO();
                    zssdQO.setCqzh(zsDO.getBdcqzh());
                    zssdQO.setZsid(zsDO.getZsid());
                    List<BdcZssdDO> bdcZssdDOList = bdcSdFeignService.queryBdczsSd(zssdQO);
                    if (CollectionUtils.isNotEmpty(bdcZssdDOList)){
                        for (BdcZssdDO zssdDO : bdcZssdDOList){
                            sdLsgxModel.add((initSdModel(zssdDO)));
                        }
                    }
                }
            }
        }
        return sdLsgxModel;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化证书锁定LsgxModel
     */
    private LsgxModelDTO initSdModel(BdcZssdDO bdcZssdDO){
        LsgxModelDTO lsgxModelDTO = null;
        if (bdcZssdDO != null){
            lsgxModelDTO = new LsgxModelDTO();
            lsgxModelDTO.setId(bdcZssdDO.getZssdid());
            lsgxModelDTO.setSdzt(bdcZssdDO.getSdzt());
            lsgxModelDTO.setDjsj(bdcZssdDO.getSdsj());
            lsgxModelDTO.setBdcqzh(bdcZssdDO.getSdsqwh());
        }
        return lsgxModelDTO;

    }

    /**
     * @param all
     * @param xmid
     * @return LsgxXzqlModelDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据项目id获取下层的限制权利
     */
    @Override
    public LsgxXzqlModelDTO initLsgxXzqlModel(@NotBlank(message = "参数不能为空") String xmid,boolean all,Integer wlxm){
        LsgxXzqlModelDTO lsgxXzqlModelDTO = new LsgxXzqlModelDTO();
        boolean hasXzql = false;
        //根据xmrel向下找
        List<BdcXmLsgxDO> bdcXmLsgxList = null;
        if (all){
            bdcXmLsgxList = bdcXmLsgxService.nextBdcXmLsgx(xmid,null,wlxm,false);
        }else{
            bdcXmLsgxList = bdcXmLsgxService.queryBdcXmNextLsgxByXmid(xmid,"b.slsj asc",wlxm);
        }
        if (CollectionUtils.isNotEmpty(bdcXmLsgxList)){
            Class[] qllxs = new Class[]{BdcJzqDO.class,BdcCfDO.class,BdcDyaqDO.class,BdcYyDO.class,BdcYgDO.class,BdcDyiqDO.class};
            for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxList){
                if (bdcXmLsgxDO != null){
                    BdcXmDO bdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class,bdcXmLsgxDO.getXmid());
                    if (bdcXm != null){
                        BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXm.getXmid());
                        if (bdcQl != null){
                            for (Class qllx : qllxs){
                                if (qllx.equals(bdcQl.getClass())){
                                    hasXzql = true;
                                    lsgxXzqlModelDTO.getModelByQllx(qllx.getSimpleName()).add(initLsgxModel(bdcXm,bdcQl,bdcXmLsgxDO));
                                }
                            }
                        }
                    }
                }
            }
        }
        //查询锁定
        List<LsgxModelDTO> sdLsgxModel = addSdToXzqlModel(xmid);
        if (CollectionUtils.isNotEmpty(sdLsgxModel)){
            hasXzql = true;
            lsgxXzqlModelDTO.setSdLsgxModel(sdLsgxModel);
        }

        if (hasXzql){
            return lsgxXzqlModelDTO;
        }
        return null;
    }

    /**
     * 根据不动产单元号查询 是否有产权或预告信息 进行查询
     *
     * @param bdcCqList
     * @param bdcdyh
     * @param bdcQl
     * @return
     */
    private Boolean addCqXxToList(List<LsgxModelDTO> bdcCqList,String bdcdyh,BdcQl bdcQl){
        Boolean hasThisCq = false;
        if (bdcCqList == null){
            bdcCqList = new ArrayList(50);
        }
        if (bdcQl != null && StringUtils.isNotBlank(bdcdyh)){
            HashMap bdcdyMap = new HashMap(2);
            bdcdyMap.put(Constants.BDCDYH,bdcdyh);
            List<BdcQl> cqlist = bdcQllxService.andEqualQueryBdcQl(bdcQl,bdcdyMap,"djsj,qszt desc");
            logger.info("不动产预告数据查询:" + JSONObject.toJSONString(cqlist));
            if (CollectionUtils.isNotEmpty(cqlist)){
                hasThisCq = true;
                //一个不动产单元肯定只有一种权利的产权，所以不用验证别的了
                for (BdcQl qllxVoTemp : cqlist){
                    BdcXmDO bdcxm = bdcXmService.queryBdcXmByPrimaryKey(qllxVoTemp.getXmid());
                    if(bdcxm ==null){
                        throw new AppException("当前权利未查到到项目数据,请检查数据,项目ID:"+qllxVoTemp.getXmid());
                    }
                    if (bdcQl instanceof BdcYgDO){
                        //预告登记种类为预告*的和产权并列
                        if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, ((BdcYgDO) qllxVoTemp).getYgdjzl())) {
                            bdcCqList.add(initLsgxModel(bdcxm,qllxVoTemp,null));
                        }

                    }else{
                        //产权不用xmrel填写yid了 直接排序就行了
                        bdcCqList.add(initLsgxModel(bdcxm,qllxVoTemp,null));
                        // 查询产权是否有预告  new BdcYgDO()
                        Example example = new Example(BdcYgDO.class);
                        example.createCriteria().andEqualTo("xmid",bdcxm.getXmid());
                        List<BdcYgDO> list = entityMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(list)){
                            bdcCqList.add(initLsgxModel(bdcxm,list.get(0),null));
                        }
                    }
                }
            }
        }
        return hasThisCq;
    }


    /**
     * 根据不动产单元号查询 预查封 进行查询
     *
     * @param bdcCqList
     * @param bdcdyh
     * @return
     */
    private List<LsgxModelDTO> addYcfXxToList(List<LsgxModelDTO> bdcCqList,String bdcdyh){
        if (bdcCqList == null){
            bdcCqList = new ArrayList(50);
        }
        if (StringUtils.isNotBlank(bdcdyh)){
            //获取预查封和轮候预查封数据
            Example example = new Example(BdcCfDO.class);
            example.setOrderByClause("djsj,qszt desc");
            example.createCriteria().andEqualTo(Constants.BDCDYH,bdcdyh).andIn("cflx",Arrays.asList(CommonConstantUtils.CFLX_YCF,CommonConstantUtils.CFLX_LHYCF));
            List<BdcCfDO> cqlist = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(cqlist)){
                //一个不动产单元肯定只有一种权利的产权，所以不用验证别的了
                for (BdcCfDO qllxVoTemp : cqlist){
                    BdcXmDO bdcxm = bdcXmService.queryBdcXmByPrimaryKey(qllxVoTemp.getXmid());
                    bdcCqList.add(initLsgxModel(bdcxm,qllxVoTemp,null));
                }
            }
        }
        return bdcCqList;
    }

    @Override
    public List<BdcCqBgLsDTO> listBdcCqBgLs(String bdcdyh){
        List<BdcCqBgLsDTO> bdcCqBgLsDTOList = new ArrayList(50);
        Map<String,BdcCqBgLsDTO> cqBgLsDTOMap =new HashMap<>();
        if(StringUtils.isNotBlank(bdcdyh)){
            //查询产权项目
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setBdcdyh(bdcdyh);
            List<BdcXmDO> xmList =bdcXmService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(xmList)){
                xmList = xmList.stream().filter(xm->!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, xm.getQllx()) &&!CommonConstantUtils.QSZT_TEMPORY.equals(xm.getQszt())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(xmList)){
                    List<Map> qllxZdMap = bdcZdFeignService.queryBdcZd("qllx");
                    BdcXmDO bdcXmDO =xmList.get(0);
                    //向上递归
                    listPrevCqBgLsDTO(bdcXmDO,cqBgLsDTOMap,0,new HashSet<>());
                    //向下递归
                    listNextCqBgLsDTO(bdcXmDO,cqBgLsDTOMap,0,new HashSet<>());
                    //level重新排序
                    if(MapUtils.isNotEmpty(cqBgLsDTOMap)){
                        for (Map.Entry<String, BdcCqBgLsDTO> entry : cqBgLsDTOMap.entrySet()) {
                            bdcCqBgLsDTOList.add(entry.getValue());
                        }
                    }
                    bdcCqBgLsDTOList.sort(Comparator.comparing(BdcCqBgLsDTO::getLevel));
                    int maxlevel =bdcCqBgLsDTOList.get(bdcCqBgLsDTOList.size()-1).getLevel();
                    for(BdcCqBgLsDTO bdcCqBgLsDTO:bdcCqBgLsDTOList){
                        bdcCqBgLsDTO.setLevel(maxlevel -bdcCqBgLsDTO.getLevel());
                        bdcCqBgLsDTO.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcCqBgLsDTO.getQllx(), qllxZdMap));
                    }
                }

            }
        }
        return bdcCqBgLsDTOList;

    }

    @Override
    public List<LsgxModelDTO> initLsgxCqModel(@NotBlank(message = "参数不能为空")String xmid, boolean all,Integer wlxm){
        List<LsgxModelDTO> bdcCqList = new ArrayList(50);
        //当前产权
        if(StringUtils.isNotBlank(xmid)) {
            BdcXmDO bdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            if(bdcXm != null) {
                if(!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXm.getQllx())) {
                    BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXm.getXmid());
                    bdcCqList.add(initLsgxModel(bdcXm, bdcQl, null));
                }else if(CommonConstantUtils.QLLX_YG_DM.equals(bdcXm.getQllx())){
                    BdcQl bdcQl =bdcQllxService.queryQllxDO(bdcXm.getXmid());
                    if(bdcQl instanceof BdcYgDO){
                        BdcYgDO bdcYgDO =(BdcYgDO) bdcQl;
                        if(ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR,bdcYgDO.getYgdjzl())){
                            //预告作为产权
                            bdcCqList.add(initLsgxModel(bdcXm, bdcQl, null));
                        }
                    }
                }
                //根据项目历史向上找
                bdcCqList.addAll(initPrevLsgxCqModel(xmid, all, wlxm));
                //根据项目历史向下找
                bdcCqList.addAll(initNextLsgxCqModel(xmid, all, wlxm));
            }
        }

        return bdcCqList;
    }

    @Override
    public LsgxXzqlModelDTO initPrevNextLsgxXzqlModel(@NotBlank(message = "参数不能为空")String xmid, String type,Integer wlxm){
        LsgxXzqlModelDTO lsgxXzqlModelDTO =new LsgxXzqlModelDTO();
        if(StringUtils.isNotBlank(xmid)) {
            if(StringUtils.equals("all",type)) {
                BdcXmDO bdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
                if(bdcXm != null &&ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXm.getQllx())) {
                    BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXm.getXmid());
                    if(bdcQl != null) {
                        lsgxXzqlModelDTO.getModelByQllx(bdcQl.getClass().getSimpleName()).add(initLsgxModel(bdcXm, bdcQl, null));
                    }
                }
                //根据项目历史向上找
                initPrevLsgxXzqlModel(xmid, wlxm, lsgxXzqlModelDTO);
            }
            //根据项目历史向下找
            initNextLsgxXzqlModel(xmid, wlxm, lsgxXzqlModelDTO);
        }
        return lsgxXzqlModelDTO;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据项目历史向上找获取产权
     */
    private List<LsgxModelDTO> initPrevLsgxCqModel(@NotBlank(message = "参数不能为空")String xmid, boolean all,Integer wlxm){
        List<LsgxModelDTO> bdcCqList = new ArrayList(50);
        //根据项目历史向上找
        List<BdcXmLsgxDO> bdcXmLsgxList;
        if (all){
            bdcXmLsgxList = bdcXmLsgxService.prevBdcXmLsgx(xmid,null,wlxm,true);
        }else{
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(xmid);
            bdcXmLsgxQO.setWlxm(wlxm);
            bdcXmLsgxList = bdcXmLsgxService.listBdcXmLsgxs(bdcXmLsgxQO);
        }
        if (CollectionUtils.isNotEmpty(bdcXmLsgxList)){
            for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxList){
                if (bdcXmLsgxDO != null &&StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())){
                    BdcXmDO bdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class,bdcXmLsgxDO.getYxmid());
                    if (bdcXm != null){
                        if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXm.getQllx()) ||CommonConstantUtils.QLLX_YG_DM.equals(bdcXm.getQllx())){
                            BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXm.getXmid());
                            if(bdcQl instanceof BdcYgDO){
                                BdcYgDO bdcYgDO =(BdcYgDO) bdcQl;
                                //预告特殊处理,预告计入产权,预抵押不计入产权
                                if(ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR,bdcYgDO.getYgdjzl())){
                                    bdcCqList.add(initLsgxModel(bdcXm,bdcQl,null));
                                }
                            }else if(bdcQl !=null){
                                bdcCqList.add(initLsgxModel(bdcXm, bdcQl, null));
                            }
                        }
                    }
                }
            }
        }
        return bdcCqList;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据项目历史向下找获取产权(包含注销项目)
     */
    private List<LsgxModelDTO> initNextLsgxCqModel(@NotBlank(message = "参数不能为空")String xmid, boolean all,Integer wlxm){
        List<LsgxModelDTO> bdcCqList = new ArrayList(50);
        List<BdcXmLsgxDO> bdcXmLsgxList;
        if (all){
            bdcXmLsgxList = bdcXmLsgxService.nextBdcXmLsgx(xmid,null,wlxm,true);
        }else{
            bdcXmLsgxList = bdcXmLsgxService.queryBdcXmNextLsgxByXmid(xmid,"b.slsj asc",wlxm);
        }
        if (CollectionUtils.isNotEmpty(bdcXmLsgxList)){
            for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxList){
                if (bdcXmLsgxDO != null &&StringUtils.isNotBlank(bdcXmLsgxDO.getXmid())){
                    BdcXmDO bdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class,bdcXmLsgxDO.getXmid());
                    if (bdcXm != null){
                        if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXm.getQllx()) ||CommonConstantUtils.QLLX_YG_DM.equals(bdcXm.getQllx())){
                            BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXm.getXmid());
                            boolean zxql =false;
                            if(bdcQl ==null){
                                List<BdcQl> bdcQlList=bdcQllxService.queryBdcYqlxxs(bdcXm.getXmid());
                                if(CollectionUtils.isNotEmpty(bdcQlList)){
                                    bdcQl =bdcQlList.get(0);
                                }
                                zxql =true;
                            }
                            if(bdcQl instanceof BdcYgDO){
                                BdcYgDO bdcYgDO =(BdcYgDO) bdcQl;
                                //预告特殊处理,预告计入产权,预抵押不计入产权
                                if(ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR,bdcYgDO.getYgdjzl())){
                                    bdcCqList.add(initLsgxModel(bdcXm,zxql?null:bdcQl,null));
                                }
                            }else if(bdcQl != null){
                                bdcCqList.add(initLsgxModel(bdcXm, zxql?null:bdcQl, null));
                            }
                        }
                    }
                }
            }
        }
        return bdcCqList;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据项目历史向上找获取限制权利
     */
    private LsgxXzqlModelDTO initPrevLsgxXzqlModel(@NotBlank(message = "参数不能为空")String xmid,Integer wlxm,LsgxXzqlModelDTO lsgxXzqlModelDTO){
        //根据项目历史向上找
        List<BdcXmLsgxDO> bdcXmLsgxList = bdcXmLsgxService.prevBdcXmLsgx(xmid,null,wlxm,false);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxList)){
            for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxList){
                if (bdcXmLsgxDO != null &&StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())){
                    BdcXmDO bdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class,bdcXmLsgxDO.getYxmid());
                    if (bdcXm != null){
                        if (ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXm.getQllx())){
                            BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXm.getXmid());
                            if(bdcQl instanceof BdcYgDO){
                                BdcYgDO bdcYgDO =(BdcYgDO) bdcQl;
                                //预告特殊处理,预告计入产权,预抵押不计入产权
                                if(!ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR,bdcYgDO.getYgdjzl())){
                                    lsgxXzqlModelDTO.getModelByQllx(bdcQl.getClass().getSimpleName()).add(initLsgxModel(bdcXm,bdcQl,null));
                                }
                            }else if(bdcQl !=null){
                                lsgxXzqlModelDTO.getModelByQllx(bdcQl.getClass().getSimpleName()).add(initLsgxModel(bdcXm,bdcQl,null));
                            }
                        }
                    }
                }
            }
        }
        return lsgxXzqlModelDTO;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据项目历史向下找获取限制权利(包含注销项目)
     */
    private LsgxXzqlModelDTO initNextLsgxXzqlModel(@NotBlank(message = "参数不能为空")String xmid,Integer wlxm,LsgxXzqlModelDTO lsgxXzqlModelDTO){
        List<BdcXmLsgxDO> bdcXmLsgxList = bdcXmLsgxService.nextBdcXmLsgx(xmid,null,wlxm,false);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxList)){
            for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxList){
                if (bdcXmLsgxDO != null &&StringUtils.isNotBlank(bdcXmLsgxDO.getXmid())){
                    BdcXmDO bdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class,bdcXmLsgxDO.getXmid());
                    if (bdcXm != null){
                        if (ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXm.getQllx())){
                            BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXm.getXmid());
                            boolean zxql =false;
                            if(bdcQl ==null){
                                List<BdcQl> bdcQlList =bdcQllxService.queryBdcYqlxxs(bdcXm.getXmid());
                                if(CollectionUtils.isNotEmpty(bdcQlList)){
                                    bdcQl =bdcQlList.get(0);
                                }
                                zxql =true;
                            }
                            if(bdcQl instanceof BdcYgDO){
                                BdcYgDO bdcYgDO =(BdcYgDO) bdcQl;
                                //预告特殊处理,预告计入产权,预抵押不计入产权
                                if(!ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR,bdcYgDO.getYgdjzl())){
                                    List<LsgxModelDTO> lsgxModelDTOList =lsgxXzqlModelDTO.getModelByQllx(bdcQl.getClass().getSimpleName());
                                    if(lsgxModelDTOList != null) {
                                        lsgxXzqlModelDTO.getModelByQllx(bdcQl.getClass().getSimpleName()).add(initLsgxModel(bdcXm, zxql ? null : bdcQl, bdcXmLsgxDO));
                                    }
                                }
                            }else if(bdcQl != null){
                                List<LsgxModelDTO> lsgxModelDTOList =lsgxXzqlModelDTO.getModelByQllx(bdcQl.getClass().getSimpleName());
                                if(lsgxModelDTOList != null) {
                                    lsgxXzqlModelDTO.getModelByQllx(bdcQl.getClass().getSimpleName()).add(initLsgxModel(bdcXm, zxql ? null : bdcQl, bdcXmLsgxDO));
                                }
                            }
                        }
                    }
                }
            }
        }
        return lsgxXzqlModelDTO;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  向上递归
     */
    private void listPrevCqBgLsDTO(BdcXmDO bdcXmDO,Map<String,BdcCqBgLsDTO> cqBgLsDTOMap ,int level, Set<String> hashSet){
        if(StringUtils.isNotBlank(bdcXmDO.getXmid())){
            List<BdcXmLsgxDO> bdcXmLsgxDOS =bdcXmLsgxService.queryBdcXmLsgxByXmid(bdcXmDO.getXmid(),"");
            if(CollectionUtils.isNotEmpty(bdcXmLsgxDOS)){
                List<String> yxmidList = new ArrayList<>();
                for(BdcXmLsgxDO xmLsgxDO:bdcXmLsgxDOS) {
                    if (StringUtils.isNotBlank(xmLsgxDO.getYxmid())) {
                        yxmidList.add(xmLsgxDO.getYxmid());
                    }
                }
                List<BdcXmDO> yXmList =bdcXmService.listBdcXmByXmids(yxmidList);
                if(CollectionUtils.isNotEmpty(yXmList)) {
                    yXmList = yXmList.stream().filter(yxm->!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, yxm.getQllx()) &&!CommonConstantUtils.QSZT_TEMPORY.equals(yxm.getQszt())).collect(Collectors.toList());
                    yxmidList = yXmList.stream().map(BdcXmDO::getXmid).filter(StringUtils::isNotBlank).collect(Collectors.toList());

                    cqBgLsDTOMap.put(bdcXmDO.getXmid(),initBdcCqBgLsDTO(bdcXmDO, level,yxmidList));
                    level--;
                    for(BdcXmDO yXm:yXmList) {
                        listPrevCqBgLsDTO(yXm, cqBgLsDTOMap, level,hashSet);
                    }
                }
            }else{
                cqBgLsDTOMap.put(bdcXmDO.getXmid(),initBdcCqBgLsDTO(bdcXmDO, level,new ArrayList<>()));
            }
        }

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  向下递归
     */
    private void listNextCqBgLsDTO(BdcXmDO bdcXmDO,Map<String,BdcCqBgLsDTO> cqBgLsDTOMap,int level, Set<String> hashSet){
        if(StringUtils.isNotBlank(bdcXmDO.getXmid())){
            List<BdcXmLsgxDO> bdcXmLsgxDOS =bdcXmLsgxService.queryBdcXmNextLsgxByXmid(bdcXmDO.getXmid(),"",null);
            if(CollectionUtils.isNotEmpty(bdcXmLsgxDOS)){
                List<String> xmidList = new ArrayList<>();
                for(BdcXmLsgxDO xmLsgxDO:bdcXmLsgxDOS) {
                    if (StringUtils.isNotBlank(xmLsgxDO.getXmid())) {
                        xmidList.add(xmLsgxDO.getXmid());
                    }
                }
                List<BdcXmDO> xmList =bdcXmService.listBdcXmByXmids(xmidList);
                if(CollectionUtils.isNotEmpty(xmList)) {
                    xmList = xmList.stream().filter(yxm->!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, yxm.getQllx())).collect(Collectors.toList());
                    level++;
                    for(BdcXmDO xm:xmList) {
                        if(!cqBgLsDTOMap.containsKey(xm.getXmid())) {
                            if (StringUtils.equals(xm.getBdcdyh(), bdcXmDO.getBdcdyh())) {
                                List<String> yxmidList = new ArrayList<>();
                                yxmidList.add(bdcXmDO.getXmid());
                                cqBgLsDTOMap.put(xm.getXmid(), initBdcCqBgLsDTO(xm, level, yxmidList));
                            } else {
                                //不相等,向上找当前项目的所有原项目
                                List<BdcXmLsgxDO> xmLsgxDOS = bdcXmLsgxService.queryBdcXmLsgxByXmid(xm.getXmid(), "");
                                if (CollectionUtils.isNotEmpty(xmLsgxDOS)) {
                                    List<String> yxmidList = new ArrayList<>();
                                    for (BdcXmLsgxDO lsgxDO : xmLsgxDOS) {
                                        if (StringUtils.isNotBlank(lsgxDO.getYxmid())) {
                                            yxmidList.add(lsgxDO.getYxmid());
                                        }
                                    }
                                    cqBgLsDTOMap.put(xm.getXmid(), initBdcCqBgLsDTO(xm, level, yxmidList));
                                }
                            }
                            listNextCqBgLsDTO(xm, cqBgLsDTOMap, level, hashSet);
                        }
                    }
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  组织产权变更历史对象
     */
    private BdcCqBgLsDTO initBdcCqBgLsDTO(BdcXmDO bdcXmDO,int level,List<String> yxmidList){
        BdcCqBgLsDTO bdcCqBgLsDTO =new BdcCqBgLsDTO();
        if(bdcXmDO != null){
            BeanUtils.copyProperties(bdcXmDO, bdcCqBgLsDTO);
            bdcCqBgLsDTO.setLevel(level);
            bdcCqBgLsDTO.setYxmidList(yxmidList);
            bdcCqBgLsDTO.setId(bdcXmDO.getXmid());
        }
        return bdcCqBgLsDTO;
    }



}
