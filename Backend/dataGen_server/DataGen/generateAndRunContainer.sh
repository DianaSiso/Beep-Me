sudo docker rm beep-me-data
sudo docker rmi beep-me-data-container
sudo ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=beep-me-data-container
sudo docker run -p 9000:9000 --name beep-me-data beep-me-data-container