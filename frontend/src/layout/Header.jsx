import React, { Component, Fragment } from 'react';
import './Header.css'
import { connect } from "react-redux";
import { logout } from "../actions/auth";

export class Header extends Component{

    genMenu = () => {
        let menuTree = this.props.menuTree;
        console.log(menuTree)
        const menu = menuTree.map(obj => (<li key={obj.funGrp} className="dropdown-submenu">
            <a className="dropdown-toggle dropdown-item" href="#">{obj.funGrp}</a>
            <ul className="dropdown-menu">
                {obj.grpList.map(subObj =>
                    (<li className="dropdown-submenu" key={subObj.funSubGrp}><a className="dropdown-item" href="#">{subObj.funSubGrp}</a>
                        <ul className="dropdown-menu">
                            {subObj.funList.map(fun => <a className="dropdown-item" href="#" key={fun.fun}>{fun.fun}-{fun.desc}</a>)}
                        </ul>
                    </li>)
                )}
            </ul>
        </li>));
        return menu;
    }

    render() {
        let menu;
        if (this.props.isAuthenticated) {
            menu = <li className="nav-item dropdown">
                    <a id="navbarDropdown" className="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">功能</a>
                    <ul className="dropdown-menu multi-level" aria-labelledby="navbarDropdown">
                        {this.genMenu()}
                    </ul>
                </li>;
        }

        return (

            <nav className="navbar navbar-expand-lg navbar-dark fixed-top bg-primary" >
                <a className="navbar-brand" href="#">ECS</a>
                <button className="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="navbar-collapse collapse" id="navbarColor01" >
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item">
                            <a className="nav-link" href="#">首頁 <span className="sr-only">(current)</span></a>
                        </li>
                        {menu}


                        {/* <li className="nav-item dropdown">
                            <a id="navbarDropdown" className="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">功能</a>
                            <div className="dropdown-menu multi-level" aria-labelledby="navbarDropdown">
                                <a className="dropdown-item" href="#">主要功能</a>
                                <div className="dropdown-divider"></div>
                                <a className="dropdown-item" href="#">Another action</a>
                                <li className="dropdown-submenu"><a className="dropdown-toggle dropdown-item" href="#">Even More..</a>
                                    <ul className="dropdown-menu">
                                        <li className="dropdown-item"><a href="#">3rd level</a></li>
                                        <li className="dropdown-submenu"><a className="dropdown-item" href="#">another level</a>
                                            <ul className="dropdown-menu">
                                                <li className="dropdown-item"><a href="#">4th level</a></li>
                                                <li className="dropdown-item"><a href="#">4th level</a></li>
                                                <li className="dropdown-item"><a href="#">4th level</a></li>
                                            </ul>
                                        </li>
                                        <a className="dropdown-item" href="#">3rd level</a>
                                    </ul>
                                </li>
                            </div>
                        </li> */}


                        <li className="nav-item">
                            <a className="nav-link" href="#">公告事項</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="#">關於...</a>
                        </li>
                    </ul>
                    <button className="btn btn-outline-secondary btn-sm my-2 my-sm-0" onClick={this.props.logout}>登出</button> |
                    <form className="form-inline my-2 my-lg-0">
                        <input className="form-control mr-sm-2" type="text" placeholder="Search"></input>
                        <button className="btn btn-outline-secondary btn-lg  my-2 my-sm-0" type="submit">搜尋</button>
                    </form>

                </div>
            </nav>
        );
    }
}
 
const mapStateToProps = state => ({
    isAuthenticated: state.auth.isAuthenticated,
    menuTree: state.auth.menuTree,
    userInfo: state.auth.userInfo
});
export default connect(
    mapStateToProps,{logout}
)(Header);