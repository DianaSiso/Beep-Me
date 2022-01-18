# Beep Me

## Description

This project was developed in the context of [Introduction to Software Engineering](https://www.ua.pt/en/uc/12288) curricular unit, part of the Computer Science Bachelor at [Aveiro University](https://www.ua.pt/). It was lectured by professors [José Luis Guimarães Oliveira](https://www.ua.pt/pt/p/10309676) and [Ilídio Fernando de Castro Oliveira](https://www.ua.pt/en/p/10318398) during the 2020/2021 school year.

We decided to develop a web application that will manage orders and deliveries of food in shopping food courts in order to reduce contagious contact and agglomeration and study how well is the food court running. In this application the customer will link its order to the application and when the order is ready the app will notify the client. The app will also produce information about orders, time of delivery and rush hours so that the food court managers can study statistics and create historic analysis.

The application will have 3 main parts:

- _Client side_ where the client can scan a QRcode or insert a key present on his receipt and their phone will receive a notification when their order is ready.
- _Restaurant side_ where the workers will register the client’s order (automatically if system is integrated) and indicate when the order is ready for pickup.
- _Management side_ where analysts will receive data about which restaurants work better, the average time of delivery per restaurant and affluence hours

Our product is similar to some restaurants’ pager systems however it is more hygienic, has a bigger range (no need to be close to the restaurant) and it has the added bonus of being able to collect data.
For the development of our product, we will take inspiration from past experiences with these pagers but also interview some of our peers to get their opinion on the matter.

## Technology stack

The system was implemented in **Spring Boot**, with a **MySQL** database, **rabbit MQ** as a message broker and a data generation program written in **Java**. For the Beep Me application's user interface we used **Angular** [web application] and **Flutter** [mobile application available for Android and iOS].

## How to run

### Run servers:

```sh

```

### Run web application:

```sh
cd Frontend
cd beep_me_app
npm install
npm install chart.js
ng serve
```

open https://localhost:4200/ on browser

### Run mobile application:

```sh

```

## User Accounts

| Username            | Password      | Role          |
| ------------------- | ------------- | ------------- |
| Carlos              | admin1        | Administrator |
| Sofia               | admin2        | Administrator |
| h3                  | hsrrCmSKTbSN  | Restaurant    |
| kfc                 | Qz2dN3CKR4vv  | Restaurant    |
| mcdonalds           | mc1234donalds | Restaurant    |
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

| Nome             | Nmec   |
| ---------------- | ------ |
| Andreia Portela  | 97953  |
| Diana Siso       | 98607  |
| Miguel Marques   | 100850 |
| Ricardo Ferreira | 98411  |

## Bookmarks

- [Report development](https://docs.google.com/document/d/1fu4VGWpGIC-uMgZ5bZGCmADkit5x35v22K9q8SeiTM8/edit?usp=sharing)
- [Management board](https://projetoies.atlassian.net/jira/software/projects/IES/boards/1)
- [API documentation](_por link para documentaçao da api_)
