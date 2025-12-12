# 🔗 URL Shortener

A modern, full-stack URL shortening application built with Spring Boot, MongoDB, and React. Transform long URLs into short, shareable links with real-time analytics.

## ✨ Features

- **URL Shortening**: Convert long URLs into short, memorable links
- **Smart Redirects**: Automatic redirection using short codes
- **Analytics Dashboard**: Track click counts and access statistics
- **Modern UI**: Beautiful, responsive design with smooth animations
- **Real-time Updates**: Analytics refresh automatically

## 🛠️ Tech Stack

### Backend
- **Spring Boot 3.2.0**: RESTful API framework
- **MongoDB**: NoSQL database for storing URLs
- **Java 17**: Modern Java features

### Frontend
- **React 18**: Modern UI library
- **Axios**: HTTP client for API calls
- **CSS3**: Modern styling with gradients and animations

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.6+**
- **Node.js 16+** and **npm**
- **MongoDB** (running locally or connection string)

## 🚀 Getting Started

### 1. Clone or Navigate to the Project

```bash
cd url-shortener
```

### 2. Start MongoDB

Make sure MongoDB is running on your system:

```bash
# On macOS with Homebrew
brew services start mongodb-community

# Or using Docker
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

### 3. Start the Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### 4. Start the Frontend

Open a new terminal window:

```bash
cd frontend
npm install
npm start
```

The frontend will start on `http://localhost:3000`

## 📡 API Endpoints

### Shorten URL
```
POST /api/shorten
Content-Type: application/json

{
  "originalUrl": "https://example.com/very/long/url"
}
```

**Response:**
```json
{
  "shortUrl": "http://localhost:8080/abc123",
  "shortCode": "abc123",
  "originalUrl": "https://example.com/very/long/url"
}
```

### Redirect
```
GET /{shortCode}
```
Automatically redirects to the original URL.

### Get Analytics
```
GET /api/analytics/{shortCode}
```

**Response:**
```json
{
  "shortCode": "abc123",
  "originalUrl": "https://example.com/very/long/url",
  "clickCount": 42,
  "createdAt": "2024-01-15T10:30:00",
  "lastAccessedAt": "2024-01-15T14:20:00"
}
```

## 🎨 Features in Detail

### URL Shortening
- Validates URL format
- Generates unique 6-character alphanumeric codes
- Prevents duplicate URLs (returns existing short code if URL already exists)
- Automatically adds `https://` if protocol is missing

### Analytics
- Real-time click tracking
- Creation timestamp
- Last access timestamp
- Auto-refreshes every 5 seconds

### User Experience
- Clean, modern interface
- Copy-to-clipboard functionality
- Responsive design for mobile and desktop
- Error handling with user-friendly messages
- Loading states and animations

## 🔧 Configuration

### Backend Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
server.port=8080
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=urlshortener
app.base-url=http://localhost:8080
```

### Frontend Configuration

The API base URL is configured in `frontend/src/components/UrlShortener.js` and `Analytics.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

## 📁 Project Structure

```
url-shortener/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/urlshortener/
│   │   │   │   ├── controller/
│   │   │   │   │   └── UrlController.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── ShortenUrlRequest.java
│   │   │   │   │   ├── ShortenUrlResponse.java
│   │   │   │   │   └── AnalyticsResponse.java
│   │   │   │   ├── model/
│   │   │   │   │   └── UrlEntity.java
│   │   │   │   ├── repository/
│   │   │   │   │   └── UrlRepository.java
│   │   │   │   ├── service/
│   │   │   │   │   └── UrlShortenerService.java
│   │   │   │   └── UrlShortenerApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   └── pom.xml
├── frontend/
│   ├── public/
│   │   └── index.html
│   ├── src/
│   │   ├── components/
│   │   │   ├── UrlShortener.js
│   │   │   ├── UrlShortener.css
│   │   │   ├── Analytics.js
│   │   │   └── Analytics.css
│   │   ├── App.js
│   │   ├── App.css
│   │   ├── index.js
│   │   └── index.css
│   └── package.json
└── README.md
```

## 🧪 Testing

### Test the Backend

```bash
cd backend
mvn test
```

### Test the Frontend

```bash
cd frontend
npm test
```

## 🚢 Deployment

**📖 For detailed deployment instructions, see [DEPLOYMENT.md](./DEPLOYMENT.md)**

This project can be deployed for **FREE** using:
- **Backend**: Render.com or Railway.app
- **Frontend**: Vercel
- **Database**: MongoDB Atlas (Free Tier)

### Quick Deployment Steps

1. **Set up MongoDB Atlas** (free tier)
2. **Deploy backend** on Render.com with MongoDB connection string
3. **Deploy frontend** on Vercel with backend API URL
4. **Done!** Your app is live for free! 🎉

See [DEPLOYMENT.md](./DEPLOYMENT.md) for step-by-step instructions.

## 🤝 Contributing

This is a portfolio project. Feel free to fork and enhance!

## 📝 License

This project is open source and available for educational purposes.

## 🎯 Why This Project?

- **Simple but Complete**: Demonstrates full-stack development skills
- **Modern Stack**: Uses current industry-standard technologies
- **Clean Architecture**: Well-organized code structure
- **User Experience**: Thoughtful UI/UX design
- **Portfolio Ready**: Showcases backend, frontend, and database skills

---

Built with ❤️ using Spring Boot, MongoDB, and React

