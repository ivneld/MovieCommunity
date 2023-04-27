import { Link } from "react-router-dom"
import useAuth from "../../hooks/useAuth";

export default function Navbar() {
    const {user, logout, isLoading} = useAuth();

    if(isLoading)
        return <p>Loading...</p>

    return (
        <div>
            <ul>
                <li>
                    <Link to="/">Home</Link>
                </li>
                {user ? (
                    <>
                        <li>
                            <Link to="/profile">Profile</Link>
                        </li>
                        <li>
                            <button onClick={logout}>Logout</button>
                        </li>
                    </>
                ) : (
                    <>
                        <li>
                            <Link to="/login">Login</Link>
                        </li>
                        <li>
                            <Link to="/signup">Signup</Link>
                        </li>
                    </>
                )}
            </ul>

            <br /><br /><br />
TEST
            <li>
                            <Link to="/login">Login</Link>
                        </li>
                        <li>
                            <Link to="/signup">Signup</Link>
                        </li>
                        <li>
                            <Link to="/profile">Profile</Link>
                        </li>
        </div>
    )
}