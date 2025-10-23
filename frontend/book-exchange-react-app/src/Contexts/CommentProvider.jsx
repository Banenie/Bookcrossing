import { useContext, useEffect, useState } from "react";
import AuthContext from "./AuthContext";
import CommentContext from "./CommentContext";

function CommentProvider({ children }) {
  const { token, signInOrSignUp, logOut } = useContext(AuthContext);

  const [comments, setComments] = useState([]);

  const uploadComment = async (comment) => {
    if (token == null) {
      return "";
    }

    const response = await fetch("http://localhost:8080/comments", {
      method: "POST",
      body: JSON.stringify(comment),
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

  const getCommentsByBookId = async (bookId) => {
    if (token == null) {
      return "";
    }

    const response = await fetch(
      "http://localhost:8080/comments/book/" + bookId,
      {
        method: "GET",
        headers: { Authorization: token.token },
      }
    );

    if (!response.ok) {
      return await response.text();
    }

    let comments = await response.json();
    comments.sort((a, b) => a.id - b.id);
    setComments(comments);

    return "";
  };

  return (
    <CommentContext.Provider
      value={{ comments, uploadComment, getCommentsByBookId }}
    >
      {children}
    </CommentContext.Provider>
  );
}

export default CommentProvider;
