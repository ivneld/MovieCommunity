import { useState } from 'react';
import useAuth from '../hooks/useAuth';

export default function Login() {
    const [errors,setErrors] = useState([]);
    const {login, isLoading, user} = useAuth({middleware: "guest", redirectIfAuthenticated: "/"});

    const handleSubmit = async (e) => {
        e.preventDefault();
        const {username, password, remember_me} = e.target;
        login({username: username.value, password:password.value, remember_me: remember_me.checked, setErrors});
    }

    if(isLoading || user)
        return <p>Loading...</p>

    return (
        <>
        <h1>Login</h1>

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
        <input type="checkbox" name="remember_me"/>

        <button>Login</button>
        </form>
        </>
    )
}