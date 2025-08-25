// auth.js
import express from "express";
import bcrypt from "bcryptjs";
import jwt from "jsonwebtoken";
import { db } from "./db.js";

const router = express.Router();

// Helper: tạo JWT
function signAccessToken(payload) {
  return jwt.sign(payload, process.env.JWT_SECRET, { expiresIn: "1h" });
}

// POST /auth/register
router.post("/register", async (req, res) => {
  const { username, password, email } = req.body;

  if (!username || !password || !email)
    return res.status(400).json({ error: "username, password & email are required" });

  try {
    // Kiểm tra user đã tồn tại chưa
    const existingUser = await db.get(
      'SELECT id FROM users WHERE username = ? OR email = ?', 
      [username, email]
    );

    if (existingUser) {
      return res.status(409).json({ error: "User already exists" });
    }

    // Hash password và lưu user mới
    const passwordHash = await bcrypt.hash(password, 10);
    await db.run(
      'INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)',
      [username, email, passwordHash]
    );

    const token = signAccessToken({ sub: username });
    return res.status(201).json({ 
      message: "Registered successfully", 
      token,
      user: { username, email } 
    });
  } catch (error) {
    console.error('Registration error:', error);
    return res.status(500).json({ error: "Registration failed" });
  }
});

// POST /auth/login
router.post("/login", async (req, res) => {
  const { username, password } = req.body;

  try {
    // Tìm user trong database
    const user = await db.get(
      'SELECT username, email, password_hash FROM users WHERE username = ?', 
      [username]
    );

    if (!user) {
      return res.status(400).json({ error: "User not found" });
    }

    const passwordMatch = await bcrypt.compare(password, user.password_hash);
    
    if (!passwordMatch) {
      return res.status(401).json({ error: "Invalid credentials" });
    }

    const token = signAccessToken({ sub: username });
    return res.json({ 
      token, 
      user: { username: user.username, email: user.email } 
    });
  } catch (error) {
    console.error('Login error:', error);
    return res.status(500).json({ error: "Login failed" });
  }
});

export default router;
