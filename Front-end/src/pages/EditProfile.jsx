import React, { useState, useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import { useStoreState } from 'easy-peasy';
import { makeStyles } from '@material-ui/core/styles';
import { Box, Button, Divider, TextField, CircularProgress, IconButton } from '@material-ui/core';
import { useForm } from 'react-hook-form';
import EyeIcon from '@material-ui/icons/Visibility';
import axios from '../config/axiosConfig';
import { getUser } from '../utils/getUser';
import { getStaticUrl } from '../utils/getStaticUrl';
import { Link } from '../components/Link';

const useStyles = makeStyles((theme) => ({
  submit: {
    marginTop: theme.spacing(2),
  },
}));

export const EditProfile = () => {
  const classes = useStyles();
  const { register, handleSubmit, watch } = useForm();
  const { token } = useStoreState((state) => state);
  const [userData, setUserData] = useState({});
  const [imagePreviewUrl, setImagePreviewUrl] = useState('');
  const [loading, setLoading] = useState(true);
  const myId = getUser(token).userId;
  useEffect(() => {
    axios.get(`/ProfileService/userprofile/${myId}`).then((res) => {
      setUserData((data) => ({ ...data, ...res.data }));
      setLoading(false);
    });
  }, []);

  const reader = new FileReader();

  reader.onloadend = () => {
    setImagePreviewUrl(reader.result);
  };

  const imagefile = watch('imageFile');
  if (imagefile && imagefile[0]) reader.readAsDataURL(imagefile[0]);

  function onSubmit(data) {
    axios.put('/ProfileService/editProfile', {
      ...data,
    });

    if (!(imagefile && imagefile[0])) return;
    const file = new Blob([imagefile[0]]);
    const formData = new FormData();
    formData.append('imageFile', file, file.filename);

    axios.post('/ProfileService/uploadProfilePicture', formData);
  }
  if (!myId) return <Redirect to="/signin" />;
  if (loading)
    return (
      <Box mt={4} textAlign="center">
        <CircularProgress />;
      </Box>
    );
  const profileUrl = userData.profilepicture && getStaticUrl(userData.profilepicture);
  return (
    <div>
      <h1>
        My Profile{' '}
        <Link to={`/profile/${myId}`}>
          <IconButton aria-label="upload picture" component="span">
            <EyeIcon />
          </IconButton>
        </Link>
      </h1>

      <Box display="flex" alignItems="center" pb={2}>
        <Box display="flex" flexDirection="Column">
          <div
            style={{
              backgroundImage: `url(${imagePreviewUrl || profileUrl || '/defaultProfile.jpg'})`,
              backgroundSize: 'cover',
              backgroundPosition: '50%',
              width: 150,
              height: 150,
              borderRadius: '100%',
              display: 'inline-block',
            }}
          />
          <Button component="label">
            Change Avatar
            <input
              name="imageFile"
              type="file"
              style={{ display: 'none' }}
              accept="image/*"
              ref={register}
            />
          </Button>
        </Box>

        {userData.firstName && (
          <form onSubmit={handleSubmit(onSubmit)}>
            <Box px={4}>
              <TextField
                id="date"
                name="date"
                type="hidden"
                className={classes.textField}
                InputLabelProps={{
                  shrink: true,
                }}
                defaultValue={userData.birth}
                inputRef={register}
              />
              <br />
              <TextField
                variant="outlined"
                margin="normal"
                label="First Name"
                defaultValue={userData.firstName}
                name="firstName"
                inputRef={register}
              />
              <TextField
                variant="outlined"
                margin="normal"
                label="Last Name"
                name="lastName"
                defaultValue={userData.lastName}
                inputRef={register}
              />

              <TextField
                variant="outlined"
                margin="normal"
                label="Gender"
                defaultValue={userData.gender}
                name="gender"
                inputRef={register}
              />
              <TextField
                variant="outlined"
                margin="normal"
                label="Sports"
                defaultValue={userData.sports}
                name="sports"
                inputRef={register}
              />
              <TextField
                variant="outlined"
                margin="normal"
                label="Diet"
                defaultValue={userData.diet}
                name="diet"
                inputRef={register}
              />
              <TextField
                variant="outlined"
                margin="normal"
                label="Height"
                defaultValue={userData.height}
                name="height"
                inputRef={register}
              />
              <TextField
                variant="outlined"
                margin="normal"
                label="Weight"
                defaultValue={userData.weight}
                name="weight"
                inputRef={register}
              />
            </Box>
            <Box px={4}>
              <Button variant="contained" color="primary" type="submit">
                Update
              </Button>
            </Box>
          </form>
        )}
      </Box>
      <Divider />
      <Box maxWidth="400px">
        {/* <h2>Change password</h2>
        <form>
          <TextField
            variant="outlined"
            required
            fullWidth
            margin="normal"
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
          />
          <TextField
            variant="outlined"
            required
            fullWidth
            margin="normal"
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
          />
          <Button type="submit" className={classes.submit} variant="contained" color="primary">
            Update
          </Button>
        </form> */}
      </Box>
    </div>
  );
};
