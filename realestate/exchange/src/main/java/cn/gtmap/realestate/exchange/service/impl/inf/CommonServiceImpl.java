package cn.gtmap.realestate.exchange.service.impl.inf;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.PageDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZssdQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSqrFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcCzrzRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.exchange.core.dto.common.*;
import cn.gtmap.realestate.exchange.core.dto.wwsq.token.WwsqTokenDTO;
import cn.gtmap.realestate.exchange.core.dto.ycsl.queryxzqlbybdcdyh.response.YcslQueryXzqlByBdcdyhSdxx;
import cn.gtmap.realestate.exchange.core.ex.ValidException;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.TokenUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.enums.SjptCxqlEnum;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-08
 * @description
 */
@Service
@Validated
public class CommonServiceImpl implements CommonService {

    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    private BdcDjbxxFeignService bdcDjbxxFeignService;
    @Autowired
    private ProcessTaskClient processTaskClient;
    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;
    @Autowired
    private BdcSdFeignService bdcSdFeignService;
    @Autowired
    private BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    private BdcZsInitFeignService bdcZsInitFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private BdcSlZdFeignService bdcSlZdFeignService;

    @Autowired
    private BdcdyFeignService bdcdyFeignService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcZdFeignService zdService;

    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;

    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    private BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;

    @Autowired
    private BdcCzrzRestService bdcCzrzRestService;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Autowired
    private BdcXmfbFeignService bdcXmfbFeignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonServiceImpl.class);


    /**
     * @param ysfwbm
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public BdcdyResponseDTO queryHsBdcdyByYsfwbm(String ysfwbm) {
        if (StringUtils.isNotBlank(ysfwbm)) {
            if (ysfwbm.endsWith(".0")) {
                ysfwbm = ysfwbm.replace(".0", "");
            }
            return bdcdyFeignService.queryHsBdcdyByYsfwbm(ysfwbm, "");
        }
        return null;
    }

    /**
     * 根据XMID 权利人类别 查询权利人列表
     *
     * @param xmid
     * @param qlrlb
     * @return
     */
    @Override
    public List<BdcQlrDO> listBdcQlrByXmid(String xmid, String qlrlb) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcQlrQO qlrQO = new BdcQlrQO();
            qlrQO.setQlrlb(qlrlb);
            qlrQO.setXmid(xmid);
            return bdcQlrFeignService.listBdcQlr(qlrQO);
        }
        return Collections.emptyList();
    }

    /**
     * 根据权利人名称、证件号、权利人类别 查询权利人列表
     *
     * @param qlrmc
     * @param qlrzjh
     * @param qlrlb
     * @return
     */
    @Override
    public List<BdcQlrDO> listBdcQlrByQlrAndZjh(String qlrmc, String qlrzjh, String qlrlb) {
        if (StringUtils.isNotBlank(qlrmc)) {
            BdcQlrQO qlrQO = new BdcQlrQO();
            qlrQO.setQlrlb(qlrlb);
            qlrQO.setQlrmc(qlrmc);
            qlrQO.setZjh(qlrzjh);
            List<BdcQlrDO> qlrList = bdcQlrFeignService.listBdcQlr(qlrQO);
            return qlrList;
        }
        return Collections.emptyList();
    }

    /**
     * @param qlrmc
     * @param qlrzjhs
     * @param qlrlb
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据权利人名称、证件号(多个)、权利人类别 查询权利人列表
     */
    @Override
    public List<BdcQlrDO> listBdcQlrByQlrAndZjhArr(String qlrmc, List qlrzjhs, String qlrlb) {
        if (StringUtils.isBlank(qlrmc) && CollectionUtils.isEmpty(qlrzjhs)) {
            return Collections.EMPTY_LIST;
        }
        Example example = new Example(BdcQlrDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("qlrmc", qlrmc);
        if (CollectionUtils.isNotEmpty(qlrzjhs)) {
            criteria.andIn("zjh", qlrzjhs);
        }
        if (StringUtils.isNotBlank(qlrlb)) {
            criteria.andEqualTo("qlrlb", qlrlb);
        }
        return entityMapper.selectByExample(example);
    }

    /**
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.BdcFdcqDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID查询 房地产权 现势权利
     */
    @Override
    public BdcFdcqDO getFdcqByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcQl ql = bdcQllxFeignService.queryQlxx(xmid);
            if (ql != null && ql instanceof BdcFdcqDO) {
                BdcFdcqDO fdcqDO = (BdcFdcqDO) ql;
                if (Constants.QSZT_XS == fdcqDO.getQszt()) {
                    return fdcqDO;
                }
            }
        }
        return null;
    }

    /**
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.BdcFdcqDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID查询 房地产权 所有权属状态
     */
    @Override
    public BdcFdcqDO getFdcqByXmidWithNoQszt(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcQl ql = bdcQllxFeignService.queryQlxx(xmid);
            if (ql != null && ql instanceof BdcFdcqDO) {
                BdcFdcqDO fdcqDO = (BdcFdcqDO) ql;
                return fdcqDO;
            }
        }
        return null;
    }

    /**
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.BdcXmDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID 查询 项目
     */
    @Override
    public BdcXmDO getBdcXmByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                return bdcXmDOList.get(0);
            }
        }
        return null;
    }

    /**
     * @param slbh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据SLBH 查询项目列表
     */
    @Override
    public List<BdcXmDO> listBdcXmBySlbh(String slbh) {
        List<BdcXmDO> bdcXmList = new ArrayList<>();
        if (StringUtils.isNotBlank(slbh)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setSlbh(slbh);
            bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        return bdcXmList;
    }

    /**
     * @param spxtywh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据审批系统业务号查询项目列表
     */
    @Override
    public List<BdcXmDO> listBdcXmBySpxtywh(String spxtywh) {
        List<BdcXmDO> bdcXmList = new ArrayList<>();
        if (StringUtils.isNotBlank(spxtywh)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setSpxtywh(spxtywh);
            bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        return bdcXmList;
    }

    /**
     * @param bdcdyh
     * @param qllx
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQl>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询现势状态的权利
     */
    @Override
    public List<BdcQl> listXsQlByBdcdyh(String bdcdyh, String qllx) {
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.isNotBlank(qllx)) {
            List<Integer> qsztList = new ArrayList<>();
            qsztList.add(CommonConstantUtils.QSZT_VALID);
            return bdcDjbxxFeignService.listBdcQlxx(bdcdyh, qllx, qsztList);
        }
        return Collections.emptyList();
    }

    /**
     * @param bdcdyh
     * @param qllx
     * @param qsztList
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQl>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询现势状态的权利
     */
    @Override
    public List<BdcQl> listQlByBdcdyh(String bdcdyh, String qllx, List<Integer> qsztList) {
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.isNotBlank(qllx)) {
            return bdcDjbxxFeignService.listBdcQlxx(bdcdyh, qllx, qsztList);
        }
        return Collections.emptyList();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 批量单元号
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQl>
     * @description 根据批量BDCDYH查询现势权利
     */
    @Override
    public <T> List<T> listQlByBdcdyhs(List<String> bdcdyhList, T qlxx) {
        if(CollectionUtils.isEmpty(bdcdyhList)) {
            return Collections.emptyList();
        }

        List<List> subLists = ListUtils.subList(bdcdyhList, 500);
        List<T> qlList = new ArrayList<>();

        for(List subList : subLists) {
            Example example = new Example(qlxx.getClass());
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("qszt", CommonConstantUtils.QSZT_VALID);
            criteria.andIn("bdcdyh", subList);
            List subQlList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(subQlList)) {
                qlList.addAll(subQlList);
            }
        }

        return qlList;
    }

    /**
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.FdcqQlWithXmQlrDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询 FDCQ 和项目
     */
    @Override
    public List<FdcqQlWithXmQlrDTO> listFdcqByBdcdyh(QueryQlRequestDTO requestDTO) {
        String[] qlarr = SjptCxqlEnum.FDCQ.getQllxArr();
        List<FdcqQlWithXmQlrDTO> result = new ArrayList<>();
        for (String qllx : qlarr) {
            result.addAll(listQlWithXmAndQlrByBdcdyhOrXmid(FdcqQlWithXmQlrDTO.class, qllx, requestDTO));
        }
        return result;
    }

    /**
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.DyQlWithXmQlrDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询抵押权 和项目
     */
    @Override
    public List<DyQlWithXmQlrDTO> listDyaqByBdcdyh(QueryQlRequestDTO requestDTO) {
        return listQlWithXmAndQlrByBdcdyhOrXmid(DyQlWithXmQlrDTO.class, CommonConstantUtils.QLLX_DYAQ_DM.toString(), requestDTO);
    }

    /**
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.CfQlWithXmQlrDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public List<CfQlWithXmQlrDTO> listCfByBdcdyh(QueryQlRequestDTO requestDTO) {
        return listQlWithXmAndQlrByBdcdyhOrXmid(CfQlWithXmQlrDTO.class, Constants.CF_QLLX_DM, requestDTO);
    }

    /**
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.YyQlWithXmQlrDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public List<YyQlWithXmQlrDTO> listYyByBdcdyh(QueryQlRequestDTO requestDTO) {
        return listQlWithXmAndQlrByBdcdyhOrXmid(YyQlWithXmQlrDTO.class, Constants.YY_QLLX_DM, requestDTO);
    }

    /**
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.YgQlWithXmQlrDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据xmid查询预告权利
     */
    @Override
    public List<YgQlWithXmQlrDTO> listYgByBdcdyh(QueryQlRequestDTO requestDTO) {
        return listQlWithXmAndQlrByBdcdyhOrXmid(YgQlWithXmQlrDTO.class, CommonConstantUtils.QLLX_YG_DM + "", requestDTO);
    }

    /**
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.YgQlWithXmQlrDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据xmid查询预告权利
     */
    @Override
    public List<JzqQlWithXmQlrDTO> listJzqByBdcdyh(QueryQlRequestDTO requestDTO) {
        return listQlWithXmAndQlrByBdcdyhOrXmid(JzqQlWithXmQlrDTO.class, CommonConstantUtils.QLLX_JZQ + "", requestDTO);
    }

    /**
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.YgQlWithXmQlrDTO>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据xmid查询预抵押权利
     */
    @Override
    public List<YgQlWithXmQlrDTO> listYgDyaqByBdcdyh(QueryQlRequestDTO requestDTO) {
        List<YgQlWithXmQlrDTO> resultList = new ArrayList<>();
        List<YgQlWithXmQlrDTO> list = listQlWithXmAndQlrByBdcdyhOrXmid(YgQlWithXmQlrDTO.class, CommonConstantUtils.QLLX_YG_DM + "", requestDTO);
        if (CollectionUtils.isNotEmpty(list)) {
            for (YgQlWithXmQlrDTO ygQlWithXmQlrDTO : list) {
                if (ygQlWithXmQlrDTO.getBdcql() != null &&
                        ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, ygQlWithXmQlrDTO.getBdcql().getYgdjzl())) {
                    resultList.add(ygQlWithXmQlrDTO);
                }
            }
        }
        return resultList;
    }


    /**
     * @param bdcXmDO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XM实体 工作流节点名称
     */
    @Override
    public String queryJdmcByBdcXm(BdcXmDO bdcXmDO) {
        String jdzt = "";
        if (bdcXmDO != null) {
            if (CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDO.getAjzt())) {
                jdzt = "已办结";
            } else if (bdcXmDO.getAjzt() == null && StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                bdcXmDO = getBdcXmByXmid(bdcXmDO.getXmid());
            }
            if (StringUtils.isBlank(jdzt)
                    && StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
                try {
                    List<TaskData> list = processTaskClient.processLastTasks(bdcXmDO.getGzlslid());
                    if (CollectionUtils.isNotEmpty(list)) {
                        jdzt = list.get(0).getTaskName();
                    }
                } catch (Exception e) {
                    LOGGER.error("没有找到任务列表 gzlslid:{}", bdcXmDO.getGzlslid());
                }
            }
        }
        return jdzt;
    }

    /**
     * @return java.lang.String
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description
     */
    @Override
    public JSONObject queryProcessStatusInfo(JSONObject jsonObject) {
        JSONObject repJsonObject = new JSONObject(4);
        String slbh = "";
        if (jsonObject != null && jsonObject.containsKey("data") && jsonObject.getJSONObject("data").containsKey("slbh")) {
            slbh = jsonObject.getJSONObject("data").getString("slbh");
        }
        if (StringUtils.isNotBlank(slbh)) {
            String jdzt = "";
            String deleteReason = "";
            Example xmExample = new Example(BdcXmDO.class);
            xmExample.createCriteria().andEqualTo("spxtywh", slbh);
            List<BdcXmDO> bdcXmList = entityMapper.selectByExample(xmExample);
            if (CollectionUtils.isNotEmpty(bdcXmList) && bdcXmList.get(0) != null) {
                if (CommonConstantUtils.AJZT_YB_DM.equals(bdcXmList.get(0).getAjzt())) {
                    jdzt = "已办结";
                }
                if (StringUtils.isBlank(jdzt)
                        && StringUtils.isNotBlank(bdcXmList.get(0).getGzlslid())) {
                    try {
                        List<TaskData> list = processTaskClient.processLastTasks(bdcXmList.get(0).getGzlslid());
                        if (CollectionUtils.isNotEmpty(list)) {
                            jdzt = list.get(0).getTaskName();
                        }
                    } catch (Exception e) {
                        LOGGER.error("没有找到任务列表 gzlslid:{}", bdcXmList.get(0).getGzlslid());
                    }
                }
            } else {
                //删除处理
                BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                bdcCzrzDO.setSpxtywh(slbh);
                bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_SC.key());
                List<BdcCzrzDO> czrz = bdcCzrzRestService.listBdcCzrz(bdcCzrzDO);
                jdzt = "已删除";
                if (CollectionUtils.isEmpty(czrz) ) {
                    throw new AppException("未查询到该业务");
                }
                // 获取时间最大的一条数据
                BdcCzrzDO deleteBdcCzrzDO = czrz.stream().max(Comparator.comparing(BdcCzrzDO::getCzsj)).get();
                if(deleteBdcCzrzDO == null || StringUtils.isBlank(deleteBdcCzrzDO.getCzyy())){
                    throw new AppException("未查询到该业务");
                }
                deleteReason = deleteBdcCzrzDO.getCzyy().substring(deleteBdcCzrzDO.getCzyy().lastIndexOf(":") + 1, deleteBdcCzrzDO.getCzyy().length());
            }
            repJsonObject.put("bjzt", jdzt);
            repJsonObject.put("slbh", slbh);
            repJsonObject.put("scyy", deleteReason);
            return repJsonObject;
        } else {
            throw new RuntimeException("缺少slbh入参");
        }
    }

    /**
     * @param bdcXmDO
     * @return java.lang.String
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据XM实体 办件状态
     */
    @Override
    public String queryBjztByBdcXm(BdcXmDO bdcXmDO) {
        String jdzt = "";
        if (bdcXmDO != null) {
            if (CommonConstantUtils.AJZT_YB_DM.equals(bdcXmDO.getAjzt())) {
                jdzt = "已办结";
            } else if (StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
                try {
                    List<TaskData> list = processTaskClient.processLastTasks(bdcXmDO.getGzlslid());
                    if (CollectionUtils.isNotEmpty(list)) {
                        if (StringUtils.equals(list.get(0).getTaskName(), "发证")) {
                            jdzt = "可领证";
                        } else {
                            jdzt = "已受理";
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("没有找到任务列表 gzlslid:{}", bdcXmDO.getGzlslid());
                }
            }
        }
        return jdzt;
    }

    /**
     * @param beanName      token类型
     * @param requestObject token的bean名
     * @return java.lang.String
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取token
     */
    @Override
    public String getToken(String beanName, Object requestObject) {
        if (StringUtils.isBlank(beanName)) {
            throw new MissingArgumentException("获取token接口参数错误！");
        }
        String token = TokenUtil.getToken(beanName);
        if (StringUtils.isNotBlank(token)) {
            return token;
        } else {
            Object result = exchangeBeanRequestService.request(beanName, requestObject);
            if (result != null) {
                token = result.toString();
                TokenUtil.addToken(beanName, token);
            }
        }
        if (StringUtils.isBlank(token)) {
            throw new AppException("获取token失败！");
        }
        return token;
    }

    /**
     * @param beanName      token类型
     * @param requestObject token的bean名
     * @return java.lang.String
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取token
     */
    @Override
    public String getCurrentToken(String beanName, Object requestObject) {
        if (StringUtils.isBlank(beanName)) {
            throw new MissingArgumentException("获取token接口参数错误！");
        }
        String token = "";
        Object result = exchangeBeanRequestService.request(beanName, requestObject);
        if (result != null) {
            token = result.toString();
        }
        return token;
    }

    /**
     * @param pageDTO
     * @return Pageable
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public Pageable getPageable(PageDTO pageDTO) {
        if (pageDTO == null || !CheckParameter.checkAnyParameter(pageDTO)) {
            throw new ValidException("分页信息不能为空");
        }
        Sort sort = null;
        if (StringUtils.isNotBlank(pageDTO.getSort())) {
            String sortStr = pageDTO.getSort();
            String[] arr = sortStr.split(",");
            if (sortStr.endsWith(Sort.Direction.ASC.name().toLowerCase())
                    || sortStr.endsWith(Sort.Direction.DESC.name().toLowerCase())) {
                String dir = arr[arr.length - 1];
                String propertys = sortStr.replace("," + dir, "");
                sort = new Sort(Sort.Direction.fromString(dir), propertys.split(","));
            } else {
                sort = new Sort(arr);
            }
        }
        int page = StringUtils.isNotBlank(pageDTO.getPage()) ? Integer.parseInt(pageDTO.getPage()) : 1;
        int size = StringUtils.isNotBlank(pageDTO.getSize()) ? Integer.parseInt(pageDTO.getSize()) : 1;
        PageRequest pageRequest = new PageRequest(page, size, sort);
        return pageRequest;
    }

    /**
     * 获取不动产单元锁定数据
     *
     * @param bdcdyh
     * @return java.util.List<BdcDysdDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcDysdDO> listDysdByBdcdyh(@NotBlank(message = "不动产单元号不能为空") String bdcdyh, Integer sdzt) {
        return bdcBdcdyFeignService.queryBdcDysd(bdcdyh, sdzt);
    }

    /**
     * 获取不动产单元锁定数据(不包含解锁数据；包含单元锁定、证书锁定数据)
     *
     * @param bdcdyh 不动产单元号
     * @return java.util.List 锁定数据
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<YcslQueryXzqlByBdcdyhSdxx> listSdxxByBdcdyh(@NotBlank(message = "不动产单元号不能为空") String bdcdyh) {
        List<YcslQueryXzqlByBdcdyhSdxx> result = new ArrayList<YcslQueryXzqlByBdcdyhSdxx>();

        // 单元锁定
        List<BdcDysdDO> dysdList = bdcBdcdyFeignService.queryBdcDysd(bdcdyh, CommonConstantUtils.SDZT_SD);
        if (CollectionUtils.isNotEmpty(dysdList)) {
            for (BdcDysdDO bdcDysdDO : dysdList) {
                YcslQueryXzqlByBdcdyhSdxx sdxx = new YcslQueryXzqlByBdcdyhSdxx();
                sdxx.setBdcdyh(bdcDysdDO.getBdcdyh());
                sdxx.setBdcdybh(bdcDysdDO.getBdcdywybh());
                sdxx.setSdr(bdcDysdDO.getSdr());
                sdxx.setSdsj(null != bdcDysdDO.getSdsj() ? DateFormatUtils.format(bdcDysdDO.getSdsj(), "yyyy-MM-dd") : "");
                sdxx.setSdyy(bdcDysdDO.getSdyy());

                result.add(sdxx);
            }
        }

        // 证书锁定
        BdcZssdQO bdcZssdQO = new BdcZssdQO();
        bdcZssdQO.setBdcdyh(bdcdyh);
        bdcZssdQO.setSdzt(CommonConstantUtils.SDZT_SD);
        List<BdcZssdDO> zssdList = bdcZsFeignService.listBdcZssdxx(bdcZssdQO);
        if (CollectionUtils.isNotEmpty(zssdList)) {
            for (BdcZssdDO zssdDO : zssdList) {
                YcslQueryXzqlByBdcdyhSdxx sdxx = new YcslQueryXzqlByBdcdyhSdxx();
                sdxx.setBdcdyh(bdcdyh);
                sdxx.setCqzh(zssdDO.getCqzh());
                sdxx.setSdr(zssdDO.getSdr());
                sdxx.setSdsj(DateFormatUtils.format(zssdDO.getSdsj(), "yyyy-MM-dd"));
                sdxx.setSdyy(zssdDO.getSdyy());

                result.add(sdxx);
            }
        }

        return result;
    }

    /**
     * 获取证书锁定数据
     *
     * @param xmid
     * @return java.util.List<BdcZssdDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<BdcZssdDO> listZssdByXmid(@NotBlank(message = "项目ID不能为空") String xmid, Integer sdzt) {
        List<BdcZssdDO> listZssd = new ArrayList<>();
        List<BdcZsDO> listzs = bdcZsInitFeignService.queryBdcqz(xmid);
        if (CollectionUtils.isNotEmpty(listzs)) {
            BdcZssdDO bdcZssdDO = new BdcZssdDO();
            bdcZssdDO.setSdzt(sdzt);
            for (BdcZsDO bdcZsDO : listzs) {
                if (bdcZsDO != null && StringUtils.isNotBlank(bdcZsDO.getBdcqzh()) && StringUtils.isNotBlank(bdcZsDO.getZsid())) {
                    bdcZssdDO.setCqzh(bdcZsDO.getBdcqzh());
                    bdcZssdDO.setZsid(bdcZsDO.getZsid());
                    List<BdcZssdDO> list = bdcSdFeignService.queryBdczsSd(bdcZssdDO);
                    if (CollectionUtils.isNotEmpty(list)) {
                        listZssd.addAll(list);
                    }
                }
            }
        }
        return listZssd;
    }

    /**
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据工作流实例ID 查询项目列表
     */
    @Override
    public List<BdcXmDO> listBdcXmByGzlslid(String gzlslid) {
        if (StringUtil.isNotEmpty(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            return bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        return Collections.emptyList();
    }

    /**
     * @param gzlslid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据工作流实例ID 查询项目列表供南通通知交易系统调用，过滤了重复的qlxx
     */
    @Override
    public List<BdcXmDO> listBdcXmByGzlslidForNtTofcjy(String gzlslid) {
        if (StringUtil.isNotEmpty(gzlslid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            Map<String, String> tempFilterMap = new HashMap<>(2);
            ArrayList<BdcXmDO> resultList = bdcXmDOList.stream().collect(ArrayList::new,
                    (list, item) -> {
                        if (!tempFilterMap.containsKey(item.getQlr()) || !tempFilterMap.get(item.getQlr()).contains(item.getBdcqzh())) {
                            list.add(item);
                            if (tempFilterMap.containsKey(item.getQlr())) {
                                tempFilterMap.put(item.getQlr(), tempFilterMap.get(item.getQlr()) + "," + item.getBdcqzh());
                            } else {
                                tempFilterMap.put(item.getQlr(), item.getBdcqzh());
                            }
                        }
                    }, ArrayList::addAll);
            return resultList;
        }
        return Collections.emptyList();
    }

    /**
     * @param zdkey
     * @param dm
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 字典代码获取字典名称
     */
    @Override
    public String getBdcZdMcFromDm(String zdkey, String dm) {
        if(StringUtils.isEmpty(dm)){
            return "";
        }
        List<Map> zdMapList = bdcZdFeignService.queryBdcZd(zdkey);
        String value = dm;
        for (Map map : zdMapList) {
            if (StringUtils.equals(dm, MapUtils.getString(map, "DM"))) {
                value = MapUtils.getString(map, "MC");
                break;
            }
        }
        return value;
    }

    @Override
    public String getBdcSlZdMcFromDm(String zdkey, String dm) {
        List<Map> zdMapList = bdcSlZdFeignService.queryBdcSlzd(zdkey);
        String value = null;
        for (Map map : zdMapList) {
            if (StringUtils.equals(dm, MapUtils.getString(map, "DM"))) {
                value = MapUtils.getString(map, "MC");
                break;
            }
        }
        return value;
    }

    /**
     * @param xmid
     * @param qlrlb
     * @return cn.gtmap.realestate.common.core.domain.BdcQlrDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID查询权利人信息
     */
    @Override
    public BdcQlrDO wmcatQlrxx(String xmid, String qlrlb) {
        List<BdcQlrDO> bdcQlrDOList = listBdcQlrByXmid(xmid, qlrlb);
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            if (bdcQlrDOList.size() == 1) {
                // 只有一个的时候 直接返回
                return bdcQlrDOList.get(0);
            } else {
                // 如果有多个 拼接 必要信息返回
                BdcQlrDO bdcQlrDO = new BdcQlrDO();
                bdcQlrDO.setQlrmc("");
                bdcQlrDO.setZjh("");
                bdcQlrDO.setDh("");
                bdcQlrDO.setBdcqzh("");
                String split = ",";
                for (int i = 0; i < bdcQlrDOList.size(); i++) {
                    if (i == 0) {
                        split = "";
                    } else {
                        split = ",";
                    }
                    bdcQlrDO.setQlrmc(bdcQlrDO.getQlrmc() + split + bdcQlrDOList.get(i).getQlrmc());
                    bdcQlrDO.setZjh(bdcQlrDO.getZjh() + split + bdcQlrDOList.get(i).getZjh());
                    bdcQlrDO.setDh(bdcQlrDO.getDh() + split + bdcQlrDOList.get(i).getDh());
                    bdcQlrDO.setBdcqzh(bdcQlrDO.getBdcqzh() + split + bdcQlrDOList.get(i).getBdcqzh());
                    if (bdcQlrDO.getGyfs() == null) {
                        bdcQlrDO.setGyfs(bdcQlrDOList.get(i).getGyfs());
                    }
                    if (StringUtils.isEmpty(bdcQlrDO.getGyqk())) {
                        bdcQlrDO.setGyqk(bdcQlrDOList.get(i).getGyqk());
                    }
                }
                return bdcQlrDO;
            }
        }
        return null;
    }

    /**
     * @param xmid
     * @param sqrlb
     * @return cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID查询申请人信息 多个申请人 用逗号隔开拼接成一个SQR实体返回
     */
    @Override
    public BdcSlSqrDO wmcatSqrxx(String xmid, String sqrlb) {
        if (StringUtils.isNotBlank(xmid)) {
            List<BdcSlSqrDO> sqrList = bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid, sqrlb);
            return CommonUtil.wmBdcSlSqlr(sqrList);
        }
        return null;
    }

    /**
     * @param zdTable
     * @param dsfFlag
     * @param dsfzd
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 第三方字典 转成 BDC 代码
     */
    @Override
    public String dsfZdToBdcDm(String zdTable, String dsfFlag, String dsfzd) {
        if (StringUtils.isNotBlank(dsfzd)) {
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs(zdTable);
            bdcZdDsfzdgxDO.setDsfzdz(dsfzd);
            bdcZdDsfzdgxDO.setDsfxtbs(dsfFlag);
            BdcZdDsfzdgxDO result = zdService.dsfZdgx(bdcZdDsfzdgxDO);
            if (result != null) {
                return result.getBdczdz();
            }
        }
        return null;
    }

    /**
     * @param zdTable
     * @param dsfFlag
     * @param bdcdm
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转成 第三方字典 代码
     */
    @Override
    public String bdcDmToDsfZd(String zdTable, String dsfFlag, String bdcdm) {
        if (StringUtils.isNotBlank(bdcdm)) {
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs(zdTable);
            bdcZdDsfzdgxDO.setBdczdz(bdcdm);
            bdcZdDsfzdgxDO.setDsfxtbs(dsfFlag);
            BdcZdDsfzdgxDO result = zdService.dsfZdgx(bdcZdDsfzdgxDO);
            if (result != null) {
                return result.getDsfzdz();
            }
        }
        return null;
    }


    /**
     * @param zdTable
     * @param dsfFlag
     * @param bdcdm
     * @return java.lang.String
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 使用第三方字典对照表对照，有值返回，无值返回原值
     */
    @Override
    public String bdcDmToDsfZdNvl(String zdTable, String dsfFlag, String bdcdm) {
        if (StringUtils.isNotBlank(bdcdm)) {
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs(zdTable);
            bdcZdDsfzdgxDO.setBdczdz(bdcdm);
            bdcZdDsfzdgxDO.setDsfxtbs(dsfFlag);
            BdcZdDsfzdgxDO result = zdService.dsfZdgx(bdcZdDsfzdgxDO);
            if (result != null) {
                return result.getDsfzdz();
            } else {
                return bdcdm;
            }
        }
        return bdcdm;
    }

    /**
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.BdcZsDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID 查询 原证书信息
     */
    @Override
    public BdcZsDO queryYzsByXmid(String xmid) {
        BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
        bdcXmLsgxQO.setXmid(xmid);
        bdcXmLsgxQO.setWlxm(0);
        List<BdcXmLsgxDO> lsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
        if (CollectionUtils.isNotEmpty(lsgxDOList)) {
            BdcXmLsgxDO lsgx = lsgxDOList.get(0);
            // 根据 原项目ID 查询证书
            if (StringUtils.isNotBlank(lsgx.getYxmid())) {
                List<BdcZsDO> zsList = bdcZsFeignService.queryBdcZsByXmid(lsgx.getYxmid());
                if (CollectionUtils.isNotEmpty(zsList)) {
                    BdcZsDO bdcZsDO = zsList.get(0);
                    return bdcZsDO;
                }
            }
        }
        return null;
    }

    /**
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.BdcFdcqDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public BdcFdcqDO queryYFdcqByXmid(String xmid) {
        BdcQl bdcQl = queryYBdcqlByXmid(xmid);
        if (bdcQl != null && bdcQl instanceof BdcFdcqDO) {
            return (BdcFdcqDO) bdcQl;
        }
        return null;
    }

    @Override
    public BdcQl queryYBdcqlByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(xmid);
            bdcXmLsgxQO.setWlxm(0);
            List<BdcXmLsgxDO> lsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(lsgxDOList)) {
                BdcXmLsgxDO lsgx = lsgxDOList.get(0);
                // 根据 原项目ID 查询证书
                if (StringUtils.isNotBlank(lsgx.getYxmid())) {
                    return bdcQllxFeignService.queryQlxx(lsgx.getYxmid());
                }
            }
        }
        return null;
    }

    @Override
    public List<String> queryYZxxqlXmidListByXmid(String xmid) {
        List<String> yxmids = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(xmid);
            List<BdcXmLsgxDO> lsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(lsgxDOList)) {
                for (BdcXmLsgxDO bdcXmLsgxDO : lsgxDOList) {
                    if (bdcXmLsgxDO != null && StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid()) && CommonConstantUtils.ZXYQL_ZX.equals(bdcXmLsgxDO.getZxyql())) {
                        yxmids.add(bdcXmLsgxDO.getYxmid());
                    }
                }
            }
        }
        return yxmids;
    }

    @Override
    public List<String> queryYxmidListByXmidAndWlxmAndZxyql(String xmid, Integer zxyql, Integer wlxm) {
        List<String> yxmids = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(xmid);
            List<BdcXmLsgxDO> lsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(lsgxDOList)) {
                for (BdcXmLsgxDO bdcXmLsgxDO : lsgxDOList) {
                    if (bdcXmLsgxDO != null && StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())) {
                        //如果传了注销原权利，并且注销原权利值与数据表值不一致。则不返回这个xmid
                        if (zxyql != null && !zxyql.equals(bdcXmLsgxDO.getZxyql())) {
                            continue;
                        }
                        //如果传了外联项目，并且外联项目值与数据表值不一致。则不返回这个xmid
                        if (wlxm != null && !wlxm.equals(bdcXmLsgxDO.getWlxm())) {
                            continue;
                        }
                        yxmids.add(bdcXmLsgxDO.getYxmid());
                    }
                }
            }
        }
        return yxmids;
    }

    /**
     * @param xmids
     * @return List<String>
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 查询项目关联的项目信息并根据xmid bdcdyh qllx去重
     */
    @Override
    public Set<BdcXmForZxAccessDTO> queryBdcXmForZxAccessListByXmidsAndWlxmAndZxyql(List<String> xmids) {
        Set<BdcXmForZxAccessDTO> bdcXmForZxAccessDTOSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(xmids)) {
            List<BdcXmForZxAccessDTO> bdcXmForZxAccessDTOS = bdcXmMapper.listBdcXmForZxAccessDTO(xmids);
            if (CollectionUtils.isNotEmpty(bdcXmForZxAccessDTOS)) {
                bdcXmForZxAccessDTOSet.addAll(bdcXmForZxAccessDTOS);
            }
        }
        return bdcXmForZxAccessDTOSet;
    }

    @Override
    public BdcQl queryYBdcqlByXmid_BdcSlXm(String xmid) {
        List<BdcSlXmLsgxDO> slXmLsgxDOs = bdcSlXmLsgxFeignService.listBdcSlXmLsgxByXmid(xmid);
        if (CollectionUtils.isNotEmpty(slXmLsgxDOs)) {
            BdcSlXmLsgxDO xmLsgxDO = slXmLsgxDOs.get(0);
            if (null != xmLsgxDO) {
                return bdcQllxFeignService.queryQlxx(xmLsgxDO.getYxmid());
            }
        }
        return null;
    }

    @Override
    public List<BdcXmDO> listBdcXmByZfxzspbh(String zfxzspbh) {
        if (StringUtils.isNotBlank(zfxzspbh)) {
            Example example = new Example(BdcXmDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("zfxzspbh", zfxzspbh);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();

    }

    /**
     * @param xmDO@return
     * @Date 2021/7/21
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public String queryZslxByxmid(BdcXmDO xmDO) {
        if (null != xmDO && StringUtil.isNotBlank(xmDO.getXmid())) {
            List<String> zslx = bdcZsFeignService.queryGzlZslx(null, xmDO.getXmid());
            if (CollectionUtils.isNotEmpty(zslx)) {
                return zslx.get(0);
            }
        }
        return null;
    }


    @Override
    public String queryZsfjByxmid(BdcXmDO xmDO) {
        if (null != xmDO && StringUtil.isNotBlank(xmDO.getXmid())) {
            List<BdcZsDO> zsDOList = bdcZsFeignService.queryBdcZsByXmid(xmDO.getXmid());
            if (CollectionUtils.isNotEmpty(zsDOList)) {
                if (CommonConstantUtils.ZSLX_ZS.equals(zsDOList.get(0).getZslx())) {
                    return zsDOList.get(0).getFj();
                } else {
                    return "";
                }

            }
        }
        return null;
    }


    /**
     * @param xmidList 批量单元号
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQl>
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 根据批量xmid查询项目（主要针对大数据量分批查询）
     */
    @Override
    public List<BdcXmDO> listBdcXmByXmids(List<String> xmidList) {
        if (CollectionUtils.isEmpty(xmidList)) {
            return Collections.emptyList();
        }

        List<List> subLists = ListUtils.subList(xmidList, 500);
        List<BdcXmDO> xmDOList = new ArrayList<>();

        for(List subList : subLists) {
            Example example = new Example(BdcXmDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("qszt", CommonConstantUtils.QSZT_VALID);
            criteria.andIn("xmid", subList);
            List<BdcXmDO> subXmList = entityMapper.selectByExample(example);

            if(CollectionUtils.isNotEmpty(subXmList)) {
                xmDOList.addAll(subXmList);
            }
        }
        return xmDOList;
    }

    /**
     * 住址核验接口
     *
     * @param cqzh
     * @param qlrmc
     * @return
     */
    @Override
    public BdcXmCqyzDTO listQlrByCqzhAndQlr(String cqzh, String qlrmc) {
        BdcXmCqyzDTO bdcXmCqyzDTO = new BdcXmCqyzDTO();
        if (StringUtils.isBlank(cqzh) || StringUtils.isBlank(qlrmc)){
            bdcXmCqyzDTO.setCheckjg("");
            bdcXmCqyzDTO.setMsg("参数为空");
            return bdcXmCqyzDTO;
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setBdcqzh(cqzh);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if(CollectionUtils.isEmpty(bdcZsDOS)){
            bdcXmCqyzDTO.setCheckjg("产权证号不存在");
            bdcXmCqyzDTO.setMsg("查询成功");
            return bdcXmCqyzDTO;
        }
        BdcZsDO bdcZsDO = bdcZsDOS.get(0);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.queryXmByZsBdcqzh(bdcZsDO.getBdcqzh());
        if(CollectionUtils.isEmpty(bdcXmDOS)){
            bdcXmCqyzDTO.setCheckjg("产权证项目不存在");
            bdcXmCqyzDTO.setMsg("查询成功");
            return bdcXmCqyzDTO;
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listBdcQlrByXmidList(
                bdcXmDOS.stream().map(BdcXmDO::getXmid).collect(Collectors.toList()),
                CommonConstantUtils.QLRLB_QLR
        );
        List<String> qlrmcList = bdcQlrDOS.stream().map(BdcQlrDO::getQlrmc).collect(Collectors.toList());
        if (!qlrmcList.contains(qlrmc)) {
            bdcXmCqyzDTO.setCheckjg("产权证没有该权利人");
            bdcXmCqyzDTO.setMsg("查询成功");
            return bdcXmCqyzDTO;
        }
        bdcXmCqyzDTO.setCheckjg("核验通过");
        bdcXmCqyzDTO.setMsg("查询成功");
        bdcXmCqyzDTO.setZl(bdcXmDO.getZl());
        bdcXmCqyzDTO.setQlrmc(String.join(",",qlrmcList));
        //查询权利
        QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
        queryQlRequestDTO.setBdcdyh(bdcXmDO.getBdcdyh());
        queryQlRequestDTO.setQszt("1");
        queryQlRequestDTO.setWithXm(false);
        queryQlRequestDTO.setWithQlr(false);
        List<FdcqQlWithXmQlrDTO> fdcqQlWithXmQlrDTOS = listFdcqByBdcdyh(queryQlRequestDTO);
        if(CollectionUtils.isNotEmpty(fdcqQlWithXmQlrDTOS)){
            FdcqQlWithXmQlrDTO fdcqQlWithXmQlrDTO = fdcqQlWithXmQlrDTOS.get(0);
            BdcFdcqDO bdcql = fdcqQlWithXmQlrDTO.getBdcql();
            bdcXmCqyzDTO.setFwyt(getBdcZdMcFromDm("fwyt", bdcql.getGhyt().toString()));
        } else {
            bdcXmCqyzDTO.setFwyt(getBdcZdMcFromDm("fwyt", bdcXmDO.getDzwyt().toString()));
        }

        //是否有传入的权利人名称
        return bdcXmCqyzDTO;
    }

    /**
     * 处理外网申请返回的token结构
     *
     * @param object@return
     * @Date 2022/9/1
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public String wwsqToken(Object object) {
        if (null != object) {
            WwsqTokenDTO wwsqTokenDTO = JSONObject.parseObject(JSONObject.toJSONString(object), WwsqTokenDTO.class);
            LOGGER.info("获取互联网token返回为：{}", wwsqTokenDTO.toString());
            if (Constants.CODE_SUCCESS.equals(wwsqTokenDTO.getHead().getCode())) {
                return wwsqTokenDTO.getData().getToken();
            } else {
                throw new AppException("获取互联网token失败！" + wwsqTokenDTO.getHead().getCode() + wwsqTokenDTO.getHead().getMsg());
            }
        } else {
            throw new AppException("获取互联网token返回为空,获取失败！");

        }
    }


    /**
     * @param dtoClass   实际权利DTO
     * @param qllx       权利类型
     * @param requestDTO
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.common.QlWithXmQlrDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询权利、权利人
     */
    private <T> List<T> listQlWithXmAndQlrByBdcdyhOrXmid(Class<T> dtoClass, String qllx, QueryQlRequestDTO requestDTO) {
        List<BdcQl> qlList = new ArrayList<>();
        if (StringUtils.isNotBlank(requestDTO.getBdcdyh()) && StringUtils.isNotBlank(qllx)) {
            String bdcdyh = requestDTO.getBdcdyh();
            List<Integer> qsztList = new ArrayList<>();
            if (StringUtils.isBlank(requestDTO.getQszt())) {
                requestDTO.setQszt(CommonConstantUtils.QSZT_VALID + "");
            }
            String[] qsztArr = requestDTO.getQszt().split(",");
            for (String qszt : qsztArr) {
                qsztList.add(Integer.parseInt(qszt));
            }
            qlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, qllx, qsztList);
        } else if (StringUtils.isNotBlank(requestDTO.getXmid()) && requestDTO.getQllxClass() != null) {
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(requestDTO.getXmid());
            if (requestDTO.getQllxClass().isInstance(bdcQl)) {
                qlList.add(bdcQl);
            }
        }
        if (CollectionUtils.isNotEmpty(qlList)) {
            List<T> dtoList = new ArrayList<>();
            for (BdcQl bdcQl : qlList) {
                QlWithXmQlrDTO dto = new QlWithXmQlrDTO();
                dto.setBdcql(bdcQl);
                if (StringUtils.isNotBlank(bdcQl.getXmid())) {
                    // 查询项目
                    if (BooleanUtils.toBoolean(requestDTO.getWithXm())) {
                        LOGGER.info("查询项目的xmid为：{}", bdcQl.getXmid());
                        BdcXmDO bdcXmDO = getBdcXmByXmid(bdcQl.getXmid());
                        dto.setBdcXmDO(bdcXmDO);
                    }
                    // 查询权利人
                    if (BooleanUtils.toBoolean(requestDTO.getWithQlr())) {
                        List<BdcQlrDO> bdcQlrList = listBdcQlrByXmid(bdcQl.getXmid(), requestDTO.getQlrlb());
                        dto.setBdcQlrList(bdcQlrList);
                    }
                }
                T qlDto = JSONObject.parseObject(JSONObject.toJSONString(dto), dtoClass);
                dtoList.add(qlDto);
            }
            return dtoList;
        }
        return Collections.emptyList();
    }

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">caolu</a>
     * @param jsonObject   请求参数
     * @return JSONObject
     * @description 根据spxtywh查询办件状态，返回slbh
     */
    @Override
    public JSONObject getWwsqztBySlbh(JSONObject jsonObject) {
        JSONObject repJsonObject = new JSONObject(4);
        JSONObject failJson = new JSONObject(4);
        failJson.put("bjzt", "");
        failJson.put("slbh", "");
        failJson.put("scyy", "");
        String slbh = "";
        String spxtywh = "";
        if (jsonObject != null && jsonObject.containsKey("data") && jsonObject.getJSONObject("data").containsKey("slbh")) {
            spxtywh = jsonObject.getJSONObject("data").getString("slbh");
        }
        if (StringUtils.isNotBlank(spxtywh)) {
            String jdzt = "";
            String deleteReason = "";
            Example xmExample = new Example(BdcXmDO.class);
            xmExample.createCriteria().andEqualTo("spxtywh", spxtywh);
            List<BdcXmDO> bdcXmList = entityMapper.selectByExample(xmExample);
            if (CollectionUtils.isNotEmpty(bdcXmList) && bdcXmList.get(0) != null) {
                slbh = bdcXmList.get(0).getSlbh();
                if (CommonConstantUtils.AJZT_YB_DM.equals(bdcXmList.get(0).getAjzt())) {
                    jdzt = "已办结";
                }
                if (StringUtils.isBlank(jdzt)
                        && StringUtils.isNotBlank(bdcXmList.get(0).getGzlslid())) {
                    try {
                        List<TaskData> list = processTaskClient.processLastTasks(bdcXmList.get(0).getGzlslid());
                        if (CollectionUtils.isNotEmpty(list)) {
                            jdzt = list.get(0).getTaskName();
                        }
                    } catch (Exception e) {
                        LOGGER.error("没有找到任务列表 gzlslid:{}", bdcXmList.get(0).getGzlslid());
                    }
                }
            } else {
                //删除处理
                BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                bdcCzrzDO.setSpxtywh(spxtywh);
                bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_SC.key());
                List<BdcCzrzDO> czrz = bdcCzrzRestService.listBdcCzrz(bdcCzrzDO);
                jdzt = "已删除";
                if (CollectionUtils.isEmpty(czrz) ) {
                    LOGGER.error("未查询到相关删除的操作日志");
                    return failJson;
                }
                // 获取时间最大的一条数据
                BdcCzrzDO deleteBdcCzrzDO = czrz.stream().max(Comparator.comparing(BdcCzrzDO::getCzsj)).get();
                if(deleteBdcCzrzDO == null || StringUtils.isBlank(deleteBdcCzrzDO.getCzyy())){
                    LOGGER.error("未查询到相关删除的操作日志");
                    return failJson;
                }
                slbh = deleteBdcCzrzDO.getSlbh();
                deleteReason = deleteBdcCzrzDO.getCzyy().substring(deleteBdcCzrzDO.getCzyy().lastIndexOf(":") + 1, deleteBdcCzrzDO.getCzyy().length());
            }
            repJsonObject.put("bjzt", jdzt);
            repJsonObject.put("slbh", slbh);
            repJsonObject.put("scyy", deleteReason);
            return repJsonObject;
        } else {
            LOGGER.error("缺少slbh入参");
            return failJson;
        }
    }


    /**
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcQlrQO
     * @return String
     * @description 根据工作流实例ID 查询抵押和预抵押的权利人名称
     */
    @Override
    public List<String> listDyaAndYdyQlrmc(BdcQlrQO bdcQlrQO) {
        return bdcXmMapper.listDyaAndYdyQlrmc(bdcQlrQO);

    }
}
