import { LOAD_TEST } from "../actions/types";

const initialState = {
  msg: {},
};

export default function(state = initialState, action) {
  switch (action.type) {
    case LOAD_TEST:
      return {
        msg: action.payload
      };
    default:
      return state;
  }
}