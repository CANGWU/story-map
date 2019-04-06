import React from 'react'
import { Modal, Input, message } from 'antd'
import styles from './InviteModal.scss'
import { baseURL, API } from '../../config'
import { fromJS } from 'immutable'

class InviteModal extends React.Component{
    state = {
        list: [],
        searchResult: null
    }
    handleOk = () => {
        if (this.state.list.length == 0){
            message.error('未选择邀请成员', 0.5)
            return
        }
        API.query(baseURL + `/member/invite?projectId=${this.props.projectId}`, {
            method: 'POST',
            body: JSON.stringify(
                this.state.list.map((v) => {
                    return {
                        privilegeGroup: 0,
                        userId: v.id,
                    }
                })
            )
        }).then((json) => {
            if (json.code == 0){
                message.success('成功发送邀请', 1)
                this.props.cb()
            }
            else {
                message.error('邀请失败，网络错误')
            }
            this.props.cancel()
        })
    }
    handleCancel = () => {
        this.props.cancel()
    }
    handleSearch = (e) => {
        if (e.target.value == ''){
            this.setState({
                keyword: e.target.value,
                searchResult: null
            })
            return
        }
        this.setState({
            keyword: e.target.value,
        })
        API.query(baseURL + `/user/search_by_email?email=${e.target.value}`, {
            method: 'POST',
            body: JSON.stringify({
                pageNumber: 0,
                pageSize: 99,
            })
        }).then((json) => {
            if (json.code == 0){
                this.setState({
                    searchResult: json.data.content.length !== 0 ? json.data.content : null,
                })
            }
            else {
                this.setState({
                    searchResult: null,
                })
            }
        })
    }
    render(){
        return <Modal
            visible
            wrapClassName={styles.inviteModal}
            width={500}
            cancelText="取消"
            okText="确定"
            title="邀请成员"
            onOk={this.handleOk}
            onCancel={this.handleCancel}
            maskClosable={false}
        >
            <div className={styles.container}>
                <div className={styles.row}>
                    <span className={styles.label}>标题:</span>
                    <Input placeholder="请输入邮箱..." value={this.state.keyword} onChange={this.handleSearch}/>
                </div>
                {
                    this.state.searchResult && this.state.searchResult.length !== 0 && <div className={styles.row}>
                        <span className={styles.label}>用户名:</span>
                        <span className={styles.searchResult}>{`${this.state.searchResult[0].username}(${this.state.searchResult[0].email})`}</span>
                        {
                            !fromJS(this.state.list).find((v) => v.get('id') === this.state.searchResult[0].id) && <span style={{ cursor: 'pointer', color: 'rgb(55, 152, 233)', marginLeft: '10px'}} onClick={() => {
                                let tmp = this.state.list
                                tmp.push(this.state.searchResult[0])
                                this.setState({
                                    list: tmp,
                                })
                            }}>添加</span>
                        }
                    </div>
                }
                {
                    this.state.searchResult && this.state.searchResult.length == 0 && <div className={styles.row}>没有搜索结果</div>
                }
                <div className={styles.row}>
                    <span className={styles.label}>已添加:</span>
                    <span style={{ wordBreak: 'break-all' }}>{this.state.list.map((v) => `${v.username}(${v.email})`).join(',')}</span>
                </div>
            </div>
        </Modal>
    }
}

export default InviteModal