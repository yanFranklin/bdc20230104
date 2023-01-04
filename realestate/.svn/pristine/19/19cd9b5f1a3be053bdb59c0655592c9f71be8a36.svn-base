package cn.gtmap.realestate.init.service.qlr;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.IDCardUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.core.service.BdcQlrtzService;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 初始化权利人
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/10/31.
 * @description
 */
public abstract class InitBdcQlrAbstractService implements InitService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(InitBdcQlrAbstractService.class);

    /**
     * 权利人服务
     */
    @Autowired
    protected BdcQlrService bdcQlrService;
    @Autowired
    protected EntityMapper entityMapper;
    @Autowired
    private BdcZdCache bdcZdCache;
    /**
     * 项目服务
     */
    @Autowired
    protected BdcXmService bdcXmService;
    /**
     * 项目关系服务
     */
    @Autowired
    protected BdcXmLsgxService bdcXmLsgxService;

    @Autowired
    private BdcQlrtzService bdcQlrtzService;

    /**
     * 同步权籍权利人和义务人的集合
     */
    @Value("#{'${init.synchqlr:}'.split(',')}")
    private List<String> synchqlr;

    @Override
    public String getCode() {
        return "qlrsjly";
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO){
        //参数空值返回
        if(initServiceQO == null || initServiceQO.isSfdzbflpbsj() || (initServiceQO.isSfzqlpbsj() && !StringUtils.equals(Constants.QLRSJLY_LPB.toString(),getVal()))){
            return initServiceDTO;
        }
        List<BdcQlrDO> list;
        //先从受理获取，如果没有在走初始化逻辑
        if(CollectionUtils.isNotEmpty(initServiceQO.getBdcQlrList())){
            list=initServiceQO.getBdcQlrList();
        }else {
            list=initQlr(initServiceQO);
        }
        if(initServiceDTO == null) {
            initServiceDTO = new InitServiceDTO();
        }
        //过滤数据将证件号或权利人名称为空的过滤掉
        if(CollectionUtils.isNotEmpty(list)){
            List<BdcQlrDO> qlrList=new ArrayList<>();
            for(BdcQlrDO bdcQlrDO:list){
                //权利人名称为空不生成权利人数据
                if (StringUtils.isBlank(bdcQlrDO.getQlrmc())) {
                    continue;
                }
                // 去掉权利人名称空格
                bdcQlrDO.setQlrmc(bdcQlrDO.getQlrmc().replaceAll("\\s*", ""));
                // 证件号不为空去空格
                if (StringUtils.isNotBlank(bdcQlrDO.getZjh())) {
                    bdcQlrDO.setZjh(bdcQlrDO.getZjh().replaceAll("\\s*", ""));
                }
                if (Objects.isNull(bdcQlrDO.getXb())) {
                    //证件号不为空且是身份证根据证件号判断否则默认为3
                    if (StringUtils.isNotBlank(bdcQlrDO.getZjh()) && Objects.equals(CommonConstantUtils.ZJZL_SFZ, bdcQlrDO.getZjzl()) && (StringUtils.length(bdcQlrDO.getZjh()) == 18 || StringUtils.length(bdcQlrDO.getZjh()) == 15)) {
                        bdcQlrDO.setXb(IDCardUtils.getSex(bdcQlrDO.getZjh()));
                    } else {
                        bdcQlrDO.setXb(3);
                    }
                }
                qlrList.add(bdcQlrDO);
            }
            list = qlrList;
        }
        // 处理权利人共有情况
        this.setQlrGyqk(list);

        //处理权利人特征
        this.setQlrtz(list,initServiceQO);

        //没值就直接存
        if (CollectionUtils.isNotEmpty(initServiceDTO.getBdcQlrList())) {
            initServiceDTO.getBdcQlrList().addAll(list);
        } else {
            initServiceDTO.setBdcQlrList(list);
        }
        // 权利人名称
        String qlr = null;
        // 权利人证件号ID
        String zjh = null;
        // 权利人证件种类
        String zjzl = null;
        // 权利人类型
        String qlrlx = null;
        if(initServiceQO.getBdcXm()!=null){
            if(CollectionUtils.isNotEmpty(list)){
                qlr = StringToolUtils.resolveBeanToAppendStr(list, "getQlrmc", ",");
                zjh = StringToolUtils.resolveBeanToAppendStr(list, "getZjh", ",");
                zjzl = StringToolUtils.getZjzlOfZd(list, bdcZdCache.getZdTableList("BDC_ZD_ZJZL", BdcZdZjzlDO.class));
                qlrlx = StringToolUtils.convertBeanPropertiesValueOfZd(list, "qlrlx",bdcZdCache.getZdTableList("BDC_ZD_QLRLX", BdcZdQlrlxDO.class),"");
            }
            //冗余字段赋值
            BdcXmDO bdcXmDO=initServiceQO.getBdcXm();
            bdcXmDO.setQlr(qlr);
            bdcXmDO.setQlrzjh(zjh);
            bdcXmDO.setQlrzjzl(zjzl);
            bdcXmDO.setQlrlx(qlrlx);
        }
        //将生成的权利人数据存入参数结构中
        initServiceQO.setBdcQlrList(list);
        if(CollectionUtils.isNotEmpty(initServiceQO.getBdcDlrDOList())){
            LOGGER.info("{}初始化读取代理人信息:{}",initServiceQO.getBdcXm() != null?initServiceQO.getBdcXm().getSlbh():"",initServiceQO.getBdcDlrDOList());
            initServiceDTO.setBdcDlrDOList(initServiceQO.getBdcDlrDOList());
        }
        return initServiceDTO;
    }

    /**
     * 根据 gyfs 生成共有情况，不考虑数据问题的处理
     *
     * @param list 权利人集合
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    private void setQlrGyqk(List<BdcQlrDO> list) {
        if (CollectionUtils.isEmpty(list) || list.get(0).getGyfs() == null) {
            return;
        }
        Integer gyfs = list.get(0).getGyfs();
        for (BdcQlrDO bdcQlrDO : list) {
            if (CommonConstantUtils.GYFS_DDSY.equals(gyfs)) {
                bdcQlrDO.setGyqk("单独所有");
                continue;
            }

            StringBuilder gyqk = new StringBuilder("与");
            for (BdcQlrDO qlrDO : list) {
                if (!StringUtils.equals(bdcQlrDO.getQlrid(), qlrDO.getQlrid())) {
                    gyqk.append(qlrDO.getQlrmc()).append(",");
                }
            }

            // 没有权利人，清空 gyqk 字段
            if (!StringUtils.equals(gyqk.toString(), "与")) {
                gyqk.deleteCharAt(gyqk.length() - 1);
                String gyfsmc = bdcZdCache.getFeildValue(BdcZdGyfsDO.class.getSimpleName(), gyfs, BdcZdGyfsDO.class);
                if (StringUtils.isNotBlank(gyfsmc)) {
                    gyqk.append(gyfsmc);
                }
            } else {
                gyqk.delete(0, gyqk.length() - 1);
            }
            bdcQlrDO.setGyqk(gyqk.toString());
        }
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  权利人特征默认赋值
      */
    private void setQlrtz(List<BdcQlrDO> list,InitServiceQO initServiceQO){
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        if(initServiceQO != null &&initServiceQO.getBdcCshFwkgSl() != null &&initServiceQO.getBdcCshFwkgSl().getQllx() !=null) {
            Integer qlrtz = bdcQlrtzService.getQlrtzMrz(initServiceQO.getBdcCshFwkgSl().getQllx(), CommonConstantUtils.QLRLB_QLR);
            if (qlrtz != null) {
                for (BdcQlrDO bdcQlrDO : list) {
                    bdcQlrDO.setQlrtz(qlrtz);
                }
            }
        }
    }

    /**
     * 删除数据父接口
     *
     * @param sfzqlpbsj 是否抽取楼盘表数据
     * @param plDel     是否批量业务删除
     * @param list      要删除的项目
     * @return 返回数据结构
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Object> delete(List<BdcXmDO> list,Boolean sfzqlpbsj,Boolean sfdzbflpbsj,Boolean plDel) throws Exception {
        List<Object> deleteList=new ArrayList<>();
        List<BdcXmDO> listLpb=new ArrayList<>();
        //对照楼盘表数据直接返回
        if(sfdzbflpbsj){
            return deleteList;
        }
        //抽取楼盘表数据时  非楼盘表数据来源时返回
        if(sfzqlpbsj && CollectionUtils.isNotEmpty(list)){
            for(BdcXmDO bdcXmDO:list){
                //同步权利人的
                if(CollectionUtils.isNotEmpty(synchqlr) && synchqlr.contains(bdcXmDO.getDjxl())){
                    listLpb.add(bdcXmDO);
                }else{
                    BdcCshFwkgSlDO bdcCshFwkgSlDO=entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class,bdcXmDO.getXmid());
                    //楼盘表的数据读取处理
                    if(bdcCshFwkgSlDO!=null && Constants.QLRSJLY_LPB.equals(bdcCshFwkgSlDO.getQlrsjly())){
                        listLpb.add(bdcXmDO);
                    }
                }
            }
        }else{
            listLpb=list;
        }
        if(CollectionUtils.isNotEmpty(listLpb)){
            //批量的单独处理
            if(plDel){
                String gzlslid=listLpb.get(0).getGzlslid();
                if(StringUtils.isNotBlank(gzlslid)){
                    bdcQlrService.deleteBatchQlr(gzlslid,CommonConstantUtils.QLRLB_QLR);
                }
            }else{
                for(BdcXmDO bdcXmDO:listLpb){
                    BdcQlrDO bdcQlrDO=new BdcQlrDO();
                    bdcQlrDO.setXmid(bdcXmDO.getXmid());
                    bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    List<BdcQlrDO> qlrList=bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO,null);
                    if(CollectionUtils.isNotEmpty(qlrList)){
                        deleteList.addAll(qlrList);
                    }
                }
            }
        }
        return deleteList;
    }

    /**
     * 当前项目业务数据查询接口
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcXmDO 查询的项目信息
     *@param initServiceDTO 返回数据
     *@return   返回数据结构
     *@description
     */
    @Override
    public InitServiceDTO query(BdcXmDO bdcXmDO,InitServiceDTO initServiceDTO) throws Exception{
        if(initServiceDTO == null){
            initServiceDTO = new InitServiceDTO();
        }
        if(bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getXmid())){
            BdcQlrDO bdcQlrDO=new BdcQlrDO();
            bdcQlrDO.setXmid(bdcXmDO.getXmid());
            bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> qlrList=bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO,null);
            if(CollectionUtils.isNotEmpty(qlrList)){
                initServiceDTO.getBdcQlrList().addAll(qlrList);
            }
        }
        return initServiceDTO;
    }

    /**
     * 初始化权利人接口
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param initServiceQO 初始化所需数据结构
     *@return   返回所有权利人数据
     *@description
     */
    public abstract List<BdcQlrDO> initQlr(InitServiceQO initServiceQO);

}
