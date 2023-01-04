package cn.gtmap.realestate.natural.core.service.impl;

import cn.gtmap.realestate.common.core.dto.natural.ZrzyCqzhDTO;
import cn.gtmap.realestate.common.core.enums.BdcZslxEnum;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.natural.core.bo.ZrzyZscCqzhBO;
import cn.gtmap.realestate.natural.core.service.ZrzyZsDjcqzhService;
import cn.gtmap.realestate.natural.service.ZrzyZsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/4
 * @description 默认取号方式处理不动产权证号 逻辑类
 */
@Service("zrzyDjcqzhMrServiceImpl")
public class ZrzyDjcqzhMrServiceImpl implements ZrzyZsDjcqzhService {
    @Autowired
    private ZrzyZsService zrzyZsService;
    @Autowired
    private ZrzyDjcqzhGgServiceImpl zrzyDjcqzhGgService;

    /**
     * 生成证号是否采用锁方案，默认true采用
     */
    @Value("${zrzycqzh.needlock:true}")
    private Boolean zrzycqzhNeedLock;


    /**
     * @param zrzyZscCqzhBO 证号业务BO
     * @return {BdcBdcqzhDTO} 证号信息实体
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 以默认方式获取证号
     */
    @Override
    public ZrzyCqzhDTO resolveZrzycqzh(ZrzyZscCqzhBO zrzyZscCqzhBO) {
        // 1、拼接生成证号
        StringBuilder builder = new StringBuilder();
        builder.append(zrzyZscCqzhBO.getSqsjc());
        builder.append(" ");
        builder.append(zrzyZscCqzhBO.getNf());
        builder.append(" ");
        builder.append("自然资源所有权苏通 第");

        /// 获取数字顺序号
        Integer zhxlh = getZhxlh(zrzyZscCqzhBO);

        /// 长度不足，补零拼接
        String zhlsh = String.valueOf(zhxlh);
        if (zhlsh.length() < zrzyZscCqzhBO.getZrzycqzhws()) {
            do {
                zhlsh = StringUtils.join("0", zhlsh);
            } while (zhlsh.length() < zrzyZscCqzhBO.getZrzycqzhws());
        }
        /// 拼接所在区号或特定编码
        if (1 == zrzyZscCqzhBO.getSqdmkg().intValue()) {
            zhlsh = zrzyZscCqzhBO.getSqdm() + zhlsh.substring(zrzyZscCqzhBO.getSqdm().length());
        }

        builder.append(zhlsh);
        builder.append("号");

        // 2、封装返回实体
        ZrzyCqzhDTO zrzyCqzhDTO = new ZrzyCqzhDTO();
        zrzyCqzhDTO.setZsid(zrzyZscCqzhBO.getZsid());
        zrzyCqzhDTO.setZslx(zrzyZscCqzhBO.getZslx());
        zrzyCqzhDTO.setZrzycqzh(builder.toString());
        zrzyCqzhDTO.setNf(zrzyZscCqzhBO.getNf());
        zrzyCqzhDTO.setSqsjc(zrzyZscCqzhBO.getSqsjc());
        zrzyCqzhDTO.setSzsxqc(zrzyZscCqzhBO.getSzsxqc());
        zrzyCqzhDTO.setZhlsh(zhlsh);
        zrzyCqzhDTO.setZhxlh(zhxlh);

        return zrzyCqzhDTO;
    }

    private Integer getZhxlh(ZrzyZscCqzhBO bdcBdcqzhBO) {
        if (zrzycqzhNeedLock) {
            // 从数据库已有顺序号递增取值
            return getSxh(bdcBdcqzhBO);
        } else {
            // 采用缓存取号方案，从Redis取值，单个件直接获取下一个号
            return zrzyDjcqzhGgService.getNextSxhFromRedis(bdcBdcqzhBO, 1L);
        }
    }

    /**
     * @param zrzyZscCqzhBO 证书编号BO
     * @return {Integer}  顺序号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取本次证号流水号对应的顺序号
     * <p>
     * 1、不再使用SQL处理，一是效率问题，二是旧的LEAD OVER函数某些情况下测试会有问题
     * 2、顺序号需要考虑间断顺序号问题
     * 3、需要考虑起始顺序号问题，例如因为历史数据原因导致起始顺序号不为0，也有可能历史顺序号最大为10，但是起始顺序号定义为20
     * 4、预留证号和废号有些地区可能不开启，所以需要在SQL中动态拼接处理
     * </p>
     */
    private Integer getSxh(ZrzyZscCqzhBO zrzyZscCqzhBO) {
        // 获取数据库当前最大顺序号
        int maxSxh = zrzyZsService.queryMaxSxh(zrzyZscCqzhBO);
        // 初始顺序号
        int cssxh = zrzyZscCqzhBO.getCssxh();

        /**
         * 初始顺序号大于或者等于数据库中最大顺序号
         * 1、大于：
         *    直接就是从初始号编排；
         *    例如数据库最大顺序号为 10，初始号设置为20，那么就不用考虑已有数据，第一个号就是20
         * 2、等于：
         *    说明已经存在该顺序号，直接取下个顺序号
         */
        if (maxSxh < cssxh) {
            return cssxh;
        } else if (maxSxh == cssxh) {
            return Integer.valueOf(cssxh + 1);
        }

        /**
         * 初始号小于库中最大顺序号
         */
        // 获取初始顺序号和最大顺序号之间所有顺序号，需要注意返回集合数据结构
        zrzyZscCqzhBO.setSxh(maxSxh);
        zrzyZscCqzhBO.setCssxh(cssxh);
        LinkedHashSet<Integer> sxhSet = zrzyZsService.querySxh(zrzyZscCqzhBO);

        // 通过比较初始顺序号和数据库最大顺序号之间应该存在记录数量和实际顺序号数量，判断是否存在间断号
        int sxhNum = maxSxh - cssxh + 1;
        if (sxhNum == sxhSet.size()) {
            // 说明没有间断号
            return Integer.valueOf(maxSxh + 1);
        } else {
            // 说明有间断号，则获取最小的间断号
            for (Integer sxh : sxhSet) {
                if (cssxh != sxh.intValue()) {
                    break;
                }
                cssxh += 1;
            }

            return Integer.valueOf(cssxh);
        }
    }
}
