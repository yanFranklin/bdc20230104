package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.service.DjxxService;
import cn.gtmap.realestate.building.service.TuxknrService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.TuxknrDO;
import cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.TuxknrResponseDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-24
 * @description
 */
@Service
public class TuxknrServiceImpl implements TuxknrService {

    @Autowired
    private EntityMapper entityMapper;

    @Value("${tuxknr.downurl:}")
    public String tuxknrDownurl;

    @Autowired
    private DjxxService djxxService;


    /**
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 是否需要读取 tuxknr 表
     */
    @Override
    public boolean needReadTuxknr() {
        if(StringUtils.isNotBlank(tuxknrDownurl) &&
                StringUtils.equals(EnvironmentConfig.getEnvironment().getProperty("sysversion"), Constants.SYSVERSION_NANTONG)){
            return true;
        }
        return false;
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.TuxknrResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH 查询 Tuxknr表
     */
    @Override
    public List<TuxknrResponseDTO> listTuxknrByDjh(String djh) {
        List<TuxknrResponseDTO> tuxList = new ArrayList<>();
        if(StringUtils.isBlank(tuxknrDownurl)){
            return tuxList;
        }
        Example example = new Example(TuxknrDO.class);
        example.createCriteria().andEqualTo("tumc",djh);
        List<TuxknrDO> tuxknrDOS = entityMapper.selectByExample(example);
        // 如果为空 可能是老地籍号 重新查一遍
        if(CollectionUtils.isEmpty(tuxknrDOS)){
            DjDcbResponseDTO djDcbResponseDTO = djxxService.getDjdcbDTOByBdcdyh(djh + Constants.TD_BDCDYH_SUFIX);
            if(djDcbResponseDTO != null){
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(djDcbResponseDTO));
                String ydjh = jsonObject.getString("ydjh");
                if(StringUtil.isNotBlank(ydjh)){
                    ydjh = revertDjh(ydjh);
                    example = new Example(TuxknrDO.class);
                    example.createCriteria().andEqualTo("tumc",ydjh);
                    tuxknrDOS = entityMapper.selectByExample(example);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(tuxknrDOS)){
            for(TuxknrDO tuxknrDO : tuxknrDOS){
                // 循环处理 URL
                TuxknrResponseDTO dto = setUrl(tuxknrDO);
                tuxList.add(dto);
            }
        }
        return tuxList;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param tuxknrDO
     * @return cn.gtmap.realestate.common.core.dto.building.TuxknrResponseDTO
     * @description 填充全URL
     */
    private TuxknrResponseDTO setUrl(TuxknrDO tuxknrDO){
        TuxknrResponseDTO dto = new TuxknrResponseDTO();
        BeanUtils.copyProperties(tuxknrDO,dto);
        dto.setUrl(tuxknrDownurl+tuxknrDO.getFileid());
        return dto;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.lang.String
     * @description 处理待中间横杠的 DJH  保留前三个横杠
     */
    private static String revertDjh(String djh){
        String[] djhArr = djh.split("-");
        StringBuilder djhSb = new StringBuilder("");
        for(int i = 0;i < djhArr.length;i++){
            if(i < 4){
                djhSb.append(djhArr[i]);
            }else{
                djhSb.append("-").append(djhArr[i]);
            }
        }
        return djhSb.toString();
    }
}
