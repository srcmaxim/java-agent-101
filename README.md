Java Agent 101

>gradle-version: 7.0.2  
java-version: 16

```bash
wget https://repo1.maven.org/maven2/org/javassist/javassist/3.28.0-GA/javassist-3.28.0-GA.jar -o javassist.jar
mv build/libs/java-agent.jar java-agent.jar
java -javaagent:java-agent.jar -jar java-agent.jar
```
