package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcFphDO;
import cn.gtmap.realestate.common.core.domain.BdcFphSymxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxCzrzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.domain.exchange.DjfDjSfDO;
import cn.gtmap.realestate.common.core.enums.BdcZssyqkEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.dto.common.FdcqQlWithXmQlrDTO;
import cn.gtmap.realestate.exchange.core.dto.common.QueryQlRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfbcsfxx.request.ZzjfBcSfxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfbcsfxx.request.ZzjfBcSfxxRequestData;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.request.ZzjfCxSfxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.response.ZzjfCxSfxxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.response.ZzjfCxSfxxResponseData;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.response.ZzjfCxSfxxResponseHead;
import cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.response.ZzjfCxSfxxResponseSfxmXX;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.DjfDjSfMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-29
 * @description 南通自助缴费机相关服务
 */
@Service
public class ZzjfService {

    @Autowired
    private Repo repository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    private BdcSlSfxmFeignService bdcSlSfxmFeignService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private DjfDjSfMapper djfDjSfMapper;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Value("${zzjfj.sfxx.czr:自助缴费机}")
    private String zzjfjCzr;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ZzjfService.class);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.response.ZzjfCxSfxxResponseDTO
     * @description 查询收费信息列表
     */
    public ZzjfCxSfxxResponseDTO cxSfxx(ZzjfCxSfxxRequestDTO requestDTO){
        ZzjfCxSfxxResponseDTO responseDTO = new ZzjfCxSfxxResponseDTO();
        String slbh = requestDTO.getData().getSlbh();
        if(StringUtils.isBlank(slbh)){
            throw new MissingArgumentException("slbh");
        }
        List<BdcXmDO> bdcXmDOList = commonService.listBdcXmBySlbh(slbh);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            String gzlslid = bdcXmDO.getGzlslid();

            // 查询FDCQ 权利信息
            QueryQlRequestDTO qlReq = new QueryQlRequestDTO();
            qlReq.setBdcdyh(bdcXmDO.getBdcdyh());
            qlReq.setQszt("0,1,2");
            List<FdcqQlWithXmQlrDTO> fdcqQlList = commonService.listFdcqByBdcdyh(qlReq);
            if(CollectionUtils.isNotEmpty(fdcqQlList)){
                BdcFdcqDO bdcFdcqDO = fdcqQlList.get(0).getBdcql();
                // 根据流程ID 查询收费信息列表
                List<BdcSlSfxxDO> bdcSlSfxxList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(gzlslid);
                List<ZzjfCxSfxxResponseData> dataList = new ArrayList<>();
                if(CollectionUtils.isNotEmpty(bdcSlSfxxList) && bdcFdcqDO != null){
                    for(int i = 0 ;i < bdcSlSfxxList.size();i++){
                        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxList.get(i);
                        Map<String,Object> paramMap = new HashMap<>();
                        paramMap.put("xm",bdcXmDO);
                        paramMap.put("fdcq",bdcFdcqDO);
                        paramMap.put("sfxx",bdcSlSfxxDO);
                        ZzjfCxSfxxResponseData data = exchangeBeanRequestService.request("nt_zzjf_cxsfxx_sfxx",paramMap,ZzjfCxSfxxResponseData.class);
                        if(data != null){
                            data.setSfxxid(bdcSlSfxxDO.getSfxxid());
                            data.setRownum_((i+1)+"");
                            // 查询 SFXM list
                            List<BdcSlSfxmDO> bdcSlSfxmList = bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                            if(CollectionUtils.isNotEmpty(bdcSlSfxmList)){
                                List<ZzjfCxSfxxResponseSfxmXX> sfxmList = new ArrayList<>();
                                for(BdcSlSfxmDO sfxmDO : bdcSlSfxmList){
                                    paramMap.clear();
                                    paramMap.put("sfxx",bdcSlSfxxDO);
                                    paramMap.put("sfxm",sfxmDO);
                                    ZzjfCxSfxxResponseSfxmXX sfxmXX = exchangeBeanRequestService.request("nt_zzjf_cxsfxx_sfxm",paramMap,ZzjfCxSfxxResponseSfxmXX.class);
                                    if(sfxmXX != null){
                                        sfxmList.add(sfxmXX);
                                    }
                                }
                                if(CollectionUtils.isNotEmpty(sfxmList)){
                                    data.setSfxmxx(sfxmList);
                                }
                            }
                            dataList.add(data);
                        }
                    }
                }
                responseDTO.setData(dataList);
                ZzjfCxSfxxResponseHead head = new ZzjfCxSfxxResponseHead();
                //这里先写死成功的返回码，后期统一异常处理返回码
                head.setReturncode("0000");
                head.setTotal("1");
                head.setRecords(dataList.size()+"");
                head.setPage("1");
                head.setPageSize("1000");
                responseDTO.setHead(head);
            }
        }
        return responseDTO;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return void
     * @description 保存收费信息
     */
    public void bcSfxx(ZzjfBcSfxxRequestDTO requestDTO){
        String sfxmid = null;
        ZzjfBcSfxxRequestData data = requestDTO.getData();
        if(data != null){
            LOGGER.info("保存收费信息实体：{}", JSONObject.toJSONString(data));
            sfxmid = data.getSfxmid();
        }
        if(StringUtils.isBlank(sfxmid)){
            throw new MissingArgumentException("sfxmid");
        }
        BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmFeignService.queryBdcSlSfxmBySfxmid(sfxmid);
        if(bdcSlSfxmDO != null){
            String sfxxid = bdcSlSfxmDO.getSfxxid();
            BdcSlSfxxDO bdcSlSfxxDO = exchangeBeanRequestService.request("nt_zzjf_bcsfxx_sfxx",data,BdcSlSfxxDO.class);
            if(bdcSlSfxxDO != null){
                bdcSlSfxxDO.setSfxxid(sfxxid);
                bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);

                // 南通自助缴费后需要更新fph和fphsymx
               if(StringUtils.isNotBlank(data.getFphm())){
                   LOGGER.info("开始更新发票号相关信息");
                   BdcFphDO bdcFphDO = new BdcFphDO();
                   bdcFphDO.setFph(data.getFphm());
                   Example example = new Example(BdcFphDO.class);
                   example.createCriteria().andEqualTo("fph", bdcFphDO.getFph());
                   List<BdcFphDO> bdcFphDOList = entityMapper.selectByExample(example);
                   if(CollectionUtils.isNotEmpty(bdcFphDOList)){
                       // 生成使用明细id
                       BdcFphSymxDO bdcFphSymxDO = new BdcFphSymxDO();
                       bdcFphSymxDO.setFphsymxid(UUIDGenerator.generate16());

                       bdcFphDO = bdcFphDOList.get(0);
                       bdcFphDO.setSyqk(CommonConstantUtils.SYQK_YSY);
                       bdcFphDO.setSfxxid(sfxxid);
                       bdcFphDO.setFphsymxid(bdcFphSymxDO.getFphsymxid());
                       bdcFphDO.setSlbh(data.getSlbh());
                       entityMapper.updateByPrimaryKeyNull(bdcFphDO);

                       //生成 使用明细
                       bdcFphSymxDO.setFphid(bdcFphDO.getFphid());

                       UserDto userDto = userManagerUtils.getUserByName("zzjfj");
                       if(null != userDto){
                           bdcFphSymxDO.setSyr(userDto.getAlias());
                           bdcFphSymxDO.setSyrid(userDto.getId());
                       }

                       bdcFphSymxDO.setSyqk(BdcZssyqkEnum.YSY.getCode());
                       bdcFphSymxDO.setSysj(new Date());
                       LOGGER.info("开始更新发票号明细相关信息：{}",bdcFphSymxDO.toString());
                       entityMapper.insert(bdcFphSymxDO);
                   }
               }
                BdcSlSfxxDO sfxx = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
                if (sfxx !=null && StringUtils.isNotBlank(sfxx.getXmid())){
                    BdcSlSfxxCzrzDO bdcSlSfxxCzrzDO = new BdcSlSfxxCzrzDO();
                    bdcSlSfxxCzrzDO.setSfxxid(sfxxid);
                    bdcSlSfxxCzrzDO.setXmid(sfxx.getXmid());
                    bdcSlSfxxCzrzDO.setCzr(zzjfjCzr);
                    BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(sfxx.getXmid());
                    if(bdcXmDO != null){
                        bdcSlSfxxCzrzDO.setSlbh(bdcXmDO.getSlbh());
                    }else {
                        LOGGER.info("未查询到项目信息：{}",sfxx.getXmid());
                    }
                    bdcSlSfxxCzrzDO.setSfsj(new Date());
                    LOGGER.info("开始插入BDC_SL_SFXX_CZRZ相关信息：{}",bdcSlSfxxCzrzDO.toString());
                    bdcSlSfxxFeignService.insertBdcSlSfxxCzrz(bdcSlSfxxCzrzDO);
                }else {
                    LOGGER.info("未查询到收费信息或项目id为空：{}",sfxxid);
                }
            }
        }
    }


    /**
     * 此接口不验证发票号是否在登记中已有，南通特殊需求37188
     * @Date 2021/3/22 增加注释
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public void bddcSfxxWithOutFph(ZzjfBcSfxxRequestDTO requestDTO) {
        String sfxmid = null;
        ZzjfBcSfxxRequestData data = requestDTO.getData();
        if(data != null){
            LOGGER.info("保存收费信息实体：{}", JSONObject.toJSONString(data));
            sfxmid = data.getSfxmid();
        }
        if(StringUtils.isBlank(sfxmid)){
            throw new MissingArgumentException("sfxmid");
        }
        BdcSlSfxmDO bdcSlSfxmDO = bdcSlSfxmFeignService.queryBdcSlSfxmBySfxmid(sfxmid);
        if(bdcSlSfxmDO != null){
            String sfxxid = bdcSlSfxmDO.getSfxxid();
            BdcSlSfxxDO bdcSlSfxxDO = exchangeBeanRequestService.request("nt_zzjf_bcsfxx_sfxx",data,BdcSlSfxxDO.class);
            if(bdcSlSfxxDO != null){
                bdcSlSfxxDO.setSfxxid(sfxxid);
                bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);

                // 南通自助缴费后需要更新fph和fphsymx
                if(StringUtils.isNotBlank(data.getFphm())){

                    String fphsymxid = UUIDGenerator.generate16();

                    /**
                     * 更新发票号相关信息
                     */
                    BdcFphDO bdcFphDO = new BdcFphDO();
                    bdcFphDO.setFph(data.getFphm());
                    Example example = new Example(BdcFphDO.class);
                    example.createCriteria().andEqualTo("fph", bdcFphDO.getFph());
                    List<BdcFphDO> bdcFphDOList = entityMapper.selectByExample(example);
                    LOGGER.info("===>南通自助缴费机保存收费信息,发票号:{}",data.getFphm());
                    if(CollectionUtils.isNotEmpty(bdcFphDOList)){
                        LOGGER.info("===>南通自助缴费机保存收费信息,发票数量:{}",bdcFphDOList.size());
                        bdcFphDO = bdcFphDOList.get(0);
                    }
                    bdcFphDO.setFph(data.getFphm());
                    bdcFphDO.setSyqk(CommonConstantUtils.SYQK_YSY);
                    bdcFphDO.setSfxxid(sfxxid);
                    bdcFphDO.setFphsymxid(fphsymxid);
                    bdcFphDO.setSlbh(data.getSlbh());

                    Example param = new Example(BdcFphDO.class,false);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("sfxxid",sfxxid);
                    entityMapper.updateByExampleNotNull(bdcFphDO,param);

                    /**
                     *  生成发票使用明细
                     */
                    BdcFphSymxDO bdcFphSymxDO = new BdcFphSymxDO();
                    bdcFphSymxDO.setFphsymxid(fphsymxid);
                    bdcFphSymxDO.setFphid(bdcFphDO.getFphid());
                    UserDto userDto = userManagerUtils.getUserByName("zzjfj");
                    if(null != userDto){
                        bdcFphSymxDO.setSyr(userDto.getAlias());
                        bdcFphSymxDO.setSyrid(userDto.getId());
                    }
                    bdcFphSymxDO.setSyqk(BdcZssyqkEnum.YSY.getCode());
                    bdcFphSymxDO.setSysj(new Date());
                    LOGGER.info("开始更新发票号明细相关信息：{}",bdcFphSymxDO.toString());
                    entityMapper.insert(bdcFphSymxDO);

                }
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.exchange.core.dto.nantong.zzjfcxsfxx.response.ZzjfCxSfxxResponseDTO
     * @description  查询收费信息
     */
    public ZzjfCxSfxxResponseDTO cxSfxx2(ZzjfCxSfxxRequestDTO requestDTO){
        PageRequest pageable = null;
        if (requestDTO.getHead().getSize() == null || requestDTO.getHead().getSize() == 0) {
            pageable = new PageRequest(requestDTO.getHead().getPage(), 10000);
        } else {
            pageable = new PageRequest(requestDTO.getHead().getPage(), requestDTO.getHead().getSize());
        }
        ZzjfCxSfxxResponseDTO responseDTO = new ZzjfCxSfxxResponseDTO();
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("slbh",requestDTO.getData().getSlbh());
        Page<Map> pageResult = repository.selectPaging("queryZzjfCxSfxxByPageOrder",paramMap,pageable);
        // 如果内容不为空 循环处理
        if(CollectionUtils.isNotEmpty(pageResult.getContent())){
            List<Map> mapList = pageResult.getContent();
            List<ZzjfCxSfxxResponseData> dataList = new ArrayList<>();
            for (Map tempMap : mapList){
                // 处理收费信息
                ZzjfCxSfxxResponseData data = new ZzjfCxSfxxResponseData();
                BeanUtils.copyProperties(tempMap,data);
                String ywh = MapUtils.getString(tempMap,"YWH");
                if(StringUtils.isNotBlank(ywh)){
                    HashMap<String, String> map = new HashMap();
                    map.put("ywh", ywh);
                    // 与汇交上报使用同一个SQL 查询
                    List<DjfDjSfDO> djfDjSfList = djfDjSfMapper.queryDjfDjSfList(map);
                    if(CollectionUtils.isNotEmpty(djfDjSfList)){
                        List<ZzjfCxSfxxResponseSfxmXX> sfxmxxList = new ArrayList<>();
                        for(DjfDjSfDO djfDjSfDO:djfDjSfList){
                            ZzjfCxSfxxResponseSfxmXX sfxmxx = new ZzjfCxSfxxResponseSfxmXX();
                            BeanUtils.copyProperties(djfDjSfDO,sfxmxx);
                            // 2.0 版本接口  SFLX字段读取的是 金额单位
                            sfxmxx.setSflx(djfDjSfDO.getSfdw());
                            sfxmxxList.add(sfxmxx);
                        }
                        data.setSfxmxx(sfxmxxList);
                    }
                }
                dataList.add(data);
            }
            responseDTO.setData(dataList);
        }


        return responseDTO;
    }


}
