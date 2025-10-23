import { useContext, useState } from "react";
import BookContext from "./BookContext";
import AuthContext from "./AuthContext";

function BookProvider({ children }) {
  const { token, signInOrSignUp, logOut } = useContext(AuthContext);

  const [books, setBooks] = useState([]);

  const getBooks = async (location, available) => {
    if (token == null) {
      return "";
    }

    let url = "http://localhost:8080/books";

    if (location.length !== 0) {
      url += "?location=" + location;
      if (available.length !== 0) {
        url += "&isAvailable=" + available;
      }
    } else {
      if (available.length !== 0) {
        url += "?isAvailable=" + available;
      }
    }

    const response = await fetch(url, {
      method: "GET",
      headers: { Authorization: token.token },
    });

    if (!response.ok) {
      return await response.text();
    }

    let books = await response.json();
    books.sort((a, b) => a.id - b.id);
    setBooks(books);

    return "";
  };

  const uploadBook = async (book) => {
    if (token == null) {
      return "";
    }

    const response = await fetch("http://localhost:8080/books", {
      method: "POST",
      body: JSON.stringify(book),
      headers: {
        Authorization: token.token,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      return await response.text();
    }

    return "";
  };

  const getBookById = async (id) => {
    if (token == null) {
      return "";
    }

    const response = await fetch("http://localhost:8080/books/" + id, {
      method: "GET",
      headers: { Authorization: token.token },
    });

    if (!response.ok) {
      return await response.text();
    }

    const book = await response.json();
    setBooks([book]);

    return "";
  };

  const getBooksByBorrowBy = async (id) => {
    if (token == null) {
      return "";
    }

    const response = await fetch("http://localhost:8080/books/borrowBy/" + id, {
      method: "GET",
      headers: { Authorization: token.token },
    });

    if (!response.ok) {
      return await response.text();
    }

    let books = await response.json();
    books.sort((a, b) => a.id - b.id);
    setBooks(books);

    return "";
  };

  return (
    <BookContext.Provider
      value={{ books, getBooks, uploadBook, getBookById, getBooksByBorrowBy }}
    >
      {children}
    </BookContext.Provider>
  );
}

export default BookProvider;
