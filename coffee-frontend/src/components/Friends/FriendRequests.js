import React, {useState, useEffect} from 'react'
import {AuthContext} from '../../utils/auth'
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';

import Button from '@material-ui/core/Button';
import {makeStyles } from '@material-ui/core/styles';
import {acceptFriend, rejectFriend, FriendContext} from '../../utils/friendUtils'
import Alert from '@material-ui/lab/Alert';
var moment = require('moment');



const useStyles = makeStyles(theme => ({
    root: {
      display: 'flex',
    },
    button: {
        marginTop: theme.spacing(0.5),
        flexGrow: 1,
      },
    list: {
        background: 'white',
    },
    listItemText: {
        color: 'black',
    },
    actions: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        rigth: '2px'
    }
    }),
    );


export default function FriendRequest() {
    const { state } = React.useContext(AuthContext);
    const {friends} = React.useContext(FriendContext);
    const requests = friends.requests;
    const [alertText, setAlertText] = useState('');
    const [alertSuccess, setAlertSuccess] = useState(false);
    const [showAlert, setShowAlert] = useState(false)
    const classes = useStyles();
   
    useEffect(() => {
  
 
    }, []);

    const handleAcceptRequest = async (id) => {
        try {
            const response = await acceptFriend(id)
            console.log(response)
            setAlertText('Accepted friend request')
            setAlertSuccess(true);

            
        }
        catch (err) {
            console.log(err);
            setAlertText('Something went wrong');
            setAlertSuccess(false);
        
        }
        setShowAlert(true)
        handleAlertClose();

    }

    const handleRejectRequest = async (id) => {
        try {
            const response = await rejectFriend(id)
            setAlertText('Rejected friend request')
            setAlertSuccess(true);

            
        }
        catch (err) {
            setAlertText('Something went wrong');
            setAlertSuccess(false);
        
        }
        setShowAlert(true)
        handleAlertClose();
      };


    
    const handleAlertClose = () => {
    setTimeout( () => {
        setShowAlert(false);

    }, 3000)
}


    


    const alert = (
    <Alert severity={alertSuccess ? 'success' : 'error'} onClose={() => {}}>{alertText}</Alert>

    ) 
    if (!state.isAuthenticated) {
        return <p>Please log in to view this page</p>
    }
    
    return (
        
        <div>
        {showAlert && alert}

        <List className={classes.list}>
        {requests.map((request, index) => (
            <ListItem button key={request.id}>
            <ListItemText className={classes.listItemText} primary={request.from.name} secondary={'Sent: ' + moment(request.created).format('DD-MM-YY')}/>
            <ListItemSecondaryAction className={classes.actions}>
                
            <Button
                variant="contained"
                size="small"
                color="primary"
                className={classes.button}
                onClick={()=>handleAcceptRequest(request.id)}>
                Accept
                </Button>
                <Button
                variant="contained"
                size="small"
                color="secondary"
                className={classes.button}
                onClick={()=>handleRejectRequest(request.id)}>
                Reject
                </Button>
            </ListItemSecondaryAction>
            </ListItem>
        ))}
        </List>


       
    </div>
    )

}