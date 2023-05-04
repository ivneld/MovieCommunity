import React from 'react';
import { ACCESS_TOKEN, REFRESH_TOKEN } from '../context/index';
import { useNavigate, useLocation } from 'react-router-dom';

const OAuth2RedirectHandler = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const getUrlParameter = (name) => {
    name = name.replace(/[\\[]/, '\\[').replace(/[\]]/, '\\]');
    const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');

    const results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
  };

  const token = getUrlParameter('token');
  const error = getUrlParameter('error');

  if (token) {
    localStorage.setItem(ACCESS_TOKEN, token);
    localStorage.setItem(REFRESH_TOKEN, null);
    navigate('/profile', {
      state: { from: location }
    });
  } else {
    navigate('/login', {
      state: {
        from: location,
        error: error
      }
    });
  }
}

export default OAuth2RedirectHandler;