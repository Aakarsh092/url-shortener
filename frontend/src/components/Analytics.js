import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Analytics.css';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

function Analytics({ shortCode }) {
  const [analytics, setAnalytics] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (shortCode) {
      fetchAnalytics();
      // Refresh analytics every 5 seconds
      const interval = setInterval(fetchAnalytics, 5000);
      return () => clearInterval(interval);
    }
  }, [shortCode]);

  const fetchAnalytics = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/analytics/${shortCode}`);
      setAnalytics(response.data);
      setError('');
    } catch (err) {
      setError('Failed to load analytics');
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleString();
  };

  if (!shortCode) return null;

  return (
    <div className="analytics-container">
      <h2 className="analytics-title">📊 Analytics</h2>
      
      {loading && !analytics ? (
        <div className="loading">Loading analytics...</div>
      ) : error ? (
        <div className="error-message">{error}</div>
      ) : analytics ? (
        <div className="analytics-content">
          <div className="stat-card">
            <div className="stat-value">{analytics.clickCount || 0}</div>
            <div className="stat-label">Total Clicks</div>
          </div>
          
          <div className="info-section">
            <div className="info-item">
              <span className="info-label">Original URL:</span>
              <a 
                href={analytics.originalUrl} 
                target="_blank" 
                rel="noopener noreferrer"
                className="info-value-link"
              >
                {analytics.originalUrl}
              </a>
            </div>
            
            <div className="info-item">
              <span className="info-label">Short Code:</span>
              <span className="info-value">{analytics.shortCode}</span>
            </div>
            
            <div className="info-item">
              <span className="info-label">Created:</span>
              <span className="info-value">{formatDate(analytics.createdAt)}</span>
            </div>
            
            <div className="info-item">
              <span className="info-label">Last Accessed:</span>
              <span className="info-value">{formatDate(analytics.lastAccessedAt)}</span>
            </div>
          </div>
        </div>
      ) : null}
    </div>
  );
}

export default Analytics;

