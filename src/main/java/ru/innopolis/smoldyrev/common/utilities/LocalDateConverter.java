package ru.innopolis.smoldyrev.common.utilities;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDate;

/**
 * Created by smoldyrev on 19.03.17.
 */
public class LocalDateConverter extends BidirectionalConverter<LocalDate, LocalDate> {

    @Override
    public LocalDate convertTo(LocalDate source, Type<LocalDate> destinationType, MappingContext mappingContext) {
        return (LocalDate.from(source));
    }

    @Override
    public LocalDate convertFrom(LocalDate source, Type<LocalDate> destinationType, MappingContext mappingContext) {
        return (LocalDate.from(source));
    }
}
