package cn.gtmap.realestate.inquiry.service;


import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.HefeiDzzzZzDataDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDzzzCxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzZzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/2/24
 * @description 电子证照操作服务接口
 */
public interface BdcDzzzCzService {

    /**
     * 查询不动产登记库中的证书、证明，可通过受理编号、坐落、产权证号精确查询和模糊查询。
     *
     * @param pageable  分页
     * @param bdcDzzzQO 台账查询参数
     * @return 分页证书证明
     * @Date 2020/2/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<BdcDzzzCxDTO> listBdcDzzzByPage(Pageable pageable, BdcDzzzQO bdcDzzzQO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcDzzzZzQO]
     * @return java.lang.Object
     * @description 查询电子证照需要制证的台账 （权属状态为现势、流程状态已办结但没有生成电子证照的数据）
     */
    Page<HefeiDzzzZzDataDTO> queryDzzzZz(BdcDzzzZzQO bdcDzzzZzQO);


}
