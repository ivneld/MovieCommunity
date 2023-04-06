import React from 'react';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
const Foreign = () => {
    const { data : foreignData } = useSWR('http://localhost:8080/foreign', fetcher, {
        dedupingInterval: 100000,
    });
    if (!foreignData){
        return <div>데이터가 없습니다</div>
    }   
    // console.log(foreignData)
    return(
        <>
            <div>
                해외영화
            </div>
            <hr/>
            <div>
                {foreignData.map((data)=>{
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

export default Foreign;