import axios from '../config/axiosConfig';

export async function getUserName(userId) {
  const { data } = await axios.get(`/ProfileService/userprofile/${userId}`);

  return `${data.firstName} ${data.lastName}`;
}
