package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.service.BdcdyLsSqlBuilderService;
import cn.gtmap.realestate.building.service.BdcdySqlBuilderService;
import cn.gtmap.realestate.building.service.InitDjxxService;
import cn.gtmap.realestate.building.util.BdcdySqlConstants;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.TzmUtils;
import cn.gtmap.realestate.common.util.InterfaceCodeBeanFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-25
 * @description 查询BDCDY的SQL构造器
 */
@Service
public class BdcdyLsSqlBuilderServiceImpl extends InterfaceCodeBeanFactory implements BdcdyLsSqlBuilderService {

    @Autowired
    private Set<InitDjxxService> initDjxxServiceSet;


    /**
     * @param dzwtzm
     * @param zdtzm
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取SQL主体LIST
     */
    @Override
    public List<String> getMainSqlList(String[] dzwtzm, String[] zdtzm,
                                       String[] fwlx, boolean withQlr,String qlrmh) {
        List<String> mainSqlList = new ArrayList<>();
        addWithDzwTzm(dzwtzm,zdtzm,fwlx,withQlr,mainSqlList,qlrmh);

        return mainSqlList;
    }

    private void addWithDzwTzm(String[] dzwtzm, String[] zdtzm,String[] fwlx, boolean withQlr, List<String> mainSqlList,String qlrmh){
        if(dzwtzm.length > 0){
            for(String tzm : dzwtzm){
                switch (StringUtils.upperCase(tzm)){
                    case "W" :
                        addTdWitZdTzm(zdtzm,withQlr,mainSqlList,qlrmh);
                        break;
                    case "F" :
                        addFSqlWithFwlx(fwlx,withQlr,mainSqlList,qlrmh);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    private void addTdWitZdTzm(String[] zdtzm, boolean withQlr, List<String> mainSqlList,String qlrmh){
        HashSet<String> beanSet = new HashSet();
        if(zdtzm.length == 0){
            beanSet.add(Constants.DJXX_SERVICE_ZD);
            beanSet.add(Constants.DJXX_SERVICE_QSZD);
            beanSet.add(Constants.DJXX_SERVICE_NYD);
            beanSet.add(Constants.DJXX_SERVICE_JYQDK);
        }else{
            for(String tzm : zdtzm){
                if(tzm.length() == 1){
                    String[] beanName= TzmUtils.getBeanNameWithTzm(tzm + "W");
                    if(beanName != null &&beanName.length>0) {
                        for (int i = 0; i < beanName.length; i++) {
                            beanSet.add(beanName[i]);
                        }
                    }
                }
            }
        }
        for(String beanCode : beanSet){
            InitDjxxService djxxService = getBean(initDjxxServiceSet, beanCode);
            if (djxxService != null) {
                String sql = djxxService.getLsBdcdyQuerySql(withQlr,qlrmh);
                if(StringUtils.isNotBlank(sql)){
                    mainSqlList.add(sql);
                }
            }
        }
    }



    private void addFSqlWithFwlx(String[] fwlx,boolean withQlr, List<String> mainSqlList,String qlrmh){
        if(fwlx.length == 0){
            fwlx = new String[]{"xmxx","ljz","hs"};
        }
        for(String lx : fwlx){
            switch (lx){
                case "xmxx" :
                    mainSqlList.add(BdcdySqlConstants.FW_LS_XMXX_SQL);
                    break;
                case "ljz" :
                    mainSqlList.add(BdcdySqlConstants.FW_LS_LJZ_SQL);
                    break;
                case "hs" :
                    mainSqlList.add(BdcdySqlConstants.FW_LS_HS_SQL);
                    break;
                default:
                    break;
            }
        }
    }
}
