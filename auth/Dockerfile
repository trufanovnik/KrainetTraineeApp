FROM eclipse-temurin:21-jdk-alpine as app-build
ENV RELEASE=21

WORKDIR /opt/build
COPY ./target/auth-0.0.1-SNAPSHOT.jar ./application.jar

RUN java -Djarmode=layertools -jar application.jar extract
RUN $JAVA_HOME/bin/jlink \
         --add-modules `jdeps --ignore-missing-deps -q -recursive --multi-release ${RELEASE} --print-module-deps -cp 'dependencies/BOOT-INF/lib/*':'snapshot-dependencies/BOOT-INF/lib/*' application.jar` \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output jdk

FROM alpine:3.18

ARG BUILD_PATH=/opt/build
ENV JAVA_HOME=/opt/jdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"

RUN addgroup -g 1000 spring-app && \
    adduser -u 1000 -G spring-app -h /home/spring-app -D spring-app

USER spring-app:spring-app
WORKDIR /opt/workspace
COPY src/main/resources/db/migration /app/migration
COPY --from=app-build $BUILD_PATH/jdk $JAVA_HOME
COPY --from=app-build $BUILD_PATH/spring-boot-loader/ ./
COPY --from=app-build $BUILD_PATH/dependencies/ ./
COPY --from=app-build $BUILD_PATH/snapshot-dependencies/ ./
COPY --from=app-build $BUILD_PATH/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]