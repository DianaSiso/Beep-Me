sudo docker rm beep-me-data
sudo docker rmi beep-me-data-container
sudo ./mvnw clean install
# sudo ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=beep-me-data-container
sudo docker build -t beep-me-image-datagen .
sudo docker run -p 9000:9000 --name beep-me-container-datagen beep-me-image-datagen --rest=$1