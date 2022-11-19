mvn clean verify sonar:sonar \
  -Dsonar.projectKey=supply-core-java \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=$1