# An Non-Innovative Website for UTEK

A website developed in 5 hours during a competition to solve a source distribution problem. I worked on the backend, while my friend Gary handled the frontend. Despite being a 2-person team, we outperformed all but three 4-person teams. Unfortunately, we didn't win a prize since our project was deemed a typical task for a full-stack engineering intern.

## Problem Statement

Your task is to design a Disaster Relief Communication Hub—a system that improves
communication during disaster scenarios, leveraging limited internet connectivity and
focusing on how messages are passed, tracked, and prioritized to ensure effective
coordination and response. The hub will serve as a critical tool to maintain
communication between responders and affected citizens, addressing the unique
challenges posed by disaster situations.

## Solution Overview
Our design allows users (citizens) to enter their life needs during a disaster. The system operates as follows:
1. **Request Logging**: User requests are stored in a **MySQL database**.
2. **Email Notifications**: The backend sends an email to the resource administrator with details of the request.
3. **Excel Export**: The resource administrator can download the requests as an Excel file using the link provided in the email.

## Technologies Used
- **Frontend**: HTML, CSS, JavaScript (handled by Gary)
- **Backend**: Java Spring, mybatisPlus
- **Database**: MySQL
- **Email Integration**: SMTP for automated notifications


Our design allows users/citizens to enter their life needs in the disaster. The request will be recorded in our MySQL database. At the same time, the backend will send an email to the resource administrator, who could use the link in the email to download the request as an Excel file. 


<img width="729" alt="image" src="https://github.com/user-attachments/assets/fe78492a-77c8-42a6-8cc1-d55ae4ce2bf3" />
<img width="722" alt="image" src="https://github.com/user-attachments/assets/2b9fa4cf-f2fd-4d45-86d7-53ba5c06e464" />
<img width="735" alt="image" src="https://github.com/user-attachments/assets/48dba7c5-c599-4538-a12f-b62ac729d9ef" />
