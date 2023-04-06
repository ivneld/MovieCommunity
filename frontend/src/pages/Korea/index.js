import React from 'react';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
const Korea = () => {
    const { data : koreaData } = useSWR('http://localhost:8080/korea', fetcher, {
        dedupingInterval: 100000,
    });
    if (!koreaData){
        return <div>데이터가 없습니다</div>
    }   
    // console.log(koreaData)
    return(
        <>
            <div>
                국내영화
            </div>
            <hr/>
            <div>
                {koreaData.map((data)=>{
                        return(
                            <div>
                                <span>{data.movieNm}</span>
                            </div>
                        )}
                    )
                }
            </div>
        </>
    )
};

export default Korea;