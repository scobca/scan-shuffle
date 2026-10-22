FROM scobca/java21-doppler:latest

COPY build/libs/scan-shuffle.jar app.jar

ENTRYPOINT ["/bin/sh", "-c", "export DOPPLER_TOKEN=\"$SCAN_SHUFFLE_DOPPLER_ACCESS_KEY\"; exec doppler run -- java -jar /app.jar"]
