import React, {useEffect, useState} from 'react';

import AppBar from '@material-ui/core/AppBar';
import CssBaseline from '@material-ui/core/CssBaseline';
import Drawer from '@material-ui/core/Drawer';
import Hidden from '@material-ui/core/Hidden';
import IconButton from '@material-ui/core/IconButton';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import AccountCircle from '@material-ui/icons/AccountCircle';
import MenuIcon from '@material-ui/icons/Menu';
import CloseIcon from '@material-ui/icons/Close';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import { getCurrentUser } from '../../utils/loginUtils';
import Content from '../Content/Content'
import { useHistory } from "react-router-dom";


export const AuthContext = React.createContext(); // added this


const initialState = {
    isAuthenticated: false,
    user: null,
    token: null,
  };

const reducer = (state, action) => {
    switch (action.type) {

      case "LOGIN":
        console.log('LOGIN CALLED')
        localStorage.setItem("token", JSON.stringify(action.payload));
        return {
          ...state,
          isAuthenticated: true,
          token: action.payload
        };
      case "LOGOUT":
        localStorage.clear();
        return {
          ...state,
          isAuthenticated: false,
          user: null
        };
    case  "GETUSER":
        console.log(action.payload)
        return {
            ...state,
            user: action.payload
        }
      default:
        return state;
    }
  };


const drawerWidth = 240;
const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
  },
  drawer: {
    [theme.breakpoints.up('sm')]: {
      width: drawerWidth,
      flexShrink: 0,
    },
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
    [theme.breakpoints.up('sm')]: {
      display: 'none',
    },
  },
  toolbar: theme.mixins.toolbar,
  drawerPaper: {
    width: drawerWidth
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
  },
  closeMenuButton: {
    marginRight: 'auto',
    marginLeft: 0,
  },
}));

function Wrapper() {
  const dummyCategories = ['This is a list of all my friends']
  const classes = useStyles();
  const theme = useTheme();
  const [mobileOpen, setMobileOpen] = React.useState(false);
  const auth = React.useState(true);
  const [currentUser, setCurrentUser] = useState('')
  const [state, dispatch] = React.useReducer(reducer, initialState);
  const history = useHistory();


  useEffect(() => {
    async function getUser() {
        const user = await getCurrentUser()
        console.log(user)
        dispatch({type: "GETUSER", 
                payload: user.data})
        history.push("/drink")
    }
    getUser();
}, []);


function handleDrawerToggle() {
    setMobileOpen(!mobileOpen)
  }

  

function handleProfileClick() {
    console.log('profile clicked');
} 

const drawer = (
    <div>
      <List>
        {dummyCategories.map((text, index) => (
          <ListItem button key={text}>
            <ListItemText primary={text} />
          </ListItem>
        ))}
      </List>
    </div>
  );
return (
    <AuthContext.Provider value={{
        state,
        dispatch
      }}>
    <div className={classes.root}>
      <CssBaseline />
      <AppBar position="fixed" className={classes.appBar}>
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="Open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            className={classes.menuButton}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap>
            Lets's Coffee
          </Typography>
          {state.isAuthenticated && (
            <div>
              <IconButton
                aria-label="account of current user"
                aria-controls="menu-appbar"
                onClick={handleProfileClick}
                color="inherit"
                edge='end'
              >
                <AccountCircle />
              </IconButton>
              
            </div>
          )}
        </Toolbar>
      </AppBar>
      
      <nav className={classes.drawer}>
        {/* The implementation can be swapped with js to avoid SEO duplication of links. */}
        <Hidden smUp implementation="css">
          <Drawer
            variant="temporary"
            anchor={theme.direction === 'rtl' ? 'right' : 'left'}
            open={mobileOpen}
            onClose={handleDrawerToggle}
            classes={{
              paper: classes.drawerPaper,
            }}
            ModalProps={{
              keepMounted: true, // Better open performance on mobile.
            }}
          >
            <IconButton onClick={handleDrawerToggle} className={classes.closeMenuButton}>
              <CloseIcon/>
            </IconButton>
            {drawer}
          </Drawer>
        </Hidden>
<Hidden xsDown implementation="css">
          <Drawer
            className={classes.drawer}
            variant="permanent"
            classes={{
              paper: classes.drawerPaper,
            }}
          >
            <div className={classes.toolbar} />
            {drawer}
          </Drawer>  
        </Hidden>
      </nav>
      <div className={classes.content}>
        <Content />
      </div>
    </div>
    </AuthContext.Provider>
  );
}
export default Wrapper;
