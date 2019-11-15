import React, { Component } from 'react';
import { Link, Redirect } from "react-router-dom";
import { connect } from "react-redux";

export class Welcome extends Component {
    render() {
        if (!this.props.isAuthenticated) {
            return <Redirect to="/login" />;
        }
        return (
            <div className="jumbotron">
                <h1 className="display-3">Welcome To ECS!</h1>
                <p className="lead"></p>
                <hr className="my-4"></hr>
                <p></p>
                <p className="lead"><a className="btn btn-primary btn-lg" href="#" role="button">取得用戶資料</a></p>
                <p className="lead"><a className="btn btn-primary btn-lg" href="#" role="button">測試負載平衡</a></p>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    isAuthenticated: state.auth.isAuthenticated
});
export default connect(
    mapStateToProps,{}
)(Welcome);
