package test.java;

import main.java.TestMethod;

public class TestNotEqualsIntTest extends TestMethod {

	public TestNotEqualsIntTest(String newName) {
		super(newName);
	}

	@Override
	public void run() {
		testNotEqualsIntTest();
	}

	private void testNotEqualsIntTest() {
		assertNotEquals("testNotEqualsIntTest", 1, 2);
    }
}
