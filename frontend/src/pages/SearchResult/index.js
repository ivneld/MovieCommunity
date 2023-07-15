import { useEffect, useState } from "react";
import { useLocation } from 'react-router-dom';
import fetcher from "../../utils/fetcher";
import useSWR from 'swr';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import { Link } from 'react-router-dom';
function SearchResult() {
    const location = useLocation();
    const search = location.state.search;
    const apiUrl = process.env.REACT_APP_API_URL;
    const { data: searchResults } = useSWR(`${apiUrl}/movie/search/detail?search=${search}`, fetcher);
    console.log(searchResults?.movies)

    const [activeTab, setActiveTab] = useState(0);
    const handleTabChange = (index) => {
        setActiveTab(index);
    };
    return(
    <div style={{margin:"40px 0 40px 40px"}}>
        <div style={{fontWeight:"bold", fontSize:"24px", marginBottom:"20px"}}>'<span style={{color:"rgb(145, 205, 220)", fontSize:"36px"}}>{search}</span>' 검색결과</div>
        <Tabs selectedIndex={activeTab} onSelect={handleTabChange}>
            <TabList style={{fontWeight:"bold", fontSize:"20px", marginBottom:"20px"}}>
                <Tab>전체</Tab>
                <Tab>영화</Tab>
                <Tab>영화인</Tab>
                <Tab>커뮤니티</Tab>
            </TabList>

            <TabPanel>
                <div style={{fontWeight:"bold", fontSize:"24px", marginBottom:"20px"}}>영화 <span style={{fontWeight:"normal"}}>{searchResults?.movies?.length}</span></div>
                    <div style={{display: "flex", flexWrap: "wrap"}}>
                        {searchResults?.movies?.map((obj, idx)=>{
                            return(
                                <div key={idx} style={{ width: "25%", marginBottom:"20px", display:"flex", alignItems:"center" }}>
                                    <Link to={`/movie/${obj.id}`} key={obj.id} state={{detail : obj.id}}>
                                        <img src={obj.posterPath} width="266.66px" height="400px" alt={obj.movieNm} />
                                    </Link>
                                    <div style={{marginLeft:"20px"}}>
                                        <span style={{fontWeight:"bold", fontSize:"20px"}}>{obj.movieNm}</span>
                                        <div>{obj.openDt}, {obj.nationNm}</div>
                                    </div>
                                </div>
                            )
                        })}
                </div>
                <hr/>
                <div style={{fontWeight:"bold", fontSize:"24px", marginBottom:"20px"}}>영화인 <span style={{fontWeight:"normal"}}>{searchResults?.credits?.length}</span></div>
                    <div style={{display: "flex", flexWrap: "wrap"}}>
                        {searchResults?.credits?.map((obj, idx)=>{
                            return(
                                <div key={idx} style={{ width: "25%", marginBottom:"20px", display:"flex", alignItems:"center" }}>
                                    <img src={obj.profileUrl} width="266.66px" height="400px" alt={obj.actorNm} />
                                    <div style={{marginLeft:"20px"}}>
                                        <span style={{fontWeight:"bold", fontSize:"20px"}}>{obj.actorNm}</span>
                                        <div>{obj.creditCategory}</div>
                                    </div>
                                </div>
                            )
                        })}
                </div>
                <div>
                    커뮤니티 내용 뭐시기뭐시기
                </div>
            </TabPanel>

            <TabPanel>
                <div style={{fontWeight:"bold", fontSize:"24px", marginBottom:"20px"}}>영화 <span style={{fontWeight:"normal"}}>{searchResults?.movies?.length}</span></div>
                <div style={{display: "flex", flexWrap: "wrap"}}>
                    {searchResults?.movies?.map((obj, idx)=>{
                        return(
                            <div key={idx} style={{ width: "25%", marginBottom:"20px", display:"flex", alignItems:"center" }}>
                            <Link to={`/movie/${obj.id}`} key={obj.id} state={{detail : obj.id}}>
                                <img src={obj.posterPath} width="266.66px" height="400px" alt={obj.movieNm} />
                            </Link>
                            <div style={{marginLeft:"20px"}}>
                                <span style={{fontWeight:"bold", fontSize:"20px"}}>{obj.movieNm}</span>
                                <div>{obj.openDt}, {obj.nationNm}</div>
                            </div>
                        </div>
                        )
                    })}
                </div>
            </TabPanel>

            <TabPanel>
                <div style={{fontWeight:"bold", fontSize:"24px", marginBottom:"20px"}}>영화인 <span style={{fontWeight:"normal"}}>{searchResults?.credits?.length}</span></div>
                    <div style={{display: "flex", flexWrap: "wrap"}}>
                        {searchResults?.credits?.map((obj, idx)=>{
                            return(
                                <div key={idx} style={{ width: "25%", marginBottom:"20px", display:"flex", alignItems:"center" }}>
                                    <img src={obj.profileUrl} width="266.66px" height="400px" alt={obj.actorNm} />
                                    <div style={{marginLeft:"20px"}}>
                                        <span style={{fontWeight:"bold", fontSize:"20px"}}>{obj.actorNm}</span>
                                        <div>{obj.creditCategory}</div>
                                    </div>
                                </div>
                            )
                        })}
                </div>
            </TabPanel>

            <TabPanel>
                <div>
                    커뮤니티 내용 뭐시기뭐시기
                </div>
            </TabPanel>
        </Tabs>
    </div>
)
}

export default SearchResult;