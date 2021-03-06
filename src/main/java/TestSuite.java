package main.java;

import java.util.Collection;
import java.util.HashMap;
import main.java.TestExistsException;

public class TestSuite extends Test {

	private HashMap<String,Test> tests;
	private SelectionTemplate selector;

	public TestSuite (String newName) {
		super(newName);
		tests = new HashMap<String, Test>();
		tags = new TagList(suiteReservedTag);
		selector = new SelectionAlways();
	}
	
	@Override
	public void runTest() {
		printer.printSuite(this);
		setUp();
		long timeTestBegins = System.currentTimeMillis();
		Collection<Test> col = tests.values();

		for (Test test : col) {
			if (mustBeRun(test)) {
				runSubTest(test);
			}
		}
		
		this.timeElapsed = (System.currentTimeMillis() - timeTestBegins);
		tearDown();
		printer.removeSuite(this);
	}
	
	public void addTest(Test test) throws TestExistsException {
		if (!tests.containsKey(test.getName())) {
			tests.put(test.getName(), test);
			test.addSuite(name);
		}
		else { 
			throw new TestExistsException(test.getName() 
					+ " already present in TestSuite "
					+ this.getName()); 
		}
	}
	
	@Override
	public boolean isSetToSkip(){
		return false;
	}

	public ResultPrinter getPrinter() {
		return printer;
	}
	
	public void setToRunByTestName(String testRegex){
		selector = new SelectionByTestName(testRegex);
	}
	
	public void setToRunByTag(String tag){
		selector = new SelectionByTags(new TagList(tag));
	}
	
	public void setToRunByTags(TagList tags){
		selector = new SelectionByTags(tags);
	}
	
	public void setToRunByTagsAndTestName(TagList tags, String testRegex){
		selector = new SelectionByTagsAndTestName(tags, testRegex);
	}
	
	public void setToRunByTagsOrTestName(TagList tags, String testRegex){
		selector = new SelectionByTagsOrTestName(tags, testRegex);
	}
	
	public void setToRunByTagsAndSuiteName(TagList tags, String suiteRegex){
		selector = new SelectionByTagsAndSuiteName(tags, suiteRegex);
	}
	
	public void setToRunByTagsOrSuiteName(TagList tags, String suiteRegex){
		selector = new SelectionByTagsOrSuiteName(tags, suiteRegex);
	}
	
	public void setToRunByTagsAndTestNameAndSuiteName(TagList tags, String testRegex, String suiteRegex){
		selector = new SelectionByTagsAndTestNameAndSuiteName(tags, testRegex, suiteRegex);
	}
	
	public void setToRunByTagsAndTestNameOrSuiteName(TagList tags, String testRegex, String suiteRegex){
		selector = new SelectionByTagsAndTestNameOrSuiteName(tags, testRegex, suiteRegex);
	}
	
	public void setToRunByTagsOrTestNameAndSuiteName(TagList tags, String testRegex, String suiteRegex){
		selector = new SelectionByTagsOrTestNameAndSuiteName(tags, testRegex, suiteRegex);
	}
	
	public void setToRunByTagsOrTestNameOrSuiteName(TagList tags, String testRegex, String suiteRegex){
		selector = new SelectionByTagsOrTestNameOrSuiteName(tags, testRegex, suiteRegex);
	}
	
	public Collection<Test> getTests(){
		return this.tests.values();
	}
	
	public boolean mustBeRun(Test test){
		return !test.isSetToSkip() && selector.isSelected(test);
	}
	
	private void runSubTest(Test test) {
		test.setUp();
		test.setPrinter(printer);
		test.runTest();
		test.tearDown();
	}
}
