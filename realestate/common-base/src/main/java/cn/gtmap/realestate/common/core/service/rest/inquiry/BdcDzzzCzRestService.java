package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.HefeiDzzzZzDataDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDzzzCxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzZzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/2/24
 * @description 电子证照操作服务接口
 */
public interface BdcDzzzCzRestService {

    /**
     * 查询不动产登记库中的证书、证明，可通过受理编号、坐落、产权证号精确查询和模糊查询,用于电子证照的补发和注销
     *
     * @param pageable      分页
     * @param bdcDzzzQOJson 台账查询参数
     * @return 分页证书证明
     * @Date 2020/2/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/dzzzcz/page")
    Page<BdcDzzzCxDTO> listBdcDzzzByPage(Pageable pageable,
                                         @RequestParam(name = "bdcDzzzQOJson", required = false) String bdcDzzzQOJson);


    /**
     * 获取访问电子证照token
     *
     * @return token
     * @Date 2020/2/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/dzzzcz/getToken")
    String getToken();

    /**
     * 创建电子证照
     *
     * @param xmids
     * @return 创建个数
     * @Date 2020/2/29
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dzzzcz/createDzzz")
    Integer createDzzz(@RequestBody List<String> xmids);

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/2/29
     * @description 注销电子证照
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dzzzcz/cancelDzzz")
    Integer cancelDzzz(@RequestBody List<String> xmids);

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/2/29
     * @description 作废电子证照
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dzzzcz/zfDzzz")
    Integer zfDzzz(@RequestBody List<String> xmids);

    /**
     * 判断电子证照是否是历史状态
     *
     * @param zsids 证书ids
     * @return flag
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dzzzcz/queryDzzzZt")
    Integer sfYzx(@RequestBody List<String> zsids);

    /**
     * 下载电子证照并上传到登记
     *
     * @param xmid xmid
     * @date 2020/7/7
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dzzzcz/reUploadDzzz")
    void reUploadDzzz(@RequestParam(value = "xmid") String xmid);

    /**
     * 更新电子证照的 storageid 到证书表
     *
     * @param stroageid stroageid
     * @date 2020/7/14
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dzzzcz/stroageid")
    void updateZsStroageId(@RequestParam(value = "stroageid") String stroageid, @RequestParam(value = "zsid") String zsid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcDzzzZzQO]
     * @return java.lang.Object
     * @description 合肥 查询电子证照需要制证的台账 （权属状态为现势、流程状态已办结但没有生成电子证照的数据）
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/dzzzcz/queryDzzzZz")
    Page<HefeiDzzzZzDataDTO> queryDzzzZz(@RequestBody BdcDzzzZzQO bdcDzzzZzQO);
}
