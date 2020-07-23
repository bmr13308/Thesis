import jwtdecode from 'jwt-decode';

export const getUser = (token) => {
  let user;
  try {
    user = jwtdecode(token);
  } catch (error) {
    user = { id: 0 };
  }

  return user;
};
