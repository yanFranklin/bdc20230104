package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDtcxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.common.DtcxCheckEnum;
import cn.gtmap.realestate.inquiry.common.DtcxConstants;
import cn.gtmap.realestate.inquiry.core.mapper.BdcDtcxMapper;
import cn.gtmap.realestate.inquiry.service.DtcxConfigService;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/3/19
 * @description
 */
@Service
public class DtcxConfigServiceImpl implements DtcxConfigService{

    @Autowired
    BdcDtcxMapper dtcxMapper;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    UserManagerUtils userManager;
    @Autowired
    Repo repo;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAllDtcxCxxx(BdcDtcxDTO bdcDtcxDTO,boolean fuzhiCx){
        // 保存查询
        DtcxDO dtcxDO = new DtcxDO();
        BeanUtils.copyProperties(bdcDtcxDTO,dtcxDO);
        if (StringUtils.isBlank(dtcxDO.getCjr())){
            dtcxDO.setCjr(userManager.getCurrentUserName());
            dtcxDO.setCjsj(new Date());
        }else{
            dtcxDO.setBgr(userManager.getCurrentUserName());
            dtcxDO.setBgsj(new Date());
        }
        // 加个复制判定 如果为复制把这些都重新定义一遍
        if (fuzhiCx){
            dtcxDO.setCjr(userManager.getCurrentUserName());
            dtcxDO.setCjsj(new Date());
            dtcxDO.setBgr("");
            dtcxDO.setBgsj(null);
        }
        entityMapper.saveOrUpdate(dtcxDO,dtcxDO.getCxid());

        // 保存查询条件
        String cxid = dtcxDO.getCxid();
        String cxdh = dtcxDO.getCxdh();
        if (CollectionUtils.isNotEmpty(bdcDtcxDTO.getCxtjDOList())){
            // 先删除所有条件再重新插入
            Example example = new Example(DtcxCxtjDO.class);
            example.createCriteria().andEqualTo(DtcxConstants.CXID,cxid);
            entityMapper.deleteByExample(example);
            for (DtcxCxtjDO dtcxCxtjDO : bdcDtcxDTO.getCxtjDOList()){
                dtcxCxtjDO.setCxid(cxid);
                dtcxCxtjDO.setCxdh(cxdh);
                dtcxCxtjDO.setTjusage(DtcxConstants.CXTJ);
                dtcxCxtjDO.setTjid(UUIDGenerator.generate());
            }
            // 之前需要全部删除所有记录，此处再统一插入
            entityMapper.insertBatchSelective(bdcDtcxDTO.getCxtjDOList());
        }

        // 保存查询结果
        if (CollectionUtils.isNotEmpty(bdcDtcxDTO.getCxjgDOList())){
            // 先删除所有结果再重新插入
            Example example = new Example(DtcxCxjgDO.class);
            example.createCriteria().andEqualTo(DtcxConstants.CXID,cxid);
            entityMapper.deleteByExample(example);

            for (DtcxCxjgDO dtcxCxjgDO : bdcDtcxDTO.getCxjgDOList()){
                dtcxCxjgDO.setCxid(cxid);
                dtcxCxjgDO.setCxdh(cxdh);
                if (StringUtils.isNotBlank(dtcxCxjgDO.getJgzdid())){
                    dtcxCxjgDO.setJgzdid(dtcxCxjgDO.getJgzdid().toUpperCase());
                }
                if (StringUtils.isBlank(dtcxCxjgDO.getJgid())){
                    dtcxCxjgDO.setJgid(UUIDGenerator.generate());
                }
            }
            // 之前需要全部删除所有记录，此处再统一插入
            entityMapper.insertBatchSelective(bdcDtcxDTO.getCxjgDOList());
        }
    }

    @Override
    public Map getKeysAndParams(String cxsql){
        // 验证SQL
        HashMap result = new HashMap();
        if (StringUtils.isBlank(cxsql)){
            result.put(DtcxConstants.RESULT,"SQL为空");
            return result;
        }
        List<String> keyList = new ArrayList();
        Pattern pattern = Pattern.compile(DtcxConstants.PARAMETER);
        Matcher matcher = pattern.matcher(cxsql);
        String sql = cxsql;
        sql = sql.replace("　"," ");
        List<String> paramList = new ArrayList();
        // 获取参数
        while (matcher.find()){
            String group = matcher.group(0);
            paramList.add(matcher.group(1));
            sql = sql.replace(group,"");
        }
        HashSet paramHashSet = new HashSet(paramList);
        paramList.clear();
        paramList.addAll(paramHashSet);
        // 获取结果列 并 判断sql能否执行
        try{
            // 解析SQL
            CCJSqlParserManager parserManager = new CCJSqlParserManager();
            Statement parse = parserManager.parse(new StringReader(sql));
            Select noOrderSelect = (Select) parse;
            SelectBody selectBody = noOrderSelect.getSelectBody();
            PlainSelect setOperationList;
            if (selectBody instanceof PlainSelect){
                setOperationList = (PlainSelect) selectBody;
            }else{
                setOperationList = (PlainSelect) (((SetOperationList) selectBody).getSelects()).get(0);
            }

            for (SelectItem item : setOperationList.getSelectItems()){
                if (((SelectExpressionItem) item).getAlias() != null){
                    keyList.add(((SelectExpressionItem) item).getAlias().getName().toUpperCase());
                }else{
                    keyList.add(((Column) ((SelectExpressionItem) item).getExpression()).getColumnName().toUpperCase());
                }
            }
        }catch (Exception e){
            result.put(DtcxConstants.RESULT,DtcxCheckEnum.SQL_ERROR);
            return result;
        }
        // 若验证通过 返回结果
        result.put(DtcxConstants.RESULT,DtcxConstants.SUCCESS);
        result.put(DtcxConstants.KEYLIST,keyList);
        result.put(DtcxConstants.PARAMLIST,paramList);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDtcxCx(String cxid){
        // 删除查询配置信息
        entityMapper.deleteByPrimaryKey(DtcxDO.class,cxid);

        // 删除查询条件配置
        Example example = new Example(DtcxCxtjDO.class);
        example.createCriteria().andEqualTo(DtcxConstants.CXID,cxid);
        entityMapper.deleteByExample(example);

        //删除查询结果配置
        example = new Example(DtcxCxjgDO.class);
        example.createCriteria().andEqualTo(DtcxConstants.CXID,cxid);
        entityMapper.deleteByExample(example);
        example.clear();
    }

    @Override
    public DtcxConfigCheckDTO checkCxtj(String sql,List<DtcxCxtjDO> cxtjList){
        if (StringUtils.isBlank(sql) || cxtjList == null){
            return null;
        }
        HashMap result = new HashMap();
        // 通过查询条件列表生成查询条件id列表
        // 判断条件字段id是否存在重复
        List cxtjidList = new ArrayList();
        List repeatTjidList = new ArrayList();
        Boolean repeatFlg = false;
        for (DtcxCxtjDO cxtj : cxtjList){
            if (!cxtjidList.contains(cxtj.getTjzdid())){
                cxtjidList.add(cxtj.getTjzdid());
            }else{
                repeatFlg = true;
                repeatTjidList.add(cxtj.getTjzdid());
            }
        }
        // 若存在重复 添加重复字段ID
        if (repeatFlg){
            return new DtcxConfigCheckDTO(0,DtcxCheckEnum.CXTJ_REPEAT.getMc(),repeatTjidList);
        }

        String cxSql = sql;
        Map keysAndParams = getKeysAndParams(cxSql);
        List<String> paramList;
        if (keysAndParams.containsKey(DtcxConstants.PARAMLIST)){
            paramList = (List) keysAndParams.get(DtcxConstants.PARAMLIST);
        }else{
            return new DtcxConfigCheckDTO(0,DtcxCheckEnum.SQL_ERROR.getMc(),null);
        }

        // 确认是否有存在于SQL中的未被设定的查询条件
        List<String> paramTempList = new ArrayList(paramList);
        paramTempList.removeAll(cxtjidList);
        if (!paramTempList.isEmpty()){
            StringBuilder resultStringBuilder = new StringBuilder();
            resultStringBuilder.append(DtcxCheckEnum.CXTJ_NOT_INPUT_ERROR.getMc());
            for (String param : paramTempList){
                resultStringBuilder.append(param + " ");
            }
            result.put(DtcxConstants.RESULT,resultStringBuilder.toString());
            return new DtcxConfigCheckDTO(0,resultStringBuilder.toString(),null);
        }

        // 确认是否有存在于查询条件中在SQL中不存在的查询条件
        cxtjidList.removeAll(paramList);
        if (cxtjidList.isEmpty()){
            result.put(DtcxConstants.RESULT,DtcxConstants.SUCCESS);
        }else{
            List errorTjidList = new ArrayList();
            for (DtcxCxtjDO cxtj : cxtjList){
                if (cxtjidList.contains(cxtj.getTjzdid())){
                    errorTjidList.add(cxtj.getTjzdid());
                }
            }
            result.put(DtcxConstants.ERRORTJIDLIST,errorTjidList);
            result.put(DtcxConstants.RESULT,DtcxCheckEnum.CXTJ_NOT_EXISTS_ERROR.getMc());
            return new DtcxConfigCheckDTO(0,DtcxCheckEnum.CXTJ_NOT_EXISTS_ERROR.getMc(),null);
        }

        // 用字段对应方式替换后的sql执行
        for (DtcxCxtjDO cxtj : cxtjList){
            cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}",cxtj.getZddyfs());
            if (StringUtils.equals(DtcxConstants.TEXT,cxtj.getTjtype())
                    || StringUtils.equals(DtcxConstants.DROPDOWN,cxtj.getTjtype())
                    || StringUtils.equals(DtcxConstants.HIDDEN,cxtj.getTjtype())){
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","''");
            }else if (StringUtils.equals(DtcxConstants.DATE,cxtj.getTjtype())){
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","to_char(SYSDATE,'yyyy-MM-dd')");
            }else if (StringUtils.equals(DtcxConstants.TIME,cxtj.getTjtype())){
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","to_char(SYSDATE,'yyyy-MM-dd HH24:mi:ss ')");
            }else if (StringUtils.equals(DtcxConstants.NUMBER,cxtj.getTjtype())){
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","0");
            }else if (StringUtils.equals(DtcxConstants.ID,cxtj.getTjtype())){
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","('')");
            }else if (StringUtils.equals(DtcxConstants.PLCX,cxtj.getTjtype())){
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","('')");
            }else if (StringUtils.equals(DtcxConstants.BM,cxtj.getTjtype())){
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","('')");
            }else if (StringUtils.equals(DtcxConstants.BMRY,cxtj.getTjtype())){
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","('')");
            }else if (StringUtils.equals(DtcxConstants.QXDMGL,cxtj.getTjtype())){
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","('')");
            }else if (StringUtils.equals(DtcxConstants.DROPDOWNS,cxtj.getTjtype())){
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","('')");
            }
        }
        try{
            cxSql = cxSql.replace(DtcxConstants.UPPER_WHERE," WHERE rownum = 1 and ");
            cxSql = cxSql.replace(DtcxConstants.LOWER_WHERE," where rownum = 1 and ");
            cxSql = cxSql.replace("#{=}","=");
            dtcxMapper.testSql(cxSql);
        }catch (Exception e){
            return new DtcxConfigCheckDTO(0,"字段对应方式存在问题！\n问题原因：" + e.getCause(),null);
        }

        return new DtcxConfigCheckDTO(1,DtcxConstants.SUCCESS,new ArrayList<>());
    }

    @Override
    public DtcxConfigCheckDTO checkCxjg(String sql,List<DtcxCxjgDO> cxjgList){
        if (StringUtils.isBlank(sql) || cxjgList == null){
            return null;
        }
        DtcxConfigCheckDTO checkDTO = new DtcxConfigCheckDTO();
        // 通过查询结果列表生成查询结果id列表
        List<String> cxjgidList = new ArrayList();
        List repeatJgidList = new ArrayList();
        Boolean repeatFlg = false;
        for (DtcxCxjgDO cxjg : cxjgList){
            if (StringUtils.equals(cxjg.getJgtype(),DtcxConstants.BUTTON) || StringUtils.equals(cxjg.getJgtype(),DtcxConstants.TOOL)){
                // 不作处理
            }else if (!cxjgidList.contains(cxjg.getJgzdid())){
                cxjgidList.add(cxjg.getJgzdid().toUpperCase());
            }else{
                repeatFlg = true;
                repeatJgidList.add(cxjg.getJgid());
            }
        }
        // 若存在重复 添加重复字段ID
        if (repeatFlg){
            checkDTO.setSuccess(0);
            checkDTO.setResult(DtcxCheckEnum.CXJG_REPEAT.getMc());
            checkDTO.setErrorId(repeatJgidList);
            return checkDTO;
        }

        String cxSql = sql;

        Map keysAndParams = getKeysAndParams(cxSql);
        List keyList;
        if (keysAndParams.containsKey(DtcxConstants.KEYLIST)){
            keyList = (List) keysAndParams.get(DtcxConstants.KEYLIST);
        }else{
            checkDTO.setSuccess(0);
            checkDTO.setResult(DtcxCheckEnum.SQL_ERROR.getMc());
            return checkDTO;
        }
        cxjgidList.removeAll(keyList);
        if (cxjgidList.isEmpty()){
            checkDTO.setResult(DtcxConstants.SUCCESS);
            checkDTO.setSuccess(0);
            checkDTO.setErrorId(new ArrayList<>());
        }else{
            StringBuilder resultStringBuilder = new StringBuilder();
            resultStringBuilder.append(DtcxCheckEnum.CXJG_NOT_EXISTS_ERROR.getMc());
            checkDTO.setSuccess(0);
            checkDTO.setResult(resultStringBuilder.toString());
            checkDTO.setErrorId(cxjgidList);
        }

        return checkDTO;
    }

    @Override
    public List<DtcxCxjgDO> getCxjgList(String cxid){
        if (StringUtils.isNotEmpty(cxid)){
            Example example = new Example(DtcxCxjgDO.class);
            example.createCriteria().andEqualTo("cxid",cxid);
            example.setOrderByClause("priority asc");

            return entityMapper.selectByExample(example);
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public Page<BdcDtcxDTO> listDtcxPage(BdcDtcxQO qo,Pageable pageable){
        return repo.selectPaging("listDtcxByPage",qo,pageable);
    }

    @Override
    public BdcDtcxDTO getConfigsByCxdh(String cxdh){

        BdcDtcxDTO bdcDtcxDTO;
        bdcDtcxDTO = dtcxMapper.getDtcxByCxdh(cxdh);
        if (bdcDtcxDTO != null){
            bdcDtcxDTO.setCxtjDOList(dtcxMapper.getCxtjConfig(cxdh));
            bdcDtcxDTO.setCxjgDOList(dtcxMapper.getCxjgConfig(cxdh));
        }
        return bdcDtcxDTO;
    }
}
