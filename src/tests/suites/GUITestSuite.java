package tests.suites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tests.category.GUITest;

@RunWith(Categories.class)
@Categories.IncludeCategory(GUITest.class)
@Suite.SuiteClasses({
    tests.TestChenillesForest.class,
    tests.TestChenillesRock.class,
    tests.TestChenillesWater.class,
    tests.TestFillPattes.class,
    tests.TestMove.class,
    tests.TestOutOfBond.class,
    tests.TestPattesRock.class,
    tests.TestPattesWater.class,
    tests.TestRefillImpossible.class,
    tests.TestRefillSideImpossible.class,
    tests.TestScenario1.class,
    tests.TestWheelForest.class,
    tests.TestWheelRock.class,
    tests.TestWheelWater.class,
    tests.TestMoveAllTheWay.class,
})
public class GUITestSuite {}
