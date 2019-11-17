import { combineReducers } from "redux";

import auth from "./auth";
import errors from "./errors";
import load from "./load";
export default combineReducers({
  auth,
  errors,
  load
});