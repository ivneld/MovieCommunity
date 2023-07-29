import React, { useCallback, useState, useEffect } from 'react';
import fetcher from '../../utils/fetcher';
import useSWR from 'swr';
import { Link } from 'react-router-dom';
import { FlexDiv } from "./styles";

const Genre = () => {
    const apiUrl = process.env.REACT_APP_API_URL;
    const { data : genreData, error } = useSWR(`${apiUrl}/genre`, fetcher, {
        dedupingInterval: 100000,
    });
    console.log('aa',genreData)

    return(
        <>
            <div style={{fontSize:"24px", fontWeight:"bold", margin: "20px"}}>장르별 보고싶은 영화를 찾아보세요</div>
            <div style={{color:"black", fontWeight:"bold", display:"flex", justifyContent:"center"}}>
                <div style={{ maxWidth:"1700px", display: "flex", flexWrap: "wrap" }}>
                    {genreData?.map((data, index) => (
                        <Link to={`/genre/${data.genreId}`} state={{genreId : data.genreId, genreNm: data.genreNm, count: data.count}} style={{width:"12.5%", marginBottom:"20px"}}>
                            <FlexDiv key={data.genreId}>
                                <div>{data.genreNm} </div>
                                {/* <span>{data.count}</span> */}
                            </FlexDiv>
                        </Link>
                    ))}
                </div>
            </div>
        </>
    )
};

export default Genre;