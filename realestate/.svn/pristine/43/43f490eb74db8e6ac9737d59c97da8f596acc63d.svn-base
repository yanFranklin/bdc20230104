package cn.gtmap.realestate.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * JSON与XML互转，工具类
 */
public class JsonXmlUtil {

    private static final char EOFC = (char) -1;

    /**
     * JSON 转 XML
     *
     * @param jsonString JSON字符串
     */
    public static String getXmlFromJson(String jsonString) throws IOException {
        InputStream in = new ByteArrayInputStream(jsonString.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        translate(in, out);
        return out.toString();
    }

    /**
     * 将xml转换为JSON对象
     *
     * @param xml xml字符串
     */
    public static JSONObject getJsonFromXml(String xml) throws Exception {
        return getJsonFromXml(xml, "");
    }

    /**
     * 将xml转换为JSON对象
     *
     * @param xml             xml字符串
     * @param parseArrayNames （如果xml中的一个节点不是数组，而你又需要将它转化为json数组），请填写该节点名称，多个值时用逗号分隔
     * @return
     * @throws Exception
     */
    public static JSONObject getJsonFromXml(String xml, String parseArrayNames) throws DocumentException {
        JSONObject jsonObject = new JSONObject();
        Document document = DocumentHelper.parseText(xml);
        //获取根节点元素对象
        Element root = document.getRootElement();
        iterateNodes(root, jsonObject, parseArrayNames);
        return jsonObject;
    }

    /**
     * 将 JSON输入流 转化为 XML输出流
     */
    private static void translate(InputStream in, OutputStream out) throws IOException, IllegalArgumentException {
        InputStreamReader re = new InputStreamReader(in);
        char c;
        while ((c = (char) re.read()) != EOFC) {
            switch (c) {
                case '{':
                    translateObject(re, out);
                    break;
                case '[':
                    translateArray(re, out, null);
                    break;
                default:
                    if (Character.isSpaceChar(c)) {
                        break;
                    } else {
                        throw new IllegalArgumentException("invalid character:" + c + " (" + (int) c + ")");
                    }
            }
        }
    }

    private static void translateArray(InputStreamReader re, OutputStream out, String tname) throws IOException, IllegalArgumentException {
        char c;
        while ((c = (char) re.read()) != EOFC) {
            switch (c) {
                case '{':
                    translateObject(re, out);
                    break;
                case '[':
                    translateArray(re, out, tname);
                    break;
                case ',':
                    if (tname != null) {
                        out.write("</".getBytes());
                        out.write(tname.getBytes());
                        out.write("><".getBytes());
                        out.write(tname.getBytes());
                        out.write(">".getBytes());
                    }
                    break;
                case ']':
                    return;
                case '"':
                    String n = readStringToken(re);
                    if (n != null) {
                        out.write(n.getBytes());
                    }
                    break;
                default:
                    if (Character.isSpaceChar(c)) {
                        break;
                    } else {
                        out.write(("" + c).getBytes());
                    }
            }
        }
    }

    private static String readStringToken(InputStreamReader re) throws IOException {
        StringBuilder b = new StringBuilder();
        char c;
        boolean esc = false;
        while ((c = (char) re.read()) != EOFC) {
            switch (c) {
                case '\\':
                    esc = true;
                    break;
                case 'n':
                    if (!esc) {
                        b.append(c);
                    } else {
                        b.append((char) 10);
                        esc = false;
                    }
                    break;
                case 'r':
                    if (!esc) {
                        b.append(c);
                    } else {
                        b.append((char) 13);
                        esc = false;
                    }
                    break;
                case 't':
                    if (!esc) {
                        b.append(c);
                    } else {
                        b.append((char) 9);
                        esc = false;
                    }
                    break;
                case 'u':
                    if (!esc) {
                        b.append(c);
                    } else {
                        StringBuilder sb = new StringBuilder(6);
                        sb.append("0x");
                        sb.append((char) re.read());
                        sb.append((char) re.read());
                        sb.append((char) re.read());
                        sb.append((char) re.read());
                        b.append((char) Integer.decode(sb.toString()).intValue());
                        esc = false;
                    }
                    break;
                case '"':
                    if (!esc) {
                        return b.toString();
                    }
                default:
                    b.append(c);
                    esc = false;
            }
        }
        return b.toString();
    }

    private static boolean translateValue(InputStreamReader re, OutputStream out, String tname) throws IOException, IllegalArgumentException {
        char c;
        while ((c = (char) re.read()) != EOFC) {
            switch (c) {
                case '[':
                    translateArray(re, out, tname);
                    return false;
                case '{':
                    translateObject(re, out);
                    return false;
                case '"':
                    String v = readStringToken(re);
                    outs(out, v);
                    return false;
                case 'n':
                    re.read(); // u
                    re.read(); // l
                    re.read(); // l
                    return false;
                case ' ':
                    break;
                case '}':
                    return true;
                case ',':
                    return false;
                default:
                    outc(out, c);
                    break;
            }
        }
        return false;
    }

    private static void outs(OutputStream out, String s) throws IOException {
        if (s != null) {
            for (int i = 0, l = s.length(); i < l; i++) {
                outc(out, s.charAt(i));
            }
        }
    }

    private static void outc(OutputStream out, char c) throws IOException {
        switch (c) {
            case '<':
                out.write("&lt;".getBytes());
                break;
            case '>':
                out.write("&gt;".getBytes());
                break;
            case '\"':
                out.write("&quot;".getBytes());
                break;
            case '&':
                out.write("&amp;".getBytes());
                break;
            case '\'':
                out.write("&apos;".getBytes());
                break;
            default:
//                if (c > 0x7f) {
//                    if (c < 0x400 || c > 0x4FF) {
//                        out.write(("&#" + ((int) c) + ";").getBytes());
//                    } else {
//                        out.write(("" + c).getBytes());
//                    }
//                } else {
//                    out.write(("" + c).getBytes());
//                }
                out.write(("" + c).getBytes());
        }
    }

    private static void translateObject(InputStreamReader re, OutputStream out) throws IOException, IllegalArgumentException {
        char c;
        String field = null;
        while ((c = (char) re.read()) != EOFC) {
            switch (c) {
                case ':':
                    if (field != null) {
                        out.write("<".getBytes());
                        out.write(field.getBytes());
                        out.write(">".getBytes());
                    }
                    boolean eoo = translateValue(re, out, field);
                    if (field != null) {
                        out.write("</".getBytes());
                        out.write(field.getBytes());
                        out.write(">".getBytes());
                    }
                    if (eoo) {
                        return;
                    }
                    break;
                case '"':
                    field = readStringToken(re);
                    break;
                case '}':
                    return;
                default:
            }
        }
    }

    /**
     * 遍历元素
     *
     * @param node 元素
     * @param json 将元素遍历完成之后放进JSON对象
     */
    private static void iterateNodes(Element node, JSONObject json, String parseArrayNames) {
        //获取当前元素的名称
        String nodeName = node.getName();
        List<String> parseArrayNamesList = Arrays.asList(parseArrayNames.split(","));
        //判断已遍历的JSON中是否已经有了该元素的名称
        if (json.containsKey(nodeName) || parseArrayNamesList.contains(nodeName)) {
            //该元素在同级下有多个
            Object Object = json.get(nodeName);
            JSONArray array = new JSONArray();
            if (Object instanceof JSONArray) {
                array = (JSONArray) Object;
            } else if (Object != null) {
                array = new JSONArray();
                array.add(Object);
            }
            //获取该元素下所有子元素
            List<Element> listElement = node.elements();
            if (listElement.isEmpty()) {
                //该元素无子元素，获取元素的值
                String nodeValue = node.getTextTrim();
                array.add(nodeValue);
                json.put(nodeName, array);
                return;
            }
            //有子元素
            JSONObject newJson = new JSONObject();
            //遍历所有子元素
            for (Element e : listElement) {
                //递归
                iterateNodes(e, newJson, parseArrayNames);
            }
            array.add(newJson);
            json.put(nodeName, array);
            return;
        }
        //该元素同级下第一次遍历
        //获取该元素下所有子元素
        List<Element> listElement = node.elements();
        if (listElement.isEmpty()) {
            //该元素无子元素，获取元素的值
            String nodeValue = node.getTextTrim();
            json.put(nodeName, nodeValue);
            return;
        }
        //有子节点，新建一个JSONObject来存储该节点下子节点的值
        JSONObject object = new JSONObject();
        //遍历所有一级子节点
        for (Element e : listElement) {
            //递归
            iterateNodes(e, object, parseArrayNames);
        }
        json.put(nodeName, object);
        return;
    }
}

