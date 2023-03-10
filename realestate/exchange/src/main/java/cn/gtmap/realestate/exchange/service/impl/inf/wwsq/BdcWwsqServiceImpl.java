package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.LogStatisticResDto;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.core.dto.jgpt.jksl.JgptBmjkDTO;
import cn.gtmap.realestate.exchange.core.dto.jgpt.jksl.JgptJkslDO;
import cn.gtmap.realestate.exchange.core.dto.jgpt.ywsl.JgptTjywResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.jgpt.ywsl.JgptYwlcDO;
import cn.gtmap.realestate.exchange.core.dto.jgpt.ywsl.JgptYwslDO;
import cn.gtmap.realestate.exchange.core.dto.jgpt.ywsl.JgptYwslDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqcfCqzxxFy.response.GetWwsqCfCqzxxResponseCqxx;
import cn.gtmap.realestate.exchange.core.dto.wwsq.querycfxx.response.GetWwsqCfxxResponseCqxx;
import cn.gtmap.realestate.exchange.core.mapper.server.WwsqMapper;
import cn.gtmap.realestate.exchange.core.qo.BdcCfqlQO;
import cn.gtmap.realestate.exchange.core.qo.BdcCfxxQO;
import cn.gtmap.realestate.exchange.core.qo.jgpt.JgpTjQO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * ????????????????????????
 */
@Service
public class BdcWwsqServiceImpl {
    private static Logger LOGGER = LoggerFactory.getLogger(BdcWwsqServiceImpl.class);

    private final static String ES_QUERY_TYPE_LIKE = "like";

    private final static String ES_QUERY_TYPE_EQUAL = "equal";

    private final static String ES_QUERY_DSF_FLAG = "gxbmbz";

    private final static String ES_QUERY_JKMCKEY = "jkmc";
    @Resource(name = "repository")
    protected Repo repo;
    @Autowired
    private GxWwSqxxServiceImpl gxWwSqxxServiceImpl;
    @Autowired
    private CommonService commonService;
    @Autowired
    private WwsqMapper wwsqMapper;
    @Autowired
    private EntityMapper entityMapper;
    @Value("${wwsq.cdgh.gzldyid:}")
    private String ghGzldyid;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private LogMessageClient logMessageClient;
    //????????????????????????
    @Value("#{'${jgpt.xsyw.sply:}'.split(',')}")
    private List<Integer> xsywSply;
    //????????????????????????
    @Value("#{'${jgpt.xxyw.sply:}'.split(',')}")
    private List<Integer> xxywSply;

    /**
     * 6.1.7??????????????????????????????????????????
     *
     * @param pageable  ????????????
     * @param bdcCfqlQO ????????????
     * @return ????????????
     */
    public Page<GetWwsqCfCqzxxResponseCqxx> getWwsqcfCqzxxFy(Pageable pageable, BdcCfqlQO bdcCfqlQO) {
        if (StringUtils.isNotBlank(bdcCfqlQO.getQlrzjh())) {
            bdcCfqlQO.setZjhs(Arrays.asList(CardNumberTransformation.zjhTransformation(bdcCfqlQO.getQlrzjh()).split(",")));
        }

        Page<GetWwsqCfCqzxxResponseCqxx> listCqxx = repo.selectPaging("listBdcCfcqxxByPage", bdcCfqlQO, pageable);
        if (null == listCqxx || CollectionUtils.isEmpty(listCqxx.getContent())) {
            return listCqxx;
        }

        for (GetWwsqCfCqzxxResponseCqxx cqxx : listCqxx.getContent()) {
            if (StringUtils.isNotBlank(cqxx.getXmid())) {
                //??????????????????????????????
                cqxx.setGltdzxx(gxWwSqxxServiceImpl.getGltdzxx(cqxx.getXmid()));
                //?????????????????????
                cqxx.setQlrlist(gxWwSqxxServiceImpl.qlrsjConvert(cqxx.getXmid(), CommonConstantUtils.QLRLB_QLR, "CfcqQlrxx"));

                if (StringUtils.isNotBlank(cqxx.getBdcdyh())) {
                    //????????????
                    String bdcdyh = cqxx.getBdcdyh();
                    List<BdcQl> listDyaq = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString());

                    if (CollectionUtils.isNotEmpty(listDyaq)) {
                        cqxx.setSfdy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        cqxx.setSfdy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
                    List<BdcQl> listCf = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    if (CollectionUtils.isNotEmpty(listCf)) {
                        cqxx.setSfcf(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        cqxx.setSfcf(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
                    List<BdcQl> listYy = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YY.toString());
                    if (CollectionUtils.isNotEmpty(listYy)) {
                        cqxx.setSfyy(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        cqxx.setSfyy(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????
                    List<BdcQl> listYg = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_YG_DM.toString());
                    if (CollectionUtils.isNotEmpty(listYg)) {
                        cqxx.setSfyg(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        cqxx.setSfyg(String.valueOf(Constants.WWSQ_SF_F));
                    }
                    //????????????  ???????????? ???????????????
                    List<BdcQl> listSd = commonService.listXsQlByBdcdyh(bdcdyh, CommonConstantUtils.QLLX_CF.toString());
                    // ????????????
                    List<BdcZssdDO> zssdDOList = commonService.listZssdByXmid(cqxx.getXmid(), CommonConstantUtils.SDZT_SD);
                    if (CollectionUtils.isNotEmpty(listSd) || CollectionUtils.isNotEmpty(zssdDOList)) {
                        cqxx.setSfsd(String.valueOf(Constants.WWSQ_SF_S));
                    } else {
                        cqxx.setSfsd(String.valueOf(Constants.WWSQ_SF_F));
                    }

                    if (StringUtil.isNotBlank(ghGzldyid)) {
                        //????????????????????????????????????
                        Example example = new Example(BdcXmDO.class);
                        example.createCriteria().andEqualTo("gzldyid", ghGzldyid).andEqualTo("bdcdyh", bdcdyh).andEqualTo("qszt", CommonConstantUtils.QSZT_VALID);
                        List<BdcXmDO> bdcXmDOList = entityMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            cqxx.setSfcdgh(String.valueOf(Constants.WWSQ_SF_S));
                        } else {
                            cqxx.setSfcdgh(String.valueOf(Constants.WWSQ_SF_F));
                        }
                    }
                }
            }
        }
        return listCqxx;
    }

    /**
     * 6.1.9???????????????????????????????????????
     *
     * @param cfxxqo ????????????
     * @return ????????????
     */
    public List<GetWwsqCfxxResponseCqxx> querycfxx(BdcCfxxQO cfxxqo) {
        if (null == cfxxqo || StringToolUtils.isAllNullOrEmpty(cfxxqo.getCfjg(),
                cfxxqo.getCfwh(), cfxxqo.getBcfr(), cfxxqo.getBcfrmh(), cfxxqo.getCqzh(), cfxxqo.getCqzhmh(),
                cfxxqo.getZlmh())) {
            throw new MissingArgumentException("???????????????????????????");
        }


        List<GetWwsqCfxxResponseCqxx> listCfxx = wwsqMapper.listCfxx(cfxxqo);
        if (null == listCfxx || CollectionUtils.isEmpty(listCfxx)) {
            return listCfxx;
        }

        for (GetWwsqCfxxResponseCqxx cfxx : listCfxx) {
            if (StringUtils.isNotBlank(cfxx.getXmid())) {
                //?????????????????????
                cfxx.setQlrlist(gxWwSqxxServiceImpl.qlrsjConvert(cfxx.getXmid(), "", "CfcqQlrxx"));
            }
        }
        LOGGER.info("{}", JSON.toJSONString(listCfxx));
        return listCfxx;
    }


    /**
     * @param ???????????????????????????
     * @return ?????????????????????
     * @Date 2022/9/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<JgptTjywResponseDTO> ywblTj(JgpTjQO jgpTjQO) {
        if (null == jgpTjQO.getQssj() || null == jgpTjQO.getJssj()) {
            return BdcCommonResponse.fail("???????????????????????????????????????");
        }
        JgptTjywResponseDTO responseDTO = new JgptTjywResponseDTO();
        List<JgptYwslDO> ywslDOList = wwsqMapper.listYwbjsl(jgpTjQO);
        if (CollectionUtils.isNotEmpty(ywslDOList)) {
            LOGGER.info("?????????????????????:{}", ywslDOList.toString());
            int xsywCount = 0;
            int xxywCount = 0;
            JgptYwslDTO xsywDTO = new JgptYwslDTO();
            JgptYwslDTO xxywDTO = new JgptYwslDTO();
            List<JgptYwlcDO> xsywslList = new ArrayList<>();
            List<JgptYwlcDO> xxywslList = new ArrayList<>();
            for (JgptYwslDO jgptYwslDO : ywslDOList) {
                //?????????????????????
                if (xsywSply.contains(jgptYwslDO.getSply())) {
                    xsywCount += jgptYwslDO.getSl();
                    JgptYwlcDO ywlcDO = new JgptYwlcDO();
                    //????????????????????????
                    BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                    bdcZdDsfzdgxDO.setZdbs("BDC_ZD_LCMC");
                    bdcZdDsfzdgxDO.setBdczdz(jgptYwslDO.getGzldyid());
                    bdcZdDsfzdgxDO.setDsfxtbs("JGPT");
                    BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                    if (result != null) {
                        ywlcDO.setLcmc(result.getDsfzdz());
                        ywlcDO.setSl(jgptYwslDO.getSl());
                        xsywslList.add(ywlcDO);
                    }

                }
                //?????????????????????
                if (xxywSply.contains(jgptYwslDO.getSply())) {
                    xxywCount += jgptYwslDO.getSl();
                    JgptYwlcDO ywlcDO = new JgptYwlcDO();
                    //????????????????????????
                    BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                    bdcZdDsfzdgxDO.setZdbs("BDC_ZD_LCMC");
                    bdcZdDsfzdgxDO.setBdczdz(jgptYwslDO.getGzldyid());
                    bdcZdDsfzdgxDO.setDsfxtbs("JGPT");
                    BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                    if (result != null) {
                        ywlcDO.setLcmc(result.getDsfzdz());
                        ywlcDO.setSl(jgptYwslDO.getSl());
                        xxywslList.add(ywlcDO);
                    }
                }
            }
            xsywDTO.setCount(xsywCount);
            xsywDTO.setDetails(xsywslList);
            xxywDTO.setCount(xxywCount);
            xxywDTO.setDetails(xxywslList);
            responseDTO.setXsyw(xsywDTO);
            responseDTO.setXxyw(xxywDTO);
        }
        return BdcCommonResponse.ok(responseDTO);
    }


    /**
     * @param ???????????????????????????
     * @return ?????????????????????
     * @Date 2022/9/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse cyywblTj(JgpTjQO jgpTjQO) {
        if (null == jgpTjQO.getQssj() || null == jgpTjQO.getJssj()) {
            return BdcCommonResponse.fail("???????????????????????????????????????");
        }
        List<JgptYwlcDO> ywlcDOS = new ArrayList<>();
        List<JgptYwslDO> ywslDOList = wwsqMapper.listCyywbjsl(jgpTjQO);
        if (CollectionUtils.isNotEmpty(ywslDOList)) {
            LOGGER.info("???????????????????????????:{}", ywslDOList.toString());
            for (JgptYwslDO jgptYwslDO : ywslDOList) {
                //????????????????????????
                BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                bdcZdDsfzdgxDO.setZdbs("BDC_ZD_LCMC");
                bdcZdDsfzdgxDO.setBdczdz(jgptYwslDO.getGzldyid());
                bdcZdDsfzdgxDO.setDsfxtbs("JGPT");
                BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                if (result != null) {
                    JgptYwlcDO ywlcDO = new JgptYwlcDO();
                    ywlcDO.setSl(jgptYwslDO.getSl());
                    ywlcDO.setLcmc(result.getDsfzdz());
                    ywlcDOS.add(ywlcDO);
                }

            }
        }
        return BdcCommonResponse.ok(ywlcDOS);
    }

    /**
     * @param ???????????????????????????
     * @return ????????????????????????
     * @Date 2022/9/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse dyjkTj(JgpTjQO jgpTjQO) {
        if (null == jgpTjQO.getQssj() || null == jgpTjQO.getJssj()) {
            return BdcCommonResponse.fail("???????????????????????????????????????");
        }
        List<QueryLogCondition> conditions = new ArrayList<>();
//        conditions.add(instCondi("gxbmbz","JKGL",ES_QUERY_TYPE_EQUAL));
        if (CollectionUtils.isNotEmpty(jgpTjQO.getBmmc()) && StringUtils.isNotBlank(jgpTjQO.getBmmc().get(0))) {
            conditions.add(instCondi("gxbmbz", jgpTjQO.getBmmc().get(0), ES_QUERY_TYPE_EQUAL));
        }
        Long begin = getQueryTime(jgpTjQO.getQssj());
        // ????????????
        Long end = getQueryTime(jgpTjQO.getJssj());

        String key = "gxbmbz,bdcdz,jkmc";
        LOGGER.info("countZj?????????????????? ??????????????????????????????????????????{},?????????conditions:{}", DateUtils.formateTimeYmdhms(new Date()), JSONObject.toJSONString(conditions));
        List<LogStatisticResDto> logStatisticResDtos = logMessageClient.multilevelStatistic(key, Constants.EXCHANGE_ES_RZLX, begin, end, conditions);
        LOGGER.info("?????????????????????{}", JSONObject.toJSONString(logStatisticResDtos));
        List<JgptBmjkDTO> bmjkDTOS = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(logStatisticResDtos)) {
            int dycs = 0;
            for (LogStatisticResDto logStatisticResDto : logStatisticResDtos) {
                List<JgptJkslDO> details = new ArrayList<>();
                //?????????????????????????????????????????????bdc????????????????????????????????????????????????
                if (CollectionUtils.isNotEmpty(logStatisticResDto.getChildren())) {
                    if (StringUtils.isNotBlank(logStatisticResDto.getValue())) {
                        //?????????????????????????????????
                        JgptBmjkDTO jgptBmjkDTO = new JgptBmjkDTO();
                        jgptBmjkDTO.setBmmc(logStatisticResDto.getValue());
                        for (LogStatisticResDto jkmc : logStatisticResDto.getChildren()) {
                            dycs += jkmc.getCount().intValue();
                            JgptJkslDO jkslDO = new JgptJkslDO();
                            jkslDO.setJkdz(jkmc.getValue());
                            jkslDO.setJkdycs(jkmc.getCount().intValue());
                            LogStatisticResDto jkmcChildren = jkmc.getChildren().get(0);
                            jkslDO.setJkmc(jkmcChildren.getValue());
                            details.add(jkslDO);
                        }
                        jgptBmjkDTO.setDetails(details);
                        jgptBmjkDTO.setDycs(dycs);
                        bmjkDTOS.add(jgptBmjkDTO);
                    }
                }

            }
        }

        return BdcCommonResponse.ok(bmjkDTOS);
    }

    private List<QueryLogCondition> mapToCondiList(Map paramMap) {
        List<QueryLogCondition> conditions = new ArrayList<>();
        String gxbmbz = MapUtils.getString(paramMap, "gxbmbz");
        if (StringUtils.isNotBlank(gxbmbz)) {
            conditions.add(instCondi("gxbmbz", gxbmbz, ES_QUERY_TYPE_EQUAL));
        }
        String jkmc = MapUtils.getString(paramMap, "jkmc");
        if (StringUtils.isNotBlank(jkmc)) {
            conditions.add(instCondi("jkmc", jkmc, ES_QUERY_TYPE_EQUAL));
        }
        String jkmcmh = MapUtils.getString(paramMap, "jkmcmh");
        if (StringUtils.isNotBlank(jkmcmh)) {
            conditions.add(instCondi("jkmc", jkmcmh, ES_QUERY_TYPE_LIKE));
        }
        String slbh = MapUtils.getString(paramMap, "slbh");
        if (StringUtils.isNotEmpty(slbh)) {
            conditions.add(instCondi("slbh", slbh, ES_QUERY_TYPE_EQUAL));
        }
        String bdcdz = MapUtils.getString(paramMap, "bdcdz");
        if (StringUtils.isNotEmpty(bdcdz)) {
            conditions.add(instCondi("bdcdz", bdcdz, ES_QUERY_TYPE_LIKE));
        }
        String dsfdz = MapUtils.getString(paramMap, "dsfdz");
        if (StringUtils.isNotEmpty(dsfdz)) {
            conditions.add(instCondi("dsfdz", dsfdz, ES_QUERY_TYPE_LIKE));
        }
        String bdcjs = MapUtils.getString(paramMap, "bdcjs");
        if (StringUtils.isNotEmpty(bdcjs)) {
            if (StringUtils.equals("qqf", bdcjs)) {
                conditions.add(instCondi("qqf", "BDC", ES_QUERY_TYPE_LIKE));
            }
            if (StringUtils.equals("xyf", bdcjs)) {
                conditions.add(instCondi("xyf", "BDC", ES_QUERY_TYPE_LIKE));
            }
        }
        String czr = MapUtils.getString(paramMap, "czr");
        if (StringUtils.isNotEmpty(czr)) {
            conditions.add(instCondi("czr", czr, ES_QUERY_TYPE_EQUAL));
        }
        String alias = MapUtils.getString(paramMap, "alias");
        if (StringUtils.isNotEmpty(alias)) {
            conditions.add(instCondi("alias", alias, ES_QUERY_TYPE_EQUAL));
        }
        return conditions;
    }

    /**
     * @param
     * @return cn.gtmap.gtc.sso.domain.dto.QueryLogCondition
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????? ES ????????????
     */
    private QueryLogCondition instCondi(String key, String value, String type) {
        QueryLogCondition condition = new QueryLogCondition();
        condition.setType(type);
        condition.setKey(key);
        condition.setValue(value);
        return condition;
    }

    /**
     * @return java.lang.Long
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????????????????
     */
    private Long getQueryTime(String dateStr) {
        Long temp = null;
        if (StringUtils.isNotBlank(dateStr)) {
            Date beginDate = DateUtils.formatDate(dateStr);
            if (beginDate != null) {
                temp = beginDate.getTime();
            }
        }
        return temp;
    }
}
