# AestheticTrack

## Overview

Project for the software engineering course by Matteo Nicoli.

AestheticTrack is a web-based platform designed to assist aesthetic surgeons in managing patient records, treatments, medical visits, and related data efficiently.

The platform allows surgeons to register patients, track their treatment history, and upload pre- and post-treatment photos.

## Features

<ul>
  <li>Patient Registration & Management: Store and manage patient details</li>
  <li>Treatment Tracking: Record all aesthetic procedures with relevant details.</li>
  <li>Medical Visits: Schedule and manage patient visits.</li>
  <li>FPhoto Management: Upload and compare pre- and post-treatment images.</li>
</ul>

## Technologies Used

<ul>
  <li><strong>Backend</strong>: Java, SpringBoot, Maven</li>
  <li><strong>Frontend</strong>: React.js</li>
  <li><strong>Database</strong>: h2</li>
  <li><strong>Testing</strong>: JUnit</li>
  <li><strong>Modeling</strong>: Papyrus</li>
</ul>

## Installation

<ol>

  <li> Clone the repository: </li>
  

> git clone https://github.com/mnicoli13/aesthetic-track.git



<li> Navigate into the project backend directory: </li>

> cd aesthetic-track/backend

<li>build maven project </li>

> mvn clean install

<li>run the springboot backend </li>

> mvn spring-boot:run

<li> Navigate into the project frontend directory: </li>

> cd aesthetic-track/frontend

<li> Install dependencies: </li>

> npm i

<li> Run React.js frontend: </li>

> npm run dev

<li>Frontend is now available at </li>

> http://localhost:5173/

</ol>

<strong>Note: </strong> in Eclipse, it is necessary to have the Spring Tools 4 plugin installed.

<strong>Note: </strong> .env file in frontend must contain VITE_API_URL='http://localhost:port/api'



