//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.gtmap.realestate.common.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public abstract class ParamUtil {
    public ParamUtil() {
    }

    public static void isBlank(String string, String msg) {
        if (StringUtil.isNotBlank(string)) {
            throw new RuntimeException(msg);
        }
    }

    public static void notBlank(String string, String msg) {
        if (StringUtil.isBlank(string)) {
            throw new RuntimeException(msg);
        }
    }

    public static void notEmpty(Collection collection, String msg) {
        if (null == collection || collection.isEmpty()) {
            throw new RuntimeException(msg);
        }
    }

    public static void nonBlankElements(Collection<String> collection, String elementMsg) {
        Iterator var2 = collection.iterator();

        while(var2.hasNext()) {
            String str = (String)var2.next();
            notBlank(str, elementMsg);
        }

    }

    public static void nonNull(Object object, String msg) {
        if (null == object) {
            throw new RuntimeException(msg);
        }
    }

    public static void isNull(Object object, String msg) {
        if (null != object) {
            throw new RuntimeException(msg);
        }
    }

    public static void expectTrue(boolean boolExpression, String falseMsg) {
        if (!boolExpression) {
            throw new RuntimeException(falseMsg);
        }
    }

    public static void expectFalse(boolean boolExpression, String trueMsg) {
        if (boolExpression) {
            throw new RuntimeException(trueMsg);
        }
    }

    public static void expectAnyFalse(String msg, Boolean... booleans) throws RuntimeException {
        if (Arrays.stream(booleans).allMatch((t) -> {
            return t;
        })) {
            throw new RuntimeException(msg);
        }
    }

    public static void expectInRange(Collection collection, int minElements, int maxElements, String msg) {
        expectInRange(collection.size(), minElements, maxElements, msg);
    }

    public static void expectInRange(String string, int minLength, int maxLength, String msg) {
        if (StringUtil.isBlank(string) || string.length() < minLength || string.length() > maxLength) {
            throw new RuntimeException(msg);
        }
    }

    public static void expectInRange(int value, int minValue, int maxValue, String msg) {
        if (value < minValue || value > maxValue) {
            throw new RuntimeException(msg);
        }
    }

    public static void expectDateStrWithPattern(String sDate, String pattern, String msg) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            df.parse(sDate);
        } catch (Exception var4) {
            throw new RuntimeException(msg);
        }
    }
}
