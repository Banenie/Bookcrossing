import { useState, useEffect, useMemo, useContext } from "react";
import "./Home.css";
import BookContext, { getHTML, getLocations } from "../Contexts/BookContext";
import AuthContext from "../Contexts/AuthContext";
import TransactionContext from "../Contexts/TransactionContext";

export default function MyBooks() {
  const { token, tokenUser, signInOrSignUp, getUserByToken, logOut } =
    useContext(AuthContext);
  const { books, getBooks, getBooksByBorrowBy } = useContext(BookContext);
  const { uploadTransaction } = useContext(TransactionContext);

  const [bookId, setBookId] = useState(null);
  const [location, setLocation] = useState("Офис");

  useEffect(() => {
    if (tokenUser == null) {
      return;
    }

    getBooksByBorrowBy(tokenUser.id);
  }, [tokenUser]);

  return (
    <main className="main-container">
      {bookId != null && (
        <div>
          <select
            className="search-select"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          >
            {getLocations().map((location) => (
              <option key={location} value={location}>
                {location}
              </option>
            ))}
          </select>
          <button
            onClick={async () => {
              await uploadTransaction({
                userId: tokenUser.id,
                bookId: bookId,
                location: location,
                borrowed: false,
              });
              await getBooksByBorrowBy(tokenUser.id);
              setBookId(null);
            }}
          >
            Вернуть книгу
          </button>
        </div>
      )}
      <h1 className="main-title">Мои книги</h1>
      {getHTML(books, null, (book) => setBookId(book.id))}
    </main>
  );
}
