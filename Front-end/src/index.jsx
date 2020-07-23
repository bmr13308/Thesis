import React from 'react';
import ReactDOM from 'react-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import { StoreProvider } from 'easy-peasy';
import { App } from './App';
import * as serviceWorker from './serviceWorker';
import { store } from './store/store';

ReactDOM.render(
  <React.StrictMode>
    <StoreProvider store={store}>
      <CssBaseline>
        <App />
      </CssBaseline>
    </StoreProvider>
  </React.StrictMode>,
  document.getElementById('root')
);

serviceWorker.unregister();
