package cn.gtmap.realestate.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author <a href="mailto:jiangganzhi@gtmap.cn">jiangganzhi</a>
 * @description 类处理工具类
 */
public class ClassHandleComonUtil {
    //获取属性名数组
    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    //根据属性名获取属性值
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取属性的数据类型
    public static Object getFiledType(String fieldName, Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Objects.equals(fieldName, field.getName())) {
                return field.getType();
            }
        }
        return null;
    }

    public static Object setDefaultValueToNullField(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                //存在xml节点注解才赋值默认值
                Boolean existXmlAttributeAnnotation = existXmlAttributeAnnotation(o, field, XmlAttribute.class);
                if (!Modifier.isFinal(field.getModifiers())
                        && !StringUtils.equals(field.getName(), "ysdm") && existXmlAttributeAnnotation) {
                    try {
                        Object object = ClassHandleComonUtil.getFieldValueByName(field.getName(), o);
                        Object typeObject = ClassHandleComonUtil.getFiledType(field.getName(), o);
                        if (object == null) {
                            if (typeObject == Double.class) {
                                field.setAccessible(true);
                                field.set(o, 0.00);
                            } else if (typeObject == String.class) {
                                field.setAccessible(true);
                                field.set(o, "/");
                            } else if (typeObject == Integer.class) {
                                field.setAccessible(true);
                                field.set(o, -1);
                            } else if (typeObject == Date.class) {
                                field.setAccessible(true);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = sdf.parse("1900-01-01");
                                field.set(o, date);
                            }
                        } else if (typeObject == String.class && object.equals("")) {
                            field.setAccessible(true);
                            field.set(o, "/");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return o;
    }

    public static Object headModelSetDefaultValueToNullField(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                //存在xml节点注解才赋值默认值
                Boolean existXmlAttributeAnnotation = existXmlElementAnnotation(o, field, XmlElement.class);
                if (!Modifier.isFinal(field.getModifiers())
                        && existXmlAttributeAnnotation) {
                    try {
                        Object object = ClassHandleComonUtil.getFieldValueByName(field.getName(), o);
                        Object typeObject = ClassHandleComonUtil.getFiledType(field.getName(), o);
                        if (object == null) {
                            if (typeObject == Double.class) {
                                field.setAccessible(true);
                                field.set(o, 0.00);
                            } else if (typeObject == String.class) {
                                field.setAccessible(true);
                                field.set(o, "/");
                            } else if (typeObject == Integer.class) {
                                field.setAccessible(true);
                                field.set(o, -1);
                            } else if (typeObject == Date.class) {
                                field.setAccessible(true);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = sdf.parse("1900-01-01");
                                field.set(o, date);
                            }
                        } else if (typeObject == String.class && object.equals("")) {
                            field.setAccessible(true);
                            field.set(o, "/");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return o;
    }

    /**
     * 给空值元素赋默认值
     *
     * @param
     * @return
     * @Date 2022/5/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public static Object headModelSetDefaultValueToNullFieldXmlElemen(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                //存在xml节点注解才赋值默认值
                Boolean existXmlAttributeAnnotation = existXmlElementAnnotation(o, field, XmlElement.class);
                if (!Modifier.isFinal(field.getModifiers())
                        && existXmlAttributeAnnotation) {
                    try {
                        Object object = ClassHandleComonUtil.getFieldValueByName(field.getName(), o);
                        Object typeObject = ClassHandleComonUtil.getFiledType(field.getName(), o);
                        if (object == null) {
                            if (typeObject == Double.class) {
                                field.setAccessible(true);
                                field.set(o, 0.00);
                            } else if (typeObject == String.class) {
                                field.setAccessible(true);
                                field.set(o, "");
                            } else if (typeObject == Integer.class) {
                                field.setAccessible(true);
                                field.set(o, -1);
                            } else if (typeObject == Date.class) {
                                field.setAccessible(true);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = sdf.parse("1900-01-01");
                                field.set(o, date);
                            }
                        } else if (typeObject == String.class && object.equals("")) {
                            field.setAccessible(true);
                            field.set(o, "/");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return o;
    }

    public static Boolean existXmlAttributeAnnotation(Object o, Field field, Class<?> checkClass) {
        Boolean existAnnotation = false;
        if (field != null && checkClass != null) {
            try {
                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + field.getName().substring(1);
                Method method = o.getClass().getMethod(getter, new Class[]{});
                XmlAttribute xmlAttribute = method.getAnnotation(XmlAttribute.class);
                if (xmlAttribute != null) {
                    existAnnotation = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return existAnnotation;
    }

    public static Boolean existXmlElementAnnotation(Object o, Field field, Class<?> checkClass) {
        Boolean existAnnotation = false;
        if (field != null && checkClass != null) {
            try {
                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + field.getName().substring(1);
                Method method = o.getClass().getMethod(getter, new Class[]{});
                XmlElement xmlElement = method.getAnnotation(XmlElement.class);
                if (xmlElement != null) {
                    existAnnotation = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return existAnnotation;
    }

    //转换两个对象同名字段值
    public static Object oldObjectToNewObject(Object oldObject, Object newObject) {
        if (oldObject != null) {
            try {
                Field[] resultFields = newObject.getClass().getDeclaredFields();
                Field[] fields = oldObject.getClass().getDeclaredFields();
                if (resultFields != null && resultFields.length > 0
                        && fields != null && fields.length > 0) {
                    for (Field resultField : resultFields) {
                        for (Field field : fields) {
                            //非final属性赋值
                            if (!Modifier.isFinal(field.getModifiers()) &&
                                    !Modifier.isFinal(resultField.getModifiers())) {
                                if (StringUtils.equals(field.getName(), resultField.getName())) {
                                    //设置访问权限
                                    resultField.setAccessible(true);
                                    //对该属性赋值

                                    resultField.set(newObject, getFieldValueByName(resultField.getName(), oldObject));

                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newObject;
    }


    public static Object headModelSetDefaultValueToNullFieldXmlAttribute(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                //存在xml节点注解才赋值默认值
                Boolean existXmlAttributeAnnotation = existXmlAttributeAnnotation(o, field, XmlAttribute.class);
                if (!Modifier.isFinal(field.getModifiers())
                        && existXmlAttributeAnnotation) {
                    try {
                        Object object = ClassHandleComonUtil.getFieldValueByName(field.getName(), o);
                        Object typeObject = ClassHandleComonUtil.getFiledType(field.getName(), o);
                        if (object == null) {
                            if (typeObject == Double.class) {
                                field.setAccessible(true);
                                field.set(o, 0.00);
                            } else if (typeObject == String.class) {
                                field.setAccessible(true);
                                field.set(o, "");
                            } else if (typeObject == Integer.class) {
                                field.setAccessible(true);
                                field.set(o, -1);
                            } else if (typeObject == Date.class) {
                                field.setAccessible(true);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = sdf.parse("1900-01-01");
                                field.set(o, date);
                            }
                        } else if (typeObject == String.class && object.equals("")) {
                            field.setAccessible(true);
                            field.set(o, "/");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return o;
    }

}
