sudo docker rm beep-me
sudo docker rmi beep-me-container
sudo ./mvnw clean install
# sudo ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=beep-me-container
sudo docker run -p 8080:8080 --name beep-me beep-me-container 