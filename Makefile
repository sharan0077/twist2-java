
ifndef prefix
prefix=/usr/local
endif

PROGRAM_NAME=twist2

default:
	ant jar

clean:
	ant clean

install:
	install -m 755 -d $(prefix)/lib/$(PROGRAM_NAME)/java
	install -m 755 -d $(prefix)/lib/$(PROGRAM_NAME)/java/libs
	install -m 644 libs/* $(prefix)/lib/$(PROGRAM_NAME)/java/libs
	install -m 644 build/jar/* $(prefix)/lib/$(PROGRAM_NAME)/java
	install -m 755 -d $(prefix)/bin
	install -m 755 twist-java.sh $(prefix)/bin
	install -m 755 -d $(prefix)/share/$(PROGRAM_NAME)/languages
	install -m 644 java.json $(prefix)/share/$(PROGRAM_NAME)/languages

