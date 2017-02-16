package com.sandjelkovic.dispatchd.domain.data.entity;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DispatchdApplication.class)
@WebAppConfiguration
public class BasicEntityTest {

	private BasicEntity currentEntity;
	private BasicEntity otherEntity;

	@Test
	public void testEqualsEqualIds() throws Exception {
		currentEntity = generateEntity(1);
		otherEntity = generateEntity(1);

		assertTrue("Should be the true", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsNotEqualIds() throws Exception {
		currentEntity = generateEntity(1);
		otherEntity = generateEntity(2);

		assertFalse("Should be the false", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsCurrentIdNull() throws Exception {
		currentEntity = generateEntity(null);
		otherEntity = generateEntity(2);

		assertFalse("Should be the false", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsOtherIdNull() throws Exception {
		currentEntity = generateEntity(1);
		otherEntity = generateEntity(null);

		assertFalse("Should be the false", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsBothIdsNull() throws Exception {
		currentEntity = generateEntity(null);
		otherEntity = generateEntity(null);

		assertFalse("Should be the false", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsStringIdsSame() throws Exception {
		currentEntity = generateEntity("id1");
		otherEntity = generateEntity("id1");

		assertTrue("Should be the true", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsStringIdSDifferentVariablesSameString() throws Exception {
		String id1 = "string id";
		String id2 = "string id";

		currentEntity = generateEntity(id1);
		otherEntity = generateEntity(id2);

		assertTrue("Should be the true", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsStringIdsDifferentStrings() throws Exception {
		String id1 = "string id1";
		String id2 = "string id2";

		currentEntity = generateEntity(id1);
		otherEntity = generateEntity(id2);

		assertFalse("Should be the false", currentEntity.equals(otherEntity));
	}

	private BasicEntity generateEntity(Object id) {
		return new BasicEntity() {
			@Override
			protected Object getInternalId() {
				return id;
			}
		};
	}
}
