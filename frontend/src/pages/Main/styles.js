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
  color: #1264a3;
`