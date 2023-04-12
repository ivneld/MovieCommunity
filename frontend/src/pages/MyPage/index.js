import React, { useCallback, useState, useContext } from 'react';
import { AuthContext } from '../../context/AuthProvider';
const MyPage = () => {
    const { auth, setAuth } = useContext(AuthContext);

    return(
        <>
            마이페이지
            <hr/>
            {							
                (auth) ?
                    <>
                        <span> 아이디 : {auth}</span>
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