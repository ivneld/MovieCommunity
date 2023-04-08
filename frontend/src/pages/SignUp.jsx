import { useState } from "react";
import useAuth from "../hooks/useAuth";

export default function SignUp() {
    const [errors,setErrors] = useState([]);
    const {signup, isLoading, user} = useAuth({middleware: "guest", redirectIfAuthenticated: "/"});

    const handleSubmit = async (e) => {
        e.preventDefault();
        const {username, password} = e.target;
        signup({username: username.value, password:password.value, setErrors});
    }

    if(isLoading || user)
        return <p>Loading...</p>

    return (
        <>
        <h1>SignUp</h1>

        {typeof errors == "string" ? (
            <p style={{color: 'red'}}>{errors}</p>
        ) : (
            <ul style={{color: 'red'}}>
                {errors.map((error,i) => {
                    return (
                        <li key={i}>{error}</li>
                    )
                })}
            </ul>
        )}

        <form onSubmit={handleSubmit} method="post">

        <input type="text" name="username"/>
        <br />
        <input type="password" name="password"/>
        <br />

        <button>SignUp</button>
        </form>
        </>
    )
}