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
import Signup from '../Login/signup/Signup'

import Profile from '../Login/profile/Profile'
import OAuth2RedirectHandler from '../Login/oauth2/OAuth2RedirectHandler'


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
            <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}></Route>  
            <Route path="/signup" component={Signup}></Route>   
            <Route  path="/login" component={Login}></Route>           


          </Switch>
          </div>
    );
  }
}

export default Content;
