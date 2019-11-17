import axios from "axios";
import { returnErrors } from "./messages";
import { LOAD_TEST } from "./types";

export const loadTest = () => dispatch => {
       axios
        .get("/api/ecs-example-service/public/welcome")
        .then(res => {
            dispatch({
                type: LOAD_TEST,
                payload: res.data
            });
        })
        .catch(err => {
            dispatch(returnErrors(err.response.data, err.response.status));
        });
};

