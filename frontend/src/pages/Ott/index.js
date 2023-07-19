import React, { useCallback, useState } from 'react';
import { OpendtApiContainer } from './styles';
import useSWR from 'swr';
import fetcher from '../../utils/fetcher';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';

const Ott = () => {
    const apiUrl = process.env.REACT_APP_API_URL;

    const { data : netflixData, error1 } = useSWR(`${apiUrl}/tv/netflix`, fetcher, {
        dedupingInterval: 100000,
    });
    const { data : watchaData, error2 } = useSWR(`${apiUrl}/tv/watcha`, fetcher, {
        dedupingInterval: 100000,
    });
    const { data : waaveData, error3 } = useSWR(`${apiUrl}/tv/waave`, fetcher, {
        dedupingInterval: 100000,
    });
    const { data : disneyPlusData, error4 } = useSWR(`${apiUrl}/tv/disneyPlus`, fetcher, {
        dedupingInterval: 100000,
    });
    const { data : appleTvData, error5 } = useSWR(`${apiUrl}/tv/appleTv`, fetcher, {
        dedupingInterval: 100000,
    });

    const [activeTab, setActiveTab] = useState(0);
    const handleTabChange = (index) => {
        setActiveTab(index);
    };
    return (
        <OpendtApiContainer>
            <Tabs selectedIndex={activeTab} onSelect={handleTabChange}>
                <TabList style={{fontSize:"28px", fontWeight:"bold"}}>
                    <Tab>넷플릭스</Tab>
                    <Tab>왓챠</Tab>
                    <Tab>웨이브</Tab>
                    <Tab>디즈니플러스</Tab>
                    <Tab>애플티비</Tab>
                </TabList>

                <TabPanel>
                    <div style={{display:"flex", justifyContent:"center"}}>
                        <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                        {netflixData?.[0]?.map((obj, index) => {
                            return (
                                <div key={index} style={{ width: "25%", marginBottom:"20px" }}>
                                        <div style={{ position: "relative" }}>
                                        <img src={obj.posterPath} width="266.66px" height="400px" alt="포스터주소" />
                                        <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>{index+1}</span>
                                        </div>
                                </div>
                            );
                        })}
                        </div>
                    </div>
                </TabPanel>

                <TabPanel>
                    <div style={{display:"flex", justifyContent:"center"}}>
                        <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                        {watchaData?.[0]?.map((obj, index) => {
                            return (
                                <div key={index} style={{ width: "25%", marginBottom:"20px" }}>
                                        <div style={{ position: "relative" }}>
                                        <img src={obj.posterPath} width="266.66px" height="400px" alt="포스터주소" />
                                        <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>{index+1}</span>
                                        </div>
                                </div>
                            );
                        })}
                        </div>
                    </div>
                </TabPanel>

                <TabPanel>
                    <div style={{display:"flex", justifyContent:"center"}}>
                        <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                        {waaveData?.[0]?.map((obj, index) => {
                            return (
                                <div key={index} style={{ width: "25%", marginBottom:"20px" }}>
                                        <div style={{ position: "relative" }}>
                                        <img src={obj.posterPath} width="266.66px" height="400px" alt="포스터주소" />
                                        <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>{index+1}</span>
                                        </div>
                                </div>
                            );
                        })}
                        </div>
                    </div>
                </TabPanel>

                <TabPanel>
                    <div style={{display:"flex", justifyContent:"center"}}>
                        <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                        {disneyPlusData?.[0]?.map((obj, index) => {
                            return (
                                <div key={index} style={{ width: "25%", marginBottom:"20px" }}>
                                        <div style={{ position: "relative" }}>
                                        <img src={obj.posterPath} width="266.66px" height="400px" alt="포스터주소" />
                                        <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>{index+1}</span>
                                        </div>
                                </div>
                            );
                        })}
                        </div>
                    </div>
                </TabPanel>

                <TabPanel>
                    <div style={{display:"flex", justifyContent:"center"}}>
                        <div style={{ maxWidth:"1150px", marginTop:"20px", display: "flex", flexWrap: "wrap" }}>
                        {appleTvData?.[0]?.map((obj, index) => {
                            return (
                                <div key={index} style={{ width: "25%", marginBottom:"20px" }}>
                                        <div style={{ position: "relative" }}>
                                        <img src={obj.posterPath} width="266.66px" height="400px" alt="포스터주소" />
                                        <span style={{ position: 'absolute', top: 0, left: 0, zIndex: 1, color: 'white', textShadow:"4px 2px 4px black",fontWeight: 'bold', fontSize: '100px', padding: '0 20px' }}>{index+1}</span>
                                        </div>
                                </div>
                            );
                        })}
                        </div>
                    </div>
                </TabPanel>
            </Tabs>

        </OpendtApiContainer>
    )

};

export default Ott;