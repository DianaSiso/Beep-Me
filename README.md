
# Beep Me

## Description

This project was developed in the context of [Introduction to Software Engineering](https://www.ua.pt/en/uc/12288) curricular unit, part of the Computer Science Bachelor at [Aveiro University](https://www.ua.pt/). It was lectured by professors [José Luis Guimarães Oliveira](https://www.ua.pt/pt/p/10309676) and [Ilídio Fernando de Castro Oliveira](https://www.ua.pt/en/p/10318398) during the 2020/2021 school year.

We decided to develop a web application that will manage orders and deliveries of food in shopping food courts in order to reduce contagious contact and agglomeration and study how well is the food court running. In this application the customer will link its order to the application and when the order is ready the app will notify the consumer. The app will also produce information about orders, time of delivery and rush hours so that the food court managers can study statistics and create historic analysis.

The application will have 3 main parts:

- _Consumer side_ where the client can scan a QRcode or insert a key present on his receipt and their phone will receive a notification when their order is ready.
- _Restaurant side_ where the workers will register the client’s order (automatically if system is integrated) and indicate when the order is ready for pickup.
- _Management side_ where analysts will receive data about which restaurants work better, the average time of delivery per restaurant and affluence hours

Our product is similar to some restaurants’ pager systems however it is more hygienic, has a bigger range (no need to be close to the restaurant) and it has the added bonus of being able to collect data.
For the development of our product, we will take inspiration from past experiences with these pagers but also interview some of our peers to get their opinion on the matter.

## Technology stack

The system was implemented in **Spring Boot**, with a **MySQL** database, **RabbitMQ** as a message broker and a data generation program written in **Java**. For the Beep Me application's user interface we used **Angular** [web application] and **Flutter** [mobile application available for Android and iOS]. All our system modules are deployed in our virtual machine, and each one has its own **Docker** container. The containers names are (frontend: frontend_container, server:beep-me, message broker: rabbitMQContainer, database: db_db_1, data generation: beep-me-data-container).

## Docker Containers

Deployed in our Virtual Machine are the 5 modules of our system, which one with his own **Docker** container. The containers names are (frontend: frontend_container, server:beep-me, message broker: rabbitMQContainer, database: db_db_1, data generation: beep-me-data-container).

The main container is the container of the main Server. Is through this container that almost all the modules communicate. To create this container we used the maven/spring command `./mvnw spring-boot:build-image` that creates an image of that maven project. This container is running in the port 9000.

Then we have 2 containers that are created in the same docker compose, that are the **MySQL** container, that contains the database, and all the data, and the **RabbitMQ** message broker.

The Frontend is also in a container, in this case in a container that is running nginx. The image is built using a Dockerfile located in the angular project directory. In that Dockerfile we programmed it to at first create an npm container, that generates all the modules necessary for the angular app and then builds the project generating the "artifacts". Then these files that were created are copied over to the nginx container, the environment where the server is run.

The Data Generation container is build the same way as the main Serve, we use the maven/spring command `./mvnw spring-boot:build-image` to create the image.

## Data Generation

In terms of data generation, our goal was to simulate the behavior of the real orders that are created when the client makes an order at the counter. 
To do so, we developed a java program that creates an order based on the time of day and the restaurant in case. If the time of day is less agitated, an order will be less likely to be created. We assigned a probability to each restaurant to make sure that bigger and more popular restaurants would have more orders than the others. The orders are published using a message broker. 
This works by publishing an order in the message broker each time one is created. On the server side, it will be listening to new orders being added in a specific queue. When an order is added, the server will "pick it up" and save it in the database. To make all of this happen, we had to integrate the data generated program in a Spring Boot application to be able to publish each order created to the message broker.


## How to run

### Run containers in the vm:

All the files are localted in the beep-me user, under the directory of REPO/Beep-me/
The frontend and the server, as well as the the database and message broker are turned on for being able to open the url of the frontend. The data generation is turned off because the is no need to generate data when that data will not be processed. 
- To start generating data run:
```sh
sudo docker start beep-me-data-container
```
Just to be documented, below there is the commands to create and run each container.
- To generate database and message broker image and run their containers:
```sh
cd Backend/DB/
sudo docker-compose up -d
```
- To generate server image and run the container:
```sh
cd Backend/spring_backend_server/beep-me/
chmod +x generateAndRunContainer.sh
./generateAndRunContainer.sh
```
- To generate frontend image and run the container:
```sh
cd Frontend/beep_me_app/
chmod +x generateAndRunContainer.sh
./generateAndRunContainer.sh
```
- To generate the data generation image and run the container:
```sh
cd Backend/dataGen_server/DataGen/
chmod +x generateAndRunContainer.sh
./generateAndRunContainer.sh
```

### Run web application localy:

```sh
cd Frontend
cd beep_me_app
npm install
npm install chart.js
ng serve
```

Open https://localhost:4200/ on browser

### Run web application remotely:

Open http://deti-engsoft-02.ua.pt:80/ on browser while connected to UA's VPN.

### Mobile application:

In the folder Frontend, there is a folder named apk, and inside there is an apk of the mobile app that can be installed to test the application.

## User Accounts

| Username            | Password      | Role          |
| ------------------- | ------------- | ------------- |
| Carlos              | admin1        | Administrator |
| Sofia               | admin2        | Administrator |
| mcdonalds           | mc1234donalds | Restaurant    |
| h3                  | hsrrCmSKTbSN  | Restaurant    |
| kfc                 | Qz2dN3CKR4vv  | Restaurant    |
| tacobell            | GC6tfrF4Tcgc  | Restaurant    |
| vitaminas           | JtkzMfX5GPvF  | Restaurant    |
| aguladoprego        | yEWjVJpGH6ca  | Restaurant    |
| akigrill            | KkDp4kc85Gya  | Restaurant    |
| oita                | ngPU38sPRxwG  | Restaurant    |
| domfranguito        | eK8kb2hKWwhe  | Restaurant    |
| alicarius           | U99WUSdTv7u7  | Restaurant    |
| bicadaria           | uqpeNs9nLyUa  | Restaurant    |
| caffelato           | 4bHtmCEp5yzF  | Restaurant    |
| caseiroebom         | 8uP88yJbJTVE  | Restaurant    |
| hummy               | mLgkUJVASd3J  | Restaurant    |
| italianrepublic     | eu24uq9m26mB  | Restaurant    |
| malguinhasepregos   | e9Nhk54yVchu  | Restaurant    |
| maniapokebowls      | KQUK96SqX9d6  | Restaurant    |
| patronopizza        | NFDM7bkHJsze  | Restaurant    |
| paodivino           | E7p9zkmZxJAj  | Restaurant    |
| sical               | HK7jE5qst83k  | Restaurant    |
| sunbufe             | vgqNqECgXZWG  | Restaurant    |
| tasquinhadobacalhau | PrRnw7HLYbba  | Restaurant    |
| zedatripa           | 8pVzVte7V2zk  | Restaurant    |

## Team

| Nome             | Nmec   | Role |
| ---------------- | ------ | -----|
| Andreia Portela  | 97953  | Team manager & developer|
| Diana Siso       | 98607  | Product owner & developer |
| Miguel Marques   | 100850 | Architect & developer|
| Ricardo Ferreira | 98411  | DevOps master & developer|

## Bookmarks

- [Frontend Website](http://deti-engsoft-02.ua.pt:80)
- [Report development](https://docs.google.com/document/d/1fu4VGWpGIC-uMgZ5bZGCmADkit5x35v22K9q8SeiTM8/edit?usp=sharing)
- [Management board](https://projetoies.atlassian.net/jira/software/projects/IES/boards/1)
- [API documentation](http://deti-engsoft-02.ua.pt:8080/swagger-ui.html#/)

