import React, { useEffect, useState } from 'react';
import {
  Route,
  Switch
} from 'react-router-dom';
import PrivateRoute from '../Shared/PrivateRoute';
import './Content.scss';
import Login from '../Login/login/Login'
import Signup from '../Login/signup/Signup'
import { getCurrentUser } from '../../utils/loginUtils'; 
import Profile from '../Login/profile/Profile'
import OAuth2RedirectHandler from '../Login/oauth2/OAuth2RedirectHandler'


function Content() {
    const [currentUser, setCurrentUser] = useState('')

    useEffect(() => {
      const currentUser = getCurrentUser();
      setCurrentUser(currentUser);
  }, []);

    return (
        <div>
          <Switch>
            <Route exact path="/" component={Login}></Route>           
            <Route path="/profile" currentUser={currentUser}
              component={Profile}></Route>
            <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}></Route>  
            <Route path="/signup" component={Signup}></Route>   
            <Route  path="/login" component={Login}></Route>           


          </Switch>
          </div>
    );
  }

export default Content;
