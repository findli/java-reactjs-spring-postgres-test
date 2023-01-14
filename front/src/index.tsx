import React from 'react';
import {createRoot} from 'react-dom/client';
import {ErrorBoundary} from 'react-error-boundary';
import {SWRConfig} from 'swr';

import 'bootstrap/dist/css/bootstrap.css';
import reportWebVitals from './reportWebVitals';

import App from './App';
import {ErrorFallback} from './components/ErrorFallback';
import {fetcherGet} from './utils/fetcher-get';

createRoot(
    document.getElementById('root')!).render(
    <ErrorBoundary FallbackComponent={ErrorFallback}>
        {/*<React.StrictMode>*/}
            <SWRConfig value={{fetcher: fetcherGet, suspense: true}}>
                <App/>
            </SWRConfig>
        {/*</React.StrictMode>*/}
    </ErrorBoundary>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
