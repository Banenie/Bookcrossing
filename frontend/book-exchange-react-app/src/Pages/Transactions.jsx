import { useContext } from "react";
import TransactionContext from "../Contexts/TransactionContext";
import "./Transactions.css";

export default function Transactions() {
  const { transactions, uploadTransaction } = useContext(TransactionContext);

  return (
    <main className="main-container">
      <h1 className="main-title">Обмены</h1>
      <div>
        {transactions.map((t) => (
          <div className="transaction" key={t.id}>
            <p>ID пользователя: {t.userId}</p>
            <p>ID книги: {t.bookId}</p>
            <p>Место: {t.location}</p>
            <p>{t.borrowed ? "Забрал" : "Вернул"}</p>
          </div>
        ))}
      </div>
    </main>
  );
}
