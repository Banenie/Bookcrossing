import { useState, createContext } from "react";
import { useNavigate } from "react-router";

export const CommentContext = createContext(null);

export function useTitleState() {
  let title = localStorage.getItem("comment-title");

  if (title === null) {
    title = "";
  }

  return useState(title);
}

export function useTextState() {
  let text = localStorage.getItem("comment-text");

  if (text === null) {
    text = "";
  }

  return useState(text);
}

export function setTitle(_setTitle, title) {
  _setTitle(title);
  localStorage.setItem("comment-title", title);
}

export function setText(_setText, text) {
  _setText(text);
  localStorage.setItem("comment-text", text);
}

export default CommentContext;
