package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshFwkgDataDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.etl.BzjdDTO;
import cn.gtmap.realestate.common.core.dto.etl.XscqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.CourtYwxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmAndFbDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmLsgxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmZhxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.mapper.BdcCshFwkgSlMapper;
import cn.gtmap.realestate.init.core.mapper.BdcXmLsgxMapper;
import cn.gtmap.realestate.init.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.init.core.service.*;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/10/31
 * @description ?????????????????????
 */
@Service
@Validated
public class BdcXmServiceImpl implements BdcXmService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXmServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;
    @Autowired
    private DozerUtils dozerUtils;
    @Resource(name = "dozerSameNullTMapper")
    private DozerBeanMapper dozerMapperT;
    @Autowired
    private BdcXmLsgxMapper bdcXmLsgxMapper;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private BdcdyFeignService bdcdyFeignService;
    @Autowired
    BdcZsService bdcZsService;
    @Autowired
    BdcQllxService bdcQllxService;
    @Autowired
    private BdcCshFwkgSlMapper bdcCshFwkgSlMapper;
    @Autowired
    private BdcBdcdyService bdcBdcdyService;
    @Autowired
    private BdcSdFeignService bdcSdFeignService;
    @Autowired
    private BdcZsxmFeignService bdcZsxmFeignService;

    @Value("#{'${bdcXmLx.pt:}'.split(',')}")
    private List<String> ptlx;
    @Value("#{'${bdcXmLx.zh:}'.split(',')}")
    private List<String> zhlx;
    @Value("#{'${bdcXmLx.pl:}'.split(',')}")
    private List<String> pllx;
    @Value("#{'${bdcXmLx.plzh:}'.split(',')}")
    private List<String> plzhlx;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    @Value("#{'${lcbs.jfcqhb:}'.split(',')}")
    private List<String> jfcqhbGzldyidList;


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    @Value("#{'${lcbs.dyzxcqhb:}'.split(',')}")
    private List<String> jycqhbGzldyidList;

    /**
     * ??????????????????????????????????????????????????????????????????????????????
     */
    @Value("${rwlb.mhsl:500}")
    private Integer rwlbMhsl;


    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????
     */
    @Value("${nttscl.qlztzkfk:false}")
    private boolean qlztzkfk;

    /**
     * ?????????????????????????????????????????? Redis, ???????????????????????????????????????
     */
    private List<String> qjgldmList = Collections.emptyList();

    /**
     * ?????????????????????
     */
    @PostConstruct
    public void init() {
        // ?????????????????????????????????
        qjgldmList = bdcXmMapper.listQjgldm();
    }


    /**
     * @param bdcXmQO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????????????????? ?????? ????????????
     */
    @Override
    public List<BdcXmDO> listBdcXmByRq(BdcXmQO bdcXmQO) {
        if (bdcXmQO != null && bdcXmQO.getBjrq() != null) {
            Date bjrq = bdcXmQO.getBjrq();
            Date startDate = DateUtils.dealDate(bjrq, "00:00:00");
            Date endDate = DateUtils.dealDate(bjrq, "23:59:59");
            Map paramMap = new HashMap();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            paramMap.put("startDate", dateFormat.format(startDate));
            paramMap.put("endDate", dateFormat.format(endDate));
            List<BdcXmDO> bdcXmDOList = bdcXmMapper.listBdcdyhByBjrq(paramMap);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                return bdcXmDOList;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<BdcXmDO> listBdcXm(BdcXmDO bdcXmDO) {
        return entityMapper.selectByObj(bdcXmDO);
    }

    /**
     * ???????????????????????????????????????????????????????????????
     *
     * @param bdcXmQO ???????????????????????????
     * @return
     */
    @Override
    public List<BdcXmDO> listBdcXm(BdcXmQO bdcXmQO) {
        if (!CheckParameter.checkAnyParameter(bdcXmQO, "xmid", "qlr", "slbh", "gzlslid", "bjrq", "bdcdyh", "bdcdyhList", "jyhth", "spxtywh", "bdcqzh", "bdcdywybh", "xmidList", "slbhList", "zl", "zljq", "currentDate", "zfxzspbh")) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER) + JSONObject.toJSONString(bdcXmQO));
        }
        if(Objects.nonNull(bdcXmQO.getSply())){
            if(bdcXmQO.getSply().equals(CommonConstantUtils.SPLY_WWSQ)){
                bdcXmQO.setSply(null);
                List<Integer> splyList = new ArrayList<>(CommonConstantUtils.SPLY_WWSQ_DETAIL);
                if(CollectionUtils.isEmpty(bdcXmQO.getSplys())){
                    bdcXmQO.setSplys(splyList);
                }else {
                    bdcXmQO.getSplys().addAll(splyList);
                }
            }
        }
        return bdcXmMapper.listBdcXm(bdcXmQO);
    }

    /**
     * ?????????????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @param slbh    ???????????????ID
     * @return
     */
    @Override
    public List<BdcXmDTO> listXmBfxx(String gzlslid, String slbh) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcXmMapper.listXmBfxx(gzlslid, slbh, false);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @param slbh    ???????????????ID
     * @return
     */
    @Override
    public BdcXmDTO getXmBfxxOnlyOne(String gzlslid, String slbh) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        List<BdcXmDTO> list = bdcXmMapper.listXmBfxx(gzlslid, slbh, true);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @param bdcXmDO ?????????????????????
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description ???????????????????????????
     */
    @Override
    public int updateBdcXm(BdcXmDO bdcXmDO) {
        if (bdcXmDO == null || StringUtils.isBlank(bdcXmDO.getXmid())) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.updateByPrimaryKeySelective(bdcXmDO);
    }

    @Override
    public BdcXmDO queryBdcXmByPrimaryKey(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
    }

    /**
     * ????????????id???????????????????????????????????????
     *
     * @param xmid ???????????????id
     * @return BdcCshFwkgSlDO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public BdcCshFwkgSlDO queryCshFwkgSl(String xmid) {
        BdcCshFwkgSlDO bdcCshFwkgSlDO = null;
        if (StringUtils.isNotBlank(xmid)) {
            bdcCshFwkgSlDO = entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class, xmid);
        }
        return bdcCshFwkgSlDO;
    }

    @Override
    public int updateCshFwkgSl(BdcCshFwkgSlDO bdcCshFwkgSlDO) {
        if (StringUtils.isBlank(bdcCshFwkgSlDO.getId())) {
            throw new MissingArgumentException("????????????ID?????????");
        }
        return entityMapper.updateByPrimaryKey(bdcCshFwkgSlDO);
    }

    /**
     * @return int
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmids, sfhz]
     * @description ????????????????????????
     */
    @Override
    public int batchUpdateCshFwkgSlSfhz(List<String> xmids, String sfhz) {
        if (CollectionUtils.isEmpty(xmids) || StringUtils.isBlank(sfhz)) {
            LOGGER.error("?????????????????????????????????????????????");
            return 0;
        }
        return bdcCshFwkgSlMapper.batchUpdateCshFwkgSlSfhz(xmids, sfhz);
    }

    @Override
    public List<BdcSlCshFwkgDataDTO> queryCshFwkgSlByGzlslid(String gzlslid, String bdcdyh) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("???????????????????????????ID???");
        }

        if (StringUtils.isNotBlank(bdcdyh)) {
            // ?????????????????????????????????????????????????????????????????????????????????
            List<BdcSlCshFwkgDataDTO> dataList = new ArrayList<>(1);
            dataList.add(this.getFwkgDataByGzlslidAndBdcdyh(gzlslid, bdcdyh));
            return dataList;
        } else {
            return this.bdcCshFwkgSlMapper.queryBdcCshFwkgSlByGzlslid(gzlslid);
        }
    }

    /**
     * ?????????????????????ID???????????????????????????????????????????????????????????????
     */
    private BdcSlCshFwkgDataDTO getFwkgDataByGzlslidAndBdcdyh(String gzlslid, String bdcdyh) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("???????????????????????????");
        }
        String xmid = bdcXmDOList.get(0).getXmid();
        BdcCshFwkgSlDO bdcCshFwkgSlDO = entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class, xmid);
        BdcSlCshFwkgDataDTO fwkgDataDTO = new BdcSlCshFwkgDataDTO(bdcdyh);
        dozerMapperT.map(bdcCshFwkgSlDO, fwkgDataDTO);
        return fwkgDataDTO;
    }


    /**
     * ?????????????????????????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @return
     */
    @Override
    public List<BdcCshFwkgSlDO> listBdCshSl(@NotBlank(message = "???????????????ID????????????") String gzlslid) {
        return bdcXmMapper.listBdCshSl(gzlslid);
    }

    /**
     * ????????????id???????????????????????????????????????
     *
     * @param xmid  ???????????????id
     * @param sftlc ???????????????
     * @return List<BdcXmZhxxDTO>
     */
    @Override
    public List<BdcXmZhxxDTO> queryBdcXmZhxx(String xmid, boolean sftlc) {
        List<BdcXmZhxxDTO> list = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            if (bdcXmDO != null && (StringUtils.isNotBlank(bdcXmDO.getGzlslid()) || !sftlc)) {
                String gzlslid = bdcXmDO.getGzlslid();
                //??????????????????id
                prevBdcXmZhxx(gzlslid, xmid, list, sftlc);
                //??????????????????
                list.add(dozerMapperT.map(bdcXmDO, BdcXmZhxxDTO.class));
                //??????????????????id
                nextBdcXmZhxx(gzlslid, xmid, list, sftlc);
                //?????????????????????
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSxh(i + 1);
                }
            }
        }
        return list;
    }

    /**
     * ??????????????????????????????ID?????????????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @return
     */
    @Override
    public List<BdcXmDO> listBdcXm(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            BdcXmDO bdcXmDO = new BdcXmDO();
            bdcXmDO.setGzlslid(gzlslid);
            return listBdcXm(bdcXmDO);
        }
        return Collections.emptyList();
    }

    /**
     * ????????????ID???????????????ID
     *
     * @param xmid ???????????????ID
     * @return ?????????ID
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public String queryYxmid(String xmid) {
        String yxmid = "";
        if (StringUtils.isNotBlank(xmid)) {
            Map map = new HashMap<>();
            map.put("xmid", xmid);
            map.put("wlxm", CommonConstantUtils.SF_F_DM);
            List<BdcXmLsgxDO> xmLigxList = bdcXmLsgxMapper.queryBdcXmLsgxList(map);
            if (CollectionUtils.isNotEmpty(xmLigxList)) {
                yxmid = xmLigxList.get(0).getYxmid();
            }
        }
        return yxmid;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param gzlslid
     * @param xmid
     * @param list
     * @param sftlc   ???????????????
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    private void prevBdcXmZhxx(String gzlslid, String xmid, List<BdcXmZhxxDTO> list, boolean sftlc) {
        //??????????????????id
        List<BdcXmLsgxDO> listPrevXm = bdcXmLsgxService.queryBdcXmLsgxByXmid(xmid, "b.slsj asc");
        if (CollectionUtils.isNotEmpty(listPrevXm)) {
            for (BdcXmLsgxDO bdcXmLsgxDO : listPrevXm) {
                if (StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                    BdcXmDO prevBdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcXmLsgxDO.getYxmid());
                    if (prevBdcXm != null && (StringUtils.equals(gzlslid, prevBdcXm.getGzlslid()) || !sftlc)) {
                        list.add(0, dozerMapperT.map(prevBdcXm, BdcXmZhxxDTO.class));
                        prevBdcXmZhxx(gzlslid, prevBdcXm.getXmid(), list, sftlc);
                    }
                }
            }
        }
    }

    /**
     * ???????????????????????????????????????
     *
     * @param gzlslid
     * @param xmid
     * @param list
     * @param sftlc   ???????????????
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    private void nextBdcXmZhxx(String gzlslid, String xmid, List<BdcXmZhxxDTO> list, boolean sftlc) {
        //??????????????????id
        List<BdcXmLsgxDO> listNextXm = bdcXmLsgxService.queryBdcXmNextLsgxByXmid(xmid, "b.slsj asc", null);
        if (CollectionUtils.isNotEmpty(listNextXm)) {
            for (BdcXmLsgxDO bdcXmLsgxDO : listNextXm) {
                if (StringUtils.isNotBlank(bdcXmLsgxDO.getXmid())) {
                    BdcXmDO nextBdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcXmLsgxDO.getXmid());
                    if (nextBdcXm != null && (StringUtils.equals(gzlslid, nextBdcXm.getGzlslid()) || !sftlc)) {
                        list.add(dozerMapperT.map(nextBdcXm, BdcXmZhxxDTO.class));
                        nextBdcXmZhxx(gzlslid, nextBdcXm.getXmid(), list, sftlc);
                    }
                }
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param bdcXmList ?????????????????????
     * @return 1:??????  2?????????  3????????? 4:????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ????????????????????????
     */
    @Override
    public int bdcXmLx(@NotEmpty(message = "????????????????????????") List<BdcXmDTO> bdcXmList) {
        int lx = CommonConstantUtils.LCLX_PT;
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            String gzldyid = bdcXmList.get(0).getGzldyid();
            //????????????????????????
            if (ptlx.contains(gzldyid)) {
                return CommonConstantUtils.LCLX_PT;
            } else if (zhlx.contains(gzldyid)) {
                return CommonConstantUtils.LCLX_ZH;
            } else if (pllx.contains(gzldyid)) {
                return CommonConstantUtils.LCLX_PL;
            } else if (plzhlx.contains(gzldyid)) {
                return CommonConstantUtils.LCLX_PLZH;
            }
            boolean multiple = bdcXmList.size() > 1;
            if (multiple) {
                //?????????????????????
                lx = CommonConstantUtils.LCLX_PL;
                //????????????????????????????????????????????????
                Set<String> bdcdyhSet = new HashSet<>();
                //?????????????????????????????????????????????
                Set<String> djxlSet = new HashSet<>();
                //???????????????????????????????????????
                Set<String> qlSet = new HashSet<>();
                for (BdcXmDTO xm : bdcXmList) {
                    //????????????????????????????????????djxl???????????????
                    if (djxlSet.contains(xm.getDjxl()) && (jfcqhbGzldyidList.contains(gzldyid) || jycqhbGzldyidList.contains(gzldyid))) {
                        lx = CommonConstantUtils.LCLX_PLZH;
                        break;
                    }
                    if(xm.getQllx() != null) {
                        BdcQl bdcQl = bdcQllxService.makeSureQllx(xm.getQllx().toString());
                        if (bdcQl != null) {
                            qlSet.add(bdcQl.getClass().getName());
                        }
                    }

                    djxlSet.add(xm.getDjxl());
                    //???????????????????????????????????????????????????????????????????????????
                    if (bdcdyhSet.contains(xm.getBdcdyh()) && djxlSet.size() > 1) {
                        lx = CommonConstantUtils.LCLX_ZH;

                    }

                    bdcdyhSet.add(xm.getBdcdyh());
                    //????????????????????????????????????
                    if (CommonConstantUtils.LCLX_ZH.equals(lx) && bdcdyhSet.size() > 1) {
                        lx = CommonConstantUtils.LCLX_PLZH;
                        break;
                    }
                    //????????????????????????????????????????????????
                    if (qlSet.size() > 1) {
                        if (bdcdyhSet.size() > 1) {
                            lx = CommonConstantUtils.LCLX_PLZH;
                            break;
                        } else {
                            lx = CommonConstantUtils.LCLX_ZH;
                        }
                    }
                }
            }
        }
        return lx;
    }

    /**
     * ????????????????????????
     *
     * @param bdcXmList ?????????????????????
     * @return 1:??????  2?????????  3?????????  4:????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ????????????????????????
     */
    @Override
    public int bdcXmLxByAllXm(@NotEmpty(message = "????????????????????????") List<BdcXmDO> bdcXmList) {
        int lx = CommonConstantUtils.LCLX_PT;
        boolean multiple = bdcXmList.size() > 1;
        if (multiple) {
            //?????????????????????
            lx = CommonConstantUtils.LCLX_PL;
            //????????????????????????????????????????????????
            Set<String> bdcdyhSet = new HashSet<>();
            for (BdcXmDO xm : bdcXmList) {
                //????????????????????????????????????????????????
                if (bdcdyhSet.contains(xm.getBdcdyh())) {
                    lx = CommonConstantUtils.LCLX_ZH;
                }
                bdcdyhSet.add(xm.getBdcdyh());
                //????????????????????????????????????
                if (CommonConstantUtils.LCLX_ZH.equals(lx) && bdcdyhSet.size() > 1) {
                    lx = CommonConstantUtils.LCLX_PLZH;
                    break;
                }
            }
        }
        return lx;
    }

    /**
     * ????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @return 1:??????  2?????????  3????????? 4:????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ????????????????????????
     */
    @Override
    public int bdcXmLx(@NotBlank(message = "???????????????ID????????????") String gzlslid) {
        List<BdcXmDTO> bdcXmDOList = listXmBfxx(gzlslid, null);
        return bdcXmLx(bdcXmDOList);
    }

    /**
     * ???????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @return
     */
    @Override
    public void deleteCshFwkgSl(@NotBlank(message = "???????????????ID????????????") String gzlslid) {
        bdcXmMapper.deleteCshFwkgSl(gzlslid);
    }

    /**
     * ?????????????????????Id?????????????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @param qllx    ????????????
     * @return
     */
    @Override
    public String queryDjyy(@NotBlank(message = "???????????????ID????????????") String gzlslid, Integer qllx) {
        return bdcXmMapper.queryDjyy(gzlslid, qllx);
    }

    @Override
    public String queryZxYgYdyDjyy(String gzlslid, List<Integer> ygdjzl) {
        return bdcXmMapper.queryZxYgYdyDjyy(gzlslid, ygdjzl);

    }

    @Override
    public int updateBatchBdcXm(BdcDjxxUpdateQO bdcDjxxUpdateQO) {
        if (bdcDjxxUpdateQO == null || StringUtils.isBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("??????????????????");
        }
        String jsonStr = bdcDjxxUpdateQO.getJsonStr();
        //????????????json??????
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        // ???????????????????????????????????????
        if (jsonObject.containsKey(CommonConstantUtils.ADD_QLQTZK)) {
            jsonObject.put("bfqlqtzk", jsonObject.get(CommonConstantUtils.ADD_QLQTZK).toString());
            jsonStr = JSON.toJSONString(jsonObject);
        }
        Map map = new HashMap();
        //????????????????????????
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcXmDO.class.getName());
        if (!statement.contains("SET")) {
            return 0;
        }
        if (jsonObject.containsKey(CommonConstantUtils.ADD_QLQTZK) && StringUtils.indexOf(statement, "BFQLQTZK=") > -1) {
            statement = StringUtils.replace(statement, "BFQLQTZK=", "BFQLQTZK=BFQLQTZK||CHR(13)||");
        }
        map.put("statement", statement);
        //where ??????
        map.putAll(bdcDjxxUpdateQO.getWhereMap());
        //??????????????????
        BdcXmDO bdcXmDO = JSON.parseObject(jsonStr, BdcXmDO.class);
        map.put("record", bdcXmDO);
        return bdcXmMapper.updateBatchBdcXm(map);
    }

    /**
     * @param bz
     * @param djxl
     * @param gzlslid
     * @param xmid
     * @return ????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ????????????????????????
     */
    @Override
    public int updateBatchXmBz(@NotBlank(message = "??????????????????") String bz, String djxl, String gzlslid, String xmid) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("???????????????????????????");
        }
        Map map = new HashMap();
        map.put("bz", bz);
        map.put("djxl", djxl);
        map.put("gzlslid", gzlslid);
        map.put("xmid", xmid);
        return bdcXmMapper.updateBatchXmBz(map);
    }

    /**
     * @param json ????????????json
     * @return ???????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????json?????????????????????????????????????????????
     */
    @Override
    public int updateBdcZsXmBfqlqtzk(String json, Boolean plgx) {
        if (StringUtils.isBlank(json)) {
            throw new MissingArgumentException("updateQlqtzkAndFj??????????????????Json?????????");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        // ???????????????
        BdcXmDO bdcXmDO = new BdcXmDO();
        if (jsonObject.containsKey(Constants.XMID)) {
            bdcXmDO.setXmid(jsonObject.getString(Constants.XMID));
        }
        if (jsonObject.containsKey(Constants.BFQLQTZK)) {
            bdcXmDO.setBfqlqtzk(jsonObject.getString(Constants.BFQLQTZK));
        }
        if (jsonObject.containsKey(Constants.GZLSLID)) {
            bdcXmDO.setGzlslid(jsonObject.getString(Constants.GZLSLID));
        }
        return this.updateBdcZsXmBfqlqtzk(bdcXmDO, plgx);
    }

    /**
     * @param bdcXmDO ????????????
     * @return int ????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????????????????????????????????????????
     */
    @Override
    public int updateBdcZsXmBfqlqtzk(BdcXmDO bdcXmDO, Boolean plgx) {
        if (Objects.isNull(plgx)) {
            plgx = false;
        }
        String xmidParam = bdcXmDO.getXmid();
        if (StringUtils.isBlank(xmidParam)) {
            throw new MissingArgumentException("????????????xmid???");
        }
        Map<String, Object> whereMap = new HashMap();
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();

        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(bdcXmDO));
        bdcDjxxUpdateQO.setWhereMap(whereMap);

        List<String> zsXmidlist = new ArrayList<>();
        if (qlztzkfk && !plgx) {
            // ?????????????????????????????????
            zsXmidlist.add(bdcXmDO.getXmid());
        } else {
            // ?????????????????????????????????????????????(??????????????????????????????????????????????????????????????????????????????????????????????????????)
            zsXmidlist = this.queryZsxmList(xmidParam);
        }

        if (CollectionUtils.isEmpty(zsXmidlist)) {
            // ???????????????????????????????????????????????????????????????
            zsXmidlist = new ArrayList();
            zsXmidlist.add(xmidParam);
        }
        whereMap.put(Constants.XMIDS, zsXmidlist);
        whereMap.put(Constants.GZLSLID, bdcXmDO.getGzlslid());
        return this.updateBatchBdcXm(bdcDjxxUpdateQO);
    }

    /**
     * @param xmid
     * @return List<String> ????????????
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description ????????????????????????????????????????????????
     */
    @Override
    public List<String> queryZsxmList(String xmid) {
        List<String> list = new ArrayList<>();
        BdcCshFwkgSlDO bdcCshFwkgSlDO = this.queryCshFwkgSl(xmid);
        //??????????????????????????????????????????????????????
        BdcXmDO bdcXmDO = this.queryBdcXmByPrimaryKey(xmid);
        if (bdcCshFwkgSlDO != null && bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
            if (bdcCshFwkgSlDO.getZsxh() == null) {
                list.add(bdcCshFwkgSlDO.getId());
            } else {
                List<BdcCshFwkgSlDO> listBdCshSl = this.listBdCshSl(bdcXmDO.getGzlslid());
                if (CollectionUtils.isNotEmpty(listBdCshSl)) {
                    for (BdcCshFwkgSlDO slDOdo : listBdCshSl) {
                        //????????????????????????
                        if (bdcCshFwkgSlDO.getZsxh() != null && bdcCshFwkgSlDO.getZsxh().equals(slDOdo.getZsxh())) {
                            list.add(slDOdo.getId());
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<BdcZdFwytDO> queryZdFwytByGzlslid(String gzlslid) {
        return bdcXmMapper.queryZdFwytByGzlslid(gzlslid);

    }

    /**
     * @param gzlslid ??????ID??????
     * @return ????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ???????????????????????????????????????
     */
    @Override
    public String generateXmBzYzhNum(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return null;
        }
        StringBuilder bz = new StringBuilder();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> listBdcXm = bdcXmMapper.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(listBdcXm)) {
            return null;
        }
        Set<String> ycqzhSet = new HashSet();
        Set<String> yfczhSet = new HashSet();
        Set<String> ytdzhSet = new HashSet();
        for (BdcXmDO bdcXmDO : listBdcXm) {
            // ????????????
            if (StringUtils.isNotBlank(bdcXmDO.getYcqzh())) {
                for (String ycqzh : StringUtils.split(bdcXmDO.getYcqzh(), CommonConstantUtils.ZF_YW_DH)) {
                    if (StringUtils.isNotBlank(ycqzh)) {
                        ycqzhSet.add(ycqzh);
                    }
                }
            }
            // ????????????
            if (StringUtils.isNotBlank(bdcXmDO.getYfczh())) {
                for (String yfczh : StringUtils.split(bdcXmDO.getYfczh(), CommonConstantUtils.ZF_YW_DH)) {
                    if (StringUtils.isNotBlank(yfczh)) {
                        yfczhSet.add(yfczh);
                    }
                }
            }

            // ????????????
            if (StringUtils.isNotBlank(bdcXmDO.getYtdzh())) {
                for (String ytdzh : StringUtils.split(bdcXmDO.getYtdzh(), CommonConstantUtils.ZF_YW_DH)) {
                    if (StringUtils.isNotBlank(ytdzh)) {
                        ytdzhSet.add(ytdzh);
                    }
                }
            }
        }
        if (CollectionUtils.size(ycqzhSet) > 0) {
            bz = bz.append("???????????????")
                    .append(CommonConstantUtils.ZF_ZW_MH)
                    .append(CollectionUtils.size(ycqzhSet))
                    .append("??????");
        }
        if (CollectionUtils.size(yfczhSet) > 0) {
            bz = bz.append("????????????")
                    .append(CommonConstantUtils.ZF_ZW_MH)
                    .append(CollectionUtils.size(yfczhSet))
                    .append("??????");
        }
        if (CollectionUtils.size(ytdzhSet) > 0) {
            bz = bz.append("????????????")
                    .append(CommonConstantUtils.ZF_ZW_MH)
                    .append(CollectionUtils.size(ytdzhSet))
                    .append("??????");
        }

        return bz.toString();
    }

    @Override
    public List<BdcXmDO> listYxmByGzlslid(BdcXmLsgxDO bdcXmLsgxDO, String gzlslid, String djxl) {
        if (StringUtils.isBlank(gzlslid)) {
            return null;
        }
        Map map = new HashMap();
        map.put("gzlslid", gzlslid);
        map.put("djxl", djxl);
        if (bdcXmLsgxDO != null) {
            map.putAll(Object2MapUtils.object2MapExceptNull(bdcXmLsgxDO));
        }
        return bdcXmMapper.listYxmByGzlslid(map);


    }

    @Override
    public List<BdcXmDO> listYxmByYxmQO(BdcYxmQO bdcYxmQO) {
        if (bdcYxmQO == null || (StringUtils.isBlank(bdcYxmQO.getGzlslid()) && CollectionUtils.isEmpty(bdcYxmQO.getXmidList()))) {
            throw new AppException("???????????????????????????");
        }
        List<BdcXmDO> yBdcXmList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcYxmQO.getXmidList())) {
            List<List> partList = ListUtils.subList(bdcYxmQO.getXmidList(), 1000);
            for (List data : partList) {
                bdcYxmQO.setXmidList(data);
                List<BdcXmDO> bdcXmDOList = bdcXmMapper.listYxmByYxmQO(bdcYxmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    yBdcXmList.addAll(bdcXmDOList);
                }
            }
        } else {
            yBdcXmList = bdcXmMapper.listYxmByYxmQO(bdcYxmQO);
        }
        return yBdcXmList;

    }

    @Override
    public List<BdcXmDO> listPrevCqXm(String xmid, List<BdcXmDO> bdcXmDOList, boolean getOneXm) {
        if (bdcXmDOList == null) {
            bdcXmDOList = new ArrayList<>();
        }
        //??????????????????id
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
            bdcXmLsgxQO.setXmid(xmid);
            List<BdcXmLsgxDO> listPrevXm = bdcXmLsgxService.listBdcXmLsgx(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(listPrevXm)) {
                for (BdcXmLsgxDO bdcXmLsgxDO : listPrevXm) {
                    if (StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                        BdcXmDO prevXm = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcXmLsgxDO.getYxmid());
                        if (prevXm != null) {
                            if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, prevXm.getQllx())) {
                                //??????
                                bdcXmDOList.add(prevXm);
                            }
                            if (getOneXm && bdcXmDOList.size() == 1) {
                                break;
                            } else {
                                //??????????????????
                                listPrevCqXm(bdcXmLsgxDO.getYxmid(), bdcXmDOList, getOneXm);
                            }
                        }
                    }
                }
            }
        }
        return bdcXmDOList;

    }

    @Override
    public List<BdcXmDO> listPrevCqXmPl(List<String> xmidList, List<BdcXmDO> bdcXmDOList, boolean getOneXm) {
        if (bdcXmDOList == null) {
            bdcXmDOList = new ArrayList<>();
        }
        if (CollectionUtils.isNotEmpty(xmidList)) {
            //??????????????????
            BdcYxmQO bdcYxmQO = new BdcYxmQO();
            bdcYxmQO.setXmidList(xmidList);
            bdcYxmQO.setWlxm(CommonConstantUtils.SF_F_DM);
            List<BdcXmDO> yBdcXmList = listYxmByYxmQO(bdcYxmQO);
            if (CollectionUtils.isNotEmpty(yBdcXmList)) {
                List<String> prevXmidList = new ArrayList<>();
                for (BdcXmDO prevXm : yBdcXmList) {
                    if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, prevXm.getQllx())) {
                        //??????
                        bdcXmDOList.add(prevXm);
                        if (!getOneXm) {
                            prevXmidList.add(prevXm.getXmid());
                        }
                    } else {
                        prevXmidList.add(prevXm.getXmid());
                    }

                }
                if (CollectionUtils.isNotEmpty(prevXmidList)) {
                    //??????????????????
                    listPrevCqXmPl(prevXmidList, bdcXmDOList, getOneXm);
                }
            }
        }
        return bdcXmDOList;

    }

    @Override
    public InitResultDTO updateAllBdcdyXx(List<BdcXmDO> list, String bdcdyh, String qjgldm, Boolean gxbdcdyfwlx,Integer bhdysd) {
        InitResultDTO initResultDTO = null;
        if (CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(bdcdyh)) {
            initResultDTO = new InitResultDTO();
            //???????????????????????????bdcdywybh
            Boolean isFw = bdcBdcdyService.judgeIsFwByBdcdyh(bdcdyh);
            BdcdyResponseDTO bdcdyResponseDTO = null;
            if (isFw) {
                bdcdyResponseDTO = bdcdyFeignService.queryBdcdy(bdcdyh, null, qjgldm);

            }
            LOGGER.info("?????????????????????????????????{}??????{}", bdcdyh, JSON.toJSONString(bdcdyResponseDTO));
            for (BdcXmDO xm : list) {
                String ybdcdyh = xm.getBdcdyh();
                //??????????????????wybh??????
                if (isFw) {
                    if (bdcdyResponseDTO != null) {
                        xm.setBdcdywybh(bdcdyResponseDTO.getFwbm());
                        if (Boolean.TRUE.equals(gxbdcdyfwlx) && StringUtils.isNotBlank(bdcdyResponseDTO.getBdcdyfwlx())) {
                            xm.setBdcdyfwlx(Integer.parseInt(bdcdyResponseDTO.getBdcdyfwlx()));
                        }
                    } else {
                        //???????????????????????????????????????19?????????????????????
                        xm.setBdcdywybh(bdcdyh.length() > 19 ? bdcdyh.substring(0, 19) : bdcdyh);
                    }
                } else {
                    String bdcdyhwybh = bdcdyh.length() > 19 ? bdcdyh.substring(0, 19) : bdcdyh;
                    xm.setBdcdywybh(bdcdyhwybh);
                }
                //??????????????????????????????
                xm.setBdcdyh(bdcdyh);
                initResultDTO.getBdcXmList().add(xm);
                //?????????????????????
                List<BdcZsDO> listZs = bdcZsService.queryBdcZsByXmid(xm.getXmid());
                if (CollectionUtils.isNotEmpty(listZs)) {
                    for (BdcZsDO bdcZsDO : listZs) {
                        bdcZsDO.setBdcdyh(bdcdyh);
                    }
                }
                initResultDTO.getBdcZsList().addAll(listZs);
                //?????????????????????
                BdcQl bdcQl = bdcQllxService.queryQllxDO(xm.getXmid());
                if (bdcQl != null) {
                    BdcXmDO dyhXm = new BdcXmDO();
                    dyhXm.setBdcdyh(bdcdyh);
                    dyhXm.setBdcdywybh(xm.getBdcdywybh());
                    dozerUtils.initBeanDateConvert(dyhXm, bdcQl);
                    initResultDTO.getBdcQlList().add(bdcQl);
                }
                //???????????????,???????????????
                if(!CommonConstantUtils.SF_F_DM.equals(bhdysd)) {
                    //??????????????????
                    BdcDysdDO bdcDysdDO = new BdcDysdDO();
                    bdcDysdDO.setBdcdyh(ybdcdyh);
                    List<BdcDysdDO> bdcDysdDOList = this.bdcSdFeignService.queryBdcdySd(bdcDysdDO);
                    if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
                        for (BdcDysdDO dysd : bdcDysdDOList) {
                            dysd.setBdcdyh(bdcdyh);
                        }
                        initResultDTO.getBdcDysdDOList().addAll(bdcDysdDOList);
                    }
                }
            }
        }
        return initResultDTO;
    }

    /**
     * @param bdcXmQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????
     * @date : 2020/9/8 15:44
     */
    @Override
    public List<BdcXmAndFbDTO> listXmAndFb(BdcXmQO bdcXmQO) {
        if (!CheckParameter.checkAnyParameter(bdcXmQO, "xmid", "slbh", "gzlslid", "bdcqzh", "qlr")) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER) + JSONObject.toJSONString(bdcXmQO));
        }
        return bdcXmMapper.listXmAndFb(bdcXmQO);
    }

    /**
     * @param xm   ???????????????
     * @param zjhm ??????????????????
     * @param cqzh ??????????????????
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description ????????????????????????
     */
    @Override
    public List<XscqDTO> listXscq(String xm, String zjhm, String cqzh) {
        return bdcXmMapper.listXscq(xm, zjhm, cqzh);
    }

    /**
     * @param xm   ???????????????
     * @param zjhm ??????????????????
     * @param htbh ????????????
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description ??????????????????????????????
     */
    @Override
    public List<BzjdDTO> listBzjd(String xm, String zjhm, String htbh) {
        return bdcXmMapper.listBzjd(xm, zjhm, htbh);
    }

    /**
     * @param bdcQlrQO ???????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????????????????????????????????????????
     * @date : 2020/11/5 9:26
     */
    @Override
    public List<BdcXmDTO> listCbjyqXm(BdcQlrQO bdcQlrQO) {
        if (StringUtils.isBlank(bdcQlrQO.getZjh())) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER) + JSONObject.toJSONString(bdcQlrQO));
        }
        return bdcXmMapper.listCbjyqXm(bdcQlrQO);
    }

    /**
     * ??????xmids????????????????????????
     *
     * @param xmids ??????ID??????
     * @return ??????????????????
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    public List<BdcXmDO> listBdcXmByXmids(List<String> xmids) {
        if (CollectionUtils.isEmpty(xmids)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER) + xmids);
        }
        List<List> xmidPartList = ListUtils.subList(xmids, 500);
        List<BdcXmDO> bdcXmDOList =new ArrayList<>();
        for (List partXmids : xmidPartList) {
            List<BdcXmDO> xmList =bdcXmMapper.listBdcXmByXmids(partXmids);
            if(CollectionUtils.isNotEmpty(xmList)){
                bdcXmDOList.addAll(xmList);
            }
        }
        return bdcXmDOList;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @param sfzf
     * @return
     */
    @Override
    public List<BdcCshFwkgSlDO> listBdCshSlSfzf(String gzlslid, Integer sfzf) {
        return bdcXmMapper.listBdCshSlSfzf(gzlslid, sfzf);
    }

    /**
     * ????????????????????????????????????
     *
     * @param courtYwxxcxRequestDTO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    @Override
    public List<BdcXmDO> listCourtXscq(CourtYwxxcxRequestDTO courtYwxxcxRequestDTO) {
        return bdcXmMapper.listCourtXscq(courtYwxxcxRequestDTO);
    }

    @Override
    public List<BdcXmDO> listXscqXm(List<BdcXmDO> bdcXmDOList) {
        List<BdcXmDO> cqXmList = new ArrayList<>();
        List<String> nullbdclxDyhList = new ArrayList<>();
        Map<Integer, List<String>> bdclxDyhMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXm : bdcXmDOList) {
                if (StringUtils.isNotBlank(bdcXm.getBdcdyh())) {
                    if (bdcXm.getBdclx() != null) {
                        List<String> dyhList = new ArrayList<>();
                        if (bdclxDyhMap.containsKey(bdcXm.getBdclx())) {
                            dyhList = bdclxDyhMap.get(bdcXm.getBdclx());
                        }
                        dyhList.add(bdcXm.getBdcdyh());
                        bdclxDyhMap.put(bdcXm.getBdclx(), dyhList);

                    } else {
                        nullbdclxDyhList.add(bdcXm.getBdcdyh());
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(nullbdclxDyhList)) {
                List<List> partList = ListUtils.subList(nullbdclxDyhList, 500);
                for (List dyhList : partList) {
                    List<BdcXmDO> xmList = bdcXmMapper.listXscqXm(dyhList, null);
                    if (CollectionUtils.isNotEmpty(xmList)) {
                        cqXmList.addAll(xmList);
                    }
                }
            }
            if (MapUtils.isNotEmpty(bdclxDyhMap)) {
                for (Map.Entry<Integer, List<String>> entry : bdclxDyhMap.entrySet()) {
                    List<List> partList = ListUtils.subList(entry.getValue(), 500);
                    for (List dyhList : partList) {
                        List<BdcXmDO> xmList = bdcXmMapper.listXscqXm(dyhList, entry.getKey());
                        if (CollectionUtils.isNotEmpty(xmList)) {
                            cqXmList.addAll(xmList);
                        }
                    }

                }
            }

        }
        return cqXmList;

    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @return {List} ????????????????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<String> listQjgldm() {
        return qjgldmList;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @param xmid
     * @return
     */
    @Override
    public List<BdcCshFwkgSlDO> listBdCshSlById(String gzlslid, String xmid) {
        return bdcXmMapper.listBdCshSlById(gzlslid, xmid);
    }

    /**
     * @param bdcCshFwkgSlDO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????????????????
     * @date : 2021/9/30 14:22
     */
    @Override
    public int insertBdcCshFwkgSl(BdcCshFwkgSlDO bdcCshFwkgSlDO) {
        if (Objects.nonNull(bdcCshFwkgSlDO)) {
            return entityMapper.insertSelective(bdcCshFwkgSlDO);
        }
        return 0;
    }

    /**
     * ??????xmid??????????????????
     *
     * @param xmid ??????????????????
     * @return List<Map>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public BdcXmLsgxDTO queryBdcxmLsgxByXmid(String xmid) {
        BdcXmLsgxDTO bdcXmLsgxDTO = new BdcXmLsgxDTO();
        bdcXmLsgxDTO.setXmid(xmid);
        //????????????????????????
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> listBdcXm = bdcXmMapper.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(listBdcXm)) {
            BdcXmDO bdcXmDO = listBdcXm.get(0);
            BeanUtils.copyProperties(bdcXmDO, bdcXmLsgxDTO);
            bdcXmLsgxDTO.setBdcXmDO(bdcXmDO);
            //???????????????
            queryBdcYxm(bdcXmLsgxDTO);
            //???????????????
            queryBdcNextXm(bdcXmLsgxDTO);
        }
        return bdcXmLsgxDTO;
    }

    @Override
    public List<BdcXmDO> queryXmByZsBdcqzh(String bdcqzh) {
        if (StringUtils.isBlank(bdcqzh)) {
            return Collections.emptyList();
        }
        return bdcXmMapper.queryXmByZsBdcqzh(bdcqzh);
    }

    @Override
    public List<BdcXmDO> listOtherXmWithSameSpxtywh(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return Collections.emptyList();
        }
        return bdcXmMapper.listOtherXmWithSameSpxtywh(gzlslid);

    }

    /**
     * @param xmid
     * @return daywh
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description ??????xmid???daywh????????????BDC_XM??????????????????
     */
    @Override
    public String queryDaywh(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("????????????ID!");
        }
        return bdcXmMapper.queryDaywh(xmid);
    }

    /**
     * ????????????????????????
     *
     * @param bdcXmQO ????????????
     * @return {List} ????????????
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<BdcXmDO> listBdcXmSlbhs(BdcXmQO bdcXmQO) {
        if (!CheckParameter.checkAnyParameter(bdcXmQO, "bdcdyh", "zl")) {
            return Collections.emptyList();
        }

        if (null == bdcXmQO.getCount() || bdcXmQO.getCount() <= 0) {
            bdcXmQO.setCount(rwlbMhsl);
        }
        return bdcXmMapper.listBdcXmSlbhs(bdcXmQO);
    }

    /**
     * @param gzlslid ???????????????ID
     * @return int ?????????????????????ID????????????????????????
     * @description ??????????????????????????????????????????ID?????????????????????????????????????????????????????????????????????
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/6/21 20:28
     */
    @Override
    public int countBdcByGzlslidGroupBdcdyh(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("????????????ID!");
        }
        return bdcXmMapper.countBdcByGzlslidGroupBdcdyh(gzlslid);
    }

    @Override
    public BdcXmDO queryOneBdcXmDOBySlbh(String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new AppException(ErrorCode.MISSING_ARG, "??????????????????????????????");
        }
        return this.bdcXmMapper.queryOneBdcXmDOBySlbh(slbh);
    }

    /**
     * ???????????????
     *
     * @param bdcXmLsgxDTO
     * @return
     */
    public BdcXmLsgxDTO queryBdcYxm(BdcXmLsgxDTO bdcXmLsgxDTO) {
        if (CollectionUtils.isEmpty(bdcXmLsgxDTO.getParentXm())) {
            bdcXmLsgxDTO.setParentXm(new ArrayList<>());
        }
        //?????????????????????
        Example example = new Example(BdcXmLsgxDO.class);
        example.createCriteria().andEqualTo("xmid", bdcXmLsgxDTO.getXmid());
        List<BdcXmLsgxDO> bdcXmLsgxDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            //??????????????????????????????
            List<Object> yxmidList = bdcXmLsgxDOList
                    .stream()
                    .map(BdcXmLsgxDO::getYxmid)
                    .collect(Collectors.toList());
            Example xmExample = new Example(BdcXmDO.class);
            xmExample.createCriteria().andIn("xmid", yxmidList);
            List<BdcXmDO> listBdcXm = entityMapper.selectByExample(xmExample);
            listBdcXm.forEach(bdcXmDO -> {
                BdcXmLsgxDTO parentXm = new BdcXmLsgxDTO();
                BeanUtils.copyProperties(bdcXmDO, parentXm);
                parentXm.setBdcXmDO(bdcXmDO);
                bdcXmLsgxDTO.getParentXm().add(parentXm);
                queryBdcYxm(parentXm);
            });
        }
        return bdcXmLsgxDTO;
    }

    /**
     * ???????????????
     *
     * @param bdcXmLsgxDTO
     * @return
     */
    public BdcXmLsgxDTO queryBdcNextXm(BdcXmLsgxDTO bdcXmLsgxDTO) {
        if (CollectionUtils.isEmpty(bdcXmLsgxDTO.getSonXm())) {
            bdcXmLsgxDTO.setSonXm(new ArrayList<>());
        }
        //?????????????????????
        Example example = new Example(BdcXmLsgxDO.class);
        example.createCriteria().andEqualTo("yxmid", bdcXmLsgxDTO.getXmid());
        List<BdcXmLsgxDO> bdcXmLsgxDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            //????????????????????????????????????
            List<Object> xmidList = bdcXmLsgxDOList
                    .stream()
                    .map(BdcXmLsgxDO::getXmid)
                    .collect(Collectors.toList());
            Example xmExample = new Example(BdcXmDO.class);
            xmExample.createCriteria().andIn("xmid", xmidList);
            List<BdcXmDO> listBdcXm = entityMapper.selectByExample(xmExample);
            listBdcXm.forEach(bdcXmDO -> {
                BdcXmLsgxDTO sonXm = new BdcXmLsgxDTO();
                BeanUtils.copyProperties(bdcXmDO, sonXm);
                sonXm.setBdcXmDO(bdcXmDO);
                bdcXmLsgxDTO.getSonXm().add(sonXm);
                queryBdcNextXm(sonXm);
            });
        }
        return bdcXmLsgxDTO;
    }


    @Override
    public List<String> listYbzXmByXmid(String xmid,Integer qszt) {
        List<String> xmidList =new ArrayList<>();
        if(StringUtils.isNotBlank(xmid)) {
            List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
                String zsid = bdcZsDOList.get(0).getZsid();
                List<BdcXmDO> bdcXmDOList = bdcZsxmFeignService.listBdcXmByZsid(zsid);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    xmidList =bdcXmDOList.stream().filter(bdcXmDO -> qszt ==null ||qszt.equals(bdcXmDO.getQszt())).map(BdcXmDO::getXmid).collect(Collectors.toList());
                }
            }
        }
        return xmidList;
    }
}
