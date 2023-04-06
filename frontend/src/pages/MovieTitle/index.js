import React from 'react';
import { useLocation } from 'react-router-dom';

const MovieTitle = () => {
    const location = useLocation();
    const title = location.state.title;
    console.log(title)
    return(
        <>
            {title}
        </>
    )
};

export default MovieTitle;

