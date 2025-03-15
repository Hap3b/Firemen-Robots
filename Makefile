# Chemins et fichiers
JUNIT_CP=lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar
GUI_CP=lib/gui.jar
JACOCO_AGENT=lib/jacocoagent.jar
JACOCO_CLI=lib/jacococli.jar
COVERAGE_FILE=coverage.exec
REPORT_DIR=coverage_report
CLASSPATH=bin:$(JUNIT_CP):$(GUI_CP)

SRC_FILES=$(shell find src -name "*.java")

# Compilation
all: compile

compile:
	javac -d bin -classpath $(CLASSPATH) -sourcepath src $(SRC_FILES)

# Exécuter les tests avec JaCoCo
test: compile
	java -javaagent:$(JACOCO_AGENT)=destfile=$(COVERAGE_FILE) -classpath $(CLASSPATH) org.junit.runner.JUnitCore $(shell find bin/tests -name "*.class" | sed 's/bin\/tests\///' | sed 's/\.class//' | tr '/' '.' | sed 's/^/tests./')

# Générer un rapport de couverture JaCoCo
coverage_report: test
	mkdir -p $(REPORT_DIR)
	java -jar $(JACOCO_CLI) report $(COVERAGE_FILE) --classfiles bin --sourcefiles src --html $(REPORT_DIR)

# Nettoyage
clean:
	rm -rf bin/* $(COVERAGE_FILE) $(REPORT_DIR)

# Nettoyage spécifique des fichiers de couverture
clean_coverage:
	rm -rf $(COVERAGE_FILE) $(REPORT_DIR)

# Générer la documentation Javadoc
doc:
	javadoc -d doc -sourcepath src -subpackages field:io:machines:simulator -classpath $(GUI_CP)
