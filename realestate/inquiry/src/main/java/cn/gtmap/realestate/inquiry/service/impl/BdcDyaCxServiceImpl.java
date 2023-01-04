package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxModelDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDyaDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.common.core.qo.inquiry.count.DyaTjQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcDyaTjVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.core.mapper.BdcDyaXxMapper;
import cn.gtmap.realestate.inquiry.service.BdcDyaCxService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/9/3 14:58
 * @description
 */
@Service
public class BdcDyaCxServiceImpl implements BdcDyaCxService {

    @Autowired
    private Repo repo;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private EntityMapper entityMapper;
    /**
     * 不动产单元状态查询
     */
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    private BdcDyaXxMapper bdcDyaXxMapper;
    @Autowired
    private BdcDypzFeignService bdcDypzFeignService;
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDyaCxServiceImpl.class);


    /**
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 分页查询抵押不动产单元
     */
    @Override
    public Page<BdcDyaDTO> listBdcDyaByPage(Pageable pageable, BdcDyaQo bdcDyaQo) {
        List<String> bdcdyhList = repo.selectList("listBdcdys", bdcDyaQo);
        List<LsgxModelDTO> lsgxModelDTOS = null;
        List<String> xmidList = new ArrayList<>();
        try {
            for (String bdcdyh : bdcdyhList) {
                lsgxModelDTOS = bdcLsgxFeignService.getCqList(bdcdyh);
                // 取现势状态的xmid
                for (LsgxModelDTO lsgxModelDTO : lsgxModelDTOS) {
                    if (1 == lsgxModelDTO.getQszt()) {
                        xmidList.add(lsgxModelDTO.getId());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("调用init获取产权信息接口异常" + e.getMessage());
        }
        Map paramMap = new HashMap<>(16);
        paramMap.put("xmids", xmidList);

        Page<BdcDyaDTO> dyaDTOPage = repo.selectPaging("listBdcDyaByXmidOrder", paramMap, pageable);
        List<BdcDyaDTO> bdcDyaDTOList = dyaDTOPage.getContent();
        /// 2、调用权籍获取状态
        List<BdcdyhZtResponseDTO> bdcdyhZtDTOList = bdcdyZtFeignService.commonListBdcdyhZtBybdcdyh(bdcdyhList, "");
        if (CollectionUtils.isEmpty(bdcdyhZtDTOList)) {
            return dyaDTOPage;
        }
        /// 3、匹配设置不动产单元状态
        for (BdcDyaDTO bdcDyaDTO : bdcDyaDTOList) {
            for (BdcdyhZtResponseDTO bdcdyhZtDTO : bdcdyhZtDTOList) {
                if (org.apache.commons.lang3.StringUtils.equals(bdcDyaDTO.getBdcdyh(), bdcdyhZtDTO.getBdcdyh())) {
                    bdcDyaDTO.setBdcdyZtDTO(bdcdyhZtDTO);
                }
            }
        }
        return dyaDTOPage;
    }

    /**
     * @param dyaTjQO 抵押统计查询QO
     * @return List<BdcDyaTjVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计月报信息
     */
    @Override
    public List<BdcDyaTjVO> getDyaTjMonth(DyaTjQO dyaTjQO) {
        if (CollectionUtils.isEmpty(dyaTjQO.getBmmcList())) {
            throw new MissingArgumentException("缺失所有的组织机构信息");
        }
        List<BdcDyaTjVO> bdcDyaTjVOList = new ArrayList();
        // 合计对象
        BdcDyaTjVO bdcDyaTjVOHj = new BdcDyaTjVO();
        bdcDyaTjVOHj.setBmmc("合计");

        DyaTjQO dyaTjQOParam = new DyaTjQO();
        dyaTjQOParam.setCxkssj(dyaTjQO.getCxkssj());
        dyaTjQOParam.setCxjssj(dyaTjQO.getCxjssj());
        dyaTjQOParam.setGzldyidList(dyaTjQO.getGzldyidList());
        for (String bmmc : dyaTjQO.getBmmcList()) {
            dyaTjQOParam.setBmmc(bmmc);

            bdcDyaTjVOList.add(this.getBmDyaTjMonth(dyaTjQOParam, bdcDyaTjVOHj));
        }
        bdcDyaTjVOList.add(bdcDyaTjVOHj);
        return bdcDyaTjVOList;
    }

    /**
     * @param dyaTjQO 抵押统计查询QO
     * @return List<BdcDyaTjVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计日报信息
     */
    @Override
    public List<BdcDyaTjVO> getDyaTjDay(DyaTjQO dyaTjQO) {
        List<BdcDyaTjVO> bdcDyaTjVOList = bdcDyaXxMapper.getDayDyatjXx(dyaTjQO);
        if (CollectionUtils.isNotEmpty(bdcDyaTjVOList)) {
            Integer sum = 0;
            Double dyaJeSum = 0.0;
            for (BdcDyaTjVO bdcDyaTjVO : bdcDyaTjVOList) {
                sum += bdcDyaTjVO.getDaySum();
                dyaJeSum += bdcDyaTjVO.getDayDyaJeSum();
                bdcDyaTjVO.setSum(sum);
                bdcDyaTjVO.setDyaJeSum(dyaJeSum);
            }
        }
        return bdcDyaTjVOList;
    }

    /**
     * @param redisKey 保存redis的key值
     * @param dylx     抵押统计的打印类型
     * @return 打印xml信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计的xml信息
     */
    @Override
    public String getDyaTjXml(String redisKey, String dylx) {
        if (StringUtils.isBlank(redisKey) || StringUtils.isBlank(dylx)) {
            throw new MissingArgumentException("缺失查询参数redisKey或dylx！");
        }

        //执行sql从数据库获取需要打印的数据
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        // 子表打印信息
        Map dyatjObj = this.getDyatjXxByRedis(redisKey);
        List<BdcDyaTjVO> bdcDyaTjVOList = new ArrayList();
        if (MapUtils.isNotEmpty(dyatjObj) && dyatjObj.containsKey("list")) {
            bdcDyaTjVOList = JSONObject.parseArray(JSONObject.toJSONString(dyatjObj.get("list")), BdcDyaTjVO.class);
            dealZeroToNull(bdcDyaTjVOList);
        }
        Multimap<String, List> zbMap = ArrayListMultimap.create();
        zbMap.put("dyatjList", bdcDyaTjVOList);

        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList();
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDylx(dylx);
        bdcDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        bdcDysjDTO.setDysj(JSONObject.toJSONString(dyatjObj));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(zbMap));

        bdcDysjDTOList.add(bdcDysjDTO);
        return bdcPrintFeignService.printDatas(bdcDysjDTOList);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 处理结果中的0 为null
     */
    private void dealZeroToNull(List<BdcDyaTjVO> bdcDyaTjVOList) {
        for (BdcDyaTjVO bdcDyaTjVO : bdcDyaTjVOList) {
            bdcDyaTjVO.setSum(0 == bdcDyaTjVO.getSum() ? null : bdcDyaTjVO.getSum());
            bdcDyaTjVO.setDyaJeSum(0 == bdcDyaTjVO.getDyaJeSum() ? null : bdcDyaTjVO.getDyaJeSum());
            bdcDyaTjVO.setDyaMjSum(0 == bdcDyaTjVO.getDyaMjSum() ? null : bdcDyaTjVO.getDyaMjSum());

            bdcDyaTjVO.setDaySum(0 == bdcDyaTjVO.getDaySum() ? null : bdcDyaTjVO.getDaySum());
            bdcDyaTjVO.setDayDyaJeSum(0 == bdcDyaTjVO.getDayDyaJeSum() ? null : bdcDyaTjVO.getDayDyaJeSum());
            bdcDyaTjVO.setDayDyaMjSum(0 == bdcDyaTjVO.getDayDyaMjSum() ? null : bdcDyaTjVO.getDayDyaMjSum());

            bdcDyaTjVO.setMonthSum(0 == bdcDyaTjVO.getMonthSum() ? null : bdcDyaTjVO.getMonthSum());
            bdcDyaTjVO.setMonthDyaJeSum(0 == bdcDyaTjVO.getMonthDyaJeSum() ? null : bdcDyaTjVO.getMonthDyaJeSum());
            bdcDyaTjVO.setMonthDyaMjSum(0 == bdcDyaTjVO.getMonthDyaMjSum() ? null : bdcDyaTjVO.getMonthDyaMjSum());

            bdcDyaTjVO.setYearSum(0 == bdcDyaTjVO.getYearSum() ? null : bdcDyaTjVO.getYearSum());
            bdcDyaTjVO.setYearDyaJeSum(0 == bdcDyaTjVO.getYearDyaJeSum() ? null : bdcDyaTjVO.getYearDyaJeSum());
            bdcDyaTjVO.setYearDyaMjSum(0 == bdcDyaTjVO.getYearDyaMjSum() ? null : bdcDyaTjVO.getYearDyaMjSum());

            bdcDyaTjVO.setValidSum(0 == bdcDyaTjVO.getValidSum() ? null : bdcDyaTjVO.getValidSum());
            bdcDyaTjVO.setValidDyaJeSum(0 == bdcDyaTjVO.getValidDyaJeSum() ? null : bdcDyaTjVO.getValidDyaJeSum());
            bdcDyaTjVO.setValidDyaMjSum(0 == bdcDyaTjVO.getValidDyaMjSum() ? null : bdcDyaTjVO.getValidDyaMjSum());
        }
    }

    /**
     * @param dylx         打印类型
     * @param listDyatjStr 打印统计信息
     * @return 保存到redis的key值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存抵押提交信息到redis
     */
    @Override
    public String saveDyatjXxToRedis(String dylx, String listDyatjStr) {
        // 保存至Redis
        String key;
        if (org.apache.commons.lang3.StringUtils.equals(dylx, "dyatjMonth")) {
            key = CommonConstantUtils.REDIS_DYATJ_MONTH_PRINT;
        } else if (org.apache.commons.lang3.StringUtils.equals(dylx, "dyatjDay")) {
            key = CommonConstantUtils.REDIS_DYATJ_DAY_PRINT;
        } else {
            throw new MissingArgumentException("redis保存key值有误，请检查！");
        }
        return redisUtils.addStringValue(key + UUIDGenerator.generate(),
                listDyatjStr, 360);
    }

    /**
     * 抵押分页查询-标准版：查询抵押权表
     *
     * @param pageable pageable
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<BdcDyaDTO> listStandardDyaByPage(Pageable pageable, BdcDyaQo bdcDyaQo) {
        if (bdcDyaQo == null) {
            throw new MissingArgumentException("查询参数对象不能为空！");
        }
        Page<BdcDyaDTO> dyaDTOPage = repo.selectPaging("listBdcDyaByPageOrder", bdcDyaQo, pageable);
        return dyaDTOPage;
    }

    /**
     * 标准版: 抵押分页查询，查询预告预抵押信息
     */
    @Override
    public Page<BdcDyaDTO> listStandardYgDyaByPage(Pageable pageable, BdcDyaQo bdcDyaQo) {
        if (bdcDyaQo == null) {
            throw new MissingArgumentException("查询参数对象不能为空！");
        }
        Page<BdcDyaDTO> dyaDTOPage = repo.selectPaging("listBdcYgDyaByPageOrder", bdcDyaQo, pageable);
        return dyaDTOPage;
    }

    /**
     * 抵押分页查询-常州版：查询抵押权表
     *
     * @param pageable pageable
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @Override
    public Page<BdcDyaDTO> listChangzhouDyaByPage(Pageable pageable, BdcDyaQo bdcDyaQo) {
        if (bdcDyaQo == null) {
            throw new MissingArgumentException("查询参数对象不能为空！");
        }
        Page<BdcDyaDTO> dyaDTOPage = repo.selectPaging("listBdcDyaCzByPageOrder", bdcDyaQo, pageable);
        return dyaDTOPage;
    }

    /**
     * 抵押分页查询-标准版：查询抵押权表
     *
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @Override
    public List listStandardDya(BdcDyaQo bdcDyaQo) {
        if (null == bdcDyaQo) {
            throw new MissingArgumentException("查询参数对象不能为空！");
        }
        return repo.selectList("listBdcDyaByPageOrder", bdcDyaQo);
    }

    /**
     * 标准版: 抵押查询：查询预告预抵押信息
     */
    @Override
    public List<BdcDyaDTO> listStandardYgDya(BdcDyaQo bdcDyaQo) {
        if (null == bdcDyaQo) {
            throw new MissingArgumentException("查询参数对象不能为空！");
        }
        return repo.selectList("listBdcYgDyaByPageOrder", bdcDyaQo);
    }

    /**
     * 抵押分页查询-常州版：查询抵押权表
     *
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @Override
    public List listChangzhouDya(BdcDyaQo bdcDyaQo) {
        if (null == bdcDyaQo) {
            throw new MissingArgumentException("查询参数对象不能为空！");
        }
        return repo.selectList("listBdcDyaCzByPageOrder", bdcDyaQo);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询抵押
     */
    @Override
    public List listDyaByXmid(String xmid) {
        return bdcDyaXxMapper.listDyaByXmid(xmid);
    }

    /**
     * @param redisKey redis的key值
     * @return List<BdcDyaTjVO> 抵押统计查询信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据redis的key值，获取redis中的抵押统计查询信息
     */
    private Map getDyatjXxByRedis(String redisKey) {
        String dyaTjxxStr = redisUtils.getStringValue(redisKey);
        redisUtils.deleteKey(redisKey);
        if (StringUtils.isNotEmpty(dyaTjxxStr)) {
            return (Map) JSONObject.parse(dyaTjxxStr);
        } else {
            return new HashMap();
        }
    }

    /**
     * @param dyaTjQOParam 抵押统计查询QO
     * @param bdcDyaTjVOHj 总合计对象，用于数据累计计算
     * @return BdcDyaTjVO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取单个部门的抵押统计月报信息
     */
    private BdcDyaTjVO getBmDyaTjMonth(DyaTjQO dyaTjQOParam, BdcDyaTjVO bdcDyaTjVOHj) {
        // 月报查询的为本月，本年的信息，任意获取一个时间参数作为查询条件
        BdcDyaTjVO bdcDyaTjVO = new BdcDyaTjVO();
        bdcDyaTjVO.setBmmc(dyaTjQOParam.getBmmc());
        bdcDyaTjVO.setCxkssj(dyaTjQOParam.getCxkssj());
        bdcDyaTjVO.setCxjssj(dyaTjQOParam.getCxjssj());
        // 查询本月抵押信息
        BdcDyaTjVO bdcDyaTjVOMonth = bdcDyaXxMapper.getMonthDyatjXx(dyaTjQOParam);
        bdcDyaTjVO.setMonthSum(bdcDyaTjVOMonth.getMonthSum());
        bdcDyaTjVO.setMonthDyaJeSum(bdcDyaTjVOMonth.getMonthDyaJeSum());
        bdcDyaTjVO.setMonthDyaMjSum(bdcDyaTjVOMonth.getMonthDyaMjSum());

        bdcDyaTjVOHj.setMonthSum(bdcDyaTjVOHj.getMonthSum() + bdcDyaTjVOMonth.getMonthSum());
        bdcDyaTjVOHj.setMonthDyaMjSum(bdcDyaTjVOHj.getMonthDyaMjSum() + bdcDyaTjVOMonth.getMonthDyaMjSum());
        bdcDyaTjVOHj.setMonthDyaJeSum(bdcDyaTjVOHj.getMonthDyaJeSum() + bdcDyaTjVOMonth.getMonthDyaJeSum());
        // 查询本年抵押信息
        BdcDyaTjVO bdcDyaTjVOYear = bdcDyaXxMapper.getYearDyatjXx(dyaTjQOParam);
        bdcDyaTjVO.setYearSum(bdcDyaTjVOYear.getYearSum());
        bdcDyaTjVO.setYearDyaJeSum(bdcDyaTjVOYear.getYearDyaJeSum());
        bdcDyaTjVO.setYearDyaMjSum(bdcDyaTjVOYear.getYearDyaMjSum());

        bdcDyaTjVOHj.setYearSum(bdcDyaTjVOHj.getYearSum() + bdcDyaTjVOYear.getYearSum());
        bdcDyaTjVOHj.setYearDyaMjSum(bdcDyaTjVOHj.getYearDyaMjSum() + bdcDyaTjVOYear.getYearDyaMjSum());
        bdcDyaTjVOHj.setYearDyaJeSum(bdcDyaTjVOHj.getYearDyaJeSum() + bdcDyaTjVOYear.getYearDyaJeSum());
        // 查询现势抵押信息
        BdcDyaTjVO bdcDyaTjVOValid = bdcDyaXxMapper.getValidDyatjXx(dyaTjQOParam);
        bdcDyaTjVO.setValidSum(bdcDyaTjVOValid.getValidSum());
        bdcDyaTjVO.setValidDyaJeSum(bdcDyaTjVOValid.getValidDyaJeSum());
        bdcDyaTjVO.setValidDyaMjSum(bdcDyaTjVOValid.getValidDyaMjSum());

        bdcDyaTjVOHj.setValidSum(bdcDyaTjVOHj.getValidSum() + bdcDyaTjVOValid.getValidSum());
        bdcDyaTjVOHj.setValidDyaMjSum(bdcDyaTjVOHj.getValidDyaMjSum() + bdcDyaTjVOValid.getValidDyaMjSum());
        bdcDyaTjVOHj.setValidDyaJeSum(bdcDyaTjVOHj.getValidDyaJeSum() + bdcDyaTjVOValid.getValidDyaJeSum());

        return bdcDyaTjVO;
    }

    /**
     * 抵押分页查询-蚌埠版：查询抵押权表
     *
     * @param pageable pageable
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<BdcDyaDTO> listBengbuDyaByPage(Pageable pageable, BdcDyaQo bdcDyaQo) {
        if (bdcDyaQo == null) {
            throw new MissingArgumentException("查询参数对象不能为空！");
        }
        Page<BdcDyaDTO> dyaDTOPage = repo.selectPaging("listBdcDyaBengbuByPageOrder", bdcDyaQo, pageable);
        return dyaDTOPage;
    }

    /**
     * 抵押分页查询-蚌埠版：查询抵押权表
     *
     * @param bdcDyaQo 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    public List listBengbuDya(BdcDyaQo bdcDyaQo) {
        if (null == bdcDyaQo) {
            throw new MissingArgumentException("查询参数对象不能为空！");
        }
        return repo.selectList("listBdcDyaBengbu", bdcDyaQo);
    }

    /**
     * 查询抵押全表 qszt！=2
     *
     * @param bdcdyh
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:22 2020/11/5
     */
    @Override
    public List listDyaByBdcdyh(String bdcdyh) {
        return bdcDyaXxMapper.listDyaByBdcdyh(bdcdyh);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询抵押信息
     */
    @Override
    public List<BdcDyaqDO> listBdcDyaqByBdcdyhs(List<String> bdcdyhList) {
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            return Lists.newArrayList();
        }
        return bdcDyaXxMapper.listBdcDyaqByBdcdyhs(bdcdyhList);
    }

    /**
     * 查询抵押全表
     *
     * @param bdcdyh
     * @param qszt
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:22 2020/11/5
     */
    @Override
    public List listDyaByBdcdyhAndqszt(String bdcdyh, Integer qszt) {

        return bdcDyaXxMapper.listDyaByBdcdyhAndqszt(bdcdyh, qszt);

    }

    @Override
    public String getCqBdcqzhByXmxx(BdcDyaDTO bdcDyaDTO) {
        if(StringUtils.isNotBlank(bdcDyaDTO.getBdcdyh()) && Objects.nonNull(bdcDyaDTO.getQllx())){
            List<String> bdcqzhList = repo.selectList("getCqBdcqzhByXmxx", bdcDyaDTO);
            if(CollectionUtils.isNotEmpty(bdcqzhList)){
                return bdcqzhList.get(0);
            }
        }
        return "";
    }

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
     * @version 2022/09/21
     * @description 根据地籍号查询抵押权表现势的在建工程抵押
     */
    @Override
    public List<BdcDyaqDO> listDyaByDjhAndQszt(String djh, Integer qszt, Integer dybdclx) {
        if(StringUtils.isBlank(djh) || qszt == null || dybdclx == null ){
            throw new MissingArgumentException("缺失参数！");
        }

        return bdcDyaXxMapper.listBdcDyaqByDjhAndQszt(djh,qszt,dybdclx);
    }

}
