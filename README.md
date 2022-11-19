### SIZE API
### REST API to handle product size operations

### Technologies
- OpenJDK 17
- SpringBoot 2.7.5
- MYSQL 
- JUnit
- JSON

Application configuration:
````yaml
server:
  port: 8080
  servlet:
    contextPath: /

spring:
  datasource:
    username: size_user_service
    password: 123
    url: jdbc:mysql://${MYSQL_HOST:localhost}:3306/products
    driver-class-name: com.mysql.cj.jdbc.Driver
````

## Test Configuration

pom.xml additional setup:
````xml
<!-- H2 memory DB -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.1.210</version>
    <scope>test</scope>
</dependency>

<!-- junit dependency -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.1</version>
    <scope>test</scope>
</dependency>

<!-- jacoco coverage report -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.8</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>		
````

Java annotation configurations (Stub Tests):
````java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestBase {}
````

Java annotation configuration (MVC / Integrated)
````java
@Sql({"/create-table.sql"})
@RunWith(SpringRunner.class)
public class TestController{}
````

Java imports:
````java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
````

Add application.yml configuration file into resources test folder:
````yaml
server:
  port: 8080
  servlet:
    contextPath: /

spring:
  datasource:
    username: root
    password: 123
    url: jdbc:h2:mem:products
    driver-class-name: org.h2.Driver
````

Add sql initialization file into resources test folder:
````sql
create schema if not exists products;

create table if not exists sizes (
    id varchar(100) not null,
    description varchar(100) not null,
    short_description varchar(10) not null,
    size_range varchar(50) not null,
    primary key (id)
);
````







