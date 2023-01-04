package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/10/24
 * @description 用于规则验证提示信息封装的工具类
 */
public class BdcGzyzTsxxUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzyzTsxxUtils.class);
    /**
     * 验证后信息整理
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param listBdcGzYzTsxx
     *@return StringBuilder
     *@description
     */
    public static List<BdcGzyzVO> checkTsxx(List<BdcGzYzTsxxDTO> listBdcGzYzTsxx){
        List<BdcGzyzVO> bdcGzyzVOS=Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(listBdcGzYzTsxx)) {
            for (BdcGzYzTsxxDTO bdcGzYzTsxxDTO : listBdcGzYzTsxx) {
                if(bdcGzYzTsxxDTO==null){
                    continue;
                }
                List<BdcGzZgzTsxxDTO> zgzTsxxDTOS=bdcGzYzTsxxDTO.getZgzTsxxDTOList();
                LOGGER.debug("规则子系统规则校验 ：提示信息 {}",JSONObject.toJSONString(zgzTsxxDTOS));
                if (CollectionUtils.isEmpty(zgzTsxxDTOS)) {
                    continue;
                }
                bdcGzyzVOS.addAll(distinctTsxx(zgzTsxxDTOS));
            }
        }
        return bdcGzyzVOS;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  其他验证子规则提示整理
     */
    public static List<BdcGzyzVO> checkZgzTsxx(List<BdcGzZgzTsxxDTO> bdcGzZgzTsxxDTOList){
        List<BdcGzyzVO> bdcGzyzVOS=Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(bdcGzZgzTsxxDTOList)) {
            LOGGER.debug("规则子系统规则校验 ：提示信息 {}",JSONObject.toJSONString(bdcGzZgzTsxxDTOList));
            bdcGzyzVOS.addAll(distinctTsxx(bdcGzZgzTsxxDTOList));
        }
        return bdcGzyzVOS;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 组织去重 提示信息
     */
    private static List<BdcGzyzVO> distinctTsxx(List<BdcGzZgzTsxxDTO> tsxxDTOList){
        List<BdcGzyzVO> bdcGzyzVOList=Lists.newArrayList();
        //去重tsxx集合
        Set<String> tsxxSet=new HashSet<>();
        for (BdcGzZgzTsxxDTO bdcGzZgzTsxxDTO : tsxxDTOList) {
            if (bdcGzZgzTsxxDTO == null || CollectionUtils.isEmpty(bdcGzZgzTsxxDTO.getTsxx())) {
                continue;
            }
            List<String> tsxxList = bdcGzZgzTsxxDTO.getTsxx();
            StringBuilder returnvalue = new StringBuilder();
            // 去除 ZDDYH 后缀
            tsxxList = tsxxList.stream().map(tsxx -> {
                if (StringUtils.endsWith(tsxx, "ZDDYH")) {
                    return StringUtils.remove(tsxx, "ZDDYH");
                }
                return tsxx;
            }).collect(Collectors.toList());
            // 去重
            if (tsxxList.size() == 1 && tsxxSet.contains(tsxxList.get(0))) {
                continue;
            }
            //循环处理
            for (String tsxx : tsxxList) {
                LOGGER.debug("规则子系统规则校验 ：提示信息 {}", JSONObject.toJSONString(tsxx));
                if (StringUtils.isNotBlank(tsxx) && !tsxxSet.contains(tsxx)) {
                    //去重tsxx
                    tsxxSet.add(tsxx);
                    if (StringUtils.isNotBlank(returnvalue)) {
                        returnvalue.append("<br>").append(tsxx);
                    } else {
                        returnvalue.append(tsxx);
                    }
                }
            }
            BdcGzyzVO bdcGzyzVO=new BdcGzyzVO();
            bdcGzyzVO.setYzlx(bdcGzZgzTsxxDTO.getYxj());
            bdcGzyzVO.setTsxx(returnvalue.toString());
            bdcGzyzVO.setGzid(bdcGzZgzTsxxDTO.getGzid());
            bdcGzyzVO.setGzmc(bdcGzZgzTsxxDTO.getGzmc());
            bdcGzyzVO.setRequestParam(JSON.toJSONString(bdcGzZgzTsxxDTO.getParam()));
            bdcGzyzVO.setResponseData(JSON.toJSONString(bdcGzZgzTsxxDTO.getSjljg()));
            bdcGzyzVOList.add(bdcGzyzVO);
        }
        return bdcGzyzVOList;
    }
}
