/* eslint-disable react/prop-types */
import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import { useStoreState } from 'easy-peasy';
import { Link } from 'react-router-dom';
import { CardHeader, Avatar, CardActions, Checkbox } from '@material-ui/core';
import FavoriteIcon from '@material-ui/icons/Favorite';
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import { getFormattedDate } from '../utils/getFormattedDate';
import { getUserName } from '../utils/getUserName';
import { getStaticUrl } from '../utils/getStaticUrl';
import axios from '../config/axiosConfig';
import { getUser } from '../utils/getUser';

const useStyles = makeStyles((theme) => ({
  root: {
    maxWidth: 450,
    marginTop: 30,
  },
  iconRight: {
    marginLeft: 'auto',
  },
  noUnderline: {
    textDecoration: 'none',
  },
  link: { textDecoration: 'none', color: theme.palette.text.primary },
}));

export function Post({ data }) {
  const classes = useStyles();
  const [name, setName] = useState('...');
  const [likes, setLikes] = useState(data.likes);
  const [liked, setLiked] = useState(false);
  const { token } = useStoreState((state) => state);
  useEffect(() => {
    getUserName(data.userId).then((n) => {
      setName(n);
    });
    axios.get(`/PostService/getPostLikes/${data.id}`).then((res) => {
      if (res.data.userIdList.includes(Number(getUser(token).userId))) setLiked(true);
    });
  }, []);

  const handleLikeClick = () => {
    axios.post(`PostService/likeToggle/${data.id}`);
    setLikes((l) => (liked ? l - 1 : l + 1));
    setLiked((l) => !l);
  };

  return (
    <Card className={classes.root}>
      <CardHeader
        avatar={
          <Avatar aria-label="recipe" className={classes.avatar}>
            {name.charAt(0).toUpperCase()}
          </Avatar>
        }
        title={
          <Link to={`/profile/${data.userId}`} className={classes.link}>
            {name}
          </Link>
        }
        subheader={getFormattedDate(data.timestamp)}
      />
      <Link to={`/post/${data.id}`} className={classes.noUnderline}>
        <CardActionArea>
          <CardMedia
            component="img"
            alt="Contemplative Reptile"
            height="140"
            image={getStaticUrl(data.imageSource)}
            title="Contemplative Reptile"
          />

          <CardContent>
            <Typography variant="body2" color="textSecondary" component="p">
              {data.text}
            </Typography>
          </CardContent>
        </CardActionArea>
      </Link>
      <CardActions disableSpacing>
        <Checkbox
          icon={<FavoriteBorderIcon />}
          checkedIcon={<FavoriteIcon />}
          name="checkedH"
          checked={liked}
          onClick={handleLikeClick}
        />
        {likes}
        {/* <IconButton aria-label="add to favorites" className={classes.iconRight}>
          <CommentIcon />
        </IconButton> */}
      </CardActions>
    </Card>
  );
}
