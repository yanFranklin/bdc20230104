package cn.gtmap.realestate.init.service.jtcy;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrHkbGxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrHkbGxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.IDCardUtils;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcJtcyService;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/5.
 * @description 初始化家庭成员基础服务
 */
public abstract class InitBdcJtcyBaseAbstractService  implements InitService {
    /**
     * 同步权籍权利人和义务人的集合
     */
    @Value("#{'${init.synchqlr:}'.split(',')}")
    private List<String> synchqlr;
    @Autowired
    protected InitBeanFactory initBeanFactory;
    @Autowired
    protected EntityMapper entityMapper;
    @Autowired
    protected BdcJtcyService bdcJtcyService;
    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        // 参数空值返回
        if (initServiceQO == null) {
            return initServiceDTO;
        }
        if (initServiceDTO == null) {
            initServiceDTO = new InitServiceDTO();
        }
        initServiceDTO = initJtcy(initServiceQO,initServiceDTO);
        dealJtcy(initServiceDTO.getBdcJtcyDOList());
        return initServiceDTO;
    }
    public abstract InitServiceDTO initJtcy(InitServiceQO initServiceQO,InitServiceDTO initServiceDTO) throws Exception;

    /**
     * 根据权利人类别 获取家庭成员信息
     * @param bdcXmDO
     * @param initServiceDTO
     * @param qlrlb
     * @return
     */
    public InitServiceDTO queryJtcy(BdcXmDO bdcXmDO, InitServiceDTO initServiceDTO,String qlrlb){
        if(initServiceDTO == null){
            initServiceDTO = new InitServiceDTO();
        }
        if(bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getXmid())){
            // 查询权利人和家庭成员关系
            BdcQlrHkbGxQO bdcQlrHkbGxQO = new BdcQlrHkbGxQO();
            bdcQlrHkbGxQO.setXmid(bdcXmDO.getXmid());
            bdcQlrHkbGxQO.setQlrlb(qlrlb);
            List<BdcQlrHkbGxDO> bdcQlrHkbGxDTOList = bdcJtcyService.listBdcQlrHkbGxDO(bdcQlrHkbGxQO);
            if(CollectionUtils.isNotEmpty(bdcQlrHkbGxDTOList)){
                // 查询家庭成员信息
                initServiceDTO.setBdcQlrHkbGxDOList(bdcQlrHkbGxDTOList);
                List<BdcJtcyDO> bdcJtcyDOList = new ArrayList<>();
                for(BdcQlrHkbGxDO bdcQlrHkbGxDO:bdcQlrHkbGxDTOList){
                    BdcJtcyDO bdcJtcyDO = new BdcJtcyDO();
                    bdcJtcyDO.setHkbbbh(bdcQlrHkbGxDO.getHkbbbh());
                    bdcJtcyDO.setHkbbm(bdcQlrHkbGxDO.getHkbbm());
                    List<BdcJtcyDO> jtcyDOList = bdcJtcyService.queryJtcy(bdcJtcyDO);
                    if(CollectionUtils.isNotEmpty(jtcyDOList)){
                        bdcJtcyDOList.addAll(jtcyDOList);
                    }
                }
                initServiceDTO.setBdcJtcyDOList(bdcJtcyDOList);
            }
        }
        return initServiceDTO;
    }

    /**
     * 根据权利人类别 删除家庭成员
     * @param list
     * @param sfzqlpbsj
     * @param sfdzbflpbsj
     * @param plDel
     * @param qlrlb
     * @return
     */
    public List<Object> deleteJtcy(List<BdcXmDO> list, Boolean sfzqlpbsj, Boolean sfdzbflpbsj, Boolean plDel,String qlrlb){
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
                    Integer sjly = qlrlb.equals(CommonConstantUtils.QLRLB_QLR)?bdcCshFwkgSlDO.getQlrsjly():bdcCshFwkgSlDO.getYwrsjly();
                    if(bdcCshFwkgSlDO!=null && Constants.QLRSJLY_LPB.equals(sjly)){
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
                    bdcJtcyService.deleteBatchQlrHkbGx(gzlslid, qlrlb);
                }
            }else{
                for(BdcXmDO bdcXmDO:listLpb){
                    BdcQlrHkbGxQO bdcQlrHkbGxQO = new BdcQlrHkbGxQO(bdcXmDO.getXmid(),qlrlb);
                    List<BdcQlrHkbGxDO> bdcQlrHkbGxDTOList = bdcJtcyService.listBdcQlrHkbGxDO(bdcQlrHkbGxQO);
                    if(CollectionUtils.isNotEmpty(bdcQlrHkbGxDTOList)){
                        deleteList.addAll(bdcQlrHkbGxDTOList);
                    }
                }
            }
        }
        return deleteList;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  处理家庭成员默认值
     */
    private void dealJtcy(List<BdcJtcyDO> bdcJtcyDOList){
        if(CollectionUtils.isNotEmpty(bdcJtcyDOList)){
            for(BdcJtcyDO bdcJtcyDO:bdcJtcyDOList){
                if(StringUtils.isNotBlank(bdcJtcyDO.getJtcyzjh()) && Objects.equals(CommonConstantUtils.ZJZL_SFZ, bdcJtcyDO.getJtcyzjzl()) && (StringUtils.length(bdcJtcyDO.getJtcyzjh()) == 18 || StringUtils.length(bdcJtcyDO.getJtcyzjh()) == 15)){
                    if(bdcJtcyDO.getXb() ==null){
                        bdcJtcyDO.setXb(IDCardUtils.getSex(bdcJtcyDO.getJtcyzjh()).toString());
                    }
                    if(bdcJtcyDO.getNl() ==null){
                        bdcJtcyDO.setNl(IDCardUtils.getAge(bdcJtcyDO.getJtcyzjh()));
                    }
                }
            }
        }

    }
}
