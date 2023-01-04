package cn.gtmap.realestate.init.service.tshz.hefei;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 根据项目关系读取原产权证号做拼接的登记小类
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/29.
 * @description
 */
@Service("xmgxDqYcqzhServiceImpl_hefei")
@ConfigurationProperties(prefix = "djxl")
public class InitXmgxDqYcqzhServiceImpl implements InitBdcTsHzService {
    /**
     * 项目关系查找原产权证号
     */
    private List<String> xmgxdqycqzh;
    @Autowired
    private InitServiceQoService initServiceQoService;
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;

    /**
     * 根据项目关系读取原产权证号做拼接的登记小类
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //判定上一首是产权的做拼接
        if(CollectionUtils.isNotEmpty(xmgxdqycqzh) && !initServiceQO.isSfzqlpbsj() &&
                initServiceDTO!=null &&  StringUtils.isNotBlank(initServiceQO.getYxmid())
                && initServiceDTO.getBdcXm()!=null && xmgxdqycqzh.contains(initServiceDTO.getBdcXm().getDjxl())){
            BdcXmDO ybdcxm=initServiceQoService.queryYbdcxm(initServiceQO.getYxmid(),initServiceQO);
            //原项目来源为2和3的数据,做处理
            if(ybdcxm!=null && Arrays.asList(2,3).contains(ybdcxm.getXmly())){
                List<String> listZh=new ArrayList<>();
                boolean hasCq=false;
                List<BdcXmLsgxDO> listLsgx=initServiceQO.getBdcXmLsgxList();
                int size=0;
                while(true){
                    String yxmid=StringUtils.EMPTY;
                    if(CollectionUtils.isNotEmpty(listLsgx)){
                        //排序升序
                        listLsgx.sort((o1, o2) -> Integer.compare(o1.getWlxm()==null? 0 : o1.getWlxm(),o2.getWlxm()==null? 0 : o2.getWlxm()));
                        for(BdcXmLsgxDO bdcXmLsgxDO:listLsgx){
                            BdcXmDO yxm=initServiceQoService.queryYbdcxm(bdcXmLsgxDO.getYxmid(),initServiceQO);
                            if(!CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getWlxm())){
                                yxmid=yxm.getXmid();
                            }
                            //产权类的
                            if(Arrays.asList(CommonConstantUtils.BDC_ZS_QLLX).contains(yxm.getQllx())){
                                if(CommonConstantUtils.SF_S_DM.equals(bdcXmLsgxDO.getWlxm())){
                                    if(hasCq && StringUtils.isNotBlank(yxm.getBdcqzh()) && !listZh.contains(yxm.getBdcqzh())){
                                        listZh.add(yxm.getBdcqzh());
                                    }
                                }else{
                                    hasCq=true;
                                    if(StringUtils.isNotBlank(yxm.getBdcqzh()) && !listZh.contains(yxm.getBdcqzh())){
                                        listZh.add(0,yxm.getBdcqzh());
                                    }
                                    if(StringUtils.isNotBlank(yxm.getYxtcqzh()) && !listZh.contains(yxm.getYxtcqzh())){
                                        listZh.add(yxm.getYxtcqzh());
                                    }
                                }
                            }
                        }
                    }else{
                        hasCq=true;
                    }
                    //20次就跳出循环  防止死循环
                    if(hasCq || StringUtils.isBlank(yxmid) || size>20){
                        break;
                    }
                    listLsgx=bdcXmLsgxService.queryBdcXmLsgxByXmid(yxmid,null);
                    size++;
                }
                initServiceDTO.getBdcXm().setYcqzh(StringUtils.join(listZh, ","));
            }
        }
        return initServiceDTO;
    }

    public List<String> getXmgxdqycqzh() {
        return xmgxdqycqzh;
    }

    public void setXmgxdqycqzh(List<String> xmgxdqycqzh) {
        this.xmgxdqycqzh = xmgxdqycqzh;
    }
}
