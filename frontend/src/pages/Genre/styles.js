import styled from '@emotion/styled';

export const FlexDiv = styled.div`
    color: white;
    font-size: 30px;
    border: 1px solid black; 
    width: 200px; 
    height: 200px; 
    display: flex;
    justify-content: center; 
    align-items: center;
    background-color: rgb(145, 205, 220);
    transition: all 0.25s;

    &:hover {
    transform: scale(1.05);
    background-color: RGB(255, 215, 0);
    transition: all 0.5s; 
    }
`;