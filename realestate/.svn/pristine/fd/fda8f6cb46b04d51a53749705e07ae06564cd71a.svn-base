package cn.gtmap.realestate.init.service.qlr;

import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdZjzlDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化义务人
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/10/31.
 * @description
 */
public abstract class InitBdcYwrAbstractService implements InitService {
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
        return "ywrsjly";
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO){
        //参数空值返回或者非抓取楼盘表数据的不处理
        if(initServiceQO == null || initServiceQO.isSfdzbflpbsj() || (initServiceQO.isSfzqlpbsj() && !StringUtils.equals(Constants.QLRSJLY_LPB.toString(),getVal()))){
            return initServiceDTO;
        }
        List<BdcQlrDO> list;
        //先从受理获取，如果没有在走初始化逻辑
        if(CollectionUtils.isNotEmpty(initServiceQO.getBdcYwrList())){
            list=initServiceQO.getBdcYwrList();
        }else {
            list=initYwr(initServiceQO);
        }
        if(initServiceDTO == null) {
            initServiceDTO = new InitServiceDTO();
        }
        //过滤数据将证件号或权利人名称为空的过滤掉
        if(CollectionUtils.isNotEmpty(list)){
            List<BdcQlrDO> qlrList=new ArrayList<>();
            for(BdcQlrDO bdcQlrDO:list){
                //名称为空就抛异常
                if(StringUtils.isBlank(bdcQlrDO.getQlrmc())){
                    if (initServiceQO.getXxblLccj()) {
                        //如果是信息补录流程创建，不作提示不带入名称为空的人信息
                        break;
                    }
                    // 提示改为权利人
                    throw new AppException("权利人数据中的权利人名称为空，请补充数据！");
                }
                // 去掉义务人名称空格
                bdcQlrDO.setQlrmc(bdcQlrDO.getQlrmc().replaceAll("\\s*", ""));
                // 证件号不为空去空格
                if(StringUtils.isNotBlank(bdcQlrDO.getZjh())){
                    bdcQlrDO.setZjh(bdcQlrDO.getZjh().replaceAll("\\s*", ""));
                }
                qlrList.add(bdcQlrDO);
            }
            list=qlrList;
        }

        //处理权利人特征
        this.setQlrtz(list,initServiceQO);

        //没值就直接存
        if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcQlrList())){
            initServiceDTO.getBdcQlrList().addAll(list);
        }else{
            initServiceDTO.setBdcQlrList(list);
        }
        // 义务人名称
        String ywr = null;
        // 义务人证件号ID
        String zjh = null;
        // 义务人证件种类
        String zjzl = null;
        if(initServiceQO.getBdcXm()!=null){
            if(CollectionUtils.isNotEmpty(list)){
                ywr = StringToolUtils.resolveBeanToAppendStr(list, "getQlrmc", ",");
                zjh = StringToolUtils.resolveBeanToAppendStr(list, "getZjh", ",");
                zjzl = StringToolUtils.getZjzlOfZd(list, bdcZdCache.getZdTableList("BDC_ZD_ZJZL", BdcZdZjzlDO.class));
            }
            //冗余字段赋值
            BdcXmDO bdcXmDO=initServiceQO.getBdcXm();
            bdcXmDO.setYwr(ywr);
            bdcXmDO.setYwrzjh(zjh);
            bdcXmDO.setYwrzjzl(zjzl);
        }
        //将生成的义务人数据存入参数结构中
        initServiceQO.setBdcYwrList(list);
        return initServiceDTO;
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
            Integer qlrtz = bdcQlrtzService.getQlrtzMrz(initServiceQO.getBdcCshFwkgSl().getQllx(), CommonConstantUtils.QLRLB_YWR);
            if (qlrtz != null) {
                for (BdcQlrDO bdcQlrDO : list) {
                    bdcQlrDO.setQlrtz(qlrtz);
                }
            }
        }
    }

    /**
     * 删除数据父接口
     *@param sfzqlpbsj 是否抽取楼盘表数据
     *  @param plDel 是否批量业务删除
     * @param list 要删除的项目
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
                    if(bdcCshFwkgSlDO!=null && Constants.QLRSJLY_LPB.equals(bdcCshFwkgSlDO.getYwrsjly())){
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
                    bdcQlrService.deleteBatchQlr(gzlslid,CommonConstantUtils.QLRLB_YWR);
                }
            }else{
                for(BdcXmDO bdcXmDO:listLpb){
                    BdcQlrDO bdcQlrDO=new BdcQlrDO();
                    bdcQlrDO.setXmid(bdcXmDO.getXmid());
                    bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
                    List<BdcQlrDO> ywrList=bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO,null);
                    if(CollectionUtils.isNotEmpty(ywrList)){
                        deleteList.addAll(ywrList);
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
            bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_YWR);
            List<BdcQlrDO> ywrList=bdcQlrService.queryBdcQlrByQlrxx(bdcQlrDO,null);
            if(CollectionUtils.isNotEmpty(ywrList)){
                initServiceDTO.getBdcQlrList().addAll(ywrList);
            }
        }
        return initServiceDTO;
    }

    /**
     * 初始化义务人接口
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param initServiceQO 初始化所需数据结构
     *@return List<BdcQlrDO> 返回所有义务人信息
     *@description
     */
    public abstract List<BdcQlrDO> initYwr(InitServiceQO initServiceQO);

}
