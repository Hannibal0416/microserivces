import React, { Component } from 'react';
import { Link, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import { loadUser } from "../../actions/auth";
import { loadTest } from "../../actions/load";

export class Welcome extends Component {

    render() {
        if (!this.props.isAuthenticated) {
            return <Redirect to="/login" />;
        }
        console.log(this.props.profile);
        let message = this.props.message;
        return (
            <div className="jumbotron">
                <h1 className="display-3">Welcome To ECS!</h1>
                <p className="lead" >
                    {this.props.profile}
                    <br/>
                    
                </p>
                <hr className="my-4"></hr>
                <p>{Object.entries(message).length !== 0?message:""}</p>
                <p className="lead"><button className="btn btn-primary btn-lg" onClick={this.props.loadUser}>取得用戶資料</button></p>
                <p className="lead"><button className="btn btn-primary btn-lg" onClick={this.props.loadTest}>測試負載平衡</button></p>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    isAuthenticated: state.auth.isAuthenticated,
    message: state.load.msg,
    profile: state.auth.profile
});
export default connect(
    mapStateToProps,{loadUser,loadTest}
)(Welcome);
