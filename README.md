# itsuda-api

it업계 사람들을 위한 이벤트 공유 서비스


### 배포
방법1. kotless를 이용해서 lambda 배포
방법2. spring-cloud를 이용해 lambda 배포

방법2로 진행
- build
```
./gradlew shadowJar
```

- aws-cli로 배포
```
 aws lambda update-function-code --profile jin --function-name <생성한 람다 이름> --zip-file  fileb://build/libs/itsuda-0.0.1-SNAPSHOT-all.jar
ex) aws lambda update-function-code --profile jin --function-name itsuda-api-prod-event-service --zip-file  fileb://build/libs/itsuda-0.0.1-SNAPSHOT-all.jar

 # aws cli로 iam 계정 로그인 후 해당 명령어로 실행
 # aws configure  명령어로 access token, secret token 입력해서 설정
```
- application.yml 설정
```
spring:
  cloud:
    function:
      definition: greet
  main:
    banner-mode: off

logging:
  level:
    root: info
```

- lambda에서 런타임 설정
<br>런타임 설정에서 핸들러 Info를 편집해서 RequestHandler가 있는 클래스 경로로 지정해준다.
