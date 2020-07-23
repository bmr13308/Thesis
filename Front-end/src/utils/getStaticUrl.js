import { STATIC_SERVER } from '../config';

export const getStaticUrl = (badUrl) => {
  const imageName = badUrl.split('/').slice(-1)[0];
  return `http://${STATIC_SERVER}/${imageName}`;
};
