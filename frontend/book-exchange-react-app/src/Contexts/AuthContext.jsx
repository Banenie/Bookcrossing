import { useState, createContext } from "react";

export const AuthContext = createContext(null);

export function useLoginState() {
  let login = localStorage.getItem("login");

  if (login === null) {
    login = "";
  }

  return useState(login);
}

export function setLogin(_setLogin, login) {
  _setLogin(login);
  localStorage.setItem("login", login);
}

export default AuthContext;
