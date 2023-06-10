import { useEffect, useContext } from "react";
import { useNavigate } from "react-router";
import { AuthContext } from "../../context/AuthProvider";

function Logout() {

	const { auth, setAuth } = useContext(AuthContext);

	const navigate = useNavigate();
	
	const logout = () => {
		
		localStorage.removeItem("accessToken");
		localStorage.removeItem("email");

		console.log("성공적으로 로그아웃 되었습니다 🔒");
		setAuth(null);
		
		navigate("/");
	};

	useEffect(() => {
		logout();
	}, []);

}

export default Logout;