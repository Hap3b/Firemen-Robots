
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

JUNIT_CP=lib/junit-platform-console-standalone-1.12.0-RC2-javadoc.jar
GUI_CP=lib/gui.jar
CLASSPATH=bin:$(JUNIT_CP):$(GUI_CP)

SRC_FILES=$(shell find src -name "*.java")

all: compile

doc:
	javadoc -d doc -sourcepath src -subpackages field:io:machines:simulator -classpath lib/gui.jar

compile:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src $(SRC_FILES)

testInvader:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestInvader.java

testLecture:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestLecteurDonnees.java

testPart1:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestPart1.java

testMove:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestMove.java

testOutOfBond:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestOutOfBond.java

testWheelWater:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestWheelWater.java

testWheelForest:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestWheelForest.java

testWheelRock:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestWheelRock.java

testPattesWater:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestPattesWater.java

testPattesRock:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestPattesRock.java

testChenillesWater:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestChenillesWater.java

testChenillesRock:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestChenillesRock.java

testChenillesForest:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestChenillesForest.java

testFillPattes:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestFillPattes.java

testFillSide:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestFillSide.java

testFillWater:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestFillWater.java

testRefillImpossible:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestRefillImpossible.java

testRefillSideImpossible:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src src/tests/TestRefillSideImpossible.java

exeInvader: 
	java -classpath $(CLASSPATH) tests.TestInvader

exeLecture: 
	java -classpath $(CLASSPATH) tests.TestLecteurDonnees cartes/carteSujet.map

exePart1:
	java -classpath $(CLASSPATH) tests.TestPart1

exeMove:
	java -classpath $(CLASSPATH) tests.TestMove

exeOutOfBond:
	java -classpath $(CLASSPATH) tests.TestOutOfBond

exeWheelWater:
	java -classpath $(CLASSPATH) tests.TestWheelWater

exeWheelForest:
	java -classpath $(CLASSPATH) tests.TestWheelForest

exeWheelRock:
	java -classpath $(CLASSPATH) tests.TestWheelRock

exePattesWater:
	java -classpath $(CLASSPATH) tests.TestPattesWater

exePattesRock:
	java -classpath $(CLASSPATH) tests.TestPattesRock

exeChenillesWater:
	java -classpath $(CLASSPATH) tests.TestChenillesWater

exeChenillesRock:
	java -classpath $(CLASSPATH) tests.TestChenillesRock

exeChenillesForest:
	java -classpath $(CLASSPATH) tests.TestChenillesForest

exeFillPattes:
	java -classpath $(CLASSPATH) tests.TestFillPattes

exeFillSide:
	java -classpath $(CLASSPATH) tests.TestFillSide

exeFillWater:
	java -classpath $(CLASSPATH) tests.TestFillWater

exeRefillImpossible:
	java -classpath $(CLASSPATH) tests.TestRefillImpossible

exeRefillSideImpossible:
	java -classpath $(CLASSPATH) tests.TestRefillSideImpossible

clean:
	rm -rf bin/*
