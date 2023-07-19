import styled from '@emotion/styled';

export const MovieDiv = styled.div`
  margin-right: 30px;
  margin-bottom: 10px;
  font-weight: bold;
  font-size: 22px;
`;

export const CustomDiv = styled.div`
  font-size: 28px;
  font-weight: bold;
`;

export const InterestSpan = styled.span`
  font-size: 32px;
  color: white;
  margin: 0 35%;
  padding: 5px 12px;
  border: 1px solid black;
  border-radius: 30px;
  &:hover {
    cursor: pointer;
  }
`;

//여기부터 CommentModal
export const TextArea = styled.textarea`
  width: 550px;
  max-width: 550px;
`;
export const Form = styled.form`
  margin: 0 auto;
  width: 700px;
  max-width: 700px;
`;

export const Button = styled.button`
  margin-bottom: 12px;
  width: 40%;
  max-width: 100%;
  color: #fff;
  background-color: #4a154b;
  border: none;
  font-size: 18px;
  font-weight: 900;
  height: 44px;
  min-width: 96px;
  padding: 0 16px 3px;
  transition: all 80ms linear;
  user-select: none;
  outline: none;
  cursor: pointer;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.3);
  &:hover {
    background-color: rgba(74, 21, 75, 0.9);
    border: none;
  }
  &:focus {
    --saf-0: rgba(var(--sk_highlight, 18, 100, 163), 1);
    box-shadow: 0 0 0 1px var(--saf-0), 0 0 0 5px rgba(29, 155, 209, 0.3);
  }
`;