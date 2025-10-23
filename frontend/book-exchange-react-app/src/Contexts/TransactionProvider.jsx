import { useContext, useEffect, useState } from "react";
import TransactionContext from "./TransactionContext";
import AuthContext from "./AuthContext";

function TransactionProvider({ children }) {
  const { token, tokenUser, signInOrSignUp, logOut } = useContext(AuthContext);

  const [transactions, setTransactions] = useState([]);

  const getTransactions = async () => {
    if (tokenUser == null || !tokenUser.admin) {
      return "";
    }

    const response = await fetch("http://localhost:8080/transactions", {
      method: "GET",
      headers: { Authorization: token.token },
    });

    if (!response.ok) {
      return await response.text();
    }

    let transactions = await response.json();
    transactions.sort((a, b) => a.id - b.id);
    setTransactions(transactions);

    return "";
  };

  const uploadTransaction = async (transaction) => {
    if (token == null) {
      return "";
    }

    const response = await fetch("http://localhost:8080/transactions", {
      method: "POST",
      body: JSON.stringify(transaction),
      headers: {
        Authorization: token.token,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      return await response.text();
    }

    getTransactions();

    return "";
  };

  useEffect(() => {
    getTransactions();
  }, [tokenUser]);

  return (
    <TransactionContext.Provider value={{ transactions, uploadTransaction }}>
      {children}
    </TransactionContext.Provider>
  );
}

export default TransactionProvider;
