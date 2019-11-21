import axios from "axios";
import { returnErrors } from "./messages";
import Cookies from 'js-cookie';

import {
  USER_LOADED,
  USER_LOADING,
  AUTH_ERROR,
  LOGIN_SUCCESS,
  LOGIN_FAIL,
  LOGOUT_SUCCESS,
  REGISTER_SUCCESS,
  REGISTER_FAIL
} from "./types";

// CHECK TOKEN & LOAD USER
export const loadUser = () => (dispatch, getState) => {
  const transport = axios.create({
    withCredentials: true
});
console.log(Cookies.get("jti"));
console.log('access'+Cookies.get("access_token"));
  axios
    .post("/api/ecs-example-service/user/profile",{
        withCredentials: true
    })
    .then(res => {
      dispatch({
        type: USER_LOADED,
        payload: res.data
      });
    })
    .catch(err => {
      dispatch(returnErrors(err.response.data, err.response.status));
    });
};

// LOGIN USER
export const login = (username, password) => dispatch => {
  //   Headers
  const config = {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    }
  };

  // Request Body
  //   const body = JSON.stringify({ username, password });
  var bodyFormData = new FormData();
  bodyFormData.set("username", username);
  bodyFormData.set("password", password);
  axios
    .post("/api/ecs-oauth-service/oauth/token", bodyFormData, config)
    .then(res => {
      dispatch({
        type: LOGIN_SUCCESS,
        payload: res.data
      });
    })
    .catch(err => {
      console.log(err);
      dispatch(returnErrors(err.response.data, err.response.status));
      dispatch({
        type: LOGIN_FAIL
      });
    });
};

// REGISTER USER
export const register = ({ username, password, email }) => dispatch => {
  // Headers
  const config = {
    headers: {
      "Content-Type": "application/json"
    }
  };

  // Request Body
  const body = JSON.stringify({ username, email, password });

  axios
    .post("/api/auth/register", body, config)
    .then(res => {
      dispatch({
        type: REGISTER_SUCCESS,
        payload: res.data
      });
    })
    .catch(err => {
      dispatch(returnErrors(err.response.data, err.response.status));
      dispatch({
        type: REGISTER_FAIL
      });
    });
};

// LOGOUT USER
export const logout = () => (dispatch, getState) => {
  dispatch({
    type: LOGOUT_SUCCESS
  });
  // axios
  //   .post("/api/auth/logout/", null, tokenConfig(getState))
  //   .then(res => {
  //     dispatch({ type: "CLEAR_LEADS" });
  //     dispatch({
  //       type: LOGOUT_SUCCESS
  //     });
  //   })
  //   .catch(err => {
  //     dispatch(returnErrors(err.response.data, err.response.status));
  //   });
};

// Setup config with token - helper function
export const tokenConfig = getState => {
  // Get token from state
  const token = getState().auth.token;

  // Headers
  const config = {
    headers: {
      "Content-Type": "application/json"
    }
  };

  // If token, add to headers config
  if (token) {
    config.headers["Authorization"] = `Token ${token}`;
  }

  return config;
};
