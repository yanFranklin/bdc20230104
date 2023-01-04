package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.init.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcLsxmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.DyhGzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDysdQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.init.core.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;



/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/9/21
 * @description 批量更新不动产单元服务
 */
@Service
public class BdcDyhGzServiceImpl implements BdcDyhGzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDyhGzServiceImpl.class);

    public static final String TYPE_CQ = "cq";

    public static final String TYPE_YGCQ = "ygcq";

    public static final String TYPE_PP = "pp";

    public static final String XMTYPE_CQ = "1";

    public static final String XMTYPE_XZQL = "2";

    @Autowired
    private BdcXmService bdcXmService;

    @Autowired
    private BdcXnbdcdyhGxService bdcXnbdcdyhGxService;

    @Autowired
    BdcBdcdyService bdcBdcdyService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcFctdPpgxService bdcFctdPpgxService;

    @Autowired
    private LsgxModelService lsgxModelService;

    @Autowired
    private BdcQllxService bdcQllxService;

    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;

    @Override
    public void updateBdcdyhPl(DyhGzQO dyhGzQO){
        if(CollectionUtils.isNotEmpty(dyhGzQO.getBdcXmDOList()) && StringUtils.isNotBlank(dyhGzQO.getBdcdyh())){
            List<BdcXmDO> bdcXmDOList =dyhGzQO.getBdcXmDOList();
            String bdcdyh =dyhGzQO.getBdcdyh();
            //验证数据
            String returnMsg =checkBdcdyh(bdcXmDOList,bdcdyh);
            if(StringUtils.equals(CommonConstantUtils.JK_RESPONSE_SUCCESS,returnMsg)){
                try {
                    String qjgldm = "";
                    if (StringUtils.isNotBlank(bdcXmDOList.get(0).getXmid())) {
                        BdcXmFbDO bdcXmFbDO = entityMapper.selectByPrimaryKey(BdcXmFbDO.class, bdcXmDOList.get(0).getXmid());
                        if (bdcXmFbDO != null) {
                            qjgldm = bdcXmFbDO.getQjgldm();
                        }
                    }
                    //存储关系数据
                    List<BdcXnbdcdyhGxDO> listXndyhgx=new ArrayList<>();
                    String czr =userManagerUtils.getUserAlias();
                    //循环生成落宗关系
                    for(BdcXmDO xm:bdcXmDOList){
                        BdcXnbdcdyhGxDO xnbdcdyhGxDO = new BdcXnbdcdyhGxDO();
                        xnbdcdyhGxDO.setBdcdyh(bdcdyh);
                        xnbdcdyhGxDO.setGxid(UUIDGenerator.generate16());
                        xnbdcdyhGxDO.setXnbdcdyh(xm.getBdcdyh());
                        xnbdcdyhGxDO.setXnbdcdyhxmid(xm.getXmid());
                        xnbdcdyhGxDO.setCzr(czr);
                        xnbdcdyhGxDO.setCzsj(new Date());
                        xnbdcdyhGxDO.setGxlb(CommonConstantUtils.GXLB_DYHGZ);
                        listXndyhgx.add(xnbdcdyhGxDO);
                    }
                    //插入临时单元号和不动产单元号的关系
                    entityMapper.insertBatchSelective(listXndyhgx);
                    //插入历史关系
                    if(CollectionUtils.isNotEmpty(dyhGzQO.getBdcXmLsgxDOList())){
                        for(BdcXmLsgxDO bdcXmLsgxDO:dyhGzQO.getBdcXmLsgxDOList()){
                            bdcXmLsgxDO.setGxid(UUIDGenerator.generate16());
                        }
                        entityMapper.insertBatchSelective(dyhGzQO.getBdcXmLsgxDOList());
                    }
                    //更新单元号数据
                    bdcXnbdcdyhGxService.updateBdcdyh(bdcXmDOList, bdcdyh,qjgldm,CommonConstantUtils.SF_F_DM,true);
                    //更新单元锁定
                   if(CollectionUtils.isNotEmpty(dyhGzQO.getBdcDysdDOList())){
                       for(BdcDysdDO bdcDysdDO:dyhGzQO.getBdcDysdDOList()){
                           BdcDysdDO updateDysd =new BdcDysdDO();
                           updateDysd.setDysdid(bdcDysdDO.getDysdid());
                           updateDysd.setBdcdyh(bdcdyh);
                           entityMapper.updateByPrimaryKeySelective(updateDysd);
                       }
                   }
                    //回写状态到权籍
                    bdcBdcdyService.syncBdcdyZtByBdcdyh(bdcdyh,qjgldm);
                }catch (Exception e){
                    LOGGER.error("更新不动产单元操作异常",e);
                    throw new AppException(ErrorCode.SERVER_EX, "更新不动产单元操作异常");
                }
            }else{
                throw new AppException(ErrorCode.CUSTOM, returnMsg);
            }
        }else{
            throw new AppException(ErrorCode.MISSING_ARG, "缺失bdcdyh或项目数据");
        }

    }

    @Override
    public DyhGzXmDTO queryDyhGzXmDTO(String xmid,String xmtype){
        DyhGzXmDTO dyhGzXmDTO =new DyhGzXmDTO();
        if(StringUtils.equals(XMTYPE_CQ,xmtype)){
            return queryCqDyhGzXmDTO(xmid);
        }else if(StringUtils.equals(XMTYPE_XZQL,xmtype)){
            return queryXzqlDyhGzXmDTO(xmid);
        }
        return dyhGzXmDTO;

    }

    @Override
    public List<BdcGzyzVO> yzXzxx(DyhGzQO dyhGzQO){
        List<BdcGzyzVO> bdcGzyzVOList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(dyhGzQO.getBdcXmDOList()) && StringUtils.isNotBlank(dyhGzQO.getBdcdyh())){
            List<String> bdcdyhList = dyhGzQO.getBdcXmDOList().stream().map(BdcXmDO::getBdcdyh).distinct().collect(Collectors.toList());
            List<String> xmidList = dyhGzQO.getBdcXmDOList().stream().map(BdcXmDO::getXmid).distinct().collect(Collectors.toList());
            //单元锁定数据
            if(CollectionUtils.isNotEmpty(bdcdyhList)){
                BdcDysdQO bdcDysdQO = new BdcDysdQO();
                bdcDysdQO.setSdlx(null);
                bdcDysdQO.setBdcdyhList(bdcdyhList);
                List<BdcDysdDO> bdcDysdDOList =bdcBdcdyFeignService.listBdcDysd(bdcDysdQO);
                if(CollectionUtils.isNotEmpty(bdcDysdDOList)){
                    List<String> dysdids =new ArrayList<>();
                    if(CollectionUtils.isNotEmpty(dyhGzQO.getBdcDysdDOList())){
                        dysdids = dyhGzQO.getBdcDysdDOList().stream().map(x -> x.getDysdid()).collect(Collectors.toList());
                    }
                    for(BdcDysdDO bdcDysdDO:bdcDysdDOList) {
                        if (CollectionUtils.isEmpty(dysdids) || !dysdids.contains(bdcDysdDO.getDysdid())){
                            BdcGzyzVO bdcGzyzVO =new BdcGzyzVO();
                            bdcGzyzVO.setTsxx("单元号"+bdcDysdDO.getBdcdyh()+"存在可能漏选的单元锁定数据,请确认");
                            bdcGzyzVO.setYzlx(CommonConstantUtils.YZLX_CONFIRM);
                            bdcGzyzVOList.add(bdcGzyzVO);

                        }
                    }
                }
                //预查封信息
                Example cfexample = new Example(BdcCfDO.class);
                Example.Criteria criteria = cfexample.createCriteria();
                criteria.andInWithListString("bdcdyh", bdcdyhList);
                criteria.andIn("cflx", Arrays.asList(CommonConstantUtils.CFLX_YCF,CommonConstantUtils.CFLX_LHYCF));
                List<BdcCfDO> bdcCfDOList =entityMapper.selectByExample(cfexample);
                if(CollectionUtils.isNotEmpty(bdcCfDOList)){
                    for(BdcCfDO bdcCfDO:bdcCfDOList) {
                        if (!xmidList.contains(bdcCfDO.getXmid())){
                            BdcGzyzVO bdcGzyzVO =new BdcGzyzVO();
                            bdcGzyzVO.setTsxx("单元号"+bdcCfDO.getBdcdyh()+"存在可能漏选的预查封数据,请确认");
                            bdcGzyzVO.setYzlx(CommonConstantUtils.YZLX_CONFIRM);
                            bdcGzyzVOList.add(bdcGzyzVO);

                        }
                    }
                }
                //预告信息
                Example ygexample = new Example(BdcYgDO.class);
                Example.Criteria ygcriteria = ygexample.createCriteria();
                ygcriteria.andInWithListString("bdcdyh", bdcdyhList);
                List<BdcYgDO> bdcYgDOList =entityMapper.selectByExample(ygexample);
                if(CollectionUtils.isNotEmpty(bdcYgDOList)){
                    for(BdcYgDO bdcYgDO:bdcYgDOList) {
                        if (!xmidList.contains(bdcYgDO.getXmid())){
                            BdcGzyzVO bdcGzyzVO =new BdcGzyzVO();
                            bdcGzyzVO.setTsxx("单元号"+bdcYgDO.getBdcdyh()+"存在可能漏选的预告数据,请确认");
                            bdcGzyzVO.setYzlx(CommonConstantUtils.YZLX_CONFIRM);
                            bdcGzyzVOList.add(bdcGzyzVO);

                        }
                    }
                }
            }

        }
        return bdcGzyzVOList;

    }

    @Override
    public DyhGzWcqlsgxDTO queryDyhGzWcqlsgxDTO(List<BdcXmDO> bdcXmDOList){
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "查询无产权历史关系数据缺失已选项目数据");
        }
        List<String> bdcdyhList = bdcXmDOList.stream().map(BdcXmDO::getBdcdyh).distinct().collect(Collectors.toList());
        List<String> xmidList = bdcXmDOList.stream().map(BdcXmDO::getXmid).distinct().collect(Collectors.toList());
        //查询无产权历史关系的限制权利
        DyhGzWcqlsgxDTO dyhGzWcqlsgxDTO =listWcqlsgxXmList(bdcdyhList,xmidList);
        //查询单元锁定数据
        BdcDysdQO bdcDysdQO = new BdcDysdQO();
        bdcDysdQO.setSdlx(null);
        bdcDysdQO.setBdcdyhList(bdcdyhList);
        List<BdcDysdDO> bdcDysdDOList =bdcBdcdyFeignService.listBdcDysd(bdcDysdQO);
        if(CollectionUtils.isNotEmpty(bdcDysdDOList)){
            dyhGzWcqlsgxDTO.setBdcDysdList(bdcDysdDOList);
        }
        return dyhGzWcqlsgxDTO;

    }

    @Override
    public void updateWlcqXzql(List<DyhGzQO> dyhGzQOList){
        if(CollectionUtils.isNotEmpty(dyhGzQOList)) {
            for (DyhGzQO dyhGzQO : dyhGzQOList) {
                if(CollectionUtils.isNotEmpty(dyhGzQO.getBdcXmDOList())) {
                    try {
                        //更新单元号数据
                        bdcXnbdcdyhGxService.updateBdcdyh(dyhGzQO.getBdcXmDOList(), dyhGzQO.getBdcdyh(), dyhGzQO.getQjgldm(), CommonConstantUtils.SF_F_DM, true);
                    } catch (Exception e){
                        LOGGER.error("更新不动产单元操作异常",e);
                        throw new AppException(ErrorCode.SERVER_EX, "更新不动产单元操作异常");
                }
                }
                //单元锁定
                if(CollectionUtils.isNotEmpty(dyhGzQO.getBdcDysdDOList())){
                    for(BdcDysdDO bdcDysdDO:dyhGzQO.getBdcDysdDOList()){
                        BdcDysdDO updateDysd =new BdcDysdDO();
                        updateDysd.setDysdid(bdcDysdDO.getDysdid());
                        updateDysd.setBdcdyh(dyhGzQO.getBdcdyh());
                        entityMapper.updateByPrimaryKeySelective(updateDysd);
                    }
                }
                //插入历史关系
                if(CollectionUtils.isNotEmpty(dyhGzQO.getBdcXmLsgxDOList())){
                    for(BdcXmLsgxDO bdcXmLsgxDO:dyhGzQO.getBdcXmLsgxDOList()){
                        bdcXmLsgxDO.setGxid(UUIDGenerator.generate16());
                    }
                    entityMapper.insertBatchSelective(dyhGzQO.getBdcXmLsgxDOList());
                }
                //回写状态到权籍
                bdcBdcdyService.syncBdcdyZtByBdcdyh(dyhGzQO.getBdcdyh(),dyhGzQO.getQjgldm());
            }
        }
    }


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据产权项目获取所有关联的项目集合
     */
    private DyhGzXmDTO queryCqDyhGzXmDTO(String cqxmid){
        DyhGzXmDTO dyhGzXmDTO =new DyhGzXmDTO();
        if(StringUtils.isNotBlank(cqxmid)){
            //获取产权关联项目
            dyhGzXmDTO.setCqglLsgxXmDTO(queryDyhGzCqLsgxXmDTO(cqxmid,TYPE_CQ));
            //查询匹配关系
            List<BdcFctdPpgxDO> bdcFctdPpgxDOList =bdcFctdPpgxService.queryBdcFctdPpgx(cqxmid);
            if(CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)){
                if(bdcFctdPpgxDOList.size() >1){
                    throw new AppException(ErrorCode.CUSTOM, "匹配关系存在多条,请检查数据");
                }
                dyhGzXmDTO.setPpcqLsgxXmDTO(queryDyhGzCqLsgxXmDTO(bdcFctdPpgxDOList.get(0).getTdcqxmid(),TYPE_PP));
            }else{
                bdcFctdPpgxDOList =bdcFctdPpgxService.queryBdcFctdPpgxByTdxmid(cqxmid);
                if(CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)){
                    if(bdcFctdPpgxDOList.size() >1){
                        throw new AppException(ErrorCode.CUSTOM, "匹配关系存在多条,请检查数据");
                    }
                    dyhGzXmDTO.setPpcqLsgxXmDTO(queryDyhGzCqLsgxXmDTO(bdcFctdPpgxDOList.get(0).getFwcqxmid(),TYPE_PP));
                }else{
                    //没有匹配关系.查找是否存在同一单元号的房屋土地数据,此种情况只能一对一
                    String fwtdxmid =getZdglFwtdXmid(cqxmid);
                    if(StringUtils.isNotBlank(fwtdxmid)){
                        dyhGzXmDTO.setPpcqLsgxXmDTO(queryDyhGzCqLsgxXmDTO(fwtdxmid,TYPE_PP));
                    }
                }
            }
            //项目集合
            dealXmxx(dyhGzXmDTO);

        }
        return dyhGzXmDTO;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据新增权利项目获取所有关联的项目集合
     */
    private DyhGzXmDTO queryXzqlDyhGzXmDTO(String addxmid){
        DyhGzXmDTO dyhGzXmDTO =new DyhGzXmDTO();
        if(StringUtils.isNotBlank(addxmid)){
            BdcXmDO bdcXmDO =entityMapper.selectByPrimaryKey(BdcXmDO.class,addxmid);
            if(bdcXmDO ==null || !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())){
                throw new AppException(ErrorCode.VALIDATION_EX, "请选择非产权数据");
            }
            //验证新增权利是否关联其他产权
            List<LsgxModelDTO> cqlsgxModelDTOList = lsgxModelService.initLsgxCqModel(bdcXmDO.getXmid(), true, CommonConstantUtils.SF_F_DM);
            if(CollectionUtils.isNotEmpty(cqlsgxModelDTOList)){
                for (LsgxModelDTO lsgxModelDTO:cqlsgxModelDTOList) {
                    if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, lsgxModelDTO.getQllx())) {
                        throw new AppException(ErrorCode.VALIDATION_EX, "新增的权利数据已关联其他产权,不允许新增");
                    }
                }
            }
            if(CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx())){
                BdcQl bdcQl =bdcQllxService.queryQllxDO(addxmid);
                if(bdcQl instanceof BdcYgDO){
                    BdcYgDO bdcYgDO =(BdcYgDO) bdcQl;
                    if(ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR,bdcYgDO.getYgdjzl())){
                        //预告作为产权
                        dyhGzXmDTO.setCqglLsgxXmDTO(queryDyhGzCqLsgxXmDTO(addxmid,TYPE_YGCQ));
                    }else{
                        dyhGzXmDTO.setXzqlLsgxXmDTO(queryXzqlLsgxXmDTO(addxmid));
                    }
                }
            }else{
                dyhGzXmDTO.setXzqlLsgxXmDTO(queryXzqlLsgxXmDTO(addxmid));
            }
        }
        //项目集合
        dealXmxx(dyhGzXmDTO);
        return dyhGzXmDTO;


    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @return 验证是否通过,通过返回SUCCESS，不通过返回原因
     * @description  验证单元号是否可以更新
     */
    private String checkBdcdyh(List<BdcXmDO> bdcXmDOList, String bdcdyh){
        if(StringUtils.isNotBlank(bdcdyh) &&CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //验证单元号是否已关联了已有的产权或者限制权利
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setBdcdyh(bdcdyh);
            List<BdcXmDO> xmDOList =bdcXmService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isEmpty(xmDOList)){
                //没有做过登记,允许更新
                return CommonConstantUtils.JK_RESPONSE_SUCCESS;
            }else{
                //判断单元号对应的项目是否在选择范围内
                List<String> xmids =bdcXmDOList.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
                for(BdcXmDO xmDO:xmDOList){
                    if(!xmids.contains(xmDO.getXmid())){
                        return "更新的单元号"+bdcdyh+"关联了其他权利,不允许更新,其他权利对应xmid:"+xmDO.getXmid();
                    }
                }
            }
            //验证选择的项目对应的不动产单元号是否存在落宗关系
            for(BdcXmDO bdcXmDO:bdcXmDOList){
                List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList =bdcXnbdcdyhGxService.queryBdcXnbdcdyhGx(bdcXmDO.getXmid());
                if(CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)){
                    return "选择的权利存在匹配关系,请先取消匹配,虚拟单元号:"+bdcXnbdcdyhGxDOList.get(0).getXnbdcdyh();
                }
            }
            BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO =new BdcXnbdcdyhGxDO();
            bdcXnbdcdyhGxDO.setBdcdyh(bdcdyh);
            List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList =bdcXnbdcdyhGxService.queryBdcXnbdcdyhByCondition(bdcXnbdcdyhGxDO);
            if(CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)){
                return "不动产单元号"+bdcdyh+"已被匹配,请先取消匹配";
            }
        }else{
            return "数据错误";
        }
        return CommonConstantUtils.JK_RESPONSE_SUCCESS;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据产权项目ID通过历史关系获取对应项目
     */
    private DyhGzCqLsgxXmDTO queryDyhGzCqLsgxXmDTO(String cqxmid,String type){
        DyhGzCqLsgxXmDTO dyhGzCqLsgxXmDTO =new DyhGzCqLsgxXmDTO();
        if(StringUtils.isNotBlank(cqxmid)){
            BdcXmDO bdcXmDO =entityMapper.selectByPrimaryKey(BdcXmDO.class,cqxmid);
            if(bdcXmDO ==null || ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())){
                if(StringUtils.equals(TYPE_CQ,type)) {
                    throw new AppException(ErrorCode.VALIDATION_EX, "请选择产权数据");
                }else if(StringUtils.equals(TYPE_PP,type)){
                    throw new AppException(ErrorCode.VALIDATION_EX, "匹配数据非产权数据,请检查");
                }
            }
            dyhGzCqLsgxXmDTO.setBdcXmDO(bdcXmDO);
            //查询产权对应历史产权
            List<LsgxModelDTO> cqlsgxModelDTOList = lsgxModelService.initLsgxCqModel(cqxmid, true, CommonConstantUtils.SF_F_DM);
            //循环产权
            if(CollectionUtils.isNotEmpty(cqlsgxModelDTOList)) {
                //验证产权
                int xscqCount =0;
                for(LsgxModelDTO cqlsgxModelDTO:cqlsgxModelDTOList) {
                    if(!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, cqlsgxModelDTO.getQllx()) &&CommonConstantUtils.QSZT_VALID.equals(cqlsgxModelDTO.getQszt())){
                        xscqCount++;
                    }
                }
                if(xscqCount >1){
                    throw new AppException(ErrorCode.CUSTOM, "当前关联权利存在多条现势产权");
                }
                //组织产权限制权利数据
                List<CqXzqlLsgxModel> cqXzqlLsgxModelList =new ArrayList<>();
                for(LsgxModelDTO cqlsgxModelDTO:cqlsgxModelDTOList) {
                    CqXzqlLsgxModel cqXzqlLsgxModel =new CqXzqlLsgxModel();
                    cqXzqlLsgxModel.setCqlsgxModelDTO(cqlsgxModelDTO);
                    LsgxXzqlModelDTO lsgxXzqlModelDTO = lsgxModelService.initPrevNextLsgxXzqlModel(cqlsgxModelDTO.getId(), "next", CommonConstantUtils.SF_F_DM);
                    if(lsgxXzqlModelDTO != null){
                        cqXzqlLsgxModel.setLsgxXzqlModelDTO(lsgxXzqlModelDTO);
                    }
                    cqXzqlLsgxModelList.add(cqXzqlLsgxModel);
                }
                dyhGzCqLsgxXmDTO.setCqXzqlLsgxModelList(cqXzqlLsgxModelList);
            }

        }
        return dyhGzCqLsgxXmDTO;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据限制权利项目ID通过历史关系获取对应项目
     */
    private LsgxXzqlModelDTO queryXzqlLsgxXmDTO(String xzqlxmid){
        LsgxXzqlModelDTO xzqlLsgxXmDTO =new LsgxXzqlModelDTO();
        if(StringUtils.isNotBlank(xzqlxmid)) {
            xzqlLsgxXmDTO = lsgxModelService.initPrevNextLsgxXzqlModel(xzqlxmid, "all", CommonConstantUtils.SF_F_DM);
        }
        return xzqlLsgxXmDTO;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  组织项目集合信息
     */
    private void dealXmxx(DyhGzXmDTO dyhGzXmDTO){
        List<BdcXmDO> bdcXmDOList =new ArrayList<>();
        //产权
        if(dyhGzXmDTO.getCqglLsgxXmDTO() != null &&CollectionUtils.isNotEmpty(dyhGzXmDTO.getCqglLsgxXmDTO().getCqXzqlLsgxModelList())){
            for(CqXzqlLsgxModel cqXzqlLsgxModel:dyhGzXmDTO.getCqglLsgxXmDTO().getCqXzqlLsgxModelList()){
                dealCqXmxx(cqXzqlLsgxModel,bdcXmDOList);
            }
        }
        //匹配产权
        if(dyhGzXmDTO.getPpcqLsgxXmDTO() != null &&CollectionUtils.isNotEmpty(dyhGzXmDTO.getPpcqLsgxXmDTO().getCqXzqlLsgxModelList())){
            for(CqXzqlLsgxModel cqXzqlLsgxModel:dyhGzXmDTO.getPpcqLsgxXmDTO().getCqXzqlLsgxModelList()){
                dealCqXmxx(cqXzqlLsgxModel,bdcXmDOList);
            }
        }
        //限制权利
        if(dyhGzXmDTO.getXzqlLsgxXmDTO() !=null){
            LsgxXzqlModelDTO lsgxXzqlModelDTO = dyhGzXmDTO.getXzqlLsgxXmDTO();
            //抵押信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getDyLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getDyLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }
            //查封信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getCfLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getCfLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }
            //异议信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getYyLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getYyLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }
            //预告信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getYgLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getYgLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }
            //地役权信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getDyiLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getDyiLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }
            //居住权信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getJzLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getJzLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }

        }
        dyhGzXmDTO.setBdcXmDOList(bdcXmDOList);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            List<String> xmidList = bdcXmDOList.stream().map(x -> x.getXmid()).collect(Collectors.toList());
            dyhGzXmDTO.setXmidList(xmidList);
        }

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  组织产权项目集合信息
     */
    private void dealCqXmxx(CqXzqlLsgxModel cqXzqlLsgxModel,List<BdcXmDO> bdcXmDOList){
        if(cqXzqlLsgxModel.getCqlsgxModelDTO() !=null &&cqXzqlLsgxModel.getCqlsgxModelDTO().getBdcXmDO() !=null){
            bdcXmDOList.add(cqXzqlLsgxModel.getCqlsgxModelDTO().getBdcXmDO());
        }
        if(cqXzqlLsgxModel.getLsgxXzqlModelDTO() !=null){
            LsgxXzqlModelDTO lsgxXzqlModelDTO =cqXzqlLsgxModel.getLsgxXzqlModelDTO();
            //抵押信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getDyLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getDyLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }
            //查封信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getCfLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getCfLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }
            //异议信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getYyLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getYyLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }
            //预告信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getYgLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getYgLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }
            //地役权信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getDyiLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getDyiLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }
            //居住权信息
            if(CollectionUtils.isNotEmpty(lsgxXzqlModelDTO.getJzLsgxModel())){
                for(LsgxModelDTO lsgxModelDTO:lsgxXzqlModelDTO.getJzLsgxModel()){
                    if(lsgxModelDTO.getBdcXmDO() !=null){
                        bdcXmDOList.add(lsgxModelDTO.getBdcXmDO());
                    }
                }
            }

        }

    }

    /**
     * @param bdcdyhList 不动产单元集合
     * @param xmidList 已选信息项目ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  查询无产权历史关系的限制权利
     */
    private DyhGzWcqlsgxDTO listWcqlsgxXmList(List<String> bdcdyhList,List<String> xmidList){
        DyhGzWcqlsgxDTO dyhGzWcqlsgxDTO =new DyhGzWcqlsgxDTO();
        List<BdcXmDO> xzqlXmList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcdyhList)) {
            //查询限制权利项目
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setQllxs(Arrays.asList(CommonConstantUtils.BDC_QLLX_XZQL));
            bdcXmQO.setBdcdyhList(bdcdyhList);
            List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                for(BdcXmDO bdcXmDO:bdcXmDOList) {
                    if(!xmidList.contains(bdcXmDO.getXmid())) {
                        //向上递归查询是否有产权项目
                        BdcLsxmQO bdcLsxmQO = new BdcLsxmQO();
                        bdcLsxmQO.setXmid(bdcXmDO.getXmid());
                        bdcLsxmQO.setPcqllxs(Arrays.asList(CommonConstantUtils.BDC_QLLX_XZQL));
                        List<BdcXmDO> bdcXmDOS = bdcXmLsgxService.getAllLsXmByLsgx(bdcLsxmQO);
                        if (CollectionUtils.isEmpty(bdcXmDOS)) {
                            xzqlXmList.add(bdcXmDO);
                        }
                    }
                }
            }
        }
        dyhGzWcqlsgxDTO.setXzqlXmList(xzqlXmList);
        return dyhGzWcqlsgxDTO;

    }

    /**
     * @param cqxmid 查询的产权项目ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取自动关联的房屋或土地项目ID
     */
    private String getZdglFwtdXmid(String cqxmid){
        BdcXmDO bdcXmDO =bdcXmService.queryBdcXmByPrimaryKey(cqxmid);
        if(bdcXmDO !=null &&StringUtils.isNotBlank(bdcXmDO.getBdcdyh())){
            BdcXmDO xmDO =new BdcXmDO();
            xmDO.setBdcdyh(bdcXmDO.getBdcdyh());
            xmDO.setQllx(bdcXmDO.getQllx());
            xmDO.setQszt(CommonConstantUtils.QSZT_VALID);
            List<BdcXmDO> xmDOList =bdcXmService.listBdcXm(xmDO);
            //查询同权利的现势数据,不允许多条现势
            if(CollectionUtils.isNotEmpty(xmDOList) &&xmDOList.size() ==1 &&StringUtils.equals(cqxmid,xmDOList.get(0).getXmid())) {
                xmDO =new BdcXmDO();
                xmDO.setBdcdyh(bdcXmDO.getBdcdyh());
                if(CommonConstantUtils.FWBDCLX.equals(bdcXmDO.getBdclx())){
                    //房屋查找土地
                    xmDO.setBdclx(CommonConstantUtils.DYBDCLX_CTD);

                }else if(CommonConstantUtils.DYBDCLX_CTD.equals(bdcXmDO.getBdclx())){
                    //土地查找房屋
                    xmDO.setBdclx(CommonConstantUtils.FWBDCLX);
                }
                xmDOList =bdcXmService.listBdcXm(xmDO);
                if(CollectionUtils.isNotEmpty(xmDOList)){
                    List<BdcXmDO> cqXmList =new ArrayList<>();
                    List<BdcXmDO> xscqXmList =new ArrayList<>();
                    for(BdcXmDO cqxm:xmDOList){
                        if (ArrayUtils.contains(CommonConstantUtils.BDC_ZS_QLLX, cqxm.getQllx())) {
                            cqXmList.add(cqxm);
                            if(CommonConstantUtils.QSZT_VALID.equals(cqxm.getQszt())){
                                xscqXmList.add(cqxm);
                            }
                        }
                    }
                    //现势产权只允许一条
                    if(CollectionUtils.isNotEmpty(xscqXmList)){
                        if(xscqXmList.size() ==1){
                            return xscqXmList.get(0).getXmid();
                        }
                    }else if(CollectionUtils.isNotEmpty(cqXmList)){
                        return cqXmList.get(0).getXmid();
                    }
                }
            }
        }
        return null;
    }
}
