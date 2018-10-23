# WILDLIFE TRACKER  Application
Wildlife Tracker, 10/16/2018
#### By **Arnold Henry**
## Description
This is a Wildlife application.
* This application is intended to track both all animals and endangered animals.
Its main purpose is record all type of animals including ranger who spotted the animal.
The rangers can submit as many times as they want depending on how many they have sighted.
* Rangers are able to be sorted depending on where they sighted the animal.
## Setup/Installation Requirements
Get to the link below and test the app. Database creation will is as below;
**PSQL**
* create database wildlife_tracker;
* grant all privileges on database wildlife_tracker to <username>
* create table animals (id serial primary key,name text,location varchar,age int,health text);
* create table sightings (id serial primary key,location varchar,age int,ranger text);

[Wildlife Tracker](https://wildlife25.herokuapp.com/)
## Known Bugs
No bugs
## Technologies Used
**used;**
* Java
* CSS
* Boostrap
* Postgresql
* Velocity
* Spark
## Support and contact details
Comment if any issues arise
Copyright (c) {2018} **Arnold Henry**
