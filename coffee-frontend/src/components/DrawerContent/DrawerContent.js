import React, {useState, useEffect} from 'react'
import {AuthContext} from '../../utils/auth'
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Button from '@material-ui/core/Button';
import {makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import {getFriends, addFriend, FriendContext} from '../../utils/friendUtils'
import Alert from '@material-ui/lab/Alert';



const useStyles = makeStyles(theme => ({
    root: {
      display: 'flex',
    },
    addButton: {
        marginTop: theme.spacing(2),
        marginBottom: theme.spacing(2),
        flexGrow: 1,
        position: "fixed",
        bottom: theme.spacing.unit * 2,
        left: theme.spacing.unit * 3
      },
    }),
    );


export function DrawerContent() {
    const { state } = React.useContext(AuthContext);
    const {friends} = React.useContext(FriendContext).friends

    const classes = useStyles();
    const [addDialog, setAddDialog] = React.useState(false);
    const [friend, setFriend] = useState('');
    const [showAlert, setShowAlert] = useState(false)
    const [alertText, setAlertText] = useState('');
    const [addFriendSuccess, setAddFriendSuccess] = useState(false);

    useEffect(() => {
  
 
    }, []);

    const handleAddFriendButton = () => {
        setAddDialog(true);
    }

    const handleClose = () => {
        setAddDialog(false);
      };

    
    const handleAddFriend = async () => {
        try {
            const friendRequest = await addFriend(friend);
            setAlertText('Friend request successfully sent')
            setAddFriendSuccess(true);

            
        }
        catch (err) {
            setAlertText(err.response.data.message);
            setAddFriendSuccess(false);
        
        }
        setAddDialog(false);
        setShowAlert(true) //TODO: Remove after X Seconds
        setFriend('')
        handleAlertClose();

    }

    const handleAlertClose = () => {
        setTimeout( () => {
            setShowAlert(false);

        }, 3000)
    }

    const addFriendDialog = (
        <Dialog open={addDialog} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Add new friend</DialogTitle>
        <DialogContent>
          <DialogContentText>
            To add a new friend, please enter their email address
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="Email Address"
            type="email"
            fullWidth
            onChange={(e)=>setFriend(e.target.value)}

          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
          <Button onClick={handleAddFriend} color="primary">
            Add
          </Button>
        </DialogActions>
      </Dialog>
    )
    const alert = (
    <Alert severity={addFriendSuccess ? 'success' : 'error'} onClose={() => {}}>{alertText}</Alert>

    ) 
    if (!state.isAuthenticated) {
        return <p>Please log in</p>
    }
    
    return (
        
        <div>
        {showAlert && alert}

        <List>
                    <Button
                variant="contained"
                size="large"
                color="primary"
                className={classes.addButton}
                onClick={()=>handleAddFriendButton()}>
                Add new friend
        </Button>
        {friends.map((friend, index) => (
            <ListItem button key={friend.id}>
            <ListItemText primary={friend.name} secondary={"Last online: 2 hours ago"}/>

            </ListItem>
        ))}
        </List>

        {addFriendDialog}

       
    </div>
    )

}