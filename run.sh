rm -rf target
mvn compile
mvn exec:java -Dexec.mainClass=com.rishabhks.App
