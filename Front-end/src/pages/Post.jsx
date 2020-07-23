/* eslint-disable react/prop-types */
import React, { useEffect, useState } from 'react';
import { useParams, Redirect } from 'react-router-dom';
import {
  Button,
  Divider,
  Box,
  Avatar,
  CardHeader,
  Checkbox,
  CircularProgress,
} from '@material-ui/core';
import FavoriteIcon from '@material-ui/icons/Favorite';
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import { useForm } from 'react-hook-form';
import { useStoreState } from 'easy-peasy';
import { Link } from '../components/Link';
import axios from '../config/axiosConfig';
import { getUser } from '../utils/getUser';
import { getStaticUrl } from '../utils/getStaticUrl';
import { getFormattedDate } from '../utils/getFormattedDate';
import { getUserName } from '../utils/getUserName';

const useStyles = makeStyles((theme) => ({
  root: {
    textAlign: 'left',
    flexGrow: 1,
  },
  paper: {
    padding: theme.spacing(2),
    color: theme.palette.text.secondary,
  },
  comment: {
    textAlign: 'left',
  },
  divider: {
    margin: theme.spacing(1, 0),
  },
  commentForm: {
    marginTop: theme.spacing(4),
  },
  image: {
    maxWidth: '100%',
  },
  gridItem: {
    padding: theme.spacing(2),
  },
  sumbit: {
    margin: theme.spacing(1, 0),
  },
}));

const Comment = ({ data }) => {
  const classes = useStyles();
  const [name, setName] = useState('name');

  useEffect(() => {
    getUserName(data.userId).then((n) => {
      setName(n);
    });
  }, []);

  return (
    <div className={classes.comment}>
      <Typography color="textPrimary" variant="body2" component="span">
        <Link to={`/profile/${data.userId}`}>{name}</Link>
      </Typography>
      {' - '}
      <Typography color="textSecondary" variant="body2" component="span">
        {data.text}
      </Typography>
    </div>
  );
};

export const Post = () => {
  const { postId } = useParams();
  const [postData, setPostData] = useState({ comments: [] });
  const { register, handleSubmit } = useForm();
  const { token } = useStoreState((state) => state);
  const myId = getUser(token).userId;
  const [name, setName] = useState('...');
  const [likes, setLikes] = useState(0);
  const [liked, setLiked] = useState(false);
  const [loading, setLoading] = useState(true);

  const onSubmit = (data) => {
    axios.post(`PostService/createComment`, {
      postId,
      ...data,
    });

    setPostData((d) => ({ ...d, comments: [...d.comments, { text: data.text, userId: myId }] }));
  };

  useEffect(() => {
    axios.get(`PostService/getPost/${postId}`).then((res) => {
      setPostData((data) => ({
        ...data,
        ...res.data,
        imageSource: getStaticUrl(res.data.imageSource),
      }));
      setLikes(res.data.likes);
      setLoading(false);
      getUserName(res.data.userId).then((n) => {
        setName(n);
      });
    });
    axios.get(`/PostService/getPostLikes/${postId}`).then((res) => {
      if (res.data.userIdList.includes(Number(getUser(token).userId))) setLiked(true);
    });
    axios.get(`PostService/getComments/${postId}`).then((res) => {
      const { comments } = res.data;
      comments.reverse();
      setPostData((data) => ({ ...data, comments }));
    });
  }, []);

  const classes = useStyles();

  const handleLikeClick = () => {
    axios.post(`PostService/likeToggle/${postId}`);
    setLikes((l) => (liked ? l - 1 : l + 1));
    setLiked((l) => !l);
  };
  if (!myId) return <Redirect to="/signin" />;
  if (loading)
    return (
      <Box mt={4} textAlign="center">
        <CircularProgress />
      </Box>
    );
  return (
    <div className={classes.root}>
      <Grid container>
        <Grid item sm={12} md={8} className={classes.gridItem}>
          <Paper className={classes.paper}>
            <Box display="flex" alignItems="center" fontWeight="fontWeightBold">
              <CardHeader
                avatar={
                  <Avatar aria-label="recipe" className={classes.avatar}>
                    {name.charAt(0).toUpperCase()}
                  </Avatar>
                }
                title={
                  <Link to={`/profile/${postData.userId}`}>
                    <Typography variant="body1" color="textPrimary">
                      {name}
                    </Typography>
                  </Link>
                }
                subheader={getFormattedDate(postData.created)}
              />
              <Box display="flex" alignItems="center" justifyContent="flex-end" flexGrow="1" pr={2}>
                <Checkbox
                  icon={<FavoriteBorderIcon />}
                  checkedIcon={<FavoriteIcon />}
                  checked={liked}
                  onClick={handleLikeClick}
                  name="checkedH"
                />
                {likes || 0}
              </Box>
            </Box>
            <Divider variant="middle" />
            <Box display="flex" alignItems="center" justifyContent="center" mb={1} mt={2}>
              <img src={postData.imageSource} alt="lizard" className={classes.image} />
            </Box>
            <Box textAlign="center">
              <Typography variant="caption">{postData.text}</Typography>
            </Box>
          </Paper>
        </Grid>
        <Grid item sm={12} md={4} className={classes.gridItem}>
          <Paper className={classes.paper}>
            {postData.comments.map((comment) => (
              <>
                <Comment data={comment} />
                <Divider variant="middle" className={classes.divider} />
              </>
            ))}
            <form className={classes.commentForm} onSubmit={handleSubmit(onSubmit)}>
              <TextField
                id="outlined-multiline-static"
                label="Comment"
                multiline
                name="text"
                rows={2}
                variant="outlined"
                fullWidth
                className={classes.formElement}
                inputRef={register({ required: true })}
              />
              <Button variant="outlined" className={classes.sumbit} type="submit">
                Submit
              </Button>
            </form>
          </Paper>
        </Grid>
      </Grid>
    </div>
  );
};
