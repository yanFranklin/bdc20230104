package cn.gtmap.realestate.init.service.djbxx;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcdjbService;
import cn.gtmap.realestate.init.service.InitService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
public abstract class InitBdcdjbAbstractService implements InitService {
    @Autowired
    protected DozerBeanMapper initDozerMapper;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private InitBeanFactory initBeanFactory;
    @Autowired
    private BdcdjbService bdcdjbService;

    @Override
    public String getCode() {
        //无开关不写
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //判断是否初始化登记薄
        if(initServiceQO.isSfCshDjb() && StringUtils.isNotBlank(initServiceQO.getBdcdyh()) && initServiceQO.getBdcdyh().length()==28 && !BdcdyhToolUtils.checkXnbdcdyh(initServiceQO.getBdcdyh())){
            if (initServiceDTO == null) {
                initServiceDTO = new InitServiceDTO();
            }
            BdcBdcdjbDO bdcBdcdjb = entityMapper.selectByPrimaryKey(BdcBdcdjbDO.class, StringUtils.substring(initServiceQO.getBdcdyh(), 0, 19));
            // 当登记簿不存在时 创建登记簿信息
            if (bdcBdcdjb == null || (initBeanFactory.isCoverDjb() && !initServiceQO.isSfzqlpbsj()) || (initServiceQO.isSfzqlpbsj() && initBeanFactory.isLpbsjCoverDjb()) ) {
                BdcBdcdjbDO bdcBdcdjbDO = initBdcdjb(initServiceQO);
                initServiceDTO.setBdcdjb(bdcBdcdjbDO);
                DjDcbResponseDTO djDcbResponseDTO=initServiceQO.getDjxxResponseDTO().getDjDcbResponseDTO();
                // 若地籍数据为宗海地籍信息
                if (djDcbResponseDTO instanceof ZhDjdcbResponseDTO) {
                    initServiceDTO.setBdcBdcdjbZhjbxx(initBdcdjbZhjbxx(initServiceQO));
                    if(!initServiceQO.isSfdzbflpbsj()){
                        //用海状况
                        initServiceDTO.setBdcBdcdjbZhjbxxYhzkList(initBdcdjbZhjbxxYhzkList(initServiceQO,initServiceDTO.getBdcBdcdjbZhjbxx()));
                        //用海用岛坐标
                        initServiceDTO.setBdcBdcdjbZhjbxxYhydzbList(initBdcdjbZhjbxxYhydzbList(initServiceQO));
                    }
                } else if (djDcbResponseDTO instanceof ZdDjdcbResponseDTO ||
                        djDcbResponseDTO instanceof NydDjdcbResponseDTO ||
                        djDcbResponseDTO instanceof QszdDjdcbResponseDTO ||
                        djDcbResponseDTO instanceof JyqDkDcbResponseDTO) {
                    // 若地籍数据为宗地 农用地 或 权属宗地 时
                    initServiceDTO.setBdcBdcdjbZdjbxx(initBdcdjbZdjbxx(initServiceQO));
                    //受理项目不为空的话做对照
                    if(initServiceQO.getBdcSlXmDO()!=null && initServiceDTO.getBdcBdcdjbZdjbxx()!=null){
                        initDozerMapper.map(initServiceQO.getBdcSlXmDO(),initServiceDTO.getBdcBdcdjbZdjbxx());
                    }
                }
            }
        }
        return initServiceDTO;
    }

    /**
     * 删除数据父接口
     * @param list 要删除的项目
     * @param plDel 是否批量业务删除
     * @param sfzqlpbsj 是否抽取楼盘表数据
     * @return 返回数据结构
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Object> delete(List<BdcXmDO> list,Boolean sfzqlpbsj,Boolean sfdzbflpbsj,Boolean plDel) throws Exception {
        List<Object> delList=new ArrayList<>();
        //覆盖的话就删除zh的用海状况和坐标信息  && 覆盖的话
        if(CollectionUtils.isNotEmpty(list) && ((initBeanFactory.isCoverDjb() && !sfzqlpbsj) || (initBeanFactory.isLpbsjCoverDjb() && sfzqlpbsj))){
            List<String> zdzhdmList=new ArrayList<>();
            for(BdcXmDO bdcXmDO:list){
                if(StringUtils.isNotBlank(bdcXmDO.getBdcdyh()) && bdcXmDO.getBdcdyh().length() >19){
                    String djh= bdcXmDO.getBdcdyh().substring(0,19);
                    if(!zdzhdmList.contains(djh)){
                        zdzhdmList.add(djh);
                    }
                }
            }
            //循环查询删除数据
            for(String djh:zdzhdmList){
                delList.addAll(bdcdjbService.queryBdcdjbDelData(djh,sfdzbflpbsj));
            }
        }
        return delList;
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
        if(bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getBdcdyh())){
            BdcBdcdjbDO bdcBdcdjb = entityMapper.selectByPrimaryKey(BdcBdcdjbDO.class, StringUtils.substring(bdcXmDO.getBdcdyh(), 0, 19));
            if(bdcBdcdjb != null){
                initServiceDTO.setBdcdjb(bdcBdcdjb);
                BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO=entityMapper.selectByPrimaryKey(BdcBdcdjbZhjbxxDO.class,bdcBdcdjb.getZdzhh());
                if(bdcBdcdjbZhjbxxDO!=null){
                    initServiceDTO.setBdcBdcdjbZhjbxx(bdcBdcdjbZhjbxxDO);
                }
                BdcBdcdjbZdjbxxDO bdcBdcdjbZdjbxxDO=entityMapper.selectByPrimaryKey(BdcBdcdjbZdjbxxDO.class,bdcBdcdjb.getZdzhh());
                if(bdcBdcdjbZdjbxxDO!=null){
                    initServiceDTO.setBdcBdcdjbZdjbxx(bdcBdcdjbZdjbxxDO);
                }
            }
        }
        //不做处理
        return initServiceDTO;
    }

    /**
     * 初始化登记薄接口
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 返回登记薄数据
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    public abstract BdcBdcdjbDO initBdcdjb(InitServiceQO initServiceQO);

    /**
     * 初始化登记薄宗地基本信息接口
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 返回登记薄宗地信息数据
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description
     */
    public abstract BdcBdcdjbZdjbxxDO initBdcdjbZdjbxx(InitServiceQO initServiceQO);

    /**
     * 初始化登记薄宗海基本信息接口
     *
     * @param initServiceQO 初始化所需数据结构
     * @return 返回登记薄宗海信息数据
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description
     */
    public abstract BdcBdcdjbZhjbxxDO initBdcdjbZhjbxx(InitServiceQO initServiceQO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param initServiceQO 初始化所需数据结构
     *@return List<BdcBdcdjbZhjbxxYhzkDO>
     *@description 返回登记薄宗海用海状况List
     */
    public abstract List<BdcBdcdjbZhjbxxYhzkDO> initBdcdjbZhjbxxYhzkList(InitServiceQO initServiceQO,BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param initServiceQO 初始化所需数据结构
     *@return List<BdcBdcdjbZhjbxxYhydzbDO>
     *@description 返回登记薄宗海用海用岛坐标List
     */
    public abstract List<BdcBdcdjbZhjbxxYhydzbDO> initBdcdjbZhjbxxYhydzbList(InitServiceQO initServiceQO);
}
