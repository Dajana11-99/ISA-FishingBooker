
![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![](https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vue.js&logoColor=4FC08D)
![](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)
![](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![](https://img.shields.io/badge/firebase-%23039BE5.svg?style=for-the-badge&logo=firebase)
![CircleCI](https://img.shields.io/badge/circle%20ci-%23161616.svg?style=for-the-badge&logo=circleci&logoColor=white)
![Heroku](https://img.shields.io/badge/heroku-%23430098.svg?style=for-the-badge&logo=heroku&logoColor=white)
[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=Dajana11-99_ISA-FishingBooker)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Dajana11-99_ISA-FishingBooker&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Dajana11-99_ISA-FishingBooker)
# Fisherman

### Heroku
Backend: [![badge](https://img.shields.io/badge/Heroku-backend-9676B9)](https://ancient-cliffs-65079.herokuapp.com/)

Frontend: [![badge](https://img.shields.io/badge/Heroku-frontend-9676B9)](https://rocky-ravine-63735.herokuapp.com/)

## Description

Web application for booking fishing cabins, boats and trips. Full stack academic project made for course Internet Software Architectures at Faculty of Technical Sciences, Novi Sad.

|       Student 1       |       Student 2       |       Student 3       |   
|:----------------------:|:----------------------:|:----------------------:|
| [Aleksa Stojic RA83/2018](https://github.com/stojic19) <br> <img src="https://avatars.githubusercontent.com/stojic19" width="170" height="170"> | [Magdalena Reljin RA82/2018](https://github.com/magdalenaRA822018) <br> <img src="https://avatars.githubusercontent.com/magdalenaRA822018" width="170" height="170"> | [Dajana Zlokapa RA240/2018](https://github.com/Dajana11-99) <br> <img src="https://avatars.githubusercontent.com/Dajana11-99" width="170" height="170"> |

## Table of Contents
- [Built_with](#built_with)
- [Installation](#installation)
- [Usage](#usage)
- [DevOps_microflow](#devops_microflow)

## Built_with
- Backend: java + Spring Boot [JDK 11.0.12, SDK 14.0.2]
- Frontend: Vue js x3, Bootstrap
- Persistence: PostgreSQL,Firebase

## Installation

1. Clone the repo
   ```sh
   git clone https://github.com/Dajana11-99/ISA-FishingBooker
   ```
2. Starting frontend - open cmd in frontend directory and run:
   ```sh
   npm install
   ```
   ```sh
   npm run serve
   ```
3. Starting PostgreSQL
   - In PGAdmin create database: fishermandb
   
4. Starting backend locally - setup JDK and run

## Usage
Roles and credentials:
- Client - username: cl@gmail.com, password: 123
- Cabin Owner - username: co@gmail.com, password: 123
- Boat Owner - username: bo@gmail.com, password: 123
- Fishing instructor - username: fi@gmail.com, password: 123
- Predefined admin - username: dajanazlokapa1@gmail.com, password: 123
- Admin - username: proba@gmail.com, password: 123

## DevOps_microflow

![fisherman](readmeImages/backendDeployment.png)
![fisherman](readmeImages/deployFrontend.png)
