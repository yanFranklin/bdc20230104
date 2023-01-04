package cn.gtmap.realestate.engine.util;

import cn.gtmap.realestate.common.core.domain.BdcGzyzLogDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @version 1.0, 2022/04/12
 * @description 异步工具类
 */
@Component
public class AsyncUtil {

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param bdcGzYzQO 批量组合规则出参
     * @param bdcGzZhgzDTO
     * @param  exception 异常信息
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 批量规则验证日志，规则验证异常记录日志
     */
    @Async
    public void saveGzyzLogException(BdcGzYzQO bdcGzYzQO, BdcGzZhgzDTO bdcGzZhgzDTO, String exception) {
        String paramListString = JSON.toJSONString(bdcGzYzQO.getParamList());

        BdcGzyzLogDO bdcGzyzLogDO = new BdcGzyzLogDO();
        Date date = new Date();
        bdcGzyzLogDO.setRzid(UUIDGenerator.generate16());
        bdcGzyzLogDO.setYzrid(bdcGzYzQO.getYzrid());
        bdcGzyzLogDO.setYzrzh(bdcGzYzQO.getYzrzh());
        bdcGzyzLogDO.setYzsj(date);
        bdcGzyzLogDO.setZhbs(bdcGzZhgzDTO.getZhbs());
        bdcGzyzLogDO.setZhmc(bdcGzZhgzDTO.getZhmc());
        bdcGzyzLogDO.setYzbs(UUIDGenerator.generate16());
        bdcGzyzLogDO.setGzid("");
        bdcGzyzLogDO.setGzmc("");
        bdcGzyzLogDO.setYzjg(CommonUtil.subLongStr(exception));
        bdcGzyzLogDO.setSftg(2);
        bdcGzyzLogDO.setYzcs(paramListString);
        this.saveBdcGzyzLog(bdcGzyzLogDO);
    }

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @param bdcGzYzQO 批量验证入参
     * @param bdcGzYzTsxxDTOList 批量验证出参
     * @param bdcGzZhgzDTO 组合规则DTO
     * @description 批量规则验证日志记录
     */
    @Async
    public void saveBdcGzyzLog(BdcGzYzQO bdcGzYzQO, List<BdcGzYzTsxxDTO> bdcGzYzTsxxDTOList, BdcGzZhgzDTO bdcGzZhgzDTO)  {
        //不同的验证日志场景，相同的日志记录字段信息；
        String zhbs = bdcGzYzQO.getZhbs();
        String zhmc = bdcGzZhgzDTO.getZhmc();
        Date date = new Date();
        String yzrid = bdcGzYzQO.getYzrid();
        String yzrzh = bdcGzYzQO.getYzrzh();
        // 生成验证标识，用于标识同一组合规则下的子规则内容
        String yzbs = UUIDGenerator.generate16();

        //获取所有子规则Map
        List<Map<String, Object>> bdcGzZgzMapListAll = this.getZgzMapList(bdcGzZhgzDTO);

        //获得入参中的所有验证参数；
        List<Map<String, Object>> paramListAll = bdcGzYzQO.getParamList();

        //获得所有的验证单位，每一个paramMap参数和每一个子规则构成一个验证单位；
        Set<BdcGzYzUnit> yzUnitAll = new HashSet<>();
        for (Map<String, Object> paramMap : paramListAll){
            for (Map<String, Object> bdcGzZgzMap : bdcGzZgzMapListAll){
                //生成一个验证单位;
                BdcGzYzUnit yzUnit = new BdcGzYzUnit();
                yzUnit.setGzid((String)bdcGzZgzMap.get("gzid"));
                yzUnit.setGzmc((String)bdcGzZgzMap.get("gzmc"));
                yzUnit.setParamMap(paramMap);

                yzUnitAll.add(yzUnit);
            }
        }

        //未通过的验证单位日志记录集合；
        List<BdcGzyzLogDO> bdcGzyzLogDONotPassedList = new ArrayList<>();

        //获取所有的未通过的验证单位信息；
        Set<BdcGzYzUnit> yzUnitNotPassed = new HashSet<>();
        for (BdcGzYzTsxxDTO bdcGzYzTsxxDTO : bdcGzYzTsxxDTOList) {
            Map<String, Object> paramMap = bdcGzYzTsxxDTO.getParamMap();//yzcs
            List<BdcGzZgzTsxxDTO> bdcGzZgzTsxxDTOList = bdcGzYzTsxxDTO.getZgzTsxxDTOList();
            for (BdcGzZgzTsxxDTO bdcGzZgzTsxxDTO : bdcGzZgzTsxxDTOList){
                //生成一个验证单位;
                BdcGzYzUnit yzUnit = new BdcGzYzUnit();
                yzUnit.setParamMap(paramMap);
                yzUnit.setGzid(bdcGzZgzTsxxDTO.getGzid());
                yzUnit.setGzmc(bdcGzZgzTsxxDTO.getGzmc());
                yzUnitNotPassed.add(yzUnit);//得到所有的验证未通过的验证单位

                //生成日志记录实体
                BdcGzyzLogDO bdcGzyzLogDO = new BdcGzyzLogDO();
                bdcGzyzLogDO.setRzid(UUIDGenerator.generate16());
                bdcGzyzLogDO.setYzrid(yzrid);
                bdcGzyzLogDO.setYzrzh(yzrzh);
                bdcGzyzLogDO.setYzsj(date);
                bdcGzyzLogDO.setZhbs(zhbs);
                bdcGzyzLogDO.setZhmc(zhmc);
                bdcGzyzLogDO.setYzbs(yzbs);
                bdcGzyzLogDO.setGzid(bdcGzZgzTsxxDTO.getGzid());
                bdcGzyzLogDO.setGzmc(bdcGzZgzTsxxDTO.getGzmc());
                bdcGzyzLogDO.setYzcs(CommonUtil.subLongStr(JSON.toJSONString(paramMap)));
                bdcGzyzLogDO.setYzjg(CommonUtil.subLongStr(JSON.toJSONString(bdcGzZgzTsxxDTO.getTsxx())));
                bdcGzyzLogDO.setSftg(0);

                bdcGzyzLogDONotPassedList.add(bdcGzyzLogDO);//得到所有未通过验证的验证日志对象；
            }
        }
        //对未通过的验证单位进行批量入库操作；；
        this.saveBatchBdcGzyzLog(bdcGzyzLogDONotPassedList);

        //两个集合取差集，获得所有通过的验证单位；
        Set<BdcGzYzUnit> yzUnitPassed = new HashSet<>(yzUnitAll);
        yzUnitPassed.removeAll(yzUnitNotPassed);


        //对所有通过的验证单位，生成日志记录实体；
        List<BdcGzyzLogDO> bdcGzyzLogDOPassedList = new ArrayList<>();
        for (BdcGzYzUnit yzUnit : yzUnitPassed){
            //对未通过的验证单位进行入库操作；
            BdcGzyzLogDO bdcGzyzLogDO = new BdcGzyzLogDO();
            bdcGzyzLogDO.setRzid(UUIDGenerator.generate16());
            bdcGzyzLogDO.setYzrid(yzrid);
            bdcGzyzLogDO.setYzrzh(yzrzh);
            bdcGzyzLogDO.setYzsj(date);
            bdcGzyzLogDO.setZhbs(zhbs);
            bdcGzyzLogDO.setZhmc(zhmc);
            bdcGzyzLogDO.setYzbs(yzbs);
            bdcGzyzLogDO.setGzid(yzUnit.getGzid());
            bdcGzyzLogDO.setGzmc(yzUnit.getGzmc());
            bdcGzyzLogDO.setYzcs(CommonUtil.subLongStr(JSON.toJSONString(yzUnit.getParamMap())));
            bdcGzyzLogDO.setYzjg("");
            bdcGzyzLogDO.setSftg(1);

            bdcGzyzLogDOPassedList.add(bdcGzyzLogDO);
        }
        //对所有通过的验证单位，批量保存日志记录实体；
        this.saveBatchBdcGzyzLog(bdcGzyzLogDOPassedList);
    }

    private List<Map<String, Object>> getZgzMapList(BdcGzZhgzDTO bdcGzZhgzDTO){
        //组合规则关联的所有子规则DTO信息；
        List<BdcGzZgzDTO> bdcGzZgzDTOListAll = bdcGzZhgzDTO.getBdcGzZgzDTOList();
        //把所有的子规则放到Map中
        List<Map<String, Object>> bdcGzZgzMapListAll = new ArrayList<>();
        for (BdcGzZgzDTO bdcGzZgzDTO : bdcGzZgzDTOListAll) {
            Map<String, Object> bdcGzZgzMap = new HashMap<>();
            bdcGzZgzMap.put("gzid", bdcGzZgzDTO.getGzid());
            bdcGzZgzMap.put("gzmc", bdcGzZgzDTO.getGzmc());
            bdcGzZgzMapListAll.add(bdcGzZgzMap);
        }
        return bdcGzZgzMapListAll;
    }

    /**
     * @param bdcGzyzLogDO
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 批量规则验证日志，逐条入库
     */
    private int saveBdcGzyzLog(BdcGzyzLogDO bdcGzyzLogDO){
        //增加对待插入数据的判空操作；
        if (bdcGzyzLogDO != null && bdcGzyzLogDO.getRzid() != null){
            return entityMapper.insert(bdcGzyzLogDO);
        }
        return 0;
    }

    /**
     * @param bdcGzyzLogDOList
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 批量规则验证日志，批量保存入库
     */
    private int saveBatchBdcGzyzLog(List<BdcGzyzLogDO> bdcGzyzLogDOList){
        //增加对待插入数据的判空操作；
        if (CollectionUtils.isNotEmpty(bdcGzyzLogDOList)){
            entityMapper.batchSaveSelective(bdcGzyzLogDOList);
            return bdcGzyzLogDOList.size();
        }
        return 0;
    }

    /**
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 内部类，用于存放批量组合规则的一个验证单位
     */
    public class BdcGzYzUnit {
        /**
         * 调用规则时传入的参数
         */
        private Map<String, Object> paramMap;

        /**
         * 验证的子规则id
         */
        private String gzid;

        /**
         * 验证的子规则名称
         */
        private String gzmc;

        public Map<String, Object> getParamMap() {
            return paramMap;
        }

        public void setParamMap(Map<String, Object> paramMap) {
            this.paramMap = paramMap;
        }

        public String getGzid() {
            return gzid;
        }

        public void setGzid(String gzid) {
            this.gzid = gzid;
        }

        public String getGzmc() {
            return gzmc;
        }

        public void setGzmc(String gzmc) {
            this.gzmc = gzmc;
        }

        @Override
        public String toString() {
            return "BdcGzYzUnit{" +
                    "paramMap='" + paramMap + '\'' +
                    ", gzid='" + gzid + '\'' +
                    ", gzmc=" + gzmc +
                    '}';
        }

        @Override
        public int hashCode() {
            return this.gzid.hashCode()+this.gzmc.hashCode()+this.paramMap.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof BdcGzYzUnit) {
                BdcGzYzUnit bdcGzYzUnit = (BdcGzYzUnit) obj;
                return Objects.equals(this.gzid, bdcGzYzUnit.gzid) && Objects.equals(this.gzmc, bdcGzYzUnit.gzmc) && this.paramMap.equals(bdcGzYzUnit.paramMap);
            }
            return false;
        }
    }


}
