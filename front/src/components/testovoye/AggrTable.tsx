import React, {useEffect, useState} from 'react';
import {Table} from 'antd';
import type {ColumnsType, TablePaginationConfig} from 'antd/es/table';
import type {FilterValue} from 'antd/es/table/interface';

interface DataType {
    name: {
        first: string;
        last: string;
    };
    gender: string;
    email: string;
    login: {
        uuid: string;
    };
}

interface TableParams {
    pagination?: TablePaginationConfig;
    sortField?: string;
    sortOrder?: string;
    filters?: Record<string, FilterValue | null>;
}

const columns: ColumnsType<DataType> = [
    {
        title: 'Ctr',
        dataIndex: 'ctr',
        sorter: true,
        width: '20%',
    },
    {
        title: 'EvPM',
        dataIndex: 'evpm',
        width: '20%',
    },
    {
        title: 'Views',
        dataIndex: 'views',
    },
    {
        title: 'site',
        dataIndex: 'siteId',
    },
];

const AggrTable: React.FC = () => {
    const [data, setData] = useState<DataType[]>();
    const [loading, setLoading] = useState(false);
    const [tableParams, setTableParams] = useState<TableParams>({
        pagination: {
            current: 0,
            pageSize: 20,
        },
    });

    const fetchData = () => {
        setLoading(true);

        fetch(`${process.env.REACT_APP_API_BASE1}/api/aggr-table`, {
            method: 'POST',
            headers: {'Content-Type': 'Application/json'},
            body: JSON.stringify({size: tableParams.pagination?.pageSize, page: tableParams.pagination?.current})
        })
            .then((res) => res.json())
            .then((v) => {
                setData(v.content);
                setLoading(false);
                setTableParams({
                    ...tableParams,
                    pagination: {
                        ...tableParams.pagination,
                        total: v.totalElements,
                    },
                });
            });
    };

    useEffect(() => {
        fetchData();
    }, [JSON.stringify(tableParams)]);

    const handleTableChange = (
        pagination: TablePaginationConfig,
        filters: Record<string, FilterValue | null>,
        sorter: any,
    ) => {
        setTableParams({
            pagination,
            filters,
            ...sorter,
        });

        if (pagination.pageSize !== tableParams.pagination?.pageSize) {
            setData([]);
        }
    };

    let keyTable = 0;
    return (<>
            <Table
                columns={columns}
                rowKey={_ => keyTable++}
                dataSource={data}
                pagination={tableParams.pagination}
                loading={loading}
                onChange={handleTableChange}
            />
        </>
    );
};

export default AggrTable;
