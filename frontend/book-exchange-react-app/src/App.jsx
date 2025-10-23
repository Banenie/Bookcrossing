import "./App.css";
import AuthProvider from "./Contexts/AuthProvider";
import Router from "./Pages/Router";
import BookProvider from "./Contexts/BookProvider";
import TransactionProvider from "./Contexts/TransactionProvider";
import CommentProvider from "./Contexts/CommentProvider";

function App() {
  return (
    <AuthProvider>
      <BookProvider>
        <CommentProvider>
          <TransactionProvider>
            <Router />
          </TransactionProvider>
        </CommentProvider>
      </BookProvider>
    </AuthProvider>
  );
}

export default App;
