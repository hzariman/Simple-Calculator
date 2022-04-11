.SUFFIXES: .java .class
.java.class:
	javac -g $<

all:	Driver

Driver:	Driver.class

Driver.class:	Base.java Driver.java MyLib.java Heap.java Tracker.java
	javac -g Driver.java
	echo 'java Driver $$*' > Driver
	chmod ug+rx Driver

MyLib.class: MyLib.java

clean:
	rm -f *.class Driver

public:
	rm -f *.class Driver
	

new:
	make clean
	make

