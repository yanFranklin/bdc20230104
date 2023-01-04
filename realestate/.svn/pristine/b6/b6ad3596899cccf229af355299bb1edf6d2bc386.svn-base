package cn.gtmap.realestate.inquiry.util;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.util.CommonConstantUtils.*;
import static cn.gtmap.realestate.common.util.StringToolUtils.joinBeanAttribute;
import static cn.gtmap.realestate.common.util.StringToolUtils.resolveBeanToAppendStr;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/11/07
 * @description  房产证明公共处理类
 */
@Service
public class BdcFczmUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFczmUtils.class);

    @Autowired
    private EntityMapper entityMapper;

    /**
     * 人员信息
     */
    @Autowired
    private UserManagerUtils userManagerUtils;

    /**
     * 有房无房证明查询哪些限制状态，多个英文逗号分割，目前支持: cf 查封 dya 抵押  yg 预告  yy 异议
     */
    @Value("${zhcx.yfwfzm.xzztcx:}")
    private String xzztcx;

    /**
     * 台账查询无需进行区域过滤的角色（例如可以查询全市范围数据的角色）
     */
    @Value("#{'${qxdm.nofilter.code:}'.split(',')}")
    private List<String> qxdmNoFilterCode;

    /**
     * 有房无房证明是否合并相同房产：true 同一个房产只显示一条记录，权利人这些信息合并  false 不合并
     */
    @Value("${zhcx.yfwfzm.sfhbxtfc:false}")
    private Boolean sfhbxtfc;


    /**
     * 合并相同房产数据（例如权利人家庭查询出有房无房证明，同一套房子要求只显示一条记录）
     * @param zfxxDTOList 住房信息
     * @return {List} 合并后的房产信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public List<BdcZfxxDTO> hbxtfc(List<BdcZfxxDTO> zfxxDTOList) {
        if(null == sfhbxtfc || !sfhbxtfc) {
            return zfxxDTOList;
        }

        if(CollectionUtils.isEmpty(zfxxDTOList)) {
            return zfxxDTOList;
        }

        // 按照bdcdyh合并房产数据
        Map<String, List<BdcZfxxDTO>> zfxxMap = zfxxDTOList.stream().collect(Collectors.groupingBy(BdcZfxxDTO::getBdcdyh));
        if(MapUtils.isEmpty(zfxxMap)) {
            return zfxxDTOList;
        }

        List<BdcZfxxDTO> hbZfxxDTOList = new ArrayList<>();
        for(Map.Entry<String, List<BdcZfxxDTO>> zfxxEntry : zfxxMap.entrySet()) {
            // 主要权利人信息需要合并
            String hbQlrmc = joinBeanAttribute(zfxxEntry.getValue(), "getQlrmc", ZF_YW_XG);
            String hbQlrzjh = joinBeanAttribute(zfxxEntry.getValue(), "getQlrzjh", ZF_YW_XG);
            String hbBdcqzh = joinBeanAttribute(zfxxEntry.getValue(), "getBdcqzh", ZF_YW_XG);

            BdcZfxxDTO hbZfxxDTO = zfxxEntry.getValue().get(0);
            hbZfxxDTO.setQlrmc(hbQlrmc);
            hbZfxxDTO.setQlrzjh(hbQlrzjh);
            hbZfxxDTO.setBdcqzh(hbBdcqzh);
            hbZfxxDTOList.add(hbZfxxDTO);
        }
        return hbZfxxDTOList;
    }

    /**
     * 查询房产对应限制信息
     * @param zfxxDTOList 住房信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public void setXzzt(List<BdcZfxxDTO> zfxxDTOList) {
        if(CollectionUtils.isEmpty(zfxxDTOList) || StringUtils.isBlank(xzztcx)) {
            return;
        }

        for(BdcZfxxDTO bdcZfxxDTO : zfxxDTOList) {
            if(StringUtils.isBlank(bdcZfxxDTO.getBdcdyh())) {
                continue;
            }

            for(String xzzt : xzztcx.split(ZF_YW_DH)) {
                // cf 查封 dya 抵押  yg 预告 yy 异议
                if("cf".equals(xzzt)) {
                    bdcZfxxDTO.setSfcf(this.isXzzt(new BdcCfDO(), bdcZfxxDTO.getBdcdyh()));
                }
                if("dya".equals(xzzt)) {
                    bdcZfxxDTO.setSfdya(this.isXzzt(new BdcDyaqDO(), bdcZfxxDTO.getBdcdyh()));
                }
                if("yg".equals(xzzt)) {
                    bdcZfxxDTO.setSfyg(this.isXzzt(new BdcYgDO(), bdcZfxxDTO.getBdcdyh()));
                }
                if("yy".equals(xzzt)) {
                    bdcZfxxDTO.setSfyy(this.isXzzt(new BdcYyDO(), bdcZfxxDTO.getBdcdyh()));
                }
            }
        }
    }

    /**
     * 查询是否存在限制状态
     * @param obj 查询表
     * @param bdcdyh 不动产单元号
     * @return 存在 true，不存在 false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public <T> boolean isXzzt(T obj, String bdcdyh) {
        Example example = new Example(obj.getClass());
        example.createCriteria().andEqualTo("bdcdyh", bdcdyh).andEqualTo("qszt", 1);
        int num = entityMapper.countByExample(example);
        return num > 0;
    }

    /**
     * 判断当前用户是否有异地查询角色
     * @param bdcQlrQO 查询参数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public void isYdcxjs(BdcQlrQO bdcQlrQO) {
        if(CollectionUtils.isEmpty(qxdmNoFilterCode)) {
            return;
        }

        Set<String> roleNameSet = userManagerUtils.getCurrentUserRoleNameSet();
        if(CollectionUtils.isEmpty(roleNameSet)) {
            return;
        }

        for(String role : qxdmNoFilterCode) {
            if(roleNameSet.contains(role)) {
                bdcQlrQO.setYdcxjs(true);
            }
        }
    }

    /**
     * 字符串按照指定长度切分长度
     * @param str 字符串
     * @param splitLength 分隔长度
     * @return {String} 添加了换行符号的字符串
     */
    public String addNewLine(String str, Integer splitLength) {
        if(StringUtils.isBlank(str) || null == splitLength || splitLength <= 0) {
            return str;
        }

        String[] strArray = stringToStringArray(str, splitLength);
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < strArray.length; i++){
            if(i == 0){
                builder.append(strArray[i]);
            }else{
                builder.append(PDF_HHF).append(strArray[i]);
            }
        }
        return builder.toString();
    }

    public static String[] stringToStringArray(String text, int length) {
        //检查参数是否合法
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        if (length <= 0) {
            return null;
        }
        //获取整个字符串可以被切割成字符子串的个数
        int n = (text.length() + length - 1) / length;
        String[] splitArr = new String[n];
        for (int i = 0; i < n; i++) {
            if (i < (n - 1)) {
                splitArr[i] = text.substring(i * length, (i + 1) * length);
            } else {
                splitArr[i] = text.substring(i * length);
            }
        }
        return splitArr;
    }
}
