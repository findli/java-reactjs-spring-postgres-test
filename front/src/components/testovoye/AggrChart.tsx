import * as React from 'react';
import {useEffect, useState} from 'react';
import {CartesianGrid, Legend, Line, LineChart, Tooltip, XAxis, YAxis} from "recharts";
import {AutoComplete, Button, Dropdown, MenuProps, Space} from "antd";
import {DownOutlined} from '@ant-design/icons';

const DEFAULT_SITE_ID = "heavy.com";
export default function AggrChart() {
    let [chartType, setChartType] = useState("evpm");
    let [siteId, setSiteId] = useState(DEFAULT_SITE_ID);
    const [loading, setLoading] = useState(false);
    const [siteIdList, setSiteIdList] = useState<{ value: string }[]>([]);
    const [data, setData] = useState<{
        ctr: number
        evpm: number
        intervalAlias: string
        views: number
    }[]>([]);

    useEffect(() => {
        fetch(`${process.env.REACT_APP_API_BASE1}/api/preload`,)
            .then((res) => res.json())
            .then((v) => {
                let siteIdListLocal = v.siteId.map((value: string) => {
                    return {
                        value
                    }
                });
                setSiteIdList(siteIdListLocal);
                console.info(siteIdListLocal, siteIdListLocal[0]);
            })
    }, []);

    const fetchData = () => {
        setLoading(true);

        fetch(`${process.env.REACT_APP_API_BASE1}/api/aggr-chart`, {
            method: 'POST',
            headers: {'Content-Type': 'Application/json'},
            body: JSON.stringify({siteId})
        })
            .then((res) => res.json())
            .then((v) => {
                setData(v);
                setLoading(false);
            });
    };

    useEffect(() => fetchData(), [siteId]);

    // @ts-ignore
    let chart_data = data?.filter(v => v[chartType] != null);
    let data2 = [];
    for (let i = 0; i < chart_data?.length; i++) {
        let elem = chart_data[i];
        let intervalAlias = chart_data[0].intervalAlias;
        data2.push({
            "name": new Intl.DateTimeFormat("en-us", {
                year: 'numeric',
                month: 'numeric',
                day: 'numeric',
                hour: 'numeric',
            }).format(new Date(intervalAlias)),
            // @ts-ignore
            [chartType]: elem[chartType],
        })
    }
    const handleMenuClick: MenuProps['onClick'] = (e) => {
        setChartType(e.key);
    };

    const menuProps = {
        items: [
            {
                label: 'CTR',
                key: 'ctr',
            },
            {
                label: "EvPM",
                key: 'evpm',
            },
        ],
        onClick: handleMenuClick,
    };
    return <div>
        <div style={{display: "flex", alignItems: 'center', height: 50}}>
            <div>
                <Dropdown menu={menuProps}>
                    <Button className={'btn'}>
                        <Space>
                            CTR/EvPM
                            <DownOutlined/>
                        </Space>
                    </Button>
                </Dropdown>
            </div>
            <div style={{width: 50}}></div>
            <AutoComplete
                style={{width: 200,}}
                defaultValue={DEFAULT_SITE_ID}
                options={siteIdList}
                placeholder="site id"
                filterOption={(inputValue, option) => {
                    return option!.value.toUpperCase().startsWith(inputValue.toUpperCase());
                }
                }
                onSelect={(value) => {
                    setSiteId(value);
                }
                }
            /></div>
        <div>
            {loading && <div>loading</div>}
            {!loading && <LineChart width={730} height={250} data={data2}
                                    margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                <CartesianGrid strokeDasharray="3 3"/>
                <XAxis dataKey="name"/>
                <YAxis/>
                <Tooltip/>
                <Legend/>
                <Line isAnimationActive={false} type="monotone" dataKey={chartType} stroke="#000"/>
            </LineChart>}
        </div>
    </div>
};
