package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.service.*;
import cn.gtmap.realestate.building.service.GjZdService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-13
 * @description 挂接宗地服务
 */
@Service
public class GjZdServiceImpl implements GjZdService {

    @Autowired
    private BdcdyhService bdcdyhService;

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private FwHsService fwHsService;

    @Autowired
    private FwYcHsService fwYcHsService;

    /**
     * @param fwLjzDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 逻辑幢挂接宗地
     */
    @Override
    public void ljzGjzd(FwLjzDO fwLjzDO) {
        if(fwLjzDO != null
                && StringUtils.isNotBlank(fwLjzDO.getFwDcbIndex())
                && StringUtils.isNotBlank(fwLjzDO.getBdcdyfwlx())
                && StringUtils.isNotBlank(fwLjzDO.getLszd())
                && StringUtils.isNotBlank(fwLjzDO.getZrzh())){

            // 独幢类型 修改BDCDYH
            if(StringUtils.equals(Constants.BDCDYFWLX_DZ,fwLjzDO.getBdcdyfwlx())){
                dzlx(fwLjzDO);
            }

            // 户室类型
            if(StringUtils.equals(Constants.BDCDYFWLX_H,fwLjzDO.getBdcdyfwlx())){
                hslx(fwLjzDO);
            }

            /**
             * 多幢类型 不处理项目信息
            if(StringUtils.equals(Constants.BDCDYFWLX_XMNDZ,fwLjzDO.getBdcdyfwlx())
                    && StringUtils.isNotBlank(fwLjzDO.getFwXmxxIndex())){
                xmdzlx(fwLjzDO);
            }*/
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return void
     * @description 取消挂接宗地
     */
    @Override
    public void ljzQxgjzd(String fwDcbIndex) {
        FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
        fwLjzDO.setLszd(null);
        fwLjzDO.setZrzh(null);
        fwLjzDO.setBdcdyh(null);
        fwLjzService.updateFwLjzWithChangeBdcdyh(fwLjzDO,true);
        // 户室类型
        if(fwLjzDO != null && StringUtils.equals(Constants.BDCDYFWLX_H,fwLjzDO.getBdcdyfwlx())){
            // 查询房屋户室
            List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwDcbIndex);
            if(CollectionUtils.isNotEmpty(fwHsDOList)){
                for(FwHsDO fwHsDO : fwHsDOList){
                    if(StringUtils.isNotBlank(fwHsDO.getBdcdyh())){
                        String ybdcdyh = fwHsDO.getBdcdyh();
                        fwHsDO.setBdcdyh(null);
                        fwHsService.updateFwHsWithChangeBdcdyh(fwHsDO,true,ybdcdyh);
                    }
                }
            }

            // 查询预测户室
            List<FwYchsDO> fwYchsDOList = fwYcHsService.queryYchsByFwDcbIndex(fwDcbIndex);
            if(CollectionUtils.isNotEmpty(fwYchsDOList)){
                for(FwYchsDO ychs : fwYchsDOList){
                    if(StringUtils.isNotBlank(ychs.getBdcdyh())){
                        String ybdcdyh = ychs.getBdcdyh();
                        ychs.setBdcdyh(null);
                        fwYcHsService.updateFwYchsWithChangeBdcdyh(ychs,true,ybdcdyh);
                    }
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwLjzDO
     * @return void
     * @description 独幢类型
     */
    private void dzlx(FwLjzDO fwLjzDO){
        String ybdcdyh = fwLjzDO.getBdcdyh();
        if(StringUtils.isBlank(ybdcdyh)
                || !StringUtils.equals(ybdcdyh.substring(0,19),fwLjzDO.getLszd())){
            String bdcdyh = bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(fwLjzDO.getLszd(),fwLjzDO.getZrzh());
            if(StringUtils.isNotBlank(bdcdyh)){
                FwLjzDO newLjz = new FwLjzDO();
                newLjz.setFwDcbIndex(fwLjzDO.getFwDcbIndex());
                newLjz.setBdcdyh(bdcdyh);
                fwLjzService.updateFwLjzWithChangeBdcdyh(newLjz,false,ybdcdyh);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwLjzDO
     * @return void
     * @description 项目内多幢类型
    private void xmdzlx(FwLjzDO fwLjzDO){
        FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(fwLjzDO.getFwXmxxIndex());
        if(fwXmxxDO != null){
            String ybdcdyh = fwXmxxDO.getBdcdyh();
            if(StringUtils.isBlank(ybdcdyh)
                    || !StringUtils.equals(ybdcdyh.substring(0,19),fwLjzDO.getLszd())){
                String newBdcdyh = bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(fwLjzDO.getLszd(),fwLjzDO.getZrzh());
                fwXmxxDO.setBdcdyh(newBdcdyh);
                fwXmxxService.updateFwXmxxWithChangeBdcdyh(fwXmxxDO,false,ybdcdyh);
            }
        }
    }*/

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwLjzDO
     * @return void
     * @description 户室类型
     */
    private void hslx(FwLjzDO fwLjzDO){
        // 处理FW_HS
        List<FwHsDO> fwHsDOList = fwHsService.listFwHsListByFwDcbIndex(fwLjzDO.getFwDcbIndex());
        if(CollectionUtils.isNotEmpty(fwHsDOList)){
            for(FwHsDO hs : fwHsDOList){
                String ybdcdyh = hs.getBdcdyh();
                if(StringUtils.isBlank(ybdcdyh)
                        || !StringUtils.equals(ybdcdyh.substring(0,19),fwLjzDO.getLszd())){
                    String newBdcdyh = bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(fwLjzDO.getLszd(),fwLjzDO.getZrzh());
                    hs.setBdcdyh(newBdcdyh);
                    fwHsService.updateFwHsWithChangeBdcdyh(hs,false,ybdcdyh);
                }
            }
        }

        // 处理FW_YCHS
        List<FwYchsDO> fwYchsDOList = fwYcHsService.queryYchsByFwDcbIndex(fwLjzDO.getFwDcbIndex());
        if(CollectionUtils.isNotEmpty(fwYchsDOList)){
            for(FwYchsDO fwYchsDO : fwYchsDOList){
                String ybdcdyh = fwYchsDO.getBdcdyh();
                if(StringUtils.isBlank(ybdcdyh)
                        || !StringUtils.equals(ybdcdyh.substring(0,19),fwLjzDO.getLszd())){
                    String newBdcdyh = bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(fwLjzDO.getLszd(),fwLjzDO.getZrzh());
                    fwYchsDO.setBdcdyh(newBdcdyh);
                    fwYcHsService.updateFwYchsWithChangeBdcdyh(fwYchsDO,false,ybdcdyh);
                }
            }
        }
    }
}
