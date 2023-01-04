package cn.gtmap.realestate.init.service.xmxx;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.service.InitService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目历史关系加载服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/8.
 * @description
 */
public abstract class InitBdcXmLsgxAbstractService implements InitService {
    @Autowired
    protected   EntityMapper entityMapper;
    @Autowired
    protected BdcXmLsgxService bdcXmLsgxService;

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //参数空值返回
        if(initServiceQO == null){
            return initServiceDTO;
        }
        List<BdcXmLsgxDO> list=initBdcXmLsgx(initServiceQO);
        if(initServiceDTO==null){
            initServiceDTO=new InitServiceDTO();
        }
        initServiceDTO.setBdcXmLsgxList(list);
        return initServiceDTO;
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
    public List<Object> delete(List<BdcXmDO> list,Boolean sfzqlpbsj,Boolean sfdzbflpbsj,Boolean plDel) throws Exception {
        List<Object> deleteList=new ArrayList<>();
        //抽取楼盘表数据时 不删除
        if(sfzqlpbsj){
            return deleteList;
        }
        if(CollectionUtils.isNotEmpty(list)){
            if(plDel){
                String gzlslid=list.get(0).getGzlslid();
                if(StringUtils.isNotBlank(gzlslid)){
                    //xmid作为yxmid的项目历史关系数据
                    bdcXmLsgxService.deleteBdcYxmLsgx(gzlslid);
                    //删除项目关系数据
                    bdcXmLsgxService.deleteBdcXmLsgx(gzlslid);
                }
            }else{
                for(BdcXmDO bdcXmDO:list){
                    List<BdcXmLsgxDO> xmLsgxList=bdcXmLsgxService.queryBdcXmLsgxByXmid(bdcXmDO.getXmid(),null);
                    if(CollectionUtils.isNotEmpty(xmLsgxList)){
                        deleteList.addAll(xmLsgxList);
                    }
                    //xmid作为yxmid查询
                    List<BdcXmLsgxDO> lsgxList=bdcXmLsgxService.queryBdcXmNextLsgxByXmid(bdcXmDO.getXmid(),null,null);
                    if(CollectionUtils.isNotEmpty(lsgxList)){
                        deleteList.addAll(lsgxList);
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
            List<BdcXmLsgxDO> xmLsgxList=bdcXmLsgxService.queryBdcXmLsgxByXmid(bdcXmDO.getXmid(),null);
            if(CollectionUtils.isNotEmpty(xmLsgxList)){
                initServiceDTO.getBdcXmLsgxList().addAll(xmLsgxList);
            }
        }
        return initServiceDTO;
    }

    /**
     * 初始化不动产项目关系
     *
     * @param initServiceQO 初始化所需数据结构
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @return List<BdcXmLsgxDO>
     * @description
     */
    public  abstract  List<BdcXmLsgxDO> initBdcXmLsgx(InitServiceQO initServiceQO);
}
