/* eslint-disable react/prop-types */
/* eslint-disable react/destructuring-assignment */
/* eslint-disable react/jsx-props-no-spreading */
import React from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { makeStyles } from '@material-ui/core';

const useStyles = makeStyles({
  link: {
    color: 'inherit',
    textDecoration: 'inherit',
  },
});

export const Link = (props) => {
  const classes = useStyles();

  return <RouterLink {...props} className={`${classes.link} ${props.className}`} />;
};
