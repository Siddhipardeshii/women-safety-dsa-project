const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const path = require('path');

const app = express();
const port = 3000;

app.use(bodyParser.urlencoded({ extended: false }));
app.use(express.json()); // ✅ Add this line
app.use(express.static('public'));



// Database connection
const db = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'root123',  // ← replace this with your real MySQL password
  database: 'farmventory'

});

db.connect(err => {
  if (err) {
    console.error('❌ MySQL connection failed:', err);
    return;
  }
  console.log('✅ Connected to MySQL');
});


// Register route
app.post('/register', (req, res) => {
  const { name, username, password, email, phone } = req.body;
  const sql = 'INSERT INTO users (name, username, password, email, phone) VALUES (?, ?, ?, ?, ?)';
  db.query(sql, [name, username, password, email, phone], (err, result) => {
    if (err) return res.send('Registration failed: ' + err.message);
    res.send('✅ Registered successfully!');
  });
});

// Login route
app.post('/login', (req, res) => {
  const { username, password } = req.body;
  const sql = 'SELECT * FROM users WHERE username = ? AND password = ?';
  db.query(sql, [username, password], (err, result) => {
    if (err) return res.send('Login failed');
    if (result.length > 0) res.send('✅ Login successful!');
    else res.send('❌ Invalid credentials');
  });
});

// Start the server
app.listen(port, () => {
  console.log('🚀 Server running on http://localhost:3000');
});