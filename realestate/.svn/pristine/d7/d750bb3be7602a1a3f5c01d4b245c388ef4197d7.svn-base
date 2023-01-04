package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.service.feign.building.HfXzqFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.*;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.FwqlCxResponseSdxx;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.GrdacxService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-26
 * @description 外网申请 个人档案查询服务
 */
@Service
public class GrdacxServiceImpl implements GrdacxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrdacxServiceImpl.class);


    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcDjbxxFeignService bdcDjbxxFeignService;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private HfXzqFeignService hfXzqFeignService;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Autowired
    private BdcZdCache bdcZdCache;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    //是否查询土地信息
    @Value("${grdacx.queryJsyd:true}")
    private Boolean queryJsyd;

    /**
     * @param requestBody
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxSqxx>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据权利人证件号 查询档案信息
     */
    @Override
    public List<GrdacxSqxx> getSqxx(GrdacxRequestBody requestBody) {
        List<GrdacxSqxx> list = new ArrayList<>();
        List<BdcXmDO> xmDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(requestBody.getQlrmc())
                || StringUtils.isNotBlank(requestBody.getQlrzjh())) {
            // 增肌15  18位 QLR 转换
            String revertZjhs = "";
            if (StringUtils.isNotBlank(requestBody.getQlrzjh())) {
                revertZjhs = CardNumberTransformation.zjhTransformation(requestBody.getQlrzjh());
                String[] zjhArr = revertZjhs.split(",");
            }

//            List<BdcQlrDO> qlrList = commonService.listBdcQlrByQlrAndZjhArr(requestBody.getQlrmc(), Arrays.asList(zjhArr), Constants.QLRLB_QLR);
            List<BdcQlrDO> qlrList = bdcXmMapper.queryQlrByZjhWitjQjgldm(requestBody.getQlrmc(), revertZjhs, Constants.QLRLB_QLR, requestBody.getXzqdm());
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (BdcQlrDO qlr : qlrList) {
                    if (StringUtils.isNotBlank(qlr.getXmid())) {
                        if (StringUtils.isBlank(requestBody.getXmid()) ||
                                (StringUtils.isNotBlank(requestBody.getXmid())
                                        && StringUtils.equals(qlr.getXmid(), requestBody.getXmid()))) {
                            // 单个处理 SQXX
                            GrdacxSqxx sqxx = getSqxxByXmid(qlr.getXmid(), qlr.getBdcqzh());
                            if (sqxx != null) {
                                list.add(sqxx);
                            }
                        }
                    }
                }
            }
        } else if (StringUtils.isNotBlank(requestBody.getXmid())) {
            // 单个处理 SQXX
            GrdacxSqxx sqxx = getSqxxByXmid(requestBody.getXmid(), null);
            if (sqxx != null) {
                list.add(sqxx);
            }
        }
        return list;
    }

    /**
     * @param xmid
     * @return cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxSqxx
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据项目主键 获取申请信息
     */
    @Override
    public GrdacxSqxx getSqxxByXmid(String xmid, String cqzh) {
        // 先查BDCXM
        BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(xmid);


        if (bdcXmDO != null) {
            //只查现势
            if (!CommonConstantUtils.QSZT_VALID.equals(bdcXmDO.getQszt())) {
                return null;
            }
            if (StringUtils.isBlank(cqzh)) {
                cqzh = bdcXmDO.getBdcqzh();
            }
            String qjgldm = "";
            //查询项目附表
            BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
            bdcXmFbQO.setXmid(xmid);
            List<BdcXmFbDO> bdcXmFbDOS = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
            if (CollectionUtils.isNotEmpty(bdcXmFbDOS)) {
                qjgldm = bdcXmFbDOS.get(0).getQjgldm();
            }
            // 查 FDCQ
            BdcFdcqDO bdcFdcqDO = getFdcqByXmid(xmid);
            // 查预告权利
            BdcYgDO bdcYgDO = getYgcqByXmid(xmid);
            // 查建设用地使用权利


            if (bdcFdcqDO != null) {
                GrdacxSqxx grdacxSqxx = new GrdacxSqxx();

                grdacxSqxx.setQjgldm(qjgldm);
                dozerBeanMapper.map(bdcXmDO, grdacxSqxx, "grdacx_bdcxm");
                dozerBeanMapper.map(bdcFdcqDO, grdacxSqxx, "grdacx_fdcq");
                handleTdsysj(bdcXmDO, grdacxSqxx);
                String bdcdyh = bdcXmDO.getBdcdyh();
                if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() > 19) {
                    // 根据BDCDYH 查 查封
                    grdacxSqxx.setCfxx(listCfxxByBdcdyh(bdcdyh, cqzh));
                    // 根据BDCDYH 查 抵押
                    grdacxSqxx.setDyxx(listDyxxByBdcdyh(bdcdyh, cqzh));
                    // 根据BDCDYH 查 异议
                    grdacxSqxx.setYyxx(listYyxxByBdcdyh(bdcdyh));
                    //根据bdcdyh 查预告
                    grdacxSqxx.setYgxx(listYgxxByBdcdyh(bdcdyh, cqzh));
                    //根据bdcdyh 查 居住
                    grdacxSqxx.setJzqxx(listJzxxByBdcdyh(bdcdyh));

                    //根据bdcdyh和xmid查询锁定信息
                    grdacxSqxx.setSdxx(getSdxx(bdcdyh, bdcXmDO.getXmid()));

                    grdacxSqxx.setCqzh(cqzh);
                    // 查询宗地基本信息
                    String zdbdcdyh = StringUtils.substring(bdcdyh, 0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                    BdcBdcdjbZdjbxxDO zdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(zdbdcdyh);
                    if (zdjbxxDO != null) {
                        dozerBeanMapper.map(zdjbxxDO, grdacxSqxx, "grdacx_djbxx");
                    }
                    if (StringUtils.isNotBlank(grdacxSqxx.getCqzh())) {
                        // 查询证书信息
                        BdcZsQO bdcZsQO = new BdcZsQO();
                        bdcZsQO.setBdcqzh(grdacxSqxx.getCqzh());
                        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
                        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                            grdacxSqxx.setCertid(bdcZsDOList.get(0).getZsid());
                        }
                    }
                }
                // 查询权利人
                grdacxSqxx.setQlr(listQlrList(xmid));
                String xzq = hfXzqFeignService.queryXzqByBdcdyh(bdcdyh, null, "");
                grdacxSqxx.setXzqhszdm(xzq);
                return grdacxSqxx;
            }
            //预告信息组织
            if (bdcYgDO != null) {
                GrdacxSqxx grdacxSqxx = new GrdacxSqxx();
                grdacxSqxx.setQjgldm(qjgldm);

                dozerBeanMapper.map(bdcXmDO, grdacxSqxx, "grdacx_bdcxm");
                dozerBeanMapper.map(bdcYgDO, grdacxSqxx, "grdacx_sqxx_yg");
                handleTdsysj(bdcXmDO, grdacxSqxx);
                String bdcdyh = bdcXmDO.getBdcdyh();
                if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() > 19) {
                    //根据不动产单元号查预抵押
                    grdacxSqxx.setDyxx(listYdyaxxByBdcdyh(bdcdyh, cqzh));

                    //根据bdcdyh 查预告
                    grdacxSqxx.setYgxx(listYgxxByBdcdyh(bdcdyh, cqzh));

                    // 根据BDCDYH 查 查封
                    grdacxSqxx.setCfxx(listCfxxByBdcdyh(bdcdyh, cqzh));
                    // 根据BDCDYH 查 抵押
                    if (CollectionUtils.isNotEmpty(grdacxSqxx.getDyxx())) {
                        grdacxSqxx.getDyxx().addAll(listDyxxByBdcdyh(bdcdyh, cqzh));
                    } else {
                        grdacxSqxx.setDyxx(listDyxxByBdcdyh(bdcdyh, cqzh));
                    }
                    // 根据BDCDYH 查 异议
                    grdacxSqxx.setYyxx(listYyxxByBdcdyh(bdcdyh));
                    //根据bdcdyh 查 居住
                    grdacxSqxx.setJzqxx(listJzxxByBdcdyh(bdcdyh));

                    //根据bdcdyh和xmid查询锁定信息
                    grdacxSqxx.setSdxx(getSdxx(bdcdyh, bdcXmDO.getXmid()));

                    grdacxSqxx.setCqzh(cqzh);
                    // 查询宗地基本信息
                    String zdbdcdyh = StringUtils.substring(bdcdyh, 0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                    BdcBdcdjbZdjbxxDO zdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(zdbdcdyh);
                    if (zdjbxxDO != null) {
                        dozerBeanMapper.map(zdjbxxDO, grdacxSqxx, "grdacx_djbxx");
                    }
                    if (StringUtils.isNotBlank(grdacxSqxx.getCqzh())) {
                        // 查询证书信息
                        BdcZsQO bdcZsQO = new BdcZsQO();
                        bdcZsQO.setBdcqzh(grdacxSqxx.getCqzh());
                        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
                        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                            grdacxSqxx.setCertid(bdcZsDOList.get(0).getZsid());
                        }
                    }
                }
                // 查询权利人
                grdacxSqxx.setQlr(listQlrList(xmid));
                String xzq = hfXzqFeignService.queryXzqByBdcdyh(bdcdyh, null, "");
                grdacxSqxx.setXzqhszdm(xzq);
                return grdacxSqxx;
            }
            if (queryJsyd) {
                LOGGER.info("查询土地信息：{},xmid:{}", queryJsyd, xmid);
                BdcJsydsyqDO bdcJsydsyqDO = getJsydsyqByXmid(xmid);
                // 建设用地信息组织
                if (Objects.nonNull(bdcJsydsyqDO)) {
                    GrdacxSqxx grdacxSqxx = new GrdacxSqxx();
                    grdacxSqxx.setQjgldm(qjgldm);

                    dozerBeanMapper.map(bdcXmDO, grdacxSqxx, "grdacx_bdcxm");
                    dozerBeanMapper.map(bdcJsydsyqDO, grdacxSqxx, "grdacx_jsytsyq");
                    handleTdsysj(bdcXmDO, grdacxSqxx);
                    String bdcdyh = bdcXmDO.getBdcdyh();
                    if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() > 19) {
                        // 根据BDCDYH 查 查封
                        grdacxSqxx.setCfxx(listCfxxByBdcdyh(bdcdyh, cqzh));
                        // 根据BDCDYH 查 抵押
                        grdacxSqxx.setDyxx(listDyxxByBdcdyh(bdcdyh, cqzh));
                        // 根据BDCDYH 查 异议
                        grdacxSqxx.setYyxx(listYyxxByBdcdyh(bdcdyh));
                        //根据bdcdyh 查预告
                        grdacxSqxx.setYgxx(listYgxxByBdcdyh(bdcdyh, cqzh));
                        //根据bdcdyh 查 居住
                        grdacxSqxx.setJzqxx(listJzxxByBdcdyh(bdcdyh));
                        //根据bdcdyh和xmid查询锁定信息
                        grdacxSqxx.setSdxx(getSdxx(bdcdyh, bdcXmDO.getXmid()));

                        grdacxSqxx.setCqzh(cqzh);
                        // 查询宗地基本信息
                        String zdbdcdyh = StringUtils.substring(bdcdyh, 0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                        BdcBdcdjbZdjbxxDO zdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(zdbdcdyh);
                        if (zdjbxxDO != null) {
                            dozerBeanMapper.map(zdjbxxDO, grdacxSqxx, "grdacx_djbxx");
                        }
                        if (StringUtils.isNotBlank(grdacxSqxx.getCqzh())) {
                            // 查询证书信息
                            BdcZsQO bdcZsQO = new BdcZsQO();
                            bdcZsQO.setBdcqzh(grdacxSqxx.getCqzh());
                            List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
                            if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                                grdacxSqxx.setCertid(bdcZsDOList.get(0).getZsid());
                            }
                        }
                    }
                    // 查询权利人
                    grdacxSqxx.setQlr(listQlrList(xmid));
                    String xzq = hfXzqFeignService.queryXzqByBdcdyh(bdcdyh, null, "");
                    grdacxSqxx.setXzqhszdm(xzq);
                    return grdacxSqxx;
                }
            }

        }
        return null;
    }

    private void handleTdsysj(BdcXmDO bdcXmDO, GrdacxSqxx grdacxSqxx) {
        /**
         * 根据bdclx设置时间
         * 如果是查出来的是房屋的不动产，那权利起始、结束时间 就置空，土地使用开始、结束时间返回土地的期限，
         * 如果查出来的是土地，那权利起始、结束时间 就返回土地权利期限，土地使用开始、结束时间这两字段返回空
         */
        if (Objects.nonNull(bdcXmDO.getBdclx()) && bdcXmDO.getBdclx() == 1) {
            grdacxSqxx.setTdsyksqx(null);
            grdacxSqxx.setTdsyjsqx(null);
        }else if (Objects.nonNull(bdcXmDO.getBdclx()) && bdcXmDO.getBdclx() == 2) {
            grdacxSqxx.setQlqssj(null);
            grdacxSqxx.setQljssj(null);
        }
    }

    /**
     * @param requestBody
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxSqxx>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 个人档案查询，查询房地产权和建设用地使用权（土地证） ，该接口不查预告信息
     */
    @Override
    public List<GrdacxSqxx> getSqxxWithoutYg(GrdacxRequestBody requestBody) {
        List<GrdacxSqxx> sqxxList = new ArrayList<>();
        List<BdcXmDO> xmDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(requestBody.getQlrmc())
                && StringUtils.isNotBlank(requestBody.getQlrzjh())) {
            // 增肌15  18位 QLR 转换
            String revertZjhs = CardNumberTransformation.zjhTransformation(requestBody.getQlrzjh());
            String[] zjhArr = revertZjhs.split(",");
            List<BdcQlrDO> qlrList = commonService.listBdcQlrByQlrAndZjhArr(requestBody.getQlrmc(), Arrays.asList(zjhArr), Constants.QLRLB_QLR);
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (BdcQlrDO qlr : qlrList) {
                    if (StringUtils.isNotBlank(qlr.getXmid())) {
                        if (StringUtils.isBlank(requestBody.getXmid()) ||
                                (StringUtils.isNotBlank(requestBody.getXmid())
                                        && StringUtils.equals(qlr.getXmid(), requestBody.getXmid()))) {
                            // 单个处理 SQXX
                            GrdacxSqxx sqxx = getSqxxByXmidWithoutYg(qlr.getXmid(), qlr.getBdcqzh());
                            if (sqxx != null) {
                                sqxxList.add(sqxx);
                            }
                        }
                    }
                }
            }
        } else if (StringUtils.isNotBlank(requestBody.getXmid())) {
            //根据xmid查询单个申请信息
            GrdacxSqxx sqxx = getSqxxByXmidWithoutYg(requestBody.getXmid(), null);
            if (null != sqxx) {
                sqxxList.add(sqxx);
            }
        }
        return sqxxList;
    }

    /**
     * @param xmid   xmid
     * @param bdcqzh bdcqzh
     * @return
     * @Date 2021/3/25
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private GrdacxSqxx getSqxxByXmidWithoutYg(String xmid, String bdcqzh) {
        //查项目信息
        BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(xmid);
        //只查现势数据
        if (!CommonConstantUtils.QSZT_VALID.equals(bdcXmDO.getQszt())) {
            return null;
        }
        if (StringUtils.isBlank(bdcqzh)) {
            bdcqzh = bdcXmDO.getBdcqzh();
        }
        if (null != bdcXmDO) {
            //查fdcq
            BdcFdcqDO fdcqDO = getFdcqByXmid(xmid);
            //查建设用地使用权
            Example example = new Example(BdcJsydsyqDO.class);
            example.setOrderByClause("djsj ASC,slbh ASC");
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            criteria.andEqualTo("qszt", 1);
            List<BdcJsydsyqDO> bdcJsydsyqDOList = entityMapper.selectByExampleNotNull(example);
            //开始组织数据
            if (null != fdcqDO) {
                GrdacxSqxx sqxx = new GrdacxSqxx();
                dozerBeanMapper.map(bdcXmDO, sqxx, "grdacx_bdcxm");
                dozerBeanMapper.map(fdcqDO, sqxx, "grdacx_fdcq");
                String bdcdyh = bdcXmDO.getBdcdyh();
                if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() > 19) {
                    // 根据BDCDYH 查 查封
                    sqxx.setCfxx(listCfxxByBdcdyh(bdcdyh, bdcqzh));
                    // 根据BDCDYH 查 抵押
                    sqxx.setDyxx(listDyxxByBdcdyh(bdcdyh, bdcqzh));
                    // 根据BDCDYH 查 异议
                    sqxx.setYyxx(listYyxxByBdcdyh(bdcdyh));

                    //根据bdcdyh和xmid查询锁定信息
                    sqxx.setSdxx(getSdxx(bdcdyh, bdcXmDO.getXmid()));

                    sqxx.setCqzh(bdcqzh);
                    // 查询宗地基本信息
                    String zdbdcdyh = StringUtils.substring(bdcdyh, 0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                    BdcBdcdjbZdjbxxDO zdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(zdbdcdyh);
                    if (zdjbxxDO != null) {
                        dozerBeanMapper.map(zdjbxxDO, sqxx, "grdacx_djbxx");
                    }
                    if (StringUtils.isNotBlank(sqxx.getCqzh())) {
                        // 查询证书信息
                        BdcZsQO bdcZsQO = new BdcZsQO();
                        bdcZsQO.setBdcqzh(sqxx.getCqzh());
                        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
                        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                            sqxx.setCertid(bdcZsDOList.get(0).getZsid());
                        }
                    }
                }
                // 查询权利人
                sqxx.setQlr(listQlrList(xmid));
                String xzq = hfXzqFeignService.queryXzqByBdcdyh(bdcdyh, null, "");
                sqxx.setXzqhszdm(xzq);
                return sqxx;
            }
            //土地证信息

            if (CollectionUtils.isNotEmpty(bdcJsydsyqDOList)) {
                GrdacxSqxx sqxx = new GrdacxSqxx();
                dozerBeanMapper.map(bdcXmDO, sqxx, "grdacx_bdcxm");
                dozerBeanMapper.map(bdcJsydsyqDOList.get(0), sqxx, "grdacx_jsytsyq");
                String bdcdyh = bdcXmDO.getBdcdyh();
                if (StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() > 19) {
                    // 根据BDCDYH 查 查封
                    sqxx.setCfxx(listCfxxByBdcdyh(bdcdyh, bdcqzh));
                    // 根据BDCDYH 查 抵押
                    sqxx.setDyxx(listDyxxByBdcdyh(bdcdyh, bdcqzh));
                    // 根据BDCDYH 查 异议
                    sqxx.setYyxx(listYyxxByBdcdyh(bdcdyh));

                    //根据bdcdyh和xmid查询锁定信息
                    sqxx.setSdxx(getSdxx(bdcdyh, bdcXmDO.getXmid()));

                    sqxx.setCqzh(bdcqzh);
                    // 查询宗地基本信息
                    String zdbdcdyh = StringUtils.substring(bdcdyh, 0, 19) + CommonConstantUtils.SUFFIX_ZD_BDCDYH;
                    BdcBdcdjbZdjbxxDO zdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxx(zdbdcdyh);
                    if (zdjbxxDO != null) {
                        dozerBeanMapper.map(zdjbxxDO, sqxx, "grdacx_djbxx");
                    }
                    if (StringUtils.isNotBlank(sqxx.getCqzh())) {
                        // 查询证书信息
                        BdcZsQO bdcZsQO = new BdcZsQO();
                        bdcZsQO.setBdcqzh(sqxx.getCqzh());
                        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
                        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                            sqxx.setCertid(bdcZsDOList.get(0).getZsid());
                        }
                    }
                }
                // 查询权利人
                sqxx.setQlr(listQlrList(xmid));
                String xzq = hfXzqFeignService.queryXzqByBdcdyh(bdcdyh, null, "");
                sqxx.setXzqhszdm(xzq);
                return sqxx;
            }

        }
        return null;

    }

    /**
     * xmid查询预告权利信息
     *
     * @param xmid xmid
     * @return BdcYgDO BdcYgDO
     * @Date 2020/12/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    private BdcYgDO getYgcqByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcQl ql = bdcQllxFeignService.queryQlxx(xmid);
            if (ql != null && ql instanceof BdcYgDO) {
                BdcYgDO ygDO = (BdcYgDO) ql;
                if (ygDO.getQszt() != null && Constants.QSZT_XS == ygDO.getQszt()) {
                    return ygDO;
                }
            }
        }
        return null;
    }

    /**
     * xmid查询建设用地权利信息
     *
     * @param xmid
     * @return BdcJsydsyqDO
     * @Date 2022/9/23
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     */
    private BdcJsydsyqDO getJsydsyqByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcQl ql = bdcQllxFeignService.queryQlxx(xmid);
            if (ql != null && ql instanceof BdcJsydsyqDO) {
                BdcJsydsyqDO jsydsyqDO = (BdcJsydsyqDO) ql;
                if (jsydsyqDO.getQszt() != null && Constants.QSZT_XS == jsydsyqDO.getQszt()) {
                    return jsydsyqDO;
                }
            }
        }
        return null;
    }

    /**
     * @param xmid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcFdcqDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID 查询FDCQ信息
     */
    public BdcFdcqDO getFdcqByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcQl ql = bdcQllxFeignService.queryQlxx(xmid);
            if (ql != null && ql instanceof BdcFdcqDO) {
                BdcFdcqDO fdcqDO = (BdcFdcqDO) ql;
                if (fdcqDO.getQszt() != null && Constants.QSZT_XS == fdcqDO.getQszt()) {
                    return fdcqDO;
                }
            }
        }
        return null;
    }

    /**
     * 查询锁定信息  包括不动产单元锁定和证书锁定
     *
     * @param bdcdyh
     * @param xmid
     * @return
     */
    private List<FwqlCxResponseSdxx> getSdxx(String bdcdyh, String xmid) {
        List<FwqlCxResponseSdxx> responseSdxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            // 单元锁定
            List<BdcDysdDO> dysdDOList = commonService.listDysdByBdcdyh(bdcdyh, CommonConstantUtils.SDZT_SD);
            if (CollectionUtils.isNotEmpty(dysdDOList)) {
                for (BdcDysdDO dysdDO : dysdDOList) {
                    FwqlCxResponseSdxx sdxx = new FwqlCxResponseSdxx();
                    dozerBeanMapper.map(dysdDO, sdxx, "bdcDysdToResponseSdxx");
                    responseSdxxList.add(sdxx);
                }
            }
        }
        if (StringUtils.isNotBlank(xmid)) {
            // 证书锁定
            List<BdcZssdDO> zssdDOList = commonService.listZssdByXmid(xmid, CommonConstantUtils.SDZT_SD);
            if (CollectionUtils.isNotEmpty(zssdDOList)) {
                for (BdcZssdDO zssdDO : zssdDOList) {
                    FwqlCxResponseSdxx sdxx = new FwqlCxResponseSdxx();
                    dozerBeanMapper.map(zssdDO, sdxx, "bdcZssdToResponseSdxx");
                    responseSdxxList.add(sdxx);
                }
            }
        }
        return responseSdxxList;
    }

    /**
     * @param bdcdyh bdcdyh
     * @return GrdacxYgxx GrdacxYgxx
     * @Date 2020/12/18
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public List<GrdacxDyxx> listYdyaxxByBdcdyh(String bdcdyh, String cqzh) {
        List<GrdacxDyxx> dyxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            List<Integer> qsztList = new ArrayList<>();
            qsztList.add(Constants.QSZT_XS);
            List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, Constants.YG_QLLX_DM, qsztList);
            if (CollectionUtils.isNotEmpty(bdcQlList)) {
                for (BdcQl qltemp : bdcQlList) {
                    BdcYgDO yg = (BdcYgDO) qltemp;
                    GrdacxDyxx dyxx = new GrdacxDyxx();
                    if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, yg.getYgdjzl())) {
                        dozerBeanMapper.map(yg, dyxx, "grdacx_ygdyaxx");
                        // 查询权利人（抵押人、抵押权人）
                        List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(yg.getXmid(), "");
                        if (CollectionUtils.isNotEmpty(qlrList)) {
                            dyxx.setDyqr(CommonUtil.wmQlrMcWithList(qlrList, Constants.QLRLB_QLR));
                            dyxx.setDyr(CommonUtil.wmQlrMcWithList(qlrList, Constants.QLRLB_YWR));
                        }
                        // 查询抵押证明
                        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(yg.getXmid());
                        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                            String zmh = CommonUtil.wmString(bdcZsDOList, "getBdcqzh", ",");
                            dyxx.setBdcdjzmh(zmh);
                        }
                        dyxx.setCqzh(cqzh);
                    }
                    if (null != dyxx && StringUtils.isNotBlank(dyxx.getBdcdyh())) {
                        dyxxList.add(dyxx);
                    }
//                    dozerBeanMapper.map(yg, grdacxYgxx, "grdacx_ygxx");
                }
            }
        }

        return dyxxList;

    }

    /**
     * @param bdcdyh bdcdyh
     * @return GrdacxYgxx GrdacxYgxx
     * @Date 2020/12/18
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public List<GrdacxYgxx> listYgxxByBdcdyh(String bdcdyh, String cqzh) {
        List<GrdacxYgxx> ygxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            List<Integer> qsztList = new ArrayList<>();
            qsztList.add(Constants.QSZT_XS);
            List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, Constants.YG_QLLX_DM, qsztList);
            if (CollectionUtils.isNotEmpty(bdcQlList)) {
                for (BdcQl qltemp : bdcQlList) {
                    BdcYgDO yg = (BdcYgDO) qltemp;
                    GrdacxYgxx grdacxYgxx = new GrdacxYgxx();
                    dozerBeanMapper.map(yg, grdacxYgxx, "grdacx_ygxx");
                    // 查询权利人（预告申请人 证件号）
                    List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(yg.getXmid(), Constants.QLRLB_QLR);
                    if (CollectionUtils.isNotEmpty(qlrList)) {
                        grdacxYgxx.setQlr(CommonUtil.wmString(qlrList, "getQlrmc", ","));
                    }
                    // 查询预告证明
                    List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(yg.getXmid());
                    if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                        String zmh = CommonUtil.wmString(bdcZsDOList, "getBdcqzh", ",");
                        grdacxYgxx.setYgdjzmh(zmh);
                    }
//                    grdacxYgxx.setYgdjzmh(cqzh);
                    ygxxList.add(grdacxYgxx);
                }
            }
        }

        return ygxxList;

    }

    /**
     * @param bdcdyh
     * @return jzqxxList
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     */
    public List<GrdacxJzqxx> listJzxxByBdcdyh(String bdcdyh) {
        List<GrdacxJzqxx> jzqxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            List<Integer> qsztList = new ArrayList<>();
            qsztList.add(Constants.QSZT_XS);
            List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, Constants.JZQ_QLLX_DM, qsztList);
            if (CollectionUtils.isNotEmpty(bdcQlList)) {
                for (BdcQl qltemp : bdcQlList) {
                    BdcJzqDO jzqDO = (BdcJzqDO) qltemp;
                    GrdacxJzqxx grdacxJzqxx = new GrdacxJzqxx();
                    dozerBeanMapper.map(jzqDO, grdacxJzqxx, "grdacx_jzqxx");
                    //新增字段
                    if (StringUtils.isNotBlank(jzqDO.getXmid())) {
                        String xmid = jzqDO.getXmid();
                        //新增字段 不动产权证号
                        //查项目信息,取项目表不动产权证号
                        BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(xmid);
                        if (bdcXmDO != null) {
                            if (StringUtils.isNotBlank(bdcXmDO.getBdcqzh())) {
                                grdacxJzqxx.setBdczmh(bdcXmDO.getBdcqzh());
                            }
                        }

                        //新增字段 居住权业务的权利人信息
                        List<GrdacxJzqr> jzqrxxList = new ArrayList<>();
                        List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(xmid, Constants.QLRLB_QLR);
                        if (CollectionUtils.isNotEmpty(qlrList)) {
                            for (BdcQlrDO qlrtemp : qlrList) {
                                GrdacxJzqr grdacxJzqr = new GrdacxJzqr();
                                dozerBeanMapper.map(qlrtemp, grdacxJzqr, "grdacx_jzqrxx");
                                //grdacxJzqr对象添加证件种类名称
                                if (StringUtils.isNotBlank(grdacxJzqr.getJzqrzjzldm())) {
                                    String zjzlmc = this.converZjzl(qlrtemp.getZjzl());
                                grdacxJzqr.setJzqrzjzlmc(zjzlmc);
                                }
                                jzqrxxList.add(grdacxJzqr);
                            }
                            grdacxJzqxx.setJzqrxx(jzqrxxList);
                        }
                    }
                    jzqxxList.add(grdacxJzqxx);
                }
            }
        }
        return jzqxxList;
    }

    /**
     * 证件种类名称
     * @param zjzl
     * @return
     */
    private String converZjzl(Integer zjzl) {
        return bdcZdCache.getFeildValue("BDC_ZD_ZJZL", zjzl, BdcZdZjzlDO.class);
    }


        /**
         * @param bdcdyh
         * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxCfxx>
         * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
         * @description 根据BDCDYH 查询 查封信息 列表
         */
    public List<GrdacxCfxx> listCfxxByBdcdyh(String bdcdyh, String bdcqzh) {
        List<GrdacxCfxx> cfxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            List<Integer> qsztList = new ArrayList<>();
            qsztList.add(Constants.QSZT_XS);
            List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, Constants.CF_QLLX_DM, qsztList);
            if (CollectionUtils.isNotEmpty(bdcQlList)) {
                for (BdcQl qltemp : bdcQlList) {
                    BdcCfDO cf = (BdcCfDO) qltemp;
                    GrdacxCfxx grdacxCfxx = new GrdacxCfxx();
                    dozerBeanMapper.map(cf, grdacxCfxx, "grdacx_cfxx");
                    grdacxCfxx.setCqzh(bdcqzh);
                    cfxxList.add(grdacxCfxx);
                }
            }
        }
        return cfxxList;
    }


    /**
     * 根据BDCDYH 查询 抵押信息
     *
     * @param bdcdyh
     * @return
     */
    public List<GrdacxDyxx> listDyxxByBdcdyh(String bdcdyh, String bdcqzh) {
        List<GrdacxDyxx> dyxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            List<Integer> qsztList = new ArrayList<>();
            qsztList.add(Constants.QSZT_XS);
            List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString(), qsztList);
            if (CollectionUtils.isNotEmpty(bdcQlList)) {
                for (BdcQl qltemp : bdcQlList) {
                    BdcDyaqDO dyaq = (BdcDyaqDO) qltemp;
                    GrdacxDyxx dyxx = new GrdacxDyxx();
                    dozerBeanMapper.map(dyaq, dyxx, "grdacx_dyxx");
                    // 查询权利人（抵押人、抵押权人）
                    List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(dyaq.getXmid(), "");
                    if (CollectionUtils.isNotEmpty(qlrList)) {
                        dyxx.setDyqr(CommonUtil.wmQlrMcWithList(qlrList, Constants.QLRLB_QLR));
                        dyxx.setDyr(CommonUtil.wmQlrMcWithList(qlrList, Constants.QLRLB_YWR));
                    }
                    // 查询抵押证明
                    List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(dyaq.getXmid());
                    if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                        String zmh = CommonUtil.wmString(bdcZsDOList, "getBdcqzh", ",");
                        dyxx.setBdcdjzmh(zmh);
                    }
                    dyxx.setCqzh(bdcqzh);
                    dyxxList.add(dyxx);
                }
            }
        }
        return dyxxList;
    }

    /**
     * @param xmid
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxQlr>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID 查询 权利人列表
     */
    public List<GrdacxQlr> listQlrList(String xmid) {
        List<GrdacxQlr> qlrxxList = new ArrayList<>();
        List<BdcQlrDO> bdcQlrList = commonService.listBdcQlrByXmid(xmid, Constants.QLRLB_QLR);
        if (CollectionUtils.isNotEmpty(bdcQlrList)) {
            for (BdcQlrDO tempQlr : bdcQlrList) {
                GrdacxQlr qlrxx = new GrdacxQlr();
                dozerBeanMapper.map(tempQlr, qlrxx, "grdacx_qlr");
                qlrxxList.add(qlrxx);
            }
        }
        return qlrxxList;
    }


    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.response.GrdacxYyxx>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询异议列表
     */
    public List<GrdacxYyxx> listYyxxByBdcdyh(String bdcdyh) {
        List<GrdacxYyxx> yyxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            List<Integer> qsztList = new ArrayList<>();
            qsztList.add(Constants.QSZT_XS);
            List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, Constants.YY_QLLX_DM, qsztList);
            if (CollectionUtils.isNotEmpty(bdcQlList)) {
                for (BdcQl qltemp : bdcQlList) {
                    BdcYyDO bdcYyDO = (BdcYyDO) qltemp;
                    GrdacxYyxx yyxx = new GrdacxYyxx();
                    dozerBeanMapper.map(bdcYyDO, yyxx, "grdacx_yyxx");
                    // 查询权利人（异议申请人 证件号）
                    List<BdcQlrDO> qlrList = commonService.listBdcQlrByXmid(bdcYyDO.getXmid(), Constants.QLRLB_QLR);
                    if (CollectionUtils.isNotEmpty(qlrList)) {
                        yyxx.setYysqr(CommonUtil.wmString(qlrList, "getQlrmc", ","));
                        yyxx.setYysqrzjhm(CommonUtil.wmString(qlrList, "getZjh", ","));
                    }
                    yyxxList.add(yyxx);
                }
            }
        }
        return yyxxList;
    }


}
