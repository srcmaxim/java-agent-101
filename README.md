# Java Agent 101

>gradle-version: 7.0.2  
java-version: 16

This code provides simple Java Agent available via [`java.lang.instrument`](https://docs.oracle.com/en/java/javase/16/docs/api/java.instrument/java/lang/instrument/package-summary.html) API with [Javassist](https://www.javassist.org) and [ByteBuddy](https://bytebuddy.net) bytecode manipulation libraries

How to run example:

```bash
wget https://repo1.maven.org/maven2/org/javassist/javassist/3.28.0-GA/javassist-3.28.0-GA.jar -o javassist.jar
mv build/libs/java-agent-101.jar java-agent-101.jar
java -javaagent:java-agent-101.jar -jar java-agent-101.jar
```
