package cn.gtmap.realestate.init.service.other.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.GzwFeignService;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/5.
 * @description
 */
@Service
public class InitServiceQoServiceImpl implements InitServiceQoService {
    /**
     * 楼盘表服务
     */
    @Autowired
    private DjxxFeignService djxxFeignService;
    @Autowired
    private BdcdyFeignService bdcdyFeignService;
    @Autowired
    private GzwFeignService gzwFeignService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcQllxService bdcQllxService;

    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;

    @Value("${init.dozerVersion:standard}")
    private String version;

    /**
     * 创建QO对象
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceQO creatInitServiceQO() {
        return new InitServiceQO(bdcdyFeignService,gzwFeignService,djxxFeignService,version);
    }

    /**
     * 从QO对象中获取原不动产项目
     *
     * @param yxmid
     * @param initServiceQO
     * @return
     */
    @Override
    public BdcXmDO queryYbdcxm(String yxmid, InitServiceQO initServiceQO) {
        BdcXmDO ybdcxm=null;
        InitServiceDTO initServiceDTO = initServiceQO.getServiceDataMap().get(yxmid);
        if(initServiceDTO!=null && initServiceDTO.getBdcXm()!=null){
            ybdcxm=initServiceDTO.getBdcXm();
        }else{
            ybdcxm=initServiceQO.getYbdcxmDataMap().get(yxmid);
            if(ybdcxm==null){
                ybdcxm =bdcXmService.queryBdcXmByPrimaryKey(yxmid);
                initServiceQO.getYbdcxmDataMap().put(yxmid,ybdcxm);
            }
        }
        return ybdcxm;
    }

    /**
     * 从QO对象中获取原权利
     *
     * @param yxmid
     * @param initServiceQO
     * @return
     */
    @Override
    public BdcQl queryYql(String yxmid, InitServiceQO initServiceQO) {
        BdcQl yql=null;
        InitServiceDTO initServiceDTO = initServiceQO.getServiceDataMap().get(yxmid);
        if(initServiceDTO!=null && initServiceDTO.getBdcXm()!=null){
            yql=initServiceDTO.getBdcQl();
        }else{
            yql=initServiceQO.getYqlDataMap().get(yxmid);
            if(yql == null && !initServiceQO.getYqlDataMap().containsKey(yxmid)){
                BdcXmDO bdcXmDO=queryYbdcxm(yxmid,initServiceQO);
                if(bdcXmDO!=null && bdcXmDO.getQllx()!=null){
                    BdcQl bdcQl=bdcQllxService.makeSureQllx(bdcXmDO.getQllx().toString());
                    yql=bdcQllxService.queryQllxDO(bdcQl,yxmid);
                    initServiceQO.getYqlDataMap().put(yxmid,yql);
                }
            }
        }
        return yql;
    }

    /**
     * @param initServiceQO
     * @return 原同权利xmid
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据历史关系获取原同权利xmid
     */
    public String getYtqlXmid(InitServiceQO initServiceQO){
        if(StringUtils.isNotBlank(initServiceQO.getYtqlxmid())){
            return initServiceQO.getYtqlxmid();
        }
        List<BdcXmLsgxDO> listLsgx=null;
        if (initServiceQO != null) {
            //获取历史关系
            if(CollectionUtils.isNotEmpty(initServiceQO.getBdcXmLsgxList())){
                listLsgx=initServiceQO.getBdcXmLsgxList();
            }else{
                listLsgx=bdcXmLsgxService.queryBdcXmLsgxByXmid(initServiceQO.getXmid(),null);
            }
            if(CollectionUtils.isNotEmpty(listLsgx)){
                //升序排列
                listLsgx.sort((o1, o2) -> o1.getWlxm().compareTo(o2.getWlxm()));
                for(BdcXmLsgxDO bdcXmLsgxDO:listLsgx){
                    if(StringUtils.isNotBlank(bdcXmLsgxDO.getYxmid())){
                        BdcXmDO yxm=bdcXmService.queryBdcXmByPrimaryKey(bdcXmLsgxDO.getYxmid());
                        //读取原居住权项目的数据
                        if(yxm!=null && initServiceQO.getBdcXm().getQllx() != null &&initServiceQO.getBdcXm().getQllx().equals(yxm.getQllx())){
                            initServiceQO.setYtqlxmid(bdcXmLsgxDO.getYxmid());
                            return bdcXmLsgxDO.getYxmid();
                        }
                    }
                }
            }
        }
        return null;
    }
}
