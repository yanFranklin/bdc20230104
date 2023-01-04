package cn.gtmap.realestate.common.core.service.interfaces;


import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;

public abstract class CustomConverter<T, R> implements Function<T, R> {

    public abstract R doConvert(T source);

    @Override
    public R apply(T source) {
        return doConvert(source);
    }
}