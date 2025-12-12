# Deployment Guide - URL Shortener

This guide will help you deploy your URL Shortener application on free hosting services.

## Architecture

- **Backend**: Spring Boot (Java) → Deploy on Render.com
- **Frontend**: React → Deploy on Vercel
- **Database**: MongoDB → Use MongoDB Atlas (Free Tier)

---

## Step 1: Set Up MongoDB Atlas (Free)

1. Go to [MongoDB Atlas](https://www.mongodb.com/cloud/atlas/register)
2. Sign up for a free account
3. Create a new cluster (choose the FREE M0 tier)
4. Wait for the cluster to be created (2-3 minutes)
5. Click **"Connect"** on your cluster
6. Choose **"Connect your application"**
7. Copy the connection string (it will look like):
   ```
   mongodb+srv://<username>:<password>@cluster0.xxxxx.mongodb.net/?retryWrites=true&w=majority
   ```
8. Replace `<username>` and `<password>` with your database user credentials
9. Add the database name at the end: `/urlshortener?retryWrites=true&w=majority`
10. **Save this connection string** - you'll need it for Render deployment

### Create Database User

1. In MongoDB Atlas, go to **Database Access**
2. Click **"Add New Database User"**
3. Choose **"Password"** authentication
4. Create a username and password (save these!)
5. Set user privileges to **"Read and write to any database"**
6. Click **"Add User"**

### Configure Network Access

1. Go to **Network Access** in MongoDB Atlas
2. Click **"Add IP Address"**
3. Click **"Allow Access from Anywhere"** (for Render deployment)
4. Click **"Confirm"**

---

## Step 2: Deploy Backend on Render.com

1. Go to [Render.com](https://render.com) and sign up/login
2. Click **"New +"** → **"Web Service"**
3. Connect your GitHub repository
4. Select the repository: `Aakarsh092/url-shortener`
5. Configure the service:
   - **Name**: `url-shortener-backend`
   - **Root Directory**: `backend`
   - **Environment**: `Java`
   - **Build Command**: `./mvnw clean package -DskipTests` (or `mvn clean package -DskipTests`)
   - **Start Command**: `java -jar target/url-shortener-backend-1.0.0.jar`
6. Click **"Advanced"** and add Environment Variables:
   - `MONGODB_URI`: Paste your MongoDB Atlas connection string
     ```
     mongodb+srv://username:password@cluster0.xxxxx.mongodb.net/urlshortener?retryWrites=true&w=majority
     ```
   - `APP_BASE_URL`: Will be auto-set by Render (you can also set it manually to your Render URL)
7. Click **"Create Web Service"**
8. Wait for deployment to complete (5-10 minutes)
9. **Copy your Render backend URL** (e.g., `https://url-shortener-backend.onrender.com`)

**Note**: Render free tier services spin down after 15 minutes of inactivity. The first request after spin-down may take 30-60 seconds.

---

## Step 3: Deploy Frontend on Vercel

1. Go to [Vercel.com](https://vercel.com) and sign up/login with GitHub
2. Click **"Add New Project"**
3. Import your GitHub repository: `Aakarsh092/url-shortener`
4. Configure the project:
   - **Framework Preset**: React
   - **Root Directory**: `frontend`
   - **Build Command**: `npm run build` (should auto-detect)
   - **Output Directory**: `build`
5. Add Environment Variable:
   - **Key**: `REACT_APP_API_URL`
   - **Value**: Your Render backend URL + `/api` (e.g., `https://url-shortener-backend.onrender.com/api`)
6. Click **"Deploy"**
7. Wait for deployment (2-3 minutes)
8. **Copy your Vercel frontend URL** (e.g., `https://url-shortener.vercel.app`)

---

## Step 4: Update Backend CORS (if needed)

If you encounter CORS errors, you may need to update the frontend URL in your backend:

1. Go to Render dashboard → Your backend service
2. Go to **Environment** tab
3. Add/Update environment variable:
   - **Key**: `FRONTEND_URL`
   - **Value**: Your Vercel frontend URL (e.g., `https://url-shortener.vercel.app`)
4. Redeploy the service

---

## Step 5: Update Short URL Generation

The backend needs to know the frontend URL for generating short URLs. Update the `APP_BASE_URL` in Render:

1. Go to Render dashboard → Your backend service
2. Go to **Environment** tab
3. Update `APP_BASE_URL` to your Render backend URL:
   ```
   https://url-shortener-backend.onrender.com
   ```
4. Redeploy if needed

---

## Testing Your Deployment

1. Visit your Vercel frontend URL
2. Try shortening a URL
3. Click on the shortened URL to test redirection
4. Check analytics

---

## Troubleshooting

### Backend Issues

- **Build fails**: Check that Maven wrapper exists in `backend/` directory
- **Connection refused**: Verify MongoDB Atlas network access allows all IPs
- **CORS errors**: Ensure CORS config allows your Vercel domain

### Frontend Issues

- **API calls fail**: Verify `REACT_APP_API_URL` is set correctly in Vercel
- **Build fails**: Check that all dependencies are in `package.json`

### Database Issues

- **Connection timeout**: Check MongoDB Atlas network access settings
- **Authentication failed**: Verify username/password in connection string

---

## Free Tier Limitations

### Render.com
- Services spin down after 15 minutes of inactivity
- First request after spin-down takes 30-60 seconds
- 750 hours/month free (enough for always-on service)

### Vercel
- Unlimited deployments
- 100GB bandwidth/month
- Perfect for React apps

### MongoDB Atlas
- 512MB storage (free forever)
- Shared cluster (may have occasional slowdowns)
- Perfect for development and small projects

---

## Alternative: Railway.app (Backend)

If Render doesn't work well, you can use Railway.app:

1. Go to [Railway.app](https://railway.app)
2. Sign up with GitHub
3. Create new project → Deploy from GitHub
4. Select your repository
5. Set root directory to `backend`
6. Add environment variables (same as Render)
7. Railway auto-detects Java and builds automatically

---

## Cost

**Total Cost: $0/month** - Everything is free! 🎉

---

## Need Help?

- Render Docs: https://render.com/docs
- Vercel Docs: https://vercel.com/docs
- MongoDB Atlas Docs: https://docs.atlas.mongodb.com

