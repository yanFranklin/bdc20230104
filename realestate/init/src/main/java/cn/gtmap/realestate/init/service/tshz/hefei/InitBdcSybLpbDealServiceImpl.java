package cn.gtmap.realestate.init.service.tshz.hefei;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.dto.building.ZdDjdcbResponseDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 同步权籍数据时特殊处理
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcSybLpbDealServiceImpl_hefei")
public class InitBdcSybLpbDealServiceImpl implements InitBdcTsHzService {
    private static Logger logger = LoggerFactory.getLogger(InitBdcSybLpbDealServiceImpl.class);

    /**
     * 房地产权同步权籍时候匹配fw_hs的ghyt和zd_djdcb的土地用途
     */
    @Value("${synclpb.pptdyt:false}")
    private Boolean synclpbPptdyt;

    /**
     * fw_hs的ghyt和zd_djdcb的tdyt匹配关系
     */
    @Value("#{${synclpb.ppgx: null}}")
    private Map<String, String> synclpbPpgx;

    /**
     * 同步权籍数据时特殊处理
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //同步权籍数据时特殊处理
        if(initServiceQO.isSfzqlpbsj() && initServiceDTO!=null && initServiceDTO.getBdcXm()!=null){
             if(initServiceQO.getDjxxResponseDTO()!=null &&  initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO() instanceof ZdDjdcbResponseDTO){
                 ZdDjdcbResponseDTO zdDjdcbResponseDTO= (ZdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO();
                 //实测面积有值
                 if(zdDjdcbResponseDTO.getScmj()!=null && zdDjdcbResponseDTO.getScmj()>0){
                     if(initServiceDTO.getBdcQl() instanceof BdcFdcqDO){
                         ((BdcFdcqDO) initServiceDTO.getBdcQl()).setTdsyqmj(zdDjdcbResponseDTO.getScmj());
                         initServiceDTO.getBdcXm().setZdzhmj(zdDjdcbResponseDTO.getScmj());

                         this.resolveTdytAndQzrq(initServiceQO, initServiceDTO);
                     }else if(initServiceDTO.getBdcQl() instanceof BdcJsydsyqDO){
                         ((BdcJsydsyqDO) initServiceDTO.getBdcQl()).setSyqmj(zdDjdcbResponseDTO.getScmj());
                         initServiceDTO.getBdcXm().setZdzhmj(zdDjdcbResponseDTO.getScmj());
                     }
                 }
             }
        }
        return initServiceDTO;
    }

    /**
     * 重新处理从权籍读取的土地用途和土地使用起止时间
     * （需求45850：房地产权同步权籍时候匹配fw_hs的ghyt和zd_djdcb的土地用途，将匹配的用途及对应土地使用起始时间优先设置到项目表zdzhyt和房地产权表tdsyqssj、tdsyjssj）
     *
     * @param initServiceQO 初始化查询信息
     * @param initServiceDTO 初始化结果信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private void resolveTdytAndQzrq(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) {
        try {
            if (Boolean.TRUE.equals(synclpbPptdyt) && MapUtils.isNotEmpty(synclpbPpgx) && Boolean.TRUE.equals(isFwHsTdytNull(initServiceQO))) {
                List<ZdDjdcbDO> zdxx = splitZdxx((ZdDjdcbResponseDTO) initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO());
                String fwhsGhyt = initServiceQO.getBdcdyResponseDTO().getGhyt();

                // 获取配置中房屋户室规划用途匹配的宗地地籍调查表土地用途
                String ppZdDjdcb = MapUtils.getString(synclpbPpgx, fwhsGhyt);

                // 从当前宗地地籍调查表中查找匹配的土地用途
                if (StringUtils.isNotBlank(ppZdDjdcb)) {
                    Set<String> ppZdDjdcbSet = Stream.of(ppZdDjdcb.split(CommonConstantUtils.ZF_YW_DH)).collect(Collectors.toSet());

                    List<Integer> indexList = new ArrayList<>(3);
                    boolean pp = false;
                    for (int i = 0; i < zdxx.size(); i++) {
                        if (ppZdDjdcbSet.contains(zdxx.get(i).getTdyt()) && !pp) {
                            // 优先采用第一个匹配的土地用途及起止日期
                            indexList.add(0, i);
                            pp = true;
                        } else {
                            indexList.add(i);
                        }
                    }
                    this.setTdytAndQzrq(initServiceDTO, zdxx, indexList);
                }
            }
        } catch (Exception e) {
            // 这里异常不抛出，即使该功能处理异常不影响整体逻辑执行
            logger.error("重新处理从权籍读取的土地用途和土地使用起止时间异常，对应项目受理编号：{}", initServiceDTO.getBdcXm().getSlbh());
        }
    }

    /**
     * 判断房屋户室土地用途是否为空
     * @param initServiceQO 初始化查询信息
     * @return true: 空 ， false：不为空
     */
    private boolean isFwHsTdytNull(InitServiceQO initServiceQO) {
        return null == initServiceQO.getBdcdyResponseDTO() || StringUtils.isBlank(initServiceQO.getBdcdyResponseDTO().getTdyt());
    }

    /**
     * 拆分宗地信息（将宗地用途123和对应的土地使用起止时间分别提取出来）
     * @param djdcb 地籍调查表信息
     * @return {List} 宗地土地用途等信息
     */
    private List<ZdDjdcbDO> splitZdxx(ZdDjdcbResponseDTO djdcb) {
        List<ZdDjdcbDO> result = new ArrayList<>();

        ZdDjdcbDO zdDjdcbDO = new ZdDjdcbDO();
        zdDjdcbDO.setTdyt(djdcb.getTdyt());
        zdDjdcbDO.setQsrq(djdcb.getQsrq());
        zdDjdcbDO.setZzrq(djdcb.getZzrq());
        result.add(zdDjdcbDO);

        ZdDjdcbDO zdDjdcbDO2 = new ZdDjdcbDO();
        zdDjdcbDO2.setTdyt(djdcb.getTdyt2());
        zdDjdcbDO2.setQsrq(djdcb.getQsrq2());
        zdDjdcbDO2.setZzrq(djdcb.getZzrq2());
        result.add(zdDjdcbDO2);

        ZdDjdcbDO zdDjdcbDO3 = new ZdDjdcbDO();
        zdDjdcbDO3.setTdyt(djdcb.getTdyt3());
        zdDjdcbDO3.setQsrq(djdcb.getQsrq3());
        zdDjdcbDO3.setZzrq(djdcb.getZzrq3());
        result.add(zdDjdcbDO3);

        return result;
    }

    /**
     * 重新设值土地用途和起止时间
     * @param initServiceDTO 初始化结果信息
     * @param zdxx 地籍调查表信息
     * @param indexList 按照匹配的土地用途优先排序的zdxx索引
     */
    private void setTdytAndQzrq(InitServiceDTO initServiceDTO, List<ZdDjdcbDO> zdxx, List<Integer> indexList) {
        initServiceDTO.getBdcXm().setZdzhyt(zdxx.get(indexList.get(0)).getTdyt());
        initServiceDTO.getBdcXm().setZdzhyt2(zdxx.get(indexList.get(1)).getTdyt());
        initServiceDTO.getBdcXm().setZdzhyt3(zdxx.get(indexList.get(2)).getTdyt());

        BdcFdcqDO fdcqDO = (BdcFdcqDO) initServiceDTO.getBdcQl();
        fdcqDO.setTdsyqssj(zdxx.get(indexList.get(0)).getQsrq());
        fdcqDO.setTdsyjssj(zdxx.get(indexList.get(0)).getZzrq());

        fdcqDO.setTdsyqssj2(zdxx.get(indexList.get(1)).getQsrq());
        fdcqDO.setTdsyjssj2(zdxx.get(indexList.get(1)).getZzrq());

        fdcqDO.setTdsyqssj3(zdxx.get(indexList.get(2)).getQsrq());
        fdcqDO.setTdsyjssj3(zdxx.get(indexList.get(2)).getZzrq());
    }
}
