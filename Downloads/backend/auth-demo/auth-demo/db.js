// db.js - Database configuration with SQLite
import sqlite3 from 'sqlite3';
import { open } from 'sqlite';
import { fileURLToPath } from 'url';
import { dirname, join } from 'path';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

let db = null;

// Khởi tạo database
async function initDatabase() {
  try {
    db = await open({
      filename: join(__dirname, 'auth_demo.db'),
      driver: sqlite3.Database
    });

    // Tạo bảng users nếu chưa tồn tại
    await db.exec(`
      CREATE TABLE IF NOT EXISTS users (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        username TEXT UNIQUE NOT NULL,
        email TEXT UNIQUE NOT NULL,
        password_hash TEXT NOT NULL,
        created_at DATETIME DEFAULT CURRENT_TIMESTAMP
      )
    `);

    console.log('SQLite Database initialized successfully');
  } catch (error) {
    console.error('Database initialization error:', error);
  }
}

// Xuất db instance
export { db, initDatabase };
