import React from 'react'
import styles from './CreateProjectContainer.scss'
import { Input, Button, message } from 'antd'
import { baseURL, API } from '../config'

const { TextArea } = Input
class CreateProjectContainer extends React.Component{
    handleCreate = () => {
        let auth = JSON.parse(localStorage.getItem('auth')) || {}
        if (this.state.name == '' || this.state.des == '' || this.state.sign == ''){
            message.error('信息填写不全')
            return
        }
        API.query(baseURL + '/project/create', {
            method: 'POST',
            body: JSON.stringify({
                description: this.state.des,
                name: this.state.name,
                sign: this.state.sign,
            })
        }).then((json) => {
            if (json.code == 0){
                this.props.fresh()
                this.props.history.push('/index')
                message.success('创建成功')
            }
            else{
                message.error(json.data)
            }
        })
    }
    state = {
        name: '',
        des: '',
        sign: '',
    }
    render(){
        return <div className={styles.createProjectContainer}>
            <div className={styles.content}>
                <div className={styles.title}>新建项目</div>
                <hr/>
                <div className={styles.form}>
                    <div className={styles.row}>
                        <div className={styles.label}>项目名称:</div>
                        <div className={styles.value}>
                            <Input placeholder="请输入项目名称" value={this.state.name} onChange={(e) => { this.setState({ name: e.target.value})}}/>
                        </div>
                    </div>
                    <div className={styles.row}>
                        <div className={styles.label}>项目标识:</div>
                        <div className={styles.value}>
                            <Input placeholder="请输入项目标识" value={this.state.sign} onChange={(e) => { this.setState({ sign: e.target.value})}}/>
                        </div>
                    </div>
                    <div className={styles.row} style={{ alignItems: 'flex-start' }}>
                        <div className={styles.label} style={{ position: 'relative', top: '3px' }}>项目描述:</div>
                        <div className={styles.value}>
                            <TextArea placeholder="请输入项目描述" rows={4} value={this.state.des} onChange={(e) => {
                                this.setState({
                                    des: e.target.value.replace(/[\r\n]/g, "")
                                })
                            }} onKeyDown={(e) => {
                                if (e.keyCode == 13){
                                    this.handleCreate()
                                    return
                                }
                            }}/>
                        </div>
                    </div>
                    <div className={styles.bottom}>
                        <Button type="primary" onClick={this.handleCreate}>确定</Button>
                        <Button onClick={() => {this.props.history.push('/index')}}>取消</Button>
                    </div>
                </div>
            </div>

        </div>
    }
}

export default CreateProjectContainer