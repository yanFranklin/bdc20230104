package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcBaxxPlcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPlcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcBaxxCxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcPlcxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcPlcxMapper;
import cn.gtmap.realestate.inquiry.service.BdcPlcxService;
import cn.gtmap.realestate.inquiry.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.*;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 皮量查询服务imp
 */
@Service
public class BdcPlcxServiceImpl implements BdcPlcxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcPlcxServiceImpl.class);

    @Autowired
    private Repo repo;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    BdcPlcxMapper bdcPlcxMapper;

    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    /**
     * @param pageable 分页参数
     * @param bdcPlcxQO 查询Qo
     * List<BdcPlcxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询批量查询信息
     */
    @Override
    public Page<BdcPlcxDTO> listBdcPlcxByPage(Pageable pageable, BdcPlcxQO bdcPlcxQO) {
        Page<BdcPlcxDTO> page = repo.selectPaging("listBdcPlcxByPageOrder", bdcPlcxQO, pageable);
        if(page != null && CollectionUtils.isNotEmpty(page.getContent())){
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            for(int i =0 ;i<page.getContent().size();i++){
                if(StringToolUtils.isNotBlank(page.getContent().get(i).getGhyt())){
                    String yyt = page.getContent().get(i).getGhyt();
                    // htba_spf的yt是名称，不需要通过字典表取值
                    if (StringUtils.isNumeric(yyt)) {
                        String fyFwyt = StringToolUtils.convertBeanPropertyValueOfZd(
                                Integer.parseInt(yyt), zdMap.get(Constants.FWYT));
                        if(yyt.indexOf(fyFwyt) > -1){
                            fyFwyt = StringToolUtils.convertBeanPropertyValueOfZd(
                                    Integer.parseInt(yyt), zdMap.get(Constants.TDYT));
                        }
                        page.getContent().get(i).setGhyt(fyFwyt);
                    }
                }
            }
            this.handlePlcxBdcdyhXzZt(page.getContent());
        }
        return page;
    }

    /**
     * 处理不动产单元号限制状态
     */
    private void handlePlcxBdcdyhXzZt(List<BdcPlcxDTO> bdcPlcxDTOList){
        // 获取不动产单元状态
        /// 1、先获取不动产单元号集合
        List<String> bdcdyhList = new ArrayList<>(bdcPlcxDTOList.size());
        //根据权籍管理代码对不动产单元集合分组
        Map<String,List<String>> qjgldmBdcdyhMap =new HashMap<>();
        for (BdcPlcxDTO bdcPlcxDTO : bdcPlcxDTOList) {
            if (null == bdcPlcxDTO || null == bdcPlcxDTO.getQszt() || bdcPlcxDTO.getSfhtba()) {
                continue;
            }
            /// 只显示现势产权对应的限制信息
            if (StringUtils.equals(String.valueOf(QSZT_VALID), bdcPlcxDTO.getQszt())) {
                if(StringUtils.isNotBlank(bdcPlcxDTO.getQjgldm())) {
                    if (qjgldmBdcdyhMap.containsKey(bdcPlcxDTO.getQjgldm())) {
                        List<String> dyhList = qjgldmBdcdyhMap.get(bdcPlcxDTO.getQjgldm());
                        dyhList.add(bdcPlcxDTO.getBdcdyh());
                        qjgldmBdcdyhMap.put(bdcPlcxDTO.getQjgldm(), dyhList);
                    }else{
                        List<String> dyhList =new ArrayList<>();
                        dyhList.add(bdcPlcxDTO.getBdcdyh());
                        qjgldmBdcdyhMap.put(bdcPlcxDTO.getQjgldm(),dyhList);
                    }
                }else{
                    bdcdyhList.add(bdcPlcxDTO.getBdcdyh());
                }
            }
        }
        if (CollectionUtils.isEmpty(bdcdyhList) &&MapUtils.isEmpty(qjgldmBdcdyhMap)) {
            return;
        }
        /// 2、调用权籍获取状态
        List<BdcdyhZtResponseDTO> bdcdyhZtDTOList =new ArrayList<>();
        if(MapUtils.isNotEmpty(qjgldmBdcdyhMap)){
            for (Map.Entry<String, List<String>> entry : qjgldmBdcdyhMap.entrySet()) {
                bdcdyhZtDTOList.addAll(bdcdyZtFeignService.commonListBdcdyhZtBybdcdyh(entry.getValue(),entry.getKey()));
            }
        }else {
            bdcdyhZtDTOList = bdcdyZtFeignService.commonListBdcdyhZtBybdcdyh(bdcdyhList, "");
        }
        if (CollectionUtils.isEmpty(bdcdyhZtDTOList)) {
            return;
        }
        /// 3、匹配设置不动产单元状态
        for (BdcPlcxDTO bdcPlcxDTO : bdcPlcxDTOList) {
            for (BdcdyhZtResponseDTO bdcdyhZtDTO : bdcdyhZtDTOList) {
                if (StringUtils.equals(bdcPlcxDTO.getBdcdyh(), bdcdyhZtDTO.getBdcdyh())
                        && StringUtils.isNotBlank(bdcPlcxDTO.getQszt())
                        && StringUtils.equals(String.valueOf(QSZT_VALID), bdcPlcxDTO.getQszt())) {
                    bdcPlcxDTO.setBdcdyZtDTO(bdcdyhZtDTO);
                }
            }
        }
    }


    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcPlcxQO, isImportExcel]
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO>
     * @description 盐城 批量查询
     */
    @Override
    public List<BdcYcPlcxDTO> listBdcYcPlcx(BdcPlcxQO bdcPlcxQO, boolean isImportExcel) {
        List<BdcYcPlcxDTO> databaseQueryList;
        if(StringUtils.isNotBlank(bdcPlcxQO.getBdclx()) && StringUtils.equals(bdcPlcxQO.getBdclx(), "all")){
            databaseQueryList = bdcPlcxMapper.listBdcYcPlcxAll(bdcPlcxQO);
        }else{
            databaseQueryList = bdcPlcxMapper.listBdcYcPlcx(bdcPlcxQO);
            convertGhytToMc(databaseQueryList);
        }
        LOGGER.info("符合查询条件的数据共有{}条",databaseQueryList.size());

        if(CollectionUtils.isEmpty(databaseQueryList)) {
            return Collections.emptyList();
        }

        // 需求37156：产权证号相同的数据，合并为一条 by zhuyong 20210204
        // 需要考虑导入查询和手动查询区别，导入查询需要在区分人的情况下再按照证号合并，手动查询直接按照证号合并
        Map<String, List<BdcYcPlcxDTO>> bdcqzhGroupMap = databaseQueryList.stream().filter(item -> StringUtils.isNotBlank(item.getBdcqzh())).collect(Collectors.groupingBy(BdcYcPlcxDTO::getBdcqzh));
        if(MapUtils.isEmpty(bdcqzhGroupMap)) {
            return Collections.emptyList();
        }

        // 导入查询情况下，列表需要按照查询人顺序展示，需要从结果中匹配数据
        // Map<权利人, List<结果数据>> 用于缓存不同人对应的数据
        Map<String, List<BdcYcPlcxDTO>> qlrFcxxMap = new LinkedHashMap<>();
        // Map<权利人, 权利人查询Excel顺序> 用于匹配定义展示结果顺序
        Map<String, Integer> qlrSxhMap = new HashMap<>();
        // Excel中原始序号
        String[] xhList = StringUtils.split(bdcPlcxQO.getXh(), ZF_YW_DH);

        if(CollectionUtils.isNotEmpty(bdcPlcxQO.getZjh())) {
            for (int i = 0; i < bdcPlcxQO.getZjh().size(); i++) {
                String key = getKey(i >= bdcPlcxQO.getQlr().size() ? "" : bdcPlcxQO.getQlr().get(i), bdcPlcxQO.getZjh().get(i));
                qlrFcxxMap.put(key, new ArrayList<>());
                int xh ;
                if(0 == xhList.length || i >= xhList.length || null == xhList[i]) {
                    xh = i + 1;
                } else {
                    xh = Integer.parseInt(xhList[i]);
                }
                qlrSxhMap.put(key, xh);
            }
        }

        // 查询出历史产权转移登记时间
        Map<String, String> lscqZydjsj = this.getLscqZydjsj(databaseQueryList);

        // 将分组的查询结果数据进行合并处理，以证为核心
        List<BdcYcPlcxDTO> mergedDataList = new ArrayList<>();
        for(Map.Entry entry : bdcqzhGroupMap.entrySet()) {
            if(CollectionUtils.isEmpty((List<BdcYcPlcxDTO>) entry.getValue())) {
                continue;
            }

            BdcYcPlcxDTO mergedData = this.mergeDataList(entry);
            // 如果该产权为历史状态，则在备注栏中显示该产权的转移登记时间
            if(MapUtils.isNotEmpty(lscqZydjsj) && isHistoryQszt(mergedData.getQszt())) {
                String djsj = lscqZydjsj.get(entry.getKey());
                djsj = StringUtils.isBlank(djsj) ? "" : djsj;
                mergedData.setBz(mergedData.getBz() + " " + djsj);
                // 注销时间为空时，获取下一首产权的登记时间
                if(StringUtils.isBlank(mergedData.getZxdjsj())){
                    mergedData.setZxdjsj(djsj);
                }
            }
            mergedDataList.add(mergedData);

            if(MapUtils.isNotEmpty(qlrFcxxMap)) {
                // 房产数据按照证号合并后，需要将房产数据对照查询条件权利人匹配，例如权利人A、B共有一套房产，
                // 合并后就一条记录了，但是最终展示是按照人展示，所以需要匹配人
                List<String> qlrmcs = (List<String>) CollectionUtils.union(Arrays.asList(StringUtils.split(mergedData.getQlr(), ZF_YW_DH)),
                        Arrays.asList(StringUtils.split(mergedData.getQlr(), ZF_YW_XG)));

                List<String> zjhs = (List<String>) CollectionUtils.union(Arrays.asList(StringUtils.split(mergedData.getZjh(), ZF_YW_DH)),
                        Arrays.asList(StringUtils.split(mergedData.getZjh(), ZF_YW_XG)));

                for(String qlrmc : qlrmcs) {
                    for(String zjh : zjhs) {
                        String key = getKey(qlrmc, zjh);
                        if(!qlrFcxxMap.containsKey(key)) {
                            continue;
                        }

                        BdcYcPlcxDTO data = JSON.parseObject(JSON.toJSONString(mergedData), BdcYcPlcxDTO.class);
                        // 这里的序号如果是Excel导入查询的则按照Excel中的序号来
                        data.setXh(qlrSxhMap.get(key));
                        qlrFcxxMap.get(key).add(data);
                    }
                }
            }
        }

        if(MapUtils.isNotEmpty(qlrFcxxMap)) {
            // 一般是导入Excel查询
            List<BdcYcPlcxDTO> result = new ArrayList<>();
            for(Map.Entry entry : qlrFcxxMap.entrySet()) {
                result.addAll((Collection<? extends BdcYcPlcxDTO>) entry.getValue());
            }
            return result;
        } else {
            // 手动查询
            for (int i = 0; i < mergedDataList.size(); i++) {
                mergedDataList.get(i).setXh(i + 1);
            }
            return mergedDataList;
        }
    }

    /**
     * 生成key
     * @param qlrmc 权利人名称
     * @param zjh 证件号
     * @return {String} 权利人名称和18、15位证件号拼接的字符串
     */
    private String getKey(String qlrmc, String zjh) {
        String zjhs = CardNumberTransformation.zjhTransformation(zjh);
        return qlrmc + ZF_YW_MH + zjhs;
    }

    /**
     * 获取历史产权转移登记时间
     * @param databaseQueryList 批量查询产权数据
     * @return 证号-转移登记时间 Map
     */
    private Map<String, String> getLscqZydjsj(List<BdcYcPlcxDTO> databaseQueryList) {
        Set<String> bdcqzhSet = new HashSet<>();
        for(BdcYcPlcxDTO item : databaseQueryList) {
            if(null != item && isHistoryQszt(item.getQszt())) {
                bdcqzhSet.add(item.getBdcqzh());
            }
        }

        if(CollectionUtils.isEmpty(bdcqzhSet)) {
            return null;
        }

        // 查询出产权下一手项目信息（现场数据可能没有上一手注销时间，直接查询下一手项目）
        List<BdcXmDO> xmDOList = bdcPlcxMapper.listXysxmByBdcqzh(bdcqzhSet);
        if(CollectionUtils.isEmpty(xmDOList)) {
            return null;
        }

        Map<String, String> result = new HashMap<>();
        for(BdcXmDO bdcXmDO : xmDOList) {
            if(!result.containsKey(bdcXmDO.getBdcqzh()) && null != bdcXmDO.getDjsj()) {
                // 只取第一手转移登记时间
                result.put(bdcXmDO.getBdcqzh(), DateFormatUtils.format(bdcXmDO.getDjsj(), "yyyy-MM-dd"));
            }
        }
        return result;
    }

    /**
     * 判断是否是历史权属状态
     */
    private boolean isHistoryQszt(String qszt) {
        if(StringUtils.isBlank(qszt)) {
            return false;
        }
        return QSZT_HISTORY.intValue() == Integer.parseInt(qszt.split(",")[0]);
    }

    /**
     * 将多条证号相同的产权记录合并成一条产权
     * @param entry Map Entry，对应证号产权数据
     * @return 合并后新的产权信息
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private BdcYcPlcxDTO mergeDataList(Map.Entry entry) {
        List<BdcYcPlcxDTO> itemList = (List<BdcYcPlcxDTO>) entry.getValue();

        BdcYcPlcxDTO resultItem = new BdcYcPlcxDTO();
        resultItem.setBdcqzh((String) entry.getKey());
        resultItem.setQlr(StringToolUtils.joinBeanAttribute(itemList, "getQlr", ZF_YW_DH));
        resultItem.setZjh(StringToolUtils.joinBeanAttribute(itemList, "getZjh", ZF_YW_DH));
        resultItem.setDjsj(StringToolUtils.joinBeanAttribute(itemList, "getDjsj", ZF_YW_DH));
        resultItem.setBz(StringToolUtils.joinBeanAttribute(itemList, "getBz", ZF_YW_DH));
        resultItem.setQszt(StringToolUtils.joinBeanAttribute(itemList, "getQszt", ZF_YW_DH));
        resultItem.setZl(StringToolUtils.joinBeanAttribute(itemList, "getZl", ZF_YW_DH));
        resultItem.setZxdjsj(StringToolUtils.joinBeanAttribute(itemList, "getZxdjsj", ZF_YW_DH));
        if(StringUtils.isNotBlank(resultItem.getZl())) {
            // 一本证多个单元的建筑面积、规划用途可能相等，但是需求要求按数量展示
            Map<String, List<BdcYcPlcxDTO>> zlGroupMap = itemList.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(BdcYcPlcxDTO::getZl));

            List<String> jzmjList = new ArrayList<>();
            List<String> ghytList = new ArrayList<>();
            for(String zl : resultItem.getZl().split(",")) {
                List<BdcYcPlcxDTO> plcxDTOS = zlGroupMap.get(zl);
                if(CollectionUtils.isNotEmpty(plcxDTOS)) {
                    jzmjList.add(plcxDTOS.get(0).getJzmj());
                    ghytList.add(plcxDTOS.get(0).getGhyt());
                }
            }
            resultItem.setJzmj(StringUtils.join(jzmjList,","));
            resultItem.setGhyt(StringUtils.join(ghytList, ","));
        } else {
            resultItem.setJzmj(StringToolUtils.joinBeanAttribute(itemList, "getJzmj", ZF_YW_DH));
            resultItem.setGhyt(StringToolUtils.joinBeanAttribute(itemList, "getGhyt", ZF_YW_DH));
        }

        return resultItem;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcYcPlcxDTOList]
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO>
     * @description 规划用途转字典值
     */
    private List<BdcYcPlcxDTO> convertGhytToMc(List<BdcYcPlcxDTO> bdcYcPlcxDTOList){
        if(CollectionUtils.isNotEmpty(bdcYcPlcxDTOList)) {
            Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
            for (int i = 0; i < bdcYcPlcxDTOList.size(); i++) {
                if (StringToolUtils.isNotBlank(bdcYcPlcxDTOList.get(i).getGhyt())) {
                    String yyt = bdcYcPlcxDTOList.get(i).getGhyt();
                    String fyFwyt = StringToolUtils.convertBeanPropertyValueOfZd(
                            Integer.parseInt(yyt), zdMap.get(Constants.FWYT));
                    if (yyt.indexOf(fyFwyt) > -1) {
                        fyFwyt = StringToolUtils.convertBeanPropertyValueOfZd(
                                Integer.parseInt(yyt), zdMap.get(Constants.TDYT));
                    }
                    bdcYcPlcxDTOList.get(i).setGhyt(fyFwyt);
                }
            }
        }
        return bdcYcPlcxDTOList;
    }


    /**
     * @param pageable 分页参数
     * @param bdcBaxxCxQO 查询Qo
     * List<BdcBaxxPlcxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询备案信息
     */
    @Override
    public Page<BdcBaxxPlcxDTO> listBdcBaxxPlcxByPage(Pageable pageable, BdcBaxxCxQO bdcBaxxCxQO) {
        return repo.selectPaging("listBdcBaxxPlcxByPageOrder", bdcBaxxCxQO, pageable);
    }

    /**
     * @param bdcBaxxCxQO 查询Qo
     * List<BdcBaxxPlcxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询备案信息
     */
    @Override
    public List<BdcBaxxPlcxDTO> listBdcBaxxPlcx(BdcBaxxCxQO bdcBaxxCxQO) {
        return repo.selectList("listBdcBaxxPlcx", bdcBaxxCxQO);
    }

}
