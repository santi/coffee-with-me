import React, { Component } from 'react';
import {
  Route,
  Switch
} from 'react-router-dom';
import PrivateRoute from '../Shared/PrivateRoute';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import './Content.scss';
import Login from '../Login/login/Login'
import Profile from '../Login/profile/Profile'
class Content extends Component {
  constructor(props) {
    super(props);
    this.state = {
      authenticated: false,
      currentUser: null,
      loading: false
    }

  }

  render() {

    return (
        <div>
          <Switch>
            <Route exact path="/" component={Login}></Route>           
            <PrivateRoute path="/profile" authenticated={this.state.authenticated} currentUser={this.state.currentUser}
              component={Profile}></PrivateRoute>
          </Switch>
          </div>
    );
  }
}

export default Content;
