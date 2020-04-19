import React, { Component } from 'react';
import { ACCESS_TOKEN } from '../../../utils/constants';
import { Redirect } from 'react-router-dom'
import { getCurrentUser } from '../../../utils/loginUtils';
import {AuthContext} from '../../../utils/auth'

class OAuth2RedirectHandler extends Component {

    static contextType = AuthContext



    getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');

        var results = regex.exec(this.props.location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    };

    getUser = async ()  => {
        const user = await getCurrentUser();
        const dispatch = this.context;
        console.log(dispatch);
        dispatch({type: "GETUSER", 
        payload: user.data})

    }

    render() {        
        const token = this.getUrlParameter('token');
        const error = this.getUrlParameter('error');

        if(token) {
            this.getUser();
            localStorage.setItem(ACCESS_TOKEN, token);
            return <Redirect to={{
                pathname: "/profile",
                state: { from: this.props.location }
            }}/>; 
        } else {
            return <Redirect to={{
                pathname: "/login",
                state: { 
                    from: this.props.location,
                    error: error 
                }
            }}/>; 
        }
    }
}

export default OAuth2RedirectHandler;