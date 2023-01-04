package cn.gtmap.realestate.init.service.lhxx;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmJsydlhxxGxDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmJsydlhxxGxService;
import cn.gtmap.realestate.init.service.InitService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 建设用地量化
 */
public abstract class InitBdcJsydLhxxAbstractService implements InitService {

    @Autowired
    private BdcXmJsydlhxxGxService bdcXmJsydlhxxGxService;

    @Override
    public String getCode() {
        return "sfsclhgx";
    }

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
        initServiceDTO.setBdcXmJsydlhxxGxList(initBdcXmJsydlhxxGxList(initServiceQO));
        return initServiceDTO;

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Object> delete(List<BdcXmDO> list, Boolean sfzqlpbsj, Boolean sfdzbflpbsj, Boolean plDel) throws Exception {
        List<Object> deleteList=new ArrayList<>();
        //抽取楼盘表数据时 不删除
        if(sfzqlpbsj){
            return deleteList;
        }
        if(CollectionUtils.isNotEmpty(list)){
            if(plDel){
                String gzlslid=list.get(0).getGzlslid();
                if(StringUtils.isNotBlank(gzlslid)){
                    //删除项目建设用地量化关系
                    bdcXmJsydlhxxGxService.deleteBdcXmJsydlhxxGx(gzlslid);

                }
            }else{
                for(BdcXmDO bdcXmDO:list){
                    List<BdcXmJsydlhxxGxDO> bdcXmJsydlhxxGxList=bdcXmJsydlhxxGxService.listBdcXmJsydlhxxGxByGzlslid(bdcXmDO.getXmid());
                    if(CollectionUtils.isNotEmpty(bdcXmJsydlhxxGxList)){
                        deleteList.addAll(bdcXmJsydlhxxGxList);
                    }
                }
            }
        }
        return deleteList;

    }

    @Override
    public InitServiceDTO query(BdcXmDO bdcXmDO, InitServiceDTO initServiceDTO) throws Exception{
        //不做处理
        return initServiceDTO;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    public abstract List<BdcXmJsydlhxxGxDO> initBdcXmJsydlhxxGxList(InitServiceQO initServiceQO);
}
