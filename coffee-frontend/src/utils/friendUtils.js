import { API_BASE_URL, ACCESS_TOKEN } from './constants';
import axios from 'axios'
import React, { useState, useEffect, useContext, createContext } from "react";



export const getFriends = () =>  {

    return axios.get(API_BASE_URL + "/user/friends")
}

export const getFriendRequests = () =>  {

    return axios.get(API_BASE_URL + "/friends/requests/toMe")
}

export const addFriend = (friendId) =>  {
    return axios.post(API_BASE_URL + "/friends/requests", {to: friendId})
}

export const acceptFriend= (requestId) => {
    return axios.get(API_BASE_URL + "/friends/requests/" + requestId + "/accept")
}

export const rejectFriend = (requestId) => {
    return axios.get(API_BASE_URL + "/friends/requests/" + requestId + "/reject")
}


export const FriendContext = React.createContext(); // added this