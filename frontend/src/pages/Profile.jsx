import useAuth from "../hooks/useAuth";

export default function Profile() {
    const {isLoading, user} = useAuth({middleware: "auth"});

    if(isLoading || !user)
        return <p>Loading...</p>

    return (
        <>
        <h1>My Profile</h1>
        <p>ID : {user.id}</p>
        <p>Username : {user.username}</p>
        </>
    )
}