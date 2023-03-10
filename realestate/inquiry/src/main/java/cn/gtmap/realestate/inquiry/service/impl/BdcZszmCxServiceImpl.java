package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZszmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcSzgzlTjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZsTjQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZsTjVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZsmxTjVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.core.dto.BdcFdcqGhytDTO;
import cn.gtmap.realestate.inquiry.core.dto.BdcQlfjDTO;
import cn.gtmap.realestate.inquiry.core.mapper.BdcQlrMapper;
import cn.gtmap.realestate.inquiry.core.mapper.BdcSzgzlTjMapper;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZszmMapper;
import cn.gtmap.realestate.inquiry.service.BdcZszmCxService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/5/15
 * @description ????????????????????????????????????????????????????????????????????????????????????
 */
@Service
public class BdcZszmCxServiceImpl implements BdcZszmCxService {
    public static final Logger LOGGER = LoggerFactory.getLogger(BdcZszmCxServiceImpl.class);

    private static final String BDC_ZSZM_PRINT_INFO = "BDC_ZSZM_PRINT_INFO_";

    /** ?????????????????????????????????????????????????????? */
    @Value("${zhcx.djxldm:}")
    private String djxldm;

    /** ??????????????????????????????????????? */
    @Value("${zhcx.djlx:}")
    private String djlx;

    /** ?????????????????????????????????????????????????????? */
    @Value("${zhcx.wcqcxzt: false}")
    private boolean wcqcxzt;

    /** ???????????????????????????????????????????????????????????????????????? */
    @Value("${zhcx.qlrmcReplaceBracket: true}")
    private boolean qlrmcReplaceBracket;

    /** ???????????????????????????????????? */
    @Value("${zhcx.qlfj:false}")
    private boolean zhcxQlfj;

    /** ??????????????????????????????????????????????????????????????? */
    @Value("${zhcx.zhzlkh:false}")
    private boolean zhcxZhzlkh;
    /**
     * ORM??????
     */
    @Autowired
    private Repo repo;
    /**
     * ????????????ORM??????
     */
    @Autowired
    private BdcZszmMapper bdcZszmMapper;

    @Autowired
    private BdcQlrMapper qlrMapper;

    /**
     * ???????????????????????????
     */
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    /**
     * ??????????????????
     */
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    /**
     * ??????????????????
     */
    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    private BdcSzgzlTjMapper bdcSzgzlTjMapper;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    private RedisUtils redisUtils;


    /**
     * ???????????????????????????????????????????????????
     * @param pageable  ????????????
     * @param bdcZszmQO ????????????
     * @return {Page} ????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public Page<BdcZszmDTO> listBdcZszm(Pageable pageable, BdcZszmQO bdcZszmQO) {
        // ???????????????????????????
        Page<BdcZszmDTO> bdcZszmDTOPage = repo.selectPaging("listBdcZszmByPageOrder", this.resolveParam(bdcZszmQO), pageable);
        if (null == bdcZszmDTOPage || CollectionUtils.isEmpty(bdcZszmDTOPage.getContent())) {
            return bdcZszmDTOPage;
        }

        //??????
        List<BdcZszmDTO> bdcZszmDTOList = bdcZszmDTOPage.getContent().stream().distinct().collect(Collectors.toList());
        List<String> xmidList = bdcZszmDTOList.stream().map(BdcZszmDTO::getXmid).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(xmidList)) {
            return bdcZszmDTOPage;
        }

        List<List> lists = ListUtils.subList(xmidList,500);
        if(CollectionUtils.isNotEmpty(lists)){
            for (List list : lists) {
                // ?????????????????????????????????
                this.setXmndzGhyt(bdcZszmDTOList, list);
                // ????????????????????????
                this.getZsFjXx(bdcZszmDTOList, list);
                // ??????????????????
                this.setQlFj(bdcZszmDTOList, list);
            }
        }
        List<List> listList = ListUtils.subList(bdcZszmDTOList,500);
        if(CollectionUtils.isNotEmpty(listList)){
            for (List list : listList) {
                // ????????????????????????
                this.setBdcdyXzzt(list);
            }
        }

        return bdcZszmDTOPage;
    }

    /**
     * @param xmidList ??????ID????????????
     * @return {List} ??????????????????????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ??????XMID????????????????????????????????????
     * ?????????
     * 1???????????????????????????????????????????????????????????????????????????????????????????????????
     * 2???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * ??????????????????????????????????????????????????????
     */
    @Override
    public List<BdcZhcxDTO> listZhcxFjxx(List<String> xmidList) {
        if (CollectionUtils.isEmpty(xmidList)) {
            return Collections.emptyList();
        }

        Map<String, BdcZhcxDTO> zhcxFjxxMap = new HashMap<>(10);

        // ?????????????????????
        List<BdcZhcxDTO> qlxxDTOList = bdcZszmMapper.listQlxxDTO(xmidList);
        if (CollectionUtils.isNotEmpty(qlxxDTOList)) {
            for (BdcZhcxDTO bdcZhcxDTO : qlxxDTOList) {
                if (StringUtils.isNotBlank(bdcZhcxDTO.getXmid())) {
                    zhcxFjxxMap.put(bdcZhcxDTO.getXmid(), bdcZhcxDTO);
                }
            }
        }

        // ????????????????????????
        List<BdcZhcxDTO> qlrDOList = bdcZszmMapper.listQlrDTO(xmidList);

        // ??????qllx?????????????????????
        List<BdcXmDO> listBfxm = bdcZszmMapper.listBfBdcxm(xmidList);

        // ???????????????????????????
        List<BdcJsydsyqDO> listSyqmjBdcxm = bdcZszmMapper.listSyqmjBdcxm(xmidList);

        if (CollectionUtils.isNotEmpty(qlrDOList)) {
            // ????????????ID??????
            Map<String, List<BdcZhcxDTO>> qlrDOMapByXmid = qlrDOList.stream().collect(Collectors.groupingBy(BdcZhcxDTO::getXmid));
            BdcXmQO bdcXmQO = new BdcXmQO();
            if (MapUtils.isNotEmpty(qlrDOMapByXmid)) {
                for (Map.Entry<String, List<BdcZhcxDTO>> entry : qlrDOMapByXmid.entrySet()) {
                    if (CollectionUtils.isNotEmpty(entry.getValue())) {
                        // ????????????????????????????????????
                        String dhStr = entry.getValue().stream().filter(zhcx ->
                                StringUtils.isNotBlank(zhcx.getDh())).map(BdcZhcxDTO::getDh)
                                .distinct().collect(Collectors.joining(","));

                        if (MapUtils.isNotEmpty(zhcxFjxxMap) && zhcxFjxxMap.containsKey(entry.getKey())) {
                            zhcxFjxxMap.get(entry.getKey()).setDh(dhStr);
                        } else {
                            BdcZhcxDTO bdcZhcxDTO = new BdcZhcxDTO();
                            bdcZhcxDTO.setXmid(entry.getKey());
                            bdcZhcxDTO.setDh(dhStr);
                            zhcxFjxxMap.put(entry.getKey(), bdcZhcxDTO);
                        }
                        // ?????????????????? ?????????????????????
                        // added by cyc at 2020-4-21
                        if (CollectionUtils.isNotEmpty(listBfxm)) {
                            for (int k = 0; k < listBfxm.size(); k++) {
                                if (listBfxm.get(k).getXmid().equals(entry.getKey())) {
                                    BdcZhcxDTO bdcZhcxDTO = new BdcZhcxDTO();
                                    bdcZhcxDTO.setXmid(entry.getKey());
                                    bdcZhcxDTO.setDh(dhStr);
                                    bdcZhcxDTO.setTdsyqmj(listBfxm.get(k).getZdzhmj() + "");
                                    zhcxFjxxMap.put(entry.getKey(), bdcZhcxDTO);
                                    break;
                                }
                            }
                        }
                        for (BdcJsydsyqDO bdcJsydsyqDO : listSyqmjBdcxm) {
                            if (bdcJsydsyqDO.getXmid().equals(entry.getKey())) {
                                zhcxFjxxMap.get(entry.getKey()).setTdzsyqmj(doubleToString(bdcJsydsyqDO.getSyqmj()));
                                break;
                            }
                        }
                    }
                }
            }
        }

        // ??????????????????????????????
        List<BdcZhcxDTO> bdcZhcxDTOList = new ArrayList<>(zhcxFjxxMap.size());
        for (Map.Entry<String, BdcZhcxDTO> entry : zhcxFjxxMap.entrySet()) {
            bdcZhcxDTOList.add(entry.getValue());
        }
        return bdcZhcxDTOList;

    }

    /**
     * ????????????????????????????????????
     * @param bdcZszmDTOList ????????????
     * @param xmidList ??????ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void setXmndzGhyt(List<BdcZszmDTO> bdcZszmDTOList, List<String> xmidList) {
        List<BdcFdcqGhytDTO> ghytDTOList = bdcZszmMapper.listFdcqXmGhyt(xmidList);
        if(CollectionUtils.isNotEmpty(ghytDTOList)) {
            Map<String, List<BdcFdcqGhytDTO>> xmGhytMap = ghytDTOList.stream().collect(Collectors.groupingBy(BdcFdcqGhytDTO::getXmid));
            for(BdcZszmDTO bdcZszmDTO : bdcZszmDTOList) {
                List<BdcFdcqGhytDTO> xmGhyt = xmGhytMap.get(bdcZszmDTO.getXmid());
                if(CollectionUtils.isEmpty(xmGhyt)) {
                    continue;
                }
                bdcZszmDTO.setGhyt(StringToolUtils.joinBeanAttribute(xmGhyt, "getGhyt", CommonConstantUtils.ZF_YW_DH));
            }
        }
    }

    /**
     * @param bdcZszmDTOList ????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ????????????????????????
     */
    private void getZsFjXx(List<BdcZszmDTO> bdcZszmDTOList, List<String> xmidList) {
        if (CollectionUtils.isEmpty(bdcZszmDTOList)) {
            return;
        }

        // ??????XMID??????????????????
        List<BdcZsxxDTO> bdcZsDOList = bdcZszmMapper.listBdcZsDO(xmidList);
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            return;
        }

        Map<String, List<BdcZsxxDTO>> map = bdcZsDOList.stream().collect(Collectors.groupingBy(BdcZsxxDTO::getXmid));
        for (BdcZszmDTO bdcZszmDTO : bdcZszmDTOList) {
            // ????????????????????????????????????id
            List<BdcZsxxDTO> zsxxDTOList = map.get(bdcZszmDTO.getXmid());
            if (CollectionUtils.isNotEmpty(zsxxDTOList)) {
                bdcZszmDTO.setZhlsh(StringToolUtils.resolveBeanToAppendStr(zsxxDTOList, "getZhlsh", ","));
                if (null != zsxxDTOList.get(0)) {
                    bdcZszmDTO.setZslx(zsxxDTOList.get(0).getZslx());
                    bdcZszmDTO.setZsid(zsxxDTOList.get(0).getZsid());
                }
            }
        }
    }

    /**
     * ??????????????????????????????
     * @param bdcZszmDTOList ????????????
     * @param xmidList ??????ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void setQlFj(List<BdcZszmDTO> bdcZszmDTOList, List<String> xmidList) {
        if(!zhcxQlfj) return;

        List<BdcQlfjDTO> qlfjList = bdcZszmMapper.listBdcQlfj(xmidList);
        if(CollectionUtils.isEmpty(qlfjList)) {
            return;
        }

        Map<String, List<BdcQlfjDTO>> qlfjMap = qlfjList.stream().collect(Collectors.groupingBy(BdcQlfjDTO::getXmid));
        for (BdcZszmDTO bdcZszmDTO : bdcZszmDTOList) {
            if (StringUtils.isNotBlank(bdcZszmDTO.getXmid())) {
                List<BdcQlfjDTO> qlfj = qlfjMap.get(bdcZszmDTO.getXmid());
                if(CollectionUtils.isEmpty(qlfj) || null == qlfj.get(0)) {
                    continue;
                }
                bdcZszmDTO.setFj(qlfj.get(0).getFj());
            }
        }
    }

    /**
     * ????????????????????????
     * @param bdcZszmDTOList ????????????
     */
    private void setBdcdyXzzt(List<BdcZszmDTO> bdcZszmDTOList) {
        // ???????????????????????????
        /// 1????????????????????????????????????
        List<String> bdcdyhList = new ArrayList<>(bdcZszmDTOList.size());
        // ??????????????????????????????????????????????????????
        Map<String,List<String>> qjgldmBdcdyhMap =new HashMap<>();
        for (BdcZszmDTO bdcZszmDTO : bdcZszmDTOList) {
            if (null == bdcZszmDTO || null == bdcZszmDTO.getQszt() || StringUtils.isBlank(bdcZszmDTO.getBdcdyh())) {
                continue;
            }
            // ??????????????????????????????????????????, ??? ?????????????????????????????????????????????????????????????????????wcqcxzt: true???
            if ((this.isZsZmd(bdcZszmDTO) || wcqcxzt) && CommonConstantUtils.QSZT_VALID.equals(bdcZszmDTO.getQszt())) {
                if(StringUtils.isNotBlank(bdcZszmDTO.getQjgldm())) {
                    if (qjgldmBdcdyhMap.containsKey(bdcZszmDTO.getQjgldm())) {
                        List<String> dyhList = qjgldmBdcdyhMap.get(bdcZszmDTO.getQjgldm());
                        dyhList.add(bdcZszmDTO.getBdcdyh());
                        qjgldmBdcdyhMap.put(bdcZszmDTO.getQjgldm(),dyhList);
                    }else{
                        List<String> dyhList =new ArrayList<>();
                        dyhList.add(bdcZszmDTO.getBdcdyh());
                        qjgldmBdcdyhMap.put(bdcZszmDTO.getQjgldm(),dyhList);

                    }
                }else{
                    bdcdyhList.add(bdcZszmDTO.getBdcdyh());
                }
            }
        }
        if (CollectionUtils.isEmpty(bdcdyhList) && MapUtils.isEmpty(qjgldmBdcdyhMap)) {
            return;
        }

        // ???????????????????????????????????????????????????qjgldmBdcdyhMap???
        if(MapUtils.isNotEmpty(qjgldmBdcdyhMap) && CollectionUtils.isNotEmpty(bdcdyhList)){
            qjgldmBdcdyhMap.put("", bdcdyhList);
        }

        /// 2???????????????
        List<BdcdyhZtResponseDTO> bdcdyhZtDTOList =new ArrayList<>();
        if(MapUtils.isNotEmpty(qjgldmBdcdyhMap)){
            for (Map.Entry<String, List<String>> entry : qjgldmBdcdyhMap.entrySet()) {
                bdcdyhZtDTOList.addAll(bdcdyZtFeignService.commonListBdcdyhZtPlcx(entry.getValue(),entry.getKey()));
            }
        }else {
            bdcdyhZtDTOList = bdcdyZtFeignService.commonListBdcdyhZtPlcx(bdcdyhList, "");
        }
        if (CollectionUtils.isEmpty(bdcdyhZtDTOList)) {
            return;
        }
        /// 3????????????????????????????????????
        for (BdcZszmDTO bdcZszmDTO : bdcZszmDTOList) {
            for (BdcdyhZtResponseDTO bdcdyhZtDTO : bdcdyhZtDTOList) {
                if (StringUtils.equals(bdcZszmDTO.getBdcdyh(), bdcdyhZtDTO.getBdcdyh()) && (this.isZsZmd(bdcZszmDTO) || wcqcxzt)
                        && null != bdcZszmDTO.getQszt() && CommonConstantUtils.QSZT_VALID.equals(bdcZszmDTO.getQszt())) {
                    bdcZszmDTO.setBdcdyZtDTO(bdcdyhZtDTO);
                }
            }
        }
    }

    /**
     * ???????????????????????????????????????????????????
     * @param bdcZszmDTO ????????????
     * @return true: ??????????????????  false: ????????????
     */
    private boolean isZsZmd(BdcZszmDTO bdcZszmDTO) {
        return Objects.nonNull(bdcZszmDTO.getZslx())
                && (CommonConstantUtils.ZSLX_ZS.equals(bdcZszmDTO.getZslx()) || CommonConstantUtils.ZSLX_ZMD.equals(bdcZszmDTO.getZslx()));
    }

    /**
     * @param bdcZszmQO ????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ??????????????????
     */
    private BdcZszmQO resolveParam(BdcZszmQO bdcZszmQO) {
        // ??????????????????????????????????????????
        bdcZszmQO.setDjxldm(djxldm);

        // ?????????????????????????????????
        bdcZszmQO.setDjlx(djlx);

        // ??????????????????:18??????15????????????????????????????????????????????????
        /// ??????????????????
        if (null != bdcZszmQO.getQlrzjh() && bdcZszmQO.getQlrzjh().length > 0) {
            bdcZszmQO.setQlrzjh(Stream.of(bdcZszmQO.getQlrzjh()).map(e ->
                    CardNumberTransformation.zjhTransformation(e)).collect(Collectors.joining(",")).split(","));
        }
        /// ??????????????????
        if (null != bdcZszmQO.getYwrzjh() && bdcZszmQO.getYwrzjh().length > 0) {
            bdcZszmQO.setYwrzjh(Stream.of(bdcZszmQO.getYwrzjh()).map(e ->
                    CardNumberTransformation.zjhTransformation(e)).collect(Collectors.joining(",")).split(","));
        }

        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        //??????????????????????????????
        if (Boolean.TRUE.equals(zhcxZhzlkh) && StringUtils.isNotBlank(bdcZszmQO.getZl())) {
            bdcZszmQO.setZl(StringToolUtils.replaceBracket(bdcZszmQO.getZl()));
        }
        if (StringUtils.isNotBlank(bdcZszmQO.getBdcqzh())) {
            bdcZszmQO.setBdcqzh(StringToolUtils.replaceBracket(bdcZszmQO.getBdcqzh()));
        }
        if (StringUtils.isNotBlank(bdcZszmQO.getYcqzh())) {
            bdcZszmQO.setYcqzh(StringToolUtils.replaceBracket(bdcZszmQO.getYcqzh()));
        }

        if(CommonConstantUtils.SYSTEM_VERSION_YC.equals(bdcZszmQO.getVersion())){
            // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            if (null != bdcZszmQO.getQlrmc() && bdcZszmQO.getQlrmc().length > 0) {
                Set<String> qlrmcSet = new TreeSet<>();
                for (int i = 0; i < bdcZszmQO.getQlrmc().length; i++) {
                    qlrmcSet.add(bdcZszmQO.getQlrmc()[i]);
                    qlrmcSet.add(StringToolUtils.replaceBracket(bdcZszmQO.getQlrmc()[i]));
                }
                bdcZszmQO.setQlrmc(qlrmcSet.toArray(new String[0]));
            }
            if (null != bdcZszmQO.getYwrmc() && bdcZszmQO.getYwrmc().length > 0) {
                Set<String> ywrmcSet = new TreeSet<>();
                for (int i = 0; i < bdcZszmQO.getYwrmc().length; i++) {
                    ywrmcSet.add(bdcZszmQO.getYwrmc()[i]);
                    ywrmcSet.add(StringToolUtils.replaceBracket(bdcZszmQO.getYwrmc()[i]));
                }
                bdcZszmQO.setYwrmc(ywrmcSet.toArray(new String[0]));
            }
        } else {
            if(qlrmcReplaceBracket){
                if (null != bdcZszmQO.getQlrmc() && bdcZszmQO.getQlrmc().length > 0) {
                    for (int i = 0; i < bdcZszmQO.getQlrmc().length; i++) {
                        bdcZszmQO.getQlrmc()[i] = StringToolUtils.replaceBracket(bdcZszmQO.getQlrmc()[i]);
                    }
                }
            }
            if (null != bdcZszmQO.getYwrmc() && bdcZszmQO.getYwrmc().length > 0) {
                for (int i = 0; i < bdcZszmQO.getYwrmc().length; i++) {
                    bdcZszmQO.getYwrmc()[i] = StringToolUtils.replaceBracket(bdcZszmQO.getYwrmc()[i]);
                }
            }
        }

        if (null != bdcZszmQO.getQlrzjh() && bdcZszmQO.getQlrzjh().length > 0) {
            for (int i = 0; i < bdcZszmQO.getQlrzjh().length; i++) {
                bdcZszmQO.getQlrzjh()[i] = StringToolUtils.replaceBracket(bdcZszmQO.getQlrzjh()[i]);
            }
        }
        if (null != bdcZszmQO.getYwrzjh() && bdcZszmQO.getYwrzjh().length > 0) {
            for (int i = 0; i < bdcZszmQO.getYwrzjh().length; i++) {
                bdcZszmQO.getYwrzjh()[i] = StringToolUtils.replaceBracket(bdcZszmQO.getYwrzjh()[i]);
            }
        }

        return bdcZszmQO;
    }

    /**
     * @param pageable  ????????????
     * @param bdcZszmQO ????????????
     * @return {Page} ????????????????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ????????????ID???????????????????????????
     */
    @Override
    public Page<BdcXmDO> listBdcZszmXmxx(Pageable pageable, BdcZszmQO bdcZszmQO) {
        if (null == bdcZszmQO || StringUtils.isBlank(bdcZszmQO.getZsid())) {
            throw new NullPointerException("??????????????????????????????ID??????????????????????????????????????????");
        }

        return repo.selectPaging("listBdcZszmXmxxByPageOrder", bdcZszmQO, pageable);
    }

    /**
     * @param zsid ??????ID
     * @return {List} ????????????????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description ???????????????????????????????????????????????????
     */
    @Override
    public List<String> listBdcZszmBdcdyh(String zsid) {
        if (StringUtils.isBlank(zsid)) {
            throw new NullPointerException("????????????????????????????????????????????????????????????????????????");
        }

        return bdcZszmMapper.listBdcZszmBdcdyh(zsid);
    }

    /**
     * @param bdcZsTjQO
     * @return {List} ??????????????????
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    public List<BdcZsTjVO> listBdcZsTj(BdcZsTjQO bdcZsTjQO) {
        if (bdcZsTjQO.getDjbmdm().split(",").length > 1) {
            bdcZsTjQO.setDjbmdmlist(Arrays.asList(bdcZsTjQO.getDjbmdm().split(",")));
        } else if (StringUtils.isNotBlank(bdcZsTjQO.getDjbmdm())) {
            bdcZsTjQO.setDjbmdmlist(Arrays.asList(bdcZsTjQO.getDjbmdm()));
        } else {
            bdcZsTjQO.setDjbmdmlist(new ArrayList());
        }

        if (bdcZsTjQO.getZslx().split(",").length > 1) {
            bdcZsTjQO.setZslxlist(Arrays.asList(bdcZsTjQO.getZslx().split(",")));
        } else if (StringUtils.isNotBlank(bdcZsTjQO.getZslx())) {
            bdcZsTjQO.setZslxlist(Arrays.asList(bdcZsTjQO.getZslx()));
        } else {
            bdcZsTjQO.setZslxlist(new ArrayList());
        }
        return repo.selectList("listBdcZsTj", bdcZsTjQO);
    }
    /**
     * @param bdcZsTjQO
     * @return {List} ????????????????????????
     * @author <a href ="mailto:wutao2@gtmap.cn">wutao</a>
     * @description
     */
    @Override
    public List<BdcZsmxTjVO> listBdcZsmxTj(Pageable pageable ,BdcZsTjQO bdcZsTjQO) {

        if (StringUtils.isNotBlank(bdcZsTjQO.getDjbmdm()) && bdcZsTjQO.getDjbmdm().split(",").length > 1) {
            bdcZsTjQO.setDjbmdmlist(Arrays.asList(bdcZsTjQO.getDjbmdm().split(",")));
        } else if (StringUtils.isNotBlank(bdcZsTjQO.getDjbmdm())) {
            bdcZsTjQO.setDjbmdmlist(Arrays.asList(bdcZsTjQO.getDjbmdm()));
        } else {
            bdcZsTjQO.setDjbmdmlist(new ArrayList());
        }

        if (StringUtils.isNotBlank(bdcZsTjQO.getZslx()) && bdcZsTjQO.getZslx().split(",").length > 1) {
            bdcZsTjQO.setZslxlist(Arrays.asList(bdcZsTjQO.getZslx().split(",")));
        } else if (StringUtils.isNotBlank(bdcZsTjQO.getZslx())) {
            bdcZsTjQO.setZslxlist(Arrays.asList(bdcZsTjQO.getZslx()));
        } else {
            bdcZsTjQO.setZslxlist(new ArrayList());
        }
        return repo.selectList("listBdcZsmxTj", bdcZsTjQO);
    }

    /**
     * @param bdcGzYzQO
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcGzYzQO] ??????????????????
     * @return: List<String> ??????????????????
     * @description ?????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    @Override
    public List<Map<String, Object>> checkBdcdyhGz(BdcGzYzQO bdcGzYzQO) {
        final List<BdcGzYzTsxxDTO> bdcGzYzTsxxDTOList = this.bdcGzZhGzFeignService.listBdcGzYzTsxx(bdcGzYzQO);
        List<Map<String, Object>> promptMsgList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcGzYzTsxxDTOList)) {
            for (BdcGzYzTsxxDTO bdcGzYzTsxxDTO : bdcGzYzTsxxDTOList) {
                final List<BdcGzZgzTsxxDTO> bdcGzZgzTsxxDTOList = bdcGzYzTsxxDTO.getZgzTsxxDTOList();
                if (CollectionUtils.isNotEmpty(bdcGzZgzTsxxDTOList)) {
                    for (BdcGzZgzTsxxDTO bdcGzZgzTsxxDTO : bdcGzZgzTsxxDTOList) {
                        final List<String> tsxxList = bdcGzZgzTsxxDTO.getTsxx();
                        if (CollectionUtils.isNotEmpty(bdcGzZgzTsxxDTO.getTsxx())) {
                            for (String tsxx : tsxxList) {
                                if (StringUtils.isNotBlank(tsxx)) {
                                    Map<String, Object> tsxxMap = new HashMap<>();
                                    // ccx 2019-10-04  ??????????????????????????????
                                    tsxxMap.put("tsxx", tsxx);
                                    tsxxMap.put("xzxx", bdcGzZgzTsxxDTO.getSjljg());
                                    tsxxMap.put("yxj", bdcGzZgzTsxxDTO.getYxj());
                                    promptMsgList.add(tsxxMap);
                                }
                            }
                        }
                    }
                }
            }
        }
        return promptMsgList;
    }

    /**
     * ??????fdcq?????????????????????????????????????????????????????????????????????
     *
     * @param gzlslid
     * @return num
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Integer getFdcqCount(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new NullPointerException("????????????????????????????????????????????????????????????");
        }
        return bdcZszmMapper.getFdcqCount(gzlslid);
    }

    /**
     * ??????????????????????????????1
     *
     * @param gzlslid
     * @return num
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Integer getDyaqCount(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new NullPointerException("????????????????????????????????????????????????????????????");
        }
        return bdcZszmMapper.getDyaqCount(gzlslid);
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param pageable
     * @param parseObject
     * @return BdcZsTjDTO BdcZsTjDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<BdcZsTjDTO> getZszmCount(Pageable pageable, BdcZsTjQO parseObject) {

        List<BdcZsTjDTO> list = Lists.newArrayList();
        Page<BdcZsTjDTO> bdcZsTjDTOPage = repo.selectPaging("getZszmCount", parseObject, pageable);
        if (null != bdcZsTjDTOPage.getContent() && CollectionUtils.isNotEmpty(bdcZsTjDTOPage.getContent())) {
            List<String> hasAddData = Lists.newArrayList();
            for (int i = 0; i < bdcZsTjDTOPage.getContent().size(); i++) {
                BdcZsTjDTO bdcZsTjDTO1 = bdcZsTjDTOPage.getContent().get(i);
                if (hasAddData.contains(bdcZsTjDTO1.getDjjg())) {
                    continue;
                }
                for (int j = i; j < bdcZsTjDTOPage.getContent().size(); j++) {
                    BdcZsTjDTO bdcZsTjDTO = bdcZsTjDTOPage.getContent().get(j);
                    if (bdcZsTjDTO.getDjjg().equals(bdcZsTjDTO1.getDjjg())) {

                        if (!bdcZsTjDTO.getZslx().equals(bdcZsTjDTO1.getZslx())) {
                            bdcZsTjDTO1.setZmsl(bdcZsTjDTO.getZssl());
                            bdcZsTjDTO1.setXh(String.valueOf((i + 1)));
                            list.add(bdcZsTjDTO1);
                            hasAddData.add(bdcZsTjDTO1.getDjjg());
                            break;
                        }
                        if (Integer.valueOf(2).equals(bdcZsTjDTO.getZslx()) && Integer.valueOf(2).equals(bdcZsTjDTO1.getZslx())) {
                            bdcZsTjDTO1.setZmsl(bdcZsTjDTO.getZssl());
                            bdcZsTjDTO1.setZssl(null);
                        }
                    }
                }
                if (!hasAddData.contains(bdcZsTjDTO1.getDjjg())) {
                    bdcZsTjDTO1.setXh(String.valueOf((i + 1)));
                    list.add(bdcZsTjDTO1);
                }
            }
        }
        return new PageImpl(list, pageable, list.size());
    }

    @Override
    public List<BdcZsTjDTO> getZszmCountExcel(BdcZsTjQO parseObject) {
        return bdcZszmMapper.getZszmCountExcel(parseObject);
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param zszmCountJson zszmCountJson
     * @return BdcZsTjDTO BdcZsTjDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public List<BdcZsTjDTO> getZszmCountList(BdcZsTjQO zszmCountJson) {
        List<BdcZsTjDTO> list = new ArrayList<>();
        List<BdcZsTjDTO> bdcZsTjDTOList = bdcZszmMapper.getZszmCountList(zszmCountJson);
        if (CollectionUtils.isNotEmpty(bdcZsTjDTOList)) {
            List<String> hasAddData = Lists.newArrayList();
            for (int i = 0; i < bdcZsTjDTOList.size(); i++) {
                BdcZsTjDTO bdcZsTjDTO1 = bdcZsTjDTOList.get(i);
                if (hasAddData.contains(bdcZsTjDTO1.getDjjg())) {
                    continue;
                }
                for (int j = i; j < bdcZsTjDTOList.size(); j++) {
                    BdcZsTjDTO bdcZsTjDTO = bdcZsTjDTOList.get(j);
                    if (StringUtils.isNotBlank(bdcZsTjDTO.getDjjg()) && StringUtils.isNotBlank(bdcZsTjDTO1.getDjjg()) ) {
                        if (bdcZsTjDTO.getDjjg().equals(bdcZsTjDTO1.getDjjg())) {
                            if (!bdcZsTjDTO.getZslx().equals(bdcZsTjDTO1.getZslx())) {
                                bdcZsTjDTO1.setZmsl(bdcZsTjDTO.getZssl());
                                bdcZsTjDTO1.setXh(String.valueOf((i + 1)));
                                list.add(bdcZsTjDTO1);
                                hasAddData.add(bdcZsTjDTO1.getDjjg());
                                break;
                            }
                            if (Integer.valueOf(2).equals(bdcZsTjDTO.getZslx()) && Integer.valueOf(2).equals(bdcZsTjDTO1.getZslx())) {
                                bdcZsTjDTO1.setZmsl(bdcZsTjDTO.getZssl());
                                bdcZsTjDTO1.setZssl(null);
                            }
                        }
                    } else {
                        throw new AppException(500,"?????????????????????????????????????????????");
                    }

                }
                if (!hasAddData.contains(bdcZsTjDTO1.getDjjg())) {
                    bdcZsTjDTO1.setXh(String.valueOf((i + 1)));
                    list.add(bdcZsTjDTO1);
                }

            }
        }

        return list;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????18??????15????????????
     *
     * @param  qlrmc ?????????????????????
     * @return int   ??????
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public Integer getQlrZjhNullCount(List<String> qlrmc) {
        return this.bdcZszmMapper.getQlrZjhNullCount(qlrmc);
    }

    /**
     * ???????????????????????????
     *
     * @param  bdcSzgzlTjQO ?????????????????????QO
     * @return List<BdcSzgzlTjDTO> ?????????????????????list
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @Override
    public List<BdcSzgzlTjDTO> getSzgzl(BdcSzgzlTjQO bdcSzgzlTjQO) {
        //????????????????????????????????????????????????????????????????????????
        List<BdcSzgzlTjDTO> bdcSzgzlTjDTOList = bdcSzgzlTjMapper.yzhlylmxTj(bdcSzgzlTjQO);
        List<String> nameList = bdcSzgzlTjDTOList.stream().map(BdcSzgzlTjDTO::getName).distinct().filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.toList());

        if(CollectionUtils.isNotEmpty(nameList)){
            String name = StringUtils.join(nameList, ",");
            bdcSzgzlTjQO.setRymc(name);
            List<BdcSzgzlTjDTO> lylList = this.bdcSzgzlTjMapper.yzhlylTj(bdcSzgzlTjQO);
            Map<String, Integer> zslylMap = lylList.stream().collect(Collectors.toMap(BdcSzgzlTjDTO::getName,BdcSzgzlTjDTO::getZsyzhlyl));
            for(BdcSzgzlTjDTO bdcSzgzlTj:bdcSzgzlTjDTOList){
                Integer zslyl = zslylMap.get(bdcSzgzlTj.getName());
                if(null != zslyl){
                    bdcSzgzlTj.setZsyzhlyl(zslyl);
                }
            }
            Map<String, Integer> zmlylMap = lylList.stream().collect(Collectors.toMap(BdcSzgzlTjDTO::getName,BdcSzgzlTjDTO::getZmyzhlyl));
            for(BdcSzgzlTjDTO bdcSzgzlTj:bdcSzgzlTjDTOList){
                Integer zmlyl = zslylMap.get(bdcSzgzlTj.getName());
                if(null != zmlyl){
                    bdcSzgzlTj.setZmyzhlyl(zmlyl);
                }
            }
            //??????????????????
            List<BdcSzgzlTjDTO> ywblzlList = this.bdcSzgzlTjMapper.ywblzlTj(bdcSzgzlTjQO);
            Map<String, Integer> ywblzlMap = ywblzlList.stream().collect(Collectors.toMap(BdcSzgzlTjDTO::getName,BdcSzgzlTjDTO::getYwblzl));
            for(BdcSzgzlTjDTO bdcSzgzlTj:bdcSzgzlTjDTOList){
                Integer ywblzl = ywblzlMap.get(bdcSzgzlTj.getName());
                if(null != ywblzl){
                    bdcSzgzlTj.setYwblzl(ywblzl);
                }
            }
            //????????????????????????????????????
            bdcSzgzlTjDTOList = getdyywl(bdcSzgzlTjQO,bdcSzgzlTjDTOList,nameList);

            if(CollectionUtils.isNotEmpty(bdcSzgzlTjDTOList)){
                bdcSzgzlTjDTOList = getHj(bdcSzgzlTjDTOList);
            }


        }
        //??????????????????????????????
        return bdcSzgzlTjDTOList;
    }

    /**
     * ????????????????????????????????????????????????
     * @param qlrmc
     * @return  true????????? false????????????
     */
    @Override
    public Boolean checkQlczjh(String qlrmc) {
        Integer num = qlrMapper.queryQlrlbByQlrmc(qlrmc);
        if (num > 0){
            return true;
        }
        return false;
    }

    private List<BdcSzgzlTjDTO> getHj(List<BdcSzgzlTjDTO> bdcSzgzlTjDTOList){
        BdcSzgzlTjDTO bdcSzgzlTjDTO = new BdcSzgzlTjDTO();
        bdcSzgzlTjDTO.setName("??????");
        int ywblzlSum = bdcSzgzlTjDTOList.stream().mapToInt(o -> o.getYwblzl()).sum();
        bdcSzgzlTjDTO.setYwblzl(ywblzlSum);
        int zsdyywlSum = bdcSzgzlTjDTOList.stream().mapToInt(o -> o.getZsdyywl()).sum();
        bdcSzgzlTjDTO.setZsdyywl(zsdyywlSum);
        int zsyzhlylSum = bdcSzgzlTjDTOList.stream().mapToInt(o -> o.getZsyzhlyl()).sum();
        bdcSzgzlTjDTO.setZsyzhlyl(zsyzhlylSum);
        int zsyzhsylSum = bdcSzgzlTjDTOList.stream().mapToInt(o -> o.getZsyzhsyl()).sum();
        bdcSzgzlTjDTO.setZsyzhsyl(zsyzhsylSum);
        int zsyzhzflSum = bdcSzgzlTjDTOList.stream().mapToInt(o -> o.getZsyzhzfl()).sum();
        bdcSzgzlTjDTO.setZsyzhzfl(zsyzhzflSum);
        int zmdyywlSum = bdcSzgzlTjDTOList.stream().mapToInt(o -> o.getZmdyywl()).sum();
        bdcSzgzlTjDTO.setZmdyywl(zmdyywlSum);
        int zmyzhlylSum = bdcSzgzlTjDTOList.stream().mapToInt(o -> o.getZmyzhlyl()).sum();
        bdcSzgzlTjDTO.setZmyzhlyl(zmyzhlylSum);
        int zmyzhsylSum = bdcSzgzlTjDTOList.stream().mapToInt(o -> o.getZmyzhsyl()).sum();
        bdcSzgzlTjDTO.setZmyzhsyl(zmyzhsylSum);
        int zmyzhzflSum = bdcSzgzlTjDTOList.stream().mapToInt(o -> o.getZmyzhzfl()).sum();
        bdcSzgzlTjDTO.setZmyzhzfl(zmyzhzflSum);
        bdcSzgzlTjDTOList.add(bdcSzgzlTjDTO);
        return bdcSzgzlTjDTOList;
    }
    private  List<BdcSzgzlTjDTO>  getdyywl(BdcSzgzlTjQO bdcSzgzlTjQO ,List<BdcSzgzlTjDTO> bdcSzgzlTjDTOList ,List<String> nameList){
        // ????????????????????????
        double startTime = this.getStartTime(bdcSzgzlTjQO.getKssj());
        double endTime = this.getEndTime(bdcSzgzlTjQO.getJzsj());
        Set<String> logSet = redisUtils.getZsetValue(this.getKey(), startTime, endTime);
        if(CollectionUtils.isEmpty(logSet)){
            return bdcSzgzlTjDTOList;
        }
        // ????????????????????????
        Map<String, Integer> orgKeyMap = new LinkedHashMap<>();
        List<Map> list = new ArrayList<>();
        for(String json : logSet){
            if(StringUtils.isNotBlank(json)){
                Map map = JSON.parseObject(json, Map.class);
                String org = (String) map.get("NAME");
                    if(nameList.contains(org)){
                        list.add(map);
                    }
            }
        }
        for (BdcSzgzlTjDTO bdcSzgzlTjDTO : bdcSzgzlTjDTOList) {
            for(Map map : list){
                if(bdcSzgzlTjDTO.getName().equals((String) map.get("NAME"))){
                    if((Integer)map.get("ZSLX") == 1){
                        bdcSzgzlTjDTO.setZsdyywl(bdcSzgzlTjDTO.getZsdyywl() + (Integer)map.get("COUNT"));
                    }
                    if((Integer)map.get("ZSLX") ==2){
                        bdcSzgzlTjDTO.setZmdyywl(bdcSzgzlTjDTO.getZmdyywl() + (Integer)map.get("COUNT"));
                    }
                }
            }
        }
        return bdcSzgzlTjDTOList;
    }
    private static String doubleToString(Double d) {
        if (Objects.isNull(d)) {
            return "";
        }
        DecimalFormat df1 = new DecimalFormat("0.00");
        return df1.format(d);
    }


    private double getStartTime(String time){
        if(StringUtils.isBlank(time)){
            return Double.valueOf(DateUtils.getDayTimeOfZeroHMS(new Date()));
        }

        Date date = DateUtils.formatDate(time + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        if(null == date){
            return Double.valueOf(DateUtils.getDayTimeOfZeroHMS(new Date()));
        }
        return Double.valueOf(String.valueOf(date.getTime()));
    }


    private double getEndTime(String time){
        if(StringUtils.isBlank(time)){
            return Double.valueOf(DateUtils.getDayTimeOfLastHMS(new Date()));
        }

        Date date = DateUtils.formatDate(time+ " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        if(null == date){
            return Double.valueOf(DateUtils.getDayTimeOfLastHMS(new Date()));
        }
        return Double.valueOf(String.valueOf(date.getTime()));
    }

    /**
     * @description ??????????????????KEY????????? ????????????+??????+????????????????????????key????????????
     */
    private String getKey(){
        return  BDC_ZSZM_PRINT_INFO + LocalDate.now().getYear() + "_" + DateUtils.getSeason(new Date());
    }
}
