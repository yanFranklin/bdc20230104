package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcdyxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.dazt.DaztDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcdyxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.DaxxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.service.BdcdyXxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2019-05-22
 * @description 不动产单元查询服务
 */
@Service
public class BdcdyXxServiceImpl implements BdcdyXxService {

    public static final Logger logger = LoggerFactory.getLogger(BdcdyXxServiceImpl.class);
    @Autowired
    private Repo repo;

    /**
     * 不动产单元状态查询
     */
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;

    /*
     * 权利类型过滤列表
     */
    @Value("#{'${bdcdycx.qllx:}'.split(',')}")
    private List<String> qllxList;

    /**
     * @param pageable  分页参数
     * @param bdcdyxxQO 查询Qo
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询已登记不动产单元信息
     */
    @Override
    public Page<BdcdyxxDTO> listBdcdyxxByPage(Pageable pageable, BdcdyxxQO bdcdyxxQO) {
        // 如果没有配置不动产单元查询过滤类型，则获取默认过滤类型
        if (CollectionUtils.isNotEmpty(qllxList) && qllxList.size() == 1 && StringUtils.isBlank(qllxList.get(0))) {
            bdcdyxxQO.setQllxList(Arrays.asList(CommonConstantUtils.BDCDYCX_QLLX));
        } else {
            bdcdyxxQO.setQllxList(qllxList);
        }
        // 转换list类型为数字
        List<String> qllxStrList = bdcdyxxQO.getQllxList();
        if (CollectionUtils.isNotEmpty(qllxStrList)) {
            List<Integer> qllxListInt = new ArrayList<>();
            for (String qllxStr : qllxStrList) {
                qllxListInt.add(Integer.valueOf(qllxStr));
            }
            bdcdyxxQO.setQllxListInt(qllxListInt);
        }



        Page<BdcdyxxDTO> bdcdyxxDTOPage = repo.selectPaging("listBdcdyxxByPage", this.resolveParam(bdcdyxxQO), pageable);

        if (null == bdcdyxxDTOPage || CollectionUtils.isEmpty(bdcdyxxDTOPage.getContent())) {
            return bdcdyxxDTOPage;
        }
        // 获取不动产单元状态
        List<BdcdyxxDTO> bdcdyxxDTOList = bdcdyxxDTOPage.getContent();
        /// 1、先获取不动产单元号集合
        List<String> bdcdyhList = new ArrayList<>(bdcdyxxDTOList.size());
        for (BdcdyxxDTO bdcdyxxDTO : bdcdyxxDTOList) {
            bdcdyhList.add(bdcdyxxDTO.getBdcdyh());
        }
        /// 2、调用权籍获取状态
        List<BdcdyhZtResponseDTO> bdcdyhZtDTOList = bdcdyZtFeignService.commonListBdcdyhZtBybdcdyh(bdcdyhList,"");
        if (CollectionUtils.isEmpty(bdcdyhZtDTOList)) {
            return bdcdyxxDTOPage;
        }
        /// 3、匹配设置不动产单元状态
        for (BdcdyxxDTO bdcdyxxDTO : bdcdyxxDTOList) {
            for (BdcdyhZtResponseDTO bdcdyhZtDTO : bdcdyhZtDTOList) {
                if (org.apache.commons.lang3.StringUtils.equals(bdcdyxxDTO.getBdcdyh(), bdcdyhZtDTO.getBdcdyh())) {
                    bdcdyxxDTO.setBdcdyZtDTO(bdcdyhZtDTO);
                    break;
                }
            }
        }

        return bdcdyxxDTOPage;
    }

    /**
     * 根据gzlslid查询档案柜信息
     *
     * @param pageable
     * @param gzlslid
     * @return DaztDTO
     * @Date 2022/1/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<DaztDTO> listDaxxByPage(Pageable pageable, DaxxQO daxxQO) {
        Page<DaztDTO> daztDTOPage = repo.selectPaging("listDagxxByPage", daxxQO, pageable);

        return daztDTOPage;
    }

    /**
     * @param bdcdyxxQO 查询条件
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 处理查询参数
     */
    private Object resolveParam(BdcdyxxQO bdcdyxxQO) {
        // 单元号再次单独去除空格
        if (StringUtils.isNotBlank(bdcdyxxQO.getBdcdyh())) {
            bdcdyxxQO.setBdcdyh(StringUtils.deleteWhitespace(bdcdyxxQO.getBdcdyh()));
        }

        // 处理中文括号
        if (StringUtils.isNotBlank(bdcdyxxQO.getQlrmc())) {
            bdcdyxxQO.setQlrmc(StringToolUtils.replaceBracket(bdcdyxxQO.getQlrmc()));
        }
        if (StringUtils.isNotBlank(bdcdyxxQO.getZl())) {
            bdcdyxxQO.setZl(StringToolUtils.replaceBracket(bdcdyxxQO.getZl()));
        }

        return bdcdyxxQO;
    }
}
