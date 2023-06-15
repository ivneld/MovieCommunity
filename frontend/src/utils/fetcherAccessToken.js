import axios from "axios";

const fetcherAccessToken = (url) => {
  const accessToken = localStorage.getItem('accessToken');
  return axios
    .get(url, { headers: { Authorization: `Bearer ${accessToken}` } })
    .then((res) => res.data);
};

export default fetcherAccessToken;