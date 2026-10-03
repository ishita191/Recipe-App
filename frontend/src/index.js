import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

localStorage.removeItem('token');
localStorage.removeItem('user');

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<React.StrictMode><App /></React.StrictMode>);
