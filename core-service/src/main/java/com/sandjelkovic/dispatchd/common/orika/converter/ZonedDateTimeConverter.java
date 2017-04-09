package com.sandjelkovic.dispatchd.common.orika.converter;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.ZonedDateTime;

public class ZonedDateTimeConverter extends BidirectionalConverter<ZonedDateTime, ZonedDateTime> {

	@Override
	public ZonedDateTime convertTo(ZonedDateTime source, Type<ZonedDateTime> destinationType) {
		return ZonedDateTime.from(source);
	}

	@Override
	public ZonedDateTime convertFrom(ZonedDateTime source, Type<ZonedDateTime> destinationType) {
		return ZonedDateTime.from(source);
	}
}
