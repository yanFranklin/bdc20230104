package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.config.logaop.DbLogUtils;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqZxsqDO;
import cn.gtmap.realestate.common.core.domain.register.BdcBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxPlRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxModelDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxXzqlModelDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcdyZtDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyhZtFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.yancheng.BdcZqFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.core.mapper.BdcdyZtMapper;
import cn.gtmap.realestate.register.core.qo.BdcXmGxQO;
import cn.gtmap.realestate.register.core.service.BdcdySdService;
import cn.gtmap.realestate.register.core.service.impl.*;
import cn.gtmap.realestate.register.core.thread.BdcdyZtThread;
import cn.gtmap.realestate.register.rabbitMq.SynQjBdcdyztMqService;
import cn.gtmap.realestate.register.service.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/17
 * @description 不动产单元状态实现类
 */
@Service
public class BdcdyZtServiceImpl implements BdcdyZtService {

    protected final Logger LOGGER = LoggerFactory.getLogger(BdcdyZtServiceImpl.class);

    @Autowired
    BdcQlxxService bdcQlxxService;
    @Autowired
    BeansResolveUtils beansResolveUtils;
    @Autowired
    BdcYgServiceImpl bdcYgService;
    @Autowired
    BdcCfServiceImpl bdcCfService;
    @Autowired
    BdcYyServiceImpl bdcYyService;
    @Autowired
    BdcDyiqServiceImpl bdcDyiqService;
    @Autowired
    BdcDyaqServiceImpl bdcDyaqService;
    @Autowired
    BdcJzqServiceImpl bdcJzqService;
    @Autowired
    BdcdySdService bdcdySdService;
    @Autowired
    ThreadEngine threadEngine;
    @Autowired
    BdcDbxxService bdcDbxxService;
    @Autowired
    SynQjBdcdyztMqService synQjBdcdyztMqService;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    BdcXmxxService bdcXmxxService;
    @Autowired
    DbLogUtils dbLogUtils;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    HtbaService htbaService;
    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  权籍状态服务
      */
    @Autowired
    private BdcdyhZtFeignService bdcdyhZtFeignService;

    @Autowired
    BdcZqFeignService bdcZqFeignService;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcdyZtMapper bdcdyZtMapper;

    /**
     * 登簿同步权籍不动产单元状态
     */
    @Value("${dbEvent.syncQjBdcdyZt:true}")
    private boolean dbSyncQjBdcdyzt;

    /**
     * 登簿同步权籍不动产单元状态
     */
    @Value("${dbEvent.syncQjBdcdyXx:true}")
    private boolean dbSyncQjBdcdyXx;

    /**
     * 续封是否计入查封次数
     */
    @Value("${xf.jrcfcs:false}")
    private boolean jrcfcs;

    /**
     * 不动产单元现势状态来源：bdcsjgl:权籍 bdcdj：登记
     */
    @Value("${bdcdyxszt.ly:bdcsjgl}")
    private String dyxsztly;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否查询房屋拆迁状态
     */
    @Value("${bdcdyzt.fwcqcx:false}")
    private boolean fwcqcx;

    /**
     * @param bdcdyh 不动产单元号
     * @return BdcdyZtDTO 不动产单元状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元状态
     */
    @Override
    public BdcdyZtDTO queryBdcdyZt(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("缺失查询参数bdcdyh");
        }
        BdcdyZtDTO bdcdyZtDTO = new BdcdyZtDTO();

        // 查询产权
        queryCqzt(bdcdyh, bdcdyZtDTO);
        // 如果产权已经被注销了，后面的权利不再查询
        if (null != bdcdyZtDTO.getZx() && bdcdyZtDTO.getZx()) {
            return bdcdyZtDTO;
        }
        // 查询预告
        queryYgzt(bdcdyh, bdcdyZtDTO, null);
        // 查询查封
        queryCfzt(bdcdyh, bdcdyZtDTO, null);
        // 查询异议
        queryYyzt(bdcdyh, bdcdyZtDTO, null);
        // 查询地役
        queryDyiqzt(bdcdyh, bdcdyZtDTO, null);
        // 查询抵押
        queryDya(bdcdyh, bdcdyZtDTO, null);
        // 查询锁定
        querySd(bdcdyh, bdcdyZtDTO, null);
        // 查询居住权
        queryJzq(bdcdyh, bdcdyZtDTO, null);
        return bdcdyZtDTO;
    }

    /**
     * @param bdcdyh     不动产单元号
     * @param bdcdyZtDTO 不动产单元状态返回结果
     * @param bdcSyncZtRequestDTO   状态数量结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元锁定状态
     */
    private void querySd(String bdcdyh, BdcdyZtDTO bdcdyZtDTO, BdcSyncZtRequestDTO bdcSyncZtRequestDTO) {
        List<BdcDysdDO> bdcDysdDOList = bdcdySdService.queryBdcDysd(bdcdyh, CommonConstantUtils.SDZT_SD, null);
        List<BdcZssdDO> bdcZssdDOList = bdcdySdService.queryBdZsSd(bdcdyh, CommonConstantUtils.SDZT_SD, null);
        if (CollectionUtils.isNotEmpty(bdcDysdDOList) || CollectionUtils.isNotEmpty(bdcZssdDOList)) {
            bdcdyZtDTO.setSd(true);
            if (bdcSyncZtRequestDTO != null) {
                int xssdcs = 0;
                if (CollectionUtils.isNotEmpty(bdcDysdDOList)){
                    xssdcs += bdcDysdDOList.size();
                }
                if (CollectionUtils.isNotEmpty(bdcZssdDOList)){
                    xssdcs += bdcZssdDOList.size();
                }
                bdcSyncZtRequestDTO.setXssdcs(xssdcs);
            }
        }
    }

    /**
     * @param bdcdyh     不动产单元号
     * @param bdcdyZtDTO 不动产单元状态返回结果
     * @param bdcSyncZtRequestDTO   状态数量结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元的居住权状态
     */
    private void queryJzq(String bdcdyh, BdcdyZtDTO bdcdyZtDTO, BdcSyncZtRequestDTO bdcSyncZtRequestDTO) {
        // 查询一般抵押
        List qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcJzqList = bdcJzqService.queryListBdcQl(bdcdyh, qsztList);
        if (CollectionUtils.isNotEmpty(bdcJzqList)) {
            bdcdyZtDTO.setJzq(true);
            if (bdcSyncZtRequestDTO != null) {
                bdcSyncZtRequestDTO.setXsjzqcs(bdcJzqList.size());
            }
        }
    }

    /**
     * @param bdcdyh     不动产单元号
     * @param bdcdyZtDTO 不动产单元状态返回结果
     * @param bdcSyncZtRequestDTO   状态数量结果
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询备案状态
     * @date : 2020/12/17 19:55
     */
    private void queryBazt(String bdcdyh, BdcdyZtDTO bdcdyZtDTO, BdcSyncZtRequestDTO bdcSyncZtRequestDTO) {
        if(StringUtils.isNotBlank(bdcdyh)) {
            HtbaxxQO htbaxxQO = new HtbaxxQO();
            htbaxxQO.setBdcdyh(bdcdyh);
            htbaxxQO.setBazt(CommonConstantUtils.BAZT_BA);
            List<HtbaSpfDO> htbaSpfDOList = htbaService.listHtbaSpf(htbaxxQO);
            if (CollectionUtils.isNotEmpty(htbaSpfDOList)) {
                bdcdyZtDTO.setBa(true);
                if (bdcSyncZtRequestDTO != null) {
                    bdcSyncZtRequestDTO.setSfba(CommonConstantUtils.BAZT_S);
                }
            } else {
                bdcdyZtDTO.setBa(false);
                bdcSyncZtRequestDTO.setSfba(CommonConstantUtils.BAZT_F);
            }
        }
    }

    /**
     * @param bdcdyh     不动产单元号
     * @param bdcdyZtDTO 不动产单元状态返回结果
     * @param bdcSyncZtRequestDTO   状态数量结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元的抵押权状态
     */
    private void queryDya(String bdcdyh, BdcdyZtDTO bdcdyZtDTO, BdcSyncZtRequestDTO bdcSyncZtRequestDTO) {
        // 查询一般抵押
        List qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcDyaqList = bdcDyaqService.queryListBdcQl(bdcdyh, qsztList);
        if (CollectionUtils.isNotEmpty(bdcDyaqList)) {
            int dySize = 0;
            int zjgcdySize = 0;
            for (BdcQl bdcQl : bdcDyaqList) {
                BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                if (null != bdcDyaqDO && CommonConstantUtils.DYBDCLX_ZJJZW.equals(bdcDyaqDO.getDybdclx())) {
                    bdcdyZtDTO.setZjgcdy(true);
                    ++zjgcdySize;
                } else {
                    bdcdyZtDTO.setDya(true);
                    ++dySize;
                }
            }
            if (bdcSyncZtRequestDTO != null) {
                bdcSyncZtRequestDTO.setXsdyacs(dySize);
                bdcSyncZtRequestDTO.setXszjgcdycs(zjgcdySize);
            }
        }
    }

    /**
     * @param bdcdyh     不动产单元号
     * @param bdcdyZtDTO 不动产单元状态返回结果
     *  @param bdcSyncZtRequestDTO   状态数量结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元号的地役状态
     */
    private void queryDyiqzt(String bdcdyh, BdcdyZtDTO bdcdyZtDTO, BdcSyncZtRequestDTO bdcSyncZtRequestDTO) {
        List qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcDyiqList = bdcDyiqService.queryListBdcQl(bdcdyh, qsztList);
        if (CollectionUtils.isNotEmpty(bdcDyiqList)) {
            bdcdyZtDTO.setDyi(true);
            if (bdcSyncZtRequestDTO != null) {
                bdcSyncZtRequestDTO.setXsdyics(bdcDyiqList.size());
            }
        }
    }

    /**
     * @param bdcdyh     不动产单元号
     * @param bdcdyZtDTO 不动产单元状态返回结果
     *  @param bdcSyncZtRequestDTO   状态数量结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元号的异议状态
     */
    private void queryYyzt(String bdcdyh, BdcdyZtDTO bdcdyZtDTO, BdcSyncZtRequestDTO bdcSyncZtRequestDTO) {
        List qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcYyList = bdcYyService.queryListBdcQl(bdcdyh, qsztList);
        if (CollectionUtils.isNotEmpty(bdcYyList)) {
            bdcdyZtDTO.setYy(true);
            if (bdcSyncZtRequestDTO != null) {
                bdcSyncZtRequestDTO.setXsyycs(bdcYyList.size());
            }
        }
    }

    /**
     * @param bdcdyh     不动产单元号
     * @param bdcdyZtDTO 不动产单元状态返回结果
     *  @param bdcSyncZtRequestDTO   状态数量结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元号的查封状态
     */
    private void queryCfzt(String bdcdyh, BdcdyZtDTO bdcdyZtDTO, BdcSyncZtRequestDTO bdcSyncZtRequestDTO) {
        List qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcCfList = bdcCfService.queryListBdcQl(bdcdyh, qsztList);
        if (CollectionUtils.isNotEmpty(bdcCfList)) {
            int ycfSize = 0;
            int cfSize = 0;
            for (BdcQl bdcQl : bdcCfList) {
                Integer cflx = ((BdcCfDO) bdcQl).getCflx();
                if (ArrayUtils.contains(CommonConstantUtils.CFLX_YCF_ARR, cflx)) {
                    bdcdyZtDTO.setYcf(true);
                    ++ycfSize;

                    // 有预查封，表示已登记
                    bdcdyZtDTO.setDj(true);
                } else {
                    bdcdyZtDTO.setCf(true);
                    // 续封类型
                    if (!CommonConstantUtils.CFLX_XF.equals(cflx) ||jrcfcs) {
                        ++cfSize;
                    }
                }
            }
            if (bdcSyncZtRequestDTO != null) {
                bdcSyncZtRequestDTO.setXsycfcs(ycfSize);
                bdcSyncZtRequestDTO.setXscfcs(cfSize);
            }
        }
    }

    /**
     * @param bdcdyh     不动产单元号
     * @param bdcdyZtDTO 不动产单元状态返回结果
     *  @param bdcSyncZtRequestDTO   状态数量结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元的预告状态
     */
    private void queryYgzt(String bdcdyh, BdcdyZtDTO bdcdyZtDTO, BdcSyncZtRequestDTO bdcSyncZtRequestDTO) {
        List qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcYgList = bdcYgService.queryListBdcQl(bdcdyh, qsztList);
        if (CollectionUtils.isNotEmpty(bdcYgList)) {
            int ygSize = 0;
            int ydySize = 0;
            for (BdcQl bdcQl : bdcYgList) {
                if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, ((BdcYgDO) bdcQl).getYgdjzl())) {
                    bdcdyZtDTO.setYg(true);
                    ygSize++;

                    // 有预告，表示已登记
                    bdcdyZtDTO.setDj(true);
                } else {
                    bdcdyZtDTO.setYdya(true);
                    ydySize++;
                }
            }
            if (bdcSyncZtRequestDTO != null) {
                bdcSyncZtRequestDTO.setXsygcs(ygSize);
                bdcSyncZtRequestDTO.setXsydyacs(ydySize);
            }
        }
    }

    /**
     * @param bdcdyh     不动产单元号
     * @param bdcdyZtDTO 不动产单元状态返回结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元的产权结果
     */
    private void queryCqzt(String bdcdyh, BdcdyZtDTO bdcdyZtDTO) {
        // 查询现势产权
        List<BdcQl> cqList;
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        // 为了查询效率，将房屋和非房屋的分开查询
        if (StringUtils.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh), CommonConstantUtils.BDCLX_TZM_F)) {
            cqList = bdcQlxxService.queryListBdcFwCq(bdcdyh, qsztList);
        } else {
            cqList = bdcQlxxService.queryListBdcFfwCq(bdcdyh, qsztList);
        }
        if (CollectionUtils.isNotEmpty(cqList)) {
            bdcdyZtDTO.setDj(true);
            return;
        }

        // 查询注销产权信息
        qsztList.clear();
        qsztList.add(CommonConstantUtils.QSZT_HISTORY);
        if (StringUtils.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh), CommonConstantUtils.BDCLX_TZM_F)) {
            cqList = bdcQlxxService.queryListBdcFwCq(bdcdyh, qsztList);
        } else {
            cqList = bdcQlxxService.queryListBdcFfwCq(bdcdyh, qsztList);
        }
        if (CollectionUtils.isNotEmpty(cqList)) {
            bdcdyZtDTO.setZx(true);
        }
    }



    /**
     * @param zsxmid     产权证书项目ID
     * @param bdcdyZtDTO 不动产单元状态返回结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元的产权结果
     */
    private void queryCqztByZsxmid(String zsxmid, BdcdyZtDTO bdcdyZtDTO) {
        BdcQl bdcQl =bdcQllxFeignService.queryQlxx(zsxmid);
        if(bdcQl != null){
            if(CommonConstantUtils.QSZT_VALID.equals(bdcQl.getQszt())){
                //现势产权
                bdcdyZtDTO.setDj(true);
            }else if(CommonConstantUtils.QSZT_HISTORY.equals(bdcQl.getQszt())){
                bdcdyZtDTO.setZx(true);
            }
        }

    }

    /**
     * @param zsxmid     产权证书项目ID
     * @param bdcdyZtDTO 不动产单元状态返回结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元的限制状态结果
     */
    private void queryXzztByZsxmid(String zsxmid, BdcdyZtDTO bdcdyZtDTO) {
        LsgxXzqlModelDTO lsgxXzqlModelDTO = bdcLsgxFeignService.getXzqlList(zsxmid,null);
        if(lsgxXzqlModelDTO != null){
            List<LsgxModelDTO> listDy = lsgxXzqlModelDTO.getDyLsgxModel();
            if (CollectionUtils.isNotEmpty(listDy)) {
                listDy = listDy.stream().filter(dy -> CommonConstantUtils.QSZT_VALID.equals(dy.getQszt())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(listDy)){
                    //抵押
                    for(LsgxModelDTO lsgxModelDTO:listDy){
                        if(StringUtils.isNotBlank(lsgxModelDTO.getQlxx())){
                            BdcDyaqDO bdcDyaqDO =JSONObject.parseObject(lsgxModelDTO.getQlxx(),BdcDyaqDO.class);
                            if (null != bdcDyaqDO && CommonConstantUtils.DYBDCLX_ZJJZW.equals(bdcDyaqDO.getDybdclx())) {
                                bdcdyZtDTO.setZjgcdy(true);
                            } else {
                                bdcdyZtDTO.setDya(true);
                            }
                        }
                    }
                }
            }
            List<LsgxModelDTO> listCf = lsgxXzqlModelDTO.getCfLsgxModel();
            if (CollectionUtils.isNotEmpty(listCf)) {
                listCf = listCf.stream().filter(cf -> CommonConstantUtils.QSZT_VALID.equals(cf.getQszt())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(listCf)){
                    //查封
                    for(LsgxModelDTO lsgxModelDTO:listCf){
                        if(StringUtils.isNotBlank(lsgxModelDTO.getQlxx())){
                            BdcCfDO bdcCfDO =JSONObject.parseObject(lsgxModelDTO.getQlxx(),BdcCfDO.class);
                            if (bdcCfDO != null &&ArrayUtils.contains(CommonConstantUtils.CFLX_YCF_ARR, bdcCfDO.getCflx())) {
                                bdcdyZtDTO.setYcf(true);
                                // 有预查封，表示已登记
                                bdcdyZtDTO.setDj(true);
                            } else {
                                bdcdyZtDTO.setCf(true);
                            }
                        }
                    }
                }
            }
            List<LsgxModelDTO> listDyi = lsgxXzqlModelDTO.getDyiLsgxModel();
            if (CollectionUtils.isNotEmpty(listDyi)) {
                listDyi = listDyi.stream().filter(dyi -> CommonConstantUtils.QSZT_VALID.equals(dyi.getQszt())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(listDyi)){
                    //地役
                    bdcdyZtDTO.setDyi(true);
                }
            }
            List<LsgxModelDTO> listYg = lsgxXzqlModelDTO.getYgLsgxModel();
            if (CollectionUtils.isNotEmpty(listYg)) {
                for(LsgxModelDTO lsgxModelDTO:listYg){
                    //预告
                    if(StringUtils.isNotBlank(lsgxModelDTO.getQlxx())){
                        BdcYgDO bdcYgDO =JSONObject.parseObject(lsgxModelDTO.getQlxx(),BdcYgDO.class);
                        if (bdcYgDO != null &&ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, bdcYgDO.getYgdjzl())) {
                            bdcdyZtDTO.setYg(true);
                            // 有预告，表示已登记
                            bdcdyZtDTO.setDj(true);
                        } else {
                            bdcdyZtDTO.setYdya(true);
                        }
                    }
                }
            }
            List<LsgxModelDTO> listYy = lsgxXzqlModelDTO.getYyLsgxModel();
            if (CollectionUtils.isNotEmpty(listYy)) {
                listYy = listYy.stream().filter(yy -> CommonConstantUtils.QSZT_VALID.equals(yy.getQszt())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(listYy)){
                    //异议
                    bdcdyZtDTO.setYy(true);
                }
            }

        }


    }

    /**
     * @param bdcdyList 不动产单元List
     * @return List<BdcdyZtDTO> 不动产单元List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量查询不动产单元状态
     */
    @Override
    public List<BdcdyZtDTO> queryBdcdyZtList(List<String> bdcdyList) {
        if (CollectionUtils.isEmpty(bdcdyList)) {
            throw new MissingArgumentException("缺失bdcdyList参数信息！");
        }
        List<BdcdyZtDTO> bdcdyZtDTOList = new ArrayList();
        for (String bdcdyh : bdcdyList) {
            bdcdyZtDTOList.add(queryBdcdyZt(bdcdyh));
        }
        return bdcdyZtDTOList;
    }

    /**
     * @param bdcdyList 不动产单元号 List
     * @return BatchBdcdyhSyncZtRequestDTO 不动产单元各种权利数量集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 批量查询不动产单元各种权利数量集合
     */
    @Override
    public BatchBdcdyhSyncZtRequestDTO queryBdcdyhSyncZtList(List<String> bdcdyList) {
        //返回结果
        BatchBdcdyhSyncZtRequestDTO batchBdcdyhSyncZtRequestDTO = new BatchBdcdyhSyncZtRequestDTO();
        //状态集合
        List<BdcSyncZtRequestDTO> bdcdyhZtList = new CopyOnWriteArrayList<>();
        batchBdcdyhSyncZtRequestDTO.setBdcdyhZtList(bdcdyhZtList);
        if (CollectionUtils.isNotEmpty(bdcdyList)) {
            //循环线程
            List<CommonThread> listThread = new ArrayList();
            //线程对象
            Map<String, Object> taskMap = new ConcurrentHashMap();
            //判断是否走多线程计数限制
            boolean sfbjs = bdcdyList.size() <= 1;
            for (String bdcdyh : bdcdyList) {
                BdcdyZtThread bdcdyZtThread = new BdcdyZtThread(bdcdyhZtList, bdcdyh, this);
                bdcdyZtThread.setSfbjs(sfbjs);
                bdcdyZtThread.setTaskMap(taskMap);
                listThread.add(bdcdyZtThread);
            }
            //多线程处理操作
            threadEngine.excuteThread(listThread, true, taskMap);
            if (taskMap.containsKey("msg")) {
                throw new AppException(taskMap.get("msg") != null ? taskMap.get("msg").toString() : "回写不动产单元状态失败!");
            }
        }
        return batchBdcdyhSyncZtRequestDTO;
    }

    /**
     * @param bdcdyh 不动产单元
     * @return BdcSyncZtRequestDTO 不动产单元各种权利数量集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询不动产单元各种权利数量集合
     */
    @Override
    public BdcSyncZtRequestDTO queryBdcdyhSyncZt(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            BdcdyZtDTO bdcdyZtDTO = new BdcdyZtDTO();
            BdcSyncZtRequestDTO bdcSyncZtRequestDTO = new BdcSyncZtRequestDTO();

            // 存入bdcdyh
            bdcSyncZtRequestDTO.setBdcdyh(bdcdyh);

            // 查询产权
            queryCqzt(bdcdyh, bdcdyZtDTO);
            // 预告时，查到的登记状态为null
            // 同步权籍状态的时候 当dj 的时候 不能影响zx标志的逻辑
            // modified by cyc at 2021-3-16
            //if (null != bdcdyZtDTO.getDj()) {
                if (null != bdcdyZtDTO.getDj() && bdcdyZtDTO.getDj()) {
                    // 现势产权
                    bdcSyncZtRequestDTO.setDjzt(CommonConstantUtils.QJDJ_VALID.toString());
                } else if (null != bdcdyZtDTO.getZx() && bdcdyZtDTO.getZx()) {
                    // 已注销
                    bdcSyncZtRequestDTO.setDjzt(CommonConstantUtils.QJDJ_HISTORY.toString());
                }
            //}

            // 查询预告
            queryYgzt(bdcdyh, bdcdyZtDTO, bdcSyncZtRequestDTO);
            // 查询查封
            queryCfzt(bdcdyh, bdcdyZtDTO, bdcSyncZtRequestDTO);
            // 查询异议
            queryYyzt(bdcdyh, bdcdyZtDTO, bdcSyncZtRequestDTO);
            // 查询地役
            queryDyiqzt(bdcdyh, bdcdyZtDTO, bdcSyncZtRequestDTO);
            // 查询抵押
            queryDya(bdcdyh, bdcdyZtDTO, bdcSyncZtRequestDTO);
            // 查询锁定
            querySd(bdcdyh, bdcdyZtDTO, bdcSyncZtRequestDTO);
            // 查询备案状态
            queryBazt(bdcdyh, bdcdyZtDTO, bdcSyncZtRequestDTO);
            // 查询居住权
            queryJzq(bdcdyh, bdcdyZtDTO, bdcSyncZtRequestDTO);
            LOGGER.warn("当前单元号{}查询登记状态数据{}", bdcdyh, JSON.toJSONString(bdcSyncZtRequestDTO, SerializerFeature.WriteNullStringAsEmpty));
            return bdcSyncZtRequestDTO;
        }
        return null;
    }

    /**
     * @param bdcdyList 批量的单元参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新权籍不动产单元状态
     */
    @Override
    public void syncBdcdyZtByBdcdyh(List<String> bdcdyList,String qjgldm) {
        // 判断是否更新开关
        if (!dbSyncQjBdcdyzt) {
            return;
        }
        if (CollectionUtils.isEmpty(bdcdyList)) {
            throw new MissingArgumentException("缺失bdcdyList单元号信息！");
        }
        // 记录参数日志
        Map bdcdyhMap = new HashMap();
        bdcdyhMap.put(CommonConstantUtils.BDCDYH, bdcdyList);
        AuditEvent auditEvent = new AuditEvent("syncBdcdyZtByBdcdyh", CommonConstantUtils.LOG_EVENT_QJBDCDYZT, bdcdyhMap);
        try {
            zipkinAuditEventRepository.add(auditEvent);
        } catch (Exception e) {
            LOGGER.error("-------更新权籍登记不动产单元状态，日志记录接口出错-------" + e.getMessage());
        }

        BatchBdcdyhSyncZtRequestDTO batchBdcdyhSyncZtRequestDTO = this.queryBdcdyhSyncZtList(bdcdyList);
        batchBdcdyhSyncZtRequestDTO.setQjgldm(qjgldm);
        LOGGER.warn("{}同步不动产单元状态！查询到的状态信息为：{}", JSONObject.toJSONString(bdcdyList), JSONObject.toJSONString(batchBdcdyhSyncZtRequestDTO));
        //更新登记现势状态
        if(StringUtils.equals("bdcdj",dyxsztly)){
            saveBdcdjZtWithDTO(batchBdcdyhSyncZtRequestDTO);
            LOGGER.info("{}同步登记库不动产单元状态结束！",JSONObject.toJSONString(bdcdyList));
        }
        if (batchBdcdyhSyncZtRequestDTO != null) {
            synQjBdcdyztMqService.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.SYNCBDCDYZTQUEUE.getCode(), JSONObject.toJSONString(batchBdcdyhSyncZtRequestDTO));
            // 保存发送日志
            dbLogUtils.saveMqSendLog(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.SYNCBDCDYZTQUEUE.getCode(), JSONObject.toJSONString(batchBdcdyhSyncZtRequestDTO));
        }
    }

    /**
     * @param bdcXmDOList 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新权籍不动产单元的项目信息
     */
    @Override
    public void syncBdcdyxxByBdcXm(List<BdcXmDO> bdcXmDOList) {
        // 判断是否同步开关
        if (!dbSyncQjBdcdyXx) {
            return;
        }
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("缺失bdcXmDOList项目信息！");
        }
        BdcdyxxPlRequestDTO bdcdyxxPlRequestDTO = bdcDbxxService.getBdcdyxxPlRequestDTO(bdcXmDOList);
        if (bdcdyxxPlRequestDTO != null && CollectionUtils.isNotEmpty(bdcdyxxPlRequestDTO.getBdcdyxxRequestDTOList())) {
            LOGGER.warn("同步不动产单元信息！" + JSONObject.toJSONString(bdcdyxxPlRequestDTO));
            synQjBdcdyztMqService.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.BDCDYXXQUEUE.getCode(), JSONObject.toJSONString(bdcdyxxPlRequestDTO));
            // 保存发送日志
            dbLogUtils.saveMqSendLog(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.BDCDYXXQUEUE.getCode(), JSONObject.toJSONString(bdcdyxxPlRequestDTO));
        } else {
            AuditEvent auditEvent = new AuditEvent("同步权籍：同步权籍基本信息", "不动产项目同步权籍基本信息:未查询到不动产单元信息", JSONObject.toJSONString(bdcXmDOList));
            zipkinAuditEventRepository.add(auditEvent);
            LOGGER.error("不动产项目同步权籍基本信息，未查询到不动产单元信息！");
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 同步权籍基本信息和同步权籍单元状态
     */
    @Override
    public void synQjJbxxAndBdcdyzt(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("工作流实例id 不能为空！");
        }
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new EntityNotFoundException("工作量实例ID：{" + gzlslid + "}未查询到相关的项目信息！");
        }

        if (dbSyncQjBdcdyzt) {
            // 查询需要更新权籍的单元号信息
            List<String> bdcdyhList = this.querySyncQjBdcdyh(gzlslid, bdcXmDOList);
            // 批量同步不动产单元状态
            BdcXmXmfbDTO bdcXmXmfbDTO =bdcXmxxService.getOnlyOneXm(gzlslid,"");
            this.syncBdcdyZtByBdcdyh(bdcdyhList,bdcXmXmfbDTO != null ?bdcXmXmfbDTO.getQjgldm():"");
        }
        if (dbSyncQjBdcdyXx) {
            // 同步当前流程中不动产单元的基本信息
            this.syncBdcdyxxByBdcXm(bdcXmDOList);
        }

    }

    /**
     * @param xmidList 项目ID集合
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据xmid同步权籍单元状态
     */
    @Override
    public void syncBdcdyztByXmid(List<String> xmidList) {
        List<String> bdcdyhList = this.querySyncQjBdcdyh(xmidList);
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            BdcXmXmfbDTO bdcXmXmfbDTO =bdcXmxxService.getOnlyOneXm("",xmidList.get(0));
            this.syncBdcdyZtByBdcdyh(bdcdyhList,bdcXmXmfbDTO != null ?bdcXmXmfbDTO.getQjgldm():"");
        }
        LOGGER.warn("根据xmid同步权籍单元状态,项目ID参数{}", xmidList);
    }

    /**
     * @param gzlslid     工作流实例ID
     * @param bdcXmDOList 已知的当前流程的项目信息
     * @return List<String> 需要操作的不动产单元信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程需要更新权籍的不动产单元号
     */
    @Override
    public List<String> querySyncQjBdcdyh(String gzlslid, List<BdcXmDO> bdcXmDOList) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失查询参数gzlslid");
        }
        // 当前流程的项目信息
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        }

        // 获取原项目信息
        BdcXmGxQO bdcXmGxQO = new BdcXmGxQO();
        bdcXmGxQO.setGzlslid(gzlslid);
        bdcXmGxQO.setZxyql(CommonConstantUtils.SF_S_DM);
        List<BdcXmDO> bdcYxmDOList = bdcXmxxService.getListYbdcXm(bdcXmGxQO);
        if (CollectionUtils.isNotEmpty(bdcYxmDOList)) {
            bdcXmDOList.addAll(bdcYxmDOList);
        }

        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            Set<String> setBdcdyh = new HashSet<>();
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                setBdcdyh.add(bdcXmDO.getBdcdyh());
            }
            return new ArrayList<>(setBdcdyh);
        }
        return null;
    }

    /**
     * @param xmidList xmidList 项目ID
     * @return List<String> 需要操作的不动产单元信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程需要更新权籍的不动产单元号
     */
    @Override
    public List<String> querySyncQjBdcdyh(List<String> xmidList) {
        if (CollectionUtils.isEmpty(xmidList)) {
            LOGGER.error("项目ID为空！");
        }
        if (CollectionUtils.isNotEmpty(xmidList)) {
            Set<String> setBdcdyh = new HashSet<>();
            for (String xmid : xmidList) {
                BdcXmDO bdcXmDO = bdcXmxxService.getBdcXmByXmid(xmid);
                if (null != bdcXmDO) {
                    setBdcdyh.add(bdcXmDO.getBdcdyh());
                }
                // 获取原项目信息
                BdcXmGxQO bdcXmGxQO = new BdcXmGxQO();
                bdcXmGxQO.setXmid(xmid);
                bdcXmGxQO.setZxyql(CommonConstantUtils.SF_S_DM);
                List<BdcXmDO> bdcYxmDOList = bdcXmxxService.getListYbdcXm(bdcXmGxQO);
                if (CollectionUtils.isNotEmpty(bdcYxmDOList)) {
                    for (BdcXmDO ybdcXm : bdcYxmDOList) {
                        setBdcdyh.add(ybdcXm.getBdcdyh());
                    }
                }
            }

            return new ArrayList<>(setBdcdyh);
        }
        return null;
    }

    @Override
    public BdcdyZtDTO queryZsBdcdyZtByCqzsxmid(String zsxmid){
        if (StringUtils.isBlank(zsxmid)) {
            throw new MissingArgumentException("缺失查询参数证书项目ID");
        }

        BdcdyZtDTO bdcdyZtDTO = new BdcdyZtDTO();

        // 查询产权
        queryCqztByZsxmid(zsxmid, bdcdyZtDTO);
        // 如果产权已经被注销了，后面的权利不再查询
        if (null != bdcdyZtDTO.getZx() && bdcdyZtDTO.getZx()) {
            return bdcdyZtDTO;
        }
        // 查询预告
        queryXzztByZsxmid(zsxmid,bdcdyZtDTO);

        BdcXmDO bdcXmDO =bdcXmxxService.getBdcXmByXmid(zsxmid);
        if(bdcXmDO != null){
            // 查询锁定
            querySd(bdcXmDO.getBdcdyh(), bdcdyZtDTO, null);

        }


        return bdcdyZtDTO;

    }

    @Override
    public Boolean existXzqlByCqxmid(String cqxmid, Integer bdclx) {
        if(StringUtils.isBlank(cqxmid)){
            throw new MissingArgumentException("缺失查询参数产权项目ID");
        }
        BdcXmDO cqXm = this.bdcXmxxService.getBdcXmByXmid(cqxmid);
        if(cqXm != null &&StringUtils.isNotBlank(cqXm.getBdcdyh())){
            BdcXmQO bdcXmQO =new BdcXmQO();
            bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
            bdcXmQO.setBdclx(bdclx);
            bdcXmQO.setBdcdyh(cqXm.getBdcdyh());
            bdcXmQO.setQllxs(Arrays.asList(CommonConstantUtils.BDC_QLLX_XZQL));
            List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                return true;
            }
            // 查询锁定
            List<BdcDysdDO> bdcDysdDOList = bdcdySdService.queryBdcDysd(cqXm.getBdcdyh(), CommonConstantUtils.SDZT_SD, Integer.parseInt(CommonConstantUtils.BDCLX_TD_DM));
            if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据bdcdyh查询不动产单元现势状态
     */
    @Override
    public BdcdyhZtResponseDTO commonQueryBdcdyhZtBybdcdyh(String bdcdyh,String qjgldm) {
        if(!StringUtils.equals("bdcdj",dyxsztly)){
            return bdcdyhZtFeignService.queryBdcdyhZtBybdcdyh(bdcdyh,qjgldm);
        }
        return queryBdcdjBdcdyhZtBybdcdyh(bdcdyh);
    }

    /**
     * @param bdcdyhList 不动产单元列表
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 批量根据BDCDYH查询不动产单元现势状态
     */
    @Override
    public List<BdcdyhZtResponseDTO> commonListBdcdyhZtBybdcdyh(List<String> bdcdyhList,String qjgldm) {
        List<BdcdyhZtResponseDTO> bdcdyhZtResponseDTOList = new ArrayList<>();
        if (bdcdyhList.size() < 520) {
            if (CollectionUtils.isNotEmpty(bdcdyhList)) {
                for (String bdcdyh : bdcdyhList) {
                    BdcdyhZtResponseDTO bdcdyhZtResponseDTO = commonQueryBdcdyhZtBybdcdyh(bdcdyh,qjgldm);
                    if (bdcdyhZtResponseDTO != null) {
                        bdcdyhZtResponseDTOList.add(bdcdyhZtResponseDTO);
                    }
                }
            }
        } else {
            throw new AppException("查询数量过多");
        }
        return bdcdyhZtResponseDTOList;
    }

    /**
     * @param bdcdyh 新不动产单元号
     * @param bdcdyhList 合并不动产单元列表
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 批量不动产单元合并，要把这些不动产单元和限制状态求和放到新的不动产单元号里
     */
    @Override
    public void commonUpdateBdcdyZtByPlBdcdyh(String bdcdyh, List<String> bdcdyhList,String qjgldm) {
        if(StringUtils.equals("bdcdj",dyxsztly)){
            updateBdcdjBdcdyZtByPlBdcdyh(bdcdyh,bdcdyhList);
        }
        bdcdyhZtFeignService.updateBdcdyZtByPlBdcdyh(bdcdyh,bdcdyhList,qjgldm);
    }

    /**
      * @param bdcdyh 不动产单元号
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据bdcdyh查询不动产登记单元现势状态
      */
    private BdcdyhZtResponseDTO queryBdcdjBdcdyhZtBybdcdyh(String bdcdyh){
        if (StringUtils.isNotBlank(bdcdyh)) {
            BdcdyhZtResponseDTO dto = queryBdcdjZtByBdcdyh(bdcdyh);
            if(dto != null) {
                dto.setFwcq(false);
            }
            if(!BdcdyhToolUtils.isTdBdcdy(bdcdyh) && dto != null){
                if(fwcqcx) {
                    //南通需求,增加房屋拆迁状态
                    BdcZqZxsqDO bdcZqZxsqDO = bdcZqFeignService.queryZxsqByBdcdyh(bdcdyh);
                    if (bdcZqZxsqDO != null) {
                        dto.setFwcq(true);
                    }
                }
                String tdBdcdyh = BdcdyhToolUtils.convertFToW(bdcdyh);
                BdcdyhZtResponseDTO zdDto = queryBdcdjZtByBdcdyh(tdBdcdyh);
                dto.setZdZtDTO(zdDto);
            }
            return dto;
        }
        return null;

    }

    /**
     * @param bdcdyh
     * @param bdcdyhList
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 批量不动产单元合并，要把这些不动产单元和限制状态求和放到新的不动产单元号里
     */

    public Integer updateBdcdjBdcdyZtByPlBdcdyh(String bdcdyh, List<String> bdcdyhList) {
        Integer updateResult = 0;
        if (CollectionUtils.isNotEmpty(bdcdyhList) && StringUtils.isNotBlank(bdcdyh)) {
            BdcBdcdyhxsztDO sSjBdcdyhxsztDO = querySsjBdcdyhxsztDOByBdcdyh(bdcdyh);
            if (sSjBdcdyhxsztDO != null) {
                for (String bdcdyTemp : bdcdyhList) {
                    if (!StringUtils.equals(bdcdyTemp, bdcdyh)) {
                        BdcBdcdyhxsztDO sSjBdcdyhxsztTemp = querySsjBdcdyhxsztDOByBdcdyh(bdcdyTemp);
                        if (sSjBdcdyhxsztTemp != null) {
                            sSjBdcdyhxsztDO.setXszjgcdycs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXszjgcdycs(), sSjBdcdyhxsztTemp.getXszjgcdycs()));
                            sSjBdcdyhxsztDO.setXsygcs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsygcs(), sSjBdcdyhxsztTemp.getXsygcs()));
                            sSjBdcdyhxsztDO.setXsydyacs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsydyacs(), sSjBdcdyhxsztTemp.getXsydyacs()));
                            sSjBdcdyhxsztDO.setXsdyacs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsdyacs(), sSjBdcdyhxsztTemp.getXsdyacs()));
                            sSjBdcdyhxsztDO.setXsycfcs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsycfcs(), sSjBdcdyhxsztTemp.getXsycfcs()));
                            sSjBdcdyhxsztDO.setXscfcs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXscfcs(), sSjBdcdyhxsztTemp.getXscfcs()));
                            sSjBdcdyhxsztDO.setXsyycs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsyycs(), sSjBdcdyhxsztTemp.getXsyycs()));
                            sSjBdcdyhxsztDO.setXsdyics(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXsdyics(), sSjBdcdyhxsztTemp.getXsdyics()));
                            sSjBdcdyhxsztDO.setXssdcs(CommonUtil.plusTwoInterger(sSjBdcdyhxsztDO.getXssdcs(), sSjBdcdyhxsztTemp.getXssdcs()));
                        }
                    }
                }
                updateResult = entityMapper.updateByPrimaryKeySelective(sSjBdcdyhxsztDO);
            }
        }
        return updateResult;
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description   根据bdcdyh查询不动产登记单元现势状态
      */
    private BdcdyhZtResponseDTO queryBdcdjZtByBdcdyh(String bdcdyh){
        if (StringUtils.isNotBlank(bdcdyh)) {
            // 使用资源服务 和楼盘表展现页面使用一个处理逻辑 判断状态
            BdcBdcdyhxsztDO sSjBdcdyhxsztDO = querySsjBdcdyhxsztDOByBdcdyh(bdcdyh);
            if(sSjBdcdyhxsztDO == null){
                return null;
            }
            BdcdyhZtResponseDTO dto =BdcdyztToolUtils.revertDTO(sSjBdcdyhxsztDO);
            dto.setBdcdyh(bdcdyh);
            return dto;
        }
        return null;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询 状态实体
     */
    public BdcBdcdyhxsztDO querySsjBdcdyhxsztDOByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            return entityMapper.selectByPrimaryKey(BdcBdcdyhxsztDO.class, bdcdyh);
        }
        return null;
    }

    /**
      * @param batchDTO 批量更新实体
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 批量更新状态表
      */
    public void saveBdcdjZtWithDTO(BatchBdcdyhSyncZtRequestDTO batchDTO) {
        if (batchDTO != null && CollectionUtils.isNotEmpty(batchDTO.getBdcdyhZtList())) {
            for (BdcSyncZtRequestDTO dto : batchDTO.getBdcdyhZtList()) {
                if (!CheckEntityUtils.checkPkAndFk(dto)) {
                    throw new IllegalArgumentException("主键为空");
                }
                BdcBdcdyhxsztDO xszt = querySsjBdcdyhxsztDOByBdcdyh(dto.getBdcdyh());

                // 如果不存在 直接新增一条
                if(xszt == null){
                    xszt = new BdcBdcdyhxsztDO();
                    BeanUtils.copyProperties(dto,xszt);
                    xszt.setGxsj(new Date());
                    LOGGER.info("新增现势状态：{}",xszt);
                    insertXsztDO(xszt);
                }else{
                    BeanUtils.copyProperties(dto,xszt);
                    xszt.setGxsj(new Date());
                    // 存在即更新
                    LOGGER.info("更新现势状态：{}",xszt);
                    updateXsztDO(xszt,true);
                }
            }
        } else {
            throw new IllegalArgumentException("更新列表为空");
        }
    }

    /**
      * @param batchDTO 锁定更新实体
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 保存锁定状态
      */
    @Override
    public void saveBdcdjSdZtWithDTO(BatchBdcdyhSyncZtRequestDTO batchDTO) {
        if (batchDTO != null && CollectionUtils.isNotEmpty(batchDTO.getBdcdyhZtList())) {
            for (BdcSyncZtRequestDTO dto : batchDTO.getBdcdyhZtList()) {
                if (!CheckEntityUtils.checkPkAndFk(dto)) {
                    throw new IllegalArgumentException("主键为空");
                }
                BdcBdcdyhxsztDO xszt = querySsjBdcdyhxsztDOByBdcdyh(dto.getBdcdyh());

                // 如果不存在 直接新增一条
                if(xszt == null){
                    xszt = new BdcBdcdyhxsztDO();
                    BeanUtils.copyProperties(dto, xszt);
                    xszt.setGxsj(new Date());
                    LOGGER.info("新增现势状态：{}", xszt);
                    insertXsztDO(xszt);
                }else{
                    xszt.setXssdcs(dto.getXssdcs());
                    // 存在即更新
                    LOGGER.info("更新现势状态：{}",xszt);
                    updateXsztDO(xszt,true);
                }
            }
        } else {
            throw new IllegalArgumentException("更新列表为空");
        }
    }

    @Override
    public List<BdcdyhZtResponseDTO> commonListBdcdyhZtPlcx(List<String> bdcdyhList,String qjgldm){
        if(StringUtils.equals("bdcdj",dyxsztly)){
            return listBdcdyhZtPlcx(bdcdyhList);
        }
        return bdcdyhZtFeignService.listBdcdyhZtPlcx(bdcdyhList,qjgldm);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @return {List} 现势状态信息
     * @description 批量查询不动产单元现势状态信息（不循环查询避免批量查询慢）-登记库
     */
    private List<BdcdyhZtResponseDTO> listBdcdyhZtPlcx(List<String> bdcdyhList) {
        if(CollectionUtils.isEmpty(bdcdyhList)) {
            return Collections.emptyList();
        }
        //批量查询登记库状态表
        List<BdcBdcdyhxsztDO> bdcdyhxsztDOList = bdcdyZtMapper.listDjBdcdyXszt(bdcdyhList);
        if(CollectionUtils.isEmpty(bdcdyhxsztDOList)) {
            return Collections.emptyList();
        }

        List<BdcdyhZtResponseDTO> result = new ArrayList<>();
        for(BdcBdcdyhxsztDO xszt : bdcdyhxsztDOList) {
            // 使用资源服务 和楼盘表展现页面使用一个处理逻辑 判断状态
            if(xszt == null){
                continue;
            }
            BdcdyhZtResponseDTO dto = BdcdyztToolUtils.revertDTO(xszt);
            dto.setBdcdyh(xszt.getBdcdyh());
            dto.setXszjgcdycs(xszt.getXszjgcdycs());
            dto.setXsygcs(xszt.getXsygcs());
            dto.setXsydyacs(xszt.getXsydyacs());
            dto.setXsdyacs(xszt.getXsdyacs());
            dto.setXsycfcs(xszt.getXsycfcs());
            dto.setXscfcs(xszt.getXscfcs());
            dto.setXsyycs(xszt.getXsyycs());
            dto.setXsdyics(xszt.getXsdyics());
            dto.setXssdcs(xszt.getXssdcs());
            dto.setXsjzqcs(xszt.getXsjzqcs());
            result.add(dto);
        }

        return result;
    }

    /**
      * @param xsztDO 新增实体
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 新增状态表
      */
    public void insertXsztDO(BdcBdcdyhxsztDO xsztDO) {
        if(xsztDO != null && StringUtils.isNotBlank(xsztDO.getBdcdyh())){
            try {
                entityMapper.saveOrUpdate(xsztDO,xsztDO.getBdcdyh());
            } catch (Exception e){
                LOGGER.error("保存xszt报错,xszt {}",xsztDO.toString(),e);
            }
        }
    }

    /**
      * @param xsztDO 更新实体
      * @param updateNull 是否更新空值
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 更新状态表
      */
    public void updateXsztDO(BdcBdcdyhxsztDO xsztDO, boolean updateNull) {
        if(xsztDO != null && StringUtils.isNotBlank(xsztDO.getBdcdyh())){
            if(updateNull){
                entityMapper.updateByPrimaryKeyNull(xsztDO);
            }else{
                entityMapper.updateByPrimaryKeySelective(xsztDO);
            }
        }
    }

}
