import React, { useCallback, useState, useContext } from 'react';
import { AuthContext } from '../../context/AuthProvider';
import useSWR, { mutate } from 'swr';
import fetcher from '../../utils/fetcher';
import fetcherAccessToken from '../../utils/fetcherAccessToken';

const MyPage = () => {
    const { auth, setAuth } = useContext(AuthContext);

    const accessToken = localStorage.getItem('accessToken');
    const { data : mypageData, error } = useSWR(`http://localhost:8080/mypage/1/movie`, fetcherAccessToken, {
        dedupingInterval: 100000,
    });
    console.log(mypageData)

    return(
        <>
            마이페이지
            <hr/>
            {							
                (auth) ?
                    <>
                        <span>{auth}님이 좋아요 한 영화</span>
                        <div style={{display:"flex", justifyContent:"center"}}>
                            <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                                {mypageData?.content?.map((obj, idx) => {
                                    return(
                                        <div key={idx}>
                                            <img src={obj.posterPath} width="266.66px" height="400px" alt="포스터주소" />
                                            <div>{obj.movieNm}</div>
                                        </div>
                                    )
                                })}
                            </div>
                        </div>
                    </>
                    :
                    <>
                        정보없음
                    </>
            }
        </>
    )
};

export default MyPage;