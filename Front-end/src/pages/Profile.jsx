/* eslint-disable eqeqeq */
/* eslint-disable react/prop-types */
/* eslint-disable no-nested-ternary */
import { useStoreState } from 'easy-peasy';
import React, { useState, useEffect } from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { useParams, Redirect } from 'react-router-dom';
import { makeStyles } from '@material-ui/core/styles';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import PersonAddIcon from '@material-ui/icons/PersonAdd';
import PersonIcon from '@material-ui/icons/Person';
import PersonAddDisabledIcon from '@material-ui/icons/PersonAddDisabled';
import { Box, Typography, Divider, Button, Grid, CircularProgress } from '@material-ui/core';
import { Link } from '../components/Link';
import axios from '../config/axiosConfig';
import { getUser } from '../utils/getUser';
import { getStaticUrl } from '../utils/getStaticUrl';

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
    flexWrap: 'wrap',
    justifyContent: 'space-around',
    backgroundColor: theme.palette.background.paper,
    flexShrink: 0,
  },
  gridList: {
    width: '100%',
  },
  icon: {
    color: 'rgba(255, 255, 255, 0.54)',
  },
  table: {
    maxWidth: 300,
  },
}));

export default function TitlebarGridList({ tileData }) {
  const classes = useStyles();
  return (
    <div className={classes.root}>
      <GridList className={classes.gridList} cols={3} spacing={24}>
        {tileData.map((tile) => {
          return (
            <GridListTile key={tile.imageSource}>
              <Link to={`/post/${tile.id}`}>
                <div
                  style={{
                    backgroundImage: `url(${tile.imageSource})`,
                    backgroundSize: 'cover',
                    backgroundPosition: '50%',
                    width: '100%',
                    height: '100%',
                    display: 'inline-block',
                  }}
                />
              </Link>
            </GridListTile>
          );
        })}
      </GridList>
    </div>
  );
}

export const Profile = () => {
  const { userId } = useParams();
  const classes = useStyles();
  const [followed, setFollowed] = useState(false);
  const [followHover, setFollowHover] = useState(false);
  const [userData, setUserData] = useState({ posts: [] });
  const [loading, setLoading] = useState(true);
  const [rows, setRows] = useState([]);
  const { token } = useStoreState((state) => state);
  const myId = getUser(token).userId;

  const followClick = () => {
    if (!followed) {
      setFollowed(true);
      setUserData((data) => ({ ...data, followerCount: data.followerCount + 1 }));
      axios.post(`/FollowService/follow/${userId}`);
    } else {
      setFollowed(false);
      setUserData((data) => ({ ...data, followerCount: data.followerCount - 1 }));
      axios.delete(`/FollowService/unfollow/${userId}`);
    }
  };

  useEffect(() => {
    axios.get(`/ProfileService/userprofile/${userId}`).then((res) => {
      setLoading(false);
      setUserData((data) => ({ ...data, ...res.data }));
      if (res.data.gender) setRows((r) => [...r, { name: 'Gender', value: res.data.gender }]);
      if (res.data.sports) setRows((r) => [...r, { name: 'Sports', value: res.data.sports }]);
      if (res.data.diet) setRows((r) => [...r, { name: 'Diet', value: res.data.diet }]);
      if (res.data.height) setRows((r) => [...r, { name: 'Height', value: res.data.height }]);
      if (res.data.weight) setRows((r) => [...r, { name: 'Weight', value: res.data.weight }]);
    });

    axios.get(`/FollowService/followersCount/${userId}`).then((res) => {
      setUserData((data) => ({ ...data, followerCount: res.data.count }));
    });

    axios.get(`/FollowService/followingCount/${userId}`).then((res) => {
      setUserData((data) => ({ ...data, followingCount: res.data.count }));
    });

    axios.get(`/FollowService/followers/${userId}`).then((res) => {
      if (res.data.followers.includes(Number(myId))) setFollowed(true);
    });

    axios.get(`/PostService/getPosts/${userId}`).then((res) => {
      const list = res.data.map((el) => ({
        ...el,
        imageSource: getStaticUrl(el.imageSource),
      })); // TODO

      setUserData((data) => ({ ...data, posts: list }));
    });
  }, []);

  if (!myId) return <Redirect to="/signin" />;
  if (loading)
    return (
      <Box mt={4} textAlign="center">
        <CircularProgress />
      </Box>
    );
  return (
    <Box mt={4}>
      <Grid container>
        <Grid item sm={12} md={4} className={classes.gridItem}>
          <Box textAlign="center">
            <div
              style={{
                backgroundImage: `url(${
                  userData.profilepicture
                    ? getStaticUrl(userData.profilepicture)
                    : '/defaultProfile.jpg'
                })`,
                backgroundSize: 'cover',
                backgroundPosition: '50%',
                width: 150,
                height: 150,
                borderRadius: '100%',
                display: 'inline-block',
              }}
            />
            <Box>
              <Typography variant="h2">{userData.firstName}</Typography>
            </Box>
          </Box>{' '}
        </Grid>

        <Grid item sm={12} md={8} className={classes.gridItem}>
          <Box p={2} display="flex" flexDirection="column" height="100%">
            <TableContainer component={Paper} className={classes.table}>
              <Table className={classes.table} size="small" aria-label="a dense table">
                <TableBody>
                  {rows.map((row) => (
                    <TableRow key={row.name}>
                      <TableCell component="th" scope="row">
                        <b>{row.name}</b>
                      </TableCell>
                      <TableCell align="right">{row.value}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
            <Box flexGrow="1" my={4}>
              <Grid container style={{ height: '100%' }}>
                <Grid item sm={12} md={4} className={classes.gridItem}>
                  <Box height="100%" display="flex" alignItems="center">
                    <b>{userData.followerCount || 0}</b>&nbsp;Followers
                  </Box>
                </Grid>
                <Grid item sm={12} md={4} className={classes.gridItem}>
                  <Box height="100%" display="flex" alignItems="center">
                    <b>{userData.posts.length}</b>&nbsp;Posts
                  </Box>
                </Grid>
                <Grid item sm={12} md={4} className={classes.gridItem}>
                  <Box height="100%" display="flex" alignItems="center">
                    <b>{userData.followingCount || 0}</b>&nbsp;Following
                  </Box>
                </Grid>
              </Grid>
            </Box>
            <Box display="flex" alignItems="flex-end">
              {userId != myId &&
                (!followed ? (
                  <Button startIcon={<PersonAddIcon />} onClick={followClick}>
                    Follow
                  </Button>
                ) : !followHover ? (
                  <Button
                    startIcon={<PersonIcon />}
                    onClick={followClick}
                    onMouseEnter={() => setFollowHover(true)}
                  >
                    Following
                  </Button>
                ) : (
                  <Button
                    startIcon={<PersonAddDisabledIcon />}
                    onClick={followClick}
                    onMouseLeave={() => setFollowHover(false)}
                  >
                    Unfollow
                  </Button>
                ))}
            </Box>
          </Box>
        </Grid>
      </Grid>
      <Divider />
      <Box mt={4}>
        <TitlebarGridList tileData={userData.posts} />
      </Box>
    </Box>
  );
};
