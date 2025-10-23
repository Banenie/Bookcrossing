import { useState, createContext } from "react";
import { useNavigate } from "react-router";

export const BookContext = createContext(null);

export function useTitleState() {
  let title = localStorage.getItem("title");

  if (title === null) {
    title = "";
  }

  return useState(title);
}

export function useAuthorState() {
  let author = localStorage.getItem("author");

  if (author === null) {
    author = "";
  }

  return useState(author);
}

export function useLocationState() {
  let location = localStorage.getItem("location");

  if (location === null) {
    location = "Офис";
  }

  return useState(location);
}

export function setTitle(_setTitle, title) {
  _setTitle(title);
  localStorage.setItem("title", title);
}

export function setAuthor(_setAuthor, author) {
  _setAuthor(author);
  localStorage.setItem("author", author);
}

export function setLocation(_setLocation, location) {
  _setLocation(location);
  localStorage.setItem("location", location);
}

export function getLocations() {
  return ["Офис", "Школа", "Кампус"];
}

export function getHTMLBook(b, navigate, borrowBook = null, returnBook = null) {
  if (b == undefined) {
    return <></>;
  }
  return (
    <>
      <h2>{b.title}</h2>
      <p>{b.author}</p>
      {b.location != null && <p>{b.location}</p>}
      {borrowBook != null && (
        <p>{b.available ? "✔️ Доступно" : "✖️ Не доступно"}</p>
      )}
      {b.available && borrowBook != null && (
        <button
          onClick={(e) => {
            e.stopPropagation();
            borrowBook(b);
          }}
        >
          Забрать книгу
        </button>
      )}
      {returnBook != null && (
        <button
          onClick={(e) => {
            e.stopPropagation();
            returnBook(b);
          }}
        >
          Вернуть книгу
        </button>
      )}
    </>
  );
}

export function getHTML(books, borrowBook = null, returnBook = null) {
  const navigate = useNavigate();

  return (
    <section className="cards-grid">
      {" "}
      {books.map((b) => (
        <article
          key={b.id}
          className="card"
          onClick={() => navigate("/books/" + b.id)}
        >
          {getHTMLBook(b, navigate, borrowBook, returnBook)}
        </article>
      ))}
    </section>
  );
}

export default BookContext;
