import React, { useCallback, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import { Link } from 'react-router-dom';

const GenreMovies = () => {
    const location = useLocation();
    const genreId = location.state.genreId;
    const genreNm = location.state.genreNm;
    const count = location.state.count;
    const apiUrl = process.env.REACT_APP_API_URL;
    let x;
    switch (count) {
      case 1:
        x = 100;
        break;
      case 2:
        x = 50;
        break;
      case 3:
        x = 33.33;
        break;
      case 4:
        x = 25;
        break;
      default:
        x = 25;
        break;
    }
    const { data : genreMoviesData, error } = useSWR(`${apiUrl}/genre/${genreId}`, fetcher, {
        dedupingInterval: 100000,
    });
    console.log('aa',genreMoviesData)
    return (
        <>
            {genreMoviesData && (<>
            <div>{genreNm}</div>
            <div style={{display:"flex", justifyContent:"center"}}>
                <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                {genreMoviesData.map((obj, index) => {
                  // if(obj.posterPath)
                    return (
                        <div key={index} style={{ width: `${x}%`, marginBottom:"20px" }}>
                            <Link to={`/movie/${obj.id}`} key={obj.id} state={{detail : obj.id}}>
                                <div style={{ position: "relative" }}>
                                <img src={obj.posterPath} width="266.66px" height="400px" alt="포스터주소" />
                                <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>{obj.rank}</span>
                                </div>
                            </Link>
                        </div>
                    );
                })}
                </div>
            </div>
            </>)}
        </>
    )

};

export default GenreMovies;