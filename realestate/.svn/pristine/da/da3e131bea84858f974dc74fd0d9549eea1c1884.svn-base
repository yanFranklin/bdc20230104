package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcNslxdService;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.StringToolUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/11/8.
 * @description 纳税联系单接口服务实现
 */
@Service
public class BdcNslxdServiceImpl implements BdcNslxdService {

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;
    /**
     * 日期格式
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * @param processInsId
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: processInsId 工作流实例id
     * @return: List<Map < String, String>> 纳税联系单数据
     * @description 通过工作流实例id获取纳税联系单表单数据，返回值为List类型
     */
    @Override
    public List<Map<String, String>> getNslxdData(String processInsId) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        // 根据工作流程实例id查询项目信息
        final List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            throw new NullPointerException("未获取到不动产项目信息。");
        }
        // 获取不动产房屋用途字典项
        List<Map> zdList = bdcZdFeignService.listBdcZd().get("fwyt");
        if(CollectionUtils.isEmpty(zdList)){
            throw new NullPointerException("获取字典项服务失败。");
        }
        // 通过证书组合分类项目信息
        Map<String,List<BdcXmDO>> zsMap = this.getZsXmMap(bdcXmDOList);

        //获取不动产权利，合同交易时间等信息。
        List<Map<String,String>> nslxdDataList = new ArrayList<>();
        for(Map.Entry<String,List<BdcXmDO>> entry : zsMap.entrySet()){
            List<BdcXmDO> list = entry.getValue();
            if(CollectionUtils.isEmpty(list)){
                continue;
            }
            //对不动产项目进行排序，按照定着物用途 主房》阁楼》车库
            this.sortBdcXmListByDzwyt(list, zdList);

            String htjysj = this.getHtjysj(list.get(0).getXmid());
            List<String> zrfxmList = new ArrayList<>();
            List<String> csfxmList = new ArrayList<>();
            List<String> zlList = new ArrayList<>();
            BigDecimal htjyjg = BigDecimal.valueOf(0);
            BigDecimal zfmj = BigDecimal.valueOf(0);
            BigDecimal glmj = BigDecimal.valueOf(0);
            BigDecimal ckmj = BigDecimal.valueOf(0);
            // 用于判断当前证书是否存在房地产权，当count>1时，存在则需要打印纳税联系单
            int count = 0;
            for(BdcXmDO bdcXmDO : list){
                // 获取合同交易金额
                BdcQl bdcQl = this.bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                if(!(bdcQl instanceof BdcFdcqDO)){
                    continue;
                }
                count++;
                final BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                if(null != bdcFdcqDO.getJyjg()){
                    htjyjg = htjyjg.add(BigDecimal.valueOf(bdcFdcqDO.getJyjg()));
                }
                // 获取房屋坐落信息
                zlList.add(bdcXmDO.getZl());
                // 获取面积信息
                {
                    final String dzwytmc = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getDzwyt(), zdList);
                    if(dzwytmc.indexOf("车库")>-1 || dzwytmc.indexOf("车位")>-1){
                        if(null != bdcXmDO.getDzwmj()){
                            ckmj = ckmj.add(BigDecimal.valueOf(bdcXmDO.getDzwmj()));
                        }
                    }else if(dzwytmc.indexOf("阁楼")>-1){
                        if(null != bdcXmDO.getDzwmj()){
                            glmj = glmj.add(BigDecimal.valueOf(bdcXmDO.getDzwmj()));
                        }
                    }else{
                        if(null != bdcXmDO.getDzwmj()){
                            zfmj = zfmj.add(BigDecimal.valueOf(bdcXmDO.getDzwmj()));
                        }
                    }
                }
                // 转让方姓名
                this.addPerson(zrfxmList, bdcXmDO.getYwr());
                // 承受方姓名
                this.addPerson(csfxmList, bdcXmDO.getQlr());
            }
            if(count > 0){
                Map<String,String> resultMap = new HashMap<>();
                resultMap.put("zrfxm",String.join(",",zrfxmList));
                resultMap.put("csfxm",String.join(",",csfxmList));
                resultMap.put("fwzl",String.join(",",zlList));
                resultMap.put("fwmj",String.valueOf(zfmj));
                resultMap.put("glmj",String.valueOf(glmj));
                resultMap.put("ckmj",String.valueOf(ckmj));
                resultMap.put("htydjg",String.valueOf(htjyjg));
                resultMap.put("htqdsj",htjysj);
                nslxdDataList.add(resultMap);
            }
        }
        return nslxdDataList;
    }

    // 根据证书数量，获取组合成一起的bdcXm信息，Map<key,value> key为证书序号，value为一本证上的项目信息
    private Map<String,List<BdcXmDO>> getZsXmMap(List<BdcXmDO> bdcXmDOList){
        Map<String,List<BdcXmDO>> zsMap = new HashMap<>();
        for(BdcXmDO bdcXmDO:bdcXmDOList){
            final BdcCshFwkgSlDO bdcCshFwkgSlDO = this.bdcXmFeignService.queryFwkgsl(bdcXmDO.getXmid());
            if(null != bdcCshFwkgSlDO){
                // 当证书序号为空时，则生成一本证书
                if(null == bdcCshFwkgSlDO.getZsxh()){
                    List<BdcXmDO> list = new ArrayList<>();
                    list.add(bdcXmDO);
                    zsMap.put(bdcCshFwkgSlDO.getId(), list);
                }else{
                    //判断相同序号的证书序号是否存在于zsMap中
                    if(zsMap.containsKey(bdcCshFwkgSlDO.getZsxh().toString())){
                        List<BdcXmDO> list = zsMap.get(bdcCshFwkgSlDO.getZsxh().toString());
                        list.add(bdcXmDO);
                        zsMap.put(bdcCshFwkgSlDO.getZsxh().toString(),list);
                    }else{
                        List<BdcXmDO> list = new ArrayList<>();
                        list.add(bdcXmDO);
                        zsMap.put(bdcCshFwkgSlDO.getZsxh().toString(), list);
                    }
                }
            }
        }
        return zsMap;
    }

    //根据不动产项目的定着物用途进行排序，按照 主房》阁楼》车库
    private void sortBdcXmListByDzwyt(List<BdcXmDO> list, List<Map> zdList){
        list.sort(new Comparator<BdcXmDO>() {
            @Override
            public int compare(BdcXmDO o1, BdcXmDO o2) {
                final String o1Dzwytmc = StringToolUtils.convertBeanPropertyValueOfZd(o1.getDzwyt(), zdList);
                final String o2Dzwytmc = StringToolUtils.convertBeanPropertyValueOfZd(o2.getDzwyt(), zdList);
                int o1Count = this.getCountByDzwyt(o1Dzwytmc);
                int o2Count = this.getCountByDzwyt(o2Dzwytmc);
                return o1Count >= o2Count ? -1 : 1;
            }

            private int getCountByDzwyt(String dzwytmc){
                if(dzwytmc.indexOf("车库")>-1 || dzwytmc.indexOf("车位")>-1){
                    return 1;
                }else if(dzwytmc.indexOf("阁楼")>-1){
                    return 2;
                }else{
                    return 3;
                }
            }
        });
    }

    // 获取合同交易时间
    private String getHtjysj(String xmid){
        final List<BdcSlJyxxDO> bdcSlJyxxDOList = this.bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
        String htqdsj = "";
        if(CollectionUtils.isNotEmpty(bdcSlJyxxDOList) && null!=bdcSlJyxxDOList.get(0).getHtdjsj()){
            Instant instant = bdcSlJyxxDOList.get(0).getHtdjsj().toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            htqdsj = localDateTime.format(TIME_FORMATTER);
        }
        return htqdsj;
    }

    // 将不动产项目表中的权利人与业务人加入到承受方与转让方，去除相同人名
    private void addPerson(List<String> personList, String personInfo){
        if(StringUtils.isNotBlank(personInfo)){
            final String[] personArray = personInfo.split(",");
            for(String str: personArray){
                if(!personList.contains(str)){
                    personList.add(str);
                }
            }
        }
    }

}
