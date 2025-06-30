STYLESNAP – E-COMMERCE WEBSITE

Developed by: Swathi G
College: Gitam University
Degree: B.Tech – Computer Science Engineering
Internship Mentor:Mr.Nagasai Mudara  
Internship Company: Codebees Company
Internship Duration: 20th May to 8th July 2025


PROJECT OVERVIEW
This project is a full-stack e-commerce website that allows users to browse products, add them to cart, place orders, and manage user sessions.
The backend is built using Java Spring Boot and connected to a SQL Server database
running on Docker. The frontend uses HTML, CSS, and JavaScript.
All features are designed with multi-user session support.


FEATURES
- User Signup and Login 
- Product Listing and Product Detail Pages
- Add to Cart (with quantity support)
- Live cart count updates
- Delivery address saved once per user
- Order Placement page
- Logout and session management


TECHNOLOGIES USED
- Frontend: HTML, CSS, JavaScript
- Backend: Java Spring Boot
- Database: SQL Server (Docker)
- Tools: VS Code, Docker, Live Server


FOLDER STRUCTURE

StyleSnap/
│
├── frontend/
│   ├── index.html
│   ├── products.html
│   ├── cart.html
│   ├── contact.html
│   ├── login.html
│   ├── signup.html
│   ├── placeorder.html
│   ├── payment.html
│   ├── js/
│   ├── css/
│   └── images/
│
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── application.properties
│
└── README.txt


HOW TO RUN THE PROJECT

1. BACKEND
- Make sure Docker and SQL Server container are running
- Open backend folder in VS Code
- Run the backend using: mvn spring-boot:run
- Backend will start on: http://localhost:8083

2. FRONTEND
- Open index.html using Live Server from VS Code
- Runs on: http://127.0.0.1:5500

3. DATABASE
- Contains tables: Users, Catalog, Cart, Transaction, DeliveryDetails,contact_messages,Transaction_Details
- Dummy data is inserted into the catalog table


NOTES
- Cart count updates based on quantity, not just product count
- Only logged-in users can place orders
- Delivery address is stored once and reused


How to Run the Project
1. Backend Setup (Spring Boot)
Open a terminal in the stylesnap-backend folder.

Run the backend using:

cd ~/Desktop/StyleSnap/backend/stylesnap-backend
mvn clean install
mvn spring-boot:run

or in Windows:

mvn.cmd spring-boot:run
✅ Backend will run at: http://localhost:8083

2. Frontend Setup
Open the frontend/ folder in VS Code.

Right-click on index.html and select "Open with Live Server".
The website will open in your browser, typically at:
http://127.0.0.1:5500/index.html
✅ Make sure backend (localhost:8083) is running for data to load.

Database Setup (SQL Server)
Database Name: StyleSnap

Tool Used: SQL Server (via Docker) + Azure Data Studio
Tables Used:
Users,Catalog,Cart,Transaction,Transaction_Details,Delivery_Detail,Contact_messages

Connection Details (Used in application.properties)

spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=StyleSnap
spring.datasource.username=sa
spring.datasource.password=YourPasswordHere

SQL Server runs using Docker:

docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=YOUR_PASSWORD" -p 1433:1433 -d mcr.microsoft.com/mssql/server:2019-latest

Tables were created manually using Azure Data Studio and sample data was inserted into the Catalog table.

