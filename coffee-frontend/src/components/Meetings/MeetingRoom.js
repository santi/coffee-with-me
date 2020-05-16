import React, { useState, useEffect, Component } from 'react';
import Button from '@material-ui/core/Button';
import {AuthContext} from '../../utils/auth'
import { drinkCoffee } from '../../utils/coffee-utils';
import { createStyles, makeStyles } from '@material-ui/core/styles';




const useStyles = makeStyles((theme) =>
  createStyles({

  }),
);

function MeetingRoom() {
    const classes = useStyles();
    return (
        <p>Waiting for someone to join you in Coffee</p>
    )


}

export default MeetingRoom
