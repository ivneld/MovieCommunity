import { createContext, useState, useEffect } from "react";

export const AuthContext = createContext();

function AuthProvider({ children }) {

	const [auth, setAuth] = useState(localStorage.getItem("name"));

	useEffect(() => {
		setAuth(localStorage.getItem("name"));
	  }, [setAuth]);

	const updateAuth = (newAuth) => {
		// auth 값을 업데이트하기 전에 localStorage에도 해당 값을 저장합니다.
		localStorage.setItem("name", newAuth);
		setAuth(newAuth);
	  };

  	const value = { auth, setAuth: updateAuth };

	return (
		<AuthContext.Provider value = {value}>
			{children}
		</AuthContext.Provider>
	);

}

export default AuthProvider;