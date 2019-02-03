import React from 'react'
import styles from './LoginContainer.scss'
import loginBg from '../resource/img/loginBg.jpg'
import { Icon, Input, Button, message } from 'antd'
import { Redirect } from 'react-router'
import { baseURL, API } from '../config'
class LoginContainer extends React.Component{
    state = {
        loginWarning: false,
        registerEmailWarning: -1, //邮箱格式检测
        registerEmailText: '',
        registerNameWarning: -1, //昵称检测
        registerNameText: '',
        registerPwWarning: -1,  //密码检测
        registerPdText: '',
        registering: false,
        registerEmail: '',
        registerName: '',
        registerPw: '',
        loginEmail: '',
        loginPw: '',

    }
    handleRegister = () => {
        let t = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;

        if (!t.test(this.state.registerEmail) || this.state.registerName == '' || this.state.registerPw == ''){
            return
        }
        else {
            fetch('http://cangwu.art:9000/user/register', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: this.state.registerEmail,
                    username: this.state.registerName,
                    password: this.state.registerPw,
                })
            }).then((res) => res.json()).then((json) => {
                if (json.code == 0){
                    localStorage.setItem('auth', JSON.stringify({
                        email: json.data.email,
                        token: json.data.token,
                        username: json.data.username,
                        id: json.data.id,
                    }))
                    this.props.history.push('/index')
                    message.success('注册成功',0.5)
                }
                else {
                    message.error('注册失败，网络错误',0.5)
                }

            })
        }

    }
    handleLogin = () => {
        if (this.state.loginEmail == '' || this.state.loginPw == ''){
            this.setState({
                loginWarning: true,
            })
            return
        }
        fetch('http://cangwu.art:9000/user/login', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: this.state.loginEmail,
                password: this.state.loginPw,
            })
        }).then((res) => res.json()).then((json) => {
            if (json.code == 0){
                fetch('http://cangwu.art:9000/user/', {
                    method: 'GET',
                    headers: {
                        'Authorization': json.data,
                    }
                }).then((res) => res.json()).then((info) => {
                    if (info.code == 0){
                        localStorage.setItem('auth', JSON.stringify({
                            email: info.data.email,
                            token: json.data,
                            id: info.data.id,
                            username: info.data.username,
                        }))
                        this.props.history.push('/index')
                    }
                    else {
                        message.error('用户信息获取失败，请重新登录',0.5)
                    }
                })
            }
            else {
                this.setState({
                    loginWarning: true,
                })
            }
        })
    }
    checkEmail = (e) => {
        let t = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
        if (t.test(e.target.value)){
            this.setState({
                registerEmailWarning: 0,
                registerEmailText: '',
            })
        }
        else {
            this.setState({
                registerEmailWarning: 1,
                registerEmailText: '邮箱格式不正确',
            })
        }
    }
    checkName = (e) => {
        if (e.target.value == ''){
            this.setState({
                registerNameWarning: 1,
                registerNameText: '昵称不能为空',
            })
        }
        else {
            this.setState({
                registerNameWarning: 0,
                registerNameText: '',
            })
        }
    }
    checkPassword = (e) => {
        if (e.target.value == ''){
            this.setState({
                registerPwWarning: 1,
                registerPwText: '密码不能为空',
            })
        }
        else {
            this.setState({
                registerPwWarning: 0,
                registerPwText: '',
            })
        }
    }
    renderRegister = () => {
        return <div className={styles.content}>
            <div className={styles.row}>
                <Input placeholder="请输入邮箱" onBlur={this.checkEmail} onChange={(e) => {this.setState({ registerEmail: e.target.value })}} value={this.state.registerEmail}/>
                {
                    this.state.registerEmailWarning == 0 &&  <Icon className={styles.check} type="check" />
                }
                {
                    this.state.registerEmailWarning == 1 && <span className={styles.warning}>{this.state.registerEmailText}</span>
                }
            </div>
            <div className={styles.row}>
                <Input placeholder="请输入昵称" onBlur={this.checkName} value={this.state.registerName} onChange={(e) => { this.setState({ registerName: e.target.value})}}/>
                {
                    this.state.registerNameWarning == 0 &&  <Icon className={styles.check} type="check" />
                }
                {
                    this.state.registerNameWarning == 1 && <span className={styles.warning}>{this.state.registerNameText}</span>
                }
            </div>
            <div className={styles.row}>
                <Input.Password placeholder="请输入密码" onBlur={this.checkPassword} onChange={(e) => {this.setState({ registerPw: e.target.value})}} value={this.state.registerPw} onKeyDown={(e) => {
                    if (e.keyCode == 13){
                        this.handleRegister()
                    }
                }}/>
                {
                    this.state.registerPwWarning == 0 &&  <Icon className={styles.check} type="check" />
                }
                {
                    this.state.registerPwWarning == 1 && <span className={styles.warning}>{this.state.registerPwText}</span>
                }
            </div>
            <div className={styles.row}>
                <Button type="primary" onClick={this.handleRegister}>注册</Button>
            </div>
            <div className={styles.row}>
                <Button onClick={() => {this.setState({
                    registerEmailWarning: -1, //邮箱格式检测
                    registerEmailText: '',
                    registerNameWarning: -1, //昵称检测
                    registerNameText: '',
                    registerPwWarning: -1,  //密码检测
                    registerPdText: '',
                    registering: false,
                    registerEmail: '',
                    registerName: '',
                    registerPw: '',
                })}}>返回登录</Button>
            </div>
        </div>
    }
    render(){
        // 需要判断是否已经登录 如果已经登录 需要跳转
        return <div className={styles.loginContainer}>
            <div className={styles.header}>
                <img src={loginBg} />
                <div className={styles.title}>
                    <Icon type="coffee" />
                    <span>Story-Map</span>
                </div>
            </div>
            {
                this.state.registering ? this.renderRegister() : <div className={styles.content}>
                    <div className={styles.row}>
                        <Input placeholder="邮箱" value={this.state.loginEmail} onChange={(e) => {this.setState({ loginEmail: e.target.value})}} />
                        {
                            this.state.loginWarning && <span className={styles.warning}>登录名或密码错误</span>
                        }
                    </div>
                    <div className={styles.row}>
                        <Input.Password placeholder="密码" value={this.state.loginPw} onChange={(e) => {this.setState({ loginPw: e.target.value })}} onKeyDown={(e) => {
                            if (e.keyCode == 13){
                                this.handleLogin()
                            }
                        }}/>
                    </div>
                    <div className={styles.row}>
                        <Button type="primary" onClick={this.handleLogin}>登录</Button>
                    </div>
                    <div className={styles.row}>
                        <Button onClick={() => {this.setState({ registering: true })}}>注册</Button>
                    </div>
                </div>
            }

        </div>
    }
}

export default LoginContainer