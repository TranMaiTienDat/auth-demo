import express from "express";
import dotenv from "dotenv";
import cors from "cors";
import authRoutes from "./auth.js";
import { requireAuth } from "./auth.middleware.js";
import { initDatabase } from "./db.js";

dotenv.config();
const app = express();

// Khởi tạo database
initDatabase();

app.use(cors());           // mở CORS cho dev
app.use(express.json());   // parse JSON body

// route test
app.get("/", (req, res) => res.send("Node.js API is running 🚀"));

// gắn route auth
app.use("/auth", authRoutes);

// ví dụ route cần login: GET /profile
app.get("/profile", requireAuth, (req, res) => {
  res.json({ message: "Protected route", user: req.user });
});

const port = process.env.PORT || 4000;
app.listen(port, () => console.log(`Server running on http://localhost:${port}`));
