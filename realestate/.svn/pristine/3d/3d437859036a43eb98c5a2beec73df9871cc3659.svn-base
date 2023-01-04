package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.service.BdcZsBdcqzhService;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.enums.BdcZslxEnum;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
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
@Service("bdcBdcqzhMrServiceImpl")
public class BdcBdcqzhMrServiceImpl implements BdcZsBdcqzhService {
    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    private BdcBdcqzhGgServiceImpl baseBdcBdcqzhService;

    /**
     * 版本
     */
    @Value("${certificate.version:null}")
    private String certificateVersion;

    /**
     * 生成证号是否采用锁方案，默认true采用
     */
    @Value("${bdcqzh.needlock:true}")
    private Boolean bdcqzhNeedLock;


    /**
     * @param bdcBdcqzhBO 证号业务BO
     * @return {BdcBdcqzhDTO} 证号信息实体
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 以默认方式获取证号
     */
    @Override
    public BdcBdcqzhDTO resolveBdcqzh(BdcBdcqzhBO bdcBdcqzhBO) {
        // 1、拼接生成证号
        StringBuilder builder = new StringBuilder();
        builder.append(bdcBdcqzhBO.getSqsjc());
        builder.append(CommonConstantUtils.BDCQ_BH_LEFT_BRACKET);
        builder.append(bdcBdcqzhBO.getNf());
        builder.append(CommonConstantUtils.BDCQ_BH_RIGHT_BRACKET);
        builder.append(bdcBdcqzhBO.getSzsxqc());
        builder.append(BdcZslxEnum.getZhlx(bdcBdcqzhBO.getZslx(), certificateVersion));
        builder.append("第");

        /// 获取数字顺序号
        Integer zhxlh = this.getZhxlh(bdcBdcqzhBO);

        /// 长度不足，补零拼接
        String zhlsh = String.valueOf(zhxlh);
        if(zhlsh.length() < bdcBdcqzhBO.getBdcqzhws()){
            do{
                zhlsh = StringUtils.join("0", zhlsh);
            }while (zhlsh.length() < bdcBdcqzhBO.getBdcqzhws());
        }
        /// 拼接所在区号或特定编码
        if(1 == bdcBdcqzhBO.getSqdmkg().intValue()){
            zhlsh = bdcBdcqzhBO.getSqdm() + zhlsh.substring(bdcBdcqzhBO.getSqdm().length());
        }

        builder.append(zhlsh);
        builder.append("号");

        // 2、封装返回实体
        BdcBdcqzhDTO bdcBdcqzhDTO = new BdcBdcqzhDTO();
        bdcBdcqzhDTO.setZsid(bdcBdcqzhBO.getZsid());
        bdcBdcqzhDTO.setZslx(bdcBdcqzhBO.getZslx());
        bdcBdcqzhDTO.setBdcqzh(builder.toString());
        bdcBdcqzhDTO.setNf(bdcBdcqzhBO.getNf());
        bdcBdcqzhDTO.setSqsjc(bdcBdcqzhBO.getSqsjc());
        bdcBdcqzhDTO.setSzsxqc(bdcBdcqzhBO.getSzsxqc());
        bdcBdcqzhDTO.setZhlsh(zhlsh);
        bdcBdcqzhDTO.setZhxlh(zhxlh);

        return bdcBdcqzhDTO;
    }

    private Integer getZhxlh(BdcBdcqzhBO bdcBdcqzhBO) {
        if(bdcqzhNeedLock) {
            // 从数据库已有顺序号递增取值
            return this.getSxh(bdcBdcqzhBO);
        } else {
            // 采用缓存取号方案，从Redis取值，单个件直接获取下一个号
            return baseBdcBdcqzhService.getNextSxhFromRedis(bdcBdcqzhBO, Integer.valueOf(1).longValue());
        }
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcBdcqzhBO 证书编号BO
     * @return  {Integer}  顺序号
     * @description
     *      获取本次证号流水号对应的顺序号
     *    <p>
     *        1、不再使用SQL处理，一是效率问题，二是旧的LEAD OVER函数某些情况下测试会有问题
     *        2、顺序号需要考虑间断顺序号问题
     *        3、需要考虑起始顺序号问题，例如因为历史数据原因导致起始顺序号不为0，也有可能历史顺序号最大为10，但是起始顺序号定义为20
     *        4、预留证号和废号有些地区可能不开启，所以需要在SQL中动态拼接处理
     *    </p>
     */
    private Integer getSxh(BdcBdcqzhBO bdcBdcqzhBO) {
        // 获取数据库当前最大顺序号
        int maxSxh = bdcZsService.queryMaxSxh(bdcBdcqzhBO);
        // 初始顺序号
        int cssxh = bdcBdcqzhBO.getCssxh();

        /**
         * 初始顺序号大于或者等于数据库中最大顺序号
         * 1、大于：
         *    直接就是从初始号编排；
         *    例如数据库最大顺序号为 10，初始号设置为20，那么就不用考虑已有数据，第一个号就是20
         * 2、等于：
         *    说明已经存在该顺序号，直接取下个顺序号
         */
        if(maxSxh < cssxh){
            return cssxh;
        } else if(maxSxh == cssxh){
            return Integer.valueOf(cssxh + 1);
        }

        /**
         * 初始号小于库中最大顺序号
         */
        // 获取初始顺序号和最大顺序号之间所有顺序号，需要注意返回集合数据结构
        bdcBdcqzhBO.setSxh(maxSxh);
        bdcBdcqzhBO.setCssxh(cssxh);
        LinkedHashSet<Integer> sxhSet = bdcZsService.querySxh(bdcBdcqzhBO);

        // 通过比较初始顺序号和数据库最大顺序号之间应该存在记录数量和实际顺序号数量，判断是否存在间断号
        int sxhNum = maxSxh - cssxh + 1;
        if(sxhNum == sxhSet.size()){
            // 说明没有间断号
            return Integer.valueOf(maxSxh + 1);
        } else {
            // 说明有间断号，则获取最小的间断号
            for(Integer sxh : sxhSet){
                if(cssxh != sxh.intValue()){
                    break;
                }
                cssxh += 1;
            }

            return Integer.valueOf(cssxh);
        }
    }
}
