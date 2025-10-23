import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext, { useLoginState, setLogin } from "../Contexts/AuthContext";
import "./Auth.css";
import "./window.css";

function Auth() {
  const { token, signInOrSignUp, logOut } = useContext(AuthContext);
  const navigate = useNavigate();

  const [login, _setLogin] = useLoginState();
  const [password, setPassword] = useState("");
  const [err, setErr] = useState("");

  const continueButton = async () => {
    const err = await signInOrSignUp(login, password);
    if (err.length !== 0) {
      setErr("Учетные данные введены некорректно");
      return;
    }
    navigate("/home");
  };

  return (
    <div className="window" id="auth-window">
      <h1>Добро пожаловать на сайт по обмену книгами</h1>
      <p>Здесь ты можешь безвозмездно обменять любую свою книгу.</p>
      <label>
        Зарегистрируйся или войди в аккаунт, чтобы поскорее продолжить!
      </label>
      <input
        type="text"
        placeholder="Ваш логин"
        onChange={(e) => setLogin(_setLogin, e.target.value)}
        value={login}
      />
      <input
        type="text"
        placeholder="Ваш пароль"
        onChange={(e) => setPassword(e.target.value)}
        value={password}
      />
      <p>{err}</p>
      <button onClick={() => continueButton()}>Продолжить</button>
      <p id="copyright">
        © 1989 Во время создания сайта ни одна книга не пострадала.
      </p>
    </div>
  );
}

export default Auth;
