# Auth Demo - Full Stack Authentication System

A complete authentication system built with Node.js/Express backend and React frontend, featuring JWT authentication and SQLite database integration.

## 🚀 Features

- **User Registration & Login** - Complete authentication flow
- **JWT Token Authentication** - Secure token-based authentication
- **Password Encryption** - Bcrypt hashing for secure password storage
- **SQLite Database** - Lightweight database for user management
- **React Frontend** - Modern UI with authentication context
- **Protected Routes** - Route protection based on authentication status
- **Responsive Design** - Mobile-friendly interface

## 📁 Project Structure

```
auth-demo/
├── auth-demo/                 # Backend (Node.js/Express)
│   ├── server.js             # Main server file
│   ├── auth.js               # Authentication routes
│   ├── auth.middleware.js    # JWT middleware
│   ├── db.js                 # SQLite database configuration
│   ├── auth_demo.db          # SQLite database file
│   └── package.json          # Backend dependencies
├── figma_UI/figma_UI/        # Frontend (React)
│   ├── src/
│   │   ├── components/       # React components (Login, Register, Home, etc.)
│   │   ├── contexts/         # Authentication context
│   │   ├── services/         # API services
│   │   └── App.js           # Main app component
│   ├── public/
│   └── package.json         # Frontend dependencies
└── README.md                # Project documentation
```

## 🛠️ Installation & Setup

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn

### Backend Setup
1. Navigate to backend directory:
   ```bash
   cd auth-demo
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the backend server:
   ```bash
   npm start
   ```
   Server will run on `http://localhost:4000`

### Frontend Setup
1. Navigate to frontend directory:
   ```bash
   cd figma_UI/figma_UI
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the frontend development server:
   ```bash
   npm start
   ```
   Frontend will run on `http://localhost:3002`

## 🔧 Technologies Used

### Backend
- **Node.js** - Runtime environment
- **Express.js** - Web framework
- **SQLite** - Database
- **bcryptjs** - Password hashing
- **jsonwebtoken** - JWT authentication
- **cors** - Cross-origin resource sharing

### Frontend
- **React** - Frontend framework
- **React Router** - Client-side routing
- **styled-components** - CSS-in-JS styling
- **Context API** - State management
- **localStorage** - Token persistence

## 📡 API Endpoints

### Authentication Routes
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Protected Routes
- `GET /api/profile` - Get user profile (requires authentication)

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT UNIQUE NOT NULL,
  email TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

## 🔐 Authentication Flow

1. **Registration**: User creates account with username, email, and password
2. **Password Hashing**: Password is encrypted using bcrypt
3. **User Storage**: User data stored in SQLite database
4. **Login**: User authenticates with credentials
5. **JWT Generation**: Server generates JWT token upon successful login
6. **Token Storage**: Frontend stores JWT in localStorage
7. **Protected Access**: JWT token used for accessing protected routes

## 🎨 Frontend Components

- **Login Component** - User authentication form
- **Register Component** - User registration form
- **Home Component** - Main dashboard (protected)
- **Header Component** - Navigation with auth status
- **ArticleDetail Component** - Content display (protected)
- **ForgotPassword Component** - Password recovery

## 🚀 Usage

1. Start both backend and frontend servers
2. Open `http://localhost:3002` in your browser
3. Register a new account or login with existing credentials
4. Navigate through protected routes after authentication
5. Logout to clear authentication state

## 🔒 Security Features

- Password encryption with bcrypt
- JWT token-based authentication
- Protected route middleware
- CORS configuration
- Input validation
- Secure token storage

## 📝 Environment Configuration

### Backend (.env - optional)
```
PORT=4000
JWT_SECRET=your_jwt_secret_key
DB_PATH=./auth_demo.db
```

### Frontend (proxy in package.json)
```json
"proxy": "http://localhost:4000"
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

**Tran Mai Tien Dat**
- GitHub: [@TranMaiTienDat](https://github.com/TranMaiTienDat)
- Repository: [auth-demo](https://github.com/TranMaiTienDat/auth-demo)

## 🙏 Acknowledgments

- React community for excellent documentation
- Express.js for robust backend framework
- SQLite for lightweight database solution
- JWT for secure authentication standard

---

⭐ If you find this project helpful, please consider giving it a star!
