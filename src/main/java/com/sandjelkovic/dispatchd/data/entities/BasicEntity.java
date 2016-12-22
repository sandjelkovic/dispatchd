package com.sandjelkovic.dispatchd.data.entities;

public abstract class BasicEntity {
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BasicEntity)) {
			return false;
		}

		BasicEntity oth = (BasicEntity) other;
		if (getInternalId() == null || oth.getInternalId() == null) {
			return false;
		}
		return getInternalId().equals(oth.getInternalId());
	}

	abstract protected Object getInternalId();
}
