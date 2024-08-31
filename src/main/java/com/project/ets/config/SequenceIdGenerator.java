package com.project.ets.config;

import java.time.Year;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentityGenerator;

public class SequenceIdGenerator implements IdentifierGenerator{

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		String uuid = UUID.randomUUID().toString();
		String id="EDU"+Year.now().toString()+uuid;
		return id;
	}

}
