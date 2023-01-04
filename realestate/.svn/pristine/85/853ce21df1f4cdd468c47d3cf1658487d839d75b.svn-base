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
 * @description 不动产登记小类配置数据服务
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
     * 登记小类配置需要匹配yqllx的流程
     */
    @Value("#{'${djxlpz.ppyqllx.gzldyid:}'.split(',')}")
    private List<String> ppyqllxLcList;

    /**
     * 上报校验-非登记业务流程
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
     * @param gzldyid 工作流定义ID
     * @param dyhqllx 单元号权利类型
     * @return 不动产登记小类配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产登记小类配置
     */
    @Override
    public List<BdcDjxlPzDO> listBdcDjxlPzByGzldyid(String gzldyid, Integer dyhqllx, Integer yqllx) {
        List<String> fdjywlcList = bdcSlZjjgService.getFdjywlcDyidList("");
        if (CollectionUtils.isNotEmpty(fdjywlcList) && fdjywlcList.contains(gzldyid)) {
            //非登记业务流程无需根据dyhqllx过滤,bdc_djxl_pz 配置一条即可
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
                //原权利类型配置为空或者相等
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
     * @param gzldyid 工作流定义ID
     * @return 不动产受理选择台账配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产受理选择台账配置
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
            throw new AppException("获取登记小类配置的参数不能为空！");
        }
        return entityMapper.selectByObj(bdcDjxlPzDO);

    }

    /**
     * @param bdcDjxlPzDO 不动产登记小类配置
     * @return 保存数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产登记小类配置
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
     * @param bdcDjxlPzDOList 登记小类配置
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除登记小类配置
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
     * @param bdcDjxlPzJson 登记小类配置
     * @return 登记小类配置分页
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记小类配置分页
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
            throw new MissingArgumentException("缺失顺序号查询参数");
        }
        return bdcDjxlPzMapper.queryDjxlPzMaxSxh(bdcDjxlPzDO);

    }

    /**
     * @param gzldyid
     * @param djxl
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记小类配置
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
     * @description 上报配置校验
     * @date : 2022/7/8 10:18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int sbpzjy(String pzid) {
        int count = 0;
        if (StringUtils.isNotBlank(pzid)) {
            BdcDjxlPzDO bdcDjxlPzDO = this.queryBdcDjxlPzDOByPzid(pzid);
            //先删除校验结果表的这一条配置id数据，如果校验失败，新增
            if (Objects.nonNull(bdcDjxlPzDO)) {
                Example example = new Example(BdcJrywpzJcjgDO.class);
                example.createCriteria().andEqualTo("pzid", pzid);
                entityMapper.deleteByExample(example);
                return checkJrPz(bdcDjxlPzDO);
            }
        } else {
            //查询所有的登记流程
            //先删除校验结果表所有数据，再插入校验失败的数据
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
     * @description 根据查询条件查询流程定义id
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
        //确定dyhqllx
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
        //匹配预告登记种类
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
                throw new AppException("不动产单元号：" + djxlPzCxQO.getBdcdyh() + "不动产登记小类配置（BDC_DJXL_PZ）未配置,当前dyhqllx=" + dyhqllx + "请联系管理员");
            }else{
                throw new AppException("不动产单元号：" + djxlPzCxQO.getBdcdyh() + "不动产登记小类配置（BDC_DJXL_PZ）未配置,当前dyhqllx=" + dyhqllx +"djxl:"+djxlPzCxQO.getDjxl()+"qllx:"+djxlPzCxQO.getQllx() +"请联系管理员");
            }
        }
        return bdcDjxlPzDOS;
    }

    /**
     * @param bdcDjxlPzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 检查接入配置
     * @date : 2022/7/8 10:26
     */
    private int checkJrPz(BdcDjxlPzDO bdcDjxlPzDO) {
        int count = 0;
        //1. 检查是否是否配置了是否上报，所有登记业务必须配置
        BdcSlXztzPzDO bdcSlXztzPzDO = bdcSlXztzPzService.queryBdcSlXztzPzDOByGzldyid(bdcDjxlPzDO.getGzldyid());
        if (CollectionUtils.isEmpty(fdjywGzldyidList) || (CollectionUtils.isNotEmpty(fdjywGzldyidList) && !fdjywGzldyidList.contains(bdcDjxlPzDO.getGzldyid()))) {
            if (Objects.isNull(bdcDjxlPzDO.getSfsb())) {
                //记录问题数据
                BdcJrywpzJcjgDO bdcJrywpzJcjgDO = generateJcjg(bdcDjxlPzDO, "当前业务未配置是否上报", Objects.nonNull(bdcSlXztzPzDO) ? bdcSlXztzPzDO.getBdcdyfwlx() : "");
                count += entityMapper.insertSelective(bdcJrywpzJcjgDO);
            }
            if (Objects.nonNull(bdcDjxlPzDO.getSfsb()) && CommonConstantUtils.SF_S_DM.equals(bdcDjxlPzDO.getSfsb())) {
                //2.先查询选择台账配置的bdcdyfwlx
                //查询是否配置了接入信息
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
                            //没有配置接入信息
                            BdcJrywpzJcjgDO bdcJrywpzJcjgDO = generateJcjg(bdcDjxlPzDO, "当前业务不动产单元房屋类型为" + bdcdyfwlx + "未配置接入信息，请检查bdc_xt_jr表", bdcdyfwlx);
                            count += entityMapper.insertSelective(bdcJrywpzJcjgDO);
                        } else {
                            if (CollectionUtils.size(bdcXtJrDOList) > 1) {
                                //配置接入信息只能有一条数据
                                BdcJrywpzJcjgDO bdcJrywpzJcjgDO = generateJcjg(bdcDjxlPzDO, "当前业务登记小类" + bdcDjxlPzDO.getDjxl() + "权力类型" + bdcDjxlPzDO.getQllx() + "不动产类型" + bdcDjxlPzDO.getBdclx() + "不动产单元房屋类型" + bdcdyfwlx + "查询接入业务配置存在多条数据，不允许重复", bdcdyfwlx);
                                count += entityMapper.insertSelective(bdcJrywpzJcjgDO);
                            } else {
                                for (BdcXtJrDO bdcXtJrDO : bdcXtJrDOList) {
                                    //检查每一条数据 的ywdm 和ywfwdm 是否能对应
                                    Map ywdmMap = bdcXtJrFeignService.queryJrYwdm(bdcXtJrDO.getYwfwdm());
                                    if (!StringUtils.equals(bdcXtJrDO.getYwdm(), MapUtils.getString(ywdmMap, "ywdm", ""))) {
                                        //插入问题数据
                                        BdcJrywpzJcjgDO bdcJrywpzJcjgDO = generateJcjg(bdcDjxlPzDO, "当前业务不动产单元房屋类型" + bdcdyfwlx + "配置的业务服务代码和服务代码不对应,服务代码应该为" + MapUtils.getString(ywdmMap, "ywdm", ""), bdcdyfwlx);
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
        //设置流程名称
        ProcessDefData processDefData = workFlowUtils.getAllProcessDefDataByGzldyid(bdcDjxlPzDO.getGzldyid());
        if (Objects.nonNull(processDefData)) {
            bdcJrywpzJcjgDO.setLcmc(processDefData.getName());
        }
        bdcJrywpzJcjgDO.setBdcdyfwlx(bdcdyfwlx);
        bdcJrywpzJcjgDO.setJcjg(jcjg);
        return bdcJrywpzJcjgDO;
    }
}
