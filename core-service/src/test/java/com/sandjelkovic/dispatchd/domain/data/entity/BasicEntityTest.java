package com.sandjelkovic.dispatchd.domain.data.entity;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(classes = {DispatchdApplication.class})
public class BasicEntityTest {

	private BasicEntity currentEntity;
	private BasicEntity otherEntity;

	@Test
	public void testEqualsEqualIds() {
		currentEntity = generateEntity(1);
		otherEntity = generateEntity(1);

		assertTrue("Should be the true", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsNotEqualIds() {
		currentEntity = generateEntity(1);
		otherEntity = generateEntity(2);

		assertFalse("Should be the false", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsCurrentIdNull() {
		currentEntity = generateEntity(null);
		otherEntity = generateEntity(2);

		assertFalse("Should be the false", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsOtherIdNull() {
		currentEntity = generateEntity(1);
		otherEntity = generateEntity(null);

		assertFalse("Should be the false", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsBothIdsNull() {
		currentEntity = generateEntity(null);
		otherEntity = generateEntity(null);

		assertFalse("Should be the false", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsStringIdsSame() {
		currentEntity = generateEntity("id1");
		otherEntity = generateEntity("id1");

		assertTrue("Should be the true", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsStringIdSDifferentVariablesSameString() {
		String id1 = "string id";
		String id2 = "string id";

		currentEntity = generateEntity(id1);
		otherEntity = generateEntity(id2);

		assertTrue("Should be the true", currentEntity.equals(otherEntity));
	}

	@Test
	public void testEqualsStringIdsDifferentStrings() {
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
