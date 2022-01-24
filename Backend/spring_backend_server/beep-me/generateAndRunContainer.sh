CONTAINER_NAME_RETURNED="$(sudo docker ps --format "{{.Names}}" | grep -w db_db_1)"
CONTAINER_NAME="db_db_1"
if [ "$CONTAINER_NAME" = "$CONTAINER_NAME_RETURNED" ]; then
    sudo docker rm beep-me
    sudo docker rmi beep-me-container
    # sudo ./mvnw clean install
    sudo ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=beep-me-container
    # sudo docker build -t beep-me-container .
    sudo docker run -p 8080:8080 -d --restart unless-stopped --name beep-me beep-me-container 
    #sudo docker run -p 8080:8080 --restart unless-stopped --name beep-me beep-me-container 
else
    sudo docker rm db_db_1
    sudo docker rm rabbitMQContainer
    sudo rm -rf db_data
    cd ~/REPO/Beep-Me/Backend/DB
    sudo docker-compose up -d
    sleep 30
    sudo docker rm beep-me
    sudo docker rmi beep-me-container
    cd ~/REPO/Beep-Me/Backend/spring_backend_server/beep-me
    # sudo ./mvnw clean install
    sudo ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=beep-me-container
    # sudo docker build -t beep-me-container .
    #sudo docker run -p 8080:8080 -d --restart unless-stopped --name beep-me beep-me-container 
    sudo docker run -p 8080:8080 --restart unless-stopped --name beep-me beep-me-container 
    # chmod +x insertUsers.sh
    # ./insertUsers.sh
fi