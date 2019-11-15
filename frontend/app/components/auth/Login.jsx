import React, { Component } from 'react';
import PropTypes from "prop-types";
import { login } from "../../actions/auth";
import { connect } from "react-redux";
import { Link, Redirect } from "react-router-dom";

export class Login extends Component {
    state = {
        username: "",
        password: ""
    };

    static propTypes = {
        login: PropTypes.func.isRequired,
        isAuthenticated: PropTypes.bool
    };

    onSubmit = e => {
        e.preventDefault();
        this.props.login(this.state.username, this.state.password);
    };

    onChange = e => this.setState({ [e.target.name]: e.target.value });


    render() {
        if (this.props.isAuthenticated) {
            return <Redirect to="/" />;
        }
        return (
            <div className="card border-success mb-3" style={{ maxWidth: "20 rem" }}>
                <div className="card-header">登入</div>
                <div className="card-body">
                    <form onSubmit={this.onSubmit}>
                        <fieldset>
                            <legend>請輸入您的帳號</legend>
                            <div className="form-group">
                                <label htmlFor="inputUsername">帳號</label>
                                <input type="username" className="form-control" id="inputUsername" name="username"
                                    aria-describedby="usernameHelp" placeholder="Username" onChange={this.onChange} />
                                <small id="usernameHelp" className="form-text text-muted">Username</small>
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputPassword">密碼</label>
                                <input type="password" className="form-control" id="inputPassword" name="password"
                                    placeholder="Password" onChange={this.onChange} />
                            </div>
                            <div className="form-group">
                                <button type="submit" className="btn btn-outline-primary" >登入</button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    isAuthenticated: state.auth.isAuthenticated
});
export default connect(
    mapStateToProps,
    { login }
)(Login);
