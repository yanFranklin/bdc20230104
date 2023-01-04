package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcLshService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.gtmap.realestate.accept.core.mapper.BdcLshMapper;
import cn.gtmap.realestate.accept.service.BdcBhService;
import cn.gtmap.realestate.accept.util.CommonUtil;
import cn.gtmap.realestate.accept.util.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/6
 * @description 不动产编号服务
 */
@Service
public class BdcBhServiceImpl implements BdcBhService {


    @Autowired
    BdcLshService bdcLshService;



    /**
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 不动产流水号数据mapper
     */
    @Autowired
    BdcLshMapper bdcLshMapper;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  受理编号生成版本
     */
    @Value("${slbhscfs.version:}")
    private String slbhgs;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  受理编号自增时间范围
     */
    @Value("${slbh.zzsjfw:}")
    private String slbhzzsjfw;

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao</a>
     * @description  受理编号年月日时间格式
     */
    @Value("${slbh.zzsjgs:}")
    private String zzsjgs;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  受理编号自增序列号位数
     */
    @Value("${slbh.zzxlh:4}")
    private Integer slbhzzxlh;



    /**
     * @param ywlx   业务类型
     * @param zzsjfw 自增时间范围 包括YEAR  MONTH  DAY
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据自增时间范围、自增业务类型  生成编号
     */

    @Override
    public String queryBh(String ywlx, String zzsjfw) {
        if(StringUtils.isBlank(zzsjfw)){
            zzsjfw =slbhzzsjfw;
        }
        String nyr = "";
        Integer lsh = bdcLshService.queryLsh(ywlx, zzsjfw);
        if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_YEAR)) {
            if (StringUtils.equals(slbhgs, Constants.VERSION_NT)) {
                //南通受理编号只获取年份的后两位即可
                nyr = CommonUtil.getCurrYearYear().substring(CommonUtil.getCurrYearYear().length() - 2, CommonUtil.getCurrYearYear().length());
            }else {
                nyr = CommonUtil.getCurrYearYear();
            }
        } else if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_MONTH)) {
            nyr = CommonUtil.getCurrYearMonth();
        } else if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_DAY)) {
            nyr = CommonUtil.getCurrDay();
        }
        // 受理编号年月日时间格式
        if(StringUtils.isNotBlank(zzsjgs)  && StringUtils.equals(ywlx, Constants.YWLX_SLBH)){
            nyr = new SimpleDateFormat(zzsjgs).format(new Date(System.currentTimeMillis()));
        }
        /// 长度不足，补零拼接
        String bhlsh = String.valueOf(lsh);
        if(bhlsh.length() < slbhzzxlh){
            do{
                bhlsh = StringUtils.join("0", bhlsh);
            }while (bhlsh.length() < slbhzzxlh);
        }

        return nyr + bhlsh;
    }

    /**
     * @param ywlx 业务类型
     * @param zzsjfw 自增时间范围
     * @param zzxlh 自增序列号
     * @param prebh 编号前缀
     * @return 编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据自增时间范围、自增业务类型生成编号通用方法
     */
    @Override
    public String queryCommonBh(String ywlx, String zzsjfw,Integer zzxlh,String prebh) {
        String nyr = "";
        Integer lsh = bdcLshService.queryLsh(ywlx, zzsjfw);
        if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_YEAR)) {
            nyr = CommonUtil.getCurrYearYear();
        } else if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_MONTH)) {
            nyr = CommonUtil.getCurrYearMonth();
        } else if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_DAY)) {
            nyr = CommonUtil.getCurrDay();
        }
        // 受理编号年月日时间格式
        if(StringUtils.isNotBlank(zzsjgs) && StringUtils.equals(ywlx, Constants.YWLX_SLBH)){
            nyr = new SimpleDateFormat(zzsjgs).format(new Date(System.currentTimeMillis()));
        }
        /// 长度不足，补零拼接
        String bhlsh = String.valueOf(lsh);
        if(bhlsh.length() < zzxlh){
            do{
                bhlsh = StringUtils.join("0", bhlsh);
            }while (bhlsh.length() < zzxlh);
        }

        return prebh +nyr + bhlsh;
    }

    @Override
    public String queryTzmByBdcdyh(String bdcdyh){
        String tzm ="";
        String dzwtzm = bdcdyh.substring(19, 20);
        String qslxdm =StringUtils.substring(bdcdyh, 13, 14);
        if (StringUtils.equals(dzwtzm, Constants.BHTZM_FW)) {
            tzm =Constants.BHTZM_FW;
        } else if (StringUtils.equals(dzwtzm, Constants.DZWTZM_TD)) {
            tzm =Constants.BHTZM_TD;
            if (StringUtils.equals(Constants.QSLXDM_H, qslxdm) ||StringUtils.contains(Constants.QSLXDM_G, qslxdm) ) {
                //海域
                tzm =Constants.BHTZM_HY;
            }else if (StringUtils.equals(Constants.QSLXDM_L, qslxdm)){
                //林权
                tzm =Constants.BHTZM_LQ;
            }

        }  else if (StringUtils.equals(dzwtzm, Constants.BHTZM_LQ)) {
            tzm =Constants.BHTZM_LQ;
        } else {
            tzm =Constants.BHTZM_FW;
        }
        return tzm;

    }

    @Override
    public String queryBhBySlsj(String ywlx, String zzsjfw, Date slsj) {
        if(StringUtils.isBlank(zzsjfw)){
            zzsjfw =slbhzzsjfw;
        }
        String nyr = "";
        Integer lsh = bdcLshService.queryLshBySlsj(ywlx, zzsjfw,slsj);
        if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_YEAR)) {
            if (StringUtils.equals(slbhgs, Constants.VERSION_NT)) {
                //南通受理编号只获取年份的后两位即可
                nyr = CommonUtil.getCurrYearMonthDay(slsj,Constants.DATE_N).substring(CommonUtil.getCurrYearMonthDay(slsj,Constants.DATE_N).length() - 2, CommonUtil.getCurrYearMonthDay(slsj,Constants.DATE_N).length());
            }else {
                nyr = CommonUtil.getCurrYearMonthDay(slsj,Constants.DATE_N);
            }
        } else if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_MONTH)) {
            nyr = CommonUtil.getCurrYearMonthDay(slsj,Constants.DATE_NY);
        } else if (StringUtils.equals(zzsjfw, Constants.ZZSJFW_DAY)) {
            nyr = CommonUtil.getCurrYearMonthDay(slsj,Constants.DATE_NYR);
        }
        /// 长度不足，补零拼接
        String bhlsh = String.valueOf(lsh);
        if(bhlsh.length() < slbhzzxlh){
            do{
                bhlsh = StringUtils.join("0", bhlsh);
            }while (bhlsh.length() < slbhzzxlh);
        }

        return nyr + bhlsh;
    }





}
