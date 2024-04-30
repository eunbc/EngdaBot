#빌드된 JAR 파일을 실행하기 위한 새로운 Docker 이미지
FROM openjdk:17-slim

# 작업 디렉토리 설정
WORKDIR /app

# 1단계에서 빌드한 JAR 파일을 현재 이미지로 복사
COPY build/libs/engdabot-0.0.1-SNAPSHOT.jar engdabot.jar

# 컨테이너가 시작될 때 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java -Duser.timezone=Asia/Seoul -DbotToken=${BOT_TOKEN} -DapiKey=${API_KEY} -jar engdabot.jar"]
