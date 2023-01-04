package cn.gtmap.realestate.common.core.service.interfaces;


import org.springframework.core.convert.converter.Converter;

public abstract class CustomConverter<T, R> implements Converter<T, R> {

    public abstract R doConvert(T source);

    @Override
    public R convert(T source) {
        return doConvert(source);
    }
}
