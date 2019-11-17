import {
  USER_LOADED,
  USER_LOADING,
  AUTH_ERROR,
  LOGIN_SUCCESS,
  LOGIN_FAIL,
  LOGOUT_SUCCESS,
  REGISTER_SUCCESS,
  REGISTER_FAIL
} from "../actions/types";

const initialState = {
  token: localStorage.getItem("token"),
  isAuthenticated: null,
  isLoading: false,
  user: null
};

export default function (state = initialState, action) {
  switch (action.type) {
    case USER_LOADING:
      return {
        ...state,
        isLoading: true
      };
    case USER_LOADED:
      console.log(action.payload);
      return {
        ...state,
        isAuthenticated: true,
        isLoading: false
      };
    case LOGIN_SUCCESS:
    case REGISTER_SUCCESS:
      // localStorage.setItem("token", action.payload.token);
      let token = action.payload.access_token;
      let decode = JSON.parse(atob(token.split('.')[1]));
      const menuTree = [{
        "funGrp": "ACT",
        "grpList": [
          {
            "funSubGrp": "ACTM01"
            , "funList": [{
              "fun": "actm0010",
              "desc": "自動扣繳帳號維護作業"
            }, {
              "fun": "actm0012",
              "desc": "外幣自動扣繳帳號維護"
            }]
          }
          , {
            "funSubGrp": "ACTP01"
            , "funList": [{
              "fun": "actp0010",
              "desc": "自動扣繳帳號維護作業"
            }, {
              "fun": "actp0015",
              "desc": "銀行農漁會整併自動扣繳帳號變更作業處理"
            }]
          }, {
            "funSubGrp": "ACTQ01"
            , "funList": [{
              "fun": "actq0020",
              "desc": "單月帳務資料查詢"
            }, {
              "fun": "actq0040",
              "desc": "流水帳務明細查詢"
            }]
          }, {
            "funSubGrp": "ACTR01"
            , "funList": [{
              "fun": "actr0016",
              "desc": "溢繳款超過美元四萬元自動啟帳及提出掛帳報表"
            }, {
              "fun": "actr0030",
              "desc": "報送JCIC帳戶繳款評等異動清單"
            }]
          }
        ]
      }, {
        "funGrp": "BIL",
        "grpList": [
          {
            "funSubGrp": "BILM01"
            , "funList": [{
              "fun": "bilm0020",
              "desc": "格式查核錯誤維護作業"
            }, {
              "fun": "bilm0070",
              "desc": "郵購/分期付款特約商店基本資料維護作業"
            }]
          }
          , {
            "funSubGrp": "BILP01"
            , "funList": [{
              "fun": "bilp0010",
              "desc": "帳單請款覆核作業"
            }, {
              "fun": "bilp0020",
              "desc": "郵購/分期付款特約商店基本資料覆核作業"
            }]
          }, {
            "funSubGrp": "BILQ01"
            , "funList": [{
              "fun": "bilq0010",
              "desc": "郵購/分期付款明細查詢"
            }, {
              "fun": "bilq0020",
              "desc": "暫存檔請款明細查詢"
            }]
          }, {
            "funSubGrp": "BILR01"
            , "funList": [{
              "fun": "bilr0010",
              "desc": "郵購/分期付款申購書覆核明細表"
            }, {
              "fun": "bilr0011",
              "desc": "分期付款未到期明細表"
            }]
          }
        ]
      }];
      return {
        ...state,
        userInfo: decode,
        menuTree: menuTree,
        isAuthenticated: true,
        isLoading: false
      };
    case AUTH_ERROR:
    case LOGIN_FAIL:
    case LOGOUT_SUCCESS:
    case REGISTER_FAIL:
      localStorage.removeItem("token");
      return {
        ...state,
        token: null,
        user: null,
        isAuthenticated: false,
        isLoading: false
      };
    default:
      return state;
  }
}