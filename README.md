💬 Real-Time Chat Application
A secure and scalable real-time chat application built with Spring Boot, WebSocket, and MySQL, enabling seamless communication between users in dynamic chat rooms. Deployed on Render.com.

⚡ Live Demo: https://chatroom-app-v2.onrender.com
(Note: Free Render instance – initial load may take 1–2 minutes)

🚀 Features
🔐 Secure & Scalable backend architecture

🧩 Create & manage chat rooms with unique invite codes

💬 Real-time messaging via WebSocket

🛠️ Update & delete rooms with ease

🧑‍🤝‍🧑 Supports 500+ concurrent users with zero downtime

🗄️ MySQL integration for persistent storage

🛠️ Tech Stack
Backend: Spring Boot, WebSocket

Database: MySQL

Deployment: Render.com

Other: REST APIs, JPA, Lombok
📦 Getting Started
1. Clone the repository
bash
Copy
Edit
git clone https://github.com/your-username/real-time-chat-app.git
cd real-time-chat-app
2. Configure MySQL
Update your application.properties with your local or remote MySQL credentials.

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/chat_app
spring.datasource.username=your-username
spring.datasource.password=your-password
3. Run the application
bash
Copy
Edit
./mvnw spring-boot:run
App will be accessible at: http://localhost:8080

🌐 Deployment
Backend is hosted on Render.com (free tier). Expect a ~1–2 minute cold start delay.

To deploy your own:

Push code to a GitHub repository

Connect it to Render

Configure environment variables and database URL

Deploy!

