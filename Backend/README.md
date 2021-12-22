# How to create a user in the vm

- Log in as root
    - sudo -i
    - insert the password
- useradd *username*
- passwd *username*
    - insert the password for the new user
    - insert the password again

# How to make a user a Super user

- Log in as root
- usermod -aG sudo *username*

# How to delete a user form Super user

- Log in as root 
- deluser *username* sudo

# How to change the shell of a user

- usermod --shell */bin/bash* *username*

# How to copy files from one machine to another using ssh 

- scp *source* *destiantion*
- to copy a directory run 
    - scp -r *source* *destiantion*

# How to make two docker containers communicate by ip in the same machine

- Using **bridge network** (Docker default networking driver). This gives simple communication between containers in the same host

- Check if bridge network is running 
    - <docker network ls> - this should show the bridge network

- If it shows, then the container can communicate using their own ip address
    - To check the ip address of the container <docker inspect mynginx | grep IPAddress>

# How to remove a docker image and container 

- Remove an image:
    - <docker rmi **image_name_or_ID**>

- Remove a container:
    - <docker rm **container_name_or_ID**>


# How to containarize a spring boot app

- <./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=beep-me-container>
- <sudo docker run -p 8080:8080 **image_name**>
    - tem de ser primerio a tag da port, porque senão não vai dar para aceder fora da vm

# How to remove a directory

- rm -Rf **name of dir**

# How to enter in exec mode in a container

- <docker exec -it **nome_container** /bin/bash >

# How to enter in the mysql cli interface

- <mysql --user=user_name --password db_name>
 



