TITLE = Assignment2


${TITLE}.class: Utils.class Assignment2Interface.class

%.class: %.java
	javac -d . $<

clean:
	-rm -fr *.class utils

test: ${TITLE}.class
	java ${TITLE} $<

.PHONY: clean test
