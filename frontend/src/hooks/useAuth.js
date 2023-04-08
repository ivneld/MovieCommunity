import useSWR from "swr";
import axios from "../utils/axios";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

export default function useAuth({ middleware, redirectIfAuthenticated } = {}) {
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(true);

  const {
    data: user,
    error,
    mutate,
  } = useSWR("/users/user", (key) => {
    return axios
      .get(key, {})
      .then((res) => {
        return res.data.data;
      })
      .catch((error) => {});
  });

  const csrf = () => axios.get("/auth/csrf-token");

  const login = async ({ setErrors, ...props }) => {
    setErrors([]);

    await csrf();

    axios
      .post("/auth/login", props)
      .then(() => mutate() && navigate("/"))
      .catch((error) => {
        if (![401, 422].includes(error.response.status)) throw error;
        if (error.response.status == 401)
          setErrors(error.response.data.message);
        if (error.response.status == 422)
          setErrors(Object.values(error.response.data.errors).flat());
      });
  };

  const signup = async ({ setErrors, ...props }) => {
    setErrors([]);

    await csrf();

    axios
      .post("/auth/signup", props)
      .then(() => mutate() && navigate("/login"))
      .catch((error) => {
        if (![401, 422, 409].includes(error.response.status)) throw error;
        if ([401, 409].includes(error.response.status))
          setErrors(error.response.data.message);
        if (error.response.status == 422)
          setErrors(Object.values(error.response.data.errors).flat());
      });
  };

  const logout = async () => {
    if (!error) {
      await axios.delete("/auth/logout");
      mutate();
    }
    navigate("/login");
  };

  useEffect(() => {
    if (user || error || !user) setIsLoading(false);

    if (middleware == "guest" && redirectIfAuthenticated && user)
      navigate(redirectIfAuthenticated);
    if (middleware == "auth" && !user && !error) navigate("/login");
    if (middleware == "auth" && !user && error) logout();
  }, [user, error]);

  return {
    user,
    csrf,
    login,
    logout,
    isLoading,
    signup,
  };
}
