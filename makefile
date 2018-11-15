JFLAGS = -g
JC = javac
#JC = /usr/java/jdk1.8.0_45/bin/javac
JVM= java

BIN = ./exe/

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java -cp $(CCPATHS) -d $(BIN)

CCPATHS = ./src:$(JARS)
RCPATHS = $(BIN):$(JARS)

CLASSES = \
	src/Main.java \
	src/domain/classes/*.java \
	src/domain/classes/restrictions/*.java \
	src/domain/controllers/*.java \
	src/domain/drivers/*.java \
	src/domain/drivers/stubs/*.java \
	src/persistance/*.java

JARS = ./externalClasses/json-simple-1.1c.jar

MAIN = Main

default: bin classes

bin:
	mkdir -p $(BIN)

classes: $(CLASSES:.java=.class)

run: $(BIN)$(MAIN).class
	$(JVM) -cp $(RCPATHS) $(MAIN)

run-driver-schedule:
	$(JVM) -cp $(RCPATHS) domain.drivers.ScheduleDriver

run-driver-group:
	$(JVM) -cp $(RCPATHS) domain.drivers.GroupDriver

run-driver-room:
	$(JVM) -cp $(RCPATHS) domain.drivers.RoomDriver

run-driver-lecture:
	$(JVM) -cp $(RCPATHS) domain.drivers.LectureDriver

run-driver-schedule:
	$(JVM) -cp $(RCPATHS) domain.drivers.ScheduleDriver

clean:
	$(RM) -rf $(BIN)*
