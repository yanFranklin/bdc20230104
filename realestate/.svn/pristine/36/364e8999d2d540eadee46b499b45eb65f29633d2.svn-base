package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYyQO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcYyVO;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcYyXxMapper;
import cn.gtmap.realestate.inquiry.service.BdcYyXxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-07-11
 * @description 不动产异议信息业务类
 */
@Service
public class BdcYyXxServiceImpl implements BdcYyXxService {

    public static final Logger LOGGER = LoggerFactory.getLogger(BdcYyXxServiceImpl.class);
    @Autowired
    private Repo repo;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private BdcYyXxMapper bdcYyXxMapper;

    /**
     * @param pageable bdcYyDO
     * @param bdcYyQO
     * @return Page<BdcYyDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产异议分页信息
     */
    @Override
    public Page<BdcYyVO> listBdcYyxxByPage(Pageable pageable, BdcYyQO bdcYyQO) {
        this.handleBdcYyQueryData(bdcYyQO);
        return repo.selectPaging("listBdcYyXxByPage", bdcYyQO, pageable);
    }

    /**
     * 处理异议查询参数值：去除不动产单元号空格，权利人证件号 15位至18位的转换
     */
    private void handleBdcYyQueryData(BdcYyQO bdcYyQO){
        if (bdcYyQO != null && StringUtils.isNotBlank(bdcYyQO.getBdcdyh())) {
            bdcYyQO.setBdcdyh(StringUtils.deleteWhitespace(bdcYyQO.getBdcdyh()));
        }
        // 处理权利人证件号:18位和15位转换；其他证件号：统一返回大写
        if(bdcYyQO !=null && null != bdcYyQO.getQlrzjh() && bdcYyQO.getQlrzjh().length > 0){
            bdcYyQO.setQlrzjh(Stream.of(bdcYyQO.getQlrzjh()).map(e ->
                    CardNumberTransformation.zjhTransformation(e)).collect(Collectors.joining(",")).split(","));
        }
    }

    @Override
    public Page<BdcYyVO> listBdcYyxxByPageCz(Pageable pageable, BdcYyQO bdcYyQO) {
        this.handleBdcYyQueryData(bdcYyQO);
        return repo.selectPaging("listBdcYyXxByPageCz", bdcYyQO, pageable);
    }

    /**
     * @param bdcYyQO
     * @return Page<BdcYyDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产异议分页信息
     */
    @Override
    public List listBdcYyxx(BdcYyQO bdcYyQO) {
        this.handleBdcYyQueryData(bdcYyQO);
        return repo.selectList("listBdcYyXx", bdcYyQO);
    }

    @Override
    public List listBdcYyxxCz(BdcYyQO bdcYyQO) {
        this.handleBdcYyQueryData(bdcYyQO);
        return repo.selectList("listBdcYyXxByPageCz", bdcYyQO);
    }

    /**
     * @param bdcYyDOList@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 批量更新异议
     */
    @Override
    public int updateBdcYy(List<BdcYyDO> bdcYyDOList) {
        List <String> listXmids= new ArrayList<>();
        String zxyy = "";
        if(CollectionUtils.isNotEmpty(bdcYyDOList)){
            for(BdcYyDO bdcYyDO:bdcYyDOList){
                listXmids.add(bdcYyDO.getXmid());
                zxyy = bdcYyDO.getZxyyyy();
            }
        }
        BdcZxQO bdcZxQO = new BdcZxQO();
        bdcZxQO.setXmidList(listXmids);
        bdcZxQO.setQszt(CommonConstantUtils.QSZT_HISTORY);
        bdcZxQO.setZxyy(zxyy);
        bdcDbxxFeignService.zxQl(bdcZxQO,userManagerUtils.getCurrentUserName());
        int i=0;
        if(CollectionUtils.isNotEmpty(bdcYyDOList)){
            for(BdcYyDO bdcYyDO:bdcYyDOList){
                bdcYyDO.setQszt(null);
                i+=entityMapper.updateByPrimaryKeySelective(bdcYyDO);
            }
        }

        return i;
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询异议
     */
    @Override
    public List listBdcYyByXmid(String xmid) {
        return bdcYyXxMapper.listBdcYyByXmid(xmid);
    }

    @Override
    public List listBdcYyByBdcdyh(String bdcdyh) {
        return bdcYyXxMapper.listBdcYyByBdcdyh(bdcdyh);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询抵押信息
     */
    @Override
    public List<BdcYyDO> listBdcYyByBdcdyhs(List<String> bdcdyhList) {
        if(CollectionUtils.isEmpty(bdcdyhList)){
            return Lists.newArrayList();
        }
        return bdcYyXxMapper.listBdcYyByBdcdyhs(bdcdyhList);
    }
}
