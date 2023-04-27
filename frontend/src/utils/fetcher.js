import axios from "axios";

const fetcher = (url) =>
    axios
        .get(url, {
            withCredentials: false
        })
        .then((res) => res.data);

export default fetcher;