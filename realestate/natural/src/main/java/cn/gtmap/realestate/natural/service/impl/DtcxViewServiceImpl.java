package cn.gtmap.realestate.natural.service.impl;

import cn.gtmap.realestate.common.core.domain.natural.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.domain.natural.DtcxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.natural.common.DtcxConstants;
import cn.gtmap.realestate.natural.core.mapper.ZrzyDtcxMapper;
import cn.gtmap.realestate.natural.service.DtcxViewService;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/8/30
 * @description
 */
@Service
public class DtcxViewServiceImpl implements DtcxViewService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DtcxViewServiceImpl.class);

    private static final Pattern pattern = Pattern.compile("\\s+order\\s+by\\s+.*",Pattern.CASE_INSENSITIVE);
    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private ZrzyDtcxMapper zrzyDtcxMapper;

    @Override
    public String getSqlByCxxx(Map mapCxxx){
        if(mapCxxx == null || MapUtils.getString(mapCxxx, DtcxConstants.CXID) == null){
            return null;
        }
        DtcxDO dtcxCx = entityMapper.selectByPrimaryKey(DtcxDO.class,MapUtils.getString(mapCxxx,DtcxConstants.CXID));
        Example example = new Example(DtcxCxtjDO.class);
        example.createCriteria().andEqualTo(DtcxConstants.CXID,MapUtils.getString(mapCxxx,DtcxConstants.CXID));
        List<DtcxCxtjDO> cxtjList = entityMapper.selectByExample(example);
        String cxSql = dtcxCx.getCxsql();
        addParamerMhcx(mapCxxx);
        // 用字段对应方式替换后的sql执行
        for(DtcxCxtjDO cxtj : cxtjList){
            if(mapCxxx.containsKey(cxtj.getTjzdid())
                    && StringUtils.isNotBlank(mapCxxx.get(cxtj.getTjzdid()).toString())){
                String mhType = MapUtils.getString(mapCxxx,cxtj.getTjzdid() + "mh");
                if(StringUtils.equals("1",mhType) || StringUtils.isBlank(mhType)){
                    cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}",cxtj.getZddyfs().replace("#{=}","="));
                }else{
                    cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}",cxtj.getZddyfs().replace("#{=}","like"));
                }
                if(StringUtils.equals(DtcxConstants.ID,cxtj.getTjtype())){
                    String[] zjh = CardNumberTransformation.zjhTransformation(mapCxxx.get(cxtj.getTjzdid()).toString()).split(",");
                    if(zjh != null){
                        String replacezjh = "";
                        for(int i = 0; i < zjh.length; i++){
                            replacezjh += "#{" + cxtj.getTjzdid() + i + "},";
                            mapCxxx.put(cxtj.getTjzdid() + i,zjh[i]);
                        }
                        replacezjh = replacezjh.substring(0,replacezjh.length() - 1);
                        cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","(" + replacezjh + ")");
                    }
                }
                if(StringUtils.equals(DtcxConstants.PLCX,cxtj.getTjtype())){
                    String[] plcx = (mapCxxx.get(cxtj.getTjzdid()).toString()).split(",");
                    if(plcx != null){
                        StringBuilder replaceplcx = new StringBuilder();
                        for(int i = 0; i < plcx.length; i++){
                            replaceplcx.append("#{" + cxtj.getTjzdid() + i + "},");
                            mapCxxx.put(cxtj.getTjzdid() + i,plcx[i]);
                        }
                        replaceplcx = new StringBuilder(replaceplcx.substring(0,replaceplcx.length() - 1));
                        cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","(" + replaceplcx.toString() + ")");
                    }
                }
            }else{
                cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","");
            }
        }

        String sidx = MapUtils.getString(mapCxxx,"sidx");
        String sord = MapUtils.getString(mapCxxx,"sord");
        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)){
            cxSql = setOrderBy(cxSql,sidx,sord,cxtjList);
        }

        return cxSql;
    }

    @Override
    public String getSqlByExcelCxxx(Map mapCxxx){
        if(mapCxxx == null || MapUtils.getString(mapCxxx,DtcxConstants.CXID) == null){
            return null;
        }
        DtcxDO dtcxCx = entityMapper.selectByPrimaryKey(DtcxDO.class,MapUtils.getString(mapCxxx,DtcxConstants.CXID));
        Example example = new Example(DtcxCxtjDO.class);
        example.createCriteria().andEqualTo(DtcxConstants.CXID,MapUtils.getString(mapCxxx,DtcxConstants.CXID));
        List<DtcxCxtjDO> cxtjList = entityMapper.selectByExample(example);
        String cxSql = dtcxCx.getCxsql();
        Map<String,List<String>> queryMap = new HashMap<>();
        addExcelParamerMhcx(mapCxxx,queryMap);
        // 筛选过后的sqllist
        // 组装所有的查询sql
        StringBuilder querySql = new StringBuilder();
        String key = queryMap.entrySet().iterator().next().getKey();
        int size = queryMap.get(key).size();
        for(int i = 0; i < size; i++){
            Map<String,String> tempMap = new HashMap();
            String tempCxSql = cxSql;
            final int tempInt = i;
            queryMap.forEach((x,y) -> {
                tempMap.put(x,y.get(tempInt));
            });
            if(StringUtils.isNotBlank(MapUtils.getString(tempMap,DtcxConstants.BDCDYH))){
                tempMap.put(DtcxConstants.BDCDYH,StringUtils.deleteWhitespace(MapUtils.getString(tempMap,DtcxConstants.BDCDYH)));
            }
            boolean flag = true;
            for(String value : tempMap.values()){
                if(StringUtils.isNotEmpty(value)){
                    flag = false;
                }
            }
            if(flag){
                continue;
            }

            for(DtcxCxtjDO cxtj : cxtjList){
                if(tempMap.containsKey(cxtj.getTjzdid()) && StringUtils.isNotBlank(tempMap.get(cxtj.getTjzdid()))){
                    String mhType = MapUtils.getString(mapCxxx,cxtj.getTjzdid() + "mh");
                    // 如果字段设置身份证类型，需要处理成查询15、18位身份证号同时查
                    if(StringUtils.equals(DtcxConstants.ID,cxtj.getTjtype())){
                        String[] zjh = CardNumberTransformation.zjhTransformation(tempMap.get(cxtj.getTjzdid()).toString()).split(",");
                        String zjhStr = StringToolUtils.appendSqlInStr(Arrays.asList(zjh));
                        String replacedZhjh = cxtj.getZddyfs().replace("#{" + cxtj.getTjzdid() + "}", zjhStr);
                        tempCxSql = tempCxSql.replace("#{" + cxtj.getTjzdid() + "}", replacedZhjh);
                    }

                    // 处理精确度、替换字段值
                    if(StringUtils.equals("1",mhType) || StringUtils.isBlank(mhType)){
                        tempCxSql = tempCxSql.replace("#{" + cxtj.getTjzdid() + "}",cxtj.getZddyfs().replace("#{=}","=").replace("#{" + cxtj.getTjzdid() + "}"," '" + tempMap.get(cxtj.getTjzdid()) + "' "));
                    }else{
                        tempCxSql = tempCxSql.replace("#{" + cxtj.getTjzdid() + "}",cxtj.getZddyfs().replace("#{=}","like").replace("#{" + cxtj.getTjzdid() + "}"," '" + tempMap.get(cxtj.getTjzdid()) + "' "));
                    }
                }else{
                    tempCxSql = tempCxSql.replace("#{" + cxtj.getTjzdid() + "}","");
                }
            }
//            Pattern pattern = Pattern.compile("\\s+order\\s+by\\s+.*",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(tempCxSql);
            if(matcher.find()){
                tempCxSql = tempCxSql.replace(matcher.group(0),"");
            }
            querySql.append(tempCxSql).append(" union ");
        }
        cxSql = querySql.toString();
        cxSql = cxSql.substring(0,cxSql.length() - 6);

        return cxSql;
    }

    @Override
    public Page listResultByPage(org.springframework.data.domain.Pageable pageable,String sql,Map dataMap){

        if(StringUtils.isNotBlank(MapUtils.getString(dataMap,DtcxConstants.BDCDYH))){
            dataMap.put(DtcxConstants.BDCDYH,StringUtils.deleteWhitespace(MapUtils.getString(dataMap,DtcxConstants.BDCDYH)));
        }
        return repo.selectPaging("listResultByPage",dataMap,pageable);
    }

    @Override
    public List<Map> listResultData(String sql){
        return zrzyDtcxMapper.getResultList(sql);
    }

    @Override
    public List<Map> listResultData(Map dataMap){
        return zrzyDtcxMapper.getViewList(dataMap);
    }

    @Override
    public String getSqlBycxid(String cxid){
        return zrzyDtcxMapper.getSqlBycxid(cxid);
    }

    /**
     * 为SQL语句处理排序问题
     *
     * @param cxSql
     * @param sidx
     * @param sord
     * @return
     */
    private static String setOrderBy(String cxSql,String sidx,String sord,List<DtcxCxtjDO> cxtjList){

        // 将条件字段变为字段 避免解析失败
        for(DtcxCxtjDO cxtj : cxtjList){
            cxSql = cxSql.replace("#{" + cxtj.getTjzdid() + "}","'#{" + cxtj.getTjzdid() + "}'");
        }
        CCJSqlParserManager parserManager = new CCJSqlParserManager();

        try{
            Statement parse = parserManager.parse(new StringReader(cxSql));
            Select noOrderSelect = (Select) parse;
            SelectBody selectBody = noOrderSelect.getSelectBody();
            PlainSelect setOperationList = (PlainSelect) selectBody;
            List orderByList = new ArrayList();
            OrderByElement addOrder = new OrderByElement();

            Expression expression = CCJSqlParserUtil.parseExpression(sidx);
            addOrder.setExpression(expression);
            if(StringUtils.equals("desc",sord)){
                addOrder.setAsc(false);
            }else{
                addOrder.setAscDescPresent(true);
            }
            orderByList.add(0,addOrder);
            setOperationList.setOrderByElements(orderByList);
            cxSql = noOrderSelect.toString();
        }catch(JSQLParserException ex){
            LOGGER.error(ex.getMessage(),ex);
        }

        // 恢复SQL格式
        for(DtcxCxtjDO cxtj : cxtjList){
            cxSql = cxSql.replace("'#{" + cxtj.getTjzdid() + "}'","#{" + cxtj.getTjzdid() + "}");
        }
        return cxSql;
    }

    private void addParamerMhcx(Map<String,Object> mapCxxx){
        String dtcxCxid = MapUtils.getString(mapCxxx,"cxid");
        //获得所有查询条件
        Example example = new Example(DtcxCxtjDO.class);
        example.createCriteria().andEqualTo("cxid",dtcxCxid);
        List<DtcxCxtjDO> dtcxCxtjs = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(dtcxCxtjs)){
            for(DtcxCxtjDO dtcxCxtj : dtcxCxtjs){
                if(StringUtils.isNotBlank(MapUtils.getString(mapCxxx,dtcxCxtj.getTjzdid()))){
                    StringBuilder queryString = new StringBuilder(MapUtils.getString(mapCxxx,dtcxCxtj.getTjzdid()));
                    String mhType = MapUtils.getString(mapCxxx,dtcxCxtj.getTjzdid() + "mh");
                    if(StringUtils.isBlank(mhType)){
                        mhType = "";
                    }
                    queryString = new StringBuilder(queryString.toString().replace("%",""));
                    String excelString = MapUtils.getString(mapCxxx,dtcxCxtj.getTjzdid());
                    if(excelString.contains("|")){
                        StringBuilder finalString = new StringBuilder(excelString);
                        switch(mhType){
                            case "1":
                                String[] strings = excelString.split("\\|");
                                String temp = Arrays.asList(strings).stream().map(eo -> "'" + eo + "'").collect(Collectors.joining(","));
                                mapCxxx.put(dtcxCxtj.getTjzdid(),"(" + temp + ")");
                                break;
                            case "2":
                                // 组装数据
                                mapCxxx.put(dtcxCxtj.getTjzdid(),"(" + excelString + ")");
                                break;
                            case "3":
                                mapCxxx.put(dtcxCxtj.getTjzdid(),"(" + excelString + ")$");
                                break;
                            case "4":
                                mapCxxx.put(dtcxCxtj.getTjzdid(),"^(" + excelString + ")");
                                break;
                            default:
                                break;
                        }
                    }else{
                        switch(mhType){
                            case "1":
                                break;
                            case "2":
                                queryString.insert(0,"%");
                                mapCxxx.put(dtcxCxtj.getTjzdid(),queryString.toString());
                                break;
                            case "3":
                                queryString.append("%");
                                mapCxxx.put(dtcxCxtj.getTjzdid(),queryString.toString());
                                break;
                            case "4":
                                queryString.insert(0,"%");
                                queryString.append("%");
                                mapCxxx.put(dtcxCxtj.getTjzdid(),queryString.toString());
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    private void addExcelParamerMhcx(Map<String,Object> mapCxxx,Map<String,List<String>> queryMap){
        String dtcxCxid = MapUtils.getString(mapCxxx,"cxid");
        //获得所有查询条件
        Example example = new Example(DtcxCxtjDO.class);
        example.createCriteria().andEqualTo("cxid",dtcxCxid);
        List<DtcxCxtjDO> dtcxCxtjs = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(dtcxCxtjs)){
            for(DtcxCxtjDO dtcxCxtj : dtcxCxtjs){
                if(StringUtils.isNotBlank(MapUtils.getString(mapCxxx,dtcxCxtj.getTjzdid()))){
                    String mhType = MapUtils.getString(mapCxxx,dtcxCxtj.getTjzdid() + "mh");
                    if(StringUtils.isBlank(mhType)){
                        mhType = "";
                    }
                    List<String> tempList = new ArrayList<>();
                    String excelString = MapUtils.getString(mapCxxx,dtcxCxtj.getTjzdid());
                    String[] strings = excelString.split("\\|");
                    switch(mhType){
                        case "1":
                            for(int i = 0; i < strings.length; i++){
                                if("&&".equals(strings[i])){
                                    tempList.add("");
                                }else{
                                    tempList.add(strings[i]);
                                }
                            }
                            queryMap.put(dtcxCxtj.getTjzdid(),tempList);
                            break;
                        case "2":
                            // 组装数据
                            for(int i = 0; i < strings.length; i++){
                                if("&&".equals(strings[i])){
                                    tempList.add("");
                                }else{
                                    tempList.add("%" + strings[i]);
                                }
                            }
                            queryMap.put(dtcxCxtj.getTjzdid(),tempList);
                            break;
                        case "3":
                            for(int i = 0; i < strings.length; i++){
                                if("&&".equals(strings[i])){
                                    tempList.add("");
                                }else{
                                    tempList.add(strings[i] + "%");
                                }
                            }
                            queryMap.put(dtcxCxtj.getTjzdid(),tempList);
                            break;
                        case "4":
                            for(int i = 0; i < strings.length; i++){
                                if("&&".equals(strings[i])){
                                    tempList.add("");
                                }else{
                                    tempList.add("%" + strings[i] + "%");
                                }
                            }
                            queryMap.put(dtcxCxtj.getTjzdid(),tempList);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
