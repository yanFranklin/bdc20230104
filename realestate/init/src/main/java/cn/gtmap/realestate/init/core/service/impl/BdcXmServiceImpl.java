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
 * @description 查询不动产信息
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
     * @description 解封产权合并流程
     */
    @Value("#{'${lcbs.jfcqhb:}'.split(',')}")
    private List<String> jfcqhbGzldyidList;


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 解押产权合并流程
     */
    @Value("#{'${lcbs.dyzxcqhb:}'.split(',')}")
    private List<String> jycqhbGzldyidList;

    /**
     * 任务列表单元号、坐落等条件输入联想词匹配查询限制数量
     */
    @Value("${rwlb.mhsl:500}")
    private Integer rwlbMhsl;


    /**
     * 权利其它状况每个不动产单元分开处理，分别读取、保存自己的项目记录
     */
    @Value("${nttscl.qlztzkfk:false}")
    private boolean qlztzkfk;

    /**
     * 缓存权籍管理代码（无需缓存到 Redis, 直接每个应用实例内部缓存）
     */
    private List<String> qjgldmList = Collections.emptyList();

    /**
     * 启动初始化处理
     */
    @PostConstruct
    public void init() {
        // 查询权籍管理代码并缓存
        qjgldmList = bdcXmMapper.listQjgldm();
    }


    /**
     * @param bdcXmQO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据办结日期 查询 项目列表
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
     * 通过传入的不动产项目参数查询不动产项目信息
     *
     * @param bdcXmQO 项目表查询参数对象
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
     * 查询不动产项目部分信息
     *
     * @param gzlslid 工作流实例ID
     * @param slbh    工作流实例ID
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
     * 查询一条不动产项目部分信息
     *
     * @param gzlslid 工作流实例ID
     * @param slbh    工作流实例ID
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
     * @param bdcXmDO 不动产项目信息
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 更新不动产项目信息
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
     * 通过项目id获得不动产服务开关实例信息
     *
     * @param xmid 不动产项目id
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
            throw new MissingArgumentException("缺失参数ID信息。");
        }
        return entityMapper.updateByPrimaryKey(bdcCshFwkgSlDO);
    }

    /**
     * @return int
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmids, sfhz]
     * @description 批量更新是否换证
     */
    @Override
    public int batchUpdateCshFwkgSlSfhz(List<String> xmids, String sfhz) {
        if (CollectionUtils.isEmpty(xmids) || StringUtils.isBlank(sfhz)) {
            LOGGER.error("批量更新是否换证缺少必要参数！");
            return 0;
        }
        return bdcCshFwkgSlMapper.batchUpdateCshFwkgSlSfhz(xmids, sfhz);
    }

    @Override
    public List<BdcSlCshFwkgDataDTO> queryCshFwkgSlByGzlslid(String gzlslid, String bdcdyh) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("未获取到工作流实例ID。");
        }

        if (StringUtils.isNotBlank(bdcdyh)) {
            // 当存在不动产单元号时，查询一条记录的初始化开关服务信息
            List<BdcSlCshFwkgDataDTO> dataList = new ArrayList<>(1);
            dataList.add(this.getFwkgDataByGzlslidAndBdcdyh(gzlslid, bdcdyh));
            return dataList;
        } else {
            return this.bdcCshFwkgSlMapper.queryBdcCshFwkgSlByGzlslid(gzlslid);
        }
    }

    /**
     * 通过工作流实例ID与不动产单元号，查询一条初始化开关服务信息
     */
    private BdcSlCshFwkgDataDTO getFwkgDataByGzlslidAndBdcdyh(String gzlslid, String bdcdyh) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyh(bdcdyh);
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = this.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("未获取到项目信息。");
        }
        String xmid = bdcXmDOList.get(0).getXmid();
        BdcCshFwkgSlDO bdcCshFwkgSlDO = entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class, xmid);
        BdcSlCshFwkgDataDTO fwkgDataDTO = new BdcSlCshFwkgDataDTO(bdcdyh);
        dozerMapperT.map(bdcCshFwkgSlDO, fwkgDataDTO);
        return fwkgDataDTO;
    }


    /**
     * 查询不动产项目初始化流实例数据
     *
     * @param gzlslid 工作流实例ID
     * @return
     */
    @Override
    public List<BdcCshFwkgSlDO> listBdCshSl(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) {
        return bdcXmMapper.listBdCshSl(gzlslid);
    }

    /**
     * 通过项目id获得与其组合的所有项目信息
     *
     * @param xmid  不动产项目id
     * @param sftlc 是否同流程
     * @return List<BdcXmZhxxDTO>
     */
    @Override
    public List<BdcXmZhxxDTO> queryBdcXmZhxx(String xmid, boolean sftlc) {
        List<BdcXmZhxxDTO> list = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            if (bdcXmDO != null && (StringUtils.isNotBlank(bdcXmDO.getGzlslid()) || !sftlc)) {
                String gzlslid = bdcXmDO.getGzlslid();
                //查询上手项目id
                prevBdcXmZhxx(gzlslid, xmid, list, sftlc);
                //当前项目存储
                list.add(dozerMapperT.map(bdcXmDO, BdcXmZhxxDTO.class));
                //查询下手项目id
                nextBdcXmZhxx(gzlslid, xmid, list, sftlc);
                //循环赋值顺序号
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSxh(i + 1);
                }
            }
        }
        return list;
    }

    /**
     * 通过传入的工作流实例ID参数查询不动产项目信息
     *
     * @param gzlslid 工作流实例ID
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
     * 通过项目ID获得原项目ID
     *
     * @param xmid 不动产项目ID
     * @return 原项目ID
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
     * 嵌套获取上手项目信息并存储
     *
     * @param gzlslid
     * @param xmid
     * @param list
     * @param sftlc   是否同流程
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    private void prevBdcXmZhxx(String gzlslid, String xmid, List<BdcXmZhxxDTO> list, boolean sftlc) {
        //查询上手项目id
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
     * 嵌套获取下手项目信息并存储
     *
     * @param gzlslid
     * @param xmid
     * @param list
     * @param sftlc   是否同流程
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    private void nextBdcXmZhxx(String gzlslid, String xmid, List<BdcXmZhxxDTO> list, boolean sftlc) {
        //查询上手项目id
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
     * 判定该流程的类型
     *
     * @param bdcXmList 不动产项目集合
     * @return 1:普通  2：组合  3：批量 4:批量组合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 判定该流程的类型
     */
    @Override
    public int bdcXmLx(@NotEmpty(message = "项目集合不能为空") List<BdcXmDTO> bdcXmList) {
        int lx = CommonConstantUtils.LCLX_PT;
        if (CollectionUtils.isNotEmpty(bdcXmList)) {
            String gzldyid = bdcXmList.get(0).getGzldyid();
            //流程类型特殊处理
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
                //默认赋值为批量
                lx = CommonConstantUtils.LCLX_PL;
                //循环项目查看是否有多个不动产单元
                Set<String> bdcdyhSet = new HashSet<>();
                //循环项目查看是否有多个登记小类
                Set<String> djxlSet = new HashSet<>();
                //循环项目查询是否有多种权利
                Set<String> qlSet = new HashSet<>();
                for (BdcXmDTO xm : bdcXmList) {
                    //解封合并流程，存在同一个djxl的批量组合
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
                    //已经存在同一个不动产单元的并且登记小类要多个为组合
                    if (bdcdyhSet.contains(xm.getBdcdyh()) && djxlSet.size() > 1) {
                        lx = CommonConstantUtils.LCLX_ZH;

                    }

                    bdcdyhSet.add(xm.getBdcdyh());
                    //组合的判定是不是批量组合
                    if (CommonConstantUtils.LCLX_ZH.equals(lx) && bdcdyhSet.size() > 1) {
                        lx = CommonConstantUtils.LCLX_PLZH;
                        break;
                    }
                    //存在多种权利，为批量组合或者组合
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
     * 判定该流程的类型
     *
     * @param bdcXmList 不动产项目集合
     * @return 1:普通  2：组合  3：批量  4:批量组合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 判定该流程的类型
     */
    @Override
    public int bdcXmLxByAllXm(@NotEmpty(message = "项目集合不能为空") List<BdcXmDO> bdcXmList) {
        int lx = CommonConstantUtils.LCLX_PT;
        boolean multiple = bdcXmList.size() > 1;
        if (multiple) {
            //默认赋值为批量
            lx = CommonConstantUtils.LCLX_PL;
            //循环项目查看是否有多个不动产单元
            Set<String> bdcdyhSet = new HashSet<>();
            for (BdcXmDO xm : bdcXmList) {
                //已经存在同一个不动产单元的为组合
                if (bdcdyhSet.contains(xm.getBdcdyh())) {
                    lx = CommonConstantUtils.LCLX_ZH;
                }
                bdcdyhSet.add(xm.getBdcdyh());
                //组合的判定是不是批量组合
                if (CommonConstantUtils.LCLX_ZH.equals(lx) && bdcdyhSet.size() > 1) {
                    lx = CommonConstantUtils.LCLX_PLZH;
                    break;
                }
            }
        }
        return lx;
    }

    /**
     * 判定该流程的类型
     *
     * @param gzlslid 工作流实例ID
     * @return 1:普通  2：组合  3：批量 4:批量组合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 判定该流程的类型
     */
    @Override
    public int bdcXmLx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) {
        List<BdcXmDTO> bdcXmDOList = listXmBfxx(gzlslid, null);
        return bdcXmLx(bdcXmDOList);
    }

    /**
     * 删除流程实例关系表
     *
     * @param gzlslid 工作流实例ID
     * @return
     */
    @Override
    public void deleteCshFwkgSl(@NotBlank(message = "工作流实例ID不能为空") String gzlslid) {
        bdcXmMapper.deleteCshFwkgSl(gzlslid);
    }

    /**
     * 根据工作流实例Id和权利类型获取登记原因
     *
     * @param gzlslid 工作流实例ID
     * @param qllx    权利类型
     * @return
     */
    @Override
    public String queryDjyy(@NotBlank(message = "工作流实例ID不能为空") String gzlslid, Integer qllx) {
        return bdcXmMapper.queryDjyy(gzlslid, qllx);
    }

    @Override
    public String queryZxYgYdyDjyy(String gzlslid, List<Integer> ygdjzl) {
        return bdcXmMapper.queryZxYgYdyDjyy(gzlslid, ygdjzl);

    }

    @Override
    public int updateBatchBdcXm(BdcDjxxUpdateQO bdcDjxxUpdateQO) {
        if (bdcDjxxUpdateQO == null || StringUtils.isBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
        }
        String jsonStr = bdcDjxxUpdateQO.getJsonStr();
        //获取更新json对象
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        // 判断是否要追加权利其他状况
        if (jsonObject.containsKey(CommonConstantUtils.ADD_QLQTZK)) {
            jsonObject.put("bfqlqtzk", jsonObject.get(CommonConstantUtils.ADD_QLQTZK).toString());
            jsonStr = JSON.toJSONString(jsonObject);
        }
        Map map = new HashMap();
        //获取批量更新语句
        String statement = SqlUtils.getBatchUpdateStatement(jsonObject, BdcXmDO.class.getName());
        if (!statement.contains("SET")) {
            return 0;
        }
        if (jsonObject.containsKey(CommonConstantUtils.ADD_QLQTZK) && StringUtils.indexOf(statement, "BFQLQTZK=") > -1) {
            statement = StringUtils.replace(statement, "BFQLQTZK=", "BFQLQTZK=BFQLQTZK||CHR(13)||");
        }
        map.put("statement", statement);
        //where 条件
        map.putAll(bdcDjxxUpdateQO.getWhereMap());
        //获取实体对象
        BdcXmDO bdcXmDO = JSON.parseObject(jsonStr, BdcXmDO.class);
        map.put("record", bdcXmDO);
        return bdcXmMapper.updateBatchBdcXm(map);
    }

    /**
     * @param bz
     * @param djxl
     * @param gzlslid
     * @param xmid
     * @return 更新数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 批量更新项目备注
     */
    @Override
    public int updateBatchXmBz(@NotBlank(message = "备注不能为空") String bz, String djxl, String gzlslid, String xmid) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("接口传递参数缺失！");
        }
        Map map = new HashMap();
        map.put("bz", bz);
        map.put("djxl", djxl);
        map.put("gzlslid", gzlslid);
        map.put("xmid", xmid);
        return bdcXmMapper.updateBatchXmBz(map);
    }

    /**
     * @param json 更新内容json
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据json内容更新证书项目的权利其他状况
     */
    @Override
    public int updateBdcZsXmBfqlqtzk(String json, Boolean plgx) {
        if (StringUtils.isBlank(json)) {
            throw new MissingArgumentException("updateQlqtzkAndFj缺失更新参数Json数据！");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        // 更新项目的
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
     * @param bdcXmDO 项目信息
     * @return int 更新数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新证书相关的项目的部分权利其他状况
     */
    @Override
    public int updateBdcZsXmBfqlqtzk(BdcXmDO bdcXmDO, Boolean plgx) {
        if (Objects.isNull(plgx)) {
            plgx = false;
        }
        String xmidParam = bdcXmDO.getXmid();
        if (StringUtils.isBlank(xmidParam)) {
            throw new MissingArgumentException("缺失参数xmid！");
        }
        Map<String, Object> whereMap = new HashMap();
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();

        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(bdcXmDO));
        bdcDjxxUpdateQO.setWhereMap(whereMap);

        List<String> zsXmidlist = new ArrayList<>();
        if (qlztzkfk && !plgx) {
            // 只更新自己的项目表记录
            zsXmidlist.add(bdcXmDO.getXmid());
        } else {
            // 获取项目所在证书关联的所有项目(批量发一本证，单个权利表单保存后所有项目表的权利其它状况都更新为一致)
            zsXmidlist = this.queryZsxmList(xmidParam);
        }

        if (CollectionUtils.isEmpty(zsXmidlist)) {
            // 老数据查询不到初始化实例关系信息，直接更新
            zsXmidlist = new ArrayList();
            zsXmidlist.add(xmidParam);
        }
        whereMap.put(Constants.XMIDS, zsXmidlist);
        whereMap.put(Constants.GZLSLID, bdcXmDO.getGzlslid());
        return this.updateBatchBdcXm(bdcDjxxUpdateQO);
    }

    /**
     * @param xmid
     * @return List<String> 项目集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询当前证书项目的涉及的项目集合
     */
    @Override
    public List<String> queryZsxmList(String xmid) {
        List<String> list = new ArrayList<>();
        BdcCshFwkgSlDO bdcCshFwkgSlDO = this.queryCshFwkgSl(xmid);
        //判断当前流程是否有多个项目生成一本正
        BdcXmDO bdcXmDO = this.queryBdcXmByPrimaryKey(xmid);
        if (bdcCshFwkgSlDO != null && bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
            if (bdcCshFwkgSlDO.getZsxh() == null) {
                list.add(bdcCshFwkgSlDO.getId());
            } else {
                List<BdcCshFwkgSlDO> listBdCshSl = this.listBdCshSl(bdcXmDO.getGzlslid());
                if (CollectionUtils.isNotEmpty(listBdCshSl)) {
                    for (BdcCshFwkgSlDO slDOdo : listBdCshSl) {
                        //顺序号相等的加入
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
     * @param gzlslid 项目ID信息
     * @return 备注信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成项目关于证书数量的备注
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
            // 原产权证
            if (StringUtils.isNotBlank(bdcXmDO.getYcqzh())) {
                for (String ycqzh : StringUtils.split(bdcXmDO.getYcqzh(), CommonConstantUtils.ZF_YW_DH)) {
                    if (StringUtils.isNotBlank(ycqzh)) {
                        ycqzhSet.add(ycqzh);
                    }
                }
            }
            // 原房产证
            if (StringUtils.isNotBlank(bdcXmDO.getYfczh())) {
                for (String yfczh : StringUtils.split(bdcXmDO.getYfczh(), CommonConstantUtils.ZF_YW_DH)) {
                    if (StringUtils.isNotBlank(yfczh)) {
                        yfczhSet.add(yfczh);
                    }
                }
            }

            // 原土地证
            if (StringUtils.isNotBlank(bdcXmDO.getYtdzh())) {
                for (String ytdzh : StringUtils.split(bdcXmDO.getYtdzh(), CommonConstantUtils.ZF_YW_DH)) {
                    if (StringUtils.isNotBlank(ytdzh)) {
                        ytdzhSet.add(ytdzh);
                    }
                }
            }
        }
        if (CollectionUtils.size(ycqzhSet) > 0) {
            bz = bz.append("原产权证书")
                    .append(CommonConstantUtils.ZF_ZW_MH)
                    .append(CollectionUtils.size(ycqzhSet))
                    .append("本。");
        }
        if (CollectionUtils.size(yfczhSet) > 0) {
            bz = bz.append("原房产证")
                    .append(CommonConstantUtils.ZF_ZW_MH)
                    .append(CollectionUtils.size(yfczhSet))
                    .append("本。");
        }
        if (CollectionUtils.size(ytdzhSet) > 0) {
            bz = bz.append("原土地证")
                    .append(CommonConstantUtils.ZF_ZW_MH)
                    .append(CollectionUtils.size(ytdzhSet))
                    .append("本。");
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
            throw new AppException("查询原项目缺失参数");
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
        //查询上手项目id
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
                                //产权
                                bdcXmDOList.add(prevXm);
                            }
                            if (getOneXm && bdcXmDOList.size() == 1) {
                                break;
                            } else {
                                //继续往下递归
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
            //查询上手项目
            BdcYxmQO bdcYxmQO = new BdcYxmQO();
            bdcYxmQO.setXmidList(xmidList);
            bdcYxmQO.setWlxm(CommonConstantUtils.SF_F_DM);
            List<BdcXmDO> yBdcXmList = listYxmByYxmQO(bdcYxmQO);
            if (CollectionUtils.isNotEmpty(yBdcXmList)) {
                List<String> prevXmidList = new ArrayList<>();
                for (BdcXmDO prevXm : yBdcXmList) {
                    if (!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, prevXm.getQllx())) {
                        //产权
                        bdcXmDOList.add(prevXm);
                        if (!getOneXm) {
                            prevXmidList.add(prevXm.getXmid());
                        }
                    } else {
                        prevXmidList.add(prevXm.getXmid());
                    }

                }
                if (CollectionUtils.isNotEmpty(prevXmidList)) {
                    //继续往下递归
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
            //房屋的话就清掉宗地bdcdywybh
            Boolean isFw = bdcBdcdyService.judgeIsFwByBdcdyh(bdcdyh);
            BdcdyResponseDTO bdcdyResponseDTO = null;
            if (isFw) {
                bdcdyResponseDTO = bdcdyFeignService.queryBdcdy(bdcdyh, null, qjgldm);

            }
            LOGGER.info("匹配查询权籍信息单元号{}结果{}", bdcdyh, JSON.toJSONString(bdcdyResponseDTO));
            for (BdcXmDO xm : list) {
                String ybdcdyh = xm.getBdcdyh();
                //不动产单元做wybh处理
                if (isFw) {
                    if (bdcdyResponseDTO != null) {
                        xm.setBdcdywybh(bdcdyResponseDTO.getFwbm());
                        if (Boolean.TRUE.equals(gxbdcdyfwlx) && StringUtils.isNotBlank(bdcdyResponseDTO.getBdcdyfwlx())) {
                            xm.setBdcdyfwlx(Integer.parseInt(bdcdyResponseDTO.getBdcdyfwlx()));
                        }
                    } else {
                        //如果没有查询到赋值单元号前19位作为唯一编码
                        xm.setBdcdywybh(bdcdyh.length() > 19 ? bdcdyh.substring(0, 19) : bdcdyh);
                    }
                } else {
                    String bdcdyhwybh = bdcdyh.length() > 19 ? bdcdyh.substring(0, 19) : bdcdyh;
                    xm.setBdcdywybh(bdcdyhwybh);
                }
                //处理的不动产项目信息
                xm.setBdcdyh(bdcdyh);
                initResultDTO.getBdcXmList().add(xm);
                //处理的证书信息
                List<BdcZsDO> listZs = bdcZsService.queryBdcZsByXmid(xm.getXmid());
                if (CollectionUtils.isNotEmpty(listZs)) {
                    for (BdcZsDO bdcZsDO : listZs) {
                        bdcZsDO.setBdcdyh(bdcdyh);
                    }
                }
                initResultDTO.getBdcZsList().addAll(listZs);
                //处理的权利信息
                BdcQl bdcQl = bdcQllxService.queryQllxDO(xm.getXmid());
                if (bdcQl != null) {
                    BdcXmDO dyhXm = new BdcXmDO();
                    dyhXm.setBdcdyh(bdcdyh);
                    dyhXm.setBdcdywybh(xm.getBdcdywybh());
                    dozerUtils.initBeanDateConvert(dyhXm, bdcQl);
                    initResultDTO.getBdcQlList().add(bdcQl);
                }
                //为否不处理,其余均处理
                if(!CommonConstantUtils.SF_F_DM.equals(bhdysd)) {
                    //处理单元锁定
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
     * @description 查询项目和附表信息
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
     * @param xm   权利人名称
     * @param zjhm 权利人证件号
     * @param cqzh 不动产权证号
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据条件查询产权
     */
    @Override
    public List<XscqDTO> listXscq(String xm, String zjhm, String cqzh) {
        return bdcXmMapper.listXscq(xm, zjhm, cqzh);
    }

    /**
     * @param xm   权利人名称
     * @param zjhm 权利人证件号
     * @param htbh 合同编号
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据条件查询办证进度
     */
    @Override
    public List<BzjdDTO> listBzjd(String xm, String zjhm, String htbh) {
        return bdcXmMapper.listBzjd(xm, zjhm, htbh);
    }

    /**
     * @param bdcQlrQO 权利人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人名称加证件号找到项目表的经营权信息
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
     * 根据xmids批量查询项目信息
     *
     * @param xmids 项目ID集合
     * @return 项目数据集合
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
     * 查询不动产项目初始化流实例数据
     *
     * @param gzlslid 工作流实例ID
     * @param sfzf
     * @return
     */
    @Override
    public List<BdcCshFwkgSlDO> listBdCshSlSfzf(String gzlslid, Integer sfzf) {
        return bdcXmMapper.listBdCshSlSfzf(gzlslid, sfzf);
    }

    /**
     * 根据产权证号查询现势产权
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
     * 查询登记系统存在的所有权籍管理代码
     *
     * @return {List} 权籍管理代码集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<String> listQjgldm() {
        return qjgldmList;
    }

    /**
     * 查询不动产项目初始化流实例数据
     *
     * @param gzlslid 工作流实例ID
     * @param xmid
     * @return
     */
    @Override
    public List<BdcCshFwkgSlDO> listBdCshSlById(String gzlslid, String xmid) {
        return bdcXmMapper.listBdCshSlById(gzlslid, xmid);
    }

    /**
     * @param bdcCshFwkgSlDO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增初始化房屋开关实例表
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
     * 根据xmid获取历史关系
     *
     * @param xmid 不动产权证号
     * @return List<Map>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public BdcXmLsgxDTO queryBdcxmLsgxByXmid(String xmid) {
        BdcXmLsgxDTO bdcXmLsgxDTO = new BdcXmLsgxDTO();
        bdcXmLsgxDTO.setXmid(xmid);
        //查询中间项目节点
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> listBdcXm = bdcXmMapper.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(listBdcXm)) {
            BdcXmDO bdcXmDO = listBdcXm.get(0);
            BeanUtils.copyProperties(bdcXmDO, bdcXmLsgxDTO);
            bdcXmLsgxDTO.setBdcXmDO(bdcXmDO);
            //查询上一手
            queryBdcYxm(bdcXmLsgxDTO);
            //查询下一手
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
     * @description 根据xmid取daywh值（盐城BDC_XM表独有字段）
     */
    @Override
    public String queryDaywh(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失项目ID!");
        }
        return bdcXmMapper.queryDaywh(xmid);
    }

    /**
     * 模糊查询项目数据
     *
     * @param bdcXmQO 查询参数
     * @return {List} 项目数据
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
     * @param gzlslid 工作流实例ID
     * @return int 同一工作流实例ID不动产单元号数量
     * @description 查询项目信息中同一工作流实例ID不动产单元号数量（【常州】提示查看清册和附表）
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/6/21 20:28
     */
    @Override
    public int countBdcByGzlslidGroupBdcdyh(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失流程ID!");
        }
        return bdcXmMapper.countBdcByGzlslidGroupBdcdyh(gzlslid);
    }

    @Override
    public BdcXmDO queryOneBdcXmDOBySlbh(String slbh) {
        if (StringUtils.isBlank(slbh)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到受理编号信息");
        }
        return this.bdcXmMapper.queryOneBdcXmDOBySlbh(slbh);
    }

    /**
     * 查询上一手
     *
     * @param bdcXmLsgxDTO
     * @return
     */
    public BdcXmLsgxDTO queryBdcYxm(BdcXmLsgxDTO bdcXmLsgxDTO) {
        if (CollectionUtils.isEmpty(bdcXmLsgxDTO.getParentXm())) {
            bdcXmLsgxDTO.setParentXm(new ArrayList<>());
        }
        //如果还有上一手
        Example example = new Example(BdcXmLsgxDO.class);
        example.createCriteria().andEqualTo("xmid", bdcXmLsgxDTO.getXmid());
        List<BdcXmLsgxDO> bdcXmLsgxDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            //查询原项目对应的数据
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
     * 查询下一手
     *
     * @param bdcXmLsgxDTO
     * @return
     */
    public BdcXmLsgxDTO queryBdcNextXm(BdcXmLsgxDTO bdcXmLsgxDTO) {
        if (CollectionUtils.isEmpty(bdcXmLsgxDTO.getSonXm())) {
            bdcXmLsgxDTO.setSonXm(new ArrayList<>());
        }
        //如果还有下一手
        Example example = new Example(BdcXmLsgxDO.class);
        example.createCriteria().andEqualTo("yxmid", bdcXmLsgxDTO.getXmid());
        List<BdcXmLsgxDO> bdcXmLsgxDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
            //查询下一手项目对应的数据
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
