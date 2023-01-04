package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.gtc.workflow.domain.common.RequestCondition;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.HefeiDzzzZzDataDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDzzzCxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzZzQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzAppearCityFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzGxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZszmMapper;
import cn.gtmap.realestate.inquiry.service.BdcDzzzCzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/2/24
 * @description 电子证照操作服务接口实现类
 */
@Service
public class BdcDzzzCzServiceImpl implements BdcDzzzCzService {

    public static final Logger logger = LoggerFactory.getLogger(BdcDzzzCzServiceImpl.class);

    @Autowired
    Repo repo;

    @Autowired
    private BdcDzzzAppearCityFeignService bdcDzzzAppearCityFeignService;

    @Autowired
    private BdcDzzzGxFeignService bdcDzzzGxFeignService;

    @Autowired
    private BdcZszmMapper bdcZszmMapper;

    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;

    /**
     * 查询不动产登记库中的证书、证明，可通过受理编号、坐落、产权证号精确查询和模糊查询。
     *
     * @param pageable  分页
     * @param bdcDzzzQO 电子证照台账查询参数
     * @return 分页证书证明
     * @Date 2020/2/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<BdcDzzzCxDTO> listBdcDzzzByPage(Pageable pageable, BdcDzzzQO bdcDzzzQO) {

        Page<BdcDzzzCxDTO> bdcZsTjDTOs= repo.selectPaging("listDzzzZszmByPageOrder", bdcDzzzQO,pageable);
        return bdcZsTjDTOs;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcDzzzZzQO]
     * @return java.lang.Object
     * @description 合肥 查询电子证照需要制证的台账 （权属状态为现势、流程状态已办结但没有生成电子证照的数据）
     */
    @Override
    public Page<HefeiDzzzZzDataDTO> queryDzzzZz(BdcDzzzZzQO bdcDzzzZzQO) {
        Pageable pageable = new PageRequest(bdcDzzzZzQO.getPage() -1,bdcDzzzZzQO.getSize());
        Page<HefeiDzzzZzDataDTO> dzzzZzDataDTOList = repo.selectPaging("listWzzZsDataByPage", bdcDzzzZzQO, pageable);
        return dzzzZzDataDTOList;
    }

}
