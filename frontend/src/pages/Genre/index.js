import React, { useCallback, useState } from 'react';
import fetcher from '../../utils/fetcher';
import useSWR from 'swr';
import { Link } from 'react-router-dom';

const Genre = () => {
    const { data : genreData, error } = useSWR(`http://localhost:8080/genre`, fetcher, {
        dedupingInterval: 100000,
    });
    console.log(genreData)

    return(
        <>
            장르별 보고싶은 영화를 찾아보세요
            <div>
            {genreData?.map((data, index) => (
                <Link to={`/genre/${data.genreId}`} state={{genreId : data.genreId, genreNm: data.genreNm, count: data.count}}>
                    <div key={data.genreId} style={{border:"1px solid black", marginBottom:"10px", backgroundColor:"beige"}}>
                    <span>{data.genreNm} </span>
                    <span>{data.count}</span>
                    </div>
                </Link>
            ))}
            </div>
        </>
    )
};

export default Genre;