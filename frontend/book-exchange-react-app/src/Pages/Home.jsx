import { useState, useEffect, useMemo, useContext } from "react";
import "./Home.css";
import BookContext, { getHTML, getLocations } from "../Contexts/BookContext";
import TransactionContext from "../Contexts/TransactionContext";
import AuthContext from "../Contexts/AuthContext";

export default function Home() {
  const { token, tokenUser, signInOrSignUp, getUserByToken, logOut } =
    useContext(AuthContext);
  const { books, getBooks } = useContext(BookContext);
  const { uploadTransaction } = useContext(TransactionContext);

  const [query, setQuery] = useState("");
  const [location, setLocation] = useState("");
  const [available, setAvailable] = useState("");

  useEffect(() => {
    getBooks(location, available);
  }, [location, available]);

  return (
    <main className="main-container">
      <h1 className="main-title">Поиск книги</h1>

      <section className="search-bar">
        <select
          className="search-select"
          value={location}
          onChange={(e) => setLocation(e.target.value)}
        >
          <option value={""}>Все места</option>
          {getLocations().map((location) => (
            <option key={location} value={location}>
              {location}
            </option>
          ))}
        </select>
        <select
          className="search-select"
          value={available}
          onChange={(e) => setAvailable(e.target.value)}
        >
          <option value={""}>Все книги</option>
          <option value={true}>Только доступные</option>
        </select>
      </section>
      {getHTML(books, async (book) => {
        await uploadTransaction({
          userId: tokenUser.id,
          bookId: book.id,
          borrowed: true,
        });
        await getBooks(location, available);
      })}
    </main>
  );
}
