sudo docker rm beep-me-data-container
sudo docker rmi beep-me-data-image
# sudo ./mvnw clean install
sudo ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=beep-me-data-image
# sudo docker build -t beep-me-image-datagen .
sudo docker run -p 9000:9000 --name beep-me-data-container beep-me-data-image #--rest=$1