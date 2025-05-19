const express = require('express');
const { Pool } = require('pg');
const app = express();
const port = process.env.PORT || 5500;

// PostgreSQL
const pool = new Pool({
  user: '',
  host: 'localhost', 
  database: '',
  password: '',
  port: 5432,
});

app.use(express.json());
app.use(cors());

// Роутим к данным
app.get('/api/data', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM '); // запрос к бд
    res.json(result.rows);
  } catch (err) {
    console.error(err.message);
    res.status(500).send('Ошибка сервера');
  }
});

app.listen(port, () => {
  console.log(`Сервер: http://localhost:${port}`);
});