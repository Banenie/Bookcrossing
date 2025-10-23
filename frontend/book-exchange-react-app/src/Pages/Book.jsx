import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AuthContext from "../Contexts/AuthContext";
import "./window.css";
import "./Book.css";
import BookContext, { getHTMLBook } from "../Contexts/BookContext";
import CommentContext, {
  setText,
  setTitle,
  useTextState,
  useTitleState,
} from "../Contexts/CommentContext";

function Book() {
  const navigate = useNavigate();

  const { id } = useParams();

  const { token, tokenUser, signInOrSignUp, logOut } = useContext(AuthContext);
  const { books, getBooks, uploadBook, getBookById, getBooksByBorrowBy } =
    useContext(BookContext);
  const { comments, uploadComment, getCommentsByBookId } =
    useContext(CommentContext);

  const [title, _setTitle] = useTitleState();
  const [text, _setText] = useTextState();

  useEffect(() => {
    getBookById(id);
    getCommentsByBookId(id);
  }, [token]);

  return (
    <div className="window" id="book-window">
      {getHTMLBook(books[0], navigate)}
      <h3>Оставить отзыв</h3>
      <div>
        <input
          className="book-input"
          placeholder="Введите название отзыва"
          onChange={(e) => setTitle(_setTitle, e.target.value)}
          value={title}
        />
        <input
          className="book-input"
          placeholder="Введите отзыв"
          onChange={(e) => setText(_setText, e.target.value)}
          value={text}
        />
        <button
          onClick={async () => {
            await uploadComment({
              userId: tokenUser.id,
              bookId: id,
              title: title,
              text: text,
            });
            await getCommentsByBookId(id);
          }}
        >
          Отправить отзыв
        </button>
      </div>
      <h1>Отзывы</h1>
      <div>
        {comments.map((c) => (
          <div key={c.id}>
            <h3>{c.title}</h3>
            <p>{c.text}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Book;
