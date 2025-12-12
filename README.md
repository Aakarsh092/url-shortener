# URL Shortener

A simple URL shortener app that converts long URLs into short links. Built with Spring Boot, MongoDB, and React.

## Features

- Shorten long URLs
- Track click counts
- View analytics for shortened URLs
- Simple and clean UI

## Tech Stack

- **Backend**: Spring Boot, Java
- **Database**: MongoDB
- **Frontend**: React

## How to Run

### Prerequisites

- Java 17 or higher
- Maven
- Node.js and npm
- MongoDB (running locally)

### Steps

1. Start MongoDB on your machine

2. Start the backend:
```bash
cd backend
mvn spring-boot:run
```
Backend runs on http://localhost:8080

3. Start the frontend (in a new terminal):
```bash
cd frontend
npm install
npm start
```
Frontend runs on http://localhost:3000

4. Open http://localhost:3000 in your browser

## Project Structure

```
url-shortener/
├── backend/          # Spring Boot backend
├── frontend/         # React frontend
└── README.md
```

## API Endpoints

- `POST /api/shorten` - Shorten a URL
- `GET /api/{shortCode}` - Redirect to original URL
- `GET /api/analytics/{shortCode}` - Get analytics for a short code

## License

Open source project for learning purposes.
