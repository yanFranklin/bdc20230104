package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.SlymGwcDTO;
import cn.gtmap.realestate.common.core.enums.BdcZsslEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcGwcDeleteQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlXmVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/14
 * @description 购物车相关操作
 */
@Controller
@RequestMapping("/gwc")
public class BdcdyGwcController extends BaseController {
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    //转移换证工作流定义id
    @Value("#{'${zyhz.gzldyid:}'.split(',')}")
    private List<String> zyhzGzldyidList;
    //转移换证-登记小类
    @Value("#{'${zyhz.djxlList:}'.split(',')}")
    private List<String> zyhzDjxlList;

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取受理项目集合
     */
    @ResponseBody
    @GetMapping("/list/bdcslxm")
    public List<BdcSlXmDO> listBdcSlXm(String jbxxid) {
        return bdcSlXmFeignService.listBdcSlXmByJbxxid(jbxxid);
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据不动产单元号排除重复的值
     */
    @ResponseBody
    @GetMapping("/list/bdcslxm/removeduplicate")
    public List<BdcSlXmVO> listBdcSlXmRemoveDuplicate(String jbxxid, Integer sfzlcsh) {
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setJbxxid(jbxxid);
        if (sfzlcsh != null) {
            //存在增量初始化数据，查询增量初始化
            bdcSlXmQO.setSfzlcsh(sfzlcsh);
        }
        List<BdcSlXmVO> bdcSlXmDOList =bdcSlXmFeignService.listGwcSelectDataWithLsgx(bdcSlXmQO);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList) && null != bdcSlXmDOList.get(0) &&
                !bdcSlXmFeignService.bdcdyhIsRepeat(bdcSlXmDOList.get(0).getXmid())) {
            return bdcSlXmDOList;
        }
        // 先进行对不动产单元号的去重，在对xmid进行排序。
        bdcSlXmDOList = bdcSlXmDOList.stream().filter(bdcslxmDO -> Objects.nonNull(bdcslxmDO.getBdcdyh()) && StringUtils.isNotBlank(bdcslxmDO.getBdcdyh())).collect(Collectors.toList());
        Set<BdcSlXmVO> set = new TreeSet<>(Comparator.comparing(BdcSlXmDO::getBdcdyh));
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            set.addAll(bdcSlXmDOList);
        }
        List<BdcSlXmVO> listSortbyXmid = new ArrayList<>(set);
        listSortbyXmid.sort((o1,o2)->o1.getXmid().compareTo(o2.getXmid()));
        return listSortbyXmid;
    }

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 单条删除项目
     */
    @ResponseBody
    @DeleteMapping("/bdcslxm")
    public void deleteBdcslxm(String xmid) {
        bdcSlXmFeignService.deleteBdcSlXmByXmid(xmid);
    }

    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 分页已选业务信息
     */
    @ResponseBody
    @GetMapping("/listBdcdyhByPageJson")
    public Object listBdcdyhByPageJson(Pageable pageable, BdcSlXmQO bdcSlXmQO, String gzldyid) {
        String slXmQO = JSON.toJSONString(bdcSlXmQO);
        pageable = delPageRequest(pageable);
        return bdcSlXmFeignService.queryYxBdcdyDTOByPage(pageable, slXmQO, gzldyid, bdcSlXmQO.getJbxxid());
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 已选业务信息(不分页)
     */
    @ResponseBody
    @GetMapping("/listYxBdcdyDTO")
    public Object listYxBdcdyDTO(BdcSlXmQO bdcSlXmQO, String gzldyid) {
        String slXmQO = JSON.toJSONString(bdcSlXmQO);
        return bdcSlXmFeignService.queryYxBdcdyDTO(slXmQO, gzldyid, bdcSlXmQO.getJbxxid());
    }

    /**
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 根据xmid删除已选项目
     */
    @ResponseBody
    @RequestMapping(value = "/deleteYxxm")
    public void deleteYxxm(BdcGwcDeleteQO bdcGwcDeleteQO) {
        bdcGwcDeleteQO.setSystemVersion(systemVersion);
        bdcSlXmFeignService.deleteYxxm(bdcGwcDeleteQO);
    }

    /**
     * @param json 集合
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新受理项目
     */
    @ResponseBody
    @PatchMapping("/updateBdcSlXm")
    public Integer updateBdcSlXm(@RequestBody String json) {
        return bdcSlXmFeignService.updateBdcSlXm(JSONObject.parseObject(json, BdcSlXmDO.class));
    }

    /**
     * @param json 集合
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新受理项目关系
     */
    @ResponseBody
    @PatchMapping("/updateBdcSlXmLsgx")
    public Integer updateBdcSlXmLsgx(@RequestBody String json) {
        return bdcSlXmLsgxFeignService.updateBdcSlXmLsgx(JSONObject.parseObject(json, BdcSlXmLsgxDO.class));
    }

    /**
     * @param gxid 关系id
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过关系id删除项目关系表
     */
    @ResponseBody
    @DeleteMapping(value = "/deleteBdcSlXmLsgx")
    public void deleteBdcSlXmLsgx(@RequestParam("gxid") String gxid) {
        bdcSlXmLsgxFeignService.deleteBdcSlXmLsgxByGxid(gxid);
    }

    /**
     * @param json 前台传输受理项目集合Json
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新受理项目
     */
    @ResponseBody
    @PatchMapping(value = "/slxm/list")
    public Integer updateBdcSlXmList(@RequestBody String json) {
        return bdcSlXmFeignService.updateBdcSlXm(json);
    }

    /**
     * @param zsxh
     * @param jbxxid 基本信息id
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理项目证书序号
     */
    @ResponseBody
    @PatchMapping(value = "/batchUpdateBdcSlXmZsxh")
    public Integer batchUpdateBdcSlXmZsxh(String zsxh, String jbxxid, String xmids) {
        return bdcSlXmFeignService.batchUpdateBdcSlXmZsxh(zsxh, jbxxid, Arrays.asList(xmids.split(CommonConstantUtils.ZF_YW_DH)));
    }

    /**
     * @param xmids  当前需注销项目
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理已选需注销项目(用于部分注销)
     */
    @ResponseBody
    @PatchMapping(value = "/dealYxzxxm")
    public void dealYxzxxm(String jbxxid, String xmids) {
        if (StringUtils.isBlank(xmids) || StringUtils.isBlank(jbxxid)) {
            throw new MissingArgumentException("xmids,jbxxid");
        }
        String[] xmidsArr = xmids.split(CommonConstantUtils.ZF_YW_DH);
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(jbxxid);
        if (bdcSlXmDOList.size() <= xmidsArr.length) {
            throw new AppException("当前数据不支持部分注销，不予处理");
        }
        for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
            if (!ArrayUtils.contains(xmidsArr, bdcSlXmDO.getXmid())) {
                for (String zxxmid : xmidsArr) {
                    //将要注销的项目历史关系转为其他项目的外联证书
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgxByXmid(zxxmid);
                    if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                        for (BdcSlXmLsgxDO bdcSlXmLsgxDO : bdcSlXmLsgxDOList) {
                            bdcSlXmLsgxDO.setXmid(bdcSlXmDO.getXmid());
                            bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                            bdcSlXmLsgxFeignService.updateBdcSlXmLsgx(bdcSlXmLsgxDO);
                            //删除要注销的受理项目
                            bdcSlXmFeignService.deleteBdcSlXmByXmid(zxxmid);
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * @param bdcSlXmQO 受理项目查询参数
     * @param gzldyid   工作流定义id
     * @param sfazfz    是否按幢分组
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据登记小类分组查询已选业务信息(不分页)
     */
    @ResponseBody
    @GetMapping("/listYxBdcdyDTOGroupByDjxl")
    public Object listYxBdcdyDTOGroupByDjx(BdcSlXmQO bdcSlXmQO, String gzldyid, String sfazfz) {
        String slXmQO = JSON.toJSONString(bdcSlXmQO);
        return bdcSlXmFeignService.queryYxBdcdyDTOGroupByDjxl(slXmQO, gzldyid, bdcSlXmQO.getJbxxid(), sfazfz);
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取勾选放到购物车的数据，主要需要获取yxmid
     */
    @ResponseBody
    @GetMapping("/listGwcData")
    public Object listGwcData(String jbxxid) {
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setJbxxid(jbxxid);
        String slXmQO = JSON.toJSONString(bdcSlXmQO);
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = bdcSlXmFeignService.listGwcData(slXmQO);
        if (CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            return bdcSlYwxxDTOList;
        } else {
            return Collections.emptyList();
        }
    }

    @ResponseBody
    @GetMapping("/zyhz")
    public Object listZyhzData() {
        HashMap<String,List<String>> zyhzMap = new HashMap<>(2);
        zyhzMap.put("zyhzGzldyidList",zyhzGzldyidList);
        zyhzMap.put("zyhzDjxlList",zyhzDjxlList);
        return zyhzMap;
    }

    @ResponseBody
    @GetMapping("/slym")
    public Object querySlymGwcData(BdcXmQO bdcXmQO) {
        if(StringUtils.isAnyBlank(bdcXmQO.getGzlslid())) {
            throw new AppException("受理页面查询购物车信息缺失工作流实例id");
        }
        List<SlymGwcDTO> slymGwcDTOS = new ArrayList<>();
        SlymGwcDTO slymGwcDTO = new SlymGwcDTO();
        BdcXmQO xmQO = new BdcXmQO();
        xmQO.setGzlslid(bdcXmQO.getGzlslid());
        List<BdcXmDO> bfXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bfXmList)) {
            //页面展示，多查询条件的部分项目信息，证书数量判断根据全部项目判断
            slymGwcDTO.setBdcXmDOList(bfXmList);
            List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(bdcXmQO.getGzlslid());
            if (CollectionUtils.isNotEmpty(bdcCshFwkgSlDOList)) {
                slymGwcDTO.setBdcCshFwkgSlDOList(bdcCshFwkgSlDOList);
                //先判断是否生成证书
                if (Objects.equals(CommonConstantUtils.SF_F_DM, bdcCshFwkgSlDOList.get(0).getSfsczs())) {
                    slymGwcDTO.setZssl(BdcZsslEnum.ZSSL_NOZS.getZssl());
                    slymGwcDTOS.add(slymGwcDTO);
                    return slymGwcDTOS;
                } else {
                    List<BdcCshFwkgSlDO> sortBdcCshFwkgSlList = new ArrayList<>();
                    //登记小类分组结果
                    Map<String, List<BdcCshFwkgSlDO>> djxlXmMap = new HashMap<>();
                    //按照登记小类分组
                    djxlXmMap = bdcCshFwkgSlDOList.stream().collect(Collectors.groupingBy(BdcCshFwkgSlDO::getDjxl));
                    if (MapUtils.isNotEmpty(djxlXmMap)) {
                        //整理分组后的数据
                        for (Map.Entry<String, List<BdcCshFwkgSlDO>> entry : djxlXmMap.entrySet()) {
                            List<BdcCshFwkgSlDO> djxlXmList = entry.getValue();
                            djxlXmList.sort(Comparator.comparing(BdcCshFwkgSlDO::getId));
                            if (CollectionUtils.isNotEmpty(djxlXmList)) {
                                //取分组后各个list中第一条,用于后续排序
                                sortBdcCshFwkgSlList.add(djxlXmList.get(0));
                            }
                        }
                    }
                    if (MapUtils.isNotEmpty(djxlXmMap)) {
                        //根据xmid排序
                        sortBdcCshFwkgSlList.sort(Comparator.comparing(BdcCshFwkgSlDO::getId));
                        if (CollectionUtils.isNotEmpty(sortBdcCshFwkgSlList)) {
                            for (BdcCshFwkgSlDO bdcCshFwkgSlDO : sortBdcCshFwkgSlList) {
                                SlymGwcDTO groupGwc = new SlymGwcDTO();
                                List<BdcCshFwkgSlDO> groupFwkgList = new ArrayList<>();
                                if (MapUtils.isNotEmpty(djxlXmMap)) {
                                    groupFwkgList = djxlXmMap.get(bdcCshFwkgSlDO.getDjxl());
                                    // 证书序号为空生成多本证
                                    // 证书序号去重后数量为1 生成一本证， 否则 为任意组合
                                    List<BdcCshFwkgSlDO> nullZsxhFwkgList = groupFwkgList.stream().filter(cshFwkgSlDO -> Objects.isNull(cshFwkgSlDO.getZsxh())).collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(nullZsxhFwkgList) && CollectionUtils.size(nullZsxhFwkgList) == CollectionUtils.size(groupFwkgList)) {
                                        groupGwc.setZssl(BdcZsslEnum.ZSSL_DBZ.getZssl());
                                    } else {
                                        List<BdcCshFwkgSlDO> ybzList = groupFwkgList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcCshFwkgSlDO::getZsxh))), ArrayList::new));
                                        if (CollectionUtils.isNotEmpty(ybzList) && Objects.equals(1, CollectionUtils.size(ybzList))) {
                                            groupGwc.setZssl(BdcZsslEnum.ZSSL_YBZ.getZssl());
                                        } else {
                                            groupGwc.setZssl(BdcZsslEnum.ZSSL_RYZH.getZssl());
                                        }
                                    }
                                }
                                if (CollectionUtils.isNotEmpty(groupFwkgList)) {
                                    groupGwc.setBdcCshFwkgSlDOList(groupFwkgList);
                                    List<String> xmidList = groupFwkgList.stream().map(BdcCshFwkgSlDO::getId).collect(Collectors.toList());
                                    List<BdcXmDO> groupXmList = new ArrayList<>();
                                    for (BdcXmDO bdcXmDO : bfXmList) {
                                        if (xmidList.contains(bdcXmDO.getXmid())) {
                                            groupXmList.add(bdcXmDO);
                                        }
                                    }
                                    groupGwc.setBdcXmDOList(groupXmList);
                                    slymGwcDTOS.add(groupGwc);
                                }
                            }
                        }
                    }
                }
            }
        }
        return slymGwcDTOS;
    }

    /**
     * @param json 需要更新的内容
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新初始化房屋开关实例表数据
     * @date : 2020/8/5 14:33
     */
    @ResponseBody
    @PatchMapping("/zsxh/{gzlslid}")
    public Object saveZsxh(@RequestBody String json, @PathVariable(value = "gzlslid") String gzlslid){
        int count = 0;
        JSONArray jsonArray = JSONObject.parseArray(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            BdcCshFwkgSlDO updateCshkg = JSONObject.parseObject(obj.toJSONString(), BdcCshFwkgSlDO.class);
                String xmid = updateCshkg.getId();
                if(StringUtils.isNotBlank(xmid)) {
                    //任意组合根据xmid更新
                    BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(xmid);
                    bdcCshFwkgSlDO.setSfsczs(updateCshkg.getSfsczs());
                    bdcCshFwkgSlDO.setZsxh(updateCshkg.getZsxh());
                    count += bdcXmFeignService.updateCshFwkgSl(bdcCshFwkgSlDO);
                } else {
                    //不传xmid  全部更新
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setGzlslid(gzlslid);
                    List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
                    if(CollectionUtils.isNotEmpty(bdcCshFwkgSlDOList)) {
                        for(BdcCshFwkgSlDO bdcCshFwkgSlDO : bdcCshFwkgSlDOList) {
                            bdcCshFwkgSlDO.setSfsczs(updateCshkg.getSfsczs());
                            bdcCshFwkgSlDO.setZsxh(updateCshkg.getZsxh());
                            count += bdcXmFeignService.updateCshFwkgSl(bdcCshFwkgSlDO);
                        }
                    }
                }
        }
        return count;
    }

    /**
     * 更新受理项目中的 证书类型与是否发证
     * @param jbxxid 基本信息ID
     * @param bdcSlXmDOList  不动产受理项目数据集合
     */
    @ResponseBody
    @PutMapping("/updateSlxmFzzt/{jbxxid}")
    public List<String> updateSlxmFzzt(@PathVariable(name = "jbxxid") String jbxxid,
                                       @RequestBody List<BdcSlXmDO> bdcSlXmDOList, @RequestParam(name = "gzldyid") String gzldyid) {
        return bdcSlXmFeignService.updateSlxmFzztPl(jbxxid, bdcSlXmDOList, gzldyid);

    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询所有的规划用途
     * @date : 2022/9/13 10:50
     */
    @ResponseBody
    @GetMapping("/ghyt")
    public List<String> listGwcGhyt(String jbxxid) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new AppException("查询购物车规划用途缺失jbxxid");
        }
        return bdcSlXmFeignService.listGwcGhytByJbxxid(jbxxid);
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询受理项目数据的总面积
     * @date : 2022/9/13 11:39
     */
    @ResponseBody
    @GetMapping("/jzmj")
    public Object querGwcJzmj(String jbxxid, String djxl) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new AppException("计算购物车总面积缺失jbxxid");
        }
        return bdcSlXmFeignService.queryGwcJzmjByJbxxid(jbxxid, djxl);
    }


}
