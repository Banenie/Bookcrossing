import { useEffect, useState } from "react";
import AuthContext from "./AuthContext";

function AuthProvider({ children }) {
  const token_ = localStorage.getItem("token");

  let savedToken = null;
  if (token_ !== null) {
    savedToken = { token: token_ };
  }

  const [token, setToken] = useState(savedToken);
  const [tokenUser, setTokenUser] = useState(null);

  const setInfo = (token) => {
    setToken({ token: token });
    localStorage.setItem("token", token);
  };

  const signInOrSignUp = async (login, password) => {
    const response = await fetch("http://localhost:8080/auth/token", {
      method: "POST",
      body: JSON.stringify({ login: login, password: password }),
      headers: { "Content-Type": "application/json" },
    });

    if (!response.ok) {
      return await response.text();
    }

    const token = await response.text();
    setInfo(token);

    return "";
  };

  const getUserByToken = async () => {
    if (token == null) {
      return "";
    }

    const response = await fetch("http://localhost:8080/auth/token", {
      method: "GET",
      headers: { Authorization: token.token },
    });

    if (!response.ok) {
      return await response.text();
    }

    const tokenUser = await response.json();
    setTokenUser(tokenUser);

    return "";
  };

  const logOut = () => {
    setToken(null);
    setTokenUser(null);
    localStorage.removeItem("token");
  };

  useEffect(() => {
    getUserByToken();
  }, [token]);

  return (
    <AuthContext.Provider
      value={{ token, tokenUser, signInOrSignUp, getUserByToken, logOut }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export default AuthProvider;
