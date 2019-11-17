import React, { Component } from 'react';
import { Link, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import { loadUser } from "../../actions/auth";
import { loadTest } from "../../actions/load";

export class Welcome extends Component {

    state = {
        message : this.props.message
    };
    static getDerivedStateFromProps(props, state) {
        if (props.message !== state.message) {
            return {
                message: props.message
            };
        }
        return null;
    }

    render() {
        if (!this.props.isAuthenticated) {
            return <Redirect to="/login" />;
        }
        const message = this.state.message;
        return (
            <div className="jumbotron">
                <h1 className="display-3">Welcome To ECS!</h1>
                <p className="lead">{message} </p>
                <hr className="my-4"></hr>
                <p></p>
                <p className="lead"><button className="btn btn-primary btn-lg" onClick={this.props.loadUser}>取得用戶資料</button></p>
                <p className="lead"><button className="btn btn-primary btn-lg" onClick={this.props.loadTest}>測試負載平衡</button></p>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    isAuthenticated: state.auth.isAuthenticated,
    message: state.load.msg
});
export default connect(
    mapStateToProps,{loadUser,loadTest}
)(Welcome);
