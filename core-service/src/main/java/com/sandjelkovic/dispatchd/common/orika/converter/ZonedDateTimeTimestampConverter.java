package com.sandjelkovic.dispatchd.common.orika.converter;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeTimestampConverter extends BidirectionalConverter<ZonedDateTime, Timestamp> {

	@Override
	public Timestamp convertTo(ZonedDateTime source, Type<Timestamp> destinationType) {
		return Timestamp.from(source.toInstant());
	}

	@Override
	public ZonedDateTime convertFrom(Timestamp source, Type<ZonedDateTime> destinationType) {
		return ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
	}
}
