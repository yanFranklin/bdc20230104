package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.init.BdcCopyReplaceYwxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.init.core.service.BdcBdcdyService;
import cn.gtmap.realestate.init.core.service.BdcFctdPpgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.core.service.BdcXnbdcdyhGxService;
import cn.gtmap.realestate.init.service.other.InitDataService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 房产土地匹配关系服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/4.
 * @description
 */
@Service
public class BdcFctdPpgxServiceImpl implements BdcFctdPpgxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFctdPpgxServiceImpl.class);
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcXnbdcdyhGxService bdcXnbdcdyhGxService;
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    BdcCzrzServiceImpl bdcCzrzFeignService;
    @Autowired
    private BdcBdcdyService bdcBdcdyService;
    @Autowired
    private InitDataService initDataService;
    @Autowired
    private BdcXmService bdcXmService;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 匹配土地证信息是否回写土地证信息到房屋项目
      */
    @Value("${pptdz.hxtdzxx:false}")
    private Boolean hxtdzxx;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 土地证已被匹配，再次匹配时是否复制土地项目信息
     */
    @Value("${pptdz.fztdxx:false}")
    private Boolean fztdxx;

    /**
     * 房产证和土地证进行匹配记录关系
     * @param fcxmid 房产证项目ID
     * @param tdxmid  土地证项目id
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogNote(value = "房产证和土地证匹配服务", action = LogActionConstans.FCTDPP_PPTD, custom = LogConstans.LOG_TYPE_PP)
    public void pptd(String fcxmid, String tdxmid,String requestClientRealIP) throws Exception {
        if(StringUtils.isNotBlank(fcxmid) && StringUtils.isNotBlank(tdxmid)){
            BdcXmDO fcxm=entityMapper.selectByPrimaryKey(BdcXmDO.class,fcxmid);
            //复制的项目集合
            List<BdcXmDO> fzXmList =new ArrayList<>();
            Integer gxlb =CommonConstantUtils.GXLB_FDPP;
            if(fcxm ==null){
                throw new AppException(ErrorCode.SERVER_EX, "房屋项目ID未查询到项目数据");
            }
            BdcXmDO tdxm;
            //判断当前是否已有匹配关系
            Example example = new Example(BdcFctdPpgxDO.class);
            example.createCriteria()
                    .andEqualTo("tdcqxmid",tdxmid);
            List<BdcFctdPpgxDO> bdcFctdPpgxList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcFctdPpgxList)){
                if(Boolean.TRUE.equals(fztdxx)){
                    //处理数据,复制土地项目信息,匹配的土地项目取新生成的土地项目
                    tdxm =fzTdxx(tdxmid,fcxm.getBdcdyh(),fzXmList);
                    gxlb=CommonConstantUtils.GXLB_FZTDXMPP;
                    if(tdxm ==null){
                        throw new AppException(ErrorCode.SERVER_EX, "复制土地项目失败");
                    }
                    tdxmid = tdxm.getXmid();
                }else {
                    throw new AppException(ErrorCode.SERVER_EX, "土地证已存在匹配关系，不允许再被匹配");
                }
            }else{
                tdxm=entityMapper.selectByPrimaryKey(BdcXmDO.class,tdxmid);
            }
            String czr =userManagerUtils.getUserAlias();

            BdcFctdPpgxDO bdcFctdPpgxDO = new BdcFctdPpgxDO();
            bdcFctdPpgxDO.setFwcqxmid(fcxmid);
            //先删除
            entityMapper.delete(bdcFctdPpgxDO);
            bdcFctdPpgxDO.setTdcqxmid(tdxmid);
            bdcFctdPpgxDO.setGxid(UUIDGenerator.generate16());
            bdcFctdPpgxDO.setCzr(czr);
            bdcFctdPpgxDO.setCzsj(new Date());
            bdcFctdPpgxDO.setGxlb(gxlb);

            //新增匹配关系
            entityMapper.insertSelective(bdcFctdPpgxDO);
            //落宗
            if(fcxm != null) {
                //改变匹配状态为已匹配
                fcxm.setXmid(fcxmid);
                fcxm.setClsjppzt(CommonConstantUtils.SF_S_DM);
                //土地证号赋值
                if (tdxm != null) {
                    fcxm.setYtdzh(StringUtils.defaultString(tdxm.getBdcqzh()));
                    if (Boolean.TRUE.equals(hxtdzxx) &&!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, fcxm.getQllx())&&!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, tdxm.getQllx())) {
                        BdcXmDO yfcxm =new BdcXmDO();
                        BeanUtils.copyProperties(fcxm,yfcxm);
                        //回写土地证信息到房产证
                        if(StringUtils.isNotBlank(tdxm.getZdzhyt())) {
                            fcxm.setZdzhyt(tdxm.getZdzhyt());
                        }
                        if(tdxm.getZdzhmj() != null) {
                            fcxm.setZdzhmj(tdxm.getZdzhmj());
                        }
                        if(StringUtils.isNotBlank(tdxm.getQlxz())) {
                            fcxm.setQlxz(tdxm.getQlxz());
                        }
                        //记录日志,方便回溯
                        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), CommonConstantUtils.LOG_EVENT_PPTDZHX, LogCompareUtils.initBdcXgData(fcxmid,yfcxm,fcxm,CommonConstantUtils.LOG_EVENT_PPTDZHX));
                        zipkinAuditEventRepository.add(auditEvent);
                    }
                }
                entityMapper.updateByPrimaryKeyNull(fcxm);
            }

            //单元号不同时走落宗逻辑
            if(tdxm!=null && fcxm!=null && !StringUtils.equals(tdxm.getBdcdyh(),fcxm.getBdcdyh())){
                bdcXnbdcdyhGxService.lz(tdxm.getBdcdyh(),
                        fcxm.getBdcdyh(),CommonConstantUtils.DYBDCLX_CTD,null,null,gxlb);

            }else if(CommonConstantUtils.GXLB_FZTDXMPP.equals(gxlb)){
                //复制土地项目时，土地项目与当前房屋单元号一致,仅建立土地项目与房屋关系
                //存储关系数据
                List<BdcXnbdcdyhGxDO> listXndyhgx=new ArrayList<>();
                //循环生成落宗关系
                for(BdcXmDO xm:fzXmList){
                    BdcXnbdcdyhGxDO xnbdcdyhGxDO = new BdcXnbdcdyhGxDO();
                    xnbdcdyhGxDO.setBdcdyh(fcxm.getBdcdyh());
                    xnbdcdyhGxDO.setGxid(UUIDGenerator.generate16());
                    xnbdcdyhGxDO.setXnbdcdyh("复制土地单元号");
                    xnbdcdyhGxDO.setXnbdcdyhxmid(xm.getXmid());
                    xnbdcdyhGxDO.setCzr(czr);
                    xnbdcdyhGxDO.setCzsj(new Date());
                    xnbdcdyhGxDO.setGxlb(CommonConstantUtils.GXLB_FZTDXMPP);
                    listXndyhgx.add(xnbdcdyhGxDO);
                }
                //插入临时单元号和不动产单元号的关系
                entityMapper.insertBatchSelective(listXndyhgx);
            }
            addModifyCzrz(fcxmid,tdxmid,requestClientRealIP,BdcCzrzLxEnum.CZRZ_CZLX_PP.key());
        }
    }

    /**
     * 取消房产证和土地证匹配关系
     * @param fcxmid 房产证项目ID
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogNote(value = "房产证和土地证取消匹配服务", action = LogActionConstans.FCTDPP_QXPPTD, custom = LogConstans.LOG_TYPE_PP)
    public void qxpptd(String fcxmid) throws Exception {
        if (StringUtils.isNotBlank(fcxmid)) {
            String tdcqxmid = "";
            BdcFctdPpgxDO bdcFctdPpgxDO = new BdcFctdPpgxDO();
            bdcFctdPpgxDO.setFwcqxmid(fcxmid);
            //先删除
            List<BdcFctdPpgxDO> list=entityMapper.selectByObj(bdcFctdPpgxDO);
            if(CollectionUtils.isNotEmpty(list)){
                tdcqxmid = list.get(0).getTdcqxmid();
                //删除
                entityMapper.delete(bdcFctdPpgxDO);
                BdcXmDO fcxm=entityMapper.selectByPrimaryKey(BdcXmDO.class,fcxmid);
                if(fcxm != null) {
                    //改变匹配状态为未匹配
                    fcxm.setXmid(fcxmid);
                    fcxm.setClsjppzt(CommonConstantUtils.SF_F_DM);
                    fcxm.setYtdzh(StringUtils.EMPTY);
                    if (Boolean.TRUE.equals(hxtdzxx) &&!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, fcxm.getQllx())) {
                        //清空土地证信息
                        fcxm.setZdzhyt(StringUtils.EMPTY);
                        fcxm.setZdzhmj(null);
                        fcxm.setQlxz(StringUtils.EMPTY);
                    }
                    entityMapper.updateByPrimaryKeyNull(fcxm);
                }
                //循环处理 取消落宗  正常只有一个
                for(BdcFctdPpgxDO fctdPpgxDO:list){
                    BdcXmDO tdxm=entityMapper.selectByPrimaryKey(BdcXmDO.class,fctdPpgxDO.getTdcqxmid());
                    if(tdxm!=null){
                        //判断土地项目是否为复制土地项目,复制土地项目,删除关系数据和土地项目数据
                        if(CommonConstantUtils.GXLB_FZTDXMPP.equals(list.get(0).getGxlb())){
                            deleteFztdzxx(fctdPpgxDO.getTdcqxmid());
                        }else {
                            bdcXnbdcdyhGxService.qxlz(tdxm.getBdcdyh(), fctdPpgxDO.getTdcqxmid());
                        }
                    }
                }
                addModifyCzrz(fcxmid,tdcqxmid,null,BdcCzrzLxEnum.CZRZ_CZLX_QXPP.key());
            }
        }
    }

    /**
     * 根据房产项目ID获取匹配关系
     *
     * @param fcxmid
     * @return List<BdcFctdPpgxDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcFctdPpgxDO> queryBdcFctdPpgx(String fcxmid){
        if(StringUtils.isBlank(fcxmid)){
            return Collections.emptyList();
        }
        BdcFctdPpgxDO bdcFctdPpgxDO = new BdcFctdPpgxDO();
        bdcFctdPpgxDO.setFwcqxmid(fcxmid);
        return entityMapper.selectByObj(bdcFctdPpgxDO);
    }

    /**
     * 根据土地项目ID获取匹配关系
     * @param tdxmid
     * @return List<BdcFctdPpgxDO>
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     */
    @Override
    public List<BdcFctdPpgxDO> queryBdcFctdPpgxByTdxmid(String tdxmid) {
        if(StringUtils.isBlank(tdxmid)){
            return Collections.emptyList();
        }
        BdcFctdPpgxDO bdcFctdPpgxDO = new BdcFctdPpgxDO();
        bdcFctdPpgxDO.setTdcqxmid(tdxmid);
        return entityMapper.selectByObj(bdcFctdPpgxDO);
    }


    /**
     * 添加操作日志
     * @param fcxmid
     * @param tdxmid
     * @param requestClientRealIP
     * @param czlx
     */
    private void addModifyCzrz(String fcxmid,
                               String tdxmid,
                               String requestClientRealIP,
                               Integer czlx) {
        LOGGER.info("匹配日志记录{},{},{}",czlx,fcxmid,tdxmid);
        //查询项目信息
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        bdcCzrzDO.setCzsj(new Date());
        bdcCzrzDO.setCzzt(1);
        bdcCzrzDO.setCzlx(czlx);
        bdcCzrzDO.setCzyy(BdcCzrzLxEnum.codeTodesc(czlx));
        bdcCzrzDO.setCzr(userManagerUtils.getCurrentUserName());
        if(StringUtils.isNotBlank(requestClientRealIP)) {
            bdcCzrzDO.setIp(requestClientRealIP);
        }
        if(StringUtils.isNotBlank(fcxmid)) {
            BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, fcxmid);
            if (bdcXmDO != null) {
                BeanUtils.copyProperties(bdcXmDO, bdcCzrzDO);
                bdcCzrzDO.setLcmc(bdcXmDO.getGzldymc());
                bdcCzrzDO.setCzcs("证号:" + bdcXmDO.getBdcqzh() + " "
                        + BdcCzrzLxEnum.codeTodesc(czlx) +
                        "土地证由" + fcxmid + "变为" + tdxmid);
            }
        }
        bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
    }

    /**
     * @param tdxmid 土地项目ID
     * @param fwbdcdyh 当前房屋单元号
     * @param fzXmList 需要复制生成的土地项目集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  复制土地证以及对应限制权利信息
     */
    private BdcXmDO fzTdxx(String tdxmid,String fwbdcdyh,List<BdcXmDO> fzXmList){
        BdcXmDO tdcqXm =new BdcXmDO();
        //存储复制前xmid与复制后xmid的对照
        Map<String,String> xmidDz =new HashMap<>();
        if(StringUtils.isNotBlank(tdxmid) &&StringUtils.isNotBlank(fwbdcdyh)){
            //数据校验,房屋单元号不能存在bdclx为土地的数据
            BdcXmDO xmDO =new BdcXmDO();
            xmDO.setBdcdyh(fwbdcdyh);
            xmDO.setBdclx(CommonConstantUtils.DYBDCLX_CTD);
            List<BdcXmDO> xmList=entityMapper.selectByObj(xmDO);
            if(CollectionUtils.isNotEmpty(xmList)){
                throw new AppException(ErrorCode.SERVER_EX, "当前房屋存在不动产类型为土地的项目数据,请检查");
            }
            BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO =new BdcXnbdcdyhGxDO();
            bdcXnbdcdyhGxDO.setXnbdcdyhxmid(tdxmid);
            bdcXnbdcdyhGxDO.setGxlb(CommonConstantUtils.GXLB_FDPP);
            List<BdcXnbdcdyhGxDO> list=entityMapper.selectByObj(bdcXnbdcdyhGxDO);
            //根据原匹配关系的房屋单元号查询土地对应的所有项目
            if(CollectionUtils.isNotEmpty(list) &&StringUtils.isNotBlank(list.get(0).getBdcdyh())){
                Example example =new Example(BdcXnbdcdyhGxDO.class);
                List<Object> gxlbList =new ArrayList<>();
                //原关系类别为房地匹配或复制土地项目匹配
                gxlbList.add(CommonConstantUtils.GXLB_FDPP);
                gxlbList.add(CommonConstantUtils.GXLB_FZTDXMPP);
                example.createCriteria().andEqualTo("bdcdyh",list.get(0).getBdcdyh()).andIn("gxlb",gxlbList);
                list=entityMapper.selectByExample(example);
                if(CollectionUtils.isNotEmpty(list)){
                    List<BdcYwxxDTO> bdcYwxxDTOList =new ArrayList<>();
                    for(BdcXnbdcdyhGxDO xnbdcdyhGxDO:list) {
                        //复制
                        BdcCopyReplaceYwxxDTO bdcCopyReplaceYwxxDTO =new BdcCopyReplaceYwxxDTO();
                        bdcCopyReplaceYwxxDTO.setQueryXmid(xnbdcdyhGxDO.getXnbdcdyhxmid());
                        bdcCopyReplaceYwxxDTO.setBdcdyh(fwbdcdyh);
                        bdcCopyReplaceYwxxDTO.setCopyDjb(false);
                        bdcCopyReplaceYwxxDTO.setCopyZs(false);
                        BdcYwxxDTO bdcYwxxDTO =initDataService.copyYwxx(bdcCopyReplaceYwxxDTO);

                        if(bdcYwxxDTO != null) {
                            if (StringUtils.equals(xnbdcdyhGxDO.getXnbdcdyhxmid(), tdxmid)) {
                                tdcqXm = bdcYwxxDTO.getBdcXm();
                            }
                            fzXmList.add(bdcYwxxDTO.getBdcXm());
                            //存储复制前和复制后xmid
                            xmidDz.put(xnbdcdyhGxDO.getXnbdcdyhxmid(),bdcYwxxDTO.getBdcXm().getXmid());
                            bdcYwxxDTOList.add(bdcYwxxDTO);
                        }
                    }
                    for(BdcYwxxDTO bdcYwxxDTO:bdcYwxxDTOList){
                        if(CollectionUtils.isNotEmpty(bdcYwxxDTO.getBdcXmLsgxList())){
                            for(BdcXmLsgxDO xmLsgxDO:bdcYwxxDTO.getBdcXmLsgxList()){
                                //新历史关系根据xmid对照处理
                                if(StringUtils.isNotBlank(xmLsgxDO.getYxmid()) &&xmidDz.containsKey(xmLsgxDO.getYxmid())){
                                    xmLsgxDO.setYxmid(xmidDz.get(xmLsgxDO.getYxmid()));
                                }
                            }
                        }
                    }
                    //数据入库
                    initDataService.saveOrUpdateYwsjPl(null,bdcYwxxDTOList,true);
                    LOGGER.info("复制土地证信息,房屋单元号：{},复制土地证信息集合:{}",fwbdcdyh,bdcYwxxDTOList);
                    //记录操作日志
                    BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                    bdcCzrzDO.setRzid(UUIDGenerator.generate16());
                    bdcCzrzDO.setCzsj(new Date());
                    bdcCzrzDO.setCzzt(1);
                    bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_PP.key());
                    bdcCzrzDO.setCzyy(BdcCzrzLxEnum.CZRZ_CZLX_PP.value());
                    bdcCzrzDO.setCzr(userManagerUtils.getCurrentUserName());
                    bdcCzrzDO.setCzcs("房地匹配,fwbdcdyh:"+fwbdcdyh+"复制土地证前后xmid"+ JSONObject.toJSONString(xmidDz));
                    entityMapper.saveOrUpdate(bdcCzrzDO, bdcCzrzDO.getRzid());
                }
            }
        }
        return tdcqXm;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  删除复制土地证信息和关系数据
     */
    private void deleteFztdzxx(String tdxmid){
        if(StringUtils.isNotBlank(tdxmid)){
            BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO =new BdcXnbdcdyhGxDO();
            bdcXnbdcdyhGxDO.setXnbdcdyhxmid(tdxmid);
            List<BdcXnbdcdyhGxDO> list=entityMapper.selectByObj(bdcXnbdcdyhGxDO);
            //查询土地对应的所有项目
            if(CollectionUtils.isNotEmpty(list) &&StringUtils.isNotBlank(list.get(0).getBdcdyh())){
                bdcXnbdcdyhGxDO =new BdcXnbdcdyhGxDO();
                bdcXnbdcdyhGxDO.setBdcdyh(list.get(0).getBdcdyh());
                bdcXnbdcdyhGxDO.setGxlb(CommonConstantUtils.GXLB_FZTDXMPP);
                list=entityMapper.selectByObj(bdcXnbdcdyhGxDO);
                if(CollectionUtils.isNotEmpty(list)){
                    List<String> xmids =list.stream().map(BdcXnbdcdyhGxDO::getXnbdcdyhxmid).collect(Collectors.toList());
                    if(CollectionUtils.isNotEmpty(xmids)){
                        List<BdcXmDO> bdcXmDOList =bdcXmService.listBdcXmByXmids(xmids);
                        //删除业务数据
                        try {
                            initDataService.deleteYwsj(bdcXmDOList, false);
                        }catch (Exception e){
                            throw new AppException(ErrorCode.SERVER_EX, "取消匹配删除业务数据失败");
                        }
                        //删除关系数据
                        entityMapper.delete(bdcXnbdcdyhGxDO);
                    }
                    LOGGER.info("删除复制土地证信息和关系数据,房屋单元号：{},删除土地证数据集合:{}",list.get(0).getBdcdyh(),xmids);
                    //记录操作日志
                    BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                    bdcCzrzDO.setRzid(UUIDGenerator.generate16());
                    bdcCzrzDO.setCzsj(new Date());
                    bdcCzrzDO.setCzzt(1);
                    bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_QXPP.key());
                    bdcCzrzDO.setCzyy(BdcCzrzLxEnum.CZRZ_CZLX_QXPP.value());
                    bdcCzrzDO.setCzr(userManagerUtils.getCurrentUserName());
                    bdcCzrzDO.setCzcs("房地取消匹配,fwbdcdyh:"+list.get(0).getBdcdyh()+"删除复制土地证xmid"+ JSONObject.toJSONString(xmids));
                    entityMapper.saveOrUpdate(bdcCzrzDO, bdcCzrzDO.getRzid());
                }

            }

        }
    }

}
