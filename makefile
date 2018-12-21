JFLAGS = -g
JC = javac
#JC = /usr/java/jdk1.8.0_45/bin/javac
JVM= java

BIN = ./exe/

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java -cp $(CCPATHS) -encoding iso-8859-1 -d $(BIN)

CCPATHS = ./src:$(JARS)
RCPATHS = $(BIN):$(JARS)

CLASSES = \
	src/Main.java \
	src/RealMain.java \
	src/domain/classes/*.java \
	src/domain/classes/restrictions/*.java \
	src/domain/controllers/*.java \
	src/persistance/*.java \
	src/presentation/*.java

JARS = ./externalClasses/json-simple-1.1c.jar:./externalClasses/junit-4.12.jar:./externalClasses/hamcrest-core-1.3.jar

default: bin classes

bin:
	mkdir -p $(BIN)

classes: $(CLASSES:.java=.class)

run:
	$(JVM) -cp $(RCPATHS) RealMain

run-old:
	$(JVM) -cp $(RCPATHS) Main

clean:
	$(RM) -rf $(BIN)*
