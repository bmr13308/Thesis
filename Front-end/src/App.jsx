import React from 'react';
import PropTypes from 'prop-types';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import HomeIcon from '@material-ui/icons/FitnessCenter';
import ExitToAppIcon from '@material-ui/icons/ExitToApp';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import { SnackbarProvider } from 'notistack';
import { useStoreState, useStoreActions } from 'easy-peasy';
import { Box } from '@material-ui/core';
import { Feed } from './pages/Feed';
import { SignIn } from './pages/SignIn';
import { SignUp } from './pages/SignUp';
import { EditProfile } from './pages/EditProfile';
import { Post } from './pages/Post';
import { Profile } from './pages/Profile';
import { getUser } from './utils/getUser';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
}));

function ButtonAppBar({ user }) {
  const classes = useStyles();
  const { reset } = useStoreActions((actions) => actions);

  const logout = () => {
    reset();
  };

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <IconButton edge="start" color="inherit" aria-label="menu" component={Link} to="/feed">
            <HomeIcon />
          </IconButton>
          <Typography variant="h6" className={classes.title}>
            FitNest
          </Typography>
          {user.userId ? (
            <>
              <IconButton
                edge="start"
                className={classes.menuButton}
                color="inherit"
                aria-label="menu"
                component={Link}
                to="/settings"
              >
                <AccountCircleIcon />
              </IconButton>
              <IconButton
                edge="start"
                className={classes.menuButton}
                color="inherit"
                aria-label="menu"
                onClick={logout}
              >
                <ExitToAppIcon />
              </IconButton>
            </>
          ) : (
            <>
              <Button component={Link} to="/signin" color="inherit">
                Sign In
              </Button>
              <Button component={Link} to="/" color="inherit">
                Sign Up
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
}
ButtonAppBar.propTypes = {
  // eslint-disable-next-line react/forbid-prop-types
  user: PropTypes.any,
};
ButtonAppBar.defaultProps = {
  user: null,
};

export function App() {
  const { token } = useStoreState((state) => state);

  const user = getUser(token);

  return (
    <Box>
      <Router>
        <ButtonAppBar user={user} />
        <Box maxWidth="1000px" margin="0 auto">
          <Switch>
            <Route exact path="/">
              <SignUp />
            </Route>
            <Route path="/signin">
              <SignIn />
            </Route>
            <Route path="/feed">
              <Feed />
            </Route>
            <Route path="/post/:postId">
              <Post />
            </Route>
            <Route path="/profile/:userId">
              <Profile />
            </Route>
            <Route path="/settings">
              <EditProfile />
            </Route>
          </Switch>
        </Box>
      </Router>
    </Box>
  );
}

export default function IntegrationNotistack() {
  return (
    <SnackbarProvider maxSnack={3}>
      <App />
    </SnackbarProvider>
  );
}
