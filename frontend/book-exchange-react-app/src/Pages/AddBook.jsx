import { useContext, useState } from "react";
import "./Auth.css";
import "./window.css";
import BookContext, {
  getLocations,
  setAuthor,
  setLocation,
  setTitle,
  useAuthorState,
  useLocationState,
  useTitleState,
} from "../Contexts/BookContext";

function AddBook() {
  const { books, getBooks, uploadBook, getBooksByBorrowBy } =
    useContext(BookContext);

  const [title, _setTitle] = useTitleState();
  const [author, _setAuthor] = useAuthorState();
  const [location, _setLocation] = useLocationState();

  const [err, setErr] = useState("");

  const continueButton = async () => {
    const err = await uploadBook({
      title: title,
      author: author,
      location: location,
    });
    if (err.length !== 0) {
      setErr("Ошибка сервера");
      return;
    }
    setErr("Удачно добавлена");
  };

  return (
    <div className="window" id="auth-window">
      <h1>Добавить книгу</h1>
      <input
        type="text"
        placeholder="Название"
        onChange={(e) => setTitle(_setTitle, e.target.value)}
        value={title}
      />
      <input
        type="text"
        placeholder="Автор"
        onChange={(e) => setAuthor(_setAuthor, e.target.value)}
        value={author}
      />
      <select
        className="search-select"
        value={location}
        onChange={(e) => setLocation(_setLocation, e.target.value)}
      >
        {getLocations().map((location) => (
          <option key={location} value={location}>
            {location}
          </option>
        ))}
      </select>
      <p>{err}</p>
      <button onClick={() => continueButton()}>Добавить</button>
    </div>
  );
}

export default AddBook;
