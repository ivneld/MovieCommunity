import styled from '@emotion/styled';

export const LinkContainer = styled.p`
  font-size: 16px;
  color: #616061;
  margin: 0 auto 8px;
  & a {
    color: #1264a3;
    text-decoration: none;
    font-weight: 700;
    &:hover {
      text-decoration: underline;
    }
  }
`;
export const MovieSpan = styled.span`
  cursor: pointer;
`
export const CategorySpan = styled.span`
  cursor: pointer;
  font-weight: 700;
  color: #1264a3;
`

export const OpendtApiContainer = styled.div`
  
`
export const PosterContainer1 = styled.div`
  display: flex;
  justify-content: center;
`
export const PosterContainer2 = styled.div`
  display: flex;
  
  margin-top: 10px;

`
export const MovieImg = styled.img`
  height: 400;
`
export const TimeStamp = styled.div`
  display: flex;
  justify-content: center;
  margin: 10px 0;
`