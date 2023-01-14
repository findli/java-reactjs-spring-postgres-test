import {useRoutes} from 'react-router-dom';
import AggrChart from "./testovoye/AggrChart";
import AggrTable from "./testovoye/AggrTable";

export function AppRoutes() {
    const element = useRoutes([
        {
            path: '/',
            element: (
                <div className="row">
                    <h2 className="text-center mt-5">
                        Test
                    </h2>
                </div>
            ),
        },
        {path: '/chart', element: <AggrChart/>},
        {path: '/table', element: <AggrTable/>},
    ]);

    return element;
}
