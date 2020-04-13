import { API_BASE_URL, ACCESS_TOKEN } from './constants';
import axios from 'axios'



export const login = (loginRequest) =>  {
    return axios.post(API_BASE_URL + "/auth/login", loginRequest)
}

export const signup = (signupRequest) =>  {
    return axios.post(API_BASE_URL + "/auth/signup", signupRequest)
}

const request_text = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);
    return fetch(options.url, options)
    .then(response => {
        return response.text().then(text => {
            if(!response.ok) {
                return Promise.reject(text);
            }
            return text;
        })
});
};


const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);
    return fetch(options.url, options)
    .then(response => {
        return response.json().then(json => {
            if(!response.ok) {
                return Promise.reject(json);
            }
            return json;
        })
});
};

export function getCurrentUser() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/user/me",
        method: 'GET'
    });
}

