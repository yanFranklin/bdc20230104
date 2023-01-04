package cn.gtmap.realestate.init.service.xmxx;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.delete.InitBdcOtherDeleteService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 项目加载服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/8.
 * @description
 */
public abstract class InitBdcXmAbstractService implements InitService {

    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    protected EntityMapper entityMapper;
    @Autowired
    protected BdcXmLsgxService bdcXmLsgxService;
    @Autowired
    protected BdcXmService bdcXmService;
    @Autowired
    Set<InitBdcOtherDeleteService> deleteServiceSet;

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    /**
     * 删除数据父接口
     *@param sfzqlpbsj 是否抽取楼盘表数据
     * @param plDel 是否批量业务删除
     * @param list 要删除的项目
     * @return 返回数据结构
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Object> delete(List<BdcXmDO> list, Boolean sfzqlpbsj,Boolean sfdzbflpbsj,Boolean plDel) throws Exception {
        List<Object> deleteList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            String gzlslid=list.get(0).getGzlslid();
            boolean sfAllDel=false;
            //非批量删除做查询看是否是整体删除
            if(StringUtils.isNotBlank(gzlslid) && (plDel==null || !plDel)){
                List<BdcXmDTO> listXmDTO=bdcXmService.listXmBfxx(gzlslid,null);
                //全部删除
                if(CollectionUtils.isNotEmpty(listXmDTO) && list.size()==listXmDTO.size()){
                    sfAllDel=true;
                }
            }
            //删除服务
            for(InitBdcOtherDeleteService initBdcOtherDeleteService:deleteServiceSet){
                deleteList.addAll(initBdcOtherDeleteService.delete(list,sfzqlpbsj,sfdzbflpbsj,plDel,sfAllDel));
            }
            //批量删除
            if(plDel){
                if(StringUtils.isNotBlank(gzlslid)){
                    //抓取楼盘表数据不做此处理
                    if(!sfzqlpbsj){
                        //先删除项目实例
                        bdcXmService.deleteCshFwkgSl(gzlslid);
                    }
                    //删除项目
                    Example example=new Example(BdcXmDO.class);
                    example.createCriteria().andEqualTo("gzlslid",gzlslid);
                    entityMapper.deleteByExample(example);
                }
            }else{
                deleteList.addAll(list);
                //抓取楼盘表数据不做此处理
                if(!sfzqlpbsj){
                    //删除对象的初始化实例表
                    for(BdcXmDO bdcXmDO:list){
                        BdcCshFwkgSlDO bdcCshFwkgSlDO=entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class,bdcXmDO.getXmid());
                        if(bdcCshFwkgSlDO!=null){
                            deleteList.add(bdcCshFwkgSlDO);
                        }
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
    public InitServiceDTO query(BdcXmDO bdcXmDO, InitServiceDTO initServiceDTO) throws Exception{
        if(initServiceDTO == null){
            initServiceDTO = new InitServiceDTO();
        }
        if(bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getXmid())){
            initServiceDTO.setBdcXm(bdcXmDO);
            BdcCshFwkgSlDO bdcCshFwkgSlDO=entityMapper.selectByPrimaryKey(BdcCshFwkgSlDO.class,bdcXmDO.getXmid());
            if(bdcCshFwkgSlDO!=null){
                initServiceDTO.setBdcCshFwkgSlDO(bdcCshFwkgSlDO);
            }
        }
        return initServiceDTO;
    }

    /**
     * 初始化不动产项目基本信息
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 不动产项目基本信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description
     */
    public abstract BdcXmDO initBdcXm(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO);
}
