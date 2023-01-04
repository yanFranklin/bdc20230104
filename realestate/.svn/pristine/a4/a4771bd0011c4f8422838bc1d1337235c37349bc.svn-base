package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.service.*;
import cn.gtmap.realestate.init.util.DataParseUtil;
import cn.gtmap.realestate.init.web.rest.BdcPrintDataRestController;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/9/11.
 * @description
 */
@Service
public class BdcPrintDataServiceImpl implements BdcPrintDataService{
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcPrintDataRestController.class);
    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private FwHsFeignService fwHsFeignService;
    @Autowired
    private BdcdyFeignService bdcdyFeignService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcXmFbService bdcXmFbService;
    @Autowired
    private DozerBeanMapper initDozerMapper;
    @Autowired
    private BdcZdCache bdcZdCache;
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Override
    public List<BdcDysjDTO> queryFdcqDzFwqdDysj(List<String> xmids, String gzlslid, String zsid, boolean hfwqd) {
        // 返回结果
        List<BdcDysjDTO> list = new ArrayList<>();
        //项目id集合
        List<String> xmidList = getXmidList(xmids,gzlslid,zsid);
        // 组织返回信息
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSONObject.toJSONString(getDysj(zsid)));
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(getFwxx(xmidList,hfwqd)));
        //xml模板
        bdcDysjDTO.setDyzd(getDyzd());
        list.add(bdcDysjDTO);
        return list;
    }
    /**
    * @author chenchunxue 2020/9/11
    * @param xmids
    * @param zsid
    * @param gzlslid
    * @return java.util.List<java.lang.String>
    * @description 根据xmids gzlslid zsid 查询项目id集合
    */
    private List<String> getXmidList(List<String> xmids,String gzlslid,String zsid){
        //项目id集合
        List<String> xmidList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(xmids)) {
            xmidList = xmids;
        } else if (StringUtils.isNotBlank(zsid)) {
            xmidList = new ArrayList<>();
            BdcXmZsGxDO bdcXmZsGxDO = new BdcXmZsGxDO();
            bdcXmZsGxDO.setZsid(zsid);
            List<BdcXmZsGxDO> listXmZsGx = entityMapper.selectByObj(bdcXmZsGxDO);
            if (CollectionUtils.isNotEmpty(listXmZsGx)) {
                for (BdcXmZsGxDO xmZsGxDO : listXmZsGx) {
                    xmidList.add(xmZsGxDO.getXmid());
                }
            }
        } else {
            List<BdcXmDTO> listBdcXm = bdcXmService.listXmBfxx(gzlslid, null);
            if (CollectionUtils.isNotEmpty(listBdcXm)) {
                for (BdcXmDTO bdcXmDTO : listBdcXm) {
                    xmidList.add(bdcXmDTO.getXmid());
                }
            }
        }
        return xmidList;
    }

    /**
    * @author chenchunxue 2020/9/11
    * @param xmidList
    * @param hfwqd
    * @return com.google.common.collect.Multimap<java.lang.String,java.util.List>
    * @description 查询房屋信息
    */
    private Multimap<String, List> getFwxx(List<String> xmidList,boolean hfwqd){
        Multimap<String, List> zbMap = ArrayListMultimap.create();
        // 房屋信息
        List<Map<String, String>> listMap = new ArrayList<>();
        // 不动产单元信息，用于项目内多幢查询
        Set<String> bdcdyhs = new HashSet<>();
        // 权利id ，用于项目内多幢查询
        Set<String> qlids = new HashSet<>();
        // 权利信息,用于房地产权查询
        List<BdcFdcqDO> bdcFdcqDOList = new ArrayList();
        // 标识是不是项目内多幢信息
        boolean isXmndz = false;
        //权籍管理代码
        String qjgldm ="";
        //循环查询数据
        for (String xmid : xmidList) {
            if (StringUtils.isNotBlank(xmid)) {
                BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(xmid);
                //产权
                if (bdcXmDO != null && ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, bdcXmDO.getQllx())) {
                    BdcQl bdcQl = bdcQllxService.queryQllxDO(new BdcFdcqDO(), xmid);
                    //多幢的查询
                    if (bdcQl != null) {
                        if (CommonConstantUtils.FWLX_DUOZH.equals(((BdcFdcqDO) bdcQl).getBdcdyfwlx())) {
                            bdcdyhs.add(bdcXmDO.getBdcdyh());
                            qlids.add(bdcQl.getQlid());
                            isXmndz = true;
                        } else {
                            bdcFdcqDOList.add((BdcFdcqDO) bdcQl);
                        }
                    }
                }
            }
        }
        // 生成打印数据
        if(StringUtils.isNotBlank(xmidList.get(0))){
            BdcXmFbDO bdcXmFbDO =entityMapper.selectByPrimaryKey(BdcXmFbDO.class,xmidList.get(0));
            if(bdcXmFbDO != null){
                qjgldm =bdcXmFbDO.getQjgldm();
            }
        }
        if (isXmndz) {

            // 项目内多幢信息
            this.generateXmxx(listMap, bdcdyhs,qlids,hfwqd,qjgldm);
        } else {
            // 房地产权信息
            this.generateFdcq(listMap, bdcFdcqDOList,qjgldm);
        }

        try{
            //排序 正序
            if (CollectionUtils.isNotEmpty(listMap) && listMap.size() > 1) {
                Collections.sort(listMap , new Comparator<Map<String, String>>() {
                    @Override
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        String o1Value = o1.get("zh");
                        String o2Value = o2.get("zh");
                        if(StringUtils.isNotBlank(o1Value) && StringUtils.isNotBlank(o2Value)){
                            return o1Value.compareTo(o2Value);
                        }else{
                            return -1;
                        }
                    }
                });
            }
        }catch (Exception e){
            LOGGER.error("幢号错误，无法按照幢号排序：",e);
        }
        // 排序完重新生成xh
        for(int i=0;i<listMap.size();i++){
            listMap.get(i).put("xh",i+1+"");
        }
        zbMap.put("fwxx",listMap);
        return zbMap;
    }
    /**
     * @param listMap       打印信息
     * @param bdcFdcqDOList 房地产权信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成房地产权打印信息
     */
    private void generateFdcq(List<Map<String, String>> listMap, List<BdcFdcqDO> bdcFdcqDOList,String qjgldm) {
        if (CollectionUtils.isNotEmpty(bdcFdcqDOList)) {
            // 按照幢号排序
            //实现一个比较器
            try{
                Collections.sort(bdcFdcqDOList,(o1,o2)->Integer.compare(DataParseUtil.stringParseIntDealNull(o1.getZh())
                        , DataParseUtil.stringParseIntDealNull(o2.getZh())));
            }catch (Exception e){
                LOGGER.error("幢号错误，无法按照幢号排序：",e);
            }
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();

            int i = 0;
            for (BdcFdcqDO bdcFdcqDO : bdcFdcqDOList) {
                Map<String, String> map = new HashMap<>();
                map.put("zdzhmj","");
                map.put("zdlx","");

                String xmid = bdcFdcqDO.getXmid();
                BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                bdcXmFbQO.setXmid(xmid);
                List<BdcXmFbDO> listFbDO = bdcXmFbService.listBdcXmFb(bdcXmFbQO);

                BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(xmid);
                map.put("zdzhmj",bdcXmDO.getZdzhmj()+"");

                if(CollectionUtils.isNotEmpty(listFbDO)){
                    Integer zdlx = listFbDO.get(0).getZdsylx();
                    String zdsylxmc = StringToolUtils.convertBeanPropertyValueOfZd(
                            zdlx, zdMap.get("zdsylx"));
                    map.put("zdlx",zdsylxmc);
                }


                map.put("xh", String.valueOf(i + 1));
                map.put("zh", bdcFdcqDO.getZh());
                if (bdcFdcqDO.getGhyt() != null) {
                    map.put("ghyt", bdcZdCache.getFeildValue(BdcZdFwytDO.class.getSimpleName(), bdcFdcqDO.getGhyt(), BdcZdFwytDO.class));
                }
                if (bdcFdcqDO.getJzmj() != null) {
                    map.put("jzmj", String.valueOf(bdcFdcqDO.getJzmj()));
                }
                if (bdcFdcqDO.getZcs() != null) {
                    map.put("zcs", String.valueOf(bdcFdcqDO.getZcs()));
                }

                if(StringUtils.isNotBlank(bdcFdcqDO.getBdcdyh())){
                    FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(bdcFdcqDO.getBdcdyh(),qjgldm);
                    if(fwHsDO != null){
                        map.put("xmmc",fwHsDO.getZl());
                    }
                }
                listMap.add(map);
                i++;
            }
        }
    }

    /**
     * @param
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成项目内多幢打印数据
     */
    private void generateXmxx(List<Map<String, String>> listMap, Set<String> bdcdyhs,Set<String> qlids,boolean hfwqd,String qjgldm) {
        List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmList = new ArrayList<>();
        //  先判断bdc_fdcq_fdcqxm表是否有数据 有数据则取bdc_fdcq_fdcqxm表  没有则取权籍数据
        // 房屋清单（户）的直接查权籍
        if(!hfwqd){
            for(String qlid:qlids){
                Example example = new Example(BdcFdcqFdcqxmDO.class);
                example.createCriteria().andEqualTo("qlid",qlid);
                List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxmDO = entityMapper.selectByExample(example);
                if(CollectionUtils.isNotEmpty(bdcFdcqFdcqxmDO)){
                    bdcFdcqFdcqxmList.addAll(bdcFdcqFdcqxmDO);
                }
            }
        }
        if(CollectionUtils.isEmpty(bdcFdcqFdcqxmList)){
            //不动产单元循环
            for (String bdcdyh : bdcdyhs) {
                BdcdyResponseDTO bdcdyResponseDTO = bdcdyFeignService.queryBdcdy(bdcdyh, CommonConstantUtils.FWLX_DUOZH.toString(),qjgldm);
                if (bdcdyResponseDTO != null && CollectionUtils.isNotEmpty(bdcdyResponseDTO.getLjzList())) {
                    for (FwLjzDO fwLjz : bdcdyResponseDTO.getLjzList()) {
                        BdcFdcqFdcqxmDO bdcFdcqFdcqxm = new BdcFdcqFdcqxmDO();
                        initDozerMapper.map(bdcdyResponseDTO, bdcFdcqFdcqxm);
                        initDozerMapper.map(fwLjz, bdcFdcqFdcqxm);
                        if (StringUtils.isNotBlank(fwLjz.getFwDcbIndex())) {
                            List<FwHsDO> listFwhs = fwHsFeignService.listFwhsByFwDcbIndex(fwLjz.getFwDcbIndex(),qjgldm);
                            if (CollectionUtils.isNotEmpty(listFwhs)) {
                                for (FwHsDO fwHsDO : listFwhs) {
                                    BdcFdcqFdcqxmDO fwhsXm = new BdcFdcqFdcqxmDO();
                                    BeanUtils.copyProperties(bdcFdcqFdcqxm, fwhsXm);
                                    initDozerMapper.map(fwHsDO, fwhsXm);
                                    fwhsXm.setQlid(qlids.iterator().next());
                                    bdcFdcqFdcqxmList.add(fwhsXm);
                                }
                            }
                        }
                    }
                }
            }
        }
        //整理的数据
        if (CollectionUtils.isNotEmpty(bdcFdcqFdcqxmList)) {
            // 按照幢号排序
            //实现一个比较器
            try{
                Collections.sort(bdcFdcqFdcqxmList,(o1,o2)->Integer.compare(DataParseUtil.stringParseIntDealNull(o1.getZh())
                        , DataParseUtil.stringParseIntDealNull(o2.getZh())));
            }catch (Exception e){
                LOGGER.error("幢号错误，无法按照幢号排序：",e);
            }
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            for (int i = 0; i < bdcFdcqFdcqxmList.size(); i++) {
                BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO = bdcFdcqFdcqxmList.get(i);

                Map<String, String> map = new HashMap<>();
                map.put("zdlx","");
                map.put("zdzhmj","");

                String qlid = bdcFdcqFdcqxmDO.getQlid();
                Example example = new Example(BdcFdcqDO.class);
                example.createCriteria().andEqualTo("qlid",qlid);
                List<BdcFdcqDO> list = entityMapper.selectByExample(example);

                if(CollectionUtils.isNotEmpty(list)){
                    String xmid = list.get(0).getXmid();
                    BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                    bdcXmFbQO.setXmid(xmid);
                    List<BdcXmFbDO> listFbDO = bdcXmFbService.listBdcXmFb(bdcXmFbQO);

                    BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(xmid);
                    map.put("zdzhmj",bdcXmDO.getZdzhmj()+"");

                    if(CollectionUtils.isNotEmpty(listFbDO)){
                        Integer zdlx = listFbDO.get(0).getZdsylx();
                        String zdsylxmc = StringToolUtils.convertBeanPropertyValueOfZd(
                                zdlx, zdMap.get("zdsylx"));
                        map.put("zdlx",zdsylxmc);
                    }
                }

                map.put("xh", String.valueOf(i + 1));
                map.put("xmmc", bdcFdcqFdcqxmDO.getXmmc());
                map.put("zh", bdcFdcqFdcqxmDO.getZh());
                if (bdcFdcqFdcqxmDO.getGhyt() != null) {
                    map.put("ghyt", bdcZdCache.getFeildValue(BdcZdFwytDO.class.getSimpleName(), bdcFdcqFdcqxmDO.getGhyt(), BdcZdFwytDO.class));
                }
                if (bdcFdcqFdcqxmDO.getJzmj() != null) {
                    map.put("jzmj", String.valueOf(bdcFdcqFdcqxmDO.getJzmj()));
                }
                if (bdcFdcqFdcqxmDO.getZcs() != null) {
                    map.put("zcs", String.valueOf(bdcFdcqFdcqxmDO.getZcs()));
                }
                listMap.add(map);
            }
        }
    }
    private Map getDysj(String zsid){
        Map<String, Object> dysjMap = new HashMap();
        //证书id不为空的话查询证号
        if (StringUtils.isNotBlank(zsid)) {
            BdcZsDO bdcZsDO = bdcZsService.queryBdcZsById(zsid);
            if (bdcZsDO != null && StringUtils.isNotBlank(bdcZsDO.getBdcqzh())) {
                dysjMap.put("bdcqzh", bdcZsDO.getBdcqzh());
            }
        }
        return dysjMap;
    }
    private String getDyzd(){
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<fetchdatas>\n" +
                "  <page>\n" +
                "    <datas>\n" +
                "      <data name=\"bdcqzh\" type=\"String\">$bdcqzh</data>\n" +
                "        </datas>\n" +
                "    <detail ID=\"fwxx\">\n" +
                "      <row ID=\"${FZID}\">\n" +
                "        <data name=\"xh\" type=\"String\">$xh</data>\n" +
                "        <data name=\"xmmc\" type=\"String\">$xmmc</data>\n" +
                "        <data name=\"zh\" type=\"String\">$zh</data>\n" +
                "        <data name=\"ghyt\" type=\"String\">$ghyt</data>\n" +
                "        <data name=\"jzmj\" type=\"String\">$jzmj</data>\n" +
                "        <data name=\"zdzhmj\" type=\"String\">$zdzhmj</data>\n" +
                "        <data name=\"zdlx\" type=\"String\">$zdlx</data>\n" +
                "         <data name=\"zcs\" type=\"String\">$zcs</data>\n" +
                "      </row>\n" +
                "    </detail>\n" +
                "  </page>\n" +
                "</fetchdatas>";
    }
}
