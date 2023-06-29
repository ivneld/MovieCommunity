import React, { useCallback, useState, useEffect } from 'react';
import fetcher from '../../utils/fetcher';
import useSWR from 'swr';
import { Link } from 'react-router-dom';

const Genre = () => {
    const { data : genreData, error } = useSWR(`http://localhost:8080/genre`, fetcher, {
        dedupingInterval: 100000,
    });
    console.log(genreData)
    const [sortedGenreData, setSortedGenreData] = useState([]);

    useEffect(() => {
      if (genreData) {
        const sortedData = [...genreData].sort((a, b) => b.count - a.count);
        setSortedGenreData(sortedData);
      }
    }, [genreData]);

    return(
        <>
            <div style={{fontSize:"24px", fontWeight:"bold", margin: "20px 0 20px 20px"}}>장르별 보고싶은 영화를 찾아보세요</div>
            <div style={{color:"black", fontWeight:"bold", display:"flex"}}>
            {genreData?.map((data, index) => (
                <Link to={`/genre/${data.genreId}`} state={{genreId : data.genreId, genreNm: data.genreNm, count: data.count}}>
                    <div key={data.genreId} style={{border:"1px solid black", marginBottom:"10px", marginRight:"10px", minWidth:"100px", width: `${data.count}*20px`, minHeight:"30px", height:"50px"}}>
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