import React, { useState } from 'react';
import './App.css';
import UrlShortener from './components/UrlShortener';
import Analytics from './components/Analytics';

function App() {
  const [shortCode, setShortCode] = useState(null);

  return (
    <div className="App">
      <div className="container">
        <header className="header">
          <h1>🔗 URL Shortener</h1>
          <p>Transform long URLs into short, shareable links</p>
        </header>
        
        <UrlShortener onShorten={setShortCode} />
        
        {shortCode && (
          <Analytics shortCode={shortCode} />
        )}
      </div>
    </div>
  );
}

export default App;

