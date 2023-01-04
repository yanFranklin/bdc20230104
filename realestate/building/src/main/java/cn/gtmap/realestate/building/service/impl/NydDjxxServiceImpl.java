package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.InitDjxxService;
import cn.gtmap.realestate.building.service.NydDjxxService;
import cn.gtmap.realestate.building.util.BdcdySqlConstants;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.TzmUtils;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-16
 * @description
 */
@Service
public class NydDjxxServiceImpl implements InitDjxxService, NydDjxxService {

    @Autowired
    private NydService nydService;

    @Autowired
    private NydQlrService nydQlrService;

    @Autowired
    private LqService lqService;

    @Autowired
    private CbzdService cbzdService;

    @Autowired
    private EntityZdConvertUtils entityZdConvertUtils;

    @Autowired
    private DozerBeanMapper initDjxxDozerMapper;

    @Autowired
    private ZdQsdcService zdQsdcService;

    /**
     * 根据不动产单元号初始化承包宗地信息
     * @param bdcdyh
     * @param nydDTO
     */
    private void initCbzdDjxx(String bdcdyh,String cbzdDcbcbfrelIndex,NydDjdcbResponseDTO nydDTO) {
        // 根据BDCDYH 查询承包宗地
        CbzdDcbDO cbzdDcbDO =  cbzdService.queryCdzdDcbByBdcdyh(bdcdyh);
        if (cbzdDcbDO != null && StringUtils.isNotBlank(cbzdDcbDO.getCbzdDcbIndex())) {
            Map<String, Object> doMap = JSONObject.parseObject(JSONObject.toJSONString(cbzdDcbDO), Map.class);
            Map<String, Object> revertMap = revertDOToDTOMap(doMap, "cbzd");
            NydDjdcbResponseDTO nydDTOTemp = JSONObject.parseObject(JSONObject.toJSONString(revertMap), NydDjdcbResponseDTO.class);
            // 对象赋值
            BuildingUtils.copyPropertiesWithOutNull(nydDTOTemp, nydDTO);
            if(StringUtils.isNotBlank(cbzdDcbcbfrelIndex)) {
                //获取承包宗地承包方合同信息
                CbzdDcbcbfRelDO cbzdDcbcbfRelDO = cbzdService.queryCbfCbzdDcbcbfRel(cbzdDcbcbfrelIndex);
                Map<String, Object> cbzdCbfMap = JSONObject.parseObject(JSONObject.toJSONString(cbzdDcbcbfRelDO), Map.class);
                Map<String, Object> revertCbzdCbfMap = revertDOToDTOMap(cbzdCbfMap, "cbzdcbf");
                NydDjdcbResponseDTO nydDTOCbzdCbfTemp = JSONObject.parseObject(JSONObject.toJSONString(revertCbzdCbfMap), NydDjdcbResponseDTO.class);
                // 对象赋值
                BuildingUtils.copyPropertiesWithOutNull(nydDTOCbzdCbfTemp, nydDTO);
            }

            // 查询发包方承包方
            if(StringUtils.isNotBlank(cbzdDcbDO.getFbfbm())){
                List<CbzdFbfDO> cbzdFbfDOList = new ArrayList<>();
                cbzdFbfDOList.add(cbzdService.getFbfByFbfIndex(cbzdDcbDO.getFbfbm()));
                if (CollectionUtils.isNotEmpty(cbzdFbfDOList)) {
                    nydDTO.setCbzdFbfDOList(cbzdFbfDOList);
                }
            }
            if(StringUtils.isNotBlank(cbzdDcbDO.getBdcdyh())){
                List<CbzdCbfDO> cbzdCbfDOList;
                if(StringUtils.isNotBlank(cbzdDcbcbfrelIndex)) {
                    cbzdCbfDOList = cbzdService.listCbfByCbzdDcbcbfrelIndex(cbzdDcbcbfrelIndex);
                }else{
                    cbzdCbfDOList = cbzdService.listCbfByBdcdyh(bdcdyh);
                }
                if (CollectionUtils.isNotEmpty(cbzdCbfDOList)) {
                    nydDTO.setCbzdCbfDOList(cbzdCbfDOList);
                }
                // 查询承包方家庭成员
                if (StringUtils.isNotBlank(cbzdDcbcbfrelIndex) && CollectionUtils.isNotEmpty(cbzdCbfDOList)) {
                    nydDTO.setCbfJtcyDOList(cbzdService.listJtcy(cbzdCbfDOList.get(0).getCbzdCbfIndex()));
                } else {
                    nydDTO.setCbfJtcyDOList(cbzdService.listCbfJtcy(bdcdyh));
                }

            }

        }
    }

    /**
     * @param djh
     * @param nydDTO
     * @param djxxResponseDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理林权 地籍信息相关属性字段
     */
    private void initLqDjxx(String djh, NydDjdcbResponseDTO nydDTO, DjxxResponseDTO djxxResponseDTO) {
        // 根据BDCDYH 查询 林权地籍信息
        LqDcbDO lqDcbDO = lqService.queryLqDcbByDjh(djh);
        if (lqDcbDO != null && StringUtils.isNotBlank(lqDcbDO.getLqDcbIndex())) {
            Map<String, Object> doMap = JSONObject.parseObject(JSONObject.toJSONString(lqDcbDO), Map.class);
            Map<String, Object> revertMap = revertDOToDTOMap(doMap, "lq");
            NydDjdcbResponseDTO nydDTOTemp = JSONObject.parseObject(JSONObject.toJSONString(revertMap), NydDjdcbResponseDTO.class);
            BuildingUtils.copyPropertiesWithOutNull(nydDTOTemp, nydDTO);
            // 处理林权权利人
            dealLqQlr(djxxResponseDTO, lqDcbDO);
        }
    }


    /**
     * @param djxxResponseDTO
     * @param lqDcbDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理林权权利人
     */
    private void dealLqQlr(DjxxResponseDTO djxxResponseDTO, LqDcbDO lqDcbDO) {

        List<NydQlrDO> nydQlrDOList = nydQlrService.listNydQlrByDjh(lqDcbDO.getDjh());

        if (CollectionUtils.isNotEmpty(nydQlrDOList)) {
            // 林地使用权人
            List<NydQlrDO> ldsyqrList = lqService.listQlrByLx(nydQlrDOList, Constants.LQQLRLX_LDSYQR);
            List<DjxxQlrDTO> ldsyqrDTOList = revertQlrDOListToDTOList(ldsyqrList);
            if (CollectionUtils.isNotEmpty(ldsyqrDTOList)) {
                djxxResponseDTO.setQlrList(ldsyqrDTOList);
            }

            // 查询 林木使用权人人
            List<NydQlrDO> lmsyqrList = lqService.listQlrByLx(nydQlrDOList, Constants.LQQLRLX_LMSYQR);
            List<DjxxQlrDTO> lmsyqrDTOList = revertQlrDOListToDTOList(lmsyqrList);
            if (CollectionUtils.isNotEmpty(lmsyqrDTOList)) {
                djxxResponseDTO.setLmsyqrList(lmsyqrDTOList);
            }

            // 林木所有权人
            List<NydQlrDO> lmsuqrList = lqService.listQlrByLx(nydQlrDOList, Constants.LQQLRLX_LMSUQR);
            List<DjxxQlrDTO> lmsuqrDTOList = revertQlrDOListToDTOList(lmsuqrList);
            if (CollectionUtils.isNotEmpty(lmsuqrDTOList)) {
                djxxResponseDTO.setLmsuqrList(lmsuqrDTOList);
            }
        }
    }

    /**
     * @param djh
     * @param nydDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理承包宗地 地籍信息相关属性字段
     */
    private void initHCbzdDjxx(String djh, NydDjdcbResponseDTO nydDTO) {
        // 根据BDCDYH 查询承包宗地
        CbzdDcbDO cbzdDcbDO = cbzdService.queryHCbzdDcbByDjh(djh);
        if (cbzdDcbDO != null && StringUtils.isNotBlank(cbzdDcbDO.getCbzdDcbIndex())) {
            Map<String, Object> doMap = JSONObject.parseObject(JSONObject.toJSONString(cbzdDcbDO), Map.class);
            Map<String, Object> revertMap = revertDOToDTOMap(doMap, "cbzd");
            NydDjdcbResponseDTO nydDTOTemp = JSONObject.parseObject(JSONObject.toJSONString(revertMap), NydDjdcbResponseDTO.class);
            // 对象赋值
            BuildingUtils.copyPropertiesWithOutNull(nydDTOTemp, nydDTO);

            //查询发包方和承包方
            if(StringUtils.isNotBlank(cbzdDcbDO.getFbfbm())){
                List<CbzdFbfDO> fbfList = new ArrayList<>();
                HCbzdFbfDO hCbzdFbfDO = cbzdService.getHFbfByFbfIndex(cbzdDcbDO.getFbfbm());
                if(hCbzdFbfDO != null){
                    CbzdFbfDO cbzdFbfDO = new CbzdFbfDO();
                    BeanUtils.copyProperties(hCbzdFbfDO,cbzdDcbDO);
                    fbfList.add(cbzdFbfDO);
                    nydDTO.setCbzdFbfDOList(fbfList);
                }
            }
            if (StringUtils.isNotBlank(cbzdDcbDO.getBdcdyh())) {
                List<HCbzdCbfDO> cbzdCbfDOList = cbzdService.listHCbfByBdcdyh(cbzdDcbDO.getBdcdyh());
                if (CollectionUtils.isNotEmpty(cbzdCbfDOList)) {
                    List<CbzdCbfDO> cbfList = new ArrayList<>();
                    for(HCbzdCbfDO hCbf: cbzdCbfDOList){
                        CbzdCbfDO cbf = new CbzdCbfDO();
                        BeanUtils.copyProperties(hCbf,cbf);
                        cbfList.add(cbf);
                    }
                    nydDTO.setCbzdCbfDOList(cbfList);
                }
            }
        }

    }

    /**
     * @param djh
     * @param nydDTO
     * @param djxxResponseDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理林权 地籍信息相关属性字段
     */
    private void initHLqDjxx(String djh, NydDjdcbResponseDTO nydDTO, DjxxResponseDTO djxxResponseDTO) {
        // 根据BDCDYH 查询 林权地籍信息
        LqDcbDO lqDcbDO = lqService.queryHLqDcbByDjh(djh);
        if (lqDcbDO != null && StringUtils.isNotBlank(lqDcbDO.getLqDcbIndex())) {
            Map<String, Object> doMap = JSONObject.parseObject(JSONObject.toJSONString(lqDcbDO), Map.class);
            Map<String, Object> revertMap = revertDOToDTOMap(doMap, "lq");
            NydDjdcbResponseDTO nydDTOTemp = JSONObject.parseObject(JSONObject.toJSONString(revertMap), NydDjdcbResponseDTO.class);
            BuildingUtils.copyPropertiesWithOutNull(nydDTOTemp, nydDTO);
            // 处理林权权利人
            dealHLqQlr(djxxResponseDTO, lqDcbDO);
        }
    }


    /**
     * @param djxxResponseDTO
     * @param lqDcbDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理林权权利人
     */
    private void dealHLqQlr(DjxxResponseDTO djxxResponseDTO, LqDcbDO lqDcbDO) {

        List<HNydQlrDO> nydQlrDOList = nydQlrService.listHNydQlrByDcbIndex(lqDcbDO.getLqDcbIndex());

        if (CollectionUtils.isNotEmpty(nydQlrDOList)) {

            List<NydQlrDO> lqQlrList = new ArrayList<>();
            for(HNydQlrDO hNydQlr:nydQlrDOList){
                NydQlrDO nydQlr = new NydQlrDO();
                BeanUtils.copyProperties(hNydQlr,nydQlr);
            }

            // 林地使用权人
            List<NydQlrDO> ldsyqrList = lqService.listQlrByLx(lqQlrList, Constants.LQQLRLX_LDSYQR);
            List<DjxxQlrDTO> ldsyqrDTOList = revertQlrDOListToDTOList(ldsyqrList);
            if (CollectionUtils.isNotEmpty(ldsyqrDTOList)) {
                djxxResponseDTO.setQlrList(ldsyqrDTOList);
            }

            // 查询 林木使用权人人
            List<NydQlrDO> lmsyqrList = lqService.listQlrByLx(lqQlrList, Constants.LQQLRLX_LMSYQR);
            List<DjxxQlrDTO> lmsyqrDTOList = revertQlrDOListToDTOList(lmsyqrList);
            if (CollectionUtils.isNotEmpty(lmsyqrDTOList)) {
                djxxResponseDTO.setLmsyqrList(lmsyqrDTOList);
            }

            // 林木所有权人
            List<NydQlrDO> lmsuqrList = lqService.listQlrByLx(lqQlrList, Constants.LQQLRLX_LMSUQR);
            List<DjxxQlrDTO> lmsuqrDTOList = revertQlrDOListToDTOList(lmsuqrList);
            if (CollectionUtils.isNotEmpty(lmsuqrDTOList)) {
                djxxResponseDTO.setLmsuqrList(lmsuqrDTOList);
            }
        }
    }

    /**
     * @param qlrDOList
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.DjxxQlrDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description qlrDOList 转成 QLR DTO
     */
    public <T> List<DjxxQlrDTO> revertQlrDOListToDTOList(List<T> qlrDOList) {
        List<DjxxQlrDTO> djxxQlrDTOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(qlrDOList)) {
            DjxxQlrDTO djxxQlrDTO;
            for (T qlrDO : qlrDOList) {
                djxxQlrDTO = revertQlrDOToDTO(qlrDO);
                if (djxxQlrDTO != null) {
                    djxxQlrDTOList.add(djxxQlrDTO);
                }
            }
        }
        return djxxQlrDTOList;
    }

    /**
     * @param qlrDO
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxQlrDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description DO权利人实体  转换 成 DTO 后面改成使用dozer组件处理
     */
    @Override
    public DjxxQlrDTO revertQlrDOToDTO(Object qlrDO) {
        if (qlrDO != null) {
            NydQlrDO nydQlrDO = (NydQlrDO) qlrDO;
            DjxxQlrDTO djxxQlrDTO = new DjxxQlrDTO();
            initDjxxDozerMapper.map(nydQlrDO,djxxQlrDTO);
            return djxxQlrDTO;
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号查询 地籍信息
     */
    @Override
    public DjxxResponseDTO getDjxxForInitByBdcdyh(String bdcdyh,String djqlrgxid) {
        String djh = BuildingUtils.getDjhByBdcdyh(bdcdyh);
        NydDjdcbDO nydDO = nydService.queryNydDjdcbByDjh(djh);
        if (nydDO != null && StringUtils.isNotBlank(nydDO.getNydDjdcbIndex())) {
            DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
            // 查询农用地调查表实体
            NydDjdcbResponseDTO nydDTO = new NydDjdcbResponseDTO();
            BuildingUtils.copyPropertiesWithOutNull(nydDO, nydDTO);

            // 查询 农用地权利人
            List<NydQlrDO> nydQlrDOList = nydQlrService.listNydQlrByDjh(nydDO.getDjh());
            List<DjxxQlrDTO> qlrDTOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(nydQlrDOList)) {
                for (NydQlrDO nydQlrDO : nydQlrDOList) {
                    qlrDTOList.add(revertQlrDOToDTO(nydQlrDO));
                }
            }
            // 保存 农用地权利人
            djxxResponseDTO.setQlrList(qlrDTOList);

            // 判断是否需要处理承包宗地调查表  处理承包宗地 地籍信息相关属性字段
            if (TzmUtils.hasCbzdDcb(bdcdyh)) {
                initCbzdDjxx(bdcdyh, djqlrgxid,nydDTO);
            }

            // 处理林权 地籍信息相关属性字段
            if (TzmUtils.hasLqDcb(bdcdyh)) {
                initLqDjxx(djh, nydDTO, djxxResponseDTO);
            }
            djxxResponseDTO.setDjDcbResponseDTO(nydDTO);
            return djxxResponseDTO;
        }
        return null;
    }

    /**
     * @param bdcdyh
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号和承包宗地关系主键查询承包宗地地籍信息
     */
    @Override
    public DjxxResponseDTO getCbzdDjxxForInit(String bdcdyh,String cbzdDcbcbfrelIndex) {
        String djh = BuildingUtils.getDjhByBdcdyh(bdcdyh);
        NydDjdcbDO nydDO = nydService.queryNydDjdcbByDjh(djh);
        if (nydDO != null && StringUtils.isNotBlank(nydDO.getNydDjdcbIndex())) {
            DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
            // 查询农用地调查表实体
            NydDjdcbResponseDTO nydDTO = new NydDjdcbResponseDTO();
            BuildingUtils.copyPropertiesWithOutNull(nydDO, nydDTO);

            // 查询 农用地权利人
            List<NydQlrDO> nydQlrDOList = nydQlrService.listNydQlrByDjh(nydDO.getDjh());
            List<DjxxQlrDTO> qlrDTOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(nydQlrDOList)) {
                for (NydQlrDO nydQlrDO : nydQlrDOList) {
                    qlrDTOList.add(revertQlrDOToDTO(nydQlrDO));
                }
            }
            // 保存 农用地权利人
            djxxResponseDTO.setQlrList(qlrDTOList);

            initCbzdDjxx(bdcdyh,cbzdDcbcbfrelIndex,nydDTO);

            djxxResponseDTO.setDjDcbResponseDTO(nydDTO);
            return djxxResponseDTO;
        }
        return null;
    }


    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据不动产单元号查询 备份表的地籍信息
     */
    @Override
    public DjxxResponseDTO getHDjxxForInitByBdcdyh(String bdcdyh) {
        String djh = BuildingUtils.getDjhByBdcdyh(bdcdyh);
        NydDjdcbDO nydDO = nydService.queryHNydDjdcbByDjh(djh);
        if (nydDO != null && StringUtils.isNotBlank(nydDO.getNydDjdcbIndex())) {
            DjxxResponseDTO djxxResponseDTO = new DjxxResponseDTO();
            // 查询农用地调查表实体
            NydDjdcbResponseDTO nydDTO = new NydDjdcbResponseDTO();
            BuildingUtils.copyPropertiesWithOutNull(nydDO, nydDTO);

            // 查询 农用地权利人
            List<HNydQlrDO> nydQlrDOList = nydQlrService.listHNydQlrByDcbIndex(nydDO.getNydDjdcbIndex());
            List<DjxxQlrDTO> qlrDTOList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(nydQlrDOList)) {
                for (NydQlrDO nydQlrDO : nydQlrDOList) {
                    qlrDTOList.add(revertQlrDOToDTO(nydQlrDO));
                }
            }
            // 保存 农用地权利人
            djxxResponseDTO.setQlrList(qlrDTOList);

            // 判断是否需要处理承包宗地调查表  处理承包宗地 地籍信息相关属性字段
            if (TzmUtils.hasCbzdDcb(bdcdyh)) {
                initHCbzdDjxx(djh, nydDTO);
            }

            // 处理林权 地籍信息相关属性字段
            if (TzmUtils.hasLqDcb(bdcdyh)) {
                initHLqDjxx(djh, nydDTO, djxxResponseDTO);
            }
            djxxResponseDTO.setDjDcbResponseDTO(nydDTO);
            return djxxResponseDTO;
        }
        return null;
    }

    /**
     * @param doMap
     * @return java.util.Map<java.lang.String               ,               java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理 DO 转成  DTO Map
     */
    private static Map<String, Object> revertDOToDTOMap(Map<String, Object> doMap, String prefix) {
        Map<String, Object> revertMap = new HashMap<>();
        if (MapUtils.isNotEmpty(doMap)) {
            Iterator iterator = doMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = entry.getKey().toString();
                Object val = entry.getValue();
                if (val != null) {
                    if (!StringUtils.startsWith(key, prefix)) {
                        key = prefix + key;
                    }
                    revertMap.put(key, val);
                }
            }
        }
        return revertMap;
    }

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> beanName = new HashSet<>(1);
        beanName.add(Constants.DJXX_SERVICE_NYD);
        return beanName;
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.NydDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 判断BDCDYH，如果构筑物特征码是W 则直接查询NYD_DJDCB，
     * 否则截取地籍号搜索 针对使用构筑物BDCDYH查询农用地基本信息场景
     */
    @Override
    public NydDjdcbDO queryDjdcbByBdcdyhOrDjh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            if (TzmUtils.isTdBdcdy(bdcdyh)) {
                return nydService.queryNydDjdcbByBdcdyh(bdcdyh);
            } else {
                return nydService.queryNydDjdcbByDjh(BuildingUtils.getDjhByBdcdyh(bdcdyh));
            }
        }
        return null;
    }
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO
     * @description 根据不动产单元号查询权利人和调查表信息
     */
    @Override
    public DjDcbAndQlrResponseDTO queryDjdcbAndQlrByBdcdyh(String bdcdyh,boolean withQlr) {
        DjDcbAndQlrResponseDTO dto=new DjDcbAndQlrResponseDTO();
        if(StringUtils.isNotBlank(bdcdyh)){
            NydDjdcbDO nydDO=queryDjdcbByBdcdyhOrDjh(bdcdyh);
            if(nydDO!=null){
                entityZdConvertUtils.convertEntityToMc(nydDO);
                NydDjdcbResponseDTO nydDTO = new NydDjdcbResponseDTO();
                BuildingUtils.copyPropertiesWithOutNull(nydDO, nydDTO);
                if (TzmUtils.hasCbzdDcb(bdcdyh)) {
                    CbzdDcbDO cbzdDcbDO =  cbzdService.queryCdzdDcbByBdcdyh(bdcdyh);
                    if(cbzdDcbDO != null){
                        nydDTO.setCbzdDcbIndex(cbzdDcbDO.getCbzdDcbIndex());
                    }

                }
                dto.setDjDcbResponseDTO(nydDTO);
            }
            if(withQlr){
                List<NydQlrDO> qlrDO=nydQlrService.listNydQlrByDjh(bdcdyh.substring(0,19));
                List<DjdcbQlrResponseDTO> qlrResponseDTOS=new ArrayList<>();
                if(qlrDO!=null){
                    for (NydQlrDO nydQlrDO:qlrDO){
                        entityZdConvertUtils.convertEntityToMc(nydQlrDO);
                        DjdcbQlrResponseDTO djdcbQlrResponseDTO=new DjdcbQlrResponseDTO();
                        BuildingUtils.copyPropertiesWithOutNull(nydQlrDO, djdcbQlrResponseDTO);
                        qlrResponseDTOS.add(djdcbQlrResponseDTO);
                    }
                    dto.setQlrResponseDTOList(qlrResponseDTOS);
                }
            }
        }
        return dto;
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据djh查询权属调查信息
     */
    @Override
    public ZdQsdcDO queryQsdcByDjh(String djh) {
        return zdQsdcService.queryQsdcByDjh(djh,"nyd_qsdc");
    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询 是否 需要查询NYD_DJDCB数据
     */
    @Override
    public boolean checkNeedDjdcb(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh) && NydDjdcbDO.class.equals(TzmUtils.getDjdcbDoWithBdcdyh(bdcdyh))) {
            return true;
        }
        return false;
    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询 是否需要查询 NYD_QLR数据
     */
    @Override
    public boolean checkNeedDjQlr(String bdcdyh) {
        return TzmUtils.isTdBdcdy(bdcdyh) && checkNeedDjdcb(bdcdyh);
    }

    /**
     * @param bdcdyh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 给 农用地权利人set外键值
     * BdcdyxxEnum.java 反射调用方法
     */
    @Override
    public void setDjQlrFkVal(String bdcdyh, NydQlrDO nydQlrDO) {
        if (StringUtils.isNotBlank(bdcdyh) && nydQlrDO != null) {
            // 根据BDCDYH 查询 ZD_DJDCB
            NydDjdcbDO nydDjdcbDO = nydService.queryNydDjdcbByBdcdyh(bdcdyh);
            if (nydDjdcbDO != null) {
                nydQlrDO.setDjdcbIndex(nydDjdcbDO.getNydDjdcbIndex());
                nydQlrDO.setDjh(nydDjdcbDO.getDjh());
            }
        }
    }

    /**
     * @param withQlr
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取查询BDCDY的SQL
     */
    @Override
    public String getBdcdyQuerySql(boolean withQlr,String qlrmh) {
        String sql = BdcdySqlConstants.NYD_DJDCB_SQL;
        if(withQlr){
            if(BdcdySqlConstants.relationDjxxQlrWithFk()){
                sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.NYD_DJDCB_QLR_SQL_WITHFK,qlrmh);
            }else{
                sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.NYD_DJDCB_QLR_SQL,qlrmh);
            }
        }
        sql +=BdcdySqlConstants.NO_LQ_DCB;
        return sql;
    }

    @Override
    public String getLqBdcdyQuerySql(boolean withQlr,String qlrmh){
        String sql =BdcdySqlConstants.LQ_DCB_SQL;
        String cbzdsql =BdcdySqlConstants.LQ_DCB_SQL;
        if(withQlr){
            cbzdsql +=BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.LQ_DCB_CBZD_QLR_SQL,qlrmh);
            //承包宗地权利人取值不同
            if(BdcdySqlConstants.relationDjxxQlrWithFk()){
                sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.LQ_DCB_QLR_SQL_WITHFK,qlrmh);

            }else{
                sql += BdcdySqlConstants.dealWithQlrSql(BdcdySqlConstants.LQ_DCB_QLR_SQL,qlrmh);
            }
            sql += " UNION ALL "+cbzdsql;
        }
        return sql;
    }
}
