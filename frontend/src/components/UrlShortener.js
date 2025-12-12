import React, { useState } from 'react';
import axios from 'axios';
import './UrlShortener.css';

const API_BASE_URL = 'http://localhost:8080/api';

function UrlShortener({ onShorten }) {
  const [url, setUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');
  const [shortCode, setShortCode] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      // Validate URL format
      if (!url.trim()) {
        setError('Please enter a URL');
        setLoading(false);
        return;
      }

      // Ensure URL has protocol
      let urlToShorten = url.trim();
      if (!urlToShorten.startsWith('http://') && !urlToShorten.startsWith('https://')) {
        urlToShorten = 'https://' + urlToShorten;
      }

      const response = await axios.post(`${API_BASE_URL}/shorten`, {
        originalUrl: urlToShorten
      });

      setShortUrl(response.data.shortUrl);
      setShortCode(response.data.shortCode);
      onShorten(response.data.shortCode);
      setUrl('');
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to shorten URL. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const copyToClipboard = () => {
    navigator.clipboard.writeText(shortUrl).then(() => {
      alert('URL copied to clipboard!');
    });
  };

  return (
    <div className="url-shortener">
      <form onSubmit={handleSubmit} className="url-form">
        <div className="input-group">
          <input
            type="text"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            placeholder="Enter your long URL here..."
            className="url-input"
            disabled={loading}
          />
          <button 
            type="submit" 
            className="shorten-btn"
            disabled={loading}
          >
            {loading ? 'Shortening...' : 'Shorten'}
          </button>
        </div>
        {error && <div className="error-message">{error}</div>}
      </form>

      {shortUrl && (
        <div className="result-container">
          <div className="result-label">Your shortened URL:</div>
          <div className="result-box">
            <a 
              href={shortUrl} 
              target="_blank" 
              rel="noopener noreferrer"
              className="short-url"
            >
              {shortUrl}
            </a>
            <button onClick={copyToClipboard} className="copy-btn">
              📋 Copy
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default UrlShortener;

