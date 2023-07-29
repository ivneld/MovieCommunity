import React, { useCallback, useState, useContext, useRef } from 'react';
import useSWR, { mutate } from 'swr';
import fetcher from '../../utils/fetcher';
import { Link } from 'react-router-dom';

const UpcomingMovies = () => {
    const apiUrl = process.env.REACT_APP_API_URL;

    const { data: upcomingMovieData, error1 } = useSWR(`${apiUrl}/movie/coming`, fetcher); 
    console.log("upcomingMovieData",upcomingMovieData)

    return(
        <>
            {upcomingMovieData && (<>
                <div>이제 곧 상영할 작품들을 둘러보세요</div>
                <div style={{display:"flex", justifyContent:"center"}}>
                    <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                    {upcomingMovieData.content?.map((obj, index) => {
                        return (
                            <div key={index} style={{ width: `25%`, marginBottom:"20px" }}>
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

export default UpcomingMovies;