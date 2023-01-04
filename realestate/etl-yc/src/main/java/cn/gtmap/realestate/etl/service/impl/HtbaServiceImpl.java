package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaFwxxDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.etl.HtbaxxDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ObjectUtils;
import cn.gtmap.realestate.etl.service.HtbaFwxxService;
import cn.gtmap.realestate.etl.service.HtbaQlrService;
import cn.gtmap.realestate.etl.service.HtbaService;
import cn.gtmap.realestate.etl.service.HtbaSpfService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: realestate
 * @description: 合同备案服务实现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-16 09:26
 **/
@Service
public class HtbaServiceImpl implements HtbaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtbaServiceImpl.class);

    @Autowired
    HtbaSpfService htbaSpfService;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;
    @Autowired
    HtbaFwxxService htbaFwxxService;
    @Autowired
    BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    HtbaQlrService htbaQlrService;
    @Autowired
    BdcBhFeignService bdcBhFeignService;
    @Autowired
    BdcGzyzFeignService bdcGzyzFeignService;
    @Resource(name = "transactionManager")
    private DataSourceTransactionManager transactionManager;

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 从楼盘表界面打开合同备案信息的处理
     * @date : 2020/12/16 9:28
     */
    @Override
    public String forwardHtbaHtml(String bdcdyh) {
        String baid = "";
        if (StringUtils.isNotBlank(bdcdyh)) {
            //1.先判断数据库是否存在数据
            List<HtbaSpfDO> htbaSpfDOList = htbaSpfService.listHtbaxx(bdcdyh);
            if (CollectionUtils.isNotEmpty(htbaSpfDOList)) {
                baid = htbaSpfDOList.get(0).getBaid();
            } else {
                //2.没有数据新增，房屋信息从权籍获取过来插入
                //先查户室信息
                FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh,"");
                if (Objects.isNull(fwHsDO)) {
                    fwHsDO = new FwHsDO();
                    FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(bdcdyh,"");
                    if (Objects.nonNull(fwYchsDO)) {
                        BeanUtils.copyProperties(fwYchsDO, fwHsDO);
                    }
                }
                if (StringUtils.isNotBlank(fwHsDO.getFwHsIndex())) {
                    //保存合同备案商品房信息
                    HtbaSpfDO htbaSpfDO = htbaSpfService.saveHtbaSpfFromFwhs(fwHsDO);
                    //保存合同备案房屋信息
                    if (Objects.nonNull(htbaSpfDO) && StringUtils.isNotBlank(htbaSpfDO.getBaid())) {
                        baid = htbaSpfDO.getBaid();
                        htbaFwxxService.saveHtbaFwxxFromFwhs(fwHsDO, htbaSpfDO.getBaid());
                    }
                    //同步权籍状态
                    //回写状态到权籍
                    List<String> bdcdyList = new ArrayList<>();
                    bdcdyList.add(fwHsDO.getBdcdyh());
                    if (CollectionUtils.isNotEmpty(bdcdyList)) {
                        bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyList,"");
                    }
                }
            }

        }
        return baid;
    }

    /**
     * @param htbaxxDTO 推送数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/4/13 16:04
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object wwtsHtbaxx(HtbaxxDTO htbaxxDTO) {
        if (Objects.isNull(htbaxxDTO)) {
            return BdcCommonResponse.fail("外网推送的合同备案信息为空数据异常", "");
        }
        LOGGER.info("外网推送接收备案信息数据{}", JSON.toJSONString(htbaxxDTO));
        //1. 根据合同编号判断是否存在已备案数据，已备案，返回失败，原因“已存在备案信息”
        HtbaSpfDO htbaSpfDO = htbaxxDTO.getHtbaSpfDO();
        if (Objects.nonNull(htbaSpfDO) && StringUtils.isNotBlank(htbaSpfDO.getHtbh())) {
            //处理完数据后手动提交事务
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
            TransactionStatus status = transactionManager.getTransaction(def);
            if (Objects.equals(CommonConstantUtils.BAZT_WBA, htbaSpfDO.getBazt())) {
                //如果推送过来的数据是未备案，走撤销的方法
                return wwcxHtbaxx(htbaxxDTO);
            }
            List<String> bdcdyList = new ArrayList<>();
            HtbaxxQO htbaxxQO = new HtbaxxQO();
            htbaxxQO.setHtbh(htbaSpfDO.getHtbh());
            List<HtbaSpfDO> htbaSpfDOList = htbaSpfService.listHtbaSpf(htbaxxQO);
            //用于判断是新增操作还是更新操作
            if (CollectionUtils.isNotEmpty(htbaSpfDOList)) {
                //合同编号只能查询到一条备案主数据
                HtbaSpfDO htbaSpf = htbaSpfDOList.get(0);
                //如果当前合同号已备案，返回失败，提示已存在备案信息
                if (Objects.equals(CommonConstantUtils.BAZT_BA, htbaSpf.getBazt())) {
                    return BdcCommonResponse.fail(htbaSpfDO.getHtbh() + "已存在备案信息", htbaSpf);
                }
                //更新合同备案数据
                BeanUtils.copyProperties(htbaSpfDO, htbaSpf, ObjectUtils.getNullPropertyNames(htbaSpfDO));
                htbaSpfDO = htbaSpfService.saveOrUpdatHtbaxx(JSON.toJSONString(htbaSpf));
            } else {
                //1.处理备案信息商品房数据,没有查询到进行新增操作
                htbaSpfDO.setSlbh(bdcBhFeignService.queryCommonBh(CommonConstantUtils.SLBH, CommonConstantUtils.ZZSJFW_DAY, 4, ""));
                htbaSpfDO = htbaSpfService.saveOrUpdatHtbaxx(JSON.toJSONString(htbaSpfDO));
            }
            //2.处理备案房屋信息
            List<HtbaFwxxDO> htbaFwxxDOList = htbaxxDTO.getHtbaFwxxDOList();
            if (CollectionUtils.isNotEmpty(htbaFwxxDOList)) {
                bdcdyList = handleHtbaFwxx(htbaSpfDO.getBaid(), htbaFwxxDOList);
            }
            //3. 处理备案申请人信息
            List<HtbaQlrDO> htbaQlrDOList = htbaxxDTO.getHtbaQlrDOList();
            if (CollectionUtils.isNotEmpty(htbaQlrDOList)) {
                handleHtbaQlr(htbaSpfDO.getBaid(), htbaQlrDOList);
            }
            try {
                //提交事务
                transactionManager.commit(status);
            } catch (Exception e) {
                transactionManager.rollback(status);
            }
            //4. 同步权籍状态
            if (CollectionUtils.isNotEmpty(bdcdyList)) {
                LOGGER.info("外网推送备案信息更新备案状态单元号{}", bdcdyList);
                bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyList,"");
            }
        } else {
            return BdcCommonResponse.fail("备案商品房数据异常", JSON.toJSONString(htbaxxDTO));
        }
        return BdcCommonResponse.ok(htbaSpfDO.getHtbh());
    }

    /**
     * @param htbaxxDTO 撤销数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外网撤销备案数据
     * @date : 2021/4/13 16:04
     */
    @Override
    public Object wwcxHtbaxx(HtbaxxDTO htbaxxDTO) {
        if (Objects.isNull(htbaxxDTO)) {
            return BdcCommonResponse.fail("外网推送的合同备案信息为空数据异常");
        }
        LOGGER.info("撤销备案数据{}", JSON.toJSONString(htbaxxDTO));
        //1. 根据单元号判断是否存在登记业务或者现势产权，调用规则验证，存在限制返回失败和原因
        List<HtbaFwxxDO> htbaFwxxDOList = htbaxxDTO.getHtbaFwxxDOList();
        List<String> bdcdyhList;
        if (CollectionUtils.isNotEmpty(htbaFwxxDOList)) {
            bdcdyhList = htbaFwxxDOList.stream().filter(Objects::nonNull).filter(htbaFwxxDO -> StringUtils.isNotBlank(htbaFwxxDO.getBdcdyh())).map(HtbaFwxxDO::getBdcdyh).collect(Collectors.toList());
        } else {
            return BdcCommonResponse.fail("撤销备案房屋信息为空异常", htbaxxDTO);
        }
        List<Map<String, Object>> gzYzsjDTOList = new ArrayList<>(CollectionUtils.size(bdcdyhList));
        for (String bdcdyh : bdcdyhList) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("bdcdyh", bdcdyh);
            gzYzsjDTOList.add(paramMap);
        }
        BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
        bdcGzYzQO.setZhbs(CommonConstantUtils.GZYZ_CXBA);
        bdcGzYzQO.setParamList(gzYzsjDTOList);
        LOGGER.info("撤销合同备案规则验证数据{}", bdcGzYzQO);
        List<Map<String, Object>> yzResult = bdcGzyzFeignService.yzBdcgz(bdcGzYzQO);
        if (CollectionUtils.isNotEmpty(yzResult)) {
            String message = yzResult.stream().filter(result -> Objects.nonNull(result.get("msg")))
                    .map(result -> String.valueOf(result.get("msg"))).collect(Collectors.joining(CommonConstantUtils.ZF_YW_DH));
            return BdcCommonResponse.fail(message, htbaFwxxDOList);
        }
        //规则验证通过修改备案状态为0 -- 未备案
        HtbaSpfDO htbaSpfDO = htbaxxDTO.getHtbaSpfDO();
        if (Objects.nonNull(htbaSpfDO) && StringUtils.isNotBlank(htbaSpfDO.getHtbh())) {
            HtbaxxQO htbaxxQO = new HtbaxxQO();
            htbaxxQO.setHtbh(htbaSpfDO.getHtbh());
            List<HtbaSpfDO> yHtbaSpfList = htbaSpfService.listHtbaSpf(htbaxxQO);
            if (CollectionUtils.isEmpty(yHtbaSpfList)) {
                return BdcCommonResponse.fail("未查询到合同编号为" + htbaSpfDO.getHtbh() + "备案数据", htbaSpfDO);
            }
            HtbaSpfDO yHtbaSpf = yHtbaSpfList.get(0);
            yHtbaSpf.setBazt(CommonConstantUtils.BAZT_WBA);
            htbaSpfService.saveOrUpdatHtbaxx(JSON.toJSONString(yHtbaSpf));
            //处理权籍备案状态
            if (CollectionUtils.isNotEmpty(bdcdyhList)) {
                LOGGER.info("外网撤销备案信息更新备案状态单元号{}", bdcdyhList);
                bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyhList,"");
            }
        } else {
            return BdcCommonResponse.fail("撤销备案商品房数据异常", htbaSpfDO);
        }
        return BdcCommonResponse.ok(htbaSpfDO.getHtbh());
    }

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 处理房屋信息, 返回单元号数据，用于更新权籍状态
     * @date : 2021/4/14 10:23
     */
    @Transactional(rollbackFor = Exception.class)
    public List<String> handleHtbaFwxx(String baid, List<HtbaFwxxDO> htbaFwxxDOList) {
        List<String> bdcdyhList = new ArrayList<>(CollectionUtils.size(htbaFwxxDOList));
        //删除htbafwxx原有数据，再重新新增
        List<HtbaFwxxDO> yhtbaFwxxList = htbaFwxxService.listHtbaFwxxByBaid(baid);
        if (CollectionUtils.isNotEmpty(yhtbaFwxxList)) {
            htbaFwxxService.deleteHtbaxx(baid);
        }
        for (HtbaFwxxDO htbaFwxxDO : htbaFwxxDOList) {
            bdcdyhList.add(htbaFwxxDO.getBdcdyh());
            htbaFwxxDO.setBaid(baid);
            //直接新增处理
            //查户室index作为fwid
            if (StringUtils.isBlank(htbaFwxxDO.getFwid()) && StringUtils.isNotBlank(htbaFwxxDO.getBdcdyh())) {
                FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(htbaFwxxDO.getBdcdyh(),"");
                if (Objects.isNull(fwHsDO)) {
                    fwHsDO = new FwHsDO();
                    FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(htbaFwxxDO.getBdcdyh(),"");
                    if (Objects.nonNull(fwYchsDO)) {
                        BeanUtils.copyProperties(fwYchsDO, fwHsDO);
                    }
                }
                if (StringUtils.isNotBlank(fwHsDO.getFwHsIndex())) {
                    htbaFwxxDO.setFwid(fwHsDO.getFwHsIndex());
                }
            }
            //手动提交事务--事务进行中，更新备案状态查询时还未提交事务，这里手动提交或者回滚
//            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
//            TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
//            try {
            htbaFwxxService.saveHtbaFwxxDO(htbaFwxxDO);
            //提交事务
//                transactionManager.commit(status);
//            } catch (Exception e) {
//                transactionManager.rollback(status);
//            }
        }
        return bdcdyhList;
    }


    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 处理合同备案权利人信息
     * @date : 2021/4/14 10:24
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleHtbaQlr(String baid, List<HtbaQlrDO> htbaQlrDOList) {
        //删除原来的合同备案权利人
        List<HtbaQlrDO> yhtbaQlrList = htbaQlrService.listHtbaQlrByBaid(baid);
        if (CollectionUtils.isNotEmpty(yhtbaQlrList)) {
            htbaQlrService.deleteHtbaQlrByBaid(baid);
        }
        for (HtbaQlrDO htbaQlrDO : htbaQlrDOList) {
            htbaQlrDO.setBaid(baid);
        }
        htbaQlrService.saveOrUpdatHtbaQlr(JSON.toJSONString(htbaQlrDOList));
    }
}
