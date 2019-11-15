import './bootstrap-minty.css';
import $ from 'jquery';
import Popper from 'popper.js';
import {
  HashRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";

import { transitions, positions, Provider as AlertProvider } from 'react-alert'
import AlertTemplate from "react-alert-template-basic";
import Alerts from "./layout/Alerts";

import Header from './layout/Header'
import Login from './components/auth/Login';
import Welcome from './components/common/Welcome'
import React, { Component, Fragment } from 'react';
import ReactDOM from 'react-dom';

import { Provider } from "react-redux";
import store from "./store";

const alertOptions = {
  timeout: 3000,
  position: positions.BOTTOM_CENTER,
  transition: transitions.FADE,
};

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  // genRoute = () => {
  //   let path = [ {path:"/",component:"Welcome"},{path:"/login",component:"Login"}];
  //   const route = path.map(path => (<Route key={path.component} exact path={path.path} component={path.component} />));
  //   return route;
  // }
  render() {
    // const route = this.genRoute();
    return (
      <Provider store={store}>
        <AlertProvider template={AlertTemplate} {...alertOptions}>
          <Router>
            <Fragment>
              <Header />
              <Alerts />
              <div className="container">
                <div className="row">
                  <br/><br/><br/><br/>
                </div>
                <div className="row">
                  <div className="col">
                    <Switch>
                      <Route exact path="/" component={Welcome} />
                      <Route exact path="/login" component={Login} />
                      {/* {route} */}
                    </Switch>
                  </div>
                </div>
              </div>
            </Fragment>
          </Router>
        </AlertProvider>
      </Provider>
    );
  }
}

ReactDOM.render(<App />, document.getElementById('app'));