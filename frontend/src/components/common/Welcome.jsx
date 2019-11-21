import React, { Component } from 'react';
import { Link, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import { loadUser } from "../../actions/auth";
import { loadTest } from "../../actions/load";

export class Welcome extends Component {

    genTable = () => {

        let table = 
        <div className="card border-primary mb-3" style={{maxWidth: "20 rem"}}>
            <div className="card-header">{this.props.profile}</div>
            <div className="card-body">
                <table className="table table-hover">
                    <thead>
                        <tr className="table-info">
                            <th scope="col">User Name</th>
                            <th scope="col">Client ID</th>
                            <th scope="col">Scope</th>
                            <th scope="col">Authority</th>
                        </tr>
                        
                    </thead>
                    <tbody>
                        <tr>
                            <td>{this.props.userInfo.user_name}</td>
                            <td>{this.props.userInfo.client_id}</td>
                            <td>{this.props.userInfo.scope}</td>
                            <td>{this.props.userInfo.authorities[0]}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>;
        return table;
    }

    render() {
        if (!this.props.isAuthenticated) {
            return <Redirect to="/login" />;
        }
        let message = this.props.message;
        let tableInfo = "";
        if(this.props.profile) {
            tableInfo = this.genTable();
        }
        
        
        return (
            <div className="jumbotron">
                <h1 className="display-3">Welcome To ECS!</h1>
                <br/><br/>
                {tableInfo}
                
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
    profile: state.auth.profile,
    userInfo: state.auth.userInfo
});
export default connect(
    mapStateToProps,{loadUser,loadTest}
)(Welcome);
