DB_CONTAINER="$(sudo docker ps --format "{{.Names}}" | grep -w db_db_1)"
DB_CONTAINER_NAME="db_db_1"
SERVER_CONTAINER="$(sudo docker ps --format "{{.Names}}" | grep -w server-container)"
SERVER_CONTAINER_NAME="server-container"
DATA_STREAM_CONTAINER="$(sudo docker ps --format "{{.Names}}" | grep -w data-stream-container)"
DATA_STREAM_CONTAINER_NAME="data-stream-container"

if [ "$DB_CONTAINER_NAME" = "$DB_CONTAINER" ]; then
    echo "Database and Message Broker already running!"
    if ["$SERVER_CONTAINER" = "$SERVER_CONTAINER_NAME"]; then 
        echo "Server container already running!"
    else
        echo "Server container is not running!"
        cd ~/REPO/Beep-Me/Backend/spring_backend_server/beep-me
        sudo docker rm server-container
        sudo docker rmi server-image
        # sudo ./mvnw clean install
        sudo ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=server-image
        # sudo docker build -t beep-me-container .
        sudo docker run -p 8080:8080 -d --restart unless-stopped --name server-container server-image

        if ["$DATA_STREAM_CONTAINER" = "$DATA_STREAM_CONTAINER_NAME"]; then
            echo "Data Stream container already running!"
        else
            echo "Data Stream container is not running!"
            cd ~/REPO/Beep-Me/Backend/dataGen_server/DataGen

            sudo docker rmi data-stream-image
            #sudo ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=data-stream-image
            input="rest_list.txt"
            while IFS= read -r line
            do
                CONTAINER_NAME="data-stream-container-"
                CONTAINER_NAME+=$line
                sudo docker rm $CONTAINER_NAME
                sudo ./mvnw clean install
                sudo docker build -t data-stream-image .
                sudo docker run -p 9000:9000 --name $CONTAINER_NAME data-stream-image --rest=$line
            done < "$input"
            
        fi
    fi
    
else
    echo "Database and Message Broker are not running!"
    cd ~/REPO/Beep-Me/Backend/DB
    sudo docker rm db_db_1
    sudo docker rm rabbitMQContainer
    sudo rm -rf db_data
    sudo docker-compose up -d
    sleep 30
    sudo docker rm beep-me
    sudo docker rmi beep-me-container

    cd ~/REPO/Beep-Me/Backend/spring_backend_server/beep-me
    # sudo ./mvnw clean install
    sudo docker stop server-container
    sudo docker rm server-container
    sudo docker rmi server-image
    sudo ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=server-image
    # sudo docker build -t beep-me-container .
    sudo docker run -p 8080:8080 -d --restart unless-stopped --name server-container server-image
    chmod +x insertUsers.sh
    sleep 30
    ./insertUsers.sh

    cd ~/REPO/Beep-Me/Backend/dataGen_server/DataGen
    sudo docker rmi data-stream-image
    #sudo ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=data-stream-image
    input="rest_list.txt"
    while IFS= read -r line
    do
        CONTAINER_NAME="data-stream-container-"
        CONTAINER_NAME+=$line
        sudo docker rm $CONTAINER_NAME
        sudo ./mvnw clean install
        sudo docker build -t data-stream-image .
        sudo docker run -p 9000:9000 --name $CONTAINER_NAME data-stream-image --rest=$line
    done < "$input"
fi