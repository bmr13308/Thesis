/* eslint-disable react/prop-types */
import React, { useState, useEffect } from 'react';
import { useStoreState } from 'easy-peasy';
import { Redirect } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';
import AddIcon from '@material-ui/icons/Add';
import { makeStyles, Box, Typography, Divider, CircularProgress } from '@material-ui/core';
import { useForm } from 'react-hook-form';
import { Post } from '../components/PostPreview';
import { getUser } from '../utils/getUser';
import axios from '../config/axiosConfig';

const useStyles = makeStyles((theme) => ({
  newPost: {
    padding: theme.spacing(2),
    alignSelf: 'center',
  },
  formElement: {
    marginTop: theme.spacing(2),
  },
}));

const NewPost = ({ callback }) => {
  const classes = useStyles();
  const { register, handleSubmit, watch } = useForm();
  const [imagePreviewUrl, setImagePreviewUrl] = useState('');

  const reader = new FileReader();

  reader.onloadend = () => {
    setImagePreviewUrl(reader.result);
  };

  const imagefile = watch('imageFile');
  if (imagefile && imagefile[0]) reader.readAsDataURL(imagefile[0]);

  const onSubmit = (data) => {
    const file = new Blob([data.imageFile[0]]);
    const formData = new FormData();
    formData.append('imageFile', file, file.filename);
    formData.append('text', data.text);
    axios.post(`PostService/createPost`, formData).then(() => {
      callback();
    });
  };
  return (
    <Paper className={classes.newPost}>
      <Box>
        <Typography variant="h5" component="h5">
          Create Post
        </Typography>
      </Box>
      <Divider />
      <Box pt={3}>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Button component="label">
            <AddIcon />
            Upload photo
            <input
              name="imageFile"
              type="file"
              style={{ display: 'none' }}
              accept="image/*"
              ref={register}
            />
          </Button>
          <TextField
            name="text"
            id="outlined-multiline-static"
            label="Description"
            multiline
            rows={4}
            variant="outlined"
            fullWidth
            className={classes.formElement}
            inputRef={register({ required: true })}
          />

          <Button variant="contained" type="submit" className={classes.formElement} color="primary">
            Post
          </Button>
        </form>
        <Box mt={2}>{imagefile && imagefile[0] && <img src={imagePreviewUrl} alt="Preview" />}</Box>
      </Box>
    </Paper>
  );
};

export function Feed() {
  const { token } = useStoreState((state) => state);
  const user = getUser(token);
  const [open, setOpen] = useState(false);
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get(`/FeedService/getfeed?lastPostId=0`, { lastPostId: 0 }).then((res) => {
      setPosts((p) => [...p, ...res.data.posts]);
      setLoading(false);
    });
  }, []);
  if (!user.userId) {
    return <Redirect to="/signin" />;
  }

  return (
    <Box display="flex" flexDirection="column" alignItems="center" pt={4}>
      {open ? (
        <NewPost callback={() => setOpen(false)} />
      ) : (
        <Button onClick={() => setOpen(true)}>
          <AddIcon />
          New Post
        </Button>
      )}
      {loading && <CircularProgress />}
      {posts.map((post) => (
        <Post data={post} key={post.id} />
      ))}

      {/* <Button to="/signin" color="primary">
        More
      </Button> */}
    </Box>
  );
}
