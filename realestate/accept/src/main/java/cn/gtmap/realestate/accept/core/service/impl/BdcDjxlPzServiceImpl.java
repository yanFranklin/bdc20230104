package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.accept.core.mapper.BdcDjxlPzMapper;
import cn.gtmap.realestate.accept.core.service.BdcDjxlPzService;
import cn.gtmap.realestate.accept.core.service.BdcSlXztzPzService;
import cn.gtmap.realestate.accept.core.service.BdcSlZjjgService;
import cn.gtmap.realestate.accept.service.BdcQllxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcJrywpzJcjgDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXztzPzDO;
import cn.gtmap.realestate.common.core.domain.exchange.BdcXtJrDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.DjxlPzCxQO;
import cn.gtmap.realestate.common.core.qo.exchange.openapi.BdcXtJrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDjxlPzQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.BdcXtJrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/12
 * @description ???????????????????????????????????????
 */
@Service
public class BdcDjxlPzServiceImpl implements BdcDjxlPzService {

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    protected Repo repo;
    @Autowired
    private BdcDjxlPzMapper bdcDjxlPzMapper;
    @Autowired
    BdcSlZjjgService bdcSlZjjgService;

    @Autowired
    BdcSlXztzPzService bdcSlXztzPzService;

    @Autowired
    BdcQllxService bdcQllxService;

    /**
     * ??????????????????????????????yqllx?????????
     */
    @Value("#{'${djxlpz.ppyqllx.gzldyid:}'.split(',')}")
    private List<String> ppyqllxLcList;

    /**
     * ????????????-?????????????????????
     */
    @Value("#{'${sbjy.fdjywlc.gzldyid:}'.split(',')}")
    private List<String> fdjywGzldyidList;

    @Autowired
    BdcXtJrFeignService bdcXtJrFeignService;

    @Autowired
    WorkFlowUtils workFlowUtils;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    /**
     * @param gzldyid ???????????????ID
     * @param dyhqllx ?????????????????????
     * @return ???????????????????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????ID?????????????????????????????????
     */
    @Override
    public List<BdcDjxlPzDO> listBdcDjxlPzByGzldyid(String gzldyid, Integer dyhqllx, Integer yqllx) {
        List<String> fdjywlcList = bdcSlZjjgService.getFdjywlcDyidList("");
        if (CollectionUtils.isNotEmpty(fdjywlcList) && fdjywlcList.contains(gzldyid)) {
            //?????????????????????????????????dyhqllx??????,bdc_djxl_pz ??????????????????
            dyhqllx = null;
        }
        List<BdcDjxlPzDO> bdcDjxlPzDOList = new ArrayList<>();
        Example example = new Example(BdcDjxlPzDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(gzldyid)) {
            criteria.andEqualTo("gzldyid", gzldyid);
            if (dyhqllx != null) {
                criteria.andEqualTo("dyhqllx", dyhqllx);
            }
            bdcDjxlPzDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isNotEmpty(fdjywlcList) && fdjywlcList.contains(gzldyid) && CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
            List<BdcDjxlPzDO> newbdcDjxlPzDOList = new ArrayList<>();
            newbdcDjxlPzDOList.add(bdcDjxlPzDOList.get(0));
            return newbdcDjxlPzDOList;
        }
        if (CollectionUtils.isEmpty(bdcDjxlPzDOList)) {
            bdcDjxlPzDOList = Collections.emptyList();
        }
        if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
            if (CollectionUtils.isNotEmpty(ppyqllxLcList) && ppyqllxLcList.contains(gzldyid)) {
                bdcDjxlPzDOList = bdcDjxlPzDOList.stream().filter(bdcDjxlPzDO -> (bdcDjxlPzDO.getYqllx() == null && yqllx == null) || (bdcDjxlPzDO.getYqllx() != null && bdcDjxlPzDO.getYqllx().equals(yqllx))).collect(Collectors.toList());
            } else {
                //???????????????????????????????????????
                bdcDjxlPzDOList = bdcDjxlPzDOList.stream().filter(bdcDjxlPzDO -> bdcDjxlPzDO.getYqllx() == null ||yqllx ==null || bdcDjxlPzDO.getYqllx().equals(yqllx)).collect(Collectors.toList());

            }
        }
        if (bdcDjxlPzDOList.size() > 1) {
            Collections.sort(bdcDjxlPzDOList);
        }
        return bdcDjxlPzDOList;
    }

    @Override
    public BdcDjxlPzDO queryBdcDjxlPzDOByPzid(String pzid) {
        if (StringUtils.isBlank(pzid)) {
            throw new MissingArgumentException("pzid");
        }
        return entityMapper.selectByPrimaryKey(BdcDjxlPzDO.class, pzid);
    }

    /**
     * @param gzldyid ???????????????ID
     * @return ?????????????????????????????????
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description ?????????????????????ID???????????????????????????????????????
     */
    @Override
    public List<BdcDjxlPzDO> queryBdcDjxlPzByGzldyid(String gzldyid) {
        if (StringUtils.isBlank(gzldyid)) {
            throw new MissingArgumentException("gzldyid");
        }
        List<BdcDjxlPzDO> bdcDjxlPzDOList = null;
        Example example = new Example(BdcDjxlPzDO.class);
        example.createCriteria().andEqualTo("gzldyid", gzldyid);
        bdcDjxlPzDOList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isEmpty(bdcDjxlPzDOList)) {
            bdcDjxlPzDOList = Collections.emptyList();
        }
        return bdcDjxlPzDOList;
    }

    @Override
    public List<BdcDjxlPzDO> listBdcDjxlPz(BdcDjxlPzDO bdcDjxlPzDO) {
        if (bdcDjxlPzDO == null) {
            throw new AppException("????????????????????????????????????????????????");
        }
        return entityMapper.selectByObj(bdcDjxlPzDO);

    }

    /**
     * @param bdcDjxlPzDO ???????????????????????????
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????????????????
     */
    @Override
    public int saveBdcDjxlPzDO(@RequestBody BdcDjxlPzDO bdcDjxlPzDO) {
        if (bdcDjxlPzDO == null) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        if (StringUtils.isBlank(bdcDjxlPzDO.getPzid())) {
            bdcDjxlPzDO.setPzid(UUIDGenerator.generate16());
        }
        return entityMapper.saveOrUpdate(bdcDjxlPzDO, bdcDjxlPzDO.getPzid());

    }

    /**
     * @param bdcDjxlPzDOList ??????????????????
     * @return ????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    @Override
    public int deleteBdcDjxlPz(List<BdcDjxlPzDO> bdcDjxlPzDOList) {
        int count = 0;
        if (CollectionUtils.isEmpty(bdcDjxlPzDOList)) {
            return count;
        }
        for (BdcDjxlPzDO bdcDjxlPzDO : bdcDjxlPzDOList) {
            count += entityMapper.delete(bdcDjxlPzDO);
        }
        return count;
    }

    /**
     * @param bdcDjxlPzJson ??????????????????
     * @return ????????????????????????
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    @Override
    public Page<BdcDjxlPzDO> listBdcDjxlPzByPage(Pageable pageable, String bdcDjxlPzJson) {
        BdcDjxlPzQO bdcDjxlPzQO = new BdcDjxlPzQO();
        if (StringUtils.isNotBlank(bdcDjxlPzJson)) {
            bdcDjxlPzQO = JSONObject.parseObject(bdcDjxlPzJson, BdcDjxlPzQO.class);
        }
        return repo.selectPaging("listBdcDjxlPzByPage", bdcDjxlPzQO, pageable);
    }

    @Override
    public int queryDjxlPzMaxSxh(BdcDjxlPzDO bdcDjxlPzDO) {
        if (null == bdcDjxlPzDO || StringUtils.isBlank(bdcDjxlPzDO.getGzldyid()) || bdcDjxlPzDO.getDyhqllx() == null) {
            throw new MissingArgumentException("???????????????????????????");
        }
        return bdcDjxlPzMapper.queryDjxlPzMaxSxh(bdcDjxlPzDO);

    }

    /**
     * @param gzldyid
     * @param djxl
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ????????????????????????
     */
    @Override
    public BdcDjxlPzDO queryBdcDjxlPzByGzldyidAndDjxl(String gzldyid, String djxl) {
        if (StringUtils.isAnyBlank(gzldyid, djxl)) {
            throw new MissingArgumentException("gzldyid,djxl");
        }
        Example example = new Example(BdcDjxlPzDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gzldyid", gzldyid);
        criteria.andEqualTo("djxl", djxl);
        List<BdcDjxlPzDO> bdcDjxlPzDOList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isEmpty(bdcDjxlPzDOList)) {
            return new BdcDjxlPzDO();
        }
        return bdcDjxlPzDOList.get(0);
    }

    /**
     * @param pzid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????
     * @date : 2022/7/8 10:18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int sbpzjy(String pzid) {
        int count = 0;
        if (StringUtils.isNotBlank(pzid)) {
            BdcDjxlPzDO bdcDjxlPzDO = this.queryBdcDjxlPzDOByPzid(pzid);
            //??????????????????????????????????????????id????????????????????????????????????
            if (Objects.nonNull(bdcDjxlPzDO)) {
                Example example = new Example(BdcJrywpzJcjgDO.class);
                example.createCriteria().andEqualTo("pzid", pzid);
                entityMapper.deleteByExample(example);
                return checkJrPz(bdcDjxlPzDO);
            }
        } else {
            //???????????????????????????
            //?????????????????????????????????????????????????????????????????????
            entityMapper.deleteByExample(new Example(BdcJrywpzJcjgDO.class));
            List<BdcDjxlPzDO> bdcDjxlPzDOList = entityMapper.selectByExample(new Example(BdcDjxlPzDO.class));
            if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
                for (BdcDjxlPzDO bdcDjxlPzDO : bdcDjxlPzDOList) {
                    count += checkJrPz(bdcDjxlPzDO);
                }
            }
        }
        return count;
    }

    /**
     * @param bdcDjxlPzQO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????????????????id
     * @date : 2022/11/29 17:07
     */
    @Override
    public List<String> listLcdyids(BdcDjxlPzQO bdcDjxlPzQO) {
        if (StringUtils.isNotBlank(bdcDjxlPzQO.getGzldyid()) || Objects.nonNull(bdcDjxlPzQO.getSfsb())) {
            return bdcDjxlPzMapper.listSfsbLcdyids(bdcDjxlPzQO);
        }
        return Collections.emptyList();
    }

    @Override
    public List<BdcDjxlPzDO> queryBdcDjxlPz(DjxlPzCxQO djxlPzCxQO){
        if(StringUtils.isBlank(djxlPzCxQO.getGzldyid()) ||StringUtils.isBlank(djxlPzCxQO.getBdcdyh())){
            return new ArrayList<>();
        }
        //??????dyhqllx
        Integer dyhqllx;
        if (djxlPzCxQO.getYqllx() != null && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, djxlPzCxQO.getYqllx())) {
            dyhqllx = djxlPzCxQO.getYqllx();
        } else {
            dyhqllx = bdcQllxService.getQllxByBdcdyh(djxlPzCxQO.getBdcdyh(), djxlPzCxQO.getDyhcxlx());
        }
        List<BdcDjxlPzDO> bdcDjxlPzDOList;
        if (djxlPzCxQO.getQllx() == null && StringUtils.isBlank(djxlPzCxQO.getDjxl())) {
            bdcDjxlPzDOList = listBdcDjxlPzByGzldyid(djxlPzCxQO.getGzldyid(), dyhqllx, djxlPzCxQO.getYqllx());
        }else{
            BdcDjxlPzDO bdcDjxlPzDO = new BdcDjxlPzDO();
            bdcDjxlPzDO.setGzldyid(djxlPzCxQO.getGzldyid());
            bdcDjxlPzDO.setQllx(djxlPzCxQO.getQllx());
            bdcDjxlPzDO.setDjxl(djxlPzCxQO.getDjxl());
            bdcDjxlPzDO.setDyhqllx(dyhqllx);
            bdcDjxlPzDOList = listBdcDjxlPz(bdcDjxlPzDO);
        }
        //????????????????????????
        List<BdcDjxlPzDO> bdcDjxlPzDOS =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
            for (BdcDjxlPzDO bdcDjxlPzDO : bdcDjxlPzDOList) {
                if (bdcDjxlPzDO.getYgdjzl() != null) {
                    if (CommonConstantUtils.QLLX_YG_DM.equals(djxlPzCxQO.getYqllx()) && StringUtils.isNotBlank(djxlPzCxQO.getYxmid())) {
                        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(djxlPzCxQO.getYxmid());
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
        if (CollectionUtils.isEmpty(bdcDjxlPzDOS)) {
            if (djxlPzCxQO.getQllx() == null && StringUtils.isBlank(djxlPzCxQO.getDjxl())) {
                throw new AppException("?????????????????????" + djxlPzCxQO.getBdcdyh() + "??????????????????????????????BDC_DJXL_PZ????????????,??????dyhqllx=" + dyhqllx + "??????????????????");
            }else{
                throw new AppException("?????????????????????" + djxlPzCxQO.getBdcdyh() + "??????????????????????????????BDC_DJXL_PZ????????????,??????dyhqllx=" + dyhqllx +"djxl:"+djxlPzCxQO.getDjxl()+"qllx:"+djxlPzCxQO.getQllx() +"??????????????????");
            }
        }
        return bdcDjxlPzDOS;
    }

    /**
     * @param bdcDjxlPzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????
     * @date : 2022/7/8 10:26
     */
    private int checkJrPz(BdcDjxlPzDO bdcDjxlPzDO) {
        int count = 0;
        //1. ????????????????????????????????????????????????????????????????????????
        BdcSlXztzPzDO bdcSlXztzPzDO = bdcSlXztzPzService.queryBdcSlXztzPzDOByGzldyid(bdcDjxlPzDO.getGzldyid());
        if (CollectionUtils.isEmpty(fdjywGzldyidList) || (CollectionUtils.isNotEmpty(fdjywGzldyidList) && !fdjywGzldyidList.contains(bdcDjxlPzDO.getGzldyid()))) {
            if (Objects.isNull(bdcDjxlPzDO.getSfsb())) {
                //??????????????????
                BdcJrywpzJcjgDO bdcJrywpzJcjgDO = generateJcjg(bdcDjxlPzDO, "?????????????????????????????????", Objects.nonNull(bdcSlXztzPzDO) ? bdcSlXztzPzDO.getBdcdyfwlx() : "");
                count += entityMapper.insertSelective(bdcJrywpzJcjgDO);
            }
            if (Objects.nonNull(bdcDjxlPzDO.getSfsb()) && CommonConstantUtils.SF_S_DM.equals(bdcDjxlPzDO.getSfsb())) {
                //2.??????????????????????????????bdcdyfwlx
                //?????????????????????????????????
                BdcXtJrQO bdcXtJrQO = new BdcXtJrQO();
                bdcXtJrQO.setDjxl(bdcDjxlPzDO.getDjxl());
                if (Objects.nonNull(bdcDjxlPzDO.getQllx())) {
                    bdcXtJrQO.setQllx(String.valueOf(bdcDjxlPzDO.getQllx()));
                }
                if (Objects.nonNull(bdcDjxlPzDO.getBdclx())) {
                    bdcXtJrQO.setBdclx(String.valueOf(bdcDjxlPzDO.getBdclx()));
                }
                if (Objects.nonNull(bdcSlXztzPzDO) && StringUtils.isNotBlank(bdcSlXztzPzDO.getBdcdyfwlx())) {
                    for (String s : bdcSlXztzPzDO.getBdcdyfwlx().split(CommonConstantUtils.ZF_YW_DH)) {
                        String bdcdyfwlx = "";
                        if (StringUtils.equals("hs", s) || StringUtils.equals("ychs", s)) {
                            bdcdyfwlx = "4";
                            bdcXtJrQO.setBdcdyfwlxList(Collections.singletonList("4"));
                        } else if (StringUtils.equals("ljz", s)) {
                            bdcdyfwlx = "2";
                            bdcXtJrQO.setBdcdyfwlxList(Collections.singletonList("2"));
                        } else if (StringUtils.equals("xmxx", s)) {
                            bdcdyfwlx = "1";
                            bdcXtJrQO.setBdcdyfwlxList(Collections.singletonList("1"));
                        }
                        List<BdcXtJrDO> bdcXtJrDOList = bdcXtJrFeignService.listBdcXtJr(bdcXtJrQO);
                        if (CollectionUtils.isEmpty(bdcXtJrDOList)) {
                            //????????????????????????
                            BdcJrywpzJcjgDO bdcJrywpzJcjgDO = generateJcjg(bdcDjxlPzDO, "??????????????????????????????????????????" + bdcdyfwlx + "?????????????????????????????????bdc_xt_jr???", bdcdyfwlx);
                            count += entityMapper.insertSelective(bdcJrywpzJcjgDO);
                        } else {
                            if (CollectionUtils.size(bdcXtJrDOList) > 1) {
                                //???????????????????????????????????????
                                BdcJrywpzJcjgDO bdcJrywpzJcjgDO = generateJcjg(bdcDjxlPzDO, "????????????????????????" + bdcDjxlPzDO.getDjxl() + "????????????" + bdcDjxlPzDO.getQllx() + "???????????????" + bdcDjxlPzDO.getBdclx() + "???????????????????????????" + bdcdyfwlx + "????????????????????????????????????????????????????????????", bdcdyfwlx);
                                count += entityMapper.insertSelective(bdcJrywpzJcjgDO);
                            } else {
                                for (BdcXtJrDO bdcXtJrDO : bdcXtJrDOList) {
                                    //????????????????????? ???ywdm ???ywfwdm ???????????????
                                    Map ywdmMap = bdcXtJrFeignService.queryJrYwdm(bdcXtJrDO.getYwfwdm());
                                    if (!StringUtils.equals(bdcXtJrDO.getYwdm(), MapUtils.getString(ywdmMap, "ywdm", ""))) {
                                        //??????????????????
                                        BdcJrywpzJcjgDO bdcJrywpzJcjgDO = generateJcjg(bdcDjxlPzDO, "???????????????????????????????????????" + bdcdyfwlx + "???????????????????????????????????????????????????,?????????????????????" + MapUtils.getString(ywdmMap, "ywdm", ""), bdcdyfwlx);
                                        count += entityMapper.insertSelective(bdcJrywpzJcjgDO);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    private BdcJrywpzJcjgDO generateJcjg(BdcDjxlPzDO bdcDjxlPzDO, String jcjg, String bdcdyfwlx) {
        BdcJrywpzJcjgDO bdcJrywpzJcjgDO = new BdcJrywpzJcjgDO();
        bdcJrywpzJcjgDO.setId(UUIDGenerator.generate16());
        bdcJrywpzJcjgDO.setPzid(bdcDjxlPzDO.getPzid());
        bdcJrywpzJcjgDO.setGzldyid(bdcDjxlPzDO.getGzldyid());
        bdcJrywpzJcjgDO.setDjxl(bdcDjxlPzDO.getDjxl());
        if (Objects.nonNull(bdcDjxlPzDO.getQllx())) {
            bdcJrywpzJcjgDO.setQllx(String.valueOf(bdcDjxlPzDO.getQllx()));
        }
        if (Objects.nonNull(bdcDjxlPzDO.getBdclx())) {
            bdcJrywpzJcjgDO.setBdclx(bdcDjxlPzDO.getBdclx().toString());
        }
        //??????????????????
        ProcessDefData processDefData = workFlowUtils.getAllProcessDefDataByGzldyid(bdcDjxlPzDO.getGzldyid());
        if (Objects.nonNull(processDefData)) {
            bdcJrywpzJcjgDO.setLcmc(processDefData.getName());
        }
        bdcJrywpzJcjgDO.setBdcdyfwlx(bdcdyfwlx);
        bdcJrywpzJcjgDO.setJcjg(jcjg);
        return bdcJrywpzJcjgDO;
    }
}
