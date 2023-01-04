package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcLqtjMxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.xuancheng.BdcLqtjDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcLqtjQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcLqCxMapper;
import cn.gtmap.realestate.inquiry.service.BdcLqcxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0  2022-11-21
 * @description 不动产林权信息查询
 */
@Service
public class BdcLqcxServiceImpl implements BdcLqcxService {

    @Value("#{${lqcxtj.djxl:null}}")
    private Map<String, String> djxlMap;

    @Autowired
    private BdcLqCxMapper bdcLqCxMapper;

    /**
     * @param bdcLqtjQO
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 林权数据统计
     * @date : 2022/11/21 11:13
     */
    @Override
    public List<BdcLqtjDTO> listTjCx(BdcLqtjQO bdcLqtjQO) {
        if(bdcLqtjQO == null || StringUtils.isBlank(bdcLqtjQO.getKssj())  || StringUtils.isBlank(bdcLqtjQO.getJzsj()) || org.apache.commons.collections4.CollectionUtils.isEmpty(bdcLqtjQO.getQxdmList())){
            throw new MissingArgumentException("缺失查询条件，开始时间、结束时间和区县代码都不能为空");
        }
        List<BdcLqtjDTO> bdcLqtjDTOList = new ArrayList<BdcLqtjDTO>();
        if(null != djxlMap){
            for (String key : djxlMap.keySet()) {
                if(StringUtils.isNotBlank(djxlMap.get(key))){
                    if(key.equals(CommonConstantUtils.XCLQTJDJLXMC_CFDJ) || key.equals(CommonConstantUtils.XCLQTJDJLXMC_JFDJ)){
                        bdcLqtjQO.setDjlx(CommonConstantUtils.XCLQTJDJLXDM_CFDJ);
                    }else if(key.equals(CommonConstantUtils.XCLQTJDJLXMC_ZXDJ)){
                        bdcLqtjQO.setDjlx(CommonConstantUtils.XCLQTJDJLXDM_ZXDJ);
                    }else if(key.equals(CommonConstantUtils.XCLQTJDJLXMC_DYZXDJ)){
                        bdcLqtjQO.setDjlx(CommonConstantUtils.XCLQTJDJLXDM_DYZXDJ);
                    }else if(key.equals(CommonConstantUtils.XCLQTJDJLXMC_DYSCDJ) || key.equals(CommonConstantUtils.XCLQTJDJLXMC_DYBGDJ)){
                        bdcLqtjQO.setDjlx(CommonConstantUtils.XCLQTJDJLXDM_DY);
                    }else{
                        bdcLqtjQO.setDjlx("");
                    }
                    String djxlList = djxlMap.get(key);
                    bdcLqtjQO.setDjxlList(Arrays.asList(djxlList.split(",")));
                    // 获取总的信息
                    BdcLqtjDTO bdcLqtjDTO = bdcLqCxMapper.queryBdcLqxx(bdcLqtjQO);
                    if(bdcLqtjDTO != null){
                        bdcLqtjDTO.setDjlxmc(key);
                        //查询国有林地数量
                        bdcLqtjQO.setLdzl("G");
                        BdcLqtjMxDTO bdcGyLqtj = bdcLqCxMapper.queryBdcLqxxmx(bdcLqtjQO);
                        if(bdcGyLqtj != null){
                            bdcLqtjDTO.setGylds(bdcGyLqtj.getLdzs());
                            bdcLqtjDTO.setGyldmj(bdcGyLqtj.getLdzmj());
                        }
                        //查询集体林地数量
                        bdcLqtjQO.setLdzl("J");
                        BdcLqtjMxDTO bdcJtLqtj = bdcLqCxMapper.queryBdcLqxxmx(bdcLqtjQO);
                        if(bdcJtLqtj != null){
                            bdcLqtjDTO.setJtlds(bdcJtLqtj.getLdzs());
                            bdcLqtjDTO.setJtldmj(bdcJtLqtj.getLdzmj());
                        }
                        bdcLqtjQO.setLdzl("");
                        bdcLqtjQO.setDjxlList(null);
                        // 权利类别林权的林地数和林地面积
                        List<BdcLqtjMxDTO> listBdcDjxlLqtj =new ArrayList<BdcLqtjMxDTO>();
                        // 根据登记小类查询每个权利类型的
                        String[] djxlArray = djxlList.split(",");
                        for(int i = 0; i < djxlArray.length; i++) {
                            if(CommonConstantUtils.XCLQTJ_DJXLWU.equals(djxlArray[i])){
                                BdcLqtjMxDTO bdcDjxlLqtj = new BdcLqtjMxDTO();
                                bdcDjxlLqtj.setLdzs(new BigDecimal(0));
                                bdcDjxlLqtj.setLdzmj(new BigDecimal(0));
                                listBdcDjxlLqtj.add(bdcDjxlLqtj);
                            }else{
                                bdcLqtjQO.setDjxl(djxlArray[i]);
                                BdcLqtjMxDTO bdcDjxlLqtj = bdcLqCxMapper.queryBdcLqxxmx(bdcLqtjQO);
                                listBdcDjxlLqtj.add(bdcDjxlLqtj);
                            }
                        }
                        bdcLqtjQO.setDjxl("");
                        // 给林权每个权利类型的林地数和林地面积赋值
                        if(CollectionUtils.isNotEmpty(listBdcDjxlLqtj) && listBdcDjxlLqtj.get(0) !=null){
                            bdcLqtjDTO.setLds1(listBdcDjxlLqtj.get(0).getLdzs());
                            bdcLqtjDTO.setLdmj1(listBdcDjxlLqtj.get(0).getLdzmj());
                            if(listBdcDjxlLqtj.size() > 1 && listBdcDjxlLqtj.get(1) !=null){
                                bdcLqtjDTO.setLds2(listBdcDjxlLqtj.get(1).getLdzs());
                                bdcLqtjDTO.setLdmj2(listBdcDjxlLqtj.get(1).getLdzmj());
                                if(listBdcDjxlLqtj.size() > 2 && listBdcDjxlLqtj.get(2) !=null){
                                    bdcLqtjDTO.setLds3(listBdcDjxlLqtj.get(2).getLdzs());
                                    bdcLqtjDTO.setLdmj3(listBdcDjxlLqtj.get(2).getLdzmj());
                                    if(listBdcDjxlLqtj.size() > 3 && listBdcDjxlLqtj.get(3) !=null){
                                        bdcLqtjDTO.setLds4(listBdcDjxlLqtj.get(3).getLdzs());
                                        bdcLqtjDTO.setLdmj4(listBdcDjxlLqtj.get(3).getLdzmj());
                                        if(listBdcDjxlLqtj.size() > 4 && listBdcDjxlLqtj.get(4) !=null){
                                            bdcLqtjDTO.setLds5(listBdcDjxlLqtj.get(4).getLdzs());
                                            bdcLqtjDTO.setLdmj5(listBdcDjxlLqtj.get(4).getLdzmj());
                                        }
                                    }
                                }
                            }
                        }
                        bdcLqtjDTOList.add(bdcLqtjDTO);
                    }
                }
            }
        }
        // 计算合计
        if(CollectionUtils.isNotEmpty(bdcLqtjDTOList)){

            BdcLqtjDTO bdcLqtjDTO = new BdcLqtjDTO();
            bdcLqtjDTO.setDjlxmc("合计");
            // 集体林地数
            BigDecimal jtlds = bdcLqtjDTOList.stream().filter(x->x.getJtlds() != null).map(x -> x.getJtlds()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setJtlds(jtlds);
            // 集体林地面积
            BigDecimal jtldmj = bdcLqtjDTOList.stream().filter(x->x.getJtldmj() != null).map(x -> x.getJtldmj()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setJtldmj(jtldmj);
            // 国有林地数
            BigDecimal gylds = bdcLqtjDTOList.stream().filter(x->x.getGylds() != null).map(x -> x.getGylds()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setGylds(gylds);
            // 国有林地面积
            BigDecimal gyldmj = bdcLqtjDTOList.stream().filter(x->x.getGyldmj() != null).map(x -> x.getGyldmj()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setGyldmj(gyldmj);
            // 林地总数
            BigDecimal ldzs = bdcLqtjDTOList.stream().filter(x->x.getLdzs() != null).map(x -> x.getLdzs()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLdzs(ldzs);
            // 林地总面积
            BigDecimal ldzmj = bdcLqtjDTOList.stream().filter(x->x.getLdzmj() != null).map(x -> x.getLdzmj()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLdzmj(ldzmj);
            // 林地使用权/森林、林木使用权 林地数
            BigDecimal lds1 = bdcLqtjDTOList.stream().filter(x->x.getLds1() != null).map(x -> x.getLds1()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLds1(lds1);
            // 林地使用权/森林、林木使用权 林地面积
            BigDecimal ldmj1 = bdcLqtjDTOList.stream().filter(x->x.getLdmj1() != null).map(x -> x.getLdmj1()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLdmj1(ldmj1);
            // 林地承包经营权/林木所有权 林地数
            BigDecimal lds2 = bdcLqtjDTOList.stream().filter(x->x.getLds2() != null).map(x -> x.getLds2()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLds2(lds2);
            // 林地承包经营权/林木所有权 林地面积
            BigDecimal ldmj2 = bdcLqtjDTOList.stream().filter(x->x.getLdmj2() != null).map(x -> x.getLdmj2()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLdmj2(ldmj2);
            // 林地使用权/林木所有权 林地数
            BigDecimal lds3 = bdcLqtjDTOList.stream().filter(x->x.getLds3() != null).map(x -> x.getLds3()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLds3(lds3);
            // 林地使用权/林木所有权 林地面积
            BigDecimal ldmj3 = bdcLqtjDTOList.stream().filter(x->x.getLdmj3() != null).map(x -> x.getLdmj3()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLdmj3(ldmj3);
            // 林地经营权/林木所有权 林地数
            BigDecimal lds4 = bdcLqtjDTOList.stream().filter(x->x.getLds4() != null).map(x -> x.getLds4()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLds4(lds4);
            // 林地经营权/林木所有权 林地面积
            BigDecimal ldmj4 = bdcLqtjDTOList.stream().filter(x->x.getLdmj4() != null).map(x -> x.getLdmj4()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLdmj4(ldmj4);
            // 林地经营权/林木使用权 林地数
            BigDecimal lds5 = bdcLqtjDTOList.stream().filter(x->x.getLds5() != null).map(x -> x.getLds5()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLds5(lds5);
            // 林地经营权/林木使用权 林地面积
            BigDecimal ldmj5 = bdcLqtjDTOList.stream().filter(x->x.getLdmj5() != null).map(x -> x.getLdmj5()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setLdmj5(ldmj5);
            // 抵押金额
            BigDecimal dyje = bdcLqtjDTOList.stream().filter(x->x.getDyje() != null).map(x -> x.getDyje()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setDyje(dyje);
            // 办件量
            BigDecimal bjl = bdcLqtjDTOList.stream().filter(x->x.getBjl() != null).map(x -> x.getBjl()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setBjl(bjl);
            // 发证数
            BigDecimal fzs = bdcLqtjDTOList.stream().filter(x->x.getFzs() != null).map(x -> x.getFzs()).reduce(BigDecimal.ZERO,BigDecimal::add);
            bdcLqtjDTO.setFzs(fzs);

            //登记类型为地役的目前不展示，默认为空
            BdcLqtjDTO bdcLqtjdyscDTO = new BdcLqtjDTO();
            bdcLqtjdyscDTO.setDjlxmc(CommonConstantUtils.XCLQTJDJLXMC_DIYSCDJ);
            bdcLqtjDTOList.add(bdcLqtjdyscDTO);
            BdcLqtjDTO bdcLqtjdyzxDTO = new BdcLqtjDTO();
            bdcLqtjdyzxDTO.setDjlxmc(CommonConstantUtils.XCLQTJDJLXMC_DIYZXDJ);
            bdcLqtjDTOList.add(bdcLqtjdyzxDTO);
            BdcLqtjDTO bdcLqtjdybgDTO = new BdcLqtjDTO();
            bdcLqtjdybgDTO.setDjlxmc(CommonConstantUtils.XCLQTJDJLXMC_DIYBGDJ);
            bdcLqtjDTOList.add(bdcLqtjdybgDTO);
            bdcLqtjDTOList.add(bdcLqtjDTO);
        }
        return bdcLqtjDTOList;
    }

}
