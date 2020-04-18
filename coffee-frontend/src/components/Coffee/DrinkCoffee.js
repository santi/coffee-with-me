import React, { useState, useEffect, Component } from 'react';
import Button from '@material-ui/core/Button';
import {AuthContext} from '../Wrapper/Wrapper'


function DrinkCoffee() {


    const { state, dispatch } = React.useContext(AuthContext);
    console.log(state);
    return (
    <p>{state.user && state.user.name}</p>
    )


}

export default DrinkCoffee
