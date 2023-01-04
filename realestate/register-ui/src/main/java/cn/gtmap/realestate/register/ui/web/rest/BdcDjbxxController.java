package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.building.SDmDwxxDO;
import cn.gtmap.realestate.common.core.domain.building.SDmXzdmDO;
import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlDjMlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQlQtsxDTO;
import cn.gtmap.realestate.common.core.enums.BdcDjbQlMlEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.register.BdcDjbqlQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.building.BuildingZdConvertRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDjbQlMlVO;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.ui.core.vo.BdcDjbMlVO;
import cn.gtmap.realestate.register.ui.core.vo.BdcQlQtsxVO;
import cn.gtmap.realestate.register.ui.core.vo.BdcdjbZdjbxxVO;
import cn.gtmap.realestate.register.ui.core.vo.BdcdjbZhjbxxVO;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/25
 * @description 不动产登记簿
 */
@RestController
@RequestMapping("/rest/v1.0/djbxx")
public class BdcDjbxxController extends BaseController {
    @Autowired
    BdcDjbxxFeignService bdcDjbxxFeignService;
    /**
     * 字典服务
     */
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    /**
     * 楼盘表字典服务
     */
    @Autowired
    BuildingZdConvertRestService buildingZdConvertRestService;

    @Autowired
    ZdFeignService zdFeignService;

    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * 实体属性或对应方法名称常量定义
     */
    public static final String QLLX = "qllx";

    @Value("${djb.show.all.yt:false}")
    private Boolean djbShowAllYtFlag;

    /**
     * @param pageable
     * @param zdzhh
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据宗地宗海号获取权利信息目录
     */
    @GetMapping(value = "/{zdzhh}/qlml")
    public Page<BdcQlDjMlDTO> queryQlml(Pageable pageable, @PathVariable(value = "zdzhh") String zdzhh, @RequestParam(name = "bdcdyh", required = false) String bdcdyh) {
        if (StringUtils.isEmpty(zdzhh)) {
            throw new MissingArgumentException("zdzhh");
        }
        BdcDjbqlQO bdcDjbqlQO = new BdcDjbqlQO();
        bdcDjbqlQO.setZdzhh(zdzhh);
        if (StringUtils.isNotBlank(bdcdyh)) {
            bdcDjbqlQO.setBdcdyh(bdcdyh);
        }
        bdcDjbqlQO.setSize(pageable.getPageSize());
        bdcDjbqlQO.setPage(pageable.getPageNumber());
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        bdcDjbqlQO.setSort(sort);
        Page<BdcQlDjMlDTO> bdcQlDjMlDTOPage = bdcDjbxxFeignService.bdcQlDjMlByPageJson(bdcDjbqlQO);
        List<Map> bdcLxZd = bdcZdFeignService.queryBdcZd(CommonConstantUtils.BDCLX);
        List<BdcQlDjMlDTO> bdcQlDjMlDTOList = bdcQlDjMlDTOPage.getContent();
        /**
         * 转换字典
         */
        if (CollectionUtils.isNotEmpty(bdcQlDjMlDTOList)) {
            bdcQlDjMlDTOList.forEach(ml -> {
                if (StringUtils.isNotBlank(ml.getBdclx())) {
                    ml.setBdclx(StringToolUtils.convertBeanPropertyValueOfZd(Integer.parseInt(ml.getBdclx()), bdcLxZd));
                }

            });
        }
        return bdcQlDjMlDTOPage;

    }

    /**
     * @param zdzhh
     * @return BdcBdcdjbDO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 不动产登记簿信息
     */
    @GetMapping(value = "/{zdzhh}/bdcdjb")
    public BdcBdcdjbDO queryBdcdjb(@PathVariable(value = "zdzhh") String zdzhh) {
        if (StringUtils.isEmpty(zdzhh)) {
            throw new MissingArgumentException("zdzhh");
        }
        BdcBdcdjbDO bdcBdcdjbDO = bdcDjbxxFeignService.queryBdcBdcdjb(zdzhh);
        Map queryZd = Maps.newHashMap();
        queryZd.put(CommonConstantUtils.TABLE_S_DM_DWXX, SDmDwxxDO.class);
        queryZd.put(CommonConstantUtils.TABLE_S_DM_XZXX, SDmXzdmDO.class);
        Map<String, List<Map>> zdMap = buildingZdConvertRestService.listZdTable(queryZd);
        List<Map> dwxxListMap = zdMap.get(CommonConstantUtils.TABLE_S_DM_DWXX);
        List<Map> xzxxListMap = zdMap.get(CommonConstantUtils.TABLE_S_DM_XZXX);
        xzxxListMap.forEach(xzxx -> {
            String xzdm = (String) xzxx.get(CommonConstantUtils.COLUMN_XZDM);
            String xzmc = (String) xzxx.get(CommonConstantUtils.COLUMN_XZMC);
            if (StringUtils.equals(xzdm, bdcBdcdjbDO.getSqs())) {
                bdcBdcdjbDO.setSqs(xzmc);
            }
            if (StringUtils.equals(xzdm, bdcBdcdjbDO.getSq())) {
                bdcBdcdjbDO.setSq(xzmc);
            }
            if (StringUtils.equals(xzdm, bdcBdcdjbDO.getXsq())) {
                bdcBdcdjbDO.setXsq(xzmc);
            }
            if (StringUtils.equals(xzdm, bdcBdcdjbDO.getJd())) {
                bdcBdcdjbDO.setJd(xzmc);
            }
            if (StringUtils.equals(xzdm, bdcBdcdjbDO.getJf())) {
                bdcBdcdjbDO.setJf(xzmc);
            }
            if (StringUtils.equals(xzdm, bdcBdcdjbDO.getZu())) {
                bdcBdcdjbDO.setZu(xzmc);
            }
        });
        dwxxListMap.forEach(dwxx -> {
            String dwdm = (String) dwxx.get(CommonConstantUtils.COLUMN_DWDM);
            String dwmc = (String) dwxx.get(CommonConstantUtils.COLUMN_DWMC);
            if (StringUtils.equals(dwdm, bdcBdcdjbDO.getJd())) {
                bdcBdcdjbDO.setJd(dwmc);
            }
            if (StringUtils.equals(dwdm, bdcBdcdjbDO.getJf())) {
                bdcBdcdjbDO.setJf(dwmc);
            }
            if (StringUtils.equals(dwdm, bdcBdcdjbDO.getZu())) {
                bdcBdcdjbDO.setZu(dwmc);
            }
        });
        return bdcBdcdjbDO;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return BdcQlQtsxVO 不动产权利及其他事项
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产权利登记信息及其他事项
     */
    @GetMapping(value = "/{bdcdyh}/qlqtsx")
    public BdcQlQtsxVO querBdcQlxxQtsx(@PathVariable(value = "bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("bdcdyh");
        }
        bdcdyh = StringUtils.deleteWhitespace(bdcdyh);
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        // 只需要查询的qszt
        List<Integer> qsztList = new ArrayList(2);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        qsztList.add(CommonConstantUtils.QSZT_HISTORY);

        BdcQlQtsxVO bdcQlQtsxVO = new BdcQlQtsxVO();
        BdcQlQtsxDTO bdcQlQtsxDTO = bdcDjbxxFeignService.queryBdcQlQtSx(bdcdyh, qsztList);
        if (null != bdcQlQtsxDTO) {
            BeanUtils.copyProperties(bdcQlQtsxDTO, bdcQlQtsxVO);
            if (null != bdcQlQtsxDTO.getQllx()) {
                bdcQlQtsxVO.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcQlQtsxDTO.getQllx(), zdMap.get(QLLX)));
            }
        }

        bdcQlQtsxVO.setBdcdyh(BdcdyhToolUtils.formatBdcdyh(bdcdyh));
        return bdcQlQtsxVO;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 登记簿权利信息获取
     */
    @GetMapping(value = "/{bdcdyh}/qlxx/{qllx}")
    public String queryBdcQl(@PathVariable(value = "bdcdyh") String bdcdyh, @PathVariable(value = "qllx") String qllx) {
        List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(bdcdyh, qllx, null);
        String zl = "";
        if (CollectionUtils.isNotEmpty(bdcQlList)) {
            BdcQl bdcQl = bdcQlList.get(bdcQlList.size() - 1);
            if (bdcQl instanceof BdcFdcqDO) {
                zl = ((BdcFdcqDO) bdcQl).getZl();
            }
            if (bdcQl instanceof BdcYgDO) {
                zl = ((BdcYgDO) bdcQl).getZl();
            }
        }
        HashMap map = new HashMap();
        map.put("bdcQlList", bdcQlList);
        map.put("bdcdyh", bdcdyh);
        map.put("zl", zl);
        return JSONObject.toJSONString(map);
    }

    /**
     * @param zdzhh 宗地宗海号，宗地代码
     * @return BdcdjbZdjbxxVO 登记簿宗地基本信息VO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取宗地基本信息，包括宗地变化情况
     */
    @GetMapping(value = "/{zdzhh}/bdcdjbZdjbxx")
    public BdcdjbZdjbxxVO queryBdcdjbZdjbxx(@PathVariable(value = "zdzhh") String zdzhh) {

        if (StringUtils.isBlank(zdzhh)) {
            throw new MissingArgumentException("zdzhh");
        }
        zdzhh = StringUtils.deleteWhitespace(zdzhh);

        List<String> tdyt = new ArrayList<>();
        BdcdjbZdjbxxVO bdcdjbZdjbxxVO = new BdcdjbZdjbxxVO();
        BdcBdcdjbZdjbxxDO bdcBdcdjbZdjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZdjbxxByZdzhh(zdzhh);
        /**
         * 36695 【如皋】登记簿宗地用途展示需求
         */
        if(djbShowAllYtFlag){
            tdyt.add(bdcBdcdjbZdjbxxDO.getYt());
            tdyt.add(bdcBdcdjbZdjbxxDO.getZdyt2());
            tdyt.add(bdcBdcdjbZdjbxxDO.getZdyt3());
            tdyt = tdyt.stream().distinct().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
            bdcBdcdjbZdjbxxDO.setYt(String.join(",", tdyt));
        }
        if (null != bdcBdcdjbZdjbxxDO) {
            BeanUtils.copyProperties(bdcBdcdjbZdjbxxDO, bdcdjbZdjbxxVO);
            List<BdcBdcdjbZdjbxxZdbhqkDO> listBdcBdcdjbZdjbxxZdbhqkDO = bdcDjbxxFeignService.listBdcBdcdjbZdjbxxZdbhqk(bdcBdcdjbZdjbxxDO.getBdcdyh());
            bdcdjbZdjbxxVO.setBdcBdcdjbZdjbxxZdbhqkDOList(listBdcBdcdjbZdjbxxZdbhqkDO);
            bdcdjbZdjbxxVO.setBdcdyh(BdcdyhToolUtils.formatBdcdyh(bdcBdcdjbZdjbxxDO.getBdcdyh()));
        }
        return bdcdjbZdjbxxVO;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return BdcdjbZhjbxxVO 登记簿宗海基本信息VO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取宗海基本信息，包含宗海变化情况
     */
    @GetMapping(value = "/{bdcdyh}/bdcdjbZhjbxx")
    public BdcdjbZhjbxxVO queryBdcdjbZhjbxx(@PathVariable(value = "bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("bdcdyh");
        }
        bdcdyh = StringUtils.deleteWhitespace(bdcdyh);

        BdcdjbZhjbxxVO bdcdjbZhjbxxVO = new BdcdjbZhjbxxVO();
        BdcBdcdjbZhjbxxDO bdcdjbZhjbxxDO = bdcDjbxxFeignService.queryBdcBdcdjbZhjbxx(bdcdyh);
        if (null != bdcdjbZhjbxxDO) {
            BeanUtils.copyProperties(bdcdjbZhjbxxDO, bdcdjbZhjbxxVO);
        }
        // 宗海变化情况
        List<BdcBdcdjbZhjbxxZhbhqkDO> listBdcdjbZhjbxxZhbhqkDO = bdcDjbxxFeignService.listBdcBdcdjbZhjbxxZhbhqk(bdcdyh);
        bdcdjbZhjbxxVO.setBdcBdcdjbZhjbxxZhbhqkDOList(listBdcdjbZhjbxxZhbhqkDO);
        // 用海状况
        List<BdcBdcdjbZhjbxxYhzkDO> listBdcdjbZhjbxxYhzkDO = bdcDjbxxFeignService.listBdcBdcdjbZhjbxxYhzk(bdcdyh);
        bdcdjbZhjbxxVO.setBdcBdcdjbZhjbxxYhzkDOList(listBdcdjbZhjbxxYhzkDO);
        // 用海用岛坐标
        List<BdcBdcdjbZhjbxxYhydzbDO> listBdcdjbZhjbxxYhydzbDO = bdcDjbxxFeignService.listBdcBdcdjbZhjbxxYhydzb(bdcdyh);
        bdcdjbZhjbxxVO.setBdcBdcdjbZhjbxxYhydzbDOList(listBdcdjbZhjbxxYhydzbDO);
        // 设置动产单元号
        bdcdjbZhjbxxVO.setBdcdyh(BdcdyhToolUtils.formatBdcdyh(bdcdyh));
        return bdcdjbZhjbxxVO;
    }

    /**
     * @param bdcdyh
     * @return BdcQlQtsxVO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取本数和页数
     */
    @GetMapping(value = "/bsys/{bdcdyh}")
    @ResponseStatus(HttpStatus.OK)
    public BdcQlQtsxVO queryBsYs(@PathVariable(name = "bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("bdcdyh");
        }
        BdcQlDjMlDTO bdcQlDjMlDTO = bdcDjbxxFeignService.indexBdcdyhQlDjMl(StringUtils.substring(bdcdyh, 0, 19), bdcdyh);
        String bs = bdcQlDjMlDTO.getXh();

        BdcQlQtsxVO bdcQlQtsxVO = querBdcQlxxQtsx(bdcdyh);
        bdcQlQtsxVO.setBs(bs);
        return bdcQlQtsxVO;
    }

    @GetMapping(value = "/format/{bdcdyh}")
    @ResponseStatus(HttpStatus.OK)
    public String formatBdcdyh(@PathVariable(name = "bdcdyh") String bdcdyh) {
        return bdcDjbxxFeignService.formatBdcdyh(bdcdyh);
    }

    /**
     * @param zdzhh
     * @return bdcDjbMlVO
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 根据宗地宗海号组织登记簿大页面左侧的目录树
     */
    @GetMapping(value = "/bdcDjbMl/{zdzhh}")
    @ResponseStatus(HttpStatus.OK)
    public BdcDjbMlVO bdcDjbMl(@PathVariable(name = "zdzhh") String zdzhh, @RequestParam(name = "bdcdyh", required = false) String bdcdyh) {
        if (StringUtils.isBlank(zdzhh)) {
            throw new AppException("参数错误！");
        }
        Date start = new Date();
        BdcDjbMlVO bdcDjbMlVO = null;
        if (StringUtils.isNotBlank(zdzhh)) {
            bdcDjbMlVO = new BdcDjbMlVO();
            /**
             * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
             * @description 登记簿基本信息对象组织
             */
            bdcDjbMlVO.setDjbfmUrl(BdcDjbQlMlEnum.BdcDjbJbxxUrlEnum.BDC_DJB_FM_URL.getName() + zdzhh);
            if (BdcdyhToolUtils.checkXnbdcdyh(bdcdyh)) {
                // 虚拟宗需要定位到具体的单元号
                bdcDjbMlVO.setQlmlUrl(BdcDjbQlMlEnum.BdcDjbJbxxUrlEnum.BDC_DJB_QLML_URL.getName() + zdzhh + "&bdcdyh=" + bdcdyh);
            } else {
                bdcDjbMlVO.setQlmlUrl(BdcDjbQlMlEnum.BdcDjbJbxxUrlEnum.BDC_DJB_QLML_URL.getName() + zdzhh);
            }
            if (BdcdyhToolUtils.ifZhh(zdzhh)) {
                bdcDjbMlVO.setZdzhxxUrl(BdcDjbQlMlEnum.BdcDjbJbxxUrlEnum.BDC_DJB_ZH_URL.getName() + BdcdyhToolUtils.convertToW(zdzhh));
            } else {
                bdcDjbMlVO.setZdzhxxUrl(BdcDjbQlMlEnum.BdcDjbJbxxUrlEnum.BDC_DJB_ZD_URL.getName() + zdzhh);
            }

            /**
             * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
             * @description 组织所有不动产单元的权利目录树，onlyQlfm = true 表示只生成权利封面的地址，具体的权利信息不组织
             */
            Boolean onlyQlfm = true;
            List bdcDjbQlMlList = bdcDjbxxFeignService.generateDjbMls(zdzhh, bdcdyh, onlyQlfm);
            bdcDjbMlVO.setBdcDjbQlMlList(bdcDjbQlMlList);
        }
        long end = System.currentTimeMillis() - start.getTime();
        LOGGER.info("查询登记簿目录，执行所需时间：{},执行数据量：{}", end, bdcDjbMlVO == null ? 0 : bdcDjbMlVO.getBdcDjbQlMlList().size());
        return bdcDjbMlVO;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description
     */
    @GetMapping(value = "/bdcDjbQlMl/{bdcdyh}")
    @ResponseStatus(HttpStatus.OK)
    public BdcDjbQlMlVO bdcDjbQlMlVO(@PathVariable(name = "bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new AppException("参数错误！");
        }
        Date start = new Date();
        BdcDjbQlMlVO bdcDjbQlMlVO = new BdcDjbQlMlVO();
        if (StringUtils.isNotBlank(bdcdyh)) {
            //组织所有不动产单元的权利目录树，onlyQlfm = true 表示只生成权利封面的地址，具体的权利信息不组织
            bdcDjbQlMlVO = bdcDjbxxFeignService.generateDyhQlMl(bdcdyh, false);
        }
        long end = System.currentTimeMillis() - start.getTime();
        LOGGER.info("查询登记簿不动产单元权利目录，执行所需时间：{},执行数据量：{}", end, bdcDjbQlMlVO == null ? 0 : bdcDjbQlMlVO.getBdcQlList().size());
        return bdcDjbQlMlVO;
    }

    /**
     * 根据不动产单元号获取宗地图
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 21:03 2020/8/19
     */
    @GetMapping("/zdt")
    @ResponseStatus(HttpStatus.OK)
    public String getZdt(@RequestParam(name = "bdcdyh") String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcdyh,"");
            if (null != zdtResponseDTO) {
                return zdtResponseDTO.getBase64();
            }
        }
        return "";
    }

    /**
     * 根据合同编号获取宗地图
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 21:03 2020/8/19
     */
    @GetMapping("/zdtByHtbh")
    @ResponseStatus(HttpStatus.OK)
    public String getZdtByHtbh(@RequestParam(name = "htbh") String htbh) {
        if (StringUtils.isNotBlank(htbh)) {
            ZdtResponseDTO zdtResponseDTO = zdFeignService.queryCbzdsytByCbhtbh(htbh, "");
            if (null != zdtResponseDTO) {
                return zdtResponseDTO.getBase64();
            }
        }
        return "";
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 修正流程查原流程的工作流实例id
     * @date : 2022/2/18 11:07
     */
    @GetMapping("/xzlc")
    public Object queryYlcslid(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return "";
        }
        String ygzlslid = "";
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDOList.get(0).getXmid(), "", CommonConstantUtils.SF_F_DM);
            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(new BdcXmQO(yxmid));
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    ygzlslid = bdcXmDOList.get(0).getGzlslid();
                }
            }
        }
        return ygzlslid;
    }
}
