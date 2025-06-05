package tests.suites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import tests.category.NoGUITests;

@RunWith(Categories.class)
@Categories.IncludeCategory(NoGUITests.class)
@Suite.SuiteClasses({
    tests.TestCostPaths.class,
    tests.TestReconstructPath.class,
})
public class NoGUITestsSuite {}
