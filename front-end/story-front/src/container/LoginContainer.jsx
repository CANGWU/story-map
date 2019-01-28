import React from 'react'
import styles from './LoginContainer.scss'
import loginBg from '../resource/img/loginBg.jpg'
import { Icon, Input, Button } from 'antd'
import { Redirect } from 'react-router'
class LoginContainer extends React.Component{
    state = {
        loginWarning: false,
        registerWarning: false,
        registering: false,
    }
    handleLogin = () => {
        console.log('handle login')
        this.props.history.push('/index')
    }
    renderRegister = () => {
        return <div className={styles.content}>
            <div className={styles.row}>
                <Input placeholder="请输入登录名"/>
                <Icon className={styles.check} type="check" />
                {
                    this.state.registerWarning && <span className={styles.warning}>长度错误</span>
                }
            </div>
            <div className={styles.row}>
                <Input placeholder="请输入密码"/>
            </div>
            <div className={styles.row}>
                <Button type="primary">注册</Button>
            </div>
            <div className={styles.row}>
                <Button onClick={() => {this.setState({ registering: false })}}>返回登录</Button>
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
                        <Input placeholder="登录名"/>
                        {
                            this.state.loginWarning && <span className={styles.warning}>登录名或密码错误</span>
                        }
                    </div>
                    <div className={styles.row}>
                        <Input placeholder="密码"/>
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