import React, { useCallback, useState } from 'react';
import fetcher from '../../utils/fetcher';
import useSWR from 'swr';

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
                <div key={index}>
                <span>{data.genreNm} </span>
                <span>{data.count}</span>
                </div>
            ))}
            </div>
        </>
    )
};

export default Genre;