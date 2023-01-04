package cn.gtmap.realestate.init.service.initJw.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: bdcdj3.0
 * @description: 结尾服务处理宗地宗海用途
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-09-29 17:37
 **/
@Service
public class BdcZdzhytServiceImpl extends InitBdcJwService {

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcXmService bdcXmService;

    /**
     * 初始化入库数据之后的服务
     *
     * @param initResultDTO 初始化后的数据
     * @param listQO
     * @throws Exception
     */
    @Override
    public void initJw(InitResultDTO initResultDTO, List<InitServiceQO> listQO) throws Exception {
        //只有当前项目是单个流程的时候才会这么处理且是房地产权数据
        if (CollectionUtils.size(initResultDTO.getBdcXmList()) == 1
                && CollectionUtils.isNotEmpty(initResultDTO.getBdcQlList())
                && initResultDTO.getBdcQlList().get(0) instanceof BdcFdcqDO) {

            if (CollectionUtils.isNotEmpty(listQO)) {
                String qlsjly = listQO.get(0).getBdcCshFwkgSl().getQlsjly();

                // 只有从读取权籍数据并且为首次登记的时候，才处理宗地宗海用途数据
                if (qlsjly.contains(Constants.QLSJLY_LPB)) {
                    BdcXmDO bdcXmDO = initResultDTO.getBdcXmList().get(0);
                    if (CommonConstantUtils.DJLX_SCDJ_DM.equals(bdcXmDO.getDjlx())) {
                        bdcXmDO.setZdzhyt2("");
                        bdcXmDO.setZdzhyt3("");
                        entityMapper.updateByPrimaryKey(bdcXmDO);
                        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) initResultDTO.getBdcQlList().get(0);
                        bdcFdcqDO.setTdsyjssj2(null);
                        bdcFdcqDO.setTdsyjssj3(null);
                        entityMapper.updateByPrimaryKey(bdcFdcqDO);
                    }
                }
            }
        }
        //批量流程的首次登记数据处理
        if (CollectionUtils.size(initResultDTO.getBdcXmList()) > 1
                && CollectionUtils.isNotEmpty(initResultDTO.getBdcQlList())
                && initResultDTO.getBdcQlList().get(0) instanceof BdcFdcqDO) {

            if (CollectionUtils.isNotEmpty(listQO)) {
                String qlsjly = listQO.get(0).getBdcCshFwkgSl().getQlsjly();

                // 只有从读取权籍数据并且为首次登记的时候，才处理宗地宗海用途数据
                if (qlsjly.contains(Constants.QLSJLY_LPB)) {
                    /*
                     * 1.首次登记，权利数据来源为权籍的批量流程 根据权籍数据返回值的tdyt判空后去重，去重数量=1 ，把tdyt赋值给zdzhyt，zdzhyt2和zdzhyt3 是空的
                     * 2. 去重后数量大于1 ，按照用途代码排序，并分别赋值给zdzhyt和zdzhyt2 ，zdzhyt3 是空的
                     * 3. 当查询权籍数据根据tdyt判空后数量为0，查询地籍调查表的tdyt，tdyt2 tdyt3 分别赋值给zdzhyt，zdzhyt2，zdzhyt3(dozer对照已经按照此逻辑处理了，不需要额外处理)
                     * 4. 批量受理页面展现直接取bdc_xm 表zdzhyt，zdzhyt2，zdzhyt3 的值，不再单独处理数据
                     * */
                    BdcXmDO bdcXmDO = initResultDTO.getBdcXmList().get(0);
                    if (CommonConstantUtils.DJLX_SCDJ_DM.equals(bdcXmDO.getDjlx())) {
                        LinkedHashSet<String> tdytSet = new LinkedHashSet<>(listQO.size());
                        for (InitServiceQO initServiceQO : listQO) {
                            if (Objects.nonNull(initServiceQO.getBdcdyResponseDTO()) && StringUtils.isNotBlank(initServiceQO.getBdcdyResponseDTO().getTdyt())) {
                                tdytSet.add(initServiceQO.getBdcdyResponseDTO().getTdyt());
                            }
                        }
                        BdcXmDO newXm = new BdcXmDO();
                        newXm.setZdzhyt3("");
                        HashMap<String, Object> whereMap = new HashMap<>(1);
                        whereMap.put("gzlslid", bdcXmDO.getGzlslid());
                        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
                        bdcDjxxUpdateQO.setWhereMap(whereMap);
                        if (CollectionUtils.size(tdytSet) == 1) {
                            newXm.setZdzhyt(StringUtils.join(tdytSet, CommonConstantUtils.ZF_YW_DH));
                            newXm.setZdzhyt2("");
                            bdcDjxxUpdateQO.setJsonStr(JSON.toJSONString(newXm));
                            bdcXmService.updateBatchBdcXm(bdcDjxxUpdateQO);
                        } else if (CollectionUtils.size(tdytSet) > 1) {
                            List<String> tdytList = new ArrayList<>(tdytSet.size());
                            tdytList.addAll(tdytSet);
                            Collections.sort(tdytList);
                            newXm.setZdzhyt(tdytList.get(0));
                            newXm.setZdzhyt2(tdytList.get(1));
                            bdcDjxxUpdateQO.setJsonStr(JSON.toJSONString(newXm));
                            bdcXmService.updateBatchBdcXm(bdcDjxxUpdateQO);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> getVersion() {
        List<String> versionList = new ArrayList<>();
        versionList.add(CommonConstantUtils.SYSTEM_VERSION_CZ);
        return versionList;
    }
}
