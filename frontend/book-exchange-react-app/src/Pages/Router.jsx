import { useContext } from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router";
import AuthContext from "../Contexts/AuthContext.jsx";
import Auth from "./Auth.jsx";
import Home from "./Home.jsx";
import "./Nav.css";
import MyBooks from "./MyBooks.jsx";
import AddBook from "./AddBook.jsx";
import Book from "./Book.jsx";
import Transactions from "./Transactions.jsx";

function Router() {
  const { token, tokenUser, signInOrSignUp, logOut } = useContext(AuthContext);

  return (
    <BrowserRouter>
      <nav>
        <div id="first-section-nav">
          <Link to="/home">Главная</Link>
          <Link to="/myBooks">Мои книги</Link>
          <Link to="/addBook">Добавить книгу</Link>
          {tokenUser !== null && tokenUser.admin && (
            <Link to="/transactions">Обмены</Link>
          )}
        </div>
        {token !== null && <button onClick={logOut}>Выйти из аккаунта</button>}
      </nav>
      <Routes>
        <Route path="/" element={token === null ? <Auth /> : <Home />} />
        <Route path="/home" element={token === null ? <Auth /> : <Home />} />
        <Route
          path="/myBooks"
          element={token === null ? <Auth /> : <MyBooks />}
        />
        <Route
          path="/addBook"
          element={token === null ? <Auth /> : <AddBook />}
        />
        <Route
          path="/books/:id"
          element={token === null ? <Auth /> : <Book />}
        />
        <Route
          path="/transactions"
          element={token === null ? <Auth /> : <Transactions />}
        />
      </Routes>
    </BrowserRouter>
  );
}

export default Router;
