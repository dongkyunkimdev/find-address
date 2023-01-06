# find-address
> 올바른 주소인지 보장할 수 없는 문자열을 입력받아 주소가 어느 "로(길)"인지 확인하는 애플리케이션

## 실행하기
- Java 17 설치 필요
- ```git clone https://github.com/dongkyunkimdev/find-address.git```
- ```./gradlew bootJar```
- ```java -jar build/libs/address.jar```
- GET ```http://localhost:8080/address/road-name/{문자열}```
  - ex) ![example](https://user-images.githubusercontent.com/49021557/210973040-4d646d5d-5bff-4c0c-a04d-5b108eeaadad.png)
