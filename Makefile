TITLE = Assignment2


${TITLE}.class: Utils.class Assignment2Interface.class

%.class: %.java
	javac $<

clean:
	-rm -f *.class

test: ${TITLE}.class
	java ${TITLE}

.PHONY: clean test
